package org.codehaus.jackson.node;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.ValueNode;
import org.codehaus.jackson.util.CharTypes;

public final class TextNode extends ValueNode {

   static final TextNode EMPTY_STRING_NODE = new TextNode("");
   final String _value;


   public TextNode(String var1) {
      this._value = var1;
   }

   protected static void appendQuoted(StringBuilder var0, String var1) {
      StringBuilder var2 = var0.append('\"');
      CharTypes.appendQuoted(var0, var1);
      StringBuilder var3 = var0.append('\"');
   }

   public static TextNode valueOf(String var0) {
      TextNode var1;
      if(var0 == null) {
         var1 = null;
      } else if(var0.length() == 0) {
         var1 = EMPTY_STRING_NODE;
      } else {
         var1 = new TextNode(var0);
      }

      return var1;
   }

   public boolean equals(Object var1) {
      byte var2;
      if(var1 == this) {
         var2 = 1;
      } else if(var1 == null) {
         var2 = 0;
      } else {
         Class var3 = var1.getClass();
         Class var4 = this.getClass();
         if(var3 != var4) {
            var2 = 0;
         } else {
            String var5 = ((TextNode)var1)._value;
            String var6 = this._value;
            var2 = var5.equals(var6);
         }
      }

      return (boolean)var2;
   }

   public String getTextValue() {
      return this._value;
   }

   public String getValueAsText() {
      return this._value;
   }

   public int hashCode() {
      return this._value.hashCode();
   }

   public boolean isTextual() {
      return true;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      if(this._value == null) {
         var1.writeNull();
      } else {
         String var3 = this._value;
         var1.writeString(var3);
      }
   }

   public String toString() {
      int var1 = this._value.length();
      int var2 = var1 + 2;
      int var3 = (var1 >> 4) + var2;
      StringBuilder var4 = new StringBuilder(var3);
      String var5 = this._value;
      appendQuoted(var4, var5);
      return var4.toString();
   }
}
