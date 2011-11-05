package myorg.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Extension;
import java.util.Date;
import myorg.bouncycastle.x509.AttributeCertificateHolder;
import myorg.bouncycastle.x509.AttributeCertificateIssuer;
import myorg.bouncycastle.x509.X509Attribute;

public interface X509AttributeCertificate extends X509Extension {

   void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException;

   void checkValidity(Date var1) throws CertificateExpiredException, CertificateNotYetValidException;

   X509Attribute[] getAttributes();

   X509Attribute[] getAttributes(String var1);

   byte[] getEncoded() throws IOException;

   AttributeCertificateHolder getHolder();

   AttributeCertificateIssuer getIssuer();

   boolean[] getIssuerUniqueID();

   Date getNotAfter();

   Date getNotBefore();

   BigInteger getSerialNumber();

   byte[] getSignature();

   int getVersion();

   void verify(PublicKey var1, String var2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
}
