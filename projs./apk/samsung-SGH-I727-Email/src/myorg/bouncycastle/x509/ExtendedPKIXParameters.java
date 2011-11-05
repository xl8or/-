package myorg.bouncycastle.x509;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import myorg.bouncycastle.util.Selector;
import myorg.bouncycastle.util.Store;
import myorg.bouncycastle.x509.PKIXAttrCertChecker;
import myorg.bouncycastle.x509.X509CertStoreSelector;

public class ExtendedPKIXParameters extends PKIXParameters {

   public static final int CHAIN_VALIDITY_MODEL = 1;
   public static final int PKIX_VALIDITY_MODEL;
   private boolean additionalLocationsEnabled;
   private List additionalStores;
   private Set attrCertCheckers;
   private Set necessaryACAttributes;
   private Set prohibitedACAttributes;
   private Selector selector;
   private List stores;
   private Set trustedACIssuers;
   private boolean useDeltas = 0;
   private int validityModel = 0;


   public ExtendedPKIXParameters(Set var1) throws InvalidAlgorithmParameterException {
      super(var1);
      ArrayList var2 = new ArrayList();
      this.stores = var2;
      ArrayList var3 = new ArrayList();
      this.additionalStores = var3;
      HashSet var4 = new HashSet();
      this.trustedACIssuers = var4;
      HashSet var5 = new HashSet();
      this.necessaryACAttributes = var5;
      HashSet var6 = new HashSet();
      this.prohibitedACAttributes = var6;
      HashSet var7 = new HashSet();
      this.attrCertCheckers = var7;
   }

   public static ExtendedPKIXParameters getInstance(PKIXParameters var0) {
      ExtendedPKIXParameters var2;
      try {
         Set var1 = var0.getTrustAnchors();
         var2 = new ExtendedPKIXParameters(var1);
      } catch (Exception var4) {
         String var3 = var4.getMessage();
         throw new RuntimeException(var3);
      }

      var2.setParams(var0);
      return var2;
   }

   public void addAddionalStore(Store var1) {
      this.addAdditionalStore(var1);
   }

   public void addAdditionalStore(Store var1) {
      if(var1 != null) {
         this.additionalStores.add(var1);
      }
   }

   public void addStore(Store var1) {
      if(var1 != null) {
         this.stores.add(var1);
      }
   }

   public Object clone() {
      ExtendedPKIXParameters var2;
      try {
         Set var1 = this.getTrustAnchors();
         var2 = new ExtendedPKIXParameters(var1);
      } catch (Exception var4) {
         String var3 = var4.getMessage();
         throw new RuntimeException(var3);
      }

      var2.setParams(this);
      return var2;
   }

   public List getAdditionalStores() {
      return Collections.unmodifiableList(this.additionalStores);
   }

   public Set getAttrCertCheckers() {
      return Collections.unmodifiableSet(this.attrCertCheckers);
   }

   public Set getNecessaryACAttributes() {
      return Collections.unmodifiableSet(this.necessaryACAttributes);
   }

   public Set getProhibitedACAttributes() {
      return Collections.unmodifiableSet(this.prohibitedACAttributes);
   }

   public List getStores() {
      List var1 = this.stores;
      return Collections.unmodifiableList(new ArrayList(var1));
   }

   public Selector getTargetConstraints() {
      Selector var1;
      if(this.selector != null) {
         var1 = (Selector)this.selector.clone();
      } else {
         var1 = null;
      }

      return var1;
   }

   public Set getTrustedACIssuers() {
      return Collections.unmodifiableSet(this.trustedACIssuers);
   }

   public int getValidityModel() {
      return this.validityModel;
   }

   public boolean isAdditionalLocationsEnabled() {
      return this.additionalLocationsEnabled;
   }

   public boolean isUseDeltasEnabled() {
      return this.useDeltas;
   }

   public void setAdditionalLocationsEnabled(boolean var1) {
      this.additionalLocationsEnabled = var1;
   }

   public void setAttrCertCheckers(Set var1) {
      if(var1 == null) {
         this.attrCertCheckers.clear();
      } else {
         Iterator var2 = var1.iterator();

         do {
            if(!var2.hasNext()) {
               this.attrCertCheckers.clear();
               this.attrCertCheckers.addAll(var1);
               return;
            }
         } while(var2.next() instanceof PKIXAttrCertChecker);

         StringBuilder var3 = (new StringBuilder()).append("All elements of set must be of type ");
         String var4 = PKIXAttrCertChecker.class.getName();
         String var5 = var3.append(var4).append(".").toString();
         throw new ClassCastException(var5);
      }
   }

   public void setCertStores(List var1) {
      if(var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            CertStore var3 = (CertStore)var2.next();
            this.addCertStore(var3);
         }

      }
   }

   public void setNecessaryACAttributes(Set var1) {
      if(var1 == null) {
         this.necessaryACAttributes.clear();
      } else {
         Iterator var2 = var1.iterator();

         do {
            if(!var2.hasNext()) {
               this.necessaryACAttributes.clear();
               this.necessaryACAttributes.addAll(var1);
               return;
            }
         } while(var2.next() instanceof String);

         throw new ClassCastException("All elements of set must be of type String.");
      }
   }

   protected void setParams(PKIXParameters var1) {
      Date var2 = var1.getDate();
      this.setDate(var2);
      List var3 = var1.getCertPathCheckers();
      this.setCertPathCheckers(var3);
      List var4 = var1.getCertStores();
      this.setCertStores(var4);
      boolean var5 = var1.isAnyPolicyInhibited();
      this.setAnyPolicyInhibited(var5);
      boolean var6 = var1.isExplicitPolicyRequired();
      this.setExplicitPolicyRequired(var6);
      boolean var7 = var1.isPolicyMappingInhibited();
      this.setPolicyMappingInhibited(var7);
      boolean var8 = var1.isRevocationEnabled();
      this.setRevocationEnabled(var8);
      Set var9 = var1.getInitialPolicies();
      this.setInitialPolicies(var9);
      boolean var10 = var1.getPolicyQualifiersRejected();
      this.setPolicyQualifiersRejected(var10);
      String var11 = var1.getSigProvider();
      this.setSigProvider(var11);
      CertSelector var12 = var1.getTargetCertConstraints();
      this.setTargetCertConstraints(var12);

      try {
         Set var13 = var1.getTrustAnchors();
         this.setTrustAnchors(var13);
      } catch (Exception var32) {
         String var31 = var32.getMessage();
         throw new RuntimeException(var31);
      }

      if(var1 instanceof ExtendedPKIXParameters) {
         ExtendedPKIXParameters var14 = (ExtendedPKIXParameters)var1;
         int var15 = var14.validityModel;
         this.validityModel = var15;
         boolean var16 = var14.useDeltas;
         this.useDeltas = var16;
         boolean var17 = var14.additionalLocationsEnabled;
         this.additionalLocationsEnabled = var17;
         Selector var18;
         if(var14.selector == null) {
            var18 = null;
         } else {
            var18 = (Selector)var14.selector.clone();
         }

         this.selector = var18;
         List var19 = var14.stores;
         ArrayList var20 = new ArrayList(var19);
         this.stores = var20;
         List var21 = var14.additionalStores;
         ArrayList var22 = new ArrayList(var21);
         this.additionalStores = var22;
         Set var23 = var14.trustedACIssuers;
         HashSet var24 = new HashSet(var23);
         this.trustedACIssuers = var24;
         Set var25 = var14.prohibitedACAttributes;
         HashSet var26 = new HashSet(var25);
         this.prohibitedACAttributes = var26;
         Set var27 = var14.necessaryACAttributes;
         HashSet var28 = new HashSet(var27);
         this.necessaryACAttributes = var28;
         Set var29 = var14.attrCertCheckers;
         HashSet var30 = new HashSet(var29);
         this.attrCertCheckers = var30;
      }
   }

   public void setProhibitedACAttributes(Set var1) {
      if(var1 == null) {
         this.prohibitedACAttributes.clear();
      } else {
         Iterator var2 = var1.iterator();

         do {
            if(!var2.hasNext()) {
               this.prohibitedACAttributes.clear();
               this.prohibitedACAttributes.addAll(var1);
               return;
            }
         } while(var2.next() instanceof String);

         throw new ClassCastException("All elements of set must be of type String.");
      }
   }

   public void setStores(List var1) {
      if(var1 == null) {
         ArrayList var2 = new ArrayList();
         this.stores = var2;
      } else {
         Iterator var3 = var1.iterator();

         do {
            if(!var3.hasNext()) {
               ArrayList var4 = new ArrayList(var1);
               this.stores = var4;
               return;
            }
         } while(var3.next() instanceof Store);

         throw new ClassCastException("All elements of list must be of type org.bouncycastle.util.Store.");
      }
   }

   public void setTargetCertConstraints(CertSelector var1) {
      super.setTargetCertConstraints(var1);
      if(var1 != null) {
         X509CertStoreSelector var2 = X509CertStoreSelector.getInstance((X509CertSelector)var1);
         this.selector = var2;
      } else {
         this.selector = null;
      }
   }

   public void setTargetConstraints(Selector var1) {
      if(var1 != null) {
         Selector var2 = (Selector)var1.clone();
         this.selector = var2;
      } else {
         this.selector = null;
      }
   }

   public void setTrustedACIssuers(Set var1) {
      if(var1 == null) {
         this.trustedACIssuers.clear();
      } else {
         Iterator var2 = var1.iterator();

         do {
            if(!var2.hasNext()) {
               this.trustedACIssuers.clear();
               this.trustedACIssuers.addAll(var1);
               return;
            }
         } while(var2.next() instanceof TrustAnchor);

         StringBuilder var3 = (new StringBuilder()).append("All elements of set must be of type ");
         String var4 = TrustAnchor.class.getName();
         String var5 = var3.append(var4).append(".").toString();
         throw new ClassCastException(var5);
      }
   }

   public void setUseDeltasEnabled(boolean var1) {
      this.useDeltas = var1;
   }

   public void setValidityModel(int var1) {
      this.validityModel = var1;
   }
}
