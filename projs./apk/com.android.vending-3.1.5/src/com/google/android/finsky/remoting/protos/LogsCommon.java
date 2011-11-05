package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LogsCommon {

   private LogsCommon() {}

   public static final class ClientType extends MessageMicro {

      public static final int GOOGLE_TV = 4;
      public static final int PHONE = 3;
      public static final int TABLET = 2;
      public static final int WEB = 1;
      private int cachedSize = -1;


      public ClientType() {}

      public static LogsCommon.ClientType parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new LogsCommon.ClientType()).mergeFrom(var0);
      }

      public static LogsCommon.ClientType parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (LogsCommon.ClientType)((LogsCommon.ClientType)(new LogsCommon.ClientType()).mergeFrom(var0));
      }

      public final LogsCommon.ClientType clear() {
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

      public LogsCommon.ClientType mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class LogsPrice extends MessageMicro {

      public static final int CHECKOUT_FLOW_REQUIRED_FIELD_NUMBER = 5;
      public static final int CURRENCY_CODE_FIELD_NUMBER = 2;
      public static final int FULL_PRICE_MICROS_FIELD_NUMBER = 6;
      public static final int MICROS_FIELD_NUMBER = 1;
      public static final int OFFER_TYPE_FIELD_NUMBER = 8;
      private int cachedSize = -1;
      private boolean checkoutFlowRequired_ = 0;
      private String currencyCode_ = "";
      private long fullPriceMicros_ = 0L;
      private boolean hasCheckoutFlowRequired;
      private boolean hasCurrencyCode;
      private boolean hasFullPriceMicros;
      private boolean hasMicros;
      private boolean hasOfferType;
      private long micros_ = 0L;
      private int offerType_ = 1;


      public LogsPrice() {}

      public static LogsCommon.LogsPrice parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new LogsCommon.LogsPrice()).mergeFrom(var0);
      }

      public static LogsCommon.LogsPrice parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (LogsCommon.LogsPrice)((LogsCommon.LogsPrice)(new LogsCommon.LogsPrice()).mergeFrom(var0));
      }

      public final LogsCommon.LogsPrice clear() {
         LogsCommon.LogsPrice var1 = this.clearMicros();
         LogsCommon.LogsPrice var2 = this.clearCurrencyCode();
         LogsCommon.LogsPrice var3 = this.clearFullPriceMicros();
         LogsCommon.LogsPrice var4 = this.clearCheckoutFlowRequired();
         LogsCommon.LogsPrice var5 = this.clearOfferType();
         this.cachedSize = -1;
         return this;
      }

      public LogsCommon.LogsPrice clearCheckoutFlowRequired() {
         this.hasCheckoutFlowRequired = (boolean)0;
         this.checkoutFlowRequired_ = (boolean)0;
         return this;
      }

      public LogsCommon.LogsPrice clearCurrencyCode() {
         this.hasCurrencyCode = (boolean)0;
         this.currencyCode_ = "";
         return this;
      }

      public LogsCommon.LogsPrice clearFullPriceMicros() {
         this.hasFullPriceMicros = (boolean)0;
         this.fullPriceMicros_ = 0L;
         return this;
      }

      public LogsCommon.LogsPrice clearMicros() {
         this.hasMicros = (boolean)0;
         this.micros_ = 0L;
         return this;
      }

      public LogsCommon.LogsPrice clearOfferType() {
         this.hasOfferType = (boolean)0;
         this.offerType_ = 1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public boolean getCheckoutFlowRequired() {
         return this.checkoutFlowRequired_;
      }

      public String getCurrencyCode() {
         return this.currencyCode_;
      }

      public long getFullPriceMicros() {
         return this.fullPriceMicros_;
      }

      public long getMicros() {
         return this.micros_;
      }

      public int getOfferType() {
         return this.offerType_;
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

         if(this.hasCheckoutFlowRequired()) {
            boolean var7 = this.getCheckoutFlowRequired();
            int var8 = CodedOutputStreamMicro.computeBoolSize(5, var7);
            var1 += var8;
         }

         if(this.hasFullPriceMicros()) {
            long var9 = this.getFullPriceMicros();
            int var11 = CodedOutputStreamMicro.computeInt64Size(6, var9);
            var1 += var11;
         }

         if(this.hasOfferType()) {
            int var12 = this.getOfferType();
            int var13 = CodedOutputStreamMicro.computeInt32Size(8, var12);
            var1 += var13;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasCheckoutFlowRequired() {
         return this.hasCheckoutFlowRequired;
      }

      public boolean hasCurrencyCode() {
         return this.hasCurrencyCode;
      }

      public boolean hasFullPriceMicros() {
         return this.hasFullPriceMicros;
      }

      public boolean hasMicros() {
         return this.hasMicros;
      }

      public boolean hasOfferType() {
         return this.hasOfferType;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasMicros && this.hasCurrencyCode) {
            var1 = true;
         }

         return var1;
      }

      public LogsCommon.LogsPrice mergeFrom(CodedInputStreamMicro var1) throws IOException {
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
            case 40:
               boolean var8 = var1.readBool();
               this.setCheckoutFlowRequired(var8);
               break;
            case 48:
               long var10 = var1.readInt64();
               this.setFullPriceMicros(var10);
               break;
            case 64:
               int var13 = var1.readInt32();
               this.setOfferType(var13);
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

      public LogsCommon.LogsPrice setCheckoutFlowRequired(boolean var1) {
         this.hasCheckoutFlowRequired = (boolean)1;
         this.checkoutFlowRequired_ = var1;
         return this;
      }

      public LogsCommon.LogsPrice setCurrencyCode(String var1) {
         this.hasCurrencyCode = (boolean)1;
         this.currencyCode_ = var1;
         return this;
      }

      public LogsCommon.LogsPrice setFullPriceMicros(long var1) {
         this.hasFullPriceMicros = (boolean)1;
         this.fullPriceMicros_ = var1;
         return this;
      }

      public LogsCommon.LogsPrice setMicros(long var1) {
         this.hasMicros = (boolean)1;
         this.micros_ = var1;
         return this;
      }

      public LogsCommon.LogsPrice setOfferType(int var1) {
         this.hasOfferType = (boolean)1;
         this.offerType_ = var1;
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

         if(this.hasCheckoutFlowRequired()) {
            boolean var5 = this.getCheckoutFlowRequired();
            var1.writeBool(5, var5);
         }

         if(this.hasFullPriceMicros()) {
            long var6 = this.getFullPriceMicros();
            var1.writeInt64(6, var6);
         }

         if(this.hasOfferType()) {
            int var8 = this.getOfferType();
            var1.writeInt32(8, var8);
         }
      }
   }

   public static final class UserInfo extends MessageMicro {

      public static final int CARRIER_MCCMNC_FIELD_NUMBER = 7;
      public static final int COUNTRY_FIELD_NUMBER = 4;
      public static final int EXPERIMENT_ID_FIELD_NUMBER = 8;
      public static final int GAIA_ID_FIELD_NUMBER = 1;
      public static final int LIST_ID_FIELD_NUMBER = 6;
      public static final int SAFESEARCH_LEVEL_FIELD_NUMBER = 5;
      public static final int USER_COUNTRY_FIELD_NUMBER = 2;
      public static final int USER_LANGUAGE_FIELD_NUMBER = 3;
      private int cachedSize;
      private List<String> carrierMccmnc_;
      private String country_ = "";
      private List<String> experimentId_;
      private long gaiaId_ = 0L;
      private boolean hasCountry;
      private boolean hasGaiaId;
      private boolean hasListId;
      private boolean hasSafesearchLevel;
      private boolean hasUserCountry;
      private boolean hasUserLanguage;
      private String listId_ = "";
      private int safesearchLevel_ = 0;
      private String userCountry_ = "";
      private String userLanguage_ = "";


      public UserInfo() {
         List var1 = Collections.emptyList();
         this.carrierMccmnc_ = var1;
         List var2 = Collections.emptyList();
         this.experimentId_ = var2;
         this.cachedSize = -1;
      }

      public static LogsCommon.UserInfo parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new LogsCommon.UserInfo()).mergeFrom(var0);
      }

      public static LogsCommon.UserInfo parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (LogsCommon.UserInfo)((LogsCommon.UserInfo)(new LogsCommon.UserInfo()).mergeFrom(var0));
      }

      public LogsCommon.UserInfo addCarrierMccmnc(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.carrierMccmnc_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.carrierMccmnc_ = var2;
            }

            this.carrierMccmnc_.add(var1);
            return this;
         }
      }

      public LogsCommon.UserInfo addExperimentId(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.experimentId_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.experimentId_ = var2;
            }

            this.experimentId_.add(var1);
            return this;
         }
      }

      public final LogsCommon.UserInfo clear() {
         LogsCommon.UserInfo var1 = this.clearGaiaId();
         LogsCommon.UserInfo var2 = this.clearUserCountry();
         LogsCommon.UserInfo var3 = this.clearUserLanguage();
         LogsCommon.UserInfo var4 = this.clearCountry();
         LogsCommon.UserInfo var5 = this.clearSafesearchLevel();
         LogsCommon.UserInfo var6 = this.clearListId();
         LogsCommon.UserInfo var7 = this.clearCarrierMccmnc();
         LogsCommon.UserInfo var8 = this.clearExperimentId();
         this.cachedSize = -1;
         return this;
      }

      public LogsCommon.UserInfo clearCarrierMccmnc() {
         List var1 = Collections.emptyList();
         this.carrierMccmnc_ = var1;
         return this;
      }

      public LogsCommon.UserInfo clearCountry() {
         this.hasCountry = (boolean)0;
         this.country_ = "";
         return this;
      }

      public LogsCommon.UserInfo clearExperimentId() {
         List var1 = Collections.emptyList();
         this.experimentId_ = var1;
         return this;
      }

      public LogsCommon.UserInfo clearGaiaId() {
         this.hasGaiaId = (boolean)0;
         this.gaiaId_ = 0L;
         return this;
      }

      public LogsCommon.UserInfo clearListId() {
         this.hasListId = (boolean)0;
         this.listId_ = "";
         return this;
      }

      public LogsCommon.UserInfo clearSafesearchLevel() {
         this.hasSafesearchLevel = (boolean)0;
         this.safesearchLevel_ = 0;
         return this;
      }

      public LogsCommon.UserInfo clearUserCountry() {
         this.hasUserCountry = (boolean)0;
         this.userCountry_ = "";
         return this;
      }

      public LogsCommon.UserInfo clearUserLanguage() {
         this.hasUserLanguage = (boolean)0;
         this.userLanguage_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getCarrierMccmnc(int var1) {
         return (String)this.carrierMccmnc_.get(var1);
      }

      public int getCarrierMccmncCount() {
         return this.carrierMccmnc_.size();
      }

      public List<String> getCarrierMccmncList() {
         return this.carrierMccmnc_;
      }

      public String getCountry() {
         return this.country_;
      }

      public String getExperimentId(int var1) {
         return (String)this.experimentId_.get(var1);
      }

      public int getExperimentIdCount() {
         return this.experimentId_.size();
      }

      public List<String> getExperimentIdList() {
         return this.experimentId_;
      }

      public long getGaiaId() {
         return this.gaiaId_;
      }

      public String getListId() {
         return this.listId_;
      }

      public int getSafesearchLevel() {
         return this.safesearchLevel_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasGaiaId()) {
            long var2 = this.getGaiaId();
            int var4 = CodedOutputStreamMicro.computeFixed64Size(1, var2);
            var1 = 0 + var4;
         }

         if(this.hasUserCountry()) {
            String var5 = this.getUserCountry();
            int var6 = CodedOutputStreamMicro.computeStringSize(2, var5);
            var1 += var6;
         }

         if(this.hasUserLanguage()) {
            String var7 = this.getUserLanguage();
            int var8 = CodedOutputStreamMicro.computeStringSize(3, var7);
            var1 += var8;
         }

         if(this.hasCountry()) {
            String var9 = this.getCountry();
            int var10 = CodedOutputStreamMicro.computeStringSize(4, var9);
            var1 += var10;
         }

         if(this.hasSafesearchLevel()) {
            int var11 = this.getSafesearchLevel();
            int var12 = CodedOutputStreamMicro.computeInt32Size(5, var11);
            var1 += var12;
         }

         if(this.hasListId()) {
            String var13 = this.getListId();
            int var14 = CodedOutputStreamMicro.computeStringSize(6, var13);
            var1 += var14;
         }

         int var15 = 0;

         int var17;
         for(Iterator var16 = this.getCarrierMccmncList().iterator(); var16.hasNext(); var15 += var17) {
            var17 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var16.next());
         }

         int var18 = var1 + var15;
         int var19 = this.getCarrierMccmncList().size() * 1;
         int var20 = var18 + var19;
         int var21 = 0;

         int var23;
         for(Iterator var22 = this.getExperimentIdList().iterator(); var22.hasNext(); var21 += var23) {
            var23 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var22.next());
         }

         int var24 = var20 + var21;
         int var25 = this.getExperimentIdList().size() * 1;
         int var26 = var24 + var25;
         this.cachedSize = var26;
         return var26;
      }

      public String getUserCountry() {
         return this.userCountry_;
      }

      public String getUserLanguage() {
         return this.userLanguage_;
      }

      public boolean hasCountry() {
         return this.hasCountry;
      }

      public boolean hasGaiaId() {
         return this.hasGaiaId;
      }

      public boolean hasListId() {
         return this.hasListId;
      }

      public boolean hasSafesearchLevel() {
         return this.hasSafesearchLevel;
      }

      public boolean hasUserCountry() {
         return this.hasUserCountry;
      }

      public boolean hasUserLanguage() {
         return this.hasUserLanguage;
      }

      public final boolean isInitialized() {
         return true;
      }

      public LogsCommon.UserInfo mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 9:
               long var3 = var1.readFixed64();
               this.setGaiaId(var3);
               break;
            case 18:
               String var6 = var1.readString();
               this.setUserCountry(var6);
               break;
            case 26:
               String var8 = var1.readString();
               this.setUserLanguage(var8);
               break;
            case 34:
               String var10 = var1.readString();
               this.setCountry(var10);
               break;
            case 40:
               int var12 = var1.readInt32();
               this.setSafesearchLevel(var12);
               break;
            case 50:
               String var14 = var1.readString();
               this.setListId(var14);
               break;
            case 58:
               String var16 = var1.readString();
               this.addCarrierMccmnc(var16);
               break;
            case 66:
               String var18 = var1.readString();
               this.addExperimentId(var18);
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

      public LogsCommon.UserInfo setCarrierMccmnc(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.carrierMccmnc_.set(var1, var2);
            return this;
         }
      }

      public LogsCommon.UserInfo setCountry(String var1) {
         this.hasCountry = (boolean)1;
         this.country_ = var1;
         return this;
      }

      public LogsCommon.UserInfo setExperimentId(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.experimentId_.set(var1, var2);
            return this;
         }
      }

      public LogsCommon.UserInfo setGaiaId(long var1) {
         this.hasGaiaId = (boolean)1;
         this.gaiaId_ = var1;
         return this;
      }

      public LogsCommon.UserInfo setListId(String var1) {
         this.hasListId = (boolean)1;
         this.listId_ = var1;
         return this;
      }

      public LogsCommon.UserInfo setSafesearchLevel(int var1) {
         this.hasSafesearchLevel = (boolean)1;
         this.safesearchLevel_ = var1;
         return this;
      }

      public LogsCommon.UserInfo setUserCountry(String var1) {
         this.hasUserCountry = (boolean)1;
         this.userCountry_ = var1;
         return this;
      }

      public LogsCommon.UserInfo setUserLanguage(String var1) {
         this.hasUserLanguage = (boolean)1;
         this.userLanguage_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasGaiaId()) {
            long var2 = this.getGaiaId();
            var1.writeFixed64(1, var2);
         }

         if(this.hasUserCountry()) {
            String var4 = this.getUserCountry();
            var1.writeString(2, var4);
         }

         if(this.hasUserLanguage()) {
            String var5 = this.getUserLanguage();
            var1.writeString(3, var5);
         }

         if(this.hasCountry()) {
            String var6 = this.getCountry();
            var1.writeString(4, var6);
         }

         if(this.hasSafesearchLevel()) {
            int var7 = this.getSafesearchLevel();
            var1.writeInt32(5, var7);
         }

         if(this.hasListId()) {
            String var8 = this.getListId();
            var1.writeString(6, var8);
         }

         Iterator var9 = this.getCarrierMccmncList().iterator();

         while(var9.hasNext()) {
            String var10 = (String)var9.next();
            var1.writeString(7, var10);
         }

         Iterator var11 = this.getExperimentIdList().iterator();

         while(var11.hasNext()) {
            String var12 = (String)var11.next();
            var1.writeString(8, var12);
         }

      }
   }

   public static final class VisibleDocument extends MessageMicro {

      public static final int CONTAINER_DOCID_FIELD_NUMBER = 9;
      public static final int DOCID_FIELD_NUMBER = 1;
      public static final int POSITION_WITHIN_SLOT_FIELD_NUMBER = 8;
      public static final int PRICE_FIELD_NUMBER = 4;
      public static final int SLOT_FIELD_NUMBER = 7;
      public static final int TITLE_FIELD_NUMBER = 2;
      public static final int URL_FIELD_NUMBER = 3;
      private int cachedSize = -1;
      private String containerDocid_ = "";
      private String docid_ = "";
      private boolean hasContainerDocid;
      private boolean hasDocid;
      private boolean hasPositionWithinSlot;
      private boolean hasPrice;
      private boolean hasSlot;
      private boolean hasTitle;
      private boolean hasUrl;
      private int positionWithinSlot_ = 0;
      private LogsCommon.VisibleDocument.Price price_ = null;
      private String slot_ = "";
      private String title_ = "";
      private String url_ = "";


      public VisibleDocument() {}

      public static LogsCommon.VisibleDocument parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new LogsCommon.VisibleDocument()).mergeFrom(var0);
      }

      public static LogsCommon.VisibleDocument parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (LogsCommon.VisibleDocument)((LogsCommon.VisibleDocument)(new LogsCommon.VisibleDocument()).mergeFrom(var0));
      }

      public final LogsCommon.VisibleDocument clear() {
         LogsCommon.VisibleDocument var1 = this.clearDocid();
         LogsCommon.VisibleDocument var2 = this.clearTitle();
         LogsCommon.VisibleDocument var3 = this.clearUrl();
         LogsCommon.VisibleDocument var4 = this.clearPrice();
         LogsCommon.VisibleDocument var5 = this.clearSlot();
         LogsCommon.VisibleDocument var6 = this.clearPositionWithinSlot();
         LogsCommon.VisibleDocument var7 = this.clearContainerDocid();
         this.cachedSize = -1;
         return this;
      }

      public LogsCommon.VisibleDocument clearContainerDocid() {
         this.hasContainerDocid = (boolean)0;
         this.containerDocid_ = "";
         return this;
      }

      public LogsCommon.VisibleDocument clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = "";
         return this;
      }

      public LogsCommon.VisibleDocument clearPositionWithinSlot() {
         this.hasPositionWithinSlot = (boolean)0;
         this.positionWithinSlot_ = 0;
         return this;
      }

      public LogsCommon.VisibleDocument clearPrice() {
         this.hasPrice = (boolean)0;
         this.price_ = null;
         return this;
      }

      public LogsCommon.VisibleDocument clearSlot() {
         this.hasSlot = (boolean)0;
         this.slot_ = "";
         return this;
      }

      public LogsCommon.VisibleDocument clearTitle() {
         this.hasTitle = (boolean)0;
         this.title_ = "";
         return this;
      }

      public LogsCommon.VisibleDocument clearUrl() {
         this.hasUrl = (boolean)0;
         this.url_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getContainerDocid() {
         return this.containerDocid_;
      }

      public String getDocid() {
         return this.docid_;
      }

      public int getPositionWithinSlot() {
         return this.positionWithinSlot_;
      }

      public LogsCommon.VisibleDocument.Price getPrice() {
         return this.price_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasDocid()) {
            String var2 = this.getDocid();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasTitle()) {
            String var4 = this.getTitle();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasUrl()) {
            String var6 = this.getUrl();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasPrice()) {
            LogsCommon.VisibleDocument.Price var8 = this.getPrice();
            int var9 = CodedOutputStreamMicro.computeGroupSize(4, var8);
            var1 += var9;
         }

         if(this.hasSlot()) {
            String var10 = this.getSlot();
            int var11 = CodedOutputStreamMicro.computeStringSize(7, var10);
            var1 += var11;
         }

         if(this.hasPositionWithinSlot()) {
            int var12 = this.getPositionWithinSlot();
            int var13 = CodedOutputStreamMicro.computeInt32Size(8, var12);
            var1 += var13;
         }

         if(this.hasContainerDocid()) {
            String var14 = this.getContainerDocid();
            int var15 = CodedOutputStreamMicro.computeStringSize(9, var14);
            var1 += var15;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getSlot() {
         return this.slot_;
      }

      public String getTitle() {
         return this.title_;
      }

      public String getUrl() {
         return this.url_;
      }

      public boolean hasContainerDocid() {
         return this.hasContainerDocid;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public boolean hasPositionWithinSlot() {
         return this.hasPositionWithinSlot;
      }

      public boolean hasPrice() {
         return this.hasPrice;
      }

      public boolean hasSlot() {
         return this.hasSlot;
      }

      public boolean hasTitle() {
         return this.hasTitle;
      }

      public boolean hasUrl() {
         return this.hasUrl;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(this.hasPrice() && !this.getPrice().isInitialized()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public LogsCommon.VisibleDocument mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setDocid(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setTitle(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setUrl(var7);
               break;
            case 35:
               LogsCommon.VisibleDocument.Price var9 = new LogsCommon.VisibleDocument.Price();
               var1.readGroup(var9, 4);
               this.setPrice(var9);
               break;
            case 58:
               String var11 = var1.readString();
               this.setSlot(var11);
               break;
            case 64:
               int var13 = var1.readInt32();
               this.setPositionWithinSlot(var13);
               break;
            case 74:
               String var15 = var1.readString();
               this.setContainerDocid(var15);
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

      public LogsCommon.VisibleDocument setContainerDocid(String var1) {
         this.hasContainerDocid = (boolean)1;
         this.containerDocid_ = var1;
         return this;
      }

      public LogsCommon.VisibleDocument setDocid(String var1) {
         this.hasDocid = (boolean)1;
         this.docid_ = var1;
         return this;
      }

      public LogsCommon.VisibleDocument setPositionWithinSlot(int var1) {
         this.hasPositionWithinSlot = (boolean)1;
         this.positionWithinSlot_ = var1;
         return this;
      }

      public LogsCommon.VisibleDocument setPrice(LogsCommon.VisibleDocument.Price var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPrice = (boolean)1;
            this.price_ = var1;
            return this;
         }
      }

      public LogsCommon.VisibleDocument setSlot(String var1) {
         this.hasSlot = (boolean)1;
         this.slot_ = var1;
         return this;
      }

      public LogsCommon.VisibleDocument setTitle(String var1) {
         this.hasTitle = (boolean)1;
         this.title_ = var1;
         return this;
      }

      public LogsCommon.VisibleDocument setUrl(String var1) {
         this.hasUrl = (boolean)1;
         this.url_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasDocid()) {
            String var2 = this.getDocid();
            var1.writeString(1, var2);
         }

         if(this.hasTitle()) {
            String var3 = this.getTitle();
            var1.writeString(2, var3);
         }

         if(this.hasUrl()) {
            String var4 = this.getUrl();
            var1.writeString(3, var4);
         }

         if(this.hasPrice()) {
            LogsCommon.VisibleDocument.Price var5 = this.getPrice();
            var1.writeGroup(4, var5);
         }

         if(this.hasSlot()) {
            String var6 = this.getSlot();
            var1.writeString(7, var6);
         }

         if(this.hasPositionWithinSlot()) {
            int var7 = this.getPositionWithinSlot();
            var1.writeInt32(8, var7);
         }

         if(this.hasContainerDocid()) {
            String var8 = this.getContainerDocid();
            var1.writeString(9, var8);
         }
      }

      public static final class Price extends MessageMicro {

         public static final int FULL_PRICE_FIELD_NUMBER = 6;
         public static final int PURCHASE_PRICE_FIELD_NUMBER = 5;
         private int cachedSize = -1;
         private LogsCommon.LogsPrice fullPrice_ = null;
         private boolean hasFullPrice;
         private boolean hasPurchasePrice;
         private LogsCommon.LogsPrice purchasePrice_ = null;


         public Price() {}

         public final LogsCommon.VisibleDocument.Price clear() {
            LogsCommon.VisibleDocument.Price var1 = this.clearPurchasePrice();
            LogsCommon.VisibleDocument.Price var2 = this.clearFullPrice();
            this.cachedSize = -1;
            return this;
         }

         public LogsCommon.VisibleDocument.Price clearFullPrice() {
            this.hasFullPrice = (boolean)0;
            this.fullPrice_ = null;
            return this;
         }

         public LogsCommon.VisibleDocument.Price clearPurchasePrice() {
            this.hasPurchasePrice = (boolean)0;
            this.purchasePrice_ = null;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public LogsCommon.LogsPrice getFullPrice() {
            return this.fullPrice_;
         }

         public LogsCommon.LogsPrice getPurchasePrice() {
            return this.purchasePrice_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasPurchasePrice()) {
               LogsCommon.LogsPrice var2 = this.getPurchasePrice();
               int var3 = CodedOutputStreamMicro.computeMessageSize(5, var2);
               var1 = 0 + var3;
            }

            if(this.hasFullPrice()) {
               LogsCommon.LogsPrice var4 = this.getFullPrice();
               int var5 = CodedOutputStreamMicro.computeMessageSize(6, var4);
               var1 += var5;
            }

            this.cachedSize = var1;
            return var1;
         }

         public boolean hasFullPrice() {
            return this.hasFullPrice;
         }

         public boolean hasPurchasePrice() {
            return this.hasPurchasePrice;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if((!this.hasPurchasePrice() || this.getPurchasePrice().isInitialized()) && (!this.hasFullPrice() || this.getFullPrice().isInitialized())) {
               var1 = true;
            }

            return var1;
         }

         public LogsCommon.VisibleDocument.Price mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 42:
                  LogsCommon.LogsPrice var3 = new LogsCommon.LogsPrice();
                  var1.readMessage(var3);
                  this.setPurchasePrice(var3);
                  break;
               case 50:
                  LogsCommon.LogsPrice var5 = new LogsCommon.LogsPrice();
                  var1.readMessage(var5);
                  this.setFullPrice(var5);
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

         public LogsCommon.VisibleDocument.Price parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new LogsCommon.VisibleDocument.Price()).mergeFrom(var1);
         }

         public LogsCommon.VisibleDocument.Price parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (LogsCommon.VisibleDocument.Price)((LogsCommon.VisibleDocument.Price)(new LogsCommon.VisibleDocument.Price()).mergeFrom(var1));
         }

         public LogsCommon.VisibleDocument.Price setFullPrice(LogsCommon.LogsPrice var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasFullPrice = (boolean)1;
               this.fullPrice_ = var1;
               return this;
            }
         }

         public LogsCommon.VisibleDocument.Price setPurchasePrice(LogsCommon.LogsPrice var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasPurchasePrice = (boolean)1;
               this.purchasePrice_ = var1;
               return this;
            }
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasPurchasePrice()) {
               LogsCommon.LogsPrice var2 = this.getPurchasePrice();
               var1.writeMessage(5, var2);
            }

            if(this.hasFullPrice()) {
               LogsCommon.LogsPrice var3 = this.getFullPrice();
               var1.writeMessage(6, var3);
            }
         }
      }
   }
}
