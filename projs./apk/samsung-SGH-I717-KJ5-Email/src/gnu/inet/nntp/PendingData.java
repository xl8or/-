package gnu.inet.nntp;

import java.io.IOException;

public interface PendingData {

   void readToEOF() throws IOException;
}
