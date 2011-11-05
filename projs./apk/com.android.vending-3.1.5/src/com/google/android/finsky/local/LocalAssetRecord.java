package com.google.android.finsky.local;

import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.PersistentAssetStore;
import com.google.android.finsky.local.Writable;
import com.google.android.finsky.local.Writer;

public class LocalAssetRecord implements Writable {

   private final String mAccountString;
   private final AutoUpdateState mAutoUpdateState;
   private final String mPackage;


   public LocalAssetRecord(String var1, String var2, AutoUpdateState var3) {
      this.mPackage = var1;
      this.mAccountString = var2;
      this.mAutoUpdateState = var3;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               label49: {
                  LocalAssetRecord var5 = (LocalAssetRecord)var1;
                  if(this.mAccountString != null) {
                     String var6 = this.mAccountString;
                     String var7 = var5.mAccountString;
                     if(!var6.equals(var7)) {
                        break label49;
                     }
                  } else if(var5.mAccountString != null) {
                     break label49;
                  }

                  AutoUpdateState var8 = this.mAutoUpdateState;
                  AutoUpdateState var9 = var5.mAutoUpdateState;
                  if(var8 != var9) {
                     var2 = false;
                  } else {
                     if(this.mPackage != null) {
                        String var10 = this.mPackage;
                        String var11 = var5.mPackage;
                        if(var10.equals(var11)) {
                           return var2;
                        }
                     } else if(var5.mPackage == null) {
                        return var2;
                     }

                     var2 = false;
                  }

                  return var2;
               }

               var2 = false;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   protected String getAccountString() {
      return this.mAccountString;
   }

   public AutoUpdateState getAutoUpdateState() {
      return this.mAutoUpdateState;
   }

   public String getPackage() {
      return this.mPackage;
   }

   public int hashCode() {
      int var1 = 0;
      int var2;
      if(this.mPackage != null) {
         var2 = this.mPackage.hashCode();
      } else {
         var2 = 0;
      }

      int var3 = var2 * 31;
      int var4;
      if(this.mAccountString != null) {
         var4 = this.mAccountString.hashCode();
      } else {
         var4 = 0;
      }

      int var5 = (var3 + var4) * 31;
      if(this.mAutoUpdateState != null) {
         var1 = this.mAutoUpdateState.hashCode();
      }

      return var5 + var1;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("LocalAssetRecord{mPackage=\'");
      String var2 = this.mPackage;
      StringBuilder var3 = var1.append(var2).append('\'').append(", mAccountString=\'");
      String var4 = this.mAccountString;
      StringBuilder var5 = var3.append(var4).append('\'').append(", mAutoUpdateState=");
      AutoUpdateState var6 = this.mAutoUpdateState;
      return var5.append(var6).append('}').toString();
   }

   public void write(PersistentAssetStore var1, Writer.Op var2) {
      int[] var3 = LocalAssetRecord.1.$SwitchMap$com$google$android$finsky$local$Writer$Op;
      int var4 = var2.ordinal();
      switch(var3[var4]) {
      case 1:
         var1.insertAsset(this);
         return;
      case 2:
         String var5 = this.getPackage();
         var1.deleteAsset(var5);
         return;
      default:
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$local$Writer$Op = new int[Writer.Op.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$local$Writer$Op;
            int var1 = Writer.Op.INSERT.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$local$Writer$Op;
            int var3 = Writer.Op.DELETE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
