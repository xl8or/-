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
public class FacebookVideo extends JMCachingDictDestination {

   @JMAutogen.InferredType(
      jsonFieldName = "display_url"
   )
   private String mDisplayUrl;
   private FacebookVideo.VideoSource mSourceType;
   @JMAutogen.InferredType(
      jsonFieldName = "source_type"
   )
   private String mSourceType_internal;
   @JMAutogen.InferredType(
      jsonFieldName = "source_url"
   )
   private String mSourceUrl;


   public FacebookVideo() {}

   public static FacebookVideo parseJson(JsonParser var0) throws FacebookApiException, JsonParseException, IOException, JMException {
      return (FacebookVideo)JMParser.parseObjectJson(var0, FacebookVideo.class);
   }

   public String getDisplayUrl() {
      return this.mDisplayUrl;
   }

   public FacebookVideo.VideoSource getSourceType() {
      return this.mSourceType;
   }

   public String getSourceUrl() {
      return this.mSourceUrl;
   }

   public void setString(String var1, String var2) throws JMException {
      if(var1.equals("mSourceType_internal")) {
         if(var2.equals("raw")) {
            FacebookVideo.VideoSource var3 = FacebookVideo.VideoSource.SOURCE_RAW;
            this.setObject("mSourceType", var3);
         } else {
            FacebookVideo.VideoSource var4 = FacebookVideo.VideoSource.SOURCE_HTML;
            this.setObject("mSourceType", var4);
         }
      } else {
         super.setString(var1, var2);
      }
   }

   public static enum VideoSource {

      // $FF: synthetic field
      private static final FacebookVideo.VideoSource[] $VALUES;
      SOURCE_HTML("SOURCE_HTML", 0),
      SOURCE_RAW("SOURCE_RAW", 1);


      static {
         FacebookVideo.VideoSource[] var0 = new FacebookVideo.VideoSource[2];
         FacebookVideo.VideoSource var1 = SOURCE_HTML;
         var0[0] = var1;
         FacebookVideo.VideoSource var2 = SOURCE_RAW;
         var0[1] = var2;
         $VALUES = var0;
      }

      private VideoSource(String var1, int var2) {}
   }
}
