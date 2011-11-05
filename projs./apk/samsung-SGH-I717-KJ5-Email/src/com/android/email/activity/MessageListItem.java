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
            this.mCachedViewPositions = (boolean)1;
         }

         CheckBox var6 = (CheckBox)this.findViewById(2131362155);
         if(MessageList.isAttModel()) {
            int var7 = this.getLeft();
            this.mCheckboxLeft = var7;
            int var8 = var6.getRight();
            this.mCheckboxRight = var8;
         } else {
            int var13 = this.getLeft();
            this.mCheckboxLeft = var13;
            int var14 = var6.getRight();
            this.mCheckboxRight = var14;
         }

         if(!this.IsEAS()) {
            int var9 = this.getWidth();
            int var10 = this.findViewById(2131362164).getWidth();
            int var11 = var9 - var10 - 20;
            this.mStarLeft = var11;
         } else {
            int var15 = this.getWidth();
            int var16 = this.findViewById(2131362170).getWidth();
            int var17 = var15 - var16 - 20;
            this.mStarLeft = var17;
         }

         if(MessageList.isAttModel()) {
            int var12 = this.getRight();
            this.mStarRight = var12;
         } else if(this.mCheckboxLeft > 0) {
            int var18 = this.mCheckboxLeft;
            this.mStarRight = var18;
         } else {
            int var19 = this.mCheckboxRight;
            this.mStarRight = var19;
         }

         switch(var1.getAction()) {
         case 0:
            this.mDownEvent = (boolean)1;
            if(this.mStarLeft >= var3 || this.mStarRight <= var3) {
               label109: {
                  if(MessageList.isAttModel()) {
                     int var20 = this.mCheckboxRight;
                     if(var3 < var20) {
                        break label109;
                     }
                  }

                  try {
                     MessageList.MessageListAdapter var21 = this.mAdapter;
                     int var22 = this.mListPosition;
                     var21.updateItemBackground(var22);
                  } catch (NullPointerException var32) {
                     var32.printStackTrace();
                  }
                  break;
               }
            }

            var2 = 1;
            break;
         case 1:
            if(this.mDownEvent) {
               label85: {
                  if(MessageList.isAttModel()) {
                     int var23 = this.mCheckboxRight;
                     if(var3 < var23) {
                        this.playSoundEffect(0);
                        byte var24;
                        if(!this.mDelete) {
                           var24 = 1;
                        } else {
                           var24 = 0;
                        }

                        this.mDelete = (boolean)var24;
                        byte var25;
                        if(!this.mMove) {
                           var25 = 1;
                        } else {
                           var25 = 0;
                        }

                        this.mMove = (boolean)var25;
                        MessageList.MessageListAdapter var26 = this.mAdapter;
                        boolean var27 = this.mDelete;
                        var26.updateSelected(this, var27);
                        var2 = 1;
                        break label85;
                     }
                  }

                  if(this.mStarLeft < var3 && this.mStarRight > var3) {
                     this.playSoundEffect(0);
                     if(!this.IsEAS()) {
                        byte var28;
                        if(!this.mFavorite) {
                           var28 = 1;
                        } else {
                           var28 = 0;
                        }

                        this.mFavorite = (boolean)var28;
                        MessageList.MessageListAdapter var29 = this.mAdapter;
                        boolean var30 = this.mFavorite;
                        var29.updateFavorite(this, var30);
                     } else {
                        byte var31;
                        if(this.mFFlagSet) {
                           this.mFFlagSet = (boolean)0;
                           this.mFFlagComplete = (boolean)1;
                           var31 = 1;
                        } else if(this.mFFlagComplete) {
                           this.mFFlagSet = (boolean)0;
                           this.mFFlagComplete = (boolean)0;
                           var31 = 0;
                        } else {
                           this.mFFlagSet = (boolean)1;
                           this.mFFlagComplete = (boolean)1;
                           var31 = 2;
                        }

                        this.mAdapter.updateFlag(this, var31);
                     }

                     var2 = 1;
                  }
               }
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
