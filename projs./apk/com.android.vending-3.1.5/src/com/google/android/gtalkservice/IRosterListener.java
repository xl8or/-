package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRosterListener extends IInterface {

   void presenceChanged(String var1) throws RemoteException;

   void rosterChanged() throws RemoteException;

   void selfPresenceChanged() throws RemoteException;

   public abstract static class Stub extends Binder implements IRosterListener {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.IRosterListener";
      static final int TRANSACTION_presenceChanged = 2;
      static final int TRANSACTION_rosterChanged = 1;
      static final int TRANSACTION_selfPresenceChanged = 3;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.IRosterListener");
      }

      public static IRosterListener asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.IRosterListener");
            if(var2 != null && var2 instanceof IRosterListener) {
               var1 = (IRosterListener)var2;
            } else {
               var1 = new IRosterListener.Stub.Proxy(var0);
            }
         }

         return (IRosterListener)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.IRosterListener");
            this.rosterChanged();
            var3.writeNoException();
            break;
         case 2:
            var2.enforceInterface("com.google.android.gtalkservice.IRosterListener");
            String var6 = var2.readString();
            this.presenceChanged(var6);
            var3.writeNoException();
            break;
         case 3:
            var2.enforceInterface("com.google.android.gtalkservice.IRosterListener");
            this.selfPresenceChanged();
            var3.writeNoException();
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.IRosterListener");
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IRosterListener {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public String getInterfaceDescriptor() {
            return "com.google.android.gtalkservice.IRosterListener";
         }

         public void presenceChanged(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.google.android.gtalkservice.IRosterListener");
               var2.writeString(var1);
               this.mRemote.transact(2, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }

         public void rosterChanged() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.google.android.gtalkservice.IRosterListener");
               this.mRemote.transact(1, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }

         public void selfPresenceChanged() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.google.android.gtalkservice.IRosterListener");
               this.mRemote.transact(3, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }
      }
   }
}
