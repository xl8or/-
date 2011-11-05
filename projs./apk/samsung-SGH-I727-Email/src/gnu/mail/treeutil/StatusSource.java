package gnu.mail.treeutil;

import gnu.mail.treeutil.StatusListener;

public interface StatusSource {

   void addStatusListener(StatusListener var1);

   void removeStatusListener(StatusListener var1);
}
