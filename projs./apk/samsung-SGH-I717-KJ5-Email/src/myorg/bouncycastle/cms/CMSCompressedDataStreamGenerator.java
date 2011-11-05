package myorg.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import myorg.bouncycastle.asn1.BERSequenceGenerator;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequenceGenerator;
import myorg.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import myorg.bouncycastle.cms.CMSUtils;

public class CMSCompressedDataStreamGenerator {

   public static final String ZLIB = "1.2.840.113549.1.9.16.3.8";
   private int _bufferSize;


   public CMSCompressedDataStreamGenerator() {}

   public OutputStream open(OutputStream var1, String var2) throws IOException {
      String var3 = CMSObjectIdentifiers.data.getId();
      return this.open(var1, var3, var2);
   }

   public OutputStream open(OutputStream var1, String var2, String var3) throws IOException {
      BERSequenceGenerator var4 = new BERSequenceGenerator(var1);
      DERObjectIdentifier var5 = CMSObjectIdentifiers.compressedData;
      var4.addObject(var5);
      OutputStream var6 = var4.getRawOutputStream();
      BERSequenceGenerator var7 = new BERSequenceGenerator(var6, 0, (boolean)1);
      DERInteger var8 = new DERInteger(0);
      var7.addObject(var8);
      OutputStream var9 = var7.getRawOutputStream();
      DERSequenceGenerator var10 = new DERSequenceGenerator(var9);
      DERObjectIdentifier var11 = new DERObjectIdentifier("1.2.840.113549.1.9.16.3.8");
      var10.addObject(var11);
      var10.close();
      OutputStream var12 = var7.getRawOutputStream();
      BERSequenceGenerator var13 = new BERSequenceGenerator(var12);
      DERObjectIdentifier var14 = new DERObjectIdentifier(var2);
      var13.addObject(var14);
      OutputStream var15 = var13.getRawOutputStream();
      int var16 = this._bufferSize;
      OutputStream var17 = CMSUtils.createBEROctetOutputStream(var15, 0, (boolean)1, var16);
      DeflaterOutputStream var18 = new DeflaterOutputStream(var17);
      return new CMSCompressedDataStreamGenerator.CmsCompressedOutputStream(var18, var4, var7, var13);
   }

   public void setBufferSize(int var1) {
      this._bufferSize = var1;
   }

   private class CmsCompressedOutputStream extends OutputStream {

      private BERSequenceGenerator _cGen;
      private BERSequenceGenerator _eiGen;
      private DeflaterOutputStream _out;
      private BERSequenceGenerator _sGen;


      CmsCompressedOutputStream(DeflaterOutputStream var2, BERSequenceGenerator var3, BERSequenceGenerator var4, BERSequenceGenerator var5) {
         this._out = var2;
         this._sGen = var3;
         this._cGen = var4;
         this._eiGen = var5;
      }

      public void close() throws IOException {
         this._out.close();
         this._eiGen.close();
         this._cGen.close();
         this._sGen.close();
      }

      public void write(int var1) throws IOException {
         this._out.write(var1);
      }

      public void write(byte[] var1) throws IOException {
         this._out.write(var1);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this._out.write(var1, var2, var3);
      }
   }
}
