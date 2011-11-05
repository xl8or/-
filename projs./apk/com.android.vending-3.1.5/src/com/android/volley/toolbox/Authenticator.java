package com.android.volley.toolbox;

import com.android.volley.AuthFailureException;

public interface Authenticator {

   String getAuthToken() throws AuthFailureException;

   String getAuthToken(String var1) throws AuthFailureException;

   void invalidateAuthToken(String var1);
}
