package com.htc.android.mail.mimemessage;

import com.htc.android.mail.mimemessage.Body;
import com.htc.android.mail.mimemessage.MessagingException;
import java.io.IOException;
import java.io.OutputStream;

public interface Part {

   void addHeader(String var1, String var2) throws MessagingException;

   Body getBody() throws MessagingException;

   String getContentId() throws MessagingException;

   String getContentType() throws MessagingException;

   String getDisposition() throws MessagingException;

   String getExtendedHeader(String var1) throws MessagingException;

   String[] getHeader(String var1) throws MessagingException;

   String getMimeType() throws MessagingException;

   int getSize() throws MessagingException;

   boolean isMimeType(String var1) throws MessagingException;

   void removeHeader(String var1) throws MessagingException;

   void setBody(Body var1) throws MessagingException;

   void setExtendedHeader(String var1, String var2) throws MessagingException;

   void setHeader(String var1, String var2) throws MessagingException;

   void writeTo(OutputStream var1) throws IOException, MessagingException;
}
