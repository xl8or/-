package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gtalkservice.ConnectionError;
import com.google.android.gtalkservice.ConnectionState;

public interface IConnectionStateListener extends IInterface {

   void connectionStateChanged(ConnectionState var1, ConnectionError var2, long var3, String var5) throws RemoteException;

   public abstract static class Stub extends Binder implements IConnectionStateListener {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.IConnectionStateListener";
      static final int TRANSACTION_connectionStateChanged = 1;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.IConnectionStateListener");
      }

      public static IConnectionStateListener asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.IConnectionStateListener");
            if(var2 != null && var2 instanceof IConnectionStateListener) {
               var1 = (IConnectionStateListener)var2;
            } else {
               var1 = new IConnectionStateListener.Stub.Proxy(var0);
            }
         }

         return (IConnectionStateListener)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.IConnectionStateListener");
            ConnectionState var6;
            if(var2.readInt() != 0) {
               var6 = (ConnectionState)ConnectionState.CREATOR.createFromParcel(var2);
            } else {
               var6 = null;
            }

            ConnectionError var7;
            if(var2.readInt() != 0) {
               var7 = (ConnectionError)ConnectionError.CREATOR.createFromParcel(var2);
            } else {
               var7 = null;
            }

            long var8 = var2.readLong();
            String var10 = var2.readString();
            this.connectionStateChanged(var6, var7, var8, var10);
            var3.writeNoException();
            var5 = true;
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.IConnectionStateListener");
            var5 = true;
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IConnectionStateListener {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public void connectionStateChanged(ConnectionState param1, ConnectionError param2, long param3, String param5) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public String getInterfaceDescriptor() {
            return "com.google.android.gtalkservice.IConnectionStateListener";
         }
      }
   }
}
