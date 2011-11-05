package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

public class PreconditionFailedException extends GDataException {

   public PreconditionFailedException() {}

   public PreconditionFailedException(String var1) {
      super(var1);
   }

   public PreconditionFailedException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
