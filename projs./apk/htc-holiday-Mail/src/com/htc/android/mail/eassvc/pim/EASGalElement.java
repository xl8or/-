package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EASGalElement implements Parcelable {

   public static final Creator<EASGalElement> CREATOR = new EASGalElement.1();
   public String Alias;
   public String ClientId;
   public String Company;
   public String DisplayName;
   public String EmailAddress;
   public String FirstName;
   public String HomePhone;
   public String LastName;
   public String MobilePhone;
   public String Office;
   public String Phone;
   public String ServerID;
   public String Title;


   public EASGalElement() {}

   private EASGalElement(Parcel var1) {
      String var2 = var1.readString();
      this.DisplayName = var2;
      String var3 = var1.readString();
      this.Phone = var3;
      String var4 = var1.readString();
      this.Office = var4;
      String var5 = var1.readString();
      this.Title = var5;
      String var6 = var1.readString();
      this.Company = var6;
      String var7 = var1.readString();
      this.Alias = var7;
      String var8 = var1.readString();
      this.FirstName = var8;
      String var9 = var1.readString();
      this.LastName = var9;
      String var10 = var1.readString();
      this.HomePhone = var10;
      String var11 = var1.readString();
      this.MobilePhone = var11;
      String var12 = var1.readString();
      this.EmailAddress = var12;
   }

   // $FF: synthetic method
   EASGalElement(Parcel var1, EASGalElement.1 var2) {
      this(var1);
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.DisplayName;
      var1.writeString(var3);
      String var4 = this.Phone;
      var1.writeString(var4);
      String var5 = this.Office;
      var1.writeString(var5);
      String var6 = this.Title;
      var1.writeString(var6);
      String var7 = this.Company;
      var1.writeString(var7);
      String var8 = this.Alias;
      var1.writeString(var8);
      String var9 = this.FirstName;
      var1.writeString(var9);
      String var10 = this.LastName;
      var1.writeString(var10);
      String var11 = this.HomePhone;
      var1.writeString(var11);
      String var12 = this.MobilePhone;
      var1.writeString(var12);
      String var13 = this.EmailAddress;
      var1.writeString(var13);
   }

   static class 1 implements Creator<EASGalElement> {

      1() {}

      public EASGalElement createFromParcel(Parcel var1) {
         return new EASGalElement(var1, (EASGalElement.1)null);
      }

      public EASGalElement[] newArray(int var1) {
         return new EASGalElement[var1];
      }
   }
}
