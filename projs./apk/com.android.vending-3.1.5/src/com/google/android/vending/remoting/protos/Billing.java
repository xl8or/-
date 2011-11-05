package com.google.android.vending.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class Billing {

   private Billing() {}

   public static final class Money extends MessageMicro {

      public static final int CURRENCY_CODE_FIELD_NUMBER = 2;
      public static final int MICROS_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private String currencyCode_ = "";
      private boolean hasCurrencyCode;
      private boolean hasMicros;
      private long micros_ = 0L;


      public Money() {}

      public static Billing.Money parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Billing.Money()).mergeFrom(var0);
      }

      public static Billing.Money parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Billing.Money)((Billing.Money)(new Billing.Money()).mergeFrom(var0));
      }

      public final Billing.Money clear() {
         Billing.Money var1 = this.clearMicros();
         Billing.Money var2 = this.clearCurrencyCode();
         this.cachedSize = -1;
         return this;
      }

      public Billing.Money clearCurrencyCode() {
         this.hasCurrencyCode = (boolean)0;
         this.currencyCode_ = "";
         return this;
      }

      public Billing.Money clearMicros() {
         this.hasMicros = (boolean)0;
         this.micros_ = 0L;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getCurrencyCode() {
         return this.currencyCode_;
      }

      public long getMicros() {
         return this.micros_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasMicros()) {
            long var2 = this.getMicros();
            int var4 = CodedOutputStreamMicro.computeInt64Size(1, var2);
            var1 = 0 + var4;
         }

         if(this.hasCurrencyCode()) {
            String var5 = this.getCurrencyCode();
            int var6 = CodedOutputStreamMicro.computeStringSize(2, var5);
            var1 += var6;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasCurrencyCode() {
         return this.hasCurrencyCode;
      }

      public boolean hasMicros() {
         return this.hasMicros;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasMicros && this.hasCurrencyCode) {
            var1 = true;
         }

         return var1;
      }

      public Billing.Money mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               long var3 = var1.readInt64();
               this.setMicros(var3);
               break;
            case 18:
               String var6 = var1.readString();
               this.setCurrencyCode(var6);
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

      public Billing.Money setCurrencyCode(String var1) {
         this.hasCurrencyCode = (boolean)1;
         this.currencyCode_ = var1;
         return this;
      }

      public Billing.Money setMicros(long var1) {
         this.hasMicros = (boolean)1;
         this.micros_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasMicros()) {
            long var2 = this.getMicros();
            var1.writeInt64(1, var2);
         }

         if(this.hasCurrencyCode()) {
            String var4 = this.getCurrencyCode();
            var1.writeString(2, var4);
         }
      }
   }

   public static final class BillingSharedEnums extends MessageMicro {

      public static final int FINSKY_CLIENT = 2;
      public static final int FINSKY_WEB = 1;
      public static final int MARKET_CLIENT = 0;
      public static final int MARKET_IN_APP_PRODUCT = 1;
      public static final int MARKET_PRODUCT;
      private int cachedSize = -1;


      public BillingSharedEnums() {}

      public static Billing.BillingSharedEnums parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Billing.BillingSharedEnums()).mergeFrom(var0);
      }

      public static Billing.BillingSharedEnums parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Billing.BillingSharedEnums)((Billing.BillingSharedEnums)(new Billing.BillingSharedEnums()).mergeFrom(var0));
      }

      public final Billing.BillingSharedEnums clear() {
         this.cachedSize = -1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getSerializedSize() {
         this.cachedSize = 0;
         return 0;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Billing.BillingSharedEnums mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {}
   }

   public static final class CountryPriceProto extends MessageMicro {

      public static final int COUNTRY_CODE_FIELD_NUMBER = 1;
      public static final int PRICE_FIELD_NUMBER = 2;
      private int cachedSize = -1;
      private String countryCode_ = "";
      private boolean hasCountryCode;
      private boolean hasPrice;
      private Billing.Money price_ = null;


      public CountryPriceProto() {}

      public static Billing.CountryPriceProto parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Billing.CountryPriceProto()).mergeFrom(var0);
      }

      public static Billing.CountryPriceProto parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Billing.CountryPriceProto)((Billing.CountryPriceProto)(new Billing.CountryPriceProto()).mergeFrom(var0));
      }

      public final Billing.CountryPriceProto clear() {
         Billing.CountryPriceProto var1 = this.clearCountryCode();
         Billing.CountryPriceProto var2 = this.clearPrice();
         this.cachedSize = -1;
         return this;
      }

      public Billing.CountryPriceProto clearCountryCode() {
         this.hasCountryCode = (boolean)0;
         this.countryCode_ = "";
         return this;
      }

      public Billing.CountryPriceProto clearPrice() {
         this.hasPrice = (boolean)0;
         this.price_ = null;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getCountryCode() {
         return this.countryCode_;
      }

      public Billing.Money getPrice() {
         return this.price_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasCountryCode()) {
            String var2 = this.getCountryCode();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasPrice()) {
            Billing.Money var4 = this.getPrice();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasCountryCode() {
         return this.hasCountryCode;
      }

      public boolean hasPrice() {
         return this.hasPrice;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasCountryCode && this.hasPrice && this.getPrice().isInitialized()) {
            var1 = true;
         }

         return var1;
      }

      public Billing.CountryPriceProto mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setCountryCode(var3);
               break;
            case 18:
               Billing.Money var5 = new Billing.Money();
               var1.readMessage(var5);
               this.setPrice(var5);
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

      public Billing.CountryPriceProto setCountryCode(String var1) {
         this.hasCountryCode = (boolean)1;
         this.countryCode_ = var1;
         return this;
      }

      public Billing.CountryPriceProto setPrice(Billing.Money var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPrice = (boolean)1;
            this.price_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasCountryCode()) {
            String var2 = this.getCountryCode();
            var1.writeString(1, var2);
         }

         if(this.hasPrice()) {
            Billing.Money var3 = this.getPrice();
            var1.writeMessage(2, var3);
         }
      }
   }
}
