package com.htc.android.mail.eassvc.core;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.common.ExchangeSyncSources;
import com.htc.android.mail.eassvc.common.HttpClientFactory;
import com.htc.android.mail.eassvc.core.WbxmlParser;
import com.htc.android.mail.eassvc.core.WbxmlSerializer;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.pim.eas.EASGalSearchResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import org.apache.harmony.luni.util.Base64;
import org.apache.http.client.methods.HttpPost;
import org.xmlpull.v1.XmlSerializer;

public class GALSearcher {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   public static final String TAG = "EAS_GALSearcher";
   private int MaxRange = 99;
   private int MinRange = 0;
   private int defaultSearchCount;
   private ExchangeAccount mAccount;
   private Context mContext;
   private AndroidHttpClient mHttpClient;
   private String mLoginCredential;
   private String policyKey;


   public GALSearcher(Context var1, ExchangeSyncSources var2, boolean var3) {
      int var4 = this.MaxRange;
      int var5 = this.MinRange;
      int var6 = var4 - var5 + 1;
      this.defaultSearchCount = var6;
      if(var3) {
         this.MaxRange = 99;
      } else {
         this.MaxRange = 99;
      }

      int var7 = this.MaxRange;
      int var8 = this.MinRange;
      int var9 = var7 - var8 + 1;
      this.defaultSearchCount = var9;
      ExchangeAccount var10 = var2.account;
      this.init(var10);
      String var11 = var2.policyKey;
      this.policyKey = var11;
      this.mContext = var1;
   }

   private byte[] createGALSearch25WBXMLOutput(String var1, String var2, String var3) throws IOException {
      WbxmlSerializer var4 = new WbxmlSerializer();
      String[] var5 = EASSyncCommon.EAS_SEARCH_TBL;
      var4.setTagTable(15, var5);
      ByteArrayOutputStream var6 = new ByteArrayOutputStream();
      var4.setOutput(var6, (String)null);
      var4.startDocument("UTF-8", (Boolean)null);
      XmlSerializer var7 = var4.startTag((String)null, "Search");
      XmlSerializer var8 = var4.startTag((String)null, "Store");
      XmlSerializer var9 = var4.startTag((String)null, "Name");
      var4.text(var1);
      XmlSerializer var11 = var4.endTag((String)null, "Name");
      XmlSerializer var12 = var4.startTag((String)null, "Query");
      byte[] var13 = var2.getBytes("UTF-8");
      int var14 = var13.length;
      String var15 = new String();

      for(int var16 = 0; var16 < var14; ++var16) {
         StringBuilder var17 = (new StringBuilder()).append(var15);
         char var18 = (char)(var13[var16] & 255);
         var15 = var17.append(var18).toString();
      }

      var4.text(var15);
      XmlSerializer var20 = var4.endTag((String)null, "Query");
      XmlSerializer var21 = var4.startTag((String)null, "Options");
      XmlSerializer var22 = var4.startTag((String)null, "Range");
      var4.text(var3);
      XmlSerializer var24 = var4.endTag((String)null, "Range");
      XmlSerializer var25 = var4.endTag((String)null, "Options");
      XmlSerializer var26 = var4.endTag((String)null, "Store");
      XmlSerializer var27 = var4.endTag((String)null, "Search");
      var4.endDocument();
      var4.flush();
      return var6.toByteArray();
   }

   private HttpPost createHttpPost(String var1, String var2) throws URISyntaxException {
      StringBuilder var3 = new StringBuilder();
      String var4;
      if(this.mAccount.requireSSL == 1) {
         var4 = "https";
      } else {
         var4 = "http";
      }

      StringBuilder var5 = var3.append(var4).append("://");
      String var6 = this.mAccount.serverName;
      StringBuilder var7 = var5.append(var6).append("/").append("Microsoft-Server-ActiveSync?").append("Cmd=").append(var1).append("&").append("User=");
      String var8 = this.mAccount.userName;
      StringBuilder var9 = var7.append(var8).append("&").append("DeviceId=");
      String var10 = this.mAccount.deviceID;
      StringBuilder var11 = var9.append(var10).append("&").append("DeviceType=");
      String var12 = this.mAccount.deviceType;
      String var13 = var11.append(var12).toString();
      HttpPost var14 = new HttpPost(var13);
      var14.addHeader("Content-Type", "application/vnd.ms-sync.wbxml");
      var14.addHeader("MS-ASProtocolVersion", var2);
      if(this.policyKey == null) {
         var14.addHeader("X-MS-PolicyKey", "0");
      } else {
         String var18 = this.policyKey;
         var14.addHeader("X-MS-PolicyKey", var18);
      }

      StringBuilder var15 = (new StringBuilder()).append("Basic ");
      String var16 = this.mLoginCredential;
      String var17 = var15.append(var16).toString();
      var14.addHeader("Authorization", var17);
      return var14;
   }

   private EASGalSearchResult doSearch(String param1, int param2, int param3) {
      // $FF: Couldn't be decompiled
   }

   private AndroidHttpClient getHttpClient() {
      if(this.mHttpClient == null) {
         Context var1 = this.mContext;
         long var2 = this.mAccount.accountId;
         AndroidHttpClient var4 = HttpClientFactory.createHttpClient(var1, var2);
         this.mHttpClient = var4;
      }

      return this.mHttpClient;
   }

   private void processSearchGALResponses(WbxmlParser param1, EASGalSearchResult param2) {
      // $FF: Couldn't be decompiled
   }

   public void init(ExchangeAccount var1) {
      this.mAccount = var1;
      if(TextUtils.isEmpty(this.mAccount.domainName)) {
         StringBuilder var2 = new StringBuilder();
         String var3 = this.mAccount.userName;
         StringBuilder var4 = var2.append(var3).append(":");
         String var5 = this.mAccount.password;
         String var6 = var4.append(var5).toString();
         this.mLoginCredential = var6;
      } else {
         StringBuilder var10 = new StringBuilder();
         String var11 = this.mAccount.domainName;
         StringBuilder var12 = var10.append(var11).append("\\");
         String var13 = this.mAccount.userName;
         StringBuilder var14 = var12.append(var13).append(":");
         String var15 = this.mAccount.password;
         String var16 = var14.append(var15).toString();
         this.mLoginCredential = var16;
      }

      try {
         byte[] var7 = this.mLoginCredential.getBytes();
         Charset var8 = Charset.forNameUEE("UTF-8");
         String var9 = Base64.encode(var7, var8);
         this.mLoginCredential = var9;
      } catch (UnsupportedEncodingException var17) {
         var17.printStackTrace();
      }
   }

   public void release() {
      if(this.mHttpClient != null) {
         this.mHttpClient.close();
      }

      this.mHttpClient = null;
   }

   public void resetHttpClient() {
      if(this.mHttpClient != null) {
         this.mHttpClient.close();
      }

      Context var1 = this.mContext;
      long var2 = this.mAccount.accountId;
      AndroidHttpClient var4 = HttpClientFactory.createHttpClient(var1, var2);
      this.mHttpClient = var4;
   }

   public EASGalSearchResult searchGAL(String var1) {
      synchronized(this){}
      boolean var7 = false;

      EASGalSearchResult var3;
      try {
         var7 = true;
         int var2 = this.defaultSearchCount - 1;
         var3 = this.doSearch(var1, 0, var2);
         var7 = false;
      } finally {
         if(var7) {
            ;
         }
      }

      return var3;
   }
}
