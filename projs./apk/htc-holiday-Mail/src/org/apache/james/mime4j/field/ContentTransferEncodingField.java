package org.apache.james.mime4j.field;

import org.apache.james.mime4j.field.Field;
import org.apache.james.mime4j.field.FieldParser;

public class ContentTransferEncodingField extends Field {

   public static final String ENC_7BIT = "7bit";
   public static final String ENC_8BIT = "8bit";
   public static final String ENC_BASE64 = "base64";
   public static final String ENC_BINARY = "binary";
   public static final String ENC_QUOTED_PRINTABLE = "quoted-printable";
   private String encoding;


   protected ContentTransferEncodingField(String var1, String var2, String var3, String var4) {
      super(var1, var2, var3);
      this.encoding = var4;
   }

   public static String getEncoding(ContentTransferEncodingField var0) {
      String var1;
      if(var0 != null && var0.getEncoding().length() != 0) {
         var1 = var0.getEncoding();
      } else {
         var1 = "7bit";
      }

      return var1;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public static class Parser implements FieldParser {

      public Parser() {}

      public Field parse(String var1, String var2, String var3) {
         String var4 = var2.trim().toLowerCase();
         return new ContentTransferEncodingField(var1, var2, var3, var4);
      }
   }
}
