package core;

import com.jakewharton.fliptables.FlipTable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Some helper Methods which are used across this package
 */
public class Util {

  static long kilo = 1024;
  static long mega = kilo * kilo;
  static long giga = mega * kilo;
  static long tera = giga * kilo;

  /**
   * Generate pretty table inside console
   *
   * @param headers  The Headers
   * @param mainList The Elements to be printed
   */
  public static void printTable(String[] headers, ArrayList<ArrayList<String>> mainList) {
    System.out.println(
        FlipTable.of(
            headers,
            mainList.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new)));
  }

  /**
   * Simple method for formatting sizes human-readable
   *
   * @param size Size as Bytes
   * @return Formatted size
   */
  // Source https://gist.github.com/SatyaSnehith/2441b85c8f945f2cf024fb7e6971d869
  public static String getSizeFromBytes(long size) {
    String s;
    double kb = (double) size / kilo;
    double mb = kb / kilo;
    double gb = mb / kilo;
    double tb = gb / kilo;
    if (size < kilo) {
      s = String.format("%d Bytes", size);
    } else if (size < mega) {
      s = String.format("%.2f", kb) + " KB";
    } else if (size < giga) {
      s = String.format("%.2f", mb) + " MB";
    } else if (size < tera) {
      s = String.format("%.2f", gb) + " GB";
    } else {
      s = String.format("%.2f", tb) + " TB";
    }
    return s;
  }

  /**
   * Read config values from config.properties
   *
   * @return Properties
   */
  public static Properties getConfig() {
    Properties prop = new Properties();
    String propFileName = "config.properties";
    InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(propFileName);
    try {
      prop.load(inputStream);
      return prop;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return prop;
  }
}
