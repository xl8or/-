package com.facebook.katana.activity.messages;

import android.content.Context;
import com.facebook.katana.activity.messages.UserSelectionListener;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.ApplicationUtils;

public class BaseUserSelectionListener implements UserSelectionListener {

   private final Context mContext;


   BaseUserSelectionListener(Context var1) {
      this.mContext = var1;
   }

   public void onUserSelected(long var1) {
      ApplicationUtils.OpenUserProfile(this.mContext, var1, (FacebookProfile)null);
   }
}
