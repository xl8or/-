package com.kenai.jbosh;

import com.kenai.jbosh.AbstractBody;
import com.kenai.jbosh.BOSHException;

interface HTTPResponse {

   void abort();

   AbstractBody getBody() throws InterruptedException, BOSHException;

   int getHTTPStatus() throws InterruptedException, BOSHException;
}
