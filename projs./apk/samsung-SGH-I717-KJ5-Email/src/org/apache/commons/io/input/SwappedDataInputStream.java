package org.apache.commons.io.input;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.EndianUtils;
import org.apache.commons.io.input.ProxyInputStream;

public class SwappedDataInputStream extends ProxyInputStream implements DataInput {

   public SwappedDataInputStream(InputStream var1) {
      super(var1);
   }

   public boolean readBoolean() throws IOException, EOFException {
      boolean var1;
      if(this.readByte() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public byte readByte() throws IOException, EOFException {
      return (byte)this.in.read();
   }

   public char readChar() throws IOException, EOFException {
      return (char)this.readShort();
   }

   public double readDouble() throws IOException, EOFException {
      return EndianUtils.readSwappedDouble(this.in);
   }

   public float readFloat() throws IOException, EOFException {
      return EndianUtils.readSwappedFloat(this.in);
   }

   public void readFully(byte[] var1) throws IOException, EOFException {
      int var2 = var1.length;
      this.readFully(var1, 0, var2);
   }

   public void readFully(byte[] var1, int var2, int var3) throws IOException, EOFException {
      int var7;
      for(int var4 = var3; var4 > 0; var4 -= var7) {
         int var5 = var3 - var4;
         int var6 = var2 + var5;
         var7 = this.read(var1, var6, var4);
         if(-1 == var7) {
            throw new EOFException();
         }
      }

   }

   public int readInt() throws IOException, EOFException {
      return EndianUtils.readSwappedInteger(this.in);
   }

   public String readLine() throws IOException, EOFException {
      throw new UnsupportedOperationException("Operation not supported: readLine()");
   }

   public long readLong() throws IOException, EOFException {
      return EndianUtils.readSwappedLong(this.in);
   }

   public short readShort() throws IOException, EOFException {
      return EndianUtils.readSwappedShort(this.in);
   }

   public String readUTF() throws IOException, EOFException {
      throw new UnsupportedOperationException("Operation not supported: readUTF()");
   }

   public int readUnsignedByte() throws IOException, EOFException {
      return this.in.read();
   }

   public int readUnsignedShort() throws IOException, EOFException {
      return EndianUtils.readSwappedUnsignedShort(this.in);
   }

   public int skipBytes(int var1) throws IOException, EOFException {
      InputStream var2 = this.in;
      long var3 = (long)var1;
      return (int)var2.skip(var3);
   }
}
