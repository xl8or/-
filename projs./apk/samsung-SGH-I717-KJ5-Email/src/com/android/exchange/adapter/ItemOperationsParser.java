package com.android.exchange.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.android.email.irm.IRMLicenseParserUtility;
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
   private boolean mIrmLicensePresent;
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
      this.mIrmLicensePresent = (boolean)0;
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

               label158: {
                  MimeMessage var25;
                  try {
                     var25 = new MimeMessage(var18);
                  } catch (MessagingException var107) {
                     var107.printStackTrace();
                     break label158;
                  }

                  var24 = var25;
               }

               try {
                  if(var24.getMessageDispostion() != null) {
                     int var28 = var1.mFlags | 1024;
                     var1.mFlags = var28;
                  }

                  if(var24.getReturnPath() != null) {
                     int var29 = var1.mFlags | 2048;
                     var1.mFlags = var29;
                  }

                  Body var30 = var24.getBody();
                  MimeBodyPart var31 = new MimeBodyPart;
                  String var32 = var24.getContentType();
                  var31.<init>(var30, var32);
                  Iterator var45;
                  String var51;
                  if(var24.getContentType().toString().contains("text/html")) {
                     StringBuilder var36 = (new StringBuilder()).append("1. mimeMsg.getContentType() ");
                     String var37 = var24.getContentType();
                     String var38 = var36.append(var37).toString();
                     int var39 = Log.v("EmailSyncAdapter.....2", var38);
                     ArrayList var40 = new ArrayList();
                     ArrayList var41 = new ArrayList();
                     MimeUtility.collectParts(var31, var40, var41);
                     var45 = var40.iterator();

                     while(true) {
                        if(!var45.hasNext()) {
                           break;
                        }

                        Part var46 = (Part)var45.next();
                        StringBuilder var47 = (new StringBuilder()).append("text/html: VIEWABLE = ");
                        String var48 = var46.getContentType();
                        String var49 = var47.append(var48).toString();
                        int var50 = Log.v("EMAILSYNCADAPTER", var49);
                        var51 = MimeUtility.getTextFromPart(var46);
                        if(var46.getContentType().contains("text/html")) {
                           if(var1.mHtml == null || var1.mHtml.equals("")) {
                              var2.mHtmlContent = var51;
                           }
                        } else if(var1.mText == null || var1.mText.equals("")) {
                           var2.mTextContent = var51;
                        }
                     }
                  } else if(!var24.getContentType().toString().contains("multipart/alternative") && !var24.getContentType().toString().contains("multipart/mixed") && !var24.getContentType().toString().contains("multipart/report")) {
                     if(var24.getContentType().toString().contains("multipart/related")) {
                        ArrayList var64 = new ArrayList();
                        ArrayList var65 = new ArrayList();
                        MimeUtility.collectParts(var31, var64, var65);
                        var45 = var64.iterator();

                        while(true) {
                           if(!var45.hasNext()) {
                              break;
                           }

                           Part var69 = (Part)var45.next();
                           StringBuilder var70 = (new StringBuilder()).append("multipart/related: VIEWABLE = ");
                           String var71 = var69.getContentType();
                           String var72 = var70.append(var71).toString();
                           int var73 = Log.v("EMAILSYNCADAPTER2", var72);
                           var51 = MimeUtility.getTextFromPart(var69);
                           if(var69.getContentType().contains("text/html")) {
                              var2.mHtmlContent = var51;
                           } else if(var69.getContentType().contains("text/plain")) {
                              var2.mTextContent = var51;
                           }

                           StringBuilder var75 = (new StringBuilder()).append("multipart/related: VIEWABLE to textfrompart = ");
                           String var77 = var75.append(var51).toString();
                           int var78 = Log.v("EMAILSYNCADAPTER2", var77);
                        }
                     } else if(!var24.getContentType().toString().contains("multipart/signed")) {
                        StringBuilder var97 = (new StringBuilder()).append("2. mimeMsg.getContentType() ");
                        String var98 = var24.getContentType();
                        String var99 = var97.append(var98).toString();
                        int var100 = Log.v("EmailSyncAdapter.....2", var99);
                        var51 = MimeUtility.getTextFromPart(var31);
                        if(var1.mText == null || var1.mText.equals("")) {
                           var2.mTextContent = var51;
                           Object var102 = null;
                           var2.mHtmlContent = (String)var102;
                        }
                     } else {
                        byte var80 = 1;
                        this.isSigned = (boolean)var80;
                        ArrayList var81 = new ArrayList();
                        ArrayList var82 = new ArrayList();
                        MimeUtility.collectParts(var31, var81, var82);
                        var45 = var81.iterator();

                        while(var45.hasNext()) {
                           Part var86 = (Part)var45.next();
                           var51 = MimeUtility.getTextFromPart(var86);
                           if(var86.getContentType().contains("text/html")) {
                              if(var1.mHtml == null || var1.mHtml.equals("")) {
                                 var2.mHtmlContent = var51;
                              }
                           } else if(var1.mText == null || var1.mText.equals("")) {
                              var2.mTextContent = var51;
                           }
                        }

                        ArrayList var89 = new ArrayList();
                        EmailContent.Attachment var90 = new EmailContent.Attachment();
                        String var91 = "cmdj_signed";
                        var90.mFileName = var91;
                        long var92 = var1.mId;
                        var90.mMessageKey = var92;
                        var89.add(var90);
                        byte var95 = 1;
                        var1.mFlagAttachment = (boolean)var95;
                        if(var1.mFlagAttachment) {
                           var1.mAttachments = var89;
                        }
                     }
                  } else {
                     ArrayList var54 = new ArrayList();
                     ArrayList var55 = new ArrayList();
                     MimeUtility.collectParts(var31, var54, var55);
                     boolean var59 = false;
                     boolean var60 = false;
                     var45 = var54.iterator();

                     while(true) {
                        if(!var45.hasNext()) {
                           break;
                        }

                        Part var61 = (Part)var45.next();
                        var51 = MimeUtility.getTextFromPart(var61);
                        if(var61.getContentType().contains("text/html")) {
                           if((var1.mHtml == null || var1.mHtml.equals("")) && !var59) {
                              var2.mHtmlContent = var51;
                              var59 = true;
                           }
                        } else if((var1.mText == null || var1.mText.equals("")) && !var60) {
                           var2.mTextContent = var51;
                           var60 = true;
                        }
                     }
                  }
               } catch (NullPointerException var105) {
                  var105.printStackTrace();
               } catch (MessagingException var106) {
                  var106.printStackTrace();
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
               boolean var34 = false;

               label221: {
                  EmailContent.Message var12;
                  try {
                     var34 = true;
                     if(!var11.moveToFirst()) {
                        var34 = false;
                        break label221;
                     }

                     var12 = new EmailContent.Message();
                     var34 = false;
                  } finally {
                     if(var34) {
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
         case 1544:
            if(var1 != null) {
               this.mIrmLicensePresent = (boolean)1;
               IRMLicenseParserUtility.parseLicense(var1, this);
            }
            break;
         default:
            this.skipTag();
         }
      }

      if(!this.mIrmLicensePresent) {
         if(var1 != null) {
            ContentValues var20 = new ContentValues();
            var20.put("IRMContentExpiryDate", "");
            var20.put("IRMContentOwner", "");
            Integer var21 = Integer.valueOf(-1);
            var20.put("IRMLicenseFlag", var21);
            var20.put("IRMOwner", "");
            var20.put("IRMTemplateId", "");
            ContentResolver var22 = this.mContext.getContentResolver();
            Uri var23 = EmailContent.Message.CONTENT_URI;
            StringBuilder var24 = (new StringBuilder()).append("_id = ");
            long var25 = var1.mId;
            String var27 = var24.append(var25).toString();
            var22.update(var23, var20, var27, (String[])null);
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
            String var6 = this.getValue();
            this.mServerId = var6;
            break;
         case 1105:
            String var7 = this.getValue();
            break;
         case 1291:
            this.fetchPropertiesParser();
            break;
         case 1293:
            int var1 = this.getValueInt();
            this.mStatus = var1;
            if(var1 != 1) {
               EasSyncService var2 = this.mService;
               StringBuilder var3 = (new StringBuilder()).append("ItemOperations failed: ");
               int var4 = this.mStatus;
               String var5 = var3.append(var4).toString();
               var2.errorLog(var5);
            }
            break;
         default:
            this.skipTag();
         }
      }

      if(this.mStatus == 1) {
         this.mResult = (boolean)1;
      } else {
         this.mResult = (boolean)0;
      }
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
                     throw new DeviceAccessException(262145, 2131166819);
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
