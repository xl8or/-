package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Common;
import com.google.android.finsky.remoting.protos.UserLocale;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Cat {

   private Cat() {}

   public static final class Category extends MessageMicro {

      public static final int BACKEND_FIELD_NUMBER = 4;
      public static final int CATEGORY_ID_FIELD_NUMBER = 9;
      public static final int CATEGORY_QUERY_FIELD_NUMBER = 3;
      public static final int CATEGORY_SIZE_FIELD_NUMBER = 5;
      public static final int CHILDREN_FIELD_NUMBER = 8;
      public static final int DESCRIPTION_FIELD_NUMBER = 2;
      public static final int FETCH_DOCID_FIELD_NUMBER = 10;
      public static final int GOOD_UNTIL_DATE_MSEC_FIELD_NUMBER = 7;
      public static final int LAST_MODIFICATION_DATE_MSEC_FIELD_NUMBER = 6;
      public static final int NAME_FIELD_NUMBER = 1;
      private int backend_ = 0;
      private int cachedSize;
      private String categoryId_ = "";
      private String categoryQuery_ = "";
      private int categorySize_ = 0;
      private List<Cat.Category> children_;
      private String description_ = "";
      private Common.Docid fetchDocid_ = null;
      private long goodUntilDateMsec_ = 0L;
      private boolean hasBackend;
      private boolean hasCategoryId;
      private boolean hasCategoryQuery;
      private boolean hasCategorySize;
      private boolean hasDescription;
      private boolean hasFetchDocid;
      private boolean hasGoodUntilDateMsec;
      private boolean hasLastModificationDateMsec;
      private boolean hasName;
      private long lastModificationDateMsec_ = 0L;
      private String name_ = "";


      public Category() {
         List var1 = Collections.emptyList();
         this.children_ = var1;
         this.cachedSize = -1;
      }

      public static Cat.Category parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Cat.Category()).mergeFrom(var0);
      }

      public static Cat.Category parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Cat.Category)((Cat.Category)(new Cat.Category()).mergeFrom(var0));
      }

      public Cat.Category addChildren(Cat.Category var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.children_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.children_ = var2;
            }

            this.children_.add(var1);
            return this;
         }
      }

      public final Cat.Category clear() {
         Cat.Category var1 = this.clearName();
         Cat.Category var2 = this.clearCategoryId();
         Cat.Category var3 = this.clearDescription();
         Cat.Category var4 = this.clearCategoryQuery();
         Cat.Category var5 = this.clearFetchDocid();
         Cat.Category var6 = this.clearBackend();
         Cat.Category var7 = this.clearCategorySize();
         Cat.Category var8 = this.clearLastModificationDateMsec();
         Cat.Category var9 = this.clearGoodUntilDateMsec();
         Cat.Category var10 = this.clearChildren();
         this.cachedSize = -1;
         return this;
      }

      public Cat.Category clearBackend() {
         this.hasBackend = (boolean)0;
         this.backend_ = 0;
         return this;
      }

      public Cat.Category clearCategoryId() {
         this.hasCategoryId = (boolean)0;
         this.categoryId_ = "";
         return this;
      }

      public Cat.Category clearCategoryQuery() {
         this.hasCategoryQuery = (boolean)0;
         this.categoryQuery_ = "";
         return this;
      }

      public Cat.Category clearCategorySize() {
         this.hasCategorySize = (boolean)0;
         this.categorySize_ = 0;
         return this;
      }

      public Cat.Category clearChildren() {
         List var1 = Collections.emptyList();
         this.children_ = var1;
         return this;
      }

      public Cat.Category clearDescription() {
         this.hasDescription = (boolean)0;
         this.description_ = "";
         return this;
      }

      public Cat.Category clearFetchDocid() {
         this.hasFetchDocid = (boolean)0;
         this.fetchDocid_ = null;
         return this;
      }

      public Cat.Category clearGoodUntilDateMsec() {
         this.hasGoodUntilDateMsec = (boolean)0;
         this.goodUntilDateMsec_ = 0L;
         return this;
      }

      public Cat.Category clearLastModificationDateMsec() {
         this.hasLastModificationDateMsec = (boolean)0;
         this.lastModificationDateMsec_ = 0L;
         return this;
      }

      public Cat.Category clearName() {
         this.hasName = (boolean)0;
         this.name_ = "";
         return this;
      }

      public int getBackend() {
         return this.backend_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getCategoryId() {
         return this.categoryId_;
      }

      public String getCategoryQuery() {
         return this.categoryQuery_;
      }

      public int getCategorySize() {
         return this.categorySize_;
      }

      public Cat.Category getChildren(int var1) {
         return (Cat.Category)this.children_.get(var1);
      }

      public int getChildrenCount() {
         return this.children_.size();
      }

      public List<Cat.Category> getChildrenList() {
         return this.children_;
      }

      public String getDescription() {
         return this.description_;
      }

      public Common.Docid getFetchDocid() {
         return this.fetchDocid_;
      }

      public long getGoodUntilDateMsec() {
         return this.goodUntilDateMsec_;
      }

      public long getLastModificationDateMsec() {
         return this.lastModificationDateMsec_;
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

         if(this.hasDescription()) {
            String var4 = this.getDescription();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasCategoryQuery()) {
            String var6 = this.getCategoryQuery();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasBackend()) {
            int var8 = this.getBackend();
            int var9 = CodedOutputStreamMicro.computeInt32Size(4, var8);
            var1 += var9;
         }

         if(this.hasCategorySize()) {
            int var10 = this.getCategorySize();
            int var11 = CodedOutputStreamMicro.computeInt32Size(5, var10);
            var1 += var11;
         }

         if(this.hasLastModificationDateMsec()) {
            long var12 = this.getLastModificationDateMsec();
            int var14 = CodedOutputStreamMicro.computeInt64Size(6, var12);
            var1 += var14;
         }

         if(this.hasGoodUntilDateMsec()) {
            long var15 = this.getGoodUntilDateMsec();
            int var17 = CodedOutputStreamMicro.computeInt64Size(7, var15);
            var1 += var17;
         }

         int var20;
         for(Iterator var18 = this.getChildrenList().iterator(); var18.hasNext(); var1 += var20) {
            Cat.Category var19 = (Cat.Category)var18.next();
            var20 = CodedOutputStreamMicro.computeMessageSize(8, var19);
         }

         if(this.hasCategoryId()) {
            String var21 = this.getCategoryId();
            int var22 = CodedOutputStreamMicro.computeStringSize(9, var21);
            var1 += var22;
         }

         if(this.hasFetchDocid()) {
            Common.Docid var23 = this.getFetchDocid();
            int var24 = CodedOutputStreamMicro.computeMessageSize(10, var23);
            var1 += var24;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasBackend() {
         return this.hasBackend;
      }

      public boolean hasCategoryId() {
         return this.hasCategoryId;
      }

      public boolean hasCategoryQuery() {
         return this.hasCategoryQuery;
      }

      public boolean hasCategorySize() {
         return this.hasCategorySize;
      }

      public boolean hasDescription() {
         return this.hasDescription;
      }

      public boolean hasFetchDocid() {
         return this.hasFetchDocid;
      }

      public boolean hasGoodUntilDateMsec() {
         return this.hasGoodUntilDateMsec;
      }

      public boolean hasLastModificationDateMsec() {
         return this.hasLastModificationDateMsec;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasName && this.hasCategoryQuery && this.hasBackend && (!this.hasFetchDocid() || this.getFetchDocid().isInitialized())) {
            Iterator var2 = this.getChildrenList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((Cat.Category)var2.next()).isInitialized());
         }

         return var1;
      }

      public Cat.Category mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setName(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setDescription(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setCategoryQuery(var7);
               break;
            case 32:
               int var9 = var1.readInt32();
               this.setBackend(var9);
               break;
            case 40:
               int var11 = var1.readInt32();
               this.setCategorySize(var11);
               break;
            case 48:
               long var13 = var1.readInt64();
               this.setLastModificationDateMsec(var13);
               break;
            case 56:
               long var16 = var1.readInt64();
               this.setGoodUntilDateMsec(var16);
               break;
            case 66:
               Cat.Category var19 = new Cat.Category();
               var1.readMessage(var19);
               this.addChildren(var19);
               break;
            case 74:
               String var21 = var1.readString();
               this.setCategoryId(var21);
               break;
            case 82:
               Common.Docid var23 = new Common.Docid();
               var1.readMessage(var23);
               this.setFetchDocid(var23);
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

      public Cat.Category setBackend(int var1) {
         this.hasBackend = (boolean)1;
         this.backend_ = var1;
         return this;
      }

      public Cat.Category setCategoryId(String var1) {
         this.hasCategoryId = (boolean)1;
         this.categoryId_ = var1;
         return this;
      }

      public Cat.Category setCategoryQuery(String var1) {
         this.hasCategoryQuery = (boolean)1;
         this.categoryQuery_ = var1;
         return this;
      }

      public Cat.Category setCategorySize(int var1) {
         this.hasCategorySize = (boolean)1;
         this.categorySize_ = var1;
         return this;
      }

      public Cat.Category setChildren(int var1, Cat.Category var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.children_.set(var1, var2);
            return this;
         }
      }

      public Cat.Category setDescription(String var1) {
         this.hasDescription = (boolean)1;
         this.description_ = var1;
         return this;
      }

      public Cat.Category setFetchDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasFetchDocid = (boolean)1;
            this.fetchDocid_ = var1;
            return this;
         }
      }

      public Cat.Category setGoodUntilDateMsec(long var1) {
         this.hasGoodUntilDateMsec = (boolean)1;
         this.goodUntilDateMsec_ = var1;
         return this;
      }

      public Cat.Category setLastModificationDateMsec(long var1) {
         this.hasLastModificationDateMsec = (boolean)1;
         this.lastModificationDateMsec_ = var1;
         return this;
      }

      public Cat.Category setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasName()) {
            String var2 = this.getName();
            var1.writeString(1, var2);
         }

         if(this.hasDescription()) {
            String var3 = this.getDescription();
            var1.writeString(2, var3);
         }

         if(this.hasCategoryQuery()) {
            String var4 = this.getCategoryQuery();
            var1.writeString(3, var4);
         }

         if(this.hasBackend()) {
            int var5 = this.getBackend();
            var1.writeInt32(4, var5);
         }

         if(this.hasCategorySize()) {
            int var6 = this.getCategorySize();
            var1.writeInt32(5, var6);
         }

         if(this.hasLastModificationDateMsec()) {
            long var7 = this.getLastModificationDateMsec();
            var1.writeInt64(6, var7);
         }

         if(this.hasGoodUntilDateMsec()) {
            long var9 = this.getGoodUntilDateMsec();
            var1.writeInt64(7, var9);
         }

         Iterator var11 = this.getChildrenList().iterator();

         while(var11.hasNext()) {
            Cat.Category var12 = (Cat.Category)var11.next();
            var1.writeMessage(8, var12);
         }

         if(this.hasCategoryId()) {
            String var13 = this.getCategoryId();
            var1.writeString(9, var13);
         }

         if(this.hasFetchDocid()) {
            Common.Docid var14 = this.getFetchDocid();
            var1.writeMessage(10, var14);
         }
      }
   }

   public static final class CategoryRequest extends MessageMicro {

      public static final int BACKEND_RESTRICT_FIELD_NUMBER = 4;
      public static final int LANGUAGE_CODE_FIELD_NUMBER = 1;
      public static final int LOCALE_FIELD_NUMBER = 2;
      private List<Integer> backendRestrict_;
      private int cachedSize;
      private boolean hasLocale;
      private List<String> languageCode_;
      private UserLocale locale_;


      public CategoryRequest() {
         List var1 = Collections.emptyList();
         this.languageCode_ = var1;
         this.locale_ = null;
         List var2 = Collections.emptyList();
         this.backendRestrict_ = var2;
         this.cachedSize = -1;
      }

      public static Cat.CategoryRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Cat.CategoryRequest()).mergeFrom(var0);
      }

      public static Cat.CategoryRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Cat.CategoryRequest)((Cat.CategoryRequest)(new Cat.CategoryRequest()).mergeFrom(var0));
      }

      public Cat.CategoryRequest addBackendRestrict(int var1) {
         if(this.backendRestrict_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.backendRestrict_ = var2;
         }

         List var3 = this.backendRestrict_;
         Integer var4 = Integer.valueOf(var1);
         var3.add(var4);
         return this;
      }

      public Cat.CategoryRequest addLanguageCode(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.languageCode_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.languageCode_ = var2;
            }

            this.languageCode_.add(var1);
            return this;
         }
      }

      public final Cat.CategoryRequest clear() {
         Cat.CategoryRequest var1 = this.clearLanguageCode();
         Cat.CategoryRequest var2 = this.clearLocale();
         Cat.CategoryRequest var3 = this.clearBackendRestrict();
         this.cachedSize = -1;
         return this;
      }

      public Cat.CategoryRequest clearBackendRestrict() {
         List var1 = Collections.emptyList();
         this.backendRestrict_ = var1;
         return this;
      }

      public Cat.CategoryRequest clearLanguageCode() {
         List var1 = Collections.emptyList();
         this.languageCode_ = var1;
         return this;
      }

      public Cat.CategoryRequest clearLocale() {
         this.hasLocale = (boolean)0;
         this.locale_ = null;
         return this;
      }

      public int getBackendRestrict(int var1) {
         return ((Integer)this.backendRestrict_.get(var1)).intValue();
      }

      public int getBackendRestrictCount() {
         return this.backendRestrict_.size();
      }

      public List<Integer> getBackendRestrictList() {
         return this.backendRestrict_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getLanguageCode(int var1) {
         return (String)this.languageCode_.get(var1);
      }

      public int getLanguageCodeCount() {
         return this.languageCode_.size();
      }

      public List<String> getLanguageCodeList() {
         return this.languageCode_;
      }

      public UserLocale getLocale() {
         return this.locale_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var3;
         for(Iterator var2 = this.getLanguageCodeList().iterator(); var2.hasNext(); var1 += var3) {
            var3 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var2.next());
         }

         int var4 = 0 + var1;
         int var5 = this.getLanguageCodeList().size() * 1;
         int var6 = var4 + var5;
         if(this.hasLocale()) {
            UserLocale var7 = this.getLocale();
            int var8 = CodedOutputStreamMicro.computeMessageSize(2, var7);
            var6 += var8;
         }

         int var9 = 0;

         int var11;
         for(Iterator var10 = this.getBackendRestrictList().iterator(); var10.hasNext(); var9 += var11) {
            var11 = CodedOutputStreamMicro.computeInt32SizeNoTag(((Integer)var10.next()).intValue());
         }

         int var12 = var6 + var9;
         int var13 = this.getBackendRestrictList().size() * 1;
         int var14 = var12 + var13;
         this.cachedSize = var14;
         return var14;
      }

      public boolean hasLocale() {
         return this.hasLocale;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Cat.CategoryRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.addLanguageCode(var3);
               break;
            case 18:
               UserLocale var5 = new UserLocale();
               var1.readMessage(var5);
               this.setLocale(var5);
               break;
            case 32:
               int var7 = var1.readInt32();
               this.addBackendRestrict(var7);
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

      public Cat.CategoryRequest setBackendRestrict(int var1, int var2) {
         List var3 = this.backendRestrict_;
         Integer var4 = Integer.valueOf(var2);
         var3.set(var1, var4);
         return this;
      }

      public Cat.CategoryRequest setLanguageCode(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.languageCode_.set(var1, var2);
            return this;
         }
      }

      public Cat.CategoryRequest setLocale(UserLocale var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasLocale = (boolean)1;
            this.locale_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getLanguageCodeList().iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            var1.writeString(1, var3);
         }

         if(this.hasLocale()) {
            UserLocale var4 = this.getLocale();
            var1.writeMessage(2, var4);
         }

         Iterator var5 = this.getBackendRestrictList().iterator();

         while(var5.hasNext()) {
            int var6 = ((Integer)var5.next()).intValue();
            var1.writeInt32(4, var6);
         }

      }
   }

   public static final class CategoryResponse extends MessageMicro {

      public static final int CATEGORYLIST_FIELD_NUMBER = 1;
      private int cachedSize;
      private List<Cat.CategoryResponse.CategoryList> categoryList_;


      public CategoryResponse() {
         List var1 = Collections.emptyList();
         this.categoryList_ = var1;
         this.cachedSize = -1;
      }

      public static Cat.CategoryResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Cat.CategoryResponse()).mergeFrom(var0);
      }

      public static Cat.CategoryResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Cat.CategoryResponse)((Cat.CategoryResponse)(new Cat.CategoryResponse()).mergeFrom(var0));
      }

      public Cat.CategoryResponse addCategoryList(Cat.CategoryResponse.CategoryList var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.categoryList_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.categoryList_ = var2;
            }

            this.categoryList_.add(var1);
            return this;
         }
      }

      public final Cat.CategoryResponse clear() {
         Cat.CategoryResponse var1 = this.clearCategoryList();
         this.cachedSize = -1;
         return this;
      }

      public Cat.CategoryResponse clearCategoryList() {
         List var1 = Collections.emptyList();
         this.categoryList_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Cat.CategoryResponse.CategoryList getCategoryList(int var1) {
         return (Cat.CategoryResponse.CategoryList)this.categoryList_.get(var1);
      }

      public int getCategoryListCount() {
         return this.categoryList_.size();
      }

      public List<Cat.CategoryResponse.CategoryList> getCategoryListList() {
         return this.categoryList_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getCategoryListList().iterator(); var2.hasNext(); var1 += var4) {
            Cat.CategoryResponse.CategoryList var3 = (Cat.CategoryResponse.CategoryList)var2.next();
            var4 = CodedOutputStreamMicro.computeGroupSize(1, var3);
         }

         this.cachedSize = var1;
         return var1;
      }

      public final boolean isInitialized() {
         Iterator var1 = this.getCategoryListList().iterator();

         boolean var2;
         while(true) {
            if(var1.hasNext()) {
               if(((Cat.CategoryResponse.CategoryList)var1.next()).isInitialized()) {
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

      public Cat.CategoryResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 11:
               Cat.CategoryResponse.CategoryList var3 = new Cat.CategoryResponse.CategoryList();
               var1.readGroup(var3, 1);
               this.addCategoryList(var3);
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

      public Cat.CategoryResponse setCategoryList(int var1, Cat.CategoryResponse.CategoryList var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.categoryList_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getCategoryListList().iterator();

         while(var2.hasNext()) {
            Cat.CategoryResponse.CategoryList var3 = (Cat.CategoryResponse.CategoryList)var2.next();
            var1.writeGroup(1, var3);
         }

      }

      public static final class CategoryList extends MessageMicro {

         public static final int LANGUAGE_CODE_FIELD_NUMBER = 2;
         public static final int RESULT_FIELD_NUMBER = 3;
         private int cachedSize;
         private boolean hasLanguageCode;
         private String languageCode_ = "";
         private List<Cat.Category> result_;


         public CategoryList() {
            List var1 = Collections.emptyList();
            this.result_ = var1;
            this.cachedSize = -1;
         }

         public Cat.CategoryResponse.CategoryList addResult(Cat.Category var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               if(this.result_.isEmpty()) {
                  ArrayList var2 = new ArrayList();
                  this.result_ = var2;
               }

               this.result_.add(var1);
               return this;
            }
         }

         public final Cat.CategoryResponse.CategoryList clear() {
            Cat.CategoryResponse.CategoryList var1 = this.clearLanguageCode();
            Cat.CategoryResponse.CategoryList var2 = this.clearResult();
            this.cachedSize = -1;
            return this;
         }

         public Cat.CategoryResponse.CategoryList clearLanguageCode() {
            this.hasLanguageCode = (boolean)0;
            this.languageCode_ = "";
            return this;
         }

         public Cat.CategoryResponse.CategoryList clearResult() {
            List var1 = Collections.emptyList();
            this.result_ = var1;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public String getLanguageCode() {
            return this.languageCode_;
         }

         public Cat.Category getResult(int var1) {
            return (Cat.Category)this.result_.get(var1);
         }

         public int getResultCount() {
            return this.result_.size();
         }

         public List<Cat.Category> getResultList() {
            return this.result_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasLanguageCode()) {
               String var2 = this.getLanguageCode();
               int var3 = CodedOutputStreamMicro.computeStringSize(2, var2);
               var1 = 0 + var3;
            }

            int var6;
            for(Iterator var4 = this.getResultList().iterator(); var4.hasNext(); var1 += var6) {
               Cat.Category var5 = (Cat.Category)var4.next();
               var6 = CodedOutputStreamMicro.computeMessageSize(3, var5);
            }

            this.cachedSize = var1;
            return var1;
         }

         public boolean hasLanguageCode() {
            return this.hasLanguageCode;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if(this.hasLanguageCode) {
               Iterator var2 = this.getResultList().iterator();

               do {
                  if(!var2.hasNext()) {
                     var1 = true;
                     break;
                  }
               } while(((Cat.Category)var2.next()).isInitialized());
            }

            return var1;
         }

         public Cat.CategoryResponse.CategoryList mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 18:
                  String var3 = var1.readString();
                  this.setLanguageCode(var3);
                  break;
               case 26:
                  Cat.Category var5 = new Cat.Category();
                  var1.readMessage(var5);
                  this.addResult(var5);
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

         public Cat.CategoryResponse.CategoryList parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new Cat.CategoryResponse.CategoryList()).mergeFrom(var1);
         }

         public Cat.CategoryResponse.CategoryList parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (Cat.CategoryResponse.CategoryList)((Cat.CategoryResponse.CategoryList)(new Cat.CategoryResponse.CategoryList()).mergeFrom(var1));
         }

         public Cat.CategoryResponse.CategoryList setLanguageCode(String var1) {
            this.hasLanguageCode = (boolean)1;
            this.languageCode_ = var1;
            return this;
         }

         public Cat.CategoryResponse.CategoryList setResult(int var1, Cat.Category var2) {
            if(var2 == null) {
               throw new NullPointerException();
            } else {
               this.result_.set(var1, var2);
               return this;
            }
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasLanguageCode()) {
               String var2 = this.getLanguageCode();
               var1.writeString(2, var2);
            }

            Iterator var3 = this.getResultList().iterator();

            while(var3.hasNext()) {
               Cat.Category var4 = (Cat.Category)var3.next();
               var1.writeMessage(3, var4);
            }

         }
      }
   }
}
