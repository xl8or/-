package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IChatListener extends IInterface {

   void callEnded() throws RemoteException;

   void chatClosed(String var1) throws RemoteException;

   void chatRead(String var1) throws RemoteException;

   void convertedToGroupChat(String var1, String var2, long var3) throws RemoteException;

   void missedCall() throws RemoteException;

   void newMessageReceived(String var1, String var2, boolean var3) throws RemoteException;

   void newMessageSent(String var1) throws RemoteException;

   void participantJoined(String var1, String var2) throws RemoteException;

   void participantLeft(String var1, String var2) throws RemoteException;

   boolean useLightweightNotify() throws RemoteException;

   void willConvertToGroupChat(String var1, String var2, long var3) throws RemoteException;

   public abstract static class Stub extends Binder implements IChatListener {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.IChatListener";
      static final int TRANSACTION_callEnded = 4;
      static final int TRANSACTION_chatClosed = 6;
      static final int TRANSACTION_chatRead = 5;
      static final int TRANSACTION_convertedToGroupChat = 8;
      static final int TRANSACTION_missedCall = 3;
      static final int TRANSACTION_newMessageReceived = 1;
      static final int TRANSACTION_newMessageSent = 2;
      static final int TRANSACTION_participantJoined = 9;
      static final int TRANSACTION_participantLeft = 10;
      static final int TRANSACTION_useLightweightNotify = 11;
      static final int TRANSACTION_willConvertToGroupChat = 7;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.IChatListener");
      }

      public static IChatListener asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.IChatListener");
            if(var2 != null && var2 instanceof IChatListener) {
               var1 = (IChatListener)var2;
            } else {
               var1 = new IChatListener.Stub.Proxy(var0);
            }
         }

         return (IChatListener)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         byte var5 = 0;
         boolean var6 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            String var7 = var2.readString();
            String var8 = var2.readString();
            if(var2.readInt() != 0) {
               var5 = 1;
            }

            this.newMessageReceived(var7, var8, (boolean)var5);
            var3.writeNoException();
            break;
         case 2:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            String var9 = var2.readString();
            this.newMessageSent(var9);
            var3.writeNoException();
            break;
         case 3:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            this.missedCall();
            var3.writeNoException();
            break;
         case 4:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            this.callEnded();
            var3.writeNoException();
            break;
         case 5:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            String var10 = var2.readString();
            this.chatRead(var10);
            var3.writeNoException();
            break;
         case 6:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            String var11 = var2.readString();
            this.chatClosed(var11);
            var3.writeNoException();
            break;
         case 7:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            String var12 = var2.readString();
            String var13 = var2.readString();
            long var14 = var2.readLong();
            this.willConvertToGroupChat(var12, var13, var14);
            var3.writeNoException();
            break;
         case 8:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            String var16 = var2.readString();
            String var17 = var2.readString();
            long var18 = var2.readLong();
            this.convertedToGroupChat(var16, var17, var18);
            var3.writeNoException();
            break;
         case 9:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            String var20 = var2.readString();
            String var21 = var2.readString();
            this.participantJoined(var20, var21);
            var3.writeNoException();
            break;
         case 10:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            String var22 = var2.readString();
            String var23 = var2.readString();
            this.participantLeft(var22, var23);
            var3.writeNoException();
            break;
         case 11:
            var2.enforceInterface("com.google.android.gtalkservice.IChatListener");
            boolean var24 = this.useLightweightNotify();
            var3.writeNoException();
            if(var24) {
               var5 = 1;
            }

            var3.writeInt(var5);
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.IChatListener");
            break;
         default:
            var6 = super.onTransact(var1, var2, var3, var4);
         }

         return var6;
      }

      private static class Proxy implements IChatListener {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public void callEnded() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               this.mRemote.transact(4, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }

         public void chatClosed(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               var2.writeString(var1);
               this.mRemote.transact(6, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }

         public void chatRead(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               var2.writeString(var1);
               this.mRemote.transact(5, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }

         public void convertedToGroupChat(String var1, String var2, long var3) throws RemoteException {
            Parcel var5 = Parcel.obtain();
            Parcel var6 = Parcel.obtain();

            try {
               var5.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               var5.writeString(var1);
               var5.writeString(var2);
               var5.writeLong(var3);
               this.mRemote.transact(8, var5, var6, 0);
               var6.readException();
            } finally {
               var6.recycle();
               var5.recycle();
            }

         }

         public String getInterfaceDescriptor() {
            return "com.google.android.gtalkservice.IChatListener";
         }

         public void missedCall() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               this.mRemote.transact(3, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }

         public void newMessageReceived(String param1, String param2, boolean param3) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void newMessageSent(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               var2.writeString(var1);
               this.mRemote.transact(2, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }

         public void participantJoined(String var1, String var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               var3.writeString(var1);
               var3.writeString(var2);
               this.mRemote.transact(9, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void participantLeft(String var1, String var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               var3.writeString(var1);
               var3.writeString(var2);
               this.mRemote.transact(10, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public boolean useLightweightNotify() throws RemoteException {
            boolean var1 = false;
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();
            boolean var8 = false;

            int var5;
            try {
               var8 = true;
               var2.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               this.mRemote.transact(11, var2, var3, 0);
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

         public void willConvertToGroupChat(String var1, String var2, long var3) throws RemoteException {
            Parcel var5 = Parcel.obtain();
            Parcel var6 = Parcel.obtain();

            try {
               var5.writeInterfaceToken("com.google.android.gtalkservice.IChatListener");
               var5.writeString(var1);
               var5.writeString(var2);
               var5.writeLong(var3);
               this.mRemote.transact(7, var5, var6, 0);
               var6.readException();
            } finally {
               var6.recycle();
               var5.recycle();
            }

         }
      }
   }
}
