package org.apache.http.entity.mime.content;

import org.apache.http.entity.mime.content.ContentBody;

public abstract class AbstractContentBody implements ContentBody {

   private final String mediaType;
   private final String mimeType;
   private final String subType;


   public AbstractContentBody(String var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("MIME type may not be null");
      } else {
         this.mimeType = var1;
         int var2 = var1.indexOf(47);
         if(var2 != -1) {
            String var3 = var1.substring(0, var2);
            this.mediaType = var3;
            int var4 = var2 + 1;
            String var5 = var1.substring(var4);
            this.subType = var5;
         } else {
            this.mediaType = var1;
            this.subType = null;
         }
      }
   }

   public String getMediaType() {
      return this.mediaType;
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public String getSubType() {
      return this.subType;
   }
}
