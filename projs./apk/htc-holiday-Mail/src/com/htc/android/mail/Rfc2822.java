package com.htc.android.mail;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;
import com.htc.android.mail.Base64;
import com.htc.android.mail.ByteString;
import com.htc.android.mail.DateParser;
import com.htc.android.mail.FormatFlowed;
import com.htc.android.mail.Headers;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.Mime;
import com.htc.android.mail.QuotedPrintable;
import com.htc.android.mail.Util;
import com.htc.android.mail.ll;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Rfc2822 {

   public static int CONTENTTYPE_ALTERNATIVE = 3;
   public static int CONTENTTYPE_DEFAULT = 0;
   public static int CONTENTTYPE_MIXED = 1;
   public static int CONTENTTYPE_OTHERRMULTIPART = 4;
   public static int CONTENTTYPE_RELATED = 2;
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   public static final int READ = 1;
   private static final String TAG = "Rfc2822";
   private int incAttachment = 0;
   private boolean isIMAP4;
   private long mAccountId;
   private String mCc;
   private int mContentType;
   private Context mContext;
   private Calendar mDate;
   private int mFlags;
   private String mFrom;
   private String mFromEmail;
   private String mGroup = null;
   int mHeaderBreak;
   private boolean mHeaderOnly;
   private HashMap<String, String> mHeaders;
   private int mImportance = 1;
   private Calendar mInternalDate;
   private ArrayList<ByteString> mLines;
   private Mailbox mMailbox;
   private String mMessageId = "";
   private int mMessageIndex = -1;
   private long mMessageid = 65535L;
   private Mime mMime;
   private long mPsuedoGroupId;
   private int mReadSize = 0;
   private String mReferences = null;
   private String mReplyTo;
   private String mSubject;
   private String mSubjectCharset;
   private Uri mSummariesUri;
   private String mTempPreview;
   private String mText;
   private String mThreadIndex;
   private String mThreadTopic;
   private String mTo;
   private int mTotalSize = 0;
   private String mUidl;
   private Uri mUri;
   private int read;


   public Rfc2822(Context var1, long var2, ArrayList<ByteString> var4, String var5, boolean var6, Mailbox var7) {
      int var8 = CONTENTTYPE_DEFAULT;
      this.mContentType = var8;
      this.isIMAP4 = (boolean)0;
      this.mTempPreview = "";
      this.read = 0;
      this.mSummariesUri = null;
      this.mHeaderOnly = (boolean)0;
      String var9 = Mail.getDefaultCharset();
      this.mSubjectCharset = var9;
      this.mContext = var1;
      this.mAccountId = var2;
      this.isIMAP4 = var6;
      this.mMailbox = var7;
      long var10;
      if(var7 == false) {
         var10 = 65535L;
      } else {
         var10 = var7.id;
      }

      Uri var12 = MailCommon.getSummariesUri(var2, var10);
      this.mSummariesUri = var12;
      int var13 = Util.unfoldLines(var4, (boolean)1);
      this.mHeaderBreak = var13;
      if(Mail.debug && DEBUG) {
         StringBuilder var14 = (new StringBuilder()).append("# break ");
         int var15 = this.mHeaderBreak;
         String var16 = var14.append(var15).toString();
         ll.d("Rfc2822", var16);
      }

      int var17 = this.mHeaderBreak;
      HashMap var18 = new HashMap(var17);
      this.mHeaders = var18;
      int var19 = this.mHeaderBreak;
      HashMap var20 = this.mHeaders;
      Headers.extract(var4, var19, var20);
      String var21 = (String)this.mHeaders.get("from");
      this.mFrom = var21;
      String var22 = (String)this.mHeaders.get("fromEmail");
      this.mFromEmail = var22;
      String var23 = (String)this.mHeaders.get("to");
      this.mTo = var23;
      String var24 = (String)this.mHeaders.get("cc");
      this.mCc = var24;
      String var25 = (String)this.mHeaders.get("subject");
      this.mSubject = var25;
      String var26 = (String)this.mHeaders.get("threadindex");
      this.mThreadIndex = var26;
      Calendar var27 = DateParser.parse((String)this.mHeaders.get("date"));
      this.mDate = var27;
      this.mUidl = var5;
      if(Mail.debug && DEBUG) {
         StringBuilder var28 = (new StringBuilder()).append("rfc2822 uidl = ");
         String var29 = this.mUidl;
         String var30 = var28.append(var29).toString();
         ll.d("Rfc2822", var30);
      }

      this.mLines = var4;
   }

   public Rfc2822(Context var1, long var2, ArrayList<ByteString> var4, String var5, boolean var6, Mailbox var7, int var8, int var9, int var10, int var11) {
      int var12 = CONTENTTYPE_DEFAULT;
      this.mContentType = var12;
      this.isIMAP4 = (boolean)0;
      this.mTempPreview = "";
      this.read = 0;
      this.mSummariesUri = null;
      this.mHeaderOnly = (boolean)0;
      String var13 = Mail.getDefaultCharset();
      this.mSubjectCharset = var13;
      this.mContext = var1;
      this.mAccountId = var2;
      this.isIMAP4 = var6;
      this.mMailbox = var7;
      this.mTotalSize = var8;
      this.mReadSize = var9;
      this.read = var10;
      this.incAttachment = var11;
      long var19;
      if(var7 == false) {
         var19 = 65535L;
      } else {
         var19 = var7.id;
      }

      Uri var21 = MailCommon.getSummariesUri(var2, var19);
      this.mSummariesUri = var21;
      int var22 = Util.unfoldLines(var4, (boolean)1);
      this.mHeaderBreak = var22;
      if(Mail.debug && DEBUG) {
         StringBuilder var23 = (new StringBuilder()).append("# break ");
         int var24 = this.mHeaderBreak;
         String var25 = var23.append(var24).toString();
         ll.d("Rfc2822", var25);
      }

      int var26 = this.mHeaderBreak;
      HashMap var27 = new HashMap(var26);
      this.mHeaders = var27;
      int var28 = this.mHeaderBreak;
      HashMap var29 = this.mHeaders;
      Headers.extract(var4, var28, var29);
      String var30 = (String)this.mHeaders.get("from");
      this.mFrom = var30;
      String var31 = (String)this.mHeaders.get("fromEmail");
      this.mFromEmail = var31;
      String var32 = (String)this.mHeaders.get("to");
      this.mTo = var32;
      String var33 = (String)this.mHeaders.get("cc");
      this.mCc = var33;
      String var34 = (String)this.mHeaders.get("reply-to");
      this.mReplyTo = var34;
      String var35 = (String)this.mHeaders.get("subject");
      this.mSubject = var35;
      if(this.mHeaders.get("subjectCharset") != null) {
         String var36 = (String)this.mHeaders.get("subjectCharset");
         this.mSubjectCharset = var36;
      }

      String var37 = (String)this.mHeaders.get("threadindex");
      this.mThreadIndex = var37;
      String var38 = (String)this.mHeaders.get("message-id");
      this.mMessageId = var38;
      if(this.mMessageId != null) {
         String var39 = this.mMessageId.trim().replaceAll("<", "").replaceAll(">", "");
         this.mMessageId = var39;
      }

      String var40 = (String)this.mHeaders.get("references");
      this.mReferences = var40;
      Calendar var41 = DateParser.parse((String)this.mHeaders.get("date"));
      this.mDate = var41;
      if(this.mDate != null) {
         if(DEBUG) {
            StringBuilder var42 = (new StringBuilder()).append(" ****** POP dateValue :");
            Long var43 = Long.valueOf(this.mDate.getTimeInMillis());
            StringBuilder var44 = var42.append(var43).append(":");
            Context var45 = this.mContext;
            long var46 = Long.valueOf(this.mDate.getTimeInMillis()).longValue();
            CharSequence var48 = Util.getTimeFullString(var45, var46);
            String var49 = var44.append(var48).toString();
            ll.d("Rfc2822", var49);
         }
      } else {
         Calendar var73 = Calendar.getInstance();
         this.mDate = var73;
      }

      String var50;
      if(this.mHeaders.get("received") == null) {
         var50 = (String)this.mHeaders.get("date");
      } else {
         var50 = Headers.getReceivedAttribute((String)this.mHeaders.get("received"));
      }

      if(var50 == null) {
         Calendar var51 = this.mDate;
         this.mInternalDate = var51;
      } else {
         Calendar var74 = DateParser.parse(var50);
         this.mInternalDate = var74;
      }

      if(this.mInternalDate != null) {
         if(DEBUG) {
            StringBuilder var52 = (new StringBuilder()).append(" ****** POP internal dateValue :");
            Long var53 = Long.valueOf(this.mInternalDate.getTimeInMillis());
            StringBuilder var54 = var52.append(var53).append(":");
            Context var55 = this.mContext;
            long var56 = Long.valueOf(this.mInternalDate.getTimeInMillis()).longValue();
            CharSequence var58 = Util.getTimeFullString(var55, var56);
            String var59 = var54.append(var58).toString();
            ll.d("Rfc2822", var59);
         }
      } else {
         Calendar var75 = this.mDate;
         this.mInternalDate = var75;
      }

      if(this.mHeaders.get("content-type") != null) {
         int var60 = setContentType(((String)this.mHeaders.get("content-type")).toLowerCase());
         this.mContentType = var60;
      }

      int var61 = MailCommon.getImportanceValue((String)this.mHeaders.get("importance"));
      this.mImportance = var61;
      this.mUidl = var5;
      if(Mail.debug && DEBUG) {
         StringBuilder var62 = (new StringBuilder()).append("rfc2822 uidl = ");
         String var63 = this.mUidl;
         String var64 = var62.append(var63).toString();
         ll.d("Rfc2822", var64);
      }

      this.mLines = var4;
      int var65 = this.mHeaderBreak + 2;
      int var66 = var4.size();
      if(var65 <= var66) {
         if(DEBUG) {
            StringBuilder var67 = (new StringBuilder()).append("enter processBody :rfc2822 : ");
            int var68 = this.mHeaderBreak;
            StringBuilder var69 = var67.append(var68).append(", ");
            int var70 = var4.size();
            String var71 = var69.append(var70).toString();
            ll.d("Rfc2822", var71);
         }

         int var72 = this.mHeaderBreak + 1;
         this.processBody(var72);
      } else {
         this.mHeaderOnly = (boolean)1;
      }
   }

   public Rfc2822(Context var1, long var2, ArrayList<ByteString> var4, String var5, boolean var6, String var7) {
      int var8 = CONTENTTYPE_DEFAULT;
      this.mContentType = var8;
      this.isIMAP4 = (boolean)0;
      this.mTempPreview = "";
      this.read = 0;
      this.mSummariesUri = null;
      this.mHeaderOnly = (boolean)0;
      String var9 = Mail.getDefaultCharset();
      this.mSubjectCharset = var9;
   }

   private final boolean findTextParts() {
      if(DEBUG) {
         StringBuilder var1 = (new StringBuilder()).append("findTextParts to :");
         Uri var2 = this.mUri;
         String var3 = var1.append(var2).toString();
         ll.d("Rfc2822", var3);
      }

      boolean var5;
      if(this.mMime != null) {
         boolean var4 = this.mMime.findText();
         if(DEBUG) {
            ll.d("Rfc2822", "find text parts !!");
         }

         var5 = var4;
      } else {
         String var6 = Headers.getBase((String)this.mHeaders.get("content-type"));
         Locale var7 = Locale.US;
         if(var6.toLowerCase(var7).equalsIgnoreCase("text/plain")) {
            var5 = true;
         } else {
            var5 = false;
         }
      }

      return var5;
   }

   private final void processBody(int var1) {
      String var2 = (String)this.mHeaders.get("content-type");
      Locale var3 = Locale.US;
      String var4 = var2.toLowerCase(var3);
      if(DEBUG) {
         StringBuilder var5 = (new StringBuilder()).append(" ********** use type :");
         String var7 = var5.append(var4).append(" **************").toString();
         ll.d("Rfc2822", var7);
      }

      String var8 = this.mSubjectCharset;
      String var10 = "charset";
      String var12 = Headers.getAttribute(var4, var10, var8);
      String var13 = (String)this.mHeaders.get("content-transfer-encoding");
      if(DEBUG) {
         StringBuilder var14 = (new StringBuilder()).append(" ********** org Charset :").append(var12).append(" ************** encoding:");
         String var16 = var14.append(var13).toString();
         ll.d("Rfc2822", var16);
      }

      if(var13 != null) {
         String var18 = "quoted-printable";
         if(var13.equalsIgnoreCase(var18)) {
            ArrayList var19 = this.mLines;
            int var20 = this.mLines.size();
            Object var24 = null;
            QuotedPrintable.decode(var19, var1, var20, (String)var24);
         }
      }

      String var26 = "text/";
      if(var4.contains(var26)) {
         String var28 = "text/plain";
         if(var4.contains(var28)) {
            String var30 = "format";
            String var31 = "fixed";
            String var32 = Headers.getAttribute(var4, var30, var31);
            String var34 = "delsp";
            String var35 = "no";
            String var36 = Headers.getAttribute(var4, var34, var35);
            String var38 = "flowed";
            if(var32.equals(var38)) {
               ArrayList var39 = this.mLines;
               int var40 = this.mLines.size();
               boolean var41 = var36.equals("yes");
               FormatFlowed.decode(var39, var1, var40, var41);
            }
         }

         if(this.isIMAP4) {
            ArrayList var46 = this.mLines;
            int var47 = this.mLines.size() - 1;
            Util.normalizeLineEndings(var46, var1, var47);
            ArrayList var51 = this.mLines;
            int var52 = this.mLines.size() - 1;
            String var57 = Util.linesToString(var51, var1, var52, var12);
            this.mText = var57;
         } else {
            ArrayList var71 = this.mLines;
            int var72 = this.mLines.size();
            Util.normalizeLineEndings(var71, var1, var72);
            ArrayList var76 = this.mLines;
            int var77 = this.mLines.size();
            String var82 = Util.linesToString(var76, var1, var77, var12);
            this.mText = var82;
         }

         if(var13 != null) {
            String var59 = "base64";
            if(var13.equalsIgnoreCase(var59) && this.mText != null) {
               String var60 = this.mText.removeSelf('\n');
               this.mText = var60;

               try {
                  String var61 = Base64.decodeToString(this.mText, var12);
                  this.mText = var61;
               } catch (UnsupportedEncodingException var105) {
                  var105.printStackTrace();
                  StringBuilder var83 = (new StringBuilder()).append("<div align=\"center\" style=\"color: gray\">[");
                  String var84 = this.mContext.getString(2131362302);
                  String var85 = var83.append(var84).append("]</div>").toString();
                  this.mText = var85;
               }
            }
         }

         if(Mail.debug) {
            if(DEBUG) {
               StringBuilder var62 = (new StringBuilder()).append("%%mText:");
               String var63 = this.mText;
               StringBuilder var64 = var62.append(var63).append(",type is ");
               StringBuilder var66 = var64.append(var4).append(".firstBodyLIne is ");
               StringBuilder var68 = var66.append(var1).append(", size is ");
               int var69 = this.mLines.size();
               String var70 = var68.append(var69).toString();
               ll.d("Rfc2822", var70);
            }
         }
      } else {
         String var87 = "multipart";
         if(var4.contains(var87)) {
            String var89 = "boundary";
            String var90 = "----";
            String var91 = Headers.getAttribute(var4, var89, var90);
            Context var92 = this.mContext;
            ArrayList var93 = this.mLines;
            boolean var94 = this.isIMAP4;
            long var95 = this.mAccountId;
            int var97 = this.mContentType;
            Mime var99 = new Mime(var92, var93, var1, var91, var94, var95, var97);
            this.mMime = var99;
            String var101 = "multipart/alternative";
            if(var4.contains(var101)) {
               this.mMime.pruneAlternatives();
            }
         } else {
            StringBuilder var102 = (new StringBuilder()).append("********************* Unhandled type ");
            String var104 = var102.append(var4).toString();
            ll.i("Rfc2822", var104);
         }
      }
   }

   private final boolean saveParts(boolean param1, ArrayList<ContentProviderOperation> param2, long param3) {
      // $FF: Couldn't be decompiled
   }

   public static int setContentType(String var0) {
      int var1;
      if(var0.toLowerCase().contains("multipart/mixed")) {
         var1 = CONTENTTYPE_MIXED;
      } else if(var0.toLowerCase().contains("multipart/related")) {
         var1 = CONTENTTYPE_RELATED;
      } else if(var0.toLowerCase().contains("multipart/alternative")) {
         var1 = CONTENTTYPE_ALTERNATIVE;
      } else if(var0.toLowerCase().startsWith("multipart/")) {
         var1 = CONTENTTYPE_OTHERRMULTIPART;
      } else {
         var1 = CONTENTTYPE_DEFAULT;
      }

      return var1;
   }

   public long getInternalDateInMillis() {
      return this.mInternalDate.getTimeInMillis();
   }

   public final long save() {
      // $FF: Couldn't be decompiled
   }
}
