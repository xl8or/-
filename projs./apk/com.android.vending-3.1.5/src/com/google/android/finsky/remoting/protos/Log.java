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

public final class Log {

   private Log() {}

   public static final class LogResponse extends MessageMicro {

      private int cachedSize = -1;


      public LogResponse() {}

      public static Log.LogResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Log.LogResponse()).mergeFrom(var0);
      }

      public static Log.LogResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Log.LogResponse)((Log.LogResponse)(new Log.LogResponse()).mergeFrom(var0));
      }

      public final Log.LogResponse clear() {
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

      public Log.LogResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
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

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {}
   }

   public static final class ClickLogEvent extends MessageMicro {

      public static final int EVENT_TIME_FIELD_NUMBER = 1;
      public static final int LIST_ID_FIELD_NUMBER = 3;
      public static final int REFERRER_LIST_ID_FIELD_NUMBER = 5;
      public static final int REFERRER_URL_FIELD_NUMBER = 4;
      public static final int URL_FIELD_NUMBER = 2;
      private int cachedSize = -1;
      private long eventTime_ = 0L;
      private boolean hasEventTime;
      private boolean hasListId;
      private boolean hasReferrerListId;
      private boolean hasReferrerUrl;
      private boolean hasUrl;
      private String listId_ = "";
      private String referrerListId_ = "";
      private String referrerUrl_ = "";
      private String url_ = "";


      public ClickLogEvent() {}

      public static Log.ClickLogEvent parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Log.ClickLogEvent()).mergeFrom(var0);
      }

      public static Log.ClickLogEvent parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Log.ClickLogEvent)((Log.ClickLogEvent)(new Log.ClickLogEvent()).mergeFrom(var0));
      }

      public final Log.ClickLogEvent clear() {
         Log.ClickLogEvent var1 = this.clearEventTime();
         Log.ClickLogEvent var2 = this.clearUrl();
         Log.ClickLogEvent var3 = this.clearListId();
         Log.ClickLogEvent var4 = this.clearReferrerUrl();
         Log.ClickLogEvent var5 = this.clearReferrerListId();
         this.cachedSize = -1;
         return this;
      }

      public Log.ClickLogEvent clearEventTime() {
         this.hasEventTime = (boolean)0;
         this.eventTime_ = 0L;
         return this;
      }

      public Log.ClickLogEvent clearListId() {
         this.hasListId = (boolean)0;
         this.listId_ = "";
         return this;
      }

      public Log.ClickLogEvent clearReferrerListId() {
         this.hasReferrerListId = (boolean)0;
         this.referrerListId_ = "";
         return this;
      }

      public Log.ClickLogEvent clearReferrerUrl() {
         this.hasReferrerUrl = (boolean)0;
         this.referrerUrl_ = "";
         return this;
      }

      public Log.ClickLogEvent clearUrl() {
         this.hasUrl = (boolean)0;
         this.url_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public long getEventTime() {
         return this.eventTime_;
      }

      public String getListId() {
         return this.listId_;
      }

      public String getReferrerListId() {
         return this.referrerListId_;
      }

      public String getReferrerUrl() {
         return this.referrerUrl_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasEventTime()) {
            long var2 = this.getEventTime();
            int var4 = CodedOutputStreamMicro.computeInt64Size(1, var2);
            var1 = 0 + var4;
         }

         if(this.hasUrl()) {
            String var5 = this.getUrl();
            int var6 = CodedOutputStreamMicro.computeStringSize(2, var5);
            var1 += var6;
         }

         if(this.hasListId()) {
            String var7 = this.getListId();
            int var8 = CodedOutputStreamMicro.computeStringSize(3, var7);
            var1 += var8;
         }

         if(this.hasReferrerUrl()) {
            String var9 = this.getReferrerUrl();
            int var10 = CodedOutputStreamMicro.computeStringSize(4, var9);
            var1 += var10;
         }

         if(this.hasReferrerListId()) {
            String var11 = this.getReferrerListId();
            int var12 = CodedOutputStreamMicro.computeStringSize(5, var11);
            var1 += var12;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getUrl() {
         return this.url_;
      }

      public boolean hasEventTime() {
         return this.hasEventTime;
      }

      public boolean hasListId() {
         return this.hasListId;
      }

      public boolean hasReferrerListId() {
         return this.hasReferrerListId;
      }

      public boolean hasReferrerUrl() {
         return this.hasReferrerUrl;
      }

      public boolean hasUrl() {
         return this.hasUrl;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Log.ClickLogEvent mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               long var3 = var1.readInt64();
               this.setEventTime(var3);
               break;
            case 18:
               String var6 = var1.readString();
               this.setUrl(var6);
               break;
            case 26:
               String var8 = var1.readString();
               this.setListId(var8);
               break;
            case 34:
               String var10 = var1.readString();
               this.setReferrerUrl(var10);
               break;
            case 42:
               String var12 = var1.readString();
               this.setReferrerListId(var12);
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

      public Log.ClickLogEvent setEventTime(long var1) {
         this.hasEventTime = (boolean)1;
         this.eventTime_ = var1;
         return this;
      }

      public Log.ClickLogEvent setListId(String var1) {
         this.hasListId = (boolean)1;
         this.listId_ = var1;
         return this;
      }

      public Log.ClickLogEvent setReferrerListId(String var1) {
         this.hasReferrerListId = (boolean)1;
         this.referrerListId_ = var1;
         return this;
      }

      public Log.ClickLogEvent setReferrerUrl(String var1) {
         this.hasReferrerUrl = (boolean)1;
         this.referrerUrl_ = var1;
         return this;
      }

      public Log.ClickLogEvent setUrl(String var1) {
         this.hasUrl = (boolean)1;
         this.url_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasEventTime()) {
            long var2 = this.getEventTime();
            var1.writeInt64(1, var2);
         }

         if(this.hasUrl()) {
            String var4 = this.getUrl();
            var1.writeString(2, var4);
         }

         if(this.hasListId()) {
            String var5 = this.getListId();
            var1.writeString(3, var5);
         }

         if(this.hasReferrerUrl()) {
            String var6 = this.getReferrerUrl();
            var1.writeString(4, var6);
         }

         if(this.hasReferrerListId()) {
            String var7 = this.getReferrerListId();
            var1.writeString(5, var7);
         }
      }
   }

   public static final class LogRequest extends MessageMicro {

      public static final int CLICK_EVENT_FIELD_NUMBER = 1;
      private int cachedSize;
      private List<Log.ClickLogEvent> clickEvent_;


      public LogRequest() {
         List var1 = Collections.emptyList();
         this.clickEvent_ = var1;
         this.cachedSize = -1;
      }

      public static Log.LogRequest parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Log.LogRequest()).mergeFrom(var0);
      }

      public static Log.LogRequest parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Log.LogRequest)((Log.LogRequest)(new Log.LogRequest()).mergeFrom(var0));
      }

      public Log.LogRequest addClickEvent(Log.ClickLogEvent var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.clickEvent_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.clickEvent_ = var2;
            }

            this.clickEvent_.add(var1);
            return this;
         }
      }

      public final Log.LogRequest clear() {
         Log.LogRequest var1 = this.clearClickEvent();
         this.cachedSize = -1;
         return this;
      }

      public Log.LogRequest clearClickEvent() {
         List var1 = Collections.emptyList();
         this.clickEvent_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Log.ClickLogEvent getClickEvent(int var1) {
         return (Log.ClickLogEvent)this.clickEvent_.get(var1);
      }

      public int getClickEventCount() {
         return this.clickEvent_.size();
      }

      public List<Log.ClickLogEvent> getClickEventList() {
         return this.clickEvent_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getClickEventList().iterator(); var2.hasNext(); var1 += var4) {
            Log.ClickLogEvent var3 = (Log.ClickLogEvent)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(1, var3);
         }

         this.cachedSize = var1;
         return var1;
      }

      public final boolean isInitialized() {
         return true;
      }

      public Log.LogRequest mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Log.ClickLogEvent var3 = new Log.ClickLogEvent();
               var1.readMessage(var3);
               this.addClickEvent(var3);
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

      public Log.LogRequest setClickEvent(int var1, Log.ClickLogEvent var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.clickEvent_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getClickEventList().iterator();

         while(var2.hasNext()) {
            Log.ClickLogEvent var3 = (Log.ClickLogEvent)var2.next();
            var1.writeMessage(1, var3);
         }

      }
   }
}
