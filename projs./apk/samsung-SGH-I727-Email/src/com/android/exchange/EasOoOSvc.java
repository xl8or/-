package com.android.exchange;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.Time;
import com.android.email.activity.setup.OoOConstants;
import com.android.email.mail.MessagingException;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailServiceCallback;
import com.android.exchange.Eas;
import com.android.exchange.EasSyncService;
import com.android.exchange.OoOData;
import com.android.exchange.OoODataList;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.LogAdapter;
import com.android.exchange.adapter.OoOCommandParser;
import com.android.exchange.adapter.Serializer;
import com.android.exchange.adapter.SettingsCommandAdapter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;

public class EasOoOSvc extends EasSyncService {

   private Date mEndDate;
   private String mExternalKnownMsg;
   private String mExternalUnKnownMsg;
   private String mInternalMsg;
   private boolean mIsExtKnown;
   private boolean mIsExtUnKnown;
   private boolean mIsGlobal;
   private boolean mIsInternal;
   private boolean mIsTimeBased;
   private Date mStartDate;
   private OoODataList mSvcData;


   public EasOoOSvc(Context var1, EmailContent.Account var2, OoODataList var3) {
      super(var1, var2);
      this.mSvcData = var3;
      this.mIsExtUnKnown = (boolean)0;
      this.mIsExtKnown = (boolean)0;
      this.mIsInternal = (boolean)0;
      this.mIsGlobal = (boolean)0;
      this.mIsTimeBased = (boolean)0;
      Date var4 = new Date();
      this.mStartDate = var4;
      Date var5 = new Date();
      this.mEndDate = var5;
      String var6 = this.mAccount.mProtocolVersion;
      this.mProtocolVersion = var6;
      Double var7 = Double.valueOf(Double.parseDouble(this.mProtocolVersion));
      this.mProtocolVersionDouble = var7;
      EmailContent.Mailbox var8 = this.mMailbox;
      long var9 = SyncManager.MAILBOX_DUMMY_OoO;
      var8.mId = var9;
      long var11 = this.mMailbox.mId;
      this.mMailboxId = var11;
      this.mMailbox.mDisplayName = "OoO";
   }

   private String convertLocalToUTC(long var1) {
      Time var3 = new Time("UTC");
      var3.set(var1);
      String[] var4 = var3.format3339((boolean)0).split("000");
      String var8;
      if(var4 != null && var4[0] != false) {
         StringBuilder var5 = new StringBuilder();
         String var6 = var4[0];
         String var7 = var5.append(var6).append("000Z").toString();
         var4[0] = var7;
         var8 = var4[0];
      } else {
         var8 = null;
      }

      return var8;
   }

   private int getOoO() throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void outOfOfficeCb(long var1, int var3, int var4, Bundle var5) {
      try {
         IEmailServiceCallback var6 = SyncManager.callback();
         var6.oOOfStatus(var1, var3, var4, var5);
      } catch (RemoteException var13) {
         ;
      }
   }

   private void prepareSetCommand() {
      this.mIsExtUnKnown = (boolean)0;
      this.mIsExtKnown = (boolean)0;
      this.mIsInternal = (boolean)0;
      this.mIsGlobal = (boolean)0;
      this.mIsTimeBased = (boolean)0;
      int var1 = 0;

      while(true) {
         int var2 = this.mSvcData.getCount();
         if(var1 >= var2) {
            return;
         }

         OoOData var3 = this.mSvcData.getItem(var1);
         label47:
         switch(var3.state) {
         case 0:
            this.mIsExtUnKnown = (boolean)0;
            this.mIsExtKnown = (boolean)0;
            this.mIsInternal = (boolean)0;
            this.mIsGlobal = (boolean)0;
            this.mIsTimeBased = (boolean)0;
            break;
         case 1:
            this.mIsGlobal = (boolean)1;
            switch(var3.type) {
            case 4:
               this.mIsInternal = (boolean)1;
               if(var3.msg != null) {
                  String var4 = var3.msg;
                  this.mInternalMsg = var4;
               }
               break label47;
            case 5:
               this.mIsExtKnown = (boolean)1;
               if(var3.msg != null) {
                  String var5 = var3.msg;
                  this.mExternalKnownMsg = var5;
               }
               break label47;
            case 6:
               this.mIsExtUnKnown = (boolean)1;
               if(var3.msg != null) {
                  String var6 = var3.msg;
                  this.mExternalUnKnownMsg = var6;
               }
            default:
               break label47;
            }
         case 2:
            this.mIsTimeBased = (boolean)1;
            if(var3.start != null) {
               Date var7 = var3.start;
               this.mStartDate = var7;
            }

            if(var3.end != null) {
               Date var8 = var3.end;
               this.mEndDate = var8;
            }

            switch(var3.type) {
            case 4:
               this.mIsInternal = (boolean)1;
               if(var3.msg != null) {
                  String var9 = var3.msg;
                  this.mInternalMsg = var9;
               }
               break;
            case 5:
               this.mIsExtKnown = (boolean)1;
               if(var3.msg != null) {
                  String var10 = var3.msg;
                  this.mExternalKnownMsg = var10;
               }
               break;
            case 6:
               this.mIsExtUnKnown = (boolean)1;
               if(var3.msg != null) {
                  String var11 = var3.msg;
                  this.mExternalUnKnownMsg = var11;
               }
            }
         }

         ++var1;
      }
   }

   private int setOoO() throws IOException, MessagingException {
      long var1 = this.mAccount.mId;
      this.outOfOfficeCb(var1, 1, 0, (Bundle)null);
      Serializer var3 = new Serializer();
      this.prepareSetCommand();
      if(!this.mIsTimeBased && !this.mIsGlobal && !this.mIsInternal && !this.mIsExtKnown && !this.mIsExtUnKnown) {
         short var5 = 1157;
         Serializer var6 = var3.start(var5).start(1161).start(1160).start(1162);
         String var7 = Integer.toString(0);
         var6.text(var7).end().end().end().end().done();
      } else {
         short var55 = 1157;
         Serializer var56 = var3.start(var55).start(1161).start(1160);
         Serializer var79;
         if(this.mIsTimeBased) {
            short var58 = 1162;
            Serializer var59 = var56.start(var58);
            String var60 = Integer.toString(2);
            Serializer var61 = var59.text(var60).end();
            short var62 = 1163;
            Serializer var63 = var61.start(var62);
            long var64 = this.mStartDate.getTime();
            String var69 = this.convertLocalToUTC(var64);
            Serializer var70 = var63.text(var69).end();
            short var71 = 1164;
            Serializer var72 = var70.start(var71);
            long var73 = this.mEndDate.getTime();
            String var78 = this.convertLocalToUTC(var73);
            var79 = var72.text(var78).end();
         } else {
            short var99 = 1162;
            Serializer var100 = var56.start(var99);
            String var101 = Integer.toString(1);
            var79 = var100.text(var101).end();
         }

         Serializer var86;
         if(this.mIsInternal) {
            short var81 = 1165;
            Serializer var82 = var79.start(var81).start(1166).end().start(1169);
            String var83 = Integer.toString(1);
            Serializer var84 = var82.text(var83).end().start(1170);
            String var85 = this.mInternalMsg;
            var86 = var84.text(var85).end().start(1171).text("text").end().end();
         } else {
            short var103 = 1165;
            Serializer var104 = var79.start(var103).start(1166).end().start(1169);
            String var105 = Integer.toString(1);
            var86 = var104.text(var105).end().start(1170).end().start(1171).text("text").end().end();
         }

         Serializer var97;
         if(this.mIsExtKnown) {
            short var88 = 1165;
            Serializer var89 = var86.start(var88).start(1167).end().start(1169);
            String var90 = Integer.toString(1);
            Serializer var91 = var89.text(var90).end().start(1170);
            String var92 = this.mExternalKnownMsg;
            Serializer var93 = var91.text(var92).end().start(1171).text("text").end().end();
            short var94 = 1165;
            Serializer var95 = var93.start(var94).start(1168).end().start(1169);
            String var96 = Integer.toString(0);
            var97 = var95.text(var96).end().end();
         } else if(this.mIsExtUnKnown) {
            short var107 = 1165;
            Serializer var108 = var86.start(var107).start(1167).end().start(1169);
            String var109 = Integer.toString(1);
            Serializer var110 = var108.text(var109).end().start(1170);
            String var111 = this.mExternalUnKnownMsg;
            Serializer var112 = var110.text(var111).end().start(1171).text("text").end().end();
            short var113 = 1165;
            Serializer var114 = var112.start(var113).start(1168).end().start(1169);
            String var115 = Integer.toString(1);
            Serializer var116 = var114.text(var115).end().start(1170);
            String var117 = this.mExternalUnKnownMsg;
            var97 = var116.text(var117).end().start(1171).text("text").end().end();
         } else {
            short var119 = 1165;
            Serializer var120 = var86.start(var119).start(1167).end().start(1169);
            String var121 = Integer.toString(0);
            Serializer var122 = var120.text(var121).end().end();
            short var123 = 1165;
            Serializer var124 = var122.start(var123).start(1168).end().start(1169);
            String var125 = Integer.toString(0);
            var97 = var124.text(var125).end().end();
         }

         var3 = var97.end().end().end();
         var3.done();
      }

      HttpResponse var29;
      byte var53;
      try {
         if(Eas.PARSER_LOG) {
            String[] var8 = new String[]{"setOoO(): Wbxml:"};
            this.userLog(var8);
            byte[] var11 = var3.toByteArray();
            ByteArrayInputStream var12 = new ByteArrayInputStream(var11);
            LogAdapter var15 = new LogAdapter;
            EmailContent.Mailbox var16 = this.mMailbox;
            var15.<init>(var16, this);
            boolean var22 = var15.parse(var12);
         }

         byte[] var23 = var3.toByteArray();
         ByteArrayEntity var24 = new ByteArrayEntity(var23);
         String var26 = "Settings";
         int var28 = 120000;
         var29 = this.sendHttpClientPost(var26, var24, var28);
      } catch (Exception var145) {
         var53 = 0;
         long var127 = this.mAccount.mId;
         this.outOfOfficeCb(var127, -1, 100, (Bundle)null);
         return var53;
      }

      int var31 = var29.getStatusLine().getStatusCode();
      String var33 = "setOoO(): sendHttpClientPost HTTP response code: ";
      this.userLog(var33, var31);
      short var36 = 200;
      byte var52;
      if(var31 == var36) {
         HttpEntity var37 = var29.getEntity();
         if((int)var37.getContentLength() != 0) {
            InputStream var38 = var37.getContent();
            Bundle var39 = new Bundle();
            OoOCommandParser var40 = new OoOCommandParser;
            SettingsCommandAdapter var41 = new SettingsCommandAdapter;
            EmailContent.Mailbox var42 = this.mMailbox;
            var41.<init>(var42, this);
            var40.<init>(var38, var41);
            if(var40.parse()) {
               String var49 = OoOConstants.OOO_SET_DATA;
               var39.putBoolean(var49, (boolean)1);
               long var50 = this.mAccount.mId;
               this.outOfOfficeCb(var50, 0, 100, var39);
               var52 = 0;
            } else {
               String var130 = OoOConstants.OOO_SET_DATA;
               var39.putBoolean(var130, (boolean)0);
               if(this.isProvisionError(var31)) {
                  var52 = 23;
               } else {
                  var52 = -1;
               }

               long var133 = this.mAccount.mId;
               this.outOfOfficeCb(var133, var52, 100, var39);
            }
         } else {
            long var135 = this.mAccount.mId;
            this.outOfOfficeCb(var135, 0, 100, (Bundle)null);
            var52 = 0;
         }
      } else {
         var52 = 21;
         if(this.isProvisionError(var31)) {
            var52 = 23;
         } else if(this.isAuthError(var31)) {
            var52 = 22;
         }

         long var139 = this.mAccount.mId;
         this.outOfOfficeCb(var139, var52, 100, (Bundle)null);
      }

      var53 = var52;
      return var53;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }
}
