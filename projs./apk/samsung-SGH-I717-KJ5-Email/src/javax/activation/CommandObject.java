package javax.activation;

import java.io.IOException;
import javax.activation.DataHandler;

public interface CommandObject {

   void setCommandContext(String var1, DataHandler var2) throws IOException;
}
