package com.android.email.activity;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.android.email.activity.MessageList;
import com.android.email.mail.Store;
import com.android.email.provider.EmailContent;

public class MessageListItem extends LinearLayout {

   public boolean isSms;
   public long mAccountId;
   private MessageList.MessageListAdapter mAdapter;
   private boolean mAllowBatch;
   private boolean mCachedViewPositions;
   private int mCheckboxLeft;
   private int mCheckboxRight;
   private long mCheckedAccountId = 65535L;
   private Context mContext = null;
   public String mConvId;
   public int mConvThreadId;
   public boolean mDelete;
   private boolean mDownEvent;
   public boolean mFFlagComplete;
   public boolean mFFlagSet;
   public boolean mFavorite;
   public int mFlagComFFConv;
   public int mFlagReadConv;
   public int mFlagSetFFConv;
   private boolean mIsEAS = 0;
   public int mListPosition;
   public long mMailboxId;
   public long mMessageId;
   public boolean mMove;
   public boolean mRead;
   public boolean mReply;
   private int mStarLeft;
   private int mStarRight;
   public Long mThreadId;
   public String mThreadName;
   public int mTypeMsg;
   private ToneGenerator toneGenerator;


   public MessageListItem(Context var1) {
      super(var1);
      this.mContext = var1;
   }

   public MessageListItem(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
   }

   private void makeTone(int var1, int var2, int var3, int var4) {
      if(this.mContext != null) {
         AudioManager var5 = (AudioManager)this.mContext.getSystemService("audio");

         try {
            ToneGenerator var6 = new ToneGenerator(var2, var3);
            this.toneGenerator = var6;
            int var7 = var5.getRingerMode();
            if(var7 != 0) {
               if(var7 != 1) {
                  this.toneGenerator.startTone(var1);
                  SystemClock.sleep((long)var4);
                  this.toneGenerator.stopTone();
               }
            }
         } catch (RuntimeException var10) {
            this.toneGenerator = null;
         } catch (Exception var11) {
            var11.printStackTrace();
            this.toneGenerator = null;
         }
      }
   }

   public boolean IsEAS() {
      Context var1 = this.getContext();
      boolean var7;
      if(this.mCheckedAccountId == 65535L) {
         long var2 = this.mAccountId;
         this.mCheckedAccountId = var2;
      } else {
         long var8 = this.mCheckedAccountId;
         long var10 = this.mAccountId;
         if(var8 == var10) {
            var7 = this.mIsEAS;
            return var7;
         }

         long var12 = this.mAccountId;
         this.mCheckedAccountId = var12;
      }

      long var4 = this.mAccountId;
      EmailContent.Account var6 = EmailContent.Account.restoreAccountWithId(var1, var4);
      if(var6 == null) {
         this.mIsEAS = (boolean)0;
         var7 = this.mIsEAS;
      } else {
         Store.StoreInfo var14 = Store.StoreInfo.getStoreInfo(var6.getStoreUri(var1), var1);
         if(var14 == null) {
            this.mIsEAS = (boolean)0;
            var7 = this.mIsEAS;
         } else {
            String var15 = var14.mScheme;
            boolean var16 = "eas".equals(var15);
            this.mIsEAS = var16;
            var7 = this.mIsEAS;
         }
      }

      return var7;
   }

   public void bindViewInit(MessageList.MessageListAdapter var1, boolean var2) {
      this.mAdapter = var1;
      this.mAllowBatch = var2;
      this.mCachedViewPositions = (boolean)0;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      byte var2 = 0;
      int var3 = (int)var1.getX();
      int[] var4 = new int[2];
      byte var5;
      if(this.mMessageId == 65534L) {
         var5 = super.onTouchEvent(var1);
      } else {
         if(!this.mCachedViewPositions) {
            int var6 = ((CheckBox)this.findViewById(2131362169)).getLeft();
            this.mCheckboxLeft = var6;
            int var7 = this.getRight();
            this.mCheckboxRight = var7;
            if(!this.IsEAS()) {
               int var8 = this.getWidth();
               int var9 = this.findViewById(2131362157).getWidth();
               int var10 = var8 - var9 - 20;
               this.mStarLeft = var10;
            } else {
               int var12 = this.getWidth();
               int var13 = this.findViewById(2131362163).getWidth();
               int var14 = var12 - var13 - 20;
               this.mStarLeft = var14;
            }

            if(this.mCheckboxLeft > 0) {
               int var11 = this.mCheckboxLeft;
               this.mStarRight = var11;
            } else {
               int var15 = this.mCheckboxRight;
               this.mStarRight = var15;
            }

            this.mCachedViewPositions = (boolean)1;
         }

         switch(var1.getAction()) {
         case 0:
            this.mDownEvent = (boolean)1;
            if(this.mStarLeft < var3 && this.mStarRight > var3) {
               var2 = 1;
            } else {
               try {
                  MessageList.MessageListAdapter var16 = this.mAdapter;
                  int var17 = this.mListPosition;
                  var16.updateItemBackground(var17);
               } catch (NullPointerException var22) {
                  var22.printStackTrace();
               }
            }
            break;
         case 1:
            if(this.mDownEvent && this.mStarLeft < var3 && this.mStarRight > var3) {
               this.playSoundEffect(0);
               if(!this.IsEAS()) {
                  byte var18;
                  if(!this.mFavorite) {
                     var18 = 1;
                  } else {
                     var18 = 0;
                  }

                  this.mFavorite = (boolean)var18;
                  MessageList.MessageListAdapter var19 = this.mAdapter;
                  boolean var20 = this.mFavorite;
                  var19.updateFavorite(this, var20);
               } else {
                  byte var21;
                  if(this.mFFlagSet) {
                     this.mFFlagSet = (boolean)0;
                     this.mFFlagComplete = (boolean)1;
                     var21 = 1;
                  } else if(this.mFFlagComplete) {
                     this.mFFlagSet = (boolean)0;
                     this.mFFlagComplete = (boolean)0;
                     var21 = 0;
                  } else {
                     this.mFFlagSet = (boolean)1;
                     this.mFFlagComplete = (boolean)1;
                     var21 = 2;
                  }

                  this.mAdapter.updateFlag(this, var21);
               }

               var2 = 1;
            }
         case 2:
         default:
            break;
         case 3:
            this.mDownEvent = (boolean)0;
         }

         if(var2 != 0) {
            this.postInvalidate();
         } else {
            var2 = super.onTouchEvent(var1);
         }

         var5 = var2;
      }

      return (boolean)var5;
   }
}
