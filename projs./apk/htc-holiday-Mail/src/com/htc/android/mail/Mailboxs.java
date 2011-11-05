package com.htc.android.mail;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.Mailbox;
import com.htc.android.mail.ll;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Mailboxs {

   private static final String TAG = "Mailboxs";
   Mailbox[] mCombinedMailboxs;
   String[] mDecodeNames;
   Mailbox mDefaultMailbox;
   Mailbox mDraftMailbox;
   Boolean mIsExchg;
   ArrayList<Mailbox> mMailboxList;
   HashMap<Long, Mailbox> mMailboxMap;
   Mailbox mOutMailbox;
   Mailbox mSentMailbox;
   String[] mShortNames;
   Mailbox mTrashMailbox;


   public Mailboxs() {
      ArrayList var1 = new ArrayList();
      this.mMailboxList = var1;
      HashMap var2 = new HashMap();
      this.mMailboxMap = var2;
      Boolean var3 = Boolean.valueOf((boolean)0);
      this.mIsExchg = var3;
      this.mCombinedMailboxs = null;
   }

   public Mailboxs(Context var1, Cursor var2, boolean var3, long var4, long var6, long var8, long var10, long var12, long var14) {
      ArrayList var16 = new ArrayList();
      this.mMailboxList = var16;
      HashMap var17 = new HashMap();
      this.mMailboxMap = var17;
      Boolean var18 = Boolean.valueOf((boolean)0);
      this.mIsExchg = var18;
      this.mCombinedMailboxs = null;
      Boolean var19 = Boolean.valueOf(var3);
      this.mIsExchg = var19;
      if(var3) {
         this.initEAS(var1, var2, var4, var6, var8, var10, var12, var14);
      } else {
         this.init(var1, var2, var4, var6, var8, var10, var12, var14);
      }
   }

   public Mailboxs(ArrayList<Mailbox> var1) {
      ArrayList var2 = new ArrayList();
      this.mMailboxList = var2;
      HashMap var3 = new HashMap();
      this.mMailboxMap = var3;
      Boolean var4 = Boolean.valueOf((boolean)0);
      this.mIsExchg = var4;
      this.mCombinedMailboxs = null;
      this.mMailboxList = var1;
      this.mShortNames = null;
      this.mDecodeNames = null;
      int var5 = this.mMailboxList.size();
      String[] var6 = new String[var5];
      this.mShortNames = var6;
      String[] var7 = new String[var5];
      this.mDecodeNames = var7;

      for(int var8 = 0; var8 < var5; ++var8) {
         String[] var9 = this.mShortNames;
         String var10 = ((Mailbox)this.mMailboxList.get(var8)).shortName;
         var9[var8] = var10;
         String[] var11 = this.mDecodeNames;
         String var12 = ((Mailbox)this.mMailboxList.get(var8)).decodeName;
         var11[var8] = var12;
         String var13 = this.mDecodeNames[var8];
         if("INBOX".equals(var13)) {
            String[] var14 = this.mDecodeNames;
            String var15 = this.mShortNames[var8];
            var14[var8] = var15;
         }
      }

   }

   private void dump() {
      ll.i("Mailboxs", "-- dump accounts --");
      ContentResolver var1 = MailProvider.sTheOne.getContext().getContentResolver();
      Uri var2 = MailProvider.sAccountsURI;
      Cursor var3 = var1.query(var2, (String[])null, (String)null, (String[])null, (String)null);
      if(var3 != null) {
         while(var3.moveToNext()) {
            int var4 = var3.getColumnIndexOrThrow("_id");
            long var5 = var3.getLong(var4);
            int var7 = var3.getColumnIndexOrThrow("_emailaddress");
            String var8 = var3.getString(var7);
            int var9 = var3.getColumnIndexOrThrow("_protocol");
            int var10 = var3.getInt(var9);
            int var11 = var3.getColumnIndexOrThrow("_defaultfolderId");
            long var12 = var3.getLong(var11);
            int var14 = var3.getColumnIndexOrThrow("_trashfolderId");
            long var15 = var3.getLong(var14);
            int var17 = var3.getColumnIndexOrThrow("_sentfolderId");
            long var18 = var3.getLong(var17);
            int var20 = var3.getColumnIndexOrThrow("_draftfolderId");
            long var21 = var3.getLong(var20);
            int var23 = var3.getColumnIndexOrThrow("_outfolderId");
            long var24 = var3.getLong(var23);
            StringBuilder var26 = (new StringBuilder()).append("id: ");
            StringBuilder var29 = var26.append(var5).append(", emailaddress: ");
            StringBuilder var31 = var29.append(var8).append(", protocol");
            StringBuilder var33 = var31.append(var10).append(", defaultfolderId: ");
            StringBuilder var36 = var33.append(var12).append(", trashfolderId: ");
            StringBuilder var39 = var36.append(var15).append(", sentfolderId:");
            StringBuilder var42 = var39.append(var18).append(", draftfolderId:");
            StringBuilder var45 = var42.append(var21).append(", outfolderId:");
            String var48 = var45.append(var24).toString();
            ll.i("Mailboxs", var48);
         }

         var3.close();
      }

      ll.i("Mailboxs", "-- dump mailboxs --");
      ContentResolver var49 = MailProvider.sTheOne.getContext().getContentResolver();
      Uri var50 = MailProvider.sMailBoxURI;
      Cursor var51 = var49.query(var50, (String[])null, (String)null, (String[])null, (String)null);
      if(var51 != null) {
         while(var51.moveToNext()) {
            int var52 = var51.getColumnIndexOrThrow("_id");
            long var53 = var51.getLong(var52);
            int var55 = var51.getColumnIndexOrThrow("_decodename");
            String var56 = var51.getString(var55);
            int var57 = var51.getColumnIndexOrThrow("_defaultfolder");
            long var58 = var51.getLong(var57);
            int var60 = var51.getColumnIndexOrThrow("_account");
            long var61 = var51.getLong(var60);
            int var63 = var51.getColumnIndexOrThrow("_type");
            long var64 = var51.getLong(var63);
            int var66 = var51.getColumnIndexOrThrow("_serverid");
            String var67 = var51.getString(var66);
            StringBuilder var68 = (new StringBuilder()).append("id: ");
            StringBuilder var71 = var68.append(var53).append(", decodename: ").append(var56).append(", defaultfolder: ").append(var58).append(", accountId: ").append(var61).append(", type: ");
            StringBuilder var74 = var71.append(var64).append(", serverid: ");
            String var76 = var74.append(var67).toString();
            ll.i("Mailboxs", var76);
         }

         var51.close();
      }
   }

   public static Mailboxs getCombinedMailboxs(Context var0) {
      Mailboxs var1 = new Mailboxs();
      int var3 = 2131362253;
      String var4 = var0.getString(var3);
      int var6 = 2131362253;
      String var7 = var0.getString(var6);
      int var9 = 2131362253;
      String var10 = var0.getString(var9);
      Mailbox var11 = new Mailbox(Long.MAX_VALUE, Long.MAX_VALUE, var4, var7, var10, 1, (String)null, (String)null, (String)null, 1, 1);
      var1.mDefaultMailbox = var11;
      var1.mDefaultMailbox.kind = Integer.MAX_VALUE;
      ArrayList var12 = var1.mMailboxList;
      Mailbox var13 = var1.mDefaultMailbox;
      var12.add(var13);
      HashMap var15 = var1.mMailboxMap;
      Long var16 = Long.valueOf(var1.mDefaultMailbox.id);
      Mailbox var17 = var1.mDefaultMailbox;
      var15.put(var16, var17);
      int var20 = 2131362259;
      String var21 = var0.getString(var20);
      int var23 = 2131362259;
      String var24 = var0.getString(var23);
      int var26 = 2131362259;
      String var27 = var0.getString(var26);
      Mailbox var28 = new Mailbox(Long.MAX_VALUE, 9223372036854775802L, var21, var24, var27, 1, (String)null, (String)null, (String)null, 1, 1);
      var28.kind = 2147483642;
      var1.mMailboxList.add(var28);
      HashMap var30 = var1.mMailboxMap;
      Long var31 = Long.valueOf(var28.id);
      var30.put(var31, var28);
      int var34 = 2131362254;
      String var35 = var0.getString(var34);
      int var37 = 2131362254;
      String var38 = var0.getString(var37);
      int var40 = 2131362254;
      String var41 = var0.getString(var40);
      Mailbox var42 = new Mailbox(Long.MAX_VALUE, 9223372036854775804L, var35, var38, var41, 1, (String)null, (String)null, (String)null, 1, 0);
      var1.mDraftMailbox = var42;
      var1.mDraftMailbox.kind = 2147483644;
      ArrayList var43 = var1.mMailboxList;
      Mailbox var44 = var1.mDraftMailbox;
      var43.add(var44);
      HashMap var46 = var1.mMailboxMap;
      Long var47 = Long.valueOf(var1.mDraftMailbox.id);
      Mailbox var48 = var1.mDraftMailbox;
      var46.put(var47, var48);
      int var51 = 2131362255;
      String var52 = var0.getString(var51);
      int var54 = 2131362255;
      String var55 = var0.getString(var54);
      int var57 = 2131362255;
      String var58 = var0.getString(var57);
      Mailbox var59 = new Mailbox(Long.MAX_VALUE, 9223372036854775803L, var52, var55, var58, 1, (String)null, (String)null, (String)null, 1, 0);
      var1.mOutMailbox = var59;
      var1.mOutMailbox.kind = 2147483643;
      ArrayList var60 = var1.mMailboxList;
      Mailbox var61 = var1.mOutMailbox;
      var60.add(var61);
      HashMap var63 = var1.mMailboxMap;
      Long var64 = Long.valueOf(var1.mOutMailbox.id);
      Mailbox var65 = var1.mOutMailbox;
      var63.put(var64, var65);
      int var68 = 2131362256;
      String var69 = var0.getString(var68);
      int var71 = 2131362256;
      String var72 = var0.getString(var71);
      int var74 = 2131362256;
      String var75 = var0.getString(var74);
      Mailbox var76 = new Mailbox(Long.MAX_VALUE, 9223372036854775805L, var69, var72, var75, 1, (String)null, (String)null, (String)null, 1, 0);
      var1.mSentMailbox = var76;
      var1.mSentMailbox.kind = 2147483645;
      ArrayList var77 = var1.mMailboxList;
      Mailbox var78 = var1.mSentMailbox;
      var77.add(var78);
      HashMap var80 = var1.mMailboxMap;
      Long var81 = Long.valueOf(var1.mSentMailbox.id);
      Mailbox var82 = var1.mSentMailbox;
      var80.put(var81, var82);
      int var85 = 2131362257;
      String var86 = var0.getString(var85);
      int var88 = 2131362257;
      String var89 = var0.getString(var88);
      int var91 = 2131362257;
      String var92 = var0.getString(var91);
      Mailbox var93 = new Mailbox(Long.MAX_VALUE, 9223372036854775806L, var86, var89, var92, 1, (String)null, (String)null, (String)null, 1, 1);
      var1.mTrashMailbox = var93;
      Mailbox var94 = var1.mTrashMailbox;
      int var95 = 2147483646;
      var94.kind = var95;
      ArrayList var96 = var1.mMailboxList;
      Mailbox var97 = var1.mTrashMailbox;
      boolean var100 = var96.add(var97);
      HashMap var101 = var1.mMailboxMap;
      Long var102 = Long.valueOf(var1.mTrashMailbox.id);
      Mailbox var103 = var1.mTrashMailbox;
      Object var107 = var101.put(var102, var103);
      int var108 = var1.mMailboxList.size();
      String[] var109 = new String[var108];
      String[] var110 = new String[var108];

      for(int var111 = 0; var111 < var108; ++var111) {
         String var112 = ((Mailbox)var1.mMailboxList.get(var111)).shortName;
         var109[var111] = var112;
         String var113 = ((Mailbox)var1.mMailboxList.get(var111)).decodeName;
         var110[var111] = var113;
      }

      var1.mShortNames = var109;
      var1.mDecodeNames = var110;
      return var1;
   }

   private void init(Context var1, Cursor var2, long var3, long var5, long var7, long var9, long var11, long var13) {
      ArrayList var15 = new ArrayList();
      ArrayList var16 = new ArrayList();

      while(var2.moveToNext()) {
         String var18 = "_account";
         int var19 = var2.getColumnIndexOrThrow(var18);
         if(var2.getLong(var19) != var3) {
            boolean var22 = var2.moveToPrevious();
            break;
         }

         String var45 = "_id";
         int var46 = var2.getColumnIndexOrThrow(var45);
         long var49 = var2.getLong(var46);
         String var52 = "_undecodename";
         int var53 = var2.getColumnIndexOrThrow(var52);
         String var56 = var2.getString(var53);
         String var58 = "_decodename";
         int var59 = var2.getColumnIndexOrThrow(var58);
         String var62 = var2.getString(var59);
         String var64 = "_shortname";
         int var65 = var2.getColumnIndexOrThrow(var64);
         String var68 = var2.getString(var65);
         String var70 = "_serverfolder";
         int var71 = var2.getColumnIndexOrThrow(var70);
         int var74 = var2.getInt(var71);
         String var76 = "_movegroup";
         int var77 = var2.getColumnIndexOrThrow(var76);
         int var80 = var2.getInt(var77);
         String var82 = "_showsender";
         int var83 = var2.getColumnIndexOrThrow(var82);
         int var86 = var2.getInt(var83);
         String var88 = "_default_sync";
         int var89 = var2.getColumnIndexOrThrow(var88);
         byte var92;
         if(var2.getInt(var89) == 1) {
            var92 = 1;
         } else {
            var92 = 0;
         }

         Mailbox var95 = new Mailbox(var3, var49, var56, var62, var68, var74, (String)null, (String)null, (String)null, var80, var86);
         boolean var98 = var15.add(var95);
         HashMap var99 = this.mMailboxMap;
         Long var100 = Long.valueOf(var49);
         var99.put(var100, var95);
         if(var92 != 0) {
            var95.setDefaultSyncEnabled((boolean)var92);
            boolean var106 = var16.add(var95);
         }

         if(var5 == var49) {
            this.mDefaultMailbox = var95;
            var95.kind = Integer.MAX_VALUE;
         }

         if(var7 == var49) {
            this.mTrashMailbox = var95;
            var95.kind = 2147483646;
         }

         if(var9 == var49) {
            this.mSentMailbox = var95;
            var95.kind = 2147483645;
         }

         if(var11 == var49) {
            this.mDraftMailbox = var95;
            var95.kind = 2147483644;
         }

         if(var13 == var49) {
            this.mOutMailbox = var95;
            var95.kind = 2147483643;
         }
      }

      this.initCombinedMailbox(var1, var3, var15, var16);
      Object var27 = null;
      this.mShortNames = (String[])var27;
      Object var28 = null;
      this.mDecodeNames = (String[])var28;
      int var29 = this.mMailboxList.size();
      String[] var30 = new String[var29];
      this.mShortNames = var30;
      String[] var31 = new String[var29];
      this.mDecodeNames = var31;

      for(int var32 = 0; var32 < var29; ++var32) {
         String[] var35 = this.mShortNames;
         ArrayList var36 = this.mMailboxList;
         String var38 = ((Mailbox)var36.get(var32)).shortName;
         var35[var32] = var38;
         String[] var39 = this.mDecodeNames;
         ArrayList var40 = this.mMailboxList;
         String var42 = ((Mailbox)var40.get(var32)).decodeName;
         var39[var32] = var42;
         String var43 = this.mDecodeNames[var32];
         if("INBOX".equals(var43)) {
            this.mDecodeNames[var32] = "Inbox";
         }
      }

   }

   private void initCombinedMailbox(Context var1, long var2, ArrayList<Mailbox> var4, ArrayList<Mailbox> var5) {
      Mailbox[] var6 = new Mailbox[2];
      this.mCombinedMailboxs = var6;
      int var8 = 2131362259;
      String var9 = var1.getString(var8);
      int var11 = 2131362259;
      String var12 = var1.getString(var11);
      int var14 = 2131362259;
      String var15 = var1.getString(var14);
      Mailbox var18 = new Mailbox(var2, 9223372036854775802L, var9, var12, var15, 1, (String)null, (String)null, (String)null, 1, 1);
      var18.kind = 2147483642;
      this.mMailboxList.add(var18);
      HashMap var20 = this.mMailboxMap;
      Long var21 = Long.valueOf(var18.id);
      var20.put(var21, var18);
      if(var5.size() != 0) {
         Iterator var23 = var5.iterator();

         while(var23.hasNext()) {
            Mailbox var24 = (Mailbox)var23.next();
            if(var24 != null) {
               long var25 = var24.id;
               var18.addMailboxId(var25);
            }
         }
      } else if(this.mDefaultMailbox != null) {
         long var27 = this.mDefaultMailbox.id;
         var18.addMailboxId(var27);
      }

      this.mCombinedMailboxs[0] = var18;
      if(this.mSentMailbox == null) {
         this.dump();
      }

      String var43;
      if(this.mIsExchg.booleanValue()) {
         StringBuilder var29 = new StringBuilder();
         Mailbox var30 = this.mDefaultMailbox;
         boolean var31 = this.mIsExchg.booleanValue();
         String var35 = var30.getShortFolderName(var1, var31);
         StringBuilder var36 = var29.append(var35).append(" + ");
         Mailbox var37 = this.mSentMailbox;
         boolean var38 = this.mIsExchg.booleanValue();
         String var42 = var37.getShortFolderName(var1, var38);
         var43 = var36.append(var42).toString();
      } else {
         int var58 = 2131362260;
         var43 = var1.getString(var58);
      }

      Mailbox var48 = new Mailbox(var2, 9223372036854775801L, var43, var43, var43, 1, (String)null, (String)null, (String)null, 1, 1);
      var48.kind = 2147483642;
      this.mMailboxList.add(var48);
      HashMap var50 = this.mMailboxMap;
      Long var51 = Long.valueOf(var48.id);
      var50.put(var51, var48);
      long var53 = this.mDefaultMailbox.id;
      var48.addMailboxId(var53);
      long var55 = this.mSentMailbox.id;
      var48.addMailboxId(var55);
      this.mCombinedMailboxs[1] = var48;
      if(var4.size() != 0) {
         ArrayList var59 = this.mMailboxList;
         int var60 = this.mMailboxList.size();
         boolean var64 = var59.addAll(var60, var4);
      }
   }

   private void initEAS(Context var1, Cursor var2, long var3, long var5, long var7, long var9, long var11, long var13) {
      if(var2 == null) {
         ll.e("Mailboxs", "initEAS, cursor is null");
      } else {
         int var15 = 0;
         boolean var16 = false;
         ArrayList var17 = new ArrayList();
         ArrayList var18 = new ArrayList();

         while(var2.moveToNext()) {
            String var20 = "_account";
            int var21 = var2.getColumnIndexOrThrow(var20);
            if(var2.getLong(var21) != var3) {
               boolean var24 = var2.moveToPrevious();
               break;
            }

            ++var15;
            String var61 = "_id";
            int var62 = var2.getColumnIndexOrThrow(var61);
            long var65 = var2.getLong(var62);
            String var68 = "_undecodename";
            int var69 = var2.getColumnIndexOrThrow(var68);
            String var72 = var2.getString(var69);
            String var74 = "_decodename";
            int var75 = var2.getColumnIndexOrThrow(var74);
            String var78 = var2.getString(var75);
            String var80 = "_shortname";
            int var81 = var2.getColumnIndexOrThrow(var80);
            String var84 = var2.getString(var81);
            String var86 = "_serverfolder";
            int var87 = var2.getColumnIndexOrThrow(var86);
            int var90 = var2.getInt(var87);
            String var92 = "_type";
            int var93 = var2.getColumnIndexOrThrow(var92);
            String var96 = var2.getString(var93);
            String var98 = "_serverid";
            int var99 = var2.getColumnIndexOrThrow(var98);
            String var102 = var2.getString(var99);
            String var104 = "_parentid";
            int var105 = var2.getColumnIndexOrThrow(var104);
            String var108 = var2.getString(var105);
            String var110 = "_movegroup";
            int var111 = var2.getColumnIndexOrThrow(var110);
            int var114 = var2.getInt(var111);
            String var116 = "_showsender";
            int var117 = var2.getColumnIndexOrThrow(var116);
            int var120 = var2.getInt(var117);
            String var122 = "_default_sync";
            int var123 = var2.getColumnIndexOrThrow(var122);
            byte var126;
            if(var2.getInt(var123) == 1) {
               var126 = 1;
            } else {
               var126 = 0;
            }

            Mailbox var129 = new Mailbox(var3, var65, var72, var78, var84, var90, var96, var102, var108, var114, var120);
            boolean var132 = var17.add(var129);
            HashMap var133 = this.mMailboxMap;
            Long var134 = Long.valueOf(var65);
            var133.put(var134, var129);
            if(var126 != 0) {
               var129.setDefaultSyncEnabled((boolean)var126);
               boolean var140 = var18.add(var129);
            }

            if(var5 == var65) {
               this.mDefaultMailbox = var129;
               var129.kind = Integer.MAX_VALUE;
            }

            if(var7 == var65) {
               this.mTrashMailbox = var129;
               var129.kind = 2147483646;
            }

            if(var9 == var65) {
               this.mSentMailbox = var129;
               var129.kind = 2147483645;
            }

            if(var11 == var65) {
               this.mDraftMailbox = var129;
               var129.kind = 2147483644;
            }

            if(var13 == var65) {
               this.mOutMailbox = var129;
               var129.kind = 2147483643;
            }
         }

         if(this.mDefaultMailbox == null) {
            var16 = true;
            ll.e("Mailboxs", "initEAS: defaultMailbox is null");
         }

         if(this.mTrashMailbox == null) {
            var16 = true;
            ll.e("Mailboxs", "initEAS: trashMailbox is null");
         }

         if(this.mSentMailbox == null) {
            var16 = true;
            ll.e("Mailboxs", "initEAS: SentMailbox is null");
         }

         if(this.mDraftMailbox == null) {
            var16 = true;
            ll.e("Mailboxs", "initEAS: DraftMailbox is null");
         }

         if(this.mOutMailbox == null) {
            var16 = true;
            ll.e("Mailboxs", "initEAS: OutMailbox is null");
         }

         if(var16) {
            StringBuilder var25 = new StringBuilder();
            StringBuilder var27 = var25.append(var15).append(",");
            StringBuilder var30 = var27.append(var5).append(",");
            StringBuilder var33 = var30.append(var7).append(",");
            StringBuilder var36 = var33.append(var9).append(",");
            StringBuilder var39 = var36.append(var11).append(",");
            String var42 = var39.append(var13).toString();
            StringBuilder var43 = (new StringBuilder()).append("mailbox error: ");
            String var45 = var43.append(var42).toString();
            ll.e("Mailboxs", var45);
         }

         this.initCombinedMailbox(var1, var3, var17, var18);
         Object var50 = null;
         this.mShortNames = (String[])var50;
         int var51 = this.mMailboxList.size();
         String[] var52 = new String[var51];
         this.mShortNames = var52;

         for(int var53 = 0; var53 < var51; ++var53) {
            String[] var56 = this.mShortNames;
            ArrayList var57 = this.mMailboxList;
            String var59 = ((Mailbox)var57.get(var53)).shortName;
            var56[var53] = var59;
         }

         String[] var146 = new String[var51];
         this.mDecodeNames = var146;

         for(int var147 = 0; var147 < var51; ++var147) {
            String[] var150 = this.mDecodeNames;
            ArrayList var151 = this.mMailboxList;
            String var153 = ((Mailbox)var151.get(var147)).decodeName;
            var150[var147] = var153;
         }

      }
   }

   public Mailbox[] getCombinedMailboxs() {
      return this.mCombinedMailboxs;
   }

   public String[] getDecodeNames(Context var1) {
      String[] var2;
      if(this.mIsExchg.booleanValue()) {
         var2 = this.mDecodeNames;
      } else {
         int var3 = this.mMailboxList.size();
         String[] var4 = new String[var3];

         for(int var5 = 0; var5 < var3; ++var5) {
            Mailbox var6 = (Mailbox)this.mMailboxList.get(var5);
            boolean var7 = this.mIsExchg.booleanValue();
            String var8 = var6.getDecodeName(var1, var7);
            var4[var5] = var8;
         }

         var2 = var4;
      }

      return var2;
   }

   public Mailbox getDefaultMailbox() {
      if(this.mDefaultMailbox == null) {
         this.dump();
      }

      return this.mDefaultMailbox;
   }

   public String getDefaultMailboxUnDecodeName() {
      String var1;
      if(this.mDefaultMailbox != null) {
         var1 = this.mDefaultMailbox.unDecodeName;
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean[] getDefaultSyncEnabled() {
      int var1 = this.mMailboxList.size();
      boolean[] var2 = new boolean[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         boolean var4 = ((Mailbox)this.mMailboxList.get(var3)).getDefaultSyncEnabled();
         var2[var3] = var4;
      }

      return var2;
   }

   public Mailbox getDraftMailbox() {
      if(this.mDraftMailbox == null) {
         this.dump();
      }

      return this.mDraftMailbox;
   }

   public String getDraftMailboxUnDecodeName() {
      String var1;
      if(this.mDraftMailbox != null) {
         var1 = this.mDraftMailbox.unDecodeName;
      } else {
         var1 = null;
      }

      return var1;
   }

   public Mailbox getMailbox(int var1) {
      return (Mailbox)this.mMailboxList.get(var1);
   }

   public Mailbox getMailboxById(long var1) {
      HashMap var3 = this.mMailboxMap;
      Long var4 = Long.valueOf(var1);
      return (Mailbox)var3.get(var4);
   }

   public Mailbox getMailboxByKind(int var1) {
      Mailbox var2;
      if(var1 == Integer.MAX_VALUE) {
         var2 = this.mDefaultMailbox;
      } else if(var1 == 2147483646) {
         var2 = this.mTrashMailbox;
      } else if(var1 == 2147483645) {
         var2 = this.mSentMailbox;
      } else if(var1 == 2147483644) {
         var2 = this.mDraftMailbox;
      } else if(var1 == 2147483643) {
         var2 = this.mOutMailbox;
      } else {
         var2 = null;
      }

      return var2;
   }

   public Mailboxs getMailboxsForChange(Mailbox var1) {
      ArrayList var2 = new ArrayList();
      int var3 = this.mMailboxList.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         Mailbox var5 = (Mailbox)this.mMailboxList.get(var4);
         long var6 = var5.id;
         long var8 = var1.id;
         if(var6 != var8) {
            var2.add(var5);
         }
      }

      Mailboxs var11 = new Mailboxs(var2);
      Boolean var12 = this.mIsExchg;
      var11.mIsExchg = var12;
      return var11;
   }

   public Mailboxs getMailboxsForMove(Mailbox var1) {
      ArrayList var2 = new ArrayList();
      int var3 = this.mMailboxList.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         Mailbox var5 = (Mailbox)this.mMailboxList.get(var4);
         long var6 = var5.id;
         long var8 = var1.id;
         if(var6 != var8) {
            long var10 = var5.id;
            long var12 = this.mOutMailbox.id;
            if(var10 != var12) {
               long var14 = var5.id;
               long var16 = this.mDraftMailbox.id;
               if(var14 != var16 && var5.kind != 2147483642) {
                  var2.add(var5);
               }
            }
         }
      }

      Mailboxs var19 = new Mailboxs(var2);
      Boolean var20 = this.mIsExchg;
      var19.mIsExchg = var20;
      return var19;
   }

   public Mailboxs getMailboxsForSelectDefaultSync() {
      ArrayList var1 = new ArrayList();
      int var2 = this.mMailboxList.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         Mailbox var4 = (Mailbox)this.mMailboxList.get(var3);
         long var5 = var4.id;
         long var7 = this.mOutMailbox.id;
         if(var5 != var7) {
            long var9 = var4.id;
            long var11 = this.mDraftMailbox.id;
            if(var9 != var11 && var4.kind != 2147483642) {
               var1.add(var4);
            }
         }
      }

      Mailboxs var14 = new Mailboxs(var1);
      Boolean var15 = this.mIsExchg;
      var14.mIsExchg = var15;
      return var14;
   }

   public Mailbox getOutMailbox() {
      if(this.mOutMailbox == null) {
         this.dump();
      }

      return this.mOutMailbox;
   }

   public String getOutMailboxUnDecodeName() {
      String var1;
      if(this.mOutMailbox != null) {
         var1 = this.mOutMailbox.unDecodeName;
      } else {
         var1 = null;
      }

      return var1;
   }

   public Mailbox getSentMailbox() {
      if(this.mSentMailbox == null) {
         this.dump();
      }

      return this.mSentMailbox;
   }

   public String getSentMailboxUnDecodeName() {
      String var1;
      if(this.mSentMailbox != null) {
         var1 = this.mSentMailbox.unDecodeName;
      } else {
         var1 = null;
      }

      return var1;
   }

   public String[] getShortNames() {
      return this.mShortNames;
   }

   public Mailbox getTrashMailbox() {
      if(this.mTrashMailbox == null) {
         this.dump();
      }

      return this.mTrashMailbox;
   }

   public String getTrashMailboxUnDecodeName() {
      String var1;
      if(this.mTrashMailbox != null) {
         var1 = this.mTrashMailbox.unDecodeName;
      } else {
         var1 = null;
      }

      return var1;
   }
}
