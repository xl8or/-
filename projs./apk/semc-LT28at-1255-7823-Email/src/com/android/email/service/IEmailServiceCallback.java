package com.android.email.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IEmailServiceCallback extends IInterface {

   void loadAttachmentStatus(long var1, long var3, int var5, int var6) throws RemoteException;

   void sendMessageStatus(long var1, String var3, int var4, int var5) throws RemoteException;

   void syncMailboxListStatus(long var1, int var3, int var4) throws RemoteException;

   void syncMailboxStatus(long var1, int var3, int var4) throws RemoteException;

   public abstract static class Stub extends Binder implements IEmailServiceCallback {

      private static final String DESCRIPTOR = "com.android.email.service.IEmailServiceCallback";
      static final int TRANSACTION_loadAttachmentStatus = 3;
      static final int TRANSACTION_sendMessageStatus = 4;
      static final int TRANSACTION_syncMailboxListStatus = 1;
      static final int TRANSACTION_syncMailboxStatus = 2;


      public Stub() {
         this.attachInterface(this, "com.android.email.service.IEmailServiceCallback");
      }

      public static IEmailServiceCallback asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.android.email.service.IEmailServiceCallback");
            if(var2 != null && var2 instanceof IEmailServiceCallback) {
               var1 = (IEmailServiceCallback)var2;
            } else {
               var1 = new IEmailServiceCallback.Stub.Proxy(var0);
            }
         }

         return (IEmailServiceCallback)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5;
         switch(var1) {
         case 1:
            String var9 = "com.android.email.service.IEmailServiceCallback";
            var2.enforceInterface(var9);
            long var10 = var2.readLong();
            int var12 = var2.readInt();
            int var13 = var2.readInt();
            this.syncMailboxListStatus(var10, var12, var13);
            var5 = true;
            break;
         case 2:
            String var15 = "com.android.email.service.IEmailServiceCallback";
            var2.enforceInterface(var15);
            long var16 = var2.readLong();
            int var18 = var2.readInt();
            int var19 = var2.readInt();
            this.syncMailboxStatus(var16, var18, var19);
            var5 = true;
            break;
         case 3:
            String var21 = "com.android.email.service.IEmailServiceCallback";
            var2.enforceInterface(var21);
            long var22 = var2.readLong();
            long var24 = var2.readLong();
            int var26 = var2.readInt();
            int var27 = var2.readInt();
            this.loadAttachmentStatus(var22, var24, var26, var27);
            var5 = true;
            break;
         case 4:
            String var29 = "com.android.email.service.IEmailServiceCallback";
            var2.enforceInterface(var29);
            long var30 = var2.readLong();
            String var32 = var2.readString();
            int var33 = var2.readInt();
            int var34 = var2.readInt();
            this.sendMessageStatus(var30, var32, var33, var34);
            var5 = true;
            break;
         case 1598968902:
            String var7 = "com.android.email.service.IEmailServiceCallback";
            var3.writeString(var7);
            var5 = true;
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IEmailServiceCallback {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public String getInterfaceDescriptor() {
            return "com.android.email.service.IEmailServiceCallback";
         }

         public void loadAttachmentStatus(long var1, long var3, int var5, int var6) throws RemoteException {
            Parcel var7 = Parcel.obtain();

            try {
               var7.writeInterfaceToken("com.android.email.service.IEmailServiceCallback");
               var7.writeLong(var1);
               var7.writeLong(var3);
               var7.writeInt(var5);
               var7.writeInt(var6);
               this.mRemote.transact(3, var7, (Parcel)null, 1);
            } finally {
               var7.recycle();
            }

         }

         public void sendMessageStatus(long var1, String var3, int var4, int var5) throws RemoteException {
            Parcel var6 = Parcel.obtain();

            try {
               var6.writeInterfaceToken("com.android.email.service.IEmailServiceCallback");
               var6.writeLong(var1);
               var6.writeString(var3);
               var6.writeInt(var4);
               var6.writeInt(var5);
               this.mRemote.transact(4, var6, (Parcel)null, 1);
            } finally {
               var6.recycle();
            }

         }

         public void syncMailboxListStatus(long var1, int var3, int var4) throws RemoteException {
            Parcel var5 = Parcel.obtain();

            try {
               var5.writeInterfaceToken("com.android.email.service.IEmailServiceCallback");
               var5.writeLong(var1);
               var5.writeInt(var3);
               var5.writeInt(var4);
               this.mRemote.transact(1, var5, (Parcel)null, 1);
            } finally {
               var5.recycle();
            }

         }

         public void syncMailboxStatus(long var1, int var3, int var4) throws RemoteException {
            Parcel var5 = Parcel.obtain();

            try {
               var5.writeInterfaceToken("com.android.email.service.IEmailServiceCallback");
               var5.writeLong(var1);
               var5.writeInt(var3);
               var5.writeInt(var4);
               this.mRemote.transact(2, var5, (Parcel)null, 1);
            } finally {
               var5.recycle();
            }

         }
      }
   }
}
