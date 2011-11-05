package com.android.email.mail.internet;

import android.content.ContentResolver;
import android.net.Uri;
import com.android.email.mail.BodyPart;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Multipart;
import com.android.email.mail.Part;
import com.android.email.mail.internet.MimeUtility;
import com.android.email.mail.store.LocalStore;
import com.android.email.provider.AttachmentProvider;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailHtmlUtil {

   private static final Pattern PLAIN_TEXT_TO_ESCAPE = Pattern.compile("[<>&]| {2,}|\r?\n");
   static StringBuilder out = null;


   public EmailHtmlUtil() {}

   public static String escapeCharacterToDisplay(String var0) {
      Matcher var1 = PLAIN_TEXT_TO_ESCAPE.matcher(var0);
      if(var1.find()) {
         if(out == null) {
            out = new StringBuilder();
         }

         int var2 = 0;

         do {
            int var3 = var1.start();
            StringBuilder var4 = out;
            String var5 = var0.substring(var2, var3);
            var4.append(var5);
            var2 = var1.end();
            int var7 = var0.codePointAt(var3);
            if(var7 == 32) {
               int var8 = 1;

               for(int var9 = var2 - var3; var8 < var9; ++var8) {
                  StringBuilder var10 = out.append("&nbsp;");
               }

               StringBuilder var11 = out.append(' ');
            } else if(var7 != 13 && var7 != 10) {
               if(var7 == 60) {
                  StringBuilder var19 = out.append("&lt;");
               } else if(var7 == 62) {
                  StringBuilder var20 = out.append("&gt;");
               } else if(var7 == 38) {
                  StringBuilder var21 = out.append("&amp;");
               }
            } else {
               StringBuilder var18 = out.append("<br>");
            }
         } while(var1.find());

         StringBuilder var12 = out;
         String var13 = var0.substring(var2);
         var12.append(var13);
         var0 = out.toString();
         StringBuilder var15 = out;
         int var16 = out.length();
         var15.delete(0, var16);
      }

      return var0;
   }

   public static String resolveInlineImage(ContentResolver var0, long var1, String var3, Part var4, int var5) throws MessagingException {
      String var22;
      if(var5 < null && var3 != null) {
         String var6 = MimeUtility.unfoldAndDecode(var4.getContentType());
         String var7 = var4.getContentId();
         if(var6 != null && var6.startsWith("image/") && var7 != null && var4 instanceof LocalStore.LocalAttachmentBodyPart) {
            long var8 = ((LocalStore.LocalAttachmentBodyPart)var4).getAttachmentId();
            Uri var10 = AttachmentProvider.getAttachmentUri(var1, var8);
            Uri var11 = AttachmentProvider.resolveAttachmentIdToContentUri(var0, var10);
            String var12 = "\\s+(?i)src=\"cid(?-i):\\Q" + var7 + "\\E\"";
            String var13 = " src=\"" + var11 + "\"";
            var3 = var3.replaceAll(var12, var13);
         }

         if(var4.getBody() instanceof Multipart) {
            Multipart var14 = (Multipart)var4.getBody();
            int var23 = 0;

            while(true) {
               int var15 = var14.getCount();
               if(var23 >= var15) {
                  break;
               }

               BodyPart var16 = var14.getBodyPart(var23);
               int var17 = var5 + 1;
               var3 = resolveInlineImage(var0, var1, var3, var16, var17);
               ++var23;
            }
         }

         var22 = var3;
      } else {
         var22 = var3;
      }

      return var22;
   }
}
