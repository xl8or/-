package com.kenai.jbosh;

import com.kenai.jbosh.AbstractAttr;

final class AttrSessionID extends AbstractAttr<String> {

   private AttrSessionID(String var1) {
      super(var1);
   }

   static AttrSessionID createFromString(String var0) {
      return new AttrSessionID(var0);
   }
}
