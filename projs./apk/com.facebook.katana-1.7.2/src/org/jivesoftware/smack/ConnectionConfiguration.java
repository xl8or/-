// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConnectionConfiguration.java

package org.jivesoftware.smack;

import java.io.File;
import javax.net.SocketFactory;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.jivesoftware.smack.proxy.ProxyInfo;
import org.jivesoftware.smack.util.DNSUtil;

// Referenced classes of package org.jivesoftware.smack:
//            Connection

public class ConnectionConfiguration
    implements Cloneable
{
    public static final class SecurityMode extends Enum
    {

        public static SecurityMode valueOf(String s)
        {
            return (SecurityMode)Enum.valueOf(org/jivesoftware/smack/ConnectionConfiguration$SecurityMode, s);
        }

        public static SecurityMode[] values()
        {
            return (SecurityMode[])$VALUES.clone();
        }

        private static final SecurityMode $VALUES[];
        public static final SecurityMode disabled;
        public static final SecurityMode enabled;
        public static final SecurityMode required;

        static 
        {
            required = new SecurityMode("required", 0);
            enabled = new SecurityMode("enabled", 1);
            disabled = new SecurityMode("disabled", 2);
            SecurityMode asecuritymode[] = new SecurityMode[3];
            asecuritymode[0] = required;
            asecuritymode[1] = enabled;
            asecuritymode[2] = disabled;
            $VALUES = asecuritymode;
        }

        private SecurityMode(String s, int i)
        {
            super(s, i);
        }
    }


    public ConnectionConfiguration(String s)
    {
        verifyChainEnabled = false;
        verifyRootCAEnabled = false;
        selfSignedCertificateEnabled = false;
        expiredCertificatesCheckEnabled = false;
        notMatchingDomainCheckEnabled = false;
        isRosterVersioningAvailable = false;
        capsNode = null;
        compressionEnabled = false;
        saslAuthenticationEnabled = true;
        debuggerEnabled = Connection.DEBUG_ENABLED;
        reconnectionAllowed = true;
        sendPresence = true;
        rosterLoadedAtLogin = true;
        securityMode = SecurityMode.enabled;
        org.jivesoftware.smack.util.DNSUtil.HostAddress hostaddress = DNSUtil.resolveXMPPDomain(s);
        init(hostaddress.getHost(), hostaddress.getPort(), s, ProxyInfo.forDefaultProxy());
    }

    public ConnectionConfiguration(String s, int i)
    {
        verifyChainEnabled = false;
        verifyRootCAEnabled = false;
        selfSignedCertificateEnabled = false;
        expiredCertificatesCheckEnabled = false;
        notMatchingDomainCheckEnabled = false;
        isRosterVersioningAvailable = false;
        capsNode = null;
        compressionEnabled = false;
        saslAuthenticationEnabled = true;
        debuggerEnabled = Connection.DEBUG_ENABLED;
        reconnectionAllowed = true;
        sendPresence = true;
        rosterLoadedAtLogin = true;
        securityMode = SecurityMode.enabled;
        init(s, i, s, ProxyInfo.forDefaultProxy());
    }

    public ConnectionConfiguration(String s, int i, String s1)
    {
        verifyChainEnabled = false;
        verifyRootCAEnabled = false;
        selfSignedCertificateEnabled = false;
        expiredCertificatesCheckEnabled = false;
        notMatchingDomainCheckEnabled = false;
        isRosterVersioningAvailable = false;
        capsNode = null;
        compressionEnabled = false;
        saslAuthenticationEnabled = true;
        debuggerEnabled = Connection.DEBUG_ENABLED;
        reconnectionAllowed = true;
        sendPresence = true;
        rosterLoadedAtLogin = true;
        securityMode = SecurityMode.enabled;
        init(s, i, s1, ProxyInfo.forDefaultProxy());
    }

    public ConnectionConfiguration(String s, int i, String s1, ProxyInfo proxyinfo)
    {
        verifyChainEnabled = false;
        verifyRootCAEnabled = false;
        selfSignedCertificateEnabled = false;
        expiredCertificatesCheckEnabled = false;
        notMatchingDomainCheckEnabled = false;
        isRosterVersioningAvailable = false;
        capsNode = null;
        compressionEnabled = false;
        saslAuthenticationEnabled = true;
        debuggerEnabled = Connection.DEBUG_ENABLED;
        reconnectionAllowed = true;
        sendPresence = true;
        rosterLoadedAtLogin = true;
        securityMode = SecurityMode.enabled;
        init(s, i, s1, proxyinfo);
    }

    public ConnectionConfiguration(String s, int i, ProxyInfo proxyinfo)
    {
        verifyChainEnabled = false;
        verifyRootCAEnabled = false;
        selfSignedCertificateEnabled = false;
        expiredCertificatesCheckEnabled = false;
        notMatchingDomainCheckEnabled = false;
        isRosterVersioningAvailable = false;
        capsNode = null;
        compressionEnabled = false;
        saslAuthenticationEnabled = true;
        debuggerEnabled = Connection.DEBUG_ENABLED;
        reconnectionAllowed = true;
        sendPresence = true;
        rosterLoadedAtLogin = true;
        securityMode = SecurityMode.enabled;
        init(s, i, s, proxyinfo);
    }

    public ConnectionConfiguration(String s, ProxyInfo proxyinfo)
    {
        verifyChainEnabled = false;
        verifyRootCAEnabled = false;
        selfSignedCertificateEnabled = false;
        expiredCertificatesCheckEnabled = false;
        notMatchingDomainCheckEnabled = false;
        isRosterVersioningAvailable = false;
        capsNode = null;
        compressionEnabled = false;
        saslAuthenticationEnabled = true;
        debuggerEnabled = Connection.DEBUG_ENABLED;
        reconnectionAllowed = true;
        sendPresence = true;
        rosterLoadedAtLogin = true;
        securityMode = SecurityMode.enabled;
        org.jivesoftware.smack.util.DNSUtil.HostAddress hostaddress = DNSUtil.resolveXMPPDomain(s);
        init(hostaddress.getHost(), hostaddress.getPort(), s, proxyinfo);
    }

    private void init(String s, int i, String s1, ProxyInfo proxyinfo)
    {
        host = s;
        port = i;
        serviceName = s1;
        proxy = proxyinfo;
        String s2 = System.getProperty("java.home");
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(s2).append(File.separator).append("lib");
        stringbuilder.append(File.separator).append("security");
        stringbuilder.append(File.separator).append("cacerts");
        truststorePath = stringbuilder.toString();
        truststoreType = "jks";
        truststorePassword = "changeit";
        keystorePath = System.getProperty("javax.net.ssl.keyStore");
        keystoreType = "jks";
        pkcs11Library = "pkcs11.config";
        socketFactory = proxyinfo.getSocketFactory();
    }

    public CallbackHandler getCallbackHandler()
    {
        return callbackHandler;
    }

    String getCapsNode()
    {
        return capsNode;
    }

    public String getHost()
    {
        return host;
    }

    public String getKeystorePath()
    {
        return keystorePath;
    }

    public String getKeystoreType()
    {
        return keystoreType;
    }

    public String getPKCS11Library()
    {
        return pkcs11Library;
    }

    String getPassword()
    {
        return password;
    }

    public int getPort()
    {
        return port;
    }

    String getResource()
    {
        return resource;
    }

    public SecurityMode getSecurityMode()
    {
        return securityMode;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public SocketFactory getSocketFactory()
    {
        return socketFactory;
    }

    public String getTruststorePassword()
    {
        return truststorePassword;
    }

    public String getTruststorePath()
    {
        return truststorePath;
    }

    public String getTruststoreType()
    {
        return truststoreType;
    }

    String getUsername()
    {
        return username;
    }

    public boolean isCompressionEnabled()
    {
        return compressionEnabled;
    }

    public boolean isDebuggerEnabled()
    {
        return debuggerEnabled;
    }

    public boolean isExpiredCertificatesCheckEnabled()
    {
        return expiredCertificatesCheckEnabled;
    }

    public boolean isNotMatchingDomainCheckEnabled()
    {
        return notMatchingDomainCheckEnabled;
    }

    public boolean isReconnectionAllowed()
    {
        return reconnectionAllowed;
    }

    public boolean isRosterLoadedAtLogin()
    {
        return rosterLoadedAtLogin;
    }

    boolean isRosterVersioningAvailable()
    {
        return isRosterVersioningAvailable;
    }

    public boolean isSASLAuthenticationEnabled()
    {
        return saslAuthenticationEnabled;
    }

    public boolean isSelfSignedCertificateEnabled()
    {
        return selfSignedCertificateEnabled;
    }

    boolean isSendPresence()
    {
        return sendPresence;
    }

    public boolean isVerifyChainEnabled()
    {
        return verifyChainEnabled;
    }

    public boolean isVerifyRootCAEnabled()
    {
        return verifyRootCAEnabled;
    }

    public void setCallbackHandler(CallbackHandler callbackhandler)
    {
        callbackHandler = callbackhandler;
    }

    void setCapsNode(String s)
    {
        capsNode = s;
    }

    public void setCompressionEnabled(boolean flag)
    {
        compressionEnabled = flag;
    }

    public void setDebuggerEnabled(boolean flag)
    {
        debuggerEnabled = flag;
    }

    public void setExpiredCertificatesCheckEnabled(boolean flag)
    {
        expiredCertificatesCheckEnabled = flag;
    }

    public void setKeystorePath(String s)
    {
        keystorePath = s;
    }

    public void setKeystoreType(String s)
    {
        keystoreType = s;
    }

    void setLoginInfo(String s, String s1, String s2)
    {
        username = s;
        password = s1;
        resource = s2;
    }

    public void setNotMatchingDomainCheckEnabled(boolean flag)
    {
        notMatchingDomainCheckEnabled = flag;
    }

    public void setPKCS11Library(String s)
    {
        pkcs11Library = s;
    }

    public void setReconnectionAllowed(boolean flag)
    {
        reconnectionAllowed = flag;
    }

    public void setRosterLoadedAtLogin(boolean flag)
    {
        rosterLoadedAtLogin = flag;
    }

    void setRosterVersioningAvailable(boolean flag)
    {
        isRosterVersioningAvailable = flag;
    }

    public void setSASLAuthenticationEnabled(boolean flag)
    {
        saslAuthenticationEnabled = flag;
    }

    public void setSecurityMode(SecurityMode securitymode)
    {
        securityMode = securitymode;
    }

    public void setSelfSignedCertificateEnabled(boolean flag)
    {
        selfSignedCertificateEnabled = flag;
    }

    public void setSendPresence(boolean flag)
    {
        sendPresence = flag;
    }

    public void setServiceName(String s)
    {
        serviceName = s;
    }

    public void setSocketFactory(SocketFactory socketfactory)
    {
        socketFactory = socketfactory;
    }

    public void setTruststorePassword(String s)
    {
        truststorePassword = s;
    }

    public void setTruststorePath(String s)
    {
        truststorePath = s;
    }

    public void setTruststoreType(String s)
    {
        truststoreType = s;
    }

    public void setVerifyChainEnabled(boolean flag)
    {
        verifyChainEnabled = flag;
    }

    public void setVerifyRootCAEnabled(boolean flag)
    {
        verifyRootCAEnabled = flag;
    }

    private CallbackHandler callbackHandler;
    private String capsNode;
    private boolean compressionEnabled;
    private boolean debuggerEnabled;
    private boolean expiredCertificatesCheckEnabled;
    private String host;
    private boolean isRosterVersioningAvailable;
    private String keystorePath;
    private String keystoreType;
    private boolean notMatchingDomainCheckEnabled;
    private String password;
    private String pkcs11Library;
    private int port;
    protected ProxyInfo proxy;
    private boolean reconnectionAllowed;
    private String resource;
    private boolean rosterLoadedAtLogin;
    private boolean saslAuthenticationEnabled;
    private SecurityMode securityMode;
    private boolean selfSignedCertificateEnabled;
    private boolean sendPresence;
    private String serviceName;
    private SocketFactory socketFactory;
    private String truststorePassword;
    private String truststorePath;
    private String truststoreType;
    private String username;
    private boolean verifyChainEnabled;
    private boolean verifyRootCAEnabled;
}
