package com.htc.android.mail.eassvc.pim;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.htc.android.mail.eassvc.pim.EASMailAttachment;
import java.util.ArrayList;
import java.util.Iterator;

public class EASMailSendItem implements Parcelable {

   public static final String BCC = "Bcc: ";
   public static final String BODY = "\r\n";
   public static final String CC = "Cc: ";
   public static final int COMMAND_SEND = 1;
   public static final int COMMAND_SMART_FORWARD = 3;
   public static final int COMMAND_SMART_REPLY = 2;
   public static final String CONTENT_ATTACH_BINARY = "Content-Type: application/octet-stream;\r\n\tname=";
   public static final String CONTENT_ATTACH_CAL_MEETING_PROPOSE_NEW_TIME = "Content-Type: text/calendar; charset=\"utf-8\"; method=COUNTER";
   public static final String CONTENT_ATTACH_CAL_MEETING_REQ = "Content-Type: text/calendar; charset=\"utf-8\"; method=REQUEST";
   public static final String CONTENT_ATTACH_CAL_MEETING_RESP = "Content-Type: text/calendar; charset=\"utf-8\"; method=REPLY";
   public static final String CONTENT_ATTACH_IMG_GIF = "Content-Type: image/gif;\r\n\tname=";
   public static final String CONTENT_ATTACH_IMG_JPG = "Content-Type: image/jpeg;\r\n\tname=";
   public static final String CONTENT_ATTACH_IMG_PNG = "Content-Type: image/png;\r\n\tname=";
   public static final String CONTENT_ATTACH_MPEG = "Content-Type: video/mpeg;\r\n\tname=";
   public static final String CONTENT_ATTACH_PDF = "Content-Type: application/pdf;\r\n\tname=";
   public static final String CONTENT_DISPOSITION = "Content-Disposition: ";
   public static final String CONTENT_DISPOSITION_ATTACH = "attachment;\r\n\tfilename=";
   public static final String CONTENT_DISPOSITION_INLINE = "inline;\r\n\tfilename=";
   public static final String CONTENT_DISPOSITON = "Content-Disposition: ";
   public static final String CONTENT_ENCODING = "Content-Transfer-Encoding: ";
   public static final String CONTENT_ID = "Content-ID: ";
   public static final String CONTENT_INLINE = "inline";
   public static final String CONTENT_TYPE = "Content-Type: ";
   public static final String CONTENT_TYPE_HTML = "Content-Type: text/html;\r\n\tcharset= ";
   public static final String CONTENT_TYPE_Mixed = "Content-Type: multipart/mixed;\r\n\tboundary=";
   public static final String CONTENT_TYPE_NAME = ";\r\n\tname=";
   public static final String CONTENT_TYPE_RelatedWithBound = "Content-Type: multipart/related;\r\n\tboundary=";
   public static final String CONTENT_TYPE_TEXT = "Content-Type: text/plain;\r\n\tcharset= ";
   public static final Creator<EASMailSendItem> CREATOR = new EASMailSendItem.1();
   public static final String Content_TYPE_AlternativeWithBound = "Content-Type: multipart/alternative;\r\n\tboundary=";
   public static final String END_LINE = "\r\n";
   public static final String FROM = "From: ";
   public static final String IMPOETANCE_LOW = "Low";
   public static final String IMPORTANCE = "Importance: ";
   public static final String IMPORTANCE_HIGH = "High";
   public static final String IMPORTANCE_NORMAL = "Normal";
   public static final String MESSAGE_ID = "Message-ID: ";
   public static final String MIME_VER = "MIME-Version: ";
   public static final String SENDER = "Sender: ";
   public static final String SUBJECT = "Subject: ";
   public static final String TAG = "EASMailSendItem";
   public static final String TO = "To: ";
   public static final String X_MIMEOLE = "X-MimeOLE: ";
   public static final String charsetBig5 = "big5";
   public static final String charsetISO88591 = "ISO-8859-1";
   public static final String charsetUTF8 = "utf-8";
   public static final String encode7bit = "7bit";
   public static final String encodeBase64 = "base64";
   public static String mX_MimeOLE = null;
   public ArrayList<EASMailAttachment> mArrachmentList;
   public String mBackupOriginalBody;
   public String mBcc;
   public String mBody;
   public String mBuackupOriginalPlainBody;
   public String mCc;
   public String mCollId;
   public int mCommand;
   public String mFrom;
   public boolean mGlobalMail;
   public String mHeaderHtml;
   public String mHeaderPlain;
   public String mImportance;
   public String mMIME_Version;
   public EASMailAttachment mMeetingAttach;
   public long mMessageId;
   public String mPlainBody;
   public ArrayList<EASMailAttachment> mRelatedList;
   public boolean mSaveInSent;
   public boolean mSendInsteadofForeard;
   public String mSender;
   public String mSubject;
   public String mTo;
   public String mUid;


   public EASMailSendItem() {
      this.mSaveInSent = (boolean)1;
      this.mSendInsteadofForeard = (boolean)0;
      this.mGlobalMail = (boolean)0;
      this.mCommand = 1;
      this.mUid = null;
      this.mCollId = null;
      this.mSender = null;
      this.mTo = null;
      this.mCc = null;
      this.mBcc = null;
      this.mBody = null;
      this.mPlainBody = null;
      this.mHeaderHtml = null;
      this.mHeaderPlain = null;
      this.mBuackupOriginalPlainBody = null;
      this.mBackupOriginalBody = null;
      this.mFrom = null;
      this.mSubject = null;
      this.mImportance = null;
      this.mMIME_Version = null;
      this.mMeetingAttach = null;
      ArrayList var1 = new ArrayList();
      this.mArrachmentList = var1;
      ArrayList var2 = new ArrayList();
      this.mRelatedList = var2;
      this.mMIME_Version = "1.0";
      this.mImportance = "Normal";
      mX_MimeOLE = "Produced By Microsoft MimeOLE V6.00.2900.2180";
   }

   private EASMailSendItem(Parcel var1) {
      this.mSaveInSent = (boolean)1;
      this.mSendInsteadofForeard = (boolean)0;
      this.mGlobalMail = (boolean)0;
      this.mCommand = 1;
      this.mUid = null;
      this.mCollId = null;
      this.mSender = null;
      this.mTo = null;
      this.mCc = null;
      this.mBcc = null;
      this.mBody = null;
      this.mPlainBody = null;
      this.mHeaderHtml = null;
      this.mHeaderPlain = null;
      this.mBuackupOriginalPlainBody = null;
      this.mBackupOriginalBody = null;
      this.mFrom = null;
      this.mSubject = null;
      this.mImportance = null;
      this.mMIME_Version = null;
      this.mMeetingAttach = null;
      ArrayList var2 = new ArrayList();
      this.mArrachmentList = var2;
      ArrayList var3 = new ArrayList();
      this.mRelatedList = var3;
      boolean[] var4 = new boolean[3];
      var1.readBooleanArray(var4);
      if(var4 != null && var4.length >= 3) {
         boolean var5 = var4[0];
         this.mSaveInSent = var5;
         boolean var6 = var4[1];
         this.mSendInsteadofForeard = var6;
         boolean var7 = var4[2];
         this.mGlobalMail = var7;
      }

      int var8 = var1.readInt();
      this.mCommand = var8;
      long var9 = var1.readLong();
      this.mMessageId = var9;
      String var11 = var1.readString();
      this.mUid = var11;
      String var12 = var1.readString();
      this.mCollId = var12;
      String var13 = var1.readString();
      this.mFrom = var13;
      String var14 = var1.readString();
      this.mSender = var14;
      String var15 = var1.readString();
      this.mTo = var15;
      String var16 = var1.readString();
      this.mCc = var16;
      String var17 = var1.readString();
      this.mBcc = var17;
      String var18 = var1.readString();
      this.mSubject = var18;
      String var19 = var1.readString();
      this.mMIME_Version = var19;
      mX_MimeOLE = var1.readString();
      String var20 = var1.readString();
      this.mBody = var20;
      String var21 = var1.readString();
      this.mPlainBody = var21;
      String var22 = var1.readString();
      this.mHeaderHtml = var22;
      String var23 = var1.readString();
      this.mHeaderPlain = var23;
      String var24 = var1.readString();
      this.mBuackupOriginalPlainBody = var24;
      String var25 = var1.readString();
      this.mBackupOriginalBody = var25;
      String var26 = var1.readString();
      this.mImportance = var26;
      ArrayList var27 = new ArrayList();
      Creator var28 = EASMailAttachment.CREATOR;
      var1.readTypedList(var27, var28);
      Iterator var29;
      if(var27 != null) {
         var29 = var27.iterator();

         while(var29.hasNext()) {
            EASMailAttachment var30 = (EASMailAttachment)var29.next();
            String var31 = var30.attachMimeType;
            if(!"Content-Type: text/calendar; charset=\"utf-8\"; method=REQUEST".equals(var31)) {
               String var32 = var30.attachMimeType;
               if(!"Content-Type: text/calendar; charset=\"utf-8\"; method=COUNTER".equals(var32)) {
                  String var33 = var30.attachMimeType;
                  if(!"Content-Type: text/calendar; charset=\"utf-8\"; method=REPLY".equals(var33)) {
                     this.mArrachmentList.add(var30);
                     continue;
                  }
               }
            }

            this.mMeetingAttach = var30;
         }
      }

      ArrayList var35 = new ArrayList();
      Creator var36 = EASMailAttachment.CREATOR;
      var1.readTypedList(var35, var36);
      if(var35 != null) {
         var29 = var27.iterator();

         while(var29.hasNext()) {
            EASMailAttachment var37 = (EASMailAttachment)var29.next();
            this.mRelatedList.add(var37);
         }

      }
   }

   // $FF: synthetic method
   EASMailSendItem(Parcel var1, EASMailSendItem.1 var2) {
      this(var1);
   }

   public static final String GenBoundary(int var0) {
      StringBuilder var1 = (new StringBuilder()).append("----=_Part_");
      String var2 = String.valueOf(var0);
      StringBuilder var3 = var1.append(var2).append("_");
      String var4 = String.valueOf(System.currentTimeMillis());
      return var3.append(var4).toString();
   }

   private boolean checkAttachmentEqual(EASMailAttachment var1, EASMailAttachment var2) {
      boolean var3;
      if(var1 == null && var2 == null) {
         var3 = true;
      } else if((var1 == null || var2 != null) && (var1 != null || var2 == null)) {
         if(var1.isEqual(var2)) {
            var3 = true;
         } else {
            var3 = false;
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   private boolean checkAttachmentListEqual(ArrayList<EASMailAttachment> var1, ArrayList<EASMailAttachment> var2) {
      boolean var3;
      if(var1 == null && var2 == null) {
         var3 = true;
      } else if((var1 == null || var2 != null) && (var1 != null || var2 == null)) {
         int var4 = var1.size();
         int var5 = var2.size();
         if(var4 != var5) {
            var3 = false;
         } else {
            int var6 = 0;

            while(true) {
               int var7 = var1.size();
               if(var6 >= var7) {
                  var3 = true;
                  break;
               }

               EASMailAttachment var8 = (EASMailAttachment)var1.get(var6);
               EASMailAttachment var9 = (EASMailAttachment)var2.get(var6);
               if(!this.checkAttachmentEqual(var8, var9)) {
                  var3 = false;
                  break;
               }

               ++var6;
            }
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   private boolean checkStringEqual(String var1, String var2) {
      boolean var3;
      if(var1 != null && !var1.equals(var2)) {
         var3 = false;
      } else if(var2 != null && !var2.equals(var1)) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   public int describeContents() {
      return 0;
   }

   public boolean isEqual(EASMailSendItem var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         boolean var3 = this.mSaveInSent;
         boolean var4 = var1.mSaveInSent;
         if(var3 == var4) {
            boolean var5 = this.mSendInsteadofForeard;
            boolean var6 = var1.mSendInsteadofForeard;
            if(var5 == var6) {
               boolean var7 = this.mGlobalMail;
               boolean var8 = var1.mGlobalMail;
               if(var7 == var8) {
                  int var9 = this.mCommand;
                  int var10 = var1.mCommand;
                  if(var9 == var10) {
                     long var11 = this.mMessageId;
                     long var13 = var1.mMessageId;
                     if(var11 == var13) {
                        String var15 = this.mUid;
                        String var16 = var1.mUid;
                        if(this.checkStringEqual(var15, var16)) {
                           String var17 = this.mCollId;
                           String var18 = var1.mCollId;
                           if(this.checkStringEqual(var17, var18)) {
                              String var19 = this.mSender;
                              String var20 = var1.mSender;
                              if(this.checkStringEqual(var19, var20)) {
                                 String var21 = this.mTo;
                                 String var22 = var1.mTo;
                                 if(this.checkStringEqual(var21, var22)) {
                                    String var23 = this.mCc;
                                    String var24 = var1.mCc;
                                    if(this.checkStringEqual(var23, var24)) {
                                       String var25 = this.mBcc;
                                       String var26 = var1.mBcc;
                                       if(this.checkStringEqual(var25, var26)) {
                                          String var27 = this.mBody;
                                          String var28 = var1.mBody;
                                          if(this.checkStringEqual(var27, var28)) {
                                             String var29 = this.mPlainBody;
                                             String var30 = var1.mPlainBody;
                                             if(this.checkStringEqual(var29, var30)) {
                                                String var31 = this.mHeaderHtml;
                                                String var32 = var1.mHeaderHtml;
                                                if(this.checkStringEqual(var31, var32)) {
                                                   String var33 = this.mHeaderPlain;
                                                   String var34 = var1.mHeaderPlain;
                                                   if(this.checkStringEqual(var33, var34)) {
                                                      String var35 = this.mBuackupOriginalPlainBody;
                                                      String var36 = var1.mBuackupOriginalPlainBody;
                                                      if(this.checkStringEqual(var35, var36)) {
                                                         String var37 = this.mBackupOriginalBody;
                                                         String var38 = var1.mBackupOriginalBody;
                                                         if(this.checkStringEqual(var37, var38)) {
                                                            String var39 = this.mFrom;
                                                            String var40 = var1.mFrom;
                                                            if(this.checkStringEqual(var39, var40)) {
                                                               String var41 = this.mSubject;
                                                               String var42 = var1.mSubject;
                                                               if(this.checkStringEqual(var41, var42)) {
                                                                  String var43 = this.mImportance;
                                                                  String var44 = var1.mImportance;
                                                                  if(this.checkStringEqual(var43, var44)) {
                                                                     String var45 = this.mMIME_Version;
                                                                     String var46 = var1.mMIME_Version;
                                                                     if(this.checkStringEqual(var45, var46)) {
                                                                        EASMailAttachment var47 = this.mMeetingAttach;
                                                                        EASMailAttachment var48 = var1.mMeetingAttach;
                                                                        if(this.checkAttachmentEqual(var47, var48)) {
                                                                           ArrayList var49 = this.mArrachmentList;
                                                                           ArrayList var50 = var1.mArrachmentList;
                                                                           if(this.checkAttachmentListEqual(var49, var50)) {
                                                                              var2 = true;
                                                                              return var2;
                                                                           }
                                                                        }
                                                                     }
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public void writeToParcel(Parcel var1, int var2) {
      boolean[] var3 = new boolean[3];
      boolean var4 = this.mSaveInSent;
      var3[0] = var4;
      boolean var5 = this.mSendInsteadofForeard;
      var3[1] = var5;
      boolean var6 = this.mGlobalMail;
      var3[2] = var6;
      var1.writeBooleanArray(var3);
      int var7 = this.mCommand;
      var1.writeInt(var7);
      long var8 = this.mMessageId;
      var1.writeLong(var8);
      String var10 = this.mUid;
      var1.writeString(var10);
      String var11 = this.mCollId;
      var1.writeString(var11);
      String var12 = this.mFrom;
      var1.writeString(var12);
      String var13 = this.mSender;
      var1.writeString(var13);
      String var14 = this.mTo;
      var1.writeString(var14);
      String var15 = this.mCc;
      var1.writeString(var15);
      String var16 = this.mBcc;
      var1.writeString(var16);
      String var17 = this.mSubject;
      var1.writeString(var17);
      String var18 = this.mMIME_Version;
      var1.writeString(var18);
      String var19 = mX_MimeOLE;
      var1.writeString(var19);
      String var20 = this.mBody;
      var1.writeString(var20);
      String var21 = this.mPlainBody;
      var1.writeString(var21);
      String var22 = this.mHeaderHtml;
      var1.writeString(var22);
      String var23 = this.mHeaderPlain;
      var1.writeString(var23);
      String var24 = this.mBuackupOriginalPlainBody;
      var1.writeString(var24);
      String var25 = this.mBackupOriginalBody;
      var1.writeString(var25);
      String var26 = this.mImportance;
      var1.writeString(var26);
      if(this.mMeetingAttach != null) {
         ArrayList var27 = this.mArrachmentList;
         EASMailAttachment var28 = this.mMeetingAttach;
         var27.add(var28);
      }

      ArrayList var30 = this.mArrachmentList;
      var1.writeTypedList(var30);
      ArrayList var31 = this.mRelatedList;
      var1.writeTypedList(var31);
   }

   static class 1 implements Creator<EASMailSendItem> {

      1() {}

      public EASMailSendItem createFromParcel(Parcel var1) {
         return new EASMailSendItem(var1, (EASMailSendItem.1)null);
      }

      public EASMailSendItem[] newArray(int var1) {
         return new EASMailSendItem[var1];
      }
   }
}
