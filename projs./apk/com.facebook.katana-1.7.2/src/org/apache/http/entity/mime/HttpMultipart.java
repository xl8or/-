package org.apache.http.entity.mime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.Header;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MinimalField;
import org.apache.http.util.ByteArrayBuffer;

public class HttpMultipart {

   private static final ByteArrayBuffer CR_LF = encode(MIME.DEFAULT_CHARSET, "\r\n");
   private static final ByteArrayBuffer FIELD_SEP = encode(MIME.DEFAULT_CHARSET, ": ");
   private static final ByteArrayBuffer TWO_DASHES = encode(MIME.DEFAULT_CHARSET, "--");
   private final String boundary;
   private final Charset charset;
   private final HttpMultipartMode mode;
   private final List<FormBodyPart> parts;
   private final String subType;


   public HttpMultipart(String var1, String var2) {
      this(var1, (Charset)null, var2);
   }

   public HttpMultipart(String var1, Charset var2, String var3) {
      HttpMultipartMode var4 = HttpMultipartMode.STRICT;
      this(var1, var2, var3, var4);
   }

   public HttpMultipart(String var1, Charset var2, String var3, HttpMultipartMode var4) {
      if(var1 == null) {
         throw new IllegalArgumentException("Multipart subtype may not be null");
      } else if(var3 == null) {
         throw new IllegalArgumentException("Multipart boundary may not be null");
      } else {
         this.subType = var1;
         Charset var5;
         if(var2 != null) {
            var5 = var2;
         } else {
            var5 = MIME.DEFAULT_CHARSET;
         }

         this.charset = var5;
         this.boundary = var3;
         ArrayList var6 = new ArrayList();
         this.parts = var6;
         this.mode = var4;
      }
   }

   private void doWriteTo(HttpMultipartMode var1, OutputStream var2, boolean var3) throws IOException {
      Charset var4 = this.charset;
      String var5 = this.getBoundary();
      ByteArrayBuffer var6 = encode(var4, var5);

      for(Iterator var7 = this.parts.iterator(); var7.hasNext(); writeBytes(CR_LF, var2)) {
         FormBodyPart var8;
         var8 = (FormBodyPart)var7.next();
         writeBytes(TWO_DASHES, var2);
         writeBytes(var6, var2);
         writeBytes(CR_LF, var2);
         Header var9 = var8.getHeader();
         int[] var10 = HttpMultipart.1.$SwitchMap$org$apache$http$entity$mime$HttpMultipartMode;
         int var11 = var1.ordinal();
         label26:
         switch(var10[var11]) {
         case 1:
            Iterator var12 = var9.iterator();

            while(true) {
               if(!var12.hasNext()) {
                  break label26;
               }

               writeField((MinimalField)var12.next(), var2);
            }
         case 2:
            MinimalField var13 = var8.getHeader().getField("Content-Disposition");
            Charset var14 = this.charset;
            writeField(var13, var14, var2);
            if(var8.getBody().getFilename() != null) {
               MinimalField var15 = var8.getHeader().getField("Content-Type");
               Charset var16 = this.charset;
               writeField(var15, var16, var2);
            }
         }

         writeBytes(CR_LF, var2);
         if(var3) {
            var8.getBody().writeTo(var2);
         }
      }

      writeBytes(TWO_DASHES, var2);
      writeBytes(var6, var2);
      writeBytes(TWO_DASHES, var2);
      writeBytes(CR_LF, var2);
   }

   private static ByteArrayBuffer encode(Charset var0, String var1) {
      CharBuffer var2 = CharBuffer.wrap(var1);
      ByteBuffer var3 = var0.encode(var2);
      int var4 = var3.remaining();
      ByteArrayBuffer var5 = new ByteArrayBuffer(var4);
      byte[] var6 = var3.array();
      int var7 = var3.position();
      int var8 = var3.remaining();
      var5.append(var6, var7, var8);
      return var5;
   }

   private static void writeBytes(String var0, OutputStream var1) throws IOException {
      writeBytes(encode(MIME.DEFAULT_CHARSET, var0), var1);
   }

   private static void writeBytes(String var0, Charset var1, OutputStream var2) throws IOException {
      writeBytes(encode(var1, var0), var2);
   }

   private static void writeBytes(ByteArrayBuffer var0, OutputStream var1) throws IOException {
      byte[] var2 = var0.buffer();
      int var3 = var0.length();
      var1.write(var2, 0, var3);
   }

   private static void writeField(MinimalField var0, OutputStream var1) throws IOException {
      writeBytes(var0.getName(), var1);
      writeBytes(FIELD_SEP, var1);
      writeBytes(var0.getBody(), var1);
      writeBytes(CR_LF, var1);
   }

   private static void writeField(MinimalField var0, Charset var1, OutputStream var2) throws IOException {
      writeBytes(var0.getName(), var1, var2);
      writeBytes(FIELD_SEP, var2);
      writeBytes(var0.getBody(), var1, var2);
      writeBytes(CR_LF, var2);
   }

   public void addBodyPart(FormBodyPart var1) {
      if(var1 != null) {
         this.parts.add(var1);
      }
   }

   public List<FormBodyPart> getBodyParts() {
      return this.parts;
   }

   public String getBoundary() {
      return this.boundary;
   }

   public Charset getCharset() {
      return this.charset;
   }

   public HttpMultipartMode getMode() {
      return this.mode;
   }

   public String getSubType() {
      return this.subType;
   }

   public long getTotalLength() {
      long var1 = 0L;
      Iterator var3 = this.parts.iterator();

      long var6;
      while(true) {
         if(var3.hasNext()) {
            long var4 = ((FormBodyPart)var3.next()).getBody().getContentLength();
            if(var4 >= 0L) {
               var1 += var4;
               continue;
            }

            var6 = 65535L;
            break;
         }

         ByteArrayOutputStream var8 = new ByteArrayOutputStream();

         int var10;
         try {
            HttpMultipartMode var9 = this.mode;
            this.doWriteTo(var9, var8, (boolean)0);
            var10 = var8.toByteArray().length;
         } catch (IOException var12) {
            var6 = 65535L;
            break;
         }

         var6 = (long)var10 + var1;
         break;
      }

      return var6;
   }

   public void writeTo(OutputStream var1) throws IOException {
      HttpMultipartMode var2 = this.mode;
      this.doWriteTo(var2, var1, (boolean)1);
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$org$apache$http$entity$mime$HttpMultipartMode = new int[HttpMultipartMode.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$org$apache$http$entity$mime$HttpMultipartMode;
            int var1 = HttpMultipartMode.STRICT.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$org$apache$http$entity$mime$HttpMultipartMode;
            int var3 = HttpMultipartMode.BROWSER_COMPATIBLE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
