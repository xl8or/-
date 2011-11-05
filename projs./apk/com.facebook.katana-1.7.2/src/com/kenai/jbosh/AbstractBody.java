package com.kenai.jbosh;

import com.kenai.jbosh.BodyQName;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public abstract class AbstractBody {

   AbstractBody() {}

   static BodyQName getBodyQName() {
      return BodyQName.createBOSH("body");
   }

   public final String getAttribute(BodyQName var1) {
      return (String)this.getAttributes().get(var1);
   }

   public final Set<BodyQName> getAttributeNames() {
      return Collections.unmodifiableSet(this.getAttributes().keySet());
   }

   public abstract Map<BodyQName, String> getAttributes();

   public abstract String toXML();
}
