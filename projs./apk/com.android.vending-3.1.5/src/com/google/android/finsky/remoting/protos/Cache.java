package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class Cache {

   private Cache() {}

   public static final class CacheData extends MessageMicro {

      public static final int BACKEND_DOCID_FIELD_NUMBER = 5;
      public static final int EXPIRATION_TIMESTAMP_FIELD_NUMBER = 2;
      public static final int FROM_CACHE_FIELD_NUMBER = 1;
      public static final int GAIA_ID_FIELD_NUMBER = 4;
      public static final int QUERY_FROM_CACHE_FIELD_NUMBER = 3;
      private String backendDocid_ = "";
      private int cachedSize = -1;
      private double expirationTimestamp_ = 0.0D;
      private boolean fromCache_ = 0;
      private long gaiaId_ = 0L;
      private boolean hasBackendDocid;
      private boolean hasExpirationTimestamp;
      private boolean hasFromCache;
      private boolean hasGaiaId;
      private boolean hasQueryFromCache;
      private String queryFromCache_ = "";


      public CacheData() {}

      public static Cache.CacheData parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Cache.CacheData()).mergeFrom(var0);
      }

      public static Cache.CacheData parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Cache.CacheData)((Cache.CacheData)(new Cache.CacheData()).mergeFrom(var0));
      }

      public final Cache.CacheData clear() {
         Cache.CacheData var1 = this.clearFromCache();
         Cache.CacheData var2 = this.clearExpirationTimestamp();
         Cache.CacheData var3 = this.clearQueryFromCache();
         Cache.CacheData var4 = this.clearGaiaId();
         Cache.CacheData var5 = this.clearBackendDocid();
         this.cachedSize = -1;
         return this;
      }

      public Cache.CacheData clearBackendDocid() {
         this.hasBackendDocid = (boolean)0;
         this.backendDocid_ = "";
         return this;
      }

      public Cache.CacheData clearExpirationTimestamp() {
         this.hasExpirationTimestamp = (boolean)0;
         this.expirationTimestamp_ = 0.0D;
         return this;
      }

      public Cache.CacheData clearFromCache() {
         this.hasFromCache = (boolean)0;
         this.fromCache_ = (boolean)0;
         return this;
      }

      public Cache.CacheData clearGaiaId() {
         this.hasGaiaId = (boolean)0;
         this.gaiaId_ = 0L;
         return this;
      }

      public Cache.CacheData clearQueryFromCache() {
         this.hasQueryFromCache = (boolean)0;
         this.queryFromCache_ = "";
         return this;
      }

      public String getBackendDocid() {
         return this.backendDocid_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public double getExpirationTimestamp() {
         return this.expirationTimestamp_;
      }

      public boolean getFromCache() {
         return this.fromCache_;
      }

      public long getGaiaId() {
         return this.gaiaId_;
      }

      public String getQueryFromCache() {
         return this.queryFromCache_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasFromCache()) {
            boolean var2 = this.getFromCache();
            int var3 = CodedOutputStreamMicro.computeBoolSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasExpirationTimestamp()) {
            double var4 = this.getExpirationTimestamp();
            int var6 = CodedOutputStreamMicro.computeDoubleSize(2, var4);
            var1 += var6;
         }

         if(this.hasQueryFromCache()) {
            String var7 = this.getQueryFromCache();
            int var8 = CodedOutputStreamMicro.computeStringSize(3, var7);
            var1 += var8;
         }

         if(this.hasGaiaId()) {
            long var9 = this.getGaiaId();
            int var11 = CodedOutputStreamMicro.computeFixed64Size(4, var9);
            var1 += var11;
         }

         if(this.hasBackendDocid()) {
            String var12 = this.getBackendDocid();
            int var13 = CodedOutputStreamMicro.computeStringSize(5, var12);
            var1 += var13;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasBackendDocid() {
         return this.hasBackendDocid;
      }

      public boolean hasExpirationTimestamp() {
         return this.hasExpirationTimestamp;
      }

      public boolean hasFromCache() {
         return this.hasFromCache;
      }

      public boolean hasGaiaId() {
         return this.hasGaiaId;
      }

      public boolean hasQueryFromCache() {
         return this.hasQueryFromCache;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Cache.CacheData mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               boolean var3 = var1.readBool();
               this.setFromCache(var3);
               break;
            case 17:
               double var5 = var1.readDouble();
               this.setExpirationTimestamp(var5);
               break;
            case 26:
               String var8 = var1.readString();
               this.setQueryFromCache(var8);
               break;
            case 33:
               long var10 = var1.readFixed64();
               this.setGaiaId(var10);
               break;
            case 42:
               String var13 = var1.readString();
               this.setBackendDocid(var13);
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

      public Cache.CacheData setBackendDocid(String var1) {
         this.hasBackendDocid = (boolean)1;
         this.backendDocid_ = var1;
         return this;
      }

      public Cache.CacheData setExpirationTimestamp(double var1) {
         this.hasExpirationTimestamp = (boolean)1;
         this.expirationTimestamp_ = var1;
         return this;
      }

      public Cache.CacheData setFromCache(boolean var1) {
         this.hasFromCache = (boolean)1;
         this.fromCache_ = var1;
         return this;
      }

      public Cache.CacheData setGaiaId(long var1) {
         this.hasGaiaId = (boolean)1;
         this.gaiaId_ = var1;
         return this;
      }

      public Cache.CacheData setQueryFromCache(String var1) {
         this.hasQueryFromCache = (boolean)1;
         this.queryFromCache_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasFromCache()) {
            boolean var2 = this.getFromCache();
            var1.writeBool(1, var2);
         }

         if(this.hasExpirationTimestamp()) {
            double var3 = this.getExpirationTimestamp();
            var1.writeDouble(2, var3);
         }

         if(this.hasQueryFromCache()) {
            String var5 = this.getQueryFromCache();
            var1.writeString(3, var5);
         }

         if(this.hasGaiaId()) {
            long var6 = this.getGaiaId();
            var1.writeFixed64(4, var6);
         }

         if(this.hasBackendDocid()) {
            String var8 = this.getBackendDocid();
            var1.writeString(5, var8);
         }
      }
   }

   public static final class CacheHint extends MessageMicro {

      public static final int UNCACHEABLE_FIELD_NUMBER = 2;
      public static final int VALID_UNTIL_TIMESTAMP_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private boolean hasUncacheable;
      private boolean hasValidUntilTimestamp;
      private boolean uncacheable_ = 0;
      private double validUntilTimestamp_ = 0.0D;


      public CacheHint() {}

      public static Cache.CacheHint parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Cache.CacheHint()).mergeFrom(var0);
      }

      public static Cache.CacheHint parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Cache.CacheHint)((Cache.CacheHint)(new Cache.CacheHint()).mergeFrom(var0));
      }

      public final Cache.CacheHint clear() {
         Cache.CacheHint var1 = this.clearValidUntilTimestamp();
         Cache.CacheHint var2 = this.clearUncacheable();
         this.cachedSize = -1;
         return this;
      }

      public Cache.CacheHint clearUncacheable() {
         this.hasUncacheable = (boolean)0;
         this.uncacheable_ = (boolean)0;
         return this;
      }

      public Cache.CacheHint clearValidUntilTimestamp() {
         this.hasValidUntilTimestamp = (boolean)0;
         this.validUntilTimestamp_ = 0.0D;
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
         if(this.hasValidUntilTimestamp()) {
            double var2 = this.getValidUntilTimestamp();
            int var4 = CodedOutputStreamMicro.computeDoubleSize(1, var2);
            var1 = 0 + var4;
         }

         if(this.hasUncacheable()) {
            boolean var5 = this.getUncacheable();
            int var6 = CodedOutputStreamMicro.computeBoolSize(2, var5);
            var1 += var6;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean getUncacheable() {
         return this.uncacheable_;
      }

      public double getValidUntilTimestamp() {
         return this.validUntilTimestamp_;
      }

      public boolean hasUncacheable() {
         return this.hasUncacheable;
      }

      public boolean hasValidUntilTimestamp() {
         return this.hasValidUntilTimestamp;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Cache.CacheHint mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 9:
               double var3 = var1.readDouble();
               this.setValidUntilTimestamp(var3);
               break;
            case 16:
               boolean var6 = var1.readBool();
               this.setUncacheable(var6);
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

      public Cache.CacheHint setUncacheable(boolean var1) {
         this.hasUncacheable = (boolean)1;
         this.uncacheable_ = var1;
         return this;
      }

      public Cache.CacheHint setValidUntilTimestamp(double var1) {
         this.hasValidUntilTimestamp = (boolean)1;
         this.validUntilTimestamp_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasValidUntilTimestamp()) {
            double var2 = this.getValidUntilTimestamp();
            var1.writeDouble(1, var2);
         }

         if(this.hasUncacheable()) {
            boolean var4 = this.getUncacheable();
            var1.writeBool(2, var4);
         }
      }
   }
}
