package org.apache.commons.httpclient.methods;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestEntity {

   long getContentLength();

   String getContentType();

   boolean isRepeatable();

   void writeRequest(OutputStream var1) throws IOException;
}
