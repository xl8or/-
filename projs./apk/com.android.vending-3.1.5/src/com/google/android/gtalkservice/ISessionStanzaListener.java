package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISessionStanzaListener extends IInterface {

   long getAccountId() throws RemoteException;

   void onStanzaReceived(String var1, String var2) throws RemoteException;

   void onStanzaResponse(String var1, String var2, String var3) throws RemoteException;

   public abstract static class Stub extends Binder implements ISessionStanzaListener {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.ISessionStanzaListener";
      static final int TRANSACTION_getAccountId = 3;
      static final int TRANSACTION_onStanzaReceived = 1;
      static final int TRANSACTION_onStanzaResponse = 2;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.ISessionStanzaListener");
      }

      public static ISessionStanzaListener asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.ISessionStanzaListener");
            if(var2 != null && var2 instanceof ISessionStanzaListener) {
               var1 = (ISessionStanzaListener)var2;
            } else {
               var1 = new ISessionStanzaListener.Stub.Proxy(var0);
            }
         }

         return (ISessionStanzaListener)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.ISessionStanzaListener");
            String var6 = var2.readString();
            String var7 = var2.readString();
            this.onStanzaReceived(var6, var7);
            var3.writeNoException();
            break;
         case 2:
            var2.enforceInterface("com.google.android.gtalkservice.ISessionStanzaListener");
            String var8 = var2.readString();
            String var9 = var2.readString();
            String var10 = var2.readString();
            this.onStanzaResponse(var8, var9, var10);
            var3.writeNoException();
            break;
         case 3:
            var2.enforceInterface("com.google.android.gtalkservice.ISessionStanzaListener");
            long var11 = this.getAccountId();
            var3.writeNoException();
            var3.writeLong(var11);
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.ISessionStanzaListener");
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements ISessionStanzaListener {

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
               var1.writeInterfaceToken("com.google.android.gtalkservice.ISessionStanzaListener");
               this.mRemote.transact(3, var1, var2, 0);
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
            return "com.google.android.gtalkservice.ISessionStanzaListener";
         }

         public void onStanzaReceived(String var1, String var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.google.android.gtalkservice.ISessionStanzaListener");
               var3.writeString(var1);
               var3.writeString(var2);
               this.mRemote.transact(1, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void onStanzaResponse(String var1, String var2, String var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.google.android.gtalkservice.ISessionStanzaListener");
               var4.writeString(var1);
               var4.writeString(var2);
               var4.writeString(var3);
               this.mRemote.transact(2, var4, var5, 0);
               var5.readException();
            } finally {
               var5.recycle();
               var4.recycle();
            }

         }
      }
   }
}
