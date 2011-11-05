package com.seven.Z7.common;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.seven.Z7.common.Z7Preference;
import java.util.HashMap;
import java.util.Map;

public interface IZ7Settings extends IInterface {

   Z7Preference getPreference(int var1, Z7Preference var2) throws RemoteException;

   Map getPreferences(int var1) throws RemoteException;

   void removePreference(int var1, String var2) throws RemoteException;

   void setPreference(int var1, Z7Preference var2) throws RemoteException;

   void updateSettings(int var1, Map var2) throws RemoteException;

   public abstract static class Stub extends Binder implements IZ7Settings {

      private static final String DESCRIPTOR = "com.seven.Z7.common.IZ7Settings";
      static final int TRANSACTION_getPreference = 4;
      static final int TRANSACTION_getPreferences = 5;
      static final int TRANSACTION_removePreference = 3;
      static final int TRANSACTION_setPreference = 2;
      static final int TRANSACTION_updateSettings = 1;


      public Stub() {
         this.attachInterface(this, "com.seven.Z7.common.IZ7Settings");
      }

      public static IZ7Settings asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.seven.Z7.common.IZ7Settings");
            if(var2 != null && var2 instanceof IZ7Settings) {
               var1 = (IZ7Settings)var2;
            } else {
               var1 = new IZ7Settings.Stub.Proxy(var0);
            }
         }

         return (IZ7Settings)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5;
         int var9;
         Z7Preference var10;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.seven.Z7.common.IZ7Settings");
            int var6 = var2.readInt();
            ClassLoader var7 = this.getClass().getClassLoader();
            HashMap var8 = var2.readHashMap(var7);
            this.updateSettings(var6, var8);
            var3.writeNoException();
            var5 = true;
            break;
         case 2:
            var2.enforceInterface("com.seven.Z7.common.IZ7Settings");
            var9 = var2.readInt();
            if(var2.readInt() != 0) {
               var10 = (Z7Preference)Z7Preference.CREATOR.createFromParcel(var2);
            } else {
               var10 = null;
            }

            this.setPreference(var9, var10);
            var3.writeNoException();
            var5 = true;
            break;
         case 3:
            var2.enforceInterface("com.seven.Z7.common.IZ7Settings");
            int var11 = var2.readInt();
            String var12 = var2.readString();
            this.removePreference(var11, var12);
            var3.writeNoException();
            var5 = true;
            break;
         case 4:
            var2.enforceInterface("com.seven.Z7.common.IZ7Settings");
            var9 = var2.readInt();
            if(var2.readInt() != 0) {
               var10 = (Z7Preference)Z7Preference.CREATOR.createFromParcel(var2);
            } else {
               var10 = null;
            }

            Z7Preference var13 = this.getPreference(var9, var10);
            var3.writeNoException();
            if(var13 != null) {
               var3.writeInt(1);
               var13.writeToParcel(var3, 1);
            } else {
               var3.writeInt(0);
            }

            var5 = true;
            break;
         case 5:
            var2.enforceInterface("com.seven.Z7.common.IZ7Settings");
            int var14 = var2.readInt();
            Map var15 = this.getPreferences(var14);
            var3.writeNoException();
            var3.writeMap(var15);
            var5 = true;
            break;
         case 1598968902:
            var3.writeString("com.seven.Z7.common.IZ7Settings");
            var5 = true;
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IZ7Settings {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public String getInterfaceDescriptor() {
            return "com.seven.Z7.common.IZ7Settings";
         }

         public Z7Preference getPreference(int param1, Z7Preference param2) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public Map getPreferences(int var1) throws RemoteException {
            Parcel var2 = Parcel.obtain();
            Parcel var3 = Parcel.obtain();
            boolean var10 = false;

            HashMap var6;
            try {
               var10 = true;
               var2.writeInterfaceToken("com.seven.Z7.common.IZ7Settings");
               var2.writeInt(var1);
               this.mRemote.transact(5, var2, var3, 0);
               var3.readException();
               ClassLoader var5 = this.getClass().getClassLoader();
               var6 = var3.readHashMap(var5);
               var10 = false;
            } finally {
               if(var10) {
                  var3.recycle();
                  var2.recycle();
               }
            }

            var3.recycle();
            var2.recycle();
            return var6;
         }

         public void removePreference(int var1, String var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.seven.Z7.common.IZ7Settings");
               var3.writeInt(var1);
               var3.writeString(var2);
               this.mRemote.transact(3, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }

         public void setPreference(int param1, Z7Preference param2) throws RemoteException {
            // $FF: Couldn't be decompiled
         }

         public void updateSettings(int var1, Map var2) throws RemoteException {
            Parcel var3 = Parcel.obtain();
            Parcel var4 = Parcel.obtain();

            try {
               var3.writeInterfaceToken("com.seven.Z7.common.IZ7Settings");
               var3.writeInt(var1);
               var3.writeMap(var2);
               this.mRemote.transact(1, var3, var4, 0);
               var4.readException();
            } finally {
               var4.recycle();
               var3.recycle();
            }

         }
      }
   }
}
