// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Configuration.java

package org.apache.harmony.javax.security.auth.login;

import org.apache.harmony.javax.security.auth.AuthPermission;

// Referenced classes of package org.apache.harmony.javax.security.auth.login:
//            AppConfigurationEntry

public abstract class Configuration
{

    protected Configuration()
    {
    }

    static Configuration getAccessibleConfiguration()
    {
        Configuration configuration1;
        configuration1 = configuration;
        if(configuration1 != null)
            break MISSING_BLOCK_LABEL_41;
        org/apache/harmony/javax/security/auth/login/Configuration;
        JVM INSTR monitorenter ;
        if(configuration == null)
            configuration = getDefaultProvider();
        Configuration configuration2 = configuration;
        configuration1 = configuration2;
        return configuration1;
    }

    public static Configuration getConfiguration()
    {
        SecurityManager securitymanager = System.getSecurityManager();
        if(securitymanager != null)
            securitymanager.checkPermission(GET_LOGIN_CONFIGURATION);
        return getAccessibleConfiguration();
    }

    private static final Configuration getDefaultProvider()
    {
        return new Configuration() {

            public AppConfigurationEntry[] getAppConfigurationEntry(String s)
            {
                return new AppConfigurationEntry[0];
            }

            public void refresh()
            {
            }

        }
;
    }

    public static void setConfiguration(Configuration configuration1)
    {
        SecurityManager securitymanager = System.getSecurityManager();
        if(securitymanager != null)
            securitymanager.checkPermission(SET_LOGIN_CONFIGURATION);
        configuration = configuration1;
    }

    public abstract AppConfigurationEntry[] getAppConfigurationEntry(String s);

    public abstract void refresh();

    private static final AuthPermission GET_LOGIN_CONFIGURATION = new AuthPermission("getLoginConfiguration");
    private static final String LOGIN_CONFIGURATION_PROVIDER = "login.configuration.provider";
    private static final AuthPermission SET_LOGIN_CONFIGURATION = new AuthPermission("setLoginConfiguration");
    private static Configuration configuration;

}
