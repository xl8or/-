package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Cache;
import com.google.android.finsky.remoting.protos.Common;
import com.google.android.finsky.remoting.protos.DebugInfo;
import com.google.android.finsky.remoting.protos.RequestContext;
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

public final class Purchase {

   private Purchase() {}

   public static final class PurchaseNotificationResponse extends MessageMicro {

      public static final int BACKEND_ERROR = 2;
      public static final int DEBUG_INFO_FIELD_NUMBER = 2;
      public static final int INVALID_DOCID = 1;
      public static final int LOCALIZED_ERROR_MESSAGE_FIELD_NUMBER = 3;
      public static final int OK = 0;
      public static final int PURCHASE_ID_FIELD_NUMBER = 4;
      public static final int STATUS_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private DebugInfo debugInfo_ = null;
      private boolean hasDebugInfo;
      private boolean hasLocalizedErrorMessage;
      private boolean hasPurchaseId;
      private boolean hasStatus;
      private String localizedErrorMessage_ = "";
      private String purchaseId_ = "";
      private int status_ = 0;


      public PurchaseNotificationResponse() {}

      public static Purchase.PurchaseNotificationResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.PurchaseNotificationResponse()).mergeFrom(var0);
      }

      public static Purchase.PurchaseNotificationResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.PurchaseNotificationResponse)((Purchase.PurchaseNotificationResponse)(new Purchase.PurchaseNotificationResponse()).mergeFrom(var0));
      }

      public final Purchase.PurchaseNotificationResponse clear() {
         Purchase.PurchaseNotificationResponse var1 = this.clearStatus();
         Purchase.PurchaseNotificationResponse var2 = this.clearPurchaseId();
         Purchase.PurchaseNotificationResponse var3 = this.clearLocalizedErrorMessage();
         Purchase.PurchaseNotificationResponse var4 = this.clearDebugInfo();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.PurchaseNotificationResponse clearDebugInfo() {
         this.hasDebugInfo = (boolean)0;
         this.debugInfo_ = null;
         return this;
      }

      public Purchase.PurchaseNotificationResponse clearLocalizedErrorMessage() {
         this.hasLocalizedErrorMessage = (boolean)0;
         this.localizedErrorMessage_ = "";
         return this;
      }

      public Purchase.PurchaseNotificationResponse clearPurchaseId() {
         this.hasPurchaseId = (boolean)0;
         this.purchaseId_ = "";
         return this;
      }

      public Purchase.PurchaseNotificationResponse clearStatus() {
         this.hasStatus = (boolean)0;
         this.status_ = 0;
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

      public String getLocalizedErrorMessage() {
         return this.localizedErrorMessage_;
      }

      public String getPurchaseId() {
         return this.purchaseId_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasStatus()) {
            int var2 = this.getStatus();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasDebugInfo()) {
            DebugInfo var4 = this.getDebugInfo();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         if(this.hasLocalizedErrorMessage()) {
            String var6 = this.getLocalizedErrorMessage();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasPurchaseId()) {
            String var8 = this.getPurchaseId();
            int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
            var1 += var9;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getStatus() {
         return this.status_;
      }

      public boolean hasDebugInfo() {
         return this.hasDebugInfo;
      }

      public boolean hasLocalizedErrorMessage() {
         return this.hasLocalizedErrorMessage;
      }

      public boolean hasPurchaseId() {
         return this.hasPurchaseId;
      }

      public boolean hasStatus() {
         return this.hasStatus;
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

      public Purchase.PurchaseNotificationResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setStatus(var3);
               break;
            case 18:
               DebugInfo var5 = new DebugInfo();
               var1.readMessage(var5);
               this.setDebugInfo(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setLocalizedErrorMessage(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setPurchaseId(var9);
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

      public Purchase.PurchaseNotificationResponse setDebugInfo(DebugInfo var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDebugInfo = (boolean)1;
            this.debugInfo_ = var1;
            return this;
         }
      }

      public Purchase.PurchaseNotificationResponse setLocalizedErrorMessage(String var1) {
         this.hasLocalizedErrorMessage = (boolean)1;
         this.localizedErrorMessage_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationResponse setPurchaseId(String var1) {
         this.hasPurchaseId = (boolean)1;
         this.purchaseId_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationResponse setStatus(int var1) {
         this.hasStatus = (boolean)1;
         this.status_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasStatus()) {
            int var2 = this.getStatus();
            var1.writeInt32(1, var2);
         }

         if(this.hasDebugInfo()) {
            DebugInfo var3 = this.getDebugInfo();
            var1.writeMessage(2, var3);
         }

         if(this.hasLocalizedErrorMessage()) {
            String var4 = this.getLocalizedErrorMessage();
            var1.writeString(3, var4);
         }

         if(this.hasPurchaseId()) {
            String var5 = this.getPurchaseId();
            var1.writeString(4, var5);
         }
      }
   }

   public static final class AdjustCartRequest extends MessageMicro {

      public static final int BACKEND_FIELD_NUMBER = 2;
      public static final int CHECKOUT_CART_ID_FIELD_NUMBER = 3;
      public static final int CONTEXT_FIELD_NUMBER = 1;
      private int backend_ = 0;
      private int cachedSize = -1;
      private String checkoutCartId_ = "";
      private RequestContext context_ = null;
      private boolean hasBackend;
      private boolean hasCheckoutCartId;
      private boolean hasContext;


      public AdjustCartRequest() {}

      public static Purchase.AdjustCartRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.AdjustCartRequest()).mergeFrom(var0);
      }

      public static Purchase.AdjustCartRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.AdjustCartRequest)((Purchase.AdjustCartRequest)(new Purchase.AdjustCartRequest()).mergeFrom(var0));
      }

      public final Purchase.AdjustCartRequest clear() {
         Purchase.AdjustCartRequest var1 = this.clearContext();
         Purchase.AdjustCartRequest var2 = this.clearBackend();
         Purchase.AdjustCartRequest var3 = this.clearCheckoutCartId();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.AdjustCartRequest clearBackend() {
         this.hasBackend = (boolean)0;
         this.backend_ = 0;
         return this;
      }

      public Purchase.AdjustCartRequest clearCheckoutCartId() {
         this.hasCheckoutCartId = (boolean)0;
         this.checkoutCartId_ = "";
         return this;
      }

      public Purchase.AdjustCartRequest clearContext() {
         this.hasContext = (boolean)0;
         this.context_ = null;
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

      public String getCheckoutCartId() {
         return this.checkoutCartId_;
      }

      public RequestContext getContext() {
         return this.context_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasBackend()) {
            int var4 = this.getBackend();
            int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
            var1 += var5;
         }

         if(this.hasCheckoutCartId()) {
            String var6 = this.getCheckoutCartId();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasBackend() {
         return this.hasBackend;
      }

      public boolean hasCheckoutCartId() {
         return this.hasCheckoutCartId;
      }

      public boolean hasContext() {
         return this.hasContext;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasContext && this.hasBackend && this.hasCheckoutCartId && this.getContext().isInitialized()) {
            var1 = true;
         }

         return var1;
      }

      public Purchase.AdjustCartRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               RequestContext var3 = new RequestContext();
               var1.readMessage(var3);
               this.setContext(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setBackend(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setCheckoutCartId(var7);
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

      public Purchase.AdjustCartRequest setBackend(int var1) {
         this.hasBackend = (boolean)1;
         this.backend_ = var1;
         return this;
      }

      public Purchase.AdjustCartRequest setCheckoutCartId(String var1) {
         this.hasCheckoutCartId = (boolean)1;
         this.checkoutCartId_ = var1;
         return this;
      }

      public Purchase.AdjustCartRequest setContext(RequestContext var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasContext = (boolean)1;
            this.context_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            var1.writeMessage(1, var2);
         }

         if(this.hasBackend()) {
            int var3 = this.getBackend();
            var1.writeInt32(2, var3);
         }

         if(this.hasCheckoutCartId()) {
            String var4 = this.getCheckoutCartId();
            var1.writeString(3, var4);
         }
      }
   }

   public static final class RefundEligibilityRequest extends MessageMicro {

      public static final int CONTEXT_FIELD_NUMBER = 1;
      public static final int TRANSACTION_FIELD_NUMBER = 2;
      private int cachedSize;
      private RequestContext context_ = null;
      private boolean hasContext;
      private List<Purchase.RefundEligibilityRequest.Transaction> transaction_;


      public RefundEligibilityRequest() {
         List var1 = Collections.emptyList();
         this.transaction_ = var1;
         this.cachedSize = -1;
      }

      public static Purchase.RefundEligibilityRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.RefundEligibilityRequest()).mergeFrom(var0);
      }

      public static Purchase.RefundEligibilityRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.RefundEligibilityRequest)((Purchase.RefundEligibilityRequest)(new Purchase.RefundEligibilityRequest()).mergeFrom(var0));
      }

      public Purchase.RefundEligibilityRequest addTransaction(Purchase.RefundEligibilityRequest.Transaction var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.transaction_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.transaction_ = var2;
            }

            this.transaction_.add(var1);
            return this;
         }
      }

      public final Purchase.RefundEligibilityRequest clear() {
         Purchase.RefundEligibilityRequest var1 = this.clearContext();
         Purchase.RefundEligibilityRequest var2 = this.clearTransaction();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.RefundEligibilityRequest clearContext() {
         this.hasContext = (boolean)0;
         this.context_ = null;
         return this;
      }

      public Purchase.RefundEligibilityRequest clearTransaction() {
         List var1 = Collections.emptyList();
         this.transaction_ = var1;
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

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         int var6;
         for(Iterator var4 = this.getTransactionList().iterator(); var4.hasNext(); var1 += var6) {
            Purchase.RefundEligibilityRequest.Transaction var5 = (Purchase.RefundEligibilityRequest.Transaction)var4.next();
            var6 = CodedOutputStreamMicro.computeGroupSize(2, var5);
         }

         this.cachedSize = var1;
         return var1;
      }

      public Purchase.RefundEligibilityRequest.Transaction getTransaction(int var1) {
         return (Purchase.RefundEligibilityRequest.Transaction)this.transaction_.get(var1);
      }

      public int getTransactionCount() {
         return this.transaction_.size();
      }

      public List<Purchase.RefundEligibilityRequest.Transaction> getTransactionList() {
         return this.transaction_;
      }

      public boolean hasContext() {
         return this.hasContext;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasContext && this.getContext().isInitialized()) {
            Iterator var2 = this.getTransactionList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((Purchase.RefundEligibilityRequest.Transaction)var2.next()).isInitialized());
         }

         return var1;
      }

      public Purchase.RefundEligibilityRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               RequestContext var3 = new RequestContext();
               var1.readMessage(var3);
               this.setContext(var3);
               break;
            case 19:
               Purchase.RefundEligibilityRequest.Transaction var5 = new Purchase.RefundEligibilityRequest.Transaction();
               var1.readGroup(var5, 2);
               this.addTransaction(var5);
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

      public Purchase.RefundEligibilityRequest setContext(RequestContext var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasContext = (boolean)1;
            this.context_ = var1;
            return this;
         }
      }

      public Purchase.RefundEligibilityRequest setTransaction(int var1, Purchase.RefundEligibilityRequest.Transaction var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.transaction_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            var1.writeMessage(1, var2);
         }

         Iterator var3 = this.getTransactionList().iterator();

         while(var3.hasNext()) {
            Purchase.RefundEligibilityRequest.Transaction var4 = (Purchase.RefundEligibilityRequest.Transaction)var3.next();
            var1.writeGroup(2, var4);
         }

      }

      public static final class Transaction extends MessageMicro {

         public static final int DOCID_FIELD_NUMBER = 3;
         private int cachedSize = -1;
         private Common.Docid docid_ = null;
         private boolean hasDocid;


         public Transaction() {}

         public final Purchase.RefundEligibilityRequest.Transaction clear() {
            Purchase.RefundEligibilityRequest.Transaction var1 = this.clearDocid();
            this.cachedSize = -1;
            return this;
         }

         public Purchase.RefundEligibilityRequest.Transaction clearDocid() {
            this.hasDocid = (boolean)0;
            this.docid_ = null;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public Common.Docid getDocid() {
            return this.docid_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasDocid()) {
               Common.Docid var2 = this.getDocid();
               int var3 = CodedOutputStreamMicro.computeMessageSize(3, var2);
               var1 = 0 + var3;
            }

            this.cachedSize = var1;
            return var1;
         }

         public boolean hasDocid() {
            return this.hasDocid;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if(this.hasDocid && this.getDocid().isInitialized()) {
               var1 = true;
            }

            return var1;
         }

         public Purchase.RefundEligibilityRequest.Transaction mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 26:
                  Common.Docid var3 = new Common.Docid();
                  var1.readMessage(var3);
                  this.setDocid(var3);
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

         public Purchase.RefundEligibilityRequest.Transaction parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new Purchase.RefundEligibilityRequest.Transaction()).mergeFrom(var1);
         }

         public Purchase.RefundEligibilityRequest.Transaction parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (Purchase.RefundEligibilityRequest.Transaction)((Purchase.RefundEligibilityRequest.Transaction)(new Purchase.RefundEligibilityRequest.Transaction()).mergeFrom(var1));
         }

         public Purchase.RefundEligibilityRequest.Transaction setDocid(Common.Docid var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasDocid = (boolean)1;
               this.docid_ = var1;
               return this;
            }
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasDocid()) {
               Common.Docid var2 = this.getDocid();
               var1.writeMessage(3, var2);
            }
         }
      }
   }

   public static final class CreateCartResponse extends MessageMicro {

      public static final int BACKEND_ERROR = 2;
      public static final int CHECKOUT_CART_ID_FIELD_NUMBER = 2;
      public static final int DEBUG_INFO_FIELD_NUMBER = 3;
      public static final int INVALID_DOCID = 1;
      public static final int OK = 0;
      public static final int STATUS_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private String checkoutCartId_ = "";
      private DebugInfo debugInfo_ = null;
      private boolean hasCheckoutCartId;
      private boolean hasDebugInfo;
      private boolean hasStatus;
      private int status_ = 0;


      public CreateCartResponse() {}

      public static Purchase.CreateCartResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.CreateCartResponse()).mergeFrom(var0);
      }

      public static Purchase.CreateCartResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.CreateCartResponse)((Purchase.CreateCartResponse)(new Purchase.CreateCartResponse()).mergeFrom(var0));
      }

      public final Purchase.CreateCartResponse clear() {
         Purchase.CreateCartResponse var1 = this.clearStatus();
         Purchase.CreateCartResponse var2 = this.clearCheckoutCartId();
         Purchase.CreateCartResponse var3 = this.clearDebugInfo();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.CreateCartResponse clearCheckoutCartId() {
         this.hasCheckoutCartId = (boolean)0;
         this.checkoutCartId_ = "";
         return this;
      }

      public Purchase.CreateCartResponse clearDebugInfo() {
         this.hasDebugInfo = (boolean)0;
         this.debugInfo_ = null;
         return this;
      }

      public Purchase.CreateCartResponse clearStatus() {
         this.hasStatus = (boolean)0;
         this.status_ = 0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getCheckoutCartId() {
         return this.checkoutCartId_;
      }

      public DebugInfo getDebugInfo() {
         return this.debugInfo_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasStatus()) {
            int var2 = this.getStatus();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasCheckoutCartId()) {
            String var4 = this.getCheckoutCartId();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasDebugInfo()) {
            DebugInfo var6 = this.getDebugInfo();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getStatus() {
         return this.status_;
      }

      public boolean hasCheckoutCartId() {
         return this.hasCheckoutCartId;
      }

      public boolean hasDebugInfo() {
         return this.hasDebugInfo;
      }

      public boolean hasStatus() {
         return this.hasStatus;
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

      public Purchase.CreateCartResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setStatus(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setCheckoutCartId(var5);
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

      public Purchase.CreateCartResponse setCheckoutCartId(String var1) {
         this.hasCheckoutCartId = (boolean)1;
         this.checkoutCartId_ = var1;
         return this;
      }

      public Purchase.CreateCartResponse setDebugInfo(DebugInfo var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDebugInfo = (boolean)1;
            this.debugInfo_ = var1;
            return this;
         }
      }

      public Purchase.CreateCartResponse setStatus(int var1) {
         this.hasStatus = (boolean)1;
         this.status_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasStatus()) {
            int var2 = this.getStatus();
            var1.writeInt32(1, var2);
         }

         if(this.hasCheckoutCartId()) {
            String var3 = this.getCheckoutCartId();
            var1.writeString(2, var3);
         }

         if(this.hasDebugInfo()) {
            DebugInfo var4 = this.getDebugInfo();
            var1.writeMessage(3, var4);
         }
      }
   }

   public static final class PurchasePermissionRequest extends MessageMicro {

      public static final int CONTEXT_FIELD_NUMBER = 1;
      public static final int DEVICE_FIELD_NUMBER = 4;
      public static final int DOCID_FIELD_NUMBER = 2;
      public static final int STABLE_DEVICE_ID_DEPRECATED_FIELD_NUMBER = 3;
      private int cachedSize;
      private RequestContext context_ = null;
      private List<Purchase.PurchasePermissionRequest.Device> device_;
      private Common.Docid docid_ = null;
      private boolean hasContext;
      private boolean hasDocid;
      private List<Long> stableDeviceIdDeprecated_;


      public PurchasePermissionRequest() {
         List var1 = Collections.emptyList();
         this.stableDeviceIdDeprecated_ = var1;
         List var2 = Collections.emptyList();
         this.device_ = var2;
         this.cachedSize = -1;
      }

      public static Purchase.PurchasePermissionRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.PurchasePermissionRequest()).mergeFrom(var0);
      }

      public static Purchase.PurchasePermissionRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.PurchasePermissionRequest)((Purchase.PurchasePermissionRequest)(new Purchase.PurchasePermissionRequest()).mergeFrom(var0));
      }

      public Purchase.PurchasePermissionRequest addDevice(Purchase.PurchasePermissionRequest.Device var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.device_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.device_ = var2;
            }

            this.device_.add(var1);
            return this;
         }
      }

      public Purchase.PurchasePermissionRequest addStableDeviceIdDeprecated(long var1) {
         if(this.stableDeviceIdDeprecated_.isEmpty()) {
            ArrayList var3 = new ArrayList();
            this.stableDeviceIdDeprecated_ = var3;
         }

         List var4 = this.stableDeviceIdDeprecated_;
         Long var5 = Long.valueOf(var1);
         var4.add(var5);
         return this;
      }

      public final Purchase.PurchasePermissionRequest clear() {
         Purchase.PurchasePermissionRequest var1 = this.clearContext();
         Purchase.PurchasePermissionRequest var2 = this.clearDocid();
         Purchase.PurchasePermissionRequest var3 = this.clearStableDeviceIdDeprecated();
         Purchase.PurchasePermissionRequest var4 = this.clearDevice();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.PurchasePermissionRequest clearContext() {
         this.hasContext = (boolean)0;
         this.context_ = null;
         return this;
      }

      public Purchase.PurchasePermissionRequest clearDevice() {
         List var1 = Collections.emptyList();
         this.device_ = var1;
         return this;
      }

      public Purchase.PurchasePermissionRequest clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = null;
         return this;
      }

      public Purchase.PurchasePermissionRequest clearStableDeviceIdDeprecated() {
         List var1 = Collections.emptyList();
         this.stableDeviceIdDeprecated_ = var1;
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

      public Purchase.PurchasePermissionRequest.Device getDevice(int var1) {
         return (Purchase.PurchasePermissionRequest.Device)this.device_.get(var1);
      }

      public int getDeviceCount() {
         return this.device_.size();
      }

      public List<Purchase.PurchasePermissionRequest.Device> getDeviceList() {
         return this.device_;
      }

      public Common.Docid getDocid() {
         return this.docid_;
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

         int var6 = this.getStableDeviceIdDeprecatedList().size() * 8;
         int var7 = var1 + var6;
         int var8 = this.getStableDeviceIdDeprecatedList().size() * 1;
         int var9 = var7 + var8;

         int var12;
         for(Iterator var10 = this.getDeviceList().iterator(); var10.hasNext(); var9 += var12) {
            Purchase.PurchasePermissionRequest.Device var11 = (Purchase.PurchasePermissionRequest.Device)var10.next();
            var12 = CodedOutputStreamMicro.computeGroupSize(4, var11);
         }

         this.cachedSize = var9;
         return var9;
      }

      public long getStableDeviceIdDeprecated(int var1) {
         return ((Long)this.stableDeviceIdDeprecated_.get(var1)).longValue();
      }

      public int getStableDeviceIdDeprecatedCount() {
         return this.stableDeviceIdDeprecated_.size();
      }

      public List<Long> getStableDeviceIdDeprecatedList() {
         return this.stableDeviceIdDeprecated_;
      }

      public boolean hasContext() {
         return this.hasContext;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasContext && this.hasDocid && this.getContext().isInitialized() && this.getDocid().isInitialized()) {
            Iterator var2 = this.getDeviceList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((Purchase.PurchasePermissionRequest.Device)var2.next()).isInitialized());
         }

         return var1;
      }

      public Purchase.PurchasePermissionRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
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
            case 25:
               long var7 = var1.readFixed64();
               this.addStableDeviceIdDeprecated(var7);
               break;
            case 35:
               Purchase.PurchasePermissionRequest.Device var10 = new Purchase.PurchasePermissionRequest.Device();
               var1.readGroup(var10, 4);
               this.addDevice(var10);
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

      public Purchase.PurchasePermissionRequest setContext(RequestContext var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasContext = (boolean)1;
            this.context_ = var1;
            return this;
         }
      }

      public Purchase.PurchasePermissionRequest setDevice(int var1, Purchase.PurchasePermissionRequest.Device var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.device_.set(var1, var2);
            return this;
         }
      }

      public Purchase.PurchasePermissionRequest setDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDocid = (boolean)1;
            this.docid_ = var1;
            return this;
         }
      }

      public Purchase.PurchasePermissionRequest setStableDeviceIdDeprecated(int var1, long var2) {
         List var4 = this.stableDeviceIdDeprecated_;
         Long var5 = Long.valueOf(var2);
         var4.set(var1, var5);
         return this;
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

         Iterator var4 = this.getStableDeviceIdDeprecatedList().iterator();

         while(var4.hasNext()) {
            long var5 = ((Long)var4.next()).longValue();
            var1.writeFixed64(3, var5);
         }

         Iterator var7 = this.getDeviceList().iterator();

         while(var7.hasNext()) {
            Purchase.PurchasePermissionRequest.Device var8 = (Purchase.PurchasePermissionRequest.Device)var7.next();
            var1.writeGroup(4, var8);
         }

      }

      public static final class Device extends MessageMicro {

         public static final int ANDROID_ID_FIELD_NUMBER = 12;
         public static final int CARRIER_DEPRECATED_FIELD_NUMBER = 8;
         public static final int CLIENT_SOFTWARE_VERSION_FIELD_NUMBER = 10;
         public static final int DEVICE_CLIENT_ID_FIELD_NUMBER = 11;
         public static final int MAKER_DEPRECATED_FIELD_NUMBER = 7;
         public static final int MODEL_DEPRECATED_FIELD_NUMBER = 6;
         public static final int SAFESEARCH_LEVEL_FIELD_NUMBER = 13;
         public static final int SIGNATURE_HASH_FIELD_NUMBER = 9;
         public static final int STABLE_DEVICE_ID_FIELD_NUMBER = 5;
         private long androidId_ = 0L;
         private int cachedSize = -1;
         private String carrierDeprecated_ = "";
         private int clientSoftwareVersion_ = 0;
         private String deviceClientId_ = "";
         private boolean hasAndroidId;
         private boolean hasCarrierDeprecated;
         private boolean hasClientSoftwareVersion;
         private boolean hasDeviceClientId;
         private boolean hasMakerDeprecated;
         private boolean hasModelDeprecated;
         private boolean hasSafesearchLevel;
         private boolean hasSignatureHash;
         private boolean hasStableDeviceId;
         private String makerDeprecated_ = "";
         private String modelDeprecated_ = "";
         private int safesearchLevel_ = 0;
         private Purchase.SignatureHash signatureHash_ = null;
         private long stableDeviceId_ = 0L;


         public Device() {}

         public final Purchase.PurchasePermissionRequest.Device clear() {
            Purchase.PurchasePermissionRequest.Device var1 = this.clearStableDeviceId();
            Purchase.PurchasePermissionRequest.Device var2 = this.clearAndroidId();
            Purchase.PurchasePermissionRequest.Device var3 = this.clearModelDeprecated();
            Purchase.PurchasePermissionRequest.Device var4 = this.clearMakerDeprecated();
            Purchase.PurchasePermissionRequest.Device var5 = this.clearCarrierDeprecated();
            Purchase.PurchasePermissionRequest.Device var6 = this.clearSignatureHash();
            Purchase.PurchasePermissionRequest.Device var7 = this.clearClientSoftwareVersion();
            Purchase.PurchasePermissionRequest.Device var8 = this.clearDeviceClientId();
            Purchase.PurchasePermissionRequest.Device var9 = this.clearSafesearchLevel();
            this.cachedSize = -1;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device clearAndroidId() {
            this.hasAndroidId = (boolean)0;
            this.androidId_ = 0L;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device clearCarrierDeprecated() {
            this.hasCarrierDeprecated = (boolean)0;
            this.carrierDeprecated_ = "";
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device clearClientSoftwareVersion() {
            this.hasClientSoftwareVersion = (boolean)0;
            this.clientSoftwareVersion_ = 0;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device clearDeviceClientId() {
            this.hasDeviceClientId = (boolean)0;
            this.deviceClientId_ = "";
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device clearMakerDeprecated() {
            this.hasMakerDeprecated = (boolean)0;
            this.makerDeprecated_ = "";
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device clearModelDeprecated() {
            this.hasModelDeprecated = (boolean)0;
            this.modelDeprecated_ = "";
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device clearSafesearchLevel() {
            this.hasSafesearchLevel = (boolean)0;
            this.safesearchLevel_ = 0;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device clearSignatureHash() {
            this.hasSignatureHash = (boolean)0;
            this.signatureHash_ = null;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device clearStableDeviceId() {
            this.hasStableDeviceId = (boolean)0;
            this.stableDeviceId_ = 0L;
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

         public String getCarrierDeprecated() {
            return this.carrierDeprecated_;
         }

         public int getClientSoftwareVersion() {
            return this.clientSoftwareVersion_;
         }

         public String getDeviceClientId() {
            return this.deviceClientId_;
         }

         public String getMakerDeprecated() {
            return this.makerDeprecated_;
         }

         public String getModelDeprecated() {
            return this.modelDeprecated_;
         }

         public int getSafesearchLevel() {
            return this.safesearchLevel_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasStableDeviceId()) {
               long var2 = this.getStableDeviceId();
               int var4 = CodedOutputStreamMicro.computeFixed64Size(5, var2);
               var1 = 0 + var4;
            }

            if(this.hasModelDeprecated()) {
               String var5 = this.getModelDeprecated();
               int var6 = CodedOutputStreamMicro.computeStringSize(6, var5);
               var1 += var6;
            }

            if(this.hasMakerDeprecated()) {
               String var7 = this.getMakerDeprecated();
               int var8 = CodedOutputStreamMicro.computeStringSize(7, var7);
               var1 += var8;
            }

            if(this.hasCarrierDeprecated()) {
               String var9 = this.getCarrierDeprecated();
               int var10 = CodedOutputStreamMicro.computeStringSize(8, var9);
               var1 += var10;
            }

            if(this.hasSignatureHash()) {
               Purchase.SignatureHash var11 = this.getSignatureHash();
               int var12 = CodedOutputStreamMicro.computeMessageSize(9, var11);
               var1 += var12;
            }

            if(this.hasClientSoftwareVersion()) {
               int var13 = this.getClientSoftwareVersion();
               int var14 = CodedOutputStreamMicro.computeInt32Size(10, var13);
               var1 += var14;
            }

            if(this.hasDeviceClientId()) {
               String var15 = this.getDeviceClientId();
               int var16 = CodedOutputStreamMicro.computeStringSize(11, var15);
               var1 += var16;
            }

            if(this.hasAndroidId()) {
               long var17 = this.getAndroidId();
               int var19 = CodedOutputStreamMicro.computeFixed64Size(12, var17);
               var1 += var19;
            }

            if(this.hasSafesearchLevel()) {
               int var20 = this.getSafesearchLevel();
               int var21 = CodedOutputStreamMicro.computeInt32Size(13, var20);
               var1 += var21;
            }

            this.cachedSize = var1;
            return var1;
         }

         public Purchase.SignatureHash getSignatureHash() {
            return this.signatureHash_;
         }

         public long getStableDeviceId() {
            return this.stableDeviceId_;
         }

         public boolean hasAndroidId() {
            return this.hasAndroidId;
         }

         public boolean hasCarrierDeprecated() {
            return this.hasCarrierDeprecated;
         }

         public boolean hasClientSoftwareVersion() {
            return this.hasClientSoftwareVersion;
         }

         public boolean hasDeviceClientId() {
            return this.hasDeviceClientId;
         }

         public boolean hasMakerDeprecated() {
            return this.hasMakerDeprecated;
         }

         public boolean hasModelDeprecated() {
            return this.hasModelDeprecated;
         }

         public boolean hasSafesearchLevel() {
            return this.hasSafesearchLevel;
         }

         public boolean hasSignatureHash() {
            return this.hasSignatureHash;
         }

         public boolean hasStableDeviceId() {
            return this.hasStableDeviceId;
         }

         public final boolean isInitialized() {
            boolean var1;
            if(!this.hasStableDeviceId) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }

         public Purchase.PurchasePermissionRequest.Device mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 41:
                  long var3 = var1.readFixed64();
                  this.setStableDeviceId(var3);
                  break;
               case 50:
                  String var6 = var1.readString();
                  this.setModelDeprecated(var6);
                  break;
               case 58:
                  String var8 = var1.readString();
                  this.setMakerDeprecated(var8);
                  break;
               case 66:
                  String var10 = var1.readString();
                  this.setCarrierDeprecated(var10);
                  break;
               case 74:
                  Purchase.SignatureHash var12 = new Purchase.SignatureHash();
                  var1.readMessage(var12);
                  this.setSignatureHash(var12);
                  break;
               case 80:
                  int var14 = var1.readInt32();
                  this.setClientSoftwareVersion(var14);
                  break;
               case 90:
                  String var16 = var1.readString();
                  this.setDeviceClientId(var16);
                  break;
               case 97:
                  long var18 = var1.readFixed64();
                  this.setAndroidId(var18);
                  break;
               case 104:
                  int var21 = var1.readInt32();
                  this.setSafesearchLevel(var21);
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

         public Purchase.PurchasePermissionRequest.Device parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new Purchase.PurchasePermissionRequest.Device()).mergeFrom(var1);
         }

         public Purchase.PurchasePermissionRequest.Device parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (Purchase.PurchasePermissionRequest.Device)((Purchase.PurchasePermissionRequest.Device)(new Purchase.PurchasePermissionRequest.Device()).mergeFrom(var1));
         }

         public Purchase.PurchasePermissionRequest.Device setAndroidId(long var1) {
            this.hasAndroidId = (boolean)1;
            this.androidId_ = var1;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device setCarrierDeprecated(String var1) {
            this.hasCarrierDeprecated = (boolean)1;
            this.carrierDeprecated_ = var1;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device setClientSoftwareVersion(int var1) {
            this.hasClientSoftwareVersion = (boolean)1;
            this.clientSoftwareVersion_ = var1;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device setDeviceClientId(String var1) {
            this.hasDeviceClientId = (boolean)1;
            this.deviceClientId_ = var1;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device setMakerDeprecated(String var1) {
            this.hasMakerDeprecated = (boolean)1;
            this.makerDeprecated_ = var1;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device setModelDeprecated(String var1) {
            this.hasModelDeprecated = (boolean)1;
            this.modelDeprecated_ = var1;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device setSafesearchLevel(int var1) {
            this.hasSafesearchLevel = (boolean)1;
            this.safesearchLevel_ = var1;
            return this;
         }

         public Purchase.PurchasePermissionRequest.Device setSignatureHash(Purchase.SignatureHash var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasSignatureHash = (boolean)1;
               this.signatureHash_ = var1;
               return this;
            }
         }

         public Purchase.PurchasePermissionRequest.Device setStableDeviceId(long var1) {
            this.hasStableDeviceId = (boolean)1;
            this.stableDeviceId_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasStableDeviceId()) {
               long var2 = this.getStableDeviceId();
               var1.writeFixed64(5, var2);
            }

            if(this.hasModelDeprecated()) {
               String var4 = this.getModelDeprecated();
               var1.writeString(6, var4);
            }

            if(this.hasMakerDeprecated()) {
               String var5 = this.getMakerDeprecated();
               var1.writeString(7, var5);
            }

            if(this.hasCarrierDeprecated()) {
               String var6 = this.getCarrierDeprecated();
               var1.writeString(8, var6);
            }

            if(this.hasSignatureHash()) {
               Purchase.SignatureHash var7 = this.getSignatureHash();
               var1.writeMessage(9, var7);
            }

            if(this.hasClientSoftwareVersion()) {
               int var8 = this.getClientSoftwareVersion();
               var1.writeInt32(10, var8);
            }

            if(this.hasDeviceClientId()) {
               String var9 = this.getDeviceClientId();
               var1.writeString(11, var9);
            }

            if(this.hasAndroidId()) {
               long var10 = this.getAndroidId();
               var1.writeFixed64(12, var10);
            }

            if(this.hasSafesearchLevel()) {
               int var12 = this.getSafesearchLevel();
               var1.writeInt32(13, var12);
            }
         }
      }
   }

   public static final class PurchaseNotificationRequest extends MessageMicro {

      public static final int ANDROID_ID_FIELD_NUMBER = 15;
      public static final int CHECKOUT_ORDER_ID_FIELD_NUMBER = 2;
      public static final int CONTEXT_FIELD_NUMBER = 1;
      public static final int DEVELOPER_PAYLOAD_FIELD_NUMBER = 14;
      public static final int DOCID_FIELD_NUMBER = 5;
      public static final int OFFER_TYPE_FIELD_NUMBER = 12;
      public static final int ORDER_TIMESTAMP_MILLIS_FIELD_NUMBER = 4;
      public static final int PRICE_FIELD_NUMBER = 11;
      public static final int PURCHASE_ID_FIELD_NUMBER = 8;
      public static final int SKIP_PURCHASE_HISTORY_FIELD_NUMBER = 16;
      public static final int STABLE_DEVICE_ID_FIELD_NUMBER = 3;
      public static final int STATE_FIELD_NUMBER = 13;
      private long androidId_ = 0L;
      private int cachedSize;
      private String checkoutOrderId_ = "";
      private RequestContext context_ = null;
      private String developerPayload_;
      private List<Common.Docid> docid_;
      private boolean hasAndroidId;
      private boolean hasCheckoutOrderId;
      private boolean hasContext;
      private boolean hasDeveloperPayload;
      private boolean hasOfferType;
      private boolean hasOrderTimestampMillis;
      private boolean hasPrice;
      private boolean hasPurchaseId;
      private boolean hasSkipPurchaseHistory;
      private boolean hasStableDeviceId;
      private boolean hasState;
      private int offerType_ = 1;
      private long orderTimestampMillis_ = 0L;
      private Common.Offer price_ = null;
      private String purchaseId_;
      private boolean skipPurchaseHistory_;
      private long stableDeviceId_ = 0L;
      private int state_;


      public PurchaseNotificationRequest() {
         List var1 = Collections.emptyList();
         this.docid_ = var1;
         this.purchaseId_ = "";
         this.state_ = 1;
         this.developerPayload_ = "";
         this.skipPurchaseHistory_ = (boolean)0;
         this.cachedSize = -1;
      }

      public static Purchase.PurchaseNotificationRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.PurchaseNotificationRequest()).mergeFrom(var0);
      }

      public static Purchase.PurchaseNotificationRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.PurchaseNotificationRequest)((Purchase.PurchaseNotificationRequest)(new Purchase.PurchaseNotificationRequest()).mergeFrom(var0));
      }

      public Purchase.PurchaseNotificationRequest addDocid(Common.Docid var1) {
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

      public final Purchase.PurchaseNotificationRequest clear() {
         Purchase.PurchaseNotificationRequest var1 = this.clearContext();
         Purchase.PurchaseNotificationRequest var2 = this.clearCheckoutOrderId();
         Purchase.PurchaseNotificationRequest var3 = this.clearOfferType();
         Purchase.PurchaseNotificationRequest var4 = this.clearStableDeviceId();
         Purchase.PurchaseNotificationRequest var5 = this.clearAndroidId();
         Purchase.PurchaseNotificationRequest var6 = this.clearPrice();
         Purchase.PurchaseNotificationRequest var7 = this.clearOrderTimestampMillis();
         Purchase.PurchaseNotificationRequest var8 = this.clearDocid();
         Purchase.PurchaseNotificationRequest var9 = this.clearPurchaseId();
         Purchase.PurchaseNotificationRequest var10 = this.clearState();
         Purchase.PurchaseNotificationRequest var11 = this.clearDeveloperPayload();
         Purchase.PurchaseNotificationRequest var12 = this.clearSkipPurchaseHistory();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearAndroidId() {
         this.hasAndroidId = (boolean)0;
         this.androidId_ = 0L;
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearCheckoutOrderId() {
         this.hasCheckoutOrderId = (boolean)0;
         this.checkoutOrderId_ = "";
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearContext() {
         this.hasContext = (boolean)0;
         this.context_ = null;
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearDeveloperPayload() {
         this.hasDeveloperPayload = (boolean)0;
         this.developerPayload_ = "";
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearDocid() {
         List var1 = Collections.emptyList();
         this.docid_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearOfferType() {
         this.hasOfferType = (boolean)0;
         this.offerType_ = 1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearOrderTimestampMillis() {
         this.hasOrderTimestampMillis = (boolean)0;
         this.orderTimestampMillis_ = 0L;
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearPrice() {
         this.hasPrice = (boolean)0;
         this.price_ = null;
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearPurchaseId() {
         this.hasPurchaseId = (boolean)0;
         this.purchaseId_ = "";
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearSkipPurchaseHistory() {
         this.hasSkipPurchaseHistory = (boolean)0;
         this.skipPurchaseHistory_ = (boolean)0;
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearStableDeviceId() {
         this.hasStableDeviceId = (boolean)0;
         this.stableDeviceId_ = 0L;
         return this;
      }

      public Purchase.PurchaseNotificationRequest clearState() {
         this.hasState = (boolean)0;
         this.state_ = 1;
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

      public String getCheckoutOrderId() {
         return this.checkoutOrderId_;
      }

      public RequestContext getContext() {
         return this.context_;
      }

      public String getDeveloperPayload() {
         return this.developerPayload_;
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

      public int getOfferType() {
         return this.offerType_;
      }

      public long getOrderTimestampMillis() {
         return this.orderTimestampMillis_;
      }

      public Common.Offer getPrice() {
         return this.price_;
      }

      public String getPurchaseId() {
         return this.purchaseId_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasCheckoutOrderId()) {
            String var4 = this.getCheckoutOrderId();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasStableDeviceId()) {
            long var6 = this.getStableDeviceId();
            int var8 = CodedOutputStreamMicro.computeFixed64Size(3, var6);
            var1 += var8;
         }

         if(this.hasOrderTimestampMillis()) {
            long var9 = this.getOrderTimestampMillis();
            int var11 = CodedOutputStreamMicro.computeInt64Size(4, var9);
            var1 += var11;
         }

         int var14;
         for(Iterator var12 = this.getDocidList().iterator(); var12.hasNext(); var1 += var14) {
            Common.Docid var13 = (Common.Docid)var12.next();
            var14 = CodedOutputStreamMicro.computeMessageSize(5, var13);
         }

         if(this.hasPurchaseId()) {
            String var15 = this.getPurchaseId();
            int var16 = CodedOutputStreamMicro.computeStringSize(8, var15);
            var1 += var16;
         }

         if(this.hasPrice()) {
            Common.Offer var17 = this.getPrice();
            int var18 = CodedOutputStreamMicro.computeMessageSize(11, var17);
            var1 += var18;
         }

         if(this.hasOfferType()) {
            int var19 = this.getOfferType();
            int var20 = CodedOutputStreamMicro.computeInt32Size(12, var19);
            var1 += var20;
         }

         if(this.hasState()) {
            int var21 = this.getState();
            int var22 = CodedOutputStreamMicro.computeInt32Size(13, var21);
            var1 += var22;
         }

         if(this.hasDeveloperPayload()) {
            String var23 = this.getDeveloperPayload();
            int var24 = CodedOutputStreamMicro.computeStringSize(14, var23);
            var1 += var24;
         }

         if(this.hasAndroidId()) {
            long var25 = this.getAndroidId();
            int var27 = CodedOutputStreamMicro.computeFixed64Size(15, var25);
            var1 += var27;
         }

         if(this.hasSkipPurchaseHistory()) {
            boolean var28 = this.getSkipPurchaseHistory();
            int var29 = CodedOutputStreamMicro.computeBoolSize(16, var28);
            var1 += var29;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean getSkipPurchaseHistory() {
         return this.skipPurchaseHistory_;
      }

      public long getStableDeviceId() {
         return this.stableDeviceId_;
      }

      public int getState() {
         return this.state_;
      }

      public boolean hasAndroidId() {
         return this.hasAndroidId;
      }

      public boolean hasCheckoutOrderId() {
         return this.hasCheckoutOrderId;
      }

      public boolean hasContext() {
         return this.hasContext;
      }

      public boolean hasDeveloperPayload() {
         return this.hasDeveloperPayload;
      }

      public boolean hasOfferType() {
         return this.hasOfferType;
      }

      public boolean hasOrderTimestampMillis() {
         return this.hasOrderTimestampMillis;
      }

      public boolean hasPrice() {
         return this.hasPrice;
      }

      public boolean hasPurchaseId() {
         return this.hasPurchaseId;
      }

      public boolean hasSkipPurchaseHistory() {
         return this.hasSkipPurchaseHistory;
      }

      public boolean hasStableDeviceId() {
         return this.hasStableDeviceId;
      }

      public boolean hasState() {
         return this.hasState;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasContext && this.getContext().isInitialized() && (!this.hasPrice() || this.getPrice().isInitialized())) {
            Iterator var2 = this.getDocidList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((Common.Docid)var2.next()).isInitialized());
         }

         return var1;
      }

      public Purchase.PurchaseNotificationRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               RequestContext var3 = new RequestContext();
               var1.readMessage(var3);
               this.setContext(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setCheckoutOrderId(var5);
               break;
            case 25:
               long var7 = var1.readFixed64();
               this.setStableDeviceId(var7);
               break;
            case 32:
               long var10 = var1.readInt64();
               this.setOrderTimestampMillis(var10);
               break;
            case 42:
               Common.Docid var13 = new Common.Docid();
               var1.readMessage(var13);
               this.addDocid(var13);
               break;
            case 66:
               String var15 = var1.readString();
               this.setPurchaseId(var15);
               break;
            case 90:
               Common.Offer var17 = new Common.Offer();
               var1.readMessage(var17);
               this.setPrice(var17);
               break;
            case 96:
               int var19 = var1.readInt32();
               this.setOfferType(var19);
               break;
            case 104:
               int var21 = var1.readInt32();
               this.setState(var21);
               break;
            case 114:
               String var23 = var1.readString();
               this.setDeveloperPayload(var23);
               break;
            case 121:
               long var25 = var1.readFixed64();
               this.setAndroidId(var25);
               break;
            case 128:
               boolean var28 = var1.readBool();
               this.setSkipPurchaseHistory(var28);
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

      public Purchase.PurchaseNotificationRequest setAndroidId(long var1) {
         this.hasAndroidId = (boolean)1;
         this.androidId_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest setCheckoutOrderId(String var1) {
         this.hasCheckoutOrderId = (boolean)1;
         this.checkoutOrderId_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest setContext(RequestContext var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasContext = (boolean)1;
            this.context_ = var1;
            return this;
         }
      }

      public Purchase.PurchaseNotificationRequest setDeveloperPayload(String var1) {
         this.hasDeveloperPayload = (boolean)1;
         this.developerPayload_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest setDocid(int var1, Common.Docid var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.docid_.set(var1, var2);
            return this;
         }
      }

      public Purchase.PurchaseNotificationRequest setOfferType(int var1) {
         this.hasOfferType = (boolean)1;
         this.offerType_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest setOrderTimestampMillis(long var1) {
         this.hasOrderTimestampMillis = (boolean)1;
         this.orderTimestampMillis_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest setPrice(Common.Offer var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPrice = (boolean)1;
            this.price_ = var1;
            return this;
         }
      }

      public Purchase.PurchaseNotificationRequest setPurchaseId(String var1) {
         this.hasPurchaseId = (boolean)1;
         this.purchaseId_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest setSkipPurchaseHistory(boolean var1) {
         this.hasSkipPurchaseHistory = (boolean)1;
         this.skipPurchaseHistory_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest setStableDeviceId(long var1) {
         this.hasStableDeviceId = (boolean)1;
         this.stableDeviceId_ = var1;
         return this;
      }

      public Purchase.PurchaseNotificationRequest setState(int var1) {
         this.hasState = (boolean)1;
         this.state_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            var1.writeMessage(1, var2);
         }

         if(this.hasCheckoutOrderId()) {
            String var3 = this.getCheckoutOrderId();
            var1.writeString(2, var3);
         }

         if(this.hasStableDeviceId()) {
            long var4 = this.getStableDeviceId();
            var1.writeFixed64(3, var4);
         }

         if(this.hasOrderTimestampMillis()) {
            long var6 = this.getOrderTimestampMillis();
            var1.writeInt64(4, var6);
         }

         Iterator var8 = this.getDocidList().iterator();

         while(var8.hasNext()) {
            Common.Docid var9 = (Common.Docid)var8.next();
            var1.writeMessage(5, var9);
         }

         if(this.hasPurchaseId()) {
            String var10 = this.getPurchaseId();
            var1.writeString(8, var10);
         }

         if(this.hasPrice()) {
            Common.Offer var11 = this.getPrice();
            var1.writeMessage(11, var11);
         }

         if(this.hasOfferType()) {
            int var12 = this.getOfferType();
            var1.writeInt32(12, var12);
         }

         if(this.hasState()) {
            int var13 = this.getState();
            var1.writeInt32(13, var13);
         }

         if(this.hasDeveloperPayload()) {
            String var14 = this.getDeveloperPayload();
            var1.writeString(14, var14);
         }

         if(this.hasAndroidId()) {
            long var15 = this.getAndroidId();
            var1.writeFixed64(15, var15);
         }

         if(this.hasSkipPurchaseHistory()) {
            boolean var17 = this.getSkipPurchaseHistory();
            var1.writeBool(16, var17);
         }
      }
   }

   public static final class CreateCartRequest extends MessageMicro {

      public static final int CONTEXT_FIELD_NUMBER = 1;
      public static final int DOCID_FIELD_NUMBER = 2;
      public static final int OFFER_TYPE_FIELD_NUMBER = 4;
      public static final int REFUNDABLE_FIELD_NUMBER = 3;
      public static final int STABLE_DEVICE_ID_FIELD_NUMBER = 5;
      private int cachedSize = -1;
      private RequestContext context_ = null;
      private Common.Docid docid_ = null;
      private boolean hasContext;
      private boolean hasDocid;
      private boolean hasOfferType;
      private boolean hasRefundable;
      private boolean hasStableDeviceId;
      private int offerType_ = 1;
      private boolean refundable_ = 0;
      private long stableDeviceId_ = 0L;


      public CreateCartRequest() {}

      public static Purchase.CreateCartRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.CreateCartRequest()).mergeFrom(var0);
      }

      public static Purchase.CreateCartRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.CreateCartRequest)((Purchase.CreateCartRequest)(new Purchase.CreateCartRequest()).mergeFrom(var0));
      }

      public final Purchase.CreateCartRequest clear() {
         Purchase.CreateCartRequest var1 = this.clearContext();
         Purchase.CreateCartRequest var2 = this.clearDocid();
         Purchase.CreateCartRequest var3 = this.clearRefundable();
         Purchase.CreateCartRequest var4 = this.clearOfferType();
         Purchase.CreateCartRequest var5 = this.clearStableDeviceId();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.CreateCartRequest clearContext() {
         this.hasContext = (boolean)0;
         this.context_ = null;
         return this;
      }

      public Purchase.CreateCartRequest clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = null;
         return this;
      }

      public Purchase.CreateCartRequest clearOfferType() {
         this.hasOfferType = (boolean)0;
         this.offerType_ = 1;
         return this;
      }

      public Purchase.CreateCartRequest clearRefundable() {
         this.hasRefundable = (boolean)0;
         this.refundable_ = (boolean)0;
         return this;
      }

      public Purchase.CreateCartRequest clearStableDeviceId() {
         this.hasStableDeviceId = (boolean)0;
         this.stableDeviceId_ = 0L;
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

      public Common.Docid getDocid() {
         return this.docid_;
      }

      public int getOfferType() {
         return this.offerType_;
      }

      public boolean getRefundable() {
         return this.refundable_;
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

         if(this.hasRefundable()) {
            boolean var6 = this.getRefundable();
            int var7 = CodedOutputStreamMicro.computeBoolSize(3, var6);
            var1 += var7;
         }

         if(this.hasOfferType()) {
            int var8 = this.getOfferType();
            int var9 = CodedOutputStreamMicro.computeInt32Size(4, var8);
            var1 += var9;
         }

         if(this.hasStableDeviceId()) {
            long var10 = this.getStableDeviceId();
            int var12 = CodedOutputStreamMicro.computeFixed64Size(5, var10);
            var1 += var12;
         }

         this.cachedSize = var1;
         return var1;
      }

      public long getStableDeviceId() {
         return this.stableDeviceId_;
      }

      public boolean hasContext() {
         return this.hasContext;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public boolean hasOfferType() {
         return this.hasOfferType;
      }

      public boolean hasRefundable() {
         return this.hasRefundable;
      }

      public boolean hasStableDeviceId() {
         return this.hasStableDeviceId;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasContext && this.hasDocid && this.getContext().isInitialized() && this.getDocid().isInitialized()) {
            var1 = true;
         }

         return var1;
      }

      public Purchase.CreateCartRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
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
            case 24:
               boolean var7 = var1.readBool();
               this.setRefundable(var7);
               break;
            case 32:
               int var9 = var1.readInt32();
               this.setOfferType(var9);
               break;
            case 41:
               long var11 = var1.readFixed64();
               this.setStableDeviceId(var11);
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

      public Purchase.CreateCartRequest setContext(RequestContext var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasContext = (boolean)1;
            this.context_ = var1;
            return this;
         }
      }

      public Purchase.CreateCartRequest setDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDocid = (boolean)1;
            this.docid_ = var1;
            return this;
         }
      }

      public Purchase.CreateCartRequest setOfferType(int var1) {
         this.hasOfferType = (boolean)1;
         this.offerType_ = var1;
         return this;
      }

      public Purchase.CreateCartRequest setRefundable(boolean var1) {
         this.hasRefundable = (boolean)1;
         this.refundable_ = var1;
         return this;
      }

      public Purchase.CreateCartRequest setStableDeviceId(long var1) {
         this.hasStableDeviceId = (boolean)1;
         this.stableDeviceId_ = var1;
         return this;
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

         if(this.hasRefundable()) {
            boolean var4 = this.getRefundable();
            var1.writeBool(3, var4);
         }

         if(this.hasOfferType()) {
            int var5 = this.getOfferType();
            var1.writeInt32(4, var5);
         }

         if(this.hasStableDeviceId()) {
            long var6 = this.getStableDeviceId();
            var1.writeFixed64(5, var6);
         }
      }
   }

   public static final class AdjustCartResponse extends MessageMicro {

      public static final int BACKEND_ERROR = 2;
      public static final int DEBUG_INFO_FIELD_NUMBER = 2;
      public static final int INVALID_DOCID = 1;
      public static final int OK = 0;
      public static final int STATUS_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private DebugInfo debugInfo_ = null;
      private boolean hasDebugInfo;
      private boolean hasStatus;
      private int status_ = 0;


      public AdjustCartResponse() {}

      public static Purchase.AdjustCartResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.AdjustCartResponse()).mergeFrom(var0);
      }

      public static Purchase.AdjustCartResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.AdjustCartResponse)((Purchase.AdjustCartResponse)(new Purchase.AdjustCartResponse()).mergeFrom(var0));
      }

      public final Purchase.AdjustCartResponse clear() {
         Purchase.AdjustCartResponse var1 = this.clearStatus();
         Purchase.AdjustCartResponse var2 = this.clearDebugInfo();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.AdjustCartResponse clearDebugInfo() {
         this.hasDebugInfo = (boolean)0;
         this.debugInfo_ = null;
         return this;
      }

      public Purchase.AdjustCartResponse clearStatus() {
         this.hasStatus = (boolean)0;
         this.status_ = 0;
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

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasStatus()) {
            int var2 = this.getStatus();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasDebugInfo()) {
            DebugInfo var4 = this.getDebugInfo();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getStatus() {
         return this.status_;
      }

      public boolean hasDebugInfo() {
         return this.hasDebugInfo;
      }

      public boolean hasStatus() {
         return this.hasStatus;
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

      public Purchase.AdjustCartResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setStatus(var3);
               break;
            case 18:
               DebugInfo var5 = new DebugInfo();
               var1.readMessage(var5);
               this.setDebugInfo(var5);
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

      public Purchase.AdjustCartResponse setDebugInfo(DebugInfo var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDebugInfo = (boolean)1;
            this.debugInfo_ = var1;
            return this;
         }
      }

      public Purchase.AdjustCartResponse setStatus(int var1) {
         this.hasStatus = (boolean)1;
         this.status_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasStatus()) {
            int var2 = this.getStatus();
            var1.writeInt32(1, var2);
         }

         if(this.hasDebugInfo()) {
            DebugInfo var3 = this.getDebugInfo();
            var1.writeMessage(2, var3);
         }
      }
   }

   public static final class SignatureHash extends MessageMicro {

      public static final int HASH_FIELD_NUMBER = 3;
      public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
      public static final int VERSION_CODE_FIELD_NUMBER = 2;
      private int cachedSize;
      private boolean hasHash;
      private boolean hasPackageName;
      private boolean hasVersionCode;
      private ByteStringMicro hash_;
      private String packageName_ = "";
      private int versionCode_ = 0;


      public SignatureHash() {
         ByteStringMicro var1 = ByteStringMicro.EMPTY;
         this.hash_ = var1;
         this.cachedSize = -1;
      }

      public static Purchase.SignatureHash parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.SignatureHash()).mergeFrom(var0);
      }

      public static Purchase.SignatureHash parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.SignatureHash)((Purchase.SignatureHash)(new Purchase.SignatureHash()).mergeFrom(var0));
      }

      public final Purchase.SignatureHash clear() {
         Purchase.SignatureHash var1 = this.clearPackageName();
         Purchase.SignatureHash var2 = this.clearVersionCode();
         Purchase.SignatureHash var3 = this.clearHash();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.SignatureHash clearHash() {
         this.hasHash = (boolean)0;
         ByteStringMicro var1 = ByteStringMicro.EMPTY;
         this.hash_ = var1;
         return this;
      }

      public Purchase.SignatureHash clearPackageName() {
         this.hasPackageName = (boolean)0;
         this.packageName_ = "";
         return this;
      }

      public Purchase.SignatureHash clearVersionCode() {
         this.hasVersionCode = (boolean)0;
         this.versionCode_ = 0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public ByteStringMicro getHash() {
         return this.hash_;
      }

      public String getPackageName() {
         return this.packageName_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasPackageName()) {
            String var2 = this.getPackageName();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasVersionCode()) {
            int var4 = this.getVersionCode();
            int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
            var1 += var5;
         }

         if(this.hasHash()) {
            ByteStringMicro var6 = this.getHash();
            int var7 = CodedOutputStreamMicro.computeBytesSize(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getVersionCode() {
         return this.versionCode_;
      }

      public boolean hasHash() {
         return this.hasHash;
      }

      public boolean hasPackageName() {
         return this.hasPackageName;
      }

      public boolean hasVersionCode() {
         return this.hasVersionCode;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Purchase.SignatureHash mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setPackageName(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setVersionCode(var5);
               break;
            case 26:
               ByteStringMicro var7 = var1.readBytes();
               this.setHash(var7);
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

      public Purchase.SignatureHash setHash(ByteStringMicro var1) {
         this.hasHash = (boolean)1;
         this.hash_ = var1;
         return this;
      }

      public Purchase.SignatureHash setPackageName(String var1) {
         this.hasPackageName = (boolean)1;
         this.packageName_ = var1;
         return this;
      }

      public Purchase.SignatureHash setVersionCode(int var1) {
         this.hasVersionCode = (boolean)1;
         this.versionCode_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasPackageName()) {
            String var2 = this.getPackageName();
            var1.writeString(1, var2);
         }

         if(this.hasVersionCode()) {
            int var3 = this.getVersionCode();
            var1.writeInt32(2, var3);
         }

         if(this.hasHash()) {
            ByteStringMicro var4 = this.getHash();
            var1.writeBytes(3, var4);
         }
      }
   }

   public static final class RefundEligibilityResponse extends MessageMicro {

      public static final int ELIGIBILITY_FIELD_NUMBER = 1;
      private int cachedSize;
      private List<Purchase.RefundEligibilityResponse.Eligibility> eligibility_;


      public RefundEligibilityResponse() {
         List var1 = Collections.emptyList();
         this.eligibility_ = var1;
         this.cachedSize = -1;
      }

      public static Purchase.RefundEligibilityResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.RefundEligibilityResponse()).mergeFrom(var0);
      }

      public static Purchase.RefundEligibilityResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.RefundEligibilityResponse)((Purchase.RefundEligibilityResponse)(new Purchase.RefundEligibilityResponse()).mergeFrom(var0));
      }

      public Purchase.RefundEligibilityResponse addEligibility(Purchase.RefundEligibilityResponse.Eligibility var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.eligibility_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.eligibility_ = var2;
            }

            this.eligibility_.add(var1);
            return this;
         }
      }

      public final Purchase.RefundEligibilityResponse clear() {
         Purchase.RefundEligibilityResponse var1 = this.clearEligibility();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.RefundEligibilityResponse clearEligibility() {
         List var1 = Collections.emptyList();
         this.eligibility_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Purchase.RefundEligibilityResponse.Eligibility getEligibility(int var1) {
         return (Purchase.RefundEligibilityResponse.Eligibility)this.eligibility_.get(var1);
      }

      public int getEligibilityCount() {
         return this.eligibility_.size();
      }

      public List<Purchase.RefundEligibilityResponse.Eligibility> getEligibilityList() {
         return this.eligibility_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getEligibilityList().iterator(); var2.hasNext(); var1 += var4) {
            Purchase.RefundEligibilityResponse.Eligibility var3 = (Purchase.RefundEligibilityResponse.Eligibility)var2.next();
            var4 = CodedOutputStreamMicro.computeGroupSize(1, var3);
         }

         this.cachedSize = var1;
         return var1;
      }

      public final boolean isInitialized() {
         Iterator var1 = this.getEligibilityList().iterator();

         boolean var2;
         while(true) {
            if(var1.hasNext()) {
               if(((Purchase.RefundEligibilityResponse.Eligibility)var1.next()).isInitialized()) {
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

      public Purchase.RefundEligibilityResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 11:
               Purchase.RefundEligibilityResponse.Eligibility var3 = new Purchase.RefundEligibilityResponse.Eligibility();
               var1.readGroup(var3, 1);
               this.addEligibility(var3);
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

      public Purchase.RefundEligibilityResponse setEligibility(int var1, Purchase.RefundEligibilityResponse.Eligibility var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.eligibility_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getEligibilityList().iterator();

         while(var2.hasNext()) {
            Purchase.RefundEligibilityResponse.Eligibility var3 = (Purchase.RefundEligibilityResponse.Eligibility)var2.next();
            var1.writeGroup(1, var3);
         }

      }

      public static final class Eligibility extends MessageMicro {

         public static final int CAN_REFUND_FIELD_NUMBER = 3;
         public static final int DOCID_FIELD_NUMBER = 2;
         public static final int REFUND_EXPIRATION_TIME_FIELD_NUMBER = 4;
         private int cachedSize = -1;
         private boolean canRefund_ = 0;
         private Common.Docid docid_ = null;
         private boolean hasCanRefund;
         private boolean hasDocid;
         private boolean hasRefundExpirationTime;
         private long refundExpirationTime_ = 0L;


         public Eligibility() {}

         public final Purchase.RefundEligibilityResponse.Eligibility clear() {
            Purchase.RefundEligibilityResponse.Eligibility var1 = this.clearDocid();
            Purchase.RefundEligibilityResponse.Eligibility var2 = this.clearCanRefund();
            Purchase.RefundEligibilityResponse.Eligibility var3 = this.clearRefundExpirationTime();
            this.cachedSize = -1;
            return this;
         }

         public Purchase.RefundEligibilityResponse.Eligibility clearCanRefund() {
            this.hasCanRefund = (boolean)0;
            this.canRefund_ = (boolean)0;
            return this;
         }

         public Purchase.RefundEligibilityResponse.Eligibility clearDocid() {
            this.hasDocid = (boolean)0;
            this.docid_ = null;
            return this;
         }

         public Purchase.RefundEligibilityResponse.Eligibility clearRefundExpirationTime() {
            this.hasRefundExpirationTime = (boolean)0;
            this.refundExpirationTime_ = 0L;
            return this;
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

         public long getRefundExpirationTime() {
            return this.refundExpirationTime_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasDocid()) {
               Common.Docid var2 = this.getDocid();
               int var3 = CodedOutputStreamMicro.computeMessageSize(2, var2);
               var1 = 0 + var3;
            }

            if(this.hasCanRefund()) {
               boolean var4 = this.getCanRefund();
               int var5 = CodedOutputStreamMicro.computeBoolSize(3, var4);
               var1 += var5;
            }

            if(this.hasRefundExpirationTime()) {
               long var6 = this.getRefundExpirationTime();
               int var8 = CodedOutputStreamMicro.computeUInt64Size(4, var6);
               var1 += var8;
            }

            this.cachedSize = var1;
            return var1;
         }

         public boolean hasCanRefund() {
            return this.hasCanRefund;
         }

         public boolean hasDocid() {
            return this.hasDocid;
         }

         public boolean hasRefundExpirationTime() {
            return this.hasRefundExpirationTime;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if(this.hasDocid && this.getDocid().isInitialized()) {
               var1 = true;
            }

            return var1;
         }

         public Purchase.RefundEligibilityResponse.Eligibility mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 18:
                  Common.Docid var3 = new Common.Docid();
                  var1.readMessage(var3);
                  this.setDocid(var3);
                  break;
               case 24:
                  boolean var5 = var1.readBool();
                  this.setCanRefund(var5);
                  break;
               case 32:
                  long var7 = var1.readUInt64();
                  this.setRefundExpirationTime(var7);
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

         public Purchase.RefundEligibilityResponse.Eligibility parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new Purchase.RefundEligibilityResponse.Eligibility()).mergeFrom(var1);
         }

         public Purchase.RefundEligibilityResponse.Eligibility parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (Purchase.RefundEligibilityResponse.Eligibility)((Purchase.RefundEligibilityResponse.Eligibility)(new Purchase.RefundEligibilityResponse.Eligibility()).mergeFrom(var1));
         }

         public Purchase.RefundEligibilityResponse.Eligibility setCanRefund(boolean var1) {
            this.hasCanRefund = (boolean)1;
            this.canRefund_ = var1;
            return this;
         }

         public Purchase.RefundEligibilityResponse.Eligibility setDocid(Common.Docid var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               this.hasDocid = (boolean)1;
               this.docid_ = var1;
               return this;
            }
         }

         public Purchase.RefundEligibilityResponse.Eligibility setRefundExpirationTime(long var1) {
            this.hasRefundExpirationTime = (boolean)1;
            this.refundExpirationTime_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasDocid()) {
               Common.Docid var2 = this.getDocid();
               var1.writeMessage(2, var2);
            }

            if(this.hasCanRefund()) {
               boolean var3 = this.getCanRefund();
               var1.writeBool(3, var3);
            }

            if(this.hasRefundExpirationTime()) {
               long var4 = this.getRefundExpirationTime();
               var1.writeUInt64(4, var4);
            }
         }
      }
   }

   public static final class PurchaseUpdateRequest extends MessageMicro {

      public static final int CONTEXT_FIELD_NUMBER = 1;
      public static final int PURCHASE_ID_FIELD_NUMBER = 2;
      public static final int STATE_FIELD_NUMBER = 4;
      private int cachedSize = -1;
      private RequestContext context_ = null;
      private boolean hasContext;
      private boolean hasPurchaseId;
      private boolean hasState;
      private String purchaseId_ = "";
      private int state_ = 1;


      public PurchaseUpdateRequest() {}

      public static Purchase.PurchaseUpdateRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.PurchaseUpdateRequest()).mergeFrom(var0);
      }

      public static Purchase.PurchaseUpdateRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.PurchaseUpdateRequest)((Purchase.PurchaseUpdateRequest)(new Purchase.PurchaseUpdateRequest()).mergeFrom(var0));
      }

      public final Purchase.PurchaseUpdateRequest clear() {
         Purchase.PurchaseUpdateRequest var1 = this.clearContext();
         Purchase.PurchaseUpdateRequest var2 = this.clearPurchaseId();
         Purchase.PurchaseUpdateRequest var3 = this.clearState();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.PurchaseUpdateRequest clearContext() {
         this.hasContext = (boolean)0;
         this.context_ = null;
         return this;
      }

      public Purchase.PurchaseUpdateRequest clearPurchaseId() {
         this.hasPurchaseId = (boolean)0;
         this.purchaseId_ = "";
         return this;
      }

      public Purchase.PurchaseUpdateRequest clearState() {
         this.hasState = (boolean)0;
         this.state_ = 1;
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

      public String getPurchaseId() {
         return this.purchaseId_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasPurchaseId()) {
            String var4 = this.getPurchaseId();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasState()) {
            int var6 = this.getState();
            int var7 = CodedOutputStreamMicro.computeInt32Size(4, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getState() {
         return this.state_;
      }

      public boolean hasContext() {
         return this.hasContext;
      }

      public boolean hasPurchaseId() {
         return this.hasPurchaseId;
      }

      public boolean hasState() {
         return this.hasState;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasContext && this.hasPurchaseId && this.hasState && this.getContext().isInitialized()) {
            var1 = true;
         }

         return var1;
      }

      public Purchase.PurchaseUpdateRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               RequestContext var3 = new RequestContext();
               var1.readMessage(var3);
               this.setContext(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setPurchaseId(var5);
               break;
            case 32:
               int var7 = var1.readInt32();
               this.setState(var7);
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

      public Purchase.PurchaseUpdateRequest setContext(RequestContext var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasContext = (boolean)1;
            this.context_ = var1;
            return this;
         }
      }

      public Purchase.PurchaseUpdateRequest setPurchaseId(String var1) {
         this.hasPurchaseId = (boolean)1;
         this.purchaseId_ = var1;
         return this;
      }

      public Purchase.PurchaseUpdateRequest setState(int var1) {
         this.hasState = (boolean)1;
         this.state_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasContext()) {
            RequestContext var2 = this.getContext();
            var1.writeMessage(1, var2);
         }

         if(this.hasPurchaseId()) {
            String var3 = this.getPurchaseId();
            var1.writeString(2, var3);
         }

         if(this.hasState()) {
            int var4 = this.getState();
            var1.writeInt32(4, var4);
         }
      }
   }

   public static final class PurchasePermissionResponse extends MessageMicro {

      public static final int CACHE_FIELD_NUMBER = 5;
      public static final int DEBUG_INFO_FIELD_NUMBER = 10;
      public static final int DOCID_FIELD_NUMBER = 1;
      public static final int FULFILLMENT_STATE_FIELD_NUMBER = 7;
      public static final int INCOMPATIBLE_CARRIER = 6;
      public static final int INCOMPATIBLE_CLIENT_APP = 12;
      public static final int INCOMPATIBLE_CONTENT_RATING = 10;
      public static final int INCOMPATIBLE_COUNTRY = 7;
      public static final int INCOMPATIBLE_DEVICE = 5;
      public static final int INCOMPATIBLE_GROUP = 8;
      public static final int INCOMPATIBLE_USER = 9;
      public static final int INSTALL_OK = 1;
      public static final int INVALID_DOCUMENT = 4;
      public static final int IS_INSTALLED = 3;
      public static final int MISSING_MARKET_DATA = 11;
      public static final int NEXT_PURCHASE_REFUNDABLE_FIELD_NUMBER = 8;
      public static final int PENDING_CHECKOUT_ORDER_ID_FIELD_NUMBER = 9;
      public static final int PERMISSION_FIELD_NUMBER = 2;
      public static final int UNKNOWN = 0;
      public static final int UPGRADE_OK = 2;
      private Cache.CacheData cache_;
      private int cachedSize;
      private DebugInfo debugInfo_;
      private Common.Docid docid_ = null;
      private int fulfillmentState_;
      private boolean hasCache;
      private boolean hasDebugInfo;
      private boolean hasDocid;
      private boolean hasFulfillmentState;
      private boolean hasNextPurchaseRefundable;
      private boolean hasPendingCheckoutOrderId;
      private boolean nextPurchaseRefundable_;
      private String pendingCheckoutOrderId_;
      private List<Purchase.PurchasePermissionResponse.Permission> permission_;


      public PurchasePermissionResponse() {
         List var1 = Collections.emptyList();
         this.permission_ = var1;
         this.cache_ = null;
         this.fulfillmentState_ = 1;
         this.nextPurchaseRefundable_ = (boolean)0;
         this.pendingCheckoutOrderId_ = "";
         this.debugInfo_ = null;
         this.cachedSize = -1;
      }

      public static Purchase.PurchasePermissionResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Purchase.PurchasePermissionResponse()).mergeFrom(var0);
      }

      public static Purchase.PurchasePermissionResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Purchase.PurchasePermissionResponse)((Purchase.PurchasePermissionResponse)(new Purchase.PurchasePermissionResponse()).mergeFrom(var0));
      }

      public Purchase.PurchasePermissionResponse addPermission(Purchase.PurchasePermissionResponse.Permission var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.permission_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.permission_ = var2;
            }

            this.permission_.add(var1);
            return this;
         }
      }

      public final Purchase.PurchasePermissionResponse clear() {
         Purchase.PurchasePermissionResponse var1 = this.clearDocid();
         Purchase.PurchasePermissionResponse var2 = this.clearPermission();
         Purchase.PurchasePermissionResponse var3 = this.clearCache();
         Purchase.PurchasePermissionResponse var4 = this.clearFulfillmentState();
         Purchase.PurchasePermissionResponse var5 = this.clearNextPurchaseRefundable();
         Purchase.PurchasePermissionResponse var6 = this.clearPendingCheckoutOrderId();
         Purchase.PurchasePermissionResponse var7 = this.clearDebugInfo();
         this.cachedSize = -1;
         return this;
      }

      public Purchase.PurchasePermissionResponse clearCache() {
         this.hasCache = (boolean)0;
         this.cache_ = null;
         return this;
      }

      public Purchase.PurchasePermissionResponse clearDebugInfo() {
         this.hasDebugInfo = (boolean)0;
         this.debugInfo_ = null;
         return this;
      }

      public Purchase.PurchasePermissionResponse clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = null;
         return this;
      }

      public Purchase.PurchasePermissionResponse clearFulfillmentState() {
         this.hasFulfillmentState = (boolean)0;
         this.fulfillmentState_ = 1;
         return this;
      }

      public Purchase.PurchasePermissionResponse clearNextPurchaseRefundable() {
         this.hasNextPurchaseRefundable = (boolean)0;
         this.nextPurchaseRefundable_ = (boolean)0;
         return this;
      }

      public Purchase.PurchasePermissionResponse clearPendingCheckoutOrderId() {
         this.hasPendingCheckoutOrderId = (boolean)0;
         this.pendingCheckoutOrderId_ = "";
         return this;
      }

      public Purchase.PurchasePermissionResponse clearPermission() {
         List var1 = Collections.emptyList();
         this.permission_ = var1;
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

      public Common.Docid getDocid() {
         return this.docid_;
      }

      public int getFulfillmentState() {
         return this.fulfillmentState_;
      }

      public boolean getNextPurchaseRefundable() {
         return this.nextPurchaseRefundable_;
      }

      public String getPendingCheckoutOrderId() {
         return this.pendingCheckoutOrderId_;
      }

      public Purchase.PurchasePermissionResponse.Permission getPermission(int var1) {
         return (Purchase.PurchasePermissionResponse.Permission)this.permission_.get(var1);
      }

      public int getPermissionCount() {
         return this.permission_.size();
      }

      public List<Purchase.PurchasePermissionResponse.Permission> getPermissionList() {
         return this.permission_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasDocid()) {
            Common.Docid var2 = this.getDocid();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         int var6;
         for(Iterator var4 = this.getPermissionList().iterator(); var4.hasNext(); var1 += var6) {
            Purchase.PurchasePermissionResponse.Permission var5 = (Purchase.PurchasePermissionResponse.Permission)var4.next();
            var6 = CodedOutputStreamMicro.computeGroupSize(2, var5);
         }

         if(this.hasCache()) {
            Cache.CacheData var7 = this.getCache();
            int var8 = CodedOutputStreamMicro.computeMessageSize(5, var7);
            var1 += var8;
         }

         if(this.hasFulfillmentState()) {
            int var9 = this.getFulfillmentState();
            int var10 = CodedOutputStreamMicro.computeInt32Size(7, var9);
            var1 += var10;
         }

         if(this.hasNextPurchaseRefundable()) {
            boolean var11 = this.getNextPurchaseRefundable();
            int var12 = CodedOutputStreamMicro.computeBoolSize(8, var11);
            var1 += var12;
         }

         if(this.hasPendingCheckoutOrderId()) {
            String var13 = this.getPendingCheckoutOrderId();
            int var14 = CodedOutputStreamMicro.computeStringSize(9, var13);
            var1 += var14;
         }

         if(this.hasDebugInfo()) {
            DebugInfo var15 = this.getDebugInfo();
            int var16 = CodedOutputStreamMicro.computeMessageSize(10, var15);
            var1 += var16;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasCache() {
         return this.hasCache;
      }

      public boolean hasDebugInfo() {
         return this.hasDebugInfo;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public boolean hasFulfillmentState() {
         return this.hasFulfillmentState;
      }

      public boolean hasNextPurchaseRefundable() {
         return this.hasNextPurchaseRefundable;
      }

      public boolean hasPendingCheckoutOrderId() {
         return this.hasPendingCheckoutOrderId;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasDocid && this.getDocid().isInitialized()) {
            Iterator var2 = this.getPermissionList().iterator();

            do {
               if(!var2.hasNext()) {
                  if(!this.hasDebugInfo() || this.getDebugInfo().isInitialized()) {
                     var1 = true;
                  }
                  break;
               }
            } while(((Purchase.PurchasePermissionResponse.Permission)var2.next()).isInitialized());
         }

         return var1;
      }

      public Purchase.PurchasePermissionResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Common.Docid var3 = new Common.Docid();
               var1.readMessage(var3);
               this.setDocid(var3);
               break;
            case 19:
               Purchase.PurchasePermissionResponse.Permission var5 = new Purchase.PurchasePermissionResponse.Permission();
               var1.readGroup(var5, 2);
               this.addPermission(var5);
               break;
            case 42:
               Cache.CacheData var7 = new Cache.CacheData();
               var1.readMessage(var7);
               this.setCache(var7);
               break;
            case 56:
               int var9 = var1.readInt32();
               this.setFulfillmentState(var9);
               break;
            case 64:
               boolean var11 = var1.readBool();
               this.setNextPurchaseRefundable(var11);
               break;
            case 74:
               String var13 = var1.readString();
               this.setPendingCheckoutOrderId(var13);
               break;
            case 82:
               DebugInfo var15 = new DebugInfo();
               var1.readMessage(var15);
               this.setDebugInfo(var15);
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

      public Purchase.PurchasePermissionResponse setCache(Cache.CacheData var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasCache = (boolean)1;
            this.cache_ = var1;
            return this;
         }
      }

      public Purchase.PurchasePermissionResponse setDebugInfo(DebugInfo var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDebugInfo = (boolean)1;
            this.debugInfo_ = var1;
            return this;
         }
      }

      public Purchase.PurchasePermissionResponse setDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDocid = (boolean)1;
            this.docid_ = var1;
            return this;
         }
      }

      public Purchase.PurchasePermissionResponse setFulfillmentState(int var1) {
         this.hasFulfillmentState = (boolean)1;
         this.fulfillmentState_ = var1;
         return this;
      }

      public Purchase.PurchasePermissionResponse setNextPurchaseRefundable(boolean var1) {
         this.hasNextPurchaseRefundable = (boolean)1;
         this.nextPurchaseRefundable_ = var1;
         return this;
      }

      public Purchase.PurchasePermissionResponse setPendingCheckoutOrderId(String var1) {
         this.hasPendingCheckoutOrderId = (boolean)1;
         this.pendingCheckoutOrderId_ = var1;
         return this;
      }

      public Purchase.PurchasePermissionResponse setPermission(int var1, Purchase.PurchasePermissionResponse.Permission var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.permission_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasDocid()) {
            Common.Docid var2 = this.getDocid();
            var1.writeMessage(1, var2);
         }

         Iterator var3 = this.getPermissionList().iterator();

         while(var3.hasNext()) {
            Purchase.PurchasePermissionResponse.Permission var4 = (Purchase.PurchasePermissionResponse.Permission)var3.next();
            var1.writeGroup(2, var4);
         }

         if(this.hasCache()) {
            Cache.CacheData var5 = this.getCache();
            var1.writeMessage(5, var5);
         }

         if(this.hasFulfillmentState()) {
            int var6 = this.getFulfillmentState();
            var1.writeInt32(7, var6);
         }

         if(this.hasNextPurchaseRefundable()) {
            boolean var7 = this.getNextPurchaseRefundable();
            var1.writeBool(8, var7);
         }

         if(this.hasPendingCheckoutOrderId()) {
            String var8 = this.getPendingCheckoutOrderId();
            var1.writeString(9, var8);
         }

         if(this.hasDebugInfo()) {
            DebugInfo var9 = this.getDebugInfo();
            var1.writeMessage(10, var9);
         }
      }

      public static final class Permission extends MessageMicro {

         public static final int OFFER_RESTRICTS_FIELD_NUMBER = 11;
         public static final int PERMISSION_FIELD_NUMBER = 4;
         public static final int STABLE_DEVICE_ID_FIELD_NUMBER = 3;
         private int cachedSize;
         private boolean hasPermission;
         private boolean hasStableDeviceId;
         private List<Integer> offerRestricts_;
         private int permission_ = 0;
         private long stableDeviceId_ = 0L;


         public Permission() {
            List var1 = Collections.emptyList();
            this.offerRestricts_ = var1;
            this.cachedSize = -1;
         }

         public Purchase.PurchasePermissionResponse.Permission addOfferRestricts(int var1) {
            if(this.offerRestricts_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.offerRestricts_ = var2;
            }

            List var3 = this.offerRestricts_;
            Integer var4 = Integer.valueOf(var1);
            var3.add(var4);
            return this;
         }

         public final Purchase.PurchasePermissionResponse.Permission clear() {
            Purchase.PurchasePermissionResponse.Permission var1 = this.clearStableDeviceId();
            Purchase.PurchasePermissionResponse.Permission var2 = this.clearPermission();
            Purchase.PurchasePermissionResponse.Permission var3 = this.clearOfferRestricts();
            this.cachedSize = -1;
            return this;
         }

         public Purchase.PurchasePermissionResponse.Permission clearOfferRestricts() {
            List var1 = Collections.emptyList();
            this.offerRestricts_ = var1;
            return this;
         }

         public Purchase.PurchasePermissionResponse.Permission clearPermission() {
            this.hasPermission = (boolean)0;
            this.permission_ = 0;
            return this;
         }

         public Purchase.PurchasePermissionResponse.Permission clearStableDeviceId() {
            this.hasStableDeviceId = (boolean)0;
            this.stableDeviceId_ = 0L;
            return this;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public int getOfferRestricts(int var1) {
            return ((Integer)this.offerRestricts_.get(var1)).intValue();
         }

         public int getOfferRestrictsCount() {
            return this.offerRestricts_.size();
         }

         public List<Integer> getOfferRestrictsList() {
            return this.offerRestricts_;
         }

         public int getPermission() {
            return this.permission_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasStableDeviceId()) {
               long var2 = this.getStableDeviceId();
               int var4 = CodedOutputStreamMicro.computeFixed64Size(3, var2);
               var1 = 0 + var4;
            }

            if(this.hasPermission()) {
               int var5 = this.getPermission();
               int var6 = CodedOutputStreamMicro.computeInt32Size(4, var5);
               var1 += var6;
            }

            int var7 = 0;

            int var9;
            for(Iterator var8 = this.getOfferRestrictsList().iterator(); var8.hasNext(); var7 += var9) {
               var9 = CodedOutputStreamMicro.computeInt32SizeNoTag(((Integer)var8.next()).intValue());
            }

            int var10 = var1 + var7;
            int var11 = this.getOfferRestrictsList().size() * 1;
            int var12 = var10 + var11;
            this.cachedSize = var12;
            return var12;
         }

         public long getStableDeviceId() {
            return this.stableDeviceId_;
         }

         public boolean hasPermission() {
            return this.hasPermission;
         }

         public boolean hasStableDeviceId() {
            return this.hasStableDeviceId;
         }

         public final boolean isInitialized() {
            boolean var1 = false;
            if(this.hasStableDeviceId && this.hasPermission) {
               var1 = true;
            }

            return var1;
         }

         public Purchase.PurchasePermissionResponse.Permission mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 25:
                  long var3 = var1.readFixed64();
                  this.setStableDeviceId(var3);
                  break;
               case 32:
                  int var6 = var1.readInt32();
                  this.setPermission(var6);
                  break;
               case 88:
                  int var8 = var1.readInt32();
                  this.addOfferRestricts(var8);
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

         public Purchase.PurchasePermissionResponse.Permission parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new Purchase.PurchasePermissionResponse.Permission()).mergeFrom(var1);
         }

         public Purchase.PurchasePermissionResponse.Permission parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (Purchase.PurchasePermissionResponse.Permission)((Purchase.PurchasePermissionResponse.Permission)(new Purchase.PurchasePermissionResponse.Permission()).mergeFrom(var1));
         }

         public Purchase.PurchasePermissionResponse.Permission setOfferRestricts(int var1, int var2) {
            List var3 = this.offerRestricts_;
            Integer var4 = Integer.valueOf(var2);
            var3.set(var1, var4);
            return this;
         }

         public Purchase.PurchasePermissionResponse.Permission setPermission(int var1) {
            this.hasPermission = (boolean)1;
            this.permission_ = var1;
            return this;
         }

         public Purchase.PurchasePermissionResponse.Permission setStableDeviceId(long var1) {
            this.hasStableDeviceId = (boolean)1;
            this.stableDeviceId_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasStableDeviceId()) {
               long var2 = this.getStableDeviceId();
               var1.writeFixed64(3, var2);
            }

            if(this.hasPermission()) {
               int var4 = this.getPermission();
               var1.writeInt32(4, var4);
            }

            Iterator var5 = this.getOfferRestrictsList().iterator();

            while(var5.hasNext()) {
               int var6 = ((Integer)var5.next()).intValue();
               var1.writeInt32(11, var6);
            }

         }
      }
   }
}
