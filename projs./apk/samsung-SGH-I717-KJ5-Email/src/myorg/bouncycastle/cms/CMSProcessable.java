package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import myorg.bouncycastle.cms.CMSException;

public interface CMSProcessable {

   Object getContent();

   void write(OutputStream var1) throws IOException, CMSException;
}
