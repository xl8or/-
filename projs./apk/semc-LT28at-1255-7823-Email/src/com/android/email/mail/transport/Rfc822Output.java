package com.android.email.mail.transport;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import com.android.email.mail.Address;
import com.android.email.mail.MessagingException;
import com.android.email.mail.internet.MimeUtility;
import com.android.email.provider.EmailContent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rfc822Output {

   private static final String BODY_PATTERN = "(?:<\\s*body[^>]*>)(.*)(?:<\\s*/\\s*body\\s*>)";
   private static final SimpleDateFormat DATE_FORMAT;
   private static final int HTML_BODY_IDX = 1;
   private static final Pattern PATTERN_ENDLINE_CRLF = Pattern.compile("\r\n");
   private static final Pattern PATTERN_START_OF_LINE = Pattern.compile("(?m)^");
   private static final int TEXT_BODY_IDX;


   static {
      Locale var0 = Locale.US;
      DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", var0);
   }

   public Rfc822Output() {}

   static String buildBodyText(Context var0, EmailContent.Message var1, boolean var2) {
      long var3 = var1.mId;
      EmailContent.Body var5 = EmailContent.Body.restoreBodyWithMessageId(var0, var3);
      int var6 = var1.mFlags;
      return buildBodyText(var5, var6, var2)[0];
   }

   private static String[] buildBodyText(EmailContent.Body var0, int var1, boolean var2) {
      String[] var3 = new String[]{false, false};
      if(var0 != null) {
         String var4 = var0.mTextContent;
         boolean var5;
         if((var1 & 1) != 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         boolean var6;
         if((var1 & 2) != 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         if(var5 || var6) {
            String var7;
            if(var0.mIntroText == null) {
               var7 = "";
            } else {
               var7 = var0.mIntroText;
            }

            var4 = var4 + var7;
         }

         if(var2) {
            if(var6) {
               var4 = var4 + "\n";
            }
         } else {
            String var9 = var0.mTextReply;
            if(var9 == null && var0.mHtmlReply != null) {
               var9 = Html.fromHtml(var0.mHtmlReply).toString();
            }

            if(var9 != null) {
               var9 = PATTERN_ENDLINE_CRLF.matcher(var9).replaceAll("\n");
            }

            if((var5 || var6) && var9 != null) {
               var4 = var4 + var9;
            }
         }

         var3[0] = var4;
         String var8 = getHtmlAlternate(var0, var2);
         var3[1] = var8;
      }

      return var3;
   }

   static String getHtmlAlternate(EmailContent.Body var0, boolean var1) {
      String var2;
      if(var0.mHtmlReply == null) {
         var2 = null;
      } else {
         StringBuffer var3 = new StringBuffer();
         String var4 = TextUtils.htmlEncode(var0.mTextContent).replaceAll("\\r?\\n", "<br>");
         var3.append(var4);
         if(var0.mIntroText != null) {
            StringBuffer var6 = var3.append("<br><br>");
            StringBuffer var7 = var3.append("<HR style=\"FILTER: alpha(opacity=100,finishopacity=0,style=3)\" width=\"99%\" color=#B5C4DF SIZE=1>");
            String var8 = TextUtils.htmlEncode(var0.mIntroText).replaceFirst("\\n\\n--.*--\\n", "").replaceAll("\\r?\\n", "<br>");
            var3.append(var8);
         }

         if(!var1) {
            String var10 = getHtmlBody(var0.mHtmlReply);
            var3.append(var10);
         }

         var2 = var3.toString();
      }

      return var2;
   }

   static String getHtmlBody(String var0) {
      Matcher var1 = Pattern.compile("(?:<\\s*body[^>]*>)(.*)(?:<\\s*/\\s*body\\s*>)", 34).matcher(var0);
      String var2;
      if(var1.find()) {
         var2 = var1.group(1);
      } else {
         var2 = var0;
      }

      return var2;
   }

   private static void writeAddressHeader(Writer var0, String var1, String var2) throws IOException {
      if(var2 != null) {
         if(var2.length() > 0) {
            var0.append(var1);
            Writer var4 = var0.append(": ");
            String var5 = Address.packedToHeader(var2);
            int var6 = var1.length() + 2;
            String var7 = MimeUtility.fold(var5, var6);
            var0.append(var7);
            Writer var9 = var0.append("\r\n");
         }
      }
   }

   private static void writeBoundary(Writer var0, String var1, boolean var2) throws IOException {
      Writer var3 = var0.append("--");
      var0.append(var1);
      if(var2) {
         Writer var5 = var0.append("--");
      }

      Writer var6 = var0.append("\r\n");
   }

   private static void writeEncodedHeader(Writer var0, String var1, String var2) throws IOException {
      if(var2 != null) {
         if(var2.length() > 0) {
            var0.append(var1);
            Writer var4 = var0.append(": ");
            int var5 = var1.length() + 2;
            String var6 = MimeUtility.foldAndEncode2(var2, var5);
            var0.append(var6);
            Writer var8 = var0.append("\r\n");
         }
      }
   }

   private static void writeHeader(Writer var0, String var1, String var2) throws IOException {
      if(var2 != null) {
         if(var2.length() > 0) {
            var0.append(var1);
            Writer var4 = var0.append(": ");
            var0.append(var2);
            Writer var6 = var0.append("\r\n");
         }
      }
   }

   private static void writeOneAttachment(Context param0, Writer param1, OutputStream param2, EmailContent.Attachment param3) throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }

   private static void writeTextWithHeaders(Writer var0, OutputStream var1, String[] var2) throws IOException {
      String var3 = var2[0];
      String var4 = var2[1];
      if(var3 == null) {
         var0.write("\r\n");
      } else {
         String var5 = null;
         boolean var6;
         if(var4 != null) {
            var6 = true;
         } else {
            var6 = false;
         }

         if(var6) {
            StringBuilder var7 = (new StringBuilder()).append("--_com.android.email_");
            long var8 = System.nanoTime();
            var5 = var7.append(var8).toString();
            String var10 = "multipart/alternative; boundary=\"" + var5 + "\"";
            writeHeader(var0, "Content-Type", var10);
            var0.write("\r\n");
            writeBoundary(var0, var5, (boolean)0);
         }

         writeHeader(var0, "Content-Type", "text/plain; charset=utf-8");
         writeHeader(var0, "Content-Transfer-Encoding", "base64");
         var0.write("\r\n");
         byte[] var11 = var3.getBytes("UTF-8");
         var0.flush();
         byte[] var12 = Base64.encode(var11, 4);
         var1.write(var12);
         if(var6) {
            writeBoundary(var0, var5, (boolean)0);
            writeHeader(var0, "Content-Type", "text/html; charset=utf-8");
            writeHeader(var0, "Content-Transfer-Encoding", "base64");
            var0.write("\r\n");
            byte[] var13 = var4.getBytes("UTF-8");
            var0.flush();
            byte[] var14 = Base64.encode(var13, 4);
            var1.write(var14);
            writeBoundary(var0, var5, (boolean)1);
         }
      }
   }

   public static void writeTo(Context param0, long param1, OutputStream param3, boolean param4, boolean param5, boolean param6) throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }
}
