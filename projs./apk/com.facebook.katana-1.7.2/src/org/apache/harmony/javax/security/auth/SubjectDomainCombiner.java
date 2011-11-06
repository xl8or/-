// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SubjectDomainCombiner.java

package org.apache.harmony.javax.security.auth;

import java.security.*;
import java.util.Set;

// Referenced classes of package org.apache.harmony.javax.security.auth:
//            AuthPermission, Subject

public class SubjectDomainCombiner
    implements DomainCombiner
{

    public SubjectDomainCombiner(Subject subject1)
    {
        if(subject1 == null)
        {
            throw new NullPointerException();
        } else
        {
            subject = subject1;
            return;
        }
    }

    public ProtectionDomain[] combine(ProtectionDomain aprotectiondomain[], ProtectionDomain aprotectiondomain1[])
    {
        int i;
        ProtectionDomain aprotectiondomain2[];
        int j;
        Set set;
        Principal aprincipal[];
        if(aprotectiondomain != null)
            i = 0 + aprotectiondomain.length;
        else
            i = 0;
        if(aprotectiondomain1 != null)
            i += aprotectiondomain1.length;
        if(i != 0) goto _L2; else goto _L1
_L1:
        aprotectiondomain2 = null;
_L4:
        return aprotectiondomain2;
_L2:
        aprotectiondomain2 = new ProtectionDomain[i];
        if(aprotectiondomain != null)
        {
            set = subject.getPrincipals();
            aprincipal = (Principal[])set.toArray(new Principal[set.size()]);
            for(j = 0; j < aprotectiondomain.length; j++)
                if(aprotectiondomain[j] != null)
                    aprotectiondomain2[j] = new ProtectionDomain(aprotectiondomain[j].getCodeSource(), aprotectiondomain[j].getPermissions(), aprotectiondomain[j].getClassLoader(), aprincipal);

        } else
        {
            j = 0;
        }
        if(aprotectiondomain1 != null)
            System.arraycopy(aprotectiondomain1, 0, aprotectiondomain2, j, aprotectiondomain1.length);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public Subject getSubject()
    {
        SecurityManager securitymanager = System.getSecurityManager();
        if(securitymanager != null)
            securitymanager.checkPermission(_GET);
        return subject;
    }

    private static final AuthPermission _GET = new AuthPermission("getSubjectFromDomainCombiner");
    private Subject subject;

}
