package com.android.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.security.Credentials;
import android.security.KeyStore;
import android.util.Log;
import java.util.Iterator;

public class CredentialInstaller extends Activity {

   private static final String TAG = "CredentialInstaller";
   private static final String UNLOCKING = "ulck";
   private KeyStore mKeyStore;
   private boolean mUnlocking;


   public CredentialInstaller() {
      KeyStore var1 = KeyStore.getInstance();
      this.mKeyStore = var1;
      this.mUnlocking = (boolean)0;
   }

   private void install() {
      Intent var1 = this.getIntent();
      Bundle var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getExtras();
      }

      if(var2 != null) {
         Iterator var3 = var2.keySet().iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            byte[] var5 = var2.getByteArray(var4);
            if(var5 != null) {
               KeyStore var6 = this.mKeyStore;
               byte[] var7 = var4.getBytes();
               boolean var8 = var6.put(var7, var5);
               StringBuilder var9 = (new StringBuilder()).append("install ").append(var4).append(": ");
               int var10 = var5.length;
               String var11 = var9.append(var10).append("  success? ").append(var8).toString();
               int var12 = Log.d("CredentialInstaller", var11);
               if(!var8) {
                  return;
               }
            }
         }

         this.setResult(-1);
      }
   }

   private boolean isKeyStoreUnlocked() {
      boolean var1;
      if(this.mKeyStore.test() == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void onRestoreInstanceState(Bundle var1) {
      super.onRestoreInstanceState(var1);
      boolean var2 = var1.getBoolean("ulck");
      this.mUnlocking = var2;
   }

   protected void onResume() {
      super.onResume();
      String var1 = this.getCallingPackage();
      if(!"com.android.certinstaller".equals(var1)) {
         this.finish();
      }

      if(this.isKeyStoreUnlocked()) {
         this.install();
      } else if(!this.mUnlocking) {
         this.mUnlocking = (boolean)1;
         Credentials.getInstance().unlock(this);
         return;
      }

      this.finish();
   }

   protected void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      boolean var2 = this.mUnlocking;
      var1.putBoolean("ulck", var2);
   }
}
