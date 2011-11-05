package org.apache.commons.httpclient.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;

public interface ProtocolSocketFactory {

   Socket createSocket(String var1, int var2) throws IOException, UnknownHostException;

   Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws IOException, UnknownHostException;

   Socket createSocket(String var1, int var2, InetAddress var3, int var4, HttpConnectionParams var5) throws IOException, UnknownHostException, ConnectTimeoutException;
}
