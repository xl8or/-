package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FacebookApiException extends Exception {

   protected FacebookApiException.ServerExceptionData mData;


   protected FacebookApiException() {}

   public FacebookApiException(int var1, String var2) {
      FacebookApiException.ServerExceptionData var3 = new FacebookApiException.ServerExceptionData(var1, var2);
      this.mData = var3;
   }

   public FacebookApiException(JsonParser var1) throws JsonParseException, IOException, JMException {
      FacebookApiException.ServerExceptionData var2 = (FacebookApiException.ServerExceptionData)JMParser.parseObjectJson(var1, FacebookApiException.ServerExceptionData.class);
      this.mData = var2;
   }

   public int getErrorCode() {
      return this.mData.mErrorCode;
   }

   public String getErrorMsg() {
      return this.mData.mErrorMsg;
   }

   protected static class ServerExceptionData extends JMCachingDictDestination {

      @JMAutogen.InferredType(
         jsonFieldName = "error_code"
      )
      final int mErrorCode;
      @JMAutogen.InferredType(
         jsonFieldName = "error_msg"
      )
      final String mErrorMsg;


      protected ServerExceptionData() {
         this.mErrorCode = -1;
         this.mErrorMsg = null;
      }

      public ServerExceptionData(int var1, String var2) {
         this.mErrorCode = var1;
         this.mErrorMsg = var2;
      }
   }
}
