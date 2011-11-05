package com.android.email.mail;

import android.app.Activity;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import com.android.email.activity.setup.AccountSetupIncoming;
import com.android.email.mail.Folder;
import com.android.email.mail.MessagingException;
import com.android.email.mail.StoreSynchronizer;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParserException;

public abstract class Store {

   public static final int FETCH_BODY_SANE_SUGGESTED_SIZE = 51200;
   public static final String STORE_SCHEME_EAS = "eas";
   public static final String STORE_SCHEME_IMAP = "imap";
   public static final String STORE_SCHEME_LOCAL = "local";
   public static final String STORE_SCHEME_POP3 = "pop3";
   public static final String STORE_SECURITY_SSL = "+ssl";
   public static final String STORE_SECURITY_TLS = "+tls";
   public static final String STORE_SECURITY_TRUST_CERTIFICATES = "+trustallcerts";
   private static final HashMap<String, Store> sStores = new HashMap();


   public Store() {}

   public static Store getInstance(String var0, Context var1, Store.PersistentDataCallbacks var2) throws MessagingException {
      synchronized(Store.class){}

      Store var3;
      try {
         var3 = (Store)sStores.get(var0);
         if(var3 == null) {
            Store.StoreInfo var4 = Store.StoreInfo.getStoreInfo(var0, var1);
            if(var4 != null) {
               var3 = instantiateStore(var4.mClassName, var0, var1, var2);
            }

            if(var3 != null) {
               sStores.put(var0, var3);
            }
         } else {
            var3.setPersistentDataCallbacks(var2);
         }

         if(var3 == null) {
            String var6 = "Unable to locate an applicable Store for " + var0;
            throw new MessagingException(var6);
         }
      } finally {
         ;
      }

      return var3;
   }

   private static Store instantiateStore(String var0, String var1, Context var2, Store.PersistentDataCallbacks var3) throws MessagingException {
      Object var8;
      try {
         Class var4 = Class.forName(var0);
         Class[] var5 = new Class[]{String.class, Context.class, Store.PersistentDataCallbacks.class};
         Method var6 = var4.getMethod("newInstance", var5);
         Object[] var7 = new Object[]{var1, var2, var3};
         var8 = var6.invoke((Object)null, var7);
      } catch (Exception var17) {
         Object[] var12 = new Object[3];
         String var13 = var17.toString();
         var12[0] = var13;
         var12[1] = var0;
         var12[2] = var1;
         String var14 = String.format("exception %s invoking %s.newInstance.(String, Context) method for %s", var12);
         int var15 = Log.d("Email", var14);
         String var16 = "can not instantiate Store object for " + var1;
         throw new MessagingException(var16);
      }

      if(!(var8 instanceof Store)) {
         String var10 = var1 + ": " + var0 + " create incompatible object";
         throw new MessagingException(var10);
      } else {
         return (Store)var8;
      }
   }

   public static Store newInstance(String var0, Context var1, Store.PersistentDataCallbacks var2) throws MessagingException {
      String var3 = "Store.newInstance: Unknown scheme in " + var0;
      throw new MessagingException(var3);
   }

   public static void removeInstance(String var0) {
      synchronized(Store.class){}

      try {
         Object var1 = sStores.remove(var0);
      } finally {
         ;
      }

   }

   public Bundle autoDiscover(Context var1, String var2, String var3) throws MessagingException {
      return null;
   }

   public abstract void checkSettings() throws MessagingException;

   public void delete() throws MessagingException {}

   public abstract Folder getFolder(String var1) throws MessagingException;

   public StoreSynchronizer getMessageSynchronizer() {
      return null;
   }

   public abstract Folder[] getPersonalNamespaces() throws MessagingException;

   public Class<? extends Activity> getSettingActivityClass() {
      return AccountSetupIncoming.class;
   }

   public boolean requireCopyMessageToSentFolder() {
      return true;
   }

   public boolean requireStructurePrefetch() {
      return false;
   }

   protected void setPersistentDataCallbacks(Store.PersistentDataCallbacks var1) {}

   public interface PersistentDataCallbacks {

      String getPersistentString(String var1, String var2);

      void setPersistentString(String var1, String var2);
   }

   public static class StoreInfo {

      public int mAccountInstanceLimit;
      public String mClassName;
      public boolean mPushSupported = 0;
      public String mScheme;
      public int mVisibleLimitDefault;
      public int mVisibleLimitIncrement;


      public StoreInfo() {}

      public static Store.StoreInfo getStoreInfo(int var0, String var1, Context var2) {
         Store.StoreInfo var7;
         Store.StoreInfo var13;
         label38: {
            try {
               XmlResourceParser var3 = var2.getResources().getXml(var0);

               while(true) {
                  int var4 = var3.next();
                  if(var4 == 1) {
                     break;
                  }

                  if(var4 == 2) {
                     String var5 = var3.getName();
                     if("store".equals(var5)) {
                        String var6 = var3.getAttributeValue((String)null, "scheme");
                        if(var1 != null && var1.startsWith(var6)) {
                           var7 = new Store.StoreInfo();
                           var7.mScheme = var6;
                           String var8 = var3.getAttributeValue((String)null, "class");
                           var7.mClassName = var8;
                           boolean var9 = var3.getAttributeBooleanValue((String)null, "push", (boolean)0);
                           var7.mPushSupported = var9;
                           int var10 = var3.getAttributeIntValue((String)null, "visibleLimitDefault", 25);
                           var7.mVisibleLimitDefault = var10;
                           int var11 = var3.getAttributeIntValue((String)null, "visibleLimitIncrement", 25);
                           var7.mVisibleLimitIncrement = var11;
                           int var12 = var3.getAttributeIntValue((String)null, "accountInstanceLimit", -1);
                           var7.mAccountInstanceLimit = var12;
                           break label38;
                        }
                     }
                  }
               }
            } catch (XmlPullParserException var16) {
               ;
            } catch (IOException var17) {
               ;
            }

            var13 = null;
            return var13;
         }

         var13 = var7;
         return var13;
      }

      public static Store.StoreInfo getStoreInfo(String var0, Context var1) {
         Store.StoreInfo var2 = getStoreInfo(2131034123, var0, var1);
         if(var2 == null) {
            var2 = getStoreInfo(2131034122, var0, var1);
         }

         return var2;
      }
   }
}
