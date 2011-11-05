package com.facebook.katana.activity.messages;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.messages.UserSelectionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.util.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MailboxMessagesAdapter extends CursorAdapter {

   private final OnClickListener mClickListener;
   private final Context mContext;
   private final ProfileImagesCache mUserImagesCache;
   private final UserSelectionListener mUserSelectionListener;
   private final List<ViewHolder<Long>> mViewHolders;


   public MailboxMessagesAdapter(Context var1, Cursor var2, ProfileImagesCache var3, UserSelectionListener var4) {
      super(var1, var2);
      this.mContext = var1;
      this.mUserImagesCache = var3;
      ArrayList var5 = new ArrayList();
      this.mViewHolders = var5;
      this.mUserSelectionListener = var4;
      MailboxMessagesAdapter.1 var6 = new MailboxMessagesAdapter.1();
      this.mClickListener = var6;
   }

   public void bindView(View var1, Context var2, Cursor var3) {
      byte var5 = 1;
      long var6 = var3.getLong(var5);
      ViewHolder var8 = (ViewHolder)var1.getTag();
      Long var9 = Long.valueOf(var6);
      var8.setItemId(var9);
      byte var11 = 6;
      String var12 = var3.getString(var11);
      if(var12 == null) {
         byte var14 = 4;
         var12 = var3.getString(var14);
         if(var12 == null) {
            var12 = this.mContext.getString(2131361895);
         }
      }

      int var16 = 2131624120;
      TextView var17 = (TextView)var1.findViewById(var16);
      var17.setText(var12);
      Context var19 = this.mContext;
      StringUtils.TimeFormatStyle var20 = StringUtils.TimeFormatStyle.MAILBOX_RELATIVE_STYLE;
      byte var22 = 2;
      long var23 = var3.getLong(var22) * 1000L;
      String var25 = StringUtils.getTimeAsString(var19, var20, var23);
      int var27 = 2131624121;
      TextView var28 = (TextView)var1.findViewById(var27);
      var28.setText(var25);
      int var31 = 2131624122;
      TextView var32 = (TextView)var1.findViewById(var31);
      byte var34 = 3;
      String var35 = var3.getString(var34);
      var32.setText(var35);
      byte var39 = 7;
      String var40 = var3.getString(var39);
      if(var40 == null) {
         byte var42 = 5;
         var40 = var3.getString(var42);
      }

      if(var40 != null) {
         ProfileImagesCache var43 = this.mUserImagesCache;
         Context var44 = this.mContext;
         Bitmap var45 = var43.get(var44, var6, var40);
         if(var45 != null) {
            var8.mImageView.setImageBitmap(var45);
         } else {
            var8.mImageView.setImageResource(2130837747);
         }
      } else {
         var8.mImageView.setImageResource(2130837747);
      }

      ImageView var46 = var8.mImageView;
      Long var47 = Long.valueOf(var6);
      var46.setTag(var47);
   }

   public boolean isEmpty() {
      byte var1;
      if(this.getCursor() == null) {
         var1 = 1;
      } else {
         var1 = super.isEmpty();
      }

      return (boolean)var1;
   }

   public View newView(Context var1, Cursor var2, ViewGroup var3) {
      View var4 = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903103, (ViewGroup)null);
      ViewHolder var5 = new ViewHolder(var4, 2131624119);
      var4.setTag(var5);
      this.mViewHolders.add(var5);
      ImageView var7 = var5.mImageView;
      OnClickListener var8 = this.mClickListener;
      var7.setOnClickListener(var8);
      return var4;
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

   public interface MessageQuery {

      int INDEX_AUTHOR_IMAGE_URL = 5;
      int INDEX_AUTHOR_NAME = 4;
      int INDEX_AUTHOR_USER_ID = 1;
      int INDEX_BODY = 3;
      int INDEX_OBJECT_IMAGE_URL = 7;
      int INDEX_OBJECT_NAME = 6;
      int INDEX_SPECIFIC_ID = 0;
      int INDEX_TIME_SENT = 2;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"_id", "author_id", "sent", "body", "author_name", "author_image_url", "object_name", "object_image_url"};
         PROJECTION = var0;
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         long var2 = ((Long)var1.getTag()).longValue();
         MailboxMessagesAdapter.this.mUserSelectionListener.onUserSelected(var2);
      }
   }
}
