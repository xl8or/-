package com.google.android.finsky.services;

import com.android.volley.Response;
import com.google.protobuf.micro.MessageMicro;

public interface CombinedResponseListener<T extends MessageMicro> extends Response.Listener<T>, Response.ErrorListener {
}
