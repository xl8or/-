package org.codehaus.jackson.node;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.BaseJsonNode;
import org.codehaus.jackson.node.MissingNode;

public abstract class ValueNode extends BaseJsonNode {

   protected ValueNode() {}

   public boolean isValueNode() {
      return true;
   }

   public JsonNode path(int var1) {
      return MissingNode.getInstance();
   }

   public JsonNode path(String var1) {
      return MissingNode.getInstance();
   }

   public String toString() {
      return this.getValueAsText();
   }
}
