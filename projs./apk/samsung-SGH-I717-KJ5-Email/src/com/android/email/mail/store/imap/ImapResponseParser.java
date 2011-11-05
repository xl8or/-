package com.android.email.mail.store.imap;

import android.util.Log;
import com.android.email.Email;
import com.android.email.FixedLengthInputStream;
import com.android.email.PeekableInputStream;
import com.android.email.mail.MessagingException;
import com.android.email.mail.store.imap.ImapElement;
import com.android.email.mail.store.imap.ImapList;
import com.android.email.mail.store.imap.ImapMemoryLiteral;
import com.android.email.mail.store.imap.ImapResponse;
import com.android.email.mail.store.imap.ImapSimpleString;
import com.android.email.mail.store.imap.ImapString;
import com.android.email.mail.store.imap.ImapTempFileLiteral;
import com.android.email.mail.transport.DiscourseLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ImapResponseParser {

   private static final boolean DEBUG_LOG_RAW_STREAM = false;
   private static final int LITERAL_KEEP_IN_MEMORY_THRESHOLD = 16777216;
   private final StringBuilder mBufferReadUntil;
   private final DiscourseLogger mDiscourseLogger;
   private final PeekableInputStream mIn;
   private final int mLiteralKeepInMemoryThreshold;
   private final StringBuilder mParseBareString;
   private final ArrayList<ImapResponse> mResponsesToDestroy;


   public ImapResponseParser(InputStream var1, DiscourseLogger var2) {
      this(var1, var2, 16777216);
   }

   ImapResponseParser(InputStream var1, DiscourseLogger var2, int var3) {
      StringBuilder var4 = new StringBuilder();
      this.mBufferReadUntil = var4;
      StringBuilder var5 = new StringBuilder();
      this.mParseBareString = var5;
      ArrayList var6 = new ArrayList();
      this.mResponsesToDestroy = var6;
      PeekableInputStream var7 = new PeekableInputStream(var1);
      this.mIn = var7;
      this.mDiscourseLogger = var2;
      this.mLiteralKeepInMemoryThreshold = var3;
   }

   private static IOException newEOSException() {
      if(Email.DEBUG) {
         int var0 = Log.d("Email", "End of stream reached");
      }

      return new IOException("End of stream reached");
   }

   private void onParseError(Exception var1) {
      for(int var2 = 0; var2 < 4; ++var2) {
         int var3;
         try {
            var3 = this.readByte();
         } catch (IOException var10) {
            break;
         }

         if(var3 == -1 || var3 == 10) {
            break;
         }
      }

      StringBuilder var5 = (new StringBuilder()).append("Exception detected: ");
      String var6 = var1.getMessage();
      String var7 = var5.append(var6).toString();
      int var8 = Log.w("Email", var7);
      this.mDiscourseLogger.logLastDiscourse();
   }

   private ImapString parseBareString() throws IOException, MessagingException {
      this.mParseBareString.setLength(0);

      while(true) {
         int var1 = this.peek();
         if(var1 == 40 || var1 == 41 || var1 == 123 || var1 == 32 || var1 == 93 || var1 == 37 || var1 == 34 || var1 >= 0 && var1 <= 31 || var1 == 127) {
            if(this.mParseBareString.length() == 0) {
               throw new MessagingException("Expected string, none found.");
            } else {
               String var2 = this.mParseBareString.toString();
               Object var3;
               if("NIL".equalsIgnoreCase(var2)) {
                  var3 = ImapString.EMPTY;
               } else {
                  var3 = new ImapSimpleString(var2);
               }

               return (ImapString)var3;
            }
         }

         if(var1 == 91) {
            StringBuilder var4 = this.mParseBareString;
            char var5 = (char)this.readByte();
            var4.append(var5);
            StringBuilder var7 = this.mParseBareString;
            String var8 = this.readUntil(']');
            var7.append(var8);
            StringBuilder var10 = this.mParseBareString.append(']');
         } else {
            StringBuilder var11 = this.mParseBareString;
            char var12 = (char)this.readByte();
            var11.append(var12);
         }
      }
   }

   private ImapElement parseElement() throws IOException, MessagingException {
      Object var1;
      switch(this.peek()) {
      case 10:
         int var5 = this.readByte();
         var1 = null;
         break;
      case 13:
         int var4 = this.readByte();
         this.expect('\n');
         var1 = null;
         break;
      case 34:
         int var2 = this.readByte();
         String var3 = this.readUntil('\"');
         var1 = new ImapSimpleString(var3);
         break;
      case 40:
         var1 = this.parseList('(', ')');
         break;
      case 91:
         var1 = this.parseList('[', ']');
         break;
      case 123:
         var1 = this.parseLiteral();
         break;
      default:
         var1 = this.parseBareString();
      }

      return (ImapElement)var1;
   }

   private void parseElements(ImapList var1, char var2) throws IOException, MessagingException {
      while(true) {
         int var3 = this.peek();
         if(var3 == var2) {
            return;
         }

         if(var3 != 32) {
            ImapElement var4 = this.parseElement();
            if(var4 == null) {
               return;
            }

            var1.add(var4);
         } else {
            int var5 = this.readByte();
         }
      }
   }

   private ImapList parseList(char var1, char var2) throws IOException, MessagingException {
      this.expect(var1);
      ImapList var3 = new ImapList();
      this.parseElements(var3, var2);
      this.expect(var2);
      return var3;
   }

   private ImapString parseLiteral() throws IOException, MessagingException {
      this.expect('{');

      int var1;
      try {
         var1 = Integer.parseInt(this.readUntil('}'));
      } catch (NumberFormatException var8) {
         throw new MessagingException("Invalid length in literal");
      }

      Object var3;
      if(var1 < 1) {
         var3 = null;
      } else {
         this.expect('\r');
         this.expect('\n');
         PeekableInputStream var5 = this.mIn;
         FixedLengthInputStream var6 = new FixedLengthInputStream(var5, var1);
         int var7 = this.mLiteralKeepInMemoryThreshold;
         if(var1 > var7) {
            var3 = new ImapTempFileLiteral(var6);
         } else {
            var3 = new ImapMemoryLiteral(var6);
         }
      }

      return (ImapString)var3;
   }

   private ImapResponse parseResponse() throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }

   private int peek() throws IOException {
      int var1 = this.mIn.peek();
      if(var1 == -1) {
         throw newEOSException();
      } else {
         return var1;
      }
   }

   private int readByte() throws IOException {
      int var1 = this.mIn.read();
      if(var1 == -1) {
         throw newEOSException();
      } else {
         this.mDiscourseLogger.addReceivedByte(var1);
         return var1;
      }
   }

   public void destroyResponses() {
      Iterator var1 = this.mResponsesToDestroy.iterator();

      while(var1.hasNext()) {
         ((ImapResponse)var1.next()).destroy();
      }

      this.mResponsesToDestroy.clear();
   }

   void expect(char var1) throws IOException {
      int var2 = this.readByte();
      if(var1 != var2) {
         Object[] var3 = new Object[4];
         Integer var4 = Integer.valueOf(var1);
         var3[0] = var4;
         Character var5 = Character.valueOf(var1);
         var3[1] = var5;
         Integer var6 = Integer.valueOf(var2);
         var3[2] = var6;
         Character var7 = Character.valueOf((char)var2);
         var3[3] = var7;
         String var8 = String.format("Expected %04x (%c) but got %04x (%c)", var3);
         throw new IOException(var8);
      }
   }

   public ImapResponse readResponse() throws IOException, MessagingException {
      ImapResponse var1;
      try {
         var1 = this.parseResponse();
         if(Email.DEBUG) {
            StringBuilder var2 = (new StringBuilder()).append("<<< ");
            String var3 = var1.toString();
            String var4 = var2.append(var3).toString();
            int var5 = Log.d("Email", var4);
         }
      } catch (RuntimeException var10) {
         this.onParseError(var10);
         throw var10;
      } catch (IOException var11) {
         this.onParseError(var11);
         throw var11;
      }

      if(var1.is(0, "BYE")) {
         int var6 = Log.w("Email", "Received BYE");
         var1.destroy();
         throw new ImapResponseParser.ByeException();
      } else {
         this.mResponsesToDestroy.add(var1);
         return var1;
      }
   }

   String readUntil(char var1) throws IOException {
      this.mBufferReadUntil.setLength(0);

      while(true) {
         int var2 = this.readByte();
         if(var2 == var1) {
            return this.mBufferReadUntil.toString();
         }

         StringBuilder var3 = this.mBufferReadUntil;
         char var4 = (char)var2;
         var3.append(var4);
      }
   }

   String readUntilEol() throws IOException, MessagingException {
      String var1 = this.readUntil('\r');
      this.expect('\n');
      return var1;
   }

   public static class ByeException extends IOException {

      public static final String MESSAGE = "Received BYE";


      public ByeException() {
         super("Received BYE");
      }
   }
}
