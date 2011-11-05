package com.google.android.apps.analytics;

import com.google.android.apps.analytics.AnalyticsParameterEncoder;

class CustomVariable {

   public static final String INDEX_ERROR_MSG = "Index must be between 1 and 5 inclusive.";
   public static final int MAX_CUSTOM_VARIABLES = 5;
   public static final int MAX_CUSTOM_VARIABLE_LENGTH = 64;
   public static final int PAGE_SCOPE = 3;
   public static final int SESSION_SCOPE = 2;
   public static final int VISITOR_SCOPE = 1;
   private final int index;
   private final String name;
   private final int scope;
   private final String value;


   public CustomVariable(int var1, String var2, String var3) {
      this(var1, var2, var3, 3);
   }

   public CustomVariable(int var1, String var2, String var3, int var4) {
      if(var4 != 1 && var4 != 2 && var4 != 3) {
         String var5 = "Invalid Scope:" + var4;
         throw new IllegalArgumentException(var5);
      } else if(var1 >= 1 && var1 <= 5) {
         if(var2 != null && var2.length() != 0) {
            if(var3 != null && var3.length() != 0) {
               int var6 = AnalyticsParameterEncoder.encode(var2 + var3).length();
               if(var6 > 64) {
                  String var7 = "Encoded form of name and value must not exceed 64 characters combined.  Character length: " + var6;
                  throw new IllegalArgumentException(var7);
               } else {
                  this.index = var1;
                  this.scope = var4;
                  this.name = var2;
                  this.value = var3;
               }
            } else {
               throw new IllegalArgumentException("Invalid argument for value:  Cannot be empty");
            }
         } else {
            throw new IllegalArgumentException("Invalid argument for name:  Cannot be empty");
         }
      } else {
         throw new IllegalArgumentException("Index must be between 1 and 5 inclusive.");
      }
   }

   public int getIndex() {
      return this.index;
   }

   public String getName() {
      return this.name;
   }

   public int getScope() {
      return this.scope;
   }

   public String getValue() {
      return this.value;
   }
}
