package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressVerificationNodeData;

public interface DataSource {

   AddressVerificationNodeData get(String var1);

   AddressVerificationNodeData getDefaultData(String var1);
}
