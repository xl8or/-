package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;

@GwtCompatible(
   serializable = true
)
class EmptyImmutableSetMultimap extends ImmutableSetMultimap<Object, Object> {

   static final EmptyImmutableSetMultimap INSTANCE = new EmptyImmutableSetMultimap();
   private static final long serialVersionUID;


   private EmptyImmutableSetMultimap() {
      ImmutableMap var1 = ImmutableMap.of();
      super(var1, 0);
   }

   private Object readResolve() {
      return INSTANCE;
   }
}
