package com.facebook.katana;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import com.facebook.katana.activity.profilelist.ProfileListNaiveCursorAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.platform.PlatformFastTrack;
import com.facebook.katana.util.PlatformUtils;

public class FriendsAdapter extends ProfileListNaiveCursorAdapter {

   public FriendsAdapter(Context var1, ProfileImagesCache var2, Cursor var3) {
      super(var1, var2, var3);
   }

   public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5) {
      View var6 = super.getChildView(var1, var2, var3, var4, var5);
      if(PlatformUtils.platformStorageSupported(this.mContext)) {
         FacebookProfile var7 = (FacebookProfile)this.getChild(var1, var2);
         View var8 = var6.findViewById(2131623987);
         String var9 = AppSession.getActiveSession(this.mContext, (boolean)0).getSessionInfo().username;
         long var10 = var7.mId;
         String[] var12 = new String[]{"vnd.android.cursor.item/vnd.facebook.profile"};
         PlatformFastTrack.prepareBadge(var8, var9, var10, var12);
      }

      return var6;
   }

   protected View inflateChildView(FacebookProfile var1) {
      View var2;
      if(!PlatformUtils.platformStorageSupported(this.mContext)) {
         var2 = super.inflateChildView(var1);
      } else {
         View var3 = this.mInflater.inflate(2130903144, (ViewGroup)null);
         View var4 = ((ViewStub)var3.findViewById(2131624201)).inflate();
         var2 = var3;
      }

      return var2;
   }
}
