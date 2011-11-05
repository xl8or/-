package com.htc.android.mail;

import android.content.Context;
import android.os.Handler;
import com.htc.android.mail.AbsRequestController;
import com.htc.android.mail.Account;
import com.htc.android.mail.ByteString;
import com.htc.android.mail.Mail;
import com.htc.android.mail.ll;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Imap4PartParser {

   public static final int BODY = 1;
   public static final int CC = 8;
   public static final int DATE = 6;
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   public static final int FROM = 5;
   public static final int HEADER = 0;
   public static final int OTHER = 9;
   public static final int PART = 3;
   public static final int SUBJECT = 4;
   private static final String TAG = "Imap4PartParser";
   public static final int TO = 7;
   public static final int UID = 2;
   private static final Pattern sFromP = Pattern.compile("^(.*) <(.*)>.*$");
   private ByteString body;
   private byte[] bodyArray;
   private String bodyContent;
   private StringBuilder bodyContentBuilder;
   private int bodySize;
   private boolean continueTo;
   private Long dateValue;
   private int end;
   private String header_cc;
   private String header_date;
   private String header_from;
   private String header_fromEmail;
   private String header_fromName;
   private String header_subject;
   private String header_to;
   private String mCharset;
   private Context mContext;
   private String mEncode;
   private int start;
   private String thread_index;
   private String thread_topic;
   private byte[] tmpBodyContent;
   private String tmpFilename;
   private String uid;


   public Imap4PartParser(Context param1, Account param2, BufferedInputStream param3, String param4, String param5, int param6, boolean param7, String param8, String param9, String param10, AbsRequestController param11, WeakReference<Handler> param12) {
      // $FF: Couldn't be decompiled
   }

   public Imap4PartParser(Context param1, Account param2, ArrayList<ByteString> param3, String param4, String param5, int param6, boolean param7, String param8, String param9, AbsRequestController param10, WeakReference<Handler> param11) {
      // $FF: Couldn't be decompiled
   }

   private String linesToString(ArrayList<ByteString> var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;

      while(true) {
         int var4 = var1.size();
         if(var3 >= var4) {
            return var2.toString();
         }

         int var5 = var1.size() - 1;
         if(var3 == var5) {
            String var6 = ((ByteString)var1.get(var3)).toString().trim();
            if(var6.endsWith(")") || var6.startsWith(")") && var6.length() == 1) {
               String var7 = var6.replace(")", "");
               var2.append(var7);
            }
         } else {
            Object var9 = var1.get(var3);
            var2.append(var9);
         }

         ++var3;
      }
   }

   private boolean processDownloadAttachment(String param1, BufferedInputStream param2, OutputStream param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private ByteString readLine(BufferedInputStream var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      ll.i("Imap4PartParser", "enter readLine");

      int var3;
      while(true) {
         var3 = var1.read();
         if(var3 == -1) {
            break;
         }

         if((char)var3 == 13) {
            var2.write(var3);
         } else {
            if((char)var3 == 10) {
               var2.write(var3);
               break;
            }

            var2.write(var3);
         }
      }

      ByteString var4;
      if(var3 == -1) {
         var4 = null;
      } else {
         byte[] var5 = var2.toByteArray();
         var4 = new ByteString(var5);
      }

      return var4;
   }

   private final boolean saveFileWithPath(String var1, byte[] var2) {
      boolean var4;
      try {
         FileOutputStream var3 = new FileOutputStream(var1, (boolean)1);
         var3.write(var2);
         var3.close();
      } catch (IOException var5) {
         var5.printStackTrace();
         var4 = false;
         return var4;
      }

      var4 = true;
      return var4;
   }

   public final byte[] getBinary() {
      return this.bodyArray;
   }

   public final String getBody() {
      return this.bodyContent;
   }

   public final int getBodySize() {
      return this.bodySize;
   }

   public final String getCc() {
      return this.header_cc;
   }

   public final long getDate() {
      return this.dateValue.longValue();
   }

   public final String getFilename() {
      return this.tmpFilename;
   }

   public final String getFromEmail() {
      return this.header_fromEmail;
   }

   public final String getFromName() {
      return this.header_fromName;
   }

   public final String getSubject() {
      return this.header_subject;
   }

   public final String getThreadIndex() {
      return this.thread_index;
   }

   public final String getThreadTopic() {
      return this.thread_topic;
   }

   public final String getTo() {
      return this.header_to;
   }
}
