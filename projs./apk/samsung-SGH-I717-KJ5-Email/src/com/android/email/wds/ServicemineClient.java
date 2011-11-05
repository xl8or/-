package com.android.email.wds;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.email.Email;
import com.android.email.wds.EmailProviderWds;
import com.android.email.wds.ServicemineParser;
import com.android.exchange.SSLSocketFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ServicemineClient {

   private static final String TAG = "ServiceMineClient >>";
   private Context mContext;
   private String mPassword = "xi7Claso";
   private String mServicemineUri;
   private String mUsername = "samsung";


   public ServicemineClient(Context var1) {
      this.mContext = var1;
   }

   public DefaultHttpClient getClient() {
      BasicHttpParams var1 = new BasicHttpParams();
      HttpVersion var2 = HttpVersion.HTTP_1_1;
      HttpProtocolParams.setVersion(var1, var2);
      HttpProtocolParams.setContentCharset(var1, "utf-8");
      HttpParams var3 = var1.setBooleanParameter("http.protocol.expect-continue", (boolean)0);
      SchemeRegistry var4 = new SchemeRegistry();
      PlainSocketFactory var5 = PlainSocketFactory.getSocketFactory();
      Scheme var6 = new Scheme("http", var5, 80);
      var4.register(var6);
      SSLSocketFactory var8 = SSLSocketFactory.getSocketFactory();
      Scheme var9 = new Scheme("https", var8, 443);
      var4.register(var9);
      ThreadSafeClientConnManager var11 = new ThreadSafeClientConnManager(var1, var4);
      return new DefaultHttpClient(var11, var1);
   }

   public EmailProviderWds getWDSResponce(String var1) {
      DefaultHttpClient var2 = this.getClient();
      StringBuilder var3 = (new StringBuilder()).append("client : ");
      String var5 = var3.append(var2).toString();
      int var6 = Log.e("ServiceMineClient >>", var5);
      EmailProviderWds var7;
      if(var2 == null) {
         var7 = null;
      } else {
         CredentialsProvider var8 = var2.getCredentialsProvider();
         AuthScope var9 = AuthScope.ANY;
         String var10 = this.mUsername;
         String var11 = this.mPassword;
         UsernamePasswordCredentials var12 = new UsernamePasswordCredentials(var10, var11);
         var8.setCredentials(var9, var12);
         StringBuilder var13 = (new StringBuilder()).append("domainName=");
         String var14 = URLEncoder.encode(var1);
         String var15 = var13.append(var14).toString();
         TelephonyManager var16 = (TelephonyManager)this.mContext.getSystemService("phone");
         String var17 = var16.getSimOperator();
         String var18 = var16.getSimOperatorName();
         String var19 = "";
         if(var17 != null && var17.length() != 0) {
            StringBuilder var20 = (new StringBuilder()).append("&hni=");
            String var21 = URLEncoder.encode(var17);
            var19 = var20.append(var21).toString();
         }

         String var22 = "";
         if(var18 != null && var18.length() != 0) {
            StringBuilder var23 = (new StringBuilder()).append("&spn=");
            String var24 = URLEncoder.encode(var18);
            var22 = var23.append(var24).toString();
         }

         Email.loge("ServiceMineClient >>", "-------------------------------------------------------");
         StringBuilder var25 = (new StringBuilder()).append("uri : ");
         String var26 = "https://servicemine-api.wdsglobal.com/servicemine-api/email";
         StringBuilder var27 = var25.append(var26).append("?");
         StringBuilder var29 = var27.append(var15);
         StringBuilder var31 = var29.append(var19);
         String var33 = var31.append(var22).toString();
         Email.loge("ServiceMineClient >>", var33);
         Email.loge("ServiceMineClient >>", "-------------------------------------------------------");
         HttpGet var34 = new HttpGet;
         StringBuilder var35 = new StringBuilder();
         String var36 = "https://servicemine-api.wdsglobal.com/servicemine-api/email";
         StringBuilder var37 = var35.append(var36).append("?");
         StringBuilder var39 = var37.append(var15);
         StringBuilder var41 = var39.append(var19);
         String var43 = var41.append(var22).toString();
         var34.<init>(var43);

         EmailProviderWds var58;
         try {
            HttpResponse var46 = var2.execute(var34);
            String var47 = (new BasicResponseHandler()).handleResponse(var46);
            String var48 = "Marvin";
            Log.d(var48, var47);
            ServicemineParser var51 = new ServicemineParser();
            StringReader var52 = new StringReader(var47);
            InputSource var55 = new InputSource(var52);
            var58 = var51.parse(var55);
            int var59 = var46.getStatusLine().getStatusCode();
            short var60 = 200;
            if(var59 != var60) {
               throw new IOException("Could not access email API");
            }

            int var61 = Log.d("Marvin", "Executed query");
         } catch (ClientProtocolException var62) {
            var62.printStackTrace();
         } catch (IOException var63) {
            var63.printStackTrace();
         } catch (ParserConfigurationException var64) {
            var64.printStackTrace();
         } catch (SAXException var65) {
            var65.printStackTrace();
         } catch (URISyntaxException var66) {
            var66.printStackTrace();
         } catch (EmailProviderWds.MissingEmailConnectionException var67) {
            var67.printStackTrace();
         }

         var7 = var58;
      }

      return var7;
   }
}
