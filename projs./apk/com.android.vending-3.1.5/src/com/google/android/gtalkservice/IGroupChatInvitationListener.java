package com.google.android.gtalkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gtalkservice.GroupChatInvitation;

public interface IGroupChatInvitationListener extends IInterface {

   boolean onInvitationReceived(GroupChatInvitation var1) throws RemoteException;

   public abstract static class Stub extends Binder implements IGroupChatInvitationListener {

      private static final String DESCRIPTOR = "com.google.android.gtalkservice.IGroupChatInvitationListener";
      static final int TRANSACTION_onInvitationReceived = 1;


      public Stub() {
         this.attachInterface(this, "com.google.android.gtalkservice.IGroupChatInvitationListener");
      }

      public static IGroupChatInvitationListener asInterface(IBinder var0) {
         Object var1;
         if(var0 == null) {
            var1 = null;
         } else {
            IInterface var2 = var0.queryLocalInterface("com.google.android.gtalkservice.IGroupChatInvitationListener");
            if(var2 != null && var2 instanceof IGroupChatInvitationListener) {
               var1 = (IGroupChatInvitationListener)var2;
            } else {
               var1 = new IGroupChatInvitationListener.Stub.Proxy(var0);
            }
         }

         return (IGroupChatInvitationListener)var1;
      }

      public IBinder asBinder() {
         return this;
      }

      public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5 = true;
         switch(var1) {
         case 1:
            var2.enforceInterface("com.google.android.gtalkservice.IGroupChatInvitationListener");
            GroupChatInvitation var6;
            if(var2.readInt() != 0) {
               var6 = (GroupChatInvitation)GroupChatInvitation.CREATOR.createFromParcel(var2);
            } else {
               var6 = null;
            }

            boolean var7 = this.onInvitationReceived(var6);
            var3.writeNoException();
            byte var8;
            if(var7) {
               var8 = 1;
            } else {
               var8 = 0;
            }

            var3.writeInt(var8);
            break;
         case 1598968902:
            var3.writeString("com.google.android.gtalkservice.IGroupChatInvitationListener");
            break;
         default:
            var5 = super.onTransact(var1, var2, var3, var4);
         }

         return var5;
      }

      private static class Proxy implements IGroupChatInvitationListener {

         private IBinder mRemote;


         Proxy(IBinder var1) {
            this.mRemote = var1;
         }

         public IBinder asBinder() {
            return this.mRemote;
         }

         public String getInterfaceDescriptor() {
            return "com.google.android.gtalkservice.IGroupChatInvitationListener";
         }

         public boolean onInvitationReceived(GroupChatInvitation param1) throws RemoteException {
            // $FF: Couldn't be decompiled
         }
      }
   }
}
