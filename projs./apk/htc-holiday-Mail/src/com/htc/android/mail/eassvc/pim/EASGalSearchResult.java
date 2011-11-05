package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class EASGalSearchResult implements Parcelable {

   public static final Creator<EASGalSearchResult> CREATOR = new EASGalSearchResult.1();
   public static final int STATUS_FAIL = 255;
   public static final int STATUS_OK = 0;
   public static final int STATUS_SERVER_ERROR = 254;
   public ArrayList<com.htc.android.pim.eas.EASGalElement> elements;
   public int nSearchReturnCode;
   public int nStatus;
   public int nStoreReturnCode;
   public int nTotal;


   public EASGalSearchResult() {
      this.nTotal = 0;
      this.nSearchReturnCode = -1;
      this.nStoreReturnCode = -1;
      this.nStatus = -1;
   }

   private EASGalSearchResult(Parcel var1) {
      int var2 = var1.readInt();
      this.nTotal = var2;
      int var3 = var1.readInt();
      this.nSearchReturnCode = var3;
      int var4 = var1.readInt();
      this.nStoreReturnCode = var4;
      int var5 = var1.readInt();
      this.nStatus = var5;
      int var6 = var1.readInt();
      if(var6 > 0) {
         ArrayList var7 = new ArrayList(var6);
         this.elements = var7;

         for(int var8 = 0; var8 < var6; ++var8) {
            ArrayList var9 = this.elements;
            com.htc.android.pim.eas.EASGalElement var10 = (com.htc.android.pim.eas.EASGalElement)var1.readParcelable((ClassLoader)null);
            var9.add(var10);
         }

      }
   }

   // $FF: synthetic method
   EASGalSearchResult(Parcel var1, EASGalSearchResult.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = this.nTotal;
      var1.writeInt(var3);
      int var4 = this.nSearchReturnCode;
      var1.writeInt(var4);
      int var5 = this.nStoreReturnCode;
      var1.writeInt(var5);
      int var6 = this.nStatus;
      var1.writeInt(var6);
      if(this.elements != null) {
         if(this.elements.size() > 0) {
            int var7 = this.elements.size();
            var1.writeInt(var7);

            for(int var8 = 0; var8 < var7; ++var8) {
               Parcelable var9 = (Parcelable)this.elements.get(var8);
               var1.writeParcelable(var9, var2);
            }

         }
      }
   }

   static class 1 implements Creator<EASGalSearchResult> {

      1() {}

      public EASGalSearchResult createFromParcel(Parcel var1) {
         return new EASGalSearchResult(var1, (EASGalSearchResult.1)null);
      }

      public EASGalSearchResult[] newArray(int var1) {
         return new EASGalSearchResult[var1];
      }
   }
}
