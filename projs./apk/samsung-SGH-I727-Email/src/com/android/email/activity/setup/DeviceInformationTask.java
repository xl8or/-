package com.android.email.activity.setup;

import android.content.Context;
import android.os.AsyncTask;
import com.android.exchange.DeviceInformation;
import com.android.exchange.EasSyncService;
import java.util.ArrayList;
import java.util.Iterator;

public class DeviceInformationTask extends AsyncTask<Void, Void, Void> {

   public static final int DEVICE_INFO_ARGUMENT_ERROR = 5002;
   public static final int DEVICE_INFO_ERROR_BASE = 5000;
   public static final int DEVICE_INFO_IOEXCEPTION = 5001;
   public static final int DEVICE_INFO_START = 5000;
   private final String TAG = "DeviceInformationTask";
   private ArrayList<DeviceInformationTask.statusCallBack> mCallBacks;
   private Context mContext;
   private DeviceInformation mDeviceInfo;
   private EasSyncService mSvc = null;


   public DeviceInformationTask(DeviceInformation var1) {
      ArrayList var2 = new ArrayList();
      this.mCallBacks = var2;
      this.mDeviceInfo = null;
      this.mDeviceInfo = var1;
   }

   protected Void doInBackground(Void ... param1) {
      // $FF: Couldn't be decompiled
   }

   public void registerStatusCallBack(DeviceInformationTask.statusCallBack var1) {
      this.mCallBacks.add(var1);
   }

   public void setUpService(Context var1, EasSyncService var2) {
      this.mSvc = var2;
      this.mContext = var1;
   }

   public void updateStatus(int var1) {
      Iterator var2 = this.mCallBacks.iterator();

      while(var2.hasNext()) {
         ((DeviceInformationTask.statusCallBack)var2.next()).status(var1);
      }

   }

   public interface statusCallBack {

      void status(int var1);
   }
}
