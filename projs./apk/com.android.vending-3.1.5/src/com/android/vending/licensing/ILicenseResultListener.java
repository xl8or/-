package com.android.vending.licensing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILicenseResultListener extends IInterface {

   void verifyLicense(int var1, String var2, String var3) throws RemoteException;

   public abstract static class Stub extends Binder implements ILicenseResultListener {

      private static final String DESCRIPTOR = "com.android.vending.licensing.ILicenseResultListener";
      static final int TRANSACTION_verifyLicense = 1;


      public Stub() {
         this.attachInterface(this, "com.android.vending.licensing.ILicenseResultListener");
      }

      public static ILicenseResultListener asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.android.vending.licensing.ILicenseResultListener");
            if(var2 != null && var2 instanceof ILicenseResultListener) {
               var1 = (ILicenseResultListener)var2;
            } else {
               var1 = new ILicenseResultListener.Stub.Proxy(var0);
            }
         }

         return (ILicenseResultListener)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.android.vending.licensing.ILicenseResultListener");
            int var6 = var2.readInt();
            String var7 = var2.readString();
            String var8 = var2.readString();
            this.verifyLicense(var6, var7, var8);
            break;
         case 1598968902:
            var3.writeString("com.android.vending.licensing.ILicenseResultListener");
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements ILicenseResultListener {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public String getInterfaceDescriptor() {
            return "com.android.vending.licensing.ILicenseResultListener";
         }

         public void verifyLicense(int var1, String var2, String var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.android.vending.licensing.ILicenseResultListener");
               var4.writeInt(var1);
               var4.writeString(var2);
               var4.writeString(var3);
               this.mRemote.transact(1, var4, (Parcel)null, 1);
            } finally {
               var4.recycle();
            }

         }
      }
   }
}
