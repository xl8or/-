package com.seven.Z7.common.im;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.seven.Z7.common.im.Z7ImPresence;

public interface IZ7DeviceInstantMessagingAPI extends IInterface {

   void clearIMThread(int var1, String var2) throws RemoteException;

   void endChat(int var1) throws RemoteException;

   int getGatewayStatus(int var1) throws RemoteException;

   int getIMOperationMode(int var1) throws RemoteException;

   void getIMRoster(int var1) throws RemoteException;

   void getInstantMessageThread(int var1, String var2) throws RemoteException;

   boolean getSaveThreadFlag(int var1, String var2) throws RemoteException;

   Z7ImPresence getUserPresence(int var1) throws RemoteException;

   void modifyRosterEntry(int var1, String var2, String var3) throws RemoteException;

   void resetIMUnreadMessageCount(int var1, String var2) throws RemoteException;

   int sendIMPresenceUpdate(int var1, int var2, String var3, int var4, String var5) throws RemoteException;

   int sendIMPresenceUpdateAfterLogin(int var1, int var2, String var3, int var4, String var5, boolean var6) throws RemoteException;

   void sendInstantMessage(int var1, String var2, String var3) throws RemoteException;

   void setActiveChat(int var1, String var2, boolean var3) throws RemoteException;

   void setAvatar(int var1, String var2, byte[] var3) throws RemoteException;

   void setIMOperationMode(int var1, int var2) throws RemoteException;

   void setLoginInfo(int var1, boolean var2, boolean var3, String var4, boolean var5) throws RemoteException;

   void setSaveThreadFlag(int var1, String var2, boolean var3) throws RemoteException;

   void setUIStateActive(boolean var1) throws RemoteException;

   public abstract static class Stub extends Binder implements IZ7DeviceInstantMessagingAPI {

      private static final String DESCRIPTOR = "com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI";
      static final int TRANSACTION_clearIMThread = 13;
      static final int TRANSACTION_endChat = 16;
      static final int TRANSACTION_getGatewayStatus = 1;
      static final int TRANSACTION_getIMOperationMode = 2;
      static final int TRANSACTION_getIMRoster = 5;
      static final int TRANSACTION_getInstantMessageThread = 11;
      static final int TRANSACTION_getSaveThreadFlag = 14;
      static final int TRANSACTION_getUserPresence = 7;
      static final int TRANSACTION_modifyRosterEntry = 6;
      static final int TRANSACTION_resetIMUnreadMessageCount = 12;
      static final int TRANSACTION_sendIMPresenceUpdate = 8;
      static final int TRANSACTION_sendIMPresenceUpdateAfterLogin = 9;
      static final int TRANSACTION_sendInstantMessage = 10;
      static final int TRANSACTION_setActiveChat = 19;
      static final int TRANSACTION_setAvatar = 17;
      static final int TRANSACTION_setIMOperationMode = 3;
      static final int TRANSACTION_setLoginInfo = 4;
      static final int TRANSACTION_setSaveThreadFlag = 15;
      static final int TRANSACTION_setUIStateActive = 18;


      public Stub() {
         this.attachInterface(this, "com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
      }

      public static IZ7DeviceInstantMessagingAPI asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            if(var2 != null && var2 instanceof IZ7DeviceInstantMessagingAPI) {
               var1 = (IZ7DeviceInstantMessagingAPI)var2;
            } else {
               var1 = new IZ7DeviceInstantMessagingAPI.Stub.Proxy(var0);
            }
         }

         return (IZ7DeviceInstantMessagingAPI)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5;
         int var12;
         String var13;
         byte var14;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var6 = var2.readInt();
            int var7 = this.getGatewayStatus(var6);
            var3.writeNoException();
            var3.writeInt(var7);
            var5 = true;
            break;
         case 2:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var8 = var2.readInt();
            int var9 = this.getIMOperationMode(var8);
            var3.writeNoException();
            var3.writeInt(var9);
            var5 = true;
            break;
         case 3:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var10 = var2.readInt();
            int var11 = var2.readInt();
            this.setIMOperationMode(var10, var11);
            var3.writeNoException();
            var5 = true;
            break;
         case 4:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            var12 = var2.readInt();
            byte var50;
            if(var2.readInt() != 0) {
               var50 = 1;
            } else {
               var50 = 0;
            }

            if(var2.readInt() != 0) {
               var14 = 1;
            } else {
               var14 = 0;
            }

            String var51 = var2.readString();
            byte var52;
            if(var2.readInt() != 0) {
               var52 = 1;
            } else {
               var52 = 0;
            }

            this.setLoginInfo(var12, (boolean)var50, (boolean)var14, var51, (boolean)var52);
            var3.writeNoException();
            var5 = true;
            break;
         case 5:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var17 = var2.readInt();
            this.getIMRoster(var17);
            var3.writeNoException();
            var5 = true;
            break;
         case 6:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var18 = var2.readInt();
            String var19 = var2.readString();
            String var20 = var2.readString();
            this.modifyRosterEntry(var18, var19, var20);
            var3.writeNoException();
            var5 = true;
            break;
         case 7:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var21 = var2.readInt();
            Z7ImPresence var22 = this.getUserPresence(var21);
            var3.writeNoException();
            if(var22 != null) {
               var3.writeInt(1);
               var22.writeToParcel(var3, 1);
            } else {
               var3.writeInt(0);
            }

            var5 = true;
            break;
         case 8:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var23 = var2.readInt();
            int var24 = var2.readInt();
            String var25 = var2.readString();
            int var26 = var2.readInt();
            String var27 = var2.readString();
            int var28 = this.sendIMPresenceUpdate(var23, var24, var25, var26, var27);
            var3.writeNoException();
            var3.writeInt(var28);
            var5 = true;
            break;
         case 9:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            var12 = var2.readInt();
            int var49 = var2.readInt();
            String var53 = var2.readString();
            int var15 = var2.readInt();
            String var16 = var2.readString();
            byte var29;
            if(var2.readInt() != 0) {
               var29 = 1;
            } else {
               var29 = 0;
            }

            int var30 = this.sendIMPresenceUpdateAfterLogin(var12, var49, var53, var15, var16, (boolean)var29);
            var3.writeNoException();
            var3.writeInt(var30);
            var5 = true;
            break;
         case 10:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var31 = var2.readInt();
            String var32 = var2.readString();
            String var33 = var2.readString();
            this.sendInstantMessage(var31, var32, var33);
            var3.writeNoException();
            var5 = true;
            break;
         case 11:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var34 = var2.readInt();
            String var35 = var2.readString();
            this.getInstantMessageThread(var34, var35);
            var3.writeNoException();
            var5 = true;
            break;
         case 12:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var36 = var2.readInt();
            String var37 = var2.readString();
            this.resetIMUnreadMessageCount(var36, var37);
            var3.writeNoException();
            var5 = true;
            break;
         case 13:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var38 = var2.readInt();
            String var39 = var2.readString();
            this.clearIMThread(var38, var39);
            var3.writeNoException();
            var5 = true;
            break;
         case 14:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var40 = var2.readInt();
            String var41 = var2.readString();
            boolean var42 = this.getSaveThreadFlag(var40, var41);
            var3.writeNoException();
            byte var43;
            if(var42) {
               var43 = 1;
            } else {
               var43 = 0;
            }

            var3.writeInt(var43);
            var5 = true;
            break;
         case 15:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            var12 = var2.readInt();
            var13 = var2.readString();
            if(var2.readInt() != 0) {
               var14 = 1;
            } else {
               var14 = 0;
            }

            this.setSaveThreadFlag(var12, var13, (boolean)var14);
            var3.writeNoException();
            var5 = true;
            break;
         case 16:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var44 = var2.readInt();
            this.endChat(var44);
            var3.writeNoException();
            var5 = true;
            break;
         case 17:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            int var45 = var2.readInt();
            String var46 = var2.readString();
            byte[] var47 = var2.createByteArray();
            this.setAvatar(var45, var46, var47);
            var3.writeNoException();
            var5 = true;
            break;
         case 18:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            byte var48;
            if(var2.readInt() != 0) {
               var48 = 1;
            } else {
               var48 = 0;
            }

            this.setUIStateActive((boolean)var48);
            var3.writeNoException();
            var5 = true;
            break;
         case 19:
            var2.enforceInterface("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            var12 = var2.readInt();
            var13 = var2.readString();
            if(var2.readInt() != 0) {
               var14 = 1;
            } else {
               var14 = 0;
            }

            this.setActiveChat(var12, var13, (boolean)var14);
            var3.writeNoException();
            var5 = true;
            break;
         case 1598968902:
            var3.writeString("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
            var5 = true;
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IZ7DeviceInstantMessagingAPI {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public void clearIMThread(int var1, String var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var3.writeInt(var1);
               var3.writeString(var2);
               this.mRemote.transact(13, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void endChat(int var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var2.writeInt(var1);
               this.mRemote.transact(16, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }

         public int getGatewayStatus(int var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();
            boolean var9 = false;

            int var5;
            try {
               var9 = true;
               var2.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var2.writeInt(var1);
               this.mRemote.transact(1, var2, var3, 0);
               var3.readException();
               var5 = var3.readInt();
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

         public int getIMOperationMode(int var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();
            boolean var9 = false;

            int var5;
            try {
               var9 = true;
               var2.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var2.writeInt(var1);
               this.mRemote.transact(2, var2, var3, 0);
               var3.readException();
               var5 = var3.readInt();
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

         public void getIMRoster(int var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var2.writeInt(var1);
               this.mRemote.transact(5, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }

         public void getInstantMessageThread(int var1, String var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var3.writeInt(var1);
               var3.writeString(var2);
               this.mRemote.transact(11, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public String getInterfaceDescriptor() {
            return "com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI";
         }

         public boolean getSaveThreadFlag(int var1, String var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();
            boolean var10 = false;

            int var6;
            try {
               var10 = true;
               var3.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var3.writeInt(var1);
               var3.writeString(var2);
               this.mRemote.transact(14, var3, var4, 0);
               var4.readException();
               var6 = var4.readInt();
               var10 = false;
            } finally {
               if(var10) {
                  var4.recycle();
                  var3.recycle();
               }
            }

            boolean var7;
            if(var6 != 0) {
               var7 = true;
            } else {
               var7 = false;
            }

            var4.recycle();
            var3.recycle();
            return var7;
         }

         public Z7ImPresence getUserPresence(int var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();
            boolean var8 = false;

            Z7ImPresence var5;
            label36: {
               try {
                  var8 = true;
                  var2.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
                  var2.writeInt(var1);
                  this.mRemote.transact(7, var2, var3, 0);
                  var3.readException();
                  if(var3.readInt() != 0) {
                     var5 = (Z7ImPresence)Z7ImPresence.CREATOR.createFromParcel(var3);
                     var8 = false;
                     break label36;
                  }

                  var8 = false;
               } finally {
                  if(var8) {
                     var3.recycle();
                     var2.recycle();
                  }
               }

               var5 = null;
            }

            var3.recycle();
            var2.recycle();
            return var5;
         }

         public void modifyRosterEntry(int var1, String var2, String var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var4.writeInt(var1);
               var4.writeString(var2);
               var4.writeString(var3);
               this.mRemote.transact(6, var4, var5, 0);
               var5.readException();
            } finally {
               var5.recycle();
               var4.recycle();
            }

         }

         public void resetIMUnreadMessageCount(int var1, String var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var3.writeInt(var1);
               var3.writeString(var2);
               this.mRemote.transact(12, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public int sendIMPresenceUpdate(int var1, int var2, String var3, int var4, String var5) throws RemoteException {
            Parcel var6 = Parcel.obtain();
            Parcel var7 = Parcel.obtain();
            boolean var13 = false;

            int var9;
            try {
               var13 = true;
               var6.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var6.writeInt(var1);
               var6.writeInt(var2);
               var6.writeString(var3);
               var6.writeInt(var4);
               var6.writeString(var5);
               this.mRemote.transact(8, var6, var7, 0);
               var7.readException();
               var9 = var7.readInt();
               var13 = false;
            } finally {
               if(var13) {
                  var7.recycle();
                  var6.recycle();
               }
            }

            var7.recycle();
            var6.recycle();
            return var9;
         }

         public int sendIMPresenceUpdateAfterLogin(int param1, int param2, String param3, int param4, String param5, boolean param6) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void sendInstantMessage(int var1, String var2, String var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var4.writeInt(var1);
               var4.writeString(var2);
               var4.writeString(var3);
               this.mRemote.transact(10, var4, var5, 0);
               var5.readException();
            } finally {
               var5.recycle();
               var4.recycle();
            }

         }

         public void setActiveChat(int param1, String param2, boolean param3) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void setAvatar(int var1, String var2, byte[] var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var4.writeInt(var1);
               var4.writeString(var2);
               var4.writeByteArray(var3);
               this.mRemote.transact(17, var4, var5, 0);
               var5.readException();
            } finally {
               var5.recycle();
               var4.recycle();
            }

         }

         public void setIMOperationMode(int var1, int var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.seven.Z7.common.im.IZ7DeviceInstantMessagingAPI");
               var3.writeInt(var1);
               var3.writeInt(var2);
               this.mRemote.transact(3, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void setLoginInfo(int param1, boolean param2, boolean param3, String param4, boolean param5) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void setSaveThreadFlag(int param1, String param2, boolean param3) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void setUIStateActive(boolean param1) throws RemoteException {
            // $FF: Couldn't be decompiled
         }
      }
   }
}
