package com.google.common.io;

import java.io.DataInput;

public interface ByteArrayDataInput extends DataInput {

   boolean readBoolean();

   byte readByte();

   char readChar();

   double readDouble();

   float readFloat();

   void readFully(byte[] var1);

   void readFully(byte[] var1, int var2, int var3);

   int readInt();

   String readLine();

   long readLong();

   short readShort();

   String readUTF();

   int readUnsignedByte();

   int readUnsignedShort();

   int skipBytes(int var1);
}
