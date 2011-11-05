package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.DocList;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class SearchResponse extends MessageMicro {

   public static final int AGGREGATE_QUERY_FIELD_NUMBER = 3;
   public static final int BUCKET_FIELD_NUMBER = 4;
   public static final int ORIGINAL_QUERY_FIELD_NUMBER = 1;
   public static final int SUGGESTED_QUERY_FIELD_NUMBER = 2;
   private boolean aggregateQuery_ = 0;
   private List<DocList.Bucket> bucket_;
   private int cachedSize;
   private boolean hasAggregateQuery;
   private boolean hasOriginalQuery;
   private boolean hasSuggestedQuery;
   private String originalQuery_ = "";
   private String suggestedQuery_ = "";


   public SearchResponse() {
      List var1 = Collections.emptyList();
      this.bucket_ = var1;
      this.cachedSize = -1;
   }

   public static SearchResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new SearchResponse()).mergeFrom(var0);
   }

   public static SearchResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (SearchResponse)((SearchResponse)(new SearchResponse()).mergeFrom(var0));
   }

   public SearchResponse addBucket(DocList.Bucket var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.bucket_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.bucket_ = var2;
         }

         this.bucket_.add(var1);
         return this;
      }
   }

   public final SearchResponse clear() {
      SearchResponse var1 = this.clearOriginalQuery();
      SearchResponse var2 = this.clearSuggestedQuery();
      SearchResponse var3 = this.clearAggregateQuery();
      SearchResponse var4 = this.clearBucket();
      this.cachedSize = -1;
      return this;
   }

   public SearchResponse clearAggregateQuery() {
      this.hasAggregateQuery = (boolean)0;
      this.aggregateQuery_ = (boolean)0;
      return this;
   }

   public SearchResponse clearBucket() {
      List var1 = Collections.emptyList();
      this.bucket_ = var1;
      return this;
   }

   public SearchResponse clearOriginalQuery() {
      this.hasOriginalQuery = (boolean)0;
      this.originalQuery_ = "";
      return this;
   }

   public SearchResponse clearSuggestedQuery() {
      this.hasSuggestedQuery = (boolean)0;
      this.suggestedQuery_ = "";
      return this;
   }

   public boolean getAggregateQuery() {
      return this.aggregateQuery_;
   }

   public DocList.Bucket getBucket(int var1) {
      return (DocList.Bucket)this.bucket_.get(var1);
   }

   public int getBucketCount() {
      return this.bucket_.size();
   }

   public List<DocList.Bucket> getBucketList() {
      return this.bucket_;
   }

   public int getCachedSize() {
      if(this.cachedSize < 0) {
         int var1 = this.getSerializedSize();
      }

      return this.cachedSize;
   }

   public String getOriginalQuery() {
      return this.originalQuery_;
   }

   public int getSerializedSize() {
      int var1 = 0;
      if(this.hasOriginalQuery()) {
         String var2 = this.getOriginalQuery();
         int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
         var1 = 0 + var3;
      }

      if(this.hasSuggestedQuery()) {
         String var4 = this.getSuggestedQuery();
         int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
         var1 += var5;
      }

      if(this.hasAggregateQuery()) {
         boolean var6 = this.getAggregateQuery();
         int var7 = CodedOutputStreamMicro.computeBoolSize(3, var6);
         var1 += var7;
      }

      int var10;
      for(Iterator var8 = this.getBucketList().iterator(); var8.hasNext(); var1 += var10) {
         DocList.Bucket var9 = (DocList.Bucket)var8.next();
         var10 = CodedOutputStreamMicro.computeMessageSize(4, var9);
      }

      this.cachedSize = var1;
      return var1;
   }

   public String getSuggestedQuery() {
      return this.suggestedQuery_;
   }

   public boolean hasAggregateQuery() {
      return this.hasAggregateQuery;
   }

   public boolean hasOriginalQuery() {
      return this.hasOriginalQuery;
   }

   public boolean hasSuggestedQuery() {
      return this.hasSuggestedQuery;
   }

   public final boolean isInitialized() {
      Iterator var1 = this.getBucketList().iterator();

      boolean var2;
      while(true) {
         if(var1.hasNext()) {
            if(((DocList.Bucket)var1.next()).isInitialized()) {
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

   public SearchResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 10:
            String var3 = var1.readString();
            this.setOriginalQuery(var3);
            break;
         case 18:
            String var5 = var1.readString();
            this.setSuggestedQuery(var5);
            break;
         case 24:
            boolean var7 = var1.readBool();
            this.setAggregateQuery(var7);
            break;
         case 34:
            DocList.Bucket var9 = new DocList.Bucket();
            var1.readMessage(var9);
            this.addBucket(var9);
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

   public SearchResponse setAggregateQuery(boolean var1) {
      this.hasAggregateQuery = (boolean)1;
      this.aggregateQuery_ = var1;
      return this;
   }

   public SearchResponse setBucket(int var1, DocList.Bucket var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.bucket_.set(var1, var2);
         return this;
      }
   }

   public SearchResponse setOriginalQuery(String var1) {
      this.hasOriginalQuery = (boolean)1;
      this.originalQuery_ = var1;
      return this;
   }

   public SearchResponse setSuggestedQuery(String var1) {
      this.hasSuggestedQuery = (boolean)1;
      this.suggestedQuery_ = var1;
      return this;
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      if(this.hasOriginalQuery()) {
         String var2 = this.getOriginalQuery();
         var1.writeString(1, var2);
      }

      if(this.hasSuggestedQuery()) {
         String var3 = this.getSuggestedQuery();
         var1.writeString(2, var3);
      }

      if(this.hasAggregateQuery()) {
         boolean var4 = this.getAggregateQuery();
         var1.writeBool(3, var4);
      }

      Iterator var5 = this.getBucketList().iterator();

      while(var5.hasNext()) {
         DocList.Bucket var6 = (DocList.Bucket)var5.next();
         var1.writeMessage(4, var6);
      }

   }
}
