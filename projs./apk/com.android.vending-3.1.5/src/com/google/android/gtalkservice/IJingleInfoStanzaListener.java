package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IJingleInfoStanzaListener extends IInterface {

   long getAccountId() throws RemoteException;

   void onStanzaReceived(String var1) throws RemoteException;

   public abstract static class Stub extends Binder implements IJingleInfoStanzaListener {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.IJingleInfoStanzaListener";
      static final int TRANSACTION_getAccountId = 2;
      static final int TRANSACTION_onStanzaReceived = 1;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.IJingleInfoStanzaListener");
      }

      public static IJingleInfoStanzaListener asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.IJingleInfoStanzaListener");
            if(var2 != null && var2 instanceof IJingleInfoStanzaListener) {
               var1 = (IJingleInfoStanzaListener)var2;
            } else {
               var1 = new IJingleInfoStanzaListener.Stub.Proxy(var0);
            }
         }

         return (IJingleInfoStanzaListener)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.IJingleInfoStanzaListener");
            String var6 = var2.readString();
            this.onStanzaReceived(var6);
            var3.writeNoException();
            break;
         case 2:
            var2.enforceInterface("com.google.android.gtalkservice.IJingleInfoStanzaListener");
            long var7 = this.getAccountId();
            var3.writeNoException();
            var3.writeLong(var7);
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.IJingleInfoStanzaListener");
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IJingleInfoStanzaListener {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public long getAccountId() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var10 = false;

            long var4;
            try {
               var10 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IJingleInfoStanzaListener");
               this.mRemote.transact(2, var1, var2, 0);
               var2.readException();
               var4 = var2.readLong();
               var10 = false;
            } finally {
               if(var10) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var4;
         }

         public String getInterfaceDescriptor() {
            return "com.google.android.gtalkservice.IJingleInfoStanzaListener";
         }

         public void onStanzaReceived(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.google.android.gtalkservice.IJingleInfoStanzaListener");
               var2.writeString(var1);
               this.mRemote.transact(1, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }
      }
   }
}
