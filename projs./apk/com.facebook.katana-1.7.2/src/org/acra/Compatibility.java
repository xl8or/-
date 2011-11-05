package org.acra;

import android.content.Context;
import android.os.Build.VERSION;
import java.lang.reflect.Field;

public class Compatibility {

   public Compatibility() {}

   static int getAPILevel() {
      int var0;
      int var1;
      try {
         var0 = VERSION.class.getField("SDK_INT").getInt((Object)null);
      } catch (SecurityException var6) {
         var1 = Integer.parseInt(VERSION.SDK);
         return var1;
      } catch (NoSuchFieldException var7) {
         var1 = Integer.parseInt(VERSION.SDK);
         return var1;
      } catch (IllegalArgumentException var8) {
         var1 = Integer.parseInt(VERSION.SDK);
         return var1;
      } catch (IllegalAccessException var9) {
         var1 = Integer.parseInt(VERSION.SDK);
         return var1;
      }

      var1 = var0;
      return var1;
   }

   static String getDropBoxServiceName() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
      Field var0 = Context.class.getField("DROPBOX_SERVICE");
      String var1;
      if(var0 != null) {
         var1 = (String)var0.get((Object)null);
      } else {
         var1 = null;
      }

      return var1;
   }
}
