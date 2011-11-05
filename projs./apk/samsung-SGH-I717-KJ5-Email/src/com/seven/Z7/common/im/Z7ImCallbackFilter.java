package com.seven.Z7.common.im;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.seven.Z7.common.Z7CallbackFilter;
import com.seven.Z7.shared.ImServiceConstants;
import com.seven.Z7.shared.Z7IDLCallbackType;

public class Z7ImCallbackFilter extends Z7CallbackFilter {

   public static final Creator<Z7ImCallbackFilter> CREATOR = new Z7ImCallbackFilter.1();
   private static final String TAG = "Z7ImCallbackFilter";
   private int mIntData;
   private Z7ImCallbackFilter.ImFilterType mType;
   private String mUserId;


   public Z7ImCallbackFilter(Parcel var1) {
      Z7ImCallbackFilter.ImFilterType var2 = Z7ImCallbackFilter.ImFilterType.valueOf(var1.readString());
      String var3 = var1.readString();
      this(var2, var3);
      int var4 = var1.readInt();
      this.mIntData = var4;
   }

   public Z7ImCallbackFilter(Z7ImCallbackFilter.ImFilterType var1, int var2) {
      super(300);
      this.mIntData = var2;
      this.mType = var1;
   }

   public Z7ImCallbackFilter(Z7ImCallbackFilter.ImFilterType var1, String var2) {
      super(300);
      this.mUserId = var2;
      this.mType = var1;
   }

   public int describeContents() {
      return 0;
   }

   public boolean filter(Z7IDLCallbackType var1, Object var2) {
      boolean var6;
      if(var1 instanceof ImServiceConstants.ImCallbackType) {
         ImServiceConstants.ImCallbackType var3 = (ImServiceConstants.ImCallbackType)var1;
         int[] var4 = Z7ImCallbackFilter.2.$SwitchMap$com$seven$Z7$common$im$Z7ImCallbackFilter$ImFilterType;
         int var5 = this.mType.ordinal();
         switch(var4[var5]) {
         case 1:
         case 2:
            if(var2 instanceof Integer) {
               int var7 = this.mIntData;
               int var8 = ((Integer)var2).intValue();
               if(var7 == var8) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               return var6;
            }

            StringBuilder var9 = new StringBuilder();
            Z7ImCallbackFilter.ImFilterType var10 = this.mType;
            String var11 = var9.append(var10).append(" not handled with data ").append(var2).toString();
            int var12 = Log.v("Z7ImCallbackFilter", var11);
            break;
         case 3:
            if(var2 instanceof String) {
               var6 = this.mUserId.equals(var2);
               return var6;
            }

            StringBuilder var13 = new StringBuilder();
            Z7ImCallbackFilter.ImFilterType var14 = this.mType;
            String var15 = var13.append(var14).append(" not handled with data ").append(var2).toString();
            int var16 = Log.v("Z7ImCallbackFilter", var15);
         }
      }

      var6 = false;
      return var6;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.mType.toString();
      var1.writeString(var3);
      String var4 = this.mUserId;
      var1.writeString(var4);
      int var5 = this.mIntData;
      var1.writeInt(var5);
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$seven$Z7$common$im$Z7ImCallbackFilter$ImFilterType = new int[Z7ImCallbackFilter.ImFilterType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$seven$Z7$common$im$Z7ImCallbackFilter$ImFilterType;
            int var1 = Z7ImCallbackFilter.ImFilterType.IM_FILTER_ACCOUNT_ID.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$seven$Z7$common$im$Z7ImCallbackFilter$ImFilterType;
            int var3 = Z7ImCallbackFilter.ImFilterType.IM_FILTER_CHAT_ID.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$seven$Z7$common$im$Z7ImCallbackFilter$ImFilterType;
            int var5 = Z7ImCallbackFilter.ImFilterType.IM_FILTER_USER_ID.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var9) {
            ;
         }
      }
   }

   static class 1 implements Creator<Z7ImCallbackFilter> {

      1() {}

      public Z7ImCallbackFilter createFromParcel(Parcel var1) {
         return new Z7ImCallbackFilter(var1);
      }

      public Z7ImCallbackFilter[] newArray(int var1) {
         return new Z7ImCallbackFilter[var1];
      }
   }

   public static enum ImFilterType {

      // $FF: synthetic field
      private static final Z7ImCallbackFilter.ImFilterType[] $VALUES;
      IM_FILTER_ACCOUNT_ID("IM_FILTER_ACCOUNT_ID", 1),
      IM_FILTER_CHAT_ID("IM_FILTER_CHAT_ID", 2),
      IM_FILTER_USER_ID("IM_FILTER_USER_ID", 0);


      static {
         Z7ImCallbackFilter.ImFilterType[] var0 = new Z7ImCallbackFilter.ImFilterType[3];
         Z7ImCallbackFilter.ImFilterType var1 = IM_FILTER_USER_ID;
         var0[0] = var1;
         Z7ImCallbackFilter.ImFilterType var2 = IM_FILTER_ACCOUNT_ID;
         var0[1] = var2;
         Z7ImCallbackFilter.ImFilterType var3 = IM_FILTER_CHAT_ID;
         var0[2] = var3;
         $VALUES = var0;
      }

      private ImFilterType(String var1, int var2) {}
   }
}
