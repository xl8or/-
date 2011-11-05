package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Rev;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class ReviewResponse extends MessageMicro {

   public static final int GET_RESPONSE_FIELD_NUMBER = 1;
   public static final int NEXT_PAGE_URL_FIELD_NUMBER = 2;
   private int cachedSize = -1;
   private Rev.GetReviewsResponse getResponse_ = null;
   private boolean hasGetResponse;
   private boolean hasNextPageUrl;
   private String nextPageUrl_ = "";


   public ReviewResponse() {}

   public static ReviewResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new ReviewResponse()).mergeFrom(var0);
   }

   public static ReviewResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (ReviewResponse)((ReviewResponse)(new ReviewResponse()).mergeFrom(var0));
   }

   public final ReviewResponse clear() {
      ReviewResponse var1 = this.clearGetResponse();
      ReviewResponse var2 = this.clearNextPageUrl();
      this.cachedSize = -1;
      return this;
   }

   public ReviewResponse clearGetResponse() {
      this.hasGetResponse = (boolean)0;
      this.getResponse_ = null;
      return this;
   }

   public ReviewResponse clearNextPageUrl() {
      this.hasNextPageUrl = (boolean)0;
      this.nextPageUrl_ = "";
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

   public String getNextPageUrl() {
      return this.nextPageUrl_;
   }

   public int getSerializedSize() {
      int var1 = 0;
      if(this.hasGetResponse()) {
         Rev.GetReviewsResponse var2 = this.getGetResponse();
         int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
         var1 = 0 + var3;
      }

      if(this.hasNextPageUrl()) {
         String var4 = this.getNextPageUrl();
         int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
         var1 += var5;
      }

      this.cachedSize = var1;
      return var1;
   }

   public boolean hasGetResponse() {
      return this.hasGetResponse;
   }

   public boolean hasNextPageUrl() {
      return this.hasNextPageUrl;
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

   public ReviewResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 10:
            Rev.GetReviewsResponse var3 = new Rev.GetReviewsResponse();
            var1.readMessage(var3);
            this.setGetResponse(var3);
            break;
         case 18:
            String var5 = var1.readString();
            this.setNextPageUrl(var5);
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

   public ReviewResponse setGetResponse(Rev.GetReviewsResponse var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         this.hasGetResponse = (boolean)1;
         this.getResponse_ = var1;
         return this;
      }
   }

   public ReviewResponse setNextPageUrl(String var1) {
      this.hasNextPageUrl = (boolean)1;
      this.nextPageUrl_ = var1;
      return this;
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      if(this.hasGetResponse()) {
         Rev.GetReviewsResponse var2 = this.getGetResponse();
         var1.writeMessage(1, var2);
      }

      if(this.hasNextPageUrl()) {
         String var3 = this.getNextPageUrl();
         var1.writeString(2, var3);
      }
   }
}
