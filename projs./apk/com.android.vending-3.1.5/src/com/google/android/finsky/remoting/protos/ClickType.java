package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class ClickType extends MessageMicro {

   public static final int ACQUISITION = 3;
   public static final int BROWSE = 2;
   public static final int DOCUMENT_DETAILS = 1;
   public static final int FULL_PREVIEW = 7;
   public static final int LONG_PREVIEW = 8;
   public static final int MY_LIBRARY = 6;
   public static final int PURCHASE_HISTORY_LINK = 5;
   public static final int SHORT_PREVIEW = 9;
   public static final int SPELLING_LINK = 4;
   private int cachedSize = -1;


   public ClickType() {}

   public static ClickType parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new ClickType()).mergeFrom(var0);
   }

   public static ClickType parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (ClickType)((ClickType)(new ClickType()).mergeFrom(var0));
   }

   public final ClickType clear() {
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

   public ClickType mergeFrom(CodedInputStreamMicro var1) throws IOException {
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
