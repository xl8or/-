package com.facebook.katana.activity.findfriends;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.findfriends.BaseAdapter;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookPhonebookContact;
import com.facebook.katana.model.FacebookPhonebookContactUser;
import java.util.ArrayList;

public class FriendsAdapter extends BaseAdapter {

   protected final ProfileImagesCache mUserImagesCache;


   public FriendsAdapter(Context var1, ArrayList<? extends FacebookPhonebookContact> var2, ProfileImagesCache var3) {
      super(var1);
      this.mAllContacts = var2;
      this.mUserImagesCache = var3;
      this.setAllContacts(var2);
   }

   protected String getActionTakenString() {
      return this.mContext.getString(2131362448);
   }

   protected String getContactAddress(FacebookPhonebookContact var1) {
      return "";
   }

   protected long getContactId(FacebookPhonebookContact var1) {
      return var1.userId;
   }

   protected String getSelectButtonText() {
      return this.mContext.getString(2131361825);
   }

   protected void setupPic(View var1, FacebookPhonebookContact var2, boolean var3) {
      FacebookPhonebookContactUser var4 = (FacebookPhonebookContactUser)var2;
      ViewHolder var5;
      if(var3) {
         var5 = new ViewHolder(var1, 2131623987);
         this.mViewHolders.add(var5);
         var1.setTag(var5);
      } else {
         var5 = (ViewHolder)var1.getTag();
      }

      Long var7 = Long.valueOf(var4.userId);
      var5.setItemId(var7);
      String var8 = var4.imageUrl;
      if(var8 != null && var8.length() != 0) {
         ProfileImagesCache var9 = this.mUserImagesCache;
         Context var10 = this.mContext;
         long var11 = var4.userId;
         Bitmap var13 = var9.get(var10, var11, var8);
         if(var13 != null) {
            var5.mImageView.setImageBitmap(var13);
         } else {
            var5.mImageView.setImageResource(2130837747);
         }
      } else {
         var5.mImageView.setImageResource(2130837747);
      }
   }
}
