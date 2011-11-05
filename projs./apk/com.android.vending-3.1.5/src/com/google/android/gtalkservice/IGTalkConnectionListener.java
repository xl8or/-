package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gtalkservice.IGTalkConnection;

public interface IGTalkConnectionListener extends IInterface {

   void onConnectionCreated(IGTalkConnection var1) throws RemoteException;

   void onConnectionCreationFailed(String var1) throws RemoteException;

   public abstract static class Stub extends Binder implements IGTalkConnectionListener {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.IGTalkConnectionListener";
      static final int TRANSACTION_onConnectionCreated = 1;
      static final int TRANSACTION_onConnectionCreationFailed = 2;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.IGTalkConnectionListener");
      }

      public static IGTalkConnectionListener asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.IGTalkConnectionListener");
            if(var2 != null && var2 instanceof IGTalkConnectionListener) {
               var1 = (IGTalkConnectionListener)var2;
            } else {
               var1 = new IGTalkConnectionListener.Stub.Proxy(var0);
            }
         }

         return (IGTalkConnectionListener)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnectionListener");
            IGTalkConnection var6 = IGTalkConnection.Stub.asInterface(var2.readStrongBinder());
            this.onConnectionCreated(var6);
            var3.writeNoException();
            break;
         case 2:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnectionListener");
            String var7 = var2.readString();
            this.onConnectionCreationFailed(var7);
            var3.writeNoException();
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.IGTalkConnectionListener");
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IGTalkConnectionListener {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public String getInterfaceDescriptor() {
            return "com.google.android.gtalkservice.IGTalkConnectionListener";
         }

         public void onConnectionCreated(IGTalkConnection param1) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void onConnectionCreationFailed(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnectionListener");
               var2.writeString(var1);
               this.mRemote.transact(2, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }
      }
   }
}
