package com.google.protobuf.micro;

import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.MessageMicro;
import com.google.protobuf.micro.WireFormatMicro;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public final class CodedOutputStreamMicro {

   public static final int DEFAULT_BUFFER_SIZE = 4096;
   public static final int LITTLE_ENDIAN_32_SIZE = 4;
   public static final int LITTLE_ENDIAN_64_SIZE = 8;
   private final byte[] buffer;
   private final int limit;
   private final OutputStream output;
   private int position;


   private CodedOutputStreamMicro(OutputStream var1, byte[] var2) {
      this.output = var1;
      this.buffer = var2;
      this.position = 0;
      int var3 = var2.length;
      this.limit = var3;
   }

   private CodedOutputStreamMicro(byte[] var1, int var2, int var3) {
      this.output = null;
      this.buffer = var1;
      this.position = var2;
      int var4 = var2 + var3;
      this.limit = var4;
   }

   public static int computeBoolSize(int var0, boolean var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeBoolSizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeBoolSizeNoTag(boolean var0) {
      return 1;
   }

   public static int computeByteArraySize(int var0, byte[] var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeByteArraySizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeByteArraySizeNoTag(byte[] var0) {
      int var1 = computeRawVarint32Size(var0.length);
      int var2 = var0.length;
      return var1 + var2;
   }

   public static int computeBytesSize(int var0, ByteStringMicro var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeBytesSizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeBytesSizeNoTag(ByteStringMicro var0) {
      int var1 = computeRawVarint32Size(var0.size());
      int var2 = var0.size();
      return var1 + var2;
   }

   public static int computeDoubleSize(int var0, double var1) {
      int var3 = computeTagSize(var0);
      int var4 = computeDoubleSizeNoTag(var1);
      return var3 + var4;
   }

   public static int computeDoubleSizeNoTag(double var0) {
      return 8;
   }

   public static int computeEnumSize(int var0, int var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeEnumSizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeEnumSizeNoTag(int var0) {
      return computeRawVarint32Size(var0);
   }

   public static int computeFixed32Size(int var0, int var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeFixed32SizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeFixed32SizeNoTag(int var0) {
      return 4;
   }

   public static int computeFixed64Size(int var0, long var1) {
      int var3 = computeTagSize(var0);
      int var4 = computeFixed64SizeNoTag(var1);
      return var3 + var4;
   }

   public static int computeFixed64SizeNoTag(long var0) {
      return 8;
   }

   public static int computeFloatSize(int var0, float var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeFloatSizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeFloatSizeNoTag(float var0) {
      return 4;
   }

   public static int computeGroupSize(int var0, MessageMicro var1) {
      int var2 = computeTagSize(var0) * 2;
      int var3 = computeGroupSizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeGroupSizeNoTag(MessageMicro var0) {
      return var0.getCachedSize();
   }

   public static int computeInt32Size(int var0, int var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeInt32SizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeInt32SizeNoTag(int var0) {
      int var1;
      if(var0 >= 0) {
         var1 = computeRawVarint32Size(var0);
      } else {
         var1 = 10;
      }

      return var1;
   }

   public static int computeInt64Size(int var0, long var1) {
      int var3 = computeTagSize(var0);
      int var4 = computeInt64SizeNoTag(var1);
      return var3 + var4;
   }

   public static int computeInt64SizeNoTag(long var0) {
      return computeRawVarint64Size(var0);
   }

   public static int computeMessageSize(int var0, MessageMicro var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeMessageSizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeMessageSizeNoTag(MessageMicro var0) {
      int var1 = var0.getCachedSize();
      return computeRawVarint32Size(var1) + var1;
   }

   public static int computeRawVarint32Size(int var0) {
      byte var1;
      if((var0 & -128) == 0) {
         var1 = 1;
      } else if((var0 & -16384) == 0) {
         var1 = 2;
      } else if((-2097152 & var0) == 0) {
         var1 = 3;
      } else if((-268435456 & var0) == 0) {
         var1 = 4;
      } else {
         var1 = 5;
      }

      return var1;
   }

   public static int computeRawVarint64Size(long var0) {
      byte var2;
      if((65408L & var0) == 0L) {
         var2 = 1;
      } else if((49152L & var0) == 0L) {
         var2 = 2;
      } else if((-2097152L & var0) == 0L) {
         var2 = 3;
      } else if((-268435456L & var0) == 0L) {
         var2 = 4;
      } else if((-34359738368L & var0) == 0L) {
         var2 = 5;
      } else if((-4398046511104L & var0) == 0L) {
         var2 = 6;
      } else if((-562949953421312L & var0) == 0L) {
         var2 = 7;
      } else if((-72057594037927936L & var0) == 0L) {
         var2 = 8;
      } else if((Long.MIN_VALUE & var0) == 0L) {
         var2 = 9;
      } else {
         var2 = 10;
      }

      return var2;
   }

   public static int computeSFixed32Size(int var0, int var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeSFixed32SizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeSFixed32SizeNoTag(int var0) {
      return 4;
   }

   public static int computeSFixed64Size(int var0, long var1) {
      int var3 = computeTagSize(var0);
      int var4 = computeSFixed64SizeNoTag(var1);
      return var3 + var4;
   }

   public static int computeSFixed64SizeNoTag(long var0) {
      return 8;
   }

   public static int computeSInt32Size(int var0, int var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeSInt32SizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeSInt32SizeNoTag(int var0) {
      return computeRawVarint32Size(encodeZigZag32(var0));
   }

   public static int computeSInt64Size(int var0, long var1) {
      int var3 = computeTagSize(var0);
      int var4 = computeSInt64SizeNoTag(var1);
      return var3 + var4;
   }

   public static int computeSInt64SizeNoTag(long var0) {
      return computeRawVarint64Size(encodeZigZag64(var0));
   }

   public static int computeStringSize(int var0, String var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeStringSizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeStringSizeNoTag(String var0) {
      int var2;
      int var3;
      try {
         byte[] var1 = var0.getBytes("UTF-8");
         var2 = computeRawVarint32Size(var1.length);
         var3 = var1.length;
      } catch (UnsupportedEncodingException var5) {
         throw new RuntimeException("UTF-8 not supported.");
      }

      return var2 + var3;
   }

   public static int computeTagSize(int var0) {
      return computeRawVarint32Size(WireFormatMicro.makeTag(var0, 0));
   }

   public static int computeUInt32Size(int var0, int var1) {
      int var2 = computeTagSize(var0);
      int var3 = computeUInt32SizeNoTag(var1);
      return var2 + var3;
   }

   public static int computeUInt32SizeNoTag(int var0) {
      return computeRawVarint32Size(var0);
   }

   public static int computeUInt64Size(int var0, long var1) {
      int var3 = computeTagSize(var0);
      int var4 = computeUInt64SizeNoTag(var1);
      return var3 + var4;
   }

   public static int computeUInt64SizeNoTag(long var0) {
      return computeRawVarint64Size(var0);
   }

   public static int encodeZigZag32(int var0) {
      int var1 = var0 << 1;
      int var2 = var0 >> 31;
      return var1 ^ var2;
   }

   public static long encodeZigZag64(long var0) {
      long var2 = var0 << 1;
      long var4 = var0 >> 63;
      return var2 ^ var4;
   }

   public static CodedOutputStreamMicro newInstance(OutputStream var0) {
      return newInstance(var0, 4096);
   }

   public static CodedOutputStreamMicro newInstance(OutputStream var0, int var1) {
      byte[] var2 = new byte[var1];
      return new CodedOutputStreamMicro(var0, var2);
   }

   public static CodedOutputStreamMicro newInstance(byte[] var0) {
      int var1 = var0.length;
      return newInstance(var0, 0, var1);
   }

   public static CodedOutputStreamMicro newInstance(byte[] var0, int var1, int var2) {
      return new CodedOutputStreamMicro(var0, var1, var2);
   }

   private void refreshBuffer() throws IOException {
      if(this.output == null) {
         throw new CodedOutputStreamMicro.OutOfSpaceException();
      } else {
         OutputStream var1 = this.output;
         byte[] var2 = this.buffer;
         int var3 = this.position;
         var1.write(var2, 0, var3);
         this.position = 0;
      }
   }

   public void checkNoSpaceLeft() {
      if(this.spaceLeft() != 0) {
         throw new IllegalStateException("Did not write as much data as expected.");
      }
   }

   public void flush() throws IOException {
      if(this.output != null) {
         this.refreshBuffer();
      }
   }

   public int spaceLeft() {
      if(this.output == null) {
         int var1 = this.limit;
         int var2 = this.position;
         return var1 - var2;
      } else {
         throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array.");
      }
   }

   public void writeBool(int var1, boolean var2) throws IOException {
      this.writeTag(var1, 0);
      this.writeBoolNoTag(var2);
   }

   public void writeBoolNoTag(boolean var1) throws IOException {
      byte var2;
      if(var1) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      this.writeRawByte((int)var2);
   }

   public void writeByteArray(int var1, byte[] var2) throws IOException {
      this.writeTag(var1, 2);
      this.writeByteArrayNoTag(var2);
   }

   public void writeByteArrayNoTag(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.writeRawVarint32(var2);
      this.writeRawBytes(var1);
   }

   public void writeBytes(int var1, ByteStringMicro var2) throws IOException {
      this.writeTag(var1, 2);
      this.writeBytesNoTag(var2);
   }

   public void writeBytesNoTag(ByteStringMicro var1) throws IOException {
      byte[] var2 = var1.toByteArray();
      int var3 = var2.length;
      this.writeRawVarint32(var3);
      this.writeRawBytes(var2);
   }

   public void writeDouble(int var1, double var2) throws IOException {
      this.writeTag(var1, 1);
      this.writeDoubleNoTag(var2);
   }

   public void writeDoubleNoTag(double var1) throws IOException {
      long var3 = Double.doubleToLongBits(var1);
      this.writeRawLittleEndian64(var3);
   }

   public void writeEnum(int var1, int var2) throws IOException {
      this.writeTag(var1, 0);
      this.writeEnumNoTag(var2);
   }

   public void writeEnumNoTag(int var1) throws IOException {
      this.writeRawVarint32(var1);
   }

   public void writeFixed32(int var1, int var2) throws IOException {
      this.writeTag(var1, 5);
      this.writeFixed32NoTag(var2);
   }

   public void writeFixed32NoTag(int var1) throws IOException {
      this.writeRawLittleEndian32(var1);
   }

   public void writeFixed64(int var1, long var2) throws IOException {
      this.writeTag(var1, 1);
      this.writeFixed64NoTag(var2);
   }

   public void writeFixed64NoTag(long var1) throws IOException {
      this.writeRawLittleEndian64(var1);
   }

   public void writeFloat(int var1, float var2) throws IOException {
      this.writeTag(var1, 5);
      this.writeFloatNoTag(var2);
   }

   public void writeFloatNoTag(float var1) throws IOException {
      int var2 = Float.floatToIntBits(var1);
      this.writeRawLittleEndian32(var2);
   }

   public void writeGroup(int var1, MessageMicro var2) throws IOException {
      this.writeTag(var1, 3);
      this.writeGroupNoTag(var2);
      this.writeTag(var1, 4);
   }

   public void writeGroupNoTag(MessageMicro var1) throws IOException {
      var1.writeTo(this);
   }

   public void writeInt32(int var1, int var2) throws IOException {
      this.writeTag(var1, 0);
      this.writeInt32NoTag(var2);
   }

   public void writeInt32NoTag(int var1) throws IOException {
      if(var1 >= 0) {
         this.writeRawVarint32(var1);
      } else {
         long var2 = (long)var1;
         this.writeRawVarint64(var2);
      }
   }

   public void writeInt64(int var1, long var2) throws IOException {
      this.writeTag(var1, 0);
      this.writeInt64NoTag(var2);
   }

   public void writeInt64NoTag(long var1) throws IOException {
      this.writeRawVarint64(var1);
   }

   public void writeMessage(int var1, MessageMicro var2) throws IOException {
      this.writeTag(var1, 2);
      this.writeMessageNoTag(var2);
   }

   public void writeMessageNoTag(MessageMicro var1) throws IOException {
      int var2 = var1.getCachedSize();
      this.writeRawVarint32(var2);
      var1.writeTo(this);
   }

   public void writeRawByte(byte var1) throws IOException {
      int var2 = this.position;
      int var3 = this.limit;
      if(var2 == var3) {
         this.refreshBuffer();
      }

      byte[] var4 = this.buffer;
      int var5 = this.position;
      int var6 = var5 + 1;
      this.position = var6;
      var4[var5] = var1;
   }

   public void writeRawByte(int var1) throws IOException {
      byte var2 = (byte)var1;
      this.writeRawByte(var2);
   }

   public void writeRawBytes(byte[] var1) throws IOException {
      int var2 = var1.length;
      this.writeRawBytes(var1, 0, var2);
   }

   public void writeRawBytes(byte[] var1, int var2, int var3) throws IOException {
      int var4 = this.limit;
      int var5 = this.position;
      if(var4 - var5 >= var3) {
         byte[] var6 = this.buffer;
         int var7 = this.position;
         System.arraycopy(var1, var2, var6, var7, var3);
         int var8 = this.position + var3;
         this.position = var8;
      } else {
         int var9 = this.limit;
         int var10 = this.position;
         int var11 = var9 - var10;
         byte[] var12 = this.buffer;
         int var13 = this.position;
         System.arraycopy(var1, var2, var12, var13, var11);
         var2 += var11;
         var3 -= var11;
         int var14 = this.limit;
         this.position = var14;
         this.refreshBuffer();
         int var15 = this.limit;
         if(var3 <= var15) {
            byte[] var16 = this.buffer;
            System.arraycopy(var1, var2, var16, 0, var3);
            this.position = var3;
         } else {
            this.output.write(var1, var2, var3);
         }
      }
   }

   public void writeRawLittleEndian32(int var1) throws IOException {
      int var2 = var1 & 255;
      this.writeRawByte(var2);
      int var3 = var1 >> 8 & 255;
      this.writeRawByte(var3);
      int var4 = var1 >> 16 & 255;
      this.writeRawByte(var4);
      int var5 = var1 >> 24 & 255;
      this.writeRawByte(var5);
   }

   public void writeRawLittleEndian64(long var1) throws IOException {
      int var3 = (int)var1 & 255;
      this.writeRawByte(var3);
      int var4 = (int)(var1 >> 8) & 255;
      this.writeRawByte(var4);
      int var5 = (int)(var1 >> 16) & 255;
      this.writeRawByte(var5);
      int var6 = (int)(var1 >> 24) & 255;
      this.writeRawByte(var6);
      int var7 = (int)(var1 >> 32) & 255;
      this.writeRawByte(var7);
      int var8 = (int)(var1 >> 40) & 255;
      this.writeRawByte(var8);
      int var9 = (int)(var1 >> 48) & 255;
      this.writeRawByte(var9);
      int var10 = (int)(var1 >> 56) & 255;
      this.writeRawByte(var10);
   }

   public void writeRawVarint32(int var1) throws IOException {
      while((var1 & -128) != 0) {
         int var2 = var1 & 127 | 128;
         this.writeRawByte(var2);
         var1 >>>= 7;
      }

      this.writeRawByte(var1);
   }

   public void writeRawVarint64(long var1) throws IOException {
      while((65408L & var1) != 0L) {
         int var4 = (int)var1 & 127 | 128;
         this.writeRawByte(var4);
         var1 >>>= 7;
      }

      int var3 = (int)var1;
      this.writeRawByte(var3);
   }

   public void writeSFixed32(int var1, int var2) throws IOException {
      this.writeTag(var1, 5);
      this.writeSFixed32NoTag(var2);
   }

   public void writeSFixed32NoTag(int var1) throws IOException {
      this.writeRawLittleEndian32(var1);
   }

   public void writeSFixed64(int var1, long var2) throws IOException {
      this.writeTag(var1, 1);
      this.writeSFixed64NoTag(var2);
   }

   public void writeSFixed64NoTag(long var1) throws IOException {
      this.writeRawLittleEndian64(var1);
   }

   public void writeSInt32(int var1, int var2) throws IOException {
      this.writeTag(var1, 0);
      this.writeSInt32NoTag(var2);
   }

   public void writeSInt32NoTag(int var1) throws IOException {
      int var2 = encodeZigZag32(var1);
      this.writeRawVarint32(var2);
   }

   public void writeSInt64(int var1, long var2) throws IOException {
      this.writeTag(var1, 0);
      this.writeSInt64NoTag(var2);
   }

   public void writeSInt64NoTag(long var1) throws IOException {
      long var3 = encodeZigZag64(var1);
      this.writeRawVarint64(var3);
   }

   public void writeString(int var1, String var2) throws IOException {
      this.writeTag(var1, 2);
      this.writeStringNoTag(var2);
   }

   public void writeStringNoTag(String var1) throws IOException {
      byte[] var2 = var1.getBytes("UTF-8");
      int var3 = var2.length;
      this.writeRawVarint32(var3);
      this.writeRawBytes(var2);
   }

   public void writeTag(int var1, int var2) throws IOException {
      int var3 = WireFormatMicro.makeTag(var1, var2);
      this.writeRawVarint32(var3);
   }

   public void writeUInt32(int var1, int var2) throws IOException {
      this.writeTag(var1, 0);
      this.writeUInt32NoTag(var2);
   }

   public void writeUInt32NoTag(int var1) throws IOException {
      this.writeRawVarint32(var1);
   }

   public void writeUInt64(int var1, long var2) throws IOException {
      this.writeTag(var1, 0);
      this.writeUInt64NoTag(var2);
   }

   public void writeUInt64NoTag(long var1) throws IOException {
      this.writeRawVarint64(var1);
   }

   public static class OutOfSpaceException extends IOException {

      private static final long serialVersionUID = -6947486886997889499L;


      OutOfSpaceException() {
         super("CodedOutputStream was writing to a flat byte array and ran out of space.");
      }
   }
}
