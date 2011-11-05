package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gtalkservice.IHttpRequestCallback;
import com.google.android.gtalkservice.IImSession;

public interface IGTalkConnection extends IInterface {

   void clearConnectionStatistics() throws RemoteException;

   IImSession createImSession() throws RemoteException;

   int getConnectionUptime() throws RemoteException;

   IImSession getDefaultImSession() throws RemoteException;

   String getDeviceId() throws RemoteException;

   IImSession getImSessionForAccountId(long var1) throws RemoteException;

   String getJid() throws RemoteException;

   long getLastActivityFromServerTime() throws RemoteException;

   long getLastActivityToServerTime() throws RemoteException;

   int getNumberOfConnectionsAttempted() throws RemoteException;

   int getNumberOfConnectionsMade() throws RemoteException;

   String getUsername() throws RemoteException;

   boolean isConnected() throws RemoteException;

   void sendHeartbeat() throws RemoteException;

   void sendHttpRequest(byte[] var1, IHttpRequestCallback var2) throws RemoteException;

   public abstract static class Stub extends Binder implements IGTalkConnection {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.IGTalkConnection";
      static final int TRANSACTION_clearConnectionStatistics = 13;
      static final int TRANSACTION_createImSession = 5;
      static final int TRANSACTION_getConnectionUptime = 12;
      static final int TRANSACTION_getDefaultImSession = 7;
      static final int TRANSACTION_getDeviceId = 3;
      static final int TRANSACTION_getImSessionForAccountId = 6;
      static final int TRANSACTION_getJid = 2;
      static final int TRANSACTION_getLastActivityFromServerTime = 8;
      static final int TRANSACTION_getLastActivityToServerTime = 9;
      static final int TRANSACTION_getNumberOfConnectionsAttempted = 11;
      static final int TRANSACTION_getNumberOfConnectionsMade = 10;
      static final int TRANSACTION_getUsername = 1;
      static final int TRANSACTION_isConnected = 4;
      static final int TRANSACTION_sendHeartbeat = 15;
      static final int TRANSACTION_sendHttpRequest = 14;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.IGTalkConnection");
      }

      public static IGTalkConnection asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.IGTalkConnection");
            if(var2 != null && var2 instanceof IGTalkConnection) {
               var1 = (IGTalkConnection)var2;
            } else {
               var1 = new IGTalkConnection.Stub.Proxy(var0);
            }
         }

         return (IGTalkConnection)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         IBinder var5 = null;
         boolean var6 = true;
         IImSession var11;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            String var7 = this.getUsername();
            var3.writeNoException();
            var3.writeString(var7);
            break;
         case 2:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            String var8 = this.getJid();
            var3.writeNoException();
            var3.writeString(var8);
            break;
         case 3:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            String var9 = this.getDeviceId();
            var3.writeNoException();
            var3.writeString(var9);
            break;
         case 4:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            boolean var10 = this.isConnected();
            var3.writeNoException();
            byte var23;
            if(var10) {
               var23 = 1;
            } else {
               var23 = 0;
            }

            var3.writeInt(var23);
            break;
         case 5:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            var11 = this.createImSession();
            var3.writeNoException();
            if(var11 != null) {
               var5 = var11.asBinder();
            }

            var3.writeStrongBinder(var5);
            break;
         case 6:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            long var12 = var2.readLong();
            var11 = this.getImSessionForAccountId(var12);
            var3.writeNoException();
            if(var11 != null) {
               var5 = var11.asBinder();
            }

            var3.writeStrongBinder(var5);
            break;
         case 7:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            var11 = this.getDefaultImSession();
            var3.writeNoException();
            if(var11 != null) {
               var5 = var11.asBinder();
            }

            var3.writeStrongBinder(var5);
            break;
         case 8:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            long var14 = this.getLastActivityFromServerTime();
            var3.writeNoException();
            var3.writeLong(var14);
            break;
         case 9:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            long var16 = this.getLastActivityToServerTime();
            var3.writeNoException();
            var3.writeLong(var16);
            break;
         case 10:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            int var18 = this.getNumberOfConnectionsMade();
            var3.writeNoException();
            var3.writeInt(var18);
            break;
         case 11:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            int var19 = this.getNumberOfConnectionsAttempted();
            var3.writeNoException();
            var3.writeInt(var19);
            break;
         case 12:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            int var20 = this.getConnectionUptime();
            var3.writeNoException();
            var3.writeInt(var20);
            break;
         case 13:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            this.clearConnectionStatistics();
            break;
         case 14:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            byte[] var21 = var2.createByteArray();
            IHttpRequestCallback var22 = IHttpRequestCallback.Stub.asInterface(var2.readStrongBinder());
            this.sendHttpRequest(var21, var22);
            var3.writeNoException();
            break;
         case 15:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkConnection");
            this.sendHeartbeat();
            var3.writeNoException();
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.IGTalkConnection");
            break;
         default:
            var6 = super.onTransact(var1, var2, var3, var4);
         }

         return var6;
      }

      private static class Proxy implements IGTalkConnection {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public void clearConnectionStatistics() throws RemoteException {
            Parcel var1 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(13, var1, (Parcel)null, 1);
            } finally {
               var1.recycle();
            }

         }

         public IImSession createImSession() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            IImSession var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(5, var1, var2, 0);
               var2.readException();
               var4 = IImSession.Stub.asInterface(var2.readStrongBinder());
               var8 = false;
            } finally {
               if(var8) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var4;
         }

         public int getConnectionUptime() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            int var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(12, var1, var2, 0);
               var2.readException();
               var4 = var2.readInt();
               var8 = false;
            } finally {
               if(var8) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var4;
         }

         public IImSession getDefaultImSession() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            IImSession var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(7, var1, var2, 0);
               var2.readException();
               var4 = IImSession.Stub.asInterface(var2.readStrongBinder());
               var8 = false;
            } finally {
               if(var8) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var4;
         }

         public String getDeviceId() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(3, var1, var2, 0);
               var2.readException();
               var4 = var2.readString();
               var8 = false;
            } finally {
               if(var8) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var4;
         }

         public IImSession getImSessionForAccountId(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();
            boolean var10 = false;

            IImSession var6;
            try {
               var10 = true;
               var3.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               var3.writeLong(var1);
               this.mRemote.transact(6, var3, var4, 0);
               var4.readException();
               var6 = IImSession.Stub.asInterface(var4.readStrongBinder());
               var10 = false;
            } finally {
               if(var10) {
                  var4.recycle();
                  var3.recycle();
               }
            }

            var4.recycle();
            var3.recycle();
            return var6;
         }

         public String getInterfaceDescriptor() {
            return "com.google.android.gtalkservice.IGTalkConnection";
         }

         public String getJid() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(2, var1, var2, 0);
               var2.readException();
               var4 = var2.readString();
               var8 = false;
            } finally {
               if(var8) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var4;
         }

         public long getLastActivityFromServerTime() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var10 = false;

            long var4;
            try {
               var10 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(8, var1, var2, 0);
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

         public long getLastActivityToServerTime() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var10 = false;

            long var4;
            try {
               var10 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(9, var1, var2, 0);
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

         public int getNumberOfConnectionsAttempted() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            int var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(11, var1, var2, 0);
               var2.readException();
               var4 = var2.readInt();
               var8 = false;
            } finally {
               if(var8) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var4;
         }

         public int getNumberOfConnectionsMade() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            int var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(10, var1, var2, 0);
               var2.readException();
               var4 = var2.readInt();
               var8 = false;
            } finally {
               if(var8) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var4;
         }

         public String getUsername() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(1, var1, var2, 0);
               var2.readException();
               var4 = var2.readString();
               var8 = false;
            } finally {
               if(var8) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var4;
         }

         public boolean isConnected() throws RemoteException {
            boolean var1 = false;
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();
            boolean var8 = false;

            int var5;
            try {
               var8 = true;
               var2.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(4, var2, var3, 0);
               var3.readException();
               var5 = var3.readInt();
               var8 = false;
            } finally {
               if(var8) {
                  var3.recycle();
                  var2.recycle();
               }
            }

            if(var5 != 0) {
               var1 = true;
            }

            var3.recycle();
            var2.recycle();
            return var1;
         }

         public void sendHeartbeat() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkConnection");
               this.mRemote.transact(15, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }

         public void sendHttpRequest(byte[] param1, IHttpRequestCallback param2) throws RemoteException {
            // $FF: Couldn't be decompiled
         }
      }
   }
}
