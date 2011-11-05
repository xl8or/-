package com.kenai.jbosh;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class QName implements Serializable {

   private static final String emptyString = "".intern();
   private String localPart;
   private String namespaceURI;
   private String prefix;


   public QName(String var1) {
      String var2 = emptyString;
      String var3 = emptyString;
      this(var2, var1, var3);
   }

   public QName(String var1, String var2) {
      String var3 = emptyString;
      this(var1, var2, var3);
   }

   public QName(String var1, String var2, String var3) {
      String var4;
      if(var1 == null) {
         var4 = emptyString;
      } else {
         var4 = var1.intern();
      }

      this.namespaceURI = var4;
      if(var2 == null) {
         throw new IllegalArgumentException("invalid QName local part");
      } else {
         String var5 = var2.intern();
         this.localPart = var5;
         if(var3 == null) {
            throw new IllegalArgumentException("invalid QName prefix");
         } else {
            String var6 = var3.intern();
            this.prefix = var6;
         }
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      String var2 = this.namespaceURI.intern();
      this.namespaceURI = var2;
      String var3 = this.localPart.intern();
      this.localPart = var3;
      String var4 = this.prefix.intern();
      this.prefix = var4;
   }

   public static QName valueOf(String var0) {
      if(var0 != null && !var0.equals("")) {
         QName var6;
         if(var0.charAt(0) == 123) {
            int var1 = var0.indexOf(125);
            if(var1 == -1) {
               throw new IllegalArgumentException("invalid QName literal");
            }

            int var2 = var0.length() - 1;
            if(var1 == var2) {
               throw new IllegalArgumentException("invalid QName literal");
            }

            String var3 = var0.substring(1, var1);
            int var4 = var1 + 1;
            String var5 = var0.substring(var4);
            var6 = new QName(var3, var5);
         } else {
            var6 = new QName(var0);
         }

         return var6;
      } else {
         throw new IllegalArgumentException("invalid QName literal");
      }
   }

   public final boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof QName)) {
         var2 = false;
      } else {
         String var3 = this.namespaceURI;
         String var4 = ((QName)var1).namespaceURI;
         if(var3 == var4) {
            String var5 = this.localPart;
            String var6 = ((QName)var1).localPart;
            if(var5 == var6) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getLocalPart() {
      return this.localPart;
   }

   public String getNamespaceURI() {
      return this.namespaceURI;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public final int hashCode() {
      int var1 = this.namespaceURI.hashCode();
      int var2 = this.localPart.hashCode();
      return var1 ^ var2;
   }

   public String toString() {
      String var1 = this.namespaceURI;
      String var2 = emptyString;
      String var3;
      if(var1 == var2) {
         var3 = this.localPart;
      } else {
         StringBuilder var4 = (new StringBuilder()).append('{');
         String var5 = this.namespaceURI;
         StringBuilder var6 = var4.append(var5).append('}');
         String var7 = this.localPart;
         var3 = var6.append(var7).toString();
      }

      return var3;
   }
}
