package com.htc.android.mail;

import android.content.Context;
import com.htc.android.mail.ByteString;
import com.htc.android.mail.DateParser;
import com.htc.android.mail.Headers;
import com.htc.android.mail.Mail;
import com.htc.android.mail.Regex;
import com.htc.android.mail.ll;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IMapHeader {

   public static final int BODY = 1;
   public static final int CC = 8;
   public static final int DATE = 6;
   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   public static final int FROM = 5;
   public static final int HEADER = 0;
   public static final int PART = 3;
   public static final int SUBJECT = 4;
   private static final String TAG = "IMapHeader";
   public static final int TO = 7;
   public static final int UID = 2;
   private static final Pattern sFromP = Pattern.compile("^(.*) <(.*)>.*$");
   private boolean ReadFlag;
   private int TotalSize;
   private byte[] bodyArray;
   private String bodyContent;
   private StringBuilder bodyContentBuilder;
   private int bodySize;
   private boolean continueTo;
   private Long dateValue;
   private String header_cc;
   private String header_date = "";
   private String header_from = "";
   private String header_fromEmail = "";
   private String header_fromName = "";
   private String header_subject = "";
   private String header_to;
   private String mCharset = "";
   private Context mContext;
   private String mEncode = "";
   private String uid;


   public IMapHeader(Context var1, ArrayList<ByteString> var2) {
      Long var3 = Long.valueOf(0L);
      this.dateValue = var3;
      this.header_to = "";
      this.continueTo = (boolean)0;
      this.bodyContent = "";
      StringBuilder var4 = new StringBuilder();
      this.bodyContentBuilder = var4;
      this.uid = "";
      this.header_cc = "";
      byte[] var5 = new byte[0];
      this.bodyArray = var5;
      this.bodySize = 0;
      this.TotalSize = 0;
      this.ReadFlag = (boolean)0;
      this.mContext = var1;
      int var6 = 0;

      while(true) {
         int var7 = var2.size();
         if(var6 >= var7) {
            if(this.header_from != null) {
               Pattern var42 = sFromP;
               String var43 = this.header_from;
               Matcher var44 = var42.matcher(var43);
               if(var44.matches()) {
                  String var45 = Headers.rfc2047(var44.group(1));
                  this.header_fromName = var45;
                  String var46 = Headers.rfc2047(var44.group(2));
                  this.header_fromEmail = var46;
                  if(this.header_fromName.trim().length() == 0) {
                     String var47 = this.header_fromEmail.split("@")[0];
                     this.header_fromName = var47;
                  } else {
                     String var54 = this.header_fromName.replace("\"", "");
                     this.header_fromName = var54;
                  }
               } else {
                  String var55 = Headers.rfc2047(this.header_from);
                  this.header_from = var55;
                  if(DEBUG) {
                     StringBuilder var56 = (new StringBuilder()).append("from:");
                     String var57 = this.header_from;
                     String var58 = var56.append(var57).toString();
                     ll.d("IMapHeader", var58);
                  }

                  Pattern var59 = sFromP;
                  String var60 = this.header_from;
                  Matcher var61 = var59.matcher(var60);
                  if(var61.matches()) {
                     String var62 = var61.group(1).replace("\"", "");
                     this.header_fromName = var62;
                     String var63 = var61.group(2);
                     this.header_fromEmail = var63;
                     if(this.header_fromName.trim().length() == 0) {
                        String var64 = this.header_fromEmail.split("@")[0];
                        this.header_fromName = var64;
                     } else {
                        String var65 = this.header_fromName.replace("\"", "");
                        this.header_fromName = var65;
                     }
                  } else if(this.header_from.length() == 0) {
                     this.header_fromName = "none";
                     this.header_fromEmail = "none";
                  } else if(this.header_from.startsWith("<")) {
                     String var66 = this.header_from.replace("<", "").replace(">", "").split("@")[0];
                     this.header_fromName = var66;
                     String var67 = this.header_from.replace("<", "").replace(">", "");
                     this.header_fromEmail = var67;
                  } else if(this.header_from.contains("@")) {
                     String var68 = this.header_from.split("@")[0];
                     this.header_fromName = var68;
                     String var69 = this.header_from;
                     this.header_fromEmail = var69;
                  } else {
                     String var70 = this.header_from;
                     this.header_fromName = var70;
                     String var71 = this.header_from;
                     this.header_fromEmail = var71;
                  }
               }
            }

            if(DEBUG) {
               StringBuilder var48 = (new StringBuilder()).append("from:");
               String var49 = this.header_fromName;
               StringBuilder var50 = var48.append(var49).append(":");
               String var51 = this.header_fromEmail;
               String var52 = var50.append(var51).toString();
               ll.d("IMapHeader", var52);
            }

            if(this.header_subject != null) {
               while(this.header_subject.contains("=?") && this.header_subject.contains("?=")) {
                  String var53 = Headers.rfc2047(this.header_subject);
                  this.header_subject = var53;
               }
            }

            if(DEBUG) {
               StringBuilder var72 = (new StringBuilder()).append("subject:");
               String var73 = this.header_subject;
               String var74 = var72.append(var73).toString();
               ll.d("IMapHeader", var74);
            }

            if(this.header_date != null) {
               Long var75 = Long.valueOf(DateParser.parse(this.header_date).getTimeInMillis());
               this.dateValue = var75;
            }

            if(DEBUG) {
               StringBuilder var76 = (new StringBuilder()).append("(before)Cc:");
               String var77 = this.header_cc;
               String var78 = var76.append(var77).toString();
               ll.d("IMapHeader", var78);
            }

            if(this.header_cc != null) {
               while(this.header_cc.contains("=?") && this.header_cc.contains("?=")) {
                  String var79 = Headers.rfc2047(this.header_cc);
                  this.header_cc = var79;
               }
            }

            if(!DEBUG) {
               return;
            } else {
               StringBuilder var80 = (new StringBuilder()).append("(after)Cc:");
               String var81 = this.header_cc;
               String var82 = var80.append(var81).toString();
               ll.d("IMapHeader", var82);
               return;
            }
         }

         String var8 = ((ByteString)var2.get(var6)).toString().trim();
         if(DEBUG) {
            String var9 = "checking :" + var8;
            ll.d("IMapHeader", var9);
         }

         if(var8.startsWith("*")) {
            Pattern var10 = Regex.getInstance().getPattern(6);
            String var11 = var2.toString().trim();
            Matcher var12 = var10.matcher(var11);
            if(var12.find()) {
               if(DEBUG) {
                  StringBuilder var13 = (new StringBuilder()).append("uid:");
                  String var14 = var12.group(1);
                  String var15 = var13.append(var14).toString();
                  ll.d("IMapHeader", var15);
               }

               if(DEBUG) {
                  StringBuilder var16 = (new StringBuilder()).append("size:");
                  String var17 = var12.group(2);
                  String var18 = var16.append(var17).toString();
                  ll.d("IMapHeader", var18);
               }

               int var19 = Integer.valueOf(var12.group(2)).intValue();
               this.TotalSize = var19;
               if(DEBUG) {
                  StringBuilder var20 = (new StringBuilder()).append("flag:");
                  String var21 = var12.group(3);
                  String var22 = var20.append(var21).toString();
                  ll.d("IMapHeader", var22);
               }

               if(var12.group(3).contains("\\Seen")) {
                  if(DEBUG) {
                     ll.d("IMapHeader", "Seen !");
                  }

                  this.ReadFlag = (boolean)1;
               }

               if(DEBUG) {
                  StringBuilder var23 = (new StringBuilder()).append("body header size:");
                  String var24 = var12.group(4);
                  String var25 = var23.append(var24).toString();
                  ll.d("IMapHeader", var25);
               }

               int var26 = Integer.valueOf(var12.group(4)).intValue();
               this.bodySize = var26;
            }
         } else if(var8.startsWith("Subject:")) {
            String var27 = var8.replace("Subject:", "");
            this.header_subject = var27;
            if(DEBUG) {
               StringBuilder var28 = (new StringBuilder()).append("header_subject set:");
               String var29 = this.header_subject;
               String var30 = var28.append(var29).toString();
               ll.d("IMapHeader", var30);
            }
         } else if(var8.startsWith("From:")) {
            String var31 = var8.split(" ", 2)[1];
            this.header_from = var31;
            if(DEBUG) {
               StringBuilder var32 = (new StringBuilder()).append("header_from set:");
               String var33 = this.header_from;
               String var34 = var32.append(var33).toString();
               ll.d("IMapHeader", var34);
            }
         } else if(var8.startsWith("Date:")) {
            String var35 = var8.split(" ", 2)[1];
            this.header_date = var35;
            if(DEBUG) {
               StringBuilder var36 = (new StringBuilder()).append("header_date set:");
               String var37 = this.header_date;
               String var38 = var36.append(var37).toString();
               ll.d("IMapHeader", var38);
            }
         } else if(var8.startsWith("To:")) {
            String var39 = var8.split(" ", 2)[1];
            this.header_to = var39;
         } else if(var8.startsWith("Cc:")) {
            String[] var40 = var8.split(" ", 2);
            if(var40.length == 2) {
               String var41 = var40[1];
               this.header_cc = var41;
            }
         }

         ++var6;
      }
   }
}
