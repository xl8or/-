package org.codehaus.jackson.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.codehaus.jackson.io.IOContext;

abstract class BaseReader extends Reader {

   protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
   protected static final char NULL_BYTE;
   protected static final char NULL_CHAR;
   protected byte[] mBuffer;
   protected final IOContext mContext;
   protected InputStream mIn;
   protected int mLength;
   protected int mPtr;
   char[] mTmpBuf = null;


   protected BaseReader(IOContext var1, InputStream var2, byte[] var3, int var4, int var5) {
      this.mContext = var1;
      this.mIn = var2;
      this.mBuffer = var3;
      this.mPtr = var4;
      this.mLength = var5;
   }

   public void close() throws IOException {
      InputStream var1 = this.mIn;
      if(var1 != null) {
         this.mIn = null;
         this.freeBuffers();
         var1.close();
      }
   }

   public final void freeBuffers() {
      byte[] var1 = this.mBuffer;
      if(var1 != null) {
         this.mBuffer = null;
         this.mContext.releaseReadIOBuffer(var1);
      }
   }

   public int read() throws IOException {
      if(this.mTmpBuf == null) {
         char[] var1 = new char[1];
         this.mTmpBuf = var1;
      }

      char[] var2 = this.mTmpBuf;
      int var3;
      if(this.read(var2, 0, 1) < 1) {
         var3 = -1;
      } else {
         var3 = this.mTmpBuf[0];
      }

      return var3;
   }

   protected void reportBounds(char[] var1, int var2, int var3) throws IOException {
      StringBuilder var4 = (new StringBuilder()).append("read(buf,").append(var2).append(",").append(var3).append("), cbuf[");
      int var5 = var1.length;
      String var6 = var4.append(var5).append("]").toString();
      throw new ArrayIndexOutOfBoundsException(var6);
   }

   protected void reportStrangeStream() throws IOException {
      throw new IOException("Strange I/O stream, returned 0 bytes on read");
   }
}
