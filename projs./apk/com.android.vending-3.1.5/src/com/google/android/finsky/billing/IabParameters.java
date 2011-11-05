package com.google.android.finsky.billing;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class IabParameters implements Parcelable {

   public static final Creator<IabParameters> CREATOR = new IabParameters.1();
   public final String developerPayload;
   public final String packageName;
   public final String packageSignatureHash;
   public final int packageVersionCode;


   public IabParameters(String var1, int var2, String var3, String var4) {
      this.packageName = var1;
      this.packageVersionCode = var2;
      this.packageSignatureHash = var3;
      this.developerPayload = var4;
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               IabParameters var5 = (IabParameters)var1;
               int var6 = this.packageVersionCode;
               int var7 = var5.packageVersionCode;
               if(var6 != var7) {
                  var2 = false;
               } else {
                  label63: {
                     if(this.developerPayload != null) {
                        String var8 = this.developerPayload;
                        String var9 = var5.developerPayload;
                        if(var8.equals(var9)) {
                           break label63;
                        }
                     } else if(var5.developerPayload == null) {
                        break label63;
                     }

                     var2 = false;
                     return var2;
                  }

                  label64: {
                     if(this.packageName != null) {
                        String var10 = this.packageName;
                        String var11 = var5.packageName;
                        if(var10.equals(var11)) {
                           break label64;
                        }
                     } else if(var5.packageName == null) {
                        break label64;
                     }

                     var2 = false;
                     return var2;
                  }

                  if(this.packageSignatureHash != null) {
                     String var12 = this.packageSignatureHash;
                     String var13 = var5.packageSignatureHash;
                     if(var12.equals(var13)) {
                        return var2;
                     }
                  } else if(var5.packageSignatureHash == null) {
                     return var2;
                  }

                  var2 = false;
               }

               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.packageName;
      var1.writeString(var3);
      int var4 = this.packageVersionCode;
      var1.writeInt(var4);
      String var5 = this.packageSignatureHash;
      var1.writeString(var5);
      String var6 = this.developerPayload;
      var1.writeString(var6);
   }

   static class 1 implements Creator<IabParameters> {

      1() {}

      public IabParameters createFromParcel(Parcel var1) {
         String var2 = var1.readString();
         int var3 = var1.readInt();
         String var4 = var1.readString();
         String var5 = var1.readString();
         return new IabParameters(var2, var3, var4, var5);
      }

      public IabParameters[] newArray(int var1) {
         return new IabParameters[var1];
      }
   }
}
