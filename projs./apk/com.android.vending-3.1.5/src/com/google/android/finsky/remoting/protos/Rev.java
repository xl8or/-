package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Common;
import com.google.android.finsky.remoting.protos.Rating;
import com.google.android.finsky.remoting.protos.RequestContext;
import com.google.android.finsky.remoting.protos.UserLocale;
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

public final class Rev {

   private Rev() {}

   public static final class ReviewRequest extends MessageMicro {

      public static final int CONTEXT_FIELD_NUMBER = 1;
      public static final int DELETE_REQUEST_FIELD_NUMBER = 6;
      public static final int DOCID_FIELD_NUMBER = 2;
      public static final int GET_REQUEST_FIELD_NUMBER = 3;
      public static final int RATE_REQUEST_FIELD_NUMBER = 4;
      public static final int REVIEW_FIELD_NUMBER = 5;
      private int cachedSize = -1;
      private RequestContext context_ = null;
      private Rev.DeleteReviewRequest deleteRequest_ = null;
      private Common.Docid docid_ = null;
      private Rev.GetReviewsRequest getRequest_ = null;
      private boolean hasContext;
      private boolean hasDeleteRequest;
      private boolean hasDocid;
      private boolean hasGetRequest;
      private boolean hasRateRequest;
      private boolean hasReview;
      private Rev.RateReviewRequest rateRequest_ = null;
      private Rev.Review review_ = null;


      public ReviewRequest() {}

      public static Rev.ReviewRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Rev.ReviewRequest()).mergeFrom(var0);
      }

      public static Rev.ReviewRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Rev.ReviewRequest)((Rev.ReviewRequest)(new Rev.ReviewRequest()).mergeFrom(var0));
      }

      public final Rev.ReviewRequest clear() {
         Rev.ReviewRequest var1 = this.clearContext();
         Rev.ReviewRequest var2 = this.clearDocid();
         Rev.ReviewRequest var3 = this.clearGetRequest();
         Rev.ReviewRequest var4 = this.clearRateRequest();
         Rev.ReviewRequest var5 = this.clearReview();
         Rev.ReviewRequest var6 = this.clearDeleteRequest();
         this.cachedSize = -1;
         return this;
      }

      public Rev.ReviewRequest clearContext() {
         this.hasContext = (boolean)0;
         this.context_ = null;
         return this;
      }

      public Rev.ReviewRequest clearDeleteRequest() {
         this.hasDeleteRequest = (boolean)0;
         this.deleteRequest_ = null;
         return this;
      }

      public Rev.ReviewRequest clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = null;
         return this;
      }

      public Rev.ReviewRequest clearGetRequest() {
         this.hasGetRequest = (boolean)0;
         this.getRequest_ = null;
         return this;
      }

      public Rev.ReviewRequest clearRateRequest() {
         this.hasRateRequest = (boolean)0;
         this.rateRequest_ = null;
         return this;
      }

      public Rev.ReviewRequest clearReview() {
         this.hasReview = (boolean)0;
         this.review_ = null;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public RequestContext getContext() {
         return this.context_;
      }

      public Rev.DeleteReviewRequest getDeleteRequest() {
         return this.deleteRequest_;
      }

      public Common.Docid getDocid() {
         return this.docid_;
      }

      public Rev.GetReviewsRequest getGetRequest() {
         return this.getRequest_;
      }

      public Rev.RateReviewRequest getRateRequest() {
         return this.rateRequest_;
      }

      public Rev.Review getReview() {
         return this.review_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasDocid()) {
            Common.Docid var4 = this.getDocid();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         if(this.hasGetRequest()) {
            Rev.GetReviewsRequest var6 = this.getGetRequest();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         if(this.hasRateRequest()) {
            Rev.RateReviewRequest var8 = this.getRateRequest();
            int var9 = CodedOutputStreamMicro.computeMessageSize(4, var8);
            var1 += var9;
         }

         if(this.hasReview()) {
            Rev.Review var10 = this.getReview();
            int var11 = CodedOutputStreamMicro.computeMessageSize(5, var10);
            var1 += var11;
         }

         if(this.hasDeleteRequest()) {
            Rev.DeleteReviewRequest var12 = this.getDeleteRequest();
            int var13 = CodedOutputStreamMicro.computeMessageSize(6, var12);
            var1 += var13;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasContext() {
         return this.hasContext;
      }

      public boolean hasDeleteRequest() {
         return this.hasDeleteRequest;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public boolean hasGetRequest() {
         return this.hasGetRequest;
      }

      public boolean hasRateRequest() {
         return this.hasRateRequest;
      }

      public boolean hasReview() {
         return this.hasReview;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasContext && this.hasDocid && this.getContext().isInitialized() && this.getDocid().isInitialized() && (!this.hasRateRequest() || this.getRateRequest().isInitialized()) && (!this.hasReview() || this.getReview().isInitialized()) && (!this.hasDeleteRequest() || this.getDeleteRequest().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public Rev.ReviewRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               RequestContext var3 = new RequestContext();
               var1.readMessage(var3);
               this.setContext(var3);
               break;
            case 18:
               Common.Docid var5 = new Common.Docid();
               var1.readMessage(var5);
               this.setDocid(var5);
               break;
            case 26:
               Rev.GetReviewsRequest var7 = new Rev.GetReviewsRequest();
               var1.readMessage(var7);
               this.setGetRequest(var7);
               break;
            case 34:
               Rev.RateReviewRequest var9 = new Rev.RateReviewRequest();
               var1.readMessage(var9);
               this.setRateRequest(var9);
               break;
            case 42:
               Rev.Review var11 = new Rev.Review();
               var1.readMessage(var11);
               this.setReview(var11);
               break;
            case 50:
               Rev.DeleteReviewRequest var13 = new Rev.DeleteReviewRequest();
               var1.readMessage(var13);
               this.setDeleteRequest(var13);
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

      public Rev.ReviewRequest setContext(RequestContext var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasContext = (boolean)1;
            this.context_ = var1;
            return this;
         }
      }

      public Rev.ReviewRequest setDeleteRequest(Rev.DeleteReviewRequest var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDeleteRequest = (boolean)1;
            this.deleteRequest_ = var1;
            return this;
         }
      }

      public Rev.ReviewRequest setDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDocid = (boolean)1;
            this.docid_ = var1;
            return this;
         }
      }

      public Rev.ReviewRequest setGetRequest(Rev.GetReviewsRequest var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasGetRequest = (boolean)1;
            this.getRequest_ = var1;
            return this;
         }
      }

      public Rev.ReviewRequest setRateRequest(Rev.RateReviewRequest var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasRateRequest = (boolean)1;
            this.rateRequest_ = var1;
            return this;
         }
      }

      public Rev.ReviewRequest setReview(Rev.Review var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasReview = (boolean)1;
            this.review_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            var1.writeMessage(1, var2);
         }

         if(this.hasDocid()) {
            Common.Docid var3 = this.getDocid();
            var1.writeMessage(2, var3);
         }

         if(this.hasGetRequest()) {
            Rev.GetReviewsRequest var4 = this.getGetRequest();
            var1.writeMessage(3, var4);
         }

         if(this.hasRateRequest()) {
            Rev.RateReviewRequest var5 = this.getRateRequest();
            var1.writeMessage(4, var5);
         }

         if(this.hasReview()) {
            Rev.Review var6 = this.getReview();
            var1.writeMessage(5, var6);
         }

         if(this.hasDeleteRequest()) {
            Rev.DeleteReviewRequest var7 = this.getDeleteRequest();
            var1.writeMessage(6, var7);
         }
      }
   }

   public static final class GetReviewsRequest extends MessageMicro {

      public static final int ALL = 0;
      public static final int AUTHOR_TYPE_FILTER_FIELD_NUMBER = 6;
      public static final int EDITORIAL_ONLY = 2;
      public static final int EDIT_DATE = 0;
      public static final int HELPFULNESS = 2;
      public static final int NUM_FIELD_NUMBER = 1;
      public static final int OFFSET_FIELD_NUMBER = 2;
      public static final int RATING = 1;
      public static final int REVIEWER_GAIA_ID_FIELD_NUMBER = 7;
      public static final int SORT_DESCENDING_FIELD_NUMBER = 4;
      public static final int SORT_ORDER_FIELD_NUMBER = 3;
      public static final int SPECIFIC_USERS_ONLY = 3;
      public static final int USER_ONLY = 1;
      private int authorTypeFilter_ = 0;
      private int cachedSize;
      private boolean hasAuthorTypeFilter;
      private boolean hasNum;
      private boolean hasOffset;
      private boolean hasSortDescending;
      private boolean hasSortOrder;
      private int num_ = 10;
      private int offset_ = 0;
      private List<Long> reviewerGaiaId_;
      private boolean sortDescending_ = 1;
      private int sortOrder_ = 0;


      public GetReviewsRequest() {
         List var1 = Collections.emptyList();
         this.reviewerGaiaId_ = var1;
         this.cachedSize = -1;
      }

      public static Rev.GetReviewsRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Rev.GetReviewsRequest()).mergeFrom(var0);
      }

      public static Rev.GetReviewsRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Rev.GetReviewsRequest)((Rev.GetReviewsRequest)(new Rev.GetReviewsRequest()).mergeFrom(var0));
      }

      public Rev.GetReviewsRequest addReviewerGaiaId(long var1) {
         if(this.reviewerGaiaId_.isEmpty()) {
            ArrayList var3 = new ArrayList();
            this.reviewerGaiaId_ = var3;
         }

         List var4 = this.reviewerGaiaId_;
         Long var5 = Long.valueOf(var1);
         var4.add(var5);
         return this;
      }

      public final Rev.GetReviewsRequest clear() {
         Rev.GetReviewsRequest var1 = this.clearNum();
         Rev.GetReviewsRequest var2 = this.clearOffset();
         Rev.GetReviewsRequest var3 = this.clearSortOrder();
         Rev.GetReviewsRequest var4 = this.clearSortDescending();
         Rev.GetReviewsRequest var5 = this.clearAuthorTypeFilter();
         Rev.GetReviewsRequest var6 = this.clearReviewerGaiaId();
         this.cachedSize = -1;
         return this;
      }

      public Rev.GetReviewsRequest clearAuthorTypeFilter() {
         this.hasAuthorTypeFilter = (boolean)0;
         this.authorTypeFilter_ = 0;
         return this;
      }

      public Rev.GetReviewsRequest clearNum() {
         this.hasNum = (boolean)0;
         this.num_ = 10;
         return this;
      }

      public Rev.GetReviewsRequest clearOffset() {
         this.hasOffset = (boolean)0;
         this.offset_ = 0;
         return this;
      }

      public Rev.GetReviewsRequest clearReviewerGaiaId() {
         List var1 = Collections.emptyList();
         this.reviewerGaiaId_ = var1;
         return this;
      }

      public Rev.GetReviewsRequest clearSortDescending() {
         this.hasSortDescending = (boolean)0;
         this.sortDescending_ = (boolean)1;
         return this;
      }

      public Rev.GetReviewsRequest clearSortOrder() {
         this.hasSortOrder = (boolean)0;
         this.sortOrder_ = 0;
         return this;
      }

      public int getAuthorTypeFilter() {
         return this.authorTypeFilter_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getNum() {
         return this.num_;
      }

      public int getOffset() {
         return this.offset_;
      }

      public long getReviewerGaiaId(int var1) {
         return ((Long)this.reviewerGaiaId_.get(var1)).longValue();
      }

      public int getReviewerGaiaIdCount() {
         return this.reviewerGaiaId_.size();
      }

      public List<Long> getReviewerGaiaIdList() {
         return this.reviewerGaiaId_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasNum()) {
            int var2 = this.getNum();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasOffset()) {
            int var4 = this.getOffset();
            int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
            var1 += var5;
         }

         if(this.hasSortOrder()) {
            int var6 = this.getSortOrder();
            int var7 = CodedOutputStreamMicro.computeInt32Size(3, var6);
            var1 += var7;
         }

         if(this.hasSortDescending()) {
            boolean var8 = this.getSortDescending();
            int var9 = CodedOutputStreamMicro.computeBoolSize(4, var8);
            var1 += var9;
         }

         if(this.hasAuthorTypeFilter()) {
            int var10 = this.getAuthorTypeFilter();
            int var11 = CodedOutputStreamMicro.computeInt32Size(6, var10);
            var1 += var11;
         }

         int var12 = this.getReviewerGaiaIdList().size() * 8;
         int var13 = var1 + var12;
         int var14 = this.getReviewerGaiaIdList().size() * 1;
         int var15 = var13 + var14;
         this.cachedSize = var15;
         return var15;
      }

      public boolean getSortDescending() {
         return this.sortDescending_;
      }

      public int getSortOrder() {
         return this.sortOrder_;
      }

      public boolean hasAuthorTypeFilter() {
         return this.hasAuthorTypeFilter;
      }

      public boolean hasNum() {
         return this.hasNum;
      }

      public boolean hasOffset() {
         return this.hasOffset;
      }

      public boolean hasSortDescending() {
         return this.hasSortDescending;
      }

      public boolean hasSortOrder() {
         return this.hasSortOrder;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Rev.GetReviewsRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setNum(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setOffset(var5);
               break;
            case 24:
               int var7 = var1.readInt32();
               this.setSortOrder(var7);
               break;
            case 32:
               boolean var9 = var1.readBool();
               this.setSortDescending(var9);
               break;
            case 48:
               int var11 = var1.readInt32();
               this.setAuthorTypeFilter(var11);
               break;
            case 57:
               long var13 = var1.readFixed64();
               this.addReviewerGaiaId(var13);
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

      public Rev.GetReviewsRequest setAuthorTypeFilter(int var1) {
         this.hasAuthorTypeFilter = (boolean)1;
         this.authorTypeFilter_ = var1;
         return this;
      }

      public Rev.GetReviewsRequest setNum(int var1) {
         this.hasNum = (boolean)1;
         this.num_ = var1;
         return this;
      }

      public Rev.GetReviewsRequest setOffset(int var1) {
         this.hasOffset = (boolean)1;
         this.offset_ = var1;
         return this;
      }

      public Rev.GetReviewsRequest setReviewerGaiaId(int var1, long var2) {
         List var4 = this.reviewerGaiaId_;
         Long var5 = Long.valueOf(var2);
         var4.set(var1, var5);
         return this;
      }

      public Rev.GetReviewsRequest setSortDescending(boolean var1) {
         this.hasSortDescending = (boolean)1;
         this.sortDescending_ = var1;
         return this;
      }

      public Rev.GetReviewsRequest setSortOrder(int var1) {
         this.hasSortOrder = (boolean)1;
         this.sortOrder_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasNum()) {
            int var2 = this.getNum();
            var1.writeInt32(1, var2);
         }

         if(this.hasOffset()) {
            int var3 = this.getOffset();
            var1.writeInt32(2, var3);
         }

         if(this.hasSortOrder()) {
            int var4 = this.getSortOrder();
            var1.writeInt32(3, var4);
         }

         if(this.hasSortDescending()) {
            boolean var5 = this.getSortDescending();
            var1.writeBool(4, var5);
         }

         if(this.hasAuthorTypeFilter()) {
            int var6 = this.getAuthorTypeFilter();
            var1.writeInt32(6, var6);
         }

         Iterator var7 = this.getReviewerGaiaIdList().iterator();

         while(var7.hasNext()) {
            long var8 = ((Long)var7.next()).longValue();
            var1.writeFixed64(7, var8);
         }

      }
   }

   public static final class GetReviewsResponse extends MessageMicro {

      public static final int MATCHING_COUNT_FIELD_NUMBER = 2;
      public static final int REVIEW_FIELD_NUMBER = 1;
      private int cachedSize;
      private boolean hasMatchingCount;
      private long matchingCount_;
      private List<Rev.Review> review_;


      public GetReviewsResponse() {
         List var1 = Collections.emptyList();
         this.review_ = var1;
         this.matchingCount_ = 0L;
         this.cachedSize = -1;
      }

      public static Rev.GetReviewsResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Rev.GetReviewsResponse()).mergeFrom(var0);
      }

      public static Rev.GetReviewsResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Rev.GetReviewsResponse)((Rev.GetReviewsResponse)(new Rev.GetReviewsResponse()).mergeFrom(var0));
      }

      public Rev.GetReviewsResponse addReview(Rev.Review var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.review_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.review_ = var2;
            }

            this.review_.add(var1);
            return this;
         }
      }

      public final Rev.GetReviewsResponse clear() {
         Rev.GetReviewsResponse var1 = this.clearReview();
         Rev.GetReviewsResponse var2 = this.clearMatchingCount();
         this.cachedSize = -1;
         return this;
      }

      public Rev.GetReviewsResponse clearMatchingCount() {
         this.hasMatchingCount = (boolean)0;
         this.matchingCount_ = 0L;
         return this;
      }

      public Rev.GetReviewsResponse clearReview() {
         List var1 = Collections.emptyList();
         this.review_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public long getMatchingCount() {
         return this.matchingCount_;
      }

      public Rev.Review getReview(int var1) {
         return (Rev.Review)this.review_.get(var1);
      }

      public int getReviewCount() {
         return this.review_.size();
      }

      public List<Rev.Review> getReviewList() {
         return this.review_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getReviewList().iterator(); var2.hasNext(); var1 += var4) {
            Rev.Review var3 = (Rev.Review)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(1, var3);
         }

         if(this.hasMatchingCount()) {
            long var5 = this.getMatchingCount();
            int var7 = CodedOutputStreamMicro.computeInt64Size(2, var5);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasMatchingCount() {
         return this.hasMatchingCount;
      }

      public final boolean isInitialized() {
         Iterator var1 = this.getReviewList().iterator();

         boolean var2;
         while(true) {
            if(var1.hasNext()) {
               if(((Rev.Review)var1.next()).isInitialized()) {
                  continue;
               }

               var2 = false;
               break;
            }

            var2 = true;
            break;
         }

         return var2;
      }

      public Rev.GetReviewsResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Rev.Review var3 = new Rev.Review();
               var1.readMessage(var3);
               this.addReview(var3);
               break;
            case 16:
               long var5 = var1.readInt64();
               this.setMatchingCount(var5);
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

      public Rev.GetReviewsResponse setMatchingCount(long var1) {
         this.hasMatchingCount = (boolean)1;
         this.matchingCount_ = var1;
         return this;
      }

      public Rev.GetReviewsResponse setReview(int var1, Rev.Review var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.review_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getReviewList().iterator();

         while(var2.hasNext()) {
            Rev.Review var3 = (Rev.Review)var2.next();
            var1.writeMessage(1, var3);
         }

         if(this.hasMatchingCount()) {
            long var4 = this.getMatchingCount();
            var1.writeInt64(2, var4);
         }
      }
   }

   public static final class Review extends MessageMicro {

      public static final int ANDROID_ID_FIELD_NUMBER = 20;
      public static final int AUTHOR_GAIA_ID_FIELD_NUMBER = 13;
      public static final int AUTHOR_NAME_FIELD_NUMBER = 1;
      public static final int AUTHOR_TYPE_FIELD_NUMBER = 14;
      public static final int COMMENT_FIELD_NUMBER = 8;
      public static final int COMMENT_ID_FIELD_NUMBER = 9;
      public static final int COMMENT_RATING_FIELD_NUMBER = 10;
      public static final int CON_FIELD_NUMBER = 12;
      public static final int DEVICE_NAME_FIELD_NUMBER = 19;
      public static final int DOCID_FIELD_NUMBER = 17;
      public static final int DOCUMENT_INTERNAL_VERSION_FIELD_NUMBER = 25;
      public static final int DOCUMENT_VERSION_FIELD_NUMBER = 4;
      public static final int EDITORIAL = 1;
      public static final int FRONTEND_SOURCE_FIELD_NUMBER = 24;
      public static final int HEADER_ORDER_FIELD_NUMBER = 22;
      public static final int IMAGE_URL_FIELD_NUMBER = 15;
      public static final int LOCALE_FIELD_NUMBER = 18;
      public static final int PRO_FIELD_NUMBER = 11;
      public static final int SOURCE_FIELD_NUMBER = 3;
      public static final int SOURCE_URL_FIELD_NUMBER = 16;
      public static final int STAR_RATING_FIELD_NUMBER = 6;
      public static final int TIMESTAMP_MSEC_FIELD_NUMBER = 5;
      public static final int TITLE_FIELD_NUMBER = 7;
      public static final int URL_FIELD_NUMBER = 2;
      public static final int USER = 0;
      public static final int USER_AGENT_FIELD_NUMBER = 23;
      public static final int USER_IP_FIELD_NUMBER = 21;
      private long androidId_;
      private long authorGaiaId_ = 0L;
      private String authorName_ = "";
      private int authorType_ = 0;
      private int cachedSize;
      private String commentId_ = "";
      private Rating.AggregateRating commentRating_ = null;
      private String comment_ = "";
      private List<String> con_;
      private String deviceName_;
      private Common.Docid docid_ = null;
      private int documentInternalVersion_ = 0;
      private String documentVersion_ = "";
      private int frontendSource_;
      private boolean hasAndroidId;
      private boolean hasAuthorGaiaId;
      private boolean hasAuthorName;
      private boolean hasAuthorType;
      private boolean hasComment;
      private boolean hasCommentId;
      private boolean hasCommentRating;
      private boolean hasDeviceName;
      private boolean hasDocid;
      private boolean hasDocumentInternalVersion;
      private boolean hasDocumentVersion;
      private boolean hasFrontendSource;
      private boolean hasHeaderOrder;
      private boolean hasImageUrl;
      private boolean hasLocale;
      private boolean hasSource;
      private boolean hasSourceUrl;
      private boolean hasStarRating;
      private boolean hasTimestampMsec;
      private boolean hasTitle;
      private boolean hasUrl;
      private boolean hasUserAgent;
      private boolean hasUserIp;
      private String headerOrder_;
      private String imageUrl_ = "";
      private UserLocale locale_;
      private List<String> pro_;
      private String sourceUrl_ = "";
      private String source_ = "";
      private int starRating_ = 0;
      private long timestampMsec_ = 0L;
      private String title_ = "";
      private String url_ = "";
      private String userAgent_;
      private ByteStringMicro userIp_;


      public Review() {
         List var1 = Collections.emptyList();
         this.pro_ = var1;
         List var2 = Collections.emptyList();
         this.con_ = var2;
         this.locale_ = null;
         this.deviceName_ = "";
         this.androidId_ = 0L;
         ByteStringMicro var3 = ByteStringMicro.EMPTY;
         this.userIp_ = var3;
         this.headerOrder_ = "";
         this.userAgent_ = "";
         this.frontendSource_ = 0;
         this.cachedSize = -1;
      }

      public static Rev.Review parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Rev.Review()).mergeFrom(var0);
      }

      public static Rev.Review parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Rev.Review)((Rev.Review)(new Rev.Review()).mergeFrom(var0));
      }

      public Rev.Review addCon(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.con_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.con_ = var2;
            }

            this.con_.add(var1);
            return this;
         }
      }

      public Rev.Review addPro(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.pro_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.pro_ = var2;
            }

            this.pro_.add(var1);
            return this;
         }
      }

      public final Rev.Review clear() {
         Rev.Review var1 = this.clearDocid();
         Rev.Review var2 = this.clearAuthorName();
         Rev.Review var3 = this.clearAuthorGaiaId();
         Rev.Review var4 = this.clearAuthorType();
         Rev.Review var5 = this.clearUrl();
         Rev.Review var6 = this.clearSource();
         Rev.Review var7 = this.clearSourceUrl();
         Rev.Review var8 = this.clearImageUrl();
         Rev.Review var9 = this.clearDocumentVersion();
         Rev.Review var10 = this.clearDocumentInternalVersion();
         Rev.Review var11 = this.clearTimestampMsec();
         Rev.Review var12 = this.clearStarRating();
         Rev.Review var13 = this.clearTitle();
         Rev.Review var14 = this.clearComment();
         Rev.Review var15 = this.clearCommentId();
         Rev.Review var16 = this.clearCommentRating();
         Rev.Review var17 = this.clearPro();
         Rev.Review var18 = this.clearCon();
         Rev.Review var19 = this.clearLocale();
         Rev.Review var20 = this.clearDeviceName();
         Rev.Review var21 = this.clearAndroidId();
         Rev.Review var22 = this.clearUserIp();
         Rev.Review var23 = this.clearHeaderOrder();
         Rev.Review var24 = this.clearUserAgent();
         Rev.Review var25 = this.clearFrontendSource();
         this.cachedSize = -1;
         return this;
      }

      public Rev.Review clearAndroidId() {
         this.hasAndroidId = (boolean)0;
         this.androidId_ = 0L;
         return this;
      }

      public Rev.Review clearAuthorGaiaId() {
         this.hasAuthorGaiaId = (boolean)0;
         this.authorGaiaId_ = 0L;
         return this;
      }

      public Rev.Review clearAuthorName() {
         this.hasAuthorName = (boolean)0;
         this.authorName_ = "";
         return this;
      }

      public Rev.Review clearAuthorType() {
         this.hasAuthorType = (boolean)0;
         this.authorType_ = 0;
         return this;
      }

      public Rev.Review clearComment() {
         this.hasComment = (boolean)0;
         this.comment_ = "";
         return this;
      }

      public Rev.Review clearCommentId() {
         this.hasCommentId = (boolean)0;
         this.commentId_ = "";
         return this;
      }

      public Rev.Review clearCommentRating() {
         this.hasCommentRating = (boolean)0;
         this.commentRating_ = null;
         return this;
      }

      public Rev.Review clearCon() {
         List var1 = Collections.emptyList();
         this.con_ = var1;
         return this;
      }

      public Rev.Review clearDeviceName() {
         this.hasDeviceName = (boolean)0;
         this.deviceName_ = "";
         return this;
      }

      public Rev.Review clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = null;
         return this;
      }

      public Rev.Review clearDocumentInternalVersion() {
         this.hasDocumentInternalVersion = (boolean)0;
         this.documentInternalVersion_ = 0;
         return this;
      }

      public Rev.Review clearDocumentVersion() {
         this.hasDocumentVersion = (boolean)0;
         this.documentVersion_ = "";
         return this;
      }

      public Rev.Review clearFrontendSource() {
         this.hasFrontendSource = (boolean)0;
         this.frontendSource_ = 0;
         return this;
      }

      public Rev.Review clearHeaderOrder() {
         this.hasHeaderOrder = (boolean)0;
         this.headerOrder_ = "";
         return this;
      }

      public Rev.Review clearImageUrl() {
         this.hasImageUrl = (boolean)0;
         this.imageUrl_ = "";
         return this;
      }

      public Rev.Review clearLocale() {
         this.hasLocale = (boolean)0;
         this.locale_ = null;
         return this;
      }

      public Rev.Review clearPro() {
         List var1 = Collections.emptyList();
         this.pro_ = var1;
         return this;
      }

      public Rev.Review clearSource() {
         this.hasSource = (boolean)0;
         this.source_ = "";
         return this;
      }

      public Rev.Review clearSourceUrl() {
         this.hasSourceUrl = (boolean)0;
         this.sourceUrl_ = "";
         return this;
      }

      public Rev.Review clearStarRating() {
         this.hasStarRating = (boolean)0;
         this.starRating_ = 0;
         return this;
      }

      public Rev.Review clearTimestampMsec() {
         this.hasTimestampMsec = (boolean)0;
         this.timestampMsec_ = 0L;
         return this;
      }

      public Rev.Review clearTitle() {
         this.hasTitle = (boolean)0;
         this.title_ = "";
         return this;
      }

      public Rev.Review clearUrl() {
         this.hasUrl = (boolean)0;
         this.url_ = "";
         return this;
      }

      public Rev.Review clearUserAgent() {
         this.hasUserAgent = (boolean)0;
         this.userAgent_ = "";
         return this;
      }

      public Rev.Review clearUserIp() {
         this.hasUserIp = (boolean)0;
         ByteStringMicro var1 = ByteStringMicro.EMPTY;
         this.userIp_ = var1;
         return this;
      }

      public long getAndroidId() {
         return this.androidId_;
      }

      public long getAuthorGaiaId() {
         return this.authorGaiaId_;
      }

      public String getAuthorName() {
         return this.authorName_;
      }

      public int getAuthorType() {
         return this.authorType_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getComment() {
         return this.comment_;
      }

      public String getCommentId() {
         return this.commentId_;
      }

      public Rating.AggregateRating getCommentRating() {
         return this.commentRating_;
      }

      public String getCon(int var1) {
         return (String)this.con_.get(var1);
      }

      public int getConCount() {
         return this.con_.size();
      }

      public List<String> getConList() {
         return this.con_;
      }

      public String getDeviceName() {
         return this.deviceName_;
      }

      public Common.Docid getDocid() {
         return this.docid_;
      }

      public int getDocumentInternalVersion() {
         return this.documentInternalVersion_;
      }

      public String getDocumentVersion() {
         return this.documentVersion_;
      }

      public int getFrontendSource() {
         return this.frontendSource_;
      }

      public String getHeaderOrder() {
         return this.headerOrder_;
      }

      public String getImageUrl() {
         return this.imageUrl_;
      }

      public UserLocale getLocale() {
         return this.locale_;
      }

      public String getPro(int var1) {
         return (String)this.pro_.get(var1);
      }

      public int getProCount() {
         return this.pro_.size();
      }

      public List<String> getProList() {
         return this.pro_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasAuthorName()) {
            String var2 = this.getAuthorName();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasUrl()) {
            String var4 = this.getUrl();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasSource()) {
            String var6 = this.getSource();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasDocumentVersion()) {
            String var8 = this.getDocumentVersion();
            int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
            var1 += var9;
         }

         if(this.hasTimestampMsec()) {
            long var10 = this.getTimestampMsec();
            int var12 = CodedOutputStreamMicro.computeInt64Size(5, var10);
            var1 += var12;
         }

         if(this.hasStarRating()) {
            int var13 = this.getStarRating();
            int var14 = CodedOutputStreamMicro.computeInt32Size(6, var13);
            var1 += var14;
         }

         if(this.hasTitle()) {
            String var15 = this.getTitle();
            int var16 = CodedOutputStreamMicro.computeStringSize(7, var15);
            var1 += var16;
         }

         if(this.hasComment()) {
            String var17 = this.getComment();
            int var18 = CodedOutputStreamMicro.computeStringSize(8, var17);
            var1 += var18;
         }

         if(this.hasCommentId()) {
            String var19 = this.getCommentId();
            int var20 = CodedOutputStreamMicro.computeStringSize(9, var19);
            var1 += var20;
         }

         if(this.hasCommentRating()) {
            Rating.AggregateRating var21 = this.getCommentRating();
            int var22 = CodedOutputStreamMicro.computeMessageSize(10, var21);
            var1 += var22;
         }

         int var23 = 0;

         int var25;
         for(Iterator var24 = this.getProList().iterator(); var24.hasNext(); var23 += var25) {
            var25 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var24.next());
         }

         int var26 = var1 + var23;
         int var27 = this.getProList().size() * 1;
         int var28 = var26 + var27;
         int var29 = 0;

         int var31;
         for(Iterator var30 = this.getConList().iterator(); var30.hasNext(); var29 += var31) {
            var31 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var30.next());
         }

         int var32 = var28 + var29;
         int var33 = this.getConList().size() * 1;
         int var34 = var32 + var33;
         if(this.hasAuthorGaiaId()) {
            long var35 = this.getAuthorGaiaId();
            int var37 = CodedOutputStreamMicro.computeFixed64Size(13, var35);
            var34 += var37;
         }

         if(this.hasAuthorType()) {
            int var38 = this.getAuthorType();
            int var39 = CodedOutputStreamMicro.computeInt32Size(14, var38);
            var34 += var39;
         }

         if(this.hasImageUrl()) {
            String var40 = this.getImageUrl();
            int var41 = CodedOutputStreamMicro.computeStringSize(15, var40);
            var34 += var41;
         }

         if(this.hasSourceUrl()) {
            String var42 = this.getSourceUrl();
            int var43 = CodedOutputStreamMicro.computeStringSize(16, var42);
            var34 += var43;
         }

         if(this.hasDocid()) {
            Common.Docid var44 = this.getDocid();
            int var45 = CodedOutputStreamMicro.computeMessageSize(17, var44);
            var34 += var45;
         }

         if(this.hasLocale()) {
            UserLocale var46 = this.getLocale();
            int var47 = CodedOutputStreamMicro.computeMessageSize(18, var46);
            var34 += var47;
         }

         if(this.hasDeviceName()) {
            String var48 = this.getDeviceName();
            int var49 = CodedOutputStreamMicro.computeStringSize(19, var48);
            var34 += var49;
         }

         if(this.hasAndroidId()) {
            long var50 = this.getAndroidId();
            int var52 = CodedOutputStreamMicro.computeInt64Size(20, var50);
            var34 += var52;
         }

         if(this.hasUserIp()) {
            ByteStringMicro var53 = this.getUserIp();
            int var54 = CodedOutputStreamMicro.computeBytesSize(21, var53);
            var34 += var54;
         }

         if(this.hasHeaderOrder()) {
            String var55 = this.getHeaderOrder();
            int var56 = CodedOutputStreamMicro.computeStringSize(22, var55);
            var34 += var56;
         }

         if(this.hasUserAgent()) {
            String var57 = this.getUserAgent();
            int var58 = CodedOutputStreamMicro.computeStringSize(23, var57);
            var34 += var58;
         }

         if(this.hasFrontendSource()) {
            int var59 = this.getFrontendSource();
            int var60 = CodedOutputStreamMicro.computeInt32Size(24, var59);
            var34 += var60;
         }

         if(this.hasDocumentInternalVersion()) {
            int var61 = this.getDocumentInternalVersion();
            int var62 = CodedOutputStreamMicro.computeInt32Size(25, var61);
            var34 += var62;
         }

         this.cachedSize = var34;
         return var34;
      }

      public String getSource() {
         return this.source_;
      }

      public String getSourceUrl() {
         return this.sourceUrl_;
      }

      public int getStarRating() {
         return this.starRating_;
      }

      public long getTimestampMsec() {
         return this.timestampMsec_;
      }

      public String getTitle() {
         return this.title_;
      }

      public String getUrl() {
         return this.url_;
      }

      public String getUserAgent() {
         return this.userAgent_;
      }

      public ByteStringMicro getUserIp() {
         return this.userIp_;
      }

      public boolean hasAndroidId() {
         return this.hasAndroidId;
      }

      public boolean hasAuthorGaiaId() {
         return this.hasAuthorGaiaId;
      }

      public boolean hasAuthorName() {
         return this.hasAuthorName;
      }

      public boolean hasAuthorType() {
         return this.hasAuthorType;
      }

      public boolean hasComment() {
         return this.hasComment;
      }

      public boolean hasCommentId() {
         return this.hasCommentId;
      }

      public boolean hasCommentRating() {
         return this.hasCommentRating;
      }

      public boolean hasDeviceName() {
         return this.hasDeviceName;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public boolean hasDocumentInternalVersion() {
         return this.hasDocumentInternalVersion;
      }

      public boolean hasDocumentVersion() {
         return this.hasDocumentVersion;
      }

      public boolean hasFrontendSource() {
         return this.hasFrontendSource;
      }

      public boolean hasHeaderOrder() {
         return this.hasHeaderOrder;
      }

      public boolean hasImageUrl() {
         return this.hasImageUrl;
      }

      public boolean hasLocale() {
         return this.hasLocale;
      }

      public boolean hasSource() {
         return this.hasSource;
      }

      public boolean hasSourceUrl() {
         return this.hasSourceUrl;
      }

      public boolean hasStarRating() {
         return this.hasStarRating;
      }

      public boolean hasTimestampMsec() {
         return this.hasTimestampMsec;
      }

      public boolean hasTitle() {
         return this.hasTitle;
      }

      public boolean hasUrl() {
         return this.hasUrl;
      }

      public boolean hasUserAgent() {
         return this.hasUserAgent;
      }

      public boolean hasUserIp() {
         return this.hasUserIp;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if((!this.hasDocid() || this.getDocid().isInitialized()) && (!this.hasCommentRating() || this.getCommentRating().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public Rev.Review mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setAuthorName(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setUrl(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setSource(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setDocumentVersion(var9);
               break;
            case 40:
               long var11 = var1.readInt64();
               this.setTimestampMsec(var11);
               break;
            case 48:
               int var14 = var1.readInt32();
               this.setStarRating(var14);
               break;
            case 58:
               String var16 = var1.readString();
               this.setTitle(var16);
               break;
            case 66:
               String var18 = var1.readString();
               this.setComment(var18);
               break;
            case 74:
               String var20 = var1.readString();
               this.setCommentId(var20);
               break;
            case 82:
               Rating.AggregateRating var22 = new Rating.AggregateRating();
               var1.readMessage(var22);
               this.setCommentRating(var22);
               break;
            case 90:
               String var24 = var1.readString();
               this.addPro(var24);
               break;
            case 98:
               String var26 = var1.readString();
               this.addCon(var26);
               break;
            case 105:
               long var28 = var1.readFixed64();
               this.setAuthorGaiaId(var28);
               break;
            case 112:
               int var31 = var1.readInt32();
               this.setAuthorType(var31);
               break;
            case 122:
               String var33 = var1.readString();
               this.setImageUrl(var33);
               break;
            case 130:
               String var35 = var1.readString();
               this.setSourceUrl(var35);
               break;
            case 138:
               Common.Docid var37 = new Common.Docid();
               var1.readMessage(var37);
               this.setDocid(var37);
               break;
            case 146:
               UserLocale var39 = new UserLocale();
               var1.readMessage(var39);
               this.setLocale(var39);
               break;
            case 154:
               String var41 = var1.readString();
               this.setDeviceName(var41);
               break;
            case 160:
               long var43 = var1.readInt64();
               this.setAndroidId(var43);
               break;
            case 170:
               ByteStringMicro var46 = var1.readBytes();
               this.setUserIp(var46);
               break;
            case 178:
               String var48 = var1.readString();
               this.setHeaderOrder(var48);
               break;
            case 186:
               String var50 = var1.readString();
               this.setUserAgent(var50);
               break;
            case 192:
               int var52 = var1.readInt32();
               this.setFrontendSource(var52);
               break;
            case 200:
               int var54 = var1.readInt32();
               this.setDocumentInternalVersion(var54);
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

      public Rev.Review setAndroidId(long var1) {
         this.hasAndroidId = (boolean)1;
         this.androidId_ = var1;
         return this;
      }

      public Rev.Review setAuthorGaiaId(long var1) {
         this.hasAuthorGaiaId = (boolean)1;
         this.authorGaiaId_ = var1;
         return this;
      }

      public Rev.Review setAuthorName(String var1) {
         this.hasAuthorName = (boolean)1;
         this.authorName_ = var1;
         return this;
      }

      public Rev.Review setAuthorType(int var1) {
         this.hasAuthorType = (boolean)1;
         this.authorType_ = var1;
         return this;
      }

      public Rev.Review setComment(String var1) {
         this.hasComment = (boolean)1;
         this.comment_ = var1;
         return this;
      }

      public Rev.Review setCommentId(String var1) {
         this.hasCommentId = (boolean)1;
         this.commentId_ = var1;
         return this;
      }

      public Rev.Review setCommentRating(Rating.AggregateRating var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasCommentRating = (boolean)1;
            this.commentRating_ = var1;
            return this;
         }
      }

      public Rev.Review setCon(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.con_.set(var1, var2);
            return this;
         }
      }

      public Rev.Review setDeviceName(String var1) {
         this.hasDeviceName = (boolean)1;
         this.deviceName_ = var1;
         return this;
      }

      public Rev.Review setDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDocid = (boolean)1;
            this.docid_ = var1;
            return this;
         }
      }

      public Rev.Review setDocumentInternalVersion(int var1) {
         this.hasDocumentInternalVersion = (boolean)1;
         this.documentInternalVersion_ = var1;
         return this;
      }

      public Rev.Review setDocumentVersion(String var1) {
         this.hasDocumentVersion = (boolean)1;
         this.documentVersion_ = var1;
         return this;
      }

      public Rev.Review setFrontendSource(int var1) {
         this.hasFrontendSource = (boolean)1;
         this.frontendSource_ = var1;
         return this;
      }

      public Rev.Review setHeaderOrder(String var1) {
         this.hasHeaderOrder = (boolean)1;
         this.headerOrder_ = var1;
         return this;
      }

      public Rev.Review setImageUrl(String var1) {
         this.hasImageUrl = (boolean)1;
         this.imageUrl_ = var1;
         return this;
      }

      public Rev.Review setLocale(UserLocale var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasLocale = (boolean)1;
            this.locale_ = var1;
            return this;
         }
      }

      public Rev.Review setPro(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.pro_.set(var1, var2);
            return this;
         }
      }

      public Rev.Review setSource(String var1) {
         this.hasSource = (boolean)1;
         this.source_ = var1;
         return this;
      }

      public Rev.Review setSourceUrl(String var1) {
         this.hasSourceUrl = (boolean)1;
         this.sourceUrl_ = var1;
         return this;
      }

      public Rev.Review setStarRating(int var1) {
         this.hasStarRating = (boolean)1;
         this.starRating_ = var1;
         return this;
      }

      public Rev.Review setTimestampMsec(long var1) {
         this.hasTimestampMsec = (boolean)1;
         this.timestampMsec_ = var1;
         return this;
      }

      public Rev.Review setTitle(String var1) {
         this.hasTitle = (boolean)1;
         this.title_ = var1;
         return this;
      }

      public Rev.Review setUrl(String var1) {
         this.hasUrl = (boolean)1;
         this.url_ = var1;
         return this;
      }

      public Rev.Review setUserAgent(String var1) {
         this.hasUserAgent = (boolean)1;
         this.userAgent_ = var1;
         return this;
      }

      public Rev.Review setUserIp(ByteStringMicro var1) {
         this.hasUserIp = (boolean)1;
         this.userIp_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasAuthorName()) {
            String var2 = this.getAuthorName();
            var1.writeString(1, var2);
         }

         if(this.hasUrl()) {
            String var3 = this.getUrl();
            var1.writeString(2, var3);
         }

         if(this.hasSource()) {
            String var4 = this.getSource();
            var1.writeString(3, var4);
         }

         if(this.hasDocumentVersion()) {
            String var5 = this.getDocumentVersion();
            var1.writeString(4, var5);
         }

         if(this.hasTimestampMsec()) {
            long var6 = this.getTimestampMsec();
            var1.writeInt64(5, var6);
         }

         if(this.hasStarRating()) {
            int var8 = this.getStarRating();
            var1.writeInt32(6, var8);
         }

         if(this.hasTitle()) {
            String var9 = this.getTitle();
            var1.writeString(7, var9);
         }

         if(this.hasComment()) {
            String var10 = this.getComment();
            var1.writeString(8, var10);
         }

         if(this.hasCommentId()) {
            String var11 = this.getCommentId();
            var1.writeString(9, var11);
         }

         if(this.hasCommentRating()) {
            Rating.AggregateRating var12 = this.getCommentRating();
            var1.writeMessage(10, var12);
         }

         Iterator var13 = this.getProList().iterator();

         while(var13.hasNext()) {
            String var14 = (String)var13.next();
            var1.writeString(11, var14);
         }

         Iterator var15 = this.getConList().iterator();

         while(var15.hasNext()) {
            String var16 = (String)var15.next();
            var1.writeString(12, var16);
         }

         if(this.hasAuthorGaiaId()) {
            long var17 = this.getAuthorGaiaId();
            var1.writeFixed64(13, var17);
         }

         if(this.hasAuthorType()) {
            int var19 = this.getAuthorType();
            var1.writeInt32(14, var19);
         }

         if(this.hasImageUrl()) {
            String var20 = this.getImageUrl();
            var1.writeString(15, var20);
         }

         if(this.hasSourceUrl()) {
            String var21 = this.getSourceUrl();
            var1.writeString(16, var21);
         }

         if(this.hasDocid()) {
            Common.Docid var22 = this.getDocid();
            var1.writeMessage(17, var22);
         }

         if(this.hasLocale()) {
            UserLocale var23 = this.getLocale();
            var1.writeMessage(18, var23);
         }

         if(this.hasDeviceName()) {
            String var24 = this.getDeviceName();
            var1.writeString(19, var24);
         }

         if(this.hasAndroidId()) {
            long var25 = this.getAndroidId();
            var1.writeInt64(20, var25);
         }

         if(this.hasUserIp()) {
            ByteStringMicro var27 = this.getUserIp();
            var1.writeBytes(21, var27);
         }

         if(this.hasHeaderOrder()) {
            String var28 = this.getHeaderOrder();
            var1.writeString(22, var28);
         }

         if(this.hasUserAgent()) {
            String var29 = this.getUserAgent();
            var1.writeString(23, var29);
         }

         if(this.hasFrontendSource()) {
            int var30 = this.getFrontendSource();
            var1.writeInt32(24, var30);
         }

         if(this.hasDocumentInternalVersion()) {
            int var31 = this.getDocumentInternalVersion();
            var1.writeInt32(25, var31);
         }
      }
   }

   public static final class RateReviewRequest extends MessageMicro {

      public static final int HELPFUL = 1;
      public static final int ID_FIELD_NUMBER = 1;
      public static final int RATING_FIELD_NUMBER = 2;
      public static final int SPAM = 3;
      public static final int UNHELPFUL = 2;
      private int cachedSize = -1;
      private boolean hasId;
      private boolean hasRating;
      private String id_ = "";
      private int rating_ = 1;


      public RateReviewRequest() {}

      public static Rev.RateReviewRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Rev.RateReviewRequest()).mergeFrom(var0);
      }

      public static Rev.RateReviewRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Rev.RateReviewRequest)((Rev.RateReviewRequest)(new Rev.RateReviewRequest()).mergeFrom(var0));
      }

      public final Rev.RateReviewRequest clear() {
         Rev.RateReviewRequest var1 = this.clearId();
         Rev.RateReviewRequest var2 = this.clearRating();
         this.cachedSize = -1;
         return this;
      }

      public Rev.RateReviewRequest clearId() {
         this.hasId = (boolean)0;
         this.id_ = "";
         return this;
      }

      public Rev.RateReviewRequest clearRating() {
         this.hasRating = (boolean)0;
         this.rating_ = 1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getId() {
         return this.id_;
      }

      public int getRating() {
         return this.rating_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasId()) {
            String var2 = this.getId();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasRating()) {
            int var4 = this.getRating();
            int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasId() {
         return this.hasId;
      }

      public boolean hasRating() {
         return this.hasRating;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasId) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Rev.RateReviewRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setId(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setRating(var5);
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

      public Rev.RateReviewRequest setId(String var1) {
         this.hasId = (boolean)1;
         this.id_ = var1;
         return this;
      }

      public Rev.RateReviewRequest setRating(int var1) {
         this.hasRating = (boolean)1;
         this.rating_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasId()) {
            String var2 = this.getId();
            var1.writeString(1, var2);
         }

         if(this.hasRating()) {
            int var3 = this.getRating();
            var1.writeInt32(2, var3);
         }
      }
   }

   public static final class DeleteReviewRequest extends MessageMicro {

      public static final int ID_FIELD_NUMBER = 1;
      public static final int IS_INTERNAL_ADMINISTRATOR_FIELD_NUMBER = 2;
      private int cachedSize = -1;
      private boolean hasId;
      private boolean hasIsInternalAdministrator;
      private String id_ = "";
      private boolean isInternalAdministrator_ = 0;


      public DeleteReviewRequest() {}

      public static Rev.DeleteReviewRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Rev.DeleteReviewRequest()).mergeFrom(var0);
      }

      public static Rev.DeleteReviewRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Rev.DeleteReviewRequest)((Rev.DeleteReviewRequest)(new Rev.DeleteReviewRequest()).mergeFrom(var0));
      }

      public final Rev.DeleteReviewRequest clear() {
         Rev.DeleteReviewRequest var1 = this.clearId();
         Rev.DeleteReviewRequest var2 = this.clearIsInternalAdministrator();
         this.cachedSize = -1;
         return this;
      }

      public Rev.DeleteReviewRequest clearId() {
         this.hasId = (boolean)0;
         this.id_ = "";
         return this;
      }

      public Rev.DeleteReviewRequest clearIsInternalAdministrator() {
         this.hasIsInternalAdministrator = (boolean)0;
         this.isInternalAdministrator_ = (boolean)0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getId() {
         return this.id_;
      }

      public boolean getIsInternalAdministrator() {
         return this.isInternalAdministrator_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasId()) {
            String var2 = this.getId();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasIsInternalAdministrator()) {
            boolean var4 = this.getIsInternalAdministrator();
            int var5 = CodedOutputStreamMicro.computeBoolSize(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasId() {
         return this.hasId;
      }

      public boolean hasIsInternalAdministrator() {
         return this.hasIsInternalAdministrator;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasId) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Rev.DeleteReviewRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setId(var3);
               break;
            case 16:
               boolean var5 = var1.readBool();
               this.setIsInternalAdministrator(var5);
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

      public Rev.DeleteReviewRequest setId(String var1) {
         this.hasId = (boolean)1;
         this.id_ = var1;
         return this;
      }

      public Rev.DeleteReviewRequest setIsInternalAdministrator(boolean var1) {
         this.hasIsInternalAdministrator = (boolean)1;
         this.isInternalAdministrator_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasId()) {
            String var2 = this.getId();
            var1.writeString(1, var2);
         }

         if(this.hasIsInternalAdministrator()) {
            boolean var3 = this.getIsInternalAdministrator();
            var1.writeBool(2, var3);
         }
      }
   }

   public static final class ReviewResponse extends MessageMicro {

      public static final int GET_RESPONSE_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private Rev.GetReviewsResponse getResponse_ = null;
      private boolean hasGetResponse;


      public ReviewResponse() {}

      public static Rev.ReviewResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Rev.ReviewResponse()).mergeFrom(var0);
      }

      public static Rev.ReviewResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Rev.ReviewResponse)((Rev.ReviewResponse)(new Rev.ReviewResponse()).mergeFrom(var0));
      }

      public final Rev.ReviewResponse clear() {
         Rev.ReviewResponse var1 = this.clearGetResponse();
         this.cachedSize = -1;
         return this;
      }

      public Rev.ReviewResponse clearGetResponse() {
         this.hasGetResponse = (boolean)0;
         this.getResponse_ = null;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Rev.GetReviewsResponse getGetResponse() {
         return this.getResponse_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasGetResponse()) {
            Rev.GetReviewsResponse var2 = this.getGetResponse();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasGetResponse() {
         return this.hasGetResponse;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(this.hasGetResponse() && !this.getGetResponse().isInitialized()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Rev.ReviewResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Rev.GetReviewsResponse var3 = new Rev.GetReviewsResponse();
               var1.readMessage(var3);
               this.setGetResponse(var3);
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

      public Rev.ReviewResponse setGetResponse(Rev.GetReviewsResponse var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasGetResponse = (boolean)1;
            this.getResponse_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasGetResponse()) {
            Rev.GetReviewsResponse var2 = this.getGetResponse();
            var1.writeMessage(1, var2);
         }
      }
   }
}
