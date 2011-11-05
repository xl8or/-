package com.facebook.katana.service.method;

import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class PlacesCreateException extends FacebookApiException {

   // $FF: synthetic field
   static final boolean $assertionsDisabled;


   static {
      byte var0;
      if(!PlacesCreateException.class.desiredAssertionStatus()) {
         var0 = 1;
      } else {
         var0 = 0;
      }

      $assertionsDisabled = (boolean)var0;
   }

   public PlacesCreateException(JsonParser var1) throws JsonParseException, IOException, JMException {
      FacebookApiException.ServerExceptionData var2 = (FacebookApiException.ServerExceptionData)JMParser.parseObjectJson(var1, PlacesCreateException.PlacesServerExceptionData.class);
      this.mData = var2;
      if(!$assertionsDisabled) {
         if(this.mData == null) {
            throw new AssertionError();
         }
      }
   }

   public List<PlacesCreateException.SimilarPlace> getSimilarPlaces() {
      return ((PlacesCreateException.PlacesServerExceptionData)this.mData).mErrorData.mSimilarPlaces;
   }

   protected static class PlacesServerExceptionData extends FacebookApiException.ServerExceptionData {

      @JMAutogen.EscapedObjectType(
         jsonFieldName = "error_data"
      )
      PlacesCreateException.ErrorData mErrorData;


      protected PlacesServerExceptionData() {}
   }

   protected static class ErrorData extends JMCachingDictDestination {

      @JMAutogen.ListType(
         jsonFieldName = "similar_places",
         listElementTypes = {PlacesCreateException.SimilarPlace.class}
      )
      List<PlacesCreateException.SimilarPlace> mSimilarPlaces;


      protected ErrorData() {}
   }

   public static class SimilarPlace extends JMCachingDictDestination {

      @JMAutogen.InferredType(
         jsonFieldName = "id"
      )
      public final long mId = 65535L;
      @JMAutogen.InferredType(
         jsonFieldName = "name"
      )
      public final String mName = null;


      public SimilarPlace() {}
   }
}
