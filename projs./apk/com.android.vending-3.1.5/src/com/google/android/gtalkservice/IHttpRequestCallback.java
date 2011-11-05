package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHttpRequestCallback extends IInterface {

   void requestComplete(byte[] var1) throws RemoteException;

   public abstract static class Stub extends Binder implements IHttpRequestCallback {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.IHttpRequestCallback";
      static final int TRANSACTION_requestComplete = 1;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.IHttpRequestCallback");
      }

      public static IHttpRequestCallback asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.IHttpRequestCallback");
            if(var2 != null && var2 instanceof IHttpRequestCallback) {
               var1 = (IHttpRequestCallback)var2;
            } else {
               var1 = new IHttpRequestCallback.Stub.Proxy(var0);
            }
         }

         return (IHttpRequestCallback)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.IHttpRequestCallback");
            byte[] var6 = var2.createByteArray();
            this.requestComplete(var6);
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.IHttpRequestCallback");
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IHttpRequestCallback {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public String getInterfaceDescriptor() {
            return "com.google.android.gtalkservice.IHttpRequestCallback";
         }

         public void requestComplete(byte[] var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.google.android.gtalkservice.IHttpRequestCallback");
               var2.writeByteArray(var1);
               this.mRemote.transact(1, var2, (Parcel)null, 1);
            } finally {
               var2.recycle();
            }

         }
      }
   }
}
