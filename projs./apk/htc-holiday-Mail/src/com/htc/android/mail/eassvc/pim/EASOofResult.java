package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.htc.android.mail.eassvc.pim.EASOofMessage;
import java.util.ArrayList;

public class EASOofResult implements Parcelable {

   public static final String BODY_TYPE_TEXT = "Text";
   public static final String BOTY_TYPE_HTML = "HTML";
   public static final Creator<EASOofResult> CREATOR = new EASOofResult.1();
   public String endTime;
   public int httpStatus;
   public ArrayList<EASOofMessage> oofMsgList;
   public int oofState;
   public String startTime;
   public int status;


   public EASOofResult() {
      ArrayList var1 = new ArrayList();
      this.oofMsgList = var1;
   }

   private EASOofResult(Parcel var1) {
      ArrayList var2 = new ArrayList();
      this.oofMsgList = var2;
      int var3 = var1.readInt();
      this.httpStatus = var3;
      int var4 = var1.readInt();
      this.status = var4;
      int var5 = var1.readInt();
      this.oofState = var5;
      String var6 = var1.readString();
      this.startTime = var6;
      String var7 = var1.readString();
      this.endTime = var7;
      ArrayList var8 = this.oofMsgList;
      Creator var9 = EASOofMessage.CREATOR;
      var1.readTypedList(var8, var9);
   }

   // $FF: synthetic method
   EASOofResult(Parcel var1, EASOofResult.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.httpStatus;
      var1.writeInt(var3);
      int var4 = this.status;
      var1.writeInt(var4);
      int var5 = this.oofState;
      var1.writeInt(var5);
      String var6 = this.startTime;
      var1.writeString(var6);
      String var7 = this.endTime;
      var1.writeString(var7);
      ArrayList var8 = this.oofMsgList;
      var1.writeTypedList(var8);
   }

   static class 1 implements Creator<EASOofResult> {

      1() {}

      public EASOofResult createFromParcel(Parcel var1) {
         return new EASOofResult(var1, (EASOofResult.1)null);
      }

      public EASOofResult[] newArray(int var1) {
         return new EASOofResult[var1];
      }
   }
}
