package com.google.common.base;

import com.google.common.base.FinalizableReference;
import com.google.common.base.FinalizableReferenceQueue;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public abstract class FinalizableSoftReference<T extends Object> extends SoftReference<T> implements FinalizableReference {

   protected FinalizableSoftReference(T var1, FinalizableReferenceQueue var2) {
      ReferenceQueue var3 = var2.queue;
      super(var1, var3);
      var2.cleanUp();
   }
}
