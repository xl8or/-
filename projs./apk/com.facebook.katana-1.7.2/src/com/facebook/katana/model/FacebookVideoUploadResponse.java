package com.facebook.katana.model;

import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

@JMAutogen.IgnoreUnexpectedJsonFields
public class FacebookVideoUploadResponse extends JMCachingDictDestination {

   @JMAutogen.InferredType(
      jsonFieldName = "link"
   )
   public final String link = null;
   @JMAutogen.InferredType(
      jsonFieldName = "vid"
   )
   public final String vid = null;


   private FacebookVideoUploadResponse() {}

   public static FacebookVideoUploadResponse parseJson(JsonParser var0) throws FacebookApiException, JsonParseException, IOException, JMException {
      return (FacebookVideoUploadResponse)JMParser.parseObjectJson(var0, FacebookVideoUploadResponse.class);
   }
}
