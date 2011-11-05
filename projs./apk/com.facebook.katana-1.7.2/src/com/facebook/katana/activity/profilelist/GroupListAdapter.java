package com.facebook.katana.activity.profilelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.profilelist.ProfileListDynamicAdapter;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookGroup;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.StringUtils;
import java.util.List;

class GroupListAdapter extends ProfileListDynamicAdapter {

   public GroupListAdapter(Context var1, ProfileImagesCache var2) {
      super(var1, var2);
   }

   public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5) {
      FacebookGroup var6 = (FacebookGroup)this.getChild(var1, var2);
      View var7 = var4;
      ViewHolder var8;
      if(var4 == null) {
         var7 = this.mInflater.inflate(2130903087, (ViewGroup)null);
         var8 = new ViewHolder(var7, 2131623987);
         this.mViewHolders.add(var8);
         var7.setTag(var8);
      } else {
         var8 = (ViewHolder)var4.getTag();
      }

      Long var10 = Long.valueOf(var6.mId);
      var8.setItemId(var10);
      String var11 = var6.mImageUrl;
      if(var11 != null && var11.length() != 0) {
         ProfileImagesCache var12 = this.mUserImagesCache;
         Context var13 = this.mContext;
         long var14 = var6.mId;
         Bitmap var16 = var12.get(var13, var14, var11);
         if(var16 != null) {
            var8.mImageView.setImageBitmap(var16);
         } else {
            var8.mImageView.setImageResource(2130837631);
         }
      } else {
         var8.mImageView.setImageResource(2130837631);
      }

      TextView var17 = (TextView)var7.findViewById(2131624078);
      String var18 = var6.mDisplayName;
      var17.setText(var18);
      TextView var19 = (TextView)var7.findViewById(2131624079);
      Context var20 = this.mContext;
      Object[] var21 = new Object[1];
      Context var22 = this.mContext;
      StringUtils.TimeFormatStyle var23 = StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE;
      long var24 = var6.mUpdateTime * 1000L;
      String var26 = StringUtils.getTimeAsString(var22, var23, var24);
      var21[0] = var26;
      String var27 = var20.getString(2131361931, var21);
      var19.setText(var27);
      TextView var28 = (TextView)var7.findViewById(2131624080);
      int var29 = var6.getUnreadCount();
      if(var29 == 0) {
         var28.setVisibility(8);
      } else {
         String var30 = Integer.toString(var29);
         var28.setText(var30);
         var28.setVisibility(0);
      }

      return var7;
   }

   public void updateProfileList(List<? extends FacebookProfile> var1) {
      this.mProfiles = var1;
      this.notifyDataSetChanged();
   }
}
