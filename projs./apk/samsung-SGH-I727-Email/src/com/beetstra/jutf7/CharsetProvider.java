package com.beetstra.jutf7;

import com.beetstra.jutf7.ModifiedUTF7Charset;
import com.beetstra.jutf7.UTF7Charset;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CharsetProvider extends java.nio.charset.spi.CharsetProvider {

   private static final String[] UTF7_ALIASES;
   private static final String[] UTF7_M_ALIASES;
   private static final String UTF7_M_NAME = "X-MODIFIED-UTF-7";
   private static final String UTF7_NAME = "UTF-7";
   private static final String[] UTF7_O_ALIASES;
   private static final String UTF7_O_NAME = "X-UTF-7-OPTIONAL";
   private List charsets;
   private Charset imap4charset;
   private Charset utf7charset;
   private Charset utf7oCharset;


   static {
      String[] var0 = new String[]{"UNICODE-1-1-UTF-7", "CSUNICODE11UTF7", "X-RFC2152", "X-RFC-2152"};
      UTF7_ALIASES = var0;
      String[] var1 = new String[]{"X-RFC2152-OPTIONAL", "X-RFC-2152-OPTIONAL"};
      UTF7_O_ALIASES = var1;
      String[] var2 = new String[]{"X-IMAP-MODIFIED-UTF-7", "X-IMAP4-MODIFIED-UTF7", "X-IMAP4-MODIFIED-UTF-7", "X-RFC3501", "X-RFC-3501"};
      UTF7_M_ALIASES = var2;
   }

   public CharsetProvider() {
      String[] var1 = UTF7_ALIASES;
      UTF7Charset var2 = new UTF7Charset("UTF-7", var1, (boolean)0);
      this.utf7charset = var2;
      String[] var3 = UTF7_O_ALIASES;
      UTF7Charset var4 = new UTF7Charset("X-UTF-7-OPTIONAL", var3, (boolean)1);
      this.utf7oCharset = var4;
      String[] var5 = UTF7_M_ALIASES;
      ModifiedUTF7Charset var6 = new ModifiedUTF7Charset("X-MODIFIED-UTF-7", var5);
      this.imap4charset = var6;
      Object[] var7 = new Object[3];
      Charset var8 = this.utf7charset;
      var7[0] = var8;
      Charset var9 = this.imap4charset;
      var7[1] = var9;
      Charset var10 = this.utf7oCharset;
      var7[2] = var10;
      List var11 = Arrays.asList(var7);
      this.charsets = var11;
   }

   public Charset charsetForName(String var1) {
      String var2 = var1.toUpperCase();
      Iterator var3 = this.charsets.iterator();

      while(true) {
         Charset var4;
         Charset var5;
         if(var3.hasNext()) {
            var4 = (Charset)var3.next();
            if(!var4.name().equals(var2)) {
               continue;
            }

            var5 = var4;
            return var5;
         }

         var3 = this.charsets.iterator();

         while(true) {
            if(var3.hasNext()) {
               var4 = (Charset)var3.next();
               if(!var4.aliases().contains(var2)) {
                  continue;
               }

               var5 = var4;
               return var5;
            }

            var5 = null;
            return var5;
         }
      }
   }

   public Iterator charsets() {
      return this.charsets.iterator();
   }
}
