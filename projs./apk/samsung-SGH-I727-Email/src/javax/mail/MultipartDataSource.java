package javax.mail;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;

public interface MultipartDataSource extends DataSource {

   BodyPart getBodyPart(int var1) throws MessagingException;

   int getCount();
}
