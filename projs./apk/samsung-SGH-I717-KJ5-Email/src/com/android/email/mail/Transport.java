package com.android.email.mail;

import com.android.email.mail.CertificateValidationException;
import com.android.email.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URI;

public interface Transport {

   int CONNECTION_SECURITY_NONE = 0;
   int CONNECTION_SECURITY_SSL = 1;
   int CONNECTION_SECURITY_TLS = 2;


   boolean canTrustAllCertificates();

   boolean canTrySslSecurity();

   boolean canTryTlsSecurity();

   void close();

   String getHost();

   InputStream getInputStream();

   OutputStream getOutputStream();

   int getPort();

   int getSecurity();

   String[] getUserInfoParts();

   boolean isOpen();

   Transport newInstanceWithConfiguration();

   void open() throws MessagingException, CertificateValidationException;

   String readLine() throws IOException;

   void reopenTls() throws MessagingException;

   void setSecurity(int var1, boolean var2);

   void setSoTimeout(int var1) throws SocketException;

   void setUri(URI var1, int var2);

   void writeLine(String var1, String var2) throws IOException;
}
