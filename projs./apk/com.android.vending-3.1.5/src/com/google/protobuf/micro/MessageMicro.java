package com.google.protobuf.micro;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import java.io.IOException;

public abstract class MessageMicro {

   public MessageMicro() {}

   public abstract int getCachedSize();

   public abstract int getSerializedSize();

   public abstract MessageMicro mergeFrom(CodedInputStreamMicro var1) throws IOException;

   public MessageMicro mergeFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
      int var2 = var1.length;
      return this.mergeFrom(var1, 0, var2);
   }

   public MessageMicro mergeFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferMicroException {
      try {
         CodedInputStreamMicro var4 = CodedInputStreamMicro.newInstance(var1, var2, var3);
         this.mergeFrom(var4);
         var4.checkLastTagWas(0);
         return this;
      } catch (InvalidProtocolBufferMicroException var7) {
         throw var7;
      } catch (IOException var8) {
         throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
      }
   }

   protected boolean parseUnknownField(CodedInputStreamMicro var1, int var2) throws IOException {
      return var1.skipField(var2);
   }

   public void toByteArray(byte[] var1, int var2, int var3) {
      try {
         CodedOutputStreamMicro var4 = CodedOutputStreamMicro.newInstance(var1, var2, var3);
         this.writeTo(var4);
         var4.checkNoSpaceLeft();
      } catch (IOException var6) {
         throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).");
      }
   }

   public byte[] toByteArray() {
      byte[] var1 = new byte[this.getSerializedSize()];
      int var2 = var1.length;
      this.toByteArray(var1, 0, var2);
      return var1;
   }

   public abstract void writeTo(CodedOutputStreamMicro var1) throws IOException;
}
