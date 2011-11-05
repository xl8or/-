package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class UserLocale extends MessageMicro {

   public static final int COUNTRY_FIELD_NUMBER = 2;
   public static final int LANGUAGE_CODE_FIELD_NUMBER = 1;
   private int cachedSize = -1;
   private String country_ = "US";
   private boolean hasCountry;
   private boolean hasLanguageCode;
   private String languageCode_ = "en-US";


   public UserLocale() {}

   public static UserLocale parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new UserLocale()).mergeFrom(var0);
   }

   public static UserLocale parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (UserLocale)((UserLocale)(new UserLocale()).mergeFrom(var0));
   }

   public final UserLocale clear() {
      UserLocale var1 = this.clearLanguageCode();
      UserLocale var2 = this.clearCountry();
      this.cachedSize = -1;
      return this;
   }

   public UserLocale clearCountry() {
      this.hasCountry = (boolean)0;
      this.country_ = "US";
      return this;
   }

   public UserLocale clearLanguageCode() {
      this.hasLanguageCode = (boolean)0;
      this.languageCode_ = "en-US";
      return this;
   }

   public int getCachedSize() {
      if(this.cachedSize < 0) {
         int var1 = this.getSerializedSize();
      }

      return this.cachedSize;
   }

   public String getCountry() {
      return this.country_;
   }

   public String getLanguageCode() {
      return this.languageCode_;
   }

   public int getSerializedSize() {
      int var1 = 0;
      if(this.hasLanguageCode()) {
         String var2 = this.getLanguageCode();
         int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
         var1 = 0 + var3;
      }

      if(this.hasCountry()) {
         String var4 = this.getCountry();
         int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
         var1 += var5;
      }

      this.cachedSize = var1;
      return var1;
   }

   public boolean hasCountry() {
      return this.hasCountry;
   }

   public boolean hasLanguageCode() {
      return this.hasLanguageCode;
   }

   public final boolean isInitialized() {
      return true;
   }

   public UserLocale mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 10:
            String var3 = var1.readString();
            this.setLanguageCode(var3);
            break;
         case 18:
            String var5 = var1.readString();
            this.setCountry(var5);
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

   public UserLocale setCountry(String var1) {
      this.hasCountry = (boolean)1;
      this.country_ = var1;
      return this;
   }

   public UserLocale setLanguageCode(String var1) {
      this.hasLanguageCode = (boolean)1;
      this.languageCode_ = var1;
      return this;
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      if(this.hasLanguageCode()) {
         String var2 = this.getLanguageCode();
         var1.writeString(1, var2);
      }

      if(this.hasCountry()) {
         String var3 = this.getCountry();
         var1.writeString(2, var3);
      }
   }
}
