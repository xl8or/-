package org.apache.commons.httpclient;

import org.apache.commons.httpclient.HttpHost;
import org.apache.commons.httpclient.protocol.Protocol;

public class ProxyHost extends HttpHost {

   public ProxyHost(String var1) {
      this(var1, -1);
   }

   public ProxyHost(String var1, int var2) {
      Protocol var3 = Protocol.getProtocol("http");
      super(var1, var2, var3);
   }

   public ProxyHost(ProxyHost var1) {
      super((HttpHost)var1);
   }

   public Object clone() throws CloneNotSupportedException {
      return (ProxyHost)super.clone();
   }
}
