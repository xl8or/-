package com.facebook.katana.activity.findfriends;

import android.content.Context;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.katana.activity.findfriends.BaseAdapter;
import com.facebook.katana.model.FacebookPhonebookContact;
import com.facebook.katana.util.GrowthUtils;
import java.util.ArrayList;

public class InvitesAdapter extends BaseAdapter {

   private Spanned mLegalDisclaimer;
   private boolean mLegalSettingSaved;
   private boolean mShowLegalBar;


   public InvitesAdapter(Context var1, ArrayList<FacebookPhonebookContact> var2, Spanned var3) {
      super(var1);
      this.mAllContacts = var2;
      this.mLegalDisclaimer = var3;
      boolean var4 = GrowthUtils.shouldShowLegalBar(this.mContext);
      this.mShowLegalBar = var4;
      this.setAllContacts(var2);
   }

   protected String getActionTakenString() {
      return this.mContext.getString(2131362449);
   }

   protected long getContactId(FacebookPhonebookContact var1) {
      return var1.recordId;
   }

   public View getSectionHeaderView(int var1, View var2, ViewGroup var3) {
      View var4;
      if(!this.mShowLegalBar) {
         var4 = super.getSectionHeaderView(var1, var2, var3);
      } else if(var1 == 0) {
         if(!this.mLegalSettingSaved) {
            this.mLegalSettingSaved = (boolean)1;
            GrowthUtils.setLegalBarShown(this.mContext);
         }

         View var5 = var2;
         if(var2 == null || !(var2 instanceof LinearLayout)) {
            var5 = this.mInflater.inflate(2130903077, (ViewGroup)null);
         }

         TextView var6 = (TextView)var5.findViewById(2131624058);
         Spanned var7 = this.mLegalDisclaimer;
         var6.setText(var7);
         MovementMethod var8 = LinkMovementMethod.getInstance();
         var6.setMovementMethod(var8);
         TextView var9 = (TextView)var5.findViewById(2131624037);
         String var10 = ((BaseAdapter.FirstLetterFriendSection)this.mSections.get(var1)).toString();
         var9.setText(var10);
         var4 = var5;
      } else if(var2 != null && !(var2 instanceof TextView)) {
         var4 = super.getSectionHeaderView(var1, (View)null, (ViewGroup)null);
      } else {
         var4 = super.getSectionHeaderView(var1, var2, var3);
      }

      return var4;
   }

   protected String getSelectButtonText() {
      return this.mContext.getString(2131362443);
   }

   protected void setupPic(View var1, FacebookPhonebookContact var2, boolean var3) {
      var1.findViewById(2131623987).setVisibility(8);
   }
}
