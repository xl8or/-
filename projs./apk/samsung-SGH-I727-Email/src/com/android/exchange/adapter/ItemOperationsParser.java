package com.android.exchange.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.email.mail.Body;
import com.android.email.mail.DeviceAccessException;
import com.android.email.mail.MessagingException;
import com.android.email.mail.Part;
import com.android.email.mail.internet.MimeBodyPart;
import com.android.email.mail.internet.MimeMessage;
import com.android.email.mail.internet.MimeUtility;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.AbstractSyncParser;
import com.android.exchange.adapter.ItemOperationsAdapter;
import com.android.exchange.adapter.LoadMoreUtility;
import com.android.exchange.adapter.Parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observer;

public class ItemOperationsParser extends AbstractSyncParser {

   public static final int ITEMOPERATIONS_STATUS_CREDENTIALS_REQUIRED = 18;
   public static final int ITEMOPERATIONS_STATUS_SUCCESS = 1;
   private static final String WHERE_SERVER_ID_AND_MAILBOX_KEY = "syncServerId=? and mailboxKey=?";
   private boolean isSigned;
   private String[] mBindArguments;
   private String mMailboxIdAsString;
   private Observer mObserver;
   private OutputStream mOs;
   private boolean mResult = 0;
   String mServerId;
   private int mStatus;


   public ItemOperationsParser(InputStream var1, AbstractSyncAdapter var2) throws IOException {
      super(var1, var2);
      String[] var3 = new String[2];
      this.mBindArguments = var3;
      this.mServerId = null;
      this.mStatus = 0;
      this.mOs = null;
      this.mObserver = null;
      this.isSigned = (boolean)0;
      String var4 = Long.toString(this.mMailbox.mId);
      this.mMailboxIdAsString = var4;
   }

   private void fetchPropertiesBodyParser(EmailContent.Message var1, EmailContent.Body var2) throws IOException {
      String var3 = null;
      int var4 = '\uffff';

      while(true) {
         short var6 = 1098;
         int var7 = this.nextTag(var6);
         byte var8 = 3;
         if(var7 == var8) {
            switch(var4) {
            case 1:
               var2.mTextContent = var3;
               return;
            case 2:
               var2.mHtmlContent = var3;
               return;
            default:
               return;
            }
         }

         switch(this.tag) {
         case 1094:
            var4 = this.getValueInt();
            break;
         case 1099:
            byte var9 = 0;
            var1.mFlagTruncated = var9;
            if(!((ItemOperationsAdapter)this.mAdapter).isMIMEDataRequested) {
               var3 = this.getValue();
            } else {
               byte var10 = 0;
               var1.mFlagTruncated = var10;
               int var11 = Log.v("EmailSyncAdapter2", "EasEmailSyncParser result  ");
               Context var12 = this.mContext;
               byte var14 = 1;
               String var16 = "tempFile";
               this.skipTag((boolean)var14, var12, var16);
               FileInputStream var18 = new FileInputStream;
               StringBuilder var19 = new StringBuilder();
               File var20 = this.mContext.getFilesDir();
               String var21 = var19.append(var20).append("/tempFile").toString();
               var18.<init>(var21);
               MimeMessage var24 = null;

               label150: {
                  MimeMessage var25;
                  try {
                     var25 = new MimeMessage(var18);
                  } catch (MessagingException var105) {
                     var105.printStackTrace();
                     break label150;
                  }

                  var24 = var25;
               }

               try {
                  Body var28 = var24.getBody();
                  MimeBodyPart var29 = new MimeBodyPart;
                  String var30 = var24.getContentType();
                  var29.<init>(var28, var30);
                  Iterator var43;
                  String var49;
                  if(var24.getContentType().toString().contains("text/html")) {
                     StringBuilder var34 = (new StringBuilder()).append("1. mimeMsg.getContentType() ");
                     String var35 = var24.getContentType();
                     String var36 = var34.append(var35).toString();
                     int var37 = Log.v("EmailSyncAdapter.....2", var36);
                     ArrayList var38 = new ArrayList();
                     ArrayList var39 = new ArrayList();
                     MimeUtility.collectParts(var29, var38, var39);
                     var43 = var38.iterator();

                     while(true) {
                        if(!var43.hasNext()) {
                           break;
                        }

                        Part var44 = (Part)var43.next();
                        StringBuilder var45 = (new StringBuilder()).append("text/html: VIEWABLE = ");
                        String var46 = var44.getContentType();
                        String var47 = var45.append(var46).toString();
                        int var48 = Log.v("EMAILSYNCADAPTER", var47);
                        var49 = MimeUtility.getTextFromPart(var44);
                        if(var44.getContentType().contains("text/html")) {
                           if(var1.mHtml == null || var1.mHtml.equals("")) {
                              var2.mHtmlContent = var49;
                           }
                        } else if(var1.mText == null || var1.mText.equals("")) {
                           var2.mTextContent = var49;
                        }
                     }
                  } else if(!var24.getContentType().toString().contains("multipart/alternative") && !var24.getContentType().toString().contains("multipart/mixed") && !var24.getContentType().toString().contains("multipart/report")) {
                     if(var24.getContentType().toString().contains("multipart/related")) {
                        ArrayList var62 = new ArrayList();
                        ArrayList var63 = new ArrayList();
                        MimeUtility.collectParts(var29, var62, var63);
                        var43 = var62.iterator();

                        while(true) {
                           if(!var43.hasNext()) {
                              break;
                           }

                           Part var67 = (Part)var43.next();
                           StringBuilder var68 = (new StringBuilder()).append("multipart/related: VIEWABLE = ");
                           String var69 = var67.getContentType();
                           String var70 = var68.append(var69).toString();
                           int var71 = Log.v("EMAILSYNCADAPTER2", var70);
                           var49 = MimeUtility.getTextFromPart(var67);
                           if(var67.getContentType().contains("text/html")) {
                              var2.mHtmlContent = var49;
                           } else if(var67.getContentType().contains("text/plain")) {
                              var2.mTextContent = var49;
                           }

                           StringBuilder var73 = (new StringBuilder()).append("multipart/related: VIEWABLE to textfrompart = ");
                           String var75 = var73.append(var49).toString();
                           int var76 = Log.v("EMAILSYNCADAPTER2", var75);
                        }
                     } else if(!var24.getContentType().toString().contains("multipart/signed")) {
                        StringBuilder var95 = (new StringBuilder()).append("2. mimeMsg.getContentType() ");
                        String var96 = var24.getContentType();
                        String var97 = var95.append(var96).toString();
                        int var98 = Log.v("EmailSyncAdapter.....2", var97);
                        var49 = MimeUtility.getTextFromPart(var29);
                        if(var1.mText == null || var1.mText.equals("")) {
                           var2.mTextContent = var49;
                           Object var100 = null;
                           var2.mHtmlContent = (String)var100;
                        }
                     } else {
                        byte var78 = 1;
                        this.isSigned = (boolean)var78;
                        ArrayList var79 = new ArrayList();
                        ArrayList var80 = new ArrayList();
                        MimeUtility.collectParts(var29, var79, var80);
                        var43 = var79.iterator();

                        while(var43.hasNext()) {
                           Part var84 = (Part)var43.next();
                           var49 = MimeUtility.getTextFromPart(var84);
                           if(var84.getContentType().contains("text/html")) {
                              if(var1.mHtml == null || var1.mHtml.equals("")) {
                                 var2.mHtmlContent = var49;
                              }
                           } else if(var1.mText == null || var1.mText.equals("")) {
                              var2.mTextContent = var49;
                           }
                        }

                        ArrayList var87 = new ArrayList();
                        EmailContent.Attachment var88 = new EmailContent.Attachment();
                        String var89 = "cmdj_signed";
                        var88.mFileName = var89;
                        long var90 = var1.mId;
                        var88.mMessageKey = var90;
                        var87.add(var88);
                        byte var93 = 1;
                        var1.mFlagAttachment = (boolean)var93;
                        if(var1.mFlagAttachment) {
                           var1.mAttachments = var87;
                        }
                     }
                  } else {
                     ArrayList var52 = new ArrayList();
                     ArrayList var53 = new ArrayList();
                     MimeUtility.collectParts(var29, var52, var53);
                     boolean var57 = false;
                     boolean var58 = false;
                     var43 = var52.iterator();

                     while(true) {
                        if(!var43.hasNext()) {
                           break;
                        }

                        Part var59 = (Part)var43.next();
                        var49 = MimeUtility.getTextFromPart(var59);
                        if(var59.getContentType().contains("text/html")) {
                           if((var1.mHtml == null || var1.mHtml.equals("")) && !var57) {
                              var2.mHtmlContent = var49;
                              var57 = true;
                           }
                        } else if((var1.mText == null || var1.mText.equals("")) && !var58) {
                           var2.mTextContent = var49;
                           var58 = true;
                        }
                     }
                  }
               } catch (NullPointerException var103) {
                  var103.printStackTrace();
               } catch (MessagingException var104) {
                  var104.printStackTrace();
               }
            }
            break;
         default:
            this.skipTag();
         }
      }
   }

   private void fetchPropertiesParser() throws IOException {
      EmailContent.Message var1 = null;
      EmailContent.Body var2 = null;

      while(this.nextTag(1291) != 3) {
         switch(this.tag) {
         case 1098:
            boolean var8 = false;
            if(this.mServerId != null) {
               String var9 = this.mServerId;
               String[] var10 = EmailContent.Message.CONTENT_PROJECTION;
               Cursor var11 = this.getServerIdCursor(var9, var10);
               boolean var25 = false;

               label186: {
                  EmailContent.Message var12;
                  try {
                     var25 = true;
                     if(!var11.moveToFirst()) {
                        var25 = false;
                        break label186;
                     }

                     var12 = new EmailContent.Message();
                     var25 = false;
                  } finally {
                     if(var25) {
                        var11.close();
                     }
                  }

                  try {
                     var12.restore(var11);
                  } finally {
                     ;
                  }

                  var1 = var12;
               }

               var11.close();
               if(var1 != null) {
                  Context var14 = this.mContext;
                  long var15 = var1.mId;
                  var2 = EmailContent.Body.restoreBodyWithMessageId(var14, var15);
                  if(var2 != null) {
                     this.fetchPropertiesBodyParser(var1, var2);
                     var8 = true;
                  }
               }
            }

            if(var8) {
               if(var1 != null && var2 != null) {
                  EasSyncService var17 = this.mService;
                  Context var18 = this.mContext;
                  LoadMoreUtility.updateEmail(var17, var18, var1, var2);
                  if(this.isSigned) {
                     LoadMoreUtility.updateAttachment(this.mContext, var1);
                  }
               }
            } else {
               this.skipTag();
            }
            break;
         case 1289:
            String var3 = this.getValue();
            break;
         case 1290:
            String var4 = this.getValue();
            break;
         case 1292:
            OutputStream var5 = this.mOs;
            Observer var6 = this.mObserver;
            this.getValue(var5, var6);
            break;
         default:
            this.skipTag();
         }
      }

   }

   private Cursor getServerIdCursor(String var1, String[] var2) {
      this.mBindArguments[0] = var1;
      String[] var3 = this.mBindArguments;
      String var4 = this.mMailboxIdAsString;
      var3[1] = var4;
      ContentResolver var5 = this.mContentResolver;
      Uri var6 = EmailContent.Message.CONTENT_URI;
      String[] var7 = this.mBindArguments;
      return var5.query(var6, var2, "syncServerId=? and mailboxKey=?", var7, (String)null);
   }

   private void itemOperationsEmptyFolderContentsParser() throws IOException {
      String var1 = null;
      int var2 = -1;

      while(this.nextTag(1298) != 3) {
         switch(this.tag) {
         case 18:
            var1 = this.getValue();
            break;
         case 1293:
            var2 = this.getValueInt();
            if(var2 != 1) {
               EasSyncService var3 = this.mService;
               String var4 = "ItemOperations failed: " + var2;
               var3.errorLog(var4);
            }
            break;
         default:
            this.skipTag();
         }
      }

      if(var2 != -1 && var1 != null) {
         byte var5;
         if(var2 == 1) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         this.mResult = (boolean)var5;
      } else {
         this.mResult = (boolean)0;
      }
   }

   private void itemOperationsFetchParser() throws IOException {
      while(this.nextTag(1286) != 3) {
         switch(this.tag) {
         case 13:
         case 984:
            String var4 = this.getValue();
            this.mServerId = var4;
            break;
         case 1105:
            String var5 = this.getValue();
            break;
         case 1291:
            this.fetchPropertiesParser();
            break;
         case 1293:
            int var1 = this.getValueInt();
            if(var1 != 1) {
               EasSyncService var2 = this.mService;
               String var3 = "ItemOperations failed: " + var1;
               var2.errorLog(var3);
            }
            break;
         default:
            this.skipTag();
         }
      }

      this.mResult = (boolean)1;
   }

   private void itemOperationsMoveParser() throws IOException {
      int var1 = -1;
      byte[] var2 = null;

      while(this.nextTag(1302) != 3) {
         if(this.tag == 1293) {
            var1 = this.getValueInt();
            if(var1 != 1) {
               EasSyncService var3 = this.mService;
               String var4 = "ItemOperation:Move  failed: " + var1;
               var3.errorLog(var4);
               this.mResult = (boolean)0;
            }
         } else if(this.tag == 1304) {
            var2 = this.getValueOpaque();
         }
      }

      if(var1 == 1 && var2 != null) {
         this.mResult = (boolean)1;
      } else {
         this.mResult = (boolean)0;
      }
   }

   private void itemOperationsResponsesParser() throws IOException {
      while(this.nextTag(1294) != 3) {
         switch(this.tag) {
         case 1286:
            this.itemOperationsFetchParser();
            break;
         case 1298:
            this.itemOperationsEmptyFolderContentsParser();
            break;
         case 1302:
            this.itemOperationsMoveParser();
            break;
         default:
            this.skipTag();
         }
      }

   }

   public void commandsParser() throws IOException {}

   public void commit() throws IOException {}

   public int getStatus() {
      return this.mStatus;
   }

   public void moveResponseParser() throws IOException {}

   public boolean parse() throws IOException, DeviceAccessException {
      if(this.nextTag(0) != 1285) {
         throw new Parser.EasParserException();
      } else {
         while(this.nextTag(0) != 3) {
            switch(this.tag) {
            case 1293:
               int var1 = this.getValueInt();
               this.mStatus = var1;
               if(var1 != 1) {
                  EasSyncService var2 = this.mService;
                  StringBuilder var3 = (new StringBuilder()).append("ItemOperations failed: ");
                  int var4 = this.mStatus;
                  String var5 = var3.append(var4).toString();
                  var2.errorLog(var5);
                  int var6 = this.mStatus;
                  if(this.isProvisioningStatus(var6)) {
                     this.mService.mEasNeedsProvisioning = (boolean)1;
                     this.mResult = (boolean)0;
                  }

                  int var7 = this.mStatus;
                  if(this.isDeviceAccessDenied(var7)) {
                     throw new DeviceAccessException(262145, 2131166810);
                  }
               }
               break;
            case 1294:
               this.itemOperationsResponsesParser();
               break;
            default:
               this.skipTag();
            }
         }

         return this.mResult;
      }
   }

   public void responsesParser() throws IOException {}

   public void setObserver(Observer var1) {
      this.mObserver = var1;
   }

   public void setOutputStream(OutputStream var1) {
      this.mOs = var1;
   }

   public void wipe() {}
}
