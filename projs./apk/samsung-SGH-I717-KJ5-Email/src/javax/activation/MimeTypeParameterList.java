package javax.activation;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

public class MimeTypeParameterList {

   private static final String TSPECIALS = "()<>@,;:/[]?=\\\"";
   private List parameterNames;
   private Map parameterValues;


   public MimeTypeParameterList() {
      ArrayList var1 = new ArrayList();
      this.parameterNames = var1;
      HashMap var2 = new HashMap();
      this.parameterValues = var2;
   }

   public MimeTypeParameterList(String var1) throws MimeTypeParseException {
      ArrayList var2 = new ArrayList();
      this.parameterNames = var2;
      HashMap var3 = new HashMap();
      this.parameterValues = var3;
      this.parse(var1);
   }

   private static String quote(String var0) {
      byte var1 = 0;
      int var2 = var0.length();
      int var3 = var1;

      StringBuffer var4;
      while(true) {
         if(var3 >= var2) {
            var4 = null;
            break;
         }

         if(!MimeType.isValidChar(var0.charAt(var3))) {
            var4 = null;
            break;
         }

         ++var3;
      }

      String var12;
      if(var4 != null) {
         var4 = new StringBuffer();
         StringBuffer var5 = var4.append('\"');

         int var9;
         for(byte var6 = 0; var6 < var2; var9 = var6 + 1) {
            char var11 = var0.charAt(var6);
            if(var11 == 92 || var11 == 34) {
               StringBuffer var7 = var4.append('\\');
            }

            var4.append(var11);
         }

         StringBuffer var10 = var4.append('\"');
         var12 = var4.toString();
      } else {
         var12 = var0;
      }

      return var12;
   }

   private static String unquote(String var0) {
      int var1 = var0.length();
      StringBuffer var2 = new StringBuffer();
      int var3 = 1;

      while(true) {
         int var4 = var1 - 1;
         if(var3 >= var4) {
            return var2.toString();
         }

         char var5 = var0.charAt(var3);
         if(var5 == 92) {
            ++var3;
            int var6 = var1 - 1;
            if(var3 < var6) {
               var5 = var0.charAt(var3);
               if(var5 != 92 && var5 != 34) {
                  StringBuffer var7 = var2.append('\\');
               }
            }
         }

         var2.append(var5);
         ++var3;
      }
   }

   public String get(String var1) {
      synchronized(this){}

      String var5;
      try {
         String var2 = var1.trim();
         Map var3 = this.parameterValues;
         String var4 = var2.toLowerCase();
         var5 = (String)var3.get(var4);
      } finally {
         ;
      }

      return var5;
   }

   public Enumeration getNames() {
      synchronized(this){}

      MimeTypeParameterList.IteratorEnumeration var2;
      try {
         Iterator var1 = this.parameterNames.iterator();
         var2 = new MimeTypeParameterList.IteratorEnumeration(var1);
      } finally {
         ;
      }

      return var2;
   }

   public boolean isEmpty() {
      synchronized(this){}
      boolean var5 = false;

      boolean var1;
      try {
         var5 = true;
         var1 = this.parameterNames.isEmpty();
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   protected void parse(String var1) throws MimeTypeParseException {
      if(var1 != null) {
         char[] var2 = var1.toCharArray();
         int var3 = var2.length;
         StringBuffer var4 = new StringBuffer();
         ArrayList var5 = new ArrayList();
         int var6 = 0;

         for(boolean var7 = false; var6 < var3; ++var6) {
            char var8 = var2[var6];
            if(var8 == 59 && !var7) {
               String var26 = var4.toString().trim();
               if(var26.length() > 0) {
                  var5.add(var26);
               }

               var4.setLength(0);
            } else {
               if(var8 == 34) {
                  if(!var7) {
                     boolean var10 = true;
                  } else {
                     boolean var12 = false;
                  }
               }

               var4.append(var8);
            }
         }

         String var13 = var4.toString().trim();
         if(var13.length() > 0) {
            var5.add(var13);
         }

         Iterator var15 = var5.iterator();

         while(var15.hasNext()) {
            String var16 = (String)var15.next();
            int var17 = var16.indexOf(61);
            if(var17 == -1) {
               throw new MimeTypeParseException("Couldn\'t find the \'=\' that separates a parameter name from its value.");
            }

            String var18;
            label49: {
               var18 = var16.substring(0, var17).trim();
               MimeType.checkValidity(var18, "Parameter name is invalid");
               int var19 = var17 + 1;
               var13 = var16.substring(var19).trim();
               int var20 = var13.length();
               if(var20 > 1 && var13.charAt(0) == 34) {
                  int var21 = var20 - 1;
                  if(var13.charAt(var21) == 34) {
                     var13 = unquote(var13);
                     break label49;
                  }
               }

               MimeType.checkValidity(var18, "Parameter value is invalid");
            }

            this.parameterNames.add(var18);
            Map var23 = this.parameterValues;
            String var24 = var18.toLowerCase();
            var23.put(var24, var13);
         }

      }
   }

   public void remove(String var1) {
      synchronized(this){}

      try {
         String var2 = var1.trim();
         Iterator var3 = this.parameterNames.iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            if(var2.equalsIgnoreCase(var4)) {
               var3.remove();
            }
         }

         Map var6 = this.parameterValues;
         String var7 = var2.toLowerCase();
         var6.remove(var7);
      } finally {
         ;
      }
   }

   public void set(String param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public int size() {
      synchronized(this){}
      boolean var5 = false;

      int var1;
      try {
         var5 = true;
         var1 = this.parameterNames.size();
         var5 = false;
      } finally {
         if(var5) {
            ;
         }
      }

      return var1;
   }

   public String toString() {
      synchronized(this){}
      boolean var17 = false;

      try {
         var17 = true;
         StringBuffer var1 = new StringBuffer();
         Iterator var2 = this.parameterNames.iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            Map var4 = this.parameterValues;
            String var5 = var3.toLowerCase();
            String var6 = (String)var4.get(var5);
            StringBuffer var7 = var1.append(';');
            StringBuffer var8 = var1.append(' ');
            var1.append(var3);
            StringBuffer var10 = var1.append('=');
            String var11 = quote(var6);
            var1.append(var11);
         }

         String var14 = var1.toString();
         var17 = false;
         return var14;
      } finally {
         if(var17) {
            ;
         }
      }
   }

   static class IteratorEnumeration implements Enumeration {

      final Iterator iterator;


      IteratorEnumeration(Iterator var1) {
         this.iterator = var1;
      }

      public boolean hasMoreElements() {
         return this.iterator.hasNext();
      }

      public Object nextElement() {
         return this.iterator.next();
      }
   }
}
