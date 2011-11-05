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

public final class DebugInfo extends MessageMicro {

   public static final int MESSAGE_FIELD_NUMBER = 1;
   public static final int TIMING_FIELD_NUMBER = 2;
   private int cachedSize;
   private List<String> message_;
   private List<DebugInfo.Timing> timing_;


   public DebugInfo() {
      List var1 = Collections.emptyList();
      this.message_ = var1;
      List var2 = Collections.emptyList();
      this.timing_ = var2;
      this.cachedSize = -1;
   }

   public static DebugInfo parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new DebugInfo()).mergeFrom(var0);
   }

   public static DebugInfo parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (DebugInfo)((DebugInfo)(new DebugInfo()).mergeFrom(var0));
   }

   public DebugInfo addMessage(String var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.message_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.message_ = var2;
         }

         this.message_.add(var1);
         return this;
      }
   }

   public DebugInfo addTiming(DebugInfo.Timing var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         if(this.timing_.isEmpty()) {
            ArrayList var2 = new ArrayList();
            this.timing_ = var2;
         }

         this.timing_.add(var1);
         return this;
      }
   }

   public final DebugInfo clear() {
      DebugInfo var1 = this.clearMessage();
      DebugInfo var2 = this.clearTiming();
      this.cachedSize = -1;
      return this;
   }

   public DebugInfo clearMessage() {
      List var1 = Collections.emptyList();
      this.message_ = var1;
      return this;
   }

   public DebugInfo clearTiming() {
      List var1 = Collections.emptyList();
      this.timing_ = var1;
      return this;
   }

   public int getCachedSize() {
      if(this.cachedSize < 0) {
         int var1 = this.getSerializedSize();
      }

      return this.cachedSize;
   }

   public String getMessage(int var1) {
      return (String)this.message_.get(var1);
   }

   public int getMessageCount() {
      return this.message_.size();
   }

   public List<String> getMessageList() {
      return this.message_;
   }

   public int getSerializedSize() {
      int var1 = 0;

      int var3;
      for(Iterator var2 = this.getMessageList().iterator(); var2.hasNext(); var1 += var3) {
         var3 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var2.next());
      }

      int var4 = 0 + var1;
      int var5 = this.getMessageList().size() * 1;
      int var6 = var4 + var5;

      int var9;
      for(Iterator var7 = this.getTimingList().iterator(); var7.hasNext(); var6 += var9) {
         DebugInfo.Timing var8 = (DebugInfo.Timing)var7.next();
         var9 = CodedOutputStreamMicro.computeGroupSize(2, var8);
      }

      this.cachedSize = var6;
      return var6;
   }

   public DebugInfo.Timing getTiming(int var1) {
      return (DebugInfo.Timing)this.timing_.get(var1);
   }

   public int getTimingCount() {
      return this.timing_.size();
   }

   public List<DebugInfo.Timing> getTimingList() {
      return this.timing_;
   }

   public final boolean isInitialized() {
      Iterator var1 = this.getTimingList().iterator();

      boolean var2;
      while(true) {
         if(var1.hasNext()) {
            if(((DebugInfo.Timing)var1.next()).isInitialized()) {
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

   public DebugInfo mergeFrom(CodedInputStreamMicro var1) throws IOException {
      while(true) {
         int var2 = var1.readTag();
         switch(var2) {
         case 10:
            String var3 = var1.readString();
            this.addMessage(var3);
            break;
         case 19:
            DebugInfo.Timing var5 = new DebugInfo.Timing();
            var1.readGroup(var5, 2);
            this.addTiming(var5);
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

   public DebugInfo setMessage(int var1, String var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.message_.set(var1, var2);
         return this;
      }
   }

   public DebugInfo setTiming(int var1, DebugInfo.Timing var2) {
      if(var2 == null) {
         throw new NullPointerException();
      } else {
         this.timing_.set(var1, var2);
         return this;
      }
   }

   public void writeTo(CodedOutputStreamMicro var1) throws IOException {
      Iterator var2 = this.getMessageList().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.writeString(1, var3);
      }

      Iterator var4 = this.getTimingList().iterator();

      while(var4.hasNext()) {
         DebugInfo.Timing var5 = (DebugInfo.Timing)var4.next();
         var1.writeGroup(2, var5);
      }

   }

   public static final class Timing extends MessageMicro {

      public static final int NAME_FIELD_NUMBER = 3;
      public static final int TIME_IN_MS_FIELD_NUMBER = 4;
      private int cachedSize = -1;
      private boolean hasName;
      private boolean hasTimeInMs;
      private String name_ = "";
      private double timeInMs_ = 0.0D;


      public Timing() {}

      public final DebugInfo.Timing clear() {
         DebugInfo.Timing var1 = this.clearName();
         DebugInfo.Timing var2 = this.clearTimeInMs();
         this.cachedSize = -1;
         return this;
      }

      public DebugInfo.Timing clearName() {
         this.hasName = (boolean)0;
         this.name_ = "";
         return this;
      }

      public DebugInfo.Timing clearTimeInMs() {
         this.hasTimeInMs = (boolean)0;
         this.timeInMs_ = 0.0D;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getName() {
         return this.name_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasName()) {
            String var2 = this.getName();
            int var3 = CodedOutputStreamMicro.computeStringSize(3, var2);
            var1 = 0 + var3;
         }

         if(this.hasTimeInMs()) {
            double var4 = this.getTimeInMs();
            int var6 = CodedOutputStreamMicro.computeDoubleSize(4, var4);
            var1 += var6;
         }

         this.cachedSize = var1;
         return var1;
      }

      public double getTimeInMs() {
         return this.timeInMs_;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public boolean hasTimeInMs() {
         return this.hasTimeInMs;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasName && this.hasTimeInMs) {
            var1 = true;
         }

         return var1;
      }

      public DebugInfo.Timing mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 26:
               String var3 = var1.readString();
               this.setName(var3);
               break;
            case 33:
               double var5 = var1.readDouble();
               this.setTimeInMs(var5);
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

      public DebugInfo.Timing parseFrom(CodedInputStreamMicro var1) throws IOException {
         return (new DebugInfo.Timing()).mergeFrom(var1);
      }

      public DebugInfo.Timing parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
         return (DebugInfo.Timing)((DebugInfo.Timing)(new DebugInfo.Timing()).mergeFrom(var1));
      }

      public DebugInfo.Timing setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public DebugInfo.Timing setTimeInMs(double var1) {
         this.hasTimeInMs = (boolean)1;
         this.timeInMs_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasName()) {
            String var2 = this.getName();
            var1.writeString(3, var2);
         }

         if(this.hasTimeInMs()) {
            double var3 = this.getTimeInMs();
            var1.writeDouble(4, var3);
         }
      }
   }
}
