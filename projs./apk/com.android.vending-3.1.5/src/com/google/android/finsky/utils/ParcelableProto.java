package com.google.android.finsky.utils;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.utils.FinskyLog;
import com.google.protobuf.micro.MessageMicro;
import java.lang.reflect.Constructor;

public class ParcelableProto<T extends MessageMicro> implements Parcelable {

   public static Creator<ParcelableProto> CREATOR = new ParcelableProto.1();
   private final T mPayload;
   private byte[] mSerialized = null;


   public ParcelableProto(T var1) {
      this.mPayload = var1;
   }

   public static <T extends MessageMicro> ParcelableProto<T> forProto(T var0) {
      return new ParcelableProto(var0);
   }

   public static <T extends MessageMicro> T getProtoFromIntent(Intent var0, String var1) {
      return ((ParcelableProto)var0.getParcelableExtra(var1)).getPayload();
   }

   public static <T extends MessageMicro> T getProtoFromParcel(Parcel var0, ClassLoader var1) {
      return ((ParcelableProto)var0.readParcelable(var1)).getPayload();
   }

   private void serializePayload() {
      byte[] var1 = this.mPayload.toByteArray();
      this.mSerialized = var1;
   }

   public int describeContents() {
      return 0;
   }

   public T getPayload() {
      return this.mPayload;
   }

   public void writeToParcel(Parcel var1, int var2) {
      if(this.mSerialized == null) {
         this.serializePayload();
      }

      int var3 = this.mSerialized.length;
      var1.writeInt(var3);
      byte[] var4 = this.mSerialized;
      var1.writeByteArray(var4);
      String var5 = this.mPayload.getClass().getName();
      var1.writeString(var5);
   }

   static class 1 implements Creator<ParcelableProto> {

      1() {}

      public ParcelableProto createFromParcel(Parcel var1) {
         byte[] var2 = new byte[var1.readInt()];
         var1.readByteArray(var2);
         String var3 = var1.readString();

         try {
            Class var4 = Class.forName(var3);
            Class[] var5 = (Class[])false;
            Constructor var6 = var4.getConstructor(var5);
            Object[] var7 = (Object[])false;
            MessageMicro var8 = (MessageMicro)var6.newInstance(var7);
            var8.mergeFrom(var2);
            ParcelableProto var10 = new ParcelableProto(var8);
            return var10;
         } catch (Exception var14) {
            Object[] var12 = new Object[]{var3};
            FinskyLog.e("Could not properly unmarshal %s", var12);
            String var13 = "Exception when unmarshalling: " + var3;
            throw new IllegalArgumentException(var13, var14);
         }
      }

      public ParcelableProto[] newArray(int var1) {
         return new ParcelableProto[var1];
      }
   }
}
