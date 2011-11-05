package org.apache.harmony.javax.security.auth;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Principal;
import java.util.Iterator;
import java.util.Set;

public final class PrivateCredentialPermission extends Permission {

   private static final String READ = "read";
   private static final long serialVersionUID = 5284372143517237068L;
   private String credentialClass;
   private transient int offset;
   private transient PrivateCredentialPermission.CredOwner[] set;


   public PrivateCredentialPermission(String var1, String var2) {
      super(var1);
      if("read".equalsIgnoreCase(var2)) {
         this.initTargetName(var1);
      } else {
         throw new IllegalArgumentException("auth.11");
      }
   }

   PrivateCredentialPermission(String var1, Set<Principal> var2) {
      super(var1);
      this.credentialClass = var1;
      PrivateCredentialPermission.CredOwner[] var3 = new PrivateCredentialPermission.CredOwner[var2.size()];
      this.set = var3;
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         Principal var5 = (Principal)var4.next();
         String var6 = var5.getClass().getName();
         String var7 = var5.getName();
         PrivateCredentialPermission.CredOwner var8 = new PrivateCredentialPermission.CredOwner(var6, var7);
         int var9 = 0;

         while(true) {
            int var10 = this.offset;
            boolean var11;
            if(var9 < var10) {
               if(!this.set[var9].equals(var8)) {
                  ++var9;
                  continue;
               }

               var11 = true;
            } else {
               var11 = false;
            }

            if(!var11) {
               PrivateCredentialPermission.CredOwner[] var12 = this.set;
               int var13 = this.offset;
               int var14 = var13 + 1;
               this.offset = var14;
               var12[var13] = var8;
            }
            break;
         }
      }

   }

   private void initTargetName(String var1) {
      boolean var2 = true;
      if(var1 == null) {
         throw new NullPointerException("auth.0E");
      } else {
         String var3 = var1.trim();
         if(var3.length() == 0) {
            throw new IllegalArgumentException("auth.0F");
         } else {
            int var4 = var3.indexOf(32);
            if(var4 == -1) {
               throw new IllegalArgumentException("auth.10");
            } else {
               String var5 = var3.substring(0, var4);
               this.credentialClass = var5;
               int var6 = var4 + 1;
               int var7 = var3.length();
               int var8 = var6;
               int var9 = 0;

               while(true) {
                  if(var8 >= var7) {
                     if(var9 < 1) {
                        throw new IllegalArgumentException("auth.10");
                     }

                     int var14 = var3.indexOf(32) + 1;
                     PrivateCredentialPermission.CredOwner[] var15 = new PrivateCredentialPermission.CredOwner[var9];
                     this.set = var15;
                     int var16 = var14;
                     int var17 = 0;

                     while(var17 < var9) {
                        int var18 = var3.indexOf(32, var16);
                        int var19 = var18 + 2;
                        int var30 = var3.indexOf(34, var19);
                        String var20 = var3.substring(var16, var18);
                        int var21 = var18 + 2;
                        String var22 = var3.substring(var21, var30);
                        PrivateCredentialPermission.CredOwner var23 = new PrivateCredentialPermission.CredOwner(var20, var22);
                        int var24 = 0;

                        while(true) {
                           int var25 = this.offset;
                           boolean var26;
                           if(var24 < var25) {
                              if(!this.set[var24].equals(var23)) {
                                 ++var24;
                                 continue;
                              }

                              var26 = true;
                           } else {
                              var26 = false;
                           }

                           if(!var26) {
                              PrivateCredentialPermission.CredOwner[] var27 = this.set;
                              int var28 = this.offset;
                              int var29 = var28 + 1;
                              this.offset = var29;
                              var27[var28] = var23;
                           }

                           var16 = var30 + 2;
                           ++var17;
                           break;
                        }
                     }

                     return;
                  }

                  int var10 = var3.indexOf(32, var8);
                  int var11 = var10 + 2;
                  int var12 = var3.indexOf(34, var11);
                  if(var10 == -1 || var12 == -1) {
                     break;
                  }

                  int var13 = var10 + 1;
                  if(var3.charAt(var13) != 34) {
                     break;
                  }

                  var8 = var12 + 2;
                  ++var9;
               }

               throw new IllegalArgumentException("auth.10");
            }
         }
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      String var2 = this.getName();
      this.initTargetName(var2);
   }

   private boolean sameMembers(Object[] var1, Object[] var2, int var3) {
      boolean var4;
      if(var1 == null && var2 == null) {
         var4 = true;
      } else if(var1 != null && var2 != null) {
         int var5 = 0;

         while(true) {
            if(var5 >= var3) {
               var4 = true;
               break;
            }

            int var6 = 0;

            boolean var9;
            while(true) {
               if(var6 >= var3) {
                  var9 = false;
                  break;
               }

               Object var7 = var1[var5];
               Object var8 = var2[var6];
               if(var7.equals(var8)) {
                  var9 = true;
                  break;
               }

               ++var6;
            }

            if(!var9) {
               var4 = false;
               break;
            }

            ++var5;
         }
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               PrivateCredentialPermission var12 = (PrivateCredentialPermission)var1;
               String var5 = this.credentialClass;
               String var6 = var12.credentialClass;
               if(var5.equals(var6)) {
                  int var7 = this.offset;
                  int var8 = var12.offset;
                  if(var7 == var8) {
                     PrivateCredentialPermission.CredOwner[] var9 = this.set;
                     PrivateCredentialPermission.CredOwner[] var10 = var12.set;
                     int var11 = this.offset;
                     if(this.sameMembers(var9, var10, var11)) {
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

   public String getActions() {
      return "read";
   }

   public String getCredentialClass() {
      return this.credentialClass;
   }

   public String[][] getPrincipals() {
      int var1 = this.offset;
      int[] var2 = new int[]{var1, 2};
      String[][] var3 = (String[][])Array.newInstance(String.class, var2);
      int var4 = 0;

      while(true) {
         int var5 = var3.length;
         if(var4 >= var5) {
            return var3;
         }

         String[] var6 = var3[var4];
         String var7 = this.set[var4].principalClass;
         var6[0] = var7;
         String[] var8 = var3[var4];
         String var9 = this.set[var4].principalName;
         var8[1] = var9;
         ++var4;
      }
   }

   public int hashCode() {
      int var1 = 0;
      int var2 = var1;

      while(true) {
         int var3 = this.offset;
         if(var1 >= var3) {
            return this.getCredentialClass().hashCode() + var2;
         }

         int var4 = this.set[var1].hashCode();
         var2 += var4;
         ++var1;
      }
   }

   public boolean implies(Permission var1) {
      boolean var4;
      if(var1 != null) {
         Class var2 = this.getClass();
         Class var3 = var1.getClass();
         if(var2 == var3) {
            PrivateCredentialPermission var17 = (PrivateCredentialPermission)var1;
            String var5 = this.credentialClass;
            if(!"*".equals(var5)) {
               String var6 = this.credentialClass;
               String var7 = var17.getCredentialClass();
               if(!var6.equals(var7)) {
                  var4 = false;
                  return var4;
               }
            }

            if(var17.offset == 0) {
               var4 = true;
            } else {
               PrivateCredentialPermission.CredOwner[] var8 = this.set;
               PrivateCredentialPermission.CredOwner[] var9 = var17.set;
               int var10 = this.offset;
               int var11 = var17.offset;

               for(int var12 = 0; var12 < var10; ++var12) {
                  int var13;
                  for(var13 = 0; var13 < var11; ++var13) {
                     PrivateCredentialPermission.CredOwner var14 = var8[var12];
                     PrivateCredentialPermission.CredOwner var15 = var9[var13];
                     if(var14.implies(var15)) {
                        break;
                     }
                  }

                  int var16 = var9.length;
                  if(var13 == var16) {
                     var4 = false;
                     return var4;
                  }
               }

               var4 = true;
            }

            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public PermissionCollection newPermissionCollection() {
      return null;
   }

   private static final class CredOwner implements Serializable {

      private static final long serialVersionUID = -5607449830436408266L;
      private transient boolean isClassWildcard;
      private transient boolean isPNameWildcard;
      String principalClass;
      String principalName;


      CredOwner(String var1, String var2) {
         if("*".equals(var1)) {
            this.isClassWildcard = (boolean)1;
         }

         if("*".equals(var2)) {
            this.isPNameWildcard = (boolean)1;
         }

         if(this.isClassWildcard && !this.isPNameWildcard) {
            throw new IllegalArgumentException("auth.12");
         } else {
            this.principalClass = var1;
            this.principalName = var2;
         }
      }

      public boolean equals(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else if(var1 instanceof PrivateCredentialPermission.CredOwner) {
            PrivateCredentialPermission.CredOwner var7 = (PrivateCredentialPermission.CredOwner)var1;
            String var3 = this.principalClass;
            String var4 = var7.principalClass;
            if(var3.equals(var4)) {
               String var5 = this.principalName;
               String var6 = var7.principalName;
               if(var5.equals(var6)) {
                  var2 = true;
                  return var2;
               }
            }

            var2 = false;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int hashCode() {
         int var1 = this.principalClass.hashCode();
         int var2 = this.principalName.hashCode();
         return var1 + var2;
      }

      boolean implies(Object var1) {
         boolean var2;
         if(var1 == this) {
            var2 = true;
         } else {
            label29: {
               PrivateCredentialPermission.CredOwner var7 = (PrivateCredentialPermission.CredOwner)var1;
               if(!this.isClassWildcard) {
                  String var3 = this.principalClass;
                  String var4 = var7.principalClass;
                  if(!var3.equals(var4)) {
                     break label29;
                  }
               }

               if(!this.isPNameWildcard) {
                  String var5 = this.principalName;
                  String var6 = var7.principalName;
                  if(!var5.equals(var6)) {
                     break label29;
                  }
               }

               var2 = true;
               return var2;
            }

            var2 = false;
         }

         return var2;
      }
   }
}
