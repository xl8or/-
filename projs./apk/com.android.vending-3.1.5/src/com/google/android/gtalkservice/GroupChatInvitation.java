package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class GroupChatInvitation implements Parcelable {

   public static final Creator<GroupChatInvitation> CREATOR = new GroupChatInvitation.1();
   private long mGroupContactId;
   private String mInviter;
   private String mPassword;
   private String mReason;
   private String mRoomAddress;


   public GroupChatInvitation(Parcel var1) {
      String var2 = var1.readString();
      this.mRoomAddress = var2;
      String var3 = var1.readString();
      this.mInviter = var3;
      String var4 = var1.readString();
      this.mReason = var4;
      String var5 = var1.readString();
      this.mPassword = var5;
      long var6 = var1.readLong();
      this.mGroupContactId = var6;
   }

   public GroupChatInvitation(String var1, String var2, String var3, String var4, long var5) {
      this.mRoomAddress = var1;
      this.mInviter = var2;
      this.mReason = var3;
      this.mPassword = var4;
      this.mGroupContactId = var5;
   }

   public int describeContents() {
      return 0;
   }

   public long getGroupContactId() {
      return this.mGroupContactId;
   }

   public String getInviter() {
      return this.mInviter;
   }

   public String getPassword() {
      return this.mPassword;
   }

   public String getReason() {
      return this.mReason;
   }

   public String getRoomAddress() {
      return this.mRoomAddress;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.mRoomAddress;
      var1.writeString(var3);
      String var4 = this.mInviter;
      var1.writeString(var4);
      String var5 = this.mReason;
      var1.writeString(var5);
      String var6 = this.mPassword;
      var1.writeString(var6);
      long var7 = this.mGroupContactId;
      var1.writeLong(var7);
   }

   static class 1 implements Creator<GroupChatInvitation> {

      1() {}

      public GroupChatInvitation createFromParcel(Parcel var1) {
         return new GroupChatInvitation(var1);
      }

      public GroupChatInvitation[] newArray(int var1) {
         return new GroupChatInvitation[var1];
      }
   }
}
