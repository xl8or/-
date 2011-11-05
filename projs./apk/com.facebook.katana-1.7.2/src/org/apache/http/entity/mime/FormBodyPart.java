package org.apache.http.entity.mime;

import org.apache.http.entity.mime.Header;
import org.apache.http.entity.mime.MinimalField;
import org.apache.http.entity.mime.content.ContentBody;

public class FormBodyPart {

   private final ContentBody body;
   private final Header header;
   private final String name;


   public FormBodyPart(String var1, ContentBody var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("Name may not be null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("Body may not be null");
      } else {
         this.name = var1;
         this.body = var2;
         Header var3 = new Header();
         this.header = var3;
         this.generateContentDisp(var2);
         this.generateContentType(var2);
         this.generateTransferEncoding(var2);
      }
   }

   public void addField(String var1, String var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("Field name may not be null");
      } else {
         Header var3 = this.header;
         MinimalField var4 = new MinimalField(var1, var2);
         var3.addField(var4);
      }
   }

   protected void generateContentDisp(ContentBody var1) {
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = var2.append("form-data; name=\"");
      String var4 = this.getName();
      var2.append(var4);
      StringBuilder var6 = var2.append("\"");
      if(var1.getFilename() != null) {
         StringBuilder var7 = var2.append("; filename=\"");
         String var8 = var1.getFilename();
         var2.append(var8);
         StringBuilder var10 = var2.append("\"");
      }

      String var11 = var2.toString();
      this.addField("Content-Disposition", var11);
   }

   protected void generateContentType(ContentBody var1) {
      StringBuilder var2 = new StringBuilder();
      String var3 = var1.getMimeType();
      var2.append(var3);
      if(var1.getCharset() != null) {
         StringBuilder var5 = var2.append("; charset=");
         String var6 = var1.getCharset();
         var2.append(var6);
      }

      String var8 = var2.toString();
      this.addField("Content-Type", var8);
   }

   protected void generateTransferEncoding(ContentBody var1) {
      String var2 = var1.getTransferEncoding();
      this.addField("Content-Transfer-Encoding", var2);
   }

   public ContentBody getBody() {
      return this.body;
   }

   public Header getHeader() {
      return this.header;
   }

   public String getName() {
      return this.name;
   }
}
