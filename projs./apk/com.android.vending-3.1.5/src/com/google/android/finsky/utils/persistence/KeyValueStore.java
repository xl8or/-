package com.google.android.finsky.utils.persistence;

import java.util.Map;

public interface KeyValueStore {

   void delete(String var1);

   Map<String, Map<String, String>> fetchAll();

   Map<String, String> get(String var1);

   void put(String var1, Map<String, String> var2);
}
