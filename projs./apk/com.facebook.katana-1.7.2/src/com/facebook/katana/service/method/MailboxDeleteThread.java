package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class MailboxDeleteThread extends ApiMethod {

   private final int mFolder;


   public MailboxDeleteThread(Context var1, Intent var2, String var3, long var4, int var6, ApiMethodListener var7) {
      String var8 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "mailbox.deleteThread", var8, var7);
      Map var13 = this.mParams;
      StringBuilder var14 = (new StringBuilder()).append("");
      long var15 = System.currentTimeMillis();
      String var17 = var14.append(var15).toString();
      var13.put("call_id", var17);
      this.mParams.put("session_key", var3);
      Map var20 = this.mParams;
      String var21 = "" + var4;
      var20.put("tid", var21);
      Map var23 = this.mParams;
      String var24 = "" + var6;
      var23.put("folder", var24);
      this.mFolder = var6;
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         ContentResolver var3 = this.mContext.getContentResolver();
         String var4 = (String)this.mParams.get("tid");
         Uri var5 = Uri.withAppendedPath(MailboxProvider.getThreadsTidFolderUri(this.mFolder), var4);
         var3.delete(var5, (String)null, (String[])null);
         Uri var7 = Uri.withAppendedPath(MailboxProvider.getMessagesTidFolderUri(this.mFolder), var4);
         var3.delete(var7, (String)null, (String[])null);
         Uri var9 = MailboxProvider.PROFILES_PRUNE_CONTENT_URI;
         var3.delete(var9, (String)null, (String[])null);
      }
   }
}
