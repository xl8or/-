package org.apache.http.entity.mime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.message.BasicHeader;

public class MultipartEntity implements HttpEntity {

   private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
   private final org.apache.http.Header contentType;
   private volatile boolean dirty;
   private long length;
   private final HttpMultipart multipart;


   public MultipartEntity() {
      HttpMultipartMode var1 = HttpMultipartMode.STRICT;
      this(var1, (String)null, (Charset)null);
   }

   public MultipartEntity(HttpMultipartMode var1) {
      this(var1, (String)null, (Charset)null);
   }

   public MultipartEntity(HttpMultipartMode var1, String var2, Charset var3) {
      if(var2 == null) {
         var2 = this.generateBoundary();
      }

      if(var1 == null) {
         var1 = HttpMultipartMode.STRICT;
      }

      HttpMultipart var4 = new HttpMultipart("form-data", var3, var2, var1);
      this.multipart = var4;
      String var5 = this.generateContentType(var2, var3);
      BasicHeader var6 = new BasicHeader("Content-Type", var5);
      this.contentType = var6;
      this.dirty = (boolean)1;
   }

   public void addPart(String var1, ContentBody var2) {
      FormBodyPart var3 = new FormBodyPart(var1, var2);
      this.addPart(var3);
   }

   public void addPart(FormBodyPart var1) {
      this.multipart.addBodyPart(var1);
      this.dirty = (boolean)1;
   }

   public void consumeContent() throws IOException, UnsupportedOperationException {
      if(this.isStreaming()) {
         throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
      }
   }

   protected String generateBoundary() {
      StringBuilder var1 = new StringBuilder();
      Random var2 = new Random();
      int var3 = var2.nextInt(11) + 30;

      for(int var4 = 0; var4 < var3; ++var4) {
         char[] var5 = MULTIPART_CHARS;
         int var6 = MULTIPART_CHARS.length;
         int var7 = var2.nextInt(var6);
         char var8 = var5[var7];
         var1.append(var8);
      }

      return var1.toString();
   }

   protected String generateContentType(String var1, Charset var2) {
      StringBuilder var3 = new StringBuilder();
      StringBuilder var4 = var3.append("multipart/form-data; boundary=");
      var3.append(var1);
      if(var2 != null) {
         StringBuilder var6 = var3.append("; charset=");
         String var7 = var2.name();
         var3.append(var7);
      }

      return var3.toString();
   }

   public InputStream getContent() throws IOException, UnsupportedOperationException {
      throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
   }

   public org.apache.http.Header getContentEncoding() {
      return null;
   }

   public long getContentLength() {
      if(this.dirty) {
         long var1 = this.multipart.getTotalLength();
         this.length = var1;
         this.dirty = (boolean)0;
      }

      return this.length;
   }

   public org.apache.http.Header getContentType() {
      return this.contentType;
   }

   public boolean isChunked() {
      boolean var1;
      if(!this.isRepeatable()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isRepeatable() {
      Iterator var1 = this.multipart.getBodyParts().iterator();

      boolean var2;
      while(true) {
         if(var1.hasNext()) {
            if(((FormBodyPart)var1.next()).getBody().getContentLength() >= 0L) {
               continue;
            }

            var2 = false;
            break;
         }

         var2 = true;
         break;
      }

      return var2;
   }

   public boolean isStreaming() {
      boolean var1;
      if(!this.isRepeatable()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void writeTo(OutputStream var1) throws IOException {
      this.multipart.writeTo(var1);
   }
}
