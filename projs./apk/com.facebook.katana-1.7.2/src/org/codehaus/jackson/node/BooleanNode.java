package org.codehaus.jackson.node;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.ValueNode;

public final class BooleanNode extends ValueNode {

   public static final BooleanNode FALSE = new BooleanNode();
   public static final BooleanNode TRUE = new BooleanNode();


   private BooleanNode() {}

   public static BooleanNode getFalse() {
      return FALSE;
   }

   public static BooleanNode getTrue() {
      return TRUE;
   }

   public static BooleanNode valueOf(boolean var0) {
      BooleanNode var1;
      if(var0) {
         var1 = TRUE;
      } else {
         var1 = FALSE;
      }

      return var1;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean getBooleanValue() {
      BooleanNode var1 = TRUE;
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String getValueAsText() {
      BooleanNode var1 = TRUE;
      String var2;
      if(this == var1) {
         var2 = "true";
      } else {
         var2 = "false";
      }

      return var2;
   }

   public boolean isBoolean() {
      return true;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      BooleanNode var3 = TRUE;
      byte var4;
      if(this == var3) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      var1.writeBoolean((boolean)var4);
   }
}
