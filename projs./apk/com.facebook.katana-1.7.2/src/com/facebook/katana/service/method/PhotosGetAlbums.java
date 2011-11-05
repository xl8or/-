package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class PhotosGetAlbums extends FqlQuery {

   private static final long MAX_ALBUMS = 1000000L;
   private final List<FacebookAlbum> mAlbums;


   public PhotosGetAlbums(Context param1, Intent param2, String param3, long param4, String param6, ApiMethodListener param7, long param8, long param10) {
      // $FF: Couldn't be decompiled
   }

   private static String buildQuery(long param0, String param2, long param3, long param5) {
      // $FF: Couldn't be decompiled
   }

   public List<FacebookAlbum> getAlbums() {
      return this.mAlbums;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         FacebookApiException var4 = new FacebookApiException(var1);
         if(var4.getErrorCode() != -1) {
            throw var4;
         }
      } else {
         JsonToken var5 = JsonToken.START_ARRAY;
         if(var2 == var5) {
            while(true) {
               JsonToken var6 = JsonToken.END_ARRAY;
               if(var2 == var6) {
                  return;
               }

               JsonToken var7 = JsonToken.START_OBJECT;
               if(var2 == var7) {
                  List var8 = this.mAlbums;
                  FacebookAlbum var9 = new FacebookAlbum(var1);
                  var8.add(var9);
               }

               var2 = var1.nextToken();
            }
         } else {
            throw new IOException("Malformed JSON");
         }
      }
   }
}
