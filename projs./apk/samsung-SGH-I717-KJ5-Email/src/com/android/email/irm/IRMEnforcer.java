package com.android.email.irm;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.android.email.provider.EmailContent;

public class IRMEnforcer {

   static IRMEnforcer sInstance;
   private Context mContext;


   private IRMEnforcer(Context var1) {
      this.mContext = var1;
   }

   public static IRMEnforcer getInstance(Context var0) {
      synchronized(IRMEnforcer.class){}

      IRMEnforcer var1;
      try {
         if(sInstance == null) {
            sInstance = new IRMEnforcer(var0);
         }

         var1 = sInstance;
      } finally {
         ;
      }

      return var1;
   }

   public int getIRMLicenseFlag(long var1) {
      ContentResolver var3 = this.mContext.getContentResolver();
      Uri var4 = EmailContent.Message.CONTENT_URI;
      String[] var5 = new String[]{"IRMLicenseFlag"};
      String[] var6 = new String[1];
      String var7 = Long.toString(var1);
      var6[0] = var7;
      Cursor var8 = var3.query(var4, var5, "_id=?", var6, (String)null);
      int var9 = -1;
      if(var8 != null && var8.getCount() > 0) {
         boolean var10 = var8.moveToFirst();
         int var11 = var8.getColumnIndex("IRMLicenseFlag");
         var9 = var8.getInt(var11);
      }

      if(var8 != null) {
         var8.close();
      }

      return var9;
   }

   public boolean isEditAllowed(long var1) {
      boolean var3 = true;
      int var4 = this.getIRMLicenseFlag(var1);
      if(var4 != -1) {
         if((var4 & 8) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public boolean isExportAllowed(long var1) {
      boolean var3 = false;
      int var4 = this.getIRMLicenseFlag(var1);
      if(var4 != -1) {
         if((var4 & 64) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public boolean isExtractAllowed(long var1) {
      boolean var3 = true;
      int var4 = this.getIRMLicenseFlag(var1);
      if(var4 != -1) {
         if((var4 & 32) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public boolean isForwardAllowed(long var1) {
      boolean var3 = true;
      int var4 = this.getIRMLicenseFlag(var1);
      if(var4 != -1) {
         if((var4 & 2) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public boolean isIRMEnabled(long param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean isModifyRecipientsAllowed(long var1) {
      boolean var3 = true;
      int var4 = this.getIRMLicenseFlag(var1);
      if(var4 != -1) {
         if((var4 & 16) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public boolean isPrintAllowed(long var1) {
      boolean var3 = true;
      int var4 = this.getIRMLicenseFlag(var1);
      if(var4 != -1) {
         if((var4 & 128) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public boolean isProgrammaticAccessAllowed(long var1) {
      boolean var3 = true;
      int var4 = this.getIRMLicenseFlag(var1);
      if(var4 != -1) {
         if((var4 & 256) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public boolean isReplyAllAllowed(long var1) {
      boolean var3 = true;
      int var4 = this.getIRMLicenseFlag(var1);
      if(var4 != -1) {
         if((var4 & 4) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public boolean isReplyAllowed(long var1) {
      boolean var3 = true;
      int var4 = this.getIRMLicenseFlag(var1);
      if(var4 != -1) {
         if((var4 & 1) != 0) {
            var3 = true;
         } else {
            var3 = false;
         }
      }

      return var3;
   }
}
