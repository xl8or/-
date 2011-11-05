package com.google.android.finsky.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Maps {

   public Maps() {}

   public static <K extends Object, V extends Object> HashMap<K, V> newHashMap() {
      return new HashMap();
   }

   public static <K extends Object, V extends Object> LinkedHashMap<K, V> newLinkedHashMap() {
      return new LinkedHashMap();
   }

   public static <K extends Object, V extends Object> LinkedHashMap<K, V> newLinkedHashMap(boolean var0) {
      return new LinkedHashMap(16, 0.75F, var0);
   }
}
