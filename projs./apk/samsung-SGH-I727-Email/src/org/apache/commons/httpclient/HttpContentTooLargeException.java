package org.apache.commons.httpclient;

import org.apache.commons.httpclient.HttpException;

public class HttpContentTooLargeException extends HttpException {

   private int maxlen;


   public HttpContentTooLargeException(String var1, int var2) {
      super(var1);
      this.maxlen = var2;
   }

   public int getMaxLength() {
      return this.maxlen;
   }
}
