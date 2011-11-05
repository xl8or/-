package com.android.email.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.QuickContact;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.email.ContactInfoCache;
import com.android.email.Controller;
import com.android.email.activity.MessageCompose;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.android.email.combined.common.ExceptionUtil;
import com.android.email.mail.Address;
import com.digc.seven.SevenSyncProvider;

public class ContactPreviewPopup extends Activity implements OnClickListener {

   private static final int CC_RECIPIENT = 3;
   private static final String EXTRA_ACCOUNT_ID = "com.android.email.activity._ACCOUNT_ID";
   private static final int RECIPIENT = 2;
   private static final int SENDER = 1;
   private Address[] addrList;
   private Button btnCompose;
   private Button btnContact;
   private Context context;
   private ImageView image;
   private ImageView leftSpinImg;
   private Uri lookupUri;
   private String mAccountAddr;
   private long mAccountId = 0L;
   private float mButtonTextSize = 24.0F;
   private String mCc = "";
   private int mCclength = 0;
   private String mFrom = "";
   private int mIndex = 0;
   private long mMessageId = 0L;
   private String mTo = "";
   private int mTolength = 0;
   private int mTotalLen = 0;
   private int mType = 0;
   private TextView nametext;
   private boolean noMoreLeft;
   private boolean noMoreRight;
   private Address personEmail;
   private ImageView rightSpinImg;
   private TextView text2;
   private TextView text3;


   public ContactPreviewPopup() {}

   public void UpdateContactInfo(Address var1) {
      ContentResolver var2 = this.context.getContentResolver();
      String var3 = var1.getAddress();
      Uri var4 = Email.CONTENT_FILTER_URI;
      String var5 = Uri.encode(var3);
      Uri var6 = Uri.withAppendedPath(var4, var5);
      Uri var7 = Data.getContactLookupUri(var2, var6);
      this.lookupUri = var7;
      ContactInfoCache var8 = ContactInfoCache.getInstance();
      ContactInfoCache.init(this.context);
      ContactInfoCache var9 = ContactInfoCache.getInstance();
      String var10 = var1.getAddress();
      this.mAccountAddr = var10;
      String var11 = this.mAccountAddr;
      ContactInfoCache.CacheEntry var12 = var9.getContactInfoForEmailAddress(var11, (boolean)1);
      if(this.lookupUri != null) {
         TextView var13 = this.nametext;
         String var14 = var12.name;
         var13.setText(var14);
         TextView var15 = this.text2;
         String var16 = var1.getAddress();
         var15.setText(var16);
         TextView var17 = this.text3;
         String var18 = var12.phoneNumber;
         var17.setText(var18);
      } else {
         TextView var29 = this.nametext;
         String var30 = this.mAccountAddr;
         String var31 = var1.getPersonal();
         String var32;
         if(var30.equals(var31)) {
            var32 = "";
         } else {
            var32 = var1.getPersonal();
         }

         var29.setText(var32);
         TextView var33 = this.text2;
         String var34 = var1.getAddress();
         var33.setText(var34);
      }

      String var19 = this.mAccountAddr;
      ContactInfoCache.CacheEntry var20 = var9.getContactInfo(var19, (boolean)1);
      if(var20 != null && var20.mAvatar != null) {
         ImageView var21 = this.image;
         BitmapDrawable var22 = var20.mAvatar;
         var21.setImageDrawable(var22);
      } else {
         this.image.setImageResource(2130837803);
      }

      ImageView var23 = this.leftSpinImg;
      byte var24;
      if(this.noMoreLeft) {
         var24 = 8;
      } else {
         var24 = 0;
      }

      var23.setVisibility(var24);
      ImageView var25 = this.rightSpinImg;
      byte var26;
      if(this.noMoreRight) {
         var26 = 8;
      } else {
         var26 = 0;
      }

      var25.setVisibility(var26);
      if(this.lookupUri == null) {
         this.btnContact.setText(2131166546);
      } else {
         this.btnContact.setText(2131166547);
      }

      Button var27 = this.btnContact;
      float var28 = this.mButtonTextSize;
      var27.setTextSize(0, var28);
      this.btnContact.setOnClickListener(this);
   }

   public void onAddToContact(Address var1) {
      Intent var2 = new Intent("android.intent.action.INSERT_OR_EDIT");
      String var3 = var1.getAddress();
      var2.putExtra("email", var3);
      String var5 = var1.getPersonal();
      if(!TextUtils.isEmpty(var5)) {
         var2.putExtra("name", var5);
      }

      Intent var7 = var2.setType("vnd.android.cursor.item/raw_contact");
      this.startActivity(var2);
      this.setResult(-1);
      this.finish();
   }

   protected void onApplyThemeResource(Theme var1, int var2, boolean var3) {
      super.onApplyThemeResource(var1, var2, var3);
      var1.applyStyle(16973913, (boolean)1);
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131362458:
         if(this.noMoreLeft) {
            return;
         } else {
            if(this.mType == 1) {
               return;
            }

            int var2 = this.mIndex - 1;
            this.mIndex = var2;
            Address[] var3 = this.addrList;
            int var4 = this.mIndex;
            Address var5 = var3[var4];
            this.personEmail = var5;
            this.noMoreLeft = (boolean)0;
            this.noMoreRight = (boolean)0;
            if(this.mIndex == 0) {
               this.noMoreLeft = (boolean)1;
            }

            int var6 = this.mIndex;
            int var7 = this.mTotalLen - 1;
            if(var6 == var7) {
               this.noMoreRight = (boolean)1;
            }

            Address var8 = this.personEmail;
            this.UpdateContactInfo(var8);
            return;
         }
      case 2131362459:
      case 2131362462:
      case 2131362463:
      case 2131362464:
      default:
         return;
      case 2131362460:
         if(this.noMoreRight) {
            return;
         } else {
            if(this.mType == 1) {
               return;
            }

            int var9 = this.mIndex + 1;
            this.mIndex = var9;
            Address[] var10 = this.addrList;
            int var11 = this.mIndex;
            Address var12 = var10[var11];
            this.personEmail = var12;
            this.noMoreLeft = (boolean)0;
            this.noMoreRight = (boolean)0;
            if(this.mIndex == 0) {
               this.noMoreLeft = (boolean)1;
            }

            int var13 = this.mIndex;
            int var14 = this.mTotalLen - 1;
            if(var13 == var14) {
               this.noMoreRight = (boolean)1;
            }

            Address var15 = this.personEmail;
            this.UpdateContactInfo(var15);
            return;
         }
      case 2131362461:
         if(this.lookupUri == null) {
            return;
         }

         Context var37 = this.context;
         ImageView var38 = this.image;
         Uri var39 = this.lookupUri;
         QuickContact.showQuickContact(var37, var38, var39, 1, (String[])null);
         return;
      case 2131362465:
         String var30 = this.personEmail.getAddress();
         String var31 = this.personEmail.getPersonal();
         Address var32 = new Address(var30, var31);
         String var33 = this.context.getResources().getString(2131166547);
         CharSequence var34 = this.btnContact.getText();
         if(var33.equals(var34)) {
            Uri var35 = this.lookupUri;
            Intent var36 = new Intent("android.intent.action.VIEW", var35);
            this.startActivity(var36);
            this.setResult(-1);
         } else {
            this.onAddToContact(var32);
         }

         this.finish();
         return;
      case 2131362466:
         String[] var16 = new String[1];
         String var17 = this.personEmail.getAddress();
         var16[0] = var17;
         Controller var18 = Controller.getInstance(this.context);
         long var19 = this.mAccountId;
         long var21 = this.mMessageId;
         if(!var18.hasPremiumExpiredAccounts(var19, var21)) {
            Context var23 = this.context;
            Intent var24 = new Intent(var23, MessageCompose.class);
            long var25 = this.mAccountId;
            var24.putExtra("com.android.email.activity._ACCOUNT_ID", var25);
            var24.putExtra("android.intent.extra.EMAIL", var16);
            Intent var29 = var24.setAction("android.intent.action.SEND");
            this.startActivity(var24);
            this.finish();
         } else {
            ExceptionUtil.showDialogPremiumExpired(this);
         }
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      LayoutParams var2 = new LayoutParams();
      var2.flags = 2;
      var2.dimAmount = 0.75F;
      this.getWindow().setAttributes(var2);
      this.setContentView(2130903167);
      Context var3 = this.getBaseContext();
      this.context = var3;
      Intent var4 = this.getIntent();
      int var5 = var4.getIntExtra("type", -1);
      this.mType = var5;
      int var6 = var4.getIntExtra("index", -1);
      this.mIndex = var6;
      String var7 = var4.getStringExtra("from");
      this.mFrom = var7;
      String var8 = var4.getStringExtra("to");
      this.mTo = var8;
      String var9 = var4.getStringExtra("cc");
      this.mCc = var9;
      int var10 = var4.getIntExtra("to_len", -1);
      this.mTolength = var10;
      int var11 = var4.getIntExtra("cc_len", -1);
      this.mCclength = var11;
      long var12 = var4.getLongExtra("messageId", 65535L);
      this.mMessageId = var12;
      ImageView var14 = (ImageView)this.findViewById(2131362458);
      this.leftSpinImg = var14;
      ImageView var15 = (ImageView)this.findViewById(2131362460);
      this.rightSpinImg = var15;
      Address var16 = new Address("");
      this.personEmail = var16;
      this.noMoreLeft = (boolean)0;
      this.noMoreRight = (boolean)0;
      if(this.mType == 1) {
         Address var17 = Address.unpackFirst(this.mFrom);
         this.personEmail = var17;
      } else if(this.mType == 2) {
         Address[] var18 = Address.unpack(this.mTo);
         this.addrList = var18;
         Address[] var19 = this.addrList;
         int var20 = this.mIndex;
         Address var21 = var19[var20];
         this.personEmail = var21;
         int var22 = this.mTolength;
         this.mTotalLen = var22;
         if(this.mIndex == 0) {
            this.noMoreLeft = (boolean)1;
         }

         int var23 = this.mIndex;
         int var24 = this.mTotalLen - 1;
         if(var23 == var24) {
            this.noMoreRight = (boolean)1;
         }
      } else {
         Address[] var25 = Address.unpack(this.mCc);
         this.addrList = var25;
         Address[] var26 = this.addrList;
         int var27 = this.mIndex;
         Address var28 = var26[var27];
         this.personEmail = var28;
         int var29 = this.mCclength;
         this.mTotalLen = var29;
         if(this.mIndex == 0) {
            this.noMoreLeft = (boolean)1;
         }

         int var30 = this.mIndex;
         int var31 = this.mTotalLen - 1;
         if(var30 == var31) {
            this.noMoreRight = (boolean)1;
         }
      }

      if(this.personEmail != null) {
         ContentResolver var32 = this.context.getContentResolver();
         String var33 = this.personEmail.getAddress();
         Uri var34 = Email.CONTENT_FILTER_URI;
         String var35 = Uri.encode(var33);
         Uri var36 = Uri.withAppendedPath(var34, var35);
         Uri var37 = Data.getContactLookupUri(var32, var36);
         this.lookupUri = var37;
         TextView var38 = (TextView)this.findViewById(2131362459);
         TextView var39 = (TextView)this.findViewById(2131362462);
         this.nametext = var39;
         TextView var40 = (TextView)this.findViewById(2131362463);
         this.text2 = var40;
         TextView var41 = (TextView)this.findViewById(2131362464);
         this.text3 = var41;
         if(this.mType == 1) {
            var38.setText(2131166463);
         } else {
            var38.setText(2131166545);
         }

         if(this.mType == 1) {
            this.noMoreLeft = (boolean)1;
            this.noMoreRight = (boolean)1;
         }

         ImageView var42 = this.leftSpinImg;
         byte var43;
         if(this.noMoreLeft) {
            var43 = 8;
         } else {
            var43 = 0;
         }

         var42.setVisibility(var43);
         ImageView var44 = this.rightSpinImg;
         byte var45;
         if(this.noMoreRight) {
            var45 = 8;
         } else {
            var45 = 0;
         }

         var44.setVisibility(var45);
         Context var46 = this.context;
         ContactInfoCache var47 = new ContactInfoCache(var46);
         String var48 = this.personEmail.getAddress();
         this.mAccountAddr = var48;
         String var49 = this.mAccountAddr;
         ContactInfoCache.CacheEntry var50 = var47.getContactInfoForEmailAddress(var49, (boolean)1);
         if(this.lookupUri != null) {
            TextView var51 = this.nametext;
            String var52 = var50.name;
            var51.setText(var52);
            TextView var53 = this.text2;
            String var54 = this.personEmail.getAddress();
            var53.setText(var54);
            String var55 = this.personEmail.getAddress();
            String var56 = var47.queryContactInfoByEmail(var55);
            this.text3.setText(var56);
         } else {
            TextView var67 = this.nametext;
            String var68 = this.mAccountAddr;
            String var69 = this.personEmail.getPersonal();
            String var70;
            if(var68.equals(var69)) {
               var70 = "";
            } else {
               var70 = this.personEmail.getPersonal();
            }

            var67.setText(var70);
            TextView var71 = this.text2;
            String var72 = this.personEmail.getAddress();
            var71.setText(var72);
         }

         ImageView var57 = (ImageView)this.findViewById(2131362461);
         this.image = var57;
         if(var50 != null && var50.mAvatar != null) {
            ImageView var58 = this.image;
            BitmapDrawable var59 = var50.mAvatar;
            var58.setImageDrawable(var59);
         } else {
            this.image.setImageResource(2130837803);
         }

         this.image.setOnClickListener(this);
         if(this.lookupUri == null) {
            this.image.setSoundEffectsEnabled((boolean)0);
         } else {
            this.image.setSoundEffectsEnabled((boolean)1);
         }

         Button var60 = (Button)this.findViewById(2131362465);
         this.btnContact = var60;
         Button var61 = (Button)this.findViewById(2131362466);
         this.btnCompose = var61;
         if(this.lookupUri != null) {
            this.btnContact.setText(2131166547);
         } else {
            this.btnContact.setText(2131166546);
         }

         if(this.context != null) {
            float var62 = (float)this.context.getResources().getInteger(2131230839);
            this.mButtonTextSize = var62;
         }

         Button var63 = this.btnContact;
         float var64 = this.mButtonTextSize;
         var63.setTextSize(0, var64);
         this.btnCompose.setText(2131166335);
         Button var65 = this.btnCompose;
         float var66 = this.mButtonTextSize;
         var65.setTextSize(0, var66);
         this.leftSpinImg.setOnClickListener(this);
         this.rightSpinImg.setOnClickListener(this);
         this.btnContact.setOnClickListener(this);
         this.btnCompose.setOnClickListener(this);
      }
   }

   protected void onRestoreInstanceState(Bundle var1) {
      super.onRestoreInstanceState(var1);
      if(AccountSetupCustomer.getInstance().getEmailType() == 1) {
         if(SevenSyncProvider.checkSevenApkVer(this)) {
            ;
         }
      }
   }

   public boolean onSearchRequested() {
      return false;
   }
}
