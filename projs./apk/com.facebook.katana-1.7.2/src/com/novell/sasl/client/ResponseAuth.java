package com.novell.sasl.client;

import com.novell.sasl.client.DirectiveList;
import com.novell.sasl.client.ParsedDirective;
import java.util.Iterator;
import org.apache.harmony.javax.security.sasl.SaslException;

class ResponseAuth {

   private String m_responseValue = null;


   ResponseAuth(byte[] var1) throws SaslException {
      DirectiveList var2 = new DirectiveList(var1);

      try {
         var2.parseDirectives();
         this.checkSemantics(var2);
      } catch (SaslException var4) {
         ;
      }
   }

   void checkSemantics(DirectiveList var1) throws SaslException {
      Iterator var2 = var1.getIterator();

      while(var2.hasNext()) {
         ParsedDirective var3 = (ParsedDirective)var2.next();
         if(var3.getName().equals("rspauth")) {
            String var4 = var3.getValue();
            this.m_responseValue = var4;
         }
      }

      if(this.m_responseValue == null) {
         throw new SaslException("Missing response-auth directive.");
      }
   }

   public String getResponseValue() {
      return this.m_responseValue;
   }
}
