package com.google.android.finsky.billing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

public interface BillingFlowContext {

   void hideFragment(Fragment var1, boolean var2);

   void persistFragment(Bundle var1, String var2, Fragment var3);

   void popFragmentStack();

   Fragment restoreFragment(Bundle var1, String var2);

   void showDialogFragment(DialogFragment var1, String var2);

   void showFragment(Fragment var1, int var2, boolean var3);

   void startActivityForResult(Intent var1, int var2);
}
