package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.Constants;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPhonebookContact;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class PhonebookLookup extends ApiMethod implements ApiMethodCallback {

   protected static final String ENTRIES_PARAM = "entries";
   protected static final String INCLUDE_NON_FB_PARAM = "include_non_fb";
   protected static final String NOT_FOR_SYNC = "not_for_sync";
   private List<FacebookPhonebookContact> mContacts;


   public PhonebookLookup(Context var1, Intent var2, String var3, List<FacebookPhonebookContact> var4, boolean var5, String var6, ApiMethodListener var7) {
      String var8 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "POST", "phonebook.lookup", var8, var7);
      Map var13 = this.mParams;
      String var14 = String.valueOf(System.currentTimeMillis());
      var13.put("call_id", var14);
      this.mParams.put("session_key", var3);
      Map var17 = this.mParams;
      String var18 = FacebookPhonebookContact.jsonEncode(var4);
      var17.put("entries", var18);
      Map var20 = this.mParams;
      String var21;
      if(var5) {
         var21 = "1";
      } else {
         var21 = "0";
      }

      var20.put("include_non_fb", var21);
      this.mParams.put("country_code", var6);
      Object var24 = this.mParams.put("not_for_sync", "1");
      ArrayList var25 = new ArrayList();
      this.mContacts = var25;
   }

   public static String lookup(AppSession var0, Context var1, List<FacebookPhonebookContact> var2, boolean var3, String var4) {
      String var5 = var0.getSessionInfo().sessionKey;
      Object var10 = null;
      PhonebookLookup var11 = new PhonebookLookup(var1, (Intent)null, var5, var2, var3, var4, (ApiMethodListener)var10);
      Object var15 = null;
      return var0.postToService(var1, var11, 1001, 1020, (Bundle)var15);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         List var10 = this.getContacts();
         var9.onPhonebookLookupComplete(var1, var4, var5, var6, var7, var10);
      }

   }

   public List<FacebookPhonebookContact> getContacts() {
      return this.mContacts;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, FacebookPhonebookContact.class);
      this.mContacts = var2;
   }
}
