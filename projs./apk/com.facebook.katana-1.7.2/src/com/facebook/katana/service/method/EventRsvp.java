package com.facebook.katana.service.method;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.provider.EventsProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class EventRsvp extends ApiMethod {

   private long mEventId;
   private FacebookEvent.RsvpStatus mNewStatus;
   private boolean mSuccess;


   public EventRsvp(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, FacebookEvent.RsvpStatus var7) {
      String var8 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "events.rsvp", var8, var4);
      Map var13 = this.mParams;
      String var14 = Long.toString(System.currentTimeMillis());
      var13.put("call_id", var14);
      this.mParams.put("session_key", var3);
      Map var17 = this.mParams;
      String var18 = Long.toString(var5);
      var17.put("eid", var18);
      Map var20 = this.mParams;
      String var21 = FacebookEvent.getRsvpStatusString(var7);
      var20.put("rsvp_status", var21);
      this.mEventId = var5;
      this.mNewStatus = var7;
      this.mSuccess = (boolean)0;
   }

   public long getEventId() {
      return this.mEventId;
   }

   public boolean getSuccess() {
      return this.mSuccess;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.VALUE_TRUE;
      if(var2 == var3) {
         this.mSuccess = (boolean)1;
         ContentValues var4 = new ContentValues();
         Integer var5 = Integer.valueOf(this.mNewStatus.status.ordinal());
         var4.put("rsvp_status", var5);
         Uri var6 = EventsProvider.EVENT_EID_CONTENT_URI;
         String var7 = Long.toString(this.mEventId);
         Uri var8 = Uri.withAppendedPath(var6, var7);
         this.mContext.getContentResolver().update(var8, var4, (String)null, (String[])null);
      }
   }
}
