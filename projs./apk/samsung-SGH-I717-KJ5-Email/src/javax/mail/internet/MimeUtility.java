package javax.mail.internet;

import gnu.inet.util.LineInputStream;
import gnu.mail.util.BOutputStream;
import gnu.mail.util.Base64InputStream;
import gnu.mail.util.Base64OutputStream;
import gnu.mail.util.QOutputStream;
import gnu.mail.util.QPInputStream;
import gnu.mail.util.QPOutputStream;
import gnu.mail.util.UUInputStream;
import gnu.mail.util.UUOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.ParseException;

public class MimeUtility {

   public static final int ALL = 255;
   static final int ALL_ASCII = 1;
   static final int MAJORITY_ASCII = 2;
   static final int MINORITY_ASCII = 3;
   private static String defaultJavaCharset;
   private static boolean java12;
   private static HashMap javaCharsets;
   private static HashMap mimeCharsets;
   private static int part;


   static {
      // $FF: Couldn't be decompiled
   }

   private MimeUtility() {}

   static int asciiStatus(InputStream param0, int param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   static int asciiStatus(byte[] var0) {
      int var1 = 0;
      int var2 = var1;
      int var3 = var1;

      while(true) {
         int var4 = var0.length;
         if(var1 >= var4) {
            byte var5;
            if(var2 == 0) {
               var5 = 1;
            } else if(var3 <= var2) {
               var5 = 3;
            } else {
               var5 = 2;
            }

            return var5;
         }

         if(isAscii(var0[var1])) {
            ++var3;
         } else {
            ++var2;
         }

         ++var1;
      }
   }

   public static InputStream decode(InputStream var0, String var1) throws MessagingException {
      Object var2;
      if(var1.equalsIgnoreCase("base64")) {
         var2 = new Base64InputStream(var0);
      } else if(var1.equalsIgnoreCase("quoted-printable")) {
         var2 = new QPInputStream(var0);
      } else if(!var1.equalsIgnoreCase("uuencode") && !var1.equalsIgnoreCase("x-uuencode")) {
         if(!var1.equalsIgnoreCase("binary") && !var1.equalsIgnoreCase("7bit") && !var1.equalsIgnoreCase("8bit")) {
            String var3 = "Unknown encoding: " + var1;
            throw new MessagingException(var3);
         }

         var2 = var0;
      } else {
         var2 = new UUInputStream(var0);
      }

      return (InputStream)var2;
   }

   private static String decodeInnerText(String var0) throws UnsupportedEncodingException {
      StringBuffer var1 = new StringBuffer();
      int var2 = var0.indexOf("=?", 0);
      int var3 = 0;

      for(int var4 = var2; var4 != -1; var4 = var0.indexOf("=?", var3)) {
         int var5 = var4 + 2;
         int var6 = var0.indexOf("?=", var5);
         if(var6 == -1) {
            break;
         }

         String var10 = var0.substring(var3, var4);
         var1.append(var10);
         var3 = var6 + 2;
         String var12 = var0.substring(var4, var3);

         try {
            String var13 = decodeWord(var12);
            var1.append(var13);
         } catch (ParseException var17) {
            var1.append(var12);
         }
      }

      String var18;
      if(var1.length() > 0) {
         int var7 = var0.length();
         if(var3 < var7) {
            String var8 = var0.substring(var3);
            var1.append(var8);
         }

         var18 = var1.toString();
      } else {
         var18 = var0;
      }

      return var18;
   }

   public static String decodeText(String var0) throws UnsupportedEncodingException {
      String var1;
      if(var0.indexOf("=?") == -1) {
         var1 = var0;
      } else {
         StringTokenizer var2 = new StringTokenizer(var0, "\t\n\r ", (boolean)1);
         StringBuffer var3 = new StringBuffer();
         StringBuffer var4 = new StringBuffer();
         boolean var5 = false;

         while(var2.hasMoreTokens()) {
            String var6 = var2.nextToken();
            char var7 = var6.charAt(0);
            if("\t\n\r ".indexOf(var7) > -1) {
               var4.append(var7);
            } else {
               boolean var16;
               String var10;
               label40: {
                  try {
                     var6 = decodeWord(var6);
                     if(!var5 && var4.length() > 0) {
                        var3.append(var4);
                     }
                  } catch (ParseException var15) {
                     var10 = var6;
                     if(!decodetextStrict()) {
                        String var13 = decodeInnerText(var6);
                     }

                     if(var4.length() > 0) {
                        var3.append(var4);
                     }

                     var16 = false;
                     break label40;
                  }

                  var10 = var6;
                  var16 = true;
               }

               var3.append(var10);
               var4.setLength(0);
               var5 = var16;
            }
         }

         var1 = var3.toString();
      }

      return var1;
   }

   public static String decodeWord(String param0) throws ParseException, UnsupportedEncodingException {
      // $FF: Couldn't be decompiled
   }

   private static boolean decodetextStrict() {
      boolean var0;
      boolean var1;
      try {
         var0 = Boolean.valueOf(System.getProperty("mail.mime.decodetext.strict", "true")).booleanValue();
      } catch (SecurityException var3) {
         var1 = true;
         return var1;
      }

      var1 = var0;
      return var1;
   }

   public static OutputStream encode(OutputStream var0, String var1) throws MessagingException {
      Object var2;
      if(var1 == null) {
         var2 = var0;
      } else if(var1.equalsIgnoreCase("base64")) {
         var2 = new Base64OutputStream(var0);
      } else if(var1.equalsIgnoreCase("quoted-printable")) {
         var2 = new QPOutputStream(var0);
      } else if(!var1.equalsIgnoreCase("uuencode") && !var1.equalsIgnoreCase("x-uuencode")) {
         if(!var1.equalsIgnoreCase("binary") && !var1.equalsIgnoreCase("7bit") && !var1.equalsIgnoreCase("8bit")) {
            String var3 = "Unknown encoding: " + var1;
            throw new MessagingException(var3);
         }

         var2 = var0;
      } else {
         var2 = new UUOutputStream(var0);
      }

      return (OutputStream)var2;
   }

   public static OutputStream encode(OutputStream var0, String var1, String var2) throws MessagingException {
      Object var3;
      if(var1 == null) {
         var3 = var0;
      } else if(var1.equalsIgnoreCase("base64")) {
         var3 = new Base64OutputStream(var0);
      } else if(var1.equalsIgnoreCase("quoted-printable")) {
         var3 = new QPOutputStream(var0);
      } else if(!var1.equalsIgnoreCase("uuencode") && !var1.equalsIgnoreCase("x-uuencode")) {
         if(!var1.equalsIgnoreCase("binary") && !var1.equalsIgnoreCase("7bit") && !var1.equalsIgnoreCase("8bit")) {
            String var4 = "Unknown encoding: " + var1;
            throw new MessagingException(var4);
         }

         var3 = var0;
      } else {
         var3 = new UUOutputStream(var0, var2);
      }

      return (OutputStream)var3;
   }

   private static void encodeBuffer(StringBuffer var0, String var1, String var2, boolean var3, int var4, String var5, boolean var6, boolean var7) throws UnsupportedEncodingException {
      byte[] var8 = var1.getBytes(var2);
      int var9;
      if(var3) {
         var9 = BOutputStream.encodedLength(var8);
      } else {
         int var35 = QOutputStream.encodedLength(var8, var7);
      }

      int var10 = var1.length();
      if(var9 > var4 && var10 > 1) {
         int var13 = var10 / 2;
         String var14 = var1.substring(0, var13);
         encodeBuffer(var0, var14, var2, var3, var4, var5, var6, var7);
         int var22 = var10 / 2;
         String var26 = var1.substring(var22, var10);
         encodeBuffer(var0, var26, var2, var3, var4, var5, (boolean)0, var7);
      } else {
         ByteArrayOutputStream var36 = new ByteArrayOutputStream();
         Object var37;
         if(var3) {
            var37 = new BOutputStream(var36);
         } else {
            var37 = new QOutputStream(var36, var7);
         }

         try {
            ((OutputStream)var37).write(var8);
            ((OutputStream)var37).close();
         } catch (IOException var52) {
            ;
         }

         byte[] var38 = var36.toByteArray();
         if(!var6) {
            StringBuffer var39 = var0.append("\r\n ");
         }

         StringBuffer var42 = var0.append(var5);
         byte var53 = 0;

         while(true) {
            int var43 = var38.length;
            if(var53 >= var43) {
               StringBuffer var50 = var0.append("?=");
               return;
            }

            char var44 = (char)var38[var53];
            var0.append(var44);
            int var46 = var53 + 1;
         }
      }
   }

   public static String encodeText(String var0) throws UnsupportedEncodingException {
      return encodeText(var0, (String)null, (String)null);
   }

   public static String encodeText(String var0, String var1, String var2) throws UnsupportedEncodingException {
      return encodeWord(var0, var1, var2, (boolean)0);
   }

   public static String encodeWord(String var0) throws UnsupportedEncodingException {
      return encodeWord(var0, (String)null, (String)null);
   }

   public static String encodeWord(String var0, String var1, String var2) throws UnsupportedEncodingException {
      return encodeWord(var0, var1, var2, (boolean)1);
   }

   private static String encodeWord(String var0, String var1, String var2, boolean var3) throws UnsupportedEncodingException {
      String var4;
      if(asciiStatus(var0.getBytes()) == 1) {
         var4 = var0;
      } else {
         String var6;
         String var7;
         if(var1 == null) {
            String var5 = getDefaultJavaCharset();
            var6 = mimeCharset(var5);
            var7 = var5;
         } else {
            var7 = javaCharset(var1);
            var6 = var1;
         }

         String var8;
         if(var2 == null) {
            if(asciiStatus(var0.getBytes(var7)) != 3) {
               var8 = "Q";
            } else {
               var8 = "B";
            }
         } else {
            var8 = var2;
         }

         byte var9;
         if(var8.equalsIgnoreCase("B")) {
            var9 = 1;
         } else {
            if(!var8.equalsIgnoreCase("Q")) {
               String var22 = "Unknown transfer encoding: " + var8;
               throw new UnsupportedEncodingException(var22);
            }

            var9 = 0;
         }

         StringBuffer var10 = new StringBuffer();
         StringBuffer var11 = var10.append("=?");
         var10.append(var6);
         StringBuffer var13 = var10.append("?");
         var10.append(var8);
         StringBuffer var15 = var10.append("?");
         StringBuffer var16 = new StringBuffer();
         int var17 = var6.length();
         int var18 = 68 - var17;
         String var19 = var10.toString();
         encodeBuffer(var16, var0, var7, (boolean)var9, var18, var19, (boolean)1, var3);
         var4 = var16.toString();
      }

      return var4;
   }

   private static boolean encodeeolStrict() {
      boolean var0;
      boolean var1;
      try {
         var0 = Boolean.valueOf(System.getProperty("mail.mime.encodeeol.strict", "false")).booleanValue();
      } catch (SecurityException var3) {
         var1 = false;
         return var1;
      }

      var1 = var0;
      return var1;
   }

   public static String fold(int var0, String var1) {
      int var2 = var1.length();
      int var3 = Math.min(76 - var0, var2);
      String var16;
      if(var3 == var2) {
         var16 = var1;
      } else {
         StringBuffer var4 = new StringBuffer();
         String var5 = var1;
         int var7 = var3;
         var3 = var2;

         while(true) {
            int var8 = whitespaceIndexOf(var5, var7, -1, var3);
            int var9;
            if(var8 == -1) {
               var9 = whitespaceIndexOf(var5, var7, 1, var3);
            } else {
               var9 = var8;
            }

            if(var9 != -1) {
               String var10 = var5.substring(0, var9);
               var4.append(var10);
               StringBuffer var12 = var4.append('\n');
               var5 = var5.substring(var9);
               int var10000 = var3 - var9;
            }

            int var14 = Math.min(76, var3);
            if(var9 == -1) {
               var4.append(var5);
               var16 = var4.toString();
               break;
            }

            var7 = var14;
         }
      }

      return var16;
   }

   public static String getDefaultJavaCharset() {
      if(defaultJavaCharset == null) {
         try {
            defaultJavaCharset = System.getProperty("mail.mime.charset");
            if(defaultJavaCharset == null) {
               defaultJavaCharset = System.getProperty("file.encoding", "UTF-8");
            }
         } catch (SecurityException var2) {
            MimeUtility.1 var1 = new MimeUtility.1();
            defaultJavaCharset = (new InputStreamReader(var1)).getEncoding();
            if(defaultJavaCharset == null) {
               defaultJavaCharset = "UTF-8";
            }
         }
      }

      return javaCharset(defaultJavaCharset);
   }

   public static String getEncoding(DataHandler var0) {
      String var1 = "base64";
      if(var0.getName() != null) {
         var1 = getEncoding(var0.getDataSource());
      } else {
         try {
            String var2 = var0.getContentType();
            boolean var3 = (new ContentType(var2)).match("text/*");
            MimeUtility.AsciiOutputStream var4 = new MimeUtility.AsciiOutputStream;
            byte var5;
            if(!var3) {
               var5 = 1;
            } else {
               var5 = 0;
            }

            byte var6;
            if(encodeeolStrict() && !var3) {
               var6 = 1;
            } else {
               var6 = 0;
            }

            var4.<init>((boolean)var5, (boolean)var6);

            try {
               var0.writeTo(var4);
            } catch (IOException var9) {
               ;
            }

            switch(var4.status()) {
            case 1:
               var1 = "7bit";
               break;
            case 2:
               if(var3) {
                  var1 = "quoted-printable";
               }
            }
         } catch (Exception var10) {
            ;
         }
      }

      return var1;
   }

   public static String getEncoding(DataSource param0) {
      // $FF: Couldn't be decompiled
   }

   static String getUniqueBoundaryValue() {
      StringBuffer var0 = new StringBuffer();
      StringBuffer var1 = var0.append("----=_Part_");
      int var2 = part;
      part = var2 + 1;
      var0.append(var2);
      StringBuffer var4 = var0.append("_");
      int var5 = Math.abs(var0.hashCode());
      var0.append(var5);
      StringBuffer var7 = var0.append('.');
      long var8 = System.currentTimeMillis();
      var0.append(var8);
      return var0.toString();
   }

   static String getUniqueMessageIDValue(Session var0) {
      InternetAddress var1 = InternetAddress.getLocalAddress(var0);
      String var2;
      if(var1 != null) {
         var2 = var1.getAddress();
      } else {
         var2 = "javamailuser@localhost";
      }

      StringBuffer var3 = new StringBuffer();
      int var4 = Math.abs(getUniqueBoundaryValue().hashCode());
      var3.append(var4);
      StringBuffer var6 = var3.append('.');
      long var7 = System.currentTimeMillis();
      var3.append(var7);
      StringBuffer var10 = var3.append('.');
      StringBuffer var11 = var3.append("JavaMail.");
      var3.append(var2);
      return var3.toString();
   }

   private static final boolean isAscii(int var0) {
      int var1;
      if(var0 < 0) {
         var1 = var0 + 255;
      } else {
         var1 = var0;
      }

      boolean var2;
      if((var1 >= 128 || var1 <= 31) && var1 != 13 && var1 != 10 && var1 != 9) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static String javaCharset(String var0) {
      String var1;
      if(mimeCharsets != null && var0 != null) {
         HashMap var2 = mimeCharsets;
         String var3 = var0.toLowerCase();
         var1 = (String)var2.get(var3);
         if(var1 != null) {
            if(!java12) {
               HashMap var4 = javaCharsets;
               String var5 = var1.toLowerCase();
               var1 = (String)var4.get(var5);
               if(var1 == null) {
                  var1 = var0;
               }
            }
         } else {
            var1 = var0;
         }
      } else {
         var1 = var0;
      }

      return var1;
   }

   public static String mimeCharset(String var0) {
      String var1;
      if(javaCharsets != null && var0 != null) {
         HashMap var2 = javaCharsets;
         String var3 = var0.toLowerCase();
         var1 = (String)var2.get(var3);
         if(var1 == null) {
            var1 = var0;
         }
      } else {
         var1 = var0;
      }

      return var1;
   }

   private static void parse(HashMap var0, LineInputStream var1) {
      try {
         while(true) {
            String var2 = var1.readLine();
            if(var2 == null) {
               return;
            }

            if(var2.startsWith("--") && var2.endsWith("--")) {
               return;
            }

            if(var2.trim().length() != 0 && !var2.startsWith("#")) {
               StringTokenizer var3 = new StringTokenizer(var2, "\t ");

               try {
                  String var4 = var3.nextToken();
                  String var5 = var3.nextToken();
                  String var6 = var4.toLowerCase();
                  var0.put(var6, var5);
               } catch (NoSuchElementException var9) {
                  ;
               }
            }
         }
      } catch (IOException var10) {
         var10.printStackTrace();
      }
   }

   public static String quote(String var0, String var1) {
      int var2 = var0.length();
      int var3 = 0;
      boolean var4 = false;

      String var17;
      while(true) {
         if(var3 >= var2) {
            if(var4) {
               int var12 = var2 + 2;
               StringBuffer var13 = new StringBuffer(var12);
               StringBuffer var14 = var13.append('\"');
               var13.append(var0);
               StringBuffer var16 = var13.append('\"');
               var17 = var13.toString();
            } else {
               var17 = var0;
            }
            break;
         }

         char var5 = var0.charAt(var3);
         if(var5 == 10 || var5 == 13 || var5 == 34 || var5 == 92) {
            int var6 = var2 + 3;
            StringBuffer var18 = new StringBuffer(var6);
            StringBuffer var7 = var18.append('\"');

            int var10;
            for(byte var19 = 0; var19 < var2; var10 = var19 + 1) {
               var5 = var0.charAt(var19);
               if(var5 == 34 || var5 == 92 || var5 == 13 || var5 == 10) {
                  StringBuffer var8 = var18.append('\\');
               }

               var18.append(var5);
            }

            StringBuffer var11 = var18.append('\"');
            var17 = var18.toString();
            break;
         }

         if(var5 < 32 || var5 > 127 || var1.indexOf(var5) >= 0) {
            var4 = true;
         }

         ++var3;
      }

      return var17;
   }

   public static String unfold(String var0) {
      int var1 = 0;
      int var2 = var0.length();
      StringBuffer var3 = null;

      for(int var4 = var1; var4 < var2; ++var4) {
         if(var0.charAt(var4) == 10) {
            int var5 = var2 - 1;
            if(var4 < var5) {
               int var6 = var4 + 1;
               char var7 = var0.charAt(var6);
               if(var7 == 32 || var7 == 9) {
                  String var8 = var0.substring(var1, var4);
                  if(var3 == null) {
                     var3 = new StringBuffer();
                  }

                  var3.append(var8);
                  var1 = var4 + 1;
               }
            }
         }
      }

      String var12;
      if(var3 == null) {
         var12 = var0;
      } else {
         String var10 = var0.substring(var1);
         var3.append(var10);
         var12 = var3.toString();
      }

      return var12;
   }

   private static int whitespaceIndexOf(String var0, int var1, int var2, int var3) {
      int var4 = var1;

      while(true) {
         if(var4 <= 0 || var4 >= var3) {
            var4 = -1;
            break;
         }

         char var5 = var0.charAt(var4);
         if(var5 == 32 || var5 == 9) {
            break;
         }

         var4 += var2;
      }

      return var4;
   }

   static class AsciiOutputStream extends OutputStream {

      static final int CR = 13;
      static final int LF = 10;
      private int asciiCount = 0;
      private boolean eolCheckFailed = 0;
      private boolean eolStrict;
      private boolean islong = 0;
      private int last = -1;
      private int len;
      private int nonAsciiCount = 0;
      private int ret;
      private boolean strict;


      public AsciiOutputStream(boolean var1, boolean var2) {
         this.strict = var1;
         this.eolStrict = var2;
      }

      private final void check(int var1) throws IOException {
         int var2 = var1 & 255;
         if(this.eolStrict && (this.last == 13 && var2 != 10 || this.last != 13 && var2 == 10)) {
            this.eolCheckFailed = (boolean)1;
         }

         if(var2 != 13 && var2 != 10) {
            int var4 = this.len + 1;
            this.len = var4;
            if(this.len > 998) {
               this.islong = (boolean)1;
            }
         } else {
            this.len = 0;
         }

         if(var2 > 127) {
            int var3 = this.nonAsciiCount + 1;
            this.nonAsciiCount = var3;
            if(this.strict) {
               this.ret = 3;
               throw new EOFException();
            }
         } else {
            int var5 = this.asciiCount + 1;
            this.asciiCount = var5;
         }

         this.last = var2;
      }

      int status() {
         int var1;
         if(this.ret != 0) {
            var1 = this.ret;
         } else if(this.eolCheckFailed) {
            var1 = 3;
         } else if(this.nonAsciiCount == 0) {
            if(!this.islong) {
               var1 = 1;
            } else {
               var1 = 2;
            }
         } else {
            int var2 = this.asciiCount;
            int var3 = this.nonAsciiCount;
            if(var2 <= var3) {
               var1 = 2;
            } else {
               var1 = 3;
            }
         }

         return var1;
      }

      public void write(int var1) throws IOException {
         this.check(var1);
      }

      public void write(byte[] var1) throws IOException {
         int var2 = var1.length;
         this.write(var1, 0, var2);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         int var4 = var3 + var2;

         for(int var5 = var2; var5 < var4; ++var5) {
            byte var6 = var1[var5];
            this.check(var6);
         }

      }
   }

   static class 1 extends InputStream {

      1() {}

      public int read() {
         return 0;
      }
   }
}
