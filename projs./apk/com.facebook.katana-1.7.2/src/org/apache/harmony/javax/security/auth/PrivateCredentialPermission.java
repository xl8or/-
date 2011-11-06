// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrivateCredentialPermission.java

package org.apache.harmony.javax.security.auth;

import java.io.*;
import java.lang.reflect.Array;
import java.security.*;
import java.util.Iterator;
import java.util.Set;

public final class PrivateCredentialPermission extends Permission
{
    private static final class CredOwner
        implements Serializable
    {

        public boolean equals(Object obj)
        {
            boolean flag;
            if(obj == this)
                flag = true;
            else
            if(obj instanceof CredOwner)
            {
                CredOwner credowner = (CredOwner)obj;
                if(principalClass.equals(credowner.principalClass) && principalName.equals(credowner.principalName))
                    flag = true;
                else
                    flag = false;
            } else
            {
                flag = false;
            }
            return flag;
        }

        public int hashCode()
        {
            return principalClass.hashCode() + principalName.hashCode();
        }

        boolean implies(Object obj)
        {
            boolean flag;
            if(obj == this)
            {
                flag = true;
            } else
            {
                CredOwner credowner = (CredOwner)obj;
                if((isClassWildcard || principalClass.equals(credowner.principalClass)) && (isPNameWildcard || principalName.equals(credowner.principalName)))
                    flag = true;
                else
                    flag = false;
            }
            return flag;
        }

        private static final long serialVersionUID = 0xb9037436L;
        private transient boolean isClassWildcard;
        private transient boolean isPNameWildcard;
        String principalClass;
        String principalName;

        CredOwner(String s, String s1)
        {
            if("*".equals(s))
                isClassWildcard = true;
            if("*".equals(s1))
                isPNameWildcard = true;
            if(isClassWildcard && !isPNameWildcard)
            {
                throw new IllegalArgumentException("auth.12");
            } else
            {
                principalClass = s;
                principalName = s1;
                return;
            }
        }
    }


    public PrivateCredentialPermission(String s, String s1)
    {
        super(s);
        if("read".equalsIgnoreCase(s1))
        {
            initTargetName(s);
            return;
        } else
        {
            throw new IllegalArgumentException("auth.11");
        }
    }

    PrivateCredentialPermission(String s, Set set1)
    {
        Iterator iterator;
        super(s);
        credentialClass = s;
        set = new CredOwner[set1.size()];
        iterator = set1.iterator();
_L5:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        CredOwner credowner;
        int i;
        Principal principal = (Principal)iterator.next();
        credowner = new CredOwner(principal.getClass().getName(), principal.getName());
        i = 0;
_L6:
        if(i >= offset)
            break MISSING_BLOCK_LABEL_146;
        if(!set[i].equals(credowner)) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L7:
        if(!flag)
        {
            CredOwner acredowner[] = set;
            int j = offset;
            offset = j + 1;
            acredowner[j] = credowner;
        }
          goto _L5
_L4:
        i++;
          goto _L6
_L2:
        return;
        flag = false;
          goto _L7
    }

    private void initTargetName(String s)
    {
        String s1;
        int i1;
        int k1;
        int l1;
        if(s == null)
            throw new NullPointerException("auth.0E");
        s1 = s.trim();
        if(s1.length() == 0)
            throw new IllegalArgumentException("auth.0F");
        int i = s1.indexOf(' ');
        if(i == -1)
            throw new IllegalArgumentException("auth.10");
        credentialClass = s1.substring(0, i);
        int j = i + 1;
        int k = s1.length();
        int l = j;
        for(i1 = 0; l < k; i1++)
        {
            int i3 = s1.indexOf(' ', l);
            int j3 = s1.indexOf('"', i3 + 2);
            if(i3 == -1 || j3 == -1 || s1.charAt(i3 + 1) != '"')
                throw new IllegalArgumentException("auth.10");
            l = j3 + 2;
        }

        if(i1 < 1)
            throw new IllegalArgumentException("auth.10");
        int j1 = 1 + s1.indexOf(' ');
        set = new CredOwner[i1];
        k1 = j1;
        l1 = 0;
_L5:
        if(l1 >= i1) goto _L2; else goto _L1
_L1:
        int j2;
        CredOwner credowner;
        int k2;
        int i2 = s1.indexOf(' ', k1);
        j2 = s1.indexOf('"', i2 + 2);
        credowner = new CredOwner(s1.substring(k1, i2), s1.substring(i2 + 2, j2));
        k2 = 0;
_L6:
        if(k2 >= offset)
            break MISSING_BLOCK_LABEL_344;
        if(!set[k2].equals(credowner)) goto _L4; else goto _L3
_L3:
        boolean flag = true;
_L7:
        if(!flag)
        {
            CredOwner acredowner[] = set;
            int l2 = offset;
            offset = l2 + 1;
            acredowner[l2] = credowner;
        }
        k1 = j2 + 2;
        l1++;
          goto _L5
_L4:
        k2++;
          goto _L6
_L2:
        return;
        flag = false;
          goto _L7
    }

    private void readObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        objectinputstream.defaultReadObject();
        initTargetName(getName());
    }

    private boolean sameMembers(Object aobj[], Object aobj1[], int i)
    {
        boolean flag;
        if(aobj == null && aobj1 == null)
        {
            flag = true;
        } else
        {
label0:
            {
                if(aobj != null && aobj1 != null)
                    break label0;
                flag = false;
            }
        }
_L7:
        return flag;
        int j = 0;
_L9:
        if(j >= i) goto _L2; else goto _L1
_L1:
        int k = 0;
_L8:
        if(k >= i)
            break MISSING_BLOCK_LABEL_98;
        if(!aobj[j].equals(aobj1[k])) goto _L4; else goto _L3
_L3:
        boolean flag1 = true;
_L10:
        if(flag1) goto _L6; else goto _L5
_L5:
        flag = false;
          goto _L7
_L4:
        k++;
          goto _L8
_L6:
        j++;
          goto _L9
_L2:
        flag = true;
          goto _L7
        flag1 = false;
          goto _L10
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == this)
            flag = true;
        else
        if(obj == null || getClass() != obj.getClass())
        {
            flag = false;
        } else
        {
            PrivateCredentialPermission privatecredentialpermission = (PrivateCredentialPermission)obj;
            if(credentialClass.equals(privatecredentialpermission.credentialClass) && offset == privatecredentialpermission.offset && sameMembers(set, privatecredentialpermission.set, offset))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public String getActions()
    {
        return "read";
    }

    public String getCredentialClass()
    {
        return credentialClass;
    }

    public String[][] getPrincipals()
    {
        int i = offset;
        int ai[] = new int[2];
        ai[0] = i;
        ai[1] = 2;
        String as[][] = (String[][])Array.newInstance(java/lang/String, ai);
        for(int j = 0; j < as.length; j++)
        {
            as[j][0] = set[j].principalClass;
            as[j][1] = set[j].principalName;
        }

        return as;
    }

    public int hashCode()
    {
        int i = 0;
        int j = i;
        for(; i < offset; i++)
            j += set[i].hashCode();

        return j + getCredentialClass().hashCode();
    }

    public boolean implies(Permission permission)
    {
        if(permission != null && getClass() == permission.getClass()) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        PrivateCredentialPermission privatecredentialpermission = (PrivateCredentialPermission)permission;
        if(!"*".equals(credentialClass) && !credentialClass.equals(privatecredentialpermission.getCredentialClass()))
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        if(privatecredentialpermission.offset == 0)
        {
            flag = true;
            continue; /* Loop/switch isn't completed */
        }
        CredOwner acredowner[] = set;
        CredOwner acredowner1[] = privatecredentialpermission.set;
        int i = offset;
        int j = privatecredentialpermission.offset;
label0:
        for(int k = 0; k < i; k++)
        {
            int l = 0;
            do
            {
                if(l >= j || acredowner[k].implies(acredowner1[l]))
                {
                    if(l != acredowner1.length)
                        continue label0;
                    flag = false;
                    continue; /* Loop/switch isn't completed */
                }
                l++;
            } while(true);
        }

        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public PermissionCollection newPermissionCollection()
    {
        return null;
    }

    private static final String READ = "read";
    private static final long serialVersionUID = 0x7b507f4cL;
    private String credentialClass;
    private transient int offset;
    private transient CredOwner set[];
}
