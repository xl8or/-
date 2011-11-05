package com.android.volley.toolbox.elegant;

import com.android.volley.VolleyLog;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class ElegantPlainSocketFactory implements SocketFactory {

   private static final ElegantPlainSocketFactory DEFAULT_FACTORY = new ElegantPlainSocketFactory();
   private final HostNameResolver nameResolver;


   public ElegantPlainSocketFactory() {
      this((HostNameResolver)null);
   }

   public ElegantPlainSocketFactory(HostNameResolver var1) {
      this.nameResolver = var1;
   }

   public static ElegantPlainSocketFactory getSocketFactory() {
      return DEFAULT_FACTORY;
   }

   public Socket connectSocket(Socket var1, String var2, int var3, InetAddress var4, int var5, HttpParams var6) throws IOException {
      if(var2 == null) {
         throw new IllegalArgumentException("Target host may not be null.");
      } else if(var6 == null) {
         throw new IllegalArgumentException("Parameters may not be null.");
      } else {
         if(var1 == null) {
            var1 = this.createSocket();
         }

         if(var4 != null || var5 > 0) {
            if(var5 < 0) {
               var5 = 0;
            }

            InetSocketAddress var9 = new InetSocketAddress(var4, var5);
            var1.bind(var9);
         }

         int var10 = HttpConnectionParams.getConnectionTimeout(var6);
         InetSocketAddress var15;
         if(this.nameResolver != null) {
            HostNameResolver var11 = this.nameResolver;
            InetAddress var13 = var11.resolve(var2);
            var15 = new InetSocketAddress(var13, var3);
         } else {
            var15 = new InetSocketAddress(var2, var3);
         }

         try {
            long var16 = System.currentTimeMillis();
            var1.connect(var15, var10);
            long var18 = System.currentTimeMillis() - var16;
            if(VolleyLog.DEBUG) {
               Object[] var20 = new Object[]{var2, null};
               Long var21 = Long.valueOf(var18);
               var20[1] = var21;
               VolleyLog.d("Established connection to [host=%s] in [%s ms]", var20);
            }

            return var1;
         } catch (SocketTimeoutException var26) {
            String var25 = "Connect to " + var15 + " timed out";
            throw new ConnectTimeoutException(var25);
         }
      }
   }

   public Socket createSocket() {
      return new Socket();
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return ElegantPlainSocketFactory.class.hashCode();
   }

   public final boolean isSecure(Socket var1) throws IllegalArgumentException {
      if(var1 == null) {
         throw new IllegalArgumentException("Socket may not be null.");
      } else if(var1.getClass() != Socket.class) {
         throw new IllegalArgumentException("Socket not created by this factory.");
      } else if(var1.isClosed()) {
         throw new IllegalArgumentException("Socket is closed.");
      } else {
         return false;
      }
   }
}
