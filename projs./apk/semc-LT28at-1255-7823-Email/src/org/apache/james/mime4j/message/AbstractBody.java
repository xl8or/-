package org.apache.james.mime4j.message;

import org.apache.james.mime4j.message.Body;
import org.apache.james.mime4j.message.Entity;

public abstract class AbstractBody implements Body {

   private Entity parent = null;


   public AbstractBody() {}

   public Entity getParent() {
      return this.parent;
   }

   public void setParent(Entity var1) {
      this.parent = var1;
   }
}
