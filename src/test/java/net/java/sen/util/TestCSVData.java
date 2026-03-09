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

package net.java.sen.util;

import org.apache.lucene.tests.util.LuceneTestCase;
import org.junit.Test;

public class TestCSVData extends LuceneTestCase {

  @Test
  public void testAppendAndToString() {
    CSVData csv = new CSVData();
    csv.append("hello");
    csv.append("world");
    assertEquals("hello,world", csv.toString());
  }

  @Test
  public void testEmpty() {
    CSVData csv = new CSVData();
    assertEquals("", csv.toString());
  }

  @Test
  public void testSingleElement() {
    CSVData csv = new CSVData();
    csv.append("test");
    assertEquals("test", csv.toString());
  }

  @Test
  public void testEnquoteWithComma() {
    CSVData csv = new CSVData();
    csv.append("a,b");
    assertEquals("\"a,b\"", csv.toString());
  }

  @Test
  public void testEnquoteWithDoubleQuote() {
    CSVData csv = new CSVData();
    csv.append("say \"hi\"");
    assertEquals("\"say \"\"hi\"\"\"", csv.toString());
  }

  @Test
  public void testEnquoteEmptyString() {
    CSVData csv = new CSVData();
    csv.append("");
    assertEquals("", csv.toString());
  }

  @Test
  public void testInsert() {
    CSVData csv = new CSVData();
    csv.append("a");
    csv.append("c");
    csv.insert(1, "b");
    assertEquals("a,b,c", csv.toString());
  }

  @Test
  public void testRemove() {
    CSVData csv = new CSVData();
    csv.append("a");
    csv.append("b");
    csv.append("c");
    csv.remove(1);
    assertEquals("a,c", csv.toString());
  }

  @Test
  public void testSet() {
    CSVData csv = new CSVData();
    csv.append("a");
    csv.append("b");
    csv.set(1, "B");
    assertEquals("a,B", csv.toString());
  }

  @Test
  public void testClear() {
    CSVData csv = new CSVData();
    csv.append("a");
    csv.append("b");
    csv.clear();
    assertEquals("", csv.toString());
  }
}
