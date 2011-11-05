package com.digc.seven;

import android.content.Context;
import android.util.Log;
import com.android.email.Email;
import com.android.email.combined.AccountFacade;
import java.util.Hashtable;

public class CSAccountSyncManager {

   private static CSAccountSyncManager mInstance;


   public CSAccountSyncManager() {}

   public static CSAccountSyncManager getInstance() {
      if(mInstance == null) {
         mInstance = new CSAccountSyncManager();
      }

      return mInstance;
   }

   public String[][] getCBAccount(Context param1) {
      // $FF: Couldn't be decompiled
   }

   public Hashtable<String, String> getSevenAccount(Context param1) {
      // $FF: Couldn't be decompiled
   }

   public void sync(Context var1) {
      String[][] var2 = this.getCBAccount(var1);
      Hashtable var3 = this.getSevenAccount(var1);
      boolean var4 = false;
      if(var2 != null) {
         int var5 = 0;

         while(true) {
            int var6 = var2.length;
            if(var5 >= var6) {
               return;
            }

            StringBuilder var7 = (new StringBuilder()).append("Combined ");
            String var8 = var2[var5][0];
            StringBuilder var9 = var7.append(var8).append(":");
            String var10 = var2[var5][1];
            String var11 = var9.append(var10).toString();
            int var12 = Log.d("$$$$", var11);
            String var13 = var2[var5][0];
            if(var3.containsKey(var13)) {
               String var14 = var2[var5][1];
               String var15 = var2[var5][0];
               String var16 = (String)var3.get(var15);
               if(!var14.equals(var16)) {
                  var4 = true;
               }
            } else {
               var4 = true;
            }

            if(var4) {
               AccountFacade var17 = Email.getAccountFacade();
               int var18 = Integer.parseInt(var2[var5][1]);
               var17.quietRemoveAccount(var18);
               var4 = false;
            }

            ++var5;
         }
      }
   }
}
