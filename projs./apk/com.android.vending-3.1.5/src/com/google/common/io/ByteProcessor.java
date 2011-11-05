package com.google.common.io;

import java.io.IOException;

public interface ByteProcessor<T extends Object> {

   T getResult();

   boolean processBytes(byte[] var1, int var2, int var3) throws IOException;
}
