package com.facebook.katana.activity.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookChatMessage;
import com.facebook.katana.model.FacebookChatUser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<FacebookChatMessage> {

   private static final int TIME_PERIOD = 120000;
   private long lastTag;
   private final Context mContext;
   private final List<ViewHolder<Long>> mViewHolders;
   private final FacebookChatUser me;
   private final FacebookChatUser recepient;
   private final ArrayList<Boolean> timeTag;


   public ChatMessageAdapter(Context var1, FacebookChatUser var2, FacebookChatUser var3) {
      ArrayList var4 = new ArrayList();
      super(var1, 2130903052, var4);
      this.lastTag = -120000L;
      ArrayList var5 = new ArrayList();
      this.timeTag = var5;
      this.mContext = var1;
      ArrayList var6 = new ArrayList();
      this.mViewHolders = var6;
      this.me = var2;
      this.recepient = var3;
   }

   public void add(FacebookChatMessage var1) {
      long var2 = var1.mTimestamp.longValue();
      long var4 = this.lastTag;
      if(var2 - var4 > 120000L) {
         long var6 = var1.mTimestamp.longValue();
         this.lastTag = var6;
         ArrayList var8 = this.timeTag;
         Boolean var9 = Boolean.valueOf((boolean)1);
         var8.add(var9);
      } else {
         ArrayList var11 = this.timeTag;
         Boolean var12 = Boolean.valueOf((boolean)0);
         var11.add(var12);
      }

      super.add(var1);
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      if(var2 == null) {
         var2 = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903052, (ViewGroup)null);
         ViewHolder var4 = new ViewHolder;
         int var7 = 2131623978;
         var4.<init>(var2, var7);
         var2.setTag(var4);
         this.mViewHolders.add(var4);
      }

      FacebookChatMessage var11 = (FacebookChatMessage)this.getItem(var1);
      long var12 = var11.mSenderUid;
      ViewHolder var14 = (ViewHolder)var2.getTag();
      ArrayList var15 = this.timeTag;
      if(((Boolean)var15.get(var1)).booleanValue()) {
         long var17 = var11.mTimestamp.longValue();
         int var20 = 2131623976;
         TextView var21 = (TextView)var2.findViewById(var20);
         String var22 = DateUtils.formatDateTime(this.mContext, var17, 2577);
         var21.setText(var22);
         int var24 = 2131623975;
         var2.findViewById(var24).setVisibility(0);
      } else {
         int var42 = 2131623975;
         var2.findViewById(var42).setVisibility(8);
      }

      Long var25 = Long.valueOf(var12);
      var14.setItemId(var25);
      int var27 = 2131623979;
      TextView var28 = (TextView)var2.findViewById(var27);
      long var29 = this.me.mUserId;
      FacebookChatUser var31;
      if(var12 == var29) {
         var31 = this.me;
         var28.setBackgroundResource(2130837567);
      } else {
         var31 = this.recepient;
         var28.setBackgroundResource(2130837579);
      }

      String var32 = var11.mBody;
      var28.setText(var32);
      ProfileImagesCache var33 = AppSession.getActiveSession(this.mContext, (boolean)0).getUserImagesCache();
      Context var34 = this.mContext;
      long var35 = var31.mUserId;
      String var37 = var31.mImageUrl;
      Bitmap var38 = var33.get(var34, var35, var37);
      if(var38 == null) {
         var14.mImageView.setImageResource(2130837747);
      } else {
         var14.mImageView.setImageBitmap(var38);
      }

      ImageView var39 = var14.mImageView;
      Long var40 = Long.valueOf(var12);
      var39.setTag(var40);
      return var2;
   }

   public void updateUserImage(ProfileImage var1) {
      Iterator var2 = this.mViewHolders.iterator();

      while(var2.hasNext()) {
         ViewHolder var3 = (ViewHolder)var2.next();
         Long var4 = (Long)var3.getItemId();
         if(var4 != null) {
            Long var5 = Long.valueOf(var1.id);
            if(var4.equals(var5)) {
               ImageView var6 = var3.mImageView;
               Bitmap var7 = var1.getBitmap();
               var6.setImageBitmap(var7);
            }
         }
      }

   }
}
