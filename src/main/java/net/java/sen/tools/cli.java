package net.java.sen.tools;

import net.java.sen.SenFactory;
import net.java.sen.StringTagger;
import net.java.sen.dictionary.Morpheme;
import net.java.sen.dictionary.Token;

import java.util.ArrayList;
import java.util.List;

public class cli {
  public static final String IPADIC_DIR = "./dictionary/ipadic/compiled-dictionaries/net/java/sen";

  public static void main(String args[]) {
    if (args.length < 1) {
      System.out.println("You must specify the text for Tokenization!");
      return;
    }

    StringTagger stringTagger = SenFactory.getStringTagger(IPADIC_DIR, false);
    stringTagger.removeFilters();

    List<Token> tokens;
    try {
      tokens = stringTagger.analyze(args[0], new ArrayList<Token>(){});
    } catch (Exception e) {
      return;
    }
    for (Token token : tokens) {
      System.out.println(getAllFeatures(token));
    }
    System.out.println("EOS");
  }

  private static String getAllFeatures(Token token) {
    //表層形\t品詞,品詞細分類1,品詞細分類2,品詞細分類3,活用型,活用形,原形,読み,発音

    StringBuilder sbMecabFormatString = new StringBuilder();

    Morpheme morpheme = token.getMorpheme();

    sbMecabFormatString.append(token.getSurface());
    sbMecabFormatString.append('\t');
    sbMecabFormatString.append(morpheme.toString());

    return sbMecabFormatString.toString();
  }
}
