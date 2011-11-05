package myorg.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.asn1.cms.CompressedData;
import myorg.bouncycastle.asn1.cms.ContentInfo;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.cms.CMSCompressedData;
import myorg.bouncycastle.cms.CMSException;
import myorg.bouncycastle.cms.CMSProcessable;

public class CMSCompressedDataGenerator {

   public static final String ZLIB = "1.2.840.113549.1.9.16.3.8";


   public CMSCompressedDataGenerator() {}

   public CMSCompressedData generate(CMSProcessable var1, String var2) throws CMSException {
      AlgorithmIdentifier var6;
      BERConstructedOctetString var8;
      try {
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         DeflaterOutputStream var4 = new DeflaterOutputStream(var3);
         var1.write(var4);
         var4.close();
         DERObjectIdentifier var5 = new DERObjectIdentifier(var2);
         var6 = new AlgorithmIdentifier(var5);
         byte[] var7 = var3.toByteArray();
         var8 = new BERConstructedOctetString(var7);
      } catch (IOException var15) {
         throw new CMSException("exception encoding data.", var15);
      }

      DERObjectIdentifier var9 = CMSObjectIdentifiers.data;
      ContentInfo var10 = new ContentInfo(var9, var8);
      DERObjectIdentifier var11 = CMSObjectIdentifiers.compressedData;
      CompressedData var12 = new CompressedData(var6, var10);
      ContentInfo var13 = new ContentInfo(var11, var12);
      return new CMSCompressedData(var13);
   }
}
