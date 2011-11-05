package com.facebook.katana;

import android.content.Context;
import android.database.Cursor;
import com.facebook.katana.ProfileSearchResultsAdapter;
import com.facebook.katana.binding.StreamPhotosCache;

public abstract class UserSearchResultsAdapter extends ProfileSearchResultsAdapter {

   public UserSearchResultsAdapter(Context var1, Cursor var2, StreamPhotosCache var3) {
      super(var1, var2, var3);
   }

   public interface SearchResultsQuery {

      int INDEX_USER_DISPLAY_NAME = 2;
      int INDEX_USER_ID = 1;
      int INDEX_USER_IMAGE_URL = 3;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"_id", "user_id", "display_name", "user_image_url"};
         PROJECTION = var0;
      }
   }
}
