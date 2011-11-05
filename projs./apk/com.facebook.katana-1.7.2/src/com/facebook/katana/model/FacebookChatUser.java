package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.model.FacebookUser;

public class FacebookChatUser extends FacebookUser implements Comparable, Parcelable {

   public static final Creator<FacebookChatUser> CREATOR = new FacebookChatUser.1();
   public boolean infoInitialized;
   public FacebookChatUser.Presence mPresence;


   public FacebookChatUser(long var1, FacebookChatUser.Presence var3) {
      FacebookChatUser.Presence var4 = FacebookChatUser.Presence.OFFLINE;
      this.mPresence = var4;
      this.infoInitialized = (boolean)0;

      try {
         this.setLong("mUserId", var1);
      } catch (Exception var6) {
         ;
      }

      this.mPresence = var3;
   }

   public FacebookChatUser(Parcel var1) {
      FacebookChatUser.Presence var2 = FacebookChatUser.Presence.OFFLINE;
      this.mPresence = var2;
      this.infoInitialized = (boolean)0;

      try {
         long var3 = var1.readLong();
         this.setLong("mUserId", var3);
         String var5 = var1.readString();
         this.setString("mFirstName", var5);
         String var6 = var1.readString();
         this.setString("mLastName", var6);
         String var7 = var1.readString();
         this.setString("mDisplayName", var7);
         String var8 = var1.readString();
         this.setString("mImageUrl", var8);
         FacebookChatUser.Presence var9 = (FacebookChatUser.Presence)var1.readValue((ClassLoader)null);
         this.mPresence = var9;
         this.infoInitialized = (boolean)1;
      } catch (Exception var11) {
         ;
      }
   }

   public FacebookChatUser(FacebookChatUser.Presence var1, long var2, String var4, String var5, String var6, String var7) {
      super(var2, var4, var5, var6, var7);
      FacebookChatUser.Presence var15 = FacebookChatUser.Presence.OFFLINE;
      this.mPresence = var15;
      this.infoInitialized = (boolean)0;
      this.mPresence = var1;
      this.infoInitialized = (boolean)1;
   }

   public static String getJid(Long var0) {
      return "-" + var0 + "@chat.facebook.com";
   }

   public static long getUid(String var0) {
      return Long.parseLong(var0.split("@")[0].substring(1));
   }

   public int compareTo(Object var1) {
      int var2;
      if(var1 == null) {
         var2 = 1;
      } else if(this.mDisplayName == null) {
         var2 = -1;
      } else {
         if(!(var1 instanceof FacebookUser)) {
            throw new ClassCastException();
         }

         FacebookUser var3 = (FacebookUser)var1;
         if(var3.mDisplayName == null) {
            var2 = 1;
         } else {
            String var4 = this.mDisplayName;
            String var5 = var3.mDisplayName;
            var2 = var4.compareTo(var5);
         }
      }

      return var2;
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else if(var1 == null) {
         var2 = false;
      } else {
         Class var3 = this.getClass();
         Class var4 = var1.getClass();
         if(var3 != var4) {
            var2 = false;
         } else {
            long var5 = ((FacebookChatUser)var1).mUserId;
            long var7 = this.mUserId;
            if(var5 == var7) {
               var2 = true;
            } else {
               var2 = false;
            }
         }
      }

      return var2;
   }

   public int hashCode() {
      long var1 = this.mUserId;
      long var3 = this.mUserId >>> 32;
      return (int)(var1 ^ var3);
   }

   public void setPresence(FacebookChatUser.Presence var1) {
      this.mPresence = var1;
   }

   public void setUserInfo(String var1, String var2, String var3, String var4) {
      try {
         this.setString("mFirstName", var1);
         this.setString("mLastName", var2);
         this.setString("mDisplayName", var3);
         this.setString("mImageUrl", var4);
         this.infoInitialized = (boolean)1;
      } catch (Exception var6) {
         ;
      }
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("Chat User [");
      long var2 = this.mUserId;
      StringBuilder var4 = var1.append(var2).append(": ");
      String var5 = this.mDisplayName;
      return var4.append(var5).append("]").toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.mUserId;
      var1.writeLong(var3);
      String var5 = this.mFirstName;
      var1.writeString(var5);
      String var6 = this.mLastName;
      var1.writeString(var6);
      String var7 = this.mDisplayName;
      var1.writeString(var7);
      String var8 = this.mImageUrl;
      var1.writeString(var8);
      FacebookChatUser.Presence var9 = this.mPresence;
      var1.writeValue(var9);
   }

   static class 1 implements Creator<FacebookChatUser> {

      1() {}

      public FacebookChatUser createFromParcel(Parcel var1) {
         return new FacebookChatUser(var1);
      }

      public FacebookChatUser[] newArray(int var1) {
         return new FacebookChatUser[var1];
      }
   }

   public static class UnreadConversation {

      public String mMessage;
      public int mUnreadCount;


      public UnreadConversation(String var1, int var2) {
         this.mMessage = var1;
         this.mUnreadCount = var2;
      }

      public void addMessage(String var1) {
         this.mMessage = var1;
         int var2 = this.mUnreadCount + 1;
         this.mUnreadCount = var2;
      }

      public void clear() {
         this.mMessage = null;
         this.mUnreadCount = 0;
      }
   }

   public static enum Presence {

      // $FF: synthetic field
      private static final FacebookChatUser.Presence[] $VALUES;
      AVAILABLE("AVAILABLE", 0),
      IDLE("IDLE", 1),
      OFFLINE("OFFLINE", 2);


      static {
         FacebookChatUser.Presence[] var0 = new FacebookChatUser.Presence[3];
         FacebookChatUser.Presence var1 = AVAILABLE;
         var0[0] = var1;
         FacebookChatUser.Presence var2 = IDLE;
         var0[1] = var2;
         FacebookChatUser.Presence var3 = OFFLINE;
         var0[2] = var3;
         $VALUES = var0;
      }

      private Presence(String var1, int var2) {}
   }
}
