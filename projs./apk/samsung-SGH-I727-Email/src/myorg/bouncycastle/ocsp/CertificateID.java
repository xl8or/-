package myorg.bouncycastle.ocsp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.ocsp.CertID;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.jce.PrincipalUtil;
import myorg.bouncycastle.ocsp.OCSPException;
import myorg.bouncycastle.ocsp.OCSPUtil;

public class CertificateID {

   public static final String HASH_SHA1 = "1.3.14.3.2.26";
   private final CertID id;


   public CertificateID(String var1, X509Certificate var2, BigInteger var3) throws OCSPException {
      this(var1, var2, var3, "myBC");
   }

   public CertificateID(String var1, X509Certificate var2, BigInteger var3, String var4) throws OCSPException {
      DERObjectIdentifier var5 = new DERObjectIdentifier(var1);
      DERNull var6 = DERNull.INSTANCE;
      AlgorithmIdentifier var7 = new AlgorithmIdentifier(var5, var6);
      DERInteger var8 = new DERInteger(var3);
      CertID var9 = createCertID(var7, var2, var8, var4);
      this.id = var9;
   }

   public CertificateID(CertID var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("\'id\' cannot be null");
      } else {
         this.id = var1;
      }
   }

   private static CertID createCertID(AlgorithmIdentifier var0, X509Certificate var1, DERInteger var2, String var3) throws OCSPException {
      try {
         MessageDigest var4 = OCSPUtil.createDigestInstance(var0.getObjectId().getId(), var3);
         byte[] var5 = PrincipalUtil.getSubjectX509Principal(var1).getEncoded();
         var4.update(var5);
         byte[] var6 = var4.digest();
         DEROctetString var7 = new DEROctetString(var6);
         byte[] var8 = var1.getPublicKey().getEncoded();
         byte[] var9 = SubjectPublicKeyInfo.getInstance((new ASN1InputStream(var8)).readObject()).getPublicKeyData().getBytes();
         var4.update(var9);
         byte[] var10 = var4.digest();
         DEROctetString var11 = new DEROctetString(var10);
         CertID var12 = new CertID(var0, var7, var11, var2);
         return var12;
      } catch (Exception var15) {
         String var14 = "problem creating ID: " + var15;
         throw new OCSPException(var14, var15);
      }
   }

   public boolean equals(Object var1) {
      byte var2;
      if(!(var1 instanceof CertificateID)) {
         var2 = 0;
      } else {
         CertificateID var3 = (CertificateID)var1;
         DERObject var4 = this.id.getDERObject();
         DERObject var5 = var3.id.getDERObject();
         var2 = var4.equals(var5);
      }

      return (boolean)var2;
   }

   public String getHashAlgOID() {
      return this.id.getHashAlgorithm().getObjectId().getId();
   }

   public byte[] getIssuerKeyHash() {
      return this.id.getIssuerKeyHash().getOctets();
   }

   public byte[] getIssuerNameHash() {
      return this.id.getIssuerNameHash().getOctets();
   }

   public BigInteger getSerialNumber() {
      return this.id.getSerialNumber().getValue();
   }

   public int hashCode() {
      return this.id.getDERObject().hashCode();
   }

   public boolean matchesIssuer(X509Certificate var1, String var2) throws OCSPException {
      AlgorithmIdentifier var3 = this.id.getHashAlgorithm();
      DERInteger var4 = this.id.getSerialNumber();
      CertID var5 = createCertID(var3, var1, var4, var2);
      CertID var6 = this.id;
      return var5.equals(var6);
   }

   public CertID toASN1Object() {
      return this.id;
   }
}
