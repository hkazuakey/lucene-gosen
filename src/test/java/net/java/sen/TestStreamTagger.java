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

package net.java.sen;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.java.sen.dictionary.Token;
import org.apache.lucene.tests.util.LuceneTestCase;
import org.junit.Test;

public class TestStreamTagger extends LuceneTestCase {

  @Test
  public void testBasicTokenization() throws Exception {
    StringTagger tagger = SenTestUtil.getStringTagger();
    StreamTagger streamTagger = new StreamTagger(tagger, new StringReader("東京都"));
    List<Token> tokens = new ArrayList<>();
    while (streamTagger.hasNext()) {
      tokens.add(streamTagger.next());
    }
    assertFalse("Should have tokens", tokens.isEmpty());
  }

  @Test
  public void testMultipleSentences() throws Exception {
    StringTagger tagger = SenTestUtil.getStringTagger();
    StreamTagger streamTagger = new StreamTagger(tagger, new StringReader("東京都。大阪府。"));
    List<Token> tokens = new ArrayList<>();
    while (streamTagger.hasNext()) {
      tokens.add(streamTagger.next());
    }
    assertFalse("Should have tokens", tokens.isEmpty());
  }

  @Test
  public void testNextMethod() throws Exception {
    StringTagger tagger = SenTestUtil.getStringTagger();
    StreamTagger streamTagger = new StreamTagger(tagger, new StringReader("東京"));
    Token token = streamTagger.next();
    assertNotNull(token);
  }

  @Test
  public void testEmptyInput() throws Exception {
    StringTagger tagger = SenTestUtil.getStringTagger();
    StreamTagger streamTagger = new StreamTagger(tagger, new StringReader(""));
    assertFalse("Empty input should return no tokens", streamTagger.hasNext());
  }

  @Test
  public void testNextOnEmptyReturnsNull() throws Exception {
    StringTagger tagger = SenTestUtil.getStringTagger();
    StreamTagger streamTagger = new StreamTagger(tagger, new StringReader(""));
    Token token = streamTagger.next();
    assertNull("next() on empty should return null", token);
  }

  @Test
  public void testSentenceStartFlag() throws Exception {
    StringTagger tagger = SenTestUtil.getStringTagger();
    StreamTagger streamTagger = new StreamTagger(tagger, new StringReader("東京都"));
    Token first = null;
    while (streamTagger.hasNext()) {
      Token t = streamTagger.next();
      if (first == null) first = t;
    }
    assertNotNull(first);
    assertTrue("First token should have sentence start", first.isSentenceStart());
  }
}
