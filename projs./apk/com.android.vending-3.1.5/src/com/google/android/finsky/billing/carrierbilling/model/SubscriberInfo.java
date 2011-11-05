package com.google.android.finsky.billing.carrierbilling.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Objects;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SubscriberInfo implements Parcelable {

   public static final Creator<SubscriberInfo> CREATOR = new SubscriberInfo.1();
   private static final String ENCODING = "UTF-8";
   private static final int NUM_PARAMS = 8;
   private final String mAddress1;
   private final String mAddress2;
   private final String mCity;
   private final String mCountry;
   private final String mIdentifier;
   private final String mName;
   private final String mPostalCode;
   private final String mState;


   private SubscriberInfo(Parcel var1) {
      String var2 = var1.readString();
      this.mName = var2;
      String var3 = var1.readString();
      this.mIdentifier = var3;
      String var4 = var1.readString();
      this.mAddress1 = var4;
      String var5 = var1.readString();
      this.mAddress2 = var5;
      String var6 = var1.readString();
      this.mCity = var6;
      String var7 = var1.readString();
      this.mState = var7;
      String var8 = var1.readString();
      this.mPostalCode = var8;
      String var9 = var1.readString();
      this.mCountry = var9;
   }

   // $FF: synthetic method
   SubscriberInfo(Parcel var1, SubscriberInfo.1 var2) {
      this(var1);
   }

   private SubscriberInfo(SubscriberInfo.Builder var1) {
      String var2 = var1.name;
      this.mName = var2;
      String var3 = var1.identifier;
      this.mIdentifier = var3;
      String var4 = var1.address1;
      this.mAddress1 = var4;
      String var5 = var1.address2;
      this.mAddress2 = var5;
      String var6 = var1.city;
      this.mCity = var6;
      String var7 = var1.state;
      this.mState = var7;
      String var8 = var1.postalCode;
      this.mPostalCode = var8;
      String var9 = var1.country;
      this.mCountry = var9;
   }

   // $FF: synthetic method
   SubscriberInfo(SubscriberInfo.Builder var1, SubscriberInfo.1 var2) {
      this(var1);
   }

   public static SubscriberInfo fromObfuscatedString(String var0) {
      SubscriberInfo.Builder var1 = new SubscriberInfo.Builder();
      byte[] var2 = Base64.decode(switchChars(var0).getBytes(), 0);
      String[] var3 = (new String(var2)).split(",", 8);
      if(var3.length != 8) {
         String var4 = "SubscriberInfo could not be parsed from " + var0;
         throw new IllegalArgumentException(var4);
      } else {
         byte var5 = 0;

         SubscriberInfo var25;
         try {
            String var6 = URLDecoder.decode(var3[var5], "UTF-8");
            SubscriberInfo.Builder var7 = var1.setName(var6);
            String var8 = URLDecoder.decode(var3[1], "UTF-8");
            SubscriberInfo.Builder var9 = var7.setIdentifier(var8);
            String var10 = URLDecoder.decode(var3[2], "UTF-8");
            SubscriberInfo.Builder var11 = var9.setAddress1(var10);
            String var12 = URLDecoder.decode(var3[3], "UTF-8");
            SubscriberInfo.Builder var13 = var11.setAddress2(var12);
            String var14 = URLDecoder.decode(var3[4], "UTF-8");
            SubscriberInfo.Builder var15 = var13.setCity(var14);
            String var16 = URLDecoder.decode(var3[5], "UTF-8");
            SubscriberInfo.Builder var17 = var15.setState(var16);
            String var18 = URLDecoder.decode(var3[6], "UTF-8");
            SubscriberInfo.Builder var19 = var17.setPostalCode(var18);
            String var20 = URLDecoder.decode(var3[7], "UTF-8");
            var19.setCountry(var20);
         } catch (UnsupportedEncodingException var24) {
            Object[] var23 = new Object[]{var24};
            FinskyLog.w("UTF-8 not supported: %s", var23);
            var25 = null;
            return var25;
         }

         var25 = var1.build();
         return var25;
      }
   }

   private static String switchChars(String var0) {
      int var1 = var0.length();
      StringBuilder var2 = new StringBuilder(var1);
      char[] var3 = var0.toCharArray();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var3[var5];
         if((var6 < 97 || var6 > 109) && (var6 < 65 || var6 > 77)) {
            if((var6 < 110 || var6 > 122) && (var6 < 78 || var6 > 90)) {
               if(var6 >= 48 && var6 <= 52) {
                  var6 = (char)(var6 + 5);
               } else if(var6 >= 53 && var6 <= 57) {
                  var6 = (char)(var6 + -5);
               }
            } else {
               var6 = (char)(var6 + -13);
            }
         } else {
            var6 = (char)(var6 + 13);
         }

         var2.append(var6);
      }

      return var2.toString();
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if(this != var1) {
         if(!(var1 instanceof SubscriberInfo)) {
            var2 = false;
         } else {
            SubscriberInfo var3 = (SubscriberInfo)var1;
            String var4 = this.mName;
            String var5 = var3.mName;
            if(Objects.equal(var4, var5)) {
               String var6 = this.mIdentifier;
               String var7 = var3.mIdentifier;
               if(Objects.equal(var6, var7)) {
                  String var8 = this.mAddress1;
                  String var9 = var3.mAddress1;
                  if(Objects.equal(var8, var9)) {
                     String var10 = this.mAddress2;
                     String var11 = var3.mAddress2;
                     if(Objects.equal(var10, var11)) {
                        String var12 = this.mCity;
                        String var13 = var3.mCity;
                        if(Objects.equal(var12, var13)) {
                           String var14 = this.mState;
                           String var15 = var3.mState;
                           if(Objects.equal(var14, var15)) {
                              String var16 = this.mPostalCode;
                              String var17 = var3.mPostalCode;
                              if(Objects.equal(var16, var17)) {
                                 String var18 = this.mCountry;
                                 String var19 = var3.mCountry;
                                 if(Objects.equal(var18, var19)) {
                                    return var2;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            var2 = false;
         }
      }

      return var2;
   }

   public String getAddress1() {
      return this.mAddress1;
   }

   public String getAddress2() {
      return this.mAddress2;
   }

   public String getCity() {
      return this.mCity;
   }

   public String getCountry() {
      return this.mCountry;
   }

   public String getIdentifier() {
      return this.mIdentifier;
   }

   public String getName() {
      return this.mName;
   }

   public String getPostalCode() {
      return this.mPostalCode;
   }

   public String getState() {
      return this.mState;
   }

   public int hashCode() {
      Object[] var1 = new Object[8];
      String var2 = this.mName;
      var1[0] = var2;
      String var3 = this.mIdentifier;
      var1[1] = var3;
      String var4 = this.mAddress1;
      var1[2] = var4;
      String var5 = this.mAddress2;
      var1[3] = var5;
      String var6 = this.mCity;
      var1[4] = var6;
      String var7 = this.mState;
      var1[5] = var7;
      String var8 = this.mPostalCode;
      var1[6] = var8;
      String var9 = this.mCountry;
      var1[7] = var9;
      return Objects.hashCode(var1);
   }

   public String toObfuscatedString() {
      String var1;
      String var2;
      String var3;
      String var4;
      String var5;
      String var6;
      String var7;
      String var8;
      label53: {
         String var10;
         try {
            if(this.mName == null) {
               var1 = "";
            } else {
               var1 = URLEncoder.encode(this.mName, "UTF-8");
            }

            if(this.mIdentifier == null) {
               var2 = "";
            } else {
               var2 = URLEncoder.encode(this.mIdentifier, "UTF-8");
            }

            if(this.mAddress1 == null) {
               var3 = "";
            } else {
               var3 = URLEncoder.encode(this.mAddress1, "UTF-8");
            }

            if(this.mAddress2 == null) {
               var4 = "";
            } else {
               var4 = URLEncoder.encode(this.mAddress2, "UTF-8");
            }

            if(this.mCity == null) {
               var5 = "";
            } else {
               var5 = URLEncoder.encode(this.mCity, "UTF-8");
            }

            if(this.mState == null) {
               var6 = "";
            } else {
               var6 = URLEncoder.encode(this.mState, "UTF-8");
            }

            if(this.mPostalCode == null) {
               var7 = "";
            } else {
               var7 = URLEncoder.encode(this.mPostalCode, "UTF-8");
            }

            if(this.mCountry == null) {
               var8 = "";
               break label53;
            }

            var10 = URLEncoder.encode(this.mCountry, "UTF-8");
         } catch (UnsupportedEncodingException var12) {
            throw new IllegalArgumentException("UTF-8 not supported", var12);
         }

         var8 = var10;
      }

      byte[] var9 = Base64.encode((var1 + "," + var2 + "," + var3 + "," + var4 + "," + var5 + "," + var6 + "," + var7 + "," + var8).getBytes(), 0);
      return switchChars(new String(var9));
   }

   public String toString() {
      return this.toObfuscatedString();
   }

   public String toUnobfuscatedString() {
      StringBuilder var1 = (new StringBuilder("SubscriberInfo: ")).append("  name      : ");
      String var2 = this.mName;
      StringBuilder var3 = var1.append(var2).append("\n").append("  identifier: ");
      String var4 = this.mIdentifier;
      StringBuilder var5 = var3.append(var4).append("\n").append("  address1  : ");
      String var6 = this.mAddress1;
      StringBuilder var7 = var5.append(var6).append("\n").append("  address2  : ");
      String var8 = this.mAddress2;
      StringBuilder var9 = var7.append(var8).append("\n").append("  city      : ");
      String var10 = this.mCity;
      StringBuilder var11 = var9.append(var10).append("\n").append("  state     : ");
      String var12 = this.mState;
      StringBuilder var13 = var11.append(var12).append("\n").append("  postalCode: ");
      String var14 = this.mPostalCode;
      StringBuilder var15 = var13.append(var14).append("\n").append("  country   : ");
      String var16 = this.mCountry;
      return var15.append(var16).append("\n").toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.mName;
      var1.writeString(var3);
      String var4 = this.mIdentifier;
      var1.writeString(var4);
      String var5 = this.mAddress1;
      var1.writeString(var5);
      String var6 = this.mAddress2;
      var1.writeString(var6);
      String var7 = this.mCity;
      var1.writeString(var7);
      String var8 = this.mState;
      var1.writeString(var8);
      String var9 = this.mPostalCode;
      var1.writeString(var9);
      String var10 = this.mCountry;
      var1.writeString(var10);
   }

   public static class Builder {

      private String address1;
      private String address2;
      private String city;
      private String country;
      private String identifier;
      private String name;
      private String postalCode;
      private String state;


      public Builder() {}

      public SubscriberInfo build() {
         return new SubscriberInfo(this, (SubscriberInfo.1)null);
      }

      public SubscriberInfo.Builder setAddress1(String var1) {
         this.address1 = var1;
         return this;
      }

      public SubscriberInfo.Builder setAddress2(String var1) {
         this.address2 = var1;
         return this;
      }

      public SubscriberInfo.Builder setCity(String var1) {
         this.city = var1;
         return this;
      }

      public SubscriberInfo.Builder setCountry(String var1) {
         this.country = var1;
         return this;
      }

      public SubscriberInfo.Builder setIdentifier(String var1) {
         this.identifier = var1;
         return this;
      }

      public SubscriberInfo.Builder setName(String var1) {
         this.name = var1;
         return this;
      }

      public SubscriberInfo.Builder setPostalCode(String var1) {
         this.postalCode = var1;
         return this;
      }

      public SubscriberInfo.Builder setState(String var1) {
         this.state = var1;
         return this;
      }
   }

   static class 1 implements Creator<SubscriberInfo> {

      1() {}

      public SubscriberInfo createFromParcel(Parcel var1) {
         return new SubscriberInfo(var1, (SubscriberInfo.1)null);
      }

      public SubscriberInfo[] newArray(int var1) {
         return new SubscriberInfo[var1];
      }
   }
}
