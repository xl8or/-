package com.google.android.finsky.utils;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionException;
import com.android.volley.Request;
import com.google.android.finsky.utils.BgDataDisabledException;

public class DenyAllNetwork implements Network {

   public DenyAllNetwork() {}

   public NetworkResponse performRequest(Request<?> var1) throws NoConnectionException {
      throw new BgDataDisabledException();
   }
}
