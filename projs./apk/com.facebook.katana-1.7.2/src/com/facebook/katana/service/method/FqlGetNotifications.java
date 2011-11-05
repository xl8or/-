package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookNotification;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetNotifications extends FqlGeneratedQuery {

   public static final String NOTIFICATIONS_QUERY = "(recipient_id=%1) AND is_hidden = 0 AND is_mobile_ready AND object_type IN (\'stream\', \'group\', \'friend\', \'event\', \'photo\') ORDER BY created_time DESC LIMIT 50";
   private List<FacebookNotification> mNotifications;


   public FqlGetNotifications(Context var1, Intent var2, String var3, long var4, ApiMethodListener var6) {
      String var7 = buildQuery(var4);
      super(var1, var2, var3, var6, "notification", var7, FacebookNotification.class);
   }

   private static String buildQuery(long var0) {
      String var2 = "" + var0;
      return "(recipient_id=%1) AND is_hidden = 0 AND is_mobile_ready AND object_type IN (\'stream\', \'group\', \'friend\', \'event\', \'photo\') ORDER BY created_time DESC LIMIT 50".replaceFirst("%1", var2);
   }

   public List<FacebookNotification> getNotifications() {
      return this.mNotifications;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, FacebookNotification.class);
      this.mNotifications = var2;
   }
}
