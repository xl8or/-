import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;

public class KeyStoreOf {
    public static void main(String args[])
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, InvalidKeySpecException, NoSuchProviderException {

        String keyPasswd = "android";
        String storePasswd = "android";
        String keyStoreName = System.getProperty("keystore");

        String keyFile = args[0];
        String certFile = args[1];
        String defaultAlias = "androiddebugkey";
        String alias = args.length < 3 ? defaultAlias : args[2];

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(toByteArray(new FileInputStream(keyFile)));
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        String X509_CERTIFICATE_TYPE = "X.509";
        CertificateFactory cf = CertificateFactory.getInstance(X509_CERTIFICATE_TYPE);
        Collection<? extends Certificate> c = cf.generateCertificates(new FileInputStream(certFile)) ;
        Certificate[] certs = c.toArray(new Certificate[c.size()]);

        KeyStore keyStore = KeyStore.getInstance("JKS" /* type */, "SUN" /* provider*/);
        keyStore.load(null, storePasswd.toCharArray());
        keyStore.setKeyEntry(alias, privateKey, keyPasswd.toCharArray(), certs);
        keyStore.store(null != keyStoreName ? new FileOutputStream(keyStoreName) : System.out, storePasswd.toCharArray());
    }

    private static final int BUF_SIZE = 0x1000; // 4K

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        return out.toByteArray();
    }

    public static long copy(InputStream from, OutputStream to) throws IOException {
        byte[] buf = new byte[BUF_SIZE];
        long total = 0;
        while (true) {
            int r = from.read(buf);
            if (r == -1) {
                break;
            }

            to.write(buf, 0, r);
            total += r;
        }

        return total;
    }
}

