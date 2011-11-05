package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class EncryptedSubscriberInfo extends MessageMicro {

   public static final int CARRIER_KEY_VERSION_FIELD_NUMBER = 6;
   public static final int DATA_FIELD_NUMBER = 1;
   public static final int ENCRYPTED_KEY_FIELD_NUMBER = 2;
   public static final int GOOGLE_KEY_VERSION_FIELD_NUMBER = 5;
   public static final int INIT_VECTOR_FIELD_NUMBER = 4;
   public static final int SIGNATURE_FIELD_NUMBER = 3;
   private int cachedSize = -1;
   private int carrierKeyVersion_ = 0;
   private String data_ = "";
   private String encryptedKey_ = "";
   private int googleKeyVersion_ = 0;
   private boolean hasCarrierKeyVersion;
   private boolean hasData;
   private boolean hasEncryptedKey;
   private boolean hasGoogleKeyVersion;
   private boolean hasInitVector;
   private boolean hasSignature;
   private String initVector_ = "";
   private String signature_ = "";


   public EncryptedSubscriberInfo() {}

   public static EncryptedSubscriberInfo parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new EncryptedSubscriberInfo()).mergeFrom(var0);
   }

   public static EncryptedSubscriberInfo parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (EncryptedSubscriberInfo)((EncryptedSubscriberInfo)(new EncryptedSubscriberInfo()).mergeFrom(var0));
   }

   public final EncryptedSubscriberInfo clear() {
      EncryptedSubscriberInfo var1 = this.clearData();
      EncryptedSubscriberInfo var2 = this.clearEncryptedKey();
      EncryptedSubscriberInfo var3 = this.clearSignature();
      EncryptedSubscriberInfo var4 = this.clearInitVector();
      EncryptedSubscriberInfo var5 = this.clearGoogleKeyVersion();
      EncryptedSubscriberInfo var6 = this.clearCarrierKeyVersion();
      this.cachedSize = -1;
      return this;
   }

   public EncryptedSubscriberInfo clearCarrierKeyVersion() {
      this.hasCarrierKeyVersion = (boolean)0;
      this.carrierKeyVersion_ = 0;
      return this;
   }

   public EncryptedSubscriberInfo clearData() {
      this.hasData = (boolean)0;
      this.data_ = "";
      return this;
   }

   public EncryptedSubscriberInfo clearEncryptedKey() {
      this.hasEncryptedKey = (boolean)0;
      this.encryptedKey_ = "";
      return this;
   }

   public EncryptedSubscriberInfo clearGoogleKeyVersion() {
      this.hasGoogleKeyVersion = (boolean)0;
      this.googleKeyVersion_ = 0;
      return this;
   }

   public EncryptedSubscriberInfo clearInitVector() {
      this.hasInitVector = (boolean)0;
      this.initVector_ = "";
      return this;
   }

   public EncryptedSubscriberInfo clearSignature() {
      this.hasSignature = (boolean)0;
      this.signature_ = "";
      return this;
   }

   public int getCachedSize() {
      if(this.cachedSize < 0) {
         int var1 = this.getSerializedSize();
      }

      return this.cachedSize;
   }

   public int getCarrierKeyVersion() {
      return this.carrierKeyVersion_;
   }

   public String getData() {
      return this.data_;
   }

   public String getEncryptedKey() {
      return this.encryptedKey_;
   }

   public int getGoogleKeyVersion() {
      return this.googleKeyVersion_;
   }

   public String getInitVector() {
      return this.initVector_;
   }

   public int getSerializedSize() {
      int var1 = 0;
      if(this.hasData()) {
         String var2 = this.getData();
         int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
         var1 = 0 + var3;
      }

      if(this.hasEncryptedKey()) {
         String var4 = this.getEncryptedKey();
         int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
         var1 += var5;
      }

      if(this.hasSignature()) {
         String var6 = this.getSignature();
         int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
         var1 += var7;
      }

      if(this.hasInitVector()) {
         String var8 = this.getInitVector();
         int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
         var1 += var9;
      }

      if(this.hasGoogleKeyVersion()) {
         int var10 = this.getGoogleKeyVersion();
         int var11 = CodedOutputStreamMicro.computeInt32Size(5, var10);
         var1 += var11;
      }

      if(this.hasCarrierKeyVersion()) {
         int var12 = this.getCarrierKeyVersion();
         int var13 = CodedOutputStreamMicro.computeInt32Size(6, var12);
         var1 += var13;
      }

      this.cachedSize = var1;
      return var1;
   }

   public String getSignature() {
      return this.signature_;
   }

   public boolean hasCarrierKeyVersion() {
      return this.hasCarrierKeyVersion;
   }

   public boolean hasData() {
      return this.hasData;
   }

   public boolean hasEncryptedKey() {
      return this.hasEncryptedKey;
   }

   public boolean hasGoogleKeyVersion() {
      return this.hasGoogleKeyVersion;
   }

   public boolean hasInitVector() {
      return this.hasInitVector;
   }

   public boolean hasSignature() {
      return this.hasSignature;
   }

   public final boolean isInitialized() {
      return true;
   }

   public EncryptedSubscriberInfo mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 10:
            String var3 = var1.readString();
            this.setData(var3);
            break;
         case 18:
            String var5 = var1.readString();
            this.setEncryptedKey(var5);
            break;
         case 26:
            String var7 = var1.readString();
            this.setSignature(var7);
            break;
         case 34:
            String var9 = var1.readString();
            this.setInitVector(var9);
            break;
         case 40:
            int var11 = var1.readInt32();
            this.setGoogleKeyVersion(var11);
            break;
         case 48:
            int var13 = var1.readInt32();
            this.setCarrierKeyVersion(var13);
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

   public EncryptedSubscriberInfo setCarrierKeyVersion(int var1) {
      this.hasCarrierKeyVersion = (boolean)1;
      this.carrierKeyVersion_ = var1;
      return this;
   }

   public EncryptedSubscriberInfo setData(String var1) {
      this.hasData = (boolean)1;
      this.data_ = var1;
      return this;
   }

   public EncryptedSubscriberInfo setEncryptedKey(String var1) {
      this.hasEncryptedKey = (boolean)1;
      this.encryptedKey_ = var1;
      return this;
   }

   public EncryptedSubscriberInfo setGoogleKeyVersion(int var1) {
      this.hasGoogleKeyVersion = (boolean)1;
      this.googleKeyVersion_ = var1;
      return this;
   }

   public EncryptedSubscriberInfo setInitVector(String var1) {
      this.hasInitVector = (boolean)1;
      this.initVector_ = var1;
      return this;
   }

   public EncryptedSubscriberInfo setSignature(String var1) {
      this.hasSignature = (boolean)1;
      this.signature_ = var1;
      return this;
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      if(this.hasData()) {
         String var2 = this.getData();
         var1.writeString(1, var2);
      }

      if(this.hasEncryptedKey()) {
         String var3 = this.getEncryptedKey();
         var1.writeString(2, var3);
      }

      if(this.hasSignature()) {
         String var4 = this.getSignature();
         var1.writeString(3, var4);
      }

      if(this.hasInitVector()) {
         String var5 = this.getInitVector();
         var1.writeString(4, var5);
      }

      if(this.hasGoogleKeyVersion()) {
         int var6 = this.getGoogleKeyVersion();
         var1.writeInt32(5, var6);
      }

      if(this.hasCarrierKeyVersion()) {
         int var7 = this.getCarrierKeyVersion();
         var1.writeInt32(6, var7);
      }
   }
}
