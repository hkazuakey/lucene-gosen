/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.lucene.analysis.gosen;

import java.io.IOException;
import java.io.Reader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.java.sen.SenTagger;
import net.java.sen.SenTokenizer;
import net.java.sen.dictionary.Dictionary;
import net.java.sen.dictionary.IPADictionary;
import net.java.sen.dictionary.Morpheme;
import net.java.sen.dictionary.Token;
import net.java.sen.filter.StreamFilter;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.gosen.tokenAttributes.BasicFormAttribute;
import org.apache.lucene.analysis.gosen.tokenAttributes.ConjugationAttribute;
import org.apache.lucene.analysis.gosen.tokenAttributes.CostAttribute;
import org.apache.lucene.analysis.gosen.tokenAttributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.gosen.tokenAttributes.PronunciationsAttribute;
import org.apache.lucene.analysis.gosen.tokenAttributes.ReadingsAttribute;
import org.apache.lucene.analysis.gosen.tokenAttributes.SentenceStartAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeFactory;

/**
 * This is a Japanese tokenizer which uses "Sen" morphological
 * analyzer.
 * <p>
 * sets the surface form as the term text, but also sets these attributes:
 * <ul>
 * <li>{@link BasicFormAttribute}
 * <li>{@link ConjugationAttribute}
 * <li>{@link PartOfSpeechAttribute}
 * <li>{@link PronunciationsAttribute}
 * <li>{@link ReadingsAttribute}
 * <li>{@link CostAttribute}
 * <li>{@link SentenceStartAttribute}
 * </ul>
 */
public final class GosenTokenizer extends Tokenizer {

  private final SenTagger tagger;

  private static final int IOBUFFER = 4096;
  private final char buffer[] = new char[IOBUFFER];

  // Term attributes
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
  private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

  // morphological attributes
  private final BasicFormAttribute basicFormAtt = addAttribute(BasicFormAttribute.class);
  private final ConjugationAttribute conjugationAtt = addAttribute(ConjugationAttribute.class);
  private final PartOfSpeechAttribute partOfSpeechAtt = addAttribute(PartOfSpeechAttribute.class);
  private final PronunciationsAttribute pronunciationsAtt = addAttribute(PronunciationsAttribute.class);
  private final ReadingsAttribute readingsAtt = addAttribute(ReadingsAttribute.class);

  // sentence increment
  private final SentenceStartAttribute sentenceAtt = addAttribute(SentenceStartAttribute.class);

  // Viterbi cost
  private final CostAttribute costAtt = addAttribute(CostAttribute.class);
  // Viterbi costs from Token.getCost() are cumulative,
  // so we accumulate this so we can then subtract to present an absolute cost.
  private int accumulatedCost = 0;

  // true length of text in the buffer
  private int length = 0;
  // length in buffer that can be evaluated safely, up to a safe end point
  private int usableLength = 0;
  // accumulated offset of previous buffers for this reader, for offsetAtt
  private int accumulateOffset = 0;

  private final BreakIterator breaker = BreakIterator.getSentenceInstance(Locale.JAPANESE); /* tokenizes a char[] of text */
  private final CharArrayIterator iterator = new CharArrayIterator();

  private List<Token> tokens = new ArrayList<Token>();
  private int tokenIndex = 0;

  // Default value for UNKNOWN Katakana tokenization
  public static final boolean DEFAULT_UNKNOWN_KATAKANA_TOKENIZATION = false;


  /**
   * Constructor
   */
  public GosenTokenizer(StreamFilter filter, String dictionaryDir, boolean tokenizeUnknownKatakana) {
    this(DEFAULT_TOKEN_ATTRIBUTE_FACTORY, filter, dictionaryDir, null, tokenizeUnknownKatakana);
  }

  /**
   * Constructor
   */
  public GosenTokenizer(AttributeFactory factory, StreamFilter filter, String dictionaryDir, boolean tokenizeUnknownKatakana) {
    this(factory, filter, dictionaryDir, null, tokenizeUnknownKatakana);
  }

  /**
   * Create A new GosenTokenizer
   *
   * @param factory
   * @param filter
   * @param dictionaryDir
   * @param userDictionaryName
   * @param tokenizeUnknownKatakana
   */
  public GosenTokenizer(AttributeFactory factory, StreamFilter filter, String dictionaryDir, String userDictionaryName, boolean tokenizeUnknownKatakana) {
    super(factory);

    Dictionary dictionary = null;
    try {
      dictionary = new IPADictionary(dictionaryDir);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (dictionaryDir != null) {
      this.tagger = new SenTagger(new SenTokenizer(dictionary, null, false));
      if (filter != null) {
        this.tagger.addFilter(filter);
      }
    } else {
      this.tagger = null;
    }
  }

  @Override
  public boolean incrementToken() throws IOException {
    Token token = next();
    if (token == null) {
      return false;
    } else {
      clearAttributes();
      final Morpheme m = token.getMorpheme();

      // note, unlike the previous implementation, we set the surface form
      termAtt.setEmpty().append(token.getSurface());
      final int cost = token.getCost();

      if (token.isSentenceStart()) {
        accumulatedCost = 0;
        sentenceAtt.setSentenceStart(true);
      }

      costAtt.setCost(cost - accumulatedCost);
      accumulatedCost = cost;
      basicFormAtt.setMorpheme(m);
      conjugationAtt.setMorpheme(m);
      partOfSpeechAtt.setMorpheme(m);
      pronunciationsAtt.setMorpheme(m);
      readingsAtt.setMorpheme(m);
      offsetAtt.setOffset(correctOffset(token.getStart()), correctOffset(token.end()));
      return true;
    }
  }

  @Override
  public void close() throws IOException {
    super.close();
    resetBuffers();
  }

  @Override
  public void reset() throws IOException {
    super.reset();
    resetBuffers();
    accumulatedCost = 0;
  }

  @Override
  public void end() throws IOException {
    super.end();
    // Set finalOffset value
    int tmpFinalOffset = (length < 0) ? accumulateOffset : accumulateOffset + length;
    final int finalOffset = correctOffset(tmpFinalOffset);
    offsetAtt.setOffset(finalOffset, finalOffset);
  }

  //-----------------------------------------------------------------------------------------------------------------

  private Token next() throws IOException {
    if (tokens == null || tokenIndex >= tokens.size()) {
      if (length == 0) {
        refill();
      }
      while (!incrementTokenBuffer()) {
        refill();
        if (length <= 0) {
          // no more bytes to read;
          return null;
        }
      }
    }
    return tokens.get(tokenIndex++);
  }

  private void refill() throws IOException {
    accumulateOffset += usableLength;

    int leftover = length - usableLength;
    System.arraycopy(buffer, usableLength, buffer, 0, leftover);

    int requested = buffer.length - leftover;
    int returned = read(input, buffer, leftover, requested);
    length = returned < 0 ? leftover : returned + leftover;
    if (returned < requested) {
      /* reader has been emptied, process the rest */
      usableLength = length;
    } else {
      /* still more data to be read, find a safe-stopping place */
      usableLength = findSafeEnd();
      if (usableLength < 0) {
        usableLength = length;
        /* more than IOBUFFER of text without breaks,
         * gonna possibly truncate tokens
         */
      }
    }

    iterator.setText(buffer, 0, Math.max(0, usableLength));
    breaker.setText(iterator);
  }

  private static int read(Reader input, char[] buffer, int offset, int length) throws IOException {
    assert length >= 0 : "length must not be negative: " + length;

    int remaining = length;
    while (remaining > 0) {
      int location = length - remaining;
      int count = input.read(buffer, offset + location, remaining);
      if (-1 == count) { // EOF
        break;
      }
      remaining -= count;
    }
    return length - remaining;
  }

  private boolean incrementTokenBuffer() throws IOException {
    while (true) {
      int start = breaker.current();
      if (start == BreakIterator.DONE) {
        return false; // BreakIterator exhausted
      }

      // find the next set of boundaries
      int end = breaker.next();
      if (end == BreakIterator.DONE) {
        return false; // BreakIterator exhausted
      }

      String text = new String(buffer, start, end - start);
      tokens = tagger.analyze(text, tokens);

      if (tokens != null && !tokens.isEmpty()) {
        for (int i = 0; i < tokens.size(); i++) {
          Token token = tokens.get(i);
          token.setSentenceStart(i == 0);
          token.setStart(token.getStart() + start + accumulateOffset);
        }
        tokenIndex = 0;
        return true;
      }
    }
  }

  private void resetBuffers() {
    iterator.setText(buffer, 0, 0);
    breaker.setText(iterator);
    length = usableLength = accumulateOffset = tokenIndex = 0;
    tokens.clear();
  }

  private int findSafeEnd() {
    for (int i = length - 1; i >= 0; i--) {
      if (isSafeEnd(buffer[i])) {
        return i + 1;
      }
    }
    return -1;
  }

  private boolean isSafeEnd(char ch) {
    switch (ch) {
      case 0x000D:
      case 0x000A:
      case 0x0085:
      case 0x2028:
      case 0x2029:
        return true;
      default:
        return false;
    }
  }


//  private void loadDictionary(String dictionaryDir, String userDictionaryPath) throws IOException {
//    // Read main dictionary files
//    SenFactory.GosenDictionaryHandler mainDictionary = new SenFactory.GosenDictionaryHandler();
//    loadDictionary(dictionaryDir, mainDictionary);
//
//    // Read user dictionary files
//    SenFactory.GosenDictionaryHandler userDictionary = null;
//    if (userDictionaryPath != null && !dictionaryDir.equals(userDictionaryPath)) {
//      userDictionary = new SenFactory.GosenDictionaryHandler();
//      loadDictionary(userDictionaryPath, userDictionary);
//    }
//
//    if (userDictionary == null) {
//      costs = mainDictionary.costs;
//      pos = mainDictionary.pos;
//      tokens = mainDictionary.tokens;
//      trie = mainDictionary.trie;
//      posIndex = mainDictionary.posIndex;
//      conjTypeIndex = mainDictionary.conjTypeIndex;
//      conjFormIndex = mainDictionary.conjFormIndex;
//    } else {
//      // Merge the main and user dictionary data
//      int size;
//      // costs
//      costs = concatByteBuffer(mainDictionary.costs, userDictionary.costs);
//      // pos
//      pos = concatByteBuffer(mainDictionary.pos, userDictionary.pos);
//      // tokens
//      tokens = concatByteBuffer(mainDictionary.tokens, userDictionary.tokens);
//      // trie
//      trie = concatByteBuffer(mainDictionary.trie, userDictionary.trie);
//
//      // posIndex
//      posIndex = concatStringArray(mainDictionary.posIndex, userDictionary.posIndex);
//      // conjTypeIndex
//      conjTypeIndex = concatStringArray(mainDictionary.conjTypeIndex, userDictionary.conjTypeIndex);
//      // conjFormIndex
//      conjFormIndex = concatStringArray(mainDictionary.conjFormIndex, userDictionary.conjFormIndex);
//    }
//  }

}
