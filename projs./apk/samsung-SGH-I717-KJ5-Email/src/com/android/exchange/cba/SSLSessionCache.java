package com.android.exchange.cba;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import org.apache.harmony.xnet.provider.jsse.FileClientSessionCache;
import org.apache.harmony.xnet.provider.jsse.SSLClientSessionCache;

public final class SSLSessionCache {

   private static final String TAG = "SSLSessionCache";
   final SSLClientSessionCache mSessionCache;


   public SSLSessionCache(Context var1) {
      File var2 = var1.getDir("sslcache", 0);
      SSLClientSessionCache var3 = null;

      label13: {
         SSLClientSessionCache var4;
         try {
            var4 = FileClientSessionCache.usingDirectory(var2);
         } catch (IOException var8) {
            String var6 = "Unable to create SSL session cache in " + var2;
            int var7 = Log.w("SSLSessionCache", var6, var8);
            break label13;
         }

         var3 = var4;
      }

      this.mSessionCache = var3;
   }

   public SSLSessionCache(File var1) throws IOException {
      SSLClientSessionCache var2 = FileClientSessionCache.usingDirectory(var1);
      this.mSessionCache = var2;
   }
}
