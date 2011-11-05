package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASOofMessage implements Parcelable {

   public static final int APPLY_TYPE_EXTERNAL_KNOWN = 2;
   public static final int APPLY_TYPE_EXTERNAL_UNKNOWN = 3;
   public static final int APPLY_TYPE_INTERNAL = 1;
   public static final String BODY_TYPE_TEXT = "Text";
   public static final String BOTY_TYPE_HTML = "HTML";
   public static final Creator<EASOofMessage> CREATOR = new EASOofMessage.1();
   public int appliesTo;
   public String bodyType;
   public int enabled;
   public String replyMessage;


   public EASOofMessage() {}

   private EASOofMessage(Parcel var1) {
      int var2 = var1.readInt();
      this.appliesTo = var2;
      int var3 = var1.readInt();
      this.enabled = var3;
      String var4 = var1.readString();
      this.replyMessage = var4;
      String var5 = var1.readString();
      this.bodyType = var5;
   }

   // $FF: synthetic method
   EASOofMessage(Parcel var1, EASOofMessage.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.appliesTo;
      var1.writeInt(var3);
      int var4 = this.enabled;
      var1.writeInt(var4);
      String var5 = this.replyMessage;
      var1.writeString(var5);
      String var6 = this.bodyType;
      var1.writeString(var6);
   }

   static class 1 implements Creator<EASOofMessage> {

      1() {}

      public EASOofMessage createFromParcel(Parcel var1) {
         return new EASOofMessage(var1, (EASOofMessage.1)null);
      }

      public EASOofMessage[] newArray(int var1) {
         return new EASOofMessage[var1];
      }
   }
}
