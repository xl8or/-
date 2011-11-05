package com.htc.android.mail;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;
import com.htc.android.mail.Account;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.Base64;
import com.htc.android.mail.ByteString;
import com.htc.android.mail.ByteStringStreams;
import com.htc.android.mail.FormatFlowed;
import com.htc.android.mail.Headers;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailCommon;
import com.htc.android.mail.QuotedPrintable;
import com.htc.android.mail.Rfc2822;
import com.htc.android.mail.Util;
import com.htc.android.mail.ll;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SyncFailedException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class Mime {

   public static final int BMP = 0;
   public static final String ContentAttachImageGif = "Content-Type: image/gif;\r\n\tname=";
   public static final String ContentAttachImageJpg = "Content-Type: image/jpeg;\r\n\tname=";
   public static final String ContentAttachPlain = "Content-Type: text/plain;\r\n\tname=";
   public static final String ContentDispositon = "Content-Disposition: ";
   public static final String ContentDispositonAttach = "attachment;\r\n\tfilename=";
   public static final String ContentDispositonInline = "inline;\r\n\tfilename=";
   public static final String ContentEncode = "Content-Transfer-Encoding: ";
   public static final String ContentId = "Content-ID: ";
   public static final String ContentInline = "inline";
   public static final String ContentType = "Content-Type: ";
   public static final String ContentTypeAlternativeWithBound = "Content-Type: multipart/alternative;\r\n\tboundary=";
   public static final String ContentTypeHtml = "Content-Type: text/html;\r\n\tcharset=";
   public static final String ContentTypeMixedWithBound = "Content-Type: multipart/mixed;\r\n\tboundary=";
   public static final String ContentTypeName = ";\r\n\tname=";
   public static final String ContentTypeRelatedWithBound = "Content-Type: multipart/related;\r\n\tboundary=";
   public static final String ContentTypeText = "Content-Type: text/plain;\r\n\tcharset=";
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   public static final int JPG = 2;
   public static final int MP3 = 5;
   public static final String MimeMultiPartDefine = "This is a multi-part message in MIME format.";
   public static final String MimeVerheader = "MIME-Version: ";
   public static final String MimeVersion = "1.0";
   public static final int PNG = 1;
   private static final String TAG = "Mime";
   public static final int TIFF = 3;
   public static final int TXT = 4;
   public static final int UNSUPPORT = 255;
   public static final String charsetBig5 = "big5";
   public static final String charsetISO88591 = "ISO-8859-1";
   public static final String charsetUTF8 = "utf-8";
   public static String chatset = "ISO-8859-1";
   public static final String encode7bit = "7bit";
   public static final String encodeBase64 = "base64";
   public static String encodeMethod = "base64";
   public static final String encodeQT = "quoted-printable";
   public static String[] mNotAttachmentType;
   public static final String shortBase64 = "B";
   public static final String shortQT = "Q";
   public static boolean supportHTML = 1;
   private boolean includeAttach = 0;
   private boolean isIMAP4 = 0;
   private Account mAccount;
   private long mAccountID = 0L;
   public int mContentType;
   Context mContext;
   private int mEnableSDsave = 0;
   int mHeaderBreak;
   ArrayList<ByteString> mLines;
   ArrayList<Mime.Part> mParts;
   private boolean mTextBodyEmpty;


   static {
      String[] var0 = new String[]{"application/x-pkcs7-signature", "application/pkcs7-signature", "application/pgp-signature"};
      mNotAttachmentType = var0;
   }

   Mime(Context var1, ArrayList<ByteString> var2, int var3, String var4, boolean var5, long var6, int var8) {
      int var9 = Rfc2822.CONTENTTYPE_DEFAULT;
      this.mContentType = var9;
      this.mTextBodyEmpty = (boolean)0;
      this.mLines = var2;
      this.mHeaderBreak = var3;
      this.mContext = var1;
      this.isIMAP4 = var5;
      this.mAccountID = var6;
      this.mContentType = var8;
      Account var10 = AccountPool.getInstance(var1).getAccount(var1, var6);
      this.mAccount = var10;
      ArrayList var11 = new ArrayList();
      this.mParts = var11;
      if(Mail.MAIL_DETAIL_DEBUG) {
         String var12 = "headerBreak is " + var3;
         ll.d("Mime", var12);
      }

      this.findParts(var4);
      this.processParts(var4);
   }

   // $FF: synthetic method
   static long access$202(Mime var0, long var1) {
      var0.mAccountID = var1;
      return var1;
   }

   // $FF: synthetic method
   static Account access$300(Mime var0) {
      return var0.mAccount;
   }

   // $FF: synthetic method
   static boolean access$400(Mime var0) {
      return var0.mTextBodyEmpty;
   }

   // $FF: synthetic method
   static boolean access$402(Mime var0, boolean var1) {
      var0.mTextBodyEmpty = var1;
      return var1;
   }

   // $FF: synthetic method
   static boolean access$500(Mime var0, String var1, String var2) {
      return var0.renameFile(var1, var2);
   }

   public static final boolean dropAttach(String var0) {
      boolean var1;
      if(var0 == null) {
         var1 = false;
      } else {
         int var2 = 0;

         while(true) {
            int var3 = mNotAttachmentType.length;
            if(var2 >= var3) {
               var1 = false;
               break;
            }

            String var4 = mNotAttachmentType[var2];
            if(var0.equalsIgnoreCase(var4)) {
               var1 = true;
               break;
            }

            ++var2;
         }
      }

      return var1;
   }

   private final void findParts(String var1) {
      ByteString var2 = new ByteString(var1);
      int var3 = var2.length();
      int var4 = this.mLines.size();
      int var5 = -1;
      int var6 = 0;
      int var7 = 0;

      for(int var8 = this.mHeaderBreak; var8 < var4; ++var8) {
         ByteString var9 = (ByteString)this.mLines.get(var8);
         if(var9.regionMatches((boolean)1, 2, var2, 0, var3)) {
            ++var6;
            if(var5 != -1) {
               ArrayList var10 = this.mParts;
               Mime.Part var11 = new Mime.Part(var5);
               var10.add(var11);
               if(Mail.MAIL_DETAIL_DEBUG) {
                  ll.d("Mime", "add part to mail");
               }

               ++var7;
            }

            var5 = var8 + 1;
            int var13 = var3 + 2 + 2;
            int var14 = var9.length();
            if(var13 < var14) {
               int var15 = var3 + 2;
               byte var16 = var9.byteAt(var15);
               if(45 == var16) {
                  int var17 = var3 + 2 + 1;
                  byte var18 = var9.byteAt(var17);
                  if(45 == var18) {
                     var6 += -1;
                     break;
                  }
               }
            }
         }
      }

      if(var6 > var7) {
         Mime.Part var19 = new Mime.Part(var5);
         var19.mIncomplete = (boolean)1;
         this.mParts.add(var19);
         if(Mail.MAIL_DETAIL_DEBUG) {
            ll.d("Mime", "This Part is incomplete!");
         }
      }
   }

   private void forceSync(File var1) {
      String var2 = "force Sync> " + var1;
      ll.i("Mime", var2);

      try {
         ParcelFileDescriptor var3 = ParcelFileDescriptor.open(var1, 268435456);
         var3.getFileDescriptor().sync();
         if(var3 != null) {
            var3.close();
         }
      } catch (FileNotFoundException var7) {
         ll.e("Mime", "File Not Found Exception!");
      } catch (SyncFailedException var8) {
         ll.e("Mime", "Sync Failed Exception");
      } catch (IOException var9) {
         ll.e("Mime", "could not force sync to SD card");
      }
   }

   private final void processParts(String var1) {
      for(int var2 = this.mParts.size() - 1; var2 >= 0; var2 += -1) {
         if(this.mParts.get(var2) == null) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var3 = "mParts null>" + var2;
               ll.d("dd", var3);
            }
         } else {
            ((Mime.Part)this.mParts.get(var2)).process(var1);
         }
      }

   }

   private final boolean renameFile(String var1, String var2) {
      File var3 = new File(var2);
      boolean var6;
      if(var3.exists()) {
         File var4 = new File(var1);
         var3.renameTo(var4);
         this.forceSync(var4);
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   private String saveFileByseperate(ArrayList<ByteString> var1, int var2, boolean var3, String var4, String var5) {
      if(Mail.MAIL_DETAIL_DEBUG) {
         StringBuilder var6 = (new StringBuilder()).append("  first line and arraysize:");
         StringBuilder var8 = var6.append(var2).append("       ");
         int var9 = var1.size();
         String var10 = var8.append(var9).toString();
         ll.i("Mime", var10);
      }

      int var11 = var1.size();
      String var16;
      if(var11 > var2 && ((ByteString)var1.get(var2)).toString().contains("token")) {
         if(Mail.MAIL_DETAIL_DEBUG) {
            StringBuilder var13 = (new StringBuilder()).append("token tmpFilename:");
            String var14 = ((ByteString)var1.get(var2)).toString();
            String var15 = var13.append(var14).toString();
            ll.i("Mime", var15);
         }

         var16 = ((ByteString)var1.get(var2)).toString();
      } else {
         int var17 = var1.size();
         if(var17 <= var2) {
            var16 = null;
         } else {
            int var19 = var1.size() - 1;
            short var20 = 1000;
            int var21 = (var19 - var2) / var20;
            int var22 = var19 - var2;
            int var23 = var21 * var20;
            int var24 = var22 - var23;
            byte[] var25 = new byte[0];
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("Mime", "do saveFileByseperate");
            }

            Context var26 = this.mContext;
            int var27 = this.mAccount.enableSDsave;
            StringBuilder var28 = (new StringBuilder()).append("token");
            String var29 = String.valueOf(System.currentTimeMillis());
            String var30 = var28.append(var29).toString();
            int var31 = this.mContentType;
            String var32 = MailCommon.getAttachmentFilePath(var26, var27, ".Mail", var30, var31);
            int var33 = 0;

            while(true) {
               if(var33 > var21) {
                  var16 = var32;
                  break;
               }

               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var36 = (new StringBuilder()).append("decode part : i =");
                  String var38 = var36.append(var33).toString();
                  ll.d("Mime", var38);
               }

               Runtime.getRuntime().gc();
               int var44;
               int var45;
               if(var33 == var21) {
                  int var43 = var21 * var20;
                  var44 = var2 + var43;
                  var45 = var21 * var20 + var2 + var24;
               } else {
                  int var62 = var33 * var20;
                  var44 = var2 + var62;
                  int var63 = (var33 + 1) * var20;
                  var45 = var2 + var63;
               }

               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var46 = (new StringBuilder()).append(" @@@@  decode start.");
                  StringBuilder var48 = var46.append(var44).append(",");
                  String var50 = var48.append(var45).toString();
                  ll.d("Mime", var50);
               }

               if(var3) {
                  ByteStringStreams var51 = new ByteStringStreams(var1, var44, var45);
                  InputStream var56 = var51.getBase64InputStream();

                  try {
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        ll.d("Mime", " @@@@ decode.");
                     }

                     byte[] var57 = Base64.decode(var56);
                     boolean var61 = this.saveFileWithPath(var32, var57);
                  } catch (IOException var76) {
                     var76.printStackTrace();
                     boolean var64 = (new File(var32)).delete();
                     Context var65 = this.mContext;
                     String var66 = this.mContext.getString(2131362452);
                     Toast.makeText(var65, var66, 1).show();
                     var16 = null;
                     break;
                  }
               } else {
                  byte[] var71 = Util.linesToByteString(var1, var44, var45, var5).mStorage;
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     ll.d("Mime", " @@@@ save.");
                  }

                  boolean var75 = this.saveFileWithPath(var32, var71);
               }

               ++var33;
            }
         }
      }

      return var16;
   }

   private final boolean saveFileWithPath(String var1, byte[] var2) {
      boolean var4;
      try {
         FileOutputStream var3 = new FileOutputStream(var1, (boolean)1);
         var3.write(var2);
         var3.close();
      } catch (IOException var5) {
         var5.printStackTrace();
         var4 = false;
         return var4;
      }

      var4 = true;
      return var4;
   }

   final boolean findText() {
      int var1 = this.mParts.size();
      boolean var2 = false;
      int var3 = var1 - 1;

      boolean var7;
      while(true) {
         if(var3 < 0) {
            var7 = var2;
            break;
         }

         Mime.Part var4 = (Mime.Part)this.mParts.get(var3);
         if(Mail.MAIL_DETAIL_DEBUG) {
            String var5 = "try find i = " + var3;
            ll.d("Mime", var5);
         }

         if(var4.mNoBody) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("Mime", "noBody exist.");
            }
         } else {
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var6 = "find i = " + var3;
               ll.d("Mime", var6);
            }

            if(var4.find() || var2) {
               var2 = true;
            }

            if(var2) {
               var7 = var2;
               break;
            }
         }

         var3 += -1;
      }

      return var7;
   }

   final Mime.Part get(int var1) {
      return (Mime.Part)this.mParts.get(var1);
   }

   final void pruneAlternatives() {
      if(this.mParts != null) {
         if(this.mParts.size() != 0) {
            int var1 = 0;
            int var2 = this.mParts.size();

            for(int var3 = 0; var3 < var2; ++var3) {
               if(!((Mime.Part)this.mParts.get(var3)).mIncomplete) {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     String var4 = "no :" + var3 + " is complete.";
                     ll.d("Mime", var4);
                  }

                  var1 = var3;
               }
            }

            Mime.Part var5 = (Mime.Part)this.mParts.get(var1);
         }
      }
   }

   final boolean saveParts(long var1, long var3, int var5, int var6, boolean var7, ArrayList<ContentProviderOperation> var8, Uri var9) {
      int var10 = this.mParts.size();
      boolean var11 = false;

      for(int var12 = 0; var12 < var10; ++var12) {
         Mime.Part var13 = (Mime.Part)this.mParts.get(var12);
         if(Mail.MAIL_DETAIL_DEBUG) {
            String var14 = "try save i = " + var12;
            ll.d("Mime", var14);
         }

         if(var13.mNoBody) {
            if(Mail.MAIL_DETAIL_DEBUG) {
               ll.d("Mime", "noBody exist.");
            }
         } else {
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var15 = "save i = " + var12;
               ll.d("Mime", var15);
            }

            if(var13.save(var1, var3, var5, var12, var6, var7, var8, var9) || var11) {
               var11 = true;
            }
         }
      }

      return var11;
   }

   final int size() {
      return this.mParts.size();
   }

   class Part {

      byte[] mBinaryPart;
      String mContentId = "";
      Mime mEnvelope = null;
      String mFilename = "";
      HashMap<String, String> mHeaders;
      boolean mIncomplete;
      boolean mNoBody = 0;
      int mRfc822Start;
      int mStart;
      String mTextPart = "";
      String mTmpFilename = "";


      Part(int var2) {
         this.mStart = var2;
      }

      private final void findFilename(String var1) {
         String var2 = Headers.getAttributeCaseSens(var1, "name", (String)null);
         String var3 = (String)this.mHeaders.get("content-description");
         String var4 = (String)this.mHeaders.get("content-disposition");
         if(var4 != null) {
            var4 = Headers.getAttributeCaseSens(var4, "filename", (String)null);
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var5 = " ######## disposition is not null." + var4;
               ll.d("Mime", var5);
            }
         }

         if(var2 != null) {
            this.mFilename = var2;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var6 = (new StringBuilder()).append(" ######## typeName mFilename is ");
               String var7 = this.mFilename;
               String var8 = var6.append(var7).toString();
               ll.d("Mime", var8);
            }
         } else if(var4 != null) {
            this.mFilename = var4;
            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var10 = (new StringBuilder()).append(" ######## disposition mFilename is ");
               String var11 = this.mFilename;
               String var12 = var10.append(var11).toString();
               ll.d("Mime", var12);
            }
         }

         String var9 = Headers.rfc2047(this.mFilename);
         this.mFilename = var9;
      }

      private final boolean saveFile(String var1, String var2, byte[] var3) {
         boolean var8;
         try {
            String var4 = Mail.MAIL_SDCARD_ATTACHMENET_HOME;
            Boolean var5 = Boolean.valueOf((new File(var4)).mkdirs());
            if(Mail.MAIL_DETAIL_DEBUG) {
               String var6 = "create folder :" + var5;
               ll.d("Mime", var6);
            }

            FileOutputStream var7 = new FileOutputStream(var1);
            var7.write(var3);
            var7.close();
         } catch (IOException var9) {
            var9.printStackTrace();
            var8 = false;
            return var8;
         }

         var8 = true;
         return var8;
      }

      final boolean find() {
         boolean var1 = false;
         boolean var2;
         if(this.mEnvelope != null) {
            if(this.mEnvelope.findText() || false) {
               var1 = true;
            }

            var2 = var1;
         } else {
            String var3 = Headers.getBase((String)this.mHeaders.get("content-type"));
            if((this.mFilename == null || this.mFilename.length() == 0) && var3.equalsIgnoreCase("text/plain")) {
               var2 = true;
            } else {
               var2 = false;
            }
         }

         return var2;
      }

      final void process(String var1) {
         ArrayList var2 = Mime.this.mLines;
         int var3 = this.mStart;
         int var4 = Util.unfoldLines(var2, var3, (boolean)1);
         if(Mail.MAIL_DETAIL_DEBUG) {
            StringBuilder var5 = (new StringBuilder()).append("mStart is:");
            int var6 = this.mStart;
            StringBuilder var7 = var5.append(var6).append(", and headerBreak is:");
            StringBuilder var9 = var7.append(var4).append(",mLines.size():");
            int var10 = Mime.this.mLines.size();
            String var11 = var9.append(var10).toString();
            ll.d("Mime", var11);
         }

         int var12 = var4 + 1;
         byte var14 = -1;
         int var15;
         if(var4 == var14) {
            var15 = Mime.this.mLines.size() - 1;

            while(true) {
               int var16 = this.mStart - 1;
               if(var15 < var16) {
                  byte var22 = 1;
                  this.mNoBody = (boolean)var22;
                  return;
               }

               ArrayList var19 = Mime.this.mLines;
               var19.remove(var15);
               var15 += -1;
            }
         } else {
            HashMap var23 = new HashMap();
            this.mHeaders = var23;
            ArrayList var24 = Mime.this.mLines;
            int var25 = this.mStart;
            HashMap var26 = this.mHeaders;
            Headers.extract(var24, var25, var4, var26);
            String var31 = (String)this.mHeaders.get("content-type");
            String var32 = Mail.getDefaultCharset();
            String var34 = "charset";
            String var36 = Headers.getAttribute(var31, var34, var32);
            String var37 = (String)this.mHeaders.get("content-transfer-encoding");
            String var38 = (String)this.mHeaders.get("content-location");
            if(this.mHeaders.get("content-id") != null) {
               String var39 = ((String)this.mHeaders.get("content-id")).trim().replaceAll("<", "").replaceAll(">", "");
               this.mContentId = var39;
            }

            if(var38 == null) {
               if(Mail.MAIL_DETAIL_DEBUG) {
                  ll.d("Mime", "location is null");
               }
            } else if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var108 = (new StringBuilder()).append("location is ");
               String var110 = var108.append(var38).toString();
               ll.d("Mime", var110);
            }

            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var40 = (new StringBuilder()).append("encoding is ");
               String var42 = var40.append(var37).toString();
               ll.d("Mime", var42);
            }

            if(Mail.MAIL_DETAIL_DEBUG) {
               StringBuilder var43 = (new StringBuilder()).append("type is ");
               StringBuilder var45 = var43.append(var31).append(",mStart is ");
               int var46 = this.mStart;
               StringBuilder var47 = var45.append(var46).append(".firstBodyLIne is ").append(var12).append(",charset is ").append(var36).append(",encoding is ");
               String var49 = var47.append(var37).toString();
               ll.d("Mime", var49);
            }

            if(var37 != null) {
               String var51 = "quoted-printable";
               if(var37.equalsIgnoreCase(var51)) {
                  ArrayList var52 = Mime.this.mLines;
                  int var53 = Mime.this.mLines.size();
                  QuotedPrintable.decode(var52, var12, var53, var1);
               }
            }

            String var59 = "text/";
            byte var84;
            ArrayList var94;
            if(var31.contains(var59)) {
               String var61 = "text/plain";
               if(var31.contains(var61)) {
                  String var63 = "format";
                  String var64 = "fixed";
                  String var65 = Headers.getAttribute(var31, var63, var64);
                  String var67 = "delsp";
                  String var68 = "no";
                  String var69 = Headers.getAttribute(var31, var67, var68);
                  String var71 = "flowed";
                  if(var65.equals(var71)) {
                     ArrayList var72 = Mime.this.mLines;
                     int var73 = Mime.this.mLines.size();
                     String var75 = "yes";
                     boolean var76 = var69.equals(var75);
                     FormatFlowed.decode(var72, var12, var73, var76);
                  }
               }

               this.findFilename(var31);
               if(this.mFilename != null && this.mFilename.length() > 0) {
                  ArrayList var81;
                  Mime var80;
                  label169: {
                     String var79 = "";
                     this.mTextPart = var79;
                     var80 = Mime.this;
                     var81 = Mime.this.mLines;
                     if(var37 != null) {
                        String var83 = "base64";
                        if(var37.contains(var83)) {
                           var84 = 1;
                           break label169;
                        }
                     }

                     var84 = 0;
                  }

                  String var86 = var80.saveFileByseperate(var81, var12, (boolean)var84, var36, var1);
                  this.mTmpFilename = var86;
               } else {
                  try {
                     ArrayList var111 = Mime.this.mLines;
                     int var112 = Mime.this.mLines.size();
                     Util.normalizeLineEndings(var111, var12, var112);
                     ArrayList var113 = Mime.this.mLines;
                     int var114 = Mime.this.mLines.size();
                     String var120 = Util.linesToString(var113, var12, var114, var36, var1);
                     this.mTextPart = var120;
                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var121 = (new StringBuilder()).append("type is ");
                        StringBuilder var123 = var121.append(var31).append(",mStart is ");
                        int var124 = this.mStart;
                        String var125 = var123.append(var124).append(".firstBodyLIne is ").append(var12).append(",charset is ").append(var36).toString();
                        ll.d("Mime", var125);
                     }

                     if(Mail.MAIL_DETAIL_DEBUG) {
                        StringBuilder var126 = (new StringBuilder()).append("%%Size is ");
                        int var127 = Mime.this.mLines.size();
                        String var128 = var126.append(var127).toString();
                        ll.d("Mime", var128);
                     }

                     if(var37 != null) {
                        String var130 = "base64";
                        if(var37.equalsIgnoreCase(var130)) {
                           String var131 = this.mTextPart.removeSelf('\n');
                           this.mTextPart = var131;
                           String var132 = Base64.decodeToString(this.mTextPart, var36);
                           this.mTextPart = var132;
                        }
                     }
                  } catch (UnsupportedEncodingException var200) {
                     var200.printStackTrace();
                     StringBuilder var133 = (new StringBuilder()).append("<div align=\"center\" style=\"color: gray\">[");
                     String var134 = Mime.this.mContext.getString(2131362302);
                     String var135 = var133.append(var134).append("]</div>").toString();
                     this.mTextPart = var135;
                  }
               }

               String var87 = (String)this.mHeaders.get("content-location");
               if(var87 != null) {
                  if(Mail.MAIL_DETAIL_DEBUG) {
                     StringBuilder var88 = (new StringBuilder()).append("content-location is ");
                     String var90 = var88.append(var87).toString();
                     ll.d("Mime", var90);
                  }

                  Mime var93;
                  label162: {
                     String var91 = "noname";
                     this.mFilename = var91;
                     String var92 = "";
                     this.mTextPart = var92;
                     var93 = Mime.this;
                     var94 = Mime.this.mLines;
                     if(var37 != null) {
                        String var96 = "base64";
                        if(var37.contains(var96)) {
                           var84 = 1;
                           break label162;
                        }
                     }

                     var84 = 0;
                  }

                  String var98 = var93.saveFileByseperate(var94, var12, (boolean)var84, var36, var1);
                  this.mTmpFilename = var98;
               }
            } else {
               String var137 = "multipart";
               if(var31.startsWith(var137)) {
                  String var139 = "boundary";
                  String var140 = "---";
                  String var141 = Headers.getAttribute(var31, var139, var140);
                  int var142 = Rfc2822.CONTENTTYPE_DEFAULT;
                  int var143 = Rfc2822.setContentType(var31);
                  Context var144 = Mime.this.mContext;
                  ArrayList var145 = Mime.this.mLines;
                  boolean var146 = Mime.this.isIMAP4;
                  long var147 = Mime.this.mAccountID;
                  Mime var150 = new Mime(var144, var145, var12, var141, var146, var147, var143);
                  this.mEnvelope = var150;
                  String var152 = "multipart/alternative";
                  if(var31.contains(var152)) {
                     this.mEnvelope.pruneAlternatives();
                  }
               } else {
                  label191: {
                     String var154 = "name";
                     Object var155 = null;
                     if(Headers.getAttributeCaseSens(var31, var154, (String)var155) != null) {
                        Mime var158;
                        label154: {
                           this.findFilename(var31);
                           var158 = Mime.this;
                           var94 = Mime.this.mLines;
                           if(var37 != null) {
                              String var160 = "base64";
                              if(var37.contains(var160)) {
                                 var84 = 1;
                                 break label154;
                              }
                           }

                           var84 = 0;
                        }

                        String var162 = var158.saveFileByseperate(var94, var12, (boolean)var84, var36, var1);
                        this.mTmpFilename = var162;
                     } else {
                        this.findFilename(var31);
                        if(this.mFilename == null || this.mFilename.length() == 0) {
                           String var176 = (String)this.mHeaders.get("content-location");
                           ArrayList var182;
                           if(var176 != null) {
                              if(Mail.MAIL_DETAIL_DEBUG) {
                                 StringBuilder var177 = (new StringBuilder()).append("content-location is ");
                                 String var179 = var177.append(var176).toString();
                                 ll.d("Mime", var179);
                              }

                              Mime var181;
                              label143: {
                                 String var180 = "noname";
                                 this.mFilename = var180;
                                 var181 = Mime.this;
                                 var182 = Mime.this.mLines;
                                 if(var37 != null) {
                                    String var184 = "base64";
                                    if(var37.contains(var184)) {
                                       var84 = 1;
                                       break label143;
                                    }
                                 }

                                 var84 = 0;
                              }

                              String var186 = var181.saveFileByseperate(var182, var12, (boolean)var84, var36, var1);
                              this.mTmpFilename = var186;
                           } else {
                              Mime var188;
                              label138: {
                                 String var187 = "noname";
                                 this.mFilename = var187;
                                 var188 = Mime.this;
                                 var182 = Mime.this.mLines;
                                 if(var37 != null) {
                                    String var190 = "base64";
                                    if(var37.contains(var190)) {
                                       var84 = 1;
                                       break label138;
                                    }
                                 }

                                 var84 = 0;
                              }

                              String var192 = var188.saveFileByseperate(var182, var12, (boolean)var84, var36, var1);
                              this.mTmpFilename = var192;
                           }
                        }
                     }

                     if(var37 != null) {
                        String var164 = "quoted-printable";
                        if(var37.contains(var164)) {
                           ArrayList var165 = Mime.this.mLines;
                           int var166 = Mime.this.mLines.size();
                           QuotedPrintable.decode(var165, var12, var166, var1);
                           ArrayList var171 = Mime.this.mLines;
                           int var172 = Mime.this.mLines.size();
                           byte[] var173 = Util.linesToString(var171, var12, var172, var36).getBytes();
                           this.mBinaryPart = var173;
                           break label191;
                        }
                     }

                     if(var37 != null) {
                        String var194 = "base64";
                        if(var37.contains(var194) && !this.mIncomplete) {
                           String var195 = this.mTmpFilename;
                           if("".equals(var195)) {
                              Mime var196 = Mime.this;
                              ArrayList var197 = Mime.this.mLines;
                              String var199 = var196.saveFileByseperate(var197, var12, (boolean)1, var36, var1);
                              this.mTmpFilename = var199;
                           }
                        }
                     }
                  }
               }
            }

            var15 = Mime.this.mLines.size() - 1;

            while(true) {
               int var99 = this.mStart;
               if(var15 < var99) {
                  return;
               }

               if(Mail.MAIL_DETAIL_DEBUG) {
                  StringBuilder var102 = (new StringBuilder()).append("remove ");
                  String var104 = var102.append(var15).toString();
                  ll.d("Mime", var104);
               }

               ArrayList var105 = Mime.this.mLines;
               var105.remove(var15);
               var15 += -1;
            }
         }
      }

      final boolean save(long param1, long param3, int param5, int param6, int param7, boolean param8, ArrayList<ContentProviderOperation> param9, Uri param10) {
         // $FF: Couldn't be decompiled
      }
   }
}
