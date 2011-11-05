package myorg.bouncycastle.jce;

import java.security.cert.CertStoreParameters;
import java.security.cert.LDAPCertStoreParameters;
import myorg.bouncycastle.x509.X509StoreParameters;

public class X509LDAPCertStoreParameters implements X509StoreParameters, CertStoreParameters {

   private String aACertificateAttribute;
   private String aACertificateSubjectAttributeName;
   private String attributeAuthorityRevocationListAttribute;
   private String attributeAuthorityRevocationListIssuerAttributeName;
   private String attributeCertificateAttributeAttribute;
   private String attributeCertificateAttributeSubjectAttributeName;
   private String attributeCertificateRevocationListAttribute;
   private String attributeCertificateRevocationListIssuerAttributeName;
   private String attributeDescriptorCertificateAttribute;
   private String attributeDescriptorCertificateSubjectAttributeName;
   private String authorityRevocationListAttribute;
   private String authorityRevocationListIssuerAttributeName;
   private String baseDN;
   private String cACertificateAttribute;
   private String cACertificateSubjectAttributeName;
   private String certificateRevocationListAttribute;
   private String certificateRevocationListIssuerAttributeName;
   private String crossCertificateAttribute;
   private String crossCertificateSubjectAttributeName;
   private String deltaRevocationListAttribute;
   private String deltaRevocationListIssuerAttributeName;
   private String ldapAACertificateAttributeName;
   private String ldapAttributeAuthorityRevocationListAttributeName;
   private String ldapAttributeCertificateAttributeAttributeName;
   private String ldapAttributeCertificateRevocationListAttributeName;
   private String ldapAttributeDescriptorCertificateAttributeName;
   private String ldapAuthorityRevocationListAttributeName;
   private String ldapCACertificateAttributeName;
   private String ldapCertificateRevocationListAttributeName;
   private String ldapCrossCertificateAttributeName;
   private String ldapDeltaRevocationListAttributeName;
   private String ldapURL;
   private String ldapUserCertificateAttributeName;
   private String searchForSerialNumberIn;
   private String userCertificateAttribute;
   private String userCertificateSubjectAttributeName;


   private X509LDAPCertStoreParameters(X509LDAPCertStoreParameters.Builder var1) {
      String var2 = var1.ldapURL;
      this.ldapURL = var2;
      String var3 = var1.baseDN;
      this.baseDN = var3;
      String var4 = var1.userCertificateAttribute;
      this.userCertificateAttribute = var4;
      String var5 = var1.cACertificateAttribute;
      this.cACertificateAttribute = var5;
      String var6 = var1.crossCertificateAttribute;
      this.crossCertificateAttribute = var6;
      String var7 = var1.certificateRevocationListAttribute;
      this.certificateRevocationListAttribute = var7;
      String var8 = var1.deltaRevocationListAttribute;
      this.deltaRevocationListAttribute = var8;
      String var9 = var1.authorityRevocationListAttribute;
      this.authorityRevocationListAttribute = var9;
      String var10 = var1.attributeCertificateAttributeAttribute;
      this.attributeCertificateAttributeAttribute = var10;
      String var11 = var1.aACertificateAttribute;
      this.aACertificateAttribute = var11;
      String var12 = var1.attributeDescriptorCertificateAttribute;
      this.attributeDescriptorCertificateAttribute = var12;
      String var13 = var1.attributeCertificateRevocationListAttribute;
      this.attributeCertificateRevocationListAttribute = var13;
      String var14 = var1.attributeAuthorityRevocationListAttribute;
      this.attributeAuthorityRevocationListAttribute = var14;
      String var15 = var1.ldapUserCertificateAttributeName;
      this.ldapUserCertificateAttributeName = var15;
      String var16 = var1.ldapCACertificateAttributeName;
      this.ldapCACertificateAttributeName = var16;
      String var17 = var1.ldapCrossCertificateAttributeName;
      this.ldapCrossCertificateAttributeName = var17;
      String var18 = var1.ldapCertificateRevocationListAttributeName;
      this.ldapCertificateRevocationListAttributeName = var18;
      String var19 = var1.ldapDeltaRevocationListAttributeName;
      this.ldapDeltaRevocationListAttributeName = var19;
      String var20 = var1.ldapAuthorityRevocationListAttributeName;
      this.ldapAuthorityRevocationListAttributeName = var20;
      String var21 = var1.ldapAttributeCertificateAttributeAttributeName;
      this.ldapAttributeCertificateAttributeAttributeName = var21;
      String var22 = var1.ldapAACertificateAttributeName;
      this.ldapAACertificateAttributeName = var22;
      String var23 = var1.ldapAttributeDescriptorCertificateAttributeName;
      this.ldapAttributeDescriptorCertificateAttributeName = var23;
      String var24 = var1.ldapAttributeCertificateRevocationListAttributeName;
      this.ldapAttributeCertificateRevocationListAttributeName = var24;
      String var25 = var1.ldapAttributeAuthorityRevocationListAttributeName;
      this.ldapAttributeAuthorityRevocationListAttributeName = var25;
      String var26 = var1.userCertificateSubjectAttributeName;
      this.userCertificateSubjectAttributeName = var26;
      String var27 = var1.cACertificateSubjectAttributeName;
      this.cACertificateSubjectAttributeName = var27;
      String var28 = var1.crossCertificateSubjectAttributeName;
      this.crossCertificateSubjectAttributeName = var28;
      String var29 = var1.certificateRevocationListIssuerAttributeName;
      this.certificateRevocationListIssuerAttributeName = var29;
      String var30 = var1.deltaRevocationListIssuerAttributeName;
      this.deltaRevocationListIssuerAttributeName = var30;
      String var31 = var1.authorityRevocationListIssuerAttributeName;
      this.authorityRevocationListIssuerAttributeName = var31;
      String var32 = var1.attributeCertificateAttributeSubjectAttributeName;
      this.attributeCertificateAttributeSubjectAttributeName = var32;
      String var33 = var1.aACertificateSubjectAttributeName;
      this.aACertificateSubjectAttributeName = var33;
      String var34 = var1.attributeDescriptorCertificateSubjectAttributeName;
      this.attributeDescriptorCertificateSubjectAttributeName = var34;
      String var35 = var1.attributeCertificateRevocationListIssuerAttributeName;
      this.attributeCertificateRevocationListIssuerAttributeName = var35;
      String var36 = var1.attributeAuthorityRevocationListIssuerAttributeName;
      this.attributeAuthorityRevocationListIssuerAttributeName = var36;
      String var37 = var1.searchForSerialNumberIn;
      this.searchForSerialNumberIn = var37;
   }

   // $FF: synthetic method
   X509LDAPCertStoreParameters(X509LDAPCertStoreParameters.Builder var1, X509LDAPCertStoreParameters.1 var2) {
      this(var1);
   }

   private int addHashCode(int var1, Object var2) {
      int var3 = var1 * 29;
      int var4;
      if(var2 == null) {
         var4 = 0;
      } else {
         var4 = var2.hashCode();
      }

      return var3 + var4;
   }

   private boolean checkField(Object var1, Object var2) {
      byte var3;
      if(var1 == var2) {
         var3 = 1;
      } else if(var1 == null) {
         var3 = 0;
      } else {
         var3 = var1.equals(var2);
      }

      return (boolean)var3;
   }

   public static X509LDAPCertStoreParameters getInstance(LDAPCertStoreParameters var0) {
      StringBuilder var1 = (new StringBuilder()).append("ldap://");
      String var2 = var0.getServerName();
      StringBuilder var3 = var1.append(var2).append(":");
      int var4 = var0.getPort();
      String var5 = var3.append(var4).toString();
      return (new X509LDAPCertStoreParameters.Builder(var5, "")).build();
   }

   public Object clone() {
      return this;
   }

   public boolean equal(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof X509LDAPCertStoreParameters)) {
         var2 = false;
      } else {
         X509LDAPCertStoreParameters var3 = (X509LDAPCertStoreParameters)var1;
         String var4 = this.ldapURL;
         String var5 = var3.ldapURL;
         if(this.checkField(var4, var5)) {
            String var6 = this.baseDN;
            String var7 = var3.baseDN;
            if(this.checkField(var6, var7)) {
               String var8 = this.userCertificateAttribute;
               String var9 = var3.userCertificateAttribute;
               if(this.checkField(var8, var9)) {
                  String var10 = this.cACertificateAttribute;
                  String var11 = var3.cACertificateAttribute;
                  if(this.checkField(var10, var11)) {
                     String var12 = this.crossCertificateAttribute;
                     String var13 = var3.crossCertificateAttribute;
                     if(this.checkField(var12, var13)) {
                        String var14 = this.certificateRevocationListAttribute;
                        String var15 = var3.certificateRevocationListAttribute;
                        if(this.checkField(var14, var15)) {
                           String var16 = this.deltaRevocationListAttribute;
                           String var17 = var3.deltaRevocationListAttribute;
                           if(this.checkField(var16, var17)) {
                              String var18 = this.authorityRevocationListAttribute;
                              String var19 = var3.authorityRevocationListAttribute;
                              if(this.checkField(var18, var19)) {
                                 String var20 = this.attributeCertificateAttributeAttribute;
                                 String var21 = var3.attributeCertificateAttributeAttribute;
                                 if(this.checkField(var20, var21)) {
                                    String var22 = this.aACertificateAttribute;
                                    String var23 = var3.aACertificateAttribute;
                                    if(this.checkField(var22, var23)) {
                                       String var24 = this.attributeDescriptorCertificateAttribute;
                                       String var25 = var3.attributeDescriptorCertificateAttribute;
                                       if(this.checkField(var24, var25)) {
                                          String var26 = this.attributeCertificateRevocationListAttribute;
                                          String var27 = var3.attributeCertificateRevocationListAttribute;
                                          if(this.checkField(var26, var27)) {
                                             String var28 = this.attributeAuthorityRevocationListAttribute;
                                             String var29 = var3.attributeAuthorityRevocationListAttribute;
                                             if(this.checkField(var28, var29)) {
                                                String var30 = this.ldapUserCertificateAttributeName;
                                                String var31 = var3.ldapUserCertificateAttributeName;
                                                if(this.checkField(var30, var31)) {
                                                   String var32 = this.ldapCACertificateAttributeName;
                                                   String var33 = var3.ldapCACertificateAttributeName;
                                                   if(this.checkField(var32, var33)) {
                                                      String var34 = this.ldapCrossCertificateAttributeName;
                                                      String var35 = var3.ldapCrossCertificateAttributeName;
                                                      if(this.checkField(var34, var35)) {
                                                         String var36 = this.ldapCertificateRevocationListAttributeName;
                                                         String var37 = var3.ldapCertificateRevocationListAttributeName;
                                                         if(this.checkField(var36, var37)) {
                                                            String var38 = this.ldapDeltaRevocationListAttributeName;
                                                            String var39 = var3.ldapDeltaRevocationListAttributeName;
                                                            if(this.checkField(var38, var39)) {
                                                               String var40 = this.ldapAuthorityRevocationListAttributeName;
                                                               String var41 = var3.ldapAuthorityRevocationListAttributeName;
                                                               if(this.checkField(var40, var41)) {
                                                                  String var42 = this.ldapAttributeCertificateAttributeAttributeName;
                                                                  String var43 = var3.ldapAttributeCertificateAttributeAttributeName;
                                                                  if(this.checkField(var42, var43)) {
                                                                     String var44 = this.ldapAACertificateAttributeName;
                                                                     String var45 = var3.ldapAACertificateAttributeName;
                                                                     if(this.checkField(var44, var45)) {
                                                                        String var46 = this.ldapAttributeDescriptorCertificateAttributeName;
                                                                        String var47 = var3.ldapAttributeDescriptorCertificateAttributeName;
                                                                        if(this.checkField(var46, var47)) {
                                                                           String var48 = this.ldapAttributeCertificateRevocationListAttributeName;
                                                                           String var49 = var3.ldapAttributeCertificateRevocationListAttributeName;
                                                                           if(this.checkField(var48, var49)) {
                                                                              String var50 = this.ldapAttributeAuthorityRevocationListAttributeName;
                                                                              String var51 = var3.ldapAttributeAuthorityRevocationListAttributeName;
                                                                              if(this.checkField(var50, var51)) {
                                                                                 String var52 = this.userCertificateSubjectAttributeName;
                                                                                 String var53 = var3.userCertificateSubjectAttributeName;
                                                                                 if(this.checkField(var52, var53)) {
                                                                                    String var54 = this.cACertificateSubjectAttributeName;
                                                                                    String var55 = var3.cACertificateSubjectAttributeName;
                                                                                    if(this.checkField(var54, var55)) {
                                                                                       String var56 = this.crossCertificateSubjectAttributeName;
                                                                                       String var57 = var3.crossCertificateSubjectAttributeName;
                                                                                       if(this.checkField(var56, var57)) {
                                                                                          String var58 = this.certificateRevocationListIssuerAttributeName;
                                                                                          String var59 = var3.certificateRevocationListIssuerAttributeName;
                                                                                          if(this.checkField(var58, var59)) {
                                                                                             String var60 = this.deltaRevocationListIssuerAttributeName;
                                                                                             String var61 = var3.deltaRevocationListIssuerAttributeName;
                                                                                             if(this.checkField(var60, var61)) {
                                                                                                String var62 = this.authorityRevocationListIssuerAttributeName;
                                                                                                String var63 = var3.authorityRevocationListIssuerAttributeName;
                                                                                                if(this.checkField(var62, var63)) {
                                                                                                   String var64 = this.attributeCertificateAttributeSubjectAttributeName;
                                                                                                   String var65 = var3.attributeCertificateAttributeSubjectAttributeName;
                                                                                                   if(this.checkField(var64, var65)) {
                                                                                                      String var66 = this.aACertificateSubjectAttributeName;
                                                                                                      String var67 = var3.aACertificateSubjectAttributeName;
                                                                                                      if(this.checkField(var66, var67)) {
                                                                                                         String var68 = this.attributeDescriptorCertificateSubjectAttributeName;
                                                                                                         String var69 = var3.attributeDescriptorCertificateSubjectAttributeName;
                                                                                                         if(this.checkField(var68, var69)) {
                                                                                                            String var70 = this.attributeCertificateRevocationListIssuerAttributeName;
                                                                                                            String var71 = var3.attributeCertificateRevocationListIssuerAttributeName;
                                                                                                            if(this.checkField(var70, var71)) {
                                                                                                               String var72 = this.attributeAuthorityRevocationListIssuerAttributeName;
                                                                                                               String var73 = var3.attributeAuthorityRevocationListIssuerAttributeName;
                                                                                                               if(this.checkField(var72, var73)) {
                                                                                                                  String var74 = this.searchForSerialNumberIn;
                                                                                                                  String var75 = var3.searchForSerialNumberIn;
                                                                                                                  if(this.checkField(var74, var75)) {
                                                                                                                     var2 = true;
                                                                                                                     return var2;
                                                                                                                  }
                                                                                                               }
                                                                                                            }
                                                                                                         }
                                                                                                      }
                                                                                                   }
                                                                                                }
                                                                                             }
                                                                                          }
                                                                                       }
                                                                                    }
                                                                                 }
                                                                              }
                                                                           }
                                                                        }
                                                                     }
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getAACertificateAttribute() {
      return this.aACertificateAttribute;
   }

   public String getAACertificateSubjectAttributeName() {
      return this.aACertificateSubjectAttributeName;
   }

   public String getAttributeAuthorityRevocationListAttribute() {
      return this.attributeAuthorityRevocationListAttribute;
   }

   public String getAttributeAuthorityRevocationListIssuerAttributeName() {
      return this.attributeAuthorityRevocationListIssuerAttributeName;
   }

   public String getAttributeCertificateAttributeAttribute() {
      return this.attributeCertificateAttributeAttribute;
   }

   public String getAttributeCertificateAttributeSubjectAttributeName() {
      return this.attributeCertificateAttributeSubjectAttributeName;
   }

   public String getAttributeCertificateRevocationListAttribute() {
      return this.attributeCertificateRevocationListAttribute;
   }

   public String getAttributeCertificateRevocationListIssuerAttributeName() {
      return this.attributeCertificateRevocationListIssuerAttributeName;
   }

   public String getAttributeDescriptorCertificateAttribute() {
      return this.attributeDescriptorCertificateAttribute;
   }

   public String getAttributeDescriptorCertificateSubjectAttributeName() {
      return this.attributeDescriptorCertificateSubjectAttributeName;
   }

   public String getAuthorityRevocationListAttribute() {
      return this.authorityRevocationListAttribute;
   }

   public String getAuthorityRevocationListIssuerAttributeName() {
      return this.authorityRevocationListIssuerAttributeName;
   }

   public String getBaseDN() {
      return this.baseDN;
   }

   public String getCACertificateAttribute() {
      return this.cACertificateAttribute;
   }

   public String getCACertificateSubjectAttributeName() {
      return this.cACertificateSubjectAttributeName;
   }

   public String getCertificateRevocationListAttribute() {
      return this.certificateRevocationListAttribute;
   }

   public String getCertificateRevocationListIssuerAttributeName() {
      return this.certificateRevocationListIssuerAttributeName;
   }

   public String getCrossCertificateAttribute() {
      return this.crossCertificateAttribute;
   }

   public String getCrossCertificateSubjectAttributeName() {
      return this.crossCertificateSubjectAttributeName;
   }

   public String getDeltaRevocationListAttribute() {
      return this.deltaRevocationListAttribute;
   }

   public String getDeltaRevocationListIssuerAttributeName() {
      return this.deltaRevocationListIssuerAttributeName;
   }

   public String getLdapAACertificateAttributeName() {
      return this.ldapAACertificateAttributeName;
   }

   public String getLdapAttributeAuthorityRevocationListAttributeName() {
      return this.ldapAttributeAuthorityRevocationListAttributeName;
   }

   public String getLdapAttributeCertificateAttributeAttributeName() {
      return this.ldapAttributeCertificateAttributeAttributeName;
   }

   public String getLdapAttributeCertificateRevocationListAttributeName() {
      return this.ldapAttributeCertificateRevocationListAttributeName;
   }

   public String getLdapAttributeDescriptorCertificateAttributeName() {
      return this.ldapAttributeDescriptorCertificateAttributeName;
   }

   public String getLdapAuthorityRevocationListAttributeName() {
      return this.ldapAuthorityRevocationListAttributeName;
   }

   public String getLdapCACertificateAttributeName() {
      return this.ldapCACertificateAttributeName;
   }

   public String getLdapCertificateRevocationListAttributeName() {
      return this.ldapCertificateRevocationListAttributeName;
   }

   public String getLdapCrossCertificateAttributeName() {
      return this.ldapCrossCertificateAttributeName;
   }

   public String getLdapDeltaRevocationListAttributeName() {
      return this.ldapDeltaRevocationListAttributeName;
   }

   public String getLdapURL() {
      return this.ldapURL;
   }

   public String getLdapUserCertificateAttributeName() {
      return this.ldapUserCertificateAttributeName;
   }

   public String getSearchForSerialNumberIn() {
      return this.searchForSerialNumberIn;
   }

   public String getUserCertificateAttribute() {
      return this.userCertificateAttribute;
   }

   public String getUserCertificateSubjectAttributeName() {
      return this.userCertificateSubjectAttributeName;
   }

   public int hashCode() {
      String var1 = this.userCertificateAttribute;
      int var2 = this.addHashCode(0, var1);
      String var3 = this.cACertificateAttribute;
      int var4 = this.addHashCode(var2, var3);
      String var5 = this.crossCertificateAttribute;
      int var6 = this.addHashCode(var4, var5);
      String var7 = this.certificateRevocationListAttribute;
      int var8 = this.addHashCode(var6, var7);
      String var9 = this.deltaRevocationListAttribute;
      int var10 = this.addHashCode(var8, var9);
      String var11 = this.authorityRevocationListAttribute;
      int var12 = this.addHashCode(var10, var11);
      String var13 = this.attributeCertificateAttributeAttribute;
      int var14 = this.addHashCode(var12, var13);
      String var15 = this.aACertificateAttribute;
      int var16 = this.addHashCode(var14, var15);
      String var17 = this.attributeDescriptorCertificateAttribute;
      int var18 = this.addHashCode(var16, var17);
      String var19 = this.attributeCertificateRevocationListAttribute;
      int var20 = this.addHashCode(var18, var19);
      String var21 = this.attributeAuthorityRevocationListAttribute;
      int var22 = this.addHashCode(var20, var21);
      String var23 = this.ldapUserCertificateAttributeName;
      int var24 = this.addHashCode(var22, var23);
      String var25 = this.ldapCACertificateAttributeName;
      int var26 = this.addHashCode(var24, var25);
      String var27 = this.ldapCrossCertificateAttributeName;
      int var28 = this.addHashCode(var26, var27);
      String var29 = this.ldapCertificateRevocationListAttributeName;
      int var30 = this.addHashCode(var28, var29);
      String var31 = this.ldapDeltaRevocationListAttributeName;
      int var32 = this.addHashCode(var30, var31);
      String var33 = this.ldapAuthorityRevocationListAttributeName;
      int var34 = this.addHashCode(var32, var33);
      String var35 = this.ldapAttributeCertificateAttributeAttributeName;
      int var36 = this.addHashCode(var34, var35);
      String var37 = this.ldapAACertificateAttributeName;
      int var38 = this.addHashCode(var36, var37);
      String var39 = this.ldapAttributeDescriptorCertificateAttributeName;
      int var40 = this.addHashCode(var38, var39);
      String var41 = this.ldapAttributeCertificateRevocationListAttributeName;
      int var42 = this.addHashCode(var40, var41);
      String var43 = this.ldapAttributeAuthorityRevocationListAttributeName;
      int var44 = this.addHashCode(var42, var43);
      String var45 = this.userCertificateSubjectAttributeName;
      int var46 = this.addHashCode(var44, var45);
      String var47 = this.cACertificateSubjectAttributeName;
      int var48 = this.addHashCode(var46, var47);
      String var49 = this.crossCertificateSubjectAttributeName;
      int var50 = this.addHashCode(var48, var49);
      String var51 = this.certificateRevocationListIssuerAttributeName;
      int var52 = this.addHashCode(var50, var51);
      String var53 = this.deltaRevocationListIssuerAttributeName;
      int var54 = this.addHashCode(var52, var53);
      String var55 = this.authorityRevocationListIssuerAttributeName;
      int var56 = this.addHashCode(var54, var55);
      String var57 = this.attributeCertificateAttributeSubjectAttributeName;
      int var58 = this.addHashCode(var56, var57);
      String var59 = this.aACertificateSubjectAttributeName;
      int var60 = this.addHashCode(var58, var59);
      String var61 = this.attributeDescriptorCertificateSubjectAttributeName;
      int var62 = this.addHashCode(var60, var61);
      String var63 = this.attributeCertificateRevocationListIssuerAttributeName;
      int var64 = this.addHashCode(var62, var63);
      String var65 = this.attributeAuthorityRevocationListIssuerAttributeName;
      int var66 = this.addHashCode(var64, var65);
      String var67 = this.searchForSerialNumberIn;
      return this.addHashCode(var66, var67);
   }

   public static class Builder {

      private String aACertificateAttribute;
      private String aACertificateSubjectAttributeName;
      private String attributeAuthorityRevocationListAttribute;
      private String attributeAuthorityRevocationListIssuerAttributeName;
      private String attributeCertificateAttributeAttribute;
      private String attributeCertificateAttributeSubjectAttributeName;
      private String attributeCertificateRevocationListAttribute;
      private String attributeCertificateRevocationListIssuerAttributeName;
      private String attributeDescriptorCertificateAttribute;
      private String attributeDescriptorCertificateSubjectAttributeName;
      private String authorityRevocationListAttribute;
      private String authorityRevocationListIssuerAttributeName;
      private String baseDN;
      private String cACertificateAttribute;
      private String cACertificateSubjectAttributeName;
      private String certificateRevocationListAttribute;
      private String certificateRevocationListIssuerAttributeName;
      private String crossCertificateAttribute;
      private String crossCertificateSubjectAttributeName;
      private String deltaRevocationListAttribute;
      private String deltaRevocationListIssuerAttributeName;
      private String ldapAACertificateAttributeName;
      private String ldapAttributeAuthorityRevocationListAttributeName;
      private String ldapAttributeCertificateAttributeAttributeName;
      private String ldapAttributeCertificateRevocationListAttributeName;
      private String ldapAttributeDescriptorCertificateAttributeName;
      private String ldapAuthorityRevocationListAttributeName;
      private String ldapCACertificateAttributeName;
      private String ldapCertificateRevocationListAttributeName;
      private String ldapCrossCertificateAttributeName;
      private String ldapDeltaRevocationListAttributeName;
      private String ldapURL;
      private String ldapUserCertificateAttributeName;
      private String searchForSerialNumberIn;
      private String userCertificateAttribute;
      private String userCertificateSubjectAttributeName;


      public Builder() {
         this("ldap://localhost:389", "");
      }

      public Builder(String var1, String var2) {
         this.ldapURL = var1;
         if(var2 == null) {
            this.baseDN = "";
         } else {
            this.baseDN = var2;
         }

         this.userCertificateAttribute = "userCertificate";
         this.cACertificateAttribute = "cACertificate";
         this.crossCertificateAttribute = "crossCertificatePair";
         this.certificateRevocationListAttribute = "certificateRevocationList";
         this.deltaRevocationListAttribute = "deltaRevocationList";
         this.authorityRevocationListAttribute = "authorityRevocationList";
         this.attributeCertificateAttributeAttribute = "attributeCertificateAttribute";
         this.aACertificateAttribute = "aACertificate";
         this.attributeDescriptorCertificateAttribute = "attributeDescriptorCertificate";
         this.attributeCertificateRevocationListAttribute = "attributeCertificateRevocationList";
         this.attributeAuthorityRevocationListAttribute = "attributeAuthorityRevocationList";
         this.ldapUserCertificateAttributeName = "cn";
         this.ldapCACertificateAttributeName = "cn ou o";
         this.ldapCrossCertificateAttributeName = "cn ou o";
         this.ldapCertificateRevocationListAttributeName = "cn ou o";
         this.ldapDeltaRevocationListAttributeName = "cn ou o";
         this.ldapAuthorityRevocationListAttributeName = "cn ou o";
         this.ldapAttributeCertificateAttributeAttributeName = "cn";
         this.ldapAACertificateAttributeName = "cn o ou";
         this.ldapAttributeDescriptorCertificateAttributeName = "cn o ou";
         this.ldapAttributeCertificateRevocationListAttributeName = "cn o ou";
         this.ldapAttributeAuthorityRevocationListAttributeName = "cn o ou";
         this.userCertificateSubjectAttributeName = "cn";
         this.cACertificateSubjectAttributeName = "o ou";
         this.crossCertificateSubjectAttributeName = "o ou";
         this.certificateRevocationListIssuerAttributeName = "o ou";
         this.deltaRevocationListIssuerAttributeName = "o ou";
         this.authorityRevocationListIssuerAttributeName = "o ou";
         this.attributeCertificateAttributeSubjectAttributeName = "cn";
         this.aACertificateSubjectAttributeName = "o ou";
         this.attributeDescriptorCertificateSubjectAttributeName = "o ou";
         this.attributeCertificateRevocationListIssuerAttributeName = "o ou";
         this.attributeAuthorityRevocationListIssuerAttributeName = "o ou";
         this.searchForSerialNumberIn = "uid serialNumber cn";
      }

      public X509LDAPCertStoreParameters build() {
         if(this.ldapUserCertificateAttributeName != null && this.ldapCACertificateAttributeName != null && this.ldapCrossCertificateAttributeName != null && this.ldapCertificateRevocationListAttributeName != null && this.ldapDeltaRevocationListAttributeName != null && this.ldapAuthorityRevocationListAttributeName != null && this.ldapAttributeCertificateAttributeAttributeName != null && this.ldapAACertificateAttributeName != null && this.ldapAttributeDescriptorCertificateAttributeName != null && this.ldapAttributeCertificateRevocationListAttributeName != null && this.ldapAttributeAuthorityRevocationListAttributeName != null && this.userCertificateSubjectAttributeName != null && this.cACertificateSubjectAttributeName != null && this.crossCertificateSubjectAttributeName != null && this.certificateRevocationListIssuerAttributeName != null && this.deltaRevocationListIssuerAttributeName != null && this.authorityRevocationListIssuerAttributeName != null && this.attributeCertificateAttributeSubjectAttributeName != null && this.aACertificateSubjectAttributeName != null && this.attributeDescriptorCertificateSubjectAttributeName != null && this.attributeCertificateRevocationListIssuerAttributeName != null && this.attributeAuthorityRevocationListIssuerAttributeName != null) {
            return new X509LDAPCertStoreParameters(this, (X509LDAPCertStoreParameters.1)null);
         } else {
            throw new IllegalArgumentException("Necessary parameters not specified.");
         }
      }

      public X509LDAPCertStoreParameters.Builder setAACertificateAttribute(String var1) {
         this.aACertificateAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAACertificateSubjectAttributeName(String var1) {
         this.aACertificateSubjectAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAttributeAuthorityRevocationListAttribute(String var1) {
         this.attributeAuthorityRevocationListAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAttributeAuthorityRevocationListIssuerAttributeName(String var1) {
         this.attributeAuthorityRevocationListIssuerAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAttributeCertificateAttributeAttribute(String var1) {
         this.attributeCertificateAttributeAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAttributeCertificateAttributeSubjectAttributeName(String var1) {
         this.attributeCertificateAttributeSubjectAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAttributeCertificateRevocationListAttribute(String var1) {
         this.attributeCertificateRevocationListAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAttributeCertificateRevocationListIssuerAttributeName(String var1) {
         this.attributeCertificateRevocationListIssuerAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAttributeDescriptorCertificateAttribute(String var1) {
         this.attributeDescriptorCertificateAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAttributeDescriptorCertificateSubjectAttributeName(String var1) {
         this.attributeDescriptorCertificateSubjectAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAuthorityRevocationListAttribute(String var1) {
         this.authorityRevocationListAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setAuthorityRevocationListIssuerAttributeName(String var1) {
         this.authorityRevocationListIssuerAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setCACertificateAttribute(String var1) {
         this.cACertificateAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setCACertificateSubjectAttributeName(String var1) {
         this.cACertificateSubjectAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setCertificateRevocationListAttribute(String var1) {
         this.certificateRevocationListAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setCertificateRevocationListIssuerAttributeName(String var1) {
         this.certificateRevocationListIssuerAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setCrossCertificateAttribute(String var1) {
         this.crossCertificateAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setCrossCertificateSubjectAttributeName(String var1) {
         this.crossCertificateSubjectAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setDeltaRevocationListAttribute(String var1) {
         this.deltaRevocationListAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setDeltaRevocationListIssuerAttributeName(String var1) {
         this.deltaRevocationListIssuerAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapAACertificateAttributeName(String var1) {
         this.ldapAACertificateAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapAttributeAuthorityRevocationListAttributeName(String var1) {
         this.ldapAttributeAuthorityRevocationListAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapAttributeCertificateAttributeAttributeName(String var1) {
         this.ldapAttributeCertificateAttributeAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapAttributeCertificateRevocationListAttributeName(String var1) {
         this.ldapAttributeCertificateRevocationListAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapAttributeDescriptorCertificateAttributeName(String var1) {
         this.ldapAttributeDescriptorCertificateAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapAuthorityRevocationListAttributeName(String var1) {
         this.ldapAuthorityRevocationListAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapCACertificateAttributeName(String var1) {
         this.ldapCACertificateAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapCertificateRevocationListAttributeName(String var1) {
         this.ldapCertificateRevocationListAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapCrossCertificateAttributeName(String var1) {
         this.ldapCrossCertificateAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapDeltaRevocationListAttributeName(String var1) {
         this.ldapDeltaRevocationListAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setLdapUserCertificateAttributeName(String var1) {
         this.ldapUserCertificateAttributeName = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setSearchForSerialNumberIn(String var1) {
         this.searchForSerialNumberIn = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setUserCertificateAttribute(String var1) {
         this.userCertificateAttribute = var1;
         return this;
      }

      public X509LDAPCertStoreParameters.Builder setUserCertificateSubjectAttributeName(String var1) {
         this.userCertificateSubjectAttributeName = var1;
         return this;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
