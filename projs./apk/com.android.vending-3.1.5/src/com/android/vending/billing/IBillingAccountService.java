package com.android.vending.billing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBillingAccountService extends IInterface {

   int hasValidCreditCard(String var1) throws RemoteException;

   public abstract static class Stub extends Binder implements IBillingAccountService {

      private static final String DESCRIPTOR = "com.android.vending.billing.IBillingAccountService";
      static final int TRANSACTION_hasValidCreditCard = 1;


      public Stub() {
         this.attachInterface(this, "com.android.vending.billing.IBillingAccountService");
      }

      public static IBillingAccountService asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.android.vending.billing.IBillingAccountService");
            if(var2 != null && var2 instanceof IBillingAccountService) {
               var1 = (IBillingAccountService)var2;
            } else {
               var1 = new IBillingAccountService.Stub.Proxy(var0);
            }
         }

         return (IBillingAccountService)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.android.vending.billing.IBillingAccountService");
            String var6 = var2.readString();
            int var7 = this.hasValidCreditCard(var6);
            var3.writeNoException();
            var3.writeInt(var7);
            break;
         case 1598968902:
            var3.writeString("com.android.vending.billing.IBillingAccountService");
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IBillingAccountService {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public String getInterfaceDescriptor() {
            return "com.android.vending.billing.IBillingAccountService";
         }

         public int hasValidCreditCard(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();
            boolean var9 = false;

            int var5;
            try {
               var9 = true;
               var2.writeInterfaceToken("com.android.vending.billing.IBillingAccountService");
               var2.writeString(var1);
               this.mRemote.transact(1, var2, var3, 0);
               var3.readException();
               var5 = var3.readInt();
               var9 = false;
            } finally {
               if(var9) {
                  var3.recycle();
                  var2.recycle();
               }
            }

            var3.recycle();
            var2.recycle();
            return var5;
         }
      }
   }
}
