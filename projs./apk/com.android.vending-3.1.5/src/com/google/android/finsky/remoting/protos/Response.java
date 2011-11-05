package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Browse;
import com.google.android.finsky.remoting.protos.Buy;
import com.google.android.finsky.remoting.protos.BuyInstruments;
import com.google.android.finsky.remoting.protos.DetailsResponse;
import com.google.android.finsky.remoting.protos.DocList;
import com.google.android.finsky.remoting.protos.Log;
import com.google.android.finsky.remoting.protos.PlusOne;
import com.google.android.finsky.remoting.protos.ReviewResponse;
import com.google.android.finsky.remoting.protos.SearchResponse;
import com.google.android.finsky.remoting.protos.Toc;
import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Response {

   private Response() {}

   public static final class Payload extends MessageMicro {

      public static final int BROWSE_RESPONSE_FIELD_NUMBER = 7;
      public static final int BUY_RESPONSE_FIELD_NUMBER = 4;
      public static final int CHECK_INSTRUMENT_RESPONSE_FIELD_NUMBER = 11;
      public static final int DETAILS_RESPONSE_FIELD_NUMBER = 2;
      public static final int LIST_RESPONSE_FIELD_NUMBER = 1;
      public static final int LOG_RESPONSE_FIELD_NUMBER = 10;
      public static final int PLUS_ONE_RESPONSE_FIELD_NUMBER = 12;
      public static final int PURCHASE_STATUS_RESPONSE_FIELD_NUMBER = 8;
      public static final int REVIEW_RESPONSE_FIELD_NUMBER = 3;
      public static final int SEARCH_RESPONSE_FIELD_NUMBER = 5;
      public static final int TOC_RESPONSE_FIELD_NUMBER = 6;
      public static final int UPDATE_INSTRUMENT_RESPONSE_FIELD_NUMBER = 9;
      private Browse.BrowseResponse browseResponse_ = null;
      private Buy.BuyResponse buyResponse_ = null;
      private int cachedSize = -1;
      private BuyInstruments.CheckInstrumentResponse checkInstrumentResponse_ = null;
      private DetailsResponse detailsResponse_ = null;
      private boolean hasBrowseResponse;
      private boolean hasBuyResponse;
      private boolean hasCheckInstrumentResponse;
      private boolean hasDetailsResponse;
      private boolean hasListResponse;
      private boolean hasLogResponse;
      private boolean hasPlusOneResponse;
      private boolean hasPurchaseStatusResponse;
      private boolean hasReviewResponse;
      private boolean hasSearchResponse;
      private boolean hasTocResponse;
      private boolean hasUpdateInstrumentResponse;
      private DocList.ListResponse listResponse_ = null;
      private Log.LogResponse logResponse_ = null;
      private PlusOne.PlusOneResponse plusOneResponse_ = null;
      private Buy.PurchaseStatusResponse purchaseStatusResponse_ = null;
      private ReviewResponse reviewResponse_ = null;
      private SearchResponse searchResponse_ = null;
      private Toc.TocResponse tocResponse_ = null;
      private BuyInstruments.UpdateInstrumentResponse updateInstrumentResponse_ = null;


      public Payload() {}

      public static Response.Payload parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Response.Payload()).mergeFrom(var0);
      }

      public static Response.Payload parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Response.Payload)((Response.Payload)(new Response.Payload()).mergeFrom(var0));
      }

      public final Response.Payload clear() {
         Response.Payload var1 = this.clearListResponse();
         Response.Payload var2 = this.clearDetailsResponse();
         Response.Payload var3 = this.clearReviewResponse();
         Response.Payload var4 = this.clearBuyResponse();
         Response.Payload var5 = this.clearSearchResponse();
         Response.Payload var6 = this.clearTocResponse();
         Response.Payload var7 = this.clearBrowseResponse();
         Response.Payload var8 = this.clearPurchaseStatusResponse();
         Response.Payload var9 = this.clearUpdateInstrumentResponse();
         Response.Payload var10 = this.clearLogResponse();
         Response.Payload var11 = this.clearCheckInstrumentResponse();
         Response.Payload var12 = this.clearPlusOneResponse();
         this.cachedSize = -1;
         return this;
      }

      public Response.Payload clearBrowseResponse() {
         this.hasBrowseResponse = (boolean)0;
         this.browseResponse_ = null;
         return this;
      }

      public Response.Payload clearBuyResponse() {
         this.hasBuyResponse = (boolean)0;
         this.buyResponse_ = null;
         return this;
      }

      public Response.Payload clearCheckInstrumentResponse() {
         this.hasCheckInstrumentResponse = (boolean)0;
         this.checkInstrumentResponse_ = null;
         return this;
      }

      public Response.Payload clearDetailsResponse() {
         this.hasDetailsResponse = (boolean)0;
         this.detailsResponse_ = null;
         return this;
      }

      public Response.Payload clearListResponse() {
         this.hasListResponse = (boolean)0;
         this.listResponse_ = null;
         return this;
      }

      public Response.Payload clearLogResponse() {
         this.hasLogResponse = (boolean)0;
         this.logResponse_ = null;
         return this;
      }

      public Response.Payload clearPlusOneResponse() {
         this.hasPlusOneResponse = (boolean)0;
         this.plusOneResponse_ = null;
         return this;
      }

      public Response.Payload clearPurchaseStatusResponse() {
         this.hasPurchaseStatusResponse = (boolean)0;
         this.purchaseStatusResponse_ = null;
         return this;
      }

      public Response.Payload clearReviewResponse() {
         this.hasReviewResponse = (boolean)0;
         this.reviewResponse_ = null;
         return this;
      }

      public Response.Payload clearSearchResponse() {
         this.hasSearchResponse = (boolean)0;
         this.searchResponse_ = null;
         return this;
      }

      public Response.Payload clearTocResponse() {
         this.hasTocResponse = (boolean)0;
         this.tocResponse_ = null;
         return this;
      }

      public Response.Payload clearUpdateInstrumentResponse() {
         this.hasUpdateInstrumentResponse = (boolean)0;
         this.updateInstrumentResponse_ = null;
         return this;
      }

      public Browse.BrowseResponse getBrowseResponse() {
         return this.browseResponse_;
      }

      public Buy.BuyResponse getBuyResponse() {
         return this.buyResponse_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public BuyInstruments.CheckInstrumentResponse getCheckInstrumentResponse() {
         return this.checkInstrumentResponse_;
      }

      public DetailsResponse getDetailsResponse() {
         return this.detailsResponse_;
      }

      public DocList.ListResponse getListResponse() {
         return this.listResponse_;
      }

      public Log.LogResponse getLogResponse() {
         return this.logResponse_;
      }

      public PlusOne.PlusOneResponse getPlusOneResponse() {
         return this.plusOneResponse_;
      }

      public Buy.PurchaseStatusResponse getPurchaseStatusResponse() {
         return this.purchaseStatusResponse_;
      }

      public ReviewResponse getReviewResponse() {
         return this.reviewResponse_;
      }

      public SearchResponse getSearchResponse() {
         return this.searchResponse_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasListResponse()) {
            DocList.ListResponse var2 = this.getListResponse();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasDetailsResponse()) {
            DetailsResponse var4 = this.getDetailsResponse();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         if(this.hasReviewResponse()) {
            ReviewResponse var6 = this.getReviewResponse();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         if(this.hasBuyResponse()) {
            Buy.BuyResponse var8 = this.getBuyResponse();
            int var9 = CodedOutputStreamMicro.computeMessageSize(4, var8);
            var1 += var9;
         }

         if(this.hasSearchResponse()) {
            SearchResponse var10 = this.getSearchResponse();
            int var11 = CodedOutputStreamMicro.computeMessageSize(5, var10);
            var1 += var11;
         }

         if(this.hasTocResponse()) {
            Toc.TocResponse var12 = this.getTocResponse();
            int var13 = CodedOutputStreamMicro.computeMessageSize(6, var12);
            var1 += var13;
         }

         if(this.hasBrowseResponse()) {
            Browse.BrowseResponse var14 = this.getBrowseResponse();
            int var15 = CodedOutputStreamMicro.computeMessageSize(7, var14);
            var1 += var15;
         }

         if(this.hasPurchaseStatusResponse()) {
            Buy.PurchaseStatusResponse var16 = this.getPurchaseStatusResponse();
            int var17 = CodedOutputStreamMicro.computeMessageSize(8, var16);
            var1 += var17;
         }

         if(this.hasUpdateInstrumentResponse()) {
            BuyInstruments.UpdateInstrumentResponse var18 = this.getUpdateInstrumentResponse();
            int var19 = CodedOutputStreamMicro.computeMessageSize(9, var18);
            var1 += var19;
         }

         if(this.hasLogResponse()) {
            Log.LogResponse var20 = this.getLogResponse();
            int var21 = CodedOutputStreamMicro.computeMessageSize(10, var20);
            var1 += var21;
         }

         if(this.hasCheckInstrumentResponse()) {
            BuyInstruments.CheckInstrumentResponse var22 = this.getCheckInstrumentResponse();
            int var23 = CodedOutputStreamMicro.computeMessageSize(11, var22);
            var1 += var23;
         }

         if(this.hasPlusOneResponse()) {
            PlusOne.PlusOneResponse var24 = this.getPlusOneResponse();
            int var25 = CodedOutputStreamMicro.computeMessageSize(12, var24);
            var1 += var25;
         }

         this.cachedSize = var1;
         return var1;
      }

      public Toc.TocResponse getTocResponse() {
         return this.tocResponse_;
      }

      public BuyInstruments.UpdateInstrumentResponse getUpdateInstrumentResponse() {
         return this.updateInstrumentResponse_;
      }

      public boolean hasBrowseResponse() {
         return this.hasBrowseResponse;
      }

      public boolean hasBuyResponse() {
         return this.hasBuyResponse;
      }

      public boolean hasCheckInstrumentResponse() {
         return this.hasCheckInstrumentResponse;
      }

      public boolean hasDetailsResponse() {
         return this.hasDetailsResponse;
      }

      public boolean hasListResponse() {
         return this.hasListResponse;
      }

      public boolean hasLogResponse() {
         return this.hasLogResponse;
      }

      public boolean hasPlusOneResponse() {
         return this.hasPlusOneResponse;
      }

      public boolean hasPurchaseStatusResponse() {
         return this.hasPurchaseStatusResponse;
      }

      public boolean hasReviewResponse() {
         return this.hasReviewResponse;
      }

      public boolean hasSearchResponse() {
         return this.hasSearchResponse;
      }

      public boolean hasTocResponse() {
         return this.hasTocResponse;
      }

      public boolean hasUpdateInstrumentResponse() {
         return this.hasUpdateInstrumentResponse;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if((!this.hasListResponse() || this.getListResponse().isInitialized()) && (!this.hasDetailsResponse() || this.getDetailsResponse().isInitialized()) && (!this.hasReviewResponse() || this.getReviewResponse().isInitialized()) && (!this.hasBuyResponse() || this.getBuyResponse().isInitialized()) && (!this.hasSearchResponse() || this.getSearchResponse().isInitialized()) && (!this.hasTocResponse() || this.getTocResponse().isInitialized()) && (!this.hasBrowseResponse() || this.getBrowseResponse().isInitialized()) && (!this.hasPurchaseStatusResponse() || this.getPurchaseStatusResponse().isInitialized()) && (!this.hasUpdateInstrumentResponse() || this.getUpdateInstrumentResponse().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public Response.Payload mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               DocList.ListResponse var3 = new DocList.ListResponse();
               var1.readMessage(var3);
               this.setListResponse(var3);
               break;
            case 18:
               DetailsResponse var5 = new DetailsResponse();
               var1.readMessage(var5);
               this.setDetailsResponse(var5);
               break;
            case 26:
               ReviewResponse var7 = new ReviewResponse();
               var1.readMessage(var7);
               this.setReviewResponse(var7);
               break;
            case 34:
               Buy.BuyResponse var9 = new Buy.BuyResponse();
               var1.readMessage(var9);
               this.setBuyResponse(var9);
               break;
            case 42:
               SearchResponse var11 = new SearchResponse();
               var1.readMessage(var11);
               this.setSearchResponse(var11);
               break;
            case 50:
               Toc.TocResponse var13 = new Toc.TocResponse();
               var1.readMessage(var13);
               this.setTocResponse(var13);
               break;
            case 58:
               Browse.BrowseResponse var15 = new Browse.BrowseResponse();
               var1.readMessage(var15);
               this.setBrowseResponse(var15);
               break;
            case 66:
               Buy.PurchaseStatusResponse var17 = new Buy.PurchaseStatusResponse();
               var1.readMessage(var17);
               this.setPurchaseStatusResponse(var17);
               break;
            case 74:
               BuyInstruments.UpdateInstrumentResponse var19 = new BuyInstruments.UpdateInstrumentResponse();
               var1.readMessage(var19);
               this.setUpdateInstrumentResponse(var19);
               break;
            case 82:
               Log.LogResponse var21 = new Log.LogResponse();
               var1.readMessage(var21);
               this.setLogResponse(var21);
               break;
            case 90:
               BuyInstruments.CheckInstrumentResponse var23 = new BuyInstruments.CheckInstrumentResponse();
               var1.readMessage(var23);
               this.setCheckInstrumentResponse(var23);
               break;
            case 98:
               PlusOne.PlusOneResponse var25 = new PlusOne.PlusOneResponse();
               var1.readMessage(var25);
               this.setPlusOneResponse(var25);
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

      public Response.Payload setBrowseResponse(Browse.BrowseResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasBrowseResponse = (boolean)1;
            this.browseResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setBuyResponse(Buy.BuyResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasBuyResponse = (boolean)1;
            this.buyResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setCheckInstrumentResponse(BuyInstruments.CheckInstrumentResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasCheckInstrumentResponse = (boolean)1;
            this.checkInstrumentResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setDetailsResponse(DetailsResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDetailsResponse = (boolean)1;
            this.detailsResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setListResponse(DocList.ListResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasListResponse = (boolean)1;
            this.listResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setLogResponse(Log.LogResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasLogResponse = (boolean)1;
            this.logResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setPlusOneResponse(PlusOne.PlusOneResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPlusOneResponse = (boolean)1;
            this.plusOneResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setPurchaseStatusResponse(Buy.PurchaseStatusResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPurchaseStatusResponse = (boolean)1;
            this.purchaseStatusResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setReviewResponse(ReviewResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasReviewResponse = (boolean)1;
            this.reviewResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setSearchResponse(SearchResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasSearchResponse = (boolean)1;
            this.searchResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setTocResponse(Toc.TocResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasTocResponse = (boolean)1;
            this.tocResponse_ = var1;
            return this;
         }
      }

      public Response.Payload setUpdateInstrumentResponse(BuyInstruments.UpdateInstrumentResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasUpdateInstrumentResponse = (boolean)1;
            this.updateInstrumentResponse_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasListResponse()) {
            DocList.ListResponse var2 = this.getListResponse();
            var1.writeMessage(1, var2);
         }

         if(this.hasDetailsResponse()) {
            DetailsResponse var3 = this.getDetailsResponse();
            var1.writeMessage(2, var3);
         }

         if(this.hasReviewResponse()) {
            ReviewResponse var4 = this.getReviewResponse();
            var1.writeMessage(3, var4);
         }

         if(this.hasBuyResponse()) {
            Buy.BuyResponse var5 = this.getBuyResponse();
            var1.writeMessage(4, var5);
         }

         if(this.hasSearchResponse()) {
            SearchResponse var6 = this.getSearchResponse();
            var1.writeMessage(5, var6);
         }

         if(this.hasTocResponse()) {
            Toc.TocResponse var7 = this.getTocResponse();
            var1.writeMessage(6, var7);
         }

         if(this.hasBrowseResponse()) {
            Browse.BrowseResponse var8 = this.getBrowseResponse();
            var1.writeMessage(7, var8);
         }

         if(this.hasPurchaseStatusResponse()) {
            Buy.PurchaseStatusResponse var9 = this.getPurchaseStatusResponse();
            var1.writeMessage(8, var9);
         }

         if(this.hasUpdateInstrumentResponse()) {
            BuyInstruments.UpdateInstrumentResponse var10 = this.getUpdateInstrumentResponse();
            var1.writeMessage(9, var10);
         }

         if(this.hasLogResponse()) {
            Log.LogResponse var11 = this.getLogResponse();
            var1.writeMessage(10, var11);
         }

         if(this.hasCheckInstrumentResponse()) {
            BuyInstruments.CheckInstrumentResponse var12 = this.getCheckInstrumentResponse();
            var1.writeMessage(11, var12);
         }

         if(this.hasPlusOneResponse()) {
            PlusOne.PlusOneResponse var13 = this.getPlusOneResponse();
            var1.writeMessage(12, var13);
         }
      }
   }

   public static final class PreFetch extends MessageMicro {

      public static final int ETAG_FIELD_NUMBER = 3;
      public static final int RESPONSE_FIELD_NUMBER = 2;
      public static final int SOFT_TTL_FIELD_NUMBER = 5;
      public static final int TTL_FIELD_NUMBER = 4;
      public static final int URL_FIELD_NUMBER = 1;
      private int cachedSize;
      private String etag_;
      private boolean hasEtag;
      private boolean hasResponse;
      private boolean hasSoftTtl;
      private boolean hasTtl;
      private boolean hasUrl;
      private ByteStringMicro response_;
      private long softTtl_;
      private long ttl_;
      private String url_ = "";


      public PreFetch() {
         ByteStringMicro var1 = ByteStringMicro.EMPTY;
         this.response_ = var1;
         this.etag_ = "";
         this.ttl_ = 0L;
         this.softTtl_ = 0L;
         this.cachedSize = -1;
      }

      public static Response.PreFetch parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Response.PreFetch()).mergeFrom(var0);
      }

      public static Response.PreFetch parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Response.PreFetch)((Response.PreFetch)(new Response.PreFetch()).mergeFrom(var0));
      }

      public final Response.PreFetch clear() {
         Response.PreFetch var1 = this.clearUrl();
         Response.PreFetch var2 = this.clearResponse();
         Response.PreFetch var3 = this.clearEtag();
         Response.PreFetch var4 = this.clearTtl();
         Response.PreFetch var5 = this.clearSoftTtl();
         this.cachedSize = -1;
         return this;
      }

      public Response.PreFetch clearEtag() {
         this.hasEtag = (boolean)0;
         this.etag_ = "";
         return this;
      }

      public Response.PreFetch clearResponse() {
         this.hasResponse = (boolean)0;
         ByteStringMicro var1 = ByteStringMicro.EMPTY;
         this.response_ = var1;
         return this;
      }

      public Response.PreFetch clearSoftTtl() {
         this.hasSoftTtl = (boolean)0;
         this.softTtl_ = 0L;
         return this;
      }

      public Response.PreFetch clearTtl() {
         this.hasTtl = (boolean)0;
         this.ttl_ = 0L;
         return this;
      }

      public Response.PreFetch clearUrl() {
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

      public String getEtag() {
         return this.etag_;
      }

      public ByteStringMicro getResponse() {
         return this.response_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasUrl()) {
            String var2 = this.getUrl();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasResponse()) {
            ByteStringMicro var4 = this.getResponse();
            int var5 = CodedOutputStreamMicro.computeBytesSize(2, var4);
            var1 += var5;
         }

         if(this.hasEtag()) {
            String var6 = this.getEtag();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasTtl()) {
            long var8 = this.getTtl();
            int var10 = CodedOutputStreamMicro.computeInt64Size(4, var8);
            var1 += var10;
         }

         if(this.hasSoftTtl()) {
            long var11 = this.getSoftTtl();
            int var13 = CodedOutputStreamMicro.computeInt64Size(5, var11);
            var1 += var13;
         }

         this.cachedSize = var1;
         return var1;
      }

      public long getSoftTtl() {
         return this.softTtl_;
      }

      public long getTtl() {
         return this.ttl_;
      }

      public String getUrl() {
         return this.url_;
      }

      public boolean hasEtag() {
         return this.hasEtag;
      }

      public boolean hasResponse() {
         return this.hasResponse;
      }

      public boolean hasSoftTtl() {
         return this.hasSoftTtl;
      }

      public boolean hasTtl() {
         return this.hasTtl;
      }

      public boolean hasUrl() {
         return this.hasUrl;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Response.PreFetch mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setUrl(var3);
               break;
            case 18:
               ByteStringMicro var5 = var1.readBytes();
               this.setResponse(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setEtag(var7);
               break;
            case 32:
               long var9 = var1.readInt64();
               this.setTtl(var9);
               break;
            case 40:
               long var12 = var1.readInt64();
               this.setSoftTtl(var12);
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

      public Response.PreFetch setEtag(String var1) {
         this.hasEtag = (boolean)1;
         this.etag_ = var1;
         return this;
      }

      public Response.PreFetch setResponse(ByteStringMicro var1) {
         this.hasResponse = (boolean)1;
         this.response_ = var1;
         return this;
      }

      public Response.PreFetch setSoftTtl(long var1) {
         this.hasSoftTtl = (boolean)1;
         this.softTtl_ = var1;
         return this;
      }

      public Response.PreFetch setTtl(long var1) {
         this.hasTtl = (boolean)1;
         this.ttl_ = var1;
         return this;
      }

      public Response.PreFetch setUrl(String var1) {
         this.hasUrl = (boolean)1;
         this.url_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasUrl()) {
            String var2 = this.getUrl();
            var1.writeString(1, var2);
         }

         if(this.hasResponse()) {
            ByteStringMicro var3 = this.getResponse();
            var1.writeBytes(2, var3);
         }

         if(this.hasEtag()) {
            String var4 = this.getEtag();
            var1.writeString(3, var4);
         }

         if(this.hasTtl()) {
            long var5 = this.getTtl();
            var1.writeInt64(4, var5);
         }

         if(this.hasSoftTtl()) {
            long var7 = this.getSoftTtl();
            var1.writeInt64(5, var7);
         }
      }
   }

   public static final class ServerCommands extends MessageMicro {

      public static final int CLEAR_CACHE_FIELD_NUMBER = 1;
      public static final int DISPLAY_ERROR_MESSAGE_FIELD_NUMBER = 2;
      public static final int LOG_ERROR_STACKTRACE_FIELD_NUMBER = 3;
      private int cachedSize = -1;
      private boolean clearCache_ = 0;
      private String displayErrorMessage_ = "";
      private boolean hasClearCache;
      private boolean hasDisplayErrorMessage;
      private boolean hasLogErrorStacktrace;
      private String logErrorStacktrace_ = "";


      public ServerCommands() {}

      public static Response.ServerCommands parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Response.ServerCommands()).mergeFrom(var0);
      }

      public static Response.ServerCommands parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Response.ServerCommands)((Response.ServerCommands)(new Response.ServerCommands()).mergeFrom(var0));
      }

      public final Response.ServerCommands clear() {
         Response.ServerCommands var1 = this.clearClearCache();
         Response.ServerCommands var2 = this.clearDisplayErrorMessage();
         Response.ServerCommands var3 = this.clearLogErrorStacktrace();
         this.cachedSize = -1;
         return this;
      }

      public Response.ServerCommands clearClearCache() {
         this.hasClearCache = (boolean)0;
         this.clearCache_ = (boolean)0;
         return this;
      }

      public Response.ServerCommands clearDisplayErrorMessage() {
         this.hasDisplayErrorMessage = (boolean)0;
         this.displayErrorMessage_ = "";
         return this;
      }

      public Response.ServerCommands clearLogErrorStacktrace() {
         this.hasLogErrorStacktrace = (boolean)0;
         this.logErrorStacktrace_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public boolean getClearCache() {
         return this.clearCache_;
      }

      public String getDisplayErrorMessage() {
         return this.displayErrorMessage_;
      }

      public String getLogErrorStacktrace() {
         return this.logErrorStacktrace_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasClearCache()) {
            boolean var2 = this.getClearCache();
            int var3 = CodedOutputStreamMicro.computeBoolSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasDisplayErrorMessage()) {
            String var4 = this.getDisplayErrorMessage();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasLogErrorStacktrace()) {
            String var6 = this.getLogErrorStacktrace();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasClearCache() {
         return this.hasClearCache;
      }

      public boolean hasDisplayErrorMessage() {
         return this.hasDisplayErrorMessage;
      }

      public boolean hasLogErrorStacktrace() {
         return this.hasLogErrorStacktrace;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Response.ServerCommands mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               boolean var3 = var1.readBool();
               this.setClearCache(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setDisplayErrorMessage(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setLogErrorStacktrace(var7);
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

      public Response.ServerCommands setClearCache(boolean var1) {
         this.hasClearCache = (boolean)1;
         this.clearCache_ = var1;
         return this;
      }

      public Response.ServerCommands setDisplayErrorMessage(String var1) {
         this.hasDisplayErrorMessage = (boolean)1;
         this.displayErrorMessage_ = var1;
         return this;
      }

      public Response.ServerCommands setLogErrorStacktrace(String var1) {
         this.hasLogErrorStacktrace = (boolean)1;
         this.logErrorStacktrace_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasClearCache()) {
            boolean var2 = this.getClearCache();
            var1.writeBool(1, var2);
         }

         if(this.hasDisplayErrorMessage()) {
            String var3 = this.getDisplayErrorMessage();
            var1.writeString(2, var3);
         }

         if(this.hasLogErrorStacktrace()) {
            String var4 = this.getLogErrorStacktrace();
            var1.writeString(3, var4);
         }
      }
   }

   public static final class ResponseWrapper extends MessageMicro {

      public static final int COMMANDS_FIELD_NUMBER = 2;
      public static final int PAYLOAD_FIELD_NUMBER = 1;
      public static final int PRE_FETCH_FIELD_NUMBER = 3;
      private int cachedSize;
      private Response.ServerCommands commands_ = null;
      private boolean hasCommands;
      private boolean hasPayload;
      private Response.Payload payload_ = null;
      private List<Response.PreFetch> preFetch_;


      public ResponseWrapper() {
         List var1 = Collections.emptyList();
         this.preFetch_ = var1;
         this.cachedSize = -1;
      }

      public static Response.ResponseWrapper parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Response.ResponseWrapper()).mergeFrom(var0);
      }

      public static Response.ResponseWrapper parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Response.ResponseWrapper)((Response.ResponseWrapper)(new Response.ResponseWrapper()).mergeFrom(var0));
      }

      public Response.ResponseWrapper addPreFetch(Response.PreFetch var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.preFetch_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.preFetch_ = var2;
            }

            this.preFetch_.add(var1);
            return this;
         }
      }

      public final Response.ResponseWrapper clear() {
         Response.ResponseWrapper var1 = this.clearPayload();
         Response.ResponseWrapper var2 = this.clearCommands();
         Response.ResponseWrapper var3 = this.clearPreFetch();
         this.cachedSize = -1;
         return this;
      }

      public Response.ResponseWrapper clearCommands() {
         this.hasCommands = (boolean)0;
         this.commands_ = null;
         return this;
      }

      public Response.ResponseWrapper clearPayload() {
         this.hasPayload = (boolean)0;
         this.payload_ = null;
         return this;
      }

      public Response.ResponseWrapper clearPreFetch() {
         List var1 = Collections.emptyList();
         this.preFetch_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Response.ServerCommands getCommands() {
         return this.commands_;
      }

      public Response.Payload getPayload() {
         return this.payload_;
      }

      public Response.PreFetch getPreFetch(int var1) {
         return (Response.PreFetch)this.preFetch_.get(var1);
      }

      public int getPreFetchCount() {
         return this.preFetch_.size();
      }

      public List<Response.PreFetch> getPreFetchList() {
         return this.preFetch_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasPayload()) {
            Response.Payload var2 = this.getPayload();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasCommands()) {
            Response.ServerCommands var4 = this.getCommands();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         int var8;
         for(Iterator var6 = this.getPreFetchList().iterator(); var6.hasNext(); var1 += var8) {
            Response.PreFetch var7 = (Response.PreFetch)var6.next();
            var8 = CodedOutputStreamMicro.computeMessageSize(3, var7);
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasCommands() {
         return this.hasCommands;
      }

      public boolean hasPayload() {
         return this.hasPayload;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(this.hasPayload() && !this.getPayload().isInitialized()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Response.ResponseWrapper mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Response.Payload var3 = new Response.Payload();
               var1.readMessage(var3);
               this.setPayload(var3);
               break;
            case 18:
               Response.ServerCommands var5 = new Response.ServerCommands();
               var1.readMessage(var5);
               this.setCommands(var5);
               break;
            case 26:
               Response.PreFetch var7 = new Response.PreFetch();
               var1.readMessage(var7);
               this.addPreFetch(var7);
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

      public Response.ResponseWrapper setCommands(Response.ServerCommands var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasCommands = (boolean)1;
            this.commands_ = var1;
            return this;
         }
      }

      public Response.ResponseWrapper setPayload(Response.Payload var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPayload = (boolean)1;
            this.payload_ = var1;
            return this;
         }
      }

      public Response.ResponseWrapper setPreFetch(int var1, Response.PreFetch var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.preFetch_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasPayload()) {
            Response.Payload var2 = this.getPayload();
            var1.writeMessage(1, var2);
         }

         if(this.hasCommands()) {
            Response.ServerCommands var3 = this.getCommands();
            var1.writeMessage(2, var3);
         }

         Iterator var4 = this.getPreFetchList().iterator();

         while(var4.hasNext()) {
            Response.PreFetch var5 = (Response.PreFetch)var4.next();
            var1.writeMessage(3, var5);
         }

      }
   }
}
