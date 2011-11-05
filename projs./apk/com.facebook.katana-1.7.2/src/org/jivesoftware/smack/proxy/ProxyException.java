package org.jivesoftware.smack.proxy;

import java.io.IOException;
import org.jivesoftware.smack.proxy.ProxyInfo;

public class ProxyException extends IOException {

   public ProxyException(ProxyInfo.ProxyType var1) {
      StringBuilder var2 = (new StringBuilder()).append("Proxy Exception ");
      String var3 = var1.toString();
      String var4 = var2.append(var3).append(" : ").append("Unknown Error").toString();
      super(var4);
   }

   public ProxyException(ProxyInfo.ProxyType var1, String var2) {
      StringBuilder var3 = (new StringBuilder()).append("Proxy Exception ");
      String var4 = var1.toString();
      String var5 = var3.append(var4).append(" : ").append(var2).toString();
      super(var5);
   }

   public ProxyException(ProxyInfo.ProxyType var1, String var2, Throwable var3) {
      StringBuilder var4 = (new StringBuilder()).append("Proxy Exception ");
      String var5 = var1.toString();
      String var6 = var4.append(var5).append(" : ").append(var2).append(", ").append(var3).toString();
      super(var6);
   }
}
