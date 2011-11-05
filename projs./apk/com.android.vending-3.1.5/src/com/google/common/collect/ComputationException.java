package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public class ComputationException extends RuntimeException {

   private static final long serialVersionUID;


   public ComputationException(Throwable var1) {
      super(var1);
   }
}
