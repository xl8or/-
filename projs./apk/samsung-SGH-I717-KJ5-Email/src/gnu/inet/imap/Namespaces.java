package gnu.inet.imap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Namespaces {

   List other;
   List personal;
   List shared;


   Namespaces(String var1) {
      ArrayList var2 = new ArrayList();
      int var3 = var1.length();
      parse(var1, 0, var3, var2);
      int var5 = var2.size();
      if(var5 > 0) {
         Object var6 = var2.get(0);
         List var7 = this.parseNamespaceList(var6);
         this.personal = var7;
         if(var5 > 1) {
            Object var8 = var2.get(1);
            List var9 = this.parseNamespaceList(var8);
            this.other = var9;
            if(var5 > 2) {
               Object var10 = var2.get(2);
               List var11 = this.parseNamespaceList(var10);
               this.shared = var11;
            }
         }
      }
   }

   private void appendNamespaceList(StringBuffer var1, List var2) {
      if(var2 == null) {
         StringBuffer var3 = var1.append("NIL");
      } else {
         int var4 = var2.size();
         StringBuffer var5 = var1.append('(');

         for(int var6 = 0; var6 < var4; ++var6) {
            if(var6 > 0) {
               StringBuffer var7 = var1.append(' ');
            }

            Object var8 = var2.get(var6);
            var1.append(var8);
         }

         StringBuffer var10 = var1.append(')');
      }
   }

   static int parse(String var0, int var1, int var2, List var3) {
      StringBuffer var4 = new StringBuffer();
      int var5 = var1;

      int var16;
      for(boolean var6 = false; var5 < var2; ++var5) {
         char var7 = var0.charAt(var5);
         if(var6) {
            if(var7 == 34) {
               String var8 = var4.toString();
               var4.setLength(0);
               var3.add(var8);
               var6 = false;
            } else {
               var4.append(var7);
            }
         } else {
            switch(var7) {
            case 32:
               String var11 = var4.toString();
               if("NIL".equals(var11)) {
                  boolean var12 = var3.add((Object)null);
               }

               var4.setLength(0);
               break;
            case 34:
               var6 = true;
               var4.setLength(0);
               break;
            case 40:
               ArrayList var13 = new ArrayList();
               int var14 = var5 + 1;
               var5 = parse(var0, var14, var2, var13);
               var3.add(var13);
               break;
            case 41:
               var16 = var5;
               return var16;
            }
         }
      }

      var16 = var2;
      return var16;
   }

   private Namespaces.Namespace parseNamespace(List var1) {
      byte var2 = 2;
      int var3 = var1.size();
      Namespaces.Namespace var4 = new Namespaces.Namespace();
      String var5 = (String)var1.get(0);
      var4.prefix = var5;
      char var6 = ((String)var1.get(1)).charAt(0);
      var4.delimiter = var6;
      if(var3 > var2) {
         TreeMap var7 = new TreeMap();
         var4.extensions = var7;

         for(int var8 = 2; var8 < var3; var8 += 2) {
            String var9 = (String)var1.get(var8);
            int var10 = var3 - 1;
            Object var13;
            if(var8 == var10) {
               var13 = null;
            } else {
               int var12 = var8 + 1;
               var13 = var1.get(var12);
            }

            var4.extensions.put(var9, var13);
         }
      }

      return var4;
   }

   private List parseNamespaceList(Object var1) {
      ArrayList var2;
      if(var1 == null) {
         var2 = null;
      } else {
         List var9 = (List)var1;
         int var3 = var9.size();
         ArrayList var4 = new ArrayList(var3);

         for(int var5 = 0; var5 < var3; ++var5) {
            List var6 = (List)var9.get(var5);
            Namespaces.Namespace var7 = this.parseNamespace(var6);
            var4.add(var7);
         }

         var2 = var4;
      }

      return var2;
   }

   private Namespaces.Namespace[] toArray(List var1) {
      Namespaces.Namespace[] var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new Namespaces.Namespace[var1.size()];
         var1.toArray(var2);
      }

      return var2;
   }

   public Namespaces.Namespace[] getOther() {
      List var1 = this.other;
      return this.toArray(var1);
   }

   public Namespaces.Namespace[] getPersonal() {
      List var1 = this.personal;
      return this.toArray(var1);
   }

   public Namespaces.Namespace[] getShared() {
      List var1 = this.shared;
      return this.toArray(var1);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      List var2 = this.personal;
      this.appendNamespaceList(var1, var2);
      StringBuffer var3 = var1.append(' ');
      List var4 = this.other;
      this.appendNamespaceList(var1, var4);
      StringBuffer var5 = var1.append(' ');
      List var6 = this.shared;
      this.appendNamespaceList(var1, var6);
      return var1.toString();
   }

   public static class Namespace {

      char delimiter;
      Map extensions;
      String prefix;


      public Namespace() {}

      private String format(Object var1) {
         String var2;
         if(var1 == null) {
            var2 = "NIL";
         } else if(var1 instanceof String) {
            var2 = quote((String)var1);
         } else {
            List var12 = (List)var1;
            int var3 = var12.size();
            StringBuffer var4 = new StringBuffer();
            StringBuffer var5 = var4.append('(');

            for(int var6 = 0; var6 < var3; ++var6) {
               if(var6 > 0) {
                  StringBuffer var7 = var4.append(' ');
               }

               Object var8 = var12.get(var6);
               String var9 = this.format(var8);
               var4.append(var9);
            }

            StringBuffer var11 = var4.append(')');
            var2 = var4.toString();
         }

         return var2;
      }

      static String quote(char var0) {
         char[] var1 = new char[]{'\"', var0, '\"'};
         return new String(var1);
      }

      static String quote(String var0) {
         return '\"' + var0 + '\"';
      }

      public char getDelimiter() {
         return this.delimiter;
      }

      public Map getExtensions() {
         return this.extensions;
      }

      public String getPrefix() {
         return this.prefix;
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer();
         StringBuffer var2 = var1.append('(');
         String var3 = quote(this.prefix);
         var1.append(var3);
         StringBuffer var5 = var1.append(' ');
         String var6 = quote(this.delimiter);
         var1.append(var6);
         if(this.extensions != null) {
            StringBuffer var8 = var1.append(' ');
            Iterator var9 = this.extensions.entrySet().iterator();

            while(var9.hasNext()) {
               Entry var10 = (Entry)var9.next();
               String var11 = quote((String)var10.getKey());
               var1.append(var11);
               StringBuffer var13 = var1.append(' ');
               Object var14 = var10.getValue();
               String var15 = this.format(var14);
               var1.append(var15);
            }
         }

         StringBuffer var17 = var1.append(')');
         return var1.toString();
      }
   }
}
