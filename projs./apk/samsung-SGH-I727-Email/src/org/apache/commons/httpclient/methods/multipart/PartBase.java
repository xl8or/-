package org.apache.commons.httpclient.methods.multipart;

import org.apache.commons.httpclient.methods.multipart.Part;

public abstract class PartBase extends Part {

   private String charSet;
   private String contentType;
   private String name;
   private String transferEncoding;


   public PartBase(String var1, String var2, String var3, String var4) {
      if(var1 == null) {
         throw new IllegalArgumentException("Name must not be null");
      } else {
         this.name = var1;
         this.contentType = var2;
         this.charSet = var3;
         this.transferEncoding = var4;
      }
   }

   public String getCharSet() {
      return this.charSet;
   }

   public String getContentType() {
      return this.contentType;
   }

   public String getName() {
      return this.name;
   }

   public String getTransferEncoding() {
      return this.transferEncoding;
   }

   public void setCharSet(String var1) {
      this.charSet = var1;
   }

   public void setContentType(String var1) {
      this.contentType = var1;
   }

   public void setName(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Name must not be null");
      } else {
         this.name = var1;
      }
   }

   public void setTransferEncoding(String var1) {
      this.transferEncoding = var1;
   }
}
