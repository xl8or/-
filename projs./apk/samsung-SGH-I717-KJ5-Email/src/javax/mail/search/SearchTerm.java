package javax.mail.search;

import java.io.Serializable;
import javax.mail.Message;

public abstract class SearchTerm implements Serializable {

   public SearchTerm() {}

   public abstract boolean match(Message var1);
}
