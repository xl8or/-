package com.facebook.katana.version;

import android.util.Patterns;
import java.util.regex.Pattern;

public class SDK8 {

   public SDK8() {}

   public static Pattern getWebUrlPattern() {
      return Patterns.WEB_URL;
   }
}
