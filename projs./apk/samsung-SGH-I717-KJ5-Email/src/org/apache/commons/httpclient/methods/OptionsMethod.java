package org.apache.commons.httpclient.methods;

import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OptionsMethod extends HttpMethodBase {

   private static final Log LOG = LogFactory.getLog(OptionsMethod.class);
   private Vector methodsAllowed;


   public OptionsMethod() {
      Vector var1 = new Vector();
      this.methodsAllowed = var1;
   }

   public OptionsMethod(String var1) {
      super(var1);
      Vector var2 = new Vector();
      this.methodsAllowed = var2;
   }

   public Enumeration getAllowedMethods() {
      this.checkUsed();
      return this.methodsAllowed.elements();
   }

   public String getName() {
      return "OPTIONS";
   }

   public boolean isAllowed(String var1) {
      this.checkUsed();
      return this.methodsAllowed.contains(var1);
   }

   public boolean needContentLength() {
      return false;
   }

   protected void processResponseHeaders(HttpState var1, HttpConnection var2) {
      LOG.trace("enter OptionsMethod.processResponseHeaders(HttpState, HttpConnection)");
      Header var3 = this.getResponseHeader("allow");
      if(var3 != null) {
         String var4 = var3.getValue();
         StringTokenizer var5 = new StringTokenizer(var4, ",");

         while(var5.hasMoreElements()) {
            String var6 = var5.nextToken().trim().toUpperCase();
            this.methodsAllowed.addElement(var6);
         }

      }
   }
}
