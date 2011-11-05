package com.facebook.katana.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class TaskContext implements Parcelable {

   public static final Creator<TaskContext> CREATOR = new TaskContext.1();
   public boolean receivedResponse;
   public boolean sentRequest;


   public TaskContext() {}

   private TaskContext(Parcel var1) {
      byte var2;
      if(var1.readByte() == 1) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      this.sentRequest = (boolean)var2;
      byte var3;
      if(var1.readByte() == 1) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      this.receivedResponse = (boolean)var3;
   }

   // $FF: synthetic method
   TaskContext(Parcel var1, TaskContext.1 var2) {
      this(var1);
   }

   public void clear() {
      this.sentRequest = (boolean)0;
      this.receivedResponse = (boolean)0;
   }

   public int describeContents() {
      return 0;
   }

   public void reset() {
      if(this.sentRequest) {
         if(!this.receivedResponse) {
            this.sentRequest = (boolean)0;
         }
      }
   }

   public void writeToParcel(Parcel var1, int var2) {
      byte var3;
      if(this.sentRequest) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      byte var4 = (byte)var3;
      var1.writeByte(var4);
      byte var5;
      if(this.receivedResponse) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      byte var6 = (byte)var5;
      var1.writeByte(var6);
   }

   static class 1 implements Creator<TaskContext> {

      1() {}

      public TaskContext createFromParcel(Parcel var1) {
         return new TaskContext(var1, (TaskContext.1)null);
      }

      public TaskContext[] newArray(int var1) {
         return new TaskContext[var1];
      }
   }
}
