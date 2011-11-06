// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppConfigurationEntry.java

package org.apache.harmony.javax.security.auth.login;

import java.util.Collections;
import java.util.Map;

public class AppConfigurationEntry
{
    public static class LoginModuleControlFlag
    {

        public String toString()
        {
            return flag;
        }

        public static final LoginModuleControlFlag OPTIONAL = new LoginModuleControlFlag("LoginModuleControlFlag: optional");
        public static final LoginModuleControlFlag REQUIRED = new LoginModuleControlFlag("LoginModuleControlFlag: required");
        public static final LoginModuleControlFlag REQUISITE = new LoginModuleControlFlag("LoginModuleControlFlag: requisite");
        public static final LoginModuleControlFlag SUFFICIENT = new LoginModuleControlFlag("LoginModuleControlFlag: sufficient");
        private final String flag;


        private LoginModuleControlFlag(String s)
        {
            flag = s;
        }
    }


    public AppConfigurationEntry(String s, LoginModuleControlFlag loginmodulecontrolflag, Map map)
    {
        if(s == null || s.length() == 0)
            throw new IllegalArgumentException("auth.26");
        if(loginmodulecontrolflag == null)
            throw new IllegalArgumentException("auth.27");
        if(map == null)
        {
            throw new IllegalArgumentException("auth.1A");
        } else
        {
            loginModuleName = s;
            controlFlag = loginmodulecontrolflag;
            options = Collections.unmodifiableMap(map);
            return;
        }
    }

    public LoginModuleControlFlag getControlFlag()
    {
        return controlFlag;
    }

    public String getLoginModuleName()
    {
        return loginModuleName;
    }

    public Map getOptions()
    {
        return options;
    }

    private final LoginModuleControlFlag controlFlag;
    private final String loginModuleName;
    private final Map options;
}
