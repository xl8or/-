package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressProblemType;
import com.android.i18n.addressinput.AddressProblems;
import com.android.i18n.addressinput.DataLoadListener;
import com.android.i18n.addressinput.FieldVerifier;
import com.android.i18n.addressinput.LookupKey;
import com.android.i18n.addressinput.NotifyingListener;
import com.android.i18n.addressinput.StandardChecks;
import com.android.i18n.addressinput.Util;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StandardAddressVerifier {

   private static final StandardAddressVerifier.VerifierRefiner DEFAULT_REFINER = new StandardAddressVerifier.VerifierRefiner();
   protected final Map<AddressField, List<AddressProblemType>> mProblemMap;
   protected final StandardAddressVerifier.VerifierRefiner mRefiner;
   protected final FieldVerifier mRootVerifier;


   public StandardAddressVerifier(FieldVerifier var1) {
      StandardAddressVerifier.VerifierRefiner var2 = DEFAULT_REFINER;
      Map var3 = StandardChecks.PROBLEM_MAP;
      this(var1, var2, var3);
   }

   public StandardAddressVerifier(FieldVerifier var1, StandardAddressVerifier.VerifierRefiner var2) {
      Map var3 = StandardChecks.PROBLEM_MAP;
      this(var1, var2, var3);
   }

   public StandardAddressVerifier(FieldVerifier var1, StandardAddressVerifier.VerifierRefiner var2, Map<AddressField, List<AddressProblemType>> var3) {
      this.mRootVerifier = var1;
      this.mRefiner = var2;
      Map var4 = StandardChecks.PROBLEM_MAP;
      this.mProblemMap = var4;
   }

   public StandardAddressVerifier(FieldVerifier var1, Map<AddressField, List<AddressProblemType>> var2) {
      StandardAddressVerifier.VerifierRefiner var3 = DEFAULT_REFINER;
      this(var1, var3, var2);
   }

   protected Iterator<AddressProblemType> getProblemIterator(AddressField var1) {
      List var2 = (List)this.mProblemMap.get(var1);
      if(var2 == null) {
         var2 = Collections.emptyList();
      }

      return var2.iterator();
   }

   protected void postVerify(FieldVerifier var1, AddressData var2, AddressProblems var3) {}

   public void verify(AddressData var1, AddressProblems var2) {
      NotifyingListener var3 = new NotifyingListener(this);
      this.verifyAsync(var1, var2, var3);

      try {
         var3.waitLoadingEnd();
      } catch (InterruptedException var5) {
         throw new RuntimeException(var5);
      }
   }

   public void verifyAsync(AddressData var1, AddressProblems var2, DataLoadListener var3) {
      StandardAddressVerifier.Verifier var4 = new StandardAddressVerifier.Verifier(var1, var2, var3);
      (new Thread(var4)).start();
   }

   protected boolean verifyField(LookupKey.ScriptType var1, FieldVerifier var2, AddressField var3, String var4, AddressProblems var5) {
      Iterator var6 = this.getProblemIterator(var3);

      boolean var14;
      while(true) {
         if(var6.hasNext()) {
            AddressProblemType var7 = (AddressProblemType)var6.next();
            if(this.verifyProblemField(var1, var2, var7, var3, var4, var5)) {
               continue;
            }

            var14 = false;
            break;
         }

         var14 = true;
         break;
      }

      return var14;
   }

   protected boolean verifyProblemField(LookupKey.ScriptType var1, FieldVerifier var2, AddressProblemType var3, AddressField var4, String var5, AddressProblems var6) {
      return var2.check(var1, var3, var4, var5, var6);
   }

   public static class VerifierRefiner {

      public VerifierRefiner() {}

      public StandardAddressVerifier.VerifierRefiner newInstance() {
         return this;
      }

      public FieldVerifier refineVerifier(FieldVerifier var1, AddressField var2, String var3) {
         return var1.refineVerifier(var3);
      }
   }

   private class Verifier implements Runnable {

      private AddressData mAddress;
      private DataLoadListener mListener;
      private AddressProblems mProblems;


      Verifier(AddressData var2, AddressProblems var3, DataLoadListener var4) {
         this.mAddress = var2;
         this.mProblems = var3;
         this.mListener = var4;
      }

      public void run() {
         this.mListener.dataLoadingBegin();
         FieldVerifier var1 = StandardAddressVerifier.this.mRootVerifier;
         LookupKey.ScriptType var2 = null;
         if(this.mAddress.getLanguageCode() != null) {
            if(Util.isExplicitLatinScript(this.mAddress.getLanguageCode())) {
               var2 = LookupKey.ScriptType.LATIN;
            } else {
               var2 = LookupKey.ScriptType.LOCAL;
            }
         }

         StandardAddressVerifier var3 = StandardAddressVerifier.this;
         AddressField var4 = AddressField.COUNTRY;
         String var5 = this.mAddress.getPostalCountry();
         AddressProblems var6 = this.mProblems;
         var3.verifyField(var2, var1, var4, var5, var6);
         if(this.mProblems.isEmpty()) {
            String var8 = this.mAddress.getPostalCountry();
            var1 = var1.refineVerifier(var8);
            StandardAddressVerifier var9 = StandardAddressVerifier.this;
            AddressField var10 = AddressField.ADMIN_AREA;
            String var11 = this.mAddress.getAdministrativeArea();
            AddressProblems var12 = this.mProblems;
            var9.verifyField(var2, var1, var10, var11, var12);
            if(this.mProblems.isEmpty()) {
               String var14 = this.mAddress.getAdministrativeArea();
               var1 = var1.refineVerifier(var14);
               StandardAddressVerifier var15 = StandardAddressVerifier.this;
               AddressField var16 = AddressField.LOCALITY;
               String var17 = this.mAddress.getLocality();
               AddressProblems var18 = this.mProblems;
               var15.verifyField(var2, var1, var16, var17, var18);
               if(this.mProblems.isEmpty()) {
                  String var20 = this.mAddress.getLocality();
                  var1 = var1.refineVerifier(var20);
                  StandardAddressVerifier var21 = StandardAddressVerifier.this;
                  AddressField var22 = AddressField.DEPENDENT_LOCALITY;
                  String var23 = this.mAddress.getDependentLocality();
                  AddressProblems var24 = this.mProblems;
                  var21.verifyField(var2, var1, var22, var23, var24);
                  if(this.mProblems.isEmpty()) {
                     String var26 = this.mAddress.getDependentLocality();
                     var1 = var1.refineVerifier(var26);
                  }
               }
            }
         }

         String[] var27 = new String[2];
         String var28 = this.mAddress.getAddressLine1();
         var27[0] = var28;
         String var29 = this.mAddress.getAddressLine2();
         var27[1] = var29;
         String var30 = Util.joinAndSkipNulls("\n", var27);
         StandardAddressVerifier var31 = StandardAddressVerifier.this;
         AddressField var32 = AddressField.POSTAL_CODE;
         String var33 = this.mAddress.getPostalCode();
         AddressProblems var34 = this.mProblems;
         var31.verifyField(var2, var1, var32, var33, var34);
         StandardAddressVerifier var36 = StandardAddressVerifier.this;
         AddressField var37 = AddressField.STREET_ADDRESS;
         AddressProblems var38 = this.mProblems;
         var36.verifyField(var2, var1, var37, var30, var38);
         StandardAddressVerifier var41 = StandardAddressVerifier.this;
         AddressField var42 = AddressField.SORTING_CODE;
         String var43 = this.mAddress.getSortingCode();
         AddressProblems var44 = this.mProblems;
         var41.verifyField(var2, var1, var42, var43, var44);
         StandardAddressVerifier var46 = StandardAddressVerifier.this;
         AddressField var47 = AddressField.ORGANIZATION;
         String var48 = this.mAddress.getOrganization();
         AddressProblems var49 = this.mProblems;
         var46.verifyField(var2, var1, var47, var48, var49);
         StandardAddressVerifier var51 = StandardAddressVerifier.this;
         AddressField var52 = AddressField.RECIPIENT;
         String var53 = this.mAddress.getRecipient();
         AddressProblems var54 = this.mProblems;
         var51.verifyField(var2, var1, var52, var53, var54);
         StandardAddressVerifier var56 = StandardAddressVerifier.this;
         AddressData var57 = this.mAddress;
         AddressProblems var58 = this.mProblems;
         var56.postVerify(var1, var57, var58);
         this.mListener.dataLoadingEnd();
      }
   }
}
