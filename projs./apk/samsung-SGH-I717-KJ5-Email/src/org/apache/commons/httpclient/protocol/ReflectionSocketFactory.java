package org.apache.commons.httpclient.protocol;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.httpclient.ConnectTimeoutException;

public final class ReflectionSocketFactory {

   private static Constructor INETSOCKETADDRESS_CONSTRUCTOR = null;
   private static boolean REFLECTION_FAILED = 0;
   private static Method SOCKETBIND_METHOD = null;
   private static Method SOCKETCONNECT_METHOD = null;
   private static Class SOCKETTIMEOUTEXCEPTION_CLASS = null;


   private ReflectionSocketFactory() {}

   public static Socket createSocket(String var0, String var1, int var2, InetAddress var3, int var4, int var5) throws IOException, UnknownHostException, ConnectTimeoutException {
      Socket var44;
      if(REFLECTION_FAILED) {
         var44 = null;
      } else {
         Socket var14;
         try {
            Class var6 = Class.forName(var0);
            Class[] var7 = new Class[0];
            Method var8 = var6.getMethod("getDefault", var7);
            Object[] var9 = new Object[0];
            Object var10 = var8.invoke((Object)null, var9);
            Class[] var11 = new Class[0];
            Method var12 = var6.getMethod("createSocket", var11);
            Object[] var13 = new Object[0];
            var14 = (Socket)var12.invoke(var10, var13);
            if(INETSOCKETADDRESS_CONSTRUCTOR == null) {
               Class var15 = Class.forName("java.net.InetSocketAddress");
               Class[] var16 = new Class[]{InetAddress.class, null};
               Class var17 = Integer.TYPE;
               var16[1] = var17;
               INETSOCKETADDRESS_CONSTRUCTOR = var15.getConstructor(var16);
            }

            Constructor var18 = INETSOCKETADDRESS_CONSTRUCTOR;
            Object[] var19 = new Object[2];
            InetAddress var20 = InetAddress.getByName(var1);
            var19[0] = var20;
            Integer var21 = Integer.valueOf(var2);
            var19[1] = var21;
            Object var45 = var18.newInstance(var19);
            Constructor var22 = INETSOCKETADDRESS_CONSTRUCTOR;
            Object[] var23 = new Object[]{var3, null};
            Integer var24 = Integer.valueOf(var4);
            var23[1] = var24;
            Object var46 = var22.newInstance(var23);
            if(SOCKETCONNECT_METHOD == null) {
               Class[] var25 = new Class[2];
               Class var26 = Class.forName("java.net.SocketAddress");
               var25[0] = var26;
               Class var27 = Integer.TYPE;
               var25[1] = var27;
               SOCKETCONNECT_METHOD = Socket.class.getMethod("connect", var25);
            }

            if(SOCKETBIND_METHOD == null) {
               Class[] var28 = new Class[1];
               Class var29 = Class.forName("java.net.SocketAddress");
               var28[0] = var29;
               SOCKETBIND_METHOD = Socket.class.getMethod("bind", var28);
            }

            Method var30 = SOCKETBIND_METHOD;
            Object[] var31 = new Object[]{var46};
            var30.invoke(var14, var31);
            Method var33 = SOCKETCONNECT_METHOD;
            Object[] var34 = new Object[]{var45, null};
            Integer var35 = Integer.valueOf(var5);
            var34[1] = var35;
            var33.invoke(var14, var34);
         } catch (InvocationTargetException var42) {
            Throwable var37 = var42.getTargetException();
            if(SOCKETTIMEOUTEXCEPTION_CLASS == null) {
               try {
                  SOCKETTIMEOUTEXCEPTION_CLASS = Class.forName("java.net.SocketTimeoutException");
               } catch (ClassNotFoundException var41) {
                  REFLECTION_FAILED = (boolean)1;
                  var44 = null;
                  return var44;
               }
            }

            if(SOCKETTIMEOUTEXCEPTION_CLASS.isInstance(var37)) {
               String var38 = "The host did not accept the connection within timeout of " + var5 + " ms";
               throw new ConnectTimeoutException(var38, var37);
            }

            if(var37 instanceof IOException) {
               throw (IOException)var37;
            }

            var44 = null;
            return var44;
         } catch (Exception var43) {
            REFLECTION_FAILED = (boolean)1;
            var44 = null;
            return var44;
         }

         var44 = var14;
      }

      return var44;
   }
}
