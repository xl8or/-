// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoginContext.java

package org.apache.harmony.javax.security.auth.login;

import java.io.IOException;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.harmony.javax.security.auth.AuthPermission;
import org.apache.harmony.javax.security.auth.Subject;
import org.apache.harmony.javax.security.auth.callback.*;
import org.apache.harmony.javax.security.auth.spi.LoginModule;

// Referenced classes of package org.apache.harmony.javax.security.auth.login:
//            LoginException, Configuration, AppConfigurationEntry

public class LoginContext
{
    private final class Module
    {

        void create(Subject subject1, CallbackHandler callbackhandler, Map map)
            throws LoginException
        {
            String s = entry.getLoginModuleName();
            if(klass == null)
                try
                {
                    klass = Class.forName(s, false, contextClassLoader);
                }
                catch(ClassNotFoundException classnotfoundexception)
                {
                    throw (LoginException)(new LoginException((new StringBuilder()).append("auth.39 ").append(s).toString())).initCause(classnotfoundexception);
                }
            if(module == null)
            {
                try
                {
                    module = (LoginModule)klass.newInstance();
                }
                catch(IllegalAccessException illegalaccessexception)
                {
                    throw (LoginException)(new LoginException((new StringBuilder()).append("auth.3A ").append(s).toString())).initCause(illegalaccessexception);
                }
                catch(InstantiationException instantiationexception)
                {
                    throw (LoginException)(new LoginException((new StringBuilder()).append("auth.3A").append(s).toString())).initCause(instantiationexception);
                }
                module.initialize(subject1, callbackhandler, map, entry.getOptions());
            }
        }

        int getFlag()
        {
            return flag;
        }

        AppConfigurationEntry entry;
        int flag;
        Class klass;
        LoginModule module;
        final LoginContext this$0;

        Module(AppConfigurationEntry appconfigurationentry)
        {
            this$0 = LoginContext.this;
            super();
            entry = appconfigurationentry;
            AppConfigurationEntry.LoginModuleControlFlag loginmodulecontrolflag = appconfigurationentry.getControlFlag();
            if(loginmodulecontrolflag == AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL)
                flag = 0;
            else
            if(loginmodulecontrolflag == AppConfigurationEntry.LoginModuleControlFlag.REQUISITE)
                flag = 2;
            else
            if(loginmodulecontrolflag == AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT)
                flag = 3;
            else
                flag = 1;
        }
    }

    private class ContextedCallbackHandler
        implements CallbackHandler
    {

        public void handle(final Callback callbacks[])
            throws IOException, UnsupportedCallbackException
        {
            try
            {
                AccessController.doPrivileged(new PrivilegedExceptionAction() {

                    public volatile Object run()
                        throws Exception
                    {
                        return run();
                    }

                    public Void run()
                        throws IOException, UnsupportedCallbackException
                    {
                        hiddenHandlerRef.handle(callbacks);
                        return null;
                    }

                    final ContextedCallbackHandler this$1;
                    final Callback val$callbacks[];

                
                {
                    this$1 = ContextedCallbackHandler.this;
                    callbacks = acallback;
                    super();
                }
                }
, userContext);
                return;
            }
            catch(PrivilegedActionException privilegedactionexception)
            {
                if(privilegedactionexception.getCause() instanceof UnsupportedCallbackException)
                    throw (UnsupportedCallbackException)privilegedactionexception.getCause();
                else
                    throw (IOException)privilegedactionexception.getCause();
            }
        }

        private final CallbackHandler hiddenHandlerRef;
        final LoginContext this$0;


        ContextedCallbackHandler(CallbackHandler callbackhandler)
        {
            this$0 = LoginContext.this;
            super();
            hiddenHandlerRef = callbackhandler;
        }
    }


    public LoginContext(String s)
        throws LoginException
    {
        init(s, null, null, null);
    }

    public LoginContext(String s, Subject subject1)
        throws LoginException
    {
        if(subject1 == null)
        {
            throw new LoginException("auth.03");
        } else
        {
            init(s, subject1, null, null);
            return;
        }
    }

    public LoginContext(String s, Subject subject1, CallbackHandler callbackhandler)
        throws LoginException
    {
        if(subject1 == null)
            throw new LoginException("auth.03");
        if(callbackhandler == null)
        {
            throw new LoginException("auth.34");
        } else
        {
            init(s, subject1, callbackhandler, null);
            return;
        }
    }

    public LoginContext(String s, Subject subject1, CallbackHandler callbackhandler, Configuration configuration)
        throws LoginException
    {
        init(s, subject1, callbackhandler, configuration);
    }

    public LoginContext(String s, CallbackHandler callbackhandler)
        throws LoginException
    {
        if(callbackhandler == null)
        {
            throw new LoginException("auth.34");
        } else
        {
            init(s, null, callbackhandler, null);
            return;
        }
    }

    private void init(String s, Subject subject1, final CallbackHandler cbHandler, Configuration configuration)
        throws LoginException
    {
        subject = subject1;
        boolean flag;
        if(subject1 != null)
            flag = true;
        else
            flag = false;
        userProvidedSubject = flag;
        if(s == null)
            throw new LoginException("auth.00");
        Configuration configuration1;
        SecurityManager securitymanager;
        AppConfigurationEntry aappconfigurationentry[];
        AppConfigurationEntry aappconfigurationentry1[];
        if(configuration == null)
        {
            configuration1 = Configuration.getAccessibleConfiguration();
        } else
        {
            userProvidedConfig = true;
            configuration1 = configuration;
        }
        securitymanager = System.getSecurityManager();
        if(securitymanager != null && !userProvidedConfig)
            securitymanager.checkPermission(new AuthPermission((new StringBuilder()).append("createLoginContext.").append(s).toString()));
        aappconfigurationentry = configuration1.getAppConfigurationEntry(s);
        if(aappconfigurationentry == null)
        {
            if(securitymanager != null && !userProvidedConfig)
                securitymanager.checkPermission(new AuthPermission("createLoginContext.other"));
            aappconfigurationentry1 = configuration1.getAppConfigurationEntry("other");
            if(aappconfigurationentry1 == null)
                throw new LoginException((new StringBuilder()).append("auth.35 ").append(s).toString());
        } else
        {
            aappconfigurationentry1 = aappconfigurationentry;
        }
        modules = new Module[aappconfigurationentry1.length];
        for(int i = 0; i < modules.length; i++)
            modules[i] = new Module(aappconfigurationentry1[i]);

        try
        {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {

                public volatile Object run()
                    throws Exception
                {
                    return run();
                }

                public Void run()
                    throws Exception
                {
                    contextClassLoader = Thread.currentThread().getContextClassLoader();
                    if(contextClassLoader == null)
                        contextClassLoader = ClassLoader.getSystemClassLoader();
                    if(cbHandler != null) goto _L2; else goto _L1
_L1:
                    String s1 = Security.getProperty("auth.login.defaultCallbackHandler");
                    if(s1 != null && s1.length() != 0) goto _L4; else goto _L3
_L3:
                    Void void1 = null;
_L5:
                    return void1;
_L4:
                    Class class1 = Class.forName(s1, true, contextClassLoader);
                    callbackHandler = (CallbackHandler)class1.newInstance();
_L6:
                    void1 = null;
                    if(true) goto _L5; else goto _L2
_L2:
                    callbackHandler = cbHandler;
                      goto _L6
                }

                final LoginContext this$0;
                final CallbackHandler val$cbHandler;

            
            {
                this$0 = LoginContext.this;
                cbHandler = callbackhandler;
                super();
            }
            }
);
        }
        catch(PrivilegedActionException privilegedactionexception)
        {
            Throwable throwable = privilegedactionexception.getCause();
            throw (LoginException)(new LoginException("auth.36")).initCause(throwable);
        }
        if(!userProvidedConfig) goto _L2; else goto _L1
_L1:
        userContext = AccessController.getContext();
_L4:
        return;
_L2:
        if(callbackHandler != null)
        {
            userContext = AccessController.getContext();
            callbackHandler = new ContextedCallbackHandler(callbackHandler);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void loginImpl()
        throws LoginException
    {
        int ai[];
        int ai1[];
        Module amodule[];
        int i;
        Throwable throwable;
        int j;
        if(subject == null)
            subject = new Subject();
        if(sharedState == null)
            sharedState = new HashMap();
        ai = new int[4];
        ai1 = new int[4];
        amodule = modules;
        i = amodule.length;
        throwable = null;
        j = 0;
_L11:
        Module module2;
        if(j >= i)
            break MISSING_BLOCK_LABEL_638;
        module2 = amodule[j];
        module2.create(subject, callbackHandler, sharedState);
        if(!module2.module.login()) goto _L2; else goto _L1
_L1:
        int l2;
        int j2 = module2.getFlag();
        ai1[j2] = 1 + ai1[j2];
        int k2 = module2.getFlag();
        ai[k2] = 1 + ai[k2];
        l2 = module2.getFlag();
        if(l2 != 3) goto _L2; else goto _L3
_L3:
        Throwable throwable1 = throwable;
_L9:
        if(ai[1] != ai1[1]) goto _L5; else goto _L4
_L4:
        if(ai[2] == ai1[2]) goto _L7; else goto _L6
_L6:
        boolean flag = true;
_L15:
        int ai2[];
        ai2 = new int[4];
        ai1[3] = 0;
        ai1[2] = 0;
        ai1[1] = 0;
        ai1[0] = 0;
        if(!flag)
        {
            Module amodule2[] = modules;
            int i1 = amodule2.length;
            Throwable throwable5 = throwable1;
            int j1 = 0;
            do
            {
                if(j1 >= i1)
                    break;
                Module module1 = amodule2[j1];
                if(module1.klass != null)
                {
                    int k1 = module1.getFlag();
                    ai1[k1] = 1 + ai1[k1];
                    Throwable throwable7;
                    int i2;
                    try
                    {
                        module1.module.commit();
                        int l1 = module1.getFlag();
                        ai2[l1] = 1 + ai2[l1];
                    }
                    catch(Throwable throwable6)
                    {
                        if(throwable5 == null)
                            throwable5 = throwable6;
                    }
                }
                j1++;
            } while(true);
            throwable1 = throwable5;
        }
          goto _L8
        throwable7;
label0:
        {
            if(throwable == null)
                throwable = throwable7;
            if(module2.klass != null)
                break label0;
            ai1[1] = 1 + ai1[1];
            throwable1 = throwable;
        }
          goto _L9
        i2 = module2.getFlag();
        ai1[i2] = 1 + ai1[i2];
        if(module2.getFlag() != 2) goto _L2; else goto _L10
_L10:
        throwable1 = throwable;
          goto _L9
_L2:
        j++;
          goto _L11
_L7:
        if(ai1[1] != 0 || ai1[2] != 0) goto _L13; else goto _L12
_L12:
        if(ai[0] == 0 && ai[3] == 0) goto _L5; else goto _L14
_L14:
        flag = false;
          goto _L15
_L13:
        flag = false;
          goto _L15
_L8:
        if(ai2[1] != ai1[1]) goto _L17; else goto _L16
_L16:
        if(ai2[2] == ai1[2]) goto _L19; else goto _L18
_L18:
        boolean flag1 = true;
_L20:
        if(flag1)
        {
            Module amodule1[] = modules;
            int k = amodule1.length;
            Throwable throwable2 = throwable1;
            int l = 0;
            do
            {
                if(l >= k)
                    break;
                Module module = amodule1[l];
                try
                {
                    module.module.abort();
                }
                catch(Throwable throwable4)
                {
                    if(throwable2 == null)
                        throwable2 = throwable4;
                }
                l++;
            } while(true);
            Throwable throwable3;
            if((throwable2 instanceof PrivilegedActionException) && throwable2.getCause() != null)
                throwable3 = throwable2.getCause();
            else
                throwable3 = throwable2;
            if(throwable3 instanceof LoginException)
                throw (LoginException)throwable3;
            else
                throw (LoginException)(new LoginException("auth.37")).initCause(throwable3);
        } else
        {
            loggedIn = true;
            return;
        }
_L19:
        if(ai1[1] == 0 && ai1[2] == 0)
        {
            if(ai2[0] != 0 || ai2[3] != 0)
            {
                flag1 = false;
                continue; /* Loop/switch isn't completed */
            }
        } else
        {
            flag1 = false;
            continue; /* Loop/switch isn't completed */
        }
_L17:
        flag1 = true;
        if(true) goto _L20; else goto _L5
_L5:
        flag = true;
          goto _L15
        throwable1 = throwable;
          goto _L9
    }

    private void logoutImpl()
        throws LoginException
    {
        boolean flag;
        Module amodule[];
        int i;
        Throwable throwable;
        int j;
        flag = false;
        if(subject == null)
            throw new LoginException("auth.38");
        loggedIn = flag;
        amodule = modules;
        i = amodule.length;
        throwable = null;
        j = ((flag) ? 1 : 0);
_L2:
        Module module;
        if(j >= i)
            break; /* Loop/switch isn't completed */
        module = amodule[j];
        module.module.logout();
        flag++;
_L3:
        j++;
        if(true) goto _L2; else goto _L1
        Throwable throwable2;
        throwable2;
        if(throwable == null)
            throwable = throwable2;
          goto _L3
_L1:
        if(throwable != null || !flag)
        {
            Throwable throwable1;
            if((throwable instanceof PrivilegedActionException) && throwable.getCause() != null)
                throwable1 = throwable.getCause();
            else
                throwable1 = throwable;
            if(throwable1 instanceof LoginException)
                throw (LoginException)throwable1;
            else
                throw (LoginException)(new LoginException("auth.37")).initCause(throwable1);
        } else
        {
            return;
        }
    }

    public Subject getSubject()
    {
        Subject subject1;
        if(userProvidedSubject || loggedIn)
            subject1 = subject;
        else
            subject1 = null;
        return subject1;
    }

    public void login()
        throws LoginException
    {
        PrivilegedExceptionAction privilegedexceptionaction = new PrivilegedExceptionAction() {

            public volatile Object run()
                throws Exception
            {
                return run();
            }

            public Void run()
                throws LoginException
            {
                loginImpl();
                return null;
            }

            final LoginContext this$0;

            
            {
                this$0 = LoginContext.this;
                super();
            }
        }
;
        try
        {
            if(userProvidedConfig)
                AccessController.doPrivileged(privilegedexceptionaction, userContext);
            else
                AccessController.doPrivileged(privilegedexceptionaction);
        }
        catch(PrivilegedActionException privilegedactionexception)
        {
            throw (LoginException)privilegedactionexception.getException();
        }
    }

    public void logout()
        throws LoginException
    {
        PrivilegedExceptionAction privilegedexceptionaction = new PrivilegedExceptionAction() {

            public volatile Object run()
                throws Exception
            {
                return run();
            }

            public Void run()
                throws LoginException
            {
                logoutImpl();
                return null;
            }

            final LoginContext this$0;

            
            {
                this$0 = LoginContext.this;
                super();
            }
        }
;
        try
        {
            if(userProvidedConfig)
                AccessController.doPrivileged(privilegedexceptionaction, userContext);
            else
                AccessController.doPrivileged(privilegedexceptionaction);
        }
        catch(PrivilegedActionException privilegedactionexception)
        {
            throw (LoginException)privilegedactionexception.getException();
        }
    }

    private static final String DEFAULT_CALLBACK_HANDLER_PROPERTY = "auth.login.defaultCallbackHandler";
    private static final int OPTIONAL = 0;
    private static final int REQUIRED = 1;
    private static final int REQUISITE = 2;
    private static final int SUFFICIENT = 3;
    private CallbackHandler callbackHandler;
    private ClassLoader contextClassLoader;
    private boolean loggedIn;
    private Module modules[];
    private Map sharedState;
    private Subject subject;
    private AccessControlContext userContext;
    private boolean userProvidedConfig;
    private boolean userProvidedSubject;



/*
    static ClassLoader access$002(LoginContext logincontext, ClassLoader classloader)
    {
        logincontext.contextClassLoader = classloader;
        return classloader;
    }

*/


/*
    static CallbackHandler access$102(LoginContext logincontext, CallbackHandler callbackhandler)
    {
        logincontext.callbackHandler = callbackhandler;
        return callbackhandler;
    }

*/



}
