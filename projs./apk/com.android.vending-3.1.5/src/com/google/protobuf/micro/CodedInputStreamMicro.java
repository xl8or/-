package com.google.protobuf.micro;

import com.google.protobuf.micro.ByteStringMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import com.google.protobuf.micro.WireFormatMicro;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public final class CodedInputStreamMicro {

   private static final int BUFFER_SIZE = 4096;
   private static final int DEFAULT_RECURSION_LIMIT = 64;
   private static final int DEFAULT_SIZE_LIMIT = 67108864;
   private final byte[] buffer;
   private int bufferPos;
   private int bufferSize;
   private int bufferSizeAfterLimit;
   private int currentLimit = Integer.MAX_VALUE;
   private final InputStream input;
   private int lastTag;
   private int recursionDepth;
   private int recursionLimit = 64;
   private int sizeLimit = 67108864;
   private int totalBytesRetired;


   private CodedInputStreamMicro(InputStream var1) {
      byte[] var2 = new byte[4096];
      this.buffer = var2;
      this.bufferSize = 0;
      this.bufferPos = 0;
      this.input = var1;
   }

   private CodedInputStreamMicro(byte[] var1, int var2, int var3) {
      this.buffer = var1;
      int var4 = var2 + var3;
      this.bufferSize = var4;
      this.bufferPos = var2;
      this.input = null;
   }

   public static int decodeZigZag32(int var0) {
      int var1 = var0 >>> 1;
      int var2 = -(var0 & 1);
      return var1 ^ var2;
   }

   public static long decodeZigZag64(long var0) {
      long var2 = var0 >>> 1;
      long var4 = -(1L & var0);
      return var2 ^ var4;
   }

   public static CodedInputStreamMicro newInstance(InputStream var0) {
      return new CodedInputStreamMicro(var0);
   }

   public static CodedInputStreamMicro newInstance(byte[] var0) {
      int var1 = var0.length;
      return newInstance(var0, 0, var1);
   }

   public static CodedInputStreamMicro newInstance(byte[] var0, int var1, int var2) {
      return new CodedInputStreamMicro(var0, var1, var2);
   }

   static int readRawVarint32(InputStream param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private void recomputeBufferSizeAfterLimit() {
      int var1 = this.bufferSize;
      int var2 = this.bufferSizeAfterLimit;
      int var3 = var1 + var2;
      this.bufferSize = var3;
      int var4 = this.totalBytesRetired;
      int var5 = this.bufferSize;
      int var6 = var4 + var5;
      int var7 = this.currentLimit;
      if(var6 > var7) {
         int var8 = this.currentLimit;
         int var9 = var6 - var8;
         this.bufferSizeAfterLimit = var9;
         int var10 = this.bufferSize;
         int var11 = this.bufferSizeAfterLimit;
         int var12 = var10 - var11;
         this.bufferSize = var12;
      } else {
         this.bufferSizeAfterLimit = 0;
      }
   }

   private boolean refillBuffer(boolean var1) throws IOException {
      int var2 = this.bufferPos;
      int var3 = this.bufferSize;
      if(var2 < var3) {
         throw new IllegalStateException("refillBuffer() called when buffer wasn\'t empty.");
      } else {
         int var4 = this.totalBytesRetired;
         int var5 = this.bufferSize;
         int var6 = var4 + var5;
         int var7 = this.currentLimit;
         boolean var8;
         if(var6 == var7) {
            if(var1) {
               throw InvalidProtocolBufferMicroException.truncatedMessage();
            }

            var8 = false;
         } else {
            int var9 = this.totalBytesRetired;
            int var10 = this.bufferSize;
            int var11 = var9 + var10;
            this.totalBytesRetired = var11;
            this.bufferPos = 0;
            int var12;
            if(this.input == null) {
               var12 = -1;
            } else {
               InputStream var16 = this.input;
               byte[] var17 = this.buffer;
               var12 = var16.read(var17);
            }

            this.bufferSize = var12;
            if(this.bufferSize == 0 || this.bufferSize < -1) {
               StringBuilder var13 = (new StringBuilder()).append("InputStream#read(byte[]) returned invalid result: ");
               int var14 = this.bufferSize;
               String var15 = var13.append(var14).append("\nThe InputStream implementation is buggy.").toString();
               throw new IllegalStateException(var15);
            }

            if(this.bufferSize == -1) {
               this.bufferSize = 0;
               if(var1) {
                  throw InvalidProtocolBufferMicroException.truncatedMessage();
               }

               var8 = false;
            } else {
               this.recomputeBufferSizeAfterLimit();
               int var18 = this.totalBytesRetired;
               int var19 = this.bufferSize;
               int var20 = var18 + var19;
               int var21 = this.bufferSizeAfterLimit;
               int var22 = var20 + var21;
               int var23 = this.sizeLimit;
               if(var22 > var23 || var22 < 0) {
                  throw InvalidProtocolBufferMicroException.sizeLimitExceeded();
               }

               var8 = true;
            }
         }

         return var8;
      }
   }

   public void checkLastTagWas(int var1) throws InvalidProtocolBufferMicroException {
      if(this.lastTag != var1) {
         throw InvalidProtocolBufferMicroException.invalidEndTag();
      }
   }

   public int getBytesUntilLimit() {
      int var1;
      if(this.currentLimit == Integer.MAX_VALUE) {
         var1 = -1;
      } else {
         int var2 = this.totalBytesRetired;
         int var3 = this.bufferPos;
         int var4 = var2 + var3;
         var1 = this.currentLimit - var4;
      }

      return var1;
   }

   public boolean isAtEnd() throws IOException {
      boolean var1 = false;
      int var2 = this.bufferPos;
      int var3 = this.bufferSize;
      if(var2 == var3 && !this.refillBuffer((boolean)0)) {
         var1 = true;
      }

      return var1;
   }

   public void popLimit(int var1) {
      this.currentLimit = var1;
      this.recomputeBufferSizeAfterLimit();
   }

   public int pushLimit(int var1) throws InvalidProtocolBufferMicroException {
      if(var1 < 0) {
         throw InvalidProtocolBufferMicroException.negativeSize();
      } else {
         int var2 = this.totalBytesRetired;
         int var3 = this.bufferPos;
         int var4 = var2 + var3;
         int var5 = var1 + var4;
         int var6 = this.currentLimit;
         if(var5 > var6) {
            throw InvalidProtocolBufferMicroException.truncatedMessage();
         } else {
            this.currentLimit = var5;
            this.recomputeBufferSizeAfterLimit();
            return var6;
         }
      }
   }

   public boolean readBool() throws IOException {
      boolean var1;
      if(this.readRawVarint32() != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public ByteStringMicro readBytes() throws IOException {
      int var1 = this.readRawVarint32();
      int var2 = this.bufferSize;
      int var3 = this.bufferPos;
      int var4 = var2 - var3;
      ByteStringMicro var7;
      if(var1 <= var4 && var1 > 0) {
         byte[] var5 = this.buffer;
         int var6 = this.bufferPos;
         var7 = ByteStringMicro.copyFrom(var5, var6, var1);
         int var8 = this.bufferPos + var1;
         this.bufferPos = var8;
      } else {
         var7 = ByteStringMicro.copyFrom(this.readRawBytes(var1));
      }

      return var7;
   }

   public double readDouble() throws IOException {
      return Double.longBitsToDouble(this.readRawLittleEndian64());
   }

   public int readEnum() throws IOException {
      return this.readRawVarint32();
   }

   public int readFixed32() throws IOException {
      return this.readRawLittleEndian32();
   }

   public long readFixed64() throws IOException {
      return this.readRawLittleEndian64();
   }

   public float readFloat() throws IOException {
      return Float.intBitsToFloat(this.readRawLittleEndian32());
   }

   public void readGroup(MessageMicro var1, int var2) throws IOException {
      int var3 = this.recursionDepth;
      int var4 = this.recursionLimit;
      if(var3 >= var4) {
         throw InvalidProtocolBufferMicroException.recursionLimitExceeded();
      } else {
         int var5 = this.recursionDepth + 1;
         this.recursionDepth = var5;
         var1.mergeFrom(this);
         int var7 = WireFormatMicro.makeTag(var2, 4);
         this.checkLastTagWas(var7);
         int var8 = this.recursionDepth + -1;
         this.recursionDepth = var8;
      }
   }

   public int readInt32() throws IOException {
      return this.readRawVarint32();
   }

   public long readInt64() throws IOException {
      return this.readRawVarint64();
   }

   public void readMessage(MessageMicro var1) throws IOException {
      int var2 = this.readRawVarint32();
      int var3 = this.recursionDepth;
      int var4 = this.recursionLimit;
      if(var3 >= var4) {
         throw InvalidProtocolBufferMicroException.recursionLimitExceeded();
      } else {
         int var5 = this.pushLimit(var2);
         int var6 = this.recursionDepth + 1;
         this.recursionDepth = var6;
         var1.mergeFrom(this);
         this.checkLastTagWas(0);
         int var8 = this.recursionDepth + -1;
         this.recursionDepth = var8;
         this.popLimit(var5);
      }
   }

   public byte readRawByte() throws IOException {
      int var1 = this.bufferPos;
      int var2 = this.bufferSize;
      if(var1 == var2) {
         boolean var3 = this.refillBuffer((boolean)1);
      }

      byte[] var4 = this.buffer;
      int var5 = this.bufferPos;
      int var6 = var5 + 1;
      this.bufferPos = var6;
      return var4[var5];
   }

   public byte[] readRawBytes(int var1) throws IOException {
      if(var1 < 0) {
         throw InvalidProtocolBufferMicroException.negativeSize();
      } else {
         int var2 = this.totalBytesRetired;
         int var3 = this.bufferPos;
         int var4 = var2 + var3 + var1;
         int var5 = this.currentLimit;
         if(var4 > var5) {
            int var6 = this.currentLimit;
            int var7 = this.totalBytesRetired;
            int var8 = var6 - var7;
            int var9 = this.bufferPos;
            int var10 = var8 - var9;
            this.skipRawBytes(var10);
            throw InvalidProtocolBufferMicroException.truncatedMessage();
         } else {
            int var11 = this.bufferSize;
            int var12 = this.bufferPos;
            int var13 = var11 - var12;
            byte[] var14;
            if(var1 <= var13) {
               var14 = new byte[var1];
               byte[] var15 = this.buffer;
               int var16 = this.bufferPos;
               System.arraycopy(var15, var16, var14, 0, var1);
               int var17 = this.bufferPos + var1;
               this.bufferPos = var17;
            } else {
               int var20;
               if(var1 < 4096) {
                  var14 = new byte[var1];
                  int var18 = this.bufferSize;
                  int var19 = this.bufferPos;
                  var20 = var18 - var19;
                  byte[] var21 = this.buffer;
                  int var22 = this.bufferPos;
                  System.arraycopy(var21, var22, var14, 0, var20);
                  int var23 = this.bufferSize;
                  this.bufferPos = var23;
                  boolean var24 = this.refillBuffer((boolean)1);

                  while(true) {
                     int var25 = var1 - var20;
                     int var26 = this.bufferSize;
                     if(var25 <= var26) {
                        byte[] var32 = this.buffer;
                        int var33 = var1 - var20;
                        System.arraycopy(var32, 0, var14, var20, var33);
                        int var34 = var1 - var20;
                        this.bufferPos = var34;
                        break;
                     }

                     byte[] var27 = this.buffer;
                     int var28 = this.bufferSize;
                     System.arraycopy(var27, 0, var14, var20, var28);
                     int var29 = this.bufferSize;
                     var20 += var29;
                     int var30 = this.bufferSize;
                     this.bufferPos = var30;
                     boolean var31 = this.refillBuffer((boolean)1);
                  }
               } else {
                  int var35 = this.bufferPos;
                  int var36 = this.bufferSize;
                  int var37 = this.totalBytesRetired;
                  int var38 = this.bufferSize;
                  int var39 = var37 + var38;
                  this.totalBytesRetired = var39;
                  this.bufferPos = 0;
                  this.bufferSize = 0;
                  int var40 = var36 - var35;
                  int var41 = var1 - var40;
                  Vector var42 = new Vector();

                  while(var41 > 0) {
                     byte[] var43 = new byte[Math.min(var41, 4096)];
                     int var44 = 0;

                     while(true) {
                        int var45 = var43.length;
                        if(var44 >= var45) {
                           int var50 = var43.length;
                           var41 -= var50;
                           var42.addElement(var43);
                           break;
                        }

                        int var46;
                        if(this.input == null) {
                           var46 = -1;
                        } else {
                           InputStream var47 = this.input;
                           int var48 = var43.length - var44;
                           var46 = var47.read(var43, var44, var48);
                        }

                        if(var46 == -1) {
                           throw InvalidProtocolBufferMicroException.truncatedMessage();
                        }

                        int var49 = this.totalBytesRetired + var46;
                        this.totalBytesRetired = var49;
                        var44 += var46;
                     }
                  }

                  var14 = new byte[var1];
                  var20 = var36 - var35;
                  System.arraycopy(this.buffer, var35, var14, 0, var20);
                  int var51 = 0;

                  while(true) {
                     int var52 = var42.size();
                     if(var51 >= var52) {
                        break;
                     }

                     byte[] var53 = (byte[])((byte[])var42.elementAt(var51));
                     int var54 = var53.length;
                     System.arraycopy(var53, 0, var14, var20, var54);
                     int var55 = var53.length;
                     var20 += var55;
                     ++var51;
                  }
               }
            }

            return var14;
         }
      }
   }

   public int readRawLittleEndian32() throws IOException {
      byte var1 = this.readRawByte();
      byte var2 = this.readRawByte();
      byte var3 = this.readRawByte();
      byte var4 = this.readRawByte();
      int var5 = var1 & 255;
      int var6 = (var2 & 255) << 8;
      int var7 = var5 | var6;
      int var8 = (var3 & 255) << 16;
      int var9 = var7 | var8;
      int var10 = (var4 & 255) << 24;
      return var9 | var10;
   }

   public long readRawLittleEndian64() throws IOException {
      byte var1 = this.readRawByte();
      byte var2 = this.readRawByte();
      byte var3 = this.readRawByte();
      byte var4 = this.readRawByte();
      byte var5 = this.readRawByte();
      byte var6 = this.readRawByte();
      byte var7 = this.readRawByte();
      byte var8 = this.readRawByte();
      long var9 = (long)var1 & 255L;
      long var11 = ((long)var2 & 255L) << 8;
      long var13 = var9 | var11;
      long var15 = ((long)var3 & 255L) << 16;
      long var17 = var13 | var15;
      long var19 = ((long)var4 & 255L) << 24;
      long var21 = var17 | var19;
      long var23 = ((long)var5 & 255L) << 32;
      long var25 = var21 | var23;
      long var27 = ((long)var6 & 255L) << 40;
      long var29 = var25 | var27;
      long var31 = ((long)var7 & 255L) << 48;
      long var33 = var29 | var31;
      long var35 = ((long)var8 & 255L) << 56;
      return var33 | var35;
   }

   public int readRawVarint32() throws IOException {
      byte var1 = this.readRawByte();
      int var2;
      if(var1 >= 0) {
         var2 = var1;
      } else {
         int var3 = var1 & 127;
         var1 = this.readRawByte();
         if(var1 >= 0) {
            int var4 = var1 << 7;
            var2 = var3 | var4;
         } else {
            int var5 = (var1 & 127) << 7;
            int var6 = var3 | var5;
            var1 = this.readRawByte();
            if(var1 >= 0) {
               int var7 = var1 << 14;
               var2 = var6 | var7;
            } else {
               int var8 = (var1 & 127) << 14;
               int var9 = var6 | var8;
               var1 = this.readRawByte();
               if(var1 >= 0) {
                  int var10 = var1 << 21;
                  var2 = var9 | var10;
               } else {
                  int var11 = (var1 & 127) << 21;
                  int var12 = var9 | var11;
                  byte var13 = this.readRawByte();
                  int var14 = var13 << 28;
                  var2 = var12 | var14;
                  if(var13 < 0) {
                     int var15 = 0;

                     while(true) {
                        if(var15 >= 5) {
                           throw InvalidProtocolBufferMicroException.malformedVarint();
                        }

                        if(this.readRawByte() >= 0) {
                           break;
                        }

                        ++var15;
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   public long readRawVarint64() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public int readSFixed32() throws IOException {
      return this.readRawLittleEndian32();
   }

   public long readSFixed64() throws IOException {
      return this.readRawLittleEndian64();
   }

   public int readSInt32() throws IOException {
      return decodeZigZag32(this.readRawVarint32());
   }

   public long readSInt64() throws IOException {
      return decodeZigZag64(this.readRawVarint64());
   }

   public String readString() throws IOException {
      int var1 = this.readRawVarint32();
      int var2 = this.bufferSize;
      int var3 = this.bufferPos;
      int var4 = var2 - var3;
      String var7;
      if(var1 <= var4 && var1 > 0) {
         byte[] var5 = this.buffer;
         int var6 = this.bufferPos;
         var7 = new String(var5, var6, var1, "UTF-8");
         int var8 = this.bufferPos + var1;
         this.bufferPos = var8;
      } else {
         byte[] var9 = this.readRawBytes(var1);
         var7 = new String(var9, "UTF-8");
      }

      return var7;
   }

   public int readTag() throws IOException {
      int var1 = 0;
      if(this.isAtEnd()) {
         this.lastTag = 0;
      } else {
         int var2 = this.readRawVarint32();
         this.lastTag = var2;
         if(this.lastTag == 0) {
            throw InvalidProtocolBufferMicroException.invalidTag();
         }

         var1 = this.lastTag;
      }

      return var1;
   }

   public int readUInt32() throws IOException {
      return this.readRawVarint32();
   }

   public long readUInt64() throws IOException {
      return this.readRawVarint64();
   }

   public void resetSizeCounter() {
      this.totalBytesRetired = 0;
   }

   public int setRecursionLimit(int var1) {
      if(var1 < 0) {
         String var2 = "Recursion limit cannot be negative: " + var1;
         throw new IllegalArgumentException(var2);
      } else {
         int var3 = this.recursionLimit;
         this.recursionLimit = var1;
         return var3;
      }
   }

   public int setSizeLimit(int var1) {
      if(var1 < 0) {
         String var2 = "Size limit cannot be negative: " + var1;
         throw new IllegalArgumentException(var2);
      } else {
         int var3 = this.sizeLimit;
         this.sizeLimit = var1;
         return var3;
      }
   }

   public boolean skipField(int var1) throws IOException {
      boolean var2 = true;
      switch(WireFormatMicro.getTagWireType(var1)) {
      case 0:
         int var3 = this.readInt32();
         break;
      case 1:
         long var4 = this.readRawLittleEndian64();
         break;
      case 2:
         int var6 = this.readRawVarint32();
         this.skipRawBytes(var6);
         break;
      case 3:
         this.skipMessage();
         int var7 = WireFormatMicro.makeTag(WireFormatMicro.getTagFieldNumber(var1), 4);
         this.checkLastTagWas(var7);
         break;
      case 4:
         var2 = false;
         break;
      case 5:
         int var8 = this.readRawLittleEndian32();
         break;
      default:
         throw InvalidProtocolBufferMicroException.invalidWireType();
      }

      return var2;
   }

   public void skipMessage() throws IOException {
      int var1;
      do {
         var1 = this.readTag();
         if(var1 == 0) {
            return;
         }
      } while(this.skipField(var1));

   }

   public void skipRawBytes(int var1) throws IOException {
      if(var1 < 0) {
         throw InvalidProtocolBufferMicroException.negativeSize();
      } else {
         int var2 = this.totalBytesRetired;
         int var3 = this.bufferPos;
         int var4 = var2 + var3 + var1;
         int var5 = this.currentLimit;
         if(var4 > var5) {
            int var6 = this.currentLimit;
            int var7 = this.totalBytesRetired;
            int var8 = var6 - var7;
            int var9 = this.bufferPos;
            int var10 = var8 - var9;
            this.skipRawBytes(var10);
            throw InvalidProtocolBufferMicroException.truncatedMessage();
         } else {
            int var11 = this.bufferSize;
            int var12 = this.bufferPos;
            int var13 = var11 - var12;
            if(var1 <= var13) {
               int var14 = this.bufferPos + var1;
               this.bufferPos = var14;
            } else {
               int var15 = this.bufferSize;
               int var16 = this.bufferPos;
               int var17 = var15 - var16;
               int var18 = this.totalBytesRetired;
               int var19 = this.bufferSize;
               int var20 = var18 + var19;
               this.totalBytesRetired = var20;
               this.bufferPos = 0;

               int var25;
               for(this.bufferSize = 0; var17 < var1; this.totalBytesRetired = var25) {
                  int var21;
                  if(this.input == null) {
                     var21 = -1;
                  } else {
                     InputStream var22 = this.input;
                     long var23 = (long)(var1 - var17);
                     var21 = (int)var22.skip(var23);
                  }

                  if(var21 <= 0) {
                     throw InvalidProtocolBufferMicroException.truncatedMessage();
                  }

                  var17 += var21;
                  var25 = this.totalBytesRetired + var21;
               }

            }
         }
      }
   }
}
