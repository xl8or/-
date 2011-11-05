package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Cache;
import com.google.android.finsky.remoting.protos.Common;
import com.google.android.finsky.remoting.protos.DebugInfo;
import com.google.android.finsky.remoting.protos.Dev;
import com.google.android.finsky.remoting.protos.Doc;
import com.google.android.finsky.remoting.protos.RequestContext;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class UserAccount {

   private UserAccount() {}

   public static final class PurchaseHistoryRequest extends MessageMicro {

      public static final int CONTEXT_FIELD_NUMBER = 1;
      public static final int DATE = 1;
      public static final int DELETEPURCHASEHISTORY_FIELD_NUMBER = 16;
      public static final int DOCUMENT_TITLE = 2;
      public static final int DOCUMENT_TYPE = 3;
      public static final int FREE = 1;
      public static final int GETPURCHASEHISTORY_FIELD_NUMBER = 2;
      public static final int ORDER_STATUS = 5;
      public static final int PAID = 2;
      public static final int PURCHASE_ID = 0;
      public static final int PURCHASE_TYPE = 6;
      public static final int RENTAL = 3;
      public static final int SOURCE_PROPERTY = 4;
      public static final int SUBSCRIPTION = 4;
      public static final int UPDATEPURCHASEHISTORY_FIELD_NUMBER = 21;
      private int cachedSize = -1;
      private RequestContext context_ = null;
      private UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory deletePurchaseHistory_ = null;
      private UserAccount.PurchaseHistoryRequest.GetPurchaseHistory getPurchaseHistory_ = null;
      private boolean hasContext;
      private boolean hasDeletePurchaseHistory;
      private boolean hasGetPurchaseHistory;
      private boolean hasUpdatePurchaseHistory;
      private UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory updatePurchaseHistory_ = null;


      public PurchaseHistoryRequest() {}

      public static UserAccount.PurchaseHistoryRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.PurchaseHistoryRequest()).mergeFrom(var0);
      }

      public static UserAccount.PurchaseHistoryRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.PurchaseHistoryRequest)((UserAccount.PurchaseHistoryRequest)(new UserAccount.PurchaseHistoryRequest()).mergeFrom(var0));
      }

      public final UserAccount.PurchaseHistoryRequest clear() {
         UserAccount.PurchaseHistoryRequest var1 = this.clearContext();
         UserAccount.PurchaseHistoryRequest var2 = this.clearGetPurchaseHistory();
         UserAccount.PurchaseHistoryRequest var3 = this.clearUpdatePurchaseHistory();
         UserAccount.PurchaseHistoryRequest var4 = this.clearDeletePurchaseHistory();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.PurchaseHistoryRequest clearContext() {
         this.hasContext = (boolean)0;
         this.context_ = null;
         return this;
      }

      public UserAccount.PurchaseHistoryRequest clearDeletePurchaseHistory() {
         this.hasDeletePurchaseHistory = (boolean)0;
         this.deletePurchaseHistory_ = null;
         return this;
      }

      public UserAccount.PurchaseHistoryRequest clearGetPurchaseHistory() {
         this.hasGetPurchaseHistory = (boolean)0;
         this.getPurchaseHistory_ = null;
         return this;
      }

      public UserAccount.PurchaseHistoryRequest clearUpdatePurchaseHistory() {
         this.hasUpdatePurchaseHistory = (boolean)0;
         this.updatePurchaseHistory_ = null;
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

      public UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory getDeletePurchaseHistory() {
         return this.deletePurchaseHistory_;
      }

      public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory getGetPurchaseHistory() {
         return this.getPurchaseHistory_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasGetPurchaseHistory()) {
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var4 = this.getGetPurchaseHistory();
            int var5 = CodedOutputStreamMicro.computeGroupSize(2, var4);
            var1 += var5;
         }

         if(this.hasDeletePurchaseHistory()) {
            UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory var6 = this.getDeletePurchaseHistory();
            int var7 = CodedOutputStreamMicro.computeGroupSize(16, var6);
            var1 += var7;
         }

         if(this.hasUpdatePurchaseHistory()) {
            UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory var8 = this.getUpdatePurchaseHistory();
            int var9 = CodedOutputStreamMicro.computeGroupSize(21, var8);
            var1 += var9;
         }

         this.cachedSize = var1;
         return var1;
      }

      public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory getUpdatePurchaseHistory() {
         return this.updatePurchaseHistory_;
      }

      public boolean hasContext() {
         return this.hasContext;
      }

      public boolean hasDeletePurchaseHistory() {
         return this.hasDeletePurchaseHistory;
      }

      public boolean hasGetPurchaseHistory() {
         return this.hasGetPurchaseHistory;
      }

      public boolean hasUpdatePurchaseHistory() {
         return this.hasUpdatePurchaseHistory;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasContext && this.getContext().isInitialized() && (!this.hasGetPurchaseHistory() || this.getGetPurchaseHistory().isInitialized()) && (!this.hasUpdatePurchaseHistory() || this.getUpdatePurchaseHistory().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public UserAccount.PurchaseHistoryRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               RequestContext var3 = new RequestContext();
               var1.readMessage(var3);
               this.setContext(var3);
               break;
            case 19:
               UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var5 = new UserAccount.PurchaseHistoryRequest.GetPurchaseHistory();
               var1.readGroup(var5, 2);
               this.setGetPurchaseHistory(var5);
               break;
            case 131:
               UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory var7 = new UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory();
               var1.readGroup(var7, 16);
               this.setDeletePurchaseHistory(var7);
               break;
            case 171:
               UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory var9 = new UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory();
               var1.readGroup(var9, 21);
               this.setUpdatePurchaseHistory(var9);
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

      public UserAccount.PurchaseHistoryRequest setContext(RequestContext var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasContext = (boolean)1;
            this.context_ = var1;
            return this;
         }
      }

      public UserAccount.PurchaseHistoryRequest setDeletePurchaseHistory(UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDeletePurchaseHistory = (boolean)1;
            this.deletePurchaseHistory_ = var1;
            return this;
         }
      }

      public UserAccount.PurchaseHistoryRequest setGetPurchaseHistory(UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasGetPurchaseHistory = (boolean)1;
            this.getPurchaseHistory_ = var1;
            return this;
         }
      }

      public UserAccount.PurchaseHistoryRequest setUpdatePurchaseHistory(UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasUpdatePurchaseHistory = (boolean)1;
            this.updatePurchaseHistory_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            var1.writeMessage(1, var2);
         }

         if(this.hasGetPurchaseHistory()) {
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var3 = this.getGetPurchaseHistory();
            var1.writeGroup(2, var3);
         }

         if(this.hasDeletePurchaseHistory()) {
            UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory var4 = this.getDeletePurchaseHistory();
            var1.writeGroup(16, var4);
         }

         if(this.hasUpdatePurchaseHistory()) {
            UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory var5 = this.getUpdatePurchaseHistory();
            var1.writeGroup(21, var5);
         }
      }

      public static final class UpdatePurchaseHistory extends MessageMicro {

         public static final int ADDFULFILLMENT_FIELD_NUMBER = 23;
         public static final int PURCHASE_FIELD_NUMBER = 22;
         private UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment addFulfillment_ = null;
         private int cachedSize = -1;
         private boolean hasAddFulfillment;
         private boolean hasPurchase;
         private UserAccount.Purchase purchase_ = null;


         public UpdatePurchaseHistory() {}

         public final UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory clear() {
            UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory var1 = this.clearPurchase();
            UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory var2 = this.clearAddFulfillment();
            this.cachedSize = -1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory clearAddFulfillment() {
            this.hasAddFulfillment = (boolean)0;
            this.addFulfillment_ = null;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory clearPurchase() {
            this.hasPurchase = (boolean)0;
            this.purchase_ = null;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment getAddFulfillment() {
            return this.addFulfillment_;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public UserAccount.Purchase getPurchase() {
            return this.purchase_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasPurchase()) {
               UserAccount.Purchase var2 = this.getPurchase();
               int var3 = CodedOutputStreamMicro.computeMessageSize(22, var2);
               var1 = 0 + var3;
            }

            if(this.hasAddFulfillment()) {
               UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment var4 = this.getAddFulfillment();
               int var5 = CodedOutputStreamMicro.computeGroupSize(23, var4);
               var1 += var5;
            }

            this.cachedSize = var1;
            return var1;
         }

         public boolean hasAddFulfillment() {
            return this.hasAddFulfillment;
         }

         public boolean hasPurchase() {
            return this.hasPurchase;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if((!this.hasPurchase() || this.getPurchase().isInitialized()) && (!this.hasAddFulfillment() || this.getAddFulfillment().isInitialized())) {
               var1 = true;
            }

            return var1;
         }

         public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 178:
                  UserAccount.Purchase var3 = new UserAccount.Purchase();
                  var1.readMessage(var3);
                  this.setPurchase(var3);
                  break;
               case 187:
                  UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment var5 = new UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment();
                  var1.readGroup(var5, 23);
                  this.setAddFulfillment(var5);
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

         public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory()).mergeFrom(var1);
         }

         public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory)((UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory)(new UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory()).mergeFrom(var1));
         }

         public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory setAddFulfillment(UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasAddFulfillment = (boolean)1;
               this.addFulfillment_ = var1;
               return this;
            }
         }

         public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory setPurchase(UserAccount.Purchase var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasPurchase = (boolean)1;
               this.purchase_ = var1;
               return this;
            }
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasPurchase()) {
               UserAccount.Purchase var2 = this.getPurchase();
               var1.writeMessage(22, var2);
            }

            if(this.hasAddFulfillment()) {
               UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment var3 = this.getAddFulfillment();
               var1.writeGroup(23, var3);
            }
         }

         public static final class AddFulfillment extends MessageMicro {

            public static final int FULFILLMENT_FIELD_NUMBER = 25;
            public static final int PURCHASE_ID_FIELD_NUMBER = 24;
            private int cachedSize = -1;
            private UserAccount.Fulfillment fulfillment_ = null;
            private boolean hasFulfillment;
            private boolean hasPurchaseId;
            private String purchaseId_ = "";


            public AddFulfillment() {}

            public final UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment clear() {
               UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment var1 = this.clearPurchaseId();
               UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment var2 = this.clearFulfillment();
               this.cachedSize = -1;
               return this;
            }

            public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment clearFulfillment() {
               this.hasFulfillment = (boolean)0;
               this.fulfillment_ = null;
               return this;
            }

            public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment clearPurchaseId() {
               this.hasPurchaseId = (boolean)0;
               this.purchaseId_ = "";
               return this;
            }

            public int getCachedSize() {
               if(this.cachedSize < 0) {
                  int var1 = this.getSerializedSize();
               }

               return this.cachedSize;
            }

            public UserAccount.Fulfillment getFulfillment() {
               return this.fulfillment_;
            }

            public String getPurchaseId() {
               return this.purchaseId_;
            }

            public int getSerializedSize() {
               int var1 = 0;
               if(this.hasPurchaseId()) {
                  String var2 = this.getPurchaseId();
                  int var3 = CodedOutputStreamMicro.computeStringSize(24, var2);
                  var1 = 0 + var3;
               }

               if(this.hasFulfillment()) {
                  UserAccount.Fulfillment var4 = this.getFulfillment();
                  int var5 = CodedOutputStreamMicro.computeMessageSize(25, var4);
                  var1 += var5;
               }

               this.cachedSize = var1;
               return var1;
            }

            public boolean hasFulfillment() {
               return this.hasFulfillment;
            }

            public boolean hasPurchaseId() {
               return this.hasPurchaseId;
            }

            public final boolean isInitialized() {
               boolean var1 = false;
               if(this.hasPurchaseId && this.hasFulfillment && this.getFulfillment().isInitialized()) {
                  var1 = true;
               }

               return var1;
            }

            public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment mergeFrom(CodedInputStreamMicro var1) throws IOException {
               while(true) {
                  int var2 = var1.readTag();
                  switch(var2) {
                  case 194:
                     String var3 = var1.readString();
                     this.setPurchaseId(var3);
                     break;
                  case 202:
                     UserAccount.Fulfillment var5 = new UserAccount.Fulfillment();
                     var1.readMessage(var5);
                     this.setFulfillment(var5);
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

            public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment parseFrom(CodedInputStreamMicro var1) throws IOException {
               return (new UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment()).mergeFrom(var1);
            }

            public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
               return (UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment)((UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment)(new UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment()).mergeFrom(var1));
            }

            public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment setFulfillment(UserAccount.Fulfillment var1) {
               if(var1 == null) {
                  throw new NullPointerException();
               } else {
                  this.hasFulfillment = (boolean)1;
                  this.fulfillment_ = var1;
                  return this;
               }
            }

            public UserAccount.PurchaseHistoryRequest.UpdatePurchaseHistory.AddFulfillment setPurchaseId(String var1) {
               this.hasPurchaseId = (boolean)1;
               this.purchaseId_ = var1;
               return this;
            }

            public void writeTo(CodedOutputStreamMicro var1) throws IOException {
               if(this.hasPurchaseId()) {
                  String var2 = this.getPurchaseId();
                  var1.writeString(24, var2);
               }

               if(this.hasFulfillment()) {
                  UserAccount.Fulfillment var3 = this.getFulfillment();
                  var1.writeMessage(25, var3);
               }
            }
         }
      }

      public static final class GetPurchaseHistory extends MessageMicro {

         public static final int ANDROID_ID_RESTRICT_FIELD_NUMBER = 27;
         public static final int BACKEND_RESTRICT_FIELD_NUMBER = 9;
         public static final int DOCID_FIELD_NUMBER = 6;
         public static final int DOCUMENT_TYPE_RESTRICT_FIELD_NUMBER = 26;
         public static final int NUM_FIELD_NUMBER = 3;
         public static final int OFFSET_FIELD_NUMBER = 4;
         public static final int ORDER_BY_FIELD_NUMBER = 7;
         public static final int ORDER_DESCENDING_FIELD_NUMBER = 10;
         public static final int PURCHASE_ID_FIELD_NUMBER = 5;
         public static final int SKIP_FETCH_FOR_MISSING_DOCUMENTS_FIELD_NUMBER = 11;
         public static final int TYPE_RESTRICT_FIELD_NUMBER = 8;
         private List<Long> androidIdRestrict_;
         private int backendRestrict_;
         private int cachedSize;
         private List<Common.Docid> docid_;
         private List<Integer> documentTypeRestrict_;
         private boolean hasBackendRestrict;
         private boolean hasNum;
         private boolean hasOffset;
         private boolean hasOrderBy;
         private boolean hasOrderDescending;
         private boolean hasSkipFetchForMissingDocuments;
         private boolean hasTypeRestrict;
         private int num_ = 0;
         private int offset_ = 0;
         private int orderBy_;
         private boolean orderDescending_;
         private List<String> purchaseId_;
         private boolean skipFetchForMissingDocuments_;
         private int typeRestrict_;


         public GetPurchaseHistory() {
            List var1 = Collections.emptyList();
            this.purchaseId_ = var1;
            List var2 = Collections.emptyList();
            this.docid_ = var2;
            this.orderBy_ = 0;
            this.orderDescending_ = (boolean)0;
            this.typeRestrict_ = 1;
            this.backendRestrict_ = 0;
            List var3 = Collections.emptyList();
            this.documentTypeRestrict_ = var3;
            List var4 = Collections.emptyList();
            this.androidIdRestrict_ = var4;
            this.skipFetchForMissingDocuments_ = (boolean)0;
            this.cachedSize = -1;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory addAndroidIdRestrict(long var1) {
            if(this.androidIdRestrict_.isEmpty()) {
               ArrayList var3 = new ArrayList();
               this.androidIdRestrict_ = var3;
            }

            List var4 = this.androidIdRestrict_;
            Long var5 = Long.valueOf(var1);
            var4.add(var5);
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory addDocid(Common.Docid var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               if(this.docid_.isEmpty()) {
                  ArrayList var2 = new ArrayList();
                  this.docid_ = var2;
               }

               this.docid_.add(var1);
               return this;
            }
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory addDocumentTypeRestrict(int var1) {
            if(this.documentTypeRestrict_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.documentTypeRestrict_ = var2;
            }

            List var3 = this.documentTypeRestrict_;
            Integer var4 = Integer.valueOf(var1);
            var3.add(var4);
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory addPurchaseId(String var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               if(this.purchaseId_.isEmpty()) {
                  ArrayList var2 = new ArrayList();
                  this.purchaseId_ = var2;
               }

               this.purchaseId_.add(var1);
               return this;
            }
         }

         public final UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clear() {
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var1 = this.clearNum();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var2 = this.clearOffset();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var3 = this.clearPurchaseId();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var4 = this.clearDocid();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var5 = this.clearOrderBy();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var6 = this.clearOrderDescending();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var7 = this.clearTypeRestrict();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var8 = this.clearBackendRestrict();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var9 = this.clearDocumentTypeRestrict();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var10 = this.clearAndroidIdRestrict();
            UserAccount.PurchaseHistoryRequest.GetPurchaseHistory var11 = this.clearSkipFetchForMissingDocuments();
            this.cachedSize = -1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearAndroidIdRestrict() {
            List var1 = Collections.emptyList();
            this.androidIdRestrict_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearBackendRestrict() {
            this.hasBackendRestrict = (boolean)0;
            this.backendRestrict_ = 0;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearDocid() {
            List var1 = Collections.emptyList();
            this.docid_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearDocumentTypeRestrict() {
            List var1 = Collections.emptyList();
            this.documentTypeRestrict_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearNum() {
            this.hasNum = (boolean)0;
            this.num_ = 0;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearOffset() {
            this.hasOffset = (boolean)0;
            this.offset_ = 0;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearOrderBy() {
            this.hasOrderBy = (boolean)0;
            this.orderBy_ = 0;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearOrderDescending() {
            this.hasOrderDescending = (boolean)0;
            this.orderDescending_ = (boolean)0;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearPurchaseId() {
            List var1 = Collections.emptyList();
            this.purchaseId_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearSkipFetchForMissingDocuments() {
            this.hasSkipFetchForMissingDocuments = (boolean)0;
            this.skipFetchForMissingDocuments_ = (boolean)0;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory clearTypeRestrict() {
            this.hasTypeRestrict = (boolean)0;
            this.typeRestrict_ = 1;
            return this;
         }

         public long getAndroidIdRestrict(int var1) {
            return ((Long)this.androidIdRestrict_.get(var1)).longValue();
         }

         public int getAndroidIdRestrictCount() {
            return this.androidIdRestrict_.size();
         }

         public List<Long> getAndroidIdRestrictList() {
            return this.androidIdRestrict_;
         }

         public int getBackendRestrict() {
            return this.backendRestrict_;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public Common.Docid getDocid(int var1) {
            return (Common.Docid)this.docid_.get(var1);
         }

         public int getDocidCount() {
            return this.docid_.size();
         }

         public List<Common.Docid> getDocidList() {
            return this.docid_;
         }

         public int getDocumentTypeRestrict(int var1) {
            return ((Integer)this.documentTypeRestrict_.get(var1)).intValue();
         }

         public int getDocumentTypeRestrictCount() {
            return this.documentTypeRestrict_.size();
         }

         public List<Integer> getDocumentTypeRestrictList() {
            return this.documentTypeRestrict_;
         }

         public int getNum() {
            return this.num_;
         }

         public int getOffset() {
            return this.offset_;
         }

         public int getOrderBy() {
            return this.orderBy_;
         }

         public boolean getOrderDescending() {
            return this.orderDescending_;
         }

         public String getPurchaseId(int var1) {
            return (String)this.purchaseId_.get(var1);
         }

         public int getPurchaseIdCount() {
            return this.purchaseId_.size();
         }

         public List<String> getPurchaseIdList() {
            return this.purchaseId_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasNum()) {
               int var2 = this.getNum();
               int var3 = CodedOutputStreamMicro.computeInt32Size(3, var2);
               var1 = 0 + var3;
            }

            if(this.hasOffset()) {
               int var4 = this.getOffset();
               int var5 = CodedOutputStreamMicro.computeInt32Size(4, var4);
               var1 += var5;
            }

            int var6 = 0;

            int var8;
            for(Iterator var7 = this.getPurchaseIdList().iterator(); var7.hasNext(); var6 += var8) {
               var8 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var7.next());
            }

            int var9 = var1 + var6;
            int var10 = this.getPurchaseIdList().size() * 1;
            int var11 = var9 + var10;

            int var14;
            for(Iterator var12 = this.getDocidList().iterator(); var12.hasNext(); var11 += var14) {
               Common.Docid var13 = (Common.Docid)var12.next();
               var14 = CodedOutputStreamMicro.computeMessageSize(6, var13);
            }

            if(this.hasOrderBy()) {
               int var15 = this.getOrderBy();
               int var16 = CodedOutputStreamMicro.computeInt32Size(7, var15);
               var11 += var16;
            }

            if(this.hasTypeRestrict()) {
               int var17 = this.getTypeRestrict();
               int var18 = CodedOutputStreamMicro.computeInt32Size(8, var17);
               var11 += var18;
            }

            if(this.hasBackendRestrict()) {
               int var19 = this.getBackendRestrict();
               int var20 = CodedOutputStreamMicro.computeInt32Size(9, var19);
               var11 += var20;
            }

            if(this.hasOrderDescending()) {
               boolean var21 = this.getOrderDescending();
               int var22 = CodedOutputStreamMicro.computeBoolSize(10, var21);
               var11 += var22;
            }

            if(this.hasSkipFetchForMissingDocuments()) {
               boolean var23 = this.getSkipFetchForMissingDocuments();
               int var24 = CodedOutputStreamMicro.computeBoolSize(11, var23);
               var11 += var24;
            }

            int var25 = 0;

            int var27;
            for(Iterator var26 = this.getDocumentTypeRestrictList().iterator(); var26.hasNext(); var25 += var27) {
               var27 = CodedOutputStreamMicro.computeInt32SizeNoTag(((Integer)var26.next()).intValue());
            }

            int var28 = var11 + var25;
            int var29 = this.getDocumentTypeRestrictList().size() * 2;
            int var30 = var28 + var29;
            int var31 = 0;

            int var33;
            for(Iterator var32 = this.getAndroidIdRestrictList().iterator(); var32.hasNext(); var31 += var33) {
               var33 = CodedOutputStreamMicro.computeInt64SizeNoTag(((Long)var32.next()).longValue());
            }

            int var34 = var30 + var31;
            int var35 = this.getAndroidIdRestrictList().size() * 2;
            int var36 = var34 + var35;
            this.cachedSize = var36;
            return var36;
         }

         public boolean getSkipFetchForMissingDocuments() {
            return this.skipFetchForMissingDocuments_;
         }

         public int getTypeRestrict() {
            return this.typeRestrict_;
         }

         public boolean hasBackendRestrict() {
            return this.hasBackendRestrict;
         }

         public boolean hasNum() {
            return this.hasNum;
         }

         public boolean hasOffset() {
            return this.hasOffset;
         }

         public boolean hasOrderBy() {
            return this.hasOrderBy;
         }

         public boolean hasOrderDescending() {
            return this.hasOrderDescending;
         }

         public boolean hasSkipFetchForMissingDocuments() {
            return this.hasSkipFetchForMissingDocuments;
         }

         public boolean hasTypeRestrict() {
            return this.hasTypeRestrict;
         }

         public final boolean isInitialized() {
            Iterator var1 = this.getDocidList().iterator();

            boolean var2;
            while(true) {
               if(var1.hasNext()) {
                  if(((Common.Docid)var1.next()).isInitialized()) {
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

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 24:
                  int var3 = var1.readInt32();
                  this.setNum(var3);
                  break;
               case 32:
                  int var5 = var1.readInt32();
                  this.setOffset(var5);
                  break;
               case 42:
                  String var7 = var1.readString();
                  this.addPurchaseId(var7);
                  break;
               case 50:
                  Common.Docid var9 = new Common.Docid();
                  var1.readMessage(var9);
                  this.addDocid(var9);
                  break;
               case 56:
                  int var11 = var1.readInt32();
                  this.setOrderBy(var11);
                  break;
               case 64:
                  int var13 = var1.readInt32();
                  this.setTypeRestrict(var13);
                  break;
               case 72:
                  int var15 = var1.readInt32();
                  this.setBackendRestrict(var15);
                  break;
               case 80:
                  boolean var17 = var1.readBool();
                  this.setOrderDescending(var17);
                  break;
               case 88:
                  boolean var19 = var1.readBool();
                  this.setSkipFetchForMissingDocuments(var19);
                  break;
               case 208:
                  int var21 = var1.readInt32();
                  this.addDocumentTypeRestrict(var21);
                  break;
               case 216:
                  long var23 = var1.readInt64();
                  this.addAndroidIdRestrict(var23);
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

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.PurchaseHistoryRequest.GetPurchaseHistory()).mergeFrom(var1);
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.PurchaseHistoryRequest.GetPurchaseHistory)((UserAccount.PurchaseHistoryRequest.GetPurchaseHistory)(new UserAccount.PurchaseHistoryRequest.GetPurchaseHistory()).mergeFrom(var1));
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setAndroidIdRestrict(int var1, long var2) {
            List var4 = this.androidIdRestrict_;
            Long var5 = Long.valueOf(var2);
            var4.set(var1, var5);
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setBackendRestrict(int var1) {
            this.hasBackendRestrict = (boolean)1;
            this.backendRestrict_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setDocid(int var1, Common.Docid var2) {
            if(var2 == null) {
               throw new NullPointerException();
            } else {
               this.docid_.set(var1, var2);
               return this;
            }
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setDocumentTypeRestrict(int var1, int var2) {
            List var3 = this.documentTypeRestrict_;
            Integer var4 = Integer.valueOf(var2);
            var3.set(var1, var4);
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setNum(int var1) {
            this.hasNum = (boolean)1;
            this.num_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setOffset(int var1) {
            this.hasOffset = (boolean)1;
            this.offset_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setOrderBy(int var1) {
            this.hasOrderBy = (boolean)1;
            this.orderBy_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setOrderDescending(boolean var1) {
            this.hasOrderDescending = (boolean)1;
            this.orderDescending_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setPurchaseId(int var1, String var2) {
            if(var2 == null) {
               throw new NullPointerException();
            } else {
               this.purchaseId_.set(var1, var2);
               return this;
            }
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setSkipFetchForMissingDocuments(boolean var1) {
            this.hasSkipFetchForMissingDocuments = (boolean)1;
            this.skipFetchForMissingDocuments_ = var1;
            return this;
         }

         public UserAccount.PurchaseHistoryRequest.GetPurchaseHistory setTypeRestrict(int var1) {
            this.hasTypeRestrict = (boolean)1;
            this.typeRestrict_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasNum()) {
               int var2 = this.getNum();
               var1.writeInt32(3, var2);
            }

            if(this.hasOffset()) {
               int var3 = this.getOffset();
               var1.writeInt32(4, var3);
            }

            Iterator var4 = this.getPurchaseIdList().iterator();

            while(var4.hasNext()) {
               String var5 = (String)var4.next();
               var1.writeString(5, var5);
            }

            Iterator var6 = this.getDocidList().iterator();

            while(var6.hasNext()) {
               Common.Docid var7 = (Common.Docid)var6.next();
               var1.writeMessage(6, var7);
            }

            if(this.hasOrderBy()) {
               int var8 = this.getOrderBy();
               var1.writeInt32(7, var8);
            }

            if(this.hasTypeRestrict()) {
               int var9 = this.getTypeRestrict();
               var1.writeInt32(8, var9);
            }

            if(this.hasBackendRestrict()) {
               int var10 = this.getBackendRestrict();
               var1.writeInt32(9, var10);
            }

            if(this.hasOrderDescending()) {
               boolean var11 = this.getOrderDescending();
               var1.writeBool(10, var11);
            }

            if(this.hasSkipFetchForMissingDocuments()) {
               boolean var12 = this.getSkipFetchForMissingDocuments();
               var1.writeBool(11, var12);
            }

            Iterator var13 = this.getDocumentTypeRestrictList().iterator();

            while(var13.hasNext()) {
               int var14 = ((Integer)var13.next()).intValue();
               var1.writeInt32(26, var14);
            }

            Iterator var15 = this.getAndroidIdRestrictList().iterator();

            while(var15.hasNext()) {
               long var16 = ((Long)var15.next()).longValue();
               var1.writeInt64(27, var16);
            }

         }
      }

      public static final class DeletePurchaseHistory extends MessageMicro {

         private int cachedSize = -1;


         public DeletePurchaseHistory() {}

         public final UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory clear() {
            this.cachedSize = -1;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public int getSerializedSize() {
            this.cachedSize = 0;
            return 0;
         }

         public final boolean isInitialized() {
            return true;
         }

         public UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               default:
                  if(this.parseUnknownField(var1, var2)) {
                     break;
                  }
               case 0:
                  return this;
               }
            }
         }

         public UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory()).mergeFrom(var1);
         }

         public UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory)((UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory)(new UserAccount.PurchaseHistoryRequest.DeletePurchaseHistory()).mergeFrom(var1));
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {}
      }
   }

   public static final class Tos extends MessageMicro {

      public static final int ACCEPTED_TIME_MSEC_FIELD_NUMBER = 1;
      public static final int ACCEPTED_VERSION_FIELD_NUMBER = 2;
      private long acceptedTimeMsec_ = 0L;
      private String acceptedVersion_ = "";
      private int cachedSize = -1;
      private boolean hasAcceptedTimeMsec;
      private boolean hasAcceptedVersion;


      public Tos() {}

      public static UserAccount.Tos parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.Tos()).mergeFrom(var0);
      }

      public static UserAccount.Tos parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.Tos)((UserAccount.Tos)(new UserAccount.Tos()).mergeFrom(var0));
      }

      public final UserAccount.Tos clear() {
         UserAccount.Tos var1 = this.clearAcceptedTimeMsec();
         UserAccount.Tos var2 = this.clearAcceptedVersion();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.Tos clearAcceptedTimeMsec() {
         this.hasAcceptedTimeMsec = (boolean)0;
         this.acceptedTimeMsec_ = 0L;
         return this;
      }

      public UserAccount.Tos clearAcceptedVersion() {
         this.hasAcceptedVersion = (boolean)0;
         this.acceptedVersion_ = "";
         return this;
      }

      public long getAcceptedTimeMsec() {
         return this.acceptedTimeMsec_;
      }

      public String getAcceptedVersion() {
         return this.acceptedVersion_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasAcceptedTimeMsec()) {
            long var2 = this.getAcceptedTimeMsec();
            int var4 = CodedOutputStreamMicro.computeInt64Size(1, var2);
            var1 = 0 + var4;
         }

         if(this.hasAcceptedVersion()) {
            String var5 = this.getAcceptedVersion();
            int var6 = CodedOutputStreamMicro.computeStringSize(2, var5);
            var1 += var6;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasAcceptedTimeMsec() {
         return this.hasAcceptedTimeMsec;
      }

      public boolean hasAcceptedVersion() {
         return this.hasAcceptedVersion;
      }

      public final boolean isInitialized() {
         return true;
      }

      public UserAccount.Tos mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               long var3 = var1.readInt64();
               this.setAcceptedTimeMsec(var3);
               break;
            case 18:
               String var6 = var1.readString();
               this.setAcceptedVersion(var6);
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

      public UserAccount.Tos setAcceptedTimeMsec(long var1) {
         this.hasAcceptedTimeMsec = (boolean)1;
         this.acceptedTimeMsec_ = var1;
         return this;
      }

      public UserAccount.Tos setAcceptedVersion(String var1) {
         this.hasAcceptedVersion = (boolean)1;
         this.acceptedVersion_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasAcceptedTimeMsec()) {
            long var2 = this.getAcceptedTimeMsec();
            var1.writeInt64(1, var2);
         }

         if(this.hasAcceptedVersion()) {
            String var4 = this.getAcceptedVersion();
            var1.writeString(2, var4);
         }
      }
   }

   public static final class DeviceConfigurationRequest extends MessageMicro {

      public static final int ANDROID_ID_FIELD_NUMBER = 1;
      public static final int GETANDROIDGROUPS_FIELD_NUMBER = 2;
      private List<Long> androidId_;
      private int cachedSize;
      private UserAccount.DeviceConfigurationRequest.GetAndroidGroups getAndroidGroups_;
      private boolean hasGetAndroidGroups;


      public DeviceConfigurationRequest() {
         List var1 = Collections.emptyList();
         this.androidId_ = var1;
         this.getAndroidGroups_ = null;
         this.cachedSize = -1;
      }

      public static UserAccount.DeviceConfigurationRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.DeviceConfigurationRequest()).mergeFrom(var0);
      }

      public static UserAccount.DeviceConfigurationRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.DeviceConfigurationRequest)((UserAccount.DeviceConfigurationRequest)(new UserAccount.DeviceConfigurationRequest()).mergeFrom(var0));
      }

      public UserAccount.DeviceConfigurationRequest addAndroidId(long var1) {
         if(this.androidId_.isEmpty()) {
            ArrayList var3 = new ArrayList();
            this.androidId_ = var3;
         }

         List var4 = this.androidId_;
         Long var5 = Long.valueOf(var1);
         var4.add(var5);
         return this;
      }

      public final UserAccount.DeviceConfigurationRequest clear() {
         UserAccount.DeviceConfigurationRequest var1 = this.clearAndroidId();
         UserAccount.DeviceConfigurationRequest var2 = this.clearGetAndroidGroups();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.DeviceConfigurationRequest clearAndroidId() {
         List var1 = Collections.emptyList();
         this.androidId_ = var1;
         return this;
      }

      public UserAccount.DeviceConfigurationRequest clearGetAndroidGroups() {
         this.hasGetAndroidGroups = (boolean)0;
         this.getAndroidGroups_ = null;
         return this;
      }

      public long getAndroidId(int var1) {
         return ((Long)this.androidId_.get(var1)).longValue();
      }

      public int getAndroidIdCount() {
         return this.androidId_.size();
      }

      public List<Long> getAndroidIdList() {
         return this.androidId_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public UserAccount.DeviceConfigurationRequest.GetAndroidGroups getGetAndroidGroups() {
         return this.getAndroidGroups_;
      }

      public int getSerializedSize() {
         int var1 = this.getAndroidIdList().size() * 8;
         int var2 = 0 + var1;
         int var3 = this.getAndroidIdList().size() * 1;
         int var4 = var2 + var3;
         if(this.hasGetAndroidGroups()) {
            UserAccount.DeviceConfigurationRequest.GetAndroidGroups var5 = this.getGetAndroidGroups();
            int var6 = CodedOutputStreamMicro.computeGroupSize(2, var5);
            var4 += var6;
         }

         this.cachedSize = var4;
         return var4;
      }

      public boolean hasGetAndroidGroups() {
         return this.hasGetAndroidGroups;
      }

      public final boolean isInitialized() {
         return true;
      }

      public UserAccount.DeviceConfigurationRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 9:
               long var3 = var1.readFixed64();
               this.addAndroidId(var3);
               break;
            case 19:
               UserAccount.DeviceConfigurationRequest.GetAndroidGroups var6 = new UserAccount.DeviceConfigurationRequest.GetAndroidGroups();
               var1.readGroup(var6, 2);
               this.setGetAndroidGroups(var6);
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

      public UserAccount.DeviceConfigurationRequest setAndroidId(int var1, long var2) {
         List var4 = this.androidId_;
         Long var5 = Long.valueOf(var2);
         var4.set(var1, var5);
         return this;
      }

      public UserAccount.DeviceConfigurationRequest setGetAndroidGroups(UserAccount.DeviceConfigurationRequest.GetAndroidGroups var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasGetAndroidGroups = (boolean)1;
            this.getAndroidGroups_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getAndroidIdList().iterator();

         while(var2.hasNext()) {
            long var3 = ((Long)var2.next()).longValue();
            var1.writeFixed64(1, var3);
         }

         if(this.hasGetAndroidGroups()) {
            UserAccount.DeviceConfigurationRequest.GetAndroidGroups var5 = this.getGetAndroidGroups();
            var1.writeGroup(2, var5);
         }
      }

      public static final class GetAndroidGroups extends MessageMicro {

         private int cachedSize = -1;


         public GetAndroidGroups() {}

         public final UserAccount.DeviceConfigurationRequest.GetAndroidGroups clear() {
            this.cachedSize = -1;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public int getSerializedSize() {
            this.cachedSize = 0;
            return 0;
         }

         public final boolean isInitialized() {
            return true;
         }

         public UserAccount.DeviceConfigurationRequest.GetAndroidGroups mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               default:
                  if(this.parseUnknownField(var1, var2)) {
                     break;
                  }
               case 0:
                  return this;
               }
            }
         }

         public UserAccount.DeviceConfigurationRequest.GetAndroidGroups parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.DeviceConfigurationRequest.GetAndroidGroups()).mergeFrom(var1);
         }

         public UserAccount.DeviceConfigurationRequest.GetAndroidGroups parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.DeviceConfigurationRequest.GetAndroidGroups)((UserAccount.DeviceConfigurationRequest.GetAndroidGroups)(new UserAccount.DeviceConfigurationRequest.GetAndroidGroups()).mergeFrom(var1));
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {}
      }
   }

   public static final class UserDataRequest extends MessageMicro {

      public static final int CONTEXT_FIELD_NUMBER = 1;
      public static final int DELETEUSERDATA_FIELD_NUMBER = 5;
      public static final int GETUSERDATA_FIELD_NUMBER = 2;
      public static final int GETUSERDEVICES_FIELD_NUMBER = 6;
      public static final int GETUSERNAME_FIELD_NUMBER = 10;
      public static final int UPDATEDEVICENAME_FIELD_NUMBER = 13;
      public static final int UPDATEUSERDATA_FIELD_NUMBER = 3;
      private int cachedSize = -1;
      private RequestContext context_ = null;
      private UserAccount.UserDataRequest.DeleteUserData deleteUserData_ = null;
      private UserAccount.UserDataRequest.GetUserData getUserData_ = null;
      private UserAccount.UserDataRequest.GetUserDevices getUserDevices_ = null;
      private UserAccount.UserDataRequest.GetUserName getUserName_ = null;
      private boolean hasContext;
      private boolean hasDeleteUserData;
      private boolean hasGetUserData;
      private boolean hasGetUserDevices;
      private boolean hasGetUserName;
      private boolean hasUpdateDeviceName;
      private boolean hasUpdateUserData;
      private UserAccount.UserDataRequest.UpdateDeviceName updateDeviceName_ = null;
      private UserAccount.UserDataRequest.UpdateUserData updateUserData_ = null;


      public UserDataRequest() {}

      public static UserAccount.UserDataRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.UserDataRequest()).mergeFrom(var0);
      }

      public static UserAccount.UserDataRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.UserDataRequest)((UserAccount.UserDataRequest)(new UserAccount.UserDataRequest()).mergeFrom(var0));
      }

      public final UserAccount.UserDataRequest clear() {
         UserAccount.UserDataRequest var1 = this.clearContext();
         UserAccount.UserDataRequest var2 = this.clearGetUserData();
         UserAccount.UserDataRequest var3 = this.clearUpdateUserData();
         UserAccount.UserDataRequest var4 = this.clearDeleteUserData();
         UserAccount.UserDataRequest var5 = this.clearGetUserDevices();
         UserAccount.UserDataRequest var6 = this.clearGetUserName();
         UserAccount.UserDataRequest var7 = this.clearUpdateDeviceName();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.UserDataRequest clearContext() {
         this.hasContext = (boolean)0;
         this.context_ = null;
         return this;
      }

      public UserAccount.UserDataRequest clearDeleteUserData() {
         this.hasDeleteUserData = (boolean)0;
         this.deleteUserData_ = null;
         return this;
      }

      public UserAccount.UserDataRequest clearGetUserData() {
         this.hasGetUserData = (boolean)0;
         this.getUserData_ = null;
         return this;
      }

      public UserAccount.UserDataRequest clearGetUserDevices() {
         this.hasGetUserDevices = (boolean)0;
         this.getUserDevices_ = null;
         return this;
      }

      public UserAccount.UserDataRequest clearGetUserName() {
         this.hasGetUserName = (boolean)0;
         this.getUserName_ = null;
         return this;
      }

      public UserAccount.UserDataRequest clearUpdateDeviceName() {
         this.hasUpdateDeviceName = (boolean)0;
         this.updateDeviceName_ = null;
         return this;
      }

      public UserAccount.UserDataRequest clearUpdateUserData() {
         this.hasUpdateUserData = (boolean)0;
         this.updateUserData_ = null;
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

      public UserAccount.UserDataRequest.DeleteUserData getDeleteUserData() {
         return this.deleteUserData_;
      }

      public UserAccount.UserDataRequest.GetUserData getGetUserData() {
         return this.getUserData_;
      }

      public UserAccount.UserDataRequest.GetUserDevices getGetUserDevices() {
         return this.getUserDevices_;
      }

      public UserAccount.UserDataRequest.GetUserName getGetUserName() {
         return this.getUserName_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasGetUserData()) {
            UserAccount.UserDataRequest.GetUserData var4 = this.getGetUserData();
            int var5 = CodedOutputStreamMicro.computeGroupSize(2, var4);
            var1 += var5;
         }

         if(this.hasUpdateUserData()) {
            UserAccount.UserDataRequest.UpdateUserData var6 = this.getUpdateUserData();
            int var7 = CodedOutputStreamMicro.computeGroupSize(3, var6);
            var1 += var7;
         }

         if(this.hasDeleteUserData()) {
            UserAccount.UserDataRequest.DeleteUserData var8 = this.getDeleteUserData();
            int var9 = CodedOutputStreamMicro.computeGroupSize(5, var8);
            var1 += var9;
         }

         if(this.hasGetUserDevices()) {
            UserAccount.UserDataRequest.GetUserDevices var10 = this.getGetUserDevices();
            int var11 = CodedOutputStreamMicro.computeGroupSize(6, var10);
            var1 += var11;
         }

         if(this.hasGetUserName()) {
            UserAccount.UserDataRequest.GetUserName var12 = this.getGetUserName();
            int var13 = CodedOutputStreamMicro.computeGroupSize(10, var12);
            var1 += var13;
         }

         if(this.hasUpdateDeviceName()) {
            UserAccount.UserDataRequest.UpdateDeviceName var14 = this.getUpdateDeviceName();
            int var15 = CodedOutputStreamMicro.computeGroupSize(13, var14);
            var1 += var15;
         }

         this.cachedSize = var1;
         return var1;
      }

      public UserAccount.UserDataRequest.UpdateDeviceName getUpdateDeviceName() {
         return this.updateDeviceName_;
      }

      public UserAccount.UserDataRequest.UpdateUserData getUpdateUserData() {
         return this.updateUserData_;
      }

      public boolean hasContext() {
         return this.hasContext;
      }

      public boolean hasDeleteUserData() {
         return this.hasDeleteUserData;
      }

      public boolean hasGetUserData() {
         return this.hasGetUserData;
      }

      public boolean hasGetUserDevices() {
         return this.hasGetUserDevices;
      }

      public boolean hasGetUserName() {
         return this.hasGetUserName;
      }

      public boolean hasUpdateDeviceName() {
         return this.hasUpdateDeviceName;
      }

      public boolean hasUpdateUserData() {
         return this.hasUpdateUserData;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasContext && this.getContext().isInitialized() && (!this.hasUpdateUserData() || this.getUpdateUserData().isInitialized()) && (!this.hasUpdateDeviceName() || this.getUpdateDeviceName().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public UserAccount.UserDataRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               RequestContext var3 = new RequestContext();
               var1.readMessage(var3);
               this.setContext(var3);
               break;
            case 19:
               UserAccount.UserDataRequest.GetUserData var5 = new UserAccount.UserDataRequest.GetUserData();
               var1.readGroup(var5, 2);
               this.setGetUserData(var5);
               break;
            case 27:
               UserAccount.UserDataRequest.UpdateUserData var7 = new UserAccount.UserDataRequest.UpdateUserData();
               var1.readGroup(var7, 3);
               this.setUpdateUserData(var7);
               break;
            case 43:
               UserAccount.UserDataRequest.DeleteUserData var9 = new UserAccount.UserDataRequest.DeleteUserData();
               var1.readGroup(var9, 5);
               this.setDeleteUserData(var9);
               break;
            case 51:
               UserAccount.UserDataRequest.GetUserDevices var11 = new UserAccount.UserDataRequest.GetUserDevices();
               var1.readGroup(var11, 6);
               this.setGetUserDevices(var11);
               break;
            case 83:
               UserAccount.UserDataRequest.GetUserName var13 = new UserAccount.UserDataRequest.GetUserName();
               var1.readGroup(var13, 10);
               this.setGetUserName(var13);
               break;
            case 107:
               UserAccount.UserDataRequest.UpdateDeviceName var15 = new UserAccount.UserDataRequest.UpdateDeviceName();
               var1.readGroup(var15, 13);
               this.setUpdateDeviceName(var15);
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

      public UserAccount.UserDataRequest setContext(RequestContext var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasContext = (boolean)1;
            this.context_ = var1;
            return this;
         }
      }

      public UserAccount.UserDataRequest setDeleteUserData(UserAccount.UserDataRequest.DeleteUserData var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDeleteUserData = (boolean)1;
            this.deleteUserData_ = var1;
            return this;
         }
      }

      public UserAccount.UserDataRequest setGetUserData(UserAccount.UserDataRequest.GetUserData var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasGetUserData = (boolean)1;
            this.getUserData_ = var1;
            return this;
         }
      }

      public UserAccount.UserDataRequest setGetUserDevices(UserAccount.UserDataRequest.GetUserDevices var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasGetUserDevices = (boolean)1;
            this.getUserDevices_ = var1;
            return this;
         }
      }

      public UserAccount.UserDataRequest setGetUserName(UserAccount.UserDataRequest.GetUserName var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasGetUserName = (boolean)1;
            this.getUserName_ = var1;
            return this;
         }
      }

      public UserAccount.UserDataRequest setUpdateDeviceName(UserAccount.UserDataRequest.UpdateDeviceName var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasUpdateDeviceName = (boolean)1;
            this.updateDeviceName_ = var1;
            return this;
         }
      }

      public UserAccount.UserDataRequest setUpdateUserData(UserAccount.UserDataRequest.UpdateUserData var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasUpdateUserData = (boolean)1;
            this.updateUserData_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            var1.writeMessage(1, var2);
         }

         if(this.hasGetUserData()) {
            UserAccount.UserDataRequest.GetUserData var3 = this.getGetUserData();
            var1.writeGroup(2, var3);
         }

         if(this.hasUpdateUserData()) {
            UserAccount.UserDataRequest.UpdateUserData var4 = this.getUpdateUserData();
            var1.writeGroup(3, var4);
         }

         if(this.hasDeleteUserData()) {
            UserAccount.UserDataRequest.DeleteUserData var5 = this.getDeleteUserData();
            var1.writeGroup(5, var5);
         }

         if(this.hasGetUserDevices()) {
            UserAccount.UserDataRequest.GetUserDevices var6 = this.getGetUserDevices();
            var1.writeGroup(6, var6);
         }

         if(this.hasGetUserName()) {
            UserAccount.UserDataRequest.GetUserName var7 = this.getGetUserName();
            var1.writeGroup(10, var7);
         }

         if(this.hasUpdateDeviceName()) {
            UserAccount.UserDataRequest.UpdateDeviceName var8 = this.getUpdateDeviceName();
            var1.writeGroup(13, var8);
         }
      }

      public static final class DeleteUserData extends MessageMicro {

         private int cachedSize = -1;


         public DeleteUserData() {}

         public final UserAccount.UserDataRequest.DeleteUserData clear() {
            this.cachedSize = -1;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public int getSerializedSize() {
            this.cachedSize = 0;
            return 0;
         }

         public final boolean isInitialized() {
            return true;
         }

         public UserAccount.UserDataRequest.DeleteUserData mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               default:
                  if(this.parseUnknownField(var1, var2)) {
                     break;
                  }
               case 0:
                  return this;
               }
            }
         }

         public UserAccount.UserDataRequest.DeleteUserData parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.UserDataRequest.DeleteUserData()).mergeFrom(var1);
         }

         public UserAccount.UserDataRequest.DeleteUserData parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.UserDataRequest.DeleteUserData)((UserAccount.UserDataRequest.DeleteUserData)(new UserAccount.UserDataRequest.DeleteUserData()).mergeFrom(var1));
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {}
      }

      public static final class GetUserName extends MessageMicro {

         public static final int GAIA_ID_FIELD_NUMBER = 11;
         private int cachedSize;
         private List<Long> gaiaId_;


         public GetUserName() {
            List var1 = Collections.emptyList();
            this.gaiaId_ = var1;
            this.cachedSize = -1;
         }

         public UserAccount.UserDataRequest.GetUserName addGaiaId(long var1) {
            if(this.gaiaId_.isEmpty()) {
               ArrayList var3 = new ArrayList();
               this.gaiaId_ = var3;
            }

            List var4 = this.gaiaId_;
            Long var5 = Long.valueOf(var1);
            var4.add(var5);
            return this;
         }

         public final UserAccount.UserDataRequest.GetUserName clear() {
            UserAccount.UserDataRequest.GetUserName var1 = this.clearGaiaId();
            this.cachedSize = -1;
            return this;
         }

         public UserAccount.UserDataRequest.GetUserName clearGaiaId() {
            List var1 = Collections.emptyList();
            this.gaiaId_ = var1;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public long getGaiaId(int var1) {
            return ((Long)this.gaiaId_.get(var1)).longValue();
         }

         public int getGaiaIdCount() {
            return this.gaiaId_.size();
         }

         public List<Long> getGaiaIdList() {
            return this.gaiaId_;
         }

         public int getSerializedSize() {
            int var1 = this.getGaiaIdList().size() * 8;
            int var2 = 0 + var1;
            int var3 = this.getGaiaIdList().size() * 1;
            int var4 = var2 + var3;
            this.cachedSize = var4;
            return var4;
         }

         public final boolean isInitialized() {
            return true;
         }

         public UserAccount.UserDataRequest.GetUserName mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 89:
                  long var3 = var1.readFixed64();
                  this.addGaiaId(var3);
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

         public UserAccount.UserDataRequest.GetUserName parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.UserDataRequest.GetUserName()).mergeFrom(var1);
         }

         public UserAccount.UserDataRequest.GetUserName parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.UserDataRequest.GetUserName)((UserAccount.UserDataRequest.GetUserName)(new UserAccount.UserDataRequest.GetUserName()).mergeFrom(var1));
         }

         public UserAccount.UserDataRequest.GetUserName setGaiaId(int var1, long var2) {
            List var4 = this.gaiaId_;
            Long var5 = Long.valueOf(var2);
            var4.set(var1, var5);
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            Iterator var2 = this.getGaiaIdList().iterator();

            while(var2.hasNext()) {
               long var3 = ((Long)var2.next()).longValue();
               var1.writeFixed64(11, var3);
            }

         }
      }

      public static final class UpdateUserData extends MessageMicro {

         public static final int UPDATE_DELTA_FIELD_NUMBER = 4;
         private int cachedSize = -1;
         private boolean hasUpdateDelta;
         private UserAccount.UserData updateDelta_ = null;


         public UpdateUserData() {}

         public final UserAccount.UserDataRequest.UpdateUserData clear() {
            UserAccount.UserDataRequest.UpdateUserData var1 = this.clearUpdateDelta();
            this.cachedSize = -1;
            return this;
         }

         public UserAccount.UserDataRequest.UpdateUserData clearUpdateDelta() {
            this.hasUpdateDelta = (boolean)0;
            this.updateDelta_ = null;
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
            if(this.hasUpdateDelta()) {
               UserAccount.UserData var2 = this.getUpdateDelta();
               int var3 = CodedOutputStreamMicro.computeMessageSize(4, var2);
               var1 = 0 + var3;
            }

            this.cachedSize = var1;
            return var1;
         }

         public UserAccount.UserData getUpdateDelta() {
            return this.updateDelta_;
         }

         public boolean hasUpdateDelta() {
            return this.hasUpdateDelta;
         }

         public final boolean isInitialized() {
            boolean var1;
            if(!this.hasUpdateDelta) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }

         public UserAccount.UserDataRequest.UpdateUserData mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 34:
                  UserAccount.UserData var3 = new UserAccount.UserData();
                  var1.readMessage(var3);
                  this.setUpdateDelta(var3);
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

         public UserAccount.UserDataRequest.UpdateUserData parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.UserDataRequest.UpdateUserData()).mergeFrom(var1);
         }

         public UserAccount.UserDataRequest.UpdateUserData parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.UserDataRequest.UpdateUserData)((UserAccount.UserDataRequest.UpdateUserData)(new UserAccount.UserDataRequest.UpdateUserData()).mergeFrom(var1));
         }

         public UserAccount.UserDataRequest.UpdateUserData setUpdateDelta(UserAccount.UserData var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasUpdateDelta = (boolean)1;
               this.updateDelta_ = var1;
               return this;
            }
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasUpdateDelta()) {
               UserAccount.UserData var2 = this.getUpdateDelta();
               var1.writeMessage(4, var2);
            }
         }
      }

      public static final class GetUserData extends MessageMicro {

         private int cachedSize = -1;


         public GetUserData() {}

         public final UserAccount.UserDataRequest.GetUserData clear() {
            this.cachedSize = -1;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public int getSerializedSize() {
            this.cachedSize = 0;
            return 0;
         }

         public final boolean isInitialized() {
            return true;
         }

         public UserAccount.UserDataRequest.GetUserData mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               default:
                  if(this.parseUnknownField(var1, var2)) {
                     break;
                  }
               case 0:
                  return this;
               }
            }
         }

         public UserAccount.UserDataRequest.GetUserData parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.UserDataRequest.GetUserData()).mergeFrom(var1);
         }

         public UserAccount.UserDataRequest.GetUserData parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.UserDataRequest.GetUserData)((UserAccount.UserDataRequest.GetUserData)(new UserAccount.UserDataRequest.GetUserData()).mergeFrom(var1));
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {}
      }

      public static final class UpdateDeviceName extends MessageMicro {

         public static final int ANDROID_ID_FIELD_NUMBER = 14;
         public static final int FRIENDLY_NAME_FIELD_NUMBER = 15;
         private long androidId_ = 0L;
         private int cachedSize = -1;
         private String friendlyName_ = "";
         private boolean hasAndroidId;
         private boolean hasFriendlyName;


         public UpdateDeviceName() {}

         public final UserAccount.UserDataRequest.UpdateDeviceName clear() {
            UserAccount.UserDataRequest.UpdateDeviceName var1 = this.clearAndroidId();
            UserAccount.UserDataRequest.UpdateDeviceName var2 = this.clearFriendlyName();
            this.cachedSize = -1;
            return this;
         }

         public UserAccount.UserDataRequest.UpdateDeviceName clearAndroidId() {
            this.hasAndroidId = (boolean)0;
            this.androidId_ = 0L;
            return this;
         }

         public UserAccount.UserDataRequest.UpdateDeviceName clearFriendlyName() {
            this.hasFriendlyName = (boolean)0;
            this.friendlyName_ = "";
            return this;
         }

         public long getAndroidId() {
            return this.androidId_;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public String getFriendlyName() {
            return this.friendlyName_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasAndroidId()) {
               long var2 = this.getAndroidId();
               int var4 = CodedOutputStreamMicro.computeFixed64Size(14, var2);
               var1 = 0 + var4;
            }

            if(this.hasFriendlyName()) {
               String var5 = this.getFriendlyName();
               int var6 = CodedOutputStreamMicro.computeStringSize(15, var5);
               var1 += var6;
            }

            this.cachedSize = var1;
            return var1;
         }

         public boolean hasAndroidId() {
            return this.hasAndroidId;
         }

         public boolean hasFriendlyName() {
            return this.hasFriendlyName;
         }

         public final boolean isInitialized() {
            boolean var1;
            if(!this.hasAndroidId) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }

         public UserAccount.UserDataRequest.UpdateDeviceName mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 113:
                  long var3 = var1.readFixed64();
                  this.setAndroidId(var3);
                  break;
               case 122:
                  String var6 = var1.readString();
                  this.setFriendlyName(var6);
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

         public UserAccount.UserDataRequest.UpdateDeviceName parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.UserDataRequest.UpdateDeviceName()).mergeFrom(var1);
         }

         public UserAccount.UserDataRequest.UpdateDeviceName parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.UserDataRequest.UpdateDeviceName)((UserAccount.UserDataRequest.UpdateDeviceName)(new UserAccount.UserDataRequest.UpdateDeviceName()).mergeFrom(var1));
         }

         public UserAccount.UserDataRequest.UpdateDeviceName setAndroidId(long var1) {
            this.hasAndroidId = (boolean)1;
            this.androidId_ = var1;
            return this;
         }

         public UserAccount.UserDataRequest.UpdateDeviceName setFriendlyName(String var1) {
            this.hasFriendlyName = (boolean)1;
            this.friendlyName_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasAndroidId()) {
               long var2 = this.getAndroidId();
               var1.writeFixed64(14, var2);
            }

            if(this.hasFriendlyName()) {
               String var4 = this.getFriendlyName();
               var1.writeString(15, var4);
            }
         }
      }

      public static final class GetUserDevices extends MessageMicro {

         public static final int MAX_AGE_MSEC_FIELD_NUMBER = 12;
         private int cachedSize = -1;
         private boolean hasMaxAgeMsec;
         private long maxAgeMsec_ = 0L;


         public GetUserDevices() {}

         public final UserAccount.UserDataRequest.GetUserDevices clear() {
            UserAccount.UserDataRequest.GetUserDevices var1 = this.clearMaxAgeMsec();
            this.cachedSize = -1;
            return this;
         }

         public UserAccount.UserDataRequest.GetUserDevices clearMaxAgeMsec() {
            this.hasMaxAgeMsec = (boolean)0;
            this.maxAgeMsec_ = 0L;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public long getMaxAgeMsec() {
            return this.maxAgeMsec_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasMaxAgeMsec()) {
               long var2 = this.getMaxAgeMsec();
               int var4 = CodedOutputStreamMicro.computeFixed64Size(12, var2);
               var1 = 0 + var4;
            }

            this.cachedSize = var1;
            return var1;
         }

         public boolean hasMaxAgeMsec() {
            return this.hasMaxAgeMsec;
         }

         public final boolean isInitialized() {
            return true;
         }

         public UserAccount.UserDataRequest.GetUserDevices mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 97:
                  long var3 = var1.readFixed64();
                  this.setMaxAgeMsec(var3);
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

         public UserAccount.UserDataRequest.GetUserDevices parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.UserDataRequest.GetUserDevices()).mergeFrom(var1);
         }

         public UserAccount.UserDataRequest.GetUserDevices parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.UserDataRequest.GetUserDevices)((UserAccount.UserDataRequest.GetUserDevices)(new UserAccount.UserDataRequest.GetUserDevices()).mergeFrom(var1));
         }

         public UserAccount.UserDataRequest.GetUserDevices setMaxAgeMsec(long var1) {
            this.hasMaxAgeMsec = (boolean)1;
            this.maxAgeMsec_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasMaxAgeMsec()) {
               long var2 = this.getMaxAgeMsec();
               var1.writeFixed64(12, var2);
            }
         }
      }
   }

   public static final class DeviceConfigurationResponse extends MessageMicro {

      public static final int CONFIGURATION_FIELD_NUMBER = 1;
      private int cachedSize;
      private List<UserAccount.DeviceConfigurationResponse.Configuration> configuration_;


      public DeviceConfigurationResponse() {
         List var1 = Collections.emptyList();
         this.configuration_ = var1;
         this.cachedSize = -1;
      }

      public static UserAccount.DeviceConfigurationResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.DeviceConfigurationResponse()).mergeFrom(var0);
      }

      public static UserAccount.DeviceConfigurationResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.DeviceConfigurationResponse)((UserAccount.DeviceConfigurationResponse)(new UserAccount.DeviceConfigurationResponse()).mergeFrom(var0));
      }

      public UserAccount.DeviceConfigurationResponse addConfiguration(UserAccount.DeviceConfigurationResponse.Configuration var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.configuration_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.configuration_ = var2;
            }

            this.configuration_.add(var1);
            return this;
         }
      }

      public final UserAccount.DeviceConfigurationResponse clear() {
         UserAccount.DeviceConfigurationResponse var1 = this.clearConfiguration();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.DeviceConfigurationResponse clearConfiguration() {
         List var1 = Collections.emptyList();
         this.configuration_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public UserAccount.DeviceConfigurationResponse.Configuration getConfiguration(int var1) {
         return (UserAccount.DeviceConfigurationResponse.Configuration)this.configuration_.get(var1);
      }

      public int getConfigurationCount() {
         return this.configuration_.size();
      }

      public List<UserAccount.DeviceConfigurationResponse.Configuration> getConfigurationList() {
         return this.configuration_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getConfigurationList().iterator(); var2.hasNext(); var1 += var4) {
            UserAccount.DeviceConfigurationResponse.Configuration var3 = (UserAccount.DeviceConfigurationResponse.Configuration)var2.next();
            var4 = CodedOutputStreamMicro.computeGroupSize(1, var3);
         }

         this.cachedSize = var1;
         return var1;
      }

      public final boolean isInitialized() {
         Iterator var1 = this.getConfigurationList().iterator();

         boolean var2;
         while(true) {
            if(var1.hasNext()) {
               if(((UserAccount.DeviceConfigurationResponse.Configuration)var1.next()).isInitialized()) {
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

      public UserAccount.DeviceConfigurationResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 11:
               UserAccount.DeviceConfigurationResponse.Configuration var3 = new UserAccount.DeviceConfigurationResponse.Configuration();
               var1.readGroup(var3, 1);
               this.addConfiguration(var3);
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

      public UserAccount.DeviceConfigurationResponse setConfiguration(int var1, UserAccount.DeviceConfigurationResponse.Configuration var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.configuration_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getConfigurationList().iterator();

         while(var2.hasNext()) {
            UserAccount.DeviceConfigurationResponse.Configuration var3 = (UserAccount.DeviceConfigurationResponse.Configuration)var2.next();
            var1.writeGroup(1, var3);
         }

      }

      public static final class Configuration extends MessageMicro {

         public static final int ANDROID_GROUP_FIELD_NUMBER = 4;
         public static final int ANDROID_ID_FIELD_NUMBER = 2;
         public static final int DEVICE_CONFIGURATION_FIELD_NUMBER = 3;
         private List<String> androidGroup_;
         private long androidId_ = 0L;
         private int cachedSize;
         private Dev.Device deviceConfiguration_ = null;
         private boolean hasAndroidId;
         private boolean hasDeviceConfiguration;


         public Configuration() {
            List var1 = Collections.emptyList();
            this.androidGroup_ = var1;
            this.cachedSize = -1;
         }

         public UserAccount.DeviceConfigurationResponse.Configuration addAndroidGroup(String var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               if(this.androidGroup_.isEmpty()) {
                  ArrayList var2 = new ArrayList();
                  this.androidGroup_ = var2;
               }

               this.androidGroup_.add(var1);
               return this;
            }
         }

         public final UserAccount.DeviceConfigurationResponse.Configuration clear() {
            UserAccount.DeviceConfigurationResponse.Configuration var1 = this.clearAndroidId();
            UserAccount.DeviceConfigurationResponse.Configuration var2 = this.clearDeviceConfiguration();
            UserAccount.DeviceConfigurationResponse.Configuration var3 = this.clearAndroidGroup();
            this.cachedSize = -1;
            return this;
         }

         public UserAccount.DeviceConfigurationResponse.Configuration clearAndroidGroup() {
            List var1 = Collections.emptyList();
            this.androidGroup_ = var1;
            return this;
         }

         public UserAccount.DeviceConfigurationResponse.Configuration clearAndroidId() {
            this.hasAndroidId = (boolean)0;
            this.androidId_ = 0L;
            return this;
         }

         public UserAccount.DeviceConfigurationResponse.Configuration clearDeviceConfiguration() {
            this.hasDeviceConfiguration = (boolean)0;
            this.deviceConfiguration_ = null;
            return this;
         }

         public String getAndroidGroup(int var1) {
            return (String)this.androidGroup_.get(var1);
         }

         public int getAndroidGroupCount() {
            return this.androidGroup_.size();
         }

         public List<String> getAndroidGroupList() {
            return this.androidGroup_;
         }

         public long getAndroidId() {
            return this.androidId_;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public Dev.Device getDeviceConfiguration() {
            return this.deviceConfiguration_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasAndroidId()) {
               long var2 = this.getAndroidId();
               int var4 = CodedOutputStreamMicro.computeFixed64Size(2, var2);
               var1 = 0 + var4;
            }

            if(this.hasDeviceConfiguration()) {
               Dev.Device var5 = this.getDeviceConfiguration();
               int var6 = CodedOutputStreamMicro.computeMessageSize(3, var5);
               var1 += var6;
            }

            int var7 = 0;

            int var9;
            for(Iterator var8 = this.getAndroidGroupList().iterator(); var8.hasNext(); var7 += var9) {
               var9 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var8.next());
            }

            int var10 = var1 + var7;
            int var11 = this.getAndroidGroupList().size() * 1;
            int var12 = var10 + var11;
            this.cachedSize = var12;
            return var12;
         }

         public boolean hasAndroidId() {
            return this.hasAndroidId;
         }

         public boolean hasDeviceConfiguration() {
            return this.hasDeviceConfiguration;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if(this.hasAndroidId && (!this.hasDeviceConfiguration() || this.getDeviceConfiguration().isInitialized())) {
               var1 = true;
            }

            return var1;
         }

         public UserAccount.DeviceConfigurationResponse.Configuration mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 17:
                  long var3 = var1.readFixed64();
                  this.setAndroidId(var3);
                  break;
               case 26:
                  Dev.Device var6 = new Dev.Device();
                  var1.readMessage(var6);
                  this.setDeviceConfiguration(var6);
                  break;
               case 34:
                  String var8 = var1.readString();
                  this.addAndroidGroup(var8);
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

         public UserAccount.DeviceConfigurationResponse.Configuration parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new UserAccount.DeviceConfigurationResponse.Configuration()).mergeFrom(var1);
         }

         public UserAccount.DeviceConfigurationResponse.Configuration parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (UserAccount.DeviceConfigurationResponse.Configuration)((UserAccount.DeviceConfigurationResponse.Configuration)(new UserAccount.DeviceConfigurationResponse.Configuration()).mergeFrom(var1));
         }

         public UserAccount.DeviceConfigurationResponse.Configuration setAndroidGroup(int var1, String var2) {
            if(var2 == null) {
               throw new NullPointerException();
            } else {
               this.androidGroup_.set(var1, var2);
               return this;
            }
         }

         public UserAccount.DeviceConfigurationResponse.Configuration setAndroidId(long var1) {
            this.hasAndroidId = (boolean)1;
            this.androidId_ = var1;
            return this;
         }

         public UserAccount.DeviceConfigurationResponse.Configuration setDeviceConfiguration(Dev.Device var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasDeviceConfiguration = (boolean)1;
               this.deviceConfiguration_ = var1;
               return this;
            }
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasAndroidId()) {
               long var2 = this.getAndroidId();
               var1.writeFixed64(2, var2);
            }

            if(this.hasDeviceConfiguration()) {
               Dev.Device var4 = this.getDeviceConfiguration();
               var1.writeMessage(3, var4);
            }

            Iterator var5 = this.getAndroidGroupList().iterator();

            while(var5.hasNext()) {
               String var6 = (String)var5.next();
               var1.writeString(4, var6);
            }

         }
      }
   }

   public static final class DevicePrefs extends MessageMicro {

      public static final int DEVICES_FIELD_NUMBER = 1;
      public static final int TIMESTAMP_MSEC_FIELD_NUMBER = 2;
      private int cachedSize;
      private List<UserAccount.SelectableDevice> devices_;
      private boolean hasTimestampMsec;
      private long timestampMsec_;


      public DevicePrefs() {
         List var1 = Collections.emptyList();
         this.devices_ = var1;
         this.timestampMsec_ = 0L;
         this.cachedSize = -1;
      }

      public static UserAccount.DevicePrefs parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.DevicePrefs()).mergeFrom(var0);
      }

      public static UserAccount.DevicePrefs parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.DevicePrefs)((UserAccount.DevicePrefs)(new UserAccount.DevicePrefs()).mergeFrom(var0));
      }

      public UserAccount.DevicePrefs addDevices(UserAccount.SelectableDevice var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.devices_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.devices_ = var2;
            }

            this.devices_.add(var1);
            return this;
         }
      }

      public final UserAccount.DevicePrefs clear() {
         UserAccount.DevicePrefs var1 = this.clearDevices();
         UserAccount.DevicePrefs var2 = this.clearTimestampMsec();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.DevicePrefs clearDevices() {
         List var1 = Collections.emptyList();
         this.devices_ = var1;
         return this;
      }

      public UserAccount.DevicePrefs clearTimestampMsec() {
         this.hasTimestampMsec = (boolean)0;
         this.timestampMsec_ = 0L;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public UserAccount.SelectableDevice getDevices(int var1) {
         return (UserAccount.SelectableDevice)this.devices_.get(var1);
      }

      public int getDevicesCount() {
         return this.devices_.size();
      }

      public List<UserAccount.SelectableDevice> getDevicesList() {
         return this.devices_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getDevicesList().iterator(); var2.hasNext(); var1 += var4) {
            UserAccount.SelectableDevice var3 = (UserAccount.SelectableDevice)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(1, var3);
         }

         if(this.hasTimestampMsec()) {
            long var5 = this.getTimestampMsec();
            int var7 = CodedOutputStreamMicro.computeInt64Size(2, var5);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public long getTimestampMsec() {
         return this.timestampMsec_;
      }

      public boolean hasTimestampMsec() {
         return this.hasTimestampMsec;
      }

      public final boolean isInitialized() {
         return true;
      }

      public UserAccount.DevicePrefs mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               UserAccount.SelectableDevice var3 = new UserAccount.SelectableDevice();
               var1.readMessage(var3);
               this.addDevices(var3);
               break;
            case 16:
               long var5 = var1.readInt64();
               this.setTimestampMsec(var5);
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

      public UserAccount.DevicePrefs setDevices(int var1, UserAccount.SelectableDevice var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.devices_.set(var1, var2);
            return this;
         }
      }

      public UserAccount.DevicePrefs setTimestampMsec(long var1) {
         this.hasTimestampMsec = (boolean)1;
         this.timestampMsec_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getDevicesList().iterator();

         while(var2.hasNext()) {
            UserAccount.SelectableDevice var3 = (UserAccount.SelectableDevice)var2.next();
            var1.writeMessage(1, var3);
         }

         if(this.hasTimestampMsec()) {
            long var4 = this.getTimestampMsec();
            var1.writeInt64(2, var4);
         }
      }
   }

   public static final class UserDataResponse extends MessageMicro {

      public static final int CACHE_FIELD_NUMBER = 4;
      public static final int DEBUG_INFO_FIELD_NUMBER = 5;
      public static final int USER_DATA_FIELD_NUMBER = 1;
      public static final int USER_DEVICES_FIELD_NUMBER = 2;
      public static final int USER_NAME_FIELD_NUMBER = 3;
      private Cache.CacheData cache_;
      private int cachedSize;
      private DebugInfo debugInfo_;
      private boolean hasCache;
      private boolean hasDebugInfo;
      private boolean hasUserData;
      private UserAccount.UserData userData_ = null;
      private List<UserAccount.SelectableDevice> userDevices_;
      private List<String> userName_;


      public UserDataResponse() {
         List var1 = Collections.emptyList();
         this.userDevices_ = var1;
         List var2 = Collections.emptyList();
         this.userName_ = var2;
         this.cache_ = null;
         this.debugInfo_ = null;
         this.cachedSize = -1;
      }

      public static UserAccount.UserDataResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.UserDataResponse()).mergeFrom(var0);
      }

      public static UserAccount.UserDataResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.UserDataResponse)((UserAccount.UserDataResponse)(new UserAccount.UserDataResponse()).mergeFrom(var0));
      }

      public UserAccount.UserDataResponse addUserDevices(UserAccount.SelectableDevice var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.userDevices_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.userDevices_ = var2;
            }

            this.userDevices_.add(var1);
            return this;
         }
      }

      public UserAccount.UserDataResponse addUserName(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.userName_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.userName_ = var2;
            }

            this.userName_.add(var1);
            return this;
         }
      }

      public final UserAccount.UserDataResponse clear() {
         UserAccount.UserDataResponse var1 = this.clearUserData();
         UserAccount.UserDataResponse var2 = this.clearUserDevices();
         UserAccount.UserDataResponse var3 = this.clearUserName();
         UserAccount.UserDataResponse var4 = this.clearCache();
         UserAccount.UserDataResponse var5 = this.clearDebugInfo();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.UserDataResponse clearCache() {
         this.hasCache = (boolean)0;
         this.cache_ = null;
         return this;
      }

      public UserAccount.UserDataResponse clearDebugInfo() {
         this.hasDebugInfo = (boolean)0;
         this.debugInfo_ = null;
         return this;
      }

      public UserAccount.UserDataResponse clearUserData() {
         this.hasUserData = (boolean)0;
         this.userData_ = null;
         return this;
      }

      public UserAccount.UserDataResponse clearUserDevices() {
         List var1 = Collections.emptyList();
         this.userDevices_ = var1;
         return this;
      }

      public UserAccount.UserDataResponse clearUserName() {
         List var1 = Collections.emptyList();
         this.userName_ = var1;
         return this;
      }

      public Cache.CacheData getCache() {
         return this.cache_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public DebugInfo getDebugInfo() {
         return this.debugInfo_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasUserData()) {
            UserAccount.UserData var2 = this.getUserData();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         int var6;
         for(Iterator var4 = this.getUserDevicesList().iterator(); var4.hasNext(); var1 += var6) {
            UserAccount.SelectableDevice var5 = (UserAccount.SelectableDevice)var4.next();
            var6 = CodedOutputStreamMicro.computeMessageSize(2, var5);
         }

         int var7 = 0;

         int var9;
         for(Iterator var8 = this.getUserNameList().iterator(); var8.hasNext(); var7 += var9) {
            var9 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var8.next());
         }

         int var10 = var1 + var7;
         int var11 = this.getUserNameList().size() * 1;
         int var12 = var10 + var11;
         if(this.hasCache()) {
            Cache.CacheData var13 = this.getCache();
            int var14 = CodedOutputStreamMicro.computeMessageSize(4, var13);
            var12 += var14;
         }

         if(this.hasDebugInfo()) {
            DebugInfo var15 = this.getDebugInfo();
            int var16 = CodedOutputStreamMicro.computeMessageSize(5, var15);
            var12 += var16;
         }

         this.cachedSize = var12;
         return var12;
      }

      public UserAccount.UserData getUserData() {
         return this.userData_;
      }

      public UserAccount.SelectableDevice getUserDevices(int var1) {
         return (UserAccount.SelectableDevice)this.userDevices_.get(var1);
      }

      public int getUserDevicesCount() {
         return this.userDevices_.size();
      }

      public List<UserAccount.SelectableDevice> getUserDevicesList() {
         return this.userDevices_;
      }

      public String getUserName(int var1) {
         return (String)this.userName_.get(var1);
      }

      public int getUserNameCount() {
         return this.userName_.size();
      }

      public List<String> getUserNameList() {
         return this.userName_;
      }

      public boolean hasCache() {
         return this.hasCache;
      }

      public boolean hasDebugInfo() {
         return this.hasDebugInfo;
      }

      public boolean hasUserData() {
         return this.hasUserData;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(this.hasDebugInfo() && !this.getDebugInfo().isInitialized()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public UserAccount.UserDataResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               UserAccount.UserData var3 = new UserAccount.UserData();
               var1.readMessage(var3);
               this.setUserData(var3);
               break;
            case 18:
               UserAccount.SelectableDevice var5 = new UserAccount.SelectableDevice();
               var1.readMessage(var5);
               this.addUserDevices(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.addUserName(var7);
               break;
            case 34:
               Cache.CacheData var9 = new Cache.CacheData();
               var1.readMessage(var9);
               this.setCache(var9);
               break;
            case 42:
               DebugInfo var11 = new DebugInfo();
               var1.readMessage(var11);
               this.setDebugInfo(var11);
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

      public UserAccount.UserDataResponse setCache(Cache.CacheData var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasCache = (boolean)1;
            this.cache_ = var1;
            return this;
         }
      }

      public UserAccount.UserDataResponse setDebugInfo(DebugInfo var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDebugInfo = (boolean)1;
            this.debugInfo_ = var1;
            return this;
         }
      }

      public UserAccount.UserDataResponse setUserData(UserAccount.UserData var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasUserData = (boolean)1;
            this.userData_ = var1;
            return this;
         }
      }

      public UserAccount.UserDataResponse setUserDevices(int var1, UserAccount.SelectableDevice var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.userDevices_.set(var1, var2);
            return this;
         }
      }

      public UserAccount.UserDataResponse setUserName(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.userName_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasUserData()) {
            UserAccount.UserData var2 = this.getUserData();
            var1.writeMessage(1, var2);
         }

         Iterator var3 = this.getUserDevicesList().iterator();

         while(var3.hasNext()) {
            UserAccount.SelectableDevice var4 = (UserAccount.SelectableDevice)var3.next();
            var1.writeMessage(2, var4);
         }

         Iterator var5 = this.getUserNameList().iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            var1.writeString(3, var6);
         }

         if(this.hasCache()) {
            Cache.CacheData var7 = this.getCache();
            var1.writeMessage(4, var7);
         }

         if(this.hasDebugInfo()) {
            DebugInfo var8 = this.getDebugInfo();
            var1.writeMessage(5, var8);
         }
      }
   }

   public static final class Delivery extends MessageMicro {

      public static final int ANDROID_ID_FIELD_NUMBER = 1;
      public static final int DELIVERY_STATE_FIELD_NUMBER = 6;
      public static final int DEVICE_INITIATION_TIMESTAMP_MSEC_FIELD_NUMBER = 7;
      public static final int DOCUMENT_ID_FIELD_NUMBER = 2;
      public static final int DOWNLOADING = 1;
      public static final int DOWNLOAD_CANCELLED = 12;
      public static final int DOWNLOAD_CANCEL_PENDING = 11;
      public static final int DOWNLOAD_DECLINED = 10;
      public static final int DOWNLOAD_FAILED = 5;
      public static final int DOWNLOAD_PENDING = 9;
      public static final int INSTALLED = 2;
      public static final int INSTALLING = 6;
      public static final int INSTALL_FAILED = 4;
      public static final int TIMESTAMP_MSEC_FIELD_NUMBER = 4;
      public static final int UNINSTALLED = 3;
      public static final int UNINSTALLING = 7;
      public static final int UNINSTALL_FAILED = 8;
      public static final int VERSION_CODE_FIELD_NUMBER = 5;
      private long androidId_ = 0L;
      private int cachedSize = -1;
      private int deliveryState_ = 1;
      private long deviceInitiationTimestampMsec_ = 0L;
      private Common.Docid documentId_ = null;
      private boolean hasAndroidId;
      private boolean hasDeliveryState;
      private boolean hasDeviceInitiationTimestampMsec;
      private boolean hasDocumentId;
      private boolean hasTimestampMsec;
      private boolean hasVersionCode;
      private long timestampMsec_ = 0L;
      private int versionCode_ = 0;


      public Delivery() {}

      public static UserAccount.Delivery parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.Delivery()).mergeFrom(var0);
      }

      public static UserAccount.Delivery parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.Delivery)((UserAccount.Delivery)(new UserAccount.Delivery()).mergeFrom(var0));
      }

      public final UserAccount.Delivery clear() {
         UserAccount.Delivery var1 = this.clearAndroidId();
         UserAccount.Delivery var2 = this.clearDocumentId();
         UserAccount.Delivery var3 = this.clearTimestampMsec();
         UserAccount.Delivery var4 = this.clearVersionCode();
         UserAccount.Delivery var5 = this.clearDeliveryState();
         UserAccount.Delivery var6 = this.clearDeviceInitiationTimestampMsec();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.Delivery clearAndroidId() {
         this.hasAndroidId = (boolean)0;
         this.androidId_ = 0L;
         return this;
      }

      public UserAccount.Delivery clearDeliveryState() {
         this.hasDeliveryState = (boolean)0;
         this.deliveryState_ = 1;
         return this;
      }

      public UserAccount.Delivery clearDeviceInitiationTimestampMsec() {
         this.hasDeviceInitiationTimestampMsec = (boolean)0;
         this.deviceInitiationTimestampMsec_ = 0L;
         return this;
      }

      public UserAccount.Delivery clearDocumentId() {
         this.hasDocumentId = (boolean)0;
         this.documentId_ = null;
         return this;
      }

      public UserAccount.Delivery clearTimestampMsec() {
         this.hasTimestampMsec = (boolean)0;
         this.timestampMsec_ = 0L;
         return this;
      }

      public UserAccount.Delivery clearVersionCode() {
         this.hasVersionCode = (boolean)0;
         this.versionCode_ = 0;
         return this;
      }

      public long getAndroidId() {
         return this.androidId_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getDeliveryState() {
         return this.deliveryState_;
      }

      public long getDeviceInitiationTimestampMsec() {
         return this.deviceInitiationTimestampMsec_;
      }

      public Common.Docid getDocumentId() {
         return this.documentId_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasAndroidId()) {
            long var2 = this.getAndroidId();
            int var4 = CodedOutputStreamMicro.computeFixed64Size(1, var2);
            var1 = 0 + var4;
         }

         if(this.hasDocumentId()) {
            Common.Docid var5 = this.getDocumentId();
            int var6 = CodedOutputStreamMicro.computeMessageSize(2, var5);
            var1 += var6;
         }

         if(this.hasTimestampMsec()) {
            long var7 = this.getTimestampMsec();
            int var9 = CodedOutputStreamMicro.computeInt64Size(4, var7);
            var1 += var9;
         }

         if(this.hasVersionCode()) {
            int var10 = this.getVersionCode();
            int var11 = CodedOutputStreamMicro.computeInt32Size(5, var10);
            var1 += var11;
         }

         if(this.hasDeliveryState()) {
            int var12 = this.getDeliveryState();
            int var13 = CodedOutputStreamMicro.computeInt32Size(6, var12);
            var1 += var13;
         }

         if(this.hasDeviceInitiationTimestampMsec()) {
            long var14 = this.getDeviceInitiationTimestampMsec();
            int var16 = CodedOutputStreamMicro.computeInt64Size(7, var14);
            var1 += var16;
         }

         this.cachedSize = var1;
         return var1;
      }

      public long getTimestampMsec() {
         return this.timestampMsec_;
      }

      public int getVersionCode() {
         return this.versionCode_;
      }

      public boolean hasAndroidId() {
         return this.hasAndroidId;
      }

      public boolean hasDeliveryState() {
         return this.hasDeliveryState;
      }

      public boolean hasDeviceInitiationTimestampMsec() {
         return this.hasDeviceInitiationTimestampMsec;
      }

      public boolean hasDocumentId() {
         return this.hasDocumentId;
      }

      public boolean hasTimestampMsec() {
         return this.hasTimestampMsec;
      }

      public boolean hasVersionCode() {
         return this.hasVersionCode;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasVersionCode && this.hasDeliveryState && (!this.hasDocumentId() || this.getDocumentId().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public UserAccount.Delivery mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 9:
               long var3 = var1.readFixed64();
               this.setAndroidId(var3);
               break;
            case 18:
               Common.Docid var6 = new Common.Docid();
               var1.readMessage(var6);
               this.setDocumentId(var6);
               break;
            case 32:
               long var8 = var1.readInt64();
               this.setTimestampMsec(var8);
               break;
            case 40:
               int var11 = var1.readInt32();
               this.setVersionCode(var11);
               break;
            case 48:
               int var13 = var1.readInt32();
               this.setDeliveryState(var13);
               break;
            case 56:
               long var15 = var1.readInt64();
               this.setDeviceInitiationTimestampMsec(var15);
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

      public UserAccount.Delivery setAndroidId(long var1) {
         this.hasAndroidId = (boolean)1;
         this.androidId_ = var1;
         return this;
      }

      public UserAccount.Delivery setDeliveryState(int var1) {
         this.hasDeliveryState = (boolean)1;
         this.deliveryState_ = var1;
         return this;
      }

      public UserAccount.Delivery setDeviceInitiationTimestampMsec(long var1) {
         this.hasDeviceInitiationTimestampMsec = (boolean)1;
         this.deviceInitiationTimestampMsec_ = var1;
         return this;
      }

      public UserAccount.Delivery setDocumentId(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDocumentId = (boolean)1;
            this.documentId_ = var1;
            return this;
         }
      }

      public UserAccount.Delivery setTimestampMsec(long var1) {
         this.hasTimestampMsec = (boolean)1;
         this.timestampMsec_ = var1;
         return this;
      }

      public UserAccount.Delivery setVersionCode(int var1) {
         this.hasVersionCode = (boolean)1;
         this.versionCode_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasAndroidId()) {
            long var2 = this.getAndroidId();
            var1.writeFixed64(1, var2);
         }

         if(this.hasDocumentId()) {
            Common.Docid var4 = this.getDocumentId();
            var1.writeMessage(2, var4);
         }

         if(this.hasTimestampMsec()) {
            long var5 = this.getTimestampMsec();
            var1.writeInt64(4, var5);
         }

         if(this.hasVersionCode()) {
            int var7 = this.getVersionCode();
            var1.writeInt32(5, var7);
         }

         if(this.hasDeliveryState()) {
            int var8 = this.getDeliveryState();
            var1.writeInt32(6, var8);
         }

         if(this.hasDeviceInitiationTimestampMsec()) {
            long var9 = this.getDeviceInitiationTimestampMsec();
            var1.writeInt64(7, var9);
         }
      }
   }

   public static final class LanguagePrefs extends MessageMicro {

      public static final int LAST_LANGUAGE_CODE_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private boolean hasLastLanguageCode;
      private String lastLanguageCode_ = "";


      public LanguagePrefs() {}

      public static UserAccount.LanguagePrefs parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.LanguagePrefs()).mergeFrom(var0);
      }

      public static UserAccount.LanguagePrefs parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.LanguagePrefs)((UserAccount.LanguagePrefs)(new UserAccount.LanguagePrefs()).mergeFrom(var0));
      }

      public final UserAccount.LanguagePrefs clear() {
         UserAccount.LanguagePrefs var1 = this.clearLastLanguageCode();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.LanguagePrefs clearLastLanguageCode() {
         this.hasLastLanguageCode = (boolean)0;
         this.lastLanguageCode_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getLastLanguageCode() {
         return this.lastLanguageCode_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasLastLanguageCode()) {
            String var2 = this.getLastLanguageCode();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasLastLanguageCode() {
         return this.hasLastLanguageCode;
      }

      public final boolean isInitialized() {
         return true;
      }

      public UserAccount.LanguagePrefs mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setLastLanguageCode(var3);
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

      public UserAccount.LanguagePrefs setLastLanguageCode(String var1) {
         this.hasLastLanguageCode = (boolean)1;
         this.lastLanguageCode_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasLastLanguageCode()) {
            String var2 = this.getLastLanguageCode();
            var1.writeString(1, var2);
         }
      }
   }

   public static final class UserData extends MessageMicro {

      public static final int DEVICE_PREFS_FIELD_NUMBER = 5;
      public static final int LANGUAGE_PREFS_FIELD_NUMBER = 3;
      public static final int SEARCH_PREFS_FIELD_NUMBER = 4;
      public static final int SELECTED_FIELD_NUMBER = 2;
      public static final int TERMS_OF_SERVICE_FIELD_NUMBER = 1;
      public static final int VISIBILITY_FIELD_NUMBER = 6;
      private int cachedSize = -1;
      private UserAccount.DevicePrefs devicePrefs_ = null;
      private boolean hasDevicePrefs;
      private boolean hasLanguagePrefs;
      private boolean hasSearchPrefs;
      private boolean hasSelected;
      private boolean hasTermsOfService;
      private boolean hasVisibility;
      private UserAccount.LanguagePrefs languagePrefs_ = null;
      private UserAccount.SearchPrefs searchPrefs_ = null;
      private UserAccount.SelectableDevice selected_ = null;
      private UserAccount.Tos termsOfService_ = null;
      private UserAccount.DeviceVisibility visibility_ = null;


      public UserData() {}

      public static UserAccount.UserData parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.UserData()).mergeFrom(var0);
      }

      public static UserAccount.UserData parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.UserData)((UserAccount.UserData)(new UserAccount.UserData()).mergeFrom(var0));
      }

      public final UserAccount.UserData clear() {
         UserAccount.UserData var1 = this.clearTermsOfService();
         UserAccount.UserData var2 = this.clearSelected();
         UserAccount.UserData var3 = this.clearLanguagePrefs();
         UserAccount.UserData var4 = this.clearSearchPrefs();
         UserAccount.UserData var5 = this.clearDevicePrefs();
         UserAccount.UserData var6 = this.clearVisibility();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.UserData clearDevicePrefs() {
         this.hasDevicePrefs = (boolean)0;
         this.devicePrefs_ = null;
         return this;
      }

      public UserAccount.UserData clearLanguagePrefs() {
         this.hasLanguagePrefs = (boolean)0;
         this.languagePrefs_ = null;
         return this;
      }

      public UserAccount.UserData clearSearchPrefs() {
         this.hasSearchPrefs = (boolean)0;
         this.searchPrefs_ = null;
         return this;
      }

      public UserAccount.UserData clearSelected() {
         this.hasSelected = (boolean)0;
         this.selected_ = null;
         return this;
      }

      public UserAccount.UserData clearTermsOfService() {
         this.hasTermsOfService = (boolean)0;
         this.termsOfService_ = null;
         return this;
      }

      public UserAccount.UserData clearVisibility() {
         this.hasVisibility = (boolean)0;
         this.visibility_ = null;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public UserAccount.DevicePrefs getDevicePrefs() {
         return this.devicePrefs_;
      }

      public UserAccount.LanguagePrefs getLanguagePrefs() {
         return this.languagePrefs_;
      }

      public UserAccount.SearchPrefs getSearchPrefs() {
         return this.searchPrefs_;
      }

      public UserAccount.SelectableDevice getSelected() {
         return this.selected_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasTermsOfService()) {
            UserAccount.Tos var2 = this.getTermsOfService();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasSelected()) {
            UserAccount.SelectableDevice var4 = this.getSelected();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         if(this.hasLanguagePrefs()) {
            UserAccount.LanguagePrefs var6 = this.getLanguagePrefs();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         if(this.hasSearchPrefs()) {
            UserAccount.SearchPrefs var8 = this.getSearchPrefs();
            int var9 = CodedOutputStreamMicro.computeMessageSize(4, var8);
            var1 += var9;
         }

         if(this.hasDevicePrefs()) {
            UserAccount.DevicePrefs var10 = this.getDevicePrefs();
            int var11 = CodedOutputStreamMicro.computeMessageSize(5, var10);
            var1 += var11;
         }

         if(this.hasVisibility()) {
            UserAccount.DeviceVisibility var12 = this.getVisibility();
            int var13 = CodedOutputStreamMicro.computeMessageSize(6, var12);
            var1 += var13;
         }

         this.cachedSize = var1;
         return var1;
      }

      public UserAccount.Tos getTermsOfService() {
         return this.termsOfService_;
      }

      public UserAccount.DeviceVisibility getVisibility() {
         return this.visibility_;
      }

      public boolean hasDevicePrefs() {
         return this.hasDevicePrefs;
      }

      public boolean hasLanguagePrefs() {
         return this.hasLanguagePrefs;
      }

      public boolean hasSearchPrefs() {
         return this.hasSearchPrefs;
      }

      public boolean hasSelected() {
         return this.hasSelected;
      }

      public boolean hasTermsOfService() {
         return this.hasTermsOfService;
      }

      public boolean hasVisibility() {
         return this.hasVisibility;
      }

      public final boolean isInitialized() {
         return true;
      }

      public UserAccount.UserData mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               UserAccount.Tos var3 = new UserAccount.Tos();
               var1.readMessage(var3);
               this.setTermsOfService(var3);
               break;
            case 18:
               UserAccount.SelectableDevice var5 = new UserAccount.SelectableDevice();
               var1.readMessage(var5);
               this.setSelected(var5);
               break;
            case 26:
               UserAccount.LanguagePrefs var7 = new UserAccount.LanguagePrefs();
               var1.readMessage(var7);
               this.setLanguagePrefs(var7);
               break;
            case 34:
               UserAccount.SearchPrefs var9 = new UserAccount.SearchPrefs();
               var1.readMessage(var9);
               this.setSearchPrefs(var9);
               break;
            case 42:
               UserAccount.DevicePrefs var11 = new UserAccount.DevicePrefs();
               var1.readMessage(var11);
               this.setDevicePrefs(var11);
               break;
            case 50:
               UserAccount.DeviceVisibility var13 = new UserAccount.DeviceVisibility();
               var1.readMessage(var13);
               this.setVisibility(var13);
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

      public UserAccount.UserData setDevicePrefs(UserAccount.DevicePrefs var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDevicePrefs = (boolean)1;
            this.devicePrefs_ = var1;
            return this;
         }
      }

      public UserAccount.UserData setLanguagePrefs(UserAccount.LanguagePrefs var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasLanguagePrefs = (boolean)1;
            this.languagePrefs_ = var1;
            return this;
         }
      }

      public UserAccount.UserData setSearchPrefs(UserAccount.SearchPrefs var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasSearchPrefs = (boolean)1;
            this.searchPrefs_ = var1;
            return this;
         }
      }

      public UserAccount.UserData setSelected(UserAccount.SelectableDevice var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasSelected = (boolean)1;
            this.selected_ = var1;
            return this;
         }
      }

      public UserAccount.UserData setTermsOfService(UserAccount.Tos var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasTermsOfService = (boolean)1;
            this.termsOfService_ = var1;
            return this;
         }
      }

      public UserAccount.UserData setVisibility(UserAccount.DeviceVisibility var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasVisibility = (boolean)1;
            this.visibility_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasTermsOfService()) {
            UserAccount.Tos var2 = this.getTermsOfService();
            var1.writeMessage(1, var2);
         }

         if(this.hasSelected()) {
            UserAccount.SelectableDevice var3 = this.getSelected();
            var1.writeMessage(2, var3);
         }

         if(this.hasLanguagePrefs()) {
            UserAccount.LanguagePrefs var4 = this.getLanguagePrefs();
            var1.writeMessage(3, var4);
         }

         if(this.hasSearchPrefs()) {
            UserAccount.SearchPrefs var5 = this.getSearchPrefs();
            var1.writeMessage(4, var5);
         }

         if(this.hasDevicePrefs()) {
            UserAccount.DevicePrefs var6 = this.getDevicePrefs();
            var1.writeMessage(5, var6);
         }

         if(this.hasVisibility()) {
            UserAccount.DeviceVisibility var7 = this.getVisibility();
            var1.writeMessage(6, var7);
         }
      }
   }

   public static final class SelectableDevice extends MessageMicro {

      public static final int ANDROID_GROUP_FIELD_NUMBER = 15;
      public static final int CAPABILITY_FIELD_NUMBER = 16;
      public static final int CARRIER_FIELD_NUMBER = 9;
      public static final int CONFIG_ANDROID_ID_FIELD_NUMBER = 4;
      public static final int DISPLAY_NAME_FIELD_NUMBER = 1;
      public static final int EMAIL_ACCOUNT_FIELD_NUMBER = 14;
      public static final int GAIA_ID_FIELD_NUMBER = 12;
      public static final int HARDWARE_ID_FIELD_NUMBER = 13;
      public static final int HIDDEN_BY_USER_FIELD_NUMBER = 11;
      public static final int LAST_USED_TIME_MSEC_FIELD_NUMBER = 10;
      public static final int MANUFACTURER_FIELD_NUMBER = 8;
      public static final int MODEL_NAME_FIELD_NUMBER = 7;
      public static final int REGISTRATION_TIME_MSEC_FIELD_NUMBER = 6;
      public static final int STABLE_DEVICE_ID_FIELD_NUMBER = 2;
      public static final int SUPPORT_APP_INSTALL_FIELD_NUMBER = 5;
      public static final int SUPPORT_DEVICE_INSTALL = 2;
      public static final int SUPPORT_GTV_INSTALL = 3;
      public static final int SUPPORT_WEB_INSTALL = 1;
      private List<String> androidGroup_;
      private int cachedSize;
      private List<Integer> capability_;
      private String carrier_ = "";
      private long configAndroidId_ = 0L;
      private String displayName_ = "";
      private List<String> emailAccount_;
      private List<Long> gaiaId_;
      private List<Long> hardwareId_;
      private boolean hasCarrier;
      private boolean hasConfigAndroidId;
      private boolean hasDisplayName;
      private boolean hasHiddenByUser;
      private boolean hasLastUsedTimeMsec;
      private boolean hasManufacturer;
      private boolean hasModelName;
      private boolean hasRegistrationTimeMsec;
      private boolean hasStableDeviceId;
      private boolean hasSupportAppInstall;
      private boolean hiddenByUser_ = 0;
      private long lastUsedTimeMsec_ = 0L;
      private String manufacturer_ = "";
      private String modelName_ = "";
      private long registrationTimeMsec_ = 0L;
      private long stableDeviceId_ = 0L;
      private boolean supportAppInstall_ = 0;


      public SelectableDevice() {
         List var1 = Collections.emptyList();
         this.gaiaId_ = var1;
         List var2 = Collections.emptyList();
         this.hardwareId_ = var2;
         List var3 = Collections.emptyList();
         this.emailAccount_ = var3;
         List var4 = Collections.emptyList();
         this.androidGroup_ = var4;
         List var5 = Collections.emptyList();
         this.capability_ = var5;
         this.cachedSize = -1;
      }

      public static UserAccount.SelectableDevice parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.SelectableDevice()).mergeFrom(var0);
      }

      public static UserAccount.SelectableDevice parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.SelectableDevice)((UserAccount.SelectableDevice)(new UserAccount.SelectableDevice()).mergeFrom(var0));
      }

      public UserAccount.SelectableDevice addAndroidGroup(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.androidGroup_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.androidGroup_ = var2;
            }

            this.androidGroup_.add(var1);
            return this;
         }
      }

      public UserAccount.SelectableDevice addCapability(int var1) {
         if(this.capability_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.capability_ = var2;
         }

         List var3 = this.capability_;
         Integer var4 = Integer.valueOf(var1);
         var3.add(var4);
         return this;
      }

      public UserAccount.SelectableDevice addEmailAccount(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.emailAccount_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.emailAccount_ = var2;
            }

            this.emailAccount_.add(var1);
            return this;
         }
      }

      public UserAccount.SelectableDevice addGaiaId(long var1) {
         if(this.gaiaId_.isEmpty()) {
            ArrayList var3 = new ArrayList();
            this.gaiaId_ = var3;
         }

         List var4 = this.gaiaId_;
         Long var5 = Long.valueOf(var1);
         var4.add(var5);
         return this;
      }

      public UserAccount.SelectableDevice addHardwareId(long var1) {
         if(this.hardwareId_.isEmpty()) {
            ArrayList var3 = new ArrayList();
            this.hardwareId_ = var3;
         }

         List var4 = this.hardwareId_;
         Long var5 = Long.valueOf(var1);
         var4.add(var5);
         return this;
      }

      public final UserAccount.SelectableDevice clear() {
         UserAccount.SelectableDevice var1 = this.clearDisplayName();
         UserAccount.SelectableDevice var2 = this.clearStableDeviceId();
         UserAccount.SelectableDevice var3 = this.clearConfigAndroidId();
         UserAccount.SelectableDevice var4 = this.clearSupportAppInstall();
         UserAccount.SelectableDevice var5 = this.clearRegistrationTimeMsec();
         UserAccount.SelectableDevice var6 = this.clearLastUsedTimeMsec();
         UserAccount.SelectableDevice var7 = this.clearModelName();
         UserAccount.SelectableDevice var8 = this.clearManufacturer();
         UserAccount.SelectableDevice var9 = this.clearCarrier();
         UserAccount.SelectableDevice var10 = this.clearHiddenByUser();
         UserAccount.SelectableDevice var11 = this.clearGaiaId();
         UserAccount.SelectableDevice var12 = this.clearHardwareId();
         UserAccount.SelectableDevice var13 = this.clearEmailAccount();
         UserAccount.SelectableDevice var14 = this.clearAndroidGroup();
         UserAccount.SelectableDevice var15 = this.clearCapability();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.SelectableDevice clearAndroidGroup() {
         List var1 = Collections.emptyList();
         this.androidGroup_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice clearCapability() {
         List var1 = Collections.emptyList();
         this.capability_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice clearCarrier() {
         this.hasCarrier = (boolean)0;
         this.carrier_ = "";
         return this;
      }

      public UserAccount.SelectableDevice clearConfigAndroidId() {
         this.hasConfigAndroidId = (boolean)0;
         this.configAndroidId_ = 0L;
         return this;
      }

      public UserAccount.SelectableDevice clearDisplayName() {
         this.hasDisplayName = (boolean)0;
         this.displayName_ = "";
         return this;
      }

      public UserAccount.SelectableDevice clearEmailAccount() {
         List var1 = Collections.emptyList();
         this.emailAccount_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice clearGaiaId() {
         List var1 = Collections.emptyList();
         this.gaiaId_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice clearHardwareId() {
         List var1 = Collections.emptyList();
         this.hardwareId_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice clearHiddenByUser() {
         this.hasHiddenByUser = (boolean)0;
         this.hiddenByUser_ = (boolean)0;
         return this;
      }

      public UserAccount.SelectableDevice clearLastUsedTimeMsec() {
         this.hasLastUsedTimeMsec = (boolean)0;
         this.lastUsedTimeMsec_ = 0L;
         return this;
      }

      public UserAccount.SelectableDevice clearManufacturer() {
         this.hasManufacturer = (boolean)0;
         this.manufacturer_ = "";
         return this;
      }

      public UserAccount.SelectableDevice clearModelName() {
         this.hasModelName = (boolean)0;
         this.modelName_ = "";
         return this;
      }

      public UserAccount.SelectableDevice clearRegistrationTimeMsec() {
         this.hasRegistrationTimeMsec = (boolean)0;
         this.registrationTimeMsec_ = 0L;
         return this;
      }

      public UserAccount.SelectableDevice clearStableDeviceId() {
         this.hasStableDeviceId = (boolean)0;
         this.stableDeviceId_ = 0L;
         return this;
      }

      public UserAccount.SelectableDevice clearSupportAppInstall() {
         this.hasSupportAppInstall = (boolean)0;
         this.supportAppInstall_ = (boolean)0;
         return this;
      }

      public String getAndroidGroup(int var1) {
         return (String)this.androidGroup_.get(var1);
      }

      public int getAndroidGroupCount() {
         return this.androidGroup_.size();
      }

      public List<String> getAndroidGroupList() {
         return this.androidGroup_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getCapability(int var1) {
         return ((Integer)this.capability_.get(var1)).intValue();
      }

      public int getCapabilityCount() {
         return this.capability_.size();
      }

      public List<Integer> getCapabilityList() {
         return this.capability_;
      }

      public String getCarrier() {
         return this.carrier_;
      }

      public long getConfigAndroidId() {
         return this.configAndroidId_;
      }

      public String getDisplayName() {
         return this.displayName_;
      }

      public String getEmailAccount(int var1) {
         return (String)this.emailAccount_.get(var1);
      }

      public int getEmailAccountCount() {
         return this.emailAccount_.size();
      }

      public List<String> getEmailAccountList() {
         return this.emailAccount_;
      }

      public long getGaiaId(int var1) {
         return ((Long)this.gaiaId_.get(var1)).longValue();
      }

      public int getGaiaIdCount() {
         return this.gaiaId_.size();
      }

      public List<Long> getGaiaIdList() {
         return this.gaiaId_;
      }

      public long getHardwareId(int var1) {
         return ((Long)this.hardwareId_.get(var1)).longValue();
      }

      public int getHardwareIdCount() {
         return this.hardwareId_.size();
      }

      public List<Long> getHardwareIdList() {
         return this.hardwareId_;
      }

      public boolean getHiddenByUser() {
         return this.hiddenByUser_;
      }

      public long getLastUsedTimeMsec() {
         return this.lastUsedTimeMsec_;
      }

      public String getManufacturer() {
         return this.manufacturer_;
      }

      public String getModelName() {
         return this.modelName_;
      }

      public long getRegistrationTimeMsec() {
         return this.registrationTimeMsec_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasDisplayName()) {
            String var2 = this.getDisplayName();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasStableDeviceId()) {
            long var4 = this.getStableDeviceId();
            int var6 = CodedOutputStreamMicro.computeFixed64Size(2, var4);
            var1 += var6;
         }

         if(this.hasConfigAndroidId()) {
            long var7 = this.getConfigAndroidId();
            int var9 = CodedOutputStreamMicro.computeFixed64Size(4, var7);
            var1 += var9;
         }

         if(this.hasSupportAppInstall()) {
            boolean var10 = this.getSupportAppInstall();
            int var11 = CodedOutputStreamMicro.computeBoolSize(5, var10);
            var1 += var11;
         }

         if(this.hasRegistrationTimeMsec()) {
            long var12 = this.getRegistrationTimeMsec();
            int var14 = CodedOutputStreamMicro.computeInt64Size(6, var12);
            var1 += var14;
         }

         if(this.hasModelName()) {
            String var15 = this.getModelName();
            int var16 = CodedOutputStreamMicro.computeStringSize(7, var15);
            var1 += var16;
         }

         if(this.hasManufacturer()) {
            String var17 = this.getManufacturer();
            int var18 = CodedOutputStreamMicro.computeStringSize(8, var17);
            var1 += var18;
         }

         if(this.hasCarrier()) {
            String var19 = this.getCarrier();
            int var20 = CodedOutputStreamMicro.computeStringSize(9, var19);
            var1 += var20;
         }

         if(this.hasLastUsedTimeMsec()) {
            long var21 = this.getLastUsedTimeMsec();
            int var23 = CodedOutputStreamMicro.computeInt64Size(10, var21);
            var1 += var23;
         }

         if(this.hasHiddenByUser()) {
            boolean var24 = this.getHiddenByUser();
            int var25 = CodedOutputStreamMicro.computeBoolSize(11, var24);
            var1 += var25;
         }

         int var26 = this.getGaiaIdList().size() * 8;
         int var27 = var1 + var26;
         int var28 = this.getGaiaIdList().size() * 1;
         int var29 = var27 + var28;
         int var30 = this.getHardwareIdList().size() * 8;
         int var31 = var29 + var30;
         int var32 = this.getHardwareIdList().size() * 1;
         int var33 = var31 + var32;
         int var34 = 0;

         int var36;
         for(Iterator var35 = this.getEmailAccountList().iterator(); var35.hasNext(); var34 += var36) {
            var36 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var35.next());
         }

         int var37 = var33 + var34;
         int var38 = this.getEmailAccountList().size() * 1;
         int var39 = var37 + var38;
         int var40 = 0;

         int var42;
         for(Iterator var41 = this.getAndroidGroupList().iterator(); var41.hasNext(); var40 += var42) {
            var42 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var41.next());
         }

         int var43 = var39 + var40;
         int var44 = this.getAndroidGroupList().size() * 1;
         int var45 = var43 + var44;
         int var46 = 0;

         int var48;
         for(Iterator var47 = this.getCapabilityList().iterator(); var47.hasNext(); var46 += var48) {
            var48 = CodedOutputStreamMicro.computeInt32SizeNoTag(((Integer)var47.next()).intValue());
         }

         int var49 = var45 + var46;
         int var50 = this.getCapabilityList().size() * 2;
         int var51 = var49 + var50;
         this.cachedSize = var51;
         return var51;
      }

      public long getStableDeviceId() {
         return this.stableDeviceId_;
      }

      public boolean getSupportAppInstall() {
         return this.supportAppInstall_;
      }

      public boolean hasCarrier() {
         return this.hasCarrier;
      }

      public boolean hasConfigAndroidId() {
         return this.hasConfigAndroidId;
      }

      public boolean hasDisplayName() {
         return this.hasDisplayName;
      }

      public boolean hasHiddenByUser() {
         return this.hasHiddenByUser;
      }

      public boolean hasLastUsedTimeMsec() {
         return this.hasLastUsedTimeMsec;
      }

      public boolean hasManufacturer() {
         return this.hasManufacturer;
      }

      public boolean hasModelName() {
         return this.hasModelName;
      }

      public boolean hasRegistrationTimeMsec() {
         return this.hasRegistrationTimeMsec;
      }

      public boolean hasStableDeviceId() {
         return this.hasStableDeviceId;
      }

      public boolean hasSupportAppInstall() {
         return this.hasSupportAppInstall;
      }

      public final boolean isInitialized() {
         return true;
      }

      public UserAccount.SelectableDevice mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setDisplayName(var3);
               break;
            case 17:
               long var5 = var1.readFixed64();
               this.setStableDeviceId(var5);
               break;
            case 33:
               long var8 = var1.readFixed64();
               this.setConfigAndroidId(var8);
               break;
            case 40:
               boolean var11 = var1.readBool();
               this.setSupportAppInstall(var11);
               break;
            case 48:
               long var13 = var1.readInt64();
               this.setRegistrationTimeMsec(var13);
               break;
            case 58:
               String var16 = var1.readString();
               this.setModelName(var16);
               break;
            case 66:
               String var18 = var1.readString();
               this.setManufacturer(var18);
               break;
            case 74:
               String var20 = var1.readString();
               this.setCarrier(var20);
               break;
            case 80:
               long var22 = var1.readInt64();
               this.setLastUsedTimeMsec(var22);
               break;
            case 88:
               boolean var25 = var1.readBool();
               this.setHiddenByUser(var25);
               break;
            case 97:
               long var27 = var1.readFixed64();
               this.addGaiaId(var27);
               break;
            case 105:
               long var30 = var1.readFixed64();
               this.addHardwareId(var30);
               break;
            case 114:
               String var33 = var1.readString();
               this.addEmailAccount(var33);
               break;
            case 122:
               String var35 = var1.readString();
               this.addAndroidGroup(var35);
               break;
            case 128:
               int var37 = var1.readInt32();
               this.addCapability(var37);
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

      public UserAccount.SelectableDevice setAndroidGroup(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.androidGroup_.set(var1, var2);
            return this;
         }
      }

      public UserAccount.SelectableDevice setCapability(int var1, int var2) {
         List var3 = this.capability_;
         Integer var4 = Integer.valueOf(var2);
         var3.set(var1, var4);
         return this;
      }

      public UserAccount.SelectableDevice setCarrier(String var1) {
         this.hasCarrier = (boolean)1;
         this.carrier_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice setConfigAndroidId(long var1) {
         this.hasConfigAndroidId = (boolean)1;
         this.configAndroidId_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice setDisplayName(String var1) {
         this.hasDisplayName = (boolean)1;
         this.displayName_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice setEmailAccount(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.emailAccount_.set(var1, var2);
            return this;
         }
      }

      public UserAccount.SelectableDevice setGaiaId(int var1, long var2) {
         List var4 = this.gaiaId_;
         Long var5 = Long.valueOf(var2);
         var4.set(var1, var5);
         return this;
      }

      public UserAccount.SelectableDevice setHardwareId(int var1, long var2) {
         List var4 = this.hardwareId_;
         Long var5 = Long.valueOf(var2);
         var4.set(var1, var5);
         return this;
      }

      public UserAccount.SelectableDevice setHiddenByUser(boolean var1) {
         this.hasHiddenByUser = (boolean)1;
         this.hiddenByUser_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice setLastUsedTimeMsec(long var1) {
         this.hasLastUsedTimeMsec = (boolean)1;
         this.lastUsedTimeMsec_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice setManufacturer(String var1) {
         this.hasManufacturer = (boolean)1;
         this.manufacturer_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice setModelName(String var1) {
         this.hasModelName = (boolean)1;
         this.modelName_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice setRegistrationTimeMsec(long var1) {
         this.hasRegistrationTimeMsec = (boolean)1;
         this.registrationTimeMsec_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice setStableDeviceId(long var1) {
         this.hasStableDeviceId = (boolean)1;
         this.stableDeviceId_ = var1;
         return this;
      }

      public UserAccount.SelectableDevice setSupportAppInstall(boolean var1) {
         this.hasSupportAppInstall = (boolean)1;
         this.supportAppInstall_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasDisplayName()) {
            String var2 = this.getDisplayName();
            var1.writeString(1, var2);
         }

         if(this.hasStableDeviceId()) {
            long var3 = this.getStableDeviceId();
            var1.writeFixed64(2, var3);
         }

         if(this.hasConfigAndroidId()) {
            long var5 = this.getConfigAndroidId();
            var1.writeFixed64(4, var5);
         }

         if(this.hasSupportAppInstall()) {
            boolean var7 = this.getSupportAppInstall();
            var1.writeBool(5, var7);
         }

         if(this.hasRegistrationTimeMsec()) {
            long var8 = this.getRegistrationTimeMsec();
            var1.writeInt64(6, var8);
         }

         if(this.hasModelName()) {
            String var10 = this.getModelName();
            var1.writeString(7, var10);
         }

         if(this.hasManufacturer()) {
            String var11 = this.getManufacturer();
            var1.writeString(8, var11);
         }

         if(this.hasCarrier()) {
            String var12 = this.getCarrier();
            var1.writeString(9, var12);
         }

         if(this.hasLastUsedTimeMsec()) {
            long var13 = this.getLastUsedTimeMsec();
            var1.writeInt64(10, var13);
         }

         if(this.hasHiddenByUser()) {
            boolean var15 = this.getHiddenByUser();
            var1.writeBool(11, var15);
         }

         Iterator var16 = this.getGaiaIdList().iterator();

         while(var16.hasNext()) {
            long var17 = ((Long)var16.next()).longValue();
            var1.writeFixed64(12, var17);
         }

         Iterator var19 = this.getHardwareIdList().iterator();

         while(var19.hasNext()) {
            long var20 = ((Long)var19.next()).longValue();
            var1.writeFixed64(13, var20);
         }

         Iterator var22 = this.getEmailAccountList().iterator();

         while(var22.hasNext()) {
            String var23 = (String)var22.next();
            var1.writeString(14, var23);
         }

         Iterator var24 = this.getAndroidGroupList().iterator();

         while(var24.hasNext()) {
            String var25 = (String)var24.next();
            var1.writeString(15, var25);
         }

         Iterator var26 = this.getCapabilityList().iterator();

         while(var26.hasNext()) {
            int var27 = ((Integer)var26.next()).intValue();
            var1.writeInt32(16, var27);
         }

      }
   }

   public static final class SearchPrefs extends MessageMicro {

      public static final int LAST_DEVICE_CONFIG_ANDROID_ID_FIELD_NUMBER = 4;
      public static final int LAST_ORDER_BY_FIELD_NUMBER = 1;
      public static final int LAST_PRICE_RESTRICT_FIELD_NUMBER = 2;
      public static final int LAST_SAFESEARCH_LEVEL_FIELD_NUMBER = 3;
      private int cachedSize;
      private boolean hasLastOrderBy;
      private boolean hasLastPriceRestrict;
      private boolean hasLastSafesearchLevel;
      private List<Long> lastDeviceConfigAndroidId_;
      private int lastOrderBy_ = 0;
      private int lastPriceRestrict_ = 0;
      private int lastSafesearchLevel_ = 0;


      public SearchPrefs() {
         List var1 = Collections.emptyList();
         this.lastDeviceConfigAndroidId_ = var1;
         this.cachedSize = -1;
      }

      public static UserAccount.SearchPrefs parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.SearchPrefs()).mergeFrom(var0);
      }

      public static UserAccount.SearchPrefs parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.SearchPrefs)((UserAccount.SearchPrefs)(new UserAccount.SearchPrefs()).mergeFrom(var0));
      }

      public UserAccount.SearchPrefs addLastDeviceConfigAndroidId(long var1) {
         if(this.lastDeviceConfigAndroidId_.isEmpty()) {
            ArrayList var3 = new ArrayList();
            this.lastDeviceConfigAndroidId_ = var3;
         }

         List var4 = this.lastDeviceConfigAndroidId_;
         Long var5 = Long.valueOf(var1);
         var4.add(var5);
         return this;
      }

      public final UserAccount.SearchPrefs clear() {
         UserAccount.SearchPrefs var1 = this.clearLastOrderBy();
         UserAccount.SearchPrefs var2 = this.clearLastPriceRestrict();
         UserAccount.SearchPrefs var3 = this.clearLastSafesearchLevel();
         UserAccount.SearchPrefs var4 = this.clearLastDeviceConfigAndroidId();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.SearchPrefs clearLastDeviceConfigAndroidId() {
         List var1 = Collections.emptyList();
         this.lastDeviceConfigAndroidId_ = var1;
         return this;
      }

      public UserAccount.SearchPrefs clearLastOrderBy() {
         this.hasLastOrderBy = (boolean)0;
         this.lastOrderBy_ = 0;
         return this;
      }

      public UserAccount.SearchPrefs clearLastPriceRestrict() {
         this.hasLastPriceRestrict = (boolean)0;
         this.lastPriceRestrict_ = 0;
         return this;
      }

      public UserAccount.SearchPrefs clearLastSafesearchLevel() {
         this.hasLastSafesearchLevel = (boolean)0;
         this.lastSafesearchLevel_ = 0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public long getLastDeviceConfigAndroidId(int var1) {
         return ((Long)this.lastDeviceConfigAndroidId_.get(var1)).longValue();
      }

      public int getLastDeviceConfigAndroidIdCount() {
         return this.lastDeviceConfigAndroidId_.size();
      }

      public List<Long> getLastDeviceConfigAndroidIdList() {
         return this.lastDeviceConfigAndroidId_;
      }

      public int getLastOrderBy() {
         return this.lastOrderBy_;
      }

      public int getLastPriceRestrict() {
         return this.lastPriceRestrict_;
      }

      public int getLastSafesearchLevel() {
         return this.lastSafesearchLevel_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasLastOrderBy()) {
            int var2 = this.getLastOrderBy();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasLastPriceRestrict()) {
            int var4 = this.getLastPriceRestrict();
            int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
            var1 += var5;
         }

         if(this.hasLastSafesearchLevel()) {
            int var6 = this.getLastSafesearchLevel();
            int var7 = CodedOutputStreamMicro.computeInt32Size(3, var6);
            var1 += var7;
         }

         int var8 = this.getLastDeviceConfigAndroidIdList().size() * 8;
         int var9 = var1 + var8;
         int var10 = this.getLastDeviceConfigAndroidIdList().size() * 1;
         int var11 = var9 + var10;
         this.cachedSize = var11;
         return var11;
      }

      public boolean hasLastOrderBy() {
         return this.hasLastOrderBy;
      }

      public boolean hasLastPriceRestrict() {
         return this.hasLastPriceRestrict;
      }

      public boolean hasLastSafesearchLevel() {
         return this.hasLastSafesearchLevel;
      }

      public final boolean isInitialized() {
         return true;
      }

      public UserAccount.SearchPrefs mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setLastOrderBy(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setLastPriceRestrict(var5);
               break;
            case 24:
               int var7 = var1.readInt32();
               this.setLastSafesearchLevel(var7);
               break;
            case 33:
               long var9 = var1.readFixed64();
               this.addLastDeviceConfigAndroidId(var9);
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

      public UserAccount.SearchPrefs setLastDeviceConfigAndroidId(int var1, long var2) {
         List var4 = this.lastDeviceConfigAndroidId_;
         Long var5 = Long.valueOf(var2);
         var4.set(var1, var5);
         return this;
      }

      public UserAccount.SearchPrefs setLastOrderBy(int var1) {
         this.hasLastOrderBy = (boolean)1;
         this.lastOrderBy_ = var1;
         return this;
      }

      public UserAccount.SearchPrefs setLastPriceRestrict(int var1) {
         this.hasLastPriceRestrict = (boolean)1;
         this.lastPriceRestrict_ = var1;
         return this;
      }

      public UserAccount.SearchPrefs setLastSafesearchLevel(int var1) {
         this.hasLastSafesearchLevel = (boolean)1;
         this.lastSafesearchLevel_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasLastOrderBy()) {
            int var2 = this.getLastOrderBy();
            var1.writeInt32(1, var2);
         }

         if(this.hasLastPriceRestrict()) {
            int var3 = this.getLastPriceRestrict();
            var1.writeInt32(2, var3);
         }

         if(this.hasLastSafesearchLevel()) {
            int var4 = this.getLastSafesearchLevel();
            var1.writeInt32(3, var4);
         }

         Iterator var5 = this.getLastDeviceConfigAndroidIdList().iterator();

         while(var5.hasNext()) {
            long var6 = ((Long)var5.next()).longValue();
            var1.writeFixed64(4, var6);
         }

      }
   }

   public static final class Purchase extends MessageMicro {

      public static final int ANDROID_ID_FIELD_NUMBER = 7;
      public static final int BACKEND_DEVICE = 3;
      public static final int BACKEND_WEB = 2;
      public static final int CAN_REFUND_FIELD_NUMBER = 9;
      public static final int DOCID_FIELD_NUMBER = 3;
      public static final int FINSKY_DEVICE = 1;
      public static final int FINSKY_WEB = 0;
      public static final int FULFILLMENT_FIELD_NUMBER = 2;
      public static final int IN_APP = 4;
      public static final int LEGACY_BATCH = 5;
      public static final int OFFER_TYPE_FIELD_NUMBER = 8;
      public static final int ORIGINATION_FIELD_NUMBER = 6;
      public static final int PROMOTION_CODE_FIELD_NUMBER = 5;
      public static final int PURCHASED_DOCUMENT_FIELD_NUMBER = 4;
      public static final int PURCHASE_ID_FIELD_NUMBER = 1;
      public static final int REFUND_EXPIRATION_TIME_FIELD_NUMBER = 10;
      private long androidId_;
      private int cachedSize;
      private boolean canRefund_;
      private Common.Docid docid_;
      private List<UserAccount.Fulfillment> fulfillment_;
      private boolean hasAndroidId;
      private boolean hasCanRefund;
      private boolean hasDocid;
      private boolean hasOfferType;
      private boolean hasOrigination;
      private boolean hasPromotionCode;
      private boolean hasPurchaseId;
      private boolean hasPurchasedDocument;
      private boolean hasRefundExpirationTime;
      private int offerType_;
      private int origination_;
      private String promotionCode_;
      private String purchaseId_ = "";
      private Doc.Document purchasedDocument_;
      private long refundExpirationTime_;


      public Purchase() {
         List var1 = Collections.emptyList();
         this.fulfillment_ = var1;
         this.docid_ = null;
         this.purchasedDocument_ = null;
         this.promotionCode_ = "";
         this.origination_ = 0;
         this.androidId_ = 0L;
         this.offerType_ = 1;
         this.canRefund_ = (boolean)0;
         this.refundExpirationTime_ = 0L;
         this.cachedSize = -1;
      }

      public static UserAccount.Purchase parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.Purchase()).mergeFrom(var0);
      }

      public static UserAccount.Purchase parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.Purchase)((UserAccount.Purchase)(new UserAccount.Purchase()).mergeFrom(var0));
      }

      public UserAccount.Purchase addFulfillment(UserAccount.Fulfillment var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.fulfillment_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.fulfillment_ = var2;
            }

            this.fulfillment_.add(var1);
            return this;
         }
      }

      public final UserAccount.Purchase clear() {
         UserAccount.Purchase var1 = this.clearPurchaseId();
         UserAccount.Purchase var2 = this.clearFulfillment();
         UserAccount.Purchase var3 = this.clearDocid();
         UserAccount.Purchase var4 = this.clearPurchasedDocument();
         UserAccount.Purchase var5 = this.clearPromotionCode();
         UserAccount.Purchase var6 = this.clearOrigination();
         UserAccount.Purchase var7 = this.clearAndroidId();
         UserAccount.Purchase var8 = this.clearOfferType();
         UserAccount.Purchase var9 = this.clearCanRefund();
         UserAccount.Purchase var10 = this.clearRefundExpirationTime();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.Purchase clearAndroidId() {
         this.hasAndroidId = (boolean)0;
         this.androidId_ = 0L;
         return this;
      }

      public UserAccount.Purchase clearCanRefund() {
         this.hasCanRefund = (boolean)0;
         this.canRefund_ = (boolean)0;
         return this;
      }

      public UserAccount.Purchase clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = null;
         return this;
      }

      public UserAccount.Purchase clearFulfillment() {
         List var1 = Collections.emptyList();
         this.fulfillment_ = var1;
         return this;
      }

      public UserAccount.Purchase clearOfferType() {
         this.hasOfferType = (boolean)0;
         this.offerType_ = 1;
         return this;
      }

      public UserAccount.Purchase clearOrigination() {
         this.hasOrigination = (boolean)0;
         this.origination_ = 0;
         return this;
      }

      public UserAccount.Purchase clearPromotionCode() {
         this.hasPromotionCode = (boolean)0;
         this.promotionCode_ = "";
         return this;
      }

      public UserAccount.Purchase clearPurchaseId() {
         this.hasPurchaseId = (boolean)0;
         this.purchaseId_ = "";
         return this;
      }

      public UserAccount.Purchase clearPurchasedDocument() {
         this.hasPurchasedDocument = (boolean)0;
         this.purchasedDocument_ = null;
         return this;
      }

      public UserAccount.Purchase clearRefundExpirationTime() {
         this.hasRefundExpirationTime = (boolean)0;
         this.refundExpirationTime_ = 0L;
         return this;
      }

      public long getAndroidId() {
         return this.androidId_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public boolean getCanRefund() {
         return this.canRefund_;
      }

      public Common.Docid getDocid() {
         return this.docid_;
      }

      public UserAccount.Fulfillment getFulfillment(int var1) {
         return (UserAccount.Fulfillment)this.fulfillment_.get(var1);
      }

      public int getFulfillmentCount() {
         return this.fulfillment_.size();
      }

      public List<UserAccount.Fulfillment> getFulfillmentList() {
         return this.fulfillment_;
      }

      public int getOfferType() {
         return this.offerType_;
      }

      public int getOrigination() {
         return this.origination_;
      }

      public String getPromotionCode() {
         return this.promotionCode_;
      }

      public String getPurchaseId() {
         return this.purchaseId_;
      }

      public Doc.Document getPurchasedDocument() {
         return this.purchasedDocument_;
      }

      public long getRefundExpirationTime() {
         return this.refundExpirationTime_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasPurchaseId()) {
            String var2 = this.getPurchaseId();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         int var6;
         for(Iterator var4 = this.getFulfillmentList().iterator(); var4.hasNext(); var1 += var6) {
            UserAccount.Fulfillment var5 = (UserAccount.Fulfillment)var4.next();
            var6 = CodedOutputStreamMicro.computeMessageSize(2, var5);
         }

         if(this.hasDocid()) {
            Common.Docid var7 = this.getDocid();
            int var8 = CodedOutputStreamMicro.computeMessageSize(3, var7);
            var1 += var8;
         }

         if(this.hasPurchasedDocument()) {
            Doc.Document var9 = this.getPurchasedDocument();
            int var10 = CodedOutputStreamMicro.computeMessageSize(4, var9);
            var1 += var10;
         }

         if(this.hasPromotionCode()) {
            String var11 = this.getPromotionCode();
            int var12 = CodedOutputStreamMicro.computeStringSize(5, var11);
            var1 += var12;
         }

         if(this.hasOrigination()) {
            int var13 = this.getOrigination();
            int var14 = CodedOutputStreamMicro.computeInt32Size(6, var13);
            var1 += var14;
         }

         if(this.hasAndroidId()) {
            long var15 = this.getAndroidId();
            int var17 = CodedOutputStreamMicro.computeFixed64Size(7, var15);
            var1 += var17;
         }

         if(this.hasOfferType()) {
            int var18 = this.getOfferType();
            int var19 = CodedOutputStreamMicro.computeInt32Size(8, var18);
            var1 += var19;
         }

         if(this.hasCanRefund()) {
            boolean var20 = this.getCanRefund();
            int var21 = CodedOutputStreamMicro.computeBoolSize(9, var20);
            var1 += var21;
         }

         if(this.hasRefundExpirationTime()) {
            long var22 = this.getRefundExpirationTime();
            int var24 = CodedOutputStreamMicro.computeUInt64Size(10, var22);
            var1 += var24;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasAndroidId() {
         return this.hasAndroidId;
      }

      public boolean hasCanRefund() {
         return this.hasCanRefund;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public boolean hasOfferType() {
         return this.hasOfferType;
      }

      public boolean hasOrigination() {
         return this.hasOrigination;
      }

      public boolean hasPromotionCode() {
         return this.hasPromotionCode;
      }

      public boolean hasPurchaseId() {
         return this.hasPurchaseId;
      }

      public boolean hasPurchasedDocument() {
         return this.hasPurchasedDocument;
      }

      public boolean hasRefundExpirationTime() {
         return this.hasRefundExpirationTime;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasDocid) {
            Iterator var2 = this.getFulfillmentList().iterator();

            do {
               if(!var2.hasNext()) {
                  if(this.getDocid().isInitialized() && (!this.hasPurchasedDocument() || this.getPurchasedDocument().isInitialized())) {
                     var1 = true;
                  }
                  break;
               }
            } while(((UserAccount.Fulfillment)var2.next()).isInitialized());
         }

         return var1;
      }

      public UserAccount.Purchase mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setPurchaseId(var3);
               break;
            case 18:
               UserAccount.Fulfillment var5 = new UserAccount.Fulfillment();
               var1.readMessage(var5);
               this.addFulfillment(var5);
               break;
            case 26:
               Common.Docid var7 = new Common.Docid();
               var1.readMessage(var7);
               this.setDocid(var7);
               break;
            case 34:
               Doc.Document var9 = new Doc.Document();
               var1.readMessage(var9);
               this.setPurchasedDocument(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setPromotionCode(var11);
               break;
            case 48:
               int var13 = var1.readInt32();
               this.setOrigination(var13);
               break;
            case 57:
               long var15 = var1.readFixed64();
               this.setAndroidId(var15);
               break;
            case 64:
               int var18 = var1.readInt32();
               this.setOfferType(var18);
               break;
            case 72:
               boolean var20 = var1.readBool();
               this.setCanRefund(var20);
               break;
            case 80:
               long var22 = var1.readUInt64();
               this.setRefundExpirationTime(var22);
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

      public UserAccount.Purchase setAndroidId(long var1) {
         this.hasAndroidId = (boolean)1;
         this.androidId_ = var1;
         return this;
      }

      public UserAccount.Purchase setCanRefund(boolean var1) {
         this.hasCanRefund = (boolean)1;
         this.canRefund_ = var1;
         return this;
      }

      public UserAccount.Purchase setDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDocid = (boolean)1;
            this.docid_ = var1;
            return this;
         }
      }

      public UserAccount.Purchase setFulfillment(int var1, UserAccount.Fulfillment var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.fulfillment_.set(var1, var2);
            return this;
         }
      }

      public UserAccount.Purchase setOfferType(int var1) {
         this.hasOfferType = (boolean)1;
         this.offerType_ = var1;
         return this;
      }

      public UserAccount.Purchase setOrigination(int var1) {
         this.hasOrigination = (boolean)1;
         this.origination_ = var1;
         return this;
      }

      public UserAccount.Purchase setPromotionCode(String var1) {
         this.hasPromotionCode = (boolean)1;
         this.promotionCode_ = var1;
         return this;
      }

      public UserAccount.Purchase setPurchaseId(String var1) {
         this.hasPurchaseId = (boolean)1;
         this.purchaseId_ = var1;
         return this;
      }

      public UserAccount.Purchase setPurchasedDocument(Doc.Document var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPurchasedDocument = (boolean)1;
            this.purchasedDocument_ = var1;
            return this;
         }
      }

      public UserAccount.Purchase setRefundExpirationTime(long var1) {
         this.hasRefundExpirationTime = (boolean)1;
         this.refundExpirationTime_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasPurchaseId()) {
            String var2 = this.getPurchaseId();
            var1.writeString(1, var2);
         }

         Iterator var3 = this.getFulfillmentList().iterator();

         while(var3.hasNext()) {
            UserAccount.Fulfillment var4 = (UserAccount.Fulfillment)var3.next();
            var1.writeMessage(2, var4);
         }

         if(this.hasDocid()) {
            Common.Docid var5 = this.getDocid();
            var1.writeMessage(3, var5);
         }

         if(this.hasPurchasedDocument()) {
            Doc.Document var6 = this.getPurchasedDocument();
            var1.writeMessage(4, var6);
         }

         if(this.hasPromotionCode()) {
            String var7 = this.getPromotionCode();
            var1.writeString(5, var7);
         }

         if(this.hasOrigination()) {
            int var8 = this.getOrigination();
            var1.writeInt32(6, var8);
         }

         if(this.hasAndroidId()) {
            long var9 = this.getAndroidId();
            var1.writeFixed64(7, var9);
         }

         if(this.hasOfferType()) {
            int var11 = this.getOfferType();
            var1.writeInt32(8, var11);
         }

         if(this.hasCanRefund()) {
            boolean var12 = this.getCanRefund();
            var1.writeBool(9, var12);
         }

         if(this.hasRefundExpirationTime()) {
            long var13 = this.getRefundExpirationTime();
            var1.writeUInt64(10, var13);
         }
      }
   }

   public static final class Fulfillment extends MessageMicro {

      public static final int CANCELED = 3;
      public static final int CHECKOUT_ORDER_ID_FIELD_NUMBER = 3;
      public static final int GIFT_ID_FIELD_NUMBER = 7;
      public static final int GIFT_PURCHASE_SUCCESS = 9;
      public static final int GIFT_REDEMPTION_INITIATED = 8;
      public static final int MODIFICATION_TIMESTAMP_MSEC_FIELD_NUMBER = 6;
      public static final int PAYMENT_DECLINED = 7;
      public static final int PENDING = 1;
      public static final int PRICE_FIELD_NUMBER = 4;
      public static final int REFUNDED = 4;
      public static final int REMOVED = 5;
      public static final int STATE_FIELD_NUMBER = 1;
      public static final int SUCCESS = 2;
      public static final int TIMESTAMP_MSEC_FIELD_NUMBER = 2;
      public static final int TRANSACTION_ID_FIELD_NUMBER = 5;
      public static final int UPDATED = 6;
      private int cachedSize = -1;
      private String checkoutOrderId_ = "";
      private String giftId_ = "";
      private boolean hasCheckoutOrderId;
      private boolean hasGiftId;
      private boolean hasModificationTimestampMsec;
      private boolean hasPrice;
      private boolean hasState;
      private boolean hasTimestampMsec;
      private boolean hasTransactionId;
      private long modificationTimestampMsec_ = 0L;
      private Common.Offer price_ = null;
      private int state_ = 1;
      private long timestampMsec_ = 0L;
      private String transactionId_ = "";


      public Fulfillment() {}

      public static UserAccount.Fulfillment parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.Fulfillment()).mergeFrom(var0);
      }

      public static UserAccount.Fulfillment parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.Fulfillment)((UserAccount.Fulfillment)(new UserAccount.Fulfillment()).mergeFrom(var0));
      }

      public final UserAccount.Fulfillment clear() {
         UserAccount.Fulfillment var1 = this.clearState();
         UserAccount.Fulfillment var2 = this.clearTimestampMsec();
         UserAccount.Fulfillment var3 = this.clearModificationTimestampMsec();
         UserAccount.Fulfillment var4 = this.clearCheckoutOrderId();
         UserAccount.Fulfillment var5 = this.clearTransactionId();
         UserAccount.Fulfillment var6 = this.clearPrice();
         UserAccount.Fulfillment var7 = this.clearGiftId();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.Fulfillment clearCheckoutOrderId() {
         this.hasCheckoutOrderId = (boolean)0;
         this.checkoutOrderId_ = "";
         return this;
      }

      public UserAccount.Fulfillment clearGiftId() {
         this.hasGiftId = (boolean)0;
         this.giftId_ = "";
         return this;
      }

      public UserAccount.Fulfillment clearModificationTimestampMsec() {
         this.hasModificationTimestampMsec = (boolean)0;
         this.modificationTimestampMsec_ = 0L;
         return this;
      }

      public UserAccount.Fulfillment clearPrice() {
         this.hasPrice = (boolean)0;
         this.price_ = null;
         return this;
      }

      public UserAccount.Fulfillment clearState() {
         this.hasState = (boolean)0;
         this.state_ = 1;
         return this;
      }

      public UserAccount.Fulfillment clearTimestampMsec() {
         this.hasTimestampMsec = (boolean)0;
         this.timestampMsec_ = 0L;
         return this;
      }

      public UserAccount.Fulfillment clearTransactionId() {
         this.hasTransactionId = (boolean)0;
         this.transactionId_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getCheckoutOrderId() {
         return this.checkoutOrderId_;
      }

      public String getGiftId() {
         return this.giftId_;
      }

      public long getModificationTimestampMsec() {
         return this.modificationTimestampMsec_;
      }

      public Common.Offer getPrice() {
         return this.price_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasState()) {
            int var2 = this.getState();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasTimestampMsec()) {
            long var4 = this.getTimestampMsec();
            int var6 = CodedOutputStreamMicro.computeInt64Size(2, var4);
            var1 += var6;
         }

         if(this.hasCheckoutOrderId()) {
            String var7 = this.getCheckoutOrderId();
            int var8 = CodedOutputStreamMicro.computeStringSize(3, var7);
            var1 += var8;
         }

         if(this.hasPrice()) {
            Common.Offer var9 = this.getPrice();
            int var10 = CodedOutputStreamMicro.computeMessageSize(4, var9);
            var1 += var10;
         }

         if(this.hasTransactionId()) {
            String var11 = this.getTransactionId();
            int var12 = CodedOutputStreamMicro.computeStringSize(5, var11);
            var1 += var12;
         }

         if(this.hasModificationTimestampMsec()) {
            long var13 = this.getModificationTimestampMsec();
            int var15 = CodedOutputStreamMicro.computeInt64Size(6, var13);
            var1 += var15;
         }

         if(this.hasGiftId()) {
            String var16 = this.getGiftId();
            int var17 = CodedOutputStreamMicro.computeStringSize(7, var16);
            var1 += var17;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getState() {
         return this.state_;
      }

      public long getTimestampMsec() {
         return this.timestampMsec_;
      }

      public String getTransactionId() {
         return this.transactionId_;
      }

      public boolean hasCheckoutOrderId() {
         return this.hasCheckoutOrderId;
      }

      public boolean hasGiftId() {
         return this.hasGiftId;
      }

      public boolean hasModificationTimestampMsec() {
         return this.hasModificationTimestampMsec;
      }

      public boolean hasPrice() {
         return this.hasPrice;
      }

      public boolean hasState() {
         return this.hasState;
      }

      public boolean hasTimestampMsec() {
         return this.hasTimestampMsec;
      }

      public boolean hasTransactionId() {
         return this.hasTransactionId;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasState && (!this.hasPrice() || this.getPrice().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public UserAccount.Fulfillment mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setState(var3);
               break;
            case 16:
               long var5 = var1.readInt64();
               this.setTimestampMsec(var5);
               break;
            case 26:
               String var8 = var1.readString();
               this.setCheckoutOrderId(var8);
               break;
            case 34:
               Common.Offer var10 = new Common.Offer();
               var1.readMessage(var10);
               this.setPrice(var10);
               break;
            case 42:
               String var12 = var1.readString();
               this.setTransactionId(var12);
               break;
            case 48:
               long var14 = var1.readInt64();
               this.setModificationTimestampMsec(var14);
               break;
            case 58:
               String var17 = var1.readString();
               this.setGiftId(var17);
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

      public UserAccount.Fulfillment setCheckoutOrderId(String var1) {
         this.hasCheckoutOrderId = (boolean)1;
         this.checkoutOrderId_ = var1;
         return this;
      }

      public UserAccount.Fulfillment setGiftId(String var1) {
         this.hasGiftId = (boolean)1;
         this.giftId_ = var1;
         return this;
      }

      public UserAccount.Fulfillment setModificationTimestampMsec(long var1) {
         this.hasModificationTimestampMsec = (boolean)1;
         this.modificationTimestampMsec_ = var1;
         return this;
      }

      public UserAccount.Fulfillment setPrice(Common.Offer var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPrice = (boolean)1;
            this.price_ = var1;
            return this;
         }
      }

      public UserAccount.Fulfillment setState(int var1) {
         this.hasState = (boolean)1;
         this.state_ = var1;
         return this;
      }

      public UserAccount.Fulfillment setTimestampMsec(long var1) {
         this.hasTimestampMsec = (boolean)1;
         this.timestampMsec_ = var1;
         return this;
      }

      public UserAccount.Fulfillment setTransactionId(String var1) {
         this.hasTransactionId = (boolean)1;
         this.transactionId_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasState()) {
            int var2 = this.getState();
            var1.writeInt32(1, var2);
         }

         if(this.hasTimestampMsec()) {
            long var3 = this.getTimestampMsec();
            var1.writeInt64(2, var3);
         }

         if(this.hasCheckoutOrderId()) {
            String var5 = this.getCheckoutOrderId();
            var1.writeString(3, var5);
         }

         if(this.hasPrice()) {
            Common.Offer var6 = this.getPrice();
            var1.writeMessage(4, var6);
         }

         if(this.hasTransactionId()) {
            String var7 = this.getTransactionId();
            var1.writeString(5, var7);
         }

         if(this.hasModificationTimestampMsec()) {
            long var8 = this.getModificationTimestampMsec();
            var1.writeInt64(6, var8);
         }

         if(this.hasGiftId()) {
            String var10 = this.getGiftId();
            var1.writeString(7, var10);
         }
      }
   }

   public static final class DeviceVisibility extends MessageMicro {

      public static final int HIDDEN_STABLE_DEVICE_ID_FIELD_NUMBER = 1;
      private int cachedSize;
      private List<Long> hiddenStableDeviceId_;


      public DeviceVisibility() {
         List var1 = Collections.emptyList();
         this.hiddenStableDeviceId_ = var1;
         this.cachedSize = -1;
      }

      public static UserAccount.DeviceVisibility parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.DeviceVisibility()).mergeFrom(var0);
      }

      public static UserAccount.DeviceVisibility parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.DeviceVisibility)((UserAccount.DeviceVisibility)(new UserAccount.DeviceVisibility()).mergeFrom(var0));
      }

      public UserAccount.DeviceVisibility addHiddenStableDeviceId(long var1) {
         if(this.hiddenStableDeviceId_.isEmpty()) {
            ArrayList var3 = new ArrayList();
            this.hiddenStableDeviceId_ = var3;
         }

         List var4 = this.hiddenStableDeviceId_;
         Long var5 = Long.valueOf(var1);
         var4.add(var5);
         return this;
      }

      public final UserAccount.DeviceVisibility clear() {
         UserAccount.DeviceVisibility var1 = this.clearHiddenStableDeviceId();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.DeviceVisibility clearHiddenStableDeviceId() {
         List var1 = Collections.emptyList();
         this.hiddenStableDeviceId_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public long getHiddenStableDeviceId(int var1) {
         return ((Long)this.hiddenStableDeviceId_.get(var1)).longValue();
      }

      public int getHiddenStableDeviceIdCount() {
         return this.hiddenStableDeviceId_.size();
      }

      public List<Long> getHiddenStableDeviceIdList() {
         return this.hiddenStableDeviceId_;
      }

      public int getSerializedSize() {
         int var1 = this.getHiddenStableDeviceIdList().size() * 8;
         int var2 = 0 + var1;
         int var3 = this.getHiddenStableDeviceIdList().size() * 1;
         int var4 = var2 + var3;
         this.cachedSize = var4;
         return var4;
      }

      public final boolean isInitialized() {
         return true;
      }

      public UserAccount.DeviceVisibility mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 9:
               long var3 = var1.readFixed64();
               this.addHiddenStableDeviceId(var3);
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

      public UserAccount.DeviceVisibility setHiddenStableDeviceId(int var1, long var2) {
         List var4 = this.hiddenStableDeviceId_;
         Long var5 = Long.valueOf(var2);
         var4.set(var1, var5);
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getHiddenStableDeviceIdList().iterator();

         while(var2.hasNext()) {
            long var3 = ((Long)var2.next()).longValue();
            var1.writeFixed64(1, var3);
         }

      }
   }

   public static final class PurchaseHistoryResponse extends MessageMicro {

      public static final int DEBUG_INFO_FIELD_NUMBER = 3;
      public static final int NUM_PURCHASES_FIELD_NUMBER = 2;
      public static final int PURCHASE_HISTORY_FIELD_NUMBER = 1;
      private int cachedSize;
      private DebugInfo debugInfo_;
      private boolean hasDebugInfo;
      private boolean hasNumPurchases;
      private int numPurchases_;
      private List<UserAccount.Purchase> purchaseHistory_;


      public PurchaseHistoryResponse() {
         List var1 = Collections.emptyList();
         this.purchaseHistory_ = var1;
         this.numPurchases_ = 0;
         this.debugInfo_ = null;
         this.cachedSize = -1;
      }

      public static UserAccount.PurchaseHistoryResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new UserAccount.PurchaseHistoryResponse()).mergeFrom(var0);
      }

      public static UserAccount.PurchaseHistoryResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (UserAccount.PurchaseHistoryResponse)((UserAccount.PurchaseHistoryResponse)(new UserAccount.PurchaseHistoryResponse()).mergeFrom(var0));
      }

      public UserAccount.PurchaseHistoryResponse addPurchaseHistory(UserAccount.Purchase var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.purchaseHistory_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.purchaseHistory_ = var2;
            }

            this.purchaseHistory_.add(var1);
            return this;
         }
      }

      public final UserAccount.PurchaseHistoryResponse clear() {
         UserAccount.PurchaseHistoryResponse var1 = this.clearPurchaseHistory();
         UserAccount.PurchaseHistoryResponse var2 = this.clearNumPurchases();
         UserAccount.PurchaseHistoryResponse var3 = this.clearDebugInfo();
         this.cachedSize = -1;
         return this;
      }

      public UserAccount.PurchaseHistoryResponse clearDebugInfo() {
         this.hasDebugInfo = (boolean)0;
         this.debugInfo_ = null;
         return this;
      }

      public UserAccount.PurchaseHistoryResponse clearNumPurchases() {
         this.hasNumPurchases = (boolean)0;
         this.numPurchases_ = 0;
         return this;
      }

      public UserAccount.PurchaseHistoryResponse clearPurchaseHistory() {
         List var1 = Collections.emptyList();
         this.purchaseHistory_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public DebugInfo getDebugInfo() {
         return this.debugInfo_;
      }

      public int getNumPurchases() {
         return this.numPurchases_;
      }

      public UserAccount.Purchase getPurchaseHistory(int var1) {
         return (UserAccount.Purchase)this.purchaseHistory_.get(var1);
      }

      public int getPurchaseHistoryCount() {
         return this.purchaseHistory_.size();
      }

      public List<UserAccount.Purchase> getPurchaseHistoryList() {
         return this.purchaseHistory_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getPurchaseHistoryList().iterator(); var2.hasNext(); var1 += var4) {
            UserAccount.Purchase var3 = (UserAccount.Purchase)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(1, var3);
         }

         if(this.hasNumPurchases()) {
            int var5 = this.getNumPurchases();
            int var6 = CodedOutputStreamMicro.computeInt32Size(2, var5);
            var1 += var6;
         }

         if(this.hasDebugInfo()) {
            DebugInfo var7 = this.getDebugInfo();
            int var8 = CodedOutputStreamMicro.computeMessageSize(3, var7);
            var1 += var8;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasDebugInfo() {
         return this.hasDebugInfo;
      }

      public boolean hasNumPurchases() {
         return this.hasNumPurchases;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         Iterator var2 = this.getPurchaseHistoryList().iterator();

         do {
            if(!var2.hasNext()) {
               if(!this.hasDebugInfo() || this.getDebugInfo().isInitialized()) {
                  var1 = true;
               }
               break;
            }
         } while(((UserAccount.Purchase)var2.next()).isInitialized());

         return var1;
      }

      public UserAccount.PurchaseHistoryResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               UserAccount.Purchase var3 = new UserAccount.Purchase();
               var1.readMessage(var3);
               this.addPurchaseHistory(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setNumPurchases(var5);
               break;
            case 26:
               DebugInfo var7 = new DebugInfo();
               var1.readMessage(var7);
               this.setDebugInfo(var7);
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

      public UserAccount.PurchaseHistoryResponse setDebugInfo(DebugInfo var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDebugInfo = (boolean)1;
            this.debugInfo_ = var1;
            return this;
         }
      }

      public UserAccount.PurchaseHistoryResponse setNumPurchases(int var1) {
         this.hasNumPurchases = (boolean)1;
         this.numPurchases_ = var1;
         return this;
      }

      public UserAccount.PurchaseHistoryResponse setPurchaseHistory(int var1, UserAccount.Purchase var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.purchaseHistory_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getPurchaseHistoryList().iterator();

         while(var2.hasNext()) {
            UserAccount.Purchase var3 = (UserAccount.Purchase)var2.next();
            var1.writeMessage(1, var3);
         }

         if(this.hasNumPurchases()) {
            int var4 = this.getNumPurchases();
            var1.writeInt32(2, var4);
         }

         if(this.hasDebugInfo()) {
            DebugInfo var5 = this.getDebugInfo();
            var1.writeMessage(3, var5);
         }
      }
   }
}
