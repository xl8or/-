package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.ASN1StreamParser;
import myorg.bouncycastle.asn1.cms.ContentInfoParser;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSUtils;

public class CMSContentInfoParser {

   protected ContentInfoParser _contentInfo;
   protected InputStream _data;


   protected CMSContentInfoParser(InputStream var1) throws CMSException {
      this._data = var1;

      try {
         int var2 = CMSUtils.getMaximumMemory();
         ASN1SequenceParser var3 = (ASN1SequenceParser)(new ASN1StreamParser(var1, var2)).readObject();
         ContentInfoParser var4 = new ContentInfoParser(var3);
         this._contentInfo = var4;
      } catch (IOException var7) {
         throw new CMSException("IOException reading content.", var7);
      } catch (ClassCastException var8) {
         throw new CMSException("Unexpected object reading content.", var8);
      }
   }

   public void close() throws IOException {
      this._data.close();
   }
}
