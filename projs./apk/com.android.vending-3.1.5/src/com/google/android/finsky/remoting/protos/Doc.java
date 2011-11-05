package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Common;
import com.google.android.finsky.remoting.protos.FilterRules;
import com.google.android.finsky.remoting.protos.Rating;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Doc {

   private Doc() {}

   public static final class Document extends MessageMicro {

      public static final int AGGREGATE_RATING_FIELD_NUMBER = 13;
      public static final int AVAILABILITY_FIELD_NUMBER = 9;
      public static final int CHILD_FIELD_NUMBER = 11;
      public static final int DOCID_FIELD_NUMBER = 1;
      public static final int DOCUMENT_VARIANT_FIELD_NUMBER = 16;
      public static final int FETCH_DOCID_FIELD_NUMBER = 2;
      public static final int IMAGE_FIELD_NUMBER = 10;
      public static final int OFFER_FIELD_NUMBER = 14;
      public static final int PRICE_DEPRECATED_FIELD_NUMBER = 7;
      public static final int SAMPLE_DOCID_FIELD_NUMBER = 3;
      public static final int SNIPPET_FIELD_NUMBER = 6;
      public static final int TITLE_FIELD_NUMBER = 4;
      public static final int TRANSLATED_SNIPPET_FIELD_NUMBER = 15;
      public static final int URL_FIELD_NUMBER = 5;
      private Rating.AggregateRating aggregateRating_;
      private FilterRules.Availability availability_;
      private int cachedSize;
      private List<Doc.Document> child_;
      private Common.Docid docid_ = null;
      private List<Doc.DocumentVariant> documentVariant_;
      private Common.Docid fetchDocid_ = null;
      private boolean hasAggregateRating;
      private boolean hasAvailability;
      private boolean hasDocid;
      private boolean hasFetchDocid;
      private boolean hasPriceDeprecated;
      private boolean hasSampleDocid;
      private boolean hasTitle;
      private boolean hasUrl;
      private List<Doc.Image> image_;
      private List<Common.Offer> offer_;
      private Common.Offer priceDeprecated_;
      private Common.Docid sampleDocid_ = null;
      private List<String> snippet_;
      private String title_ = "";
      private List<Doc.TranslatedText> translatedSnippet_;
      private String url_ = "";


      public Document() {
         List var1 = Collections.emptyList();
         this.snippet_ = var1;
         List var2 = Collections.emptyList();
         this.translatedSnippet_ = var2;
         this.priceDeprecated_ = null;
         List var3 = Collections.emptyList();
         this.offer_ = var3;
         this.availability_ = null;
         List var4 = Collections.emptyList();
         this.image_ = var4;
         List var5 = Collections.emptyList();
         this.child_ = var5;
         this.aggregateRating_ = null;
         List var6 = Collections.emptyList();
         this.documentVariant_ = var6;
         this.cachedSize = -1;
      }

      public static Doc.Document parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Doc.Document()).mergeFrom(var0);
      }

      public static Doc.Document parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Doc.Document)((Doc.Document)(new Doc.Document()).mergeFrom(var0));
      }

      public Doc.Document addChild(Doc.Document var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.child_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.child_ = var2;
            }

            this.child_.add(var1);
            return this;
         }
      }

      public Doc.Document addDocumentVariant(Doc.DocumentVariant var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.documentVariant_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.documentVariant_ = var2;
            }

            this.documentVariant_.add(var1);
            return this;
         }
      }

      public Doc.Document addImage(Doc.Image var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.image_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.image_ = var2;
            }

            this.image_.add(var1);
            return this;
         }
      }

      public Doc.Document addOffer(Common.Offer var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.offer_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.offer_ = var2;
            }

            this.offer_.add(var1);
            return this;
         }
      }

      public Doc.Document addSnippet(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.snippet_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.snippet_ = var2;
            }

            this.snippet_.add(var1);
            return this;
         }
      }

      public Doc.Document addTranslatedSnippet(Doc.TranslatedText var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.translatedSnippet_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.translatedSnippet_ = var2;
            }

            this.translatedSnippet_.add(var1);
            return this;
         }
      }

      public final Doc.Document clear() {
         Doc.Document var1 = this.clearDocid();
         Doc.Document var2 = this.clearFetchDocid();
         Doc.Document var3 = this.clearSampleDocid();
         Doc.Document var4 = this.clearTitle();
         Doc.Document var5 = this.clearUrl();
         Doc.Document var6 = this.clearSnippet();
         Doc.Document var7 = this.clearTranslatedSnippet();
         Doc.Document var8 = this.clearPriceDeprecated();
         Doc.Document var9 = this.clearOffer();
         Doc.Document var10 = this.clearAvailability();
         Doc.Document var11 = this.clearImage();
         Doc.Document var12 = this.clearChild();
         Doc.Document var13 = this.clearAggregateRating();
         Doc.Document var14 = this.clearDocumentVariant();
         this.cachedSize = -1;
         return this;
      }

      public Doc.Document clearAggregateRating() {
         this.hasAggregateRating = (boolean)0;
         this.aggregateRating_ = null;
         return this;
      }

      public Doc.Document clearAvailability() {
         this.hasAvailability = (boolean)0;
         this.availability_ = null;
         return this;
      }

      public Doc.Document clearChild() {
         List var1 = Collections.emptyList();
         this.child_ = var1;
         return this;
      }

      public Doc.Document clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = null;
         return this;
      }

      public Doc.Document clearDocumentVariant() {
         List var1 = Collections.emptyList();
         this.documentVariant_ = var1;
         return this;
      }

      public Doc.Document clearFetchDocid() {
         this.hasFetchDocid = (boolean)0;
         this.fetchDocid_ = null;
         return this;
      }

      public Doc.Document clearImage() {
         List var1 = Collections.emptyList();
         this.image_ = var1;
         return this;
      }

      public Doc.Document clearOffer() {
         List var1 = Collections.emptyList();
         this.offer_ = var1;
         return this;
      }

      public Doc.Document clearPriceDeprecated() {
         this.hasPriceDeprecated = (boolean)0;
         this.priceDeprecated_ = null;
         return this;
      }

      public Doc.Document clearSampleDocid() {
         this.hasSampleDocid = (boolean)0;
         this.sampleDocid_ = null;
         return this;
      }

      public Doc.Document clearSnippet() {
         List var1 = Collections.emptyList();
         this.snippet_ = var1;
         return this;
      }

      public Doc.Document clearTitle() {
         this.hasTitle = (boolean)0;
         this.title_ = "";
         return this;
      }

      public Doc.Document clearTranslatedSnippet() {
         List var1 = Collections.emptyList();
         this.translatedSnippet_ = var1;
         return this;
      }

      public Doc.Document clearUrl() {
         this.hasUrl = (boolean)0;
         this.url_ = "";
         return this;
      }

      public Rating.AggregateRating getAggregateRating() {
         return this.aggregateRating_;
      }

      public FilterRules.Availability getAvailability() {
         return this.availability_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Doc.Document getChild(int var1) {
         return (Doc.Document)this.child_.get(var1);
      }

      public int getChildCount() {
         return this.child_.size();
      }

      public List<Doc.Document> getChildList() {
         return this.child_;
      }

      public Common.Docid getDocid() {
         return this.docid_;
      }

      public Doc.DocumentVariant getDocumentVariant(int var1) {
         return (Doc.DocumentVariant)this.documentVariant_.get(var1);
      }

      public int getDocumentVariantCount() {
         return this.documentVariant_.size();
      }

      public List<Doc.DocumentVariant> getDocumentVariantList() {
         return this.documentVariant_;
      }

      public Common.Docid getFetchDocid() {
         return this.fetchDocid_;
      }

      public Doc.Image getImage(int var1) {
         return (Doc.Image)this.image_.get(var1);
      }

      public int getImageCount() {
         return this.image_.size();
      }

      public List<Doc.Image> getImageList() {
         return this.image_;
      }

      public Common.Offer getOffer(int var1) {
         return (Common.Offer)this.offer_.get(var1);
      }

      public int getOfferCount() {
         return this.offer_.size();
      }

      public List<Common.Offer> getOfferList() {
         return this.offer_;
      }

      public Common.Offer getPriceDeprecated() {
         return this.priceDeprecated_;
      }

      public Common.Docid getSampleDocid() {
         return this.sampleDocid_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasDocid()) {
            Common.Docid var2 = this.getDocid();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasFetchDocid()) {
            Common.Docid var4 = this.getFetchDocid();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         if(this.hasSampleDocid()) {
            Common.Docid var6 = this.getSampleDocid();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         if(this.hasTitle()) {
            String var8 = this.getTitle();
            int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
            var1 += var9;
         }

         if(this.hasUrl()) {
            String var10 = this.getUrl();
            int var11 = CodedOutputStreamMicro.computeStringSize(5, var10);
            var1 += var11;
         }

         int var12 = 0;

         int var14;
         for(Iterator var13 = this.getSnippetList().iterator(); var13.hasNext(); var12 += var14) {
            var14 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var13.next());
         }

         int var15 = var1 + var12;
         int var16 = this.getSnippetList().size() * 1;
         int var17 = var15 + var16;
         if(this.hasPriceDeprecated()) {
            Common.Offer var18 = this.getPriceDeprecated();
            int var19 = CodedOutputStreamMicro.computeMessageSize(7, var18);
            var17 += var19;
         }

         if(this.hasAvailability()) {
            FilterRules.Availability var20 = this.getAvailability();
            int var21 = CodedOutputStreamMicro.computeMessageSize(9, var20);
            var17 += var21;
         }

         int var24;
         for(Iterator var22 = this.getImageList().iterator(); var22.hasNext(); var17 += var24) {
            Doc.Image var23 = (Doc.Image)var22.next();
            var24 = CodedOutputStreamMicro.computeMessageSize(10, var23);
         }

         int var27;
         for(Iterator var25 = this.getChildList().iterator(); var25.hasNext(); var17 += var27) {
            Doc.Document var26 = (Doc.Document)var25.next();
            var27 = CodedOutputStreamMicro.computeMessageSize(11, var26);
         }

         if(this.hasAggregateRating()) {
            Rating.AggregateRating var28 = this.getAggregateRating();
            int var29 = CodedOutputStreamMicro.computeMessageSize(13, var28);
            var17 += var29;
         }

         int var32;
         for(Iterator var30 = this.getOfferList().iterator(); var30.hasNext(); var17 += var32) {
            Common.Offer var31 = (Common.Offer)var30.next();
            var32 = CodedOutputStreamMicro.computeMessageSize(14, var31);
         }

         int var35;
         for(Iterator var33 = this.getTranslatedSnippetList().iterator(); var33.hasNext(); var17 += var35) {
            Doc.TranslatedText var34 = (Doc.TranslatedText)var33.next();
            var35 = CodedOutputStreamMicro.computeMessageSize(15, var34);
         }

         int var38;
         for(Iterator var36 = this.getDocumentVariantList().iterator(); var36.hasNext(); var17 += var38) {
            Doc.DocumentVariant var37 = (Doc.DocumentVariant)var36.next();
            var38 = CodedOutputStreamMicro.computeMessageSize(16, var37);
         }

         this.cachedSize = var17;
         return var17;
      }

      public String getSnippet(int var1) {
         return (String)this.snippet_.get(var1);
      }

      public int getSnippetCount() {
         return this.snippet_.size();
      }

      public List<String> getSnippetList() {
         return this.snippet_;
      }

      public String getTitle() {
         return this.title_;
      }

      public Doc.TranslatedText getTranslatedSnippet(int var1) {
         return (Doc.TranslatedText)this.translatedSnippet_.get(var1);
      }

      public int getTranslatedSnippetCount() {
         return this.translatedSnippet_.size();
      }

      public List<Doc.TranslatedText> getTranslatedSnippetList() {
         return this.translatedSnippet_;
      }

      public String getUrl() {
         return this.url_;
      }

      public boolean hasAggregateRating() {
         return this.hasAggregateRating;
      }

      public boolean hasAvailability() {
         return this.hasAvailability;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public boolean hasFetchDocid() {
         return this.hasFetchDocid;
      }

      public boolean hasPriceDeprecated() {
         return this.hasPriceDeprecated;
      }

      public boolean hasSampleDocid() {
         return this.hasSampleDocid;
      }

      public boolean hasTitle() {
         return this.hasTitle;
      }

      public boolean hasUrl() {
         return this.hasUrl;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasDocid && this.getDocid().isInitialized() && (!this.hasFetchDocid() || this.getFetchDocid().isInitialized()) && (!this.hasSampleDocid() || this.getSampleDocid().isInitialized())) {
            Iterator var2 = this.getTranslatedSnippetList().iterator();

            do {
               if(!var2.hasNext()) {
                  if(this.hasPriceDeprecated() && !this.getPriceDeprecated().isInitialized()) {
                     break;
                  }

                  var2 = this.getOfferList().iterator();

                  while(var2.hasNext()) {
                     if(!((Common.Offer)var2.next()).isInitialized()) {
                        return var1;
                     }
                  }

                  if(this.hasAvailability() && !this.getAvailability().isInitialized()) {
                     break;
                  }

                  var2 = this.getImageList().iterator();

                  while(var2.hasNext()) {
                     if(!((Doc.Image)var2.next()).isInitialized()) {
                        return var1;
                     }
                  }

                  var2 = this.getChildList().iterator();

                  while(var2.hasNext()) {
                     if(!((Doc.Document)var2.next()).isInitialized()) {
                        return var1;
                     }
                  }

                  if(this.hasAggregateRating() && !this.getAggregateRating().isInitialized()) {
                     break;
                  }

                  var2 = this.getDocumentVariantList().iterator();

                  while(var2.hasNext()) {
                     if(!((Doc.DocumentVariant)var2.next()).isInitialized()) {
                        return var1;
                     }
                  }

                  var1 = true;
                  break;
               }
            } while(((Doc.TranslatedText)var2.next()).isInitialized());
         }

         return var1;
      }

      public Doc.Document mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Common.Docid var3 = new Common.Docid();
               var1.readMessage(var3);
               this.setDocid(var3);
               break;
            case 18:
               Common.Docid var5 = new Common.Docid();
               var1.readMessage(var5);
               this.setFetchDocid(var5);
               break;
            case 26:
               Common.Docid var7 = new Common.Docid();
               var1.readMessage(var7);
               this.setSampleDocid(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setTitle(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setUrl(var11);
               break;
            case 50:
               String var13 = var1.readString();
               this.addSnippet(var13);
               break;
            case 58:
               Common.Offer var15 = new Common.Offer();
               var1.readMessage(var15);
               this.setPriceDeprecated(var15);
               break;
            case 74:
               FilterRules.Availability var17 = new FilterRules.Availability();
               var1.readMessage(var17);
               this.setAvailability(var17);
               break;
            case 82:
               Doc.Image var19 = new Doc.Image();
               var1.readMessage(var19);
               this.addImage(var19);
               break;
            case 90:
               Doc.Document var21 = new Doc.Document();
               var1.readMessage(var21);
               this.addChild(var21);
               break;
            case 106:
               Rating.AggregateRating var23 = new Rating.AggregateRating();
               var1.readMessage(var23);
               this.setAggregateRating(var23);
               break;
            case 114:
               Common.Offer var25 = new Common.Offer();
               var1.readMessage(var25);
               this.addOffer(var25);
               break;
            case 122:
               Doc.TranslatedText var27 = new Doc.TranslatedText();
               var1.readMessage(var27);
               this.addTranslatedSnippet(var27);
               break;
            case 130:
               Doc.DocumentVariant var29 = new Doc.DocumentVariant();
               var1.readMessage(var29);
               this.addDocumentVariant(var29);
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

      public Doc.Document setAggregateRating(Rating.AggregateRating var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasAggregateRating = (boolean)1;
            this.aggregateRating_ = var1;
            return this;
         }
      }

      public Doc.Document setAvailability(FilterRules.Availability var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasAvailability = (boolean)1;
            this.availability_ = var1;
            return this;
         }
      }

      public Doc.Document setChild(int var1, Doc.Document var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.child_.set(var1, var2);
            return this;
         }
      }

      public Doc.Document setDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDocid = (boolean)1;
            this.docid_ = var1;
            return this;
         }
      }

      public Doc.Document setDocumentVariant(int var1, Doc.DocumentVariant var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.documentVariant_.set(var1, var2);
            return this;
         }
      }

      public Doc.Document setFetchDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasFetchDocid = (boolean)1;
            this.fetchDocid_ = var1;
            return this;
         }
      }

      public Doc.Document setImage(int var1, Doc.Image var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.image_.set(var1, var2);
            return this;
         }
      }

      public Doc.Document setOffer(int var1, Common.Offer var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.offer_.set(var1, var2);
            return this;
         }
      }

      public Doc.Document setPriceDeprecated(Common.Offer var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPriceDeprecated = (boolean)1;
            this.priceDeprecated_ = var1;
            return this;
         }
      }

      public Doc.Document setSampleDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasSampleDocid = (boolean)1;
            this.sampleDocid_ = var1;
            return this;
         }
      }

      public Doc.Document setSnippet(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.snippet_.set(var1, var2);
            return this;
         }
      }

      public Doc.Document setTitle(String var1) {
         this.hasTitle = (boolean)1;
         this.title_ = var1;
         return this;
      }

      public Doc.Document setTranslatedSnippet(int var1, Doc.TranslatedText var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.translatedSnippet_.set(var1, var2);
            return this;
         }
      }

      public Doc.Document setUrl(String var1) {
         this.hasUrl = (boolean)1;
         this.url_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasDocid()) {
            Common.Docid var2 = this.getDocid();
            var1.writeMessage(1, var2);
         }

         if(this.hasFetchDocid()) {
            Common.Docid var3 = this.getFetchDocid();
            var1.writeMessage(2, var3);
         }

         if(this.hasSampleDocid()) {
            Common.Docid var4 = this.getSampleDocid();
            var1.writeMessage(3, var4);
         }

         if(this.hasTitle()) {
            String var5 = this.getTitle();
            var1.writeString(4, var5);
         }

         if(this.hasUrl()) {
            String var6 = this.getUrl();
            var1.writeString(5, var6);
         }

         Iterator var7 = this.getSnippetList().iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            var1.writeString(6, var8);
         }

         if(this.hasPriceDeprecated()) {
            Common.Offer var9 = this.getPriceDeprecated();
            var1.writeMessage(7, var9);
         }

         if(this.hasAvailability()) {
            FilterRules.Availability var10 = this.getAvailability();
            var1.writeMessage(9, var10);
         }

         Iterator var11 = this.getImageList().iterator();

         while(var11.hasNext()) {
            Doc.Image var12 = (Doc.Image)var11.next();
            var1.writeMessage(10, var12);
         }

         Iterator var13 = this.getChildList().iterator();

         while(var13.hasNext()) {
            Doc.Document var14 = (Doc.Document)var13.next();
            var1.writeMessage(11, var14);
         }

         if(this.hasAggregateRating()) {
            Rating.AggregateRating var15 = this.getAggregateRating();
            var1.writeMessage(13, var15);
         }

         Iterator var16 = this.getOfferList().iterator();

         while(var16.hasNext()) {
            Common.Offer var17 = (Common.Offer)var16.next();
            var1.writeMessage(14, var17);
         }

         Iterator var18 = this.getTranslatedSnippetList().iterator();

         while(var18.hasNext()) {
            Doc.TranslatedText var19 = (Doc.TranslatedText)var18.next();
            var1.writeMessage(15, var19);
         }

         Iterator var20 = this.getDocumentVariantList().iterator();

         while(var20.hasNext()) {
            Doc.DocumentVariant var21 = (Doc.DocumentVariant)var20.next();
            var1.writeMessage(16, var21);
         }

      }
   }

   public static final class DocumentOwnership extends MessageMicro {

      public static final int OWNER_GAIA_ID_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private boolean hasOwnerGaiaId;
      private long ownerGaiaId_ = 0L;


      public DocumentOwnership() {}

      public static Doc.DocumentOwnership parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Doc.DocumentOwnership()).mergeFrom(var0);
      }

      public static Doc.DocumentOwnership parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Doc.DocumentOwnership)((Doc.DocumentOwnership)(new Doc.DocumentOwnership()).mergeFrom(var0));
      }

      public final Doc.DocumentOwnership clear() {
         Doc.DocumentOwnership var1 = this.clearOwnerGaiaId();
         this.cachedSize = -1;
         return this;
      }

      public Doc.DocumentOwnership clearOwnerGaiaId() {
         this.hasOwnerGaiaId = (boolean)0;
         this.ownerGaiaId_ = 0L;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public long getOwnerGaiaId() {
         return this.ownerGaiaId_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasOwnerGaiaId()) {
            long var2 = this.getOwnerGaiaId();
            int var4 = CodedOutputStreamMicro.computeFixed64Size(1, var2);
            var1 = 0 + var4;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasOwnerGaiaId() {
         return this.hasOwnerGaiaId;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Doc.DocumentOwnership mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 9:
               long var3 = var1.readFixed64();
               this.setOwnerGaiaId(var3);
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

      public Doc.DocumentOwnership setOwnerGaiaId(long var1) {
         this.hasOwnerGaiaId = (boolean)1;
         this.ownerGaiaId_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasOwnerGaiaId()) {
            long var2 = this.getOwnerGaiaId();
            var1.writeFixed64(1, var2);
         }
      }
   }

   public static final class DocumentSet extends MessageMicro {

      public static final int DOCUMENT_FIELD_NUMBER = 1;
      private int cachedSize;
      private List<Doc.Document> document_;


      public DocumentSet() {
         List var1 = Collections.emptyList();
         this.document_ = var1;
         this.cachedSize = -1;
      }

      public static Doc.DocumentSet parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Doc.DocumentSet()).mergeFrom(var0);
      }

      public static Doc.DocumentSet parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Doc.DocumentSet)((Doc.DocumentSet)(new Doc.DocumentSet()).mergeFrom(var0));
      }

      public Doc.DocumentSet addDocument(Doc.Document var1) {
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

      public final Doc.DocumentSet clear() {
         Doc.DocumentSet var1 = this.clearDocument();
         this.cachedSize = -1;
         return this;
      }

      public Doc.DocumentSet clearDocument() {
         List var1 = Collections.emptyList();
         this.document_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Doc.Document getDocument(int var1) {
         return (Doc.Document)this.document_.get(var1);
      }

      public int getDocumentCount() {
         return this.document_.size();
      }

      public List<Doc.Document> getDocumentList() {
         return this.document_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getDocumentList().iterator(); var2.hasNext(); var1 += var4) {
            Doc.Document var3 = (Doc.Document)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(1, var3);
         }

         this.cachedSize = var1;
         return var1;
      }

      public final boolean isInitialized() {
         Iterator var1 = this.getDocumentList().iterator();

         boolean var2;
         while(true) {
            if(var1.hasNext()) {
               if(((Doc.Document)var1.next()).isInitialized()) {
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

      public Doc.DocumentSet mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Doc.Document var3 = new Doc.Document();
               var1.readMessage(var3);
               this.addDocument(var3);
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

      public Doc.DocumentSet setDocument(int var1, Doc.Document var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.document_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getDocumentList().iterator();

         while(var2.hasNext()) {
            Doc.Document var3 = (Doc.Document)var2.next();
            var1.writeMessage(1, var3);
         }

      }
   }

   public static final class DocumentVariant extends MessageMicro {

      public static final int AUTO_TRANSLATION_FIELD_NUMBER = 6;
      public static final int CHANNEL_ID_FIELD_NUMBER = 9;
      public static final int MULTI_APK = 3;
      public static final int OFFER = 2;
      public static final int OFFER_FIELD_NUMBER = 7;
      public static final int RECENT_CHANGES_FIELD_NUMBER = 5;
      public static final int RULE_FIELD_NUMBER = 2;
      public static final int SNIPPET_FIELD_NUMBER = 4;
      public static final int TEXT = 1;
      public static final int TITLE_FIELD_NUMBER = 3;
      public static final int VARIATION_TYPE_FIELD_NUMBER = 1;
      private List<Doc.TranslatedText> autoTranslation_;
      private int cachedSize;
      private long channelId_;
      private boolean hasChannelId;
      private boolean hasRecentChanges;
      private boolean hasRule;
      private boolean hasTitle;
      private boolean hasVariationType;
      private List<Common.Offer> offer_;
      private String recentChanges_;
      private FilterRules.Rule rule_ = null;
      private List<String> snippet_;
      private String title_ = "";
      private int variationType_ = 1;


      public DocumentVariant() {
         List var1 = Collections.emptyList();
         this.snippet_ = var1;
         this.recentChanges_ = "";
         List var2 = Collections.emptyList();
         this.autoTranslation_ = var2;
         List var3 = Collections.emptyList();
         this.offer_ = var3;
         this.channelId_ = 0L;
         this.cachedSize = -1;
      }

      public static Doc.DocumentVariant parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Doc.DocumentVariant()).mergeFrom(var0);
      }

      public static Doc.DocumentVariant parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Doc.DocumentVariant)((Doc.DocumentVariant)(new Doc.DocumentVariant()).mergeFrom(var0));
      }

      public Doc.DocumentVariant addAutoTranslation(Doc.TranslatedText var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.autoTranslation_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.autoTranslation_ = var2;
            }

            this.autoTranslation_.add(var1);
            return this;
         }
      }

      public Doc.DocumentVariant addOffer(Common.Offer var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.offer_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.offer_ = var2;
            }

            this.offer_.add(var1);
            return this;
         }
      }

      public Doc.DocumentVariant addSnippet(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.snippet_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.snippet_ = var2;
            }

            this.snippet_.add(var1);
            return this;
         }
      }

      public final Doc.DocumentVariant clear() {
         Doc.DocumentVariant var1 = this.clearVariationType();
         Doc.DocumentVariant var2 = this.clearRule();
         Doc.DocumentVariant var3 = this.clearTitle();
         Doc.DocumentVariant var4 = this.clearSnippet();
         Doc.DocumentVariant var5 = this.clearRecentChanges();
         Doc.DocumentVariant var6 = this.clearAutoTranslation();
         Doc.DocumentVariant var7 = this.clearOffer();
         Doc.DocumentVariant var8 = this.clearChannelId();
         this.cachedSize = -1;
         return this;
      }

      public Doc.DocumentVariant clearAutoTranslation() {
         List var1 = Collections.emptyList();
         this.autoTranslation_ = var1;
         return this;
      }

      public Doc.DocumentVariant clearChannelId() {
         this.hasChannelId = (boolean)0;
         this.channelId_ = 0L;
         return this;
      }

      public Doc.DocumentVariant clearOffer() {
         List var1 = Collections.emptyList();
         this.offer_ = var1;
         return this;
      }

      public Doc.DocumentVariant clearRecentChanges() {
         this.hasRecentChanges = (boolean)0;
         this.recentChanges_ = "";
         return this;
      }

      public Doc.DocumentVariant clearRule() {
         this.hasRule = (boolean)0;
         this.rule_ = null;
         return this;
      }

      public Doc.DocumentVariant clearSnippet() {
         List var1 = Collections.emptyList();
         this.snippet_ = var1;
         return this;
      }

      public Doc.DocumentVariant clearTitle() {
         this.hasTitle = (boolean)0;
         this.title_ = "";
         return this;
      }

      public Doc.DocumentVariant clearVariationType() {
         this.hasVariationType = (boolean)0;
         this.variationType_ = 1;
         return this;
      }

      public Doc.TranslatedText getAutoTranslation(int var1) {
         return (Doc.TranslatedText)this.autoTranslation_.get(var1);
      }

      public int getAutoTranslationCount() {
         return this.autoTranslation_.size();
      }

      public List<Doc.TranslatedText> getAutoTranslationList() {
         return this.autoTranslation_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public long getChannelId() {
         return this.channelId_;
      }

      public Common.Offer getOffer(int var1) {
         return (Common.Offer)this.offer_.get(var1);
      }

      public int getOfferCount() {
         return this.offer_.size();
      }

      public List<Common.Offer> getOfferList() {
         return this.offer_;
      }

      public String getRecentChanges() {
         return this.recentChanges_;
      }

      public FilterRules.Rule getRule() {
         return this.rule_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasVariationType()) {
            int var2 = this.getVariationType();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasRule()) {
            FilterRules.Rule var4 = this.getRule();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         if(this.hasTitle()) {
            String var6 = this.getTitle();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         int var8 = 0;

         int var10;
         for(Iterator var9 = this.getSnippetList().iterator(); var9.hasNext(); var8 += var10) {
            var10 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var9.next());
         }

         int var11 = var1 + var8;
         int var12 = this.getSnippetList().size() * 1;
         int var13 = var11 + var12;
         if(this.hasRecentChanges()) {
            String var14 = this.getRecentChanges();
            int var15 = CodedOutputStreamMicro.computeStringSize(5, var14);
            var13 += var15;
         }

         int var18;
         for(Iterator var16 = this.getAutoTranslationList().iterator(); var16.hasNext(); var13 += var18) {
            Doc.TranslatedText var17 = (Doc.TranslatedText)var16.next();
            var18 = CodedOutputStreamMicro.computeMessageSize(6, var17);
         }

         int var21;
         for(Iterator var19 = this.getOfferList().iterator(); var19.hasNext(); var13 += var21) {
            Common.Offer var20 = (Common.Offer)var19.next();
            var21 = CodedOutputStreamMicro.computeMessageSize(7, var20);
         }

         if(this.hasChannelId()) {
            long var22 = this.getChannelId();
            int var24 = CodedOutputStreamMicro.computeInt64Size(9, var22);
            var13 += var24;
         }

         this.cachedSize = var13;
         return var13;
      }

      public String getSnippet(int var1) {
         return (String)this.snippet_.get(var1);
      }

      public int getSnippetCount() {
         return this.snippet_.size();
      }

      public List<String> getSnippetList() {
         return this.snippet_;
      }

      public String getTitle() {
         return this.title_;
      }

      public int getVariationType() {
         return this.variationType_;
      }

      public boolean hasChannelId() {
         return this.hasChannelId;
      }

      public boolean hasRecentChanges() {
         return this.hasRecentChanges;
      }

      public boolean hasRule() {
         return this.hasRule;
      }

      public boolean hasTitle() {
         return this.hasTitle;
      }

      public boolean hasVariationType() {
         return this.hasVariationType;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasVariationType && (!this.hasRule() || this.getRule().isInitialized())) {
            Iterator var2 = this.getAutoTranslationList().iterator();

            do {
               if(!var2.hasNext()) {
                  var2 = this.getOfferList().iterator();

                  while(var2.hasNext()) {
                     if(!((Common.Offer)var2.next()).isInitialized()) {
                        return var1;
                     }
                  }

                  var1 = true;
                  break;
               }
            } while(((Doc.TranslatedText)var2.next()).isInitialized());
         }

         return var1;
      }

      public Doc.DocumentVariant mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setVariationType(var3);
               break;
            case 18:
               FilterRules.Rule var5 = new FilterRules.Rule();
               var1.readMessage(var5);
               this.setRule(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setTitle(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.addSnippet(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setRecentChanges(var11);
               break;
            case 50:
               Doc.TranslatedText var13 = new Doc.TranslatedText();
               var1.readMessage(var13);
               this.addAutoTranslation(var13);
               break;
            case 58:
               Common.Offer var15 = new Common.Offer();
               var1.readMessage(var15);
               this.addOffer(var15);
               break;
            case 72:
               long var17 = var1.readInt64();
               this.setChannelId(var17);
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

      public Doc.DocumentVariant setAutoTranslation(int var1, Doc.TranslatedText var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.autoTranslation_.set(var1, var2);
            return this;
         }
      }

      public Doc.DocumentVariant setChannelId(long var1) {
         this.hasChannelId = (boolean)1;
         this.channelId_ = var1;
         return this;
      }

      public Doc.DocumentVariant setOffer(int var1, Common.Offer var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.offer_.set(var1, var2);
            return this;
         }
      }

      public Doc.DocumentVariant setRecentChanges(String var1) {
         this.hasRecentChanges = (boolean)1;
         this.recentChanges_ = var1;
         return this;
      }

      public Doc.DocumentVariant setRule(FilterRules.Rule var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasRule = (boolean)1;
            this.rule_ = var1;
            return this;
         }
      }

      public Doc.DocumentVariant setSnippet(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.snippet_.set(var1, var2);
            return this;
         }
      }

      public Doc.DocumentVariant setTitle(String var1) {
         this.hasTitle = (boolean)1;
         this.title_ = var1;
         return this;
      }

      public Doc.DocumentVariant setVariationType(int var1) {
         this.hasVariationType = (boolean)1;
         this.variationType_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasVariationType()) {
            int var2 = this.getVariationType();
            var1.writeInt32(1, var2);
         }

         if(this.hasRule()) {
            FilterRules.Rule var3 = this.getRule();
            var1.writeMessage(2, var3);
         }

         if(this.hasTitle()) {
            String var4 = this.getTitle();
            var1.writeString(3, var4);
         }

         Iterator var5 = this.getSnippetList().iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            var1.writeString(4, var6);
         }

         if(this.hasRecentChanges()) {
            String var7 = this.getRecentChanges();
            var1.writeString(5, var7);
         }

         Iterator var8 = this.getAutoTranslationList().iterator();

         while(var8.hasNext()) {
            Doc.TranslatedText var9 = (Doc.TranslatedText)var8.next();
            var1.writeMessage(6, var9);
         }

         Iterator var10 = this.getOfferList().iterator();

         while(var10.hasNext()) {
            Common.Offer var11 = (Common.Offer)var10.next();
            var1.writeMessage(7, var11);
         }

         if(this.hasChannelId()) {
            long var12 = this.getChannelId();
            var1.writeInt64(9, var12);
         }
      }
   }

   public static final class Image extends MessageMicro {

      public static final int ALT_TEXT_LOCALIZED_FIELD_NUMBER = 6;
      public static final int BADGE_LIST = 6;
      public static final int BADGE_LIST_ANNOTATION = 5;
      public static final int DIMENSION_FIELD_NUMBER = 2;
      public static final int HIRES_PREVIEW = 4;
      public static final int IMAGE_TYPE_FIELD_NUMBER = 1;
      public static final int IMAGE_URL_FIELD_NUMBER = 5;
      public static final int PAGE_HEADER_BANNER = 8;
      public static final int PAGE_HEADER_ICON = 7;
      public static final int POSITION_IN_SEQUENCE_FIELD_NUMBER = 8;
      public static final int PREVIEW = 1;
      public static final int PROMOTIONAL = 2;
      public static final int SECURE_URL_FIELD_NUMBER = 7;
      public static final int THUMBNAIL = 0;
      public static final int VIDEO = 3;
      private String altTextLocalized_ = "";
      private int cachedSize = -1;
      private Doc.Image.Dimension dimension_ = null;
      private boolean hasAltTextLocalized;
      private boolean hasDimension;
      private boolean hasImageType;
      private boolean hasImageUrl;
      private boolean hasPositionInSequence;
      private boolean hasSecureUrl;
      private int imageType_ = 0;
      private String imageUrl_ = "";
      private int positionInSequence_ = 0;
      private String secureUrl_ = "";


      public Image() {}

      public static Doc.Image parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Doc.Image()).mergeFrom(var0);
      }

      public static Doc.Image parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Doc.Image)((Doc.Image)(new Doc.Image()).mergeFrom(var0));
      }

      public final Doc.Image clear() {
         Doc.Image var1 = this.clearImageType();
         Doc.Image var2 = this.clearPositionInSequence();
         Doc.Image var3 = this.clearDimension();
         Doc.Image var4 = this.clearImageUrl();
         Doc.Image var5 = this.clearSecureUrl();
         Doc.Image var6 = this.clearAltTextLocalized();
         this.cachedSize = -1;
         return this;
      }

      public Doc.Image clearAltTextLocalized() {
         this.hasAltTextLocalized = (boolean)0;
         this.altTextLocalized_ = "";
         return this;
      }

      public Doc.Image clearDimension() {
         this.hasDimension = (boolean)0;
         this.dimension_ = null;
         return this;
      }

      public Doc.Image clearImageType() {
         this.hasImageType = (boolean)0;
         this.imageType_ = 0;
         return this;
      }

      public Doc.Image clearImageUrl() {
         this.hasImageUrl = (boolean)0;
         this.imageUrl_ = "";
         return this;
      }

      public Doc.Image clearPositionInSequence() {
         this.hasPositionInSequence = (boolean)0;
         this.positionInSequence_ = 0;
         return this;
      }

      public Doc.Image clearSecureUrl() {
         this.hasSecureUrl = (boolean)0;
         this.secureUrl_ = "";
         return this;
      }

      public String getAltTextLocalized() {
         return this.altTextLocalized_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Doc.Image.Dimension getDimension() {
         return this.dimension_;
      }

      public int getImageType() {
         return this.imageType_;
      }

      public String getImageUrl() {
         return this.imageUrl_;
      }

      public int getPositionInSequence() {
         return this.positionInSequence_;
      }

      public String getSecureUrl() {
         return this.secureUrl_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasImageType()) {
            int var2 = this.getImageType();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasDimension()) {
            Doc.Image.Dimension var4 = this.getDimension();
            int var5 = CodedOutputStreamMicro.computeGroupSize(2, var4);
            var1 += var5;
         }

         if(this.hasImageUrl()) {
            String var6 = this.getImageUrl();
            int var7 = CodedOutputStreamMicro.computeStringSize(5, var6);
            var1 += var7;
         }

         if(this.hasAltTextLocalized()) {
            String var8 = this.getAltTextLocalized();
            int var9 = CodedOutputStreamMicro.computeStringSize(6, var8);
            var1 += var9;
         }

         if(this.hasSecureUrl()) {
            String var10 = this.getSecureUrl();
            int var11 = CodedOutputStreamMicro.computeStringSize(7, var10);
            var1 += var11;
         }

         if(this.hasPositionInSequence()) {
            int var12 = this.getPositionInSequence();
            int var13 = CodedOutputStreamMicro.computeInt32Size(8, var12);
            var1 += var13;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasAltTextLocalized() {
         return this.hasAltTextLocalized;
      }

      public boolean hasDimension() {
         return this.hasDimension;
      }

      public boolean hasImageType() {
         return this.hasImageType;
      }

      public boolean hasImageUrl() {
         return this.hasImageUrl;
      }

      public boolean hasPositionInSequence() {
         return this.hasPositionInSequence;
      }

      public boolean hasSecureUrl() {
         return this.hasSecureUrl;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasImageUrl && (!this.hasDimension() || this.getDimension().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public Doc.Image mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setImageType(var3);
               break;
            case 19:
               Doc.Image.Dimension var5 = new Doc.Image.Dimension();
               var1.readGroup(var5, 2);
               this.setDimension(var5);
               break;
            case 42:
               String var7 = var1.readString();
               this.setImageUrl(var7);
               break;
            case 50:
               String var9 = var1.readString();
               this.setAltTextLocalized(var9);
               break;
            case 58:
               String var11 = var1.readString();
               this.setSecureUrl(var11);
               break;
            case 64:
               int var13 = var1.readInt32();
               this.setPositionInSequence(var13);
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

      public Doc.Image setAltTextLocalized(String var1) {
         this.hasAltTextLocalized = (boolean)1;
         this.altTextLocalized_ = var1;
         return this;
      }

      public Doc.Image setDimension(Doc.Image.Dimension var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDimension = (boolean)1;
            this.dimension_ = var1;
            return this;
         }
      }

      public Doc.Image setImageType(int var1) {
         this.hasImageType = (boolean)1;
         this.imageType_ = var1;
         return this;
      }

      public Doc.Image setImageUrl(String var1) {
         this.hasImageUrl = (boolean)1;
         this.imageUrl_ = var1;
         return this;
      }

      public Doc.Image setPositionInSequence(int var1) {
         this.hasPositionInSequence = (boolean)1;
         this.positionInSequence_ = var1;
         return this;
      }

      public Doc.Image setSecureUrl(String var1) {
         this.hasSecureUrl = (boolean)1;
         this.secureUrl_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasImageType()) {
            int var2 = this.getImageType();
            var1.writeInt32(1, var2);
         }

         if(this.hasDimension()) {
            Doc.Image.Dimension var3 = this.getDimension();
            var1.writeGroup(2, var3);
         }

         if(this.hasImageUrl()) {
            String var4 = this.getImageUrl();
            var1.writeString(5, var4);
         }

         if(this.hasAltTextLocalized()) {
            String var5 = this.getAltTextLocalized();
            var1.writeString(6, var5);
         }

         if(this.hasSecureUrl()) {
            String var6 = this.getSecureUrl();
            var1.writeString(7, var6);
         }

         if(this.hasPositionInSequence()) {
            int var7 = this.getPositionInSequence();
            var1.writeInt32(8, var7);
         }
      }

      public static final class Dimension extends MessageMicro {

         public static final int HEIGHT_FIELD_NUMBER = 4;
         public static final int WIDTH_FIELD_NUMBER = 3;
         private int cachedSize = -1;
         private boolean hasHeight;
         private boolean hasWidth;
         private int height_ = 0;
         private int width_ = 0;


         public Dimension() {}

         public final Doc.Image.Dimension clear() {
            Doc.Image.Dimension var1 = this.clearWidth();
            Doc.Image.Dimension var2 = this.clearHeight();
            this.cachedSize = -1;
            return this;
         }

         public Doc.Image.Dimension clearHeight() {
            this.hasHeight = (boolean)0;
            this.height_ = 0;
            return this;
         }

         public Doc.Image.Dimension clearWidth() {
            this.hasWidth = (boolean)0;
            this.width_ = 0;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public int getHeight() {
            return this.height_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasWidth()) {
               int var2 = this.getWidth();
               int var3 = CodedOutputStreamMicro.computeInt32Size(3, var2);
               var1 = 0 + var3;
            }

            if(this.hasHeight()) {
               int var4 = this.getHeight();
               int var5 = CodedOutputStreamMicro.computeInt32Size(4, var4);
               var1 += var5;
            }

            this.cachedSize = var1;
            return var1;
         }

         public int getWidth() {
            return this.width_;
         }

         public boolean hasHeight() {
            return this.hasHeight;
         }

         public boolean hasWidth() {
            return this.hasWidth;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if(this.hasWidth && this.hasHeight) {
               var1 = true;
            }

            return var1;
         }

         public Doc.Image.Dimension mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 24:
                  int var3 = var1.readInt32();
                  this.setWidth(var3);
                  break;
               case 32:
                  int var5 = var1.readInt32();
                  this.setHeight(var5);
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

         public Doc.Image.Dimension parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new Doc.Image.Dimension()).mergeFrom(var1);
         }

         public Doc.Image.Dimension parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (Doc.Image.Dimension)((Doc.Image.Dimension)(new Doc.Image.Dimension()).mergeFrom(var1));
         }

         public Doc.Image.Dimension setHeight(int var1) {
            this.hasHeight = (boolean)1;
            this.height_ = var1;
            return this;
         }

         public Doc.Image.Dimension setWidth(int var1) {
            this.hasWidth = (boolean)1;
            this.width_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasWidth()) {
               int var2 = this.getWidth();
               var1.writeInt32(3, var2);
            }

            if(this.hasHeight()) {
               int var3 = this.getHeight();
               var1.writeInt32(4, var3);
            }
         }
      }
   }

   public static final class BoundedDocument extends MessageMicro {

      public static final int CONTENT_FIELD_NUMBER = 1;
      private int cachedSize;
      private List<Doc.BoundedDocument.Content> content_;


      public BoundedDocument() {
         List var1 = Collections.emptyList();
         this.content_ = var1;
         this.cachedSize = -1;
      }

      public static Doc.BoundedDocument parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Doc.BoundedDocument()).mergeFrom(var0);
      }

      public static Doc.BoundedDocument parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Doc.BoundedDocument)((Doc.BoundedDocument)(new Doc.BoundedDocument()).mergeFrom(var0));
      }

      public Doc.BoundedDocument addContent(Doc.BoundedDocument.Content var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.content_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.content_ = var2;
            }

            this.content_.add(var1);
            return this;
         }
      }

      public final Doc.BoundedDocument clear() {
         Doc.BoundedDocument var1 = this.clearContent();
         this.cachedSize = -1;
         return this;
      }

      public Doc.BoundedDocument clearContent() {
         List var1 = Collections.emptyList();
         this.content_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Doc.BoundedDocument.Content getContent(int var1) {
         return (Doc.BoundedDocument.Content)this.content_.get(var1);
      }

      public int getContentCount() {
         return this.content_.size();
      }

      public List<Doc.BoundedDocument.Content> getContentList() {
         return this.content_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getContentList().iterator(); var2.hasNext(); var1 += var4) {
            Doc.BoundedDocument.Content var3 = (Doc.BoundedDocument.Content)var2.next();
            var4 = CodedOutputStreamMicro.computeGroupSize(1, var3);
         }

         this.cachedSize = var1;
         return var1;
      }

      public final boolean isInitialized() {
         Iterator var1 = this.getContentList().iterator();

         boolean var2;
         while(true) {
            if(var1.hasNext()) {
               if(((Doc.BoundedDocument.Content)var1.next()).isInitialized()) {
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

      public Doc.BoundedDocument mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 11:
               Doc.BoundedDocument.Content var3 = new Doc.BoundedDocument.Content();
               var1.readGroup(var3, 1);
               this.addContent(var3);
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

      public Doc.BoundedDocument setContent(int var1, Doc.BoundedDocument.Content var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.content_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getContentList().iterator();

         while(var2.hasNext()) {
            Doc.BoundedDocument.Content var3 = (Doc.BoundedDocument.Content)var2.next();
            var1.writeGroup(1, var3);
         }

      }

      public static final class Content extends MessageMicro {

         public static final int DOCUMENT_FIELD_NUMBER = 2;
         public static final int END_TIME_MILLIS_FIELD_NUMBER = 4;
         public static final int START_TIME_MILLIS_FIELD_NUMBER = 3;
         private int cachedSize = -1;
         private Doc.Document document_ = null;
         private long endTimeMillis_ = 0L;
         private boolean hasDocument;
         private boolean hasEndTimeMillis;
         private boolean hasStartTimeMillis;
         private long startTimeMillis_ = 0L;


         public Content() {}

         public final Doc.BoundedDocument.Content clear() {
            Doc.BoundedDocument.Content var1 = this.clearDocument();
            Doc.BoundedDocument.Content var2 = this.clearStartTimeMillis();
            Doc.BoundedDocument.Content var3 = this.clearEndTimeMillis();
            this.cachedSize = -1;
            return this;
         }

         public Doc.BoundedDocument.Content clearDocument() {
            this.hasDocument = (boolean)0;
            this.document_ = null;
            return this;
         }

         public Doc.BoundedDocument.Content clearEndTimeMillis() {
            this.hasEndTimeMillis = (boolean)0;
            this.endTimeMillis_ = 0L;
            return this;
         }

         public Doc.BoundedDocument.Content clearStartTimeMillis() {
            this.hasStartTimeMillis = (boolean)0;
            this.startTimeMillis_ = 0L;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public Doc.Document getDocument() {
            return this.document_;
         }

         public long getEndTimeMillis() {
            return this.endTimeMillis_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasDocument()) {
               Doc.Document var2 = this.getDocument();
               int var3 = CodedOutputStreamMicro.computeMessageSize(2, var2);
               var1 = 0 + var3;
            }

            if(this.hasStartTimeMillis()) {
               long var4 = this.getStartTimeMillis();
               int var6 = CodedOutputStreamMicro.computeInt64Size(3, var4);
               var1 += var6;
            }

            if(this.hasEndTimeMillis()) {
               long var7 = this.getEndTimeMillis();
               int var9 = CodedOutputStreamMicro.computeInt64Size(4, var7);
               var1 += var9;
            }

            this.cachedSize = var1;
            return var1;
         }

         public long getStartTimeMillis() {
            return this.startTimeMillis_;
         }

         public boolean hasDocument() {
            return this.hasDocument;
         }

         public boolean hasEndTimeMillis() {
            return this.hasEndTimeMillis;
         }

         public boolean hasStartTimeMillis() {
            return this.hasStartTimeMillis;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if(this.hasDocument && this.getDocument().isInitialized()) {
               var1 = true;
            }

            return var1;
         }

         public Doc.BoundedDocument.Content mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 18:
                  Doc.Document var3 = new Doc.Document();
                  var1.readMessage(var3);
                  this.setDocument(var3);
                  break;
               case 24:
                  long var5 = var1.readInt64();
                  this.setStartTimeMillis(var5);
                  break;
               case 32:
                  long var8 = var1.readInt64();
                  this.setEndTimeMillis(var8);
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

         public Doc.BoundedDocument.Content parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new Doc.BoundedDocument.Content()).mergeFrom(var1);
         }

         public Doc.BoundedDocument.Content parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (Doc.BoundedDocument.Content)((Doc.BoundedDocument.Content)(new Doc.BoundedDocument.Content()).mergeFrom(var1));
         }

         public Doc.BoundedDocument.Content setDocument(Doc.Document var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasDocument = (boolean)1;
               this.document_ = var1;
               return this;
            }
         }

         public Doc.BoundedDocument.Content setEndTimeMillis(long var1) {
            this.hasEndTimeMillis = (boolean)1;
            this.endTimeMillis_ = var1;
            return this;
         }

         public Doc.BoundedDocument.Content setStartTimeMillis(long var1) {
            this.hasStartTimeMillis = (boolean)1;
            this.startTimeMillis_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasDocument()) {
               Doc.Document var2 = this.getDocument();
               var1.writeMessage(2, var2);
            }

            if(this.hasStartTimeMillis()) {
               long var3 = this.getStartTimeMillis();
               var1.writeInt64(3, var3);
            }

            if(this.hasEndTimeMillis()) {
               long var5 = this.getEndTimeMillis();
               var1.writeInt64(4, var5);
            }
         }
      }
   }

   public static final class TranslatedText extends MessageMicro {

      public static final int SOURCE_LOCALE_FIELD_NUMBER = 2;
      public static final int TARGET_LOCALE_FIELD_NUMBER = 3;
      public static final int TEXT_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private boolean hasSourceLocale;
      private boolean hasTargetLocale;
      private boolean hasText;
      private String sourceLocale_ = "";
      private String targetLocale_ = "";
      private String text_ = "";


      public TranslatedText() {}

      public static Doc.TranslatedText parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Doc.TranslatedText()).mergeFrom(var0);
      }

      public static Doc.TranslatedText parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Doc.TranslatedText)((Doc.TranslatedText)(new Doc.TranslatedText()).mergeFrom(var0));
      }

      public final Doc.TranslatedText clear() {
         Doc.TranslatedText var1 = this.clearText();
         Doc.TranslatedText var2 = this.clearSourceLocale();
         Doc.TranslatedText var3 = this.clearTargetLocale();
         this.cachedSize = -1;
         return this;
      }

      public Doc.TranslatedText clearSourceLocale() {
         this.hasSourceLocale = (boolean)0;
         this.sourceLocale_ = "";
         return this;
      }

      public Doc.TranslatedText clearTargetLocale() {
         this.hasTargetLocale = (boolean)0;
         this.targetLocale_ = "";
         return this;
      }

      public Doc.TranslatedText clearText() {
         this.hasText = (boolean)0;
         this.text_ = "";
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
         if(this.hasText()) {
            String var2 = this.getText();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasSourceLocale()) {
            String var4 = this.getSourceLocale();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasTargetLocale()) {
            String var6 = this.getTargetLocale();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getSourceLocale() {
         return this.sourceLocale_;
      }

      public String getTargetLocale() {
         return this.targetLocale_;
      }

      public String getText() {
         return this.text_;
      }

      public boolean hasSourceLocale() {
         return this.hasSourceLocale;
      }

      public boolean hasTargetLocale() {
         return this.hasTargetLocale;
      }

      public boolean hasText() {
         return this.hasText;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasSourceLocale && this.hasTargetLocale) {
            var1 = true;
         }

         return var1;
      }

      public Doc.TranslatedText mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setText(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setSourceLocale(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setTargetLocale(var7);
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

      public Doc.TranslatedText setSourceLocale(String var1) {
         this.hasSourceLocale = (boolean)1;
         this.sourceLocale_ = var1;
         return this;
      }

      public Doc.TranslatedText setTargetLocale(String var1) {
         this.hasTargetLocale = (boolean)1;
         this.targetLocale_ = var1;
         return this;
      }

      public Doc.TranslatedText setText(String var1) {
         this.hasText = (boolean)1;
         this.text_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasText()) {
            String var2 = this.getText();
            var1.writeString(1, var2);
         }

         if(this.hasSourceLocale()) {
            String var3 = this.getSourceLocale();
            var1.writeString(2, var3);
         }

         if(this.hasTargetLocale()) {
            String var4 = this.getTargetLocale();
            var1.writeString(3, var4);
         }
      }
   }
}
