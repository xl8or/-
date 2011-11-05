package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteProcessor;
import com.google.common.io.Closeables;
import com.google.common.io.InputSupplier;
import com.google.common.io.LimitInputStream;
import com.google.common.io.MultiInputStream;
import com.google.common.io.OutputSupplier;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.Checksum;

public final class ByteStreams {

   private static final int BUF_SIZE = 4096;


   private ByteStreams() {}

   public static long copy(InputSupplier<? extends InputStream> param0, OutputSupplier<? extends OutputStream> param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static long copy(InputSupplier<? extends InputStream> var0, OutputStream var1) throws IOException {
      InputStream var2 = (InputStream)var0.getInput();
      boolean var9 = false;

      long var3;
      try {
         var9 = true;
         var3 = copy(var2, var1);
         var9 = false;
      } finally {
         if(var9) {
            Closeables.close(var2, (boolean)1);
         }
      }

      Closeables.close(var2, (boolean)0);
      return var3;
   }

   public static long copy(InputStream var0, OutputStream var1) throws IOException {
      byte[] var2 = new byte[4096];
      long var3 = 0L;

      while(true) {
         int var5 = var0.read(var2);
         if(var5 == -1) {
            return var3;
         }

         var1.write(var2, 0, var5);
         long var6 = (long)var5;
         var3 += var6;
      }
   }

   public static long copy(ReadableByteChannel var0, WritableByteChannel var1) throws IOException {
      ByteBuffer var2 = ByteBuffer.allocate(4096);

      long var3;
      long var6;
      Buffer var8;
      for(var3 = 0L; var0.read(var2) != -1; var8 = var2.clear()) {
         for(Buffer var5 = var2.flip(); var2.hasRemaining(); var3 += var6) {
            var6 = (long)var1.write(var2);
         }
      }

      return var3;
   }

   public static boolean equal(InputSupplier<? extends InputStream> param0, InputSupplier<? extends InputStream> param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static long getChecksum(InputSupplier<? extends InputStream> var0, Checksum var1) throws IOException {
      ByteStreams.2 var2 = new ByteStreams.2(var1);
      return ((Long)readBytes(var0, var2)).longValue();
   }

   public static byte[] getDigest(InputSupplier<? extends InputStream> var0, MessageDigest var1) throws IOException {
      ByteStreams.3 var2 = new ByteStreams.3(var1);
      return (byte[])readBytes(var0, var2);
   }

   public static InputSupplier<InputStream> join(Iterable<? extends InputSupplier<? extends InputStream>> var0) {
      return new ByteStreams.5(var0);
   }

   public static InputSupplier<InputStream> join(InputSupplier<? extends InputStream> ... var0) {
      return join((Iterable)Arrays.asList(var0));
   }

   public static long length(InputSupplier<? extends InputStream> var0) throws IOException {
      long var1 = 0L;
      InputStream var3 = (InputStream)var0.getInput();

      while(true) {
         long var4 = 2147483647L;
         boolean var11 = false;

         long var6;
         label57: {
            int var8;
            try {
               var11 = true;
               var6 = var3.skip(var4);
               if(var6 != 0L) {
                  var11 = false;
                  break label57;
               }

               var8 = var3.read();
               var11 = false;
            } finally {
               if(var11) {
                  Closeables.close(var3, (boolean)1);
               }
            }

            if(var8 == -1) {
               Closeables.close(var3, (boolean)0);
               return var1;
            }

            ++var1;
            continue;
         }

         var1 += var6;
      }
   }

   public static ByteArrayDataInput newDataInput(byte[] var0) {
      return new ByteStreams.ByteArrayDataInputStream(var0);
   }

   public static ByteArrayDataInput newDataInput(byte[] var0, int var1) {
      int var2 = var0.length;
      Preconditions.checkPositionIndex(var1, var2);
      return new ByteStreams.ByteArrayDataInputStream(var0, var1);
   }

   public static ByteArrayDataOutput newDataOutput() {
      return new ByteStreams.ByteArrayDataOutputStream();
   }

   public static ByteArrayDataOutput newDataOutput(int var0) {
      byte var1;
      if(var0 >= 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      Object[] var2 = new Object[1];
      Integer var3 = Integer.valueOf(var0);
      var2[0] = var3;
      Preconditions.checkArgument((boolean)var1, "Invalid size: %s", var2);
      return new ByteStreams.ByteArrayDataOutputStream(var0);
   }

   public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(byte[] var0) {
      int var1 = var0.length;
      return newInputStreamSupplier(var0, 0, var1);
   }

   public static InputSupplier<ByteArrayInputStream> newInputStreamSupplier(byte[] var0, int var1, int var2) {
      return new ByteStreams.1(var0, var1, var2);
   }

   public static int read(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      if(var3 < 0) {
         throw new IndexOutOfBoundsException("len is negative");
      } else {
         int var4;
         int var7;
         for(var4 = 0; var4 < var3; var4 += var7) {
            int var5 = var2 + var4;
            int var6 = var3 - var4;
            var7 = var0.read(var1, var5, var6);
            if(var7 == -1) {
               break;
            }
         }

         return var4;
      }
   }

   public static <T extends Object> T readBytes(InputSupplier<? extends InputStream> param0, ByteProcessor<T> param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public static void readFully(InputStream var0, byte[] var1) throws IOException {
      int var2 = var1.length;
      readFully(var0, var1, 0, var2);
   }

   public static void readFully(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      if(read(var0, var1, var2, var3) != var3) {
         throw new EOFException();
      }
   }

   public static void skipFully(InputStream var0, long var1) throws IOException {
      while(var1 > 0L) {
         long var3 = var0.skip(var1);
         if(var3 == 0L) {
            if(var0.read() == -1) {
               throw new EOFException();
            }

            --var1;
         } else {
            var1 -= var3;
         }
      }

   }

   public static InputSupplier<InputStream> slice(InputSupplier<? extends InputStream> var0, long var1, long var3) {
      byte var5 = 1;
      Object var6 = Preconditions.checkNotNull(var0);
      byte var7;
      if(var1 >= 0L) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      Preconditions.checkArgument((boolean)var7, "offset is negative");
      if(var3 < 0L) {
         var5 = 0;
      }

      Preconditions.checkArgument((boolean)var5, "length is negative");
      return new ByteStreams.4(var0, var1, var3);
   }

   public static byte[] toByteArray(InputSupplier<? extends InputStream> var0) throws IOException {
      InputStream var1 = (InputStream)var0.getInput();
      boolean var6 = false;

      byte[] var2;
      try {
         var6 = true;
         var2 = toByteArray(var1);
         var6 = false;
      } finally {
         if(var6) {
            Closeables.close(var1, (boolean)1);
         }
      }

      Closeables.close(var1, (boolean)0);
      return var2;
   }

   public static byte[] toByteArray(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      copy(var0, (OutputStream)var1);
      return var1.toByteArray();
   }

   public static void write(byte[] var0, OutputSupplier<? extends OutputStream> var1) throws IOException {
      Object var2 = Preconditions.checkNotNull(var0);
      OutputStream var3 = (OutputStream)var1.getOutput();
      boolean var6 = false;

      try {
         var6 = true;
         var3.write(var0);
         var6 = false;
      } finally {
         if(var6) {
            Closeables.close(var3, (boolean)1);
         }
      }

      Closeables.close(var3, (boolean)0);
   }

   static class 1 implements InputSupplier<ByteArrayInputStream> {

      // $FF: synthetic field
      final byte[] val$b;
      // $FF: synthetic field
      final int val$len;
      // $FF: synthetic field
      final int val$off;


      1(byte[] var1, int var2, int var3) {
         this.val$b = var1;
         this.val$off = var2;
         this.val$len = var3;
      }

      public ByteArrayInputStream getInput() {
         byte[] var1 = this.val$b;
         int var2 = this.val$off;
         int var3 = this.val$len;
         return new ByteArrayInputStream(var1, var2, var3);
      }
   }

   private static class ByteArrayDataOutputStream implements ByteArrayDataOutput {

      final ByteArrayOutputStream byteArrayOutputSteam;
      final DataOutput output;


      ByteArrayDataOutputStream() {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         this(var1);
      }

      ByteArrayDataOutputStream(int var1) {
         ByteArrayOutputStream var2 = new ByteArrayOutputStream(var1);
         this(var2);
      }

      ByteArrayDataOutputStream(ByteArrayOutputStream var1) {
         this.byteArrayOutputSteam = var1;
         DataOutputStream var2 = new DataOutputStream(var1);
         this.output = var2;
      }

      public byte[] toByteArray() {
         return this.byteArrayOutputSteam.toByteArray();
      }

      public void write(int var1) {
         try {
            this.output.write(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void write(byte[] var1) {
         try {
            this.output.write(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void write(byte[] var1, int var2, int var3) {
         try {
            this.output.write(var1, var2, var3);
         } catch (IOException var5) {
            throw new AssertionError(var5);
         }
      }

      public void writeBoolean(boolean var1) {
         try {
            this.output.writeBoolean(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeByte(int var1) {
         try {
            this.output.writeByte(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeBytes(String var1) {
         try {
            this.output.writeBytes(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeChar(int var1) {
         try {
            this.output.writeChar(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeChars(String var1) {
         try {
            this.output.writeChars(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeDouble(double var1) {
         try {
            this.output.writeDouble(var1);
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }

      public void writeFloat(float var1) {
         try {
            this.output.writeFloat(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeInt(int var1) {
         try {
            this.output.writeInt(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeLong(long var1) {
         try {
            this.output.writeLong(var1);
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }

      public void writeShort(int var1) {
         try {
            this.output.writeShort(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeUTF(String var1) {
         try {
            this.output.writeUTF(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }
   }

   static class 3 implements ByteProcessor<byte[]> {

      // $FF: synthetic field
      final MessageDigest val$md;


      3(MessageDigest var1) {
         this.val$md = var1;
      }

      public byte[] getResult() {
         return this.val$md.digest();
      }

      public boolean processBytes(byte[] var1, int var2, int var3) {
         this.val$md.update(var1, var2, var3);
         return true;
      }
   }

   static class 2 implements ByteProcessor<Long> {

      // $FF: synthetic field
      final Checksum val$checksum;


      2(Checksum var1) {
         this.val$checksum = var1;
      }

      public Long getResult() {
         long var1 = this.val$checksum.getValue();
         this.val$checksum.reset();
         return Long.valueOf(var1);
      }

      public boolean processBytes(byte[] var1, int var2, int var3) {
         this.val$checksum.update(var1, var2, var3);
         return true;
      }
   }

   static class 5 implements InputSupplier<InputStream> {

      // $FF: synthetic field
      final Iterable val$suppliers;


      5(Iterable var1) {
         this.val$suppliers = var1;
      }

      public InputStream getInput() throws IOException {
         Iterator var1 = this.val$suppliers.iterator();
         return new MultiInputStream(var1);
      }
   }

   static class 4 implements InputSupplier<InputStream> {

      // $FF: synthetic field
      final long val$length;
      // $FF: synthetic field
      final long val$offset;
      // $FF: synthetic field
      final InputSupplier val$supplier;


      4(InputSupplier var1, long var2, long var4) {
         this.val$supplier = var1;
         this.val$offset = var2;
         this.val$length = var4;
      }

      public InputStream getInput() throws IOException {
         InputStream var1 = (InputStream)this.val$supplier.getInput();
         if(this.val$offset > 0L) {
            try {
               long var2 = this.val$offset;
               ByteStreams.skipFully(var1, var2);
            } catch (IOException var7) {
               Closeables.closeQuietly(var1);
               throw var7;
            }
         }

         long var4 = this.val$length;
         return new LimitInputStream(var1, var4);
      }
   }

   private static class ByteArrayDataInputStream implements ByteArrayDataInput {

      final DataInput input;


      ByteArrayDataInputStream(byte[] var1) {
         ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
         DataInputStream var3 = new DataInputStream(var2);
         this.input = var3;
      }

      ByteArrayDataInputStream(byte[] var1, int var2) {
         int var3 = var1.length - var2;
         ByteArrayInputStream var4 = new ByteArrayInputStream(var1, var2, var3);
         DataInputStream var5 = new DataInputStream(var4);
         this.input = var5;
      }

      public boolean readBoolean() {
         try {
            boolean var1 = this.input.readBoolean();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public byte readByte() {
         try {
            byte var1 = this.input.readByte();
            return var1;
         } catch (EOFException var4) {
            throw new IllegalStateException(var4);
         } catch (IOException var5) {
            throw new AssertionError(var5);
         }
      }

      public char readChar() {
         try {
            char var1 = this.input.readChar();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public double readDouble() {
         try {
            double var1 = this.input.readDouble();
            return var1;
         } catch (IOException var4) {
            throw new IllegalStateException(var4);
         }
      }

      public float readFloat() {
         try {
            float var1 = this.input.readFloat();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public void readFully(byte[] var1) {
         try {
            this.input.readFully(var1);
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public void readFully(byte[] var1, int var2, int var3) {
         try {
            this.input.readFully(var1, var2, var3);
         } catch (IOException var5) {
            throw new IllegalStateException(var5);
         }
      }

      public int readInt() {
         try {
            int var1 = this.input.readInt();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public String readLine() {
         try {
            String var1 = this.input.readLine();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public long readLong() {
         try {
            long var1 = this.input.readLong();
            return var1;
         } catch (IOException var4) {
            throw new IllegalStateException(var4);
         }
      }

      public short readShort() {
         try {
            short var1 = this.input.readShort();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public String readUTF() {
         try {
            String var1 = this.input.readUTF();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public int readUnsignedByte() {
         try {
            int var1 = this.input.readUnsignedByte();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public int readUnsignedShort() {
         try {
            int var1 = this.input.readUnsignedShort();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public int skipBytes(int var1) {
         try {
            int var2 = this.input.skipBytes(var1);
            return var2;
         } catch (IOException var4) {
            throw new IllegalStateException(var4);
         }
      }
   }
}
