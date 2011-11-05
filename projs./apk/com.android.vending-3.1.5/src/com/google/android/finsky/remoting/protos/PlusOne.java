package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;

public final class PlusOne {

   private PlusOne() {}

   public static final class PlusOneResponse extends MessageMicro {

      private int cachedSize = -1;


      public PlusOneResponse() {}

      public static PlusOne.PlusOneResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new PlusOne.PlusOneResponse()).mergeFrom(var0);
      }

      public static PlusOne.PlusOneResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (PlusOne.PlusOneResponse)((PlusOne.PlusOneResponse)(new PlusOne.PlusOneResponse()).mergeFrom(var0));
      }

      public final PlusOne.PlusOneResponse clear() {
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

      public PlusOne.PlusOneResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
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
}
