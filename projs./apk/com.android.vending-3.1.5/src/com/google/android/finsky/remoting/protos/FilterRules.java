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

public final class FilterRules {

   private FilterRules() {}

   public static final class Availability extends MessageMicro {

      public static final int AVAILABLE = 1;
      public static final int OFFER_TYPE_FIELD_NUMBER = 6;
      public static final int PERDEVICEAVAILABILITYRESTRICTION_FIELD_NUMBER = 9;
      public static final int RESTRICTION_FIELD_NUMBER = 5;
      public static final int RULE_FIELD_NUMBER = 7;
      public static final int UNAVAILABLE_COUNTRY = 2;
      public static final int UNAVAILABLE_COUNTRY_OR_CARRIER = 11;
      public static final int UNAVAILABLE_DEVICE_CARRIER = 10;
      public static final int UNAVAILABLE_DEVICE_HARDWARE = 9;
      public static final int UNAVAILABLE_SAFE_SEARCH_LEVEL = 12;
      public static final int UNAVAILABLE_UNKNOWN_REASON = 6;
      public static final int UNAVAILABLE_UNLESS_IN_GROUP = 8;
      public static final int UNAVAILABLE_UNLESS_PURCHASED = 7;
      public static final int USER_HAS_PURCHASED_DEPRECATED_FIELD_NUMBER = 4;
      private int cachedSize;
      private boolean hasOfferType;
      private boolean hasRestriction;
      private boolean hasRule;
      private boolean hasUserHasPurchasedDeprecated;
      private int offerType_ = 1;
      private List<FilterRules.Availability.PerDeviceAvailabilityRestriction> perDeviceAvailabilityRestriction_;
      private int restriction_ = 1;
      private FilterRules.Rule rule_ = null;
      private boolean userHasPurchasedDeprecated_ = 0;


      public Availability() {
         List var1 = Collections.emptyList();
         this.perDeviceAvailabilityRestriction_ = var1;
         this.cachedSize = -1;
      }

      public static FilterRules.Availability parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new FilterRules.Availability()).mergeFrom(var0);
      }

      public static FilterRules.Availability parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (FilterRules.Availability)((FilterRules.Availability)(new FilterRules.Availability()).mergeFrom(var0));
      }

      public FilterRules.Availability addPerDeviceAvailabilityRestriction(FilterRules.Availability.PerDeviceAvailabilityRestriction var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.perDeviceAvailabilityRestriction_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.perDeviceAvailabilityRestriction_ = var2;
            }

            this.perDeviceAvailabilityRestriction_.add(var1);
            return this;
         }
      }

      public final FilterRules.Availability clear() {
         FilterRules.Availability var1 = this.clearRestriction();
         FilterRules.Availability var2 = this.clearUserHasPurchasedDeprecated();
         FilterRules.Availability var3 = this.clearOfferType();
         FilterRules.Availability var4 = this.clearRule();
         FilterRules.Availability var5 = this.clearPerDeviceAvailabilityRestriction();
         this.cachedSize = -1;
         return this;
      }

      public FilterRules.Availability clearOfferType() {
         this.hasOfferType = (boolean)0;
         this.offerType_ = 1;
         return this;
      }

      public FilterRules.Availability clearPerDeviceAvailabilityRestriction() {
         List var1 = Collections.emptyList();
         this.perDeviceAvailabilityRestriction_ = var1;
         return this;
      }

      public FilterRules.Availability clearRestriction() {
         this.hasRestriction = (boolean)0;
         this.restriction_ = 1;
         return this;
      }

      public FilterRules.Availability clearRule() {
         this.hasRule = (boolean)0;
         this.rule_ = null;
         return this;
      }

      public FilterRules.Availability clearUserHasPurchasedDeprecated() {
         this.hasUserHasPurchasedDeprecated = (boolean)0;
         this.userHasPurchasedDeprecated_ = (boolean)0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getOfferType() {
         return this.offerType_;
      }

      public FilterRules.Availability.PerDeviceAvailabilityRestriction getPerDeviceAvailabilityRestriction(int var1) {
         return (FilterRules.Availability.PerDeviceAvailabilityRestriction)this.perDeviceAvailabilityRestriction_.get(var1);
      }

      public int getPerDeviceAvailabilityRestrictionCount() {
         return this.perDeviceAvailabilityRestriction_.size();
      }

      public List<FilterRules.Availability.PerDeviceAvailabilityRestriction> getPerDeviceAvailabilityRestrictionList() {
         return this.perDeviceAvailabilityRestriction_;
      }

      public int getRestriction() {
         return this.restriction_;
      }

      public FilterRules.Rule getRule() {
         return this.rule_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasUserHasPurchasedDeprecated()) {
            boolean var2 = this.getUserHasPurchasedDeprecated();
            int var3 = CodedOutputStreamMicro.computeBoolSize(4, var2);
            var1 = 0 + var3;
         }

         if(this.hasRestriction()) {
            int var4 = this.getRestriction();
            int var5 = CodedOutputStreamMicro.computeInt32Size(5, var4);
            var1 += var5;
         }

         if(this.hasOfferType()) {
            int var6 = this.getOfferType();
            int var7 = CodedOutputStreamMicro.computeInt32Size(6, var6);
            var1 += var7;
         }

         if(this.hasRule()) {
            FilterRules.Rule var8 = this.getRule();
            int var9 = CodedOutputStreamMicro.computeMessageSize(7, var8);
            var1 += var9;
         }

         int var12;
         for(Iterator var10 = this.getPerDeviceAvailabilityRestrictionList().iterator(); var10.hasNext(); var1 += var12) {
            FilterRules.Availability.PerDeviceAvailabilityRestriction var11 = (FilterRules.Availability.PerDeviceAvailabilityRestriction)var10.next();
            var12 = CodedOutputStreamMicro.computeGroupSize(9, var11);
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean getUserHasPurchasedDeprecated() {
         return this.userHasPurchasedDeprecated_;
      }

      public boolean hasOfferType() {
         return this.hasOfferType;
      }

      public boolean hasRestriction() {
         return this.hasRestriction;
      }

      public boolean hasRule() {
         return this.hasRule;
      }

      public boolean hasUserHasPurchasedDeprecated() {
         return this.hasUserHasPurchasedDeprecated;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(!this.hasRule() || this.getRule().isInitialized()) {
            Iterator var2 = this.getPerDeviceAvailabilityRestrictionList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((FilterRules.Availability.PerDeviceAvailabilityRestriction)var2.next()).isInitialized());
         }

         return var1;
      }

      public FilterRules.Availability mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 32:
               boolean var3 = var1.readBool();
               this.setUserHasPurchasedDeprecated(var3);
               break;
            case 40:
               int var5 = var1.readInt32();
               this.setRestriction(var5);
               break;
            case 48:
               int var7 = var1.readInt32();
               this.setOfferType(var7);
               break;
            case 58:
               FilterRules.Rule var9 = new FilterRules.Rule();
               var1.readMessage(var9);
               this.setRule(var9);
               break;
            case 75:
               FilterRules.Availability.PerDeviceAvailabilityRestriction var11 = new FilterRules.Availability.PerDeviceAvailabilityRestriction();
               var1.readGroup(var11, 9);
               this.addPerDeviceAvailabilityRestriction(var11);
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

      public FilterRules.Availability setOfferType(int var1) {
         this.hasOfferType = (boolean)1;
         this.offerType_ = var1;
         return this;
      }

      public FilterRules.Availability setPerDeviceAvailabilityRestriction(int var1, FilterRules.Availability.PerDeviceAvailabilityRestriction var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.perDeviceAvailabilityRestriction_.set(var1, var2);
            return this;
         }
      }

      public FilterRules.Availability setRestriction(int var1) {
         this.hasRestriction = (boolean)1;
         this.restriction_ = var1;
         return this;
      }

      public FilterRules.Availability setRule(FilterRules.Rule var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasRule = (boolean)1;
            this.rule_ = var1;
            return this;
         }
      }

      public FilterRules.Availability setUserHasPurchasedDeprecated(boolean var1) {
         this.hasUserHasPurchasedDeprecated = (boolean)1;
         this.userHasPurchasedDeprecated_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasUserHasPurchasedDeprecated()) {
            boolean var2 = this.getUserHasPurchasedDeprecated();
            var1.writeBool(4, var2);
         }

         if(this.hasRestriction()) {
            int var3 = this.getRestriction();
            var1.writeInt32(5, var3);
         }

         if(this.hasOfferType()) {
            int var4 = this.getOfferType();
            var1.writeInt32(6, var4);
         }

         if(this.hasRule()) {
            FilterRules.Rule var5 = this.getRule();
            var1.writeMessage(7, var5);
         }

         Iterator var6 = this.getPerDeviceAvailabilityRestrictionList().iterator();

         while(var6.hasNext()) {
            FilterRules.Availability.PerDeviceAvailabilityRestriction var7 = (FilterRules.Availability.PerDeviceAvailabilityRestriction)var6.next();
            var1.writeGroup(9, var7);
         }

      }

      public static final class PerDeviceAvailabilityRestriction extends MessageMicro {

         public static final int ANDROID_ID_FIELD_NUMBER = 10;
         public static final int CHANNEL_ID_FIELD_NUMBER = 12;
         public static final int DEVICE_RESTRICTION_FIELD_NUMBER = 11;
         private long androidId_ = 0L;
         private int cachedSize = -1;
         private long channelId_ = 0L;
         private int deviceRestriction_ = 1;
         private boolean hasAndroidId;
         private boolean hasChannelId;
         private boolean hasDeviceRestriction;


         public PerDeviceAvailabilityRestriction() {}

         public final FilterRules.Availability.PerDeviceAvailabilityRestriction clear() {
            FilterRules.Availability.PerDeviceAvailabilityRestriction var1 = this.clearAndroidId();
            FilterRules.Availability.PerDeviceAvailabilityRestriction var2 = this.clearDeviceRestriction();
            FilterRules.Availability.PerDeviceAvailabilityRestriction var3 = this.clearChannelId();
            this.cachedSize = -1;
            return this;
         }

         public FilterRules.Availability.PerDeviceAvailabilityRestriction clearAndroidId() {
            this.hasAndroidId = (boolean)0;
            this.androidId_ = 0L;
            return this;
         }

         public FilterRules.Availability.PerDeviceAvailabilityRestriction clearChannelId() {
            this.hasChannelId = (boolean)0;
            this.channelId_ = 0L;
            return this;
         }

         public FilterRules.Availability.PerDeviceAvailabilityRestriction clearDeviceRestriction() {
            this.hasDeviceRestriction = (boolean)0;
            this.deviceRestriction_ = 1;
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

         public long getChannelId() {
            return this.channelId_;
         }

         public int getDeviceRestriction() {
            return this.deviceRestriction_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasAndroidId()) {
               long var2 = this.getAndroidId();
               int var4 = CodedOutputStreamMicro.computeFixed64Size(10, var2);
               var1 = 0 + var4;
            }

            if(this.hasDeviceRestriction()) {
               int var5 = this.getDeviceRestriction();
               int var6 = CodedOutputStreamMicro.computeInt32Size(11, var5);
               var1 += var6;
            }

            if(this.hasChannelId()) {
               long var7 = this.getChannelId();
               int var9 = CodedOutputStreamMicro.computeInt64Size(12, var7);
               var1 += var9;
            }

            this.cachedSize = var1;
            return var1;
         }

         public boolean hasAndroidId() {
            return this.hasAndroidId;
         }

         public boolean hasChannelId() {
            return this.hasChannelId;
         }

         public boolean hasDeviceRestriction() {
            return this.hasDeviceRestriction;
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

         public FilterRules.Availability.PerDeviceAvailabilityRestriction mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 81:
                  long var3 = var1.readFixed64();
                  this.setAndroidId(var3);
                  break;
               case 88:
                  int var6 = var1.readInt32();
                  this.setDeviceRestriction(var6);
                  break;
               case 96:
                  long var8 = var1.readInt64();
                  this.setChannelId(var8);
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

         public FilterRules.Availability.PerDeviceAvailabilityRestriction parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new FilterRules.Availability.PerDeviceAvailabilityRestriction()).mergeFrom(var1);
         }

         public FilterRules.Availability.PerDeviceAvailabilityRestriction parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (FilterRules.Availability.PerDeviceAvailabilityRestriction)((FilterRules.Availability.PerDeviceAvailabilityRestriction)(new FilterRules.Availability.PerDeviceAvailabilityRestriction()).mergeFrom(var1));
         }

         public FilterRules.Availability.PerDeviceAvailabilityRestriction setAndroidId(long var1) {
            this.hasAndroidId = (boolean)1;
            this.androidId_ = var1;
            return this;
         }

         public FilterRules.Availability.PerDeviceAvailabilityRestriction setChannelId(long var1) {
            this.hasChannelId = (boolean)1;
            this.channelId_ = var1;
            return this;
         }

         public FilterRules.Availability.PerDeviceAvailabilityRestriction setDeviceRestriction(int var1) {
            this.hasDeviceRestriction = (boolean)1;
            this.deviceRestriction_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasAndroidId()) {
               long var2 = this.getAndroidId();
               var1.writeFixed64(10, var2);
            }

            if(this.hasDeviceRestriction()) {
               int var4 = this.getDeviceRestriction();
               var1.writeInt32(11, var4);
            }

            if(this.hasChannelId()) {
               long var5 = this.getChannelId();
               var1.writeInt64(12, var5);
            }
         }
      }
   }

   public static final class Rule extends MessageMicro {

      public static final int ALWAYS_TRUE = 10;
      public static final int AND_SUBRULES = 8;
      public static final int BUILD_APPROVED = 22;
      public static final int COMMENT_FIELD_NUMBER = 9;
      public static final int CONTAINS_ALL = 5;
      public static final int CONTAINS_ANY = 4;
      public static final int COUNTRY = 2;
      public static final int DEVICE_NAME = 23;
      public static final int DOUBLE_ARG_FIELD_NUMBER = 6;
      public static final int EQUALS = 3;
      public static final int EXPERIMENT_ID = 26;
      public static final int GL_ES_VERSION = 17;
      public static final int GL_EXTENSION = 18;
      public static final int GREATER_THAN = 2;
      public static final int HAS_FIVE_WAY_NAVIGATION = 25;
      public static final int HAS_HARD_KEYBOARD = 12;
      public static final int IP_COUNTRY = 1;
      public static final int IS_FALSE = 7;
      public static final int IS_TRUE = 6;
      public static final int KEYBOARD = 16;
      public static final int KEY_FIELD_NUMBER = 3;
      public static final int LANGUAGE = 3;
      public static final int LESS_THAN = 1;
      public static final int LONG_ARG_FIELD_NUMBER = 5;
      public static final int MARKET_CLIENT_FEATURE = 21;
      public static final int MAX_APK_DOWNLOAD_SIZE_MB = 19;
      public static final int MCCMNC = 4;
      public static final int NATIVE_PLATFORM = 9;
      public static final int NAVIGATION = 11;
      public static final int NEGATE_FIELD_NUMBER = 1;
      public static final int OPERATOR_FIELD_NUMBER = 2;
      public static final int OR_SUBRULES = 9;
      public static final int RESPONSE_CODE_FIELD_NUMBER = 8;
      public static final int SAFESEARCH_LEVEL = 20;
      public static final int SCREEN_DENSITY = 14;
      public static final int SCREEN_LAYOUT = 15;
      public static final int SCREEN_SIZE = 13;
      public static final int SDK_VERSION = 5;
      public static final int STRING_ARG_FIELD_NUMBER = 4;
      public static final int SUBRULE_FIELD_NUMBER = 7;
      public static final int SYSTEM_AVAILABLE_FEATURE = 7;
      public static final int SYSTEM_SHARED_LIBRARY = 6;
      public static final int SYSTEM_SUPPORTED_LOCALE = 8;
      public static final int TOUCH_SCREEN = 10;
      private int cachedSize;
      private String comment_;
      private List<Double> doubleArg_;
      private boolean hasComment;
      private boolean hasKey;
      private boolean hasNegate;
      private boolean hasOperator;
      private boolean hasResponseCode;
      private int key_ = 1;
      private List<Long> longArg_;
      private boolean negate_ = 0;
      private int operator_ = 1;
      private int responseCode_;
      private List<String> stringArg_;
      private List<FilterRules.Rule> subrule_;


      public Rule() {
         List var1 = Collections.emptyList();
         this.stringArg_ = var1;
         List var2 = Collections.emptyList();
         this.longArg_ = var2;
         List var3 = Collections.emptyList();
         this.doubleArg_ = var3;
         List var4 = Collections.emptyList();
         this.subrule_ = var4;
         this.responseCode_ = 1;
         this.comment_ = "";
         this.cachedSize = -1;
      }

      public static FilterRules.Rule parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new FilterRules.Rule()).mergeFrom(var0);
      }

      public static FilterRules.Rule parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (FilterRules.Rule)((FilterRules.Rule)(new FilterRules.Rule()).mergeFrom(var0));
      }

      public FilterRules.Rule addDoubleArg(double var1) {
         if(this.doubleArg_.isEmpty()) {
            ArrayList var3 = new ArrayList();
            this.doubleArg_ = var3;
         }

         List var4 = this.doubleArg_;
         Double var5 = Double.valueOf(var1);
         var4.add(var5);
         return this;
      }

      public FilterRules.Rule addLongArg(long var1) {
         if(this.longArg_.isEmpty()) {
            ArrayList var3 = new ArrayList();
            this.longArg_ = var3;
         }

         List var4 = this.longArg_;
         Long var5 = Long.valueOf(var1);
         var4.add(var5);
         return this;
      }

      public FilterRules.Rule addStringArg(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.stringArg_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.stringArg_ = var2;
            }

            this.stringArg_.add(var1);
            return this;
         }
      }

      public FilterRules.Rule addSubrule(FilterRules.Rule var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.subrule_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.subrule_ = var2;
            }

            this.subrule_.add(var1);
            return this;
         }
      }

      public final FilterRules.Rule clear() {
         FilterRules.Rule var1 = this.clearNegate();
         FilterRules.Rule var2 = this.clearOperator();
         FilterRules.Rule var3 = this.clearKey();
         FilterRules.Rule var4 = this.clearStringArg();
         FilterRules.Rule var5 = this.clearLongArg();
         FilterRules.Rule var6 = this.clearDoubleArg();
         FilterRules.Rule var7 = this.clearSubrule();
         FilterRules.Rule var8 = this.clearResponseCode();
         FilterRules.Rule var9 = this.clearComment();
         this.cachedSize = -1;
         return this;
      }

      public FilterRules.Rule clearComment() {
         this.hasComment = (boolean)0;
         this.comment_ = "";
         return this;
      }

      public FilterRules.Rule clearDoubleArg() {
         List var1 = Collections.emptyList();
         this.doubleArg_ = var1;
         return this;
      }

      public FilterRules.Rule clearKey() {
         this.hasKey = (boolean)0;
         this.key_ = 1;
         return this;
      }

      public FilterRules.Rule clearLongArg() {
         List var1 = Collections.emptyList();
         this.longArg_ = var1;
         return this;
      }

      public FilterRules.Rule clearNegate() {
         this.hasNegate = (boolean)0;
         this.negate_ = (boolean)0;
         return this;
      }

      public FilterRules.Rule clearOperator() {
         this.hasOperator = (boolean)0;
         this.operator_ = 1;
         return this;
      }

      public FilterRules.Rule clearResponseCode() {
         this.hasResponseCode = (boolean)0;
         this.responseCode_ = 1;
         return this;
      }

      public FilterRules.Rule clearStringArg() {
         List var1 = Collections.emptyList();
         this.stringArg_ = var1;
         return this;
      }

      public FilterRules.Rule clearSubrule() {
         List var1 = Collections.emptyList();
         this.subrule_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getComment() {
         return this.comment_;
      }

      public double getDoubleArg(int var1) {
         return ((Double)this.doubleArg_.get(var1)).doubleValue();
      }

      public int getDoubleArgCount() {
         return this.doubleArg_.size();
      }

      public List<Double> getDoubleArgList() {
         return this.doubleArg_;
      }

      public int getKey() {
         return this.key_;
      }

      public long getLongArg(int var1) {
         return ((Long)this.longArg_.get(var1)).longValue();
      }

      public int getLongArgCount() {
         return this.longArg_.size();
      }

      public List<Long> getLongArgList() {
         return this.longArg_;
      }

      public boolean getNegate() {
         return this.negate_;
      }

      public int getOperator() {
         return this.operator_;
      }

      public int getResponseCode() {
         return this.responseCode_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasNegate()) {
            boolean var2 = this.getNegate();
            int var3 = CodedOutputStreamMicro.computeBoolSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasOperator()) {
            int var4 = this.getOperator();
            int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
            var1 += var5;
         }

         if(this.hasKey()) {
            int var6 = this.getKey();
            int var7 = CodedOutputStreamMicro.computeInt32Size(3, var6);
            var1 += var7;
         }

         int var8 = 0;

         int var10;
         for(Iterator var9 = this.getStringArgList().iterator(); var9.hasNext(); var8 += var10) {
            var10 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var9.next());
         }

         int var11 = var1 + var8;
         int var12 = this.getStringArgList().size() * 1;
         int var13 = var11 + var12;
         int var14 = 0;

         int var16;
         for(Iterator var15 = this.getLongArgList().iterator(); var15.hasNext(); var14 += var16) {
            var16 = CodedOutputStreamMicro.computeInt64SizeNoTag(((Long)var15.next()).longValue());
         }

         int var17 = var13 + var14;
         int var18 = this.getLongArgList().size() * 1;
         int var19 = var17 + var18;
         int var20 = this.getDoubleArgList().size() * 8;
         int var21 = var19 + var20;
         int var22 = this.getDoubleArgList().size() * 1;
         int var23 = var21 + var22;

         int var26;
         for(Iterator var24 = this.getSubruleList().iterator(); var24.hasNext(); var23 += var26) {
            FilterRules.Rule var25 = (FilterRules.Rule)var24.next();
            var26 = CodedOutputStreamMicro.computeMessageSize(7, var25);
         }

         if(this.hasResponseCode()) {
            int var27 = this.getResponseCode();
            int var28 = CodedOutputStreamMicro.computeInt32Size(8, var27);
            var23 += var28;
         }

         if(this.hasComment()) {
            String var29 = this.getComment();
            int var30 = CodedOutputStreamMicro.computeStringSize(9, var29);
            var23 += var30;
         }

         this.cachedSize = var23;
         return var23;
      }

      public String getStringArg(int var1) {
         return (String)this.stringArg_.get(var1);
      }

      public int getStringArgCount() {
         return this.stringArg_.size();
      }

      public List<String> getStringArgList() {
         return this.stringArg_;
      }

      public FilterRules.Rule getSubrule(int var1) {
         return (FilterRules.Rule)this.subrule_.get(var1);
      }

      public int getSubruleCount() {
         return this.subrule_.size();
      }

      public List<FilterRules.Rule> getSubruleList() {
         return this.subrule_;
      }

      public boolean hasComment() {
         return this.hasComment;
      }

      public boolean hasKey() {
         return this.hasKey;
      }

      public boolean hasNegate() {
         return this.hasNegate;
      }

      public boolean hasOperator() {
         return this.hasOperator;
      }

      public boolean hasResponseCode() {
         return this.hasResponseCode;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasOperator) {
            Iterator var2 = this.getSubruleList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((FilterRules.Rule)var2.next()).isInitialized());
         }

         return var1;
      }

      public FilterRules.Rule mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               boolean var3 = var1.readBool();
               this.setNegate(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setOperator(var5);
               break;
            case 24:
               int var7 = var1.readInt32();
               this.setKey(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.addStringArg(var9);
               break;
            case 40:
               long var11 = var1.readInt64();
               this.addLongArg(var11);
               break;
            case 49:
               double var14 = var1.readDouble();
               this.addDoubleArg(var14);
               break;
            case 58:
               FilterRules.Rule var17 = new FilterRules.Rule();
               var1.readMessage(var17);
               this.addSubrule(var17);
               break;
            case 64:
               int var19 = var1.readInt32();
               this.setResponseCode(var19);
               break;
            case 74:
               String var21 = var1.readString();
               this.setComment(var21);
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

      public FilterRules.Rule setComment(String var1) {
         this.hasComment = (boolean)1;
         this.comment_ = var1;
         return this;
      }

      public FilterRules.Rule setDoubleArg(int var1, double var2) {
         List var4 = this.doubleArg_;
         Double var5 = Double.valueOf(var2);
         var4.set(var1, var5);
         return this;
      }

      public FilterRules.Rule setKey(int var1) {
         this.hasKey = (boolean)1;
         this.key_ = var1;
         return this;
      }

      public FilterRules.Rule setLongArg(int var1, long var2) {
         List var4 = this.longArg_;
         Long var5 = Long.valueOf(var2);
         var4.set(var1, var5);
         return this;
      }

      public FilterRules.Rule setNegate(boolean var1) {
         this.hasNegate = (boolean)1;
         this.negate_ = var1;
         return this;
      }

      public FilterRules.Rule setOperator(int var1) {
         this.hasOperator = (boolean)1;
         this.operator_ = var1;
         return this;
      }

      public FilterRules.Rule setResponseCode(int var1) {
         this.hasResponseCode = (boolean)1;
         this.responseCode_ = var1;
         return this;
      }

      public FilterRules.Rule setStringArg(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.stringArg_.set(var1, var2);
            return this;
         }
      }

      public FilterRules.Rule setSubrule(int var1, FilterRules.Rule var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.subrule_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasNegate()) {
            boolean var2 = this.getNegate();
            var1.writeBool(1, var2);
         }

         if(this.hasOperator()) {
            int var3 = this.getOperator();
            var1.writeInt32(2, var3);
         }

         if(this.hasKey()) {
            int var4 = this.getKey();
            var1.writeInt32(3, var4);
         }

         Iterator var5 = this.getStringArgList().iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            var1.writeString(4, var6);
         }

         Iterator var7 = this.getLongArgList().iterator();

         while(var7.hasNext()) {
            long var8 = ((Long)var7.next()).longValue();
            var1.writeInt64(5, var8);
         }

         Iterator var10 = this.getDoubleArgList().iterator();

         while(var10.hasNext()) {
            double var11 = ((Double)var10.next()).doubleValue();
            var1.writeDouble(6, var11);
         }

         Iterator var13 = this.getSubruleList().iterator();

         while(var13.hasNext()) {
            FilterRules.Rule var14 = (FilterRules.Rule)var13.next();
            var1.writeMessage(7, var14);
         }

         if(this.hasResponseCode()) {
            int var15 = this.getResponseCode();
            var1.writeInt32(8, var15);
         }

         if(this.hasComment()) {
            String var16 = this.getComment();
            var1.writeString(9, var16);
         }
      }
   }
}
