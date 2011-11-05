package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Address;
import com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class BuyInstruments {

   private BuyInstruments() {}

   public static final class CarrierBillingInstrument extends MessageMicro {

      public static final int ACCOUNT_TYPE_FIELD_NUMBER = 2;
      public static final int CREDENTIALS_FIELD_NUMBER = 7;
      public static final int CURRENCY_CODE_FIELD_NUMBER = 3;
      public static final int ENCRYPTED_SUBSCRIBER_INFO_FIELD_NUMBER = 6;
      public static final int INSTRUMENT_KEY_FIELD_NUMBER = 1;
      public static final int SUBSCRIBER_IDENTIFIER_FIELD_NUMBER = 5;
      public static final int TRANSACTION_LIMIT_FIELD_NUMBER = 4;
      private String accountType_ = "";
      private int cachedSize = -1;
      private BuyInstruments.CarrierBillingCredentials credentials_ = null;
      private String currencyCode_ = "";
      private EncryptedSubscriberInfo encryptedSubscriberInfo_ = null;
      private boolean hasAccountType;
      private boolean hasCredentials;
      private boolean hasCurrencyCode;
      private boolean hasEncryptedSubscriberInfo;
      private boolean hasInstrumentKey;
      private boolean hasSubscriberIdentifier;
      private boolean hasTransactionLimit;
      private String instrumentKey_ = "";
      private String subscriberIdentifier_ = "";
      private long transactionLimit_ = 0L;


      public CarrierBillingInstrument() {}

      public static BuyInstruments.CarrierBillingInstrument parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BuyInstruments.CarrierBillingInstrument()).mergeFrom(var0);
      }

      public static BuyInstruments.CarrierBillingInstrument parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BuyInstruments.CarrierBillingInstrument)((BuyInstruments.CarrierBillingInstrument)(new BuyInstruments.CarrierBillingInstrument()).mergeFrom(var0));
      }

      public final BuyInstruments.CarrierBillingInstrument clear() {
         BuyInstruments.CarrierBillingInstrument var1 = this.clearInstrumentKey();
         BuyInstruments.CarrierBillingInstrument var2 = this.clearAccountType();
         BuyInstruments.CarrierBillingInstrument var3 = this.clearCurrencyCode();
         BuyInstruments.CarrierBillingInstrument var4 = this.clearTransactionLimit();
         BuyInstruments.CarrierBillingInstrument var5 = this.clearSubscriberIdentifier();
         BuyInstruments.CarrierBillingInstrument var6 = this.clearEncryptedSubscriberInfo();
         BuyInstruments.CarrierBillingInstrument var7 = this.clearCredentials();
         this.cachedSize = -1;
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument clearAccountType() {
         this.hasAccountType = (boolean)0;
         this.accountType_ = "";
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument clearCredentials() {
         this.hasCredentials = (boolean)0;
         this.credentials_ = null;
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument clearCurrencyCode() {
         this.hasCurrencyCode = (boolean)0;
         this.currencyCode_ = "";
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument clearEncryptedSubscriberInfo() {
         this.hasEncryptedSubscriberInfo = (boolean)0;
         this.encryptedSubscriberInfo_ = null;
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument clearInstrumentKey() {
         this.hasInstrumentKey = (boolean)0;
         this.instrumentKey_ = "";
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument clearSubscriberIdentifier() {
         this.hasSubscriberIdentifier = (boolean)0;
         this.subscriberIdentifier_ = "";
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument clearTransactionLimit() {
         this.hasTransactionLimit = (boolean)0;
         this.transactionLimit_ = 0L;
         return this;
      }

      public String getAccountType() {
         return this.accountType_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public BuyInstruments.CarrierBillingCredentials getCredentials() {
         return this.credentials_;
      }

      public String getCurrencyCode() {
         return this.currencyCode_;
      }

      public EncryptedSubscriberInfo getEncryptedSubscriberInfo() {
         return this.encryptedSubscriberInfo_;
      }

      public String getInstrumentKey() {
         return this.instrumentKey_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasInstrumentKey()) {
            String var2 = this.getInstrumentKey();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasAccountType()) {
            String var4 = this.getAccountType();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasCurrencyCode()) {
            String var6 = this.getCurrencyCode();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasTransactionLimit()) {
            long var8 = this.getTransactionLimit();
            int var10 = CodedOutputStreamMicro.computeInt64Size(4, var8);
            var1 += var10;
         }

         if(this.hasSubscriberIdentifier()) {
            String var11 = this.getSubscriberIdentifier();
            int var12 = CodedOutputStreamMicro.computeStringSize(5, var11);
            var1 += var12;
         }

         if(this.hasEncryptedSubscriberInfo()) {
            EncryptedSubscriberInfo var13 = this.getEncryptedSubscriberInfo();
            int var14 = CodedOutputStreamMicro.computeMessageSize(6, var13);
            var1 += var14;
         }

         if(this.hasCredentials()) {
            BuyInstruments.CarrierBillingCredentials var15 = this.getCredentials();
            int var16 = CodedOutputStreamMicro.computeMessageSize(7, var15);
            var1 += var16;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getSubscriberIdentifier() {
         return this.subscriberIdentifier_;
      }

      public long getTransactionLimit() {
         return this.transactionLimit_;
      }

      public boolean hasAccountType() {
         return this.hasAccountType;
      }

      public boolean hasCredentials() {
         return this.hasCredentials;
      }

      public boolean hasCurrencyCode() {
         return this.hasCurrencyCode;
      }

      public boolean hasEncryptedSubscriberInfo() {
         return this.hasEncryptedSubscriberInfo;
      }

      public boolean hasInstrumentKey() {
         return this.hasInstrumentKey;
      }

      public boolean hasSubscriberIdentifier() {
         return this.hasSubscriberIdentifier;
      }

      public boolean hasTransactionLimit() {
         return this.hasTransactionLimit;
      }

      public final boolean isInitialized() {
         return true;
      }

      public BuyInstruments.CarrierBillingInstrument mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setInstrumentKey(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setAccountType(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setCurrencyCode(var7);
               break;
            case 32:
               long var9 = var1.readInt64();
               this.setTransactionLimit(var9);
               break;
            case 42:
               String var12 = var1.readString();
               this.setSubscriberIdentifier(var12);
               break;
            case 50:
               EncryptedSubscriberInfo var14 = new EncryptedSubscriberInfo();
               var1.readMessage(var14);
               this.setEncryptedSubscriberInfo(var14);
               break;
            case 58:
               BuyInstruments.CarrierBillingCredentials var16 = new BuyInstruments.CarrierBillingCredentials();
               var1.readMessage(var16);
               this.setCredentials(var16);
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

      public BuyInstruments.CarrierBillingInstrument setAccountType(String var1) {
         this.hasAccountType = (boolean)1;
         this.accountType_ = var1;
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument setCredentials(BuyInstruments.CarrierBillingCredentials var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasCredentials = (boolean)1;
            this.credentials_ = var1;
            return this;
         }
      }

      public BuyInstruments.CarrierBillingInstrument setCurrencyCode(String var1) {
         this.hasCurrencyCode = (boolean)1;
         this.currencyCode_ = var1;
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument setEncryptedSubscriberInfo(EncryptedSubscriberInfo var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasEncryptedSubscriberInfo = (boolean)1;
            this.encryptedSubscriberInfo_ = var1;
            return this;
         }
      }

      public BuyInstruments.CarrierBillingInstrument setInstrumentKey(String var1) {
         this.hasInstrumentKey = (boolean)1;
         this.instrumentKey_ = var1;
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument setSubscriberIdentifier(String var1) {
         this.hasSubscriberIdentifier = (boolean)1;
         this.subscriberIdentifier_ = var1;
         return this;
      }

      public BuyInstruments.CarrierBillingInstrument setTransactionLimit(long var1) {
         this.hasTransactionLimit = (boolean)1;
         this.transactionLimit_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasInstrumentKey()) {
            String var2 = this.getInstrumentKey();
            var1.writeString(1, var2);
         }

         if(this.hasAccountType()) {
            String var3 = this.getAccountType();
            var1.writeString(2, var3);
         }

         if(this.hasCurrencyCode()) {
            String var4 = this.getCurrencyCode();
            var1.writeString(3, var4);
         }

         if(this.hasTransactionLimit()) {
            long var5 = this.getTransactionLimit();
            var1.writeInt64(4, var5);
         }

         if(this.hasSubscriberIdentifier()) {
            String var7 = this.getSubscriberIdentifier();
            var1.writeString(5, var7);
         }

         if(this.hasEncryptedSubscriberInfo()) {
            EncryptedSubscriberInfo var8 = this.getEncryptedSubscriberInfo();
            var1.writeMessage(6, var8);
         }

         if(this.hasCredentials()) {
            BuyInstruments.CarrierBillingCredentials var9 = this.getCredentials();
            var1.writeMessage(7, var9);
         }
      }
   }

   public static final class UpdateInstrumentResponse extends MessageMicro {

      public static final int CHECKOUT_TOKEN_REQUIRED_FIELD_NUMBER = 5;
      public static final int ERROR = 1;
      public static final int ERROR_INPUT_FIELD_FIELD_NUMBER = 4;
      public static final int INPUT_VALIDATION_ERROR = 2;
      public static final int INSTRUMENT_ID_FIELD_NUMBER = 2;
      public static final int RESULT_FIELD_NUMBER = 1;
      public static final int SUCCESS = 0;
      public static final int USER_MESSAGE_HTML_FIELD_NUMBER = 3;
      private int cachedSize;
      private boolean checkoutTokenRequired_;
      private List<BuyInstruments.InputValidationError> errorInputField_;
      private boolean hasCheckoutTokenRequired;
      private boolean hasInstrumentId;
      private boolean hasResult;
      private boolean hasUserMessageHtml;
      private String instrumentId_ = "";
      private int result_ = 0;
      private String userMessageHtml_ = "";


      public UpdateInstrumentResponse() {
         List var1 = Collections.emptyList();
         this.errorInputField_ = var1;
         this.checkoutTokenRequired_ = (boolean)0;
         this.cachedSize = -1;
      }

      public static BuyInstruments.UpdateInstrumentResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BuyInstruments.UpdateInstrumentResponse()).mergeFrom(var0);
      }

      public static BuyInstruments.UpdateInstrumentResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BuyInstruments.UpdateInstrumentResponse)((BuyInstruments.UpdateInstrumentResponse)(new BuyInstruments.UpdateInstrumentResponse()).mergeFrom(var0));
      }

      public BuyInstruments.UpdateInstrumentResponse addErrorInputField(BuyInstruments.InputValidationError var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.errorInputField_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.errorInputField_ = var2;
            }

            this.errorInputField_.add(var1);
            return this;
         }
      }

      public final BuyInstruments.UpdateInstrumentResponse clear() {
         BuyInstruments.UpdateInstrumentResponse var1 = this.clearResult();
         BuyInstruments.UpdateInstrumentResponse var2 = this.clearInstrumentId();
         BuyInstruments.UpdateInstrumentResponse var3 = this.clearUserMessageHtml();
         BuyInstruments.UpdateInstrumentResponse var4 = this.clearErrorInputField();
         BuyInstruments.UpdateInstrumentResponse var5 = this.clearCheckoutTokenRequired();
         this.cachedSize = -1;
         return this;
      }

      public BuyInstruments.UpdateInstrumentResponse clearCheckoutTokenRequired() {
         this.hasCheckoutTokenRequired = (boolean)0;
         this.checkoutTokenRequired_ = (boolean)0;
         return this;
      }

      public BuyInstruments.UpdateInstrumentResponse clearErrorInputField() {
         List var1 = Collections.emptyList();
         this.errorInputField_ = var1;
         return this;
      }

      public BuyInstruments.UpdateInstrumentResponse clearInstrumentId() {
         this.hasInstrumentId = (boolean)0;
         this.instrumentId_ = "";
         return this;
      }

      public BuyInstruments.UpdateInstrumentResponse clearResult() {
         this.hasResult = (boolean)0;
         this.result_ = 0;
         return this;
      }

      public BuyInstruments.UpdateInstrumentResponse clearUserMessageHtml() {
         this.hasUserMessageHtml = (boolean)0;
         this.userMessageHtml_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public boolean getCheckoutTokenRequired() {
         return this.checkoutTokenRequired_;
      }

      public BuyInstruments.InputValidationError getErrorInputField(int var1) {
         return (BuyInstruments.InputValidationError)this.errorInputField_.get(var1);
      }

      public int getErrorInputFieldCount() {
         return this.errorInputField_.size();
      }

      public List<BuyInstruments.InputValidationError> getErrorInputFieldList() {
         return this.errorInputField_;
      }

      public String getInstrumentId() {
         return this.instrumentId_;
      }

      public int getResult() {
         return this.result_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasResult()) {
            int var2 = this.getResult();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasInstrumentId()) {
            String var4 = this.getInstrumentId();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasUserMessageHtml()) {
            String var6 = this.getUserMessageHtml();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         int var10;
         for(Iterator var8 = this.getErrorInputFieldList().iterator(); var8.hasNext(); var1 += var10) {
            BuyInstruments.InputValidationError var9 = (BuyInstruments.InputValidationError)var8.next();
            var10 = CodedOutputStreamMicro.computeMessageSize(4, var9);
         }

         if(this.hasCheckoutTokenRequired()) {
            boolean var11 = this.getCheckoutTokenRequired();
            int var12 = CodedOutputStreamMicro.computeBoolSize(5, var11);
            var1 += var12;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getUserMessageHtml() {
         return this.userMessageHtml_;
      }

      public boolean hasCheckoutTokenRequired() {
         return this.hasCheckoutTokenRequired;
      }

      public boolean hasInstrumentId() {
         return this.hasInstrumentId;
      }

      public boolean hasResult() {
         return this.hasResult;
      }

      public boolean hasUserMessageHtml() {
         return this.hasUserMessageHtml;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasResult) {
            Iterator var2 = this.getErrorInputFieldList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((BuyInstruments.InputValidationError)var2.next()).isInitialized());
         }

         return var1;
      }

      public BuyInstruments.UpdateInstrumentResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setResult(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setInstrumentId(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setUserMessageHtml(var7);
               break;
            case 34:
               BuyInstruments.InputValidationError var9 = new BuyInstruments.InputValidationError();
               var1.readMessage(var9);
               this.addErrorInputField(var9);
               break;
            case 40:
               boolean var11 = var1.readBool();
               this.setCheckoutTokenRequired(var11);
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

      public BuyInstruments.UpdateInstrumentResponse setCheckoutTokenRequired(boolean var1) {
         this.hasCheckoutTokenRequired = (boolean)1;
         this.checkoutTokenRequired_ = var1;
         return this;
      }

      public BuyInstruments.UpdateInstrumentResponse setErrorInputField(int var1, BuyInstruments.InputValidationError var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.errorInputField_.set(var1, var2);
            return this;
         }
      }

      public BuyInstruments.UpdateInstrumentResponse setInstrumentId(String var1) {
         this.hasInstrumentId = (boolean)1;
         this.instrumentId_ = var1;
         return this;
      }

      public BuyInstruments.UpdateInstrumentResponse setResult(int var1) {
         this.hasResult = (boolean)1;
         this.result_ = var1;
         return this;
      }

      public BuyInstruments.UpdateInstrumentResponse setUserMessageHtml(String var1) {
         this.hasUserMessageHtml = (boolean)1;
         this.userMessageHtml_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasResult()) {
            int var2 = this.getResult();
            var1.writeInt32(1, var2);
         }

         if(this.hasInstrumentId()) {
            String var3 = this.getInstrumentId();
            var1.writeString(2, var3);
         }

         if(this.hasUserMessageHtml()) {
            String var4 = this.getUserMessageHtml();
            var1.writeString(3, var4);
         }

         Iterator var5 = this.getErrorInputFieldList().iterator();

         while(var5.hasNext()) {
            BuyInstruments.InputValidationError var6 = (BuyInstruments.InputValidationError)var5.next();
            var1.writeMessage(4, var6);
         }

         if(this.hasCheckoutTokenRequired()) {
            boolean var7 = this.getCheckoutTokenRequired();
            var1.writeBool(5, var7);
         }
      }
   }

   public static final class InputValidationError extends MessageMicro {

      public static final int ADDR_ADDRESS_LINE1 = 5;
      public static final int ADDR_ADDRESS_LINE2 = 6;
      public static final int ADDR_CITY = 7;
      public static final int ADDR_DEPENDENT_LOCALITY = 11;
      public static final int ADDR_NAME = 4;
      public static final int ADDR_PHONE = 12;
      public static final int ADDR_POSTAL_CODE = 9;
      public static final int ADDR_POSTAL_COUNTRY = 10;
      public static final int ADDR_STATE = 8;
      public static final int ADDR_WHOLE_ADDRESS = 13;
      public static final int BIRTH_DATE = 14;
      public static final int CC_CVC = 1;
      public static final int CC_EXP_MONTH = 3;
      public static final int CC_EXP_YEAR = 2;
      public static final int CC_NUMBER = 0;
      public static final int EMAIL = 17;
      public static final int ERROR_MESSAGE_FIELD_NUMBER = 2;
      public static final int FIRST_NAME = 15;
      public static final int INPUT_FIELD_FIELD_NUMBER = 1;
      public static final int LAST_NAME = 16;
      public static final int PASSWORD = 18;
      private int cachedSize = -1;
      private String errorMessage_ = "";
      private boolean hasErrorMessage;
      private boolean hasInputField;
      private int inputField_ = 0;


      public InputValidationError() {}

      public static BuyInstruments.InputValidationError parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BuyInstruments.InputValidationError()).mergeFrom(var0);
      }

      public static BuyInstruments.InputValidationError parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BuyInstruments.InputValidationError)((BuyInstruments.InputValidationError)(new BuyInstruments.InputValidationError()).mergeFrom(var0));
      }

      public final BuyInstruments.InputValidationError clear() {
         BuyInstruments.InputValidationError var1 = this.clearInputField();
         BuyInstruments.InputValidationError var2 = this.clearErrorMessage();
         this.cachedSize = -1;
         return this;
      }

      public BuyInstruments.InputValidationError clearErrorMessage() {
         this.hasErrorMessage = (boolean)0;
         this.errorMessage_ = "";
         return this;
      }

      public BuyInstruments.InputValidationError clearInputField() {
         this.hasInputField = (boolean)0;
         this.inputField_ = 0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getErrorMessage() {
         return this.errorMessage_;
      }

      public int getInputField() {
         return this.inputField_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasInputField()) {
            int var2 = this.getInputField();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasErrorMessage()) {
            String var4 = this.getErrorMessage();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasErrorMessage() {
         return this.hasErrorMessage;
      }

      public boolean hasInputField() {
         return this.hasInputField;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasInputField) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public BuyInstruments.InputValidationError mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setInputField(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setErrorMessage(var5);
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

      public BuyInstruments.InputValidationError setErrorMessage(String var1) {
         this.hasErrorMessage = (boolean)1;
         this.errorMessage_ = var1;
         return this;
      }

      public BuyInstruments.InputValidationError setInputField(int var1) {
         this.hasInputField = (boolean)1;
         this.inputField_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasInputField()) {
            int var2 = this.getInputField();
            var1.writeInt32(1, var2);
         }

         if(this.hasErrorMessage()) {
            String var3 = this.getErrorMessage();
            var1.writeString(2, var3);
         }
      }
   }

   public static final class InstrumentEnums extends MessageMicro {

      public static final int ACCOUNTING = 3;
      public static final int AMEX = 3;
      public static final int AMOUNT_EXCEEDED = 1;
      public static final int CARD = 0;
      public static final int CARRIER_BILLING = 2;
      public static final int DISCOVER = 4;
      public static final int EFT = 1;
      public static final int EXCLUDED = 6;
      public static final int EXPIRED = 2;
      public static final int INAPPLICABLE_CURRENCY = 4;
      public static final int INAPPLICABLE_INSTRUMENTSET = 3;
      public static final int INAPPLICABLE_LOCATION = 5;
      public static final int MASTER_CARD = 2;
      public static final int PAYPAL = 4;
      public static final int UNKNOWN = 0;
      public static final int VISA = 1;
      private int cachedSize = -1;


      public InstrumentEnums() {}

      public static BuyInstruments.InstrumentEnums parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BuyInstruments.InstrumentEnums()).mergeFrom(var0);
      }

      public static BuyInstruments.InstrumentEnums parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BuyInstruments.InstrumentEnums)((BuyInstruments.InstrumentEnums)(new BuyInstruments.InstrumentEnums()).mergeFrom(var0));
      }

      public final BuyInstruments.InstrumentEnums clear() {
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

      public BuyInstruments.InstrumentEnums mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class Instrument extends MessageMicro {

      public static final int BILLING_ADDRESS_FIELD_NUMBER = 2;
      public static final int CARRIER_BILLING_FIELD_NUMBER = 4;
      public static final int CREDIT_CARD_FIELD_NUMBER = 3;
      public static final int INSTRUMENT_ID_FIELD_NUMBER = 1;
      private Address billingAddress_ = null;
      private int cachedSize = -1;
      private BuyInstruments.CarrierBillingInstrument carrierBilling_ = null;
      private BuyInstruments.CreditCardInstrument creditCard_ = null;
      private boolean hasBillingAddress;
      private boolean hasCarrierBilling;
      private boolean hasCreditCard;
      private boolean hasInstrumentId;
      private String instrumentId_ = "";


      public Instrument() {}

      public static BuyInstruments.Instrument parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BuyInstruments.Instrument()).mergeFrom(var0);
      }

      public static BuyInstruments.Instrument parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BuyInstruments.Instrument)((BuyInstruments.Instrument)(new BuyInstruments.Instrument()).mergeFrom(var0));
      }

      public final BuyInstruments.Instrument clear() {
         BuyInstruments.Instrument var1 = this.clearInstrumentId();
         BuyInstruments.Instrument var2 = this.clearBillingAddress();
         BuyInstruments.Instrument var3 = this.clearCreditCard();
         BuyInstruments.Instrument var4 = this.clearCarrierBilling();
         this.cachedSize = -1;
         return this;
      }

      public BuyInstruments.Instrument clearBillingAddress() {
         this.hasBillingAddress = (boolean)0;
         this.billingAddress_ = null;
         return this;
      }

      public BuyInstruments.Instrument clearCarrierBilling() {
         this.hasCarrierBilling = (boolean)0;
         this.carrierBilling_ = null;
         return this;
      }

      public BuyInstruments.Instrument clearCreditCard() {
         this.hasCreditCard = (boolean)0;
         this.creditCard_ = null;
         return this;
      }

      public BuyInstruments.Instrument clearInstrumentId() {
         this.hasInstrumentId = (boolean)0;
         this.instrumentId_ = "";
         return this;
      }

      public Address getBillingAddress() {
         return this.billingAddress_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public BuyInstruments.CarrierBillingInstrument getCarrierBilling() {
         return this.carrierBilling_;
      }

      public BuyInstruments.CreditCardInstrument getCreditCard() {
         return this.creditCard_;
      }

      public String getInstrumentId() {
         return this.instrumentId_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasInstrumentId()) {
            String var2 = this.getInstrumentId();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasBillingAddress()) {
            Address var4 = this.getBillingAddress();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         if(this.hasCreditCard()) {
            BuyInstruments.CreditCardInstrument var6 = this.getCreditCard();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         if(this.hasCarrierBilling()) {
            BuyInstruments.CarrierBillingInstrument var8 = this.getCarrierBilling();
            int var9 = CodedOutputStreamMicro.computeMessageSize(4, var8);
            var1 += var9;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasBillingAddress() {
         return this.hasBillingAddress;
      }

      public boolean hasCarrierBilling() {
         return this.hasCarrierBilling;
      }

      public boolean hasCreditCard() {
         return this.hasCreditCard;
      }

      public boolean hasInstrumentId() {
         return this.hasInstrumentId;
      }

      public final boolean isInitialized() {
         return true;
      }

      public BuyInstruments.Instrument mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setInstrumentId(var3);
               break;
            case 18:
               Address var5 = new Address();
               var1.readMessage(var5);
               this.setBillingAddress(var5);
               break;
            case 26:
               BuyInstruments.CreditCardInstrument var7 = new BuyInstruments.CreditCardInstrument();
               var1.readMessage(var7);
               this.setCreditCard(var7);
               break;
            case 34:
               BuyInstruments.CarrierBillingInstrument var9 = new BuyInstruments.CarrierBillingInstrument();
               var1.readMessage(var9);
               this.setCarrierBilling(var9);
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

      public BuyInstruments.Instrument setBillingAddress(Address var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasBillingAddress = (boolean)1;
            this.billingAddress_ = var1;
            return this;
         }
      }

      public BuyInstruments.Instrument setCarrierBilling(BuyInstruments.CarrierBillingInstrument var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasCarrierBilling = (boolean)1;
            this.carrierBilling_ = var1;
            return this;
         }
      }

      public BuyInstruments.Instrument setCreditCard(BuyInstruments.CreditCardInstrument var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasCreditCard = (boolean)1;
            this.creditCard_ = var1;
            return this;
         }
      }

      public BuyInstruments.Instrument setInstrumentId(String var1) {
         this.hasInstrumentId = (boolean)1;
         this.instrumentId_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasInstrumentId()) {
            String var2 = this.getInstrumentId();
            var1.writeString(1, var2);
         }

         if(this.hasBillingAddress()) {
            Address var3 = this.getBillingAddress();
            var1.writeMessage(2, var3);
         }

         if(this.hasCreditCard()) {
            BuyInstruments.CreditCardInstrument var4 = this.getCreditCard();
            var1.writeMessage(3, var4);
         }

         if(this.hasCarrierBilling()) {
            BuyInstruments.CarrierBillingInstrument var5 = this.getCarrierBilling();
            var1.writeMessage(4, var5);
         }
      }
   }

   public static final class UpdateInstrumentRequest extends MessageMicro {

      public static final int CHECKOUT_TOKEN_FIELD_NUMBER = 2;
      public static final int INSTRUMENT_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private String checkoutToken_ = "";
      private boolean hasCheckoutToken;
      private boolean hasInstrument;
      private BuyInstruments.Instrument instrument_ = null;


      public UpdateInstrumentRequest() {}

      public static BuyInstruments.UpdateInstrumentRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BuyInstruments.UpdateInstrumentRequest()).mergeFrom(var0);
      }

      public static BuyInstruments.UpdateInstrumentRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BuyInstruments.UpdateInstrumentRequest)((BuyInstruments.UpdateInstrumentRequest)(new BuyInstruments.UpdateInstrumentRequest()).mergeFrom(var0));
      }

      public final BuyInstruments.UpdateInstrumentRequest clear() {
         BuyInstruments.UpdateInstrumentRequest var1 = this.clearInstrument();
         BuyInstruments.UpdateInstrumentRequest var2 = this.clearCheckoutToken();
         this.cachedSize = -1;
         return this;
      }

      public BuyInstruments.UpdateInstrumentRequest clearCheckoutToken() {
         this.hasCheckoutToken = (boolean)0;
         this.checkoutToken_ = "";
         return this;
      }

      public BuyInstruments.UpdateInstrumentRequest clearInstrument() {
         this.hasInstrument = (boolean)0;
         this.instrument_ = null;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getCheckoutToken() {
         return this.checkoutToken_;
      }

      public BuyInstruments.Instrument getInstrument() {
         return this.instrument_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasInstrument()) {
            BuyInstruments.Instrument var2 = this.getInstrument();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasCheckoutToken()) {
            String var4 = this.getCheckoutToken();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasCheckoutToken() {
         return this.hasCheckoutToken;
      }

      public boolean hasInstrument() {
         return this.hasInstrument;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasInstrument) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public BuyInstruments.UpdateInstrumentRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               BuyInstruments.Instrument var3 = new BuyInstruments.Instrument();
               var1.readMessage(var3);
               this.setInstrument(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setCheckoutToken(var5);
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

      public BuyInstruments.UpdateInstrumentRequest setCheckoutToken(String var1) {
         this.hasCheckoutToken = (boolean)1;
         this.checkoutToken_ = var1;
         return this;
      }

      public BuyInstruments.UpdateInstrumentRequest setInstrument(BuyInstruments.Instrument var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasInstrument = (boolean)1;
            this.instrument_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasInstrument()) {
            BuyInstruments.Instrument var2 = this.getInstrument();
            var1.writeMessage(1, var2);
         }

         if(this.hasCheckoutToken()) {
            String var3 = this.getCheckoutToken();
            var1.writeString(2, var3);
         }
      }
   }

   public static final class CarrierBillingCredentials extends MessageMicro {

      public static final int EXPIRATION_FIELD_NUMBER = 2;
      public static final int VALUE_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private long expiration_ = 0L;
      private boolean hasExpiration;
      private boolean hasValue;
      private String value_ = "";


      public CarrierBillingCredentials() {}

      public static BuyInstruments.CarrierBillingCredentials parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BuyInstruments.CarrierBillingCredentials()).mergeFrom(var0);
      }

      public static BuyInstruments.CarrierBillingCredentials parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BuyInstruments.CarrierBillingCredentials)((BuyInstruments.CarrierBillingCredentials)(new BuyInstruments.CarrierBillingCredentials()).mergeFrom(var0));
      }

      public final BuyInstruments.CarrierBillingCredentials clear() {
         BuyInstruments.CarrierBillingCredentials var1 = this.clearValue();
         BuyInstruments.CarrierBillingCredentials var2 = this.clearExpiration();
         this.cachedSize = -1;
         return this;
      }

      public BuyInstruments.CarrierBillingCredentials clearExpiration() {
         this.hasExpiration = (boolean)0;
         this.expiration_ = 0L;
         return this;
      }

      public BuyInstruments.CarrierBillingCredentials clearValue() {
         this.hasValue = (boolean)0;
         this.value_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public long getExpiration() {
         return this.expiration_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasValue()) {
            String var2 = this.getValue();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasExpiration()) {
            long var4 = this.getExpiration();
            int var6 = CodedOutputStreamMicro.computeInt64Size(2, var4);
            var1 += var6;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getValue() {
         return this.value_;
      }

      public boolean hasExpiration() {
         return this.hasExpiration;
      }

      public boolean hasValue() {
         return this.hasValue;
      }

      public final boolean isInitialized() {
         return true;
      }

      public BuyInstruments.CarrierBillingCredentials mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setValue(var3);
               break;
            case 16:
               long var5 = var1.readInt64();
               this.setExpiration(var5);
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

      public BuyInstruments.CarrierBillingCredentials setExpiration(long var1) {
         this.hasExpiration = (boolean)1;
         this.expiration_ = var1;
         return this;
      }

      public BuyInstruments.CarrierBillingCredentials setValue(String var1) {
         this.hasValue = (boolean)1;
         this.value_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasValue()) {
            String var2 = this.getValue();
            var1.writeString(1, var2);
         }

         if(this.hasExpiration()) {
            long var3 = this.getExpiration();
            var1.writeInt64(2, var3);
         }
      }
   }

   public static final class CreditCardInstrument extends MessageMicro {

      public static final int ESCROW_HANDLE_FIELD_NUMBER = 2;
      public static final int EXPIRATION_MONTH_FIELD_NUMBER = 4;
      public static final int EXPIRATION_YEAR_FIELD_NUMBER = 5;
      public static final int LAST_DIGITS_FIELD_NUMBER = 3;
      public static final int TYPE_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private String escrowHandle_ = "";
      private int expirationMonth_ = 0;
      private int expirationYear_ = 0;
      private boolean hasEscrowHandle;
      private boolean hasExpirationMonth;
      private boolean hasExpirationYear;
      private boolean hasLastDigits;
      private boolean hasType;
      private String lastDigits_ = "";
      private int type_ = 0;


      public CreditCardInstrument() {}

      public static BuyInstruments.CreditCardInstrument parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BuyInstruments.CreditCardInstrument()).mergeFrom(var0);
      }

      public static BuyInstruments.CreditCardInstrument parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BuyInstruments.CreditCardInstrument)((BuyInstruments.CreditCardInstrument)(new BuyInstruments.CreditCardInstrument()).mergeFrom(var0));
      }

      public final BuyInstruments.CreditCardInstrument clear() {
         BuyInstruments.CreditCardInstrument var1 = this.clearType();
         BuyInstruments.CreditCardInstrument var2 = this.clearEscrowHandle();
         BuyInstruments.CreditCardInstrument var3 = this.clearLastDigits();
         BuyInstruments.CreditCardInstrument var4 = this.clearExpirationMonth();
         BuyInstruments.CreditCardInstrument var5 = this.clearExpirationYear();
         this.cachedSize = -1;
         return this;
      }

      public BuyInstruments.CreditCardInstrument clearEscrowHandle() {
         this.hasEscrowHandle = (boolean)0;
         this.escrowHandle_ = "";
         return this;
      }

      public BuyInstruments.CreditCardInstrument clearExpirationMonth() {
         this.hasExpirationMonth = (boolean)0;
         this.expirationMonth_ = 0;
         return this;
      }

      public BuyInstruments.CreditCardInstrument clearExpirationYear() {
         this.hasExpirationYear = (boolean)0;
         this.expirationYear_ = 0;
         return this;
      }

      public BuyInstruments.CreditCardInstrument clearLastDigits() {
         this.hasLastDigits = (boolean)0;
         this.lastDigits_ = "";
         return this;
      }

      public BuyInstruments.CreditCardInstrument clearType() {
         this.hasType = (boolean)0;
         this.type_ = 0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getEscrowHandle() {
         return this.escrowHandle_;
      }

      public int getExpirationMonth() {
         return this.expirationMonth_;
      }

      public int getExpirationYear() {
         return this.expirationYear_;
      }

      public String getLastDigits() {
         return this.lastDigits_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasType()) {
            int var2 = this.getType();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasEscrowHandle()) {
            String var4 = this.getEscrowHandle();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasLastDigits()) {
            String var6 = this.getLastDigits();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasExpirationMonth()) {
            int var8 = this.getExpirationMonth();
            int var9 = CodedOutputStreamMicro.computeInt32Size(4, var8);
            var1 += var9;
         }

         if(this.hasExpirationYear()) {
            int var10 = this.getExpirationYear();
            int var11 = CodedOutputStreamMicro.computeInt32Size(5, var10);
            var1 += var11;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getType() {
         return this.type_;
      }

      public boolean hasEscrowHandle() {
         return this.hasEscrowHandle;
      }

      public boolean hasExpirationMonth() {
         return this.hasExpirationMonth;
      }

      public boolean hasExpirationYear() {
         return this.hasExpirationYear;
      }

      public boolean hasLastDigits() {
         return this.hasLastDigits;
      }

      public boolean hasType() {
         return this.hasType;
      }

      public final boolean isInitialized() {
         return true;
      }

      public BuyInstruments.CreditCardInstrument mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setType(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setEscrowHandle(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setLastDigits(var7);
               break;
            case 32:
               int var9 = var1.readInt32();
               this.setExpirationMonth(var9);
               break;
            case 40:
               int var11 = var1.readInt32();
               this.setExpirationYear(var11);
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

      public BuyInstruments.CreditCardInstrument setEscrowHandle(String var1) {
         this.hasEscrowHandle = (boolean)1;
         this.escrowHandle_ = var1;
         return this;
      }

      public BuyInstruments.CreditCardInstrument setExpirationMonth(int var1) {
         this.hasExpirationMonth = (boolean)1;
         this.expirationMonth_ = var1;
         return this;
      }

      public BuyInstruments.CreditCardInstrument setExpirationYear(int var1) {
         this.hasExpirationYear = (boolean)1;
         this.expirationYear_ = var1;
         return this;
      }

      public BuyInstruments.CreditCardInstrument setLastDigits(String var1) {
         this.hasLastDigits = (boolean)1;
         this.lastDigits_ = var1;
         return this;
      }

      public BuyInstruments.CreditCardInstrument setType(int var1) {
         this.hasType = (boolean)1;
         this.type_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasType()) {
            int var2 = this.getType();
            var1.writeInt32(1, var2);
         }

         if(this.hasEscrowHandle()) {
            String var3 = this.getEscrowHandle();
            var1.writeString(2, var3);
         }

         if(this.hasLastDigits()) {
            String var4 = this.getLastDigits();
            var1.writeString(3, var4);
         }

         if(this.hasExpirationMonth()) {
            int var5 = this.getExpirationMonth();
            var1.writeInt32(4, var5);
         }

         if(this.hasExpirationYear()) {
            int var6 = this.getExpirationYear();
            var1.writeInt32(5, var6);
         }
      }
   }

   public static final class CheckInstrumentResponse extends MessageMicro {

      public static final int CHECKOUT_TOKEN_REQUIRED_FIELD_NUMBER = 2;
      public static final int USER_HAS_VALID_CREDIT_CARD_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private boolean checkoutTokenRequired_ = 0;
      private boolean hasCheckoutTokenRequired;
      private boolean hasUserHasValidCreditCard;
      private boolean userHasValidCreditCard_ = 0;


      public CheckInstrumentResponse() {}

      public static BuyInstruments.CheckInstrumentResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BuyInstruments.CheckInstrumentResponse()).mergeFrom(var0);
      }

      public static BuyInstruments.CheckInstrumentResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BuyInstruments.CheckInstrumentResponse)((BuyInstruments.CheckInstrumentResponse)(new BuyInstruments.CheckInstrumentResponse()).mergeFrom(var0));
      }

      public final BuyInstruments.CheckInstrumentResponse clear() {
         BuyInstruments.CheckInstrumentResponse var1 = this.clearUserHasValidCreditCard();
         BuyInstruments.CheckInstrumentResponse var2 = this.clearCheckoutTokenRequired();
         this.cachedSize = -1;
         return this;
      }

      public BuyInstruments.CheckInstrumentResponse clearCheckoutTokenRequired() {
         this.hasCheckoutTokenRequired = (boolean)0;
         this.checkoutTokenRequired_ = (boolean)0;
         return this;
      }

      public BuyInstruments.CheckInstrumentResponse clearUserHasValidCreditCard() {
         this.hasUserHasValidCreditCard = (boolean)0;
         this.userHasValidCreditCard_ = (boolean)0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public boolean getCheckoutTokenRequired() {
         return this.checkoutTokenRequired_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasUserHasValidCreditCard()) {
            boolean var2 = this.getUserHasValidCreditCard();
            int var3 = CodedOutputStreamMicro.computeBoolSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasCheckoutTokenRequired()) {
            boolean var4 = this.getCheckoutTokenRequired();
            int var5 = CodedOutputStreamMicro.computeBoolSize(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean getUserHasValidCreditCard() {
         return this.userHasValidCreditCard_;
      }

      public boolean hasCheckoutTokenRequired() {
         return this.hasCheckoutTokenRequired;
      }

      public boolean hasUserHasValidCreditCard() {
         return this.hasUserHasValidCreditCard;
      }

      public final boolean isInitialized() {
         return true;
      }

      public BuyInstruments.CheckInstrumentResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               boolean var3 = var1.readBool();
               this.setUserHasValidCreditCard(var3);
               break;
            case 16:
               boolean var5 = var1.readBool();
               this.setCheckoutTokenRequired(var5);
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

      public BuyInstruments.CheckInstrumentResponse setCheckoutTokenRequired(boolean var1) {
         this.hasCheckoutTokenRequired = (boolean)1;
         this.checkoutTokenRequired_ = var1;
         return this;
      }

      public BuyInstruments.CheckInstrumentResponse setUserHasValidCreditCard(boolean var1) {
         this.hasUserHasValidCreditCard = (boolean)1;
         this.userHasValidCreditCard_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasUserHasValidCreditCard()) {
            boolean var2 = this.getUserHasValidCreditCard();
            var1.writeBool(1, var2);
         }

         if(this.hasCheckoutTokenRequired()) {
            boolean var3 = this.getCheckoutTokenRequired();
            var1.writeBool(2, var3);
         }
      }
   }
}
