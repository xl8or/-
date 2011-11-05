package com.android.exchange.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.database.MatrixCursor.RowBuilder;
import android.net.Uri;
import android.net.Uri.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.email.EmailAddressAdapter;
import com.android.email.provider.EmailContent;
import com.android.exchange.provider.ExchangeProvider;

public class GalEmailAddressAdapter extends EmailAddressAdapter {

   private static final boolean DEBUG_GAL_LOG = false;
   private static final int MINIMUM_GAL_CONSTRAINT_LENGTH = 3;
   private EmailContent.Account mAccount;
   private String mAccountEmailDomain;
   private boolean mAccountHasGal;
   private Activity mActivity;
   private LayoutInflater mInflater;
   private int mSeparatorDisplayCount;
   private int mSeparatorTotalCount;


   public GalEmailAddressAdapter(Activity var1) {
      super(var1);
      this.mActivity = var1;
      this.mAccount = null;
      this.mAccountHasGal = (boolean)0;
      LayoutInflater var2 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.mInflater = var2;
   }

   private void checkGalAccount(EmailContent.Account var1) {
      Activity var2 = this.mActivity;
      long var3 = var1.mHostAuthKeyRecv;
      EmailContent.HostAuth var5 = EmailContent.HostAuth.restoreHostAuthWithId(var2, var3);
      if(var5 != null) {
         String var6 = var5.mProtocol;
         if("eas".equalsIgnoreCase(var6)) {
            this.mAccountHasGal = (boolean)1;
            return;
         }
      }

      this.mAccount = null;
      this.mAccountHasGal = (boolean)0;
   }

   private int getRealPosition(int var1) {
      int var2 = this.getSeparatorPosition();
      int var3;
      if(var2 == -1) {
         var3 = var1;
      } else if(var1 <= var2) {
         var3 = var1;
      } else {
         var3 = var1 - 1;
      }

      return var3;
   }

   private int getSeparatorPosition() {
      Cursor var1 = this.getCursor();
      int var2;
      if(var1 instanceof GalEmailAddressAdapter.MyMergeCursor) {
         var2 = ((GalEmailAddressAdapter.MyMergeCursor)var1).getSeparatorPosition();
      } else {
         var2 = -1;
      }

      return var2;
   }

   public boolean areAllItemsEnabled() {
      return false;
   }

   public int getCount() {
      int var1 = super.getCount();
      if(this.getSeparatorPosition() != -1) {
         ++var1;
      }

      return var1;
   }

   public Object getItem(int var1) {
      int var2 = this.getRealPosition(var1);
      return super.getItem(var2);
   }

   public long getItemId(int var1) {
      int var2 = this.getSeparatorPosition();
      long var3;
      if(var1 == var2) {
         var3 = 65535L;
      } else {
         int var5 = this.getRealPosition(var1);
         var3 = super.getItemId(var5);
      }

      return var3;
   }

   public int getItemViewType(int var1) {
      int var2 = this.getSeparatorPosition();
      int var3;
      if(var1 == var2) {
         var3 = -1;
      } else {
         var3 = super.getItemViewType(var1);
      }

      return var3;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      int var4 = this.getSeparatorPosition();
      View var12;
      if(var1 == var4) {
         View var5 = this.mInflater.inflate(2130903074, var3, (boolean)0);
         TextView var6 = (TextView)var5.findViewById(2131558525);
         View var7 = var5.findViewById(2131558421);
         String var11;
         if(this.mSeparatorDisplayCount == -1) {
            Activity var8 = this.mActivity;
            Object[] var9 = new Object[1];
            String var10 = this.mAccountEmailDomain;
            var9[0] = var10;
            var11 = var8.getString(2131165454, var9);
            var7.setVisibility(0);
         } else {
            int var13 = this.mSeparatorDisplayCount;
            int var14 = this.mSeparatorTotalCount;
            if(var13 == var14) {
               Resources var15 = this.mActivity.getResources();
               int var16 = this.mSeparatorDisplayCount;
               Object[] var17 = new Object[2];
               Integer var18 = Integer.valueOf(this.mSeparatorDisplayCount);
               var17[0] = var18;
               String var19 = this.mAccountEmailDomain;
               var17[1] = var19;
               var11 = var15.getQuantityString(2131427331, var16, var17);
            } else {
               Activity var20 = this.mActivity;
               Object[] var21 = new Object[2];
               Integer var22 = Integer.valueOf(this.mSeparatorDisplayCount);
               var21[0] = var22;
               String var23 = this.mAccountEmailDomain;
               var21[1] = var23;
               var11 = var20.getString(2131165455, var21);
            }

            var7.setVisibility(8);
         }

         var6.setText(var11);
         var12 = var5;
      } else {
         int var24 = this.getRealPosition(var1);
         var12 = super.getView(var24, var2, var3);
      }

      return var12;
   }

   public boolean isEnabled(int var1) {
      int var2 = this.getSeparatorPosition();
      boolean var3;
      if(var1 != var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public Cursor runQueryOnBackgroundThread(CharSequence var1) {
      if(this.mAccount != null && !this.mAccountHasGal) {
         EmailContent.Account var2 = this.mAccount;
         this.checkGalAccount(var2);
      }

      Cursor var3 = super.runQueryOnBackgroundThread(var1);
      Object var4;
      if(this.mAccountHasGal && var1 != null) {
         String var5 = var1.toString().trim();
         if(var5.length() < 3) {
            var4 = var3;
         } else {
            String[] var6 = ExchangeProvider.GAL_PROJECTION;
            MatrixCursor var7 = new MatrixCursor(var6);
            Cursor[] var8 = new Cursor[]{var3, var7};
            GalEmailAddressAdapter.MyMergeCursor var9 = new GalEmailAddressAdapter.MyMergeCursor(var8);
            int var10 = var3.getCount();
            var9.setSeparatorPosition(var10);
            this.mSeparatorDisplayCount = -1;
            this.mSeparatorTotalCount = -1;
            GalEmailAddressAdapter.1 var11 = new GalEmailAddressAdapter.1(var5, var9, var7);
            (new Thread(var11)).start();
            var4 = var9;
         }
      } else {
         var4 = var3;
      }

      return (Cursor)var4;
   }

   public void setAccount(EmailContent.Account var1) {
      this.mAccount = var1;
      this.mAccountHasGal = (boolean)0;
      int var2 = this.mAccount.mEmailAddress.lastIndexOf(64);
      String var3 = this.mAccount.mEmailAddress;
      int var4 = var2 + 1;
      String var5 = var3.substring(var4);
      this.mAccountEmailDomain = var5;
   }

   private static class MyMergeCursor extends MergeCursor {

      private int mSeparatorPosition;


      public MyMergeCursor(Cursor[] var1) {
         super(var1);
         this.mClosed = (boolean)0;
         this.mSeparatorPosition = -1;
      }

      public void close() {
         synchronized(this){}

         try {
            super.close();
         } finally {
            ;
         }

      }

      int getSeparatorPosition() {
         return this.mSeparatorPosition;
      }

      public boolean isClosed() {
         synchronized(this){}
         boolean var5 = false;

         boolean var1;
         try {
            var5 = true;
            var1 = super.isClosed();
            var5 = false;
         } finally {
            if(var5) {
               ;
            }
         }

         return var1;
      }

      void setSeparatorPosition(int var1) {
         this.mSeparatorPosition = var1;
      }
   }

   class 1 implements Runnable {

      // $FF: synthetic field
      final String val$constraintString;
      // $FF: synthetic field
      final MatrixCursor val$matrixCursor;
      // $FF: synthetic field
      final GalEmailAddressAdapter.MyMergeCursor val$mergedResultCursor;


      1(String var2, GalEmailAddressAdapter.MyMergeCursor var3, MatrixCursor var4) {
         this.val$constraintString = var2;
         this.val$mergedResultCursor = var3;
         this.val$matrixCursor = var4;
      }

      public void run() {
         Builder var1 = ExchangeProvider.GAL_URI.buildUpon();
         String var2 = Long.toString(GalEmailAddressAdapter.this.mAccount.mId);
         Builder var3 = var1.appendPath(var2);
         String var4 = this.val$constraintString;
         Uri var5 = var3.appendPath(var4).build();
         ContentResolver var6 = GalEmailAddressAdapter.this.mContentResolver;
         String[] var7 = ExchangeProvider.GAL_PROJECTION;
         Object var8 = null;
         Object var9 = null;
         Cursor var10 = var6.query(var5, var7, (String)null, (String[])var8, (String)var9);
         if(this.val$mergedResultCursor.isClosed()) {
            if(var10 != null) {
               var10.close();
            }
         } else {
            Activity var11 = GalEmailAddressAdapter.this.mActivity;
            GalEmailAddressAdapter.1.1 var12 = new GalEmailAddressAdapter.1.1(var10);
            var11.runOnUiThread(var12);
         }
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final Cursor val$galCursor;


         1(Cursor var2) {
            this.val$galCursor = var2;
         }

         private void getGalResult() {
            if(!1.this.val$mergedResultCursor.isClosed()) {
               if(this.val$galCursor != null && this.val$galCursor.getCount() != 0) {
                  boolean var1 = this.val$galCursor.moveToPosition(-1);

                  while(this.val$galCursor.moveToNext()) {
                     RowBuilder var2 = 1.this.val$matrixCursor.newRow();
                     Long var3 = Long.valueOf(this.val$galCursor.getLong(0));
                     var2.add(var3);
                     String var5 = this.val$galCursor.getString(1);
                     var2.add(var5);
                     String var7 = this.val$galCursor.getString(2);
                     var2.add(var7);
                  }

                  GalEmailAddressAdapter var9 = GalEmailAddressAdapter.this;
                  int var10 = this.val$galCursor.getCount();
                  var9.mSeparatorDisplayCount = var10;
                  GalEmailAddressAdapter var12 = GalEmailAddressAdapter.this;
                  int var13 = this.val$galCursor.getExtras().getInt("com.android.exchange.provider.TOTAL_RESULTS");
                  var12.mSeparatorTotalCount = var13;
                  GalEmailAddressAdapter.this.notifyDataSetChanged();
               } else {
                  1.this.val$mergedResultCursor.setSeparatorPosition(-1);
                  GalEmailAddressAdapter.this.notifyDataSetChanged();
               }
            }
         }

         public void run() {
            this.getGalResult();
            if(this.val$galCursor != null) {
               this.val$galCursor.close();
            }
         }
      }
   }
}
