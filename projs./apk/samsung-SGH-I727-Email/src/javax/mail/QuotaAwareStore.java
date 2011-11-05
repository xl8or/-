package javax.mail;

import javax.mail.MessagingException;
import javax.mail.Quota;

public interface QuotaAwareStore {

   Quota[] getQuota(String var1) throws MessagingException;

   void setQuota(Quota var1) throws MessagingException;
}
