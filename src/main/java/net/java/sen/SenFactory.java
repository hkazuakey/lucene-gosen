/*
 * Copyright (C) 2002-2007
 * Takashi Okamoto <tora@debian.org>
 * Matt Francis <asbel@neosheffield.co.uk>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package net.java.sen;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.java.sen.util.IOUtils;

import net.java.sen.dictionary.Dictionary;
import net.java.sen.dictionary.Tokenizer;
import net.java.sen.dictionary.Viterbi;
import net.java.sen.tokenizers.ja.JapaneseTokenizer;

/**
 * A factory to manage creation of {@link Viterbi}, {@link SenTagger}, and
 * {@link ReadingProcessor} objects<br><br>
 * <p>
 * <b>Thread Safety:</b> This class and all its public methods are thread safe.
 * The objects constructed by the factory are <b>NOT</b> thread safe and should
 * not be accessed simultaneously by multiple threads
 */
public class SenFactory {

  private static final Map<String, SenFactory> map = new ConcurrentHashMap<String, SenFactory>();
  private static final String EMPTY_DICTIONARYDIR_KEY = "NO_DICTIONARY_INSTANCE";

  private static final String DICT_HEADER_FILE = "header.sen";
  private static final String DICT_CONNECTION_COST_FILE = "connectionCost.sen";
  private static final String DICT_POS_FILE = "partOfSpeech.sen";
  private static final String DICT_TOKEN_FILE = "token.sen";
  private static final String DICT_TRIE_FILE = "trie.sen";
  private static final String DICT_POSINDEX_FILE = "posIndex.sen";
  private static final String DICT_CHAR_CLASS_FILE = "charClass.sen";
  private static final String DICT_DEFAULT_POS_FILE = "defaultPos.sen";

  private static final String unknownPOS = "未知語";

  private final String[] posIndex, conjTypeIndex, conjFormIndex;
  private final ByteBuffer costs, pos, tokens, trie;

  private class GosenDictionaryHandler {
    ByteBuffer costs;
    ByteBuffer pos;
    ByteBuffer tokens;
    ByteBuffer trie;
    String[] posIndex;
    String[] conjTypeIndex;
    String[] conjFormIndex;
  }

  /**
   * @param dictionaryDir a directory of dictionaries
   * @return the factory instance
   */
  public static SenFactory getInstance(String dictionaryDir) {
    return getInstance(dictionaryDir, null);
  }

  /**
   * Get the singleton factory instance
   *
   * @param dictionaryDir
   * @param userDictionaryDir
   * @return
   */
  public synchronized static SenFactory getInstance(String dictionaryDir, String userDictionaryDir) {
    // Only the main dictionary path can be a key
    String key = (dictionaryDir == null || dictionaryDir.trim().length() == 0) ? EMPTY_DICTIONARYDIR_KEY : dictionaryDir;
    SenFactory instance = map.get(key);
    if (instance == null) {
      try {
        instance = new SenFactory(dictionaryDir, userDictionaryDir);
        map.put(key, instance);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    return instance;
  }

  /**
   * Constructor for Sen, which is an Japanese Morphological Analyzer
   *
   * @param dictionaryDir
   * @throws IOException
   */
  private SenFactory(String dictionaryDir) throws IOException {
    this(dictionaryDir, null);
  }

  private SenFactory(String dictionaryDir, String userDictionaryDir) throws IOException {
    // Read main dictionary files
    GosenDictionaryHandler mainDictionary = new GosenDictionaryHandler();
    loadDictionary(dictionaryDir, mainDictionary);

    // Read user dictionary files
    GosenDictionaryHandler userDictionary = null;
    if (userDictionaryDir != null && !dictionaryDir.equals(userDictionaryDir)) {
      userDictionary = new GosenDictionaryHandler();
      loadDictionary(userDictionaryDir, userDictionary);
    }

    if (userDictionary == null) {
      costs = mainDictionary.costs;
      pos = mainDictionary.pos;
      tokens = mainDictionary.tokens;
      trie = mainDictionary.trie;
      posIndex = mainDictionary.posIndex;
      conjTypeIndex = mainDictionary.conjTypeIndex;
      conjFormIndex = mainDictionary.conjFormIndex;
    } else {
      // Merge the main and user dictionary data
      int size;
      // costs
      costs = concatByteBuffer(mainDictionary.costs, userDictionary.costs);
      // pos
      pos = concatByteBuffer(mainDictionary.pos, userDictionary.pos);
      // tokens
      tokens = concatByteBuffer(mainDictionary.tokens, userDictionary.tokens);
      // trie
      trie = concatByteBuffer(mainDictionary.trie, userDictionary.trie);

      // posIndex
      posIndex = concatStringArray(mainDictionary.posIndex, userDictionary.posIndex);
      // conjTypeIndex
      conjTypeIndex = concatStringArray(mainDictionary.conjTypeIndex, userDictionary.conjTypeIndex);
      // conjFormIndex
      conjFormIndex = concatStringArray(mainDictionary.conjFormIndex, userDictionary.conjFormIndex);
    }
  }

  /**
   * @param a
   * @param b
   * @return
   */
  private ByteBuffer concatByteBuffer(ByteBuffer a, ByteBuffer b) {
    int size = a.capacity() + b.capacity();
    ByteBuffer results = ByteBuffer.allocate(size).put(a).put(b);
    results.rewind();
    return results;
  }

  /**
   * @param a
   * @param b
   * @return
   */
  private String[] concatStringArray(String[] a, String[] b) {
    String[] results = new String[a.length + b.length];
    for (int i = 0, j = 0, x = 0; i < a.length || j < b.length; x++) {
      if (j == b.length || (i != a.length && a[i].compareTo(b[j]) <= 0)) {
        results[x] = a[i++];
      } else {
        results[x] = b[j++];
      }
    }
    return results;
  }

  /**
   * @param dictPath
   * @throws IOException
   */
  private void loadDictionary(String dictPath, GosenDictionaryHandler handler) throws IOException {
    InputStream in = null;
    DataInputStream din = null;

    // Read data files
    try {
      in = getInputStream(DICT_HEADER_FILE, dictPath);
      din = new DataInputStream(in);
      handler.costs = loadBuffer(DICT_CONNECTION_COST_FILE, din.readInt(), dictPath).asReadOnlyBuffer();
      handler.pos = loadBuffer(DICT_POS_FILE, din.readInt(), dictPath).asReadOnlyBuffer();
      handler.tokens = loadBuffer(DICT_TOKEN_FILE, din.readInt(), dictPath).asReadOnlyBuffer();
      handler.trie = loadBuffer(DICT_TRIE_FILE, din.readInt(), dictPath).asReadOnlyBuffer();
    } finally {
      IOUtils.closeWhileHandlingException(din, in);
    }

    // Read index files
    try {
      in = getInputStream(DICT_POSINDEX_FILE, dictPath);
      din = new DataInputStream(in);
      handler.posIndex = new String[din.readChar()];
      for (int i = 0; i < handler.posIndex.length; i++) {
        handler.posIndex[i] = din.readUTF();
      }

      handler.conjTypeIndex = new String[din.readChar()];
      for (int i = 0; i < handler.conjTypeIndex.length; i++) {
        handler.conjTypeIndex[i] = din.readUTF();
      }

      handler.conjFormIndex = new String[din.readChar()];
      for (int i = 0; i < handler.conjFormIndex.length; i++) {
        handler.conjFormIndex[i] = din.readUTF();
      }
    } finally {
      IOUtils.closeWhileHandlingException(din, in);
    }
  }

  /**
   * @param name
   * @param dictionaryDir
   * @return
   * @throws IOException
   */
  private static InputStream getInputStream(String name, String dictionaryDir) throws IOException {
    InputStream in = null;
    if (dictionaryDir == null || dictionaryDir.trim().length() == 0) {
      in = SenFactory.class.getResourceAsStream(name);
    } else {
      in = new FileInputStream(new File(dictionaryDir, name));
    }
    if (in == null) {
      throw new RuntimeException("Not found resource[" + name + "]. dictionaryDir=[" + dictionaryDir + "]");
    }
    return in;
  }

  /**
   * Load specified dictionary data into ByteBuffer
   *
   * @param resource
   * @param size
   * @param dictionaryDir
   * @return
   * @throws IOException
   */
  private static ByteBuffer loadBuffer(String resource, int size, String dictionaryDir) throws IOException {
    InputStream in = null;
    try {
      in = getInputStream(resource, dictionaryDir);
      ByteBuffer buffer = ByteBuffer.allocateDirect(size);
      buffer.limit(size);

      byte[] buf = new byte[1024];

      while (true) {
        int numBytes = in.read(buf);
        if (numBytes == -1) break;

        buffer.put(buf, 0, numBytes);
      }

      buffer.rewind();

      return buffer;
    } finally {
      IOUtils.closeWhileHandlingException(in);
    }
  }

  /**
   * Builds a Tokenizer for the given dictionary configuration
   *
   * @param dictionaryDir           The dictionary configuration filename
   * @param tokenizeUnknownKatakana
   * @return The constructed Tokenizer
   */
  private static Tokenizer getTokenizer(String dictionaryDir, boolean tokenizeUnknownKatakana) {
    return getTokenizer(dictionaryDir, null, tokenizeUnknownKatakana);
  }

  private static Tokenizer getTokenizer(String dictionaryDir, String userDictionaryDir, boolean tokenizeUnknownKatakana) {
    SenFactory localInstance = SenFactory.getInstance(dictionaryDir, userDictionaryDir);

    return new JapaneseTokenizer(
        new Dictionary(
            localInstance.costs.asShortBuffer(),
            localInstance.pos.duplicate(),
            localInstance.tokens.duplicate(),
            localInstance.trie.asIntBuffer(),
            localInstance.posIndex,
            localInstance.conjTypeIndex,
            localInstance.conjFormIndex
        ),
        unknownPOS,
        tokenizeUnknownKatakana);
  }

  /**
   * Creates a Viterbi from the given configuration
   *
   * @param dictionaryDir           a directory of dictionary
   * @param tokenizeUnknownKatakana
   * @return A Viterbi
   */
  static Viterbi getViterbi(String dictionaryDir, boolean tokenizeUnknownKatakana) {
    // for test only
    return new Viterbi(getTokenizer(dictionaryDir, tokenizeUnknownKatakana));
  }

  /**
   * Creates a SenTagger from the given configuration
   *
   * @param dictionaryDir           a directory of dictionary
   * @param tokenizeUnknownKatakana
   * @return A SenTagger
   */
  public static SenTagger getStringTagger(String dictionaryDir, boolean tokenizeUnknownKatakana) {
    return getStringTagger(dictionaryDir, null, tokenizeUnknownKatakana);
  }

  /**
   * Creates a SenTagger from the given configuration
   *
   * @param dictionaryDir           directory of a dictionary
   * @param userDictionaryDir       directory or a user dictionary
   * @param tokenizeUnknownKatakana
   * @return
   */
  public static SenTagger getStringTagger(String dictionaryDir, String userDictionaryDir, boolean tokenizeUnknownKatakana) {
    return new SenTagger(getTokenizer(dictionaryDir, userDictionaryDir, tokenizeUnknownKatakana));
  }

  /**
   * Creates a ReadingProcessor from the given configuration
   *
   * @param dictionaryDir
   * @param tokenizeUnknownKatakana
   * @return The reading processor
   */
  static ReadingProcessor getReadingProcessor(String dictionaryDir, boolean tokenizeUnknownKatakana) {
    //for test only
    return new ReadingProcessor(getTokenizer(dictionaryDir, tokenizeUnknownKatakana));
  }

}
