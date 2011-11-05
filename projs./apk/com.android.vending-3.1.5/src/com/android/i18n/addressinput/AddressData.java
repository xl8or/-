package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressField;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AddressData implements Serializable {

   private final String mAddressLine1;
   private final String mAddressLine2;
   private final String mAdministrativeArea;
   private final String mDependentLocality;
   private final String mLanguageCode;
   private final String mLocality;
   private final String mOrganization;
   private final String mPostalCode;
   private final String mPostalCountry;
   private final String mRecipient;
   private final String mSortingCode;


   private AddressData(AddressData.Builder var1) {
      Map var2 = var1.mValues;
      AddressField var3 = AddressField.COUNTRY;
      String var4 = (String)var2.get(var3);
      this.mPostalCountry = var4;
      Map var5 = var1.mValues;
      AddressField var6 = AddressField.ADMIN_AREA;
      String var7 = (String)var5.get(var6);
      this.mAdministrativeArea = var7;
      Map var8 = var1.mValues;
      AddressField var9 = AddressField.LOCALITY;
      String var10 = (String)var8.get(var9);
      this.mLocality = var10;
      Map var11 = var1.mValues;
      AddressField var12 = AddressField.DEPENDENT_LOCALITY;
      String var13 = (String)var11.get(var12);
      this.mDependentLocality = var13;
      Map var14 = var1.mValues;
      AddressField var15 = AddressField.POSTAL_CODE;
      String var16 = (String)var14.get(var15);
      this.mPostalCode = var16;
      Map var17 = var1.mValues;
      AddressField var18 = AddressField.SORTING_CODE;
      String var19 = (String)var17.get(var18);
      this.mSortingCode = var19;
      Map var20 = var1.mValues;
      AddressField var21 = AddressField.ORGANIZATION;
      String var22 = (String)var20.get(var21);
      this.mOrganization = var22;
      Map var23 = var1.mValues;
      AddressField var24 = AddressField.RECIPIENT;
      String var25 = (String)var23.get(var24);
      this.mRecipient = var25;
      Map var26 = var1.mValues;
      AddressField var27 = AddressField.ADDRESS_LINE_1;
      String var28 = (String)var26.get(var27);
      this.mAddressLine1 = var28;
      Map var29 = var1.mValues;
      AddressField var30 = AddressField.ADDRESS_LINE_2;
      String var31 = (String)var29.get(var30);
      this.mAddressLine2 = var31;
      String var32 = var1.mLanguage;
      this.mLanguageCode = var32;
   }

   // $FF: synthetic method
   AddressData(AddressData.Builder var1, AddressData.1 var2) {
      this(var1);
   }

   public String getAddressLine1() {
      return this.mAddressLine1;
   }

   public String getAddressLine2() {
      return this.mAddressLine2;
   }

   public String getAdministrativeArea() {
      return this.mAdministrativeArea;
   }

   public String getDependentLocality() {
      return this.mDependentLocality;
   }

   public String getFieldValue(AddressField var1) {
      int[] var2 = AddressData.1.$SwitchMap$com$android$i18n$addressinput$AddressField;
      int var3 = var1.ordinal();
      String var5;
      switch(var2[var3]) {
      case 1:
         var5 = this.mPostalCountry;
         break;
      case 2:
         var5 = this.mAdministrativeArea;
         break;
      case 3:
         var5 = this.mLocality;
         break;
      case 4:
         var5 = this.mDependentLocality;
         break;
      case 5:
         var5 = this.mPostalCode;
         break;
      case 6:
         var5 = this.mSortingCode;
         break;
      case 7:
         var5 = this.mAddressLine1;
         break;
      case 8:
         var5 = this.mAddressLine2;
         break;
      case 9:
         var5 = this.mOrganization;
         break;
      case 10:
         var5 = this.mRecipient;
         break;
      default:
         String var4 = "unrecognized key: " + var1;
         throw new IllegalArgumentException(var4);
      }

      return var5;
   }

   public String getLanguageCode() {
      return this.mLanguageCode;
   }

   public String getLocality() {
      return this.mLocality;
   }

   public String getOrganization() {
      return this.mOrganization;
   }

   public String getPostalCode() {
      return this.mPostalCode;
   }

   public String getPostalCountry() {
      return this.mPostalCountry;
   }

   public String getRecipient() {
      return this.mRecipient;
   }

   public String getSortingCode() {
      return this.mSortingCode;
   }

   public static class Builder {

      private String mLanguage = null;
      private final Map<AddressField, String> mValues;


      public Builder() {
         HashMap var1 = new HashMap();
         this.mValues = var1;
      }

      public Builder(AddressData var1) {
         HashMap var2 = new HashMap();
         this.mValues = var2;
         this.set(var1);
      }

      private void normalizeAddresses() {
         Map var1 = this.mValues;
         AddressField var2 = AddressField.ADDRESS_LINE_1;
         String var3 = (String)var1.get(var2);
         Map var4 = this.mValues;
         AddressField var5 = AddressField.ADDRESS_LINE_2;
         String var6 = (String)var4.get(var5);
         if(var3 == null || var3.trim().length() == 0) {
            var3 = var6;
            var6 = null;
         }

         if(var3 != null) {
            String[] var7 = var3.split("\n");
            if(var7.length > 1) {
               var3 = var7[0];
               var6 = var7[1];
            }
         }

         Map var8 = this.mValues;
         AddressField var9 = AddressField.ADDRESS_LINE_1;
         var8.put(var9, var3);
         Map var11 = this.mValues;
         AddressField var12 = AddressField.ADDRESS_LINE_2;
         var11.put(var12, var6);
      }

      public AddressData build() {
         return new AddressData(this, (AddressData.1)null);
      }

      public AddressData.Builder set(AddressData var1) {
         this.mValues.clear();
         AddressField[] var2 = AddressField.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            AddressField var5 = var2[var4];
            AddressField var6 = AddressField.STREET_ADDRESS;
            if(var5 != var6) {
               String var7 = var1.getFieldValue(var5);
               this.set(var5, var7);
            }
         }

         this.normalizeAddresses();
         String var9 = var1.getLanguageCode();
         this.setLanguageCode(var9);
         return this;
      }

      public AddressData.Builder set(AddressField var1, String var2) {
         if(var2 != null && var2.length() != 0) {
            Map var4 = this.mValues;
            String var5 = var2.trim();
            var4.put(var1, var5);
         } else {
            this.mValues.remove(var1);
         }

         this.normalizeAddresses();
         return this;
      }

      public AddressData.Builder setAddress(String var1) {
         this.setAddressLine1(var1);
         return this;
      }

      public AddressData.Builder setAddressLine1(String var1) {
         AddressField var2 = AddressField.ADDRESS_LINE_1;
         return this.set(var2, var1);
      }

      public AddressData.Builder setAddressLine2(String var1) {
         AddressField var2 = AddressField.ADDRESS_LINE_2;
         return this.set(var2, var1);
      }

      public AddressData.Builder setAdminArea(String var1) {
         AddressField var2 = AddressField.ADMIN_AREA;
         return this.set(var2, var1);
      }

      public AddressData.Builder setCountry(String var1) {
         AddressField var2 = AddressField.COUNTRY;
         return this.set(var2, var1);
      }

      public AddressData.Builder setDependentLocality(String var1) {
         AddressField var2 = AddressField.DEPENDENT_LOCALITY;
         return this.set(var2, var1);
      }

      public AddressData.Builder setLanguageCode(String var1) {
         this.mLanguage = var1;
         return this;
      }

      public AddressData.Builder setLocality(String var1) {
         AddressField var2 = AddressField.LOCALITY;
         return this.set(var2, var1);
      }

      public AddressData.Builder setOrganization(String var1) {
         AddressField var2 = AddressField.ORGANIZATION;
         return this.set(var2, var1);
      }

      public AddressData.Builder setPostalCode(String var1) {
         AddressField var2 = AddressField.POSTAL_CODE;
         return this.set(var2, var1);
      }

      public AddressData.Builder setRecipient(String var1) {
         AddressField var2 = AddressField.RECIPIENT;
         return this.set(var2, var1);
      }

      public AddressData.Builder setSortingCode(String var1) {
         AddressField var2 = AddressField.SORTING_CODE;
         return this.set(var2, var1);
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$i18n$addressinput$AddressField = new int[AddressField.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var1 = AddressField.COUNTRY.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var39) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var3 = AddressField.ADMIN_AREA.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var38) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var5 = AddressField.LOCALITY.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var37) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var7 = AddressField.DEPENDENT_LOCALITY.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var36) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var9 = AddressField.POSTAL_CODE.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var35) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var11 = AddressField.SORTING_CODE.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var34) {
            ;
         }

         try {
            int[] var12 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var13 = AddressField.ADDRESS_LINE_1.ordinal();
            var12[var13] = 7;
         } catch (NoSuchFieldError var33) {
            ;
         }

         try {
            int[] var14 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var15 = AddressField.ADDRESS_LINE_2.ordinal();
            var14[var15] = 8;
         } catch (NoSuchFieldError var32) {
            ;
         }

         try {
            int[] var16 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var17 = AddressField.ORGANIZATION.ordinal();
            var16[var17] = 9;
         } catch (NoSuchFieldError var31) {
            ;
         }

         try {
            int[] var18 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var19 = AddressField.RECIPIENT.ordinal();
            var18[var19] = 10;
         } catch (NoSuchFieldError var30) {
            ;
         }
      }
   }
}
