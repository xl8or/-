package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPublicKeySpec;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.DSAParameter;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.crypto.params.DSAPublicKeyParameters;

public class JDKDSAPublicKey implements DSAPublicKey {

   private static final long serialVersionUID = 1752452449903495175L;
   private DSAParams dsaSpec;
   private BigInteger y;


   JDKDSAPublicKey(BigInteger var1, DSAParameterSpec var2) {
      this.y = var1;
      this.dsaSpec = var2;
   }

   JDKDSAPublicKey(DSAPublicKey var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      DSAParams var3 = var1.getParams();
      this.dsaSpec = var3;
   }

   JDKDSAPublicKey(DSAPublicKeySpec var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      BigInteger var3 = var1.getP();
      BigInteger var4 = var1.getQ();
      BigInteger var5 = var1.getG();
      DSAParameterSpec var6 = new DSAParameterSpec(var3, var4, var5);
      this.dsaSpec = var6;
   }

   JDKDSAPublicKey(SubjectPublicKeyInfo var1) {
      DERInteger var2;
      try {
         var2 = (DERInteger)var1.getPublicKey();
      } catch (IOException var12) {
         throw new IllegalArgumentException("invalid info structure in DSA public key");
      }

      BigInteger var3 = var2.getValue();
      this.y = var3;
      DEREncodable var4 = var1.getAlgorithmId().getParameters();
      if(this.isNotNull(var4)) {
         ASN1Sequence var5 = (ASN1Sequence)var1.getAlgorithmId().getParameters();
         DSAParameter var6 = new DSAParameter(var5);
         BigInteger var7 = var6.getP();
         BigInteger var8 = var6.getQ();
         BigInteger var9 = var6.getG();
         DSAParameterSpec var10 = new DSAParameterSpec(var7, var8, var9);
         this.dsaSpec = var10;
      }
   }

   JDKDSAPublicKey(DSAPublicKeyParameters var1) {
      BigInteger var2 = var1.getY();
      this.y = var2;
      BigInteger var3 = var1.getParameters().getP();
      BigInteger var4 = var1.getParameters().getQ();
      BigInteger var5 = var1.getParameters().getG();
      DSAParameterSpec var6 = new DSAParameterSpec(var3, var4, var5);
      this.dsaSpec = var6;
   }

   private boolean isNotNull(DEREncodable var1) {
      boolean var2;
      if(var1 != null && !DERNull.INSTANCE.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      BigInteger var2 = (BigInteger)var1.readObject();
      this.y = var2;
      BigInteger var3 = (BigInteger)var1.readObject();
      BigInteger var4 = (BigInteger)var1.readObject();
      BigInteger var5 = (BigInteger)var1.readObject();
      DSAParameterSpec var6 = new DSAParameterSpec(var3, var4, var5);
      this.dsaSpec = var6;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      BigInteger var2 = this.y;
      var1.writeObject(var2);
      BigInteger var3 = this.dsaSpec.getP();
      var1.writeObject(var3);
      BigInteger var4 = this.dsaSpec.getQ();
      var1.writeObject(var4);
      BigInteger var5 = this.dsaSpec.getG();
      var1.writeObject(var5);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof DSAPublicKey)) {
         var2 = false;
      } else {
         DSAPublicKey var3 = (DSAPublicKey)var1;
         BigInteger var4 = this.getY();
         BigInteger var5 = var3.getY();
         if(var4.equals(var5)) {
            BigInteger var6 = this.getParams().getG();
            BigInteger var7 = var3.getParams().getG();
            if(var6.equals(var7)) {
               BigInteger var8 = this.getParams().getP();
               BigInteger var9 = var3.getParams().getP();
               if(var8.equals(var9)) {
                  BigInteger var10 = this.getParams().getQ();
                  BigInteger var11 = var3.getParams().getQ();
                  if(var10.equals(var11)) {
                     var2 = true;
                     return var2;
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getAlgorithm() {
      return "DSA";
   }

   public byte[] getEncoded() {
      byte[] var5;
      if(this.dsaSpec == null) {
         DERObjectIdentifier var1 = X9ObjectIdentifiers.id_dsa;
         AlgorithmIdentifier var2 = new AlgorithmIdentifier(var1);
         BigInteger var3 = this.y;
         DERInteger var4 = new DERInteger(var3);
         var5 = (new SubjectPublicKeyInfo(var2, var4)).getDEREncoded();
      } else {
         DERObjectIdentifier var6 = X9ObjectIdentifiers.id_dsa;
         BigInteger var7 = this.dsaSpec.getP();
         BigInteger var8 = this.dsaSpec.getQ();
         BigInteger var9 = this.dsaSpec.getG();
         DERObject var10 = (new DSAParameter(var7, var8, var9)).getDERObject();
         AlgorithmIdentifier var11 = new AlgorithmIdentifier(var6, var10);
         BigInteger var12 = this.y;
         DERInteger var13 = new DERInteger(var12);
         var5 = (new SubjectPublicKeyInfo(var11, var13)).getDEREncoded();
      }

      return var5;
   }

   public String getFormat() {
      return "X.509";
   }

   public DSAParams getParams() {
      return this.dsaSpec;
   }

   public BigInteger getY() {
      return this.y;
   }

   public int hashCode() {
      int var1 = this.getY().hashCode();
      int var2 = this.getParams().getG().hashCode();
      int var3 = var1 ^ var2;
      int var4 = this.getParams().getP().hashCode();
      int var5 = var3 ^ var4;
      int var6 = this.getParams().getQ().hashCode();
      return var5 ^ var6;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = var1.append("DSA Public Key").append(var2);
      StringBuffer var4 = var1.append("            y: ");
      String var5 = this.getY().toString(16);
      StringBuffer var6 = var4.append(var5).append(var2);
      return var1.toString();
   }
}
