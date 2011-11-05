package com.facebook.katana;

import android.content.Context;
import android.database.Cursor;
import com.facebook.katana.ProfileSearchResultsAdapter;
import com.facebook.katana.activity.profilelist.ProfileListCursorAdapter;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.ArrayList;
import java.util.List;

public class PageSearchResultsAdapter extends ProfileSearchResultsAdapter {

   public PageSearchResultsAdapter(Context var1, Cursor var2, StreamPhotosCache var3) {
      super(var1, var2, var3);
   }

   public Object getChild(int var1, int var2) {
      Cursor var3 = this.mCursor;
      int var4 = this.getChildActualPosition(var1, var2);
      var3.moveToPosition(var4);
      long var6 = this.mCursor.getLong(1);
      String var8 = this.mCursor.getString(2);
      String var9 = this.mCursor.getString(3);
      return new FacebookProfile(var6, var8, var9, 1);
   }

   public int getSectionCount() {
      int var1;
      if(this.mCursor != null && this.mCursor.getCount() != 0) {
         var1 = this.mSections.size();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public void refreshData(Cursor var1) {
      this.mCursor = var1;
      ArrayList var2 = new ArrayList();
      this.mSections = var2;
      if(var1 != null) {
         List var3 = this.mSections;
         String var4 = this.mContext.getString(2131362069);
         int var5 = var1.getCount();
         ProfileListCursorAdapter.Section var6 = new ProfileListCursorAdapter.Section(var4, 0, var5);
         var3.add(var6);
         this.notifyDataSetChanged();
      }
   }

   public interface SearchResultsQuery {

      int INDEX_PAGE_DISPLAY_NAME = 2;
      int INDEX_PAGE_ID = 1;
      int INDEX_PAGE_IMAGE_URL = 3;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"_id", "page_id", "display_name", "pic"};
         PROJECTION = var0;
      }
   }
}
