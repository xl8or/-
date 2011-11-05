package org.jivesoftware.smack;

import com.kenai.jbosh.BOSHClientResponseListener;
import com.kenai.jbosh.BOSHMessageEvent;
import java.util.Collection;
import org.jivesoftware.smack.BOSHConnection;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.util.PacketParserUtils;
import org.xmlpull.v1.XmlPullParser;

public class BOSHPacketReader implements BOSHClientResponseListener {

   private BOSHConnection connection;


   public BOSHPacketReader(BOSHConnection var1) {
      this.connection = var1;
   }

   private void parseFeatures(XmlPullParser var1) throws Exception {
      boolean var2 = false;

      while(!var2) {
         int var3 = var1.next();
         if(var3 == 2) {
            if(var1.getName().equals("mechanisms")) {
               SASLAuthentication var4 = this.connection.getSASLAuthentication();
               Collection var5 = PacketParserUtils.parseMechanisms(var1);
               var4.setAvailableSASLMethods(var5);
            } else if(var1.getName().equals("bind")) {
               this.connection.getSASLAuthentication().bindingRequired();
            } else if(var1.getName().equals("session")) {
               this.connection.getSASLAuthentication().sessionsSupported();
            } else if(var1.getName().equals("register")) {
               this.connection.getAccountManager().setSupportsAccountCreation((boolean)1);
            }
         } else if(var3 == 3 && var1.getName().equals("features")) {
            var2 = true;
         }
      }

   }

   public void responseReceived(BOSHMessageEvent param1) {
      // $FF: Couldn't be decompiled
   }
}
