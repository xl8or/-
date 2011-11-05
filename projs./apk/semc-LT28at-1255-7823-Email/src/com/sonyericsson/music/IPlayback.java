package com.sonyericsson.music;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPlayback extends IInterface {

   int getAlbumId() throws RemoteException;

   String getAlbumName() throws RemoteException;

   int getArtistId() throws RemoteException;

   String getArtistName() throws RemoteException;

   long getDuration() throws RemoteException;

   String getPath() throws RemoteException;

   long getPosition() throws RemoteException;

   int getTrackId() throws RemoteException;

   String getTrackName() throws RemoteException;

   boolean isPlaying() throws RemoteException;

   void next() throws RemoteException;

   void pause() throws RemoteException;

   void play() throws RemoteException;

   void prev() throws RemoteException;

   public abstract static class Stub extends Binder implements IPlayback {

      private static final String DESCRIPTOR = "com.sonyericsson.music.IPlayback";
      static final int TRANSACTION_getAlbumId = 10;
      static final int TRANSACTION_getAlbumName = 11;
      static final int TRANSACTION_getArtistId = 12;
      static final int TRANSACTION_getArtistName = 13;
      static final int TRANSACTION_getDuration = 6;
      static final int TRANSACTION_getPath = 14;
      static final int TRANSACTION_getPosition = 7;
      static final int TRANSACTION_getTrackId = 8;
      static final int TRANSACTION_getTrackName = 9;
      static final int TRANSACTION_isPlaying = 1;
      static final int TRANSACTION_next = 4;
      static final int TRANSACTION_pause = 3;
      static final int TRANSACTION_play = 2;
      static final int TRANSACTION_prev = 5;


      public Stub() {
         this.attachInterface(this, "com.sonyericsson.music.IPlayback");
      }

      public static IPlayback asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.sonyericsson.music.IPlayback");
            if(var2 != null && var2 instanceof IPlayback) {
               var1 = (IPlayback)var2;
            } else {
               var1 = new IPlayback.Stub.Proxy(var0);
            }
         }

         return (IPlayback)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            boolean var6 = this.isPlaying();
            var3.writeNoException();
            byte var7;
            if(var6) {
               var7 = 1;
            } else {
               var7 = 0;
            }

            var3.writeInt(var7);
            var5 = true;
            break;
         case 2:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            this.play();
            var3.writeNoException();
            var5 = true;
            break;
         case 3:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            this.pause();
            var3.writeNoException();
            var5 = true;
            break;
         case 4:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            this.next();
            var3.writeNoException();
            var5 = true;
            break;
         case 5:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            this.prev();
            var3.writeNoException();
            var5 = true;
            break;
         case 6:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            long var8 = this.getDuration();
            var3.writeNoException();
            var3.writeLong(var8);
            var5 = true;
            break;
         case 7:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            long var10 = this.getPosition();
            var3.writeNoException();
            var3.writeLong(var10);
            var5 = true;
            break;
         case 8:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            int var12 = this.getTrackId();
            var3.writeNoException();
            var3.writeInt(var12);
            var5 = true;
            break;
         case 9:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            String var13 = this.getTrackName();
            var3.writeNoException();
            var3.writeString(var13);
            var5 = true;
            break;
         case 10:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            int var14 = this.getAlbumId();
            var3.writeNoException();
            var3.writeInt(var14);
            var5 = true;
            break;
         case 11:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            String var15 = this.getAlbumName();
            var3.writeNoException();
            var3.writeString(var15);
            var5 = true;
            break;
         case 12:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            int var16 = this.getArtistId();
            var3.writeNoException();
            var3.writeInt(var16);
            var5 = true;
            break;
         case 13:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            String var17 = this.getArtistName();
            var3.writeNoException();
            var3.writeString(var17);
            var5 = true;
            break;
         case 14:
            var2.enforceInterface("com.sonyericsson.music.IPlayback");
            String var18 = this.getPath();
            var3.writeNoException();
            var3.writeString(var18);
            var5 = true;
            break;
         case 1598968902:
            var3.writeString("com.sonyericsson.music.IPlayback");
            var5 = true;
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IPlayback {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public int getAlbumId() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            int var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
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

         public String getAlbumName() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(11, var1, var2, 0);
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

         public int getArtistId() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            int var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
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

         public String getArtistName() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(13, var1, var2, 0);
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

         public long getDuration() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var10 = false;

            long var4;
            try {
               var10 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(6, var1, var2, 0);
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
            return "com.sonyericsson.music.IPlayback";
         }

         public String getPath() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(14, var1, var2, 0);
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

         public long getPosition() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var10 = false;

            long var4;
            try {
               var10 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
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

         public int getTrackId() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            int var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(8, var1, var2, 0);
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

         public String getTrackName() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            String var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(9, var1, var2, 0);
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

         public boolean isPlaying() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();
            boolean var8 = false;

            int var4;
            try {
               var8 = true;
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(1, var1, var2, 0);
               var2.readException();
               var4 = var2.readInt();
               var8 = false;
            } finally {
               if(var8) {
                  var2.recycle();
                  var1.recycle();
               }
            }

            boolean var5;
            if(var4 != 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            var2.recycle();
            var1.recycle();
            return var5;
         }

         public void next() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(4, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }

         public void pause() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(3, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }

         public void play() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(2, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }

         public void prev() throws RemoteException {
            Parcel var1 = Parcel.obtain();
            Parcel var2 = Parcel.obtain();

            try {
               var1.writeInterfaceToken("com.sonyericsson.music.IPlayback");
               this.mRemote.transact(5, var1, var2, 0);
               var2.readException();
            } finally {
               var2.recycle();
               var1.recycle();
            }

         }
      }
   }
}
