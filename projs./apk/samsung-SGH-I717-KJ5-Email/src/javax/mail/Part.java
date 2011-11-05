package javax.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Multipart;

public interface Part {

   String ATTACHMENT = "attachment";
   String INLINE = "inline";


   void addHeader(String var1, String var2) throws MessagingException;

   Enumeration getAllHeaders() throws MessagingException;

   Object getContent() throws IOException, MessagingException;

   String getContentType() throws MessagingException;

   DataHandler getDataHandler() throws MessagingException;

   String getDescription() throws MessagingException;

   String getDisposition() throws MessagingException;

   String getFileName() throws MessagingException;

   String[] getHeader(String var1) throws MessagingException;

   InputStream getInputStream() throws IOException, MessagingException;

   int getLineCount() throws MessagingException;

   Enumeration getMatchingHeaders(String[] var1) throws MessagingException;

   Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException;

   int getSize() throws MessagingException;

   boolean isMimeType(String var1) throws MessagingException;

   void removeHeader(String var1) throws MessagingException;

   void setContent(Object var1, String var2) throws MessagingException;

   void setContent(Multipart var1) throws MessagingException;

   void setDataHandler(DataHandler var1) throws MessagingException;

   void setDescription(String var1) throws MessagingException;

   void setDisposition(String var1) throws MessagingException;

   void setFileName(String var1) throws MessagingException;

   void setHeader(String var1, String var2) throws MessagingException;

   void setText(String var1) throws MessagingException;

   void writeTo(OutputStream var1) throws IOException, MessagingException;
}
