package com.facebook.katana.activity.profilelist;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckBox;
import com.facebook.katana.activity.profilelist.ProfileListNaiveCursorAdapter;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.Set;

public class SelectableProfileListNaiveCursorAdapter extends ProfileListNaiveCursorAdapter {

   protected final Set<Long> mTagged;


   public SelectableProfileListNaiveCursorAdapter(Context var1, ProfileImagesCache var2, Cursor var3, Set<Long> var4) {
      super(var1, var2, var3);
      this.mTagged = var4;
   }

   public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5) {
      View var6 = super.getChildView(var1, var2, var3, var4, var5);
      FacebookProfile var7 = (FacebookProfile)this.getChild(var1, var2);
      CheckBox var8 = (CheckBox)var6.findViewById(2131623983);
      Set var9 = this.mTagged;
      Long var10 = Long.valueOf(var7.mId);
      boolean var11 = var9.contains(var10);
      var8.setChecked(var11);
      return var6;
   }

   protected View inflateChildView(FacebookProfile var1) {
      View var2 = super.inflateChildView(var1);
      View var3 = ((ViewStub)var2.findViewById(2131624202)).inflate();
      return var2;
   }

   void toggle(int var1, View var2) {
      FacebookProfile var3 = (FacebookProfile)this.getItem(var1);
      CheckBox var4 = (CheckBox)var2.findViewById(2131623983);
      Set var5 = this.mTagged;
      Long var6 = Long.valueOf(var3.mId);
      if(var5.contains(var6)) {
         Set var7 = this.mTagged;
         Long var8 = Long.valueOf(var3.mId);
         var7.remove(var8);
         var4.setChecked((boolean)0);
      } else {
         Set var10 = this.mTagged;
         Long var11 = Long.valueOf(var3.mId);
         var10.add(var11);
         var4.setChecked((boolean)1);
      }
   }
}
