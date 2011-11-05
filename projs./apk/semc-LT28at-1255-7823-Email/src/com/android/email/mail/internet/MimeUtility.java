package com.android.email.mail.internet;

import com.android.email.mail.Body;
import com.android.email.mail.Message;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Multipart;
import com.android.email.mail.Part;
import com.android.email.mail.internet.BinaryTempFileBody;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.james.mime4j.codec.EncoderUtil;
import org.apache.james.mime4j.decoder.Base64InputStream;
import org.apache.james.mime4j.decoder.DecoderUtil;
import org.apache.james.mime4j.decoder.QuotedPrintableInputStream;

public class MimeUtility {

   public static final String MIME_TYPE_RFC822 = "message/rfc822";
   private static final Pattern PATTERN_CR_OR_LF = Pattern.compile("\r|\n");


   public MimeUtility() {}

   public static void collectParts(Part var0, ArrayList<Part> var1, ArrayList<Part> var2) throws MessagingException {
      String var3 = var0.getDisposition();
      String var4 = null;
      String var5 = null;
      if(var3 != null) {
         var4 = getHeaderParameter(var3, (String)null);
         var5 = getHeaderParameter(var3, "filename");
      }

      boolean var6;
      if(!"attachment".equalsIgnoreCase(var4) && (var5 == null || "inline".equalsIgnoreCase(var4))) {
         var6 = false;
      } else {
         var6 = true;
      }

      if(!(var0.getBody() instanceof Multipart)) {
         if(var0.getBody() instanceof Message) {
            collectParts((Message)var0.getBody(), var1, var2);
         } else if(!var6 && var0.getMimeType().equalsIgnoreCase("text/html")) {
            var1.add(var0);
         } else if(!var6 && var0.getMimeType().equalsIgnoreCase("text/plain")) {
            var1.add(var0);
         } else {
            var2.add(var0);
         }
      } else {
         Multipart var7 = (Multipart)var0.getBody();
         int var8 = 0;

         while(true) {
            int var9 = var7.getCount();
            if(var8 >= var9) {
               return;
            }

            collectParts(var7.getBodyPart(var8), var1, var2);
            ++var8;
         }
      }
   }

   public static String decode(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = DecoderUtil.decodeEncodedWords(var0);
      }

      return var1;
   }

   public static Body decodeBody(InputStream var0, String var1) throws IOException {
      if(var1 != null) {
         var1 = getHeaderParameter(var1, (String)null);
         if("quoted-printable".equalsIgnoreCase(var1)) {
            var0 = new QuotedPrintableInputStream((InputStream)var0);
         } else if("base64".equalsIgnoreCase(var1)) {
            var0 = new Base64InputStream((InputStream)var0);
         }
      }

      BinaryTempFileBody var2 = new BinaryTempFileBody();
      OutputStream var3 = var2.getOutputStream();
      IOUtils.copy((InputStream)var0, var3);
      var3.close();
      ((InputStream)var0).close();
      return var2;
   }

   public static Part findFirstPartByMimeType(Part var0, String var1) throws MessagingException {
      Part var6;
      if(var0.getBody() instanceof Multipart) {
         Multipart var2 = (Multipart)var0.getBody();
         int var3 = 0;

         for(int var4 = var2.getCount(); var3 < var4; ++var3) {
            Part var5 = findFirstPartByMimeType(var2.getBodyPart(var3), var1);
            if(var5 != null) {
               var6 = var5;
               return var6;
            }
         }
      } else if(var0.getMimeType().equalsIgnoreCase(var1)) {
         var6 = var0;
         return var6;
      }

      var6 = null;
      return var6;
   }

   public static Part findPartByContentId(Part var0, String var1) throws Exception {
      Part var6;
      if(var0.getBody() instanceof Multipart) {
         Multipart var2 = (Multipart)var0.getBody();
         int var3 = 0;

         for(int var4 = var2.getCount(); var3 < var4; ++var3) {
            Part var5 = findPartByContentId(var2.getBodyPart(var3), var1);
            if(var5 != null) {
               var6 = var5;
               return var6;
            }
         }
      }

      String var7 = var0.getContentId();
      if(var1.equals(var7)) {
         var6 = var0;
      } else {
         var6 = null;
      }

      return var6;
   }

   public static String fold(String var0, int var1) {
      int var2 = var0.length();
      String var3;
      if(var1 + var2 <= 76) {
         var3 = var0;
      } else {
         StringBuilder var4 = new StringBuilder();
         int var5 = -var1;

         int var11;
         for(int var6 = indexOfWsp(var0, 0); var6 != var2; var6 = var11) {
            int var10 = var6 + 1;
            var11 = indexOfWsp(var0, var10);
            if(var11 - var5 > 76) {
               int var12 = Math.max(0, var5);
               String var13 = var0.substring(var12, var6);
               var4.append(var13);
               StringBuilder var15 = var4.append("\r\n");
               var5 = var6;
            }
         }

         int var7 = Math.max(0, var5);
         String var8 = var0.substring(var7);
         var4.append(var8);
         var3 = var4.toString();
      }

      return var3;
   }

   public static String foldAndEncode(String var0) {
      return var0;
   }

   public static String foldAndEncode2(String var0, int var1) {
      EncoderUtil.Usage var2 = EncoderUtil.Usage.TEXT_TOKEN;
      return fold(EncoderUtil.encodeIfNecessary(var0, var2, var1), var1);
   }

   public static String getHeaderParameter(String var0, String var1) {
      String var2;
      if(var0 == null) {
         var2 = null;
      } else {
         String[] var3 = unfold(var0).split(";");
         if(var1 == null) {
            var2 = var3[0].trim();
         } else {
            String var4 = var1.toLowerCase();
            String[] var5 = var3;
            int var6 = var3.length;
            int var7 = 0;

            while(true) {
               if(var7 >= var6) {
                  var2 = null;
                  break;
               }

               String var8 = var5[var7];
               if(var8.trim().toLowerCase().startsWith(var4)) {
                  String[] var9 = var8.split("=", 2);
                  if(var9.length < 2) {
                     var2 = null;
                  } else {
                     String var10 = var9[1].trim();
                     if(var10.startsWith("\"") && var10.endsWith("\"")) {
                        int var11 = var10.length() - 1;
                        var2 = var10.substring(1, var11);
                     } else {
                        var2 = var10;
                     }
                  }
                  break;
               }

               ++var7;
            }
         }
      }

      return var2;
   }

   public static String getTextFromPart(Part param0) {
      // $FF: Couldn't be decompiled
   }

   private static int indexOfWsp(String var0, int var1) {
      int var2 = var0.length();
      int var3 = var1;

      int var5;
      while(true) {
         if(var3 < var2) {
            char var4 = var0.charAt(var3);
            if(var4 != 32 && var4 != 9) {
               ++var3;
               continue;
            }

            var5 = var3;
            break;
         }

         var5 = var2;
         break;
      }

      return var5;
   }

   public static boolean mimeTypeMatches(String var0, String var1) {
      return Pattern.compile(var1.replaceAll("\\*", "\\.\\*"), 2).matcher(var0).matches();
   }

   public static boolean mimeTypeMatches(String var0, String[] var1) {
      String[] var2 = var1;
      int var3 = var1.length;
      int var4 = 0;

      boolean var6;
      while(true) {
         if(var4 >= var3) {
            var6 = false;
            break;
         }

         String var5 = var2[var4];
         if(mimeTypeMatches(var0, var5)) {
            var6 = true;
            break;
         }

         ++var4;
      }

      return var6;
   }

   public static String unfold(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         Matcher var2 = PATTERN_CR_OR_LF.matcher(var0);
         if(var2.find()) {
            Matcher var3 = var2.reset();
            var0 = var2.replaceAll("");
         }

         var1 = var0;
      }

      return var1;
   }

   public static String unfoldAndDecode(String var0) {
      return decode(unfold(var0));
   }
}
