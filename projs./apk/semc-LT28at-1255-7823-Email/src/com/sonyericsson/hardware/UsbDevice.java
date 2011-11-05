package com.sonyericsson.hardware;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.List;

public final class UsbDevice implements Parcelable {

   public static final Creator<UsbDevice> CREATOR = new UsbDevice.1();
   public static final int STATUS_OVER_CURRENT = 2;
   public static final int STATUS_SUPPORTED = 0;
   public static final int STATUS_UNSUPPORTED = 1;


   private UsbDevice(Parcel var1) {}

   public UsbDevice(String var1, int var2, int var3, int var4, int var5, String var6, String var7, String var8, int var9, int var10) {}

   private void readFromParcel(Parcel var1) {}

   public void addConnection(UsbDevice var1) {}

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public List<UsbDevice> getConnections() {
      return null;
   }

   public int getDeviceClass() {
      return 0;
   }

   public int getDeviceProtocol() {
      return 0;
   }

   public int getDeviceSubclass() {
      return 0;
   }

   public String getManufacturer() {
      return "";
   }

   public int getProductId() {
      return 0;
   }

   public String getProductName() {
      return "";
   }

   public String getSerialNumber() {
      return "";
   }

   public int getStatus() {
      return 0;
   }

   public int getVendorId() {
      return 0;
   }

   public boolean hasDevicePath(String var1) {
      return false;
   }

   public int hashCode() {
      return 0;
   }

   public void removeConnection(UsbDevice var1) {}

   public String toString() {
      return "";
   }

   public void writeToParcel(Parcel var1, int var2) {}

   static class 1 implements Creator<UsbDevice> {

      1() {}

      public UsbDevice createFromParcel(Parcel var1) {
         return null;
      }

      public UsbDevice[] newArray(int var1) {
         return null;
      }
   }
}
