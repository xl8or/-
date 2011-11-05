package com.htc.android.mail.mimemessage;

import android.text.TextUtils;
import android.text.util.Rfc822Token;
import android.text.util.Rfc822Tokenizer;
import com.htc.android.mail.mimemessage.Utility;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.james.mime4j.codec.EncoderUtil;
import org.apache.james.mime4j.decoder.DecoderUtil;

public class Address {

   private static final Address[] EMPTY_ADDRESS_ARRAY = new Address[0];
   private static final char LIST_DELIMITER_EMAIL = '\u0001';
   private static final char LIST_DELIMITER_PERSONAL = '\u0002';
   private static final Pattern REMOVE_OPTIONAL_BRACKET = Pattern.compile("^<?([^>]+)>?$");
   private static final Pattern REMOVE_OPTIONAL_DQUOTE = Pattern.compile("^\"?([^\"]*)\"?$");
   private static final Pattern UNQUOTE = Pattern.compile("\\\\([\\\\\"])");
   String mAddress;
   String mPersonal;


   public Address(String var1) {
      this.setAddress(var1);
   }

   public Address(String var1, String var2) {
      this.setAddress(var1);
      this.setPersonal(var2);
   }

   public static boolean isAllValid(String var0) {
      boolean var5;
      if(var0 != null && var0.length() > 0) {
         Rfc822Token[] var1 = Rfc822Tokenizer.tokenize(var0);
         int var2 = 0;

         for(int var3 = var1.length; var2 < var3; ++var2) {
            String var4 = var1[var2].getAddress();
            if(!TextUtils.isEmpty(var4) && !isValidAddress(var4)) {
               var5 = false;
               return var5;
            }
         }
      }

      var5 = true;
      return var5;
   }

   static boolean isValidAddress(String var0) {
      int var1 = var0.length();
      int var2 = var0.indexOf(64);
      int var3 = var0.lastIndexOf(64);
      int var4 = var3 + 1;
      int var5 = var0.indexOf(46, var4);
      int var6 = var0.lastIndexOf(46);
      boolean var8;
      if(var2 > 0 && var2 == var3 && var3 + 1 < var5 && var5 <= var6) {
         int var7 = var1 - 1;
         if(var6 < var7) {
            var8 = true;
            return var8;
         }
      }

      var8 = false;
      return var8;
   }

   static Address[] legacyUnpack(String var0) {
      Address[] var1;
      if(var0 != null && var0.length() != 0) {
         ArrayList var2 = new ArrayList();
         int var3 = var0.length();

         int var5;
         for(int var4 = 0; var4 < var3; var4 = var5 + 1) {
            var5 = var0.indexOf(44, var4);
            if(var5 == -1) {
               ;
            }

            int var7 = var0.indexOf(59, var4);
            String var8 = null;
            String var9;
            if(var7 != -1 && var7 <= var5) {
               var9 = Utility.fastUrlDecode(var0.substring(var4, var7));
               int var12 = var7 + 1;
               var8 = Utility.fastUrlDecode(var0.substring(var12, var5));
            } else {
               var9 = Utility.fastUrlDecode(var0.substring(var4, var5));
            }

            Address var10 = new Address(var9, var8);
            var2.add(var10);
         }

         Address[] var13 = new Address[0];
         var1 = (Address[])var2.toArray(var13);
      } else {
         var1 = new Address[0];
      }

      return var1;
   }

   public static String pack(Address[] var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         int var2 = var0.length;
         if(var2 == 0) {
            var1 = "";
         } else if(var2 == 1 && var0[0].getPersonal() == null) {
            var1 = var0[0].getAddress();
         } else {
            StringBuffer var3 = new StringBuffer();

            for(int var4 = 0; var4 < var2; ++var4) {
               if(var4 != 0) {
                  StringBuffer var5 = var3.append('\u0001');
               }

               Address var6 = var0[var4];
               String var7 = var6.getAddress();
               var3.append(var7);
               String var9 = var6.getPersonal();
               if(var9 != null) {
                  StringBuffer var10 = var3.append('\u0002');
                  var3.append(var9);
               }
            }

            var1 = var3.toString();
         }
      }

      return var1;
   }

   public static String packedToHeader(String var0) {
      return toHeader(unpack(var0));
   }

   public static Address[] parse(String var0) {
      Address[] var1;
      if(var0 != null && var0.length() != 0) {
         Rfc822Token[] var2 = Rfc822Tokenizer.tokenize(var0);
         ArrayList var3 = new ArrayList();
         int var4 = 0;

         for(int var5 = var2.length; var4 < var5; ++var4) {
            Rfc822Token var6 = var2[var4];
            String var7 = var6.getAddress();
            if(!TextUtils.isEmpty(var7) && isValidAddress(var7)) {
               String var8 = var6.getName();
               if(TextUtils.isEmpty(var8)) {
                  var8 = null;
               }

               Address var9 = new Address(var7, var8);
               var3.add(var9);
            }
         }

         Address[] var11 = new Address[0];
         var1 = (Address[])var3.toArray(var11);
      } else {
         var1 = EMPTY_ADDRESS_ARRAY;
      }

      return var1;
   }

   public static String parseAndPack(String var0) {
      return pack(parse(var0));
   }

   public static String toFriendly(Address[] var0) {
      String var1;
      if(var0 != null && var0.length != 0) {
         if(var0.length == 1) {
            var1 = var0[0].toFriendly();
         } else {
            String var2 = var0[0].toFriendly();
            StringBuffer var3 = new StringBuffer(var2);
            int var4 = 1;

            while(true) {
               int var5 = var0.length;
               if(var4 >= var5) {
                  var1 = var3.toString();
                  break;
               }

               StringBuffer var6 = var3.append(',');
               String var7 = var0[var4].toFriendly();
               var3.append(var7);
               ++var4;
            }
         }
      } else {
         var1 = null;
      }

      return var1;
   }

   public static String toHeader(Address[] var0) {
      String var1;
      if(var0 != null && var0.length != 0) {
         if(var0.length == 1) {
            var1 = var0[0].toHeader();
         } else {
            String var2 = var0[0].toHeader();
            StringBuffer var3 = new StringBuffer(var2);
            int var4 = 1;

            while(true) {
               int var5 = var0.length;
               if(var4 >= var5) {
                  var1 = var3.toString();
                  break;
               }

               StringBuffer var6 = var3.append(", ");
               String var7 = var0[var4].toHeader();
               var3.append(var7);
               ++var4;
            }
         }
      } else {
         var1 = null;
      }

      return var1;
   }

   public static String toString(Address[] var0) {
      String var1;
      if(var0 != null && var0.length != 0) {
         if(var0.length == 1) {
            var1 = var0[0].toString();
         } else {
            String var2 = var0[0].toString();
            StringBuffer var3 = new StringBuffer(var2);
            int var4 = 1;

            while(true) {
               int var5 = var0.length;
               if(var4 >= var5) {
                  var1 = var3.toString();
                  break;
               }

               StringBuffer var6 = var3.append(',');
               String var7 = var0[var4].toString();
               var3.append(var7);
               ++var4;
            }
         }
      } else {
         var1 = null;
      }

      return var1;
   }

   public static Address[] unpack(String var0) {
      Address[] var1;
      if(var0 != null && var0.length() != 0) {
         ArrayList var2 = new ArrayList();
         int var3 = var0.length();
         int var4 = 0;

         int var6;
         for(int var5 = var0.indexOf(2); var4 < var3; var4 = var6 + 1) {
            var6 = var0.indexOf(1, var4);
            if(var6 == -1) {
               ;
            }

            Address var9;
            if(var5 != -1 && var6 > var5) {
               String var11 = var0.substring(var4, var5);
               int var12 = var5 + 1;
               String var13 = var0.substring(var12, var6);
               var9 = new Address(var11, var13);
               int var14 = var6 + 1;
               var5 = var0.indexOf(2, var14);
            } else {
               String var8 = var0.substring(var4, var6);
               var9 = new Address(var8, (String)null);
            }

            var2.add(var9);
         }

         Address[] var15 = EMPTY_ADDRESS_ARRAY;
         var1 = (Address[])var2.toArray(var15);
      } else {
         var1 = EMPTY_ADDRESS_ARRAY;
      }

      return var1;
   }

   public static Address unpackFirst(String var0) {
      Address[] var1 = unpack(var0);
      Address var2;
      if(var1.length > 0) {
         var2 = var1[0];
      } else {
         var2 = null;
      }

      return var2;
   }

   public static String unpackToString(String var0) {
      return toString(unpack(var0));
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 instanceof Address) {
         String var2 = this.getAddress();
         String var3 = ((Address)var1).getAddress();
         var4 = var2.equals(var3);
      } else {
         var4 = super.equals(var1);
      }

      return var4;
   }

   public String getAddress() {
      return this.mAddress;
   }

   public String getPersonal() {
      return this.mPersonal;
   }

   public String pack() {
      String var1 = this.getAddress();
      String var2 = this.getPersonal();
      String var3;
      if(var2 == null) {
         var3 = var1;
      } else {
         var3 = var1 + '\u0002' + var2;
      }

      return var3;
   }

   public void setAddress(String var1) {
      String var2 = REMOVE_OPTIONAL_BRACKET.matcher(var1).replaceAll("$1");
      this.mAddress = var2;
   }

   public void setPersonal(String var1) {
      if(var1 != null) {
         String var2 = REMOVE_OPTIONAL_DQUOTE.matcher(var1).replaceAll("$1");
         var1 = DecoderUtil.decodeEncodedWords(UNQUOTE.matcher(var2).replaceAll("$1"));
         if(var1.length() == 0) {
            var1 = null;
         }
      }

      this.mPersonal = var1;
   }

   public String toFriendly() {
      String var1;
      if(this.mPersonal != null && this.mPersonal.length() > 0) {
         var1 = this.mPersonal;
      } else {
         var1 = this.mAddress;
      }

      return var1;
   }

   public String toHeader() {
      String var5;
      if(this.mPersonal != null) {
         StringBuilder var1 = new StringBuilder();
         String var2 = EncoderUtil.encodeAddressDisplayName(this.mPersonal);
         StringBuilder var3 = var1.append(var2).append(" <");
         String var4 = this.mAddress;
         var5 = var3.append(var4).append(">").toString();
      } else {
         var5 = this.mAddress;
      }

      return var5;
   }

   public String toString() {
      String var5;
      if(this.mPersonal != null) {
         if(this.mPersonal.matches(".*[\\(\\)<>@,;:\\\\\".\\[\\]].*")) {
            StringBuilder var1 = new StringBuilder();
            String var2 = Utility.quoteString(this.mPersonal);
            StringBuilder var3 = var1.append(var2).append(" <");
            String var4 = this.mAddress;
            var5 = var3.append(var4).append(">").toString();
         } else {
            StringBuilder var6 = new StringBuilder();
            String var7 = this.mPersonal;
            StringBuilder var8 = var6.append(var7).append(" <");
            String var9 = this.mAddress;
            var5 = var8.append(var9).append(">").toString();
         }
      } else {
         var5 = this.mAddress;
      }

      return var5;
   }
}
