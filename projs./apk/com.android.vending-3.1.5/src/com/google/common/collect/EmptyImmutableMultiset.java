package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;

@GwtCompatible(
   serializable = true
)
final class EmptyImmutableMultiset extends ImmutableMultiset<Object> {

   static final EmptyImmutableMultiset INSTANCE = new EmptyImmutableMultiset();
   private static final long serialVersionUID;


   private EmptyImmutableMultiset() {
      ImmutableMap var1 = ImmutableMap.of();
      super(var1, 0);
   }

   Object readResolve() {
      return INSTANCE;
   }
}
