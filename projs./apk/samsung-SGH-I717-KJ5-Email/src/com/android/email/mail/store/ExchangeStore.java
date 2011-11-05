package com.android.email.mail.store;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.email.ExchangeUtils;
import com.android.email.activity.setup.AccountSetupExchange;
import com.android.email.mail.Folder;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Store;
import com.android.email.mail.StoreSynchronizer;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailServiceCallback;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class ExchangeStore extends Store {

   public static final String LOG_TAG = "ExchangeStore";
   private final ExchangeStore.ExchangeTransport mTransport;
   private URI mUri;


   private ExchangeStore(String var1, Context var2, Store.PersistentDataCallbacks var3) throws MessagingException {
      try {
         URI var4 = new URI(var1);
         this.mUri = var4;
      } catch (URISyntaxException var7) {
         throw new MessagingException("Invalid uri for ExchangeStore");
      }

      ExchangeStore.ExchangeTransport var5 = ExchangeStore.ExchangeTransport.getInstance(this.mUri, var2);
      this.mTransport = var5;
   }

   public static AccountManagerFuture<Bundle> addSystemAccount(Context var0, EmailContent.Account var1, boolean var2, boolean var3, boolean var4, boolean var5, AccountManagerCallback<Bundle> var6) {
      Bundle var7 = new Bundle();
      String var8 = var1.mEmailAddress;
      var7.putString("username", var8);
      String var9 = var1.mHostAuthRecv.mPassword;
      var7.putString("password", var9);
      var7.putBoolean("contacts", var2);
      var7.putBoolean("calendar", var3);
      var7.putBoolean("tasks", var4);
      var7.putBoolean("notes", var5);
      AccountManager var10 = AccountManager.get(var0);
      Object var11 = null;
      Object var12 = null;
      Object var14 = null;
      return var10.addAccount("com.android.exchange", (String)null, (String[])var11, var7, (Activity)var12, var6, (Handler)var14);
   }

   public static Store newInstance(String var0, Context var1, Store.PersistentDataCallbacks var2) throws MessagingException {
      return new ExchangeStore(var0, var1, var2);
   }

   public static AccountManagerFuture<Boolean> removeSystemAccount(Context var0, EmailContent.Account var1, AccountManagerCallback<Bundle> var2) {
      String var3 = var1.mEmailAddress;
      Account var4 = new Account(var3, "com.android.exchange");
      return AccountManager.get(var0).removeAccount(var4, (AccountManagerCallback)null, (Handler)null);
   }

   public Bundle autoDiscover(Context var1, String var2, String var3, String var4, boolean var5) throws MessagingException {
      Bundle var6;
      Bundle var7;
      try {
         var6 = ExchangeUtils.getExchangeEmailService(var1, (IEmailServiceCallback)null).autoDiscover(var2, var3, var4, var5);
      } catch (RemoteException var9) {
         var7 = null;
         return var7;
      }

      var7 = var6;
      return var7;
   }

   public void checkSettings() throws MessagingException {
      ExchangeStore.ExchangeTransport var1 = this.mTransport;
      URI var2 = this.mUri;
      var1.checkSettings(var2);
   }

   public Folder getFolder(String var1) {
      return null;
   }

   public StoreSynchronizer getMessageSynchronizer() {
      return null;
   }

   public Folder[] getPersonalNamespaces() {
      return null;
   }

   public Class<? extends Activity> getSettingActivityClass() {
      return AccountSetupExchange.class;
   }

   public void removeFolder(String var1) {}

   public void renameFolder(String var1, String var2) {}

   public boolean requireCopyMessageToSentFolder() {
      return false;
   }

   public boolean requireStructurePrefetch() {
      return true;
   }

   public static class ExchangeTransport {

      private static HashMap<String, ExchangeStore.ExchangeTransport> sUriToInstanceMap = new HashMap();
      private final Context mContext;
      private String mDomain;
      private String mHost;
      private String mPassword;
      private String mUsername;


      private ExchangeTransport(URI var1, Context var2) throws MessagingException {
         this.mContext = var2;
         this.setUri(var1);
      }

      public static ExchangeStore.ExchangeTransport getInstance(URI var0, Context var1) throws MessagingException {
         synchronized(ExchangeStore.ExchangeTransport.class){}

         ExchangeStore.ExchangeTransport var4;
         try {
            if(!var0.getScheme().equals("eas") && !var0.getScheme().equals("eas+ssl+") && !var0.getScheme().equals("eas+ssl+trustallcerts")) {
               throw new MessagingException("Invalid scheme");
            }

            String var3 = var0.toString();
            var4 = (ExchangeStore.ExchangeTransport)sUriToInstanceMap.get(var3);
            if(var4 == null) {
               var4 = new ExchangeStore.ExchangeTransport(var0, var1);
               sUriToInstanceMap.put(var3, var4);
            }
         } finally {
            ;
         }

         return var4;
      }

      private void setUri(URI var1) throws MessagingException {
         String var2 = var1.getHost();
         this.mHost = var2;
         if(this.mHost == null) {
            throw new MessagingException("host not specified");
         } else {
            String var3 = var1.getPath();
            this.mDomain = var3;
            if(!TextUtils.isEmpty(this.mDomain)) {
               String var4 = this.mDomain.substring(1);
               this.mDomain = var4;
            }

            String var5 = var1.getUserInfo();
            if(var5 == null) {
               throw new MessagingException("user information not specifed");
            } else {
               String[] var6 = var5.split(":", 2);
               if(var6.length != 2) {
                  throw new MessagingException("user name and password not specified");
               } else {
                  String var7 = var6[0];
                  this.mUsername = var7;
                  String var8 = var6[1];
                  this.mPassword = var8;
               }
            }
         }
      }

      public void checkSettings(URI param1) throws MessagingException {
         // $FF: Couldn't be decompiled
      }
   }
}
