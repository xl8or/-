package com.android.exchange.adapter;

import android.util.Log;
import com.android.email.mail.DeviceAccessException;
import com.android.exchange.adapter.AbstractUtiltyParser;
import com.android.exchange.adapter.Parser;
import java.io.IOException;
import java.io.InputStream;

public class FourteenProvisionParser extends AbstractUtiltyParser {

   public FourteenProvisionParser(InputStream var1) throws IOException {
      super(var1);
   }

   public boolean checkForProvisioning() throws IOException, DeviceAccessException {
      return this.parse();
   }

   public boolean parse() throws IOException, DeviceAccessException {
      if(this.nextTag(0) != 470) {
         throw new Parser.EasParserException();
      } else {
         boolean var2;
         while(true) {
            if(this.nextTag(0) == 3) {
               var2 = false;
               break;
            }

            if(this.tag == 460) {
               int var1 = this.getValueInt();
               if(this.isProvisioningStatus(var1)) {
                  var2 = true;
                  break;
               }

               if(this.isDeviceAccessDenied(var1)) {
                  int var3 = Log.i("FourteenProvisionParser", "FourteenProvisionParser::parse() - Received status 129, to Block device ");
                  throw new DeviceAccessException(262145, 2131166810);
               }
            }
         }

         return var2;
      }
   }
}
