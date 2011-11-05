package org.apache.james.mime4j.field;

import org.apache.james.mime4j.decoder.DecoderUtil;
import org.apache.james.mime4j.field.Field;
import org.apache.james.mime4j.field.FieldParser;

public class UnstructuredField extends Field {

   private String value;


   protected UnstructuredField(String var1, String var2, String var3, String var4) {
      super(var1, var2, var3);
      this.value = var4;
   }

   public String getValue() {
      return this.value;
   }

   public static class Parser implements FieldParser {

      public Parser() {}

      public Field parse(String var1, String var2, String var3) {
         String var4 = DecoderUtil.decodeEncodedWords(var2);
         return new UnstructuredField(var1, var2, var3, var4);
      }
   }
}
