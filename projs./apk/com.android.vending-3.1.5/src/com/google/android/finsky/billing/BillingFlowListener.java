package com.google.android.finsky.billing;

import com.google.android.finsky.billing.BillingFlow;

public interface BillingFlowListener {

   void onError(BillingFlow var1, String var2);

   void onFinished(BillingFlow var1, boolean var2);
}
