package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class SharedEnums extends MessageMicro {

   public static final int MATURE = 4;
   public static final int NONE = 0;
   public static final int OBB = 0;
   public static final int OBB_PATCH = 1;
   public static final int OBB_SUPPORT = 1;
   public static final int PRE_TEEN = 2;
   public static final int SUITABLE_FOR_ALL = 1;
   public static final int TEEN = 3;
   private int cachedSize = -1;


   public SharedEnums() {}

   public static SharedEnums parseFrom(CodedInputStreamMicro var0) throws IOException {
      return (new SharedEnums()).mergeFrom(var0);
   }

   public static SharedEnums parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
      return (SharedEnums)((SharedEnums)(new SharedEnums()).mergeFrom(var0));
   }

   public final SharedEnums clear() {
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

   public SharedEnums mergeFrom(CodedInputStreamMicro var1) throws IOException {
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
