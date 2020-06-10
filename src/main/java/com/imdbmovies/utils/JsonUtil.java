package com.imdbmovies.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author prerngup
 *
 */
public class JsonUtil {

  public JsonUtil() {

  }


  /**
   * Get JSON from file in a JSON tree format
   * @param fileName file which needs to be read
   * @return
   * @throws IOException
   */
  public String getStringFromFile(final String fileName) throws IOException {
    final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    final InputStream in = classLoader.getResourceAsStream(fileName);
    final ByteArrayOutputStream result = new ByteArrayOutputStream();
    final byte[] buffer = new byte[1024];
    int length;
    while ((length = in.read(buffer)) != -1) {
      result.write(buffer, 0, length);
    }
    return result.toString(StandardCharsets.UTF_8.name());
  }
}
