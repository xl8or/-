package com.android.email.mail;

import com.android.email.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Body {

   InputStream getInputStream() throws MessagingException;

   void writeTo(OutputStream var1) throws IOException, MessagingException;
}
