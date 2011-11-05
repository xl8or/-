package myorg.bouncycastle.ocsp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1OutputStream;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import myorg.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import myorg.bouncycastle.asn1.ocsp.OCSPResponse;
import myorg.bouncycastle.asn1.ocsp.ResponseBytes;
import myorg.bouncycastle.ocsp.BasicOCSPResp;
import myorg.bouncycastle.ocsp.OCSPException;

public class OCSPResp {

   private OCSPResponse resp;


   public OCSPResp(InputStream var1) throws IOException {
      ASN1InputStream var2 = new ASN1InputStream(var1);
      this(var2);
   }

   private OCSPResp(ASN1InputStream var1) throws IOException {
      try {
         OCSPResponse var2 = OCSPResponse.getInstance(var1.readObject());
         this.resp = var2;
      } catch (IllegalArgumentException var11) {
         StringBuilder var4 = (new StringBuilder()).append("malformed response: ");
         String var5 = var11.getMessage();
         String var6 = var4.append(var5).toString();
         throw new IOException(var6);
      } catch (ClassCastException var12) {
         StringBuilder var8 = (new StringBuilder()).append("malformed response: ");
         String var9 = var12.getMessage();
         String var10 = var8.append(var9).toString();
         throw new IOException(var10);
      }
   }

   public OCSPResp(OCSPResponse var1) {
      this.resp = var1;
   }

   public OCSPResp(byte[] var1) throws IOException {
      ASN1InputStream var2 = new ASN1InputStream(var1);
      this(var2);
   }

   public boolean equals(Object var1) {
      byte var2;
      if(var1 == this) {
         var2 = 1;
      } else if(!(var1 instanceof OCSPResp)) {
         var2 = 0;
      } else {
         OCSPResp var3 = (OCSPResp)var1;
         OCSPResponse var4 = this.resp;
         OCSPResponse var5 = var3.resp;
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   public byte[] getEncoded() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      ASN1OutputStream var2 = new ASN1OutputStream(var1);
      OCSPResponse var3 = this.resp;
      var2.writeObject(var3);
      return var1.toByteArray();
   }

   public Object getResponseObject() throws OCSPException {
      ResponseBytes var1 = this.resp.getResponseBytes();
      Object var2;
      if(var1 == null) {
         var2 = null;
      } else {
         DERObjectIdentifier var3 = var1.getResponseType();
         DERObjectIdentifier var4 = OCSPObjectIdentifiers.id_pkix_ocsp_basic;
         if(var3.equals(var4)) {
            try {
               byte[] var5 = var1.getResponse().getOctets();
               BasicOCSPResponse var6 = BasicOCSPResponse.getInstance((new ASN1InputStream(var5)).readObject());
               var2 = new BasicOCSPResp(var6);
            } catch (Exception var9) {
               String var8 = "problem decoding object: " + var9;
               throw new OCSPException(var8, var9);
            }
         } else {
            var2 = var1.getResponse();
         }
      }

      return var2;
   }

   public int getStatus() {
      return this.resp.getResponseStatus().getValue().intValue();
   }

   public int hashCode() {
      return this.resp.hashCode();
   }
}
