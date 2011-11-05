package com.google.common.base;

import com.google.common.base.FinalizableReference;
import com.google.common.base.FinalizableReferenceQueue;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public abstract class FinalizablePhantomReference<T extends Object> extends PhantomReference<T> implements FinalizableReference {

   protected FinalizablePhantomReference(T var1, FinalizableReferenceQueue var2) {
      ReferenceQueue var3 = var2.queue;
      super(var1, var3);
      var2.cleanUp();
   }
}
