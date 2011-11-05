package com.android.email.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.email.provider.EmailContent;
import java.util.ArrayList;

public class PoliciesList extends Activity {

   private static final String ACCOUNT_ID = "accountId";
   static final int DIALOG_WARNING = 1;
   private static final String[] POLICIES_CONTENT_PROJECTION;
   static final String TAG = "DeviceAdminAdd";
   private static EmailContent.Account account;
   private static Cursor policiesCursor;
   private TextView mAccountText;
   Button mActionButton;
   final ArrayList<View> mActivePolicies;
   TextView mAddMsg;
   boolean mAddMsgEllipsized;
   CharSequence mAddMsgText;
   boolean mAdding;
   final ArrayList<View> mAddingPolicies;
   ImageView mAdminIcon;
   TextView mAdminName;
   ViewGroup mAdminPolicies;
   TextView mAdminWarning;
   Button mCancelButton;
   Handler mHandler;
   private ListView mPoliciesList;
   View mSelectLayout;
   TextView mTitle;
   private ArrayList<PoliciesList.PolicyItem> policyList;


   static {
      String[] var0 = new String[]{"_id", "name", "type", "value", "account_id"};
      POLICIES_CONTENT_PROJECTION = var0;
   }

   public PoliciesList() {
      ArrayList var1 = new ArrayList();
      this.policyList = var1;
      this.mAddMsgEllipsized = (boolean)1;
      ArrayList var2 = new ArrayList();
      this.mAddingPolicies = var2;
      ArrayList var3 = new ArrayList();
      this.mActivePolicies = var3;
   }

   public static void actionShowPolicyList(Context var0, long var1) {
      Intent var3 = new Intent(var0, PoliciesList.class);
      var3.putExtra("accountId", var1);
      Intent var5 = var3.setFlags(67108864);
      var0.startActivity(var3);
   }

   private void getPolicyNameValue(Cursor var1) {
      if(var1 != null) {
         boolean var2 = var1.moveToPosition(-1);

         while(var1.moveToNext()) {
            boolean var3 = true;
            String var4 = var1.getString(1);
            String var5 = var1.getString(3);
            if(var1.getString(2).equalsIgnoreCase("Integer")) {
               var3 = false;
            }

            if(var4.equals("AllowCamera")) {
               var4 = this.getResources().getString(2131166833);
            } else if(var4.equals("AllowTextMessaging")) {
               var4 = this.getResources().getString(2131166837);
            } else if(var4.equals("AllowWifi")) {
               var4 = this.getResources().getString(2131166838);
            } else if(var4.equals("AllowBluetoothMode")) {
               var4 = this.getResources().getString(2131166831);
               if("0".equals(var5)) {
                  var5 = this.getString(2131166863);
               } else if("1".equals(var5)) {
                  var5 = this.getString(2131166865);
               } else if("2".equals(var5)) {
                  var5 = this.getString(2131166864);
               }
            } else if(var4.equals("AllowInternetSharing")) {
               var4 = this.getResources().getString(2131166835);
            } else if(var4.equals("AllowPOPIMAPEmail")) {
               var4 = this.getResources().getString(2131166836);
            } else if(var4.equals("AllowBrowser")) {
               var4 = this.getResources().getString(2131166832);
            } else if(var4.equals("AllowHTMLEmail")) {
               var4 = this.getResources().getString(2131166834);
            } else if(var4.equals("AllowStorageCard")) {
               var4 = this.getResources().getString(2131166839);
            } else if(var4.equals("AllowSMIMESoftCerts")) {
               var4 = this.getResources().getString(2131166840);
            } else if(var4.equals("RequireDeviceEncryption")) {
               var4 = this.getResources().getString(2131166849);
               if("0".equals(var5)) {
                  var5 = this.getString(2131166863);
               } else if("1".equals(var5)) {
                  var5 = this.getString(2131166864);
               }
            } else if(var4.equals("RequireManualSyncWhenRoaming")) {
               var4 = this.getResources().getString(2131166848);
               if("0".equals(var5)) {
                  var5 = this.getString(2131166863);
               } else if("1".equals(var5)) {
                  var5 = this.getString(2131166864);
               }
            } else if(var4.equals("PasswordMode")) {
               if("0".equals(var5)) {
                  continue;
               }

               if("0".equals(var5)) {
                  var5 = this.getString(2131166863);
               } else if("1".equals(var5)) {
                  var5 = this.getString(2131166864);
               } else {
                  var5 = this.getString(2131166864);
               }
            } else if(var4.equals("PasswordRecoveryEnabled")) {
               var4 = this.getResources().getString(2131166856);
            } else if(var4.equals("DevicePasswordExpiration")) {
               var4 = this.getResources().getString(2131166858);
               if("0".equals(var5)) {
                  continue;
               }

               StringBuilder var9 = new StringBuilder();
               String var10 = policiesCursor.getString(3);
               StringBuilder var11 = var9.append(var10);
               String var12 = this.getString(2131166867);
               var5 = var11.append(var12).toString();
            } else if(var4.equals("DevicePasswordHistory")) {
               var4 = this.getResources().getString(2131166859);
               if("0".equals(var5)) {
                  continue;
               }
            } else if(var4.equals("MinPasswordComplexCharacters")) {
               var4 = this.getResources().getString(2131166862);
               if("0".equals(var5)) {
                  continue;
               }
            } else if(var4.equals("MaxDevicePasswordFailedAttempts")) {
               var4 = this.getResources().getString(2131166857);
               if("0".equals(var5)) {
                  continue;
               }
            } else if(var4.equals("MinDevicePasswordLength")) {
               var4 = "MinDevicePasswordLength";
               if("0".equals(var5)) {
                  continue;
               }
            } else if(var4.equals("AttachmentsEnabled")) {
               var4 = this.getResources().getString(2131166842);
            } else if(var4.equals("MaxEmailBodyTruncationSize")) {
               var4 = this.getResources().getString(2131166844);
               if("0".equals(var5)) {
                  continue;
               }

               StringBuilder var13 = new StringBuilder();
               String var14 = policiesCursor.getString(3);
               StringBuilder var15 = var13.append(var14);
               String var16 = this.getString(2131166866);
               var5 = var15.append(var16).toString();
            } else if(var4.equals("MaxEmailHtmlBodyTruncationSize")) {
               var4 = this.getResources().getString(2131166845);
               if("0".equals(var5)) {
                  continue;
               }

               StringBuilder var17 = new StringBuilder();
               String var18 = policiesCursor.getString(3);
               StringBuilder var19 = var17.append(var18);
               String var20 = this.getString(2131166866);
               var5 = var19.append(var20).toString();
            } else if(var4.equals("MaxAttachmentSize")) {
               var4 = this.getResources().getString(2131166843);
               if("0".equals(var5)) {
                  continue;
               }

               StringBuilder var21 = new StringBuilder();
               String var22 = policiesCursor.getString(3);
               StringBuilder var23 = var21.append(var22);
               String var24 = this.getString(2131166866);
               var5 = var23.append(var24).toString();
            } else if(var4.equals("RequireSignedSMIMEMessages")) {
               var4 = this.getResources().getString(2131166851);
            } else if(var4.equals("AllowSMIMEEncryptionAlgorithmNegotiation")) {
               var4 = this.getResources().getString(2131166841);
               if("0".equals(var5)) {
                  continue;
               }

               if("1".equals(var5)) {
                  var5 = this.getResources().getString(2131166868);
               } else if("2".equals(var5)) {
                  var5 = this.getResources().getString(2131166869);
               } else if("3".equals(var5)) {
                  var5 = this.getResources().getString(2131166870);
               }
            } else if(var4.equals("RequireEncryptedSMIMEMessages")) {
               var4 = this.getResources().getString(2131166852);
            } else {
               if(var4.equals("RemoteWipe")) {
                  continue;
               }

               if(var4.equals("AllowDesktopSync")) {
                  var4 = this.getResources().getString(17040139);
               } else {
                  if(var4.equals("AllowIrDA")) {
                     continue;
                  }

                  if(var4.equals("MaxInactivityTime")) {
                     if("0".equals(var5)) {
                        continue;
                     }
                  } else if(var4.equals("RequireSignedSMIMEAlgorithm")) {
                     var4 = this.getResources().getString(2131166853);
                     if("0".equals(var5)) {
                        var5 = this.getResources().getString(2131166792);
                     } else if("1".equals(var5)) {
                        var5 = this.getResources().getString(2131166793);
                     }
                  } else if(var4.equals("RequireEncryptionSMIMEAlgorithm")) {
                     var4 = this.getResources().getString(2131166854);
                     if("0".equals(var5)) {
                        var5 = this.getResources().getString(2131166794);
                     } else if("1".equals(var5)) {
                        var5 = this.getResources().getString(2131166795);
                     } else if("2".equals(var5)) {
                        var5 = this.getResources().getString(2131166796);
                     } else if("3".equals(var5)) {
                        var5 = this.getResources().getString(2131166797);
                     } else if("4".equals(var5)) {
                        var5 = this.getResources().getString(2131166798);
                     }
                  } else if(var4.equals("MaxEmailAgeFilter")) {
                     var4 = this.getResources().getString(17040121);
                     if("0".equals(var5)) {
                        var5 = this.getResources().getString(2131166413);
                     } else if("1".equals(var5)) {
                        var5 = this.getResources().getString(2131166408);
                     } else if("2".equals(var5)) {
                        var5 = this.getResources().getString(2131166409);
                     } else if("3".equals(var5)) {
                        var5 = this.getResources().getString(2131166410);
                     } else if("4".equals(var5)) {
                        var5 = this.getResources().getString(2131166411);
                     } else if("5".equals(var5)) {
                        var5 = this.getResources().getString(2131166412);
                     }
                  } else if(var4.equals("MaxCalendarAgeFilter")) {
                     if("0".equals(var5)) {
                        var5 = this.getResources().getString(2131166413);
                     } else if("4".equals(var5)) {
                        var5 = this.getResources().getString(2131166411);
                     } else if("5".equals(var5)) {
                        var5 = this.getResources().getString(2131166412);
                     } else if("6".equals(var5)) {
                        var5 = this.getResources().getString(2131166935);
                     } else if("7".equals(var5)) {
                        var5 = this.getResources().getString(2131166936);
                     }

                     var4 = this.getResources().getString(17040119);
                  }
               }
            }

            if(var3) {
               if(var5.trim().equalsIgnoreCase("true")) {
                  var5 = this.getApplicationContext().getResources().getString(2131166864);
               }

               if(var5.trim().equalsIgnoreCase("false")) {
                  var5 = this.getApplicationContext().getResources().getString(2131166863);
               }
            }

            ArrayList var6 = this.policyList;
            PoliciesList.PolicyItem var7 = new PoliciesList.PolicyItem(var4, var5);
            var6.add(var7);
         }

      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      long var2 = this.getIntent().getLongExtra("accountId", 65535L);
      if(var2 != 65535L) {
         account = EmailContent.Account.restoreAccountWithId(this, var2);
         if(account != null) {
            ContentResolver var4 = this.getContentResolver();
            Uri var5 = EmailContent.Policies.CONTENT_URI;
            String[] var6 = POLICIES_CONTENT_PROJECTION;
            String[] var7 = new String[1];
            String var8 = String.valueOf(var2);
            var7[0] = var8;
            policiesCursor = var4.query(var5, var6, "account_id=?", var7, (String)null);
            this.setContentView(2130903180);
            TextView var9 = (TextView)this.findViewById(2131362506);
            this.mAccountText = var9;
            TextView var10 = this.mAccountText;
            String var11 = account.mDisplayName;
            var10.setText(var11);
            ListView var12 = (ListView)this.findViewById(2131362507);
            this.mPoliciesList = var12;
            Cursor var13 = policiesCursor;
            this.getPolicyNameValue(var13);
            ListView var14 = this.mPoliciesList;
            Context var15 = this.getApplicationContext();
            ArrayList var16 = this.policyList;
            PoliciesList.PolicyAdapter var17 = new PoliciesList.PolicyAdapter(var15, 2130903182, var16);
            var14.setAdapter(var17);
            this.mPoliciesList.setClickable((boolean)0);
         }
      }
   }

   protected void onResume() {
      super.onResume();
   }

   private class PolicyAdapter extends ArrayAdapter<PoliciesList.PolicyItem> {

      private LayoutInflater mInflater;
      private int mResourceID;


      public PolicyAdapter(Context var2, int var3, ArrayList var4) {
         super(var2, var3, var4);
         this.mResourceID = var3;
         LayoutInflater var5 = (LayoutInflater)var2.getSystemService("layout_inflater");
         this.mInflater = var5;
      }

      public int getCount() {
         return PoliciesList.this.policyList.size();
      }

      public PoliciesList.PolicyItem getItem(int var1) {
         return (PoliciesList.PolicyItem)PoliciesList.this.policyList.get(var1);
      }

      public long getItemId(int var1) {
         return (long)var1;
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         View var4;
         if(var1 == 0) {
            var4 = this.mInflater.inflate(2130903181, var3, (boolean)0);
         } else {
            LayoutInflater var5 = this.mInflater;
            int var6 = this.mResourceID;
            var2 = var5.inflate(var6, var3, (boolean)0);
            PoliciesList.PolicyAdapter.ViewHolder var7 = new PoliciesList.PolicyAdapter.ViewHolder((PoliciesList.1)null);
            TextView var8 = (TextView)var2.findViewById(2131362509);
            var7.text = var8;
            TextView var9 = (TextView)var2.findViewById(2131362510);
            var7.value = var9;
            var2.setTag(var7);
            ArrayList var10 = PoliciesList.this.policyList;
            int var11 = var1 - 1;
            String var12 = ((PoliciesList.PolicyItem)var10.get(var11)).getPolicyValue();
            String var13 = PoliciesList.this.getString(2131166863);
            if(var12.equalsIgnoreCase(var13)) {
               var7.text.setTextColor(-7829368);
               var7.value.setTextColor(-7829368);
            } else {
               TextView var22 = var7.value;
               Context var23 = this.getContext();
               var22.setTextAppearance(var23, 2131623944);
               TextView var24 = var7.text;
               Context var25 = this.getContext();
               var24.setTextAppearance(var25, 2131623943);
            }

            TextView var14 = var7.text;
            ArrayList var15 = PoliciesList.this.policyList;
            int var16 = var1 - 1;
            String var17 = ((PoliciesList.PolicyItem)var15.get(var16)).getPolicyName();
            var14.setText(var17);
            TextView var18 = var7.value;
            ArrayList var19 = PoliciesList.this.policyList;
            int var20 = var1 - 1;
            String var21 = ((PoliciesList.PolicyItem)var19.get(var20)).getPolicyValue();
            var18.setText(var21);
            var4 = var2;
         }

         return var4;
      }

      public boolean isEnabled(int var1) {
         return false;
      }

      private class ViewHolder {

         TextView text;
         TextView value;


         private ViewHolder() {}

         // $FF: synthetic method
         ViewHolder(PoliciesList.1 var2) {
            this();
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class PolicyItem {

      private String Value;
      private String policyName;


      PolicyItem(String var2, String var3) {
         this.policyName = var2;
         this.Value = var3;
      }

      public String getPolicyName() {
         return this.policyName;
      }

      public String getPolicyValue() {
         return this.Value;
      }

      public void setPolicyName(String var1) {
         this.policyName = var1;
      }

      public void setPolicyValue(String var1) {
         this.Value = var1;
      }
   }
}
