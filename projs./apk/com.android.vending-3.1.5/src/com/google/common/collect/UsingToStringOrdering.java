package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Ordering;
import java.io.Serializable;

@GwtCompatible(
   serializable = true
)
final class UsingToStringOrdering extends Ordering<Object> implements Serializable {

   static final UsingToStringOrdering INSTANCE = new UsingToStringOrdering();
   private static final long serialVersionUID;


   private UsingToStringOrdering() {}

   private Object readResolve() {
      return INSTANCE;
   }

   public int compare(Object var1, Object var2) {
      String var3 = var1.toString();
      String var4 = var2.toString();
      return var3.compareTo(var4);
   }

   public String toString() {
      return "Ordering.usingToString()";
   }
}
