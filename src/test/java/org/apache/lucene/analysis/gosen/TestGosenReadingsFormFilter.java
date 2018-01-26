package org.apache.lucene.analysis.gosen;


import net.java.sen.SenTestUtil;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.solr.analysis.GosenReadingsFormFilterFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class TestGosenReadingsFormFilter extends BaseTokenStreamTestCase {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  GosenReadingsFormFilterFactory gosenReadingsFilter;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    Map<String, String> args;

    args = new HashMap<>();
    gosenReadingsFilter = new GosenReadingsFormFilterFactory(args);
  }

  public TokenStream getTokenStream(GosenReadingsFormFilterFactory factory, String in) {
    Tokenizer tokenizer = new GosenTokenizer(newAttributeFactory(), null, SenTestUtil.IPADIC_DIR, false);
    tokenizer.setReader(new StringReader(in));
    return factory.create(tokenizer);
  }

  /**
   * Test that bogus arguments result in exception
   */
  @Test
  public void testBogusArguments() throws IOException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Unknown parameters");

    new GosenReadingsFormFilterFactory(new HashMap<String, String>() {{
      put("bogusArg", "bogusValue");
    }});
  }

  /**
   * Readings type
   */
  @Test
  public void testReadings() throws IOException {
    TokenStream ts;
    ts = getTokenStream(gosenReadingsFilter, "大根");
    assertTrue(ts instanceof GosenReadingsFormFilter);
    assertTokenStreamContents(ts,
      new String[]{"オオネ"},
      new int[]{0},
      new int[]{2},
      2);

    ts = getTokenStream(gosenReadingsFilter, "にんじん");
    assertTrue(ts instanceof GosenReadingsFormFilter);
    assertTokenStreamContents(ts,
      new String[]{"ニンジン"},
      new int[]{0},
      new int[]{4},
      4);

    ts = getTokenStream(gosenReadingsFilter, "薬局");
    assertTrue(ts instanceof GosenReadingsFormFilter);
    assertTokenStreamContents(ts,
      new String[]{"ヤッキョク"},
      new int[]{0},
      new int[]{2},
      2);
  }
}


