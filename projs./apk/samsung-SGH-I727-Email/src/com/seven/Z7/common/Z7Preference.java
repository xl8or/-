package com.seven.Z7.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Z7Preference implements Parcelable {

   public static final Creator<Z7Preference> CREATOR = new Z7Preference.1();
   public static final int GLOBAL_PREFERENCES = 0;
   private static final String TAG = "Z7Preference";
   public static final int TYPE_BOOLEAN = 4;
   public static final int TYPE_INT = 1;
   public static final int TYPE_LONG = 2;
   public static final int TYPE_NOT_SET = 0;
   public static final int TYPE_STRING = 3;
   private String key;
   private int type;
   private Object value;


   public Z7Preference(Parcel var1) {
      this.readFromParcel(var1);
   }

   public Z7Preference(String var1, Object var2) {
      this.key = var1;
      this.setValue(var2);
   }

   public static int getType(Object var0) {
      byte var1 = 0;
      if(var0 instanceof Boolean) {
         var1 = 4;
      } else if(var0 instanceof String) {
         var1 = 3;
      } else if(!(var0 instanceof Integer) && !(var0 instanceof Short)) {
         if(var0 instanceof Long) {
            var1 = 2;
         }
      } else {
         var1 = 1;
      }

      return var1;
   }

   private void readFromParcel(Parcel var1) {
      String var2 = var1.readString();
      this.key = var2;
      Object var3 = this.readValue(var1);
      this.value = var3;
   }

   private Object readValue(Parcel var1) {
      int var2 = var1.readInt();
      this.type = var2;
      Object var3;
      if(this.type == 0) {
         var3 = null;
      } else {
         switch(this.type) {
         case 1:
            var3 = Integer.valueOf(var1.readInt());
            break;
         case 2:
            var3 = Long.valueOf(var1.readLong());
            break;
         case 3:
            var3 = var1.readString();
            break;
         case 4:
            byte var7;
            if(var1.readByte() == 1) {
               var7 = 1;
            } else {
               var7 = 0;
            }

            var3 = Boolean.valueOf((boolean)var7);
            break;
         default:
            StringBuilder var4 = (new StringBuilder()).append("cannot handle type:");
            int var5 = this.type;
            String var6 = var4.append(var5).toString();
            throw new RuntimeException(var6);
         }
      }

      return var3;
   }

   private void setValue(Object var1) {
      int var2 = getType(var1);
      this.type = var2;
      if(this.type == 0 && var1 != null) {
         StringBuilder var3 = (new StringBuilder()).append("value cannot be ");
         Class var4 = var1.getClass();
         String var5 = var3.append(var4).toString();
         throw new RuntimeException(var5);
      } else {
         this.value = var1;
      }
   }

   private void writeValue(Parcel var1, int var2, Object var3) {
      if(var3 == null) {
         var1.writeInt(0);
      } else {
         var1.writeInt(var2);
         switch(var2) {
         case 1:
            int var5 = ((Integer)var3).intValue();
            var1.writeInt(var5);
            return;
         case 2:
            long var6 = ((Long)var3).longValue();
            var1.writeLong(var6);
            return;
         case 3:
            String var8 = (String)var3;
            var1.writeString(var8);
            return;
         case 4:
            byte var9;
            if(((Boolean)var3).booleanValue()) {
               var9 = 1;
            } else {
               var9 = 0;
            }

            byte var10 = (byte)var9;
            var1.writeByte(var10);
            return;
         default:
            String var4 = "cannot handle type:" + var2;
            throw new RuntimeException(var4);
         }
      }
   }

   public int describeContents() {
      return 0;
   }

   public String getKey() {
      return this.key;
   }

   public int getType() {
      return this.type;
   }

   public Object getValue() {
      return this.value;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.key;
      var1.writeString(var3);
      int var4 = this.type;
      Object var5 = this.value;
      this.writeValue(var1, var4, var5);
   }

   static class 1 implements Creator<Z7Preference> {

      1() {}

      public Z7Preference createFromParcel(Parcel var1) {
         return new Z7Preference(var1);
      }

      public Z7Preference[] newArray(int var1) {
         return new Z7Preference[var1];
      }
   }
}
