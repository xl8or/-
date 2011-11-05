package com.htc.android.mail.eassvc.core;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import com.android.internal.util.FastXmlSerializer;
import com.htc.android.mail.Headers;
import com.htc.android.mail.Mail;
import com.htc.android.mail.Mailaddress;
import com.htc.android.mail.eassvc.common.EASEMail;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.common.HttpClientFactory;
import com.htc.android.mail.eassvc.core.BaseSyncSource;
import com.htc.android.mail.eassvc.core.BasicSyncListener;
import com.htc.android.mail.eassvc.core.EASRequestController;
import com.htc.android.mail.eassvc.core.SyncException;
import com.htc.android.mail.eassvc.core.SyncListener;
import com.htc.android.mail.eassvc.core.SyncManager;
import com.htc.android.mail.eassvc.mail.MailManager;
import com.htc.android.mail.eassvc.pim.EASMoveItems;
import com.htc.android.mail.eassvc.pim.EASOptions;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.mail.eassvc.util.AccountUtil;
import com.htc.android.mail.eassvc.util.EASLog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.client.methods.HttpPost;
import org.xmlpull.v1.XmlSerializer;

public class MailSyncSource extends BaseSyncSource {

   private static boolean DEBUG = Mail.EAS_DEBUG;
   private static final String EAS_ATTACHMENT_ALWASY_DOWNLOAD_SIZE = "5";
   private static final String EAS_MAIL_CFG_FILE_NAME = "eas_mail.prefs";
   private static final String EAS_SYNCSRC_CFG_ENABLED = "Enabled";
   private static final String EAS_SYNCSRC_CFG_FILE_ENCODING = "utf-8";
   private static final String EAS_SYNCSRC_CFG_FILE_FEATURE = "http://xmlpull.org/v1/doc/features.html#indent-output";
   private static final String EAS_SYNCSRC_CFG_FOLDER_SYNC_KEY = "FolderSyncKey";
   private static final String EAS_SYNCSRC_CFG_LAST_SYNC_ERROR_CODE = "LastSyncErrorCode";
   private static final String EAS_SYNCSRC_CFG_LAST_SYNC_RESULT = "LastSyncResult";
   private static final String EAS_SYNCSRC_CFG_LAST_SYNC_TIME = "LastSyncTime";
   private static final String EAS_SYNCSRC_CFG_ROOT_SECTION = "Sync";
   private static final String TAG = "MailSyncSource";
   private static Object mLockObj = new Object();
   private String dynamicWindowSize = "50";
   private boolean mCollectionInited = 0;
   private Context mContext = null;
   private long mCurrentItemNum = 0L;
   private String mDefaultInboxColID = null;
   private boolean mIsSending = 0;
   private boolean mOnlyDelete = 0;
   private AndroidHttpClient mOutHttpClient;
   private HttpPost mOutHttpPost = null;
   private long mTotalItemCount = 0L;
   private MailManager mm = null;
   private File prefFile;
   private ArrayList<EASSyncCommon.EASCollection> svrCollection;


   public MailSyncSource(Context var1, ExchangeAccount var2) {
      super(var1, var2, 3);
      ArrayList var3 = new ArrayList();
      this.svrCollection = var3;
      this.mContext = var1;
      this.mOutHttpClient = null;
      this.mCollectionInited = (boolean)0;
      this.mCurrentItemNum = 0L;
      long var4 = var2.accountId;
      File var6 = AccountUtil.getAccountConfigPath(var1, var4);
      File var7 = new File(var6, "eas_mail.prefs");
      this.prefFile = var7;
      BasicSyncListener var8 = new BasicSyncListener(var1);
      var8.setSyncSrcType(3);
      this.setListener(var8);
      MailManager var9 = new MailManager(var1, this);
      this.mm = var9;
      this.loadDataFromFile();
      this.loadData();
   }

   private void checkingSvrCollection() {
      if(this.svrCollection == null || this.svrCollection.size() <= 0) {
         long var1 = this.mAccount.accountId;
         EASLog.e("MailSyncSource", var1, "! checkingSvrCollection, collection is empty");
         if(this.svrCollection == null) {
            ArrayList var3 = new ArrayList();
            this.svrCollection = var3;
         }

         ArrayList var4 = this.svrCollection;
         synchronized(var4) {
            if(this.mm != null) {
               MailManager var5 = this.mm;
               ArrayList var6 = this.svrCollection;
               var5.retrieveCollectionFromDB(var6);
            } else {
               long var9 = this.mAccount.accountId;
               EASLog.e("MailSyncSource", var9, "! checkingSvrCollection, mm is null");
            }

            if(this.svrCollection != null && this.svrCollection.size() > 0) {
               long var12 = this.mAccount.accountId;
               StringBuilder var14 = (new StringBuilder()).append("! checkingSvrCollection, reload collection: ");
               int var15 = this.svrCollection.size();
               String var16 = var14.append(var15).toString();
               EASLog.d("MailSyncSource", var12, var16);
            } else {
               long var7 = this.mAccount.accountId;
               EASLog.e("MailSyncSource", var7, "! checkingSvrCollection, reload collection failed");
            }

         }
      }
   }

   private boolean contain(String[] var1, String var2) {
      String[] var3 = var1;
      int var4 = var1.length;
      int var5 = 0;

      boolean var6;
      while(true) {
         if(var5 >= var4) {
            var6 = false;
            break;
         }

         if(var3[var5].equals(var2)) {
            var6 = true;
            break;
         }

         ++var5;
      }

      return var6;
   }

   private void fillHierarchyName(ArrayList<EASSyncCommon.EASCollection> var1) {
      synchronized(this){}

      try {
         if(DEBUG) {
            long var2 = this.mAccount.accountId;
            EASLog.v("MailSyncSource", var2, "> Fill hierarchy name");
         }

         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            EASSyncCommon.EASCollection var5 = (EASSyncCommon.EASCollection)var4.next();
            String var6 = this.recurrsiveFindName(var5);
            var5.HierarchyName = var6;
            if(DEBUG) {
               long var7 = this.mAccount.accountId;
               StringBuilder var9 = (new StringBuilder()).append("hierarchyName: ");
               String var10 = var5.HierarchyName;
               String var11 = var9.append(var10).toString();
               EASLog.v("MailSyncSource", var7, var11);
            }
         }

         if(DEBUG) {
            long var13 = this.mAccount.accountId;
            EASLog.v("MailSyncSource", var13, "< Fill hierarchy name");
         }
      } finally {
         ;
      }

   }

   private void generateXMLData(OutputStream var1, boolean var2) {
      FastXmlSerializer var3 = new FastXmlSerializer();

      try {
         var3.setOutput(var1, "utf-8");
         Boolean var4 = Boolean.valueOf((boolean)1);
         var3.startDocument((String)null, var4);
         var3.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", (boolean)1);
         XmlSerializer var5 = var3.startTag((String)null, "Sync");
         XmlSerializer var6 = var3.startTag((String)null, "version");
         String var7 = String.valueOf(1);
         var3.text(var7);
         XmlSerializer var9 = var3.endTag((String)null, "version");
         XmlSerializer var10 = var3.startTag((String)null, "LastSyncErrorCode");
         String var11 = String.valueOf(this.mLastSyncInfo.lastSyncErrorCode);
         var3.text(var11);
         XmlSerializer var13 = var3.endTag((String)null, "LastSyncErrorCode");
         XmlSerializer var14 = var3.startTag((String)null, "LastSyncResult");
         String var15 = String.valueOf(this.mLastSyncInfo.lastSyncResult);
         var3.text(var15);
         XmlSerializer var17 = var3.endTag((String)null, "LastSyncResult");
         XmlSerializer var18 = var3.startTag((String)null, "LastSyncTime");
         String var19 = String.valueOf(this.mLastSyncInfo.lastSyncTime);
         var3.text(var19);
         XmlSerializer var21 = var3.endTag((String)null, "LastSyncTime");
         XmlSerializer var22 = var3.startTag((String)null, "Enabled");
         String var23 = String.valueOf(this.mEnabled);
         var3.text(var23);
         XmlSerializer var25 = var3.endTag((String)null, "Enabled");
         XmlSerializer var26 = var3.endTag((String)null, "Sync");
         var3.endDocument();
      } catch (Exception var27) {
         var27.printStackTrace();
      }
   }

   private void initMailSyncSource() {
      ArrayList var1 = new ArrayList();
      this.svrCollection = var1;
      Context var2 = this.mContext;
      MailManager var3 = new MailManager(var2, this);
      this.mm = var3;
      this.mCurrentItemNum = 0L;
      this.mTotalItemCount = 0L;
   }

   private String recurrsiveFindName(EASSyncCommon.EASCollection var1) {
      String var2;
      if(var1 != null && !TextUtils.isEmpty(var1.DisplayName)) {
         if(!TextUtils.isEmpty(var1.ParentID) && !var1.ParentID.equals("0") && !var1.ParentID.equals("-1")) {
            String var3 = var1.ParentID;
            EASSyncCommon.EASCollection var4 = this.getMailboxBySvrId(var3);
            StringBuilder var5 = new StringBuilder();
            String var6 = this.recurrsiveFindName(var4);
            StringBuilder var7 = var5.append(var6).append("\\");
            String var8 = var1.DisplayName;
            var2 = var7.append(var8).toString();
         } else {
            var2 = var1.DisplayName;
         }
      } else {
         var2 = "";
      }

      return var2;
   }

   public void addSendMeetingMailRecord(long var1, long var3) {
      this.mm.addSendMeetingMailRecord(var1, var3);
   }

   public void cleanSyncFailRecord(EASSyncCommon.EASCollection var1) {
      MailManager var2 = this.mm;
      String var3 = var1.ServerID;
      String var4 = var1.SyncKey;
      var2.cleanExisted(var3, var4);
   }

   public void cleanTrackStatus(ArrayList<String> var1) {
      this.mm.cleanTrackStatus(var1);
   }

   public void combineMoveCommand(EASMoveItems var1) {
      this.mm.combineMoveCommand(var1);
   }

   public void createHttpClient() {
      if(this.mOutHttpClient != null) {
         this.mOutHttpClient.close();
      }

      Context var1 = this.mContext;
      long var2 = this.mAccount.accountId;
      AndroidHttpClient var4 = HttpClientFactory.createHttpClient(var1, var2);
      this.mOutHttpClient = var4;
   }

   public String decodeMailAddress(String var1) {
      String var2;
      if(TextUtils.isEmpty(var1)) {
         var2 = "";
      } else {
         StringBuilder var3 = new StringBuilder();
         byte var4 = 1;

         try {
            ArrayList var5 = Headers.splitMailAddress(var1, (boolean)var4, "utf-8", "utf-8");
            if(var5 != null && var5.size() > 0) {
               if(DEBUG) {
                  long var7 = this.mAccount.accountId;
                  StringBuilder var9 = (new StringBuilder()).append("mail address size: ");
                  int var10 = var5.size();
                  String var11 = var9.append(var10).toString();
                  EASLog.v("MailSyncSource", var7, var11);
               }

               int var12 = 0;

               while(true) {
                  int var13 = var5.size();
                  if(var12 >= var13) {
                     break;
                  }

                  Mailaddress var14 = (Mailaddress)var5.get(var12);
                  int var15 = var12 + 1;
                  int var16 = var5.size();
                  if(var15 == var16) {
                     StringBuilder var17 = var3.append("\"");
                     String var18 = var14.mDisplayName;
                     StringBuilder var19 = var17.append(var18).append("\" <");
                     String var20 = var14.mEmail;
                     StringBuilder var21 = var19.append(var20).append(">");
                  } else {
                     StringBuilder var22 = var3.append("\"");
                     String var23 = var14.mDisplayName;
                     StringBuilder var24 = var22.append(var23).append("\" <");
                     String var25 = var14.mEmail;
                     StringBuilder var26 = var24.append(var25).append(">, ");
                  }

                  ++var12;
               }
            } else {
               var3.append(var1);
            }
         } catch (Exception var27) {
            var27.printStackTrace();
            var3 = new StringBuilder(var1);
         }

         var2 = var3.toString();
      }

      return var2;
   }

   public boolean deleteAccount() throws SQLiteFullException {
      this.initMailSyncSource();
      return super.deleteAccount();
   }

   public void deleteCancelledAttachment(EASRequestController.FetchAttachmentItem var1) {
      this.mm.deleteCancelledAttachment(var1);
   }

   public boolean deleteMail(long var1, boolean var3, boolean var4) {
      return this.mm.deleteMail(var1, var3, var4);
   }

   public void deletePIMAppData() {}

   public int deleteSurplusMail() {
      return this.mm.deleteSurplusMail();
   }

   public boolean easTrackDelProced(long var1, ArrayList<String> var3) {
      return this.mm.easTrackDelProced(var1, var3);
   }

   public boolean easTrackMoveMailSuccessProced(ArrayList<EASSyncCommon.EASMoveItemsResp> var1) {
      return this.mm.easTrackMoveMailSuccessProced(var1);
   }

   public boolean easTrackUpdProced(long var1, ArrayList<EASSyncCommon.EASUpdInfo> var3) {
      return this.mm.easTrackUpdProced(var1, var3);
   }

   public void endSync(int var1) throws SyncException {
      this.endSync(var1, (boolean)1);
   }

   public void endSync(int var1, boolean var2) throws SyncException {
      if(var2) {
         super.endSync(var1);
         this.mm.updateAccountLastUpdateTime();
      } else {
         this.mIsRunning = (boolean)0;
         if(this.listener != null) {
            SyncListener var3 = this.listener;
            long var4 = this.mAccount.accountId;
            var3.endSync(var4, var1);
         }
      }

      this.mCurrentItemNum = 0L;
   }

   public long getAccountId() {
      return this.mAccount.accountId;
   }

   public String getAttachmentMimeType(String var1) {
      return this.mm.getAttachmentMimeType(var1);
   }

   public long getAttachmentSize(String var1) {
      return this.mm.getAttachmentSize(var1);
   }

   public long getCurrentItemNum() {
      return this.mCurrentItemNum;
   }

   public String getDefaultInboxColID() {
      if(this.mDefaultInboxColID == null) {
         String var1 = this.mm.getDefaultMailboxId();
         this.mDefaultInboxColID = var1;
      }

      return this.mDefaultInboxColID;
   }

   public String[] getDefaultSyncMailbox() {
      ArrayList var1 = new ArrayList();
      ArrayList var2 = this.svrCollection;
      synchronized(var2) {
         Iterator var3 = this.svrCollection.iterator();

         while(var3.hasNext()) {
            EASSyncCommon.EASCollection var4 = (EASSyncCommon.EASCollection)var3.next();
            if(var4.defaultSync == 1) {
               String var5 = var4.ServerID;
               var1.add(var5);
            }
         }
      }

      String[] var8 = new String[0];
      return (String[])var1.toArray(var8);
   }

   public ArrayList<String> getDelList(long var1, String var3) {
      return this.mm.getDelList(var1, var3);
   }

   public EASSyncCommon.EASCollection getMailboxBySvrId(String var1) {
      EASSyncCommon.EASCollection var2;
      if(TextUtils.isEmpty(var1)) {
         var2 = null;
      } else {
         synchronized(var1) {
            Iterator var3 = this.svrCollection.iterator();

            while(true) {
               if(var3.hasNext()) {
                  EASSyncCommon.EASCollection var4 = (EASSyncCommon.EASCollection)var3.next();
                  String var5 = var4.ServerID;
                  if(!var1.equals(var5)) {
                     continue;
                  }

                  var2 = var4;
                  break;
               }

               var2 = null;
               break;
            }
         }
      }

      return var2;
   }

   public MailManager getManager() {
      return this.mm;
   }

   public boolean getOnlyDeleteIntentFlag() {
      return this.mOnlyDelete;
   }

   public AndroidHttpClient getOutHttpClient() {
      if(this.mOutHttpClient == null) {
         this.createHttpClient();
      }

      return this.mOutHttpClient;
   }

   public HttpPost getOutHttpPost() {
      return this.mOutHttpPost;
   }

   public ArrayList<EASSyncCommon.EASCollection> getSyncCollection() {
      if(!this.mCollectionInited) {
         if(DEBUG) {
            long var1 = this.mAccount.accountId;
            EASLog.d("MailSyncSource", var1, "getSyncCollection: loading mail collection");
         }

         this.loadData();
      }

      return this.svrCollection;
   }

   public String getSyncKey() {
      String var1;
      if(this.svrCollection != null && this.svrCollection.size() > 0) {
         String var2 = "";
         int var3 = this.svrCollection.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            EASSyncCommon.EASCollection var5 = (EASSyncCommon.EASCollection)this.svrCollection.get(var4);
            String var6 = var5.Type;
            String var7 = Integer.toString(2);
            if(var6.equals(var7)) {
               var2 = var5.SyncKey;
               break;
            }
         }

         if(var2 == null) {
            var2 = "";
         }

         var1 = var2;
      } else {
         var1 = "";
      }

      return var1;
   }

   public long getTotalItemCount() {
      return this.mTotalItemCount;
   }

   public ArrayList<EASSyncCommon.EASUpdInfo> getUpdList(long var1, String var3) {
      return this.mm.getUpdList(var1, var3);
   }

   public String getWindowSize(EASOptions var1) {
      String var2;
      if(var1 != null && var1.mailAttachmentOpt == 6) {
         var2 = "5";
      } else {
         var2 = this.dynamicWindowSize;
      }

      return var2;
   }

   public String getXMLDataString(boolean var1) {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      this.generateXMLData(var2, var1);
      return var2.toString();
   }

   public int isSDsave() {
      return this.mm.isSDsave();
   }

   public boolean isSendingMail() {
      return this.mIsSending;
   }

   public void loadData() {
      // $FF: Couldn't be decompiled
   }

   public void loadDataFromFile() {
      // $FF: Couldn't be decompiled
   }

   public void processAddFolder(ArrayList<EASSyncCommon.EASCollection> param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public void processDelFolder(ArrayList<EASSyncCommon.EASCollection> var1) {
      if(var1 != null) {
         if(var1.size() > 0) {
            if(DEBUG) {
               long var2 = this.mAccount.accountId;
               StringBuilder var4 = (new StringBuilder()).append("processDelFolder: ");
               int var5 = var1.size();
               String var6 = var4.append(var5).toString();
               EASLog.d("MailSyncSource", var2, var6);
            }

            this.mm.processDelFolder(var1);
         }
      }
   }

   public ArrayList<EASEMail.AttachInfo> processMailAdd(long var1, String var3, EASEMail var4, Uri var5, EASSyncCommon.EASMailSyncResult var6) {
      MailManager var7 = this.mm;
      return var7.processMailAdd(var1, var3, var4, var5, var6);
   }

   public boolean processMailChangeList(long var1, String var3, ArrayList<EASEMail> var4, ArrayList<EASEMail> var5, ArrayList<EASEMail.AttachInfo> var6, Uri var7, SyncManager var8) throws Exception {
      MailManager var9 = this.mm;
      return var9.processMailChangeList(var1, var3, var4, var5, var6, var7);
   }

   public void processUpdFolder(ArrayList<EASSyncCommon.EASCollection> var1) {
      if(var1 != null) {
         if(var1.size() > 0) {
            if(DEBUG) {
               long var2 = this.mAccount.accountId;
               StringBuilder var4 = (new StringBuilder()).append("processUpdFolder: ");
               int var5 = var1.size();
               String var6 = var4.append(var5).toString();
               EASLog.d("MailSyncSource", var2, var6);
            }

            ArrayList var7 = this.svrCollection;
            synchronized(var7) {
               Iterator var8 = var1.iterator();

               while(var8.hasNext()) {
                  EASSyncCommon.EASCollection var9 = (EASSyncCommon.EASCollection)var8.next();
                  String var10 = var9.Type;
                  String var11 = var9.ServerID;
                  Iterator var12 = this.svrCollection.iterator();

                  while(var12.hasNext()) {
                     EASSyncCommon.EASCollection var13 = (EASSyncCommon.EASCollection)var12.next();
                     String var14 = var13.Type;
                     if(var10.equalsIgnoreCase(var14)) {
                        String var15 = var13.ServerID;
                        if(var11.equalsIgnoreCase(var15)) {
                           String var16 = var9.DisplayName;
                           var13.DisplayName = var16;
                           String var17 = var9.ParentID;
                           var13.ParentID = var17;
                           boolean var18 = var13.enableSyncDown;
                           var9.enableSyncDown = var18;
                           boolean var19 = var13.enableSyncUp;
                           var9.enableSyncUp = var19;
                           break;
                        }
                     }
                  }
               }

               if(this.svrCollection.size() <= 0) {
                  long var21 = this.mAccount.accountId;
                  EASLog.e("MailSyncSource", var21, "processUpdFolder, collection is empty");
               }
            }

            ArrayList var23 = this.svrCollection;
            this.fillHierarchyName(var23);

            EASSyncCommon.EASCollection var25;
            String var27;
            for(Iterator var24 = var1.iterator(); var24.hasNext(); var25.HierarchyName = var27) {
               var25 = (EASSyncCommon.EASCollection)var24.next();
               String var26 = var25.ServerID;
               var27 = this.getMailboxBySvrId(var26).HierarchyName;
            }

            this.mm.processUpdFolder(var1);
         }
      }
   }

   public void release() {
      this.mCollectionInited = (boolean)0;
      this.svrCollection.clear();
      if(this.mOutHttpClient != null) {
         this.mOutHttpClient.close();
      }

      this.mOutHttpClient = null;
      super.release();
   }

   public void releaseOutHttpClient() {
      if(this.mOutHttpClient != null) {
         this.mOutHttpClient.close();
         this.mOutHttpClient = null;
      }
   }

   public boolean removeAllSyncMessages(String var1) {
      return this.mm.removeAllSyncMessages(var1);
   }

   protected void removeSyncInfo() {}

   public void saveData() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void setCurrentItemNum(long var1) {
      this.mCurrentItemNum = var1;
   }

   public void setDefaultSyncMailbox(String[] var1) {
      ArrayList var2 = this.svrCollection;
      synchronized(var2) {
         EASSyncCommon.EASCollection var4;
         boolean var6;
         for(Iterator var3 = this.svrCollection.iterator(); var3.hasNext(); var4.defaultSync = var6) {
            var4 = (EASSyncCommon.EASCollection)var3.next();
            String var5 = var4.ServerID;
            var6 = this.contain(var1, var5);
         }

      }
   }

   public void setFolderEnableSyncDown() {
      // $FF: Couldn't be decompiled
   }

   public void setFolderEnableSyncUp() {
      // $FF: Couldn't be decompiled
   }

   public boolean setMoveMailFlag(EASMoveItems var1, int var2) {
      return this.mm.setMoveMailFlag(var1, var2);
   }

   public void setOnlyDeleteIntentFlag(boolean var1) {
      if(DEBUG) {
         long var2 = this.mAccount.accountId;
         String var4 = "set only delete intent flag: " + var1;
         EASLog.v("MailSyncSource", var2, var4);
      }

      this.mOnlyDelete = var1;
   }

   public void setOutHttpPost(HttpPost var1) {
      this.mOutHttpPost = var1;
   }

   public void setRunning(boolean var1) {
      if(var1) {
         SyncListener var2 = this.listener;
         long var3 = this.mAccount.accountId;
         var2.startSync(var3);
      } else {
         SyncListener var5 = this.listener;
         long var6 = this.mAccount.accountId;
         var5.endSync(var6, 0);
      }

      this.mIsRunning = var1;
   }

   public void setSendMail(boolean var1) {
      this.mIsSending = var1;
   }

   public void setSyncOptions(EASOptions var1) {
      this.mm.setSyncOptions(var1);
   }

   public void setTotalItemCount(long var1) {
      this.mTotalItemCount = var1;
   }

   public boolean updateAttachment(String var1, String var2, boolean var3) {
      return this.mm.updateAttachment(var1, var2, var3);
   }

   public void updateCollections() {
      if(DEBUG) {
         long var1 = this.mAccount.accountId;
         EASLog.d("MailSyncSource", var1, "- updateCollections(mail)");
      }

      ArrayList var3 = this.svrCollection;
      synchronized(var3) {
         MailManager var4 = this.mm;
         ArrayList var5 = this.svrCollection;
         var4.loadMailboxInfo(var5);
         this.mCollectionInited = (boolean)1;
      }
   }

   public boolean updateDownloadedSize(String var1, boolean var2) {
      return this.mm.updateDownloadedSize(var1, var2);
   }

   public boolean updateMailBody(String var1, EASEMail var2, boolean var3) throws Exception {
      return this.mm.updateMailBody(var1, var2, var3);
   }

   public void updateMailboxInfo() throws IOException {
      if(DEBUG) {
         long var1 = this.mAccount.accountId;
         EASLog.v("MailSyncSource", var1, "- updateMailboxInfo()");
      }

      MailManager var3 = this.mm;
      long var4 = this.mAccount.accountId;
      ArrayList var6 = this.svrCollection;
      var3.saveMailboxInfo(var4, var6);
   }
}
