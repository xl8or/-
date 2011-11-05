package com.android.exchange.adapter;

import android.content.ContentValues;
import android.util.Log;
import com.android.exchange.Eas;
import com.android.exchange.adapter.Tags;
import com.android.exchange.utility.FileLogger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

public class Serializer {

   private static final int NOT_PENDING = 255;
   private static final String TAG = "Serializer";
   ByteArrayOutputStream buf;
   int depth;
   private boolean logging;
   String name;
   String[] nameStack;
   ByteArrayOutputStream out;
   String pending;
   int pendingTag;
   private int tagPage;
   Hashtable<String, Object> tagTable;


   public Serializer() {
      boolean var1 = Eas.SERIALIZER_LOG;
      this((boolean)1, var1);
   }

   public Serializer(boolean var1) {
      this.logging = (boolean)0;
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      this.out = var2;
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      this.buf = var3;
      this.pendingTag = -1;
      String[] var4 = new String[20];
      this.nameStack = var4;
      Hashtable var5 = new Hashtable();
      this.tagTable = var5;
      if(var1) {
         try {
            this.startDocument();
         } catch (IOException var7) {
            ;
         }
      } else {
         this.out.write(0);
      }
   }

   public Serializer(boolean var1, boolean var2) {
      this((boolean)1);
      this.logging = var2;
   }

   public void checkPendingTag(boolean var1) throws IOException {
      if(this.pendingTag != -1) {
         int var2 = this.pendingTag >> 6;
         int var3 = this.pendingTag & 63;
         int var4 = this.tagPage;
         if(var2 != var4) {
            this.tagPage = var2;
            this.buf.write(0);
            this.buf.write(var2);
         }

         ByteArrayOutputStream var5 = this.buf;
         int var6;
         if(var1) {
            var6 = var3;
         } else {
            var6 = var3 | 64;
         }

         var5.write(var6);
         if(this.logging) {
            String[] var7 = Tags.pages[var2];
            int var8 = var3 - 5;
            String var9 = var7[var8];
            String[] var10 = this.nameStack;
            int var11 = this.depth;
            var10[var11] = var9;
            String var12 = "<" + var9 + '>';
            this.log(var12);
         }

         this.pendingTag = -1;
      }
   }

   public Serializer data(int var1, String var2) throws IOException {
      if(var2 == null) {
         String var3 = "Writing null data for tag: " + var1;
         int var4 = Log.e("Serializer", var3);
      }

      this.start(var1);
      this.text(var2);
      Serializer var7 = this.end();
      return this;
   }

   public void done() throws IOException {
      if(this.depth != 0) {
         throw new IOException("Done received with unclosed tags");
      } else {
         ByteArrayOutputStream var1 = this.out;
         this.writeInteger(var1, 0);
         ByteArrayOutputStream var2 = this.out;
         byte[] var3 = this.buf.toByteArray();
         var2.write(var3);
         this.out.flush();
      }
   }

   public Serializer end() throws IOException {
      if(this.pendingTag >= 0) {
         this.checkPendingTag((boolean)1);
      } else {
         this.buf.write(1);
         if(this.logging) {
            StringBuilder var2 = (new StringBuilder()).append("</");
            String[] var3 = this.nameStack;
            int var4 = this.depth;
            String var5 = var3[var4];
            String var6 = var2.append(var5).append('>').toString();
            this.log(var6);
         }
      }

      int var1 = this.depth - 1;
      this.depth = var1;
      return this;
   }

   void log(String var1) {
      int var2 = var1.indexOf(10);
      if(var2 > 0) {
         var1 = var1.substring(0, var2);
      }

      int var3 = Log.v("Serializer", var1);
      if(Eas.FILE_LOG) {
         FileLogger.log("Serializer", var1);
      }
   }

   public Serializer start(int var1) throws IOException {
      this.checkPendingTag((boolean)0);
      this.pendingTag = var1;
      int var2 = this.depth + 1;
      this.depth = var2;
      return this;
   }

   public void startDocument() throws IOException {
      this.out.write(3);
      this.out.write(1);
      this.out.write(106);
   }

   public Serializer tag(int var1) throws IOException {
      this.start(var1);
      Serializer var3 = this.end();
      return this;
   }

   public Serializer text(String var1) throws IOException {
      if(var1 == null) {
         StringBuilder var2 = (new StringBuilder()).append("Writing null text for pending tag: ");
         int var3 = this.pendingTag;
         String var4 = var2.append(var3).toString();
         int var5 = Log.e("Serializer", var4);
      } else {
         this.checkPendingTag((boolean)0);
         this.buf.write(3);
         ByteArrayOutputStream var6 = this.buf;
         this.writeLiteralString(var6, var1);
         if(this.logging) {
            this.log(var1);
         }
      }

      return this;
   }

   public byte[] toByteArray() {
      return this.out.toByteArray();
   }

   public String toString() {
      return this.out.toString();
   }

   void writeInteger(OutputStream var1, int var2) throws IOException {
      byte[] var3 = new byte[5];
      int var4 = 0;

      while(true) {
         int var5 = var4 + 1;
         byte var6 = (byte)(var2 & 127);
         var3[var4] = var6;
         var2 >>= 7;
         if(var2 == 0) {
            int var7 = var5;

            while(var7 > 1) {
               var7 += -1;
               int var8 = var3[var7] | 128;
               var1.write(var8);
            }

            byte var9 = var3[0];
            var1.write(var9);
            if(!this.logging) {
               return;
            } else {
               String var10 = Integer.toString(var2);
               this.log(var10);
               return;
            }
         }

         var4 = var5;
      }
   }

   void writeLiteralString(OutputStream var1, String var2) throws IOException {
      byte[] var3 = var2.getBytes("UTF-8");
      var1.write(var3);
      var1.write(0);
   }

   void writeStringValue(ContentValues var1, String var2, int var3) throws IOException {
      String var4 = var1.getAsString(var2);
      if(var4 != null) {
         if(var4.length() > 0) {
            this.data(var3, var4);
         }
      }
   }
}
