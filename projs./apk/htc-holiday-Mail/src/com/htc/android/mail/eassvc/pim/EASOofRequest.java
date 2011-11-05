package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.htc.android.mail.eassvc.pim.EASOofMessage;
import java.util.ArrayList;

public class EASOofRequest implements Parcelable {

   public static final Creator<EASOofRequest> CREATOR = new EASOofRequest.1();
   public static final int OOF_STATE_DISABLE = 0;
   public static final int OOF_STATE_GLOBAL = 1;
   public static final int OOF_STATE_TIME_BASE = 2;
   public String endTime;
   public ArrayList<EASOofMessage> oofMsgList;
   public int oofState;
   public String startTime;


   public EASOofRequest() {
      ArrayList var1 = new ArrayList();
      this.oofMsgList = var1;
   }

   private EASOofRequest(Parcel var1) {
      ArrayList var2 = new ArrayList();
      this.oofMsgList = var2;
      int var3 = var1.readInt();
      this.oofState = var3;
      String var4 = var1.readString();
      this.startTime = var4;
      String var5 = var1.readString();
      this.endTime = var5;
      ArrayList var6 = this.oofMsgList;
      Creator var7 = EASOofMessage.CREATOR;
      var1.readTypedList(var6, var7);
   }

   // $FF: synthetic method
   EASOofRequest(Parcel var1, EASOofRequest.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.oofState;
      var1.writeInt(var3);
      String var4 = this.startTime;
      var1.writeString(var4);
      String var5 = this.endTime;
      var1.writeString(var5);
      ArrayList var6 = this.oofMsgList;
      var1.writeTypedList(var6);
   }

   static class 1 implements Creator<EASOofRequest> {

      1() {}

      public EASOofRequest createFromParcel(Parcel var1) {
         return new EASOofRequest(var1, (EASOofRequest.1)null);
      }

      public EASOofRequest[] newArray(int var1) {
         return new EASOofRequest[var1];
      }
   }
}
