package com.google.android.finsky.utils;

import java.util.HashSet;

public class Sets {

   public Sets() {}

   public static <T extends Object> HashSet<T> newHashSet() {
      return new HashSet();
   }
}
