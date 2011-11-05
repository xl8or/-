package com.android.email.mail;

import android.content.Context;
import com.android.email.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Body {

   InputStream getInputStream() throws MessagingException;

   void writeTo(Context var1, long var2, OutputStream var4) throws IOException, MessagingException;

   void writeTo(OutputStream var1) throws IOException, MessagingException;
}
