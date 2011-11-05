package gnu.inet.nntp;

import java.util.Iterator;

public interface Newsrc {

   void close();

   boolean isSeen(String var1, int var2);

   boolean isSubscribed(String var1);

   Iterator list();

   void setSeen(String var1, int var2, boolean var3);

   void setSubscribed(String var1, boolean var2);
}
