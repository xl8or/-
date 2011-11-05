package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class Rating {

   private Rating() {}

   public static final class RatingsRequest extends MessageMicro {

      public static final int AVERAGE = 1;
      public static final int HISTOGRAM = 2;
      public static final int TYPE_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private boolean hasType;
      private int type_ = 1;


      public RatingsRequest() {}

      public static Rating.RatingsRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Rating.RatingsRequest()).mergeFrom(var0);
      }

      public static Rating.RatingsRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Rating.RatingsRequest)((Rating.RatingsRequest)(new Rating.RatingsRequest()).mergeFrom(var0));
      }

      public final Rating.RatingsRequest clear() {
         Rating.RatingsRequest var1 = this.clearType();
         this.cachedSize = -1;
         return this;
      }

      public Rating.RatingsRequest clearType() {
         this.hasType = (boolean)0;
         this.type_ = 1;
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
         if(this.hasType()) {
            int var2 = this.getType();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getType() {
         return this.type_;
      }

      public boolean hasType() {
         return this.hasType;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasType) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Rating.RatingsRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setType(var3);
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

      public Rating.RatingsRequest setType(int var1) {
         this.hasType = (boolean)1;
         this.type_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasType()) {
            int var2 = this.getType();
            var1.writeInt32(1, var2);
         }
      }
   }

   public static final class AggregateRating extends MessageMicro {

      public static final int FIVE_STAR_AVERAGE = 1;
      public static final int FIVE_STAR_HISTOGRAM = 2;
      public static final int FIVE_STAR_RATINGS_FIELD_NUMBER = 8;
      public static final int FOUR_STAR_RATINGS_FIELD_NUMBER = 7;
      public static final int ONE_STAR_RATINGS_FIELD_NUMBER = 4;
      public static final int RATINGS_COUNT_FIELD_NUMBER = 3;
      public static final int STAR_RATING_FIELD_NUMBER = 2;
      public static final int THREE_STAR_RATINGS_FIELD_NUMBER = 6;
      public static final int THUMBS_DOWN_COUNT_FIELD_NUMBER = 10;
      public static final int THUMBS_UP_AND_DOWN = 3;
      public static final int THUMBS_UP_COUNT_FIELD_NUMBER = 9;
      public static final int TWO_STAR_RATINGS_FIELD_NUMBER = 5;
      public static final int TYPE_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private long fiveStarRatings_ = 0L;
      private long fourStarRatings_ = 0L;
      private boolean hasFiveStarRatings;
      private boolean hasFourStarRatings;
      private boolean hasOneStarRatings;
      private boolean hasRatingsCount;
      private boolean hasStarRating;
      private boolean hasThreeStarRatings;
      private boolean hasThumbsDownCount;
      private boolean hasThumbsUpCount;
      private boolean hasTwoStarRatings;
      private boolean hasType;
      private long oneStarRatings_ = 0L;
      private long ratingsCount_ = 0L;
      private float starRating_ = 0.0F;
      private long threeStarRatings_ = 0L;
      private long thumbsDownCount_ = 0L;
      private long thumbsUpCount_ = 0L;
      private long twoStarRatings_ = 0L;
      private int type_ = 1;


      public AggregateRating() {}

      public static Rating.AggregateRating parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Rating.AggregateRating()).mergeFrom(var0);
      }

      public static Rating.AggregateRating parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Rating.AggregateRating)((Rating.AggregateRating)(new Rating.AggregateRating()).mergeFrom(var0));
      }

      public final Rating.AggregateRating clear() {
         Rating.AggregateRating var1 = this.clearType();
         Rating.AggregateRating var2 = this.clearStarRating();
         Rating.AggregateRating var3 = this.clearRatingsCount();
         Rating.AggregateRating var4 = this.clearOneStarRatings();
         Rating.AggregateRating var5 = this.clearTwoStarRatings();
         Rating.AggregateRating var6 = this.clearThreeStarRatings();
         Rating.AggregateRating var7 = this.clearFourStarRatings();
         Rating.AggregateRating var8 = this.clearFiveStarRatings();
         Rating.AggregateRating var9 = this.clearThumbsUpCount();
         Rating.AggregateRating var10 = this.clearThumbsDownCount();
         this.cachedSize = -1;
         return this;
      }

      public Rating.AggregateRating clearFiveStarRatings() {
         this.hasFiveStarRatings = (boolean)0;
         this.fiveStarRatings_ = 0L;
         return this;
      }

      public Rating.AggregateRating clearFourStarRatings() {
         this.hasFourStarRatings = (boolean)0;
         this.fourStarRatings_ = 0L;
         return this;
      }

      public Rating.AggregateRating clearOneStarRatings() {
         this.hasOneStarRatings = (boolean)0;
         this.oneStarRatings_ = 0L;
         return this;
      }

      public Rating.AggregateRating clearRatingsCount() {
         this.hasRatingsCount = (boolean)0;
         this.ratingsCount_ = 0L;
         return this;
      }

      public Rating.AggregateRating clearStarRating() {
         this.hasStarRating = (boolean)0;
         this.starRating_ = 0.0F;
         return this;
      }

      public Rating.AggregateRating clearThreeStarRatings() {
         this.hasThreeStarRatings = (boolean)0;
         this.threeStarRatings_ = 0L;
         return this;
      }

      public Rating.AggregateRating clearThumbsDownCount() {
         this.hasThumbsDownCount = (boolean)0;
         this.thumbsDownCount_ = 0L;
         return this;
      }

      public Rating.AggregateRating clearThumbsUpCount() {
         this.hasThumbsUpCount = (boolean)0;
         this.thumbsUpCount_ = 0L;
         return this;
      }

      public Rating.AggregateRating clearTwoStarRatings() {
         this.hasTwoStarRatings = (boolean)0;
         this.twoStarRatings_ = 0L;
         return this;
      }

      public Rating.AggregateRating clearType() {
         this.hasType = (boolean)0;
         this.type_ = 1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public long getFiveStarRatings() {
         return this.fiveStarRatings_;
      }

      public long getFourStarRatings() {
         return this.fourStarRatings_;
      }

      public long getOneStarRatings() {
         return this.oneStarRatings_;
      }

      public long getRatingsCount() {
         return this.ratingsCount_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasType()) {
            int var2 = this.getType();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasStarRating()) {
            float var4 = this.getStarRating();
            int var5 = CodedOutputStreamMicro.computeFloatSize(2, var4);
            var1 += var5;
         }

         if(this.hasRatingsCount()) {
            long var6 = this.getRatingsCount();
            int var8 = CodedOutputStreamMicro.computeUInt64Size(3, var6);
            var1 += var8;
         }

         if(this.hasOneStarRatings()) {
            long var9 = this.getOneStarRatings();
            int var11 = CodedOutputStreamMicro.computeUInt64Size(4, var9);
            var1 += var11;
         }

         if(this.hasTwoStarRatings()) {
            long var12 = this.getTwoStarRatings();
            int var14 = CodedOutputStreamMicro.computeUInt64Size(5, var12);
            var1 += var14;
         }

         if(this.hasThreeStarRatings()) {
            long var15 = this.getThreeStarRatings();
            int var17 = CodedOutputStreamMicro.computeUInt64Size(6, var15);
            var1 += var17;
         }

         if(this.hasFourStarRatings()) {
            long var18 = this.getFourStarRatings();
            int var20 = CodedOutputStreamMicro.computeUInt64Size(7, var18);
            var1 += var20;
         }

         if(this.hasFiveStarRatings()) {
            long var21 = this.getFiveStarRatings();
            int var23 = CodedOutputStreamMicro.computeUInt64Size(8, var21);
            var1 += var23;
         }

         if(this.hasThumbsUpCount()) {
            long var24 = this.getThumbsUpCount();
            int var26 = CodedOutputStreamMicro.computeUInt64Size(9, var24);
            var1 += var26;
         }

         if(this.hasThumbsDownCount()) {
            long var27 = this.getThumbsDownCount();
            int var29 = CodedOutputStreamMicro.computeUInt64Size(10, var27);
            var1 += var29;
         }

         this.cachedSize = var1;
         return var1;
      }

      public float getStarRating() {
         return this.starRating_;
      }

      public long getThreeStarRatings() {
         return this.threeStarRatings_;
      }

      public long getThumbsDownCount() {
         return this.thumbsDownCount_;
      }

      public long getThumbsUpCount() {
         return this.thumbsUpCount_;
      }

      public long getTwoStarRatings() {
         return this.twoStarRatings_;
      }

      public int getType() {
         return this.type_;
      }

      public boolean hasFiveStarRatings() {
         return this.hasFiveStarRatings;
      }

      public boolean hasFourStarRatings() {
         return this.hasFourStarRatings;
      }

      public boolean hasOneStarRatings() {
         return this.hasOneStarRatings;
      }

      public boolean hasRatingsCount() {
         return this.hasRatingsCount;
      }

      public boolean hasStarRating() {
         return this.hasStarRating;
      }

      public boolean hasThreeStarRatings() {
         return this.hasThreeStarRatings;
      }

      public boolean hasThumbsDownCount() {
         return this.hasThumbsDownCount;
      }

      public boolean hasThumbsUpCount() {
         return this.hasThumbsUpCount;
      }

      public boolean hasTwoStarRatings() {
         return this.hasTwoStarRatings;
      }

      public boolean hasType() {
         return this.hasType;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasType) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Rating.AggregateRating mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setType(var3);
               break;
            case 21:
               float var5 = var1.readFloat();
               this.setStarRating(var5);
               break;
            case 24:
               long var7 = var1.readUInt64();
               this.setRatingsCount(var7);
               break;
            case 32:
               long var10 = var1.readUInt64();
               this.setOneStarRatings(var10);
               break;
            case 40:
               long var13 = var1.readUInt64();
               this.setTwoStarRatings(var13);
               break;
            case 48:
               long var16 = var1.readUInt64();
               this.setThreeStarRatings(var16);
               break;
            case 56:
               long var19 = var1.readUInt64();
               this.setFourStarRatings(var19);
               break;
            case 64:
               long var22 = var1.readUInt64();
               this.setFiveStarRatings(var22);
               break;
            case 72:
               long var25 = var1.readUInt64();
               this.setThumbsUpCount(var25);
               break;
            case 80:
               long var28 = var1.readUInt64();
               this.setThumbsDownCount(var28);
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

      public Rating.AggregateRating setFiveStarRatings(long var1) {
         this.hasFiveStarRatings = (boolean)1;
         this.fiveStarRatings_ = var1;
         return this;
      }

      public Rating.AggregateRating setFourStarRatings(long var1) {
         this.hasFourStarRatings = (boolean)1;
         this.fourStarRatings_ = var1;
         return this;
      }

      public Rating.AggregateRating setOneStarRatings(long var1) {
         this.hasOneStarRatings = (boolean)1;
         this.oneStarRatings_ = var1;
         return this;
      }

      public Rating.AggregateRating setRatingsCount(long var1) {
         this.hasRatingsCount = (boolean)1;
         this.ratingsCount_ = var1;
         return this;
      }

      public Rating.AggregateRating setStarRating(float var1) {
         this.hasStarRating = (boolean)1;
         this.starRating_ = var1;
         return this;
      }

      public Rating.AggregateRating setThreeStarRatings(long var1) {
         this.hasThreeStarRatings = (boolean)1;
         this.threeStarRatings_ = var1;
         return this;
      }

      public Rating.AggregateRating setThumbsDownCount(long var1) {
         this.hasThumbsDownCount = (boolean)1;
         this.thumbsDownCount_ = var1;
         return this;
      }

      public Rating.AggregateRating setThumbsUpCount(long var1) {
         this.hasThumbsUpCount = (boolean)1;
         this.thumbsUpCount_ = var1;
         return this;
      }

      public Rating.AggregateRating setTwoStarRatings(long var1) {
         this.hasTwoStarRatings = (boolean)1;
         this.twoStarRatings_ = var1;
         return this;
      }

      public Rating.AggregateRating setType(int var1) {
         this.hasType = (boolean)1;
         this.type_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasType()) {
            int var2 = this.getType();
            var1.writeInt32(1, var2);
         }

         if(this.hasStarRating()) {
            float var3 = this.getStarRating();
            var1.writeFloat(2, var3);
         }

         if(this.hasRatingsCount()) {
            long var4 = this.getRatingsCount();
            var1.writeUInt64(3, var4);
         }

         if(this.hasOneStarRatings()) {
            long var6 = this.getOneStarRatings();
            var1.writeUInt64(4, var6);
         }

         if(this.hasTwoStarRatings()) {
            long var8 = this.getTwoStarRatings();
            var1.writeUInt64(5, var8);
         }

         if(this.hasThreeStarRatings()) {
            long var10 = this.getThreeStarRatings();
            var1.writeUInt64(6, var10);
         }

         if(this.hasFourStarRatings()) {
            long var12 = this.getFourStarRatings();
            var1.writeUInt64(7, var12);
         }

         if(this.hasFiveStarRatings()) {
            long var14 = this.getFiveStarRatings();
            var1.writeUInt64(8, var14);
         }

         if(this.hasThumbsUpCount()) {
            long var16 = this.getThumbsUpCount();
            var1.writeUInt64(9, var16);
         }

         if(this.hasThumbsDownCount()) {
            long var18 = this.getThumbsDownCount();
            var1.writeUInt64(10, var18);
         }
      }
   }
}
