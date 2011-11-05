package com.android.email.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.Parcelable.Creator;
import com.android.email.service.IEmailServiceCallback;
import com.android.exchange.OoODataList;
import java.util.ArrayList;
import java.util.List;

public interface IEmailService extends IInterface {

   void MoveMessage(List<String> var1, long var2, long var4, long var6) throws RemoteException;

   void OoOffice(long var1, OoODataList var3) throws RemoteException;

   Bundle autoDiscover(String var1, String var2, String var3, boolean var4) throws RemoteException;

   boolean createFolder(long var1, String var3) throws RemoteException;

   boolean deleteFolder(long var1, String var3) throws RemoteException;

   void emptyTrash(long var1) throws RemoteException;

   void folderCreate(long var1, String var3, long var4) throws RemoteException;

   void hostChanged(long var1) throws RemoteException;

   void loadAttachment(long var1, String var3, String var4) throws RemoteException;

   void loadMore(long var1) throws RemoteException;

   void moveConversationAlways(long var1, long var3, byte[] var5, int var6) throws RemoteException;

   void refreshIRMTemplates(long var1) throws RemoteException;

   boolean renameFolder(long var1, String var3, String var4) throws RemoteException;

   void sendMeetingResponse(long var1, int var3) throws RemoteException;

   void sendRecoveryPassword(long var1, String var3) throws RemoteException;

   void setCallback(IEmailServiceCallback var1) throws RemoteException;

   void setDeviceInfo(long var1) throws RemoteException;

   void setLogging(int var1) throws RemoteException;

   void startSync(long var1) throws RemoteException;

   void stopSync(long var1) throws RemoteException;

   void updateFolderList(long var1) throws RemoteException;

   int validate(String var1, String var2, String var3, String var4, int var5, boolean var6, boolean var7) throws RemoteException;

   public abstract static class Stub extends Binder implements IEmailService {

      private static final String DESCRIPTOR = "com.android.email.service.IEmailService";
      static final int TRANSACTION_MoveMessage = 18;
      static final int TRANSACTION_OoOffice = 17;
      static final int TRANSACTION_autoDiscover = 14;
      static final int TRANSACTION_createFolder = 8;
      static final int TRANSACTION_deleteFolder = 9;
      static final int TRANSACTION_emptyTrash = 6;
      static final int TRANSACTION_folderCreate = 19;
      static final int TRANSACTION_hostChanged = 13;
      static final int TRANSACTION_loadAttachment = 5;
      static final int TRANSACTION_loadMore = 4;
      static final int TRANSACTION_moveConversationAlways = 20;
      static final int TRANSACTION_refreshIRMTemplates = 22;
      static final int TRANSACTION_renameFolder = 10;
      static final int TRANSACTION_sendMeetingResponse = 15;
      static final int TRANSACTION_sendRecoveryPassword = 16;
      static final int TRANSACTION_setCallback = 11;
      static final int TRANSACTION_setDeviceInfo = 21;
      static final int TRANSACTION_setLogging = 12;
      static final int TRANSACTION_startSync = 2;
      static final int TRANSACTION_stopSync = 3;
      static final int TRANSACTION_updateFolderList = 7;
      static final int TRANSACTION_validate = 1;


      public Stub() {
         this.attachInterface(this, "com.android.email.service.IEmailService");
      }

      public static IEmailService asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.android.email.service.IEmailService");
            if(var2 != null && var2 instanceof IEmailService) {
               var1 = (IEmailService)var2;
            } else {
               var1 = new IEmailService.Stub.Proxy(var0);
            }
         }

         return (IEmailService)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5;
         String var10;
         String var12;
         String var214;
         switch(var1) {
         case 1:
            String var9 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var9);
            var10 = var2.readString();
            var214 = var2.readString();
            var12 = var2.readString();
            String var215 = var2.readString();
            int var14 = var2.readInt();
            byte var15;
            if(var2.readInt() != 0) {
               var15 = 1;
            } else {
               var15 = 0;
            }

            byte var16;
            if(var2.readInt() != 0) {
               var16 = 1;
            } else {
               var16 = 0;
            }

            int var17 = this.validate(var10, var214, var12, var215, var14, (boolean)var15, (boolean)var16);
            var3.writeNoException();
            var3.writeInt(var17);
            var5 = true;
            break;
         case 2:
            String var21 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var21);
            long var22 = var2.readLong();
            this.startSync(var22);
            var3.writeNoException();
            var5 = true;
            break;
         case 3:
            String var28 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var28);
            long var29 = var2.readLong();
            this.stopSync(var29);
            var3.writeNoException();
            var5 = true;
            break;
         case 4:
            String var35 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var35);
            long var36 = var2.readLong();
            this.loadMore(var36);
            var3.writeNoException();
            var5 = true;
            break;
         case 5:
            String var42 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var42);
            long var43 = var2.readLong();
            String var45 = var2.readString();
            String var46 = var2.readString();
            this.loadAttachment(var43, var45, var46);
            var3.writeNoException();
            var5 = true;
            break;
         case 6:
            String var53 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var53);
            long var54 = var2.readLong();
            this.emptyTrash(var54);
            var3.writeNoException();
            var5 = true;
            break;
         case 7:
            String var60 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var60);
            long var61 = var2.readLong();
            this.updateFolderList(var61);
            var3.writeNoException();
            var5 = true;
            break;
         case 8:
            String var67 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var67);
            long var68 = var2.readLong();
            String var70 = var2.readString();
            boolean var75 = this.createFolder(var68, var70);
            var3.writeNoException();
            byte var76;
            if(var75) {
               var76 = 1;
            } else {
               var76 = 0;
            }

            var3.writeInt(var76);
            var5 = true;
            break;
         case 9:
            String var80 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var80);
            long var81 = var2.readLong();
            String var83 = var2.readString();
            boolean var88 = this.deleteFolder(var81, var83);
            var3.writeNoException();
            byte var89;
            if(var88) {
               var89 = 1;
            } else {
               var89 = 0;
            }

            var3.writeInt(var89);
            var5 = true;
            break;
         case 10:
            String var93 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var93);
            long var94 = var2.readLong();
            String var96 = var2.readString();
            String var97 = var2.readString();
            boolean var103 = this.renameFolder(var94, var96, var97);
            var3.writeNoException();
            byte var104;
            if(var103) {
               var104 = 1;
            } else {
               var104 = 0;
            }

            var3.writeInt(var104);
            var5 = true;
            break;
         case 11:
            String var108 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var108);
            IEmailServiceCallback var109 = IEmailServiceCallback.Stub.asInterface(var2.readStrongBinder());
            this.setCallback(var109);
            var3.writeNoException();
            var5 = true;
            break;
         case 12:
            String var113 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var113);
            int var114 = var2.readInt();
            this.setLogging(var114);
            var3.writeNoException();
            var5 = true;
            break;
         case 13:
            String var118 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var118);
            long var119 = var2.readLong();
            this.hostChanged(var119);
            var3.writeNoException();
            var5 = true;
            break;
         case 14:
            String var125 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var125);
            var10 = var2.readString();
            var214 = var2.readString();
            var12 = var2.readString();
            byte var13;
            if(var2.readInt() != 0) {
               var13 = 1;
            } else {
               var13 = 0;
            }

            Bundle var131 = this.autoDiscover(var10, var214, var12, (boolean)var13);
            var3.writeNoException();
            if(var131 != null) {
               byte var133 = 1;
               var3.writeInt(var133);
               byte var136 = 1;
               var131.writeToParcel(var3, var136);
            } else {
               byte var138 = 0;
               var3.writeInt(var138);
            }

            var5 = true;
            break;
         case 15:
            String var140 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var140);
            long var141 = var2.readLong();
            int var143 = var2.readInt();
            this.sendMeetingResponse(var141, var143);
            var3.writeNoException();
            var5 = true;
            break;
         case 16:
            String var149 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var149);
            long var150 = var2.readLong();
            String var152 = var2.readString();
            this.sendRecoveryPassword(var150, var152);
            var3.writeNoException();
            var5 = true;
            break;
         case 17:
            String var158 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var158);
            long var159 = var2.readLong();
            OoODataList var11;
            if(var2.readInt() != 0) {
               Creator var161 = OoODataList.CREATOR;
               var11 = (OoODataList)var161.createFromParcel(var2);
            } else {
               var11 = null;
            }

            this.OoOffice(var159, var11);
            var3.writeNoException();
            var5 = true;
            break;
         case 18:
            String var168 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var168);
            ArrayList var169 = var2.createStringArrayList();
            long var170 = var2.readLong();
            long var172 = var2.readLong();
            long var174 = var2.readLong();
            this.MoveMessage(var169, var170, var172, var174);
            var3.writeNoException();
            var5 = true;
            break;
         case 19:
            String var177 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var177);
            long var178 = var2.readLong();
            String var180 = var2.readString();
            long var181 = var2.readLong();
            this.folderCreate(var178, var180, var181);
            var3.writeNoException();
            var5 = true;
            break;
         case 20:
            String var188 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var188);
            long var189 = var2.readLong();
            long var191 = var2.readLong();
            byte[] var193 = var2.createByteArray();
            int var194 = var2.readInt();
            this.moveConversationAlways(var189, var191, var193, var194);
            var3.writeNoException();
            var5 = true;
            break;
         case 21:
            String var201 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var201);
            long var202 = var2.readLong();
            this.setDeviceInfo(var202);
            var3.writeNoException();
            var5 = true;
            break;
         case 22:
            String var208 = "com.android.email.service.IEmailService";
            var2.enforceInterface(var208);
            long var209 = var2.readLong();
            this.refreshIRMTemplates(var209);
            var3.writeNoException();
            var5 = true;
            break;
         case 1598968902:
            String var7 = "com.android.email.service.IEmailService";
            var3.writeString(var7);
            var5 = true;
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IEmailService {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public void MoveMessage(List<String> var1, long var2, long var4, long var6) throws RemoteException {
            Parcel var8 = Parcel.obtain();
            Parcel var9 = Parcel.obtain();

            try {
               var8.writeInterfaceToken("com.android.email.service.IEmailService");
               var8.writeStringList(var1);
               var8.writeLong(var2);
               var8.writeLong(var4);
               var8.writeLong(var6);
               this.mRemote.transact(18, var8, var9, 0);
               var9.readException();
            } finally {
               var9.recycle();
               var8.recycle();
            }

         }

         public void OoOffice(long param1, OoODataList param3) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public Bundle autoDiscover(String param1, String param2, String param3, boolean param4) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public boolean createFolder(long var1, String var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();
            boolean var11 = false;

            int var7;
            try {
               var11 = true;
               var4.writeInterfaceToken("com.android.email.service.IEmailService");
               var4.writeLong(var1);
               var4.writeString(var3);
               this.mRemote.transact(8, var4, var5, 0);
               var5.readException();
               var7 = var5.readInt();
               var11 = false;
            } finally {
               if(var11) {
                  var5.recycle();
                  var4.recycle();
               }
            }

            boolean var8;
            if(var7 != 0) {
               var8 = true;
            } else {
               var8 = false;
            }

            var5.recycle();
            var4.recycle();
            return var8;
         }

         public boolean deleteFolder(long var1, String var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();
            boolean var11 = false;

            int var7;
            try {
               var11 = true;
               var4.writeInterfaceToken("com.android.email.service.IEmailService");
               var4.writeLong(var1);
               var4.writeString(var3);
               this.mRemote.transact(9, var4, var5, 0);
               var5.readException();
               var7 = var5.readInt();
               var11 = false;
            } finally {
               if(var11) {
                  var5.recycle();
                  var4.recycle();
               }
            }

            boolean var8;
            if(var7 != 0) {
               var8 = true;
            } else {
               var8 = false;
            }

            var5.recycle();
            var4.recycle();
            return var8;
         }

         public void emptyTrash(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.android.email.service.IEmailService");
               var3.writeLong(var1);
               this.mRemote.transact(6, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void folderCreate(long param1, String param3, long param4) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public String getInterfaceDescriptor() {
            return "com.android.email.service.IEmailService";
         }

         public void hostChanged(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.android.email.service.IEmailService");
               var3.writeLong(var1);
               this.mRemote.transact(13, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void loadAttachment(long var1, String var3, String var4) throws RemoteException {
            Parcel var5 = Parcel.obtain();
            Parcel var6 = Parcel.obtain();

            try {
               var5.writeInterfaceToken("com.android.email.service.IEmailService");
               var5.writeLong(var1);
               var5.writeString(var3);
               var5.writeString(var4);
               this.mRemote.transact(5, var5, var6, 0);
               var6.readException();
            } finally {
               var6.recycle();
               var5.recycle();
            }

         }

         public void loadMore(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.android.email.service.IEmailService");
               var3.writeLong(var1);
               this.mRemote.transact(4, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void moveConversationAlways(long var1, long var3, byte[] var5, int var6) throws RemoteException {
            Parcel var7 = Parcel.obtain();
            Parcel var8 = Parcel.obtain();

            try {
               var7.writeInterfaceToken("com.android.email.service.IEmailService");
               var7.writeLong(var1);
               var7.writeLong(var3);
               var7.writeByteArray(var5);
               var7.writeInt(var6);
               this.mRemote.transact(20, var7, var8, 0);
               var8.readException();
            } finally {
               var8.recycle();
               var7.recycle();
            }

         }

         public void refreshIRMTemplates(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.android.email.service.IEmailService");
               var3.writeLong(var1);
               this.mRemote.transact(22, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public boolean renameFolder(long var1, String var3, String var4) throws RemoteException {
            Parcel var5 = Parcel.obtain();
            Parcel var6 = Parcel.obtain();
            boolean var12 = false;

            int var8;
            try {
               var12 = true;
               var5.writeInterfaceToken("com.android.email.service.IEmailService");
               var5.writeLong(var1);
               var5.writeString(var3);
               var5.writeString(var4);
               this.mRemote.transact(10, var5, var6, 0);
               var6.readException();
               var8 = var6.readInt();
               var12 = false;
            } finally {
               if(var12) {
                  var6.recycle();
                  var5.recycle();
               }
            }

            boolean var9;
            if(var8 != 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            var6.recycle();
            var5.recycle();
            return var9;
         }

         public void sendMeetingResponse(long var1, int var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.android.email.service.IEmailService");
               var4.writeLong(var1);
               var4.writeInt(var3);
               this.mRemote.transact(15, var4, var5, 0);
               var5.readException();
            } finally {
               var5.recycle();
               var4.recycle();
            }

         }

         public void sendRecoveryPassword(long var1, String var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.android.email.service.IEmailService");
               var4.writeLong(var1);
               var4.writeString(var3);
               this.mRemote.transact(16, var4, var5, 0);
               var5.readException();
            } finally {
               var5.recycle();
               var4.recycle();
            }

         }

         public void setCallback(IEmailServiceCallback param1) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void setDeviceInfo(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.android.email.service.IEmailService");
               var3.writeLong(var1);
               this.mRemote.transact(21, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void setLogging(int var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.android.email.service.IEmailService");
               var2.writeInt(var1);
               this.mRemote.transact(12, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }

         public void startSync(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.android.email.service.IEmailService");
               var3.writeLong(var1);
               this.mRemote.transact(2, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void stopSync(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.android.email.service.IEmailService");
               var3.writeLong(var1);
               this.mRemote.transact(3, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void updateFolderList(long var1) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.android.email.service.IEmailService");
               var3.writeLong(var1);
               this.mRemote.transact(7, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public int validate(String param1, String param2, String param3, String param4, int param5, boolean param6, boolean param7) throws RemoteException {
            // $FF: Couldn't be decompiled
         }
      }
   }
}
