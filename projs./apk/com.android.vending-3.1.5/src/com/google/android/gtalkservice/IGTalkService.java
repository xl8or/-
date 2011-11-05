package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gtalkservice.IGTalkConnection;
import com.google.android.gtalkservice.IGTalkConnectionListener;
import com.google.android.gtalkservice.IImSession;
import java.util.ArrayList;
import java.util.List;

public interface IGTalkService extends IInterface {

   void createGTalkConnection(String var1, IGTalkConnectionListener var2) throws RemoteException;

   void dismissAllNotifications() throws RemoteException;

   void dismissNotificationFor(String var1, long var2) throws RemoteException;

   void dismissNotificationsForAccount(long var1) throws RemoteException;

   List getActiveConnections() throws RemoteException;

   IGTalkConnection getConnectionForUser(String var1) throws RemoteException;

   IGTalkConnection getDefaultConnection() throws RemoteException;

   boolean getDeviceStorageLow() throws RemoteException;

   IImSession getImSessionForAccountId(long var1) throws RemoteException;

   String printDiagnostics() throws RemoteException;

   public abstract static class Stub extends Binder implements IGTalkService {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.IGTalkService";
      static final int TRANSACTION_createGTalkConnection = 1;
      static final int TRANSACTION_dismissAllNotifications = 6;
      static final int TRANSACTION_dismissNotificationFor = 8;
      static final int TRANSACTION_dismissNotificationsForAccount = 7;
      static final int TRANSACTION_getActiveConnections = 2;
      static final int TRANSACTION_getConnectionForUser = 3;
      static final int TRANSACTION_getDefaultConnection = 4;
      static final int TRANSACTION_getDeviceStorageLow = 9;
      static final int TRANSACTION_getImSessionForAccountId = 5;
      static final int TRANSACTION_printDiagnostics = 10;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.IGTalkService");
      }

      public static IGTalkService asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.IGTalkService");
            if(var2 != null && var2 instanceof IGTalkService) {
               var1 = (IGTalkService)var2;
            } else {
               var1 = new IGTalkService.Stub.Proxy(var0);
            }
         }

         return (IGTalkService)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         IBinder var5 = false;
         boolean var6 = true;
         IGTalkConnection var22;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            String var7 = var2.readString();
            IGTalkConnectionListener var8 = IGTalkConnectionListener.Stub.asInterface(var2.readStrongBinder());
            this.createGTalkConnection(var7, var8);
            break;
         case 2:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            List var9 = this.getActiveConnections();
            var3.writeNoException();
            var3.writeList(var9);
            break;
         case 3:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            String var10 = var2.readString();
            var22 = this.getConnectionForUser(var10);
            var3.writeNoException();
            if(var22 != null) {
               var5 = var22.asBinder();
            }

            var3.writeStrongBinder(var5);
            break;
         case 4:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            var22 = this.getDefaultConnection();
            var3.writeNoException();
            if(var22 != null) {
               var5 = var22.asBinder();
            }

            var3.writeStrongBinder(var5);
            break;
         case 5:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            long var12 = var2.readLong();
            IImSession var11 = this.getImSessionForAccountId(var12);
            var3.writeNoException();
            if(var11 != null) {
               var5 = var11.asBinder();
            }

            var3.writeStrongBinder(var5);
            break;
         case 6:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            this.dismissAllNotifications();
            break;
         case 7:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            long var14 = var2.readLong();
            this.dismissNotificationsForAccount(var14);
            break;
         case 8:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            String var16 = var2.readString();
            long var17 = var2.readLong();
            this.dismissNotificationFor(var16, var17);
            break;
         case 9:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            boolean var19 = this.getDeviceStorageLow();
            var3.writeNoException();
            byte var21;
            if(var19) {
               var21 = 1;
            } else {
               var21 = 0;
            }

            var3.writeInt(var21);
            break;
         case 10:
            var2.enforceInterface("com.google.android.gtalkservice.IGTalkService");
            String var20 = this.printDiagnostics();
            var3.writeNoException();
            var3.writeString(var20);
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.IGTalkService");
            break;
         default:
            var6 = super.onTransact(var1, var2, var3, var4);
         }

         return var6;
      }

      private static class Proxy implements IGTalkService {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public void createGTalkConnection(String var1, IGTalkConnectionListener var2) throws RemoteException {
            IBinder var3 = null;
            Parcel var4 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               var4.writeString(var1);
               if(var2 != null) {
                  var3 = var2.asBinder();
               }

               var4.writeStrongBinder(var3);
               this.mRemote.transact(1, var4, (Parcel)null, 1);
            } finally {
               var4.recycle();
            }

         }

         public void dismissAllNotifications() throws RemoteException {
            Parcel var1 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               this.mRemote.transact(6, var1, (Parcel)null, 1);
            } finally {
               var1.recycle();
            }

         }

         public void dismissNotificationFor(String var1, long var2) throws RemoteException {
            Parcel var4 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               var4.writeString(var1);
               var4.writeLong(var2);
               this.mRemote.transact(8, var4, (Parcel)null, 1);
            } finally {
               var4.recycle();
            }

         }

         public void dismissNotificationsForAccount(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               var3.writeLong(var1);
               this.mRemote.transact(7, var3, (Parcel)null, 1);
            } finally {
               var3.recycle();
            }

         }

         public List getActiveConnections() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var9 = false;

            ArrayList var5;
            try {
               var9 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               this.mRemote.transact(2, var1, var2, 0);
               var2.readException();
               ClassLoader var4 = this.getClass().getClassLoader();
               var5 = var2.readArrayList(var4);
               var9 = false;
            } finally {
               if(var9) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            var2.recycle();
            var1.recycle();
            return var5;
         }

         public IGTalkConnection getConnectionForUser(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();
            boolean var9 = false;

            IGTalkConnection var5;
            try {
               var9 = true;
               var2.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               var2.writeString(var1);
               this.mRemote.transact(3, var2, var3, 0);
               var3.readException();
               var5 = IGTalkConnection.Stub.asInterface(var3.readStrongBinder());
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

         public IGTalkConnection getDefaultConnection() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            IGTalkConnection var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               this.mRemote.transact(4, var1, var2, 0);
               var2.readException();
               var4 = IGTalkConnection.Stub.asInterface(var2.readStrongBinder());
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

         public boolean getDeviceStorageLow() throws RemoteException {
            boolean var1 = false;
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();
            boolean var8 = false;

            int var5;
            try {
               var8 = true;
               var2.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               this.mRemote.transact(9, var2, var3, 0);
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

         public IImSession getImSessionForAccountId(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();
            boolean var10 = false;

            IImSession var6;
            try {
               var10 = true;
               var3.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               var3.writeLong(var1);
               this.mRemote.transact(5, var3, var4, 0);
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
            return "com.google.android.gtalkservice.IGTalkService";
         }

         public String printDiagnostics() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gtalkservice.IGTalkService");
               this.mRemote.transact(10, var1, var2, 0);
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
      }
   }
}
