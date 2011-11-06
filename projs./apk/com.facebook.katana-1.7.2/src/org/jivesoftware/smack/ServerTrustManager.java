// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerTrustManager.java

package org.jivesoftware.smack;

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.X509TrustManager;

// Referenced classes of package org.jivesoftware.smack:
//            ConnectionConfiguration

class ServerTrustManager
    implements X509TrustManager
{

    public ServerTrustManager(String s, ConnectionConfiguration connectionconfiguration)
    {
        configuration = connectionconfiguration;
        server = s;
        FileInputStream fileinputstream;
        trustStore = KeyStore.getInstance(connectionconfiguration.getTruststoreType());
        fileinputstream = new FileInputStream(connectionconfiguration.getTruststorePath());
        trustStore.load(fileinputstream, connectionconfiguration.getTruststorePassword().toCharArray());
        if(fileinputstream == null)
            break MISSING_BLOCK_LABEL_64;
        fileinputstream.close();
_L1:
        return;
        Exception exception2;
        exception2;
        Exception exception3;
        fileinputstream = null;
        exception3 = exception2;
_L3:
        exception3.printStackTrace();
        connectionconfiguration.setVerifyRootCAEnabled(false);
        if(fileinputstream != null)
            try
            {
                fileinputstream.close();
            }
            catch(IOException ioexception1) { }
          goto _L1
        Exception exception;
        exception;
        Exception exception1;
        fileinputstream = null;
        exception1 = exception;
_L2:
        IOException ioexception2;
        if(fileinputstream != null)
            try
            {
                fileinputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception1;
        ioexception2;
          goto _L1
        exception1;
          goto _L2
        exception3;
          goto _L3
    }

    public static List getPeerIdentity(X509Certificate x509certificate)
    {
        Object obj = getSubjectAlternativeNames(x509certificate);
        if(((List) (obj)).isEmpty())
        {
            String s = x509certificate.getSubjectDN().getName();
            Matcher matcher = cnPattern.matcher(s);
            if(matcher.find())
                s = matcher.group(2);
            ArrayList arraylist = new ArrayList();
            arraylist.add(s);
            obj = arraylist;
        }
        return ((List) (obj));
    }

    private static List getSubjectAlternativeNames(X509Certificate x509certificate)
    {
        Object obj = new ArrayList();
        List list;
        if(x509certificate.getSubjectAlternativeNames() != null)
            break MISSING_BLOCK_LABEL_21;
        list = Collections.emptyList();
        obj = list;
_L2:
        return ((List) (obj));
        CertificateParsingException certificateparsingexception;
        certificateparsingexception;
        certificateparsingexception.printStackTrace();
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void checkClientTrusted(X509Certificate ax509certificate[], String s)
        throws CertificateException
    {
    }

    public void checkServerTrusted(X509Certificate ax509certificate[], String s)
        throws CertificateException
    {
        int i;
        List list;
label0:
        {
            i = ax509certificate.length;
            list = getPeerIdentity(ax509certificate[0]);
            if(!configuration.isVerifyChainEnabled())
                break label0;
            int k = i - 1;
            Principal principal = null;
            int l = k;
            do
            {
                if(l < 0)
                    break label0;
                X509Certificate x509certificate = ax509certificate[l];
                Principal principal1 = x509certificate.getIssuerDN();
                Principal principal2 = x509certificate.getSubjectDN();
                if(principal != null)
                {
                    if(!principal1.equals(principal))
                        break;
                    int i1 = l + 1;
                    try
                    {
                        java.security.PublicKey publickey = ax509certificate[i1].getPublicKey();
                        ax509certificate[l].verify(publickey);
                    }
                    catch(GeneralSecurityException generalsecurityexception1)
                    {
                        throw new CertificateException((new StringBuilder()).append("signature verification failed of ").append(list).toString());
                    }
                }
                l--;
                principal = principal2;
            } while(true);
            throw new CertificateException((new StringBuilder()).append("subject/issuer verification failed of ").append(list).toString());
        }
        if(!configuration.isVerifyRootCAEnabled()) goto _L2; else goto _L1
_L1:
        String s2 = trustStore.getCertificateAlias(ax509certificate[i - 1]);
        boolean flag1;
        if(s2 != null)
            flag1 = true;
        else
            flag1 = false;
        if(flag1 || i != 1)
            break MISSING_BLOCK_LABEL_246;
        if(!configuration.isSelfSignedCertificateEnabled())
            break MISSING_BLOCK_LABEL_246;
        System.out.println((new StringBuilder()).append("Accepting self-signed certificate of remote server: ").append(list).toString());
        flag1 = true;
_L3:
        if(!flag1)
            throw new CertificateException((new StringBuilder()).append("root certificate not trusted of ").append(list).toString());
        break; /* Loop/switch isn't completed */
        KeyStoreException keystoreexception;
        keystoreexception;
        boolean flag = false;
_L4:
        keystoreexception.printStackTrace();
        flag1 = flag;
        if(true) goto _L3; else goto _L2
_L2:
        if(configuration.isNotMatchingDomainCheckEnabled())
            if(list.size() == 1 && ((String)list.get(0)).startsWith("*."))
            {
                String s1 = ((String)list.get(0)).replace("*.", "");
                if(!server.endsWith(s1))
                    throw new CertificateException((new StringBuilder()).append("target verification failed of ").append(list).toString());
            } else
            if(!list.contains(server))
                throw new CertificateException((new StringBuilder()).append("target verification failed of ").append(list).toString());
        if(configuration.isExpiredCertificatesCheckEnabled())
        {
            Date date = new Date();
            int j = 0;
            while(j < i) 
            {
                try
                {
                    ax509certificate[j].checkValidity(date);
                }
                catch(GeneralSecurityException generalsecurityexception)
                {
                    throw new CertificateException((new StringBuilder()).append("invalid date of ").append(server).toString());
                }
                j++;
            }
        }
        return;
        KeyStoreException keystoreexception1;
        keystoreexception1;
        flag = flag1;
        keystoreexception = keystoreexception1;
          goto _L4
    }

    public X509Certificate[] getAcceptedIssuers()
    {
        return new X509Certificate[0];
    }

    private static Pattern cnPattern = Pattern.compile("(?i)(cn=)([^,]*)");
    private ConnectionConfiguration configuration;
    private String server;
    private KeyStore trustStore;

}
