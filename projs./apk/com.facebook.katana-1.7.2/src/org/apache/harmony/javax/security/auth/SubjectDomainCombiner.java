package org.apache.harmony.javax.security.auth;

import java.security.CodeSource;
import java.security.DomainCombiner;
import java.security.PermissionCollection;
import java.security.Principal;
import java.security.ProtectionDomain;
import java.util.Set;
import org.apache.harmony.javax.security.auth.AuthPermission;
import org.apache.harmony.javax.security.auth.Subject;

public class SubjectDomainCombiner implements DomainCombiner {

   private static final AuthPermission _GET = new AuthPermission("getSubjectFromDomainCombiner");
   private Subject subject;


   public SubjectDomainCombiner(Subject var1) {
      if(var1 == null) {
         throw new NullPointerException();
      } else {
         this.subject = var1;
      }
   }

   public ProtectionDomain[] combine(ProtectionDomain[] var1, ProtectionDomain[] var2) {
      int var3;
      if(var1 != null) {
         var3 = var1.length + 0;
      } else {
         var3 = 0;
      }

      int var4;
      if(var2 != null) {
         var4 = var2.length;
         var3 += var4;
      }

      ProtectionDomain[] var16;
      if(var3 == 0) {
         var16 = null;
      } else {
         var16 = new ProtectionDomain[var3];
         if(var1 != null) {
            Set var5 = this.subject.getPrincipals();
            Principal[] var6 = new Principal[var5.size()];
            Principal[] var15 = (Principal[])var5.toArray(var6);
            var4 = 0;

            while(true) {
               int var7 = var1.length;
               if(var4 >= var7) {
                  break;
               }

               if(var1[var4] != false) {
                  CodeSource var8 = var1[var4].getCodeSource();
                  PermissionCollection var9 = var1[var4].getPermissions();
                  ClassLoader var10 = var1[var4].getClassLoader();
                  ProtectionDomain var11 = new ProtectionDomain(var8, var9, var10, var15);
                  var16[var4] = var11;
               }

               int var12 = var4 + 1;
            }
         } else {
            boolean var13 = false;
         }

         if(var2 != null) {
            int var14 = var2.length;
            System.arraycopy(var2, 0, var16, var4, var14);
         }
      }

      return var16;
   }

   public Subject getSubject() {
      SecurityManager var1 = System.getSecurityManager();
      if(var1 != null) {
         AuthPermission var2 = _GET;
         var1.checkPermission(var2);
      }

      return this.subject;
   }
}
