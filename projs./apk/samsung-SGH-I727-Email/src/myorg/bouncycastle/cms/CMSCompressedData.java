package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.cms.CompressedData;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSUtils;

public class CMSCompressedData {

   ContentInfo contentInfo;


   public CMSCompressedData(InputStream var1) throws CMSException {
      ContentInfo var2 = CMSUtils.readContentInfo(var1);
      this(var2);
   }

   public CMSCompressedData(ContentInfo var1) throws CMSException {
      this.contentInfo = var1;
   }

   public CMSCompressedData(byte[] var1) throws CMSException {
      ContentInfo var2 = CMSUtils.readContentInfo(var1);
      this(var2);
   }

   public byte[] getContent() throws CMSException {
      InputStream var1 = ((ASN1OctetString)CompressedData.getInstance(this.contentInfo.getContent()).getEncapContentInfo().getContent()).getOctetStream();
      InflaterInputStream var2 = new InflaterInputStream(var1);

      try {
         byte[] var3 = CMSUtils.streamToByteArray(var2);
         return var3;
      } catch (IOException var5) {
         throw new CMSException("exception reading compressed stream.", var5);
      }
   }

   public byte[] getContent(int var1) throws CMSException {
      InputStream var2 = ((ASN1OctetString)CompressedData.getInstance(this.contentInfo.getContent()).getEncapContentInfo().getContent()).getOctetStream();
      InflaterInputStream var3 = new InflaterInputStream(var2);

      try {
         byte[] var4 = CMSUtils.streamToByteArray(var3, var1);
         return var4;
      } catch (IOException var6) {
         throw new CMSException("exception reading compressed stream.", var6);
      }
   }

   public ContentInfo getContentInfo() {
      return this.contentInfo;
   }

   public byte[] getEncoded() throws IOException {
      return this.contentInfo.getEncoded();
   }
}
