package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.AlbumSyncModel;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.service.method.PhotosGetAlbums;
import com.facebook.katana.service.method.PhotosGetPhotos;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetAlbums extends FqlMultiQuery {

   protected List<FacebookAlbum> mAlbums;


   public FqlGetAlbums(Context param1, Intent param2, String param3, long param4, String[] param6, ApiMethodListener param7, long param8, long param10) {
      // $FF: Couldn't be decompiled
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context param0, Intent param1, String param2, long param3, String[] param5, long param6, long param8) {
      // $FF: Couldn't be decompiled
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      PhotosGetAlbums var3 = (PhotosGetAlbums)this.getQueryByName("album_info");
      PhotosGetPhotos var4 = (PhotosGetPhotos)this.getQueryByName("album_cover");
      List var5 = var3.getAlbums();
      this.mAlbums = var5;
      List var6 = var4.getPhotos();
      List var7 = this.mAlbums;
      AlbumSyncModel.assignCoversToAlbums(var6, var7);
   }
}
