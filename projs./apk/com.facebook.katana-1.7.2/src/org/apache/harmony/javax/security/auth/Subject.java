// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Subject.java

package org.apache.harmony.javax.security.auth;

import java.io.*;
import java.security.*;
import java.util.*;

// Referenced classes of package org.apache.harmony.javax.security.auth:
//            AuthPermission, SubjectDomainCombiner, PrivateCredentialPermission

public final class Subject
    implements Serializable
{
    private final class SecureSet extends AbstractSet
        implements Serializable
    {
        private class SecureIterator
            implements Iterator
        {

            public boolean hasNext()
            {
                return iterator.hasNext();
            }

            public Object next()
            {
                return iterator.next();
            }

            public void remove()
            {
                checkState();
                Subject.checkPermission(permission);
                iterator.remove();
            }

            protected Iterator iterator;
            final SecureSet this$1;

            protected SecureIterator(Iterator iterator1)
            {
                this$1 = SecureSet.this;
                super();
                iterator = iterator1;
            }
        }


        private void readObject(ObjectInputStream objectinputstream)
            throws IOException, ClassNotFoundException
        {
            objectinputstream.defaultReadObject();
            setType;
            JVM INSTR tableswitch 0 2: default 36
        //                       0 44
        //                       1 81
        //                       2 91;
               goto _L1 _L2 _L3 _L4
_L1:
            throw new IllegalArgumentException();
_L2:
            permission = Subject._PRINCIPALS;
_L6:
            for(Iterator iterator1 = elements.iterator(); iterator1.hasNext(); verifyElement(iterator1.next()));
            break; /* Loop/switch isn't completed */
_L3:
            permission = Subject._PRIVATE_CREDENTIALS;
            continue; /* Loop/switch isn't completed */
_L4:
            permission = Subject._PUBLIC_CREDENTIALS;
            if(true) goto _L6; else goto _L5
_L5:
        }

        private void verifyElement(Object obj)
        {
            if(obj == null)
                throw new NullPointerException();
            if(permission == Subject._PRINCIPALS && !java/security/Principal.isAssignableFrom(obj.getClass()))
                throw new IllegalArgumentException("auth.0B");
            else
                return;
        }

        private void writeObject(ObjectOutputStream objectoutputstream)
            throws IOException
        {
            if(permission == Subject._PRIVATE_CREDENTIALS)
            {
                for(Iterator iterator1 = iterator(); iterator1.hasNext(); iterator1.next());
                setType = 1;
            } else
            if(permission == Subject._PRINCIPALS)
                setType = 0;
            else
                setType = 2;
            objectoutputstream.defaultWriteObject();
        }

        public boolean add(Object obj)
        {
            verifyElement(obj);
            checkState();
            Subject.checkPermission(permission);
            boolean flag;
            if(!elements.contains(obj))
            {
                elements.add(obj);
                flag = true;
            } else
            {
                flag = false;
            }
            return flag;
        }

        protected final Set get(final Class c)
        {
            if(c == null)
                throw new NullPointerException();
            AbstractSet abstractset = new AbstractSet() {

                public boolean add(Object obj1)
                {
                    if(!c.isAssignableFrom(obj1.getClass()))
                        throw new IllegalArgumentException((new StringBuilder()).append("auth.0C ").append(c.getName()).toString());
                    boolean flag;
                    if(elements.contains(obj1))
                    {
                        flag = false;
                    } else
                    {
                        elements.add(obj1);
                        flag = true;
                    }
                    return flag;
                }

                public Iterator iterator()
                {
                    return elements.iterator();
                }

                public boolean retainAll(Collection collection)
                {
                    if(collection == null)
                        throw new NullPointerException();
                    else
                        return super.retainAll(collection);
                }

                public int size()
                {
                    return elements.size();
                }

                private LinkedList elements;
                final SecureSet this$1;
                final Class val$c;

                
                {
                    this$1 = SecureSet.this;
                    c = class1;
                    super();
                    elements = new LinkedList();
                }
            }
;
            Iterator iterator1 = iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                Object obj = iterator1.next();
                if(c.isAssignableFrom(obj.getClass()))
                    abstractset.add(c.cast(obj));
            } while(true);
            return abstractset;
        }

        public Iterator iterator()
        {
            Object obj;
            if(permission == Subject._PRIVATE_CREDENTIALS)
                obj = new SecureIterator(elements.iterator()) {

                    public Object next()
                    {
                        Object obj1 = iterator.next();
                        Subject.checkPermission(new PrivateCredentialPermission(obj1.getClass().getName(), principals));
                        return obj1;
                    }

                    final SecureSet this$1;

                
                {
                    this$1 = SecureSet.this;
                    super(iterator1);
                }
                }
;
            else
                obj = new SecureIterator(elements.iterator());
            return ((Iterator) (obj));
        }

        public boolean retainAll(Collection collection)
        {
            if(collection == null)
                throw new NullPointerException();
            else
                return super.retainAll(collection);
        }

        public int size()
        {
            return elements.size();
        }

        private static final int SET_Principal = 0;
        private static final int SET_PrivCred = 1;
        private static final int SET_PubCred = 2;
        private static final long serialVersionUID = 0x17557e27L;
        private LinkedList elements;
        private transient AuthPermission permission;
        private int setType;
        final Subject this$0;


        protected SecureSet(AuthPermission authpermission)
        {
            this$0 = Subject.this;
            super();
            permission = authpermission;
            elements = new LinkedList();
        }

        protected SecureSet(AuthPermission authpermission, Collection collection)
        {
            this(authpermission);
            boolean flag;
            Iterator iterator1;
            if(collection.getClass().getClassLoader() == null)
                flag = true;
            else
                flag = false;
            iterator1 = collection.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                Object obj = iterator1.next();
                verifyElement(obj);
                if(flag || !elements.contains(obj))
                    elements.add(obj);
            } while(true);
        }
    }


    public Subject()
    {
        principals = new SecureSet(_PRINCIPALS);
        publicCredentials = new SecureSet(_PUBLIC_CREDENTIALS);
        privateCredentials = new SecureSet(_PRIVATE_CREDENTIALS);
        readOnly = false;
    }

    public Subject(boolean flag, Set set, Set set1, Set set2)
    {
        if(set == null || set1 == null || set2 == null)
        {
            throw new NullPointerException();
        } else
        {
            principals = new SecureSet(_PRINCIPALS, set);
            publicCredentials = new SecureSet(_PUBLIC_CREDENTIALS, set1);
            privateCredentials = new SecureSet(_PRIVATE_CREDENTIALS, set2);
            readOnly = flag;
            return;
        }
    }

    private static void checkPermission(Permission permission)
    {
        SecurityManager securitymanager = System.getSecurityManager();
        if(securitymanager != null)
            securitymanager.checkPermission(permission);
    }

    private void checkState()
    {
        if(readOnly)
            throw new IllegalStateException("auth.0A");
        else
            return;
    }

    public static Object doAs(Subject subject, PrivilegedAction privilegedaction)
    {
        checkPermission(_AS);
        return doAs_PrivilegedAction(subject, privilegedaction, AccessController.getContext());
    }

    public static Object doAs(Subject subject, PrivilegedExceptionAction privilegedexceptionaction)
        throws PrivilegedActionException
    {
        checkPermission(_AS);
        return doAs_PrivilegedExceptionAction(subject, privilegedexceptionaction, AccessController.getContext());
    }

    public static Object doAsPrivileged(Subject subject, PrivilegedAction privilegedaction, AccessControlContext accesscontrolcontext)
    {
        checkPermission(_AS_PRIVILEGED);
        Object obj;
        if(accesscontrolcontext == null)
            obj = doAs_PrivilegedAction(subject, privilegedaction, new AccessControlContext(new ProtectionDomain[0]));
        else
            obj = doAs_PrivilegedAction(subject, privilegedaction, accesscontrolcontext);
        return obj;
    }

    public static Object doAsPrivileged(Subject subject, PrivilegedExceptionAction privilegedexceptionaction, AccessControlContext accesscontrolcontext)
        throws PrivilegedActionException
    {
        checkPermission(_AS_PRIVILEGED);
        Object obj;
        if(accesscontrolcontext == null)
            obj = doAs_PrivilegedExceptionAction(subject, privilegedexceptionaction, new AccessControlContext(new ProtectionDomain[0]));
        else
            obj = doAs_PrivilegedExceptionAction(subject, privilegedexceptionaction, accesscontrolcontext);
        return obj;
    }

    private static Object doAs_PrivilegedAction(Subject subject, PrivilegedAction privilegedaction, final AccessControlContext context)
    {
        final SubjectDomainCombiner combiner;
        if(subject == null)
            combiner = null;
        else
            combiner = new SubjectDomainCombiner(subject);
        return AccessController.doPrivileged(privilegedaction, (AccessControlContext)AccessController.doPrivileged(new PrivilegedAction() {

            public Object run()
            {
                return new AccessControlContext(context, combiner);
            }

            final SubjectDomainCombiner val$combiner;
            final AccessControlContext val$context;

            
            {
                context = accesscontrolcontext;
                combiner = subjectdomaincombiner;
                super();
            }
        }
));
    }

    private static Object doAs_PrivilegedExceptionAction(Subject subject, PrivilegedExceptionAction privilegedexceptionaction, final AccessControlContext context)
        throws PrivilegedActionException
    {
        final SubjectDomainCombiner combiner;
        if(subject == null)
            combiner = null;
        else
            combiner = new SubjectDomainCombiner(subject);
        return AccessController.doPrivileged(privilegedexceptionaction, (AccessControlContext)AccessController.doPrivileged(new PrivilegedAction() {

            public volatile Object run()
            {
                return run();
            }

            public AccessControlContext run()
            {
                return new AccessControlContext(context, combiner);
            }

            final SubjectDomainCombiner val$combiner;
            final AccessControlContext val$context;

            
            {
                context = accesscontrolcontext;
                combiner = subjectdomaincombiner;
                super();
            }
        }
));
    }

    public static Subject getSubject(final AccessControlContext context)
    {
        checkPermission(_SUBJECT);
        if(context == null)
            throw new NullPointerException("auth.09");
        DomainCombiner domaincombiner = (DomainCombiner)AccessController.doPrivileged(new PrivilegedAction() {

            public volatile Object run()
            {
                return run();
            }

            public DomainCombiner run()
            {
                return context.getDomainCombiner();
            }

            final AccessControlContext val$context;

            
            {
                context = accesscontrolcontext;
                super();
            }
        }
);
        Subject subject;
        if(domaincombiner == null || !(domaincombiner instanceof SubjectDomainCombiner))
            subject = null;
        else
            subject = ((SubjectDomainCombiner)domaincombiner).getSubject();
        return subject;
    }

    private void readObject(ObjectInputStream objectinputstream)
        throws IOException, ClassNotFoundException
    {
        objectinputstream.defaultReadObject();
        publicCredentials = new SecureSet(_PUBLIC_CREDENTIALS);
        privateCredentials = new SecureSet(_PRIVATE_CREDENTIALS);
    }

    private void writeObject(ObjectOutputStream objectoutputstream)
        throws IOException
    {
        objectoutputstream.defaultWriteObject();
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(this == obj)
            flag = true;
        else
        if(obj == null || getClass() != obj.getClass())
        {
            flag = false;
        } else
        {
            Subject subject = (Subject)obj;
            if(principals.equals(subject.principals) && publicCredentials.equals(subject.publicCredentials) && privateCredentials.equals(subject.privateCredentials))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public Set getPrincipals()
    {
        return principals;
    }

    public Set getPrincipals(Class class1)
    {
        return ((SecureSet)principals).get(class1);
    }

    public Set getPrivateCredentials()
    {
        return privateCredentials;
    }

    public Set getPrivateCredentials(Class class1)
    {
        return privateCredentials.get(class1);
    }

    public Set getPublicCredentials()
    {
        return publicCredentials;
    }

    public Set getPublicCredentials(Class class1)
    {
        return publicCredentials.get(class1);
    }

    public int hashCode()
    {
        return principals.hashCode() + privateCredentials.hashCode() + publicCredentials.hashCode();
    }

    public boolean isReadOnly()
    {
        return readOnly;
    }

    public void setReadOnly()
    {
        checkPermission(_READ_ONLY);
        readOnly = true;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder("Subject:\n");
        for(Iterator iterator = principals.iterator(); iterator.hasNext(); stringbuilder.append('\n'))
        {
            stringbuilder.append("\tPrincipal: ");
            stringbuilder.append(iterator.next());
        }

        for(Iterator iterator1 = publicCredentials.iterator(); iterator1.hasNext(); stringbuilder.append('\n'))
        {
            stringbuilder.append("\tPublic Credential: ");
            stringbuilder.append(iterator1.next());
        }

        int i = stringbuilder.length() - 1;
        Iterator iterator2 = privateCredentials.iterator();
        try
        {
            while(iterator2.hasNext()) 
            {
                stringbuilder.append("\tPrivate Credential: ");
                stringbuilder.append(iterator2.next());
                stringbuilder.append('\n');
            }
        }
        catch(SecurityException securityexception)
        {
            stringbuilder.delete(i, stringbuilder.length());
            stringbuilder.append("\tPrivate Credentials: no accessible information\n");
        }
        return stringbuilder.toString();
    }

    private static final AuthPermission _AS = new AuthPermission("doAs");
    private static final AuthPermission _AS_PRIVILEGED = new AuthPermission("doAsPrivileged");
    private static final AuthPermission _PRINCIPALS = new AuthPermission("modifyPrincipals");
    private static final AuthPermission _PRIVATE_CREDENTIALS = new AuthPermission("modifyPrivateCredentials");
    private static final AuthPermission _PUBLIC_CREDENTIALS = new AuthPermission("modifyPublicCredentials");
    private static final AuthPermission _READ_ONLY = new AuthPermission("setReadOnly");
    private static final AuthPermission _SUBJECT = new AuthPermission("getSubject");
    private static final long serialVersionUID = 0x33fa68L;
    private final Set principals;
    private transient SecureSet privateCredentials;
    private transient SecureSet publicCredentials;
    private boolean readOnly;







}
