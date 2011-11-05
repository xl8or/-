package com.broadcom.bt.app.settings;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.android.settings.ProgressCategory;
import com.android.settings.bluetooth.LocalBluetoothManager;
import com.broadcom.bt.app.settings.BluetoothDid;
import com.broadcom.bt.app.settings.BluetoothServicesMap;
import com.broadcom.bt.app.settings.ViewDidRecord;
import java.util.HashSet;

public class DiscoverServicesActivity extends PreferenceActivity {

   private static final String KEY_BT_DID_LIST = "bt_did_list";
   private static final String KEY_BT_NAME = "bt_name";
   private static final String KEY_BT_SERVICE_LIST = "bt_service_list";
   private static final int MENU_SCAN = 1;
   private static final String TAG = "DiscoverServicesActivity";
   private String mDeviceAddress;
   private String mDeviceName;
   private ProgressCategory mDidList;
   private Handler mHandler;
   private LocalBluetoothManager mLocalManager;
   Preference mName;
   private int mRecNum;
   private final BroadcastReceiver mReceiver;
   private ProgressCategory mServiceList;
   private BluetoothServicesMap.IServiceListManager mServiceListManager;
   private int mTotalRecNum;


   public DiscoverServicesActivity() {
      Handler var1 = new Handler();
      this.mHandler = var1;
      DiscoverServicesActivity.1 var2 = new DiscoverServicesActivity.1();
      this.mReceiver = var2;
      DiscoverServicesActivity.2 var3 = new DiscoverServicesActivity.2();
      this.mServiceListManager = var3;
   }

   private String getProperty(String var1, Bundle var2) {
      String var3 = null;
      if(var2 != null) {
         var3 = var2.getString(var1);
      }

      if(TextUtils.isEmpty(var3)) {
         var3 = this.getIntent().getStringExtra(var1);
      }

      return var3;
   }

   private void processDiscoverEvent(Bundle var1) {
      this.mServiceList.removeAll();
      BluetoothDevice var2 = (BluetoothDevice)var1.get("android.bluetooth.device.extra.DEVICE");
      String var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = var2.getAddress();
      }

      if(this.mDeviceAddress != null && this.mDeviceAddress.equals(var3)) {
         Parcelable[] var4 = var1.getParcelableArray("android.bluetooth.device.extra.UUID");
         if(var4 != null) {
            HashSet var5 = new HashSet();
            int var6 = 0;

            while(true) {
               int var7 = var4.length;
               if(var6 >= var7) {
                  break;
               }

               ParcelUuid var8 = (ParcelUuid)var4[var6];
               if(!var5.contains(var8)) {
                  var5.add(var8);
                  BluetoothServicesMap.IServiceListManager var10 = this.mServiceListManager;
                  BluetoothServicesMap.getServiceStringByUUID(this, var8, var10);
               }

               ++var6;
            }
         }
      }

      this.mServiceList.setProgress((boolean)0);
   }

   private void processRemoteDiInfo(Bundle var1) {
      int var2 = var1.getInt("android.bluetooth.adapter.extra.DI_STATUS");
      int var3 = var1.getInt("android.bluetooth.adapter.extra.DI_REC_NUM");
      this.mTotalRecNum = var3;
      if(var2 == 0 && this.mTotalRecNum != 0) {
         this.mRecNum = 0;
         int var4 = this.mRecNum + 1;
         this.mRecNum = var4;
         int var5 = this.mTotalRecNum;
         if(var4 > var5) {
            this.discoverServices();
         }
      } else {
         this.discoverServices();
      }
   }

   private void processRemoteDiRecord(Context var1, Bundle var2) {
      if(this.mRecNum == 1) {
         int var3 = var2.getInt("android.bluetooth.adapter.extra.DI_SPEC_ID", 0);
         String var4 = Integer.toHexString(var3 / 256);
         String var5 = Integer.toHexString(var3 % 256);
      }

      Preference var6 = new Preference(var1);
      var6.setPersistent((boolean)0);
      var6.setSelectable((boolean)1);
      String var7 = Integer.toString(this.mRecNum);
      var6.setKey(var7);
      int var8 = this.mRecNum;
      BluetoothDid.getDidRecord(var1, var6, var2, var8);
      this.mDidList.addPreference(var6);
      int var10 = this.mRecNum + 1;
      this.mRecNum = var10;
      int var11 = this.mTotalRecNum;
      if(var10 > var11) {
         this.discoverServices();
      }
   }

   public void discoverServices() {
      this.mDidList.setProgress((boolean)0);
      this.mServiceList.setProgress((boolean)1);
      BluetoothAdapter var1 = BluetoothAdapter.getDefaultAdapter();
      if(var1 != null) {
         String var2 = this.mDeviceAddress;
         var1.getRemoteServices(var2);
      }
   }

   public void fetchRemoteDiInfo() {
      this.mDidList.setProgress((boolean)1);
      this.mDidList.removeAll();
      BluetoothAdapter var1 = BluetoothAdapter.getDefaultAdapter();
      if(var1 != null) {
         String var2 = this.mDeviceAddress;
         var1.fetchRemoteDiInfo(var2);
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      LocalBluetoothManager var2 = LocalBluetoothManager.getInstance(this);
      this.mLocalManager = var2;
      if(this.mLocalManager == null) {
         this.finish();
      }

      BluetoothDevice var3 = (BluetoothDevice)this.getIntent().getExtra("android.bluetooth.device.extra.DEVICE");
      if(var3 != null) {
         String var4 = var3.getAddress();
         this.mDeviceAddress = var4;
      }

      if(TextUtils.isEmpty(this.mDeviceAddress)) {
         int var5 = Log.w("DiscoverServicesActivity", "Activity started without address");
         this.finish();
      }

      this.addPreferencesFromResource(2130968593);
      Preference var6 = this.findPreference("bt_name");
      var6.setSelectable((boolean)0);
      String var7 = this.getProperty("android.bluetooth.device.extra.NAME", var1);
      this.mDeviceName = var7;
      if(this.mDeviceName != null) {
         String var8 = this.mDeviceName;
         var6.setSummary(var8);
      }

      ProgressCategory var9 = (ProgressCategory)this.findPreference("bt_did_list");
      this.mDidList = var9;
      ProgressCategory var10 = (ProgressCategory)this.findPreference("bt_service_list");
      this.mServiceList = var10;
      this.fetchRemoteDiInfo();
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      MenuItem var3 = var1.add(0, 1, 0, 2131232611).setIcon(17301980).setAlphabeticShortcut('r');
      return true;
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 1:
         this.fetchRemoteDiInfo();
         var2 = true;
         break;
      default:
         var2 = false;
      }

      return var2;
   }

   protected void onPause() {
      super.onPause();
      BroadcastReceiver var1 = this.mReceiver;
      this.unregisterReceiver(var1);
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      Integer var3 = Integer.valueOf(Integer.parseInt(var2.getKey()));
      String var4 = BluetoothDid.showRemoteDiRecord(this, var3);
      if(var4 != null) {
         Intent var5 = new Intent(this, ViewDidRecord.class);
         Object[] var6 = new Object[]{var3};
         String var7 = String.format("Record %1$s", var6);
         var5.putExtra("title", var7);
         var5.putExtra("content", var4);
         this.startActivity(var5);
      }

      return true;
   }

   public boolean onPrepareOptionsMenu(Menu var1) {
      super.onPrepareOptionsMenu(var1);
      BluetoothAdapter var3 = BluetoothAdapter.getDefaultAdapter();
      MenuItem var4 = var1.findItem(1);
      boolean var5 = var3.isEnabled();
      var4.setEnabled(var5);
      return true;
   }

   protected void onResume() {
      super.onResume();
      BroadcastReceiver var1 = this.mReceiver;
      IntentFilter var2 = new IntentFilter("android.bleutooth.device.action.UUID");
      this.registerReceiver(var1, var2);
      BroadcastReceiver var4 = this.mReceiver;
      IntentFilter var5 = new IntentFilter("android.bluetooth.adapter.action.DI_REMOTE_DI_INFO_RECEIVED");
      this.registerReceiver(var4, var5);
      BroadcastReceiver var7 = this.mReceiver;
      IntentFilter var8 = new IntentFilter("android.bluetooth.adapter.action.DI_REMOTE_DI_RECORD_RECEIVED");
      this.registerReceiver(var7, var8);
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         Bundle var3 = var2.getExtras();
         Handler var4 = DiscoverServicesActivity.this.mHandler;
         DiscoverServicesActivity.1.1 var5 = new DiscoverServicesActivity.1.1(var2, var3, var1);
         var4.post(var5);
      }

      class 1 implements Runnable {

         // $FF: synthetic field
         final Bundle val$b;
         // $FF: synthetic field
         final Context val$context;
         // $FF: synthetic field
         final Intent val$intent;


         1(Intent var2, Bundle var3, Context var4) {
            this.val$intent = var2;
            this.val$b = var3;
            this.val$context = var4;
         }

         public void run() {
            String var1 = this.val$intent.getAction();
            if(var1.equals("android.bleutooth.device.action.UUID")) {
               DiscoverServicesActivity var2 = DiscoverServicesActivity.this;
               Bundle var3 = this.val$b;
               var2.processDiscoverEvent(var3);
            } else if(var1.equals("android.bluetooth.adapter.action.DI_REMOTE_DI_INFO_RECEIVED")) {
               DiscoverServicesActivity var4 = DiscoverServicesActivity.this;
               Bundle var5 = this.val$b;
               var4.processRemoteDiInfo(var5);
            } else if(var1.equals("android.bluetooth.adapter.action.DI_REMOTE_DI_RECORD_RECEIVED")) {
               DiscoverServicesActivity var6 = DiscoverServicesActivity.this;
               Context var7 = this.val$context;
               Bundle var8 = this.val$b;
               var6.processRemoteDiRecord(var7, var8);
            }
         }
      }
   }

   class 2 implements BluetoothServicesMap.IServiceListManager {

      2() {}

      public void add(String var1) {
         DiscoverServicesActivity var2 = DiscoverServicesActivity.this;
         Preference var3 = new Preference(var2);
         var3.setPersistent((boolean)0);
         var3.setSelectable((boolean)0);
         var3.setTitle(var1);
         boolean var4 = DiscoverServicesActivity.this.mServiceList.addPreference(var3);
      }
   }
}
