package com.android.vending.licensing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.vending.licensing.ILicenseResultListener;

public interface ILicensingService extends IInterface {

   void checkLicense(long var1, String var3, ILicenseResultListener var4) throws RemoteException;

   public abstract static class Stub extends Binder implements ILicensingService {

      private static final String DESCRIPTOR = "com.android.vending.licensing.ILicensingService";
      static final int TRANSACTION_checkLicense = 1;


      public Stub() {
         this.attachInterface(this, "com.android.vending.licensing.ILicensingService");
      }

      public static ILicensingService asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.android.vending.licensing.ILicensingService");
            if(var2 != null && var2 instanceof ILicensingService) {
               var1 = (ILicensingService)var2;
            } else {
               var1 = new ILicensingService.Stub.Proxy(var0);
            }
         }

         return (ILicensingService)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.android.vending.licensing.ILicensingService");
            long var6 = var2.readLong();
            String var8 = var2.readString();
            ILicenseResultListener var9 = ILicenseResultListener.Stub.asInterface(var2.readStrongBinder());
            this.checkLicense(var6, var8, var9);
            break;
         case 1598968902:
            var3.writeString("com.android.vending.licensing.ILicensingService");
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements ILicensingService {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public void checkLicense(long var1, String var3, ILicenseResultListener var4) throws RemoteException {
            IBinder var5 = null;
            Parcel var6 = Parcel.obtain();

            try {
               var6.writeInterfaceToken("com.android.vending.licensing.ILicensingService");
               var6.writeLong(var1);
               var6.writeString(var3);
               if(var4 != null) {
                  var5 = var4.asBinder();
               }

               var6.writeStrongBinder(var5);
               this.mRemote.transact(1, var6, (Parcel)null, 1);
            } finally {
               var6.recycle();
            }

         }

         public String getInterfaceDescriptor() {
            return "com.android.vending.licensing.ILicensingService";
         }
      }
   }
}
