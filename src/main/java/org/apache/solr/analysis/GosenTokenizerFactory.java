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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import net.java.sen.filter.stream.CompositeTokenFilter;

import org.apache.lucene.analysis.gosen.GosenTokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.util.AttributeFactory;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.analysis.util.ResourceLoaderAware;

/**
 * Factory for {@link GosenTokenizer}.
 * <pre class="prettyprint" >
 * &lt;fieldType name="text_ja" class="solr.TextField"&gt;
 *   &lt;analyzer&gt;
 *     &lt;tokenizer class="solr.GosenTokenizerFactory"
 *       compositePOS="compositePOS.txt"
 *       dictionaryDir="/opt/dictionary"
 *       userDictionaryDir="/opt/user_dictionary"
 *       tokenizeUnknownKatakana="false / true" /&gt;
 *   &lt;/analyzer&gt;
 * &lt;/fieldType&gt;</pre>
 */
public class GosenTokenizerFactory extends TokenizerFactory implements ResourceLoaderAware {
  
  private CompositeTokenFilter compositeTokenFilter;
  private String dictionaryDir;
  private String userDictionaryDir;

  private final String compositePosFile;
  private final String dirVal;
  private final String usrDirVal;
  private final boolean tokenizeUnknownKatakana;

  /**
   * Create a new GosenTokenizerFactory
   * @param args
   */
  public GosenTokenizerFactory(Map<String,String> args) {
    super(args);

    this.compositePosFile = get(args, "compositePOS");
    this.dirVal = get(args, "dictionaryDir");
    this.usrDirVal = get(args, "userDictionaryDir");
    this.tokenizeUnknownKatakana = getBoolean(args, "tokenizeUnknownKatakana", false);

    if (!args.isEmpty()){
      throw new IllegalArgumentException("Unknown parameters: " + args);
    }
  }

  @Override
  public void inform(ResourceLoader loader) throws IOException {
    if(compositePosFile != null){
      compositeTokenFilter = new CompositeTokenFilter();
      InputStreamReader isr = null;
      BufferedReader reader = null;
      try{
        isr = new InputStreamReader(loader.openResource(compositePosFile), "UTF-8");
        reader = new BufferedReader(isr);
        compositeTokenFilter.readRules(reader);
      }
      catch(IOException e){
        throw new RuntimeException(e);
      }
      finally {
        try {
          IOUtils.close(reader, isr);
        } catch(IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
    if (dirVal != null) {
      // absolute path or relative path
      dictionaryDir = dirVal;
    }
    if (usrDirVal != null) {
      // Set path to the user dictionary.
      // Currently, only one user dictionary can be specified.
      userDictionaryDir = usrDirVal;
    }
  }

  @Override
  public GosenTokenizer create(AttributeFactory factory) {
    return new GosenTokenizer(factory,
        compositeTokenFilter,
        dictionaryDir,
        userDictionaryDir,
        tokenizeUnknownKatakana);
  }
}
