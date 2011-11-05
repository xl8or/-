package com.android.exchange.adapter;

import com.android.exchange.adapter.Parser;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractUtiltyParser extends Parser {

   private boolean mProvisioningRequired = 0;


   public AbstractUtiltyParser(InputStream var1) throws IOException {
      super(var1);
   }

   public boolean isDeviceAccessDenied(int var1) {
      boolean var2 = false;
      if(var1 == 129) {
         var2 = true;
      }

      return var2;
   }

   public boolean isProvisioningStatus(int var1) {
      switch(var1) {
      case 139:
      case 141:
         this.mProvisioningRequired = (boolean)1;
         break;
      case 140:
      case 142:
      case 143:
      case 144:
         this.mProvisioningRequired = (boolean)1;
      }

      return this.mProvisioningRequired;
   }
}
