package myorg.bouncycastle.i18n;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

public class MissingEntryException extends RuntimeException {

   private String debugMsg;
   protected final String key;
   protected final ClassLoader loader;
   protected final Locale locale;
   protected final String resource;


   public MissingEntryException(String var1, String var2, String var3, Locale var4, ClassLoader var5) {
      super(var1);
      this.resource = var2;
      this.key = var3;
      this.locale = var4;
      this.loader = var5;
   }

   public MissingEntryException(String var1, Throwable var2, String var3, String var4, Locale var5, ClassLoader var6) {
      super(var1, var2);
      this.resource = var3;
      this.key = var4;
      this.locale = var5;
      this.loader = var6;
   }

   public ClassLoader getClassLoader() {
      return this.loader;
   }

   public String getDebugMsg() {
      if(this.debugMsg == null) {
         StringBuilder var1 = (new StringBuilder()).append("Can not find entry ");
         String var2 = this.key;
         StringBuilder var3 = var1.append(var2).append(" in resource file ");
         String var4 = this.resource;
         StringBuilder var5 = var3.append(var4).append(" for the locale ");
         Locale var6 = this.locale;
         String var7 = var5.append(var6).append(".").toString();
         this.debugMsg = var7;
         if(this.loader instanceof URLClassLoader) {
            URL[] var8 = ((URLClassLoader)this.loader).getURLs();
            StringBuilder var9 = new StringBuilder();
            String var10 = this.debugMsg;
            String var11 = var9.append(var10).append(" The following entries in the classpath were searched: ").toString();
            this.debugMsg = var11;
            int var12 = 0;

            while(true) {
               int var13 = var8.length;
               if(var12 == var13) {
                  break;
               }

               StringBuilder var14 = new StringBuilder();
               String var15 = this.debugMsg;
               StringBuilder var16 = var14.append(var15);
               URL var17 = var8[var12];
               String var18 = var16.append(var17).append(" ").toString();
               this.debugMsg = var18;
               ++var12;
            }
         }
      }

      return this.debugMsg;
   }

   public String getKey() {
      return this.key;
   }

   public Locale getLocale() {
      return this.locale;
   }

   public String getResource() {
      return this.resource;
   }
}
