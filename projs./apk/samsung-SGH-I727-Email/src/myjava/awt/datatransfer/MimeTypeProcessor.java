package myjava.awt.datatransfer;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

final class MimeTypeProcessor {

   private static MimeTypeProcessor instance;


   private MimeTypeProcessor() {}

   static String assemble(MimeTypeProcessor.MimeType var0) {
      StringBuilder var1 = new StringBuilder();
      String var2 = var0.getFullType();
      var1.append(var2);

      StringBuilder var11;
      for(Enumeration var4 = var0.parameters.keys(); var4.hasMoreElements(); var11 = var1.append('\"')) {
         String var5 = (String)var4.nextElement();
         String var6 = (String)var0.parameters.get(var5);
         StringBuilder var7 = var1.append("; ");
         var1.append(var5);
         StringBuilder var9 = var1.append("=\"");
         var1.append(var6);
      }

      return var1.toString();
   }

   private static int getNextMeaningfulIndex(String var0, int var1) {
      while(true) {
         int var2 = var0.length();
         if(var1 >= var2 || isMeaningfulChar(var0.charAt(var1))) {
            return var1;
         }

         ++var1;
      }
   }

   private static boolean isMeaningfulChar(char var0) {
      boolean var1;
      if(var0 >= 33 && var0 <= 126) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isTSpecialChar(char var0) {
      boolean var1;
      if(var0 != 40 && var0 != 41 && var0 != 91 && var0 != 93 && var0 != 60 && var0 != 62 && var0 != 64 && var0 != 44 && var0 != 59 && var0 != 58 && var0 != 92 && var0 != 34 && var0 != 47 && var0 != 63 && var0 != 61) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   static MimeTypeProcessor.MimeType parse(String var0) {
      if(instance == null) {
         instance = new MimeTypeProcessor();
      }

      MimeTypeProcessor.MimeType var1 = new MimeTypeProcessor.MimeType();
      if(var0 != null) {
         MimeTypeProcessor.StringPosition var2 = new MimeTypeProcessor.StringPosition((MimeTypeProcessor.StringPosition)null);
         retrieveType(var0, var1, var2);
         retrieveParams(var0, var1, var2);
      }

      return var1;
   }

   private static void retrieveParam(String var0, MimeTypeProcessor.MimeType var1, MimeTypeProcessor.StringPosition var2) {
      String var3 = retrieveToken(var0, var2).toLowerCase();
      int var4 = var2.i;
      int var5 = getNextMeaningfulIndex(var0, var4);
      var2.i = var5;
      int var6 = var2.i;
      int var7 = var0.length();
      if(var6 < var7) {
         int var8 = var2.i;
         if(var0.charAt(var8) == 61) {
            int var9 = var2.i + 1;
            var2.i = var9;
            int var10 = var2.i;
            int var11 = getNextMeaningfulIndex(var0, var10);
            var2.i = var11;
            int var12 = var2.i;
            int var13 = var0.length();
            if(var12 >= var13) {
               throw new IllegalArgumentException();
            }

            int var14 = var2.i;
            String var15;
            if(var0.charAt(var14) == 34) {
               var15 = retrieveQuoted(var0, var2);
            } else {
               var15 = retrieveToken(var0, var2);
            }

            var1.parameters.put(var3, var15);
            return;
         }
      }

      throw new IllegalArgumentException();
   }

   private static void retrieveParams(String var0, MimeTypeProcessor.MimeType var1, MimeTypeProcessor.StringPosition var2) {
      Hashtable var3 = new Hashtable();
      var1.parameters = var3;
      Hashtable var4 = new Hashtable();
      var1.systemParameters = var4;

      while(true) {
         int var5 = var2.i;
         int var6 = getNextMeaningfulIndex(var0, var5);
         var2.i = var6;
         int var7 = var2.i;
         int var8 = var0.length();
         if(var7 >= var8) {
            return;
         }

         int var9 = var2.i;
         if(var0.charAt(var9) != 59) {
            throw new IllegalArgumentException();
         }

         int var10 = var2.i + 1;
         var2.i = var10;
         retrieveParam(var0, var1, var2);
      }
   }

   private static String retrieveQuoted(String var0, MimeTypeProcessor.StringPosition var1) {
      StringBuilder var2 = new StringBuilder();
      boolean var3 = true;
      int var4 = var1.i + 1;
      var1.i = var4;

      int var11;
      int var12;
      do {
         int var5 = var1.i;
         if(var0.charAt(var5) == 34 && var3) {
            int var6 = var1.i + 1;
            var1.i = var6;
            return var2.toString();
         }

         int var7 = var1.i;
         int var8 = var7 + 1;
         var1.i = var8;
         char var9 = var0.charAt(var7);
         if(!var3) {
            var3 = true;
         } else if(var9 == 92) {
            var3 = false;
         }

         if(var3) {
            var2.append(var9);
         }

         var11 = var1.i;
         var12 = var0.length();
      } while(var11 != var12);

      throw new IllegalArgumentException();
   }

   private static String retrieveToken(String var0, MimeTypeProcessor.StringPosition var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = var1.i;
      int var4 = getNextMeaningfulIndex(var0, var3);
      var1.i = var4;
      int var5 = var1.i;
      int var6 = var0.length();
      if(var5 < var6) {
         int var7 = var1.i;
         if(!isTSpecialChar(var0.charAt(var7))) {
            int var15;
            do {
               int var8 = var1.i;
               int var9 = var8 + 1;
               var1.i = var9;
               char var10 = var0.charAt(var8);
               var2.append(var10);
               int var12 = var1.i;
               int var13 = var0.length();
               if(var12 >= var13) {
                  break;
               }

               int var14 = var1.i;
               if(!isMeaningfulChar(var0.charAt(var14))) {
                  break;
               }

               var15 = var1.i;
            } while(!isTSpecialChar(var0.charAt(var15)));

            return var2.toString();
         }
      }

      throw new IllegalArgumentException();
   }

   private static void retrieveType(String var0, MimeTypeProcessor.MimeType var1, MimeTypeProcessor.StringPosition var2) {
      String var3 = retrieveToken(var0, var2).toLowerCase();
      var1.primaryType = var3;
      int var4 = var2.i;
      int var5 = getNextMeaningfulIndex(var0, var4);
      var2.i = var5;
      int var6 = var2.i;
      int var7 = var0.length();
      if(var6 < var7) {
         int var8 = var2.i;
         if(var0.charAt(var8) == 47) {
            int var9 = var2.i + 1;
            var2.i = var9;
            String var10 = retrieveToken(var0, var2).toLowerCase();
            var1.subType = var10;
            return;
         }
      }

      throw new IllegalArgumentException();
   }

   private static final class StringPosition {

      int i;


      private StringPosition() {
         this.i = 0;
      }

      // $FF: synthetic method
      StringPosition(MimeTypeProcessor.StringPosition var1) {
         this();
      }
   }

   static final class MimeType implements Cloneable, Serializable {

      private static final long serialVersionUID = -6693571907475992044L;
      private Hashtable<String, String> parameters;
      private String primaryType;
      private String subType;
      private Hashtable<String, Object> systemParameters;


      MimeType() {
         this.primaryType = null;
         this.subType = null;
         this.parameters = null;
         this.systemParameters = null;
      }

      MimeType(String var1, String var2) {
         this.primaryType = var1;
         this.subType = var2;
         Hashtable var3 = new Hashtable();
         this.parameters = var3;
         Hashtable var4 = new Hashtable();
         this.systemParameters = var4;
      }

      void addParameter(String var1, String var2) {
         if(var2 != null) {
            if(var2.charAt(0) == 34) {
               int var3 = var2.length() - 1;
               if(var2.charAt(var3) == 34) {
                  int var4 = var2.length() - 2;
                  var2 = var2.substring(1, var4);
               }
            }

            if(var2.length() != 0) {
               this.parameters.put(var1, var2);
            }
         }
      }

      void addSystemParameter(String var1, Object var2) {
         this.systemParameters.put(var1, var2);
      }

      public Object clone() {
         String var1 = this.primaryType;
         String var2 = this.subType;
         MimeTypeProcessor.MimeType var3 = new MimeTypeProcessor.MimeType(var1, var2);
         Hashtable var4 = (Hashtable)this.parameters.clone();
         var3.parameters = var4;
         Hashtable var5 = (Hashtable)this.systemParameters.clone();
         var3.systemParameters = var5;
         return var3;
      }

      boolean equals(MimeTypeProcessor.MimeType var1) {
         byte var2;
         if(var1 == null) {
            var2 = 0;
         } else {
            String var3 = this.getFullType();
            String var4 = var1.getFullType();
            var2 = var3.equals(var4);
         }

         return (boolean)var2;
      }

      String getFullType() {
         String var1 = String.valueOf(this.primaryType);
         StringBuilder var2 = (new StringBuilder(var1)).append("/");
         String var3 = this.subType;
         return var2.append(var3).toString();
      }

      String getParameter(String var1) {
         return (String)this.parameters.get(var1);
      }

      String getPrimaryType() {
         return this.primaryType;
      }

      String getSubType() {
         return this.subType;
      }

      Object getSystemParameter(String var1) {
         return this.systemParameters.get(var1);
      }

      void removeParameter(String var1) {
         this.parameters.remove(var1);
      }
   }
}
