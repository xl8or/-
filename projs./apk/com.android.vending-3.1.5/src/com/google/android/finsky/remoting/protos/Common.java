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

public final class Common {

   private Common() {}

   public static final class SafeSearchLevel extends MessageMicro {

      public static final int LIGHT = 1;
      public static final int MODERATE = 2;
      public static final int OFF = 0;
      public static final int STRICT = 3;
      private int cachedSize = -1;


      public SafeSearchLevel() {}

      public static Common.SafeSearchLevel parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.SafeSearchLevel()).mergeFrom(var0);
      }

      public static Common.SafeSearchLevel parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.SafeSearchLevel)((Common.SafeSearchLevel)(new Common.SafeSearchLevel()).mergeFrom(var0));
      }

      public final Common.SafeSearchLevel clear() {
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

      public Common.SafeSearchLevel mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class Docid extends MessageMicro {

      public static final int BACKEND_DOCID_FIELD_NUMBER = 1;
      public static final int BACKEND_FIELD_NUMBER = 3;
      public static final int TYPE_FIELD_NUMBER = 2;
      private String backendDocid_ = "";
      private int backend_ = 0;
      private int cachedSize = -1;
      private boolean hasBackend;
      private boolean hasBackendDocid;
      private boolean hasType;
      private int type_ = 1;


      public Docid() {}

      public static Common.Docid parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.Docid()).mergeFrom(var0);
      }

      public static Common.Docid parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.Docid)((Common.Docid)(new Common.Docid()).mergeFrom(var0));
      }

      public final Common.Docid clear() {
         Common.Docid var1 = this.clearBackendDocid();
         Common.Docid var2 = this.clearType();
         Common.Docid var3 = this.clearBackend();
         this.cachedSize = -1;
         return this;
      }

      public Common.Docid clearBackend() {
         this.hasBackend = (boolean)0;
         this.backend_ = 0;
         return this;
      }

      public Common.Docid clearBackendDocid() {
         this.hasBackendDocid = (boolean)0;
         this.backendDocid_ = "";
         return this;
      }

      public Common.Docid clearType() {
         this.hasType = (boolean)0;
         this.type_ = 1;
         return this;
      }

      public int getBackend() {
         return this.backend_;
      }

      public String getBackendDocid() {
         return this.backendDocid_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasBackendDocid()) {
            String var2 = this.getBackendDocid();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasType()) {
            int var4 = this.getType();
            int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
            var1 += var5;
         }

         if(this.hasBackend()) {
            int var6 = this.getBackend();
            int var7 = CodedOutputStreamMicro.computeInt32Size(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getType() {
         return this.type_;
      }

      public boolean hasBackend() {
         return this.hasBackend;
      }

      public boolean hasBackendDocid() {
         return this.hasBackendDocid;
      }

      public boolean hasType() {
         return this.hasType;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasBackendDocid && this.hasType && this.hasBackend) {
            var1 = true;
         }

         return var1;
      }

      public Common.Docid mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setBackendDocid(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setType(var5);
               break;
            case 24:
               int var7 = var1.readInt32();
               this.setBackend(var7);
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

      public Common.Docid setBackend(int var1) {
         this.hasBackend = (boolean)1;
         this.backend_ = var1;
         return this;
      }

      public Common.Docid setBackendDocid(String var1) {
         this.hasBackendDocid = (boolean)1;
         this.backendDocid_ = var1;
         return this;
      }

      public Common.Docid setType(int var1) {
         this.hasType = (boolean)1;
         this.type_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasBackendDocid()) {
            String var2 = this.getBackendDocid();
            var1.writeString(1, var2);
         }

         if(this.hasType()) {
            int var3 = this.getType();
            var1.writeInt32(2, var3);
         }

         if(this.hasBackend()) {
            int var4 = this.getBackend();
            var1.writeInt32(3, var4);
         }
      }
   }

   public static final class Operator extends MessageMicro {

      public static final int ALBUM = 0;
      public static final int ARTIST = 2;
      public static final int COMPOSER = 3;
      public static final int GENRE = 4;
      public static final int LABEL = 5;
      public static final int PRODUCER = 7;
      public static final int SONG = 1;
      public static final int YEAR = 6;
      private int cachedSize = -1;


      public Operator() {}

      public static Common.Operator parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.Operator()).mergeFrom(var0);
      }

      public static Common.Operator parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.Operator)((Common.Operator)(new Common.Operator()).mergeFrom(var0));
      }

      public final Common.Operator clear() {
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

      public Common.Operator mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class PriceRestrict extends MessageMicro {

      public static final int ALL = 0;
      public static final int FREE = 1;
      public static final int PAID = 2;
      private int cachedSize = -1;


      public PriceRestrict() {}

      public static Common.PriceRestrict parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.PriceRestrict()).mergeFrom(var0);
      }

      public static Common.PriceRestrict parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.PriceRestrict)((Common.PriceRestrict)(new Common.PriceRestrict()).mergeFrom(var0));
      }

      public final Common.PriceRestrict clear() {
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

      public Common.PriceRestrict mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class DocumentType extends MessageMicro {

      public static final int ANDROID_APP = 1;
      public static final int ANDROID_DEVELOPER = 8;
      public static final int ANDROID_IN_APP_ITEM = 11;
      public static final int BADGE = 10;
      public static final int CONTAINER = 7;
      public static final int EDITORIAL = 12;
      public static final int MUSIC_ALBUM = 2;
      public static final int MUSIC_ARTIST = 3;
      public static final int MUSIC_SONG = 4;
      public static final int OCEAN_AUTHOR = 9;
      public static final int OCEAN_BOOK = 5;
      public static final int YOUTUBE_MOVIE = 6;
      private int cachedSize = -1;


      public DocumentType() {}

      public static Common.DocumentType parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.DocumentType()).mergeFrom(var0);
      }

      public static Common.DocumentType parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.DocumentType)((Common.DocumentType)(new Common.DocumentType()).mergeFrom(var0));
      }

      public final Common.DocumentType clear() {
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

      public Common.DocumentType mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class OrderBy extends MessageMicro {

      public static final int NEWEST = 4;
      public static final int PRICE_ASCENDING = 3;
      public static final int PRICE_DESCENDING = 2;
      public static final int RELEVANCE = 1;
      public static final int TOP_RATED = 5;
      public static final int TOP_SELLING;
      private int cachedSize = -1;


      public OrderBy() {}

      public static Common.OrderBy parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.OrderBy()).mergeFrom(var0);
      }

      public static Common.OrderBy parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.OrderBy)((Common.OrderBy)(new Common.OrderBy()).mergeFrom(var0));
      }

      public final Common.OrderBy clear() {
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

      public Common.OrderBy mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class Backend extends MessageMicro {

      public static final int ANDROID_APPS = 3;
      public static final int MULTI_CONTAINER = 0;
      public static final int MUSIC = 2;
      public static final int OCEAN = 1;
      public static final int YOUTUBE = 4;
      private int cachedSize = -1;


      public Backend() {}

      public static Common.Backend parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.Backend()).mergeFrom(var0);
      }

      public static Common.Backend parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.Backend)((Common.Backend)(new Common.Backend()).mergeFrom(var0));
      }

      public final Common.Backend clear() {
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

      public Common.Backend mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class ResponseType extends MessageMicro {

      public static final int ALBUM_DETAILS = 4;
      public static final int ANDROID_APP_DETAILS = 3;
      public static final int ARTIST_DETAILS = 5;
      public static final int BOOK_DETAILS = 7;
      public static final int CONTAINER = 9;
      public static final int HOME = 1;
      public static final int MOVIE_DETAILS = 8;
      public static final int SEARCH_RESULTS = 2;
      public static final int SONG_DETAILS = 6;
      private int cachedSize = -1;


      public ResponseType() {}

      public static Common.ResponseType parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.ResponseType()).mergeFrom(var0);
      }

      public static Common.ResponseType parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.ResponseType)((Common.ResponseType)(new Common.ResponseType()).mergeFrom(var0));
      }

      public final Common.ResponseType clear() {
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

      public Common.ResponseType mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class Offer extends MessageMicro {

      public static final int CHECKOUT_FLOW_REQUIRED_FIELD_NUMBER = 5;
      public static final int CONVERTED_PRICE_FIELD_NUMBER = 4;
      public static final int CURRENCY_CODE_FIELD_NUMBER = 2;
      public static final int FORMATTED_AMOUNT_FIELD_NUMBER = 3;
      public static final int FORMATTED_FULL_AMOUNT_FIELD_NUMBER = 7;
      public static final int FULL_PRICE_MICROS_FIELD_NUMBER = 6;
      public static final int MICROS_FIELD_NUMBER = 1;
      public static final int OFFER_TYPE_FIELD_NUMBER = 8;
      public static final int RENTAL_TERMS_FIELD_NUMBER = 9;
      private int cachedSize;
      private boolean checkoutFlowRequired_;
      private List<Common.Offer> convertedPrice_;
      private String currencyCode_ = "";
      private String formattedAmount_ = "";
      private String formattedFullAmount_ = "";
      private long fullPriceMicros_ = 0L;
      private boolean hasCheckoutFlowRequired;
      private boolean hasCurrencyCode;
      private boolean hasFormattedAmount;
      private boolean hasFormattedFullAmount;
      private boolean hasFullPriceMicros;
      private boolean hasMicros;
      private boolean hasOfferType;
      private boolean hasRentalTerms;
      private long micros_ = 0L;
      private int offerType_;
      private Common.RentalTerms rentalTerms_;


      public Offer() {
         List var1 = Collections.emptyList();
         this.convertedPrice_ = var1;
         this.checkoutFlowRequired_ = (boolean)0;
         this.offerType_ = 1;
         this.rentalTerms_ = null;
         this.cachedSize = -1;
      }

      public static Common.Offer parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.Offer()).mergeFrom(var0);
      }

      public static Common.Offer parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.Offer)((Common.Offer)(new Common.Offer()).mergeFrom(var0));
      }

      public Common.Offer addConvertedPrice(Common.Offer var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.convertedPrice_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.convertedPrice_ = var2;
            }

            this.convertedPrice_.add(var1);
            return this;
         }
      }

      public final Common.Offer clear() {
         Common.Offer var1 = this.clearMicros();
         Common.Offer var2 = this.clearCurrencyCode();
         Common.Offer var3 = this.clearFormattedAmount();
         Common.Offer var4 = this.clearFullPriceMicros();
         Common.Offer var5 = this.clearFormattedFullAmount();
         Common.Offer var6 = this.clearConvertedPrice();
         Common.Offer var7 = this.clearCheckoutFlowRequired();
         Common.Offer var8 = this.clearOfferType();
         Common.Offer var9 = this.clearRentalTerms();
         this.cachedSize = -1;
         return this;
      }

      public Common.Offer clearCheckoutFlowRequired() {
         this.hasCheckoutFlowRequired = (boolean)0;
         this.checkoutFlowRequired_ = (boolean)0;
         return this;
      }

      public Common.Offer clearConvertedPrice() {
         List var1 = Collections.emptyList();
         this.convertedPrice_ = var1;
         return this;
      }

      public Common.Offer clearCurrencyCode() {
         this.hasCurrencyCode = (boolean)0;
         this.currencyCode_ = "";
         return this;
      }

      public Common.Offer clearFormattedAmount() {
         this.hasFormattedAmount = (boolean)0;
         this.formattedAmount_ = "";
         return this;
      }

      public Common.Offer clearFormattedFullAmount() {
         this.hasFormattedFullAmount = (boolean)0;
         this.formattedFullAmount_ = "";
         return this;
      }

      public Common.Offer clearFullPriceMicros() {
         this.hasFullPriceMicros = (boolean)0;
         this.fullPriceMicros_ = 0L;
         return this;
      }

      public Common.Offer clearMicros() {
         this.hasMicros = (boolean)0;
         this.micros_ = 0L;
         return this;
      }

      public Common.Offer clearOfferType() {
         this.hasOfferType = (boolean)0;
         this.offerType_ = 1;
         return this;
      }

      public Common.Offer clearRentalTerms() {
         this.hasRentalTerms = (boolean)0;
         this.rentalTerms_ = null;
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

      public Common.Offer getConvertedPrice(int var1) {
         return (Common.Offer)this.convertedPrice_.get(var1);
      }

      public int getConvertedPriceCount() {
         return this.convertedPrice_.size();
      }

      public List<Common.Offer> getConvertedPriceList() {
         return this.convertedPrice_;
      }

      public String getCurrencyCode() {
         return this.currencyCode_;
      }

      public String getFormattedAmount() {
         return this.formattedAmount_;
      }

      public String getFormattedFullAmount() {
         return this.formattedFullAmount_;
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

      public Common.RentalTerms getRentalTerms() {
         return this.rentalTerms_;
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

         int var11;
         for(Iterator var9 = this.getConvertedPriceList().iterator(); var9.hasNext(); var1 += var11) {
            Common.Offer var10 = (Common.Offer)var9.next();
            var11 = CodedOutputStreamMicro.computeMessageSize(4, var10);
         }

         if(this.hasCheckoutFlowRequired()) {
            boolean var12 = this.getCheckoutFlowRequired();
            int var13 = CodedOutputStreamMicro.computeBoolSize(5, var12);
            var1 += var13;
         }

         if(this.hasFullPriceMicros()) {
            long var14 = this.getFullPriceMicros();
            int var16 = CodedOutputStreamMicro.computeInt64Size(6, var14);
            var1 += var16;
         }

         if(this.hasFormattedFullAmount()) {
            String var17 = this.getFormattedFullAmount();
            int var18 = CodedOutputStreamMicro.computeStringSize(7, var17);
            var1 += var18;
         }

         if(this.hasOfferType()) {
            int var19 = this.getOfferType();
            int var20 = CodedOutputStreamMicro.computeInt32Size(8, var19);
            var1 += var20;
         }

         if(this.hasRentalTerms()) {
            Common.RentalTerms var21 = this.getRentalTerms();
            int var22 = CodedOutputStreamMicro.computeMessageSize(9, var21);
            var1 += var22;
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

      public boolean hasFormattedAmount() {
         return this.hasFormattedAmount;
      }

      public boolean hasFormattedFullAmount() {
         return this.hasFormattedFullAmount;
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

      public boolean hasRentalTerms() {
         return this.hasRentalTerms;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasMicros && this.hasCurrencyCode) {
            Iterator var2 = this.getConvertedPriceList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((Common.Offer)var2.next()).isInitialized());
         }

         return var1;
      }

      public Common.Offer mergeFrom(CodedInputStreamMicro var1) throws IOException {
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
            case 34:
               Common.Offer var10 = new Common.Offer();
               var1.readMessage(var10);
               this.addConvertedPrice(var10);
               break;
            case 40:
               boolean var12 = var1.readBool();
               this.setCheckoutFlowRequired(var12);
               break;
            case 48:
               long var14 = var1.readInt64();
               this.setFullPriceMicros(var14);
               break;
            case 58:
               String var17 = var1.readString();
               this.setFormattedFullAmount(var17);
               break;
            case 64:
               int var19 = var1.readInt32();
               this.setOfferType(var19);
               break;
            case 74:
               Common.RentalTerms var21 = new Common.RentalTerms();
               var1.readMessage(var21);
               this.setRentalTerms(var21);
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

      public Common.Offer setCheckoutFlowRequired(boolean var1) {
         this.hasCheckoutFlowRequired = (boolean)1;
         this.checkoutFlowRequired_ = var1;
         return this;
      }

      public Common.Offer setConvertedPrice(int var1, Common.Offer var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.convertedPrice_.set(var1, var2);
            return this;
         }
      }

      public Common.Offer setCurrencyCode(String var1) {
         this.hasCurrencyCode = (boolean)1;
         this.currencyCode_ = var1;
         return this;
      }

      public Common.Offer setFormattedAmount(String var1) {
         this.hasFormattedAmount = (boolean)1;
         this.formattedAmount_ = var1;
         return this;
      }

      public Common.Offer setFormattedFullAmount(String var1) {
         this.hasFormattedFullAmount = (boolean)1;
         this.formattedFullAmount_ = var1;
         return this;
      }

      public Common.Offer setFullPriceMicros(long var1) {
         this.hasFullPriceMicros = (boolean)1;
         this.fullPriceMicros_ = var1;
         return this;
      }

      public Common.Offer setMicros(long var1) {
         this.hasMicros = (boolean)1;
         this.micros_ = var1;
         return this;
      }

      public Common.Offer setOfferType(int var1) {
         this.hasOfferType = (boolean)1;
         this.offerType_ = var1;
         return this;
      }

      public Common.Offer setRentalTerms(Common.RentalTerms var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasRentalTerms = (boolean)1;
            this.rentalTerms_ = var1;
            return this;
         }
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

         Iterator var6 = this.getConvertedPriceList().iterator();

         while(var6.hasNext()) {
            Common.Offer var7 = (Common.Offer)var6.next();
            var1.writeMessage(4, var7);
         }

         if(this.hasCheckoutFlowRequired()) {
            boolean var8 = this.getCheckoutFlowRequired();
            var1.writeBool(5, var8);
         }

         if(this.hasFullPriceMicros()) {
            long var9 = this.getFullPriceMicros();
            var1.writeInt64(6, var9);
         }

         if(this.hasFormattedFullAmount()) {
            String var11 = this.getFormattedFullAmount();
            var1.writeString(7, var11);
         }

         if(this.hasOfferType()) {
            int var12 = this.getOfferType();
            var1.writeInt32(8, var12);
         }

         if(this.hasRentalTerms()) {
            Common.RentalTerms var13 = this.getRentalTerms();
            var1.writeMessage(9, var13);
         }
      }
   }

   public static final class RentalTerms extends MessageMicro {

      public static final int ACTIVATE_PERIOD_SECONDS_FIELD_NUMBER = 2;
      public static final int GRANT_PERIOD_SECONDS_FIELD_NUMBER = 1;
      private int activatePeriodSeconds_ = 0;
      private int cachedSize = -1;
      private int grantPeriodSeconds_ = 0;
      private boolean hasActivatePeriodSeconds;
      private boolean hasGrantPeriodSeconds;


      public RentalTerms() {}

      public static Common.RentalTerms parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.RentalTerms()).mergeFrom(var0);
      }

      public static Common.RentalTerms parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.RentalTerms)((Common.RentalTerms)(new Common.RentalTerms()).mergeFrom(var0));
      }

      public final Common.RentalTerms clear() {
         Common.RentalTerms var1 = this.clearGrantPeriodSeconds();
         Common.RentalTerms var2 = this.clearActivatePeriodSeconds();
         this.cachedSize = -1;
         return this;
      }

      public Common.RentalTerms clearActivatePeriodSeconds() {
         this.hasActivatePeriodSeconds = (boolean)0;
         this.activatePeriodSeconds_ = 0;
         return this;
      }

      public Common.RentalTerms clearGrantPeriodSeconds() {
         this.hasGrantPeriodSeconds = (boolean)0;
         this.grantPeriodSeconds_ = 0;
         return this;
      }

      public int getActivatePeriodSeconds() {
         return this.activatePeriodSeconds_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getGrantPeriodSeconds() {
         return this.grantPeriodSeconds_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasGrantPeriodSeconds()) {
            int var2 = this.getGrantPeriodSeconds();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasActivatePeriodSeconds()) {
            int var4 = this.getActivatePeriodSeconds();
            int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasActivatePeriodSeconds() {
         return this.hasActivatePeriodSeconds;
      }

      public boolean hasGrantPeriodSeconds() {
         return this.hasGrantPeriodSeconds;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Common.RentalTerms mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setGrantPeriodSeconds(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setActivatePeriodSeconds(var5);
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

      public Common.RentalTerms setActivatePeriodSeconds(int var1) {
         this.hasActivatePeriodSeconds = (boolean)1;
         this.activatePeriodSeconds_ = var1;
         return this;
      }

      public Common.RentalTerms setGrantPeriodSeconds(int var1) {
         this.hasGrantPeriodSeconds = (boolean)1;
         this.grantPeriodSeconds_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasGrantPeriodSeconds()) {
            int var2 = this.getGrantPeriodSeconds();
            var1.writeInt32(1, var2);
         }

         if(this.hasActivatePeriodSeconds()) {
            int var3 = this.getActivatePeriodSeconds();
            var1.writeInt32(2, var3);
         }
      }
   }

   public static final class Feature extends MessageMicro {

      public static final int ALSO_INSTALLED = 104;
      public static final int ALSO_INSTALLED_CONFIRMATION = 200;
      public static final int AUTHOR = 302;
      public static final int BANNER = 201;
      public static final int BISAC_SUBJECT = 303;
      public static final int DEVELOPER = 212;
      public static final int EDITORIAL_APPS_EDITORS_CHOICE = 211;
      public static final int EDITORIAL_BOOKS_NYT_BESTSELLERS = 300;
      public static final int FEATURED = 202;
      public static final int FEATURED_APPS = 203;
      public static final int FEATURED_BOOKS = 301;
      public static final int FEATURED_MOVIES = 400;
      public static final int FEATURED_TABLET = 204;
      public static final int MORE_FROM_AUTHOR = 103;
      public static final int MORE_FROM_DEVELOPER = 102;
      public static final int MOVIES_TOPSELLING_PAID = 402;
      public static final int MY_LIBRARY = 501;
      public static final int NEW_ARRIVALS = 106;
      public static final int NEW_RELEASES = 401;
      public static final int ORDER_HISTORY = 500;
      public static final int PERSONALIZED_RECOMMENDATIONS = 213;
      public static final int RELATED = 100;
      public static final int RELATED_APPS = 109;
      public static final int RELATED_MOVIES = 101;
      public static final int SEARCH = 1;
      public static final int SIMILAR_ALBUMS = 107;
      public static final int SIMILAR_BOOKS = 108;
      public static final int SPELLING = 2;
      public static final int TOP_FREE = 205;
      public static final int TOP_GAMES = 208;
      public static final int TOP_GROSSING = 207;
      public static final int TOP_NEW_FREE = 209;
      public static final int TOP_NEW_PAID = 210;
      public static final int TOP_PAID = 206;
      public static final int TRENDING_APPS = 105;
      private int cachedSize = -1;


      public Feature() {}

      public static Common.Feature parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.Feature()).mergeFrom(var0);
      }

      public static Common.Feature parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.Feature)((Common.Feature)(new Common.Feature()).mergeFrom(var0));
      }

      public final Common.Feature clear() {
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

      public Common.Feature mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

   public static final class OfferType extends MessageMicro {

      public static final int GIFT = 5;
      public static final int PURCHASE = 1;
      public static final int RENTAL = 3;
      public static final int RENTAL_HIGH_DEF = 4;
      public static final int SAMPLE = 2;
      private int cachedSize = -1;


      public OfferType() {}

      public static Common.OfferType parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Common.OfferType()).mergeFrom(var0);
      }

      public static Common.OfferType parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Common.OfferType)((Common.OfferType)(new Common.OfferType()).mergeFrom(var0));
      }

      public final Common.OfferType clear() {
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

      public Common.OfferType mergeFrom(CodedInputStreamMicro var1) throws IOException {
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
}
