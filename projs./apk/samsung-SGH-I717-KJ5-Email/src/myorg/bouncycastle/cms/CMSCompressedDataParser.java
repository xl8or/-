package myorg.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import myorg.bouncycastle.asn1.ASN1OctetStringParser;
import myorg.bouncycastle.asn1.ASN1SequenceParser;
import myorg.bouncycastle.asn1.cms.CompressedDataParser;
import myorg.bouncycastle.asn1.cms.ContentInfoParser;
import myorg.bouncycastle.cms.CMSContentInfoParser;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSTypedStream;

public class CMSCompressedDataParser extends CMSContentInfoParser {

   public CMSCompressedDataParser(InputStream var1) throws CMSException {
      super(var1);
   }

   public CMSCompressedDataParser(byte[] var1) throws CMSException {
      ByteArrayInputStream var2 = new ByteArrayInputStream(var1);
      this((InputStream)var2);
   }

   public CMSTypedStream getContent() throws CMSException {
      try {
         ASN1SequenceParser var1 = (ASN1SequenceParser)this._contentInfo.getContent(16);
         ContentInfoParser var2 = (new CompressedDataParser(var1)).getEncapContentInfo();
         ASN1OctetStringParser var3 = (ASN1OctetStringParser)var2.getContent(4);
         String var4 = var2.getContentType().toString();
         InputStream var5 = var3.getOctetStream();
         InflaterInputStream var6 = new InflaterInputStream(var5);
         CMSTypedStream var7 = new CMSTypedStream(var4, var6);
         return var7;
      } catch (IOException var9) {
         throw new CMSException("IOException reading compressed content.", var9);
      }
   }
}
