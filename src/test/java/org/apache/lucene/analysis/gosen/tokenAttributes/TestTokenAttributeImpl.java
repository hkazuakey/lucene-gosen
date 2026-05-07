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

package org.apache.lucene.analysis.gosen.tokenAttributes;

import java.util.HashMap;
import java.util.Map;

import net.java.sen.dictionary.Morpheme;
import org.apache.lucene.tests.util.LuceneTestCase;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeReflector;
import org.junit.Test;

public class TestTokenAttributeImpl extends LuceneTestCase {

  private Morpheme makeMorpheme() {
    return new Morpheme(
        "名詞-一般", "不変化型", "基本形",
        "テスト", new String[]{"テスト"}, new String[]{"テスト"}, null
    );
  }

  // ---- ReadingsAttributeImpl ----

  @Test
  public void testReadingsAttributeNullMorpheme() {
    ReadingsAttributeImpl attr = new ReadingsAttributeImpl();
    assertNull(attr.getReadings());
  }

  @Test
  public void testReadingsAttributeWithMorpheme() {
    ReadingsAttributeImpl attr = new ReadingsAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    assertNotNull(attr.getReadings());
    assertFalse(attr.getReadings().isEmpty());
  }

  @Test
  public void testReadingsAttributeClear() {
    ReadingsAttributeImpl attr = new ReadingsAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    attr.clear();
    assertNull(attr.getReadings());
  }

  @Test
  public void testReadingsAttributeCopyTo() {
    ReadingsAttributeImpl src = new ReadingsAttributeImpl();
    src.setMorpheme(makeMorpheme());
    ReadingsAttributeImpl dst = new ReadingsAttributeImpl();
    src.copyTo(dst);
    assertNotNull(dst.getReadings());
  }

  @Test
  public void testReadingsAttributeReflectWith() {
    ReadingsAttributeImpl attr = new ReadingsAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    Map<String, Object> map = new HashMap<>();
    attr.reflectWith((clazz, key, value) -> map.put(key, value));
    assertTrue(map.containsKey("readings"));
    assertTrue(map.containsKey("readings (en)"));
  }

  @Test
  public void testReadingsAttributeReflectWithNullMorpheme() {
    ReadingsAttributeImpl attr = new ReadingsAttributeImpl();
    Map<String, Object> map = new HashMap<>();
    attr.reflectWith((clazz, key, value) -> map.put(key, value));
    assertNull(map.get("readings"));
    assertNull(map.get("readings (en)"));
  }

  // ---- PronunciationsAttributeImpl ----

  @Test
  public void testPronunciationsAttributeNullMorpheme() {
    PronunciationsAttributeImpl attr = new PronunciationsAttributeImpl();
    assertNull(attr.getPronunciations());
  }

  @Test
  public void testPronunciationsAttributeWithMorpheme() {
    PronunciationsAttributeImpl attr = new PronunciationsAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    assertNotNull(attr.getPronunciations());
  }

  @Test
  public void testPronunciationsAttributeClear() {
    PronunciationsAttributeImpl attr = new PronunciationsAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    attr.clear();
    assertNull(attr.getPronunciations());
  }

  @Test
  public void testPronunciationsAttributeCopyTo() {
    PronunciationsAttributeImpl src = new PronunciationsAttributeImpl();
    src.setMorpheme(makeMorpheme());
    PronunciationsAttributeImpl dst = new PronunciationsAttributeImpl();
    src.copyTo(dst);
    assertNotNull(dst.getPronunciations());
  }

  @Test
  public void testPronunciationsAttributeReflectWith() {
    PronunciationsAttributeImpl attr = new PronunciationsAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    Map<String, Object> map = new HashMap<>();
    attr.reflectWith((clazz, key, value) -> map.put(key, value));
    assertTrue(map.containsKey("pronunciations"));
    assertTrue(map.containsKey("pronunciations (en)"));
  }

  @Test
  public void testPronunciationsAttributeReflectWithNullMorpheme() {
    PronunciationsAttributeImpl attr = new PronunciationsAttributeImpl();
    Map<String, Object> map = new HashMap<>();
    attr.reflectWith((clazz, key, value) -> map.put(key, value));
    assertNull(map.get("pronunciations"));
    assertNull(map.get("pronunciations (en)"));
  }

  // ---- ConjugationAttributeImpl ----

  @Test
  public void testConjugationAttributeNullMorpheme() {
    ConjugationAttributeImpl attr = new ConjugationAttributeImpl();
    assertNull(attr.getConjugationalForm());
    assertNull(attr.getConjugationalType());
  }

  @Test
  public void testConjugationAttributeWithMorpheme() {
    ConjugationAttributeImpl attr = new ConjugationAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    assertEquals("基本形", attr.getConjugationalForm());
    assertEquals("不変化型", attr.getConjugationalType());
  }

  @Test
  public void testConjugationAttributeClear() {
    ConjugationAttributeImpl attr = new ConjugationAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    attr.clear();
    assertNull(attr.getConjugationalForm());
  }

  @Test
  public void testConjugationAttributeCopyTo() {
    ConjugationAttributeImpl src = new ConjugationAttributeImpl();
    src.setMorpheme(makeMorpheme());
    ConjugationAttributeImpl dst = new ConjugationAttributeImpl();
    src.copyTo(dst);
    assertEquals("基本形", dst.getConjugationalForm());
  }

  @Test
  public void testConjugationAttributeReflectWith() {
    ConjugationAttributeImpl attr = new ConjugationAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    Map<String, Object> map = new HashMap<>();
    attr.reflectWith((clazz, key, value) -> map.put(key, value));
    assertEquals("基本形", map.get("conjugationalForm"));
    assertEquals("base", map.get("conjugationalForm (en)"));
    assertEquals("不変化型", map.get("conjugationalType"));
    assertEquals("non-inflectional", map.get("conjugationalType (en)"));
  }

  // ---- PartOfSpeechAttributeImpl ----

  @Test
  public void testPartOfSpeechAttributeNullMorpheme() {
    PartOfSpeechAttributeImpl attr = new PartOfSpeechAttributeImpl();
    assertNull(attr.getPartOfSpeech());
  }

  @Test
  public void testPartOfSpeechAttributeWithMorpheme() {
    PartOfSpeechAttributeImpl attr = new PartOfSpeechAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    assertEquals("名詞-一般", attr.getPartOfSpeech());
  }

  @Test
  public void testPartOfSpeechAttributeClear() {
    PartOfSpeechAttributeImpl attr = new PartOfSpeechAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    attr.clear();
    assertNull(attr.getPartOfSpeech());
  }

  @Test
  public void testPartOfSpeechAttributeCopyTo() {
    PartOfSpeechAttributeImpl src = new PartOfSpeechAttributeImpl();
    src.setMorpheme(makeMorpheme());
    PartOfSpeechAttributeImpl dst = new PartOfSpeechAttributeImpl();
    src.copyTo(dst);
    assertEquals("名詞-一般", dst.getPartOfSpeech());
  }

  @Test
  public void testPartOfSpeechAttributeReflectWith() {
    PartOfSpeechAttributeImpl attr = new PartOfSpeechAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    Map<String, Object> map = new HashMap<>();
    attr.reflectWith((clazz, key, value) -> map.put(key, value));
    assertEquals("名詞-一般", map.get("partOfSpeech"));
    assertEquals("noun-common", map.get("partOfSpeech (en)"));
  }

  // ---- BasicFormAttributeImpl ----

  @Test
  public void testBasicFormAttributeNullMorpheme() {
    BasicFormAttributeImpl attr = new BasicFormAttributeImpl();
    assertNull(attr.getBasicForm());
  }

  @Test
  public void testBasicFormAttributeWithMorpheme() {
    BasicFormAttributeImpl attr = new BasicFormAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    assertEquals("テスト", attr.getBasicForm());
  }

  @Test
  public void testBasicFormAttributeClear() {
    BasicFormAttributeImpl attr = new BasicFormAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    attr.clear();
    assertNull(attr.getBasicForm());
  }

  @Test
  public void testBasicFormAttributeCopyTo() {
    BasicFormAttributeImpl src = new BasicFormAttributeImpl();
    src.setMorpheme(makeMorpheme());
    BasicFormAttributeImpl dst = new BasicFormAttributeImpl();
    src.copyTo(dst);
    assertEquals("テスト", dst.getBasicForm());
  }

  @Test
  public void testBasicFormAttributeReflectWith() {
    BasicFormAttributeImpl attr = new BasicFormAttributeImpl();
    attr.setMorpheme(makeMorpheme());
    Map<String, Object> map = new HashMap<>();
    attr.reflectWith((clazz, key, value) -> map.put(key, value));
    assertEquals("テスト", map.get("basicForm"));
  }

  // ---- CostAttributeImpl ----

  @Test
  public void testCostAttributeDefault() {
    CostAttributeImpl attr = new CostAttributeImpl();
    assertEquals(0, attr.getCost());
  }

  @Test
  public void testCostAttributeSetGet() {
    CostAttributeImpl attr = new CostAttributeImpl();
    attr.setCost(42);
    assertEquals(42, attr.getCost());
  }

  @Test
  public void testCostAttributeClear() {
    CostAttributeImpl attr = new CostAttributeImpl();
    attr.setCost(42);
    attr.clear();
    assertEquals(0, attr.getCost());
  }

  @Test
  public void testCostAttributeCopyTo() {
    CostAttributeImpl src = new CostAttributeImpl();
    src.setCost(99);
    CostAttributeImpl dst = new CostAttributeImpl();
    src.copyTo(dst);
    assertEquals(99, dst.getCost());
  }

  @Test
  public void testCostAttributeReflectWith() {
    CostAttributeImpl attr = new CostAttributeImpl();
    attr.setCost(7);
    Map<String, Object> map = new HashMap<>();
    attr.reflectWith((clazz, key, value) -> map.put(key, value));
    assertEquals(7, map.get("cost"));
  }

  // ---- SentenceStartAttributeImpl ----

  @Test
  public void testSentenceStartAttributeDefault() {
    SentenceStartAttributeImpl attr = new SentenceStartAttributeImpl();
    assertFalse(attr.getSentenceStart());
  }

  @Test
  public void testSentenceStartAttributeSetGet() {
    SentenceStartAttributeImpl attr = new SentenceStartAttributeImpl();
    attr.setSentenceStart(true);
    assertTrue(attr.getSentenceStart());
  }

  @Test
  public void testSentenceStartAttributeClear() {
    SentenceStartAttributeImpl attr = new SentenceStartAttributeImpl();
    attr.setSentenceStart(true);
    attr.clear();
    assertFalse(attr.getSentenceStart());
  }

  @Test
  public void testSentenceStartAttributeCopyTo() {
    SentenceStartAttributeImpl src = new SentenceStartAttributeImpl();
    src.setSentenceStart(true);
    SentenceStartAttributeImpl dst = new SentenceStartAttributeImpl();
    src.copyTo(dst);
    assertTrue(dst.getSentenceStart());
  }

  @Test
  public void testSentenceStartAttributeReflectWith() {
    SentenceStartAttributeImpl attr = new SentenceStartAttributeImpl();
    attr.setSentenceStart(true);
    Map<String, Object> map = new HashMap<>();
    attr.reflectWith((clazz, key, value) -> map.put(key, value));
    assertEquals(true, map.get("sentenceStartFrom"));
  }
}
