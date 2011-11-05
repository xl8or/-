package gnu.inet.http;

import gnu.inet.http.HTTPDateFormat;
import gnu.inet.util.LineInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Headers implements Map {

   static final DateFormat dateFormat = new HTTPDateFormat();
   private LinkedHashMap headers;


   public Headers() {
      LinkedHashMap var1 = new LinkedHashMap();
      this.headers = var1;
   }

   private void addValue(String var1, String var2) {
      Headers.Header var3 = new Headers.Header(var1);
      String var4 = (String)this.headers.get(var3);
      if(var4 == null) {
         this.headers.put(var3, var2);
      } else {
         LinkedHashMap var6 = this.headers;
         String var7 = var4 + ", " + var2;
         var6.put(var3, var7);
      }
   }

   public void clear() {
      this.headers.clear();
   }

   public boolean containsKey(Object var1) {
      LinkedHashMap var2 = this.headers;
      String var3 = (String)var1;
      Headers.Header var4 = new Headers.Header(var3);
      return var2.containsKey(var4);
   }

   public boolean containsValue(Object var1) {
      return this.headers.containsValue(var1);
   }

   public Set entrySet() {
      Set var1 = this.headers.entrySet();
      LinkedHashSet var2 = new LinkedHashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Headers.HeaderEntry var5 = new Headers.HeaderEntry(var4);
         var2.add(var5);
      }

      return var2;
   }

   public boolean equals(Object var1) {
      return this.headers.equals(var1);
   }

   public Object get(Object var1) {
      LinkedHashMap var2 = this.headers;
      String var3 = (String)var1;
      Headers.Header var4 = new Headers.Header(var3);
      return var2.get(var4);
   }

   public Date getDateValue(String var1) {
      String var2 = this.getValue(var1);
      Date var6;
      if(var2 == null) {
         var6 = null;
      } else {
         Date var3;
         try {
            var3 = dateFormat.parse(var2);
         } catch (ParseException var5) {
            var6 = null;
            return var6;
         }

         var6 = var3;
      }

      return var6;
   }

   public int getIntValue(String var1) {
      String var2 = this.getValue(var1);
      int var3;
      if(var2 == null) {
         var3 = -1;
      } else {
         int var4;
         try {
            var4 = Integer.parseInt(var2);
         } catch (NumberFormatException var6) {
            var3 = -1;
            return var3;
         }

         var3 = var4;
      }

      return var3;
   }

   public String getValue(String var1) {
      LinkedHashMap var2 = this.headers;
      Headers.Header var3 = new Headers.Header(var1);
      return (String)var2.get(var3);
   }

   public int hashCode() {
      return this.headers.hashCode();
   }

   public boolean isEmpty() {
      return this.headers.isEmpty();
   }

   public Set keySet() {
      Set var1 = this.headers.keySet();
      LinkedHashSet var2 = new LinkedHashSet();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         String var4 = ((Headers.Header)var3.next()).name;
         var2.add(var4);
      }

      return var2;
   }

   public void parse(InputStream var1) throws IOException {
      LineInputStream var2;
      if(var1 instanceof LineInputStream) {
         var2 = (LineInputStream)var1;
      } else {
         var2 = new LineInputStream(var1);
      }

      String var3 = null;
      StringBuffer var4 = new StringBuffer();

      while(true) {
         String var5 = var2.readLine();
         if(var5 == null) {
            if(var3 == null) {
               return;
            } else {
               String var6 = var4.toString();
               this.addValue(var3, var6);
               return;
            }
         }

         int var7 = var5.length();
         if(var7 < 2) {
            if(var3 == null) {
               return;
            }

            String var8 = var4.toString();
            this.addValue(var3, var8);
            return;
         }

         char var9 = var5.charAt(0);
         if(var9 != 32 && var9 != 9) {
            if(var3 != null) {
               String var13 = var4.toString();
               this.addValue(var3, var13);
            }

            int var14 = var5.indexOf(58);
            String var18 = var5.substring(0, var14);
            var4.setLength(0);

            do {
               ++var14;
            } while(var14 < var7 && var5.charAt(var14) == 32);

            int var15 = var7 - 1;
            String var16 = var5.substring(var14, var15);
            var4.append(var16);
            var3 = var18;
         } else {
            int var10 = var7 - 1;
            String var11 = var5.substring(0, var10);
            var4.append(var11);
         }
      }
   }

   public Object put(Object var1, Object var2) {
      LinkedHashMap var3 = this.headers;
      String var4 = (String)var1;
      Headers.Header var5 = new Headers.Header(var4);
      return var3.put(var5, var2);
   }

   public void putAll(Map var1) {
      Iterator var2 = var1.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = (String)var1.get(var3);
         LinkedHashMap var5 = this.headers;
         Headers.Header var6 = new Headers.Header(var3);
         var5.put(var6, var4);
      }

   }

   public Object remove(Object var1) {
      LinkedHashMap var2 = this.headers;
      String var3 = (String)var1;
      Headers.Header var4 = new Headers.Header(var3);
      return var2.remove(var4);
   }

   public int size() {
      return this.headers.size();
   }

   public Collection values() {
      return this.headers.values();
   }

   static class Header {

      final String name;


      Header(String var1) {
         if(var1 != null && var1.length() != 0) {
            this.name = var1;
         } else {
            throw new IllegalArgumentException(var1);
         }
      }

      public boolean equals(Object var1) {
         boolean var4;
         if(var1 instanceof Headers.Header) {
            String var2 = ((Headers.Header)var1).name;
            String var3 = this.name;
            var4 = var2.equalsIgnoreCase(var3);
         } else {
            var4 = false;
         }

         return var4;
      }

      public int hashCode() {
         return this.name.toLowerCase().hashCode();
      }

      public String toString() {
         return this.name;
      }
   }

   static class HeaderEntry implements Entry {

      final Entry entry;


      HeaderEntry(Entry var1) {
         this.entry = var1;
      }

      public boolean equals(Object var1) {
         return this.entry.equals(var1);
      }

      public Object getKey() {
         return ((Headers.Header)this.entry.getKey()).name;
      }

      public Object getValue() {
         return this.entry.getValue();
      }

      public int hashCode() {
         return this.entry.hashCode();
      }

      public Object setValue(Object var1) {
         return this.entry.setValue(var1);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.getKey().toString();
         StringBuilder var3 = var1.append(var2).append("=");
         Object var4 = this.getValue();
         return var3.append(var4).toString();
      }
   }
}
