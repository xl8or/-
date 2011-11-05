package org.apache.commons.httpclient.methods.multipart;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.methods.multipart.PartSource;

public class ByteArrayPartSource implements PartSource {

   private byte[] bytes;
   private String fileName;


   public ByteArrayPartSource(String var1, byte[] var2) {
      this.fileName = var1;
      this.bytes = var2;
   }

   public InputStream createInputStream() throws IOException {
      byte[] var1 = this.bytes;
      return new ByteArrayInputStream(var1);
   }

   public String getFileName() {
      return this.fileName;
   }

   public long getLength() {
      return (long)this.bytes.length;
   }
}
