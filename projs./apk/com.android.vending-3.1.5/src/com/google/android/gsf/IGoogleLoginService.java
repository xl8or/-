package com.google.android.gsf;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gsf.GoogleLoginCredentialsResult;
import com.google.android.gsf.LoginData;

public interface IGoogleLoginService extends IInterface {

   GoogleLoginCredentialsResult blockingGetCredentials(String var1, String var2, boolean var3) throws RemoteException;

   void deleteAllAccounts() throws RemoteException;

   void deleteOneAccount(String var1) throws RemoteException;

   String getAccount(boolean var1) throws RemoteException;

   String[] getAccounts() throws RemoteException;

   long getAndroidId() throws RemoteException;

   String getPrimaryAccount() throws RemoteException;

   void invalidateAuthToken(String var1) throws RemoteException;

   String peekCredentials(String var1, String var2) throws RemoteException;

   void saveAuthToken(String var1, String var2, String var3) throws RemoteException;

   void saveNewAccount(LoginData var1) throws RemoteException;

   void saveUsernameAndPassword(String var1, String var2, int var3) throws RemoteException;

   void tryNewAccount(LoginData var1) throws RemoteException;

   void updatePassword(LoginData var1) throws RemoteException;

   boolean verifyStoredPassword(String var1, String var2) throws RemoteException;

   int waitForAndroidId() throws RemoteException;

   public abstract static class Stub extends Binder implements IGoogleLoginService {

      private static final String DESCRIPTOR = "com.google.android.gsf.IGoogleLoginService";
      static final int TRANSACTION_blockingGetCredentials = 5;
      static final int TRANSACTION_deleteAllAccounts = 15;
      static final int TRANSACTION_deleteOneAccount = 14;
      static final int TRANSACTION_getAccount = 3;
      static final int TRANSACTION_getAccounts = 1;
      static final int TRANSACTION_getAndroidId = 7;
      static final int TRANSACTION_getPrimaryAccount = 2;
      static final int TRANSACTION_invalidateAuthToken = 6;
      static final int TRANSACTION_peekCredentials = 4;
      static final int TRANSACTION_saveAuthToken = 10;
      static final int TRANSACTION_saveNewAccount = 9;
      static final int TRANSACTION_saveUsernameAndPassword = 13;
      static final int TRANSACTION_tryNewAccount = 8;
      static final int TRANSACTION_updatePassword = 11;
      static final int TRANSACTION_verifyStoredPassword = 12;
      static final int TRANSACTION_waitForAndroidId = 16;


      public Stub() {
         this.attachInterface(this, "com.google.android.gsf.IGoogleLoginService");
      }

      public static IGoogleLoginService asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gsf.IGoogleLoginService");
            if(var2 != null && var2 instanceof IGoogleLoginService) {
               var1 = (IGoogleLoginService)var2;
            } else {
               var1 = new IGoogleLoginService.Stub.Proxy(var0);
            }
         }

         return (IGoogleLoginService)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         byte var5 = 0;
         boolean var6 = true;
         LoginData var9;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            String[] var7 = this.getAccounts();
            var3.writeNoException();
            var3.writeStringArray(var7);
            break;
         case 2:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            String var8 = this.getPrimaryAccount();
            var3.writeNoException();
            var3.writeString(var8);
            break;
         case 3:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            byte var31;
            if(var2.readInt() != 0) {
               var31 = 1;
            } else {
               var31 = 0;
            }

            String var10 = this.getAccount((boolean)var31);
            var3.writeNoException();
            var3.writeString(var10);
            break;
         case 4:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            String var11 = var2.readString();
            String var12 = var2.readString();
            String var13 = this.peekCredentials(var11, var12);
            var3.writeNoException();
            var3.writeString(var13);
            break;
         case 5:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            String var32 = var2.readString();
            String var14 = var2.readString();
            byte var15;
            if(var2.readInt() != 0) {
               var15 = 1;
            } else {
               var15 = 0;
            }

            GoogleLoginCredentialsResult var16 = this.blockingGetCredentials(var32, var14, (boolean)var15);
            var3.writeNoException();
            if(var16 != null) {
               var3.writeInt(1);
               var16.writeToParcel(var3, 1);
            } else {
               var3.writeInt(0);
            }
            break;
         case 6:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            String var17 = var2.readString();
            this.invalidateAuthToken(var17);
            var3.writeNoException();
            break;
         case 7:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            long var18 = this.getAndroidId();
            var3.writeNoException();
            var3.writeLong(var18);
            break;
         case 8:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            if(var2.readInt() != 0) {
               var9 = (LoginData)LoginData.CREATOR.createFromParcel(var2);
            } else {
               var9 = null;
            }

            this.tryNewAccount(var9);
            var3.writeNoException();
            if(var9 != null) {
               var3.writeInt(1);
               var9.writeToParcel(var3, 1);
            } else {
               var3.writeInt(0);
            }
            break;
         case 9:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            if(var2.readInt() != 0) {
               var9 = (LoginData)LoginData.CREATOR.createFromParcel(var2);
            } else {
               var9 = null;
            }

            this.saveNewAccount(var9);
            var3.writeNoException();
            break;
         case 10:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            String var20 = var2.readString();
            String var21 = var2.readString();
            String var22 = var2.readString();
            this.saveAuthToken(var20, var21, var22);
            var3.writeNoException();
            break;
         case 11:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            if(var2.readInt() != 0) {
               var9 = (LoginData)LoginData.CREATOR.createFromParcel(var2);
            } else {
               var9 = null;
            }

            this.updatePassword(var9);
            var3.writeNoException();
            if(var9 != null) {
               var3.writeInt(1);
               var9.writeToParcel(var3, 1);
            } else {
               var3.writeInt(0);
            }
            break;
         case 12:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            String var23 = var2.readString();
            String var24 = var2.readString();
            boolean var25 = this.verifyStoredPassword(var23, var24);
            var3.writeNoException();
            if(var25) {
               var5 = 1;
            }

            var3.writeInt(var5);
            break;
         case 13:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            String var26 = var2.readString();
            String var27 = var2.readString();
            int var28 = var2.readInt();
            this.saveUsernameAndPassword(var26, var27, var28);
            var3.writeNoException();
            break;
         case 14:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            String var29 = var2.readString();
            this.deleteOneAccount(var29);
            var3.writeNoException();
            break;
         case 15:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            this.deleteAllAccounts();
            var3.writeNoException();
            break;
         case 16:
            var2.enforceInterface("com.google.android.gsf.IGoogleLoginService");
            int var30 = this.waitForAndroidId();
            var3.writeNoException();
            var3.writeInt(var30);
            break;
         case 1598968902:
            var3.writeString("com.google.android.gsf.IGoogleLoginService");
            break;
         default:
            var6 = super.onTransact(var1, var2, var3, var4);
         }

         return var6;
      }

      private static class Proxy implements IGoogleLoginService {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public GoogleLoginCredentialsResult blockingGetCredentials(String var1, String var2, boolean var3) throws RemoteException {
            byte var4 = 0;
            Parcel var5 = Parcel.obtain();
            Parcel var6 = Parcel.obtain();
            boolean var11 = false;

            GoogleLoginCredentialsResult var8;
            label45: {
               try {
                  var11 = true;
                  var5.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
                  var5.writeString(var1);
                  var5.writeString(var2);
                  if(var3) {
                     var4 = 1;
                  }

                  var5.writeInt(var4);
                  this.mRemote.transact(5, var5, var6, 0);
                  var6.readException();
                  if(var6.readInt() != 0) {
                     var8 = (GoogleLoginCredentialsResult)GoogleLoginCredentialsResult.CREATOR.createFromParcel(var6);
                     var11 = false;
                     break label45;
                  }

                  var11 = false;
               } finally {
                  if(var11) {
                     var6.recycle();
                     var5.recycle();
                  }
               }

               var8 = null;
            }

            var6.recycle();
            var5.recycle();
            return var8;
         }

         public void deleteAllAccounts() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               this.mRemote.transact(15, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }

         public void deleteOneAccount(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               var2.writeString(var1);
               this.mRemote.transact(14, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }

         public String getAccount(boolean var1) throws RemoteException {
            byte var2 = 0;
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();
            boolean var10 = false;

            String var6;
            try {
               var10 = true;
               var3.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               if(var1) {
                  var2 = 1;
               }

               var3.writeInt(var2);
               this.mRemote.transact(3, var3, var4, 0);
               var4.readException();
               var6 = var4.readString();
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

         public String[] getAccounts() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String[] var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               this.mRemote.transact(1, var1, var2, 0);
               var2.readException();
               var4 = var2.createStringArray();
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

         public long getAndroidId() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var10 = false;

            long var4;
            try {
               var10 = true;
               var1.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               this.mRemote.transact(7, var1, var2, 0);
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
            return "com.google.android.gsf.IGoogleLoginService";
         }

         public String getPrimaryAccount() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
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

         public void invalidateAuthToken(String var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();

            try {
               var2.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               var2.writeString(var1);
               this.mRemote.transact(6, var2, var3, 0);
               var3.readException();
            } finally {
               var3.recycle();
               var2.recycle();
            }

         }

         public String peekCredentials(String var1, String var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();
            boolean var10 = false;

            String var6;
            try {
               var10 = true;
               var3.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               var3.writeString(var1);
               var3.writeString(var2);
               this.mRemote.transact(4, var3, var4, 0);
               var4.readException();
               var6 = var4.readString();
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

         public void saveAuthToken(String var1, String var2, String var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               var4.writeString(var1);
               var4.writeString(var2);
               var4.writeString(var3);
               this.mRemote.transact(10, var4, var5, 0);
               var5.readException();
            } finally {
               var5.recycle();
               var4.recycle();
            }

         }

         public void saveNewAccount(LoginData param1) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void saveUsernameAndPassword(String var1, String var2, int var3) throws RemoteException {
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();

            try {
               var4.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               var4.writeString(var1);
               var4.writeString(var2);
               var4.writeInt(var3);
               this.mRemote.transact(13, var4, var5, 0);
               var5.readException();
            } finally {
               var5.recycle();
               var4.recycle();
            }

         }

         public void tryNewAccount(LoginData param1) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void updatePassword(LoginData param1) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public boolean verifyStoredPassword(String var1, String var2) throws RemoteException {
            boolean var3 = false;
            Parcel var4 = Parcel.obtain();
            Parcel var5 = Parcel.obtain();
            boolean var10 = false;

            int var7;
            try {
               var10 = true;
               var4.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               var4.writeString(var1);
               var4.writeString(var2);
               this.mRemote.transact(12, var4, var5, 0);
               var5.readException();
               var7 = var5.readInt();
               var10 = false;
            } finally {
               if(var10) {
                  var5.recycle();
                  var4.recycle();
               }
            }

            if(var7 != 0) {
               var3 = true;
            }

            var5.recycle();
            var4.recycle();
            return var3;
         }

         public int waitForAndroidId() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            int var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.google.android.gsf.IGoogleLoginService");
               this.mRemote.transact(16, var1, var2, 0);
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
      }
   }
}
