package org.apache.james.mime4j.field;

import java.util.HashMap;
import java.util.Map;
import org.apache.james.mime4j.field.Field;
import org.apache.james.mime4j.field.FieldParser;
import org.apache.james.mime4j.field.UnstructuredField;

public class DelegatingFieldParser implements FieldParser {

   private FieldParser defaultParser;
   private Map parsers;


   public DelegatingFieldParser() {
      HashMap var1 = new HashMap();
      this.parsers = var1;
      UnstructuredField.Parser var2 = new UnstructuredField.Parser();
      this.defaultParser = var2;
   }

   public FieldParser getParser(String var1) {
      Map var2 = this.parsers;
      String var3 = var1.toLowerCase();
      FieldParser var4 = (FieldParser)var2.get(var3);
      FieldParser var5;
      if(var4 == null) {
         var5 = this.defaultParser;
      } else {
         var5 = var4;
      }

      return var5;
   }

   public Field parse(String var1, String var2, String var3) {
      return this.getParser(var1).parse(var1, var2, var3);
   }

   public void setFieldParser(String var1, FieldParser var2) {
      Map var3 = this.parsers;
      String var4 = var1.toLowerCase();
      var3.put(var4, var2);
   }
}
