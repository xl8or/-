package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.Rev;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class DetailsResponse extends MessageMicro {

   public static final int ANALYTICS_COOKIE_FIELD_NUMBER = 2;
   public static final int DOC_FIELD_NUMBER = 1;
   public static final int USER_REVIEW_FIELD_NUMBER = 3;
   private String analyticsCookie_ = "";
   private int cachedSize = -1;
   private DeviceDoc.DeviceDocument doc_ = null;
   private boolean hasAnalyticsCookie;
   private boolean hasDoc;
   private boolean hasUserReview;
   private Rev.Review userReview_ = null;


   public DetailsResponse() {}

   public static DetailsResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new DetailsResponse()).mergeFrom(var0);
   }

   public static DetailsResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (DetailsResponse)((DetailsResponse)(new DetailsResponse()).mergeFrom(var0));
   }

   public final DetailsResponse clear() {
      DetailsResponse var1 = this.clearDoc();
      DetailsResponse var2 = this.clearAnalyticsCookie();
      DetailsResponse var3 = this.clearUserReview();
      this.cachedSize = -1;
      return this;
   }

   public DetailsResponse clearAnalyticsCookie() {
      this.hasAnalyticsCookie = (boolean)0;
      this.analyticsCookie_ = "";
      return this;
   }

   public DetailsResponse clearDoc() {
      this.hasDoc = (boolean)0;
      this.doc_ = null;
      return this;
   }

   public DetailsResponse clearUserReview() {
      this.hasUserReview = (boolean)0;
      this.userReview_ = null;
      return this;
   }

   public String getAnalyticsCookie() {
      return this.analyticsCookie_;
   }

   public int getCachedSize() {
      if(this.cachedSize < 0) {
         int var1 = this.getSerializedSize();
      }

      return this.cachedSize;
   }

   public DeviceDoc.DeviceDocument getDoc() {
      return this.doc_;
   }

   public int getSerializedSize() {
      int var1 = 0;
      if(this.hasDoc()) {
         DeviceDoc.DeviceDocument var2 = this.getDoc();
         int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
         var1 = 0 + var3;
      }

      if(this.hasAnalyticsCookie()) {
         String var4 = this.getAnalyticsCookie();
         int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
         var1 += var5;
      }

      if(this.hasUserReview()) {
         Rev.Review var6 = this.getUserReview();
         int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
         var1 += var7;
      }

      this.cachedSize = var1;
      return var1;
   }

   public Rev.Review getUserReview() {
      return this.userReview_;
   }

   public boolean hasAnalyticsCookie() {
      return this.hasAnalyticsCookie;
   }

   public boolean hasDoc() {
      return this.hasDoc;
   }

   public boolean hasUserReview() {
      return this.hasUserReview;
   }

   public final boolean isInitialized() {
      boolean var1 = false;
      if((!this.hasDoc() || this.getDoc().isInitialized()) && (!this.hasUserReview() || this.getUserReview().isInitialized())) {
         var1 = true;
      }

      return var1;
   }

   public DetailsResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 10:
            DeviceDoc.DeviceDocument var3 = new DeviceDoc.DeviceDocument();
            var1.readMessage(var3);
            this.setDoc(var3);
            break;
         case 18:
            String var5 = var1.readString();
            this.setAnalyticsCookie(var5);
            break;
         case 26:
            Rev.Review var7 = new Rev.Review();
            var1.readMessage(var7);
            this.setUserReview(var7);
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

   public DetailsResponse setAnalyticsCookie(String var1) {
      this.hasAnalyticsCookie = (boolean)1;
      this.analyticsCookie_ = var1;
      return this;
   }

   public DetailsResponse setDoc(DeviceDoc.DeviceDocument var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         this.hasDoc = (boolean)1;
         this.doc_ = var1;
         return this;
      }
   }

   public DetailsResponse setUserReview(Rev.Review var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         this.hasUserReview = (boolean)1;
         this.userReview_ = var1;
         return this;
      }
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      if(this.hasDoc()) {
         DeviceDoc.DeviceDocument var2 = this.getDoc();
         var1.writeMessage(1, var2);
      }

      if(this.hasAnalyticsCookie()) {
         String var3 = this.getAnalyticsCookie();
         var1.writeString(2, var3);
      }

      if(this.hasUserReview()) {
         Rev.Review var4 = this.getUserReview();
         var1.writeMessage(3, var4);
      }
   }
}
