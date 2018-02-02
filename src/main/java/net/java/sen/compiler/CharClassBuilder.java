package net.java.sen.compiler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CharClassBuilder {
  private HashMap charClassTable = new LinkedHashMap();

  public void readCharClassDefinitionFile(InputStream in, String encoding) {
    BufferedReader breader = new BufferedReader(new InputStreamReader(in));

    try {
      String line;
      while ((line = breader.readLine()) != null) {
        line = line.trim();
        if (line.length() == 0 || line.charAt(0) == '#') {
          continue;
        }

        //
      }
    } catch (Exception e) {
      //
    }
  }
}
