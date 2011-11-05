package org.apache.harmony.javax.security.auth;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.DomainCombiner;
import java.security.Permission;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.apache.harmony.javax.security.auth.AuthPermission;
import org.apache.harmony.javax.security.auth.PrivateCredentialPermission;
import org.apache.harmony.javax.security.auth.SubjectDomainCombiner;

public final class Subject implements Serializable {

   private static final AuthPermission _AS = new AuthPermission("doAs");
   private static final AuthPermission _AS_PRIVILEGED = new AuthPermission("doAsPrivileged");
   private static final AuthPermission _PRINCIPALS = new AuthPermission("modifyPrincipals");
   private static final AuthPermission _PRIVATE_CREDENTIALS = new AuthPermission("modifyPrivateCredentials");
   private static final AuthPermission _PUBLIC_CREDENTIALS = new AuthPermission("modifyPublicCredentials");
   private static final AuthPermission _READ_ONLY = new AuthPermission("setReadOnly");
   private static final AuthPermission _SUBJECT = new AuthPermission("getSubject");
   private static final long serialVersionUID = -8308522755600156056L;
   private final Set<Principal> principals;
   private transient Subject.SecureSet<Object> privateCredentials;
   private transient Subject.SecureSet<Object> publicCredentials;
   private boolean readOnly;


   public Subject() {
      AuthPermission var1 = _PRINCIPALS;
      Subject.SecureSet var2 = new Subject.SecureSet(var1);
      this.principals = var2;
      AuthPermission var3 = _PUBLIC_CREDENTIALS;
      Subject.SecureSet var4 = new Subject.SecureSet(var3);
      this.publicCredentials = var4;
      AuthPermission var5 = _PRIVATE_CREDENTIALS;
      Subject.SecureSet var6 = new Subject.SecureSet(var5);
      this.privateCredentials = var6;
      this.readOnly = (boolean)0;
   }

   public Subject(boolean var1, Set<? extends Principal> var2, Set<?> var3, Set<?> var4) {
      if(var2 != null && var3 != null && var4 != null) {
         AuthPermission var5 = _PRINCIPALS;
         Subject.SecureSet var6 = new Subject.SecureSet(var5, var2);
         this.principals = var6;
         AuthPermission var7 = _PUBLIC_CREDENTIALS;
         Subject.SecureSet var8 = new Subject.SecureSet(var7, var3);
         this.publicCredentials = var8;
         AuthPermission var9 = _PRIVATE_CREDENTIALS;
         Subject.SecureSet var10 = new Subject.SecureSet(var9, var4);
         this.privateCredentials = var10;
         this.readOnly = var1;
      } else {
         throw new NullPointerException();
      }
   }

   private static void checkPermission(Permission var0) {
      SecurityManager var1 = System.getSecurityManager();
      if(var1 != null) {
         var1.checkPermission(var0);
      }
   }

   private void checkState() {
      if(this.readOnly) {
         throw new IllegalStateException("auth.0A");
      }
   }

   public static Object doAs(Subject var0, PrivilegedAction var1) {
      checkPermission(_AS);
      AccessControlContext var2 = AccessController.getContext();
      return doAs_PrivilegedAction(var0, var1, var2);
   }

   public static Object doAs(Subject var0, PrivilegedExceptionAction var1) throws PrivilegedActionException {
      checkPermission(_AS);
      AccessControlContext var2 = AccessController.getContext();
      return doAs_PrivilegedExceptionAction(var0, var1, var2);
   }

   public static Object doAsPrivileged(Subject var0, PrivilegedAction var1, AccessControlContext var2) {
      checkPermission(_AS_PRIVILEGED);
      Object var5;
      if(var2 == null) {
         ProtectionDomain[] var3 = new ProtectionDomain[0];
         AccessControlContext var4 = new AccessControlContext(var3);
         var5 = doAs_PrivilegedAction(var0, var1, var4);
      } else {
         var5 = doAs_PrivilegedAction(var0, var1, var2);
      }

      return var5;
   }

   public static Object doAsPrivileged(Subject var0, PrivilegedExceptionAction var1, AccessControlContext var2) throws PrivilegedActionException {
      checkPermission(_AS_PRIVILEGED);
      Object var5;
      if(var2 == null) {
         ProtectionDomain[] var3 = new ProtectionDomain[0];
         AccessControlContext var4 = new AccessControlContext(var3);
         var5 = doAs_PrivilegedExceptionAction(var0, var1, var4);
      } else {
         var5 = doAs_PrivilegedExceptionAction(var0, var1, var2);
      }

      return var5;
   }

   private static Object doAs_PrivilegedAction(Subject var0, PrivilegedAction var1, AccessControlContext var2) {
      SubjectDomainCombiner var3;
      if(var0 == null) {
         var3 = null;
      } else {
         var3 = new SubjectDomainCombiner(var0);
      }

      AccessControlContext var4 = (AccessControlContext)AccessController.doPrivileged(new Subject.1(var2, var3));
      return AccessController.doPrivileged(var1, var4);
   }

   private static Object doAs_PrivilegedExceptionAction(Subject var0, PrivilegedExceptionAction var1, AccessControlContext var2) throws PrivilegedActionException {
      SubjectDomainCombiner var3;
      if(var0 == null) {
         var3 = null;
      } else {
         var3 = new SubjectDomainCombiner(var0);
      }

      AccessControlContext var4 = (AccessControlContext)AccessController.doPrivileged(new Subject.2(var2, var3));
      return AccessController.doPrivileged(var1, var4);
   }

   public static Subject getSubject(AccessControlContext var0) {
      checkPermission(_SUBJECT);
      if(var0 == null) {
         throw new NullPointerException("auth.09");
      } else {
         DomainCombiner var1 = (DomainCombiner)AccessController.doPrivileged(new Subject.3(var0));
         Subject var2;
         if(var1 != null && var1 instanceof SubjectDomainCombiner) {
            var2 = ((SubjectDomainCombiner)var1).getSubject();
         } else {
            var2 = null;
         }

         return var2;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      AuthPermission var2 = _PUBLIC_CREDENTIALS;
      Subject.SecureSet var3 = new Subject.SecureSet(var2);
      this.publicCredentials = var3;
      AuthPermission var4 = _PRIVATE_CREDENTIALS;
      Subject.SecureSet var5 = new Subject.SecureSet(var4);
      this.privateCredentials = var5;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               Subject var11 = (Subject)var1;
               Set var5 = this.principals;
               Set var6 = var11.principals;
               if(var5.equals(var6)) {
                  Subject.SecureSet var7 = this.publicCredentials;
                  Subject.SecureSet var8 = var11.publicCredentials;
                  if(var7.equals(var8)) {
                     Subject.SecureSet var9 = this.privateCredentials;
                     Subject.SecureSet var10 = var11.privateCredentials;
                     if(var9.equals(var10)) {
                        var2 = true;
                        return var2;
                     }
                  }
               }

               var2 = false;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public Set<Principal> getPrincipals() {
      return this.principals;
   }

   public <T extends Object & Principal> Set<T> getPrincipals(Class<T> var1) {
      return ((Subject.SecureSet)this.principals).get(var1);
   }

   public Set<Object> getPrivateCredentials() {
      return this.privateCredentials;
   }

   public <T extends Object> Set<T> getPrivateCredentials(Class<T> var1) {
      return this.privateCredentials.get(var1);
   }

   public Set<Object> getPublicCredentials() {
      return this.publicCredentials;
   }

   public <T extends Object> Set<T> getPublicCredentials(Class<T> var1) {
      return this.publicCredentials.get(var1);
   }

   public int hashCode() {
      int var1 = this.principals.hashCode();
      int var2 = this.privateCredentials.hashCode();
      int var3 = var1 + var2;
      int var4 = this.publicCredentials.hashCode();
      return var3 + var4;
   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public void setReadOnly() {
      checkPermission(_READ_ONLY);
      this.readOnly = (boolean)1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("Subject:\n");

      StringBuilder var6;
      for(Iterator var2 = this.principals.iterator(); var2.hasNext(); var6 = var1.append('\n')) {
         StringBuilder var3 = var1.append("\tPrincipal: ");
         Object var4 = var2.next();
         var1.append(var4);
      }

      StringBuilder var11;
      for(Iterator var7 = this.publicCredentials.iterator(); var7.hasNext(); var11 = var1.append('\n')) {
         StringBuilder var8 = var1.append("\tPublic Credential: ");
         Object var9 = var7.next();
         var1.append(var9);
      }

      int var12 = var1.length() - 1;
      Iterator var13 = this.privateCredentials.iterator();

      try {
         while(var13.hasNext()) {
            StringBuilder var14 = var1.append("\tPrivate Credential: ");
            Object var15 = var13.next();
            var1.append(var15);
            StringBuilder var17 = var1.append('\n');
         }
      } catch (SecurityException var22) {
         int var19 = var1.length();
         var1.delete(var12, var19);
         StringBuilder var21 = var1.append("\tPrivate Credentials: no accessible information\n");
      }

      return var1.toString();
   }

   private final class SecureSet<SST extends Object> extends AbstractSet<SST> implements Serializable {

      private static final int SET_Principal = 0;
      private static final int SET_PrivCred = 1;
      private static final int SET_PubCred = 2;
      private static final long serialVersionUID = 7911754171111800359L;
      private LinkedList<SST> elements;
      private transient AuthPermission permission;
      private int setType;


      protected SecureSet(AuthPermission var2) {
         this.permission = var2;
         LinkedList var3 = new LinkedList();
         this.elements = var3;
      }

      protected SecureSet(AuthPermission var2, Collection var3) {
         this(var2);
         boolean var4;
         if(var3.getClass().getClassLoader() == null) {
            var4 = true;
         } else {
            var4 = false;
         }

         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            Object var6 = var5.next();
            this.verifyElement(var6);
            if(var4 || !this.elements.contains(var6)) {
               this.elements.add(var6);
            }
         }

      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         switch(this.setType) {
         case 0:
            AuthPermission var2 = Subject._PRINCIPALS;
            this.permission = var2;
            break;
         case 1:
            AuthPermission var5 = Subject._PRIVATE_CREDENTIALS;
            this.permission = var5;
            break;
         case 2:
            AuthPermission var6 = Subject._PUBLIC_CREDENTIALS;
            this.permission = var6;
            break;
         default:
            throw new IllegalArgumentException();
         }

         Iterator var3 = this.elements.iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            this.verifyElement(var4);
         }

      }

      private void verifyElement(Object var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            AuthPermission var2 = this.permission;
            AuthPermission var3 = Subject._PRINCIPALS;
            if(var2 == var3) {
               Class var4 = var1.getClass();
               if(!Principal.class.isAssignableFrom(var4)) {
                  throw new IllegalArgumentException("auth.0B");
               }
            }
         }
      }

      private void writeObject(ObjectOutputStream var1) throws IOException {
         AuthPermission var2 = this.permission;
         AuthPermission var3 = Subject._PRIVATE_CREDENTIALS;
         if(var2 == var3) {
            Object var5;
            for(Iterator var4 = this.iterator(); var4.hasNext(); var5 = var4.next()) {
               ;
            }

            this.setType = 1;
         } else {
            AuthPermission var6 = this.permission;
            AuthPermission var7 = Subject._PRINCIPALS;
            if(var6 == var7) {
               this.setType = 0;
            } else {
               this.setType = 2;
            }
         }

         var1.defaultWriteObject();
      }

      public boolean add(SST var1) {
         this.verifyElement(var1);
         Subject.this.checkState();
         Subject.checkPermission(this.permission);
         boolean var3;
         if(!this.elements.contains(var1)) {
            this.elements.add(var1);
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      protected final <E extends Object> Set<E> get(Class<E> var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            Subject.SecureSet.2 var2 = new Subject.SecureSet.2(var1);
            Iterator var3 = this.iterator();

            while(var3.hasNext()) {
               Object var4 = var3.next();
               Class var5 = var4.getClass();
               if(var1.isAssignableFrom(var5)) {
                  Object var6 = var1.cast(var4);
                  var2.add(var6);
               }
            }

            return var2;
         }
      }

      public Iterator<SST> iterator() {
         AuthPermission var1 = this.permission;
         AuthPermission var2 = Subject._PRIVATE_CREDENTIALS;
         Object var4;
         if(var1 == var2) {
            Iterator var3 = this.elements.iterator();
            var4 = new Subject.SecureSet.1(var3);
         } else {
            Iterator var5 = this.elements.iterator();
            var4 = new Subject.SecureSet.SecureIterator(var5);
         }

         return (Iterator)var4;
      }

      public boolean retainAll(Collection<?> var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            return super.retainAll(var1);
         }
      }

      public int size() {
         return this.elements.size();
      }

      class 1 extends Subject.SecureSet.SecureIterator {

         1(Iterator var2) {
            super(var2);
         }

         public SST next() {
            Object var1 = this.iterator.next();
            String var2 = var1.getClass().getName();
            Set var3 = Subject.this.principals;
            Subject.checkPermission(new PrivateCredentialPermission(var2, var3));
            return var1;
         }
      }

      class 2 extends AbstractSet<E> {

         private LinkedList<E> elements;
         // $FF: synthetic field
         final Class val$c;


         2(Class var2) {
            this.val$c = var2;
            LinkedList var3 = new LinkedList();
            this.elements = var3;
         }

         public boolean add(E var1) {
            Class var2 = this.val$c;
            Class var3 = var1.getClass();
            if(!var2.isAssignableFrom(var3)) {
               StringBuilder var4 = (new StringBuilder()).append("auth.0C ");
               String var5 = this.val$c.getName();
               String var6 = var4.append(var5).toString();
               throw new IllegalArgumentException(var6);
            } else {
               boolean var7;
               if(this.elements.contains(var1)) {
                  var7 = false;
               } else {
                  this.elements.add(var1);
                  var7 = true;
               }

               return var7;
            }
         }

         public Iterator<E> iterator() {
            return this.elements.iterator();
         }

         public boolean retainAll(Collection<?> var1) {
            if(var1 == null) {
               throw new NullPointerException();
            } else {
               return super.retainAll(var1);
            }
         }

         public int size() {
            return this.elements.size();
         }
      }

      private class SecureIterator implements Iterator<SST> {

         protected Iterator<SST> iterator;


         protected SecureIterator(Iterator var2) {
            this.iterator = var2;
         }

         public boolean hasNext() {
            return this.iterator.hasNext();
         }

         public SST next() {
            return this.iterator.next();
         }

         public void remove() {
            Subject.this.checkState();
            Subject.checkPermission(SecureSet.this.permission);
            this.iterator.remove();
         }
      }
   }

   static class 3 implements PrivilegedAction<DomainCombiner> {

      // $FF: synthetic field
      final AccessControlContext val$context;


      3(AccessControlContext var1) {
         this.val$context = var1;
      }

      public DomainCombiner run() {
         return this.val$context.getDomainCombiner();
      }
   }

   static class 2 implements PrivilegedAction<AccessControlContext> {

      // $FF: synthetic field
      final SubjectDomainCombiner val$combiner;
      // $FF: synthetic field
      final AccessControlContext val$context;


      2(AccessControlContext var1, SubjectDomainCombiner var2) {
         this.val$context = var1;
         this.val$combiner = var2;
      }

      public AccessControlContext run() {
         AccessControlContext var1 = this.val$context;
         SubjectDomainCombiner var2 = this.val$combiner;
         return new AccessControlContext(var1, var2);
      }
   }

   static class 1 implements PrivilegedAction {

      // $FF: synthetic field
      final SubjectDomainCombiner val$combiner;
      // $FF: synthetic field
      final AccessControlContext val$context;


      1(AccessControlContext var1, SubjectDomainCombiner var2) {
         this.val$context = var1;
         this.val$combiner = var2;
      }

      public Object run() {
         AccessControlContext var1 = this.val$context;
         SubjectDomainCombiner var2 = this.val$combiner;
         return new AccessControlContext(var1, var2);
      }
   }
}
