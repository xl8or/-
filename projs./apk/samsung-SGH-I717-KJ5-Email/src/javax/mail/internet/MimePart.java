package javax.mail.internet;

import java.util.Enumeration;
import javax.mail.MessagingException;
import javax.mail.Part;

public interface MimePart extends Part {

   void addHeaderLine(String var1) throws MessagingException;

   Enumeration getAllHeaderLines() throws MessagingException;

   String getContentID() throws MessagingException;

   String[] getContentLanguage() throws MessagingException;

   String getContentMD5() throws MessagingException;

   String getEncoding() throws MessagingException;

   String getHeader(String var1, String var2) throws MessagingException;

   Enumeration getMatchingHeaderLines(String[] var1) throws MessagingException;

   Enumeration getNonMatchingHeaderLines(String[] var1) throws MessagingException;

   void setContentLanguage(String[] var1) throws MessagingException;

   void setContentMD5(String var1) throws MessagingException;

   void setText(String var1) throws MessagingException;

   void setText(String var1, String var2) throws MessagingException;

   void setText(String var1, String var2, String var3) throws MessagingException;
}
