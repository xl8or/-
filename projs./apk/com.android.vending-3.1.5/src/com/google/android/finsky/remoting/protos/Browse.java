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

public final class Browse {

   private Browse() {}

   public static final class BrowseResponse extends MessageMicro {

      public static final int BREADCRUMB_FIELD_NUMBER = 4;
      public static final int CATEGORY_FIELD_NUMBER = 3;
      public static final int CONTENTS_URL_FIELD_NUMBER = 1;
      public static final int PROMO_URL_FIELD_NUMBER = 2;
      private List<Browse.BrowseLink> breadcrumb_;
      private int cachedSize;
      private List<Browse.BrowseLink> category_;
      private String contentsUrl_ = "";
      private boolean hasContentsUrl;
      private boolean hasPromoUrl;
      private String promoUrl_ = "";


      public BrowseResponse() {
         List var1 = Collections.emptyList();
         this.category_ = var1;
         List var2 = Collections.emptyList();
         this.breadcrumb_ = var2;
         this.cachedSize = -1;
      }

      public static Browse.BrowseResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Browse.BrowseResponse()).mergeFrom(var0);
      }

      public static Browse.BrowseResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Browse.BrowseResponse)((Browse.BrowseResponse)(new Browse.BrowseResponse()).mergeFrom(var0));
      }

      public Browse.BrowseResponse addBreadcrumb(Browse.BrowseLink var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.breadcrumb_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.breadcrumb_ = var2;
            }

            this.breadcrumb_.add(var1);
            return this;
         }
      }

      public Browse.BrowseResponse addCategory(Browse.BrowseLink var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.category_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.category_ = var2;
            }

            this.category_.add(var1);
            return this;
         }
      }

      public final Browse.BrowseResponse clear() {
         Browse.BrowseResponse var1 = this.clearContentsUrl();
         Browse.BrowseResponse var2 = this.clearPromoUrl();
         Browse.BrowseResponse var3 = this.clearCategory();
         Browse.BrowseResponse var4 = this.clearBreadcrumb();
         this.cachedSize = -1;
         return this;
      }

      public Browse.BrowseResponse clearBreadcrumb() {
         List var1 = Collections.emptyList();
         this.breadcrumb_ = var1;
         return this;
      }

      public Browse.BrowseResponse clearCategory() {
         List var1 = Collections.emptyList();
         this.category_ = var1;
         return this;
      }

      public Browse.BrowseResponse clearContentsUrl() {
         this.hasContentsUrl = (boolean)0;
         this.contentsUrl_ = "";
         return this;
      }

      public Browse.BrowseResponse clearPromoUrl() {
         this.hasPromoUrl = (boolean)0;
         this.promoUrl_ = "";
         return this;
      }

      public Browse.BrowseLink getBreadcrumb(int var1) {
         return (Browse.BrowseLink)this.breadcrumb_.get(var1);
      }

      public int getBreadcrumbCount() {
         return this.breadcrumb_.size();
      }

      public List<Browse.BrowseLink> getBreadcrumbList() {
         return this.breadcrumb_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Browse.BrowseLink getCategory(int var1) {
         return (Browse.BrowseLink)this.category_.get(var1);
      }

      public int getCategoryCount() {
         return this.category_.size();
      }

      public List<Browse.BrowseLink> getCategoryList() {
         return this.category_;
      }

      public String getContentsUrl() {
         return this.contentsUrl_;
      }

      public String getPromoUrl() {
         return this.promoUrl_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasContentsUrl()) {
            String var2 = this.getContentsUrl();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasPromoUrl()) {
            String var4 = this.getPromoUrl();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         int var8;
         for(Iterator var6 = this.getCategoryList().iterator(); var6.hasNext(); var1 += var8) {
            Browse.BrowseLink var7 = (Browse.BrowseLink)var6.next();
            var8 = CodedOutputStreamMicro.computeMessageSize(3, var7);
         }

         int var11;
         for(Iterator var9 = this.getBreadcrumbList().iterator(); var9.hasNext(); var1 += var11) {
            Browse.BrowseLink var10 = (Browse.BrowseLink)var9.next();
            var11 = CodedOutputStreamMicro.computeMessageSize(4, var10);
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasContentsUrl() {
         return this.hasContentsUrl;
      }

      public boolean hasPromoUrl() {
         return this.hasPromoUrl;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasContentsUrl) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Browse.BrowseResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setContentsUrl(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setPromoUrl(var5);
               break;
            case 26:
               Browse.BrowseLink var7 = new Browse.BrowseLink();
               var1.readMessage(var7);
               this.addCategory(var7);
               break;
            case 34:
               Browse.BrowseLink var9 = new Browse.BrowseLink();
               var1.readMessage(var9);
               this.addBreadcrumb(var9);
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

      public Browse.BrowseResponse setBreadcrumb(int var1, Browse.BrowseLink var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.breadcrumb_.set(var1, var2);
            return this;
         }
      }

      public Browse.BrowseResponse setCategory(int var1, Browse.BrowseLink var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.category_.set(var1, var2);
            return this;
         }
      }

      public Browse.BrowseResponse setContentsUrl(String var1) {
         this.hasContentsUrl = (boolean)1;
         this.contentsUrl_ = var1;
         return this;
      }

      public Browse.BrowseResponse setPromoUrl(String var1) {
         this.hasPromoUrl = (boolean)1;
         this.promoUrl_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasContentsUrl()) {
            String var2 = this.getContentsUrl();
            var1.writeString(1, var2);
         }

         if(this.hasPromoUrl()) {
            String var3 = this.getPromoUrl();
            var1.writeString(2, var3);
         }

         Iterator var4 = this.getCategoryList().iterator();

         while(var4.hasNext()) {
            Browse.BrowseLink var5 = (Browse.BrowseLink)var4.next();
            var1.writeMessage(3, var5);
         }

         Iterator var6 = this.getBreadcrumbList().iterator();

         while(var6.hasNext()) {
            Browse.BrowseLink var7 = (Browse.BrowseLink)var6.next();
            var1.writeMessage(4, var7);
         }

      }
   }

   public static final class BrowseLink extends MessageMicro {

      public static final int DATA_URL_FIELD_NUMBER = 3;
      public static final int ICON_URL_FIELD_NUMBER = 2;
      public static final int NAME_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private String dataUrl_ = "";
      private boolean hasDataUrl;
      private boolean hasIconUrl;
      private boolean hasName;
      private String iconUrl_ = "";
      private String name_ = "";


      public BrowseLink() {}

      public static Browse.BrowseLink parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Browse.BrowseLink()).mergeFrom(var0);
      }

      public static Browse.BrowseLink parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Browse.BrowseLink)((Browse.BrowseLink)(new Browse.BrowseLink()).mergeFrom(var0));
      }

      public final Browse.BrowseLink clear() {
         Browse.BrowseLink var1 = this.clearName();
         Browse.BrowseLink var2 = this.clearIconUrl();
         Browse.BrowseLink var3 = this.clearDataUrl();
         this.cachedSize = -1;
         return this;
      }

      public Browse.BrowseLink clearDataUrl() {
         this.hasDataUrl = (boolean)0;
         this.dataUrl_ = "";
         return this;
      }

      public Browse.BrowseLink clearIconUrl() {
         this.hasIconUrl = (boolean)0;
         this.iconUrl_ = "";
         return this;
      }

      public Browse.BrowseLink clearName() {
         this.hasName = (boolean)0;
         this.name_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getDataUrl() {
         return this.dataUrl_;
      }

      public String getIconUrl() {
         return this.iconUrl_;
      }

      public String getName() {
         return this.name_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasName()) {
            String var2 = this.getName();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasIconUrl()) {
            String var4 = this.getIconUrl();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasDataUrl()) {
            String var6 = this.getDataUrl();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasDataUrl() {
         return this.hasDataUrl;
      }

      public boolean hasIconUrl() {
         return this.hasIconUrl;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Browse.BrowseLink mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setName(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setIconUrl(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setDataUrl(var7);
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

      public Browse.BrowseLink setDataUrl(String var1) {
         this.hasDataUrl = (boolean)1;
         this.dataUrl_ = var1;
         return this;
      }

      public Browse.BrowseLink setIconUrl(String var1) {
         this.hasIconUrl = (boolean)1;
         this.iconUrl_ = var1;
         return this;
      }

      public Browse.BrowseLink setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasName()) {
            String var2 = this.getName();
            var1.writeString(1, var2);
         }

         if(this.hasIconUrl()) {
            String var3 = this.getIconUrl();
            var1.writeString(2, var3);
         }

         if(this.hasDataUrl()) {
            String var4 = this.getDataUrl();
            var1.writeString(3, var4);
         }
      }
   }
}
