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
import com.android.email.mail.AuthenticationFailedException;
import com.android.email.mail.Folder;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Store;
import com.android.email.mail.StoreSynchronizer;
import com.android.email.provider.EmailContent;
import com.android.email.service.EmailServiceProxy;
import com.android.email.service.IEmailService;
import com.android.email.service.IEmailServiceCallback;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class ExchangeStore extends Store {

   public static final String LOG_TAG = "ExchangeStore";
   private final ExchangeStore.ExchangeTransport mTransport;
   private final URI mUri;


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

   public static AccountManagerFuture<Bundle> addSystemAccount(Context var0, EmailContent.Account var1, boolean var2, boolean var3, AccountManagerCallback<Bundle> var4) {
      Bundle var5 = new Bundle();
      String var6 = var1.mEmailAddress;
      var5.putString("username", var6);
      String var7 = var1.mHostAuthRecv.mPassword;
      var5.putString("password", var7);
      var5.putBoolean("contacts", var2);
      var5.putBoolean("calendar", var3);
      AccountManager var8 = AccountManager.get(var0);
      Object var9 = null;
      Object var10 = null;
      Object var12 = null;
      return var8.addAccount("com.android.exchange", (String)null, (String[])var9, var5, (Activity)var10, var4, (Handler)var12);
   }

   public static Store newInstance(String var0, Context var1, Store.PersistentDataCallbacks var2) throws MessagingException {
      return new ExchangeStore(var0, var1, var2);
   }

   public static AccountManagerFuture<Boolean> removeSystemAccount(Context var0, EmailContent.Account var1, AccountManagerCallback<Bundle> var2) {
      String var3 = var1.mEmailAddress;
      Account var4 = new Account(var3, "com.android.exchange");
      return AccountManager.get(var0).removeAccount(var4, (AccountManagerCallback)null, (Handler)null);
   }

   public Bundle autoDiscover(Context var1, String var2, String var3) throws MessagingException {
      Bundle var4;
      Bundle var5;
      try {
         var4 = ExchangeUtils.getExchangeEmailService(var1, (IEmailServiceCallback)null).autoDiscover(var2, var3);
      } catch (RemoteException var7) {
         var5 = null;
         return var5;
      }

      var5 = var4;
      return var5;
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

   public boolean requireCopyMessageToSentFolder() {
      return false;
   }

   public boolean requireStructurePrefetch() {
      return true;
   }

   public static class ExchangeTransport {

      private static final HashMap<String, ExchangeStore.ExchangeTransport> sUriToInstanceMap = new HashMap();
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

      public void checkSettings(URI var1) throws MessagingException {
         this.setUri(var1);
         boolean var2 = var1.getScheme().contains("+ssl");
         boolean var3 = var1.getScheme().contains("+trustallcerts");
         short var4;
         if(var2) {
            var4 = 443;
         } else {
            var4 = 80;
         }

         try {
            IEmailService var5 = ExchangeUtils.getExchangeEmailService(this.mContext, (IEmailServiceCallback)null);
            if(var5 instanceof EmailServiceProxy) {
               EmailServiceProxy var6 = ((EmailServiceProxy)var5).setTimeout(90);
            }

            String var7 = this.mHost;
            String var8 = this.mUsername;
            String var9 = this.mPassword;
            int var10 = var5.validate("eas", var7, var8, var9, var4, var2, var3);
            if(var10 != -1) {
               if(var10 == 5) {
                  throw new AuthenticationFailedException("Authentication failed.");
               } else {
                  throw new MessagingException(var10);
               }
            }
         } catch (RemoteException var12) {
            throw new MessagingException("Call to validate generated an exception", var12);
         }
      }
   }
}
