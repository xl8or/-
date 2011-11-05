package com.google.android.finsky.billing.carrierbilling;

import android.text.TextUtils;
import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressProblems;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.remoting.protos.Address;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class PhoneCarrierBillingUtils {

   private static final EnumMap<AddressField, PhoneCarrierBillingUtils.AddressInputField> addressMap = new EnumMap(AddressField.class);


   static {
      EnumMap var0 = addressMap;
      AddressField var1 = AddressField.STREET_ADDRESS;
      PhoneCarrierBillingUtils.AddressInputField var2 = PhoneCarrierBillingUtils.AddressInputField.ADDR_ADDRESS1;
      var0.put(var1, var2);
      EnumMap var4 = addressMap;
      AddressField var5 = AddressField.ADDRESS_LINE_2;
      PhoneCarrierBillingUtils.AddressInputField var6 = PhoneCarrierBillingUtils.AddressInputField.ADDR_ADDRESS2;
      var4.put(var5, var6);
      EnumMap var8 = addressMap;
      AddressField var9 = AddressField.LOCALITY;
      PhoneCarrierBillingUtils.AddressInputField var10 = PhoneCarrierBillingUtils.AddressInputField.ADDR_CITY;
      var8.put(var9, var10);
      EnumMap var12 = addressMap;
      AddressField var13 = AddressField.ADMIN_AREA;
      PhoneCarrierBillingUtils.AddressInputField var14 = PhoneCarrierBillingUtils.AddressInputField.ADDR_STATE;
      var12.put(var13, var14);
      EnumMap var16 = addressMap;
      AddressField var17 = AddressField.POSTAL_CODE;
      PhoneCarrierBillingUtils.AddressInputField var18 = PhoneCarrierBillingUtils.AddressInputField.ADDR_POSTAL_CODE;
      var16.put(var17, var18);
      EnumMap var20 = addressMap;
      AddressField var21 = AddressField.COUNTRY;
      PhoneCarrierBillingUtils.AddressInputField var22 = PhoneCarrierBillingUtils.AddressInputField.ADDR_COUNTRY_CODE;
      var20.put(var21, var22);
   }

   private PhoneCarrierBillingUtils() {}

   private static PhoneCarrierBillingUtils.AddressInputField convertAddressFieldToInputField(AddressField var0) {
      return (PhoneCarrierBillingUtils.AddressInputField)addressMap.get(var0);
   }

   public static Collection<PhoneCarrierBillingUtils.AddressInputField> getErrors(String var0, String var1, AddressProblems var2) {
      ArrayList var3 = new ArrayList();
      if(BillingUtils.isEmptyOrSpaces(var0)) {
         PhoneCarrierBillingUtils.AddressInputField var4 = PhoneCarrierBillingUtils.AddressInputField.PERSON_NAME;
         var3.add(var4);
      }

      if(BillingUtils.isEmptyOrSpaces(var1)) {
         PhoneCarrierBillingUtils.AddressInputField var6 = PhoneCarrierBillingUtils.AddressInputField.ADDR_PHONE;
         var3.add(var6);
      }

      Iterator var8 = var2.getProblems().entrySet().iterator();

      while(var8.hasNext()) {
         PhoneCarrierBillingUtils.AddressInputField var9 = convertAddressFieldToInputField((AddressField)((Entry)var8.next()).getKey());
         if(var9 != null) {
            var3.add(var9);
         }
      }

      return var3;
   }

   public static Address subscriberInfoToAddress(SubscriberInfo var0) {
      Address var1 = new Address();
      if(!TextUtils.isEmpty(var0.getName())) {
         String var2 = var0.getName();
         var1.setName(var2);
      }

      if(!TextUtils.isEmpty(var0.getIdentifier())) {
         String var4 = var0.getIdentifier();
         var1.setPhoneNumber(var4);
      }

      if(!TextUtils.isEmpty(var0.getAddress1())) {
         String var6 = var0.getAddress1();
         var1.setAddressLine1(var6);
      }

      if(!TextUtils.isEmpty(var0.getAddress2())) {
         String var8 = var0.getAddress2();
         var1.setAddressLine2(var8);
      }

      if(!TextUtils.isEmpty(var0.getCity())) {
         String var10 = var0.getCity();
         var1.setCity(var10);
      }

      if(!TextUtils.isEmpty(var0.getState())) {
         String var12 = var0.getState();
         var1.setState(var12);
      }

      if(!TextUtils.isEmpty(var0.getPostalCode())) {
         String var14 = var0.getPostalCode();
         var1.setPostalCode(var14);
      }

      if(!TextUtils.isEmpty(var0.getCountry())) {
         String var16 = var0.getCountry();
         var1.setPostalCountry(var16);
      }

      return var1;
   }

   public static AddressData subscriberInfoToAddressData(SubscriberInfo var0) {
      AddressData.Builder var1 = new AddressData.Builder();
      String var2 = var0.getAddress1();
      AddressData.Builder var3 = var1.setAddressLine1(var2);
      String var4 = var0.getAddress2();
      AddressData.Builder var5 = var3.setAddressLine2(var4);
      String var6 = var0.getCity();
      AddressData.Builder var7 = var5.setLocality(var6);
      String var8 = var0.getState();
      AddressData.Builder var9 = var7.setAdminArea(var8);
      String var10 = var0.getPostalCode();
      AddressData.Builder var11 = var9.setPostalCode(var10);
      String var12 = var0.getCountry();
      return var11.setCountry(var12).build();
   }

   public static enum AddressInputField {

      // $FF: synthetic field
      private static final PhoneCarrierBillingUtils.AddressInputField[] $VALUES;
      ADDR_ADDRESS1("ADDR_ADDRESS1", 3),
      ADDR_ADDRESS2("ADDR_ADDRESS2", 4),
      ADDR_CITY("ADDR_CITY", 5),
      ADDR_COUNTRY_CODE("ADDR_COUNTRY_CODE", 1),
      ADDR_PHONE("ADDR_PHONE", 7),
      ADDR_POSTAL_CODE("ADDR_POSTAL_CODE", 2),
      ADDR_STATE("ADDR_STATE", 6),
      PERSON_NAME("PERSON_NAME", 0);


      static {
         PhoneCarrierBillingUtils.AddressInputField[] var0 = new PhoneCarrierBillingUtils.AddressInputField[8];
         PhoneCarrierBillingUtils.AddressInputField var1 = PERSON_NAME;
         var0[0] = var1;
         PhoneCarrierBillingUtils.AddressInputField var2 = ADDR_COUNTRY_CODE;
         var0[1] = var2;
         PhoneCarrierBillingUtils.AddressInputField var3 = ADDR_POSTAL_CODE;
         var0[2] = var3;
         PhoneCarrierBillingUtils.AddressInputField var4 = ADDR_ADDRESS1;
         var0[3] = var4;
         PhoneCarrierBillingUtils.AddressInputField var5 = ADDR_ADDRESS2;
         var0[4] = var5;
         PhoneCarrierBillingUtils.AddressInputField var6 = ADDR_CITY;
         var0[5] = var6;
         PhoneCarrierBillingUtils.AddressInputField var7 = ADDR_STATE;
         var0[6] = var7;
         PhoneCarrierBillingUtils.AddressInputField var8 = ADDR_PHONE;
         var0[7] = var8;
         $VALUES = var0;
      }

      private AddressInputField(String var1, int var2) {}
   }
}
