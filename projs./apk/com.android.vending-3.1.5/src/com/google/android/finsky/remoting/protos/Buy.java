package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Common;
import com.google.android.finsky.remoting.protos.Purchase;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Buy {

   private Buy() {}

   public static final class LineItem extends MessageMicro {

      public static final int AMOUNT_FIELD_NUMBER = 4;
      public static final int DESCRIPTION_FIELD_NUMBER = 2;
      public static final int NAME_FIELD_NUMBER = 1;
      public static final int OFFER_FIELD_NUMBER = 3;
      private Buy.Money amount_ = null;
      private int cachedSize = -1;
      private String description_ = "";
      private boolean hasAmount;
      private boolean hasDescription;
      private boolean hasName;
      private boolean hasOffer;
      private String name_ = "";
      private Common.Offer offer_ = null;


      public LineItem() {}

      public static Buy.LineItem parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Buy.LineItem()).mergeFrom(var0);
      }

      public static Buy.LineItem parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Buy.LineItem)((Buy.LineItem)(new Buy.LineItem()).mergeFrom(var0));
      }

      public final Buy.LineItem clear() {
         Buy.LineItem var1 = this.clearName();
         Buy.LineItem var2 = this.clearDescription();
         Buy.LineItem var3 = this.clearOffer();
         Buy.LineItem var4 = this.clearAmount();
         this.cachedSize = -1;
         return this;
      }

      public Buy.LineItem clearAmount() {
         this.hasAmount = (boolean)0;
         this.amount_ = null;
         return this;
      }

      public Buy.LineItem clearDescription() {
         this.hasDescription = (boolean)0;
         this.description_ = "";
         return this;
      }

      public Buy.LineItem clearName() {
         this.hasName = (boolean)0;
         this.name_ = "";
         return this;
      }

      public Buy.LineItem clearOffer() {
         this.hasOffer = (boolean)0;
         this.offer_ = null;
         return this;
      }

      public Buy.Money getAmount() {
         return this.amount_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getDescription() {
         return this.description_;
      }

      public String getName() {
         return this.name_;
      }

      public Common.Offer getOffer() {
         return this.offer_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasName()) {
            String var2 = this.getName();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasDescription()) {
            String var4 = this.getDescription();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasOffer()) {
            Common.Offer var6 = this.getOffer();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         if(this.hasAmount()) {
            Buy.Money var8 = this.getAmount();
            int var9 = CodedOutputStreamMicro.computeMessageSize(4, var8);
            var1 += var9;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasAmount() {
         return this.hasAmount;
      }

      public boolean hasDescription() {
         return this.hasDescription;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public boolean hasOffer() {
         return this.hasOffer;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if((!this.hasOffer() || this.getOffer().isInitialized()) && (!this.hasAmount() || this.getAmount().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public Buy.LineItem mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setName(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setDescription(var5);
               break;
            case 26:
               Common.Offer var7 = new Common.Offer();
               var1.readMessage(var7);
               this.setOffer(var7);
               break;
            case 34:
               Buy.Money var9 = new Buy.Money();
               var1.readMessage(var9);
               this.setAmount(var9);
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

      public Buy.LineItem setAmount(Buy.Money var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasAmount = (boolean)1;
            this.amount_ = var1;
            return this;
         }
      }

      public Buy.LineItem setDescription(String var1) {
         this.hasDescription = (boolean)1;
         this.description_ = var1;
         return this;
      }

      public Buy.LineItem setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public Buy.LineItem setOffer(Common.Offer var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasOffer = (boolean)1;
            this.offer_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasName()) {
            String var2 = this.getName();
            var1.writeString(1, var2);
         }

         if(this.hasDescription()) {
            String var3 = this.getDescription();
            var1.writeString(2, var3);
         }

         if(this.hasOffer()) {
            Common.Offer var4 = this.getOffer();
            var1.writeMessage(3, var4);
         }

         if(this.hasAmount()) {
            Buy.Money var5 = this.getAmount();
            var1.writeMessage(4, var5);
         }
      }
   }

   public static final class Money extends MessageMicro {

      public static final int CURRENCY_CODE_FIELD_NUMBER = 2;
      public static final int FORMATTED_AMOUNT_FIELD_NUMBER = 3;
      public static final int MICROS_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private String currencyCode_ = "";
      private String formattedAmount_ = "";
      private boolean hasCurrencyCode;
      private boolean hasFormattedAmount;
      private boolean hasMicros;
      private long micros_ = 0L;


      public Money() {}

      public static Buy.Money parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Buy.Money()).mergeFrom(var0);
      }

      public static Buy.Money parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Buy.Money)((Buy.Money)(new Buy.Money()).mergeFrom(var0));
      }

      public final Buy.Money clear() {
         Buy.Money var1 = this.clearMicros();
         Buy.Money var2 = this.clearCurrencyCode();
         Buy.Money var3 = this.clearFormattedAmount();
         this.cachedSize = -1;
         return this;
      }

      public Buy.Money clearCurrencyCode() {
         this.hasCurrencyCode = (boolean)0;
         this.currencyCode_ = "";
         return this;
      }

      public Buy.Money clearFormattedAmount() {
         this.hasFormattedAmount = (boolean)0;
         this.formattedAmount_ = "";
         return this;
      }

      public Buy.Money clearMicros() {
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

      public String getFormattedAmount() {
         return this.formattedAmount_;
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

         if(this.hasFormattedAmount()) {
            String var7 = this.getFormattedAmount();
            int var8 = CodedOutputStreamMicro.computeStringSize(3, var7);
            var1 += var8;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasCurrencyCode() {
         return this.hasCurrencyCode;
      }

      public boolean hasFormattedAmount() {
         return this.hasFormattedAmount;
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

      public Buy.Money mergeFrom(CodedInputStreamMicro var1) throws IOException {
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
            case 26:
               String var8 = var1.readString();
               this.setFormattedAmount(var8);
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

      public Buy.Money setCurrencyCode(String var1) {
         this.hasCurrencyCode = (boolean)1;
         this.currencyCode_ = var1;
         return this;
      }

      public Buy.Money setFormattedAmount(String var1) {
         this.hasFormattedAmount = (boolean)1;
         this.formattedAmount_ = var1;
         return this;
      }

      public Buy.Money setMicros(long var1) {
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

         if(this.hasFormattedAmount()) {
            String var5 = this.getFormattedAmount();
            var1.writeString(3, var5);
         }
      }
   }

   public static final class BuyResponse extends MessageMicro {

      public static final int BASE_CHECKOUT_URL_FIELD_NUMBER = 14;
      public static final int CHECKOUTINFO_FIELD_NUMBER = 2;
      public static final int CHECKOUT_SERVICE_ID_FIELD_NUMBER = 12;
      public static final int CHECKOUT_TOKEN_REQUIRED_FIELD_NUMBER = 13;
      public static final int CONTINUE_VIA_URL_FIELD_NUMBER = 8;
      public static final int DEPRECATEDTOS_FIELD_NUMBER = 24;
      public static final int IAB_PERMISSION_ERROR_FIELD_NUMBER = 38;
      public static final int PURCHASE_RESPONSE_FIELD_NUMBER = 1;
      public static final int PURCHASE_STATUS_URL_FIELD_NUMBER = 9;
      public static final int TOS_CHECKBOX_HTML_FIELD_NUMBER = 37;
      private String baseCheckoutUrl_ = "";
      private int cachedSize;
      private Buy.BuyResponse.CheckoutInfo checkoutInfo_ = null;
      private String checkoutServiceId_ = "";
      private boolean checkoutTokenRequired_ = 0;
      private String continueViaUrl_ = "";
      private List<Buy.BuyResponse.DeprecatedTos> deprecatedTos_;
      private boolean hasBaseCheckoutUrl;
      private boolean hasCheckoutInfo;
      private boolean hasCheckoutServiceId;
      private boolean hasCheckoutTokenRequired;
      private boolean hasContinueViaUrl;
      private boolean hasIabPermissionError;
      private boolean hasPurchaseResponse;
      private boolean hasPurchaseStatusUrl;
      private int iabPermissionError_;
      private Purchase.PurchaseNotificationResponse purchaseResponse_ = null;
      private String purchaseStatusUrl_ = "";
      private List<String> tosCheckboxHtml_;


      public BuyResponse() {
         List var1 = Collections.emptyList();
         this.deprecatedTos_ = var1;
         List var2 = Collections.emptyList();
         this.tosCheckboxHtml_ = var2;
         this.iabPermissionError_ = 0;
         this.cachedSize = -1;
      }

      public static Buy.BuyResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Buy.BuyResponse()).mergeFrom(var0);
      }

      public static Buy.BuyResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Buy.BuyResponse)((Buy.BuyResponse)(new Buy.BuyResponse()).mergeFrom(var0));
      }

      public Buy.BuyResponse addDeprecatedTos(Buy.BuyResponse.DeprecatedTos var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.deprecatedTos_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.deprecatedTos_ = var2;
            }

            this.deprecatedTos_.add(var1);
            return this;
         }
      }

      public Buy.BuyResponse addTosCheckboxHtml(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.tosCheckboxHtml_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.tosCheckboxHtml_ = var2;
            }

            this.tosCheckboxHtml_.add(var1);
            return this;
         }
      }

      public final Buy.BuyResponse clear() {
         Buy.BuyResponse var1 = this.clearPurchaseResponse();
         Buy.BuyResponse var2 = this.clearCheckoutInfo();
         Buy.BuyResponse var3 = this.clearContinueViaUrl();
         Buy.BuyResponse var4 = this.clearCheckoutTokenRequired();
         Buy.BuyResponse var5 = this.clearCheckoutServiceId();
         Buy.BuyResponse var6 = this.clearBaseCheckoutUrl();
         Buy.BuyResponse var7 = this.clearPurchaseStatusUrl();
         Buy.BuyResponse var8 = this.clearDeprecatedTos();
         Buy.BuyResponse var9 = this.clearTosCheckboxHtml();
         Buy.BuyResponse var10 = this.clearIabPermissionError();
         this.cachedSize = -1;
         return this;
      }

      public Buy.BuyResponse clearBaseCheckoutUrl() {
         this.hasBaseCheckoutUrl = (boolean)0;
         this.baseCheckoutUrl_ = "";
         return this;
      }

      public Buy.BuyResponse clearCheckoutInfo() {
         this.hasCheckoutInfo = (boolean)0;
         this.checkoutInfo_ = null;
         return this;
      }

      public Buy.BuyResponse clearCheckoutServiceId() {
         this.hasCheckoutServiceId = (boolean)0;
         this.checkoutServiceId_ = "";
         return this;
      }

      public Buy.BuyResponse clearCheckoutTokenRequired() {
         this.hasCheckoutTokenRequired = (boolean)0;
         this.checkoutTokenRequired_ = (boolean)0;
         return this;
      }

      public Buy.BuyResponse clearContinueViaUrl() {
         this.hasContinueViaUrl = (boolean)0;
         this.continueViaUrl_ = "";
         return this;
      }

      public Buy.BuyResponse clearDeprecatedTos() {
         List var1 = Collections.emptyList();
         this.deprecatedTos_ = var1;
         return this;
      }

      public Buy.BuyResponse clearIabPermissionError() {
         this.hasIabPermissionError = (boolean)0;
         this.iabPermissionError_ = 0;
         return this;
      }

      public Buy.BuyResponse clearPurchaseResponse() {
         this.hasPurchaseResponse = (boolean)0;
         this.purchaseResponse_ = null;
         return this;
      }

      public Buy.BuyResponse clearPurchaseStatusUrl() {
         this.hasPurchaseStatusUrl = (boolean)0;
         this.purchaseStatusUrl_ = "";
         return this;
      }

      public Buy.BuyResponse clearTosCheckboxHtml() {
         List var1 = Collections.emptyList();
         this.tosCheckboxHtml_ = var1;
         return this;
      }

      public String getBaseCheckoutUrl() {
         return this.baseCheckoutUrl_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Buy.BuyResponse.CheckoutInfo getCheckoutInfo() {
         return this.checkoutInfo_;
      }

      public String getCheckoutServiceId() {
         return this.checkoutServiceId_;
      }

      public boolean getCheckoutTokenRequired() {
         return this.checkoutTokenRequired_;
      }

      public String getContinueViaUrl() {
         return this.continueViaUrl_;
      }

      public Buy.BuyResponse.DeprecatedTos getDeprecatedTos(int var1) {
         return (Buy.BuyResponse.DeprecatedTos)this.deprecatedTos_.get(var1);
      }

      public int getDeprecatedTosCount() {
         return this.deprecatedTos_.size();
      }

      public List<Buy.BuyResponse.DeprecatedTos> getDeprecatedTosList() {
         return this.deprecatedTos_;
      }

      public int getIabPermissionError() {
         return this.iabPermissionError_;
      }

      public Purchase.PurchaseNotificationResponse getPurchaseResponse() {
         return this.purchaseResponse_;
      }

      public String getPurchaseStatusUrl() {
         return this.purchaseStatusUrl_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasPurchaseResponse()) {
            Purchase.PurchaseNotificationResponse var2 = this.getPurchaseResponse();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasCheckoutInfo()) {
            Buy.BuyResponse.CheckoutInfo var4 = this.getCheckoutInfo();
            int var5 = CodedOutputStreamMicro.computeGroupSize(2, var4);
            var1 += var5;
         }

         if(this.hasContinueViaUrl()) {
            String var6 = this.getContinueViaUrl();
            int var7 = CodedOutputStreamMicro.computeStringSize(8, var6);
            var1 += var7;
         }

         if(this.hasPurchaseStatusUrl()) {
            String var8 = this.getPurchaseStatusUrl();
            int var9 = CodedOutputStreamMicro.computeStringSize(9, var8);
            var1 += var9;
         }

         if(this.hasCheckoutServiceId()) {
            String var10 = this.getCheckoutServiceId();
            int var11 = CodedOutputStreamMicro.computeStringSize(12, var10);
            var1 += var11;
         }

         if(this.hasCheckoutTokenRequired()) {
            boolean var12 = this.getCheckoutTokenRequired();
            int var13 = CodedOutputStreamMicro.computeBoolSize(13, var12);
            var1 += var13;
         }

         if(this.hasBaseCheckoutUrl()) {
            String var14 = this.getBaseCheckoutUrl();
            int var15 = CodedOutputStreamMicro.computeStringSize(14, var14);
            var1 += var15;
         }

         int var18;
         for(Iterator var16 = this.getDeprecatedTosList().iterator(); var16.hasNext(); var1 += var18) {
            Buy.BuyResponse.DeprecatedTos var17 = (Buy.BuyResponse.DeprecatedTos)var16.next();
            var18 = CodedOutputStreamMicro.computeGroupSize(24, var17);
         }

         int var19 = 0;

         int var21;
         for(Iterator var20 = this.getTosCheckboxHtmlList().iterator(); var20.hasNext(); var19 += var21) {
            var21 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var20.next());
         }

         int var22 = var1 + var19;
         int var23 = this.getTosCheckboxHtmlList().size() * 2;
         int var24 = var22 + var23;
         if(this.hasIabPermissionError()) {
            int var25 = this.getIabPermissionError();
            int var26 = CodedOutputStreamMicro.computeInt32Size(38, var25);
            var24 += var26;
         }

         this.cachedSize = var24;
         return var24;
      }

      public String getTosCheckboxHtml(int var1) {
         return (String)this.tosCheckboxHtml_.get(var1);
      }

      public int getTosCheckboxHtmlCount() {
         return this.tosCheckboxHtml_.size();
      }

      public List<String> getTosCheckboxHtmlList() {
         return this.tosCheckboxHtml_;
      }

      public boolean hasBaseCheckoutUrl() {
         return this.hasBaseCheckoutUrl;
      }

      public boolean hasCheckoutInfo() {
         return this.hasCheckoutInfo;
      }

      public boolean hasCheckoutServiceId() {
         return this.hasCheckoutServiceId;
      }

      public boolean hasCheckoutTokenRequired() {
         return this.hasCheckoutTokenRequired;
      }

      public boolean hasContinueViaUrl() {
         return this.hasContinueViaUrl;
      }

      public boolean hasIabPermissionError() {
         return this.hasIabPermissionError;
      }

      public boolean hasPurchaseResponse() {
         return this.hasPurchaseResponse;
      }

      public boolean hasPurchaseStatusUrl() {
         return this.hasPurchaseStatusUrl;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if((!this.hasPurchaseResponse() || this.getPurchaseResponse().isInitialized()) && (!this.hasCheckoutInfo() || this.getCheckoutInfo().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public Buy.BuyResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Purchase.PurchaseNotificationResponse var3 = new Purchase.PurchaseNotificationResponse();
               var1.readMessage(var3);
               this.setPurchaseResponse(var3);
               break;
            case 19:
               Buy.BuyResponse.CheckoutInfo var5 = new Buy.BuyResponse.CheckoutInfo();
               var1.readGroup(var5, 2);
               this.setCheckoutInfo(var5);
               break;
            case 66:
               String var7 = var1.readString();
               this.setContinueViaUrl(var7);
               break;
            case 74:
               String var9 = var1.readString();
               this.setPurchaseStatusUrl(var9);
               break;
            case 98:
               String var11 = var1.readString();
               this.setCheckoutServiceId(var11);
               break;
            case 104:
               boolean var13 = var1.readBool();
               this.setCheckoutTokenRequired(var13);
               break;
            case 114:
               String var15 = var1.readString();
               this.setBaseCheckoutUrl(var15);
               break;
            case 195:
               Buy.BuyResponse.DeprecatedTos var17 = new Buy.BuyResponse.DeprecatedTos();
               var1.readGroup(var17, 24);
               this.addDeprecatedTos(var17);
               break;
            case 298:
               String var19 = var1.readString();
               this.addTosCheckboxHtml(var19);
               break;
            case 304:
               int var21 = var1.readInt32();
               this.setIabPermissionError(var21);
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

      public Buy.BuyResponse setBaseCheckoutUrl(String var1) {
         this.hasBaseCheckoutUrl = (boolean)1;
         this.baseCheckoutUrl_ = var1;
         return this;
      }

      public Buy.BuyResponse setCheckoutInfo(Buy.BuyResponse.CheckoutInfo var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasCheckoutInfo = (boolean)1;
            this.checkoutInfo_ = var1;
            return this;
         }
      }

      public Buy.BuyResponse setCheckoutServiceId(String var1) {
         this.hasCheckoutServiceId = (boolean)1;
         this.checkoutServiceId_ = var1;
         return this;
      }

      public Buy.BuyResponse setCheckoutTokenRequired(boolean var1) {
         this.hasCheckoutTokenRequired = (boolean)1;
         this.checkoutTokenRequired_ = var1;
         return this;
      }

      public Buy.BuyResponse setContinueViaUrl(String var1) {
         this.hasContinueViaUrl = (boolean)1;
         this.continueViaUrl_ = var1;
         return this;
      }

      public Buy.BuyResponse setDeprecatedTos(int var1, Buy.BuyResponse.DeprecatedTos var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.deprecatedTos_.set(var1, var2);
            return this;
         }
      }

      public Buy.BuyResponse setIabPermissionError(int var1) {
         this.hasIabPermissionError = (boolean)1;
         this.iabPermissionError_ = var1;
         return this;
      }

      public Buy.BuyResponse setPurchaseResponse(Purchase.PurchaseNotificationResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPurchaseResponse = (boolean)1;
            this.purchaseResponse_ = var1;
            return this;
         }
      }

      public Buy.BuyResponse setPurchaseStatusUrl(String var1) {
         this.hasPurchaseStatusUrl = (boolean)1;
         this.purchaseStatusUrl_ = var1;
         return this;
      }

      public Buy.BuyResponse setTosCheckboxHtml(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.tosCheckboxHtml_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasPurchaseResponse()) {
            Purchase.PurchaseNotificationResponse var2 = this.getPurchaseResponse();
            var1.writeMessage(1, var2);
         }

         if(this.hasCheckoutInfo()) {
            Buy.BuyResponse.CheckoutInfo var3 = this.getCheckoutInfo();
            var1.writeGroup(2, var3);
         }

         if(this.hasContinueViaUrl()) {
            String var4 = this.getContinueViaUrl();
            var1.writeString(8, var4);
         }

         if(this.hasPurchaseStatusUrl()) {
            String var5 = this.getPurchaseStatusUrl();
            var1.writeString(9, var5);
         }

         if(this.hasCheckoutServiceId()) {
            String var6 = this.getCheckoutServiceId();
            var1.writeString(12, var6);
         }

         if(this.hasCheckoutTokenRequired()) {
            boolean var7 = this.getCheckoutTokenRequired();
            var1.writeBool(13, var7);
         }

         if(this.hasBaseCheckoutUrl()) {
            String var8 = this.getBaseCheckoutUrl();
            var1.writeString(14, var8);
         }

         Iterator var9 = this.getDeprecatedTosList().iterator();

         while(var9.hasNext()) {
            Buy.BuyResponse.DeprecatedTos var10 = (Buy.BuyResponse.DeprecatedTos)var9.next();
            var1.writeGroup(24, var10);
         }

         Iterator var11 = this.getTosCheckboxHtmlList().iterator();

         while(var11.hasNext()) {
            String var12 = (String)var11.next();
            var1.writeString(37, var12);
         }

         if(this.hasIabPermissionError()) {
            int var13 = this.getIabPermissionError();
            var1.writeInt32(38, var13);
         }
      }

      public static final class DeprecatedTos extends MessageMicro {

         public static final int TOS_CHECKBOX_TEXT_FIELD_NUMBER = 28;
         public static final int TOS_HEADER_TEXT_FIELD_NUMBER = 26;
         public static final int TOS_NAME_FIELD_NUMBER = 25;
         public static final int TOS_URL_FIELD_NUMBER = 27;
         private int cachedSize = -1;
         private boolean hasTosCheckboxText;
         private boolean hasTosHeaderText;
         private boolean hasTosName;
         private boolean hasTosUrl;
         private String tosCheckboxText_ = "";
         private String tosHeaderText_ = "";
         private String tosName_ = "";
         private String tosUrl_ = "";


         public DeprecatedTos() {}

         public final Buy.BuyResponse.DeprecatedTos clear() {
            Buy.BuyResponse.DeprecatedTos var1 = this.clearTosName();
            Buy.BuyResponse.DeprecatedTos var2 = this.clearTosHeaderText();
            Buy.BuyResponse.DeprecatedTos var3 = this.clearTosUrl();
            Buy.BuyResponse.DeprecatedTos var4 = this.clearTosCheckboxText();
            this.cachedSize = -1;
            return this;
         }

         public Buy.BuyResponse.DeprecatedTos clearTosCheckboxText() {
            this.hasTosCheckboxText = (boolean)0;
            this.tosCheckboxText_ = "";
            return this;
         }

         public Buy.BuyResponse.DeprecatedTos clearTosHeaderText() {
            this.hasTosHeaderText = (boolean)0;
            this.tosHeaderText_ = "";
            return this;
         }

         public Buy.BuyResponse.DeprecatedTos clearTosName() {
            this.hasTosName = (boolean)0;
            this.tosName_ = "";
            return this;
         }

         public Buy.BuyResponse.DeprecatedTos clearTosUrl() {
            this.hasTosUrl = (boolean)0;
            this.tosUrl_ = "";
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasTosName()) {
               String var2 = this.getTosName();
               int var3 = CodedOutputStreamMicro.computeStringSize(25, var2);
               var1 = 0 + var3;
            }

            if(this.hasTosHeaderText()) {
               String var4 = this.getTosHeaderText();
               int var5 = CodedOutputStreamMicro.computeStringSize(26, var4);
               var1 += var5;
            }

            if(this.hasTosUrl()) {
               String var6 = this.getTosUrl();
               int var7 = CodedOutputStreamMicro.computeStringSize(27, var6);
               var1 += var7;
            }

            if(this.hasTosCheckboxText()) {
               String var8 = this.getTosCheckboxText();
               int var9 = CodedOutputStreamMicro.computeStringSize(28, var8);
               var1 += var9;
            }

            this.cachedSize = var1;
            return var1;
         }

         public String getTosCheckboxText() {
            return this.tosCheckboxText_;
         }

         public String getTosHeaderText() {
            return this.tosHeaderText_;
         }

         public String getTosName() {
            return this.tosName_;
         }

         public String getTosUrl() {
            return this.tosUrl_;
         }

         public boolean hasTosCheckboxText() {
            return this.hasTosCheckboxText;
         }

         public boolean hasTosHeaderText() {
            return this.hasTosHeaderText;
         }

         public boolean hasTosName() {
            return this.hasTosName;
         }

         public boolean hasTosUrl() {
            return this.hasTosUrl;
         }

         public final boolean isInitialized() {
            return true;
         }

         public Buy.BuyResponse.DeprecatedTos mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 202:
                  String var3 = var1.readString();
                  this.setTosName(var3);
                  break;
               case 210:
                  String var5 = var1.readString();
                  this.setTosHeaderText(var5);
                  break;
               case 218:
                  String var7 = var1.readString();
                  this.setTosUrl(var7);
                  break;
               case 226:
                  String var9 = var1.readString();
                  this.setTosCheckboxText(var9);
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

         public Buy.BuyResponse.DeprecatedTos parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new Buy.BuyResponse.DeprecatedTos()).mergeFrom(var1);
         }

         public Buy.BuyResponse.DeprecatedTos parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (Buy.BuyResponse.DeprecatedTos)((Buy.BuyResponse.DeprecatedTos)(new Buy.BuyResponse.DeprecatedTos()).mergeFrom(var1));
         }

         public Buy.BuyResponse.DeprecatedTos setTosCheckboxText(String var1) {
            this.hasTosCheckboxText = (boolean)1;
            this.tosCheckboxText_ = var1;
            return this;
         }

         public Buy.BuyResponse.DeprecatedTos setTosHeaderText(String var1) {
            this.hasTosHeaderText = (boolean)1;
            this.tosHeaderText_ = var1;
            return this;
         }

         public Buy.BuyResponse.DeprecatedTos setTosName(String var1) {
            this.hasTosName = (boolean)1;
            this.tosName_ = var1;
            return this;
         }

         public Buy.BuyResponse.DeprecatedTos setTosUrl(String var1) {
            this.hasTosUrl = (boolean)1;
            this.tosUrl_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasTosName()) {
               String var2 = this.getTosName();
               var1.writeString(25, var2);
            }

            if(this.hasTosHeaderText()) {
               String var3 = this.getTosHeaderText();
               var1.writeString(26, var3);
            }

            if(this.hasTosUrl()) {
               String var4 = this.getTosUrl();
               var1.writeString(27, var4);
            }

            if(this.hasTosCheckboxText()) {
               String var5 = this.getTosCheckboxText();
               var1.writeString(28, var5);
            }
         }
      }

      public static final class CheckoutInfo extends MessageMicro {

         public static final int ADD_INSTRUMENT_URL_FIELD_NUMBER = 11;
         public static final int CHECKOUTOPTION_FIELD_NUMBER = 5;
         public static final int DEPRECATED_CHECKOUT_URL_FIELD_NUMBER = 10;
         public static final int ELIGIBLE_INSTRUMENT_FAMILY_FIELD_NUMBER = 31;
         public static final int FOOTER_HTML_FIELD_NUMBER = 20;
         public static final int FOOTNOTE_HTML_FIELD_NUMBER = 36;
         public static final int ITEM_FIELD_NUMBER = 3;
         public static final int SUB_ITEM_FIELD_NUMBER = 4;
         private String addInstrumentUrl_;
         private int cachedSize;
         private List<Buy.BuyResponse.CheckoutInfo.CheckoutOption> checkoutOption_;
         private String deprecatedCheckoutUrl_;
         private List<Integer> eligibleInstrumentFamily_;
         private List<String> footerHtml_;
         private List<String> footnoteHtml_;
         private boolean hasAddInstrumentUrl;
         private boolean hasDeprecatedCheckoutUrl;
         private boolean hasItem;
         private Buy.LineItem item_ = null;
         private List<Buy.LineItem> subItem_;


         public CheckoutInfo() {
            List var1 = Collections.emptyList();
            this.subItem_ = var1;
            List var2 = Collections.emptyList();
            this.checkoutOption_ = var2;
            this.deprecatedCheckoutUrl_ = "";
            this.addInstrumentUrl_ = "";
            List var3 = Collections.emptyList();
            this.footerHtml_ = var3;
            List var4 = Collections.emptyList();
            this.footnoteHtml_ = var4;
            List var5 = Collections.emptyList();
            this.eligibleInstrumentFamily_ = var5;
            this.cachedSize = -1;
         }

         public Buy.BuyResponse.CheckoutInfo addCheckoutOption(Buy.BuyResponse.CheckoutInfo.CheckoutOption var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               if(this.checkoutOption_.isEmpty()) {
                  ArrayList var2 = new ArrayList();
                  this.checkoutOption_ = var2;
               }

               this.checkoutOption_.add(var1);
               return this;
            }
         }

         public Buy.BuyResponse.CheckoutInfo addEligibleInstrumentFamily(int var1) {
            if(this.eligibleInstrumentFamily_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.eligibleInstrumentFamily_ = var2;
            }

            List var3 = this.eligibleInstrumentFamily_;
            Integer var4 = Integer.valueOf(var1);
            var3.add(var4);
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo addFooterHtml(String var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               if(this.footerHtml_.isEmpty()) {
                  ArrayList var2 = new ArrayList();
                  this.footerHtml_ = var2;
               }

               this.footerHtml_.add(var1);
               return this;
            }
         }

         public Buy.BuyResponse.CheckoutInfo addFootnoteHtml(String var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               if(this.footnoteHtml_.isEmpty()) {
                  ArrayList var2 = new ArrayList();
                  this.footnoteHtml_ = var2;
               }

               this.footnoteHtml_.add(var1);
               return this;
            }
         }

         public Buy.BuyResponse.CheckoutInfo addSubItem(Buy.LineItem var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               if(this.subItem_.isEmpty()) {
                  ArrayList var2 = new ArrayList();
                  this.subItem_ = var2;
               }

               this.subItem_.add(var1);
               return this;
            }
         }

         public final Buy.BuyResponse.CheckoutInfo clear() {
            Buy.BuyResponse.CheckoutInfo var1 = this.clearItem();
            Buy.BuyResponse.CheckoutInfo var2 = this.clearSubItem();
            Buy.BuyResponse.CheckoutInfo var3 = this.clearCheckoutOption();
            Buy.BuyResponse.CheckoutInfo var4 = this.clearDeprecatedCheckoutUrl();
            Buy.BuyResponse.CheckoutInfo var5 = this.clearAddInstrumentUrl();
            Buy.BuyResponse.CheckoutInfo var6 = this.clearFooterHtml();
            Buy.BuyResponse.CheckoutInfo var7 = this.clearFootnoteHtml();
            Buy.BuyResponse.CheckoutInfo var8 = this.clearEligibleInstrumentFamily();
            this.cachedSize = -1;
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo clearAddInstrumentUrl() {
            this.hasAddInstrumentUrl = (boolean)0;
            this.addInstrumentUrl_ = "";
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo clearCheckoutOption() {
            List var1 = Collections.emptyList();
            this.checkoutOption_ = var1;
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo clearDeprecatedCheckoutUrl() {
            this.hasDeprecatedCheckoutUrl = (boolean)0;
            this.deprecatedCheckoutUrl_ = "";
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo clearEligibleInstrumentFamily() {
            List var1 = Collections.emptyList();
            this.eligibleInstrumentFamily_ = var1;
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo clearFooterHtml() {
            List var1 = Collections.emptyList();
            this.footerHtml_ = var1;
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo clearFootnoteHtml() {
            List var1 = Collections.emptyList();
            this.footnoteHtml_ = var1;
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo clearItem() {
            this.hasItem = (boolean)0;
            this.item_ = null;
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo clearSubItem() {
            List var1 = Collections.emptyList();
            this.subItem_ = var1;
            return this;
         }

         public String getAddInstrumentUrl() {
            return this.addInstrumentUrl_;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public Buy.BuyResponse.CheckoutInfo.CheckoutOption getCheckoutOption(int var1) {
            return (Buy.BuyResponse.CheckoutInfo.CheckoutOption)this.checkoutOption_.get(var1);
         }

         public int getCheckoutOptionCount() {
            return this.checkoutOption_.size();
         }

         public List<Buy.BuyResponse.CheckoutInfo.CheckoutOption> getCheckoutOptionList() {
            return this.checkoutOption_;
         }

         public String getDeprecatedCheckoutUrl() {
            return this.deprecatedCheckoutUrl_;
         }

         public int getEligibleInstrumentFamily(int var1) {
            return ((Integer)this.eligibleInstrumentFamily_.get(var1)).intValue();
         }

         public int getEligibleInstrumentFamilyCount() {
            return this.eligibleInstrumentFamily_.size();
         }

         public List<Integer> getEligibleInstrumentFamilyList() {
            return this.eligibleInstrumentFamily_;
         }

         public String getFooterHtml(int var1) {
            return (String)this.footerHtml_.get(var1);
         }

         public int getFooterHtmlCount() {
            return this.footerHtml_.size();
         }

         public List<String> getFooterHtmlList() {
            return this.footerHtml_;
         }

         public String getFootnoteHtml(int var1) {
            return (String)this.footnoteHtml_.get(var1);
         }

         public int getFootnoteHtmlCount() {
            return this.footnoteHtml_.size();
         }

         public List<String> getFootnoteHtmlList() {
            return this.footnoteHtml_;
         }

         public Buy.LineItem getItem() {
            return this.item_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasItem()) {
               Buy.LineItem var2 = this.getItem();
               int var3 = CodedOutputStreamMicro.computeMessageSize(3, var2);
               var1 = 0 + var3;
            }

            int var6;
            for(Iterator var4 = this.getSubItemList().iterator(); var4.hasNext(); var1 += var6) {
               Buy.LineItem var5 = (Buy.LineItem)var4.next();
               var6 = CodedOutputStreamMicro.computeMessageSize(4, var5);
            }

            int var9;
            for(Iterator var7 = this.getCheckoutOptionList().iterator(); var7.hasNext(); var1 += var9) {
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var8 = (Buy.BuyResponse.CheckoutInfo.CheckoutOption)var7.next();
               var9 = CodedOutputStreamMicro.computeGroupSize(5, var8);
            }

            if(this.hasDeprecatedCheckoutUrl()) {
               String var10 = this.getDeprecatedCheckoutUrl();
               int var11 = CodedOutputStreamMicro.computeStringSize(10, var10);
               var1 += var11;
            }

            if(this.hasAddInstrumentUrl()) {
               String var12 = this.getAddInstrumentUrl();
               int var13 = CodedOutputStreamMicro.computeStringSize(11, var12);
               var1 += var13;
            }

            int var14 = 0;

            int var16;
            for(Iterator var15 = this.getFooterHtmlList().iterator(); var15.hasNext(); var14 += var16) {
               var16 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var15.next());
            }

            int var17 = var1 + var14;
            int var18 = this.getFooterHtmlList().size() * 2;
            int var19 = var17 + var18;
            int var20 = 0;

            int var22;
            for(Iterator var21 = this.getEligibleInstrumentFamilyList().iterator(); var21.hasNext(); var20 += var22) {
               var22 = CodedOutputStreamMicro.computeInt32SizeNoTag(((Integer)var21.next()).intValue());
            }

            int var23 = var19 + var20;
            int var24 = this.getEligibleInstrumentFamilyList().size() * 2;
            int var25 = var23 + var24;
            int var26 = 0;

            int var28;
            for(Iterator var27 = this.getFootnoteHtmlList().iterator(); var27.hasNext(); var26 += var28) {
               var28 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var27.next());
            }

            int var29 = var25 + var26;
            int var30 = this.getFootnoteHtmlList().size() * 2;
            int var31 = var29 + var30;
            this.cachedSize = var31;
            return var31;
         }

         public Buy.LineItem getSubItem(int var1) {
            return (Buy.LineItem)this.subItem_.get(var1);
         }

         public int getSubItemCount() {
            return this.subItem_.size();
         }

         public List<Buy.LineItem> getSubItemList() {
            return this.subItem_;
         }

         public boolean hasAddInstrumentUrl() {
            return this.hasAddInstrumentUrl;
         }

         public boolean hasDeprecatedCheckoutUrl() {
            return this.hasDeprecatedCheckoutUrl;
         }

         public boolean hasItem() {
            return this.hasItem;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if(!this.hasItem() || this.getItem().isInitialized()) {
               Iterator var2 = this.getSubItemList().iterator();

               do {
                  if(!var2.hasNext()) {
                     var2 = this.getCheckoutOptionList().iterator();

                     while(var2.hasNext()) {
                        if(!((Buy.BuyResponse.CheckoutInfo.CheckoutOption)var2.next()).isInitialized()) {
                           return var1;
                        }
                     }

                     var1 = true;
                     break;
                  }
               } while(((Buy.LineItem)var2.next()).isInitialized());
            }

            return var1;
         }

         public Buy.BuyResponse.CheckoutInfo mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 26:
                  Buy.LineItem var3 = new Buy.LineItem();
                  var1.readMessage(var3);
                  this.setItem(var3);
                  break;
               case 34:
                  Buy.LineItem var5 = new Buy.LineItem();
                  var1.readMessage(var5);
                  this.addSubItem(var5);
                  break;
               case 43:
                  Buy.BuyResponse.CheckoutInfo.CheckoutOption var7 = new Buy.BuyResponse.CheckoutInfo.CheckoutOption();
                  var1.readGroup(var7, 5);
                  this.addCheckoutOption(var7);
                  break;
               case 82:
                  String var9 = var1.readString();
                  this.setDeprecatedCheckoutUrl(var9);
                  break;
               case 90:
                  String var11 = var1.readString();
                  this.setAddInstrumentUrl(var11);
                  break;
               case 162:
                  String var13 = var1.readString();
                  this.addFooterHtml(var13);
                  break;
               case 248:
                  int var15 = var1.readInt32();
                  this.addEligibleInstrumentFamily(var15);
                  break;
               case 290:
                  String var17 = var1.readString();
                  this.addFootnoteHtml(var17);
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

         public Buy.BuyResponse.CheckoutInfo parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new Buy.BuyResponse.CheckoutInfo()).mergeFrom(var1);
         }

         public Buy.BuyResponse.CheckoutInfo parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (Buy.BuyResponse.CheckoutInfo)((Buy.BuyResponse.CheckoutInfo)(new Buy.BuyResponse.CheckoutInfo()).mergeFrom(var1));
         }

         public Buy.BuyResponse.CheckoutInfo setAddInstrumentUrl(String var1) {
            this.hasAddInstrumentUrl = (boolean)1;
            this.addInstrumentUrl_ = var1;
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo setCheckoutOption(int var1, Buy.BuyResponse.CheckoutInfo.CheckoutOption var2) {
            if(var2 == null) {
               throw new NullPointerException();
            } else {
               this.checkoutOption_.set(var1, var2);
               return this;
            }
         }

         public Buy.BuyResponse.CheckoutInfo setDeprecatedCheckoutUrl(String var1) {
            this.hasDeprecatedCheckoutUrl = (boolean)1;
            this.deprecatedCheckoutUrl_ = var1;
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo setEligibleInstrumentFamily(int var1, int var2) {
            List var3 = this.eligibleInstrumentFamily_;
            Integer var4 = Integer.valueOf(var2);
            var3.set(var1, var4);
            return this;
         }

         public Buy.BuyResponse.CheckoutInfo setFooterHtml(int var1, String var2) {
            if(var2 == null) {
               throw new NullPointerException();
            } else {
               this.footerHtml_.set(var1, var2);
               return this;
            }
         }

         public Buy.BuyResponse.CheckoutInfo setFootnoteHtml(int var1, String var2) {
            if(var2 == null) {
               throw new NullPointerException();
            } else {
               this.footnoteHtml_.set(var1, var2);
               return this;
            }
         }

         public Buy.BuyResponse.CheckoutInfo setItem(Buy.LineItem var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasItem = (boolean)1;
               this.item_ = var1;
               return this;
            }
         }

         public Buy.BuyResponse.CheckoutInfo setSubItem(int var1, Buy.LineItem var2) {
            if(var2 == null) {
               throw new NullPointerException();
            } else {
               this.subItem_.set(var1, var2);
               return this;
            }
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasItem()) {
               Buy.LineItem var2 = this.getItem();
               var1.writeMessage(3, var2);
            }

            Iterator var3 = this.getSubItemList().iterator();

            while(var3.hasNext()) {
               Buy.LineItem var4 = (Buy.LineItem)var3.next();
               var1.writeMessage(4, var4);
            }

            Iterator var5 = this.getCheckoutOptionList().iterator();

            while(var5.hasNext()) {
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var6 = (Buy.BuyResponse.CheckoutInfo.CheckoutOption)var5.next();
               var1.writeGroup(5, var6);
            }

            if(this.hasDeprecatedCheckoutUrl()) {
               String var7 = this.getDeprecatedCheckoutUrl();
               var1.writeString(10, var7);
            }

            if(this.hasAddInstrumentUrl()) {
               String var8 = this.getAddInstrumentUrl();
               var1.writeString(11, var8);
            }

            Iterator var9 = this.getFooterHtmlList().iterator();

            while(var9.hasNext()) {
               String var10 = (String)var9.next();
               var1.writeString(20, var10);
            }

            Iterator var11 = this.getEligibleInstrumentFamilyList().iterator();

            while(var11.hasNext()) {
               int var12 = ((Integer)var11.next()).intValue();
               var1.writeInt32(31, var12);
            }

            Iterator var13 = this.getFootnoteHtmlList().iterator();

            while(var13.hasNext()) {
               String var14 = (String)var13.next();
               var1.writeString(36, var14);
            }

         }

         public static final class CheckoutOption extends MessageMicro {

            public static final int ENCODED_ADJUSTED_CART_FIELD_NUMBER = 7;
            public static final int FOOTER_HTML_FIELD_NUMBER = 19;
            public static final int FOOTNOTE_HTML_FIELD_NUMBER = 35;
            public static final int FORM_OF_PAYMENT_FIELD_NUMBER = 6;
            public static final int INSTRUMENT_FAMILY_FIELD_NUMBER = 29;
            public static final int INSTRUMENT_ID_FIELD_NUMBER = 15;
            public static final int INSTRUMENT_INAPPLICABLE_REASON_FIELD_NUMBER = 30;
            public static final int ITEM_FIELD_NUMBER = 16;
            public static final int SELECTED_INSTRUMENT_FIELD_NUMBER = 32;
            public static final int SUB_ITEM_FIELD_NUMBER = 17;
            public static final int SUMMARY_FIELD_NUMBER = 33;
            public static final int TOTAL_FIELD_NUMBER = 18;
            private int cachedSize;
            private String encodedAdjustedCart_;
            private List<String> footerHtml_;
            private List<String> footnoteHtml_;
            private String formOfPayment_ = "";
            private boolean hasEncodedAdjustedCart;
            private boolean hasFormOfPayment;
            private boolean hasInstrumentFamily;
            private boolean hasInstrumentId;
            private boolean hasSelectedInstrument;
            private boolean hasSummary;
            private boolean hasTotal;
            private int instrumentFamily_ = 0;
            private String instrumentId_ = "";
            private List<Integer> instrumentInapplicableReason_;
            private List<Buy.LineItem> item_;
            private boolean selectedInstrument_;
            private List<Buy.LineItem> subItem_;
            private Buy.LineItem summary_;
            private Buy.LineItem total_;


            public CheckoutOption() {
               List var1 = Collections.emptyList();
               this.instrumentInapplicableReason_ = var1;
               this.selectedInstrument_ = (boolean)0;
               this.summary_ = null;
               List var2 = Collections.emptyList();
               this.item_ = var2;
               List var3 = Collections.emptyList();
               this.subItem_ = var3;
               this.total_ = null;
               List var4 = Collections.emptyList();
               this.footerHtml_ = var4;
               List var5 = Collections.emptyList();
               this.footnoteHtml_ = var5;
               this.encodedAdjustedCart_ = "";
               this.cachedSize = -1;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption addFooterHtml(String var1) {
               if(var1 == null) {
                  throw new NullPointerException();
               } else {
                  if(this.footerHtml_.isEmpty()) {
                     ArrayList var2 = new ArrayList();
                     this.footerHtml_ = var2;
                  }

                  this.footerHtml_.add(var1);
                  return this;
               }
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption addFootnoteHtml(String var1) {
               if(var1 == null) {
                  throw new NullPointerException();
               } else {
                  if(this.footnoteHtml_.isEmpty()) {
                     ArrayList var2 = new ArrayList();
                     this.footnoteHtml_ = var2;
                  }

                  this.footnoteHtml_.add(var1);
                  return this;
               }
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption addInstrumentInapplicableReason(int var1) {
               if(this.instrumentInapplicableReason_.isEmpty()) {
                  ArrayList var2 = new ArrayList();
                  this.instrumentInapplicableReason_ = var2;
               }

               List var3 = this.instrumentInapplicableReason_;
               Integer var4 = Integer.valueOf(var1);
               var3.add(var4);
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption addItem(Buy.LineItem var1) {
               if(var1 == null) {
                  throw new NullPointerException();
               } else {
                  if(this.item_.isEmpty()) {
                     ArrayList var2 = new ArrayList();
                     this.item_ = var2;
                  }

                  this.item_.add(var1);
                  return this;
               }
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption addSubItem(Buy.LineItem var1) {
               if(var1 == null) {
                  throw new NullPointerException();
               } else {
                  if(this.subItem_.isEmpty()) {
                     ArrayList var2 = new ArrayList();
                     this.subItem_ = var2;
                  }

                  this.subItem_.add(var1);
                  return this;
               }
            }

            public final Buy.BuyResponse.CheckoutInfo.CheckoutOption clear() {
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var1 = this.clearFormOfPayment();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var2 = this.clearInstrumentId();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var3 = this.clearInstrumentFamily();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var4 = this.clearInstrumentInapplicableReason();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var5 = this.clearSelectedInstrument();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var6 = this.clearSummary();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var7 = this.clearItem();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var8 = this.clearSubItem();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var9 = this.clearTotal();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var10 = this.clearFooterHtml();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var11 = this.clearFootnoteHtml();
               Buy.BuyResponse.CheckoutInfo.CheckoutOption var12 = this.clearEncodedAdjustedCart();
               this.cachedSize = -1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearEncodedAdjustedCart() {
               this.hasEncodedAdjustedCart = (boolean)0;
               this.encodedAdjustedCart_ = "";
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearFooterHtml() {
               List var1 = Collections.emptyList();
               this.footerHtml_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearFootnoteHtml() {
               List var1 = Collections.emptyList();
               this.footnoteHtml_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearFormOfPayment() {
               this.hasFormOfPayment = (boolean)0;
               this.formOfPayment_ = "";
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearInstrumentFamily() {
               this.hasInstrumentFamily = (boolean)0;
               this.instrumentFamily_ = 0;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearInstrumentId() {
               this.hasInstrumentId = (boolean)0;
               this.instrumentId_ = "";
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearInstrumentInapplicableReason() {
               List var1 = Collections.emptyList();
               this.instrumentInapplicableReason_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearItem() {
               List var1 = Collections.emptyList();
               this.item_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearSelectedInstrument() {
               this.hasSelectedInstrument = (boolean)0;
               this.selectedInstrument_ = (boolean)0;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearSubItem() {
               List var1 = Collections.emptyList();
               this.subItem_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearSummary() {
               this.hasSummary = (boolean)0;
               this.summary_ = null;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption clearTotal() {
               this.hasTotal = (boolean)0;
               this.total_ = null;
               return this;
            }

            public int getCachedSize() {
               if(this.cachedSize < 0) {
                  int var1 = this.getSerializedSize();
               }

               return this.cachedSize;
            }

            public String getEncodedAdjustedCart() {
               return this.encodedAdjustedCart_;
            }

            public String getFooterHtml(int var1) {
               return (String)this.footerHtml_.get(var1);
            }

            public int getFooterHtmlCount() {
               return this.footerHtml_.size();
            }

            public List<String> getFooterHtmlList() {
               return this.footerHtml_;
            }

            public String getFootnoteHtml(int var1) {
               return (String)this.footnoteHtml_.get(var1);
            }

            public int getFootnoteHtmlCount() {
               return this.footnoteHtml_.size();
            }

            public List<String> getFootnoteHtmlList() {
               return this.footnoteHtml_;
            }

            public String getFormOfPayment() {
               return this.formOfPayment_;
            }

            public int getInstrumentFamily() {
               return this.instrumentFamily_;
            }

            public String getInstrumentId() {
               return this.instrumentId_;
            }

            public int getInstrumentInapplicableReason(int var1) {
               return ((Integer)this.instrumentInapplicableReason_.get(var1)).intValue();
            }

            public int getInstrumentInapplicableReasonCount() {
               return this.instrumentInapplicableReason_.size();
            }

            public List<Integer> getInstrumentInapplicableReasonList() {
               return this.instrumentInapplicableReason_;
            }

            public Buy.LineItem getItem(int var1) {
               return (Buy.LineItem)this.item_.get(var1);
            }

            public int getItemCount() {
               return this.item_.size();
            }

            public List<Buy.LineItem> getItemList() {
               return this.item_;
            }

            public boolean getSelectedInstrument() {
               return this.selectedInstrument_;
            }

            public int getSerializedSize() {
               int var1 = 0;
               if(this.hasFormOfPayment()) {
                  String var2 = this.getFormOfPayment();
                  int var3 = CodedOutputStreamMicro.computeStringSize(6, var2);
                  var1 = 0 + var3;
               }

               if(this.hasEncodedAdjustedCart()) {
                  String var4 = this.getEncodedAdjustedCart();
                  int var5 = CodedOutputStreamMicro.computeStringSize(7, var4);
                  var1 += var5;
               }

               if(this.hasInstrumentId()) {
                  String var6 = this.getInstrumentId();
                  int var7 = CodedOutputStreamMicro.computeStringSize(15, var6);
                  var1 += var7;
               }

               int var10;
               for(Iterator var8 = this.getItemList().iterator(); var8.hasNext(); var1 += var10) {
                  Buy.LineItem var9 = (Buy.LineItem)var8.next();
                  var10 = CodedOutputStreamMicro.computeMessageSize(16, var9);
               }

               int var13;
               for(Iterator var11 = this.getSubItemList().iterator(); var11.hasNext(); var1 += var13) {
                  Buy.LineItem var12 = (Buy.LineItem)var11.next();
                  var13 = CodedOutputStreamMicro.computeMessageSize(17, var12);
               }

               if(this.hasTotal()) {
                  Buy.LineItem var14 = this.getTotal();
                  int var15 = CodedOutputStreamMicro.computeMessageSize(18, var14);
                  var1 += var15;
               }

               int var16 = 0;

               int var18;
               for(Iterator var17 = this.getFooterHtmlList().iterator(); var17.hasNext(); var16 += var18) {
                  var18 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var17.next());
               }

               int var19 = var1 + var16;
               int var20 = this.getFooterHtmlList().size() * 2;
               int var21 = var19 + var20;
               if(this.hasInstrumentFamily()) {
                  int var22 = this.getInstrumentFamily();
                  int var23 = CodedOutputStreamMicro.computeInt32Size(29, var22);
                  var21 += var23;
               }

               int var24 = 0;

               int var26;
               for(Iterator var25 = this.getInstrumentInapplicableReasonList().iterator(); var25.hasNext(); var24 += var26) {
                  var26 = CodedOutputStreamMicro.computeInt32SizeNoTag(((Integer)var25.next()).intValue());
               }

               int var27 = var21 + var24;
               int var28 = this.getInstrumentInapplicableReasonList().size() * 2;
               int var29 = var27 + var28;
               if(this.hasSelectedInstrument()) {
                  boolean var30 = this.getSelectedInstrument();
                  int var31 = CodedOutputStreamMicro.computeBoolSize(32, var30);
                  var29 += var31;
               }

               if(this.hasSummary()) {
                  Buy.LineItem var32 = this.getSummary();
                  int var33 = CodedOutputStreamMicro.computeMessageSize(33, var32);
                  var29 += var33;
               }

               int var34 = 0;

               int var36;
               for(Iterator var35 = this.getFootnoteHtmlList().iterator(); var35.hasNext(); var34 += var36) {
                  var36 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var35.next());
               }

               int var37 = var29 + var34;
               int var38 = this.getFootnoteHtmlList().size() * 2;
               int var39 = var37 + var38;
               this.cachedSize = var39;
               return var39;
            }

            public Buy.LineItem getSubItem(int var1) {
               return (Buy.LineItem)this.subItem_.get(var1);
            }

            public int getSubItemCount() {
               return this.subItem_.size();
            }

            public List<Buy.LineItem> getSubItemList() {
               return this.subItem_;
            }

            public Buy.LineItem getSummary() {
               return this.summary_;
            }

            public Buy.LineItem getTotal() {
               return this.total_;
            }

            public boolean hasEncodedAdjustedCart() {
               return this.hasEncodedAdjustedCart;
            }

            public boolean hasFormOfPayment() {
               return this.hasFormOfPayment;
            }

            public boolean hasInstrumentFamily() {
               return this.hasInstrumentFamily;
            }

            public boolean hasInstrumentId() {
               return this.hasInstrumentId;
            }

            public boolean hasSelectedInstrument() {
               return this.hasSelectedInstrument;
            }

            public boolean hasSummary() {
               return this.hasSummary;
            }

            public boolean hasTotal() {
               return this.hasTotal;
            }

            public final boolean isInitialized() {
               boolean var1 = false;
               if(!this.hasSummary() || this.getSummary().isInitialized()) {
                  Iterator var2 = this.getItemList().iterator();

                  do {
                     if(!var2.hasNext()) {
                        var2 = this.getSubItemList().iterator();

                        while(var2.hasNext()) {
                           if(!((Buy.LineItem)var2.next()).isInitialized()) {
                              return var1;
                           }
                        }

                        if(!this.hasTotal() || this.getTotal().isInitialized()) {
                           var1 = true;
                        }
                        break;
                     }
                  } while(((Buy.LineItem)var2.next()).isInitialized());
               }

               return var1;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption mergeFrom(CodedInputStreamMicro var1) throws IOException {
               while(true) {
                  int var2 = var1.readTag();
                  switch(var2) {
                  case 50:
                     String var3 = var1.readString();
                     this.setFormOfPayment(var3);
                     break;
                  case 58:
                     String var5 = var1.readString();
                     this.setEncodedAdjustedCart(var5);
                     break;
                  case 122:
                     String var7 = var1.readString();
                     this.setInstrumentId(var7);
                     break;
                  case 130:
                     Buy.LineItem var9 = new Buy.LineItem();
                     var1.readMessage(var9);
                     this.addItem(var9);
                     break;
                  case 138:
                     Buy.LineItem var11 = new Buy.LineItem();
                     var1.readMessage(var11);
                     this.addSubItem(var11);
                     break;
                  case 146:
                     Buy.LineItem var13 = new Buy.LineItem();
                     var1.readMessage(var13);
                     this.setTotal(var13);
                     break;
                  case 154:
                     String var15 = var1.readString();
                     this.addFooterHtml(var15);
                     break;
                  case 232:
                     int var17 = var1.readInt32();
                     this.setInstrumentFamily(var17);
                     break;
                  case 240:
                     int var19 = var1.readInt32();
                     this.addInstrumentInapplicableReason(var19);
                     break;
                  case 256:
                     boolean var21 = var1.readBool();
                     this.setSelectedInstrument(var21);
                     break;
                  case 266:
                     Buy.LineItem var23 = new Buy.LineItem();
                     var1.readMessage(var23);
                     this.setSummary(var23);
                     break;
                  case 282:
                     String var25 = var1.readString();
                     this.addFootnoteHtml(var25);
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

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption parseFrom(CodedInputStreamMicro var1) throws IOException {
               return (new Buy.BuyResponse.CheckoutInfo.CheckoutOption()).mergeFrom(var1);
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
               return (Buy.BuyResponse.CheckoutInfo.CheckoutOption)((Buy.BuyResponse.CheckoutInfo.CheckoutOption)(new Buy.BuyResponse.CheckoutInfo.CheckoutOption()).mergeFrom(var1));
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setEncodedAdjustedCart(String var1) {
               this.hasEncodedAdjustedCart = (boolean)1;
               this.encodedAdjustedCart_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setFooterHtml(int var1, String var2) {
               if(var2 == null) {
                  throw new NullPointerException();
               } else {
                  this.footerHtml_.set(var1, var2);
                  return this;
               }
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setFootnoteHtml(int var1, String var2) {
               if(var2 == null) {
                  throw new NullPointerException();
               } else {
                  this.footnoteHtml_.set(var1, var2);
                  return this;
               }
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setFormOfPayment(String var1) {
               this.hasFormOfPayment = (boolean)1;
               this.formOfPayment_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setInstrumentFamily(int var1) {
               this.hasInstrumentFamily = (boolean)1;
               this.instrumentFamily_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setInstrumentId(String var1) {
               this.hasInstrumentId = (boolean)1;
               this.instrumentId_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setInstrumentInapplicableReason(int var1, int var2) {
               List var3 = this.instrumentInapplicableReason_;
               Integer var4 = Integer.valueOf(var2);
               var3.set(var1, var4);
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setItem(int var1, Buy.LineItem var2) {
               if(var2 == null) {
                  throw new NullPointerException();
               } else {
                  this.item_.set(var1, var2);
                  return this;
               }
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setSelectedInstrument(boolean var1) {
               this.hasSelectedInstrument = (boolean)1;
               this.selectedInstrument_ = var1;
               return this;
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setSubItem(int var1, Buy.LineItem var2) {
               if(var2 == null) {
                  throw new NullPointerException();
               } else {
                  this.subItem_.set(var1, var2);
                  return this;
               }
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setSummary(Buy.LineItem var1) {
               if(var1 == null) {
                  throw new NullPointerException();
               } else {
                  this.hasSummary = (boolean)1;
                  this.summary_ = var1;
                  return this;
               }
            }

            public Buy.BuyResponse.CheckoutInfo.CheckoutOption setTotal(Buy.LineItem var1) {
               if(var1 == null) {
                  throw new NullPointerException();
               } else {
                  this.hasTotal = (boolean)1;
                  this.total_ = var1;
                  return this;
               }
            }

            public void writeTo(CodedOutputStreamMicro var1) throws IOException {
               if(this.hasFormOfPayment()) {
                  String var2 = this.getFormOfPayment();
                  var1.writeString(6, var2);
               }

               if(this.hasEncodedAdjustedCart()) {
                  String var3 = this.getEncodedAdjustedCart();
                  var1.writeString(7, var3);
               }

               if(this.hasInstrumentId()) {
                  String var4 = this.getInstrumentId();
                  var1.writeString(15, var4);
               }

               Iterator var5 = this.getItemList().iterator();

               while(var5.hasNext()) {
                  Buy.LineItem var6 = (Buy.LineItem)var5.next();
                  var1.writeMessage(16, var6);
               }

               Iterator var7 = this.getSubItemList().iterator();

               while(var7.hasNext()) {
                  Buy.LineItem var8 = (Buy.LineItem)var7.next();
                  var1.writeMessage(17, var8);
               }

               if(this.hasTotal()) {
                  Buy.LineItem var9 = this.getTotal();
                  var1.writeMessage(18, var9);
               }

               Iterator var10 = this.getFooterHtmlList().iterator();

               while(var10.hasNext()) {
                  String var11 = (String)var10.next();
                  var1.writeString(19, var11);
               }

               if(this.hasInstrumentFamily()) {
                  int var12 = this.getInstrumentFamily();
                  var1.writeInt32(29, var12);
               }

               Iterator var13 = this.getInstrumentInapplicableReasonList().iterator();

               while(var13.hasNext()) {
                  int var14 = ((Integer)var13.next()).intValue();
                  var1.writeInt32(30, var14);
               }

               if(this.hasSelectedInstrument()) {
                  boolean var15 = this.getSelectedInstrument();
                  var1.writeBool(32, var15);
               }

               if(this.hasSummary()) {
                  Buy.LineItem var16 = this.getSummary();
                  var1.writeMessage(33, var16);
               }

               Iterator var17 = this.getFootnoteHtmlList().iterator();

               while(var17.hasNext()) {
                  String var18 = (String)var17.next();
                  var1.writeString(35, var18);
               }

            }
         }
      }
   }

   public static final class PurchaseStatusResponse extends MessageMicro {

      public static final int BRIEF_MESSAGE_FIELD_NUMBER = 4;
      public static final int CHARGEABLE = 1;
      public static final int INFO_URL_FIELD_NUMBER = 5;
      public static final int PAYMENT_DECLINED = 4;
      public static final int PENDING = 2;
      public static final int REJECTED = 3;
      public static final int STATUS_FIELD_NUMBER = 1;
      public static final int STATUS_MSG_FIELD_NUMBER = 2;
      public static final int STATUS_TITLE_FIELD_NUMBER = 3;
      private String briefMessage_ = "";
      private int cachedSize = -1;
      private boolean hasBriefMessage;
      private boolean hasInfoUrl;
      private boolean hasStatus;
      private boolean hasStatusMsg;
      private boolean hasStatusTitle;
      private String infoUrl_ = "";
      private String statusMsg_ = "";
      private String statusTitle_ = "";
      private int status_ = 1;


      public PurchaseStatusResponse() {}

      public static Buy.PurchaseStatusResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Buy.PurchaseStatusResponse()).mergeFrom(var0);
      }

      public static Buy.PurchaseStatusResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Buy.PurchaseStatusResponse)((Buy.PurchaseStatusResponse)(new Buy.PurchaseStatusResponse()).mergeFrom(var0));
      }

      public final Buy.PurchaseStatusResponse clear() {
         Buy.PurchaseStatusResponse var1 = this.clearStatus();
         Buy.PurchaseStatusResponse var2 = this.clearStatusMsg();
         Buy.PurchaseStatusResponse var3 = this.clearStatusTitle();
         Buy.PurchaseStatusResponse var4 = this.clearBriefMessage();
         Buy.PurchaseStatusResponse var5 = this.clearInfoUrl();
         this.cachedSize = -1;
         return this;
      }

      public Buy.PurchaseStatusResponse clearBriefMessage() {
         this.hasBriefMessage = (boolean)0;
         this.briefMessage_ = "";
         return this;
      }

      public Buy.PurchaseStatusResponse clearInfoUrl() {
         this.hasInfoUrl = (boolean)0;
         this.infoUrl_ = "";
         return this;
      }

      public Buy.PurchaseStatusResponse clearStatus() {
         this.hasStatus = (boolean)0;
         this.status_ = 1;
         return this;
      }

      public Buy.PurchaseStatusResponse clearStatusMsg() {
         this.hasStatusMsg = (boolean)0;
         this.statusMsg_ = "";
         return this;
      }

      public Buy.PurchaseStatusResponse clearStatusTitle() {
         this.hasStatusTitle = (boolean)0;
         this.statusTitle_ = "";
         return this;
      }

      public String getBriefMessage() {
         return this.briefMessage_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getInfoUrl() {
         return this.infoUrl_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasStatus()) {
            int var2 = this.getStatus();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasStatusMsg()) {
            String var4 = this.getStatusMsg();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasStatusTitle()) {
            String var6 = this.getStatusTitle();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasBriefMessage()) {
            String var8 = this.getBriefMessage();
            int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
            var1 += var9;
         }

         if(this.hasInfoUrl()) {
            String var10 = this.getInfoUrl();
            int var11 = CodedOutputStreamMicro.computeStringSize(5, var10);
            var1 += var11;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getStatus() {
         return this.status_;
      }

      public String getStatusMsg() {
         return this.statusMsg_;
      }

      public String getStatusTitle() {
         return this.statusTitle_;
      }

      public boolean hasBriefMessage() {
         return this.hasBriefMessage;
      }

      public boolean hasInfoUrl() {
         return this.hasInfoUrl;
      }

      public boolean hasStatus() {
         return this.hasStatus;
      }

      public boolean hasStatusMsg() {
         return this.hasStatusMsg;
      }

      public boolean hasStatusTitle() {
         return this.hasStatusTitle;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasStatus) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Buy.PurchaseStatusResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setStatus(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setStatusMsg(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setStatusTitle(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setBriefMessage(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setInfoUrl(var11);
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

      public Buy.PurchaseStatusResponse setBriefMessage(String var1) {
         this.hasBriefMessage = (boolean)1;
         this.briefMessage_ = var1;
         return this;
      }

      public Buy.PurchaseStatusResponse setInfoUrl(String var1) {
         this.hasInfoUrl = (boolean)1;
         this.infoUrl_ = var1;
         return this;
      }

      public Buy.PurchaseStatusResponse setStatus(int var1) {
         this.hasStatus = (boolean)1;
         this.status_ = var1;
         return this;
      }

      public Buy.PurchaseStatusResponse setStatusMsg(String var1) {
         this.hasStatusMsg = (boolean)1;
         this.statusMsg_ = var1;
         return this;
      }

      public Buy.PurchaseStatusResponse setStatusTitle(String var1) {
         this.hasStatusTitle = (boolean)1;
         this.statusTitle_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasStatus()) {
            int var2 = this.getStatus();
            var1.writeInt32(1, var2);
         }

         if(this.hasStatusMsg()) {
            String var3 = this.getStatusMsg();
            var1.writeString(2, var3);
         }

         if(this.hasStatusTitle()) {
            String var4 = this.getStatusTitle();
            var1.writeString(3, var4);
         }

         if(this.hasBriefMessage()) {
            String var5 = this.getBriefMessage();
            var1.writeString(4, var5);
         }

         if(this.hasInfoUrl()) {
            String var6 = this.getInfoUrl();
            var1.writeString(5, var6);
         }
      }
   }
}
