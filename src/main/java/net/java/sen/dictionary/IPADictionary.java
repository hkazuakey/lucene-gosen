package net.java.sen.dictionary;

import net.java.sen.util.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;

public class IPADictionary extends Dictionary {

  private static final String DICT_HEADER_FILE = "header.sen";
  private static final String DICT_CONNECTION_COST_FILE = "connectionCost.sen";
  private static final String DICT_POS_FILE = "partOfSpeech.sen";
  private static final String DICT_TOKEN_FILE = "token.sen";
  private static final String DICT_TRIE_FILE = "trie.sen";
  private static final String DICT_POSINDEX_FILE = "posIndex.sen";
  private static final String DICT_CHAR_CLASS_FILE = "charClass.sen";
  private static final String DICT_DEFAULT_POS_FILE = "defaultPos.sen";

  private static final String unknownPOS = "未知語";

  private static ByteBuffer costs;
  private ByteBuffer pos;
  private ByteBuffer tokens;
  private ByteBuffer trie;
  private String[] posIndex;
  private String[] conjTypeIndex;
  private String[] conjFormIndex;

  /**
   * Constructor
   */
  public IPADictionary(String dictPath) throws IOException {

    InputStream in = null;
    DataInputStream din = null;

    // Read data files
    try {
      in = getInputStream(DICT_HEADER_FILE, dictPath);
      din = new DataInputStream(in);
      costs = loadBuffer(DICT_CONNECTION_COST_FILE, din.readInt(), dictPath).asReadOnlyBuffer();
      pos = loadBuffer(DICT_POS_FILE, din.readInt(), dictPath).asReadOnlyBuffer();
      tokens = loadBuffer(DICT_TOKEN_FILE, din.readInt(), dictPath).asReadOnlyBuffer();
      trie = loadBuffer(DICT_TRIE_FILE, din.readInt(), dictPath).asReadOnlyBuffer();
    } finally {
      IOUtils.closeWhileHandlingException(din, in);
    }

    // Read index files
    try {
      in = getInputStream(DICT_POSINDEX_FILE, dictPath);
      din = new DataInputStream(in);
      posIndex = new String[din.readChar()];
      for (int i = 0; i < posIndex.length; i++) {
        posIndex[i] = din.readUTF();
      }

      conjTypeIndex = new String[din.readChar()];
      for (int i = 0; i < conjTypeIndex.length; i++) {
        conjTypeIndex[i] = din.readUTF();
      }

      conjFormIndex = new String[din.readChar()];
      for (int i = 0; i < conjFormIndex.length; i++) {
        conjFormIndex[i] = din.readUTF();
      }
    } finally {
      IOUtils.closeWhileHandlingException(din, in);
    }

    super.loadSenDictionary(costs.asShortBuffer(), pos, tokens, trie.asIntBuffer(), posIndex, conjTypeIndex, conjFormIndex);
  }

}
