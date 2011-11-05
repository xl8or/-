package javax.mail;

import javax.mail.MessageContext;

public interface MessageAware {

   MessageContext getMessageContext();
}
