package com.android.settings.vpn;

import android.app.Dialog;
import android.net.vpn.VpnProfile;
import android.view.View;

public interface VpnProfileActor {

   void checkStatus();

   void connect(Dialog var1);

   View createConnectView();

   void disconnect();

   VpnProfile getProfile();

   boolean isConnectDialogNeeded();

   String validateInputs(Dialog var1);
}
