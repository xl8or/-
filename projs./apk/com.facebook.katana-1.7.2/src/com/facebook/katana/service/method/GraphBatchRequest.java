package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.GraphRequestResponse;
import com.facebook.katana.service.method.GraphApiMethod;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphBatchRequest extends GraphApiMethod {

   private static final String TAG = "GraphApiMethod";
   private final List<GraphApiMethod> mRequests;
   protected List<GraphRequestResponse> responses;


   public GraphBatchRequest(Context var1, String var2, String var3, List<GraphApiMethod> var4) {
      super(var1, var2, "POST", (String)null, var3);
      this.mRequests = var4;
      Map var9 = this.mParams;
      String var10 = this.constructBatchParam();
      var9.put("batch", var10);
   }

   public GraphBatchRequest(Context var1, String var2, List<GraphApiMethod> var3) {
      this(var1, (String)null, var2, var3);
   }

   protected String constructBatchParam() {
      JSONArray var1 = new JSONArray();
      Iterator var2 = this.mRequests.iterator();

      while(var2.hasNext()) {
         GraphApiMethod var3 = (GraphApiMethod)var2.next();
         JSONObject var4 = new JSONObject();

         try {
            String var5 = var3.mHttpMethod;
            var4.put("method", var5);
            if(var3.mHttpMethod.equals("POST")) {
               String var7 = var3.buildUrl((boolean)0, (boolean)0);
               var4.put("relative_url", var7);
               String var9 = var3.buildQueryString().toString();
               var4.put("body", var9);
            } else {
               String var13 = var3.buildUrl((boolean)0, (boolean)1);
               var4.put("relative_url", var13);
            }

            var1.put(var4);
         } catch (JSONException var16) {
            Log.e("GraphApiMethod", "error while constructing the batch param", var16);
         } catch (UnsupportedEncodingException var17) {
            Log.e("GraphApiMethod", "error encoding something for the batch param", var17);
         }
      }

      return var1.toString();
   }

   protected void parseResponse(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, GraphRequestResponse.class);
      this.responses = var2;
   }

   public void start() {
      try {
         if(this.mHttpMethod.equals("POST")) {
            byte[] var1 = this.buildQueryString().toString().getBytes("UTF-8");
            ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
            Context var3 = this.mContext;
            String var4 = this.buildUrl((boolean)1, (boolean)0);
            ByteArrayOutputStream var5 = new ByteArrayOutputStream(8192);
            HttpOperation.HttpOperationListener var6 = this.mHttpListener;
            HttpOperation var7 = new HttpOperation(var3, var4, var2, var5, "application/x-www-form-urlencoded", var6, (boolean)1);
            this.mHttpOp = var7;
            this.mHttpOp.start();
         } else {
            throw new IllegalArgumentException("HTTP method must be POST for GraphBatchRequest");
         }
      } catch (Exception var9) {
         var9.printStackTrace();
         if(this.mListener != null) {
            this.mListener.onOperationComplete(this, 0, (String)null, var9);
         }
      }
   }
}
