package com.kenai.jbosh;

import com.kenai.jbosh.AbstractAttr;
import com.kenai.jbosh.BOSHException;

final class AttrAck extends AbstractAttr<String> {

   private AttrAck(String var1) throws BOSHException {
      super(var1);
   }

   static AttrAck createFromString(String var0) throws BOSHException {
      AttrAck var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = new AttrAck(var0);
      }

      return var1;
   }
}
