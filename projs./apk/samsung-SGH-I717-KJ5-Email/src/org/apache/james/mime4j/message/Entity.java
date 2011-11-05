package org.apache.james.mime4j.message;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.james.mime4j.field.ContentTransferEncodingField;
import org.apache.james.mime4j.field.ContentTypeField;
import org.apache.james.mime4j.message.Body;
import org.apache.james.mime4j.message.Header;

public abstract class Entity {

   private Body body = null;
   private Header header = null;
   private Entity parent = null;


   public Entity() {}

   public Body getBody() {
      return this.body;
   }

   public String getCharset() {
      return ContentTypeField.getCharset((ContentTypeField)this.getHeader().getField("Content-Type"));
   }

   public String getContentTransferEncoding() {
      return ContentTransferEncodingField.getEncoding((ContentTransferEncodingField)this.getHeader().getField("Content-Transfer-Encoding"));
   }

   public Header getHeader() {
      return this.header;
   }

   public String getMimeType() {
      ContentTypeField var1 = (ContentTypeField)this.getHeader().getField("Content-Type");
      ContentTypeField var2;
      if(this.getParent() != null) {
         var2 = (ContentTypeField)this.getParent().getHeader().getField("Content-Type");
      } else {
         var2 = null;
      }

      return ContentTypeField.getMimeType(var1, var2);
   }

   public Entity getParent() {
      return this.parent;
   }

   public boolean isMimeType(String var1) {
      return this.getMimeType().equalsIgnoreCase(var1);
   }

   public boolean isMultipart() {
      ContentTypeField var1 = (ContentTypeField)this.getHeader().getField("Content-Type");
      boolean var2;
      if(var1 != null && var1.getBoundary() != null && this.getMimeType().startsWith("multipart/")) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void setBody(Body var1) {
      this.body = var1;
      var1.setParent(this);
   }

   public void setHeader(Header var1) {
      this.header = var1;
   }

   public void setParent(Entity var1) {
      this.parent = var1;
   }

   public abstract void writeTo(OutputStream var1) throws IOException;
}
