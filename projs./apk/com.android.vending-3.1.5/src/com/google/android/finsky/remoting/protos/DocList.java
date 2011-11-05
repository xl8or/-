package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class DocList {

   private DocList() {}

   public static final class ListResponse extends MessageMicro {

      public static final int BUCKET_FIELD_NUMBER = 1;
      private List<DocList.Bucket> bucket_;
      private int cachedSize;


      public ListResponse() {
         List var1 = Collections.emptyList();
         this.bucket_ = var1;
         this.cachedSize = -1;
      }

      public static DocList.ListResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DocList.ListResponse()).mergeFrom(var0);
      }

      public static DocList.ListResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DocList.ListResponse)((DocList.ListResponse)(new DocList.ListResponse()).mergeFrom(var0));
      }

      public DocList.ListResponse addBucket(DocList.Bucket var1) {
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

      public final DocList.ListResponse clear() {
         DocList.ListResponse var1 = this.clearBucket();
         this.cachedSize = -1;
         return this;
      }

      public DocList.ListResponse clearBucket() {
         List var1 = Collections.emptyList();
         this.bucket_ = var1;
         return this;
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

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getBucketList().iterator(); var2.hasNext(); var1 += var4) {
            DocList.Bucket var3 = (DocList.Bucket)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(1, var3);
         }

         this.cachedSize = var1;
         return var1;
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

      public DocList.ListResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               DocList.Bucket var3 = new DocList.Bucket();
               var1.readMessage(var3);
               this.addBucket(var3);
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

      public DocList.ListResponse setBucket(int var1, DocList.Bucket var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.bucket_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getBucketList().iterator();

         while(var2.hasNext()) {
            DocList.Bucket var3 = (DocList.Bucket)var2.next();
            var1.writeMessage(1, var3);
         }

      }
   }

   public static final class Bucket extends MessageMicro {

      public static final int ANALYTICS_COOKIE_FIELD_NUMBER = 8;
      public static final int DOCUMENT_FIELD_NUMBER = 1;
      public static final int ESTIMATED_RESULTS_FIELD_NUMBER = 7;
      public static final int FULL_CONTENTS_LIST_URL_FIELD_NUMBER = 9;
      public static final int FULL_CONTENTS_URL_FIELD_NUMBER = 5;
      public static final int ICON_URL_FIELD_NUMBER = 4;
      public static final int MULTI_CORPUS_FIELD_NUMBER = 2;
      public static final int NEXT_PAGE_URL_FIELD_NUMBER = 10;
      public static final int ORDERED_FIELD_NUMBER = 11;
      public static final int RELEVANCE_FIELD_NUMBER = 6;
      public static final int TITLE_FIELD_NUMBER = 3;
      private String analyticsCookie_;
      private int cachedSize;
      private List<DeviceDoc.DeviceDocument> document_;
      private long estimatedResults_;
      private String fullContentsListUrl_;
      private String fullContentsUrl_;
      private boolean hasAnalyticsCookie;
      private boolean hasEstimatedResults;
      private boolean hasFullContentsListUrl;
      private boolean hasFullContentsUrl;
      private boolean hasIconUrl;
      private boolean hasMultiCorpus;
      private boolean hasNextPageUrl;
      private boolean hasOrdered;
      private boolean hasRelevance;
      private boolean hasTitle;
      private String iconUrl_;
      private boolean multiCorpus_;
      private String nextPageUrl_;
      private boolean ordered_;
      private double relevance_;
      private String title_;


      public Bucket() {
         List var1 = Collections.emptyList();
         this.document_ = var1;
         this.multiCorpus_ = (boolean)0;
         this.title_ = "";
         this.iconUrl_ = "";
         this.fullContentsUrl_ = "";
         this.fullContentsListUrl_ = "";
         this.nextPageUrl_ = "";
         this.relevance_ = 0.0D;
         this.estimatedResults_ = 0L;
         this.analyticsCookie_ = "";
         this.ordered_ = (boolean)0;
         this.cachedSize = -1;
      }

      public static DocList.Bucket parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DocList.Bucket()).mergeFrom(var0);
      }

      public static DocList.Bucket parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DocList.Bucket)((DocList.Bucket)(new DocList.Bucket()).mergeFrom(var0));
      }

      public DocList.Bucket addDocument(DeviceDoc.DeviceDocument var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.document_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.document_ = var2;
            }

            this.document_.add(var1);
            return this;
         }
      }

      public final DocList.Bucket clear() {
         DocList.Bucket var1 = this.clearDocument();
         DocList.Bucket var2 = this.clearMultiCorpus();
         DocList.Bucket var3 = this.clearTitle();
         DocList.Bucket var4 = this.clearIconUrl();
         DocList.Bucket var5 = this.clearFullContentsUrl();
         DocList.Bucket var6 = this.clearFullContentsListUrl();
         DocList.Bucket var7 = this.clearNextPageUrl();
         DocList.Bucket var8 = this.clearRelevance();
         DocList.Bucket var9 = this.clearEstimatedResults();
         DocList.Bucket var10 = this.clearAnalyticsCookie();
         DocList.Bucket var11 = this.clearOrdered();
         this.cachedSize = -1;
         return this;
      }

      public DocList.Bucket clearAnalyticsCookie() {
         this.hasAnalyticsCookie = (boolean)0;
         this.analyticsCookie_ = "";
         return this;
      }

      public DocList.Bucket clearDocument() {
         List var1 = Collections.emptyList();
         this.document_ = var1;
         return this;
      }

      public DocList.Bucket clearEstimatedResults() {
         this.hasEstimatedResults = (boolean)0;
         this.estimatedResults_ = 0L;
         return this;
      }

      public DocList.Bucket clearFullContentsListUrl() {
         this.hasFullContentsListUrl = (boolean)0;
         this.fullContentsListUrl_ = "";
         return this;
      }

      public DocList.Bucket clearFullContentsUrl() {
         this.hasFullContentsUrl = (boolean)0;
         this.fullContentsUrl_ = "";
         return this;
      }

      public DocList.Bucket clearIconUrl() {
         this.hasIconUrl = (boolean)0;
         this.iconUrl_ = "";
         return this;
      }

      public DocList.Bucket clearMultiCorpus() {
         this.hasMultiCorpus = (boolean)0;
         this.multiCorpus_ = (boolean)0;
         return this;
      }

      public DocList.Bucket clearNextPageUrl() {
         this.hasNextPageUrl = (boolean)0;
         this.nextPageUrl_ = "";
         return this;
      }

      public DocList.Bucket clearOrdered() {
         this.hasOrdered = (boolean)0;
         this.ordered_ = (boolean)0;
         return this;
      }

      public DocList.Bucket clearRelevance() {
         this.hasRelevance = (boolean)0;
         this.relevance_ = 0.0D;
         return this;
      }

      public DocList.Bucket clearTitle() {
         this.hasTitle = (boolean)0;
         this.title_ = "";
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

      public DeviceDoc.DeviceDocument getDocument(int var1) {
         return (DeviceDoc.DeviceDocument)this.document_.get(var1);
      }

      public int getDocumentCount() {
         return this.document_.size();
      }

      public List<DeviceDoc.DeviceDocument> getDocumentList() {
         return this.document_;
      }

      public long getEstimatedResults() {
         return this.estimatedResults_;
      }

      public String getFullContentsListUrl() {
         return this.fullContentsListUrl_;
      }

      public String getFullContentsUrl() {
         return this.fullContentsUrl_;
      }

      public String getIconUrl() {
         return this.iconUrl_;
      }

      public boolean getMultiCorpus() {
         return this.multiCorpus_;
      }

      public String getNextPageUrl() {
         return this.nextPageUrl_;
      }

      public boolean getOrdered() {
         return this.ordered_;
      }

      public double getRelevance() {
         return this.relevance_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getDocumentList().iterator(); var2.hasNext(); var1 += var4) {
            DeviceDoc.DeviceDocument var3 = (DeviceDoc.DeviceDocument)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(1, var3);
         }

         if(this.hasMultiCorpus()) {
            boolean var5 = this.getMultiCorpus();
            int var6 = CodedOutputStreamMicro.computeBoolSize(2, var5);
            var1 += var6;
         }

         if(this.hasTitle()) {
            String var7 = this.getTitle();
            int var8 = CodedOutputStreamMicro.computeStringSize(3, var7);
            var1 += var8;
         }

         if(this.hasIconUrl()) {
            String var9 = this.getIconUrl();
            int var10 = CodedOutputStreamMicro.computeStringSize(4, var9);
            var1 += var10;
         }

         if(this.hasFullContentsUrl()) {
            String var11 = this.getFullContentsUrl();
            int var12 = CodedOutputStreamMicro.computeStringSize(5, var11);
            var1 += var12;
         }

         if(this.hasRelevance()) {
            double var13 = this.getRelevance();
            int var15 = CodedOutputStreamMicro.computeDoubleSize(6, var13);
            var1 += var15;
         }

         if(this.hasEstimatedResults()) {
            long var16 = this.getEstimatedResults();
            int var18 = CodedOutputStreamMicro.computeInt64Size(7, var16);
            var1 += var18;
         }

         if(this.hasAnalyticsCookie()) {
            String var19 = this.getAnalyticsCookie();
            int var20 = CodedOutputStreamMicro.computeStringSize(8, var19);
            var1 += var20;
         }

         if(this.hasFullContentsListUrl()) {
            String var21 = this.getFullContentsListUrl();
            int var22 = CodedOutputStreamMicro.computeStringSize(9, var21);
            var1 += var22;
         }

         if(this.hasNextPageUrl()) {
            String var23 = this.getNextPageUrl();
            int var24 = CodedOutputStreamMicro.computeStringSize(10, var23);
            var1 += var24;
         }

         if(this.hasOrdered()) {
            boolean var25 = this.getOrdered();
            int var26 = CodedOutputStreamMicro.computeBoolSize(11, var25);
            var1 += var26;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getTitle() {
         return this.title_;
      }

      public boolean hasAnalyticsCookie() {
         return this.hasAnalyticsCookie;
      }

      public boolean hasEstimatedResults() {
         return this.hasEstimatedResults;
      }

      public boolean hasFullContentsListUrl() {
         return this.hasFullContentsListUrl;
      }

      public boolean hasFullContentsUrl() {
         return this.hasFullContentsUrl;
      }

      public boolean hasIconUrl() {
         return this.hasIconUrl;
      }

      public boolean hasMultiCorpus() {
         return this.hasMultiCorpus;
      }

      public boolean hasNextPageUrl() {
         return this.hasNextPageUrl;
      }

      public boolean hasOrdered() {
         return this.hasOrdered;
      }

      public boolean hasRelevance() {
         return this.hasRelevance;
      }

      public boolean hasTitle() {
         return this.hasTitle;
      }

      public final boolean isInitialized() {
         Iterator var1 = this.getDocumentList().iterator();

         boolean var2;
         while(true) {
            if(var1.hasNext()) {
               if(((DeviceDoc.DeviceDocument)var1.next()).isInitialized()) {
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

      public DocList.Bucket mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               DeviceDoc.DeviceDocument var3 = new DeviceDoc.DeviceDocument();
               var1.readMessage(var3);
               this.addDocument(var3);
               break;
            case 16:
               boolean var5 = var1.readBool();
               this.setMultiCorpus(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setTitle(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setIconUrl(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setFullContentsUrl(var11);
               break;
            case 49:
               double var13 = var1.readDouble();
               this.setRelevance(var13);
               break;
            case 56:
               long var16 = var1.readInt64();
               this.setEstimatedResults(var16);
               break;
            case 66:
               String var19 = var1.readString();
               this.setAnalyticsCookie(var19);
               break;
            case 74:
               String var21 = var1.readString();
               this.setFullContentsListUrl(var21);
               break;
            case 82:
               String var23 = var1.readString();
               this.setNextPageUrl(var23);
               break;
            case 88:
               boolean var25 = var1.readBool();
               this.setOrdered(var25);
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

      public DocList.Bucket setAnalyticsCookie(String var1) {
         this.hasAnalyticsCookie = (boolean)1;
         this.analyticsCookie_ = var1;
         return this;
      }

      public DocList.Bucket setDocument(int var1, DeviceDoc.DeviceDocument var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.document_.set(var1, var2);
            return this;
         }
      }

      public DocList.Bucket setEstimatedResults(long var1) {
         this.hasEstimatedResults = (boolean)1;
         this.estimatedResults_ = var1;
         return this;
      }

      public DocList.Bucket setFullContentsListUrl(String var1) {
         this.hasFullContentsListUrl = (boolean)1;
         this.fullContentsListUrl_ = var1;
         return this;
      }

      public DocList.Bucket setFullContentsUrl(String var1) {
         this.hasFullContentsUrl = (boolean)1;
         this.fullContentsUrl_ = var1;
         return this;
      }

      public DocList.Bucket setIconUrl(String var1) {
         this.hasIconUrl = (boolean)1;
         this.iconUrl_ = var1;
         return this;
      }

      public DocList.Bucket setMultiCorpus(boolean var1) {
         this.hasMultiCorpus = (boolean)1;
         this.multiCorpus_ = var1;
         return this;
      }

      public DocList.Bucket setNextPageUrl(String var1) {
         this.hasNextPageUrl = (boolean)1;
         this.nextPageUrl_ = var1;
         return this;
      }

      public DocList.Bucket setOrdered(boolean var1) {
         this.hasOrdered = (boolean)1;
         this.ordered_ = var1;
         return this;
      }

      public DocList.Bucket setRelevance(double var1) {
         this.hasRelevance = (boolean)1;
         this.relevance_ = var1;
         return this;
      }

      public DocList.Bucket setTitle(String var1) {
         this.hasTitle = (boolean)1;
         this.title_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getDocumentList().iterator();

         while(var2.hasNext()) {
            DeviceDoc.DeviceDocument var3 = (DeviceDoc.DeviceDocument)var2.next();
            var1.writeMessage(1, var3);
         }

         if(this.hasMultiCorpus()) {
            boolean var4 = this.getMultiCorpus();
            var1.writeBool(2, var4);
         }

         if(this.hasTitle()) {
            String var5 = this.getTitle();
            var1.writeString(3, var5);
         }

         if(this.hasIconUrl()) {
            String var6 = this.getIconUrl();
            var1.writeString(4, var6);
         }

         if(this.hasFullContentsUrl()) {
            String var7 = this.getFullContentsUrl();
            var1.writeString(5, var7);
         }

         if(this.hasRelevance()) {
            double var8 = this.getRelevance();
            var1.writeDouble(6, var8);
         }

         if(this.hasEstimatedResults()) {
            long var10 = this.getEstimatedResults();
            var1.writeInt64(7, var10);
         }

         if(this.hasAnalyticsCookie()) {
            String var12 = this.getAnalyticsCookie();
            var1.writeString(8, var12);
         }

         if(this.hasFullContentsListUrl()) {
            String var13 = this.getFullContentsListUrl();
            var1.writeString(9, var13);
         }

         if(this.hasNextPageUrl()) {
            String var14 = this.getNextPageUrl();
            var1.writeString(10, var14);
         }

         if(this.hasOrdered()) {
            boolean var15 = this.getOrdered();
            var1.writeBool(11, var15);
         }
      }
   }
}
