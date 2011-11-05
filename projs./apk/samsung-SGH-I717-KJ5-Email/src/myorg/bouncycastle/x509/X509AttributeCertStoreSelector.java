package myorg.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import myorg.bouncycastle.asn1.ASN1InputStream;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.Target;
import myorg.bouncycastle.asn1.x509.TargetInformation;
import myorg.bouncycastle.asn1.x509.Targets;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.x509.AttributeCertificateHolder;
import myorg.bouncycastle.x509.AttributeCertificateIssuer;
import myorg.bouncycastle.x509.X509AttributeCertificate;

public class X509AttributeCertStoreSelector implements Selector {

   private X509AttributeCertificate attributeCert;
   private Date attributeCertificateValid;
   private AttributeCertificateHolder holder;
   private AttributeCertificateIssuer issuer;
   private BigInteger serialNumber;
   private Collection targetGroups;
   private Collection targetNames;


   public X509AttributeCertStoreSelector() {
      HashSet var1 = new HashSet();
      this.targetNames = var1;
      HashSet var2 = new HashSet();
      this.targetGroups = var2;
   }

   private Set extractGeneralNames(Collection var1) throws IOException {
      HashSet var2;
      if(var1 != null && !var1.isEmpty()) {
         HashSet var3 = new HashSet();
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            if(var5 instanceof GeneralName) {
               var3.add(var5);
            } else {
               GeneralName var7 = GeneralName.getInstance(ASN1Object.fromByteArray((byte[])((byte[])var5)));
               var3.add(var7);
            }
         }

         var2 = var3;
      } else {
         var2 = new HashSet();
      }

      return var2;
   }

   public void addTargetGroup(GeneralName var1) {
      this.targetGroups.add(var1);
   }

   public void addTargetGroup(byte[] var1) throws IOException {
      GeneralName var2 = GeneralName.getInstance(ASN1Object.fromByteArray(var1));
      this.addTargetGroup(var2);
   }

   public void addTargetName(GeneralName var1) {
      this.targetNames.add(var1);
   }

   public void addTargetName(byte[] var1) throws IOException {
      GeneralName var2 = GeneralName.getInstance(ASN1Object.fromByteArray(var1));
      this.addTargetName(var2);
   }

   public Object clone() {
      X509AttributeCertStoreSelector var1 = new X509AttributeCertStoreSelector();
      X509AttributeCertificate var2 = this.attributeCert;
      var1.attributeCert = var2;
      Date var3 = this.getAttributeCertificateValid();
      var1.attributeCertificateValid = var3;
      AttributeCertificateHolder var4 = this.holder;
      var1.holder = var4;
      AttributeCertificateIssuer var5 = this.issuer;
      var1.issuer = var5;
      BigInteger var6 = this.serialNumber;
      var1.serialNumber = var6;
      Collection var7 = this.getTargetGroups();
      var1.targetGroups = var7;
      Collection var8 = this.getTargetNames();
      var1.targetNames = var8;
      return var1;
   }

   public X509AttributeCertificate getAttributeCert() {
      return this.attributeCert;
   }

   public Date getAttributeCertificateValid() {
      Date var3;
      if(this.attributeCertificateValid != null) {
         long var1 = this.attributeCertificateValid.getTime();
         var3 = new Date(var1);
      } else {
         var3 = null;
      }

      return var3;
   }

   public AttributeCertificateHolder getHolder() {
      return this.holder;
   }

   public AttributeCertificateIssuer getIssuer() {
      return this.issuer;
   }

   public BigInteger getSerialNumber() {
      return this.serialNumber;
   }

   public Collection getTargetGroups() {
      return Collections.unmodifiableCollection(this.targetGroups);
   }

   public Collection getTargetNames() {
      return Collections.unmodifiableCollection(this.targetNames);
   }

   public boolean match(Object var1) {
      boolean var2;
      if(!(var1 instanceof X509AttributeCertificate)) {
         var2 = false;
      } else {
         X509AttributeCertificate var3 = (X509AttributeCertificate)var1;
         if(this.attributeCert != null && !this.attributeCert.equals(var3)) {
            var2 = false;
         } else {
            if(this.serialNumber != null) {
               BigInteger var4 = var3.getSerialNumber();
               BigInteger var5 = this.serialNumber;
               if(!var4.equals(var5)) {
                  var2 = false;
                  return var2;
               }
            }

            if(this.holder != null) {
               AttributeCertificateHolder var6 = var3.getHolder();
               AttributeCertificateHolder var7 = this.holder;
               if(!var6.equals(var7)) {
                  var2 = false;
                  return var2;
               }
            }

            if(this.issuer != null) {
               AttributeCertificateIssuer var8 = var3.getIssuer();
               AttributeCertificateIssuer var9 = this.issuer;
               if(!var8.equals(var9)) {
                  var2 = false;
                  return var2;
               }
            }

            if(this.attributeCertificateValid != null) {
               try {
                  Date var10 = this.attributeCertificateValid;
                  var3.checkValidity(var10);
               } catch (CertificateExpiredException var37) {
                  var2 = false;
                  return var2;
               } catch (CertificateNotYetValidException var38) {
                  var2 = false;
                  return var2;
               }
            }

            if(!this.targetNames.isEmpty() || !this.targetGroups.isEmpty()) {
               String var11 = X509Extensions.TargetInformation.getId();
               byte[] var12 = var3.getExtensionValue(var11);
               if(var12 != null) {
                  TargetInformation var14;
                  try {
                     byte[] var13 = ((DEROctetString)DEROctetString.fromByteArray(var12)).getOctets();
                     var14 = TargetInformation.getInstance((new ASN1InputStream(var13)).readObject());
                  } catch (IOException var35) {
                     var2 = false;
                     return var2;
                  } catch (IllegalArgumentException var36) {
                     var2 = false;
                     return var2;
                  }

                  Targets[] var15 = var14.getTargetsObjects();
                  boolean var16;
                  if(!this.targetNames.isEmpty()) {
                     var16 = false;
                     int var17 = 0;

                     while(true) {
                        int var18 = var15.length;
                        if(var17 >= var18) {
                           if(!var16) {
                              var2 = false;
                              return var2;
                           }
                           break;
                        }

                        Target[] var19 = var15[var17].getTargets();
                        int var20 = 0;

                        while(true) {
                           int var21 = var19.length;
                           if(var20 >= var21) {
                              break;
                           }

                           Collection var22 = this.targetNames;
                           GeneralName var23 = GeneralName.getInstance(var19[var20].getTargetName());
                           if(var22.contains(var23)) {
                              var16 = true;
                              break;
                           }

                           ++var20;
                        }

                        ++var17;
                     }
                  }

                  if(!this.targetGroups.isEmpty()) {
                     var16 = false;
                     byte var39 = 0;

                     while(true) {
                        int var28 = var15.length;
                        if(var39 >= var28) {
                           if(!var16) {
                              var2 = false;
                              return var2;
                           }
                           break;
                        }

                        Target[] var29 = var15[var39].getTargets();
                        int var30 = 0;

                        while(true) {
                           int var31 = var29.length;
                           if(var30 >= var31) {
                              break;
                           }

                           Collection var32 = this.targetGroups;
                           GeneralName var33 = GeneralName.getInstance(var29[var30].getTargetGroup());
                           if(var32.contains(var33)) {
                              break;
                           }

                           ++var30;
                        }

                        int var34 = var39 + 1;
                     }
                  }
               }
            }

            var2 = true;
         }
      }

      return var2;
   }

   public void setAttributeCert(X509AttributeCertificate var1) {
      this.attributeCert = var1;
   }

   public void setAttributeCertificateValid(Date var1) {
      if(var1 != null) {
         long var2 = var1.getTime();
         Date var4 = new Date(var2);
         this.attributeCertificateValid = var4;
      } else {
         this.attributeCertificateValid = null;
      }
   }

   public void setHolder(AttributeCertificateHolder var1) {
      this.holder = var1;
   }

   public void setIssuer(AttributeCertificateIssuer var1) {
      this.issuer = var1;
   }

   public void setSerialNumber(BigInteger var1) {
      this.serialNumber = var1;
   }

   public void setTargetGroups(Collection var1) throws IOException {
      Set var2 = this.extractGeneralNames(var1);
      this.targetGroups = var2;
   }

   public void setTargetNames(Collection var1) throws IOException {
      Set var2 = this.extractGeneralNames(var1);
      this.targetNames = var2;
   }
}
