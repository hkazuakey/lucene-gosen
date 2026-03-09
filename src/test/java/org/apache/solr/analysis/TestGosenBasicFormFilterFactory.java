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

package org.apache.solr.analysis;

import net.java.sen.SenTestUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.gosen.GosenTokenizer;
import org.apache.lucene.tests.util.LuceneTestCase;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;

import static org.apache.lucene.tests.analysis.BaseTokenStreamTestCase.assertTokenStreamContents;

public class TestGosenBasicFormFilterFactory extends LuceneTestCase {

  @Test
  public void testBogusArguments() throws Exception{
    try{
      new GosenBasicFormFilterFactory(new HashMap<String, String>() {{
        put("bogusArg", "bogusValue");
      }});
      fail();
    } catch (IllegalArgumentException expected) {
      assertTrue(expected.getMessage().contains("Unknown parameters"));
    }
  }

  @Test
  public void testCreate() throws Exception {
    GosenBasicFormFilterFactory factory = new GosenBasicFormFilterFactory(new HashMap<>());
    Tokenizer tokenizer = new GosenTokenizer(null, SenTestUtil.IPADIC_DIR, false);
    tokenizer.setReader(new StringReader("落ちた"));
    TokenStream stream = factory.create(tokenizer);
    assertNotNull(stream);
    assertTokenStreamContents(stream, new String[]{"落ちる", "た"});
  }
}
