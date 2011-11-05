package com.htc.android.mail.mimemessage;

import com.htc.android.mail.mimemessage.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Body {

   InputStream getInputStream() throws MessagingException;

   void writeTo(OutputStream var1) throws IOException, MessagingException;
}
