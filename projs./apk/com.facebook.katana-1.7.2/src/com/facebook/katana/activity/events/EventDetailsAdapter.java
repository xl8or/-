package com.facebook.katana.activity.events;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.TimeUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventDetailsAdapter extends BaseAdapter {

   private final Context mContext;
   private long mCreatorId;
   private Cursor mCursor;
   private long mEventId;
   private ImageView mEventImage;
   private final LayoutInflater mInflater;
   private final List<EventDetailsAdapter.Item> mItems;
   private final StreamPhotosCache mPhotosCache;
   private int mRsvpStatus;
   private String mUrl;
   private long mUserId;


   public EventDetailsAdapter(Context var1, StreamPhotosCache var2, Cursor var3, long var4, long var6) {
      this.mContext = var1;
      LayoutInflater var8 = LayoutInflater.from(var1);
      this.mInflater = var8;
      this.mPhotosCache = var2;
      ArrayList var9 = new ArrayList();
      this.mItems = var9;
      this.mCursor = var3;
      this.mEventId = var4;
      this.mUserId = var6;
   }

   public int getCount() {
      return this.mItems.size();
   }

   public long getCreatorId() {
      return this.mCreatorId;
   }

   public long getEventId() {
      return this.mEventId;
   }

   public View getFooterView() {
      View var1 = this.mInflater.inflate(2130903062, (ViewGroup)null);
      TextView var2 = (TextView)var1.findViewById(2131624026);
      String var3 = this.mCursor.getString(3);
      if(var3 != null && var3.length() != 0) {
         String var4 = this.mCursor.getString(3);
         var2.setText(var4);
      } else {
         var1.findViewById(2131624025).setVisibility(8);
      }

      return var1;
   }

   public View getHeaderDivider() {
      return this.mInflater.inflate(2130903064, (ViewGroup)null);
   }

   public View getHeaderView() {
      View var1 = this.mInflater.inflate(2130903063, (ViewGroup)null);
      TextView var2 = (TextView)var1.findViewById(2131624030);
      String var3 = this.mCursor.getString(1);
      var2.setText(var3);
      int var4 = TimeUtils.getPstOffset();
      long var5 = this.mCursor.getLong(8);
      StringBuilder var7 = new StringBuilder();
      Context var8 = this.mContext;
      long var9 = 1000L * var5;
      long var11 = (long)var4;
      long var13 = var9 + var11;
      String var15 = TimeUtils.getTimeAsStringForTimePeriod(var8, -1, var13);
      var7.append(var15);
      long var17 = this.mCursor.getLong(9);
      StringBuilder var19 = var7.append("\n");
      Context var20 = this.mContext;
      long var21 = 1000L * var17;
      long var23 = (long)var4;
      long var25 = var21 + var23;
      String var27 = TimeUtils.getTimeAsStringForTimePeriod(var20, -1, var25);
      var7.append(var27);
      TextView var29 = (TextView)var1.findViewById(2131624031);
      String var30 = var7.toString();
      var29.setText(var30);
      ImageView var31 = (ImageView)var1.findViewById(2131624028);
      this.mEventImage = var31;
      String var32 = this.mCursor.getString(2);
      this.mUrl = var32;
      if(var32 != null) {
         StreamPhotosCache var34 = this.mPhotosCache;
         Context var35 = this.mContext;
         Bitmap var36 = var34.get(var35, var32, 1);
         if(var36 != null) {
            this.mEventImage.setImageBitmap(var36);
         } else {
            this.mEventImage.setImageResource(2130837730);
         }
      } else {
         this.mEventImage.setImageResource(2130837730);
      }

      return var1;
   }

   public Object getItem(int var1) {
      return this.mItems.get(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public int getRsvpStatus() {
      return this.mRsvpStatus;
   }

   public View getRsvpView() {
      View var1 = this.mInflater.inflate(2130903065, (ViewGroup)null);
      View var2 = var1.findViewById(2131624034);
      int var3 = this.mCursor.getInt(11);
      this.setRsvpStatus(var2, var3);
      return var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      EventDetailsAdapter.Item var4 = (EventDetailsAdapter.Item)this.mItems.get(var1);
      View var5 = this.mInflater.inflate(2130903067, (ViewGroup)null);
      int var6 = 2130837628;
      if(var1 == 0 && this.getCount() == 1) {
         var6 = 2130837626;
      } else if(var1 == 0) {
         var6 = 2130837629;
      } else {
         int var11 = this.getCount() - 1;
         if(var1 == var11) {
            var6 = 2130837627;
         }
      }

      var5.setBackgroundResource(var6);
      TextView var7 = (TextView)var5.findViewById(2131624033);
      String var8 = var4.getLabel();
      var7.setText(var8);
      TextView var9 = (TextView)var5.findViewById(2131624036);
      String var10 = var4.getString();
      var9.setText(var10);
      return var5;
   }

   public boolean isEnabled(int var1) {
      return ((EventDetailsAdapter.Item)this.mItems.get(var1)).getEnabled();
   }

   public void setEventInfo() {
      this.mItems.clear();
      long var1 = this.mCursor.getLong(10);
      this.mCreatorId = var1;
      List var3 = this.mItems;
      String var4 = this.mContext.getString(2131361884);
      String var5 = this.mCursor.getString(4);
      EventDetailsAdapter.Item var6 = new EventDetailsAdapter.Item(0, var4, var5, (boolean)1);
      var3.add(var6);
      String var8 = this.mCursor.getString(5);
      if(var8 != null && var8.length() != 0) {
         List var9 = this.mItems;
         String var10 = this.mContext.getString(2131361885);
         EventDetailsAdapter.Item var11 = new EventDetailsAdapter.Item(1, var10, var8, (boolean)0);
         var9.add(var11);
      }

      StringBuilder var13 = new StringBuilder();

      try {
         Map var14 = FacebookEvent.deserializeVenue(this.mCursor.getBlob(6));
         String var15 = ((Serializable)var14.get("street")).toString();
         if(var15.length() != 0) {
            var13.append(var15);
         }

         StringBuilder var17 = new StringBuilder();
         String var18 = ((Serializable)var14.get("city")).toString();
         if(var18.length() != 0) {
            var17.append(var18);
         }

         String var20 = ((Serializable)var14.get("state")).toString();
         if(var20.length() != 0) {
            if(var18.length() != 0) {
               StringBuilder var21 = var17.append(", ");
            }

            var17.append(var20);
         }

         if(var17.length() != 0) {
            StringBuilder var23 = var13.append("\n");
            var13.append(var17);
         }

         String var25 = ((Serializable)var14.get("country")).toString();
         if(var25.length() != 0) {
            StringBuilder var26 = var13.append("\n");
            var13.append(var25);
         }

         if(var13.length() != 0) {
            List var28 = this.mItems;
            String var29 = this.mContext.getString(2131361882);
            String var30 = var13.toString();
            EventDetailsAdapter.Item var31 = new EventDetailsAdapter.Item(2, var29, var30, (boolean)1);
            var28.add(var31);
         }
      } catch (IOException var43) {
         Log.e("EventDetails Activity", "Deserialization failed for event.");
      }

      label48: {
         if(this.mCursor.getInt(12) != 0) {
            long var33 = this.mCursor.getLong(10);
            long var35 = this.mUserId;
            if(var33 != var35) {
               break label48;
            }
         }

         List var37 = this.mItems;
         String var38 = this.mContext.getString(2131361883);
         String var39 = this.mContext.getString(2131361887);
         EventDetailsAdapter.Item var40 = new EventDetailsAdapter.Item(3, var38, var39, (boolean)1);
         var37.add(var40);
      }

      this.notifyDataSetChanged();
   }

   public void setRsvpStatus(View var1, int var2) {
      this.mRsvpStatus = var2;
      TextView var3 = (TextView)var1;
      String var4 = StringUtils.rsvpStatusToString(this.mContext, var2);
      var3.setText(var4);
   }

   public void updatePhoto(Bitmap var1, String var2) {
      if(this.mUrl != null) {
         if(this.mUrl.equals(var2)) {
            this.mEventImage.setImageBitmap(var1);
         }
      }
   }

   public interface EventQuery {

      int INDEX_CREATOR_ID = 10;
      int INDEX_END_TIME = 9;
      int INDEX_EVENT_DESCRIPTION = 3;
      int INDEX_EVENT_ID = 7;
      int INDEX_EVENT_NAME = 1;
      int INDEX_HIDE_GUEST_LIST = 12;
      int INDEX_HOST = 4;
      int INDEX_LOCATION = 5;
      int INDEX_MEDIUM_IMAGE_URL = 2;
      int INDEX_RSVP_STATUS = 11;
      int INDEX_START_TIME = 8;
      int INDEX_VENUE = 6;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"_id", "event_name", "medium_image_url", "description", "host", "location", "venue", "event_id", "start_time", "end_time", "creator_id", "rsvp_status", "hide_guest_list"};
         PROJECTION = var0;
      }
   }

   protected static class Item {

      public static final int TYPE_ADDRESS = 2;
      public static final int TYPE_GUESTS = 3;
      public static final int TYPE_HOST = 0;
      public static final int TYPE_LOCATION = 1;
      private final boolean mEnabled;
      private final String mLabel;
      private final String mString;
      private final int mType;


      public Item(int var1, String var2, String var3, boolean var4) {
         this.mType = var1;
         this.mLabel = var2;
         this.mString = var3;
         this.mEnabled = var4;
      }

      public boolean getEnabled() {
         return this.mEnabled;
      }

      public String getLabel() {
         return this.mLabel;
      }

      public String getString() {
         return this.mString;
      }

      public int getType() {
         return this.mType;
      }
   }
}
