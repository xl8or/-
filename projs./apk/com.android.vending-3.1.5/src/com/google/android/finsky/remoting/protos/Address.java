package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class Address extends MessageMicro {

   public static final int ADDRESS_LINE1_FIELD_NUMBER = 2;
   public static final int ADDRESS_LINE2_FIELD_NUMBER = 3;
   public static final int CITY_FIELD_NUMBER = 4;
   public static final int DEPENDENT_LOCALITY_FIELD_NUMBER = 8;
   public static final int LANGUAGE_CODE_FIELD_NUMBER = 10;
   public static final int NAME_FIELD_NUMBER = 1;
   public static final int PHONE_NUMBER_FIELD_NUMBER = 11;
   public static final int POSTAL_CODE_FIELD_NUMBER = 6;
   public static final int POSTAL_COUNTRY_FIELD_NUMBER = 7;
   public static final int SORTING_CODE_FIELD_NUMBER = 9;
   public static final int STATE_FIELD_NUMBER = 5;
   private String addressLine1_ = "";
   private String addressLine2_ = "";
   private int cachedSize = -1;
   private String city_ = "";
   private String dependentLocality_ = "";
   private boolean hasAddressLine1;
   private boolean hasAddressLine2;
   private boolean hasCity;
   private boolean hasDependentLocality;
   private boolean hasLanguageCode;
   private boolean hasName;
   private boolean hasPhoneNumber;
   private boolean hasPostalCode;
   private boolean hasPostalCountry;
   private boolean hasSortingCode;
   private boolean hasState;
   private String languageCode_ = "";
   private String name_ = "";
   private String phoneNumber_ = "";
   private String postalCode_ = "";
   private String postalCountry_ = "";
   private String sortingCode_ = "";
   private String state_ = "";


   public Address() {}

   public static Address parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new Address()).mergeFrom(var0);
   }

   public static Address parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (Address)((Address)(new Address()).mergeFrom(var0));
   }

   public final Address clear() {
      Address var1 = this.clearName();
      Address var2 = this.clearAddressLine1();
      Address var3 = this.clearAddressLine2();
      Address var4 = this.clearCity();
      Address var5 = this.clearState();
      Address var6 = this.clearPostalCode();
      Address var7 = this.clearPostalCountry();
      Address var8 = this.clearDependentLocality();
      Address var9 = this.clearSortingCode();
      Address var10 = this.clearLanguageCode();
      Address var11 = this.clearPhoneNumber();
      this.cachedSize = -1;
      return this;
   }

   public Address clearAddressLine1() {
      this.hasAddressLine1 = (boolean)0;
      this.addressLine1_ = "";
      return this;
   }

   public Address clearAddressLine2() {
      this.hasAddressLine2 = (boolean)0;
      this.addressLine2_ = "";
      return this;
   }

   public Address clearCity() {
      this.hasCity = (boolean)0;
      this.city_ = "";
      return this;
   }

   public Address clearDependentLocality() {
      this.hasDependentLocality = (boolean)0;
      this.dependentLocality_ = "";
      return this;
   }

   public Address clearLanguageCode() {
      this.hasLanguageCode = (boolean)0;
      this.languageCode_ = "";
      return this;
   }

   public Address clearName() {
      this.hasName = (boolean)0;
      this.name_ = "";
      return this;
   }

   public Address clearPhoneNumber() {
      this.hasPhoneNumber = (boolean)0;
      this.phoneNumber_ = "";
      return this;
   }

   public Address clearPostalCode() {
      this.hasPostalCode = (boolean)0;
      this.postalCode_ = "";
      return this;
   }

   public Address clearPostalCountry() {
      this.hasPostalCountry = (boolean)0;
      this.postalCountry_ = "";
      return this;
   }

   public Address clearSortingCode() {
      this.hasSortingCode = (boolean)0;
      this.sortingCode_ = "";
      return this;
   }

   public Address clearState() {
      this.hasState = (boolean)0;
      this.state_ = "";
      return this;
   }

   public String getAddressLine1() {
      return this.addressLine1_;
   }

   public String getAddressLine2() {
      return this.addressLine2_;
   }

   public int getCachedSize() {
      if(this.cachedSize < 0) {
         int var1 = this.getSerializedSize();
      }

      return this.cachedSize;
   }

   public String getCity() {
      return this.city_;
   }

   public String getDependentLocality() {
      return this.dependentLocality_;
   }

   public String getLanguageCode() {
      return this.languageCode_;
   }

   public String getName() {
      return this.name_;
   }

   public String getPhoneNumber() {
      return this.phoneNumber_;
   }

   public String getPostalCode() {
      return this.postalCode_;
   }

   public String getPostalCountry() {
      return this.postalCountry_;
   }

   public int getSerializedSize() {
      int var1 = 0;
      if(this.hasName()) {
         String var2 = this.getName();
         int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
         var1 = 0 + var3;
      }

      if(this.hasAddressLine1()) {
         String var4 = this.getAddressLine1();
         int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
         var1 += var5;
      }

      if(this.hasAddressLine2()) {
         String var6 = this.getAddressLine2();
         int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
         var1 += var7;
      }

      if(this.hasCity()) {
         String var8 = this.getCity();
         int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
         var1 += var9;
      }

      if(this.hasState()) {
         String var10 = this.getState();
         int var11 = CodedOutputStreamMicro.computeStringSize(5, var10);
         var1 += var11;
      }

      if(this.hasPostalCode()) {
         String var12 = this.getPostalCode();
         int var13 = CodedOutputStreamMicro.computeStringSize(6, var12);
         var1 += var13;
      }

      if(this.hasPostalCountry()) {
         String var14 = this.getPostalCountry();
         int var15 = CodedOutputStreamMicro.computeStringSize(7, var14);
         var1 += var15;
      }

      if(this.hasDependentLocality()) {
         String var16 = this.getDependentLocality();
         int var17 = CodedOutputStreamMicro.computeStringSize(8, var16);
         var1 += var17;
      }

      if(this.hasSortingCode()) {
         String var18 = this.getSortingCode();
         int var19 = CodedOutputStreamMicro.computeStringSize(9, var18);
         var1 += var19;
      }

      if(this.hasLanguageCode()) {
         String var20 = this.getLanguageCode();
         int var21 = CodedOutputStreamMicro.computeStringSize(10, var20);
         var1 += var21;
      }

      if(this.hasPhoneNumber()) {
         String var22 = this.getPhoneNumber();
         int var23 = CodedOutputStreamMicro.computeStringSize(11, var22);
         var1 += var23;
      }

      this.cachedSize = var1;
      return var1;
   }

   public String getSortingCode() {
      return this.sortingCode_;
   }

   public String getState() {
      return this.state_;
   }

   public boolean hasAddressLine1() {
      return this.hasAddressLine1;
   }

   public boolean hasAddressLine2() {
      return this.hasAddressLine2;
   }

   public boolean hasCity() {
      return this.hasCity;
   }

   public boolean hasDependentLocality() {
      return this.hasDependentLocality;
   }

   public boolean hasLanguageCode() {
      return this.hasLanguageCode;
   }

   public boolean hasName() {
      return this.hasName;
   }

   public boolean hasPhoneNumber() {
      return this.hasPhoneNumber;
   }

   public boolean hasPostalCode() {
      return this.hasPostalCode;
   }

   public boolean hasPostalCountry() {
      return this.hasPostalCountry;
   }

   public boolean hasSortingCode() {
      return this.hasSortingCode;
   }

   public boolean hasState() {
      return this.hasState;
   }

   public final boolean isInitialized() {
      return true;
   }

   public Address mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 10:
            String var3 = var1.readString();
            this.setName(var3);
            break;
         case 18:
            String var5 = var1.readString();
            this.setAddressLine1(var5);
            break;
         case 26:
            String var7 = var1.readString();
            this.setAddressLine2(var7);
            break;
         case 34:
            String var9 = var1.readString();
            this.setCity(var9);
            break;
         case 42:
            String var11 = var1.readString();
            this.setState(var11);
            break;
         case 50:
            String var13 = var1.readString();
            this.setPostalCode(var13);
            break;
         case 58:
            String var15 = var1.readString();
            this.setPostalCountry(var15);
            break;
         case 66:
            String var17 = var1.readString();
            this.setDependentLocality(var17);
            break;
         case 74:
            String var19 = var1.readString();
            this.setSortingCode(var19);
            break;
         case 82:
            String var21 = var1.readString();
            this.setLanguageCode(var21);
            break;
         case 90:
            String var23 = var1.readString();
            this.setPhoneNumber(var23);
            break;
         default:
            if(this.parseUnknownField(var1, var2)) {
               break;
            }
         case 0:
            return this;
         }
      }
   }

   public Address setAddressLine1(String var1) {
      this.hasAddressLine1 = (boolean)1;
      this.addressLine1_ = var1;
      return this;
   }

   public Address setAddressLine2(String var1) {
      this.hasAddressLine2 = (boolean)1;
      this.addressLine2_ = var1;
      return this;
   }

   public Address setCity(String var1) {
      this.hasCity = (boolean)1;
      this.city_ = var1;
      return this;
   }

   public Address setDependentLocality(String var1) {
      this.hasDependentLocality = (boolean)1;
      this.dependentLocality_ = var1;
      return this;
   }

   public Address setLanguageCode(String var1) {
      this.hasLanguageCode = (boolean)1;
      this.languageCode_ = var1;
      return this;
   }

   public Address setName(String var1) {
      this.hasName = (boolean)1;
      this.name_ = var1;
      return this;
   }

   public Address setPhoneNumber(String var1) {
      this.hasPhoneNumber = (boolean)1;
      this.phoneNumber_ = var1;
      return this;
   }

   public Address setPostalCode(String var1) {
      this.hasPostalCode = (boolean)1;
      this.postalCode_ = var1;
      return this;
   }

   public Address setPostalCountry(String var1) {
      this.hasPostalCountry = (boolean)1;
      this.postalCountry_ = var1;
      return this;
   }

   public Address setSortingCode(String var1) {
      this.hasSortingCode = (boolean)1;
      this.sortingCode_ = var1;
      return this;
   }

   public Address setState(String var1) {
      this.hasState = (boolean)1;
      this.state_ = var1;
      return this;
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      if(this.hasName()) {
         String var2 = this.getName();
         var1.writeString(1, var2);
      }

      if(this.hasAddressLine1()) {
         String var3 = this.getAddressLine1();
         var1.writeString(2, var3);
      }

      if(this.hasAddressLine2()) {
         String var4 = this.getAddressLine2();
         var1.writeString(3, var4);
      }

      if(this.hasCity()) {
         String var5 = this.getCity();
         var1.writeString(4, var5);
      }

      if(this.hasState()) {
         String var6 = this.getState();
         var1.writeString(5, var6);
      }

      if(this.hasPostalCode()) {
         String var7 = this.getPostalCode();
         var1.writeString(6, var7);
      }

      if(this.hasPostalCountry()) {
         String var8 = this.getPostalCountry();
         var1.writeString(7, var8);
      }

      if(this.hasDependentLocality()) {
         String var9 = this.getDependentLocality();
         var1.writeString(8, var9);
      }

      if(this.hasSortingCode()) {
         String var10 = this.getSortingCode();
         var1.writeString(9, var10);
      }

      if(this.hasLanguageCode()) {
         String var11 = this.getLanguageCode();
         var1.writeString(10, var11);
      }

      if(this.hasPhoneNumber()) {
         String var12 = this.getPhoneNumber();
         var1.writeString(11, var12);
      }
   }
}
