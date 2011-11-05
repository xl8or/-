package com.google.android.finsky.billing.creditcard;

import android.net.Uri;
import com.android.volley.AuthFailureException;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.Maps;
import java.util.HashMap;
import java.util.Map;

public class EscrowRequest extends StringRequest {

   private static final float BACKOFF_MULTIPLIER = 0.0F;
   private static final String CARD_NUMBER_PARAM_KEY = "cardNumber";
   private static final String CVC_PARAM_KEY = "cvv";
   private static final String ESCROW_PARAMS = "?s7e=cardNumber%3Bcvv";
   private static final int MAX_RETRIES = 0;
   private static final int TIMEOUT_MS = 10000;
   private static final String USER_ID_PARAM_KEY = "gid";
   private final String mCreditCardNumber;
   private final String mCvc;
   private final int mUserId;


   public EscrowRequest(int var1, String var2, String var3, Response.Listener<String> var4, Response.ErrorListener var5) {
      String var6 = getAndCheckUrl();
      super(var6, var4, var5);
      DefaultRetryPolicy var7 = new DefaultRetryPolicy(10000, 0, 0.0F);
      this.setRetryPolicy(var7);
      this.mUserId = var1;
      this.mCreditCardNumber = var2;
      this.mCvc = var3;
   }

   private static String getAndCheckUrl() {
      StringBuilder var0 = new StringBuilder();
      String var1 = (String)G.checkoutEscrowUrl.get();
      String var2 = var0.append(var1).append("?s7e=cardNumber%3Bcvv").toString();
      Uri var3 = Uri.parse(var2);
      if(!var3.getScheme().equals("https") && !var3.getHost().endsWith("corp.google.com")) {
         throw new SecurityException("Unsafe escrow URL.");
      } else {
         return var2;
      }
   }

   public Map<String, String> getPostParams() throws AuthFailureException {
      HashMap var1 = Maps.newHashMap();
      String var2 = Integer.toString(this.mUserId);
      var1.put("gid", var2);
      String var4 = this.mCreditCardNumber;
      var1.put("cardNumber", var4);
      String var6 = this.mCvc;
      var1.put("cvv", var6);
      return var1;
   }

   public boolean isDrainable() {
      return false;
   }
}
