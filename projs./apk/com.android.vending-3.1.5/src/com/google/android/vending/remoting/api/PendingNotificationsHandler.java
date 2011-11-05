package com.google.android.vending.remoting.api;

import android.content.Context;
import com.google.android.vending.remoting.protos.VendingProtos;

public interface PendingNotificationsHandler {

   boolean handlePendingNotifications(Context var1, String var2, VendingProtos.PendingNotificationsProto var3, boolean var4);
}
