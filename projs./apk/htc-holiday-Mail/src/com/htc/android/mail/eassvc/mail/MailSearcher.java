package com.htc.android.mail.eassvc.mail;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContentProviderOperation.Builder;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.text.TextUtils;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.eassvc.common.EASEMail;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.common.ExchangeSyncSources;
import com.htc.android.mail.eassvc.core.SyncManager;
import com.htc.android.mail.eassvc.core.WbxmlParser;
import com.htc.android.mail.eassvc.core.WbxmlSerializer;
import com.htc.android.mail.eassvc.mail.MailItem;
import com.htc.android.mail.eassvc.mail.MailManager;
import com.htc.android.mail.eassvc.pim.EASMailSearchElement;
import com.htc.android.mail.eassvc.pim.EASMailSearchResult;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.mail.eassvc.util.EASLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.http.client.methods.HttpPost;
import org.xmlpull.v1.XmlSerializer;

public class MailSearcher {

   private static boolean DEBUG = Mail.EAS_DEBUG;
   private static final int INSERT_DB_THRESHOLD = 5;
   private static final String TAG = "EAS_MailSearcher";
   private boolean mCancelSearch;
   private Context mContext;
   private ExchangeSyncSources mExSyncSources;
   private HttpPost mHttpPost;
   private String mLoginCredential;
   private String mSearchKey;
   private HashMap<String, Long> mailboxMap;
   private MailManager mm;


   public MailSearcher(Context var1, SyncManager var2) {
      HashMap var3 = new HashMap();
      this.mailboxMap = var3;
      this.mCancelSearch = (boolean)0;
      this.mExSyncSources = null;
      this.mContext = var1;
   }

   private HttpPost createHttpPost(ExchangeSyncSources var1, String var2) throws URISyntaxException {
      ExchangeAccount var3 = var1.account;
      StringBuilder var4 = new StringBuilder();
      String var5;
      if(var3.requireSSL == 1) {
         var5 = "https";
      } else {
         var5 = "http";
      }

      StringBuilder var6 = var4.append(var5).append("://");
      String var7 = var3.serverName;
      StringBuilder var8 = var6.append(var7).append("/").append("Microsoft-Server-ActiveSync?").append("Cmd=").append(var2).append("&").append("User=");
      String var9 = var3.userName;
      StringBuilder var10 = var8.append(var9).append("&").append("DeviceId=");
      String var11 = var3.deviceID;
      StringBuilder var12 = var10.append(var11).append("&").append("DeviceType=");
      String var13 = var3.deviceType;
      String var14 = var12.append(var13).toString();
      HttpPost var15 = new HttpPost(var14);
      var15.addHeader("Content-Type", "application/vnd.ms-sync.wbxml");
      String var16 = var3.protocolVer;
      var15.addHeader("MS-ASProtocolVersion", var16);
      if(var1.policyKey == null) {
         if(var3.protocolVerDouble < 14.0D) {
            var15.addHeader("X-MS-PolicyKey", "0");
         }
      } else {
         String var20 = var1.policyKey;
         var15.addHeader("X-MS-PolicyKey", var20);
      }

      StringBuilder var17 = (new StringBuilder()).append("Basic ");
      String var18 = this.mLoginCredential;
      String var19 = var17.append(var18).toString();
      var15.addHeader("Authorization", var19);
      return var15;
   }

   private byte[] createMailSearchWBXMLOutput(EASMailSearchElement var1, int var2, int var3, boolean var4) throws IOException {
      WbxmlSerializer var5 = new WbxmlSerializer();
      String[] var6 = EASSyncCommon.EAS_AIR_SYNC_BASE_TBL;
      var5.setTagTable(17, var6);
      String[] var7 = EASSyncCommon.EAS_SYNC_TBL;
      var5.setTagTable(0, var7);
      String[] var8 = EASSyncCommon.EAS_SEARCH_TBL;
      var5.setTagTable(15, var8);
      String[] var9 = EASSyncCommon.EAS_EMAIL_TBL;
      var5.setTagTable(2, var9);
      ByteArrayOutputStream var10 = new ByteArrayOutputStream();
      var5.setOutput(var10, (String)null);
      var5.startDocument("UTF-8", (Boolean)null);
      XmlSerializer var11 = var5.startTag((String)null, "Search");
      XmlSerializer var12 = var5.startTag((String)null, "Store");
      XmlSerializer var13 = var5.startTag((String)null, "Name");
      XmlSerializer var14 = var5.text("Mailbox");
      XmlSerializer var15 = var5.endTag((String)null, "Name");
      XmlSerializer var16 = var5.startTag((String)null, "Query");
      if(var1.queryList != null && var1.queryList.size() > 0) {
         int var17 = 0;

         for(int var18 = var1.queryList.size(); var17 < var18; ++var17) {
            EASMailSearchElement.QueryElement var19 = (EASMailSearchElement.QueryElement)var1.queryList.get(var17);
            if(var19.queryCondition == 1) {
               XmlSerializer var20 = var5.startTag((String)null, "And");
            } else {
               XmlSerializer var51 = var5.startTag((String)null, "Or");
            }

            XmlSerializer var21 = var5.startTag((String)null, "Class");
            XmlSerializer var22 = var5.text("Email");
            XmlSerializer var23 = var5.endTag((String)null, "Class");
            if(!TextUtils.isEmpty(var19.collectionId)) {
               XmlSerializer var24 = var5.startTag((String)null, "CollectionId");
               String var25 = var19.collectionId;
               var5.text(var25);
               XmlSerializer var27 = var5.endTag((String)null, "CollectionId");
            }

            if(!TextUtils.isEmpty(var19.dateGreaterThan)) {
               XmlSerializer var28 = var5.startTag((String)null, "GreaterThan");
               XmlSerializer var29 = var5.startTag((String)null, "DateReceived");
               XmlSerializer var30 = var5.endTag((String)null, "DateReceived");
               XmlSerializer var31 = var5.startTag((String)null, "Value");
               String var32 = var19.dateGreaterThan;
               var5.text(var32);
               XmlSerializer var34 = var5.endTag((String)null, "Value");
               XmlSerializer var35 = var5.endTag((String)null, "GreaterThan");
            }

            if(!TextUtils.isEmpty(var19.dateLessThan)) {
               XmlSerializer var36 = var5.startTag((String)null, "LessThan");
               XmlSerializer var37 = var5.startTag((String)null, "DateReceived");
               XmlSerializer var38 = var5.endTag((String)null, "DateReceived");
               XmlSerializer var39 = var5.startTag((String)null, "Value");
               String var40 = var19.dateLessThan;
               var5.text(var40);
               XmlSerializer var42 = var5.endTag((String)null, "Value");
               XmlSerializer var43 = var5.endTag((String)null, "LessThan");
            }

            if(!TextUtils.isEmpty(var19.FreeText)) {
               XmlSerializer var44 = var5.startTag((String)null, "FreeText");
               byte[] var45 = var19.FreeText.getBytes("UTF-8");
               int var46 = var45.length;
               String var47 = new String();

               for(int var48 = 0; var48 < var46; ++var48) {
                  StringBuilder var49 = (new StringBuilder()).append(var47);
                  char var50 = (char)(var45[var48] & 255);
                  var47 = var49.append(var50).toString();
               }

               var5.text(var47);
               XmlSerializer var53 = var5.endTag((String)null, "FreeText");
               String var54 = var19.FreeText;
               this.mSearchKey = var54;
            }

            if(var19.queryCondition == 1) {
               XmlSerializer var55 = var5.endTag((String)null, "And");
            } else {
               XmlSerializer var56 = var5.endTag((String)null, "Or");
            }
         }
      }

      XmlSerializer var57 = var5.endTag((String)null, "Query");
      XmlSerializer var58 = var5.startTag((String)null, "Options");
      if(var4) {
         XmlSerializer var59 = var5.startTag((String)null, "RebuildResults");
         XmlSerializer var60 = var5.endTag((String)null, "RebuildResults");
      }

      if(var1.includeSubFolder) {
         XmlSerializer var61 = var5.startTag((String)null, "DeepTraversal");
         XmlSerializer var62 = var5.endTag((String)null, "DeepTraversal");
      }

      if(var3 >= var2) {
         StringBuilder var65 = new StringBuilder();
         String var66 = Integer.toString(var2);
         StringBuilder var67 = var65.append(var66).append("-");
         String var68 = Integer.toString(var3);
         String var69 = var67.append(var68).toString();
         if(DEBUG) {
            ExchangeSyncSources var70 = this.mExSyncSources;
            String var71 = "request range: " + var69;
            EASLog.d("EAS_MailSearcher", var70, var71);
         }

         XmlSerializer var72 = var5.startTag((String)null, "Range");
         var5.text(var69);
         XmlSerializer var74 = var5.endTag((String)null, "Range");
      }

      XmlSerializer var75 = var5.startTag((String)null, "BodyPreference");
      XmlSerializer var76 = var5.startTag((String)null, "Type");
      XmlSerializer var77 = var5.text("2");
      XmlSerializer var78 = var5.endTag((String)null, "Type");
      XmlSerializer var79 = var5.startTag((String)null, "TruncationSize");
      XmlSerializer var80 = var5.text("0");
      XmlSerializer var81 = var5.endTag((String)null, "TruncationSize");
      XmlSerializer var82 = var5.endTag((String)null, "BodyPreference");
      XmlSerializer var83 = var5.startTag((String)null, "MIMESupport");
      XmlSerializer var84 = var5.text("0");
      XmlSerializer var85 = var5.endTag((String)null, "MIMESupport");
      XmlSerializer var86 = var5.endTag((String)null, "Options");
      XmlSerializer var87 = var5.endTag((String)null, "Store");
      XmlSerializer var88 = var5.endTag((String)null, "Search");
      var5.endDocument();
      var5.flush();
      return var10.toByteArray();
   }

   private void deleteSearchMoreRecord() {
      StringBuilder var1 = new StringBuilder(" _uid = ");
      String var2 = Integer.toString(EASSyncCommon.SearchServerMailAccountId);
      DatabaseUtils.appendEscapedSQLString(var1, var2);
      ContentResolver var3 = this.mContext.getContentResolver();
      Uri var4 = MailProvider.sSearchSvrMessagesURI;
      String var5 = var1.toString();
      var3.delete(var4, var5, (String[])null);
   }

   private EASMailSearchResult doSearch(EASMailSearchElement param1) throws Exception {
      // $FF: Couldn't be decompiled
   }

   private void getMailBoxToMap() {
      // $FF: Couldn't be decompiled
   }

   private void processMailAdd(ArrayList<EASEMail> var1) {
      if(var1 != null && var1.size() > 0) {
         ArrayList var3 = new ArrayList();
         int var4 = -1;

         try {
            Iterator var5 = var1.iterator();

            while(var5.hasNext()) {
               EASEMail var6 = (EASEMail)var5.next();
               HashMap var7 = this.mailboxMap;
               String var8 = var6.ClientId;
               Long var9 = (Long)var7.get(var8);
               if(var9 == null) {
                  ExchangeSyncSources var10 = this.mExSyncSources;
                  StringBuilder var11 = (new StringBuilder()).append("process mail add, Can\'t retrieve mailboxId: ");
                  String var12 = var6.ClientId;
                  String var13 = var11.append(var12).toString();
                  EASLog.e("EAS_MailSearcher", var10, var13);
                  var9 = new Long;
                  long var15 = 0L;
                  var9.<init>(var15);
               }

               MailItem var17 = new MailItem();
               MailManager var18 = this.mm;
               long var19 = var9.longValue();
               var18.populatePIMItem(var19, var6, var17);
               ContentValues var26 = var17.getMessagesCV();
               if(var26 == null) {
                  ExchangeSyncSources var27 = this.mExSyncSources;
                  EASLog.e("EAS_MailSearcher", var27, "retrieve cvMessages failed");
               } else {
                  String var28 = var6.ClientId;
                  String var30 = "_collectionId";
                  var26.put(var30, var28);
                  Builder var32 = ContentProviderOperation.newInsert(MailProvider.sSearchSvrMessagesURI);
                  var32.withValues(var26);
                  ContentProviderOperation var34 = var32.build();
                  boolean var37 = var3.add(var34);
                  ++var4;
                  int var38 = var4;
                  ContentValues var39 = var17.getPartsBodyCV();
                  if(var39 == null) {
                     EASLog.e("EAS_MailSearcher", "part content value is null");
                  } else {
                     Builder var40 = ContentProviderOperation.newInsert(MailProvider.sSearchSvrPartsURI);
                     String var42 = "_message";
                     var40.withValueBackReference(var42, var4);
                     if(var17.mMimetype != null && var17.mMimetype.equals("text/html")) {
                        if(!TextUtils.isEmpty(var17.mText)) {
                           String var46 = "_mimetype";
                           String var47 = "text/html";
                           var39.put(var46, var47);
                           String var48 = var17.mText;
                           String var50 = "_text";
                           var39.put(var50, var48);
                           var40.withValues(var39);
                           ContentProviderOperation var53 = var40.build();
                           boolean var56 = var3.add(var53);
                           ++var4;
                        }
                     } else if(var17.mMimetype != null && var17.mMimetype.equals("text/plain") && !TextUtils.isEmpty(var17.mText)) {
                        String var64 = var17.mText;
                        String var66 = "_text";
                        var39.put(var66, var64);
                        var40.withValues(var39);
                        ContentProviderOperation var69 = var40.build();
                        boolean var72 = var3.add(var69);
                        ++var4;
                     }

                     int var57 = var17.getAttachmentSize();

                     for(int var58 = 0; var58 < var57; ++var58) {
                        ContentValues var63 = var17.getPartsAttachCV(var58);
                        if(var63 != null) {
                           Builder var73 = ContentProviderOperation.newInsert(MailProvider.sSearchSvrPartsURI);
                           String var75 = "_message";
                           var73.withValueBackReference(var75, var38);
                           var73.withValues(var63);
                           ContentProviderOperation var79 = var73.build();
                           boolean var82 = var3.add(var79);
                           ++var4;
                        }
                     }
                  }
               }
            }

            if(var3 != null) {
               if(var3.size() > 0) {
                  ContentResolver var83 = this.mContext.getContentResolver();
                  String var84 = "mail";
                  var83.applyBatch(var84, var3);
               }
            }
         } catch (Exception var87) {
            var87.printStackTrace();
         }
      } else {
         ExchangeSyncSources var2 = this.mExSyncSources;
         EASLog.e("EAS_MailSearcher", var2, "process mail add failed, parameter is null");
      }
   }

   private String processMailFlagCommand(WbxmlParser var1) throws Exception {
      String var2 = "0";

      while(true) {
         int var3 = var1.getEventType();
         if(1 == var3) {
            break;
         }

         int var4 = var1.getEventType();
         if(2 == var4) {
            String var5 = var1.getName();
         } else {
            int var7 = var1.getEventType();
            if(3 == var7) {
               if(var1.getName().equals("Flag")) {
                  break;
               }
            } else {
               int var8 = var1.getEventType();
               if(4 == var8) {
                  String var9 = var1.getText();
                  if(null.equals("FlagStatus")) {
                     var2 = var9;
                  }
               }
            }
         }

         int var6 = var1.next();
      }

      return var2;
   }

   private void processSearchMailResponses(WbxmlParser param1, EASMailSearchResult param2) {
      // $FF: Couldn't be decompiled
   }

   private int respRange(String var1, MailSearcher.Range var2) {
      int var3;
      if(!TextUtils.isEmpty(var1) && var2 != null) {
         int var7;
         int var8;
         try {
            String[] var4 = var1.split("-");
            if(var4 == null || var4.length != 2) {
               var3 = 0;
               return var3;
            }

            int var5 = Integer.valueOf(var4[0]).intValue();
            var2.from = var5;
            int var6 = Integer.valueOf(var4[1]).intValue();
            var2.to = var6;
            var7 = var2.to;
            var8 = var2.from;
         } catch (Exception var9) {
            var9.printStackTrace();
            var3 = 0;
            return var3;
         }

         var3 = var7 - var8 + 1;
      } else {
         var3 = 0;
      }

      return var3;
   }

   private boolean resultMailCanBeInsert(EASEMail var1) {
      boolean var2 = true;
      boolean var3;
      if(var1 == null) {
         var3 = false;
      } else if(TextUtils.isEmpty(this.mSearchKey)) {
         ExchangeSyncSources var4 = this.mExSyncSources;
         EASLog.e("EAS_MailSearcher", var4, "search key is empty");
         var3 = false;
      } else {
         label20: {
            int var16;
            try {
               StringBuilder var5 = new StringBuilder();
               String var6 = var1.Subject;
               StringBuilder var7 = var5.append(var6).append(":");
               String var8 = var1.CC;
               StringBuilder var9 = var5.append(var8).append(":");
               String var10 = var1.From;
               StringBuilder var11 = var5.append(var10).append(":");
               String var12 = var1.To;
               StringBuilder var13 = var5.append(var12).append(":");
               String var14 = var5.toString().toLowerCase();
               String var15 = this.mSearchKey.toLowerCase();
               var16 = var14.indexOf(var15);
            } catch (Exception var19) {
               ExchangeSyncSources var18 = this.mExSyncSources;
               EASLog.e("EAS_MailSearcher", var18, var19);
               break label20;
            }

            if(var16 == -1) {
               var2 = false;
            } else {
               var2 = true;
            }
         }

         var3 = var2;
      }

      return var3;
   }

   public void cancelSearchGlobalMail() {
      if(DEBUG) {
         ExchangeSyncSources var1 = this.mExSyncSources;
         EASLog.d("EAS_MailSearcher", var1, "cancel search global mail");
      }

      this.mCancelSearch = (boolean)1;
      if(this.mHttpPost != null) {
         try {
            this.mHttpPost.abort();
         } catch (Exception var2) {
            var2.printStackTrace();
         }

         this.mHttpPost = null;
      }
   }

   public EASMailSearchResult searchGlobalMail(EASMailSearchElement var1, boolean var2) throws Exception {
      EASMailSearchResult var4;
      if(var1 != null && var1.queryList.size() > 0) {
         this.mCancelSearch = (boolean)0;
         var4 = this.doSearch(var1);
      } else {
         ExchangeSyncSources var3 = this.mExSyncSources;
         EASLog.e("EAS_MailSearcher", var3, "searchGlobalMail failed, parameter is null");
         var4 = null;
      }

      return var4;
   }

   public void setExchangeSyncSources(ExchangeSyncSources var1) {
      this.mExSyncSources = var1;
      MailManager var2 = var1.mailSyncSource.getManager();
      this.mm = var2;
   }

   public void setLoginCredential(String var1) {
      this.mLoginCredential = var1;
   }

   private class Range {

      int from;
      int to;


      private Range() {
         this.from = 0;
         this.to = 0;
      }

      // $FF: synthetic method
      Range(MailSearcher.1 var2) {
         this();
      }

      public void clear() {
         this.from = 0;
         this.to = 0;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
