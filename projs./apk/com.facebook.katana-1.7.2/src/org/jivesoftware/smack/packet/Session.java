package org.jivesoftware.smack.packet;

import org.jivesoftware.smack.packet.IQ;

public class Session extends IQ {

   public Session() {
      IQ.Type var1 = IQ.Type.SET;
      this.setType(var1);
   }

   public String getChildElementXML() {
      return "<session xmlns=\"urn:ietf:params:xml:ns:xmpp-session\"/>";
   }
}
