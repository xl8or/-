package com.android.exchange;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.android.email.SecurityPolicy;
import com.android.email.provider.EmailContent;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class PoliciesMultiplexer {

   private static final String ACTION_POLICIES_RELAXED = "policies.relaxed";
   private static final String TAG = PoliciesMultiplexer.class.getSimpleName();
   private Context mContext;


   public PoliciesMultiplexer(Context var1) {
      this.mContext = var1;
   }

   private PoliciesMultiplexer.PoliciesComparable[] getAllPolicies(Long param1) {
      // $FF: Couldn't be decompiled
   }

   private Object getConcreteObject(String var1, String var2) {
      Object var3 = null;
      if(var1 != null && var2 != null) {
         if("Integer".equals(var1)) {
            var3 = Integer.valueOf(Integer.parseInt(var2));
         } else if("Boolean".equals(var1)) {
            var3 = Boolean.valueOf(Boolean.parseBoolean(var2));
         }
      }

      return var3;
   }

   private void removePasswordPolicies(ArrayList<PoliciesMultiplexer.PoliciesComparable> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         PoliciesMultiplexer.PoliciesComparable var4 = (PoliciesMultiplexer.PoliciesComparable)var3.next();
         if(!var4.mName.equals("PasswordMode")) {
            String[] var5 = SecurityPolicy.PASSWORD_POLICIES;
            String var6 = var4.mName;
            if(ArrayUtils.contains(var5, var6)) {
               var2.add(var4);
            }
         }
      }

      var1.removeAll(var2);
   }

   public HashMap<String, Object> computeAggregatePolicy(Long var1) {
      int var2 = Log.d(TAG, "update");
      PoliciesMultiplexer.PoliciesComparable[] var3 = this.getAllPolicies(var1);
      int var4 = Log.d(TAG, "grouping policies by name");
      HashMap var5 = new HashMap();
      PoliciesMultiplexer.PoliciesComparable[] var6 = var3;
      int var7 = var3.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         PoliciesMultiplexer.PoliciesComparable var9 = var6[var8];
         String var10 = var9.mName;
         ArrayList var11 = (ArrayList)var5.get(var10);
         if(var11 == null) {
            var11 = new ArrayList();
            String var12 = var9.mName;
            var5.put(var12, var11);
         }

         var11.add(var9);
      }

      int var15 = Log.d(TAG, "grouped policies by name");
      int var16 = Log.d(TAG, "finding the strongest policies");
      ArrayList var17 = new ArrayList();
      Iterator var18 = var5.keySet().iterator();

      while(var18.hasNext()) {
         String var19 = (String)var18.next();
         ArrayList var20 = (ArrayList)var5.get(var19);
         Collections.sort(var20);
         int var21 = var20.size();
         if(var21 > 0) {
            int var22 = var21 - 1;
            Object var23 = var20.get(var22);
            var17.add(var23);
         }
      }

      int var25 = Log.d(TAG, "found the strongest policies");
      int var26 = Log.d(TAG, "applying policies to the device");
      HashMap var27 = new HashMap();
      Iterator var28 = var17.iterator();

      while(var28.hasNext()) {
         PoliciesMultiplexer.PoliciesComparable var29 = (PoliciesMultiplexer.PoliciesComparable)var28.next();
         String var30 = var29.mName;
         String var31 = var29.mType;
         String var32 = var29.mValue;
         Object var33 = this.getConcreteObject(var31, var32);
         var27.put(var30, var33);
      }

      return var27;
   }

   public void removePolicyRules(long var1) {
      ContentResolver var3 = this.mContext.getContentResolver();
      Uri var4 = EmailContent.Policies.CONTENT_URI;
      String[] var5 = new String[1];
      String var6 = String.valueOf(var1);
      var5[0] = var6;
      var3.delete(var4, "account_id=?", var5);
   }

   public static final class PoliciesComparable extends EmailContent.Policies implements Comparable<PoliciesMultiplexer.PoliciesComparable> {

      public PoliciesComparable() {}

      private int compareBoolean(PoliciesMultiplexer.PoliciesComparable var1) {
         byte var2 = this.getBooleanValue();
         byte var3 = var1.getBooleanValue();
         int var4 = 0;
         if(var2 == 1 && var3 == 0) {
            var4 = 1;
         }

         if(var2 == 0 && var3 == 1) {
            var4 = -1;
         }

         if(this.mName.equals("AttachmentsEnabled") || this.mName.equals("AllowStorageCard") || this.mName.equals("AllowCamera") || this.mName.equals("AllowWifi") || this.mName.equals("AllowTextMessaging") || this.mName.equals("AllowPOPIMAPEmail") || this.mName.equals("AllowHTMLEmail") || this.mName.equals("AllowBrowser") || this.mName.equals("AllowInternetSharing") || this.mName.equals("AllowSMIMEEncryptionAlgorithmNegotiation") || this.mName.equals("AllowSMIMESoftCerts") || this.mName.equals("AllowDesktopSync") || this.mName.equals("AllowIrDA")) {
            var4 = -var4;
         }

         return var4;
      }

      private int compareInteger(PoliciesMultiplexer.PoliciesComparable var1) {
         int var2 = this.getIntegerValue().intValue();
         int var3 = var1.getIntegerValue().intValue();
         int var4 = 0;
         boolean var5 = false;
         if(!this.mName.equals("MaxInactivityTime") && !this.mName.equals("MaxDevicePasswordFailedAttempts") && !this.mName.equals("DevicePasswordExpiration") && !this.mName.equals("MaxAttachmentSize") && !this.mName.equals("MaxCalendarAgeFilter") && !this.mName.equals("MaxEmailAgeFilter") && !this.mName.equals("MaxEmailBodyTruncationSize") && !this.mName.equals("MaxEmailHtmlBodyTruncationSize")) {
            if(!this.mName.equals("RequireSignedSMIMEAlgorithm") && !this.mName.equals("RequireEncryptionSMIMEAlgorithm") && !this.mName.equals("AllowSMIMEEncryptionAlgorithmNegotiation")) {
               if(this.mName.equals("AllowBluetoothMode")) {
                  var5 = true;
               }
            } else {
               if(var2 == -1) {
                  var2 = Integer.MAX_VALUE;
               }

               if(var3 == -1) {
                  var3 = Integer.MAX_VALUE;
               }

               var5 = true;
            }
         } else {
            if(var3 == 0) {
               var3 = Integer.MAX_VALUE;
            }

            if(var2 == 0) {
               var2 = Integer.MAX_VALUE;
            }

            var5 = true;
         }

         if(var2 > var3) {
            var4 = 1;
         } else if(var2 < var3) {
            var4 = -1;
         }

         if(var5) {
            var4 = -var4;
         }

         return var4;
      }

      public int compareTo(PoliciesMultiplexer.PoliciesComparable var1) {
         int var2 = 0;
         if(this.mType.equals("Boolean")) {
            var2 = this.compareBoolean(var1);
         } else if(this.mType.equals("Integer")) {
            var2 = this.compareInteger(var1);
         }

         return var2;
      }

      public boolean getBooleanValue() {
         return Boolean.parseBoolean(this.mValue);
      }

      public Integer getIntegerValue() {
         return Integer.valueOf(Integer.parseInt(this.mValue));
      }
   }
}
