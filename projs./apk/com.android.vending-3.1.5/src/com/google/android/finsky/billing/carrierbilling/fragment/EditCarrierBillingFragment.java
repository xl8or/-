package com.google.android.finsky.billing.carrierbilling.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressProblems;
import com.android.i18n.addressinput.AddressWidget;
import com.android.i18n.addressinput.FormOptions;
import com.android.volley.Cache;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.AddressMetadataCacheManager;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.carrierbilling.PhoneCarrierBillingUtils;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.utils.FinskyLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class EditCarrierBillingFragment extends Fragment implements OnClickListener {

   private static final String HIGHLIGHT_ADDRESS_KEY = "highlight_address";
   private static final String PREFILL_ADDRESS_KEY = "prefill_address";
   private AddressWidget mAddressWidget;
   private ViewGroup mEditSection;
   Handler mHandler;
   private EditCarrierBillingFragment.EditCarrierBillingResultListener mListener;
   private TextView mNameView;
   private EditText mPhoneNumberEditView;
   private TextView mPhoneNumberView;


   public EditCarrierBillingFragment() {
      Handler var1 = new Handler();
      this.mHandler = var1;
   }

   private void displayErrorToast() {
      Toast.makeText(this.getActivity(), 2131230808, 1).show();
   }

   private void displayErrors(Collection<PhoneCarrierBillingUtils.AddressInputField> var1) {
      Object var2 = null;
      int var3 = 0;
      boolean var4 = false;
      Iterator var5 = var1.iterator();

      while(var5.hasNext()) {
         PhoneCarrierBillingUtils.AddressInputField var6 = (PhoneCarrierBillingUtils.AddressInputField)var5.next();
         int[] var7 = EditCarrierBillingFragment.1.$SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField;
         int var8 = var6.ordinal();
         Object var11;
         switch(var7[var8]) {
         case 1:
         case 2:
            AddressWidget var9 = this.mAddressWidget;
            AddressField var10 = AddressField.ADDRESS_LINE_1;
            var11 = var9.getViewForField(var10);
            if(var11 != null) {
               TextView var12 = (TextView)var11;
               this.setTextViewError(var12, 2131230793);
               var4 = true;
            }
            break;
         case 3:
            AddressWidget var13 = this.mAddressWidget;
            AddressField var14 = AddressField.ADDRESS_LINE_2;
            var11 = var13.getViewForField(var14);
            if(var11 != null) {
               TextView var15 = (TextView)var11;
               this.setTextViewError(var15, 2131230793);
               var4 = true;
            }
            break;
         case 4:
            AddressWidget var16 = this.mAddressWidget;
            AddressField var17 = AddressField.LOCALITY;
            var11 = var16.getViewForField(var17);
            if(var11 != null) {
               TextView var18 = (TextView)var11;
               this.setTextViewError(var18, 2131230795);
               var4 = true;
            }
            break;
         case 5:
            var11 = this.mNameView;
            TextView var19 = (TextView)var11;
            this.setTextViewError(var19, 2131230792);
            var4 = true;
            break;
         case 6:
            AddressWidget var20 = this.mAddressWidget;
            AddressField var21 = AddressField.POSTAL_CODE;
            var11 = var20.getViewForField(var21);
            if(var11 != null) {
               AddressWidget var22 = this.mAddressWidget;
               AddressField var23 = AddressField.POSTAL_CODE;
               var22.displayErrorMessageForInvalidEntryIn(var23);
               var4 = true;
            }
            break;
         case 7:
            AddressWidget var25 = this.mAddressWidget;
            AddressField var26 = AddressField.ADMIN_AREA;
            var11 = var25.getViewForField(var26);
            if(var11 != null) {
               AddressWidget var27 = this.mAddressWidget;
               AddressField var28 = AddressField.ADMIN_AREA;
               var27.displayErrorMessageForInvalidEntryIn(var28);
               var4 = true;
            }
            break;
         case 8:
            if(this.mPhoneNumberView.getVisibility() == 0) {
               var11 = this.mPhoneNumberView;
            } else {
               var11 = this.mPhoneNumberEditView;
            }

            TextView var30 = (TextView)var11;
            this.setTextViewError(var30, 2131230797);
            var4 = true;
            break;
         default:
            continue;
         }

         if(var2 == null) {
            var2 = var11;
            var3 = BillingUtils.getViewOffsetToChild(this.mEditSection, (View)var11);
         } else if(var11 != null) {
            int var31 = BillingUtils.getViewOffsetToChild(this.mEditSection, (View)var11);
            if(var31 < var3) {
               var2 = var11;
               var3 = var31;
            }
         }
      }

      if(var4) {
         this.displayErrorToast();
      }

      boolean var32 = ((View)var2).requestFocus();
   }

   private Collection<PhoneCarrierBillingUtils.AddressInputField> getFormErrors(ArrayList<Integer> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Integer var4 = (Integer)var3.next();
         switch(var4.intValue()) {
         case 4:
            PhoneCarrierBillingUtils.AddressInputField var6 = PhoneCarrierBillingUtils.AddressInputField.PERSON_NAME;
            var2.add(var6);
            break;
         case 6:
            PhoneCarrierBillingUtils.AddressInputField var17 = PhoneCarrierBillingUtils.AddressInputField.ADDR_ADDRESS2;
            var2.add(var17);
            break;
         case 7:
            PhoneCarrierBillingUtils.AddressInputField var12 = PhoneCarrierBillingUtils.AddressInputField.ADDR_CITY;
            var2.add(var12);
            break;
         case 8:
            PhoneCarrierBillingUtils.AddressInputField var10 = PhoneCarrierBillingUtils.AddressInputField.ADDR_STATE;
            var2.add(var10);
            break;
         case 9:
            PhoneCarrierBillingUtils.AddressInputField var19 = PhoneCarrierBillingUtils.AddressInputField.ADDR_POSTAL_CODE;
            var2.add(var19);
            break;
         case 10:
            PhoneCarrierBillingUtils.AddressInputField var21 = PhoneCarrierBillingUtils.AddressInputField.ADDR_COUNTRY_CODE;
            var2.add(var21);
            break;
         case 11:
         default:
            Object[] var5 = new Object[]{var4};
            FinskyLog.w("InputValidationError that can\'t be displayed: type=%d", var5);
            break;
         case 12:
            PhoneCarrierBillingUtils.AddressInputField var8 = PhoneCarrierBillingUtils.AddressInputField.ADDR_PHONE;
            var2.add(var8);
            break;
         case 13:
            Object[] var14 = new Object[0];
            FinskyLog.d("Input error ADDR_WHOLE_ADDRESS. Displaying at ADDRESS_LINE_1.", var14);
         case 5:
            PhoneCarrierBillingUtils.AddressInputField var15 = PhoneCarrierBillingUtils.AddressInputField.ADDR_ADDRESS1;
            var2.add(var15);
         }
      }

      return var2;
   }

   private String getPhoneNumber() {
      String var1;
      if(this.mPhoneNumberView.getVisibility() == 0) {
         var1 = this.mPhoneNumberView.getText().toString();
      } else {
         var1 = this.mPhoneNumberEditView.getText().toString();
      }

      return var1;
   }

   private SubscriberInfo getReturnAddress() {
      AddressData var1 = this.mAddressWidget.getAddressData();
      SubscriberInfo.Builder var2 = new SubscriberInfo.Builder();
      String var3 = this.mNameView.getText().toString();
      SubscriberInfo.Builder var4 = var2.setName(var3);
      String var5 = var1.getAddressLine1();
      SubscriberInfo.Builder var6 = var4.setAddress1(var5);
      String var7 = var1.getAddressLine2();
      SubscriberInfo.Builder var8 = var6.setAddress2(var7);
      String var9 = var1.getLocality();
      SubscriberInfo.Builder var10 = var8.setCity(var9);
      String var11 = var1.getAdministrativeArea();
      SubscriberInfo.Builder var12 = var10.setState(var11);
      String var13 = var1.getPostalCode();
      SubscriberInfo.Builder var14 = var12.setPostalCode(var13);
      String var15 = var1.getPostalCountry();
      SubscriberInfo.Builder var16 = var14.setCountry(var15);
      String var17 = this.getPhoneNumber();
      return var16.setIdentifier(var17).build();
   }

   private void initViews(ViewGroup var1) {
      this.mEditSection = var1;
      EditText var2 = (EditText)var1.findViewById(2131755058);
      this.mNameView = var2;
      TextView var3 = (TextView)var1.findViewById(2131755049);
      this.mPhoneNumberView = var3;
      EditText var4 = (EditText)var1.findViewById(2131755060);
      this.mPhoneNumberEditView = var4;
   }

   public static EditCarrierBillingFragment newInstance(SubscriberInfo var0, ArrayList<Integer> var1) {
      EditCarrierBillingFragment var2 = new EditCarrierBillingFragment();
      Bundle var3 = new Bundle();
      var3.putParcelable("prefill_address", var0);
      var3.putIntegerArrayList("highlight_address", var1);
      var2.setArguments(var3);
      return var2;
   }

   private void setTextViewError(TextView var1, int var2) {
      String var3 = this.getString(var2);
      var1.setError(var3);
   }

   private void setupView(View var1) {
      String var2 = PhoneNumberUtils.formatNumber(BillingLocator.getLine1NumberFromTelephony());
      this.showPhoneNumberView(var2);
      AddressData var3 = (new AddressData.Builder()).setAddressLine1("").setAddressLine2("").setLocality("").setAdminArea("").setPostalCode("").setCountry("").build();
      ViewGroup var4 = (ViewGroup)var1.findViewById(2131755048);
      FormOptions.Builder var5 = new FormOptions.Builder();
      AddressField var6 = AddressField.COUNTRY;
      FormOptions.Builder var7 = var5.hide(var6);
      AddressField var8 = AddressField.RECIPIENT;
      FormOptions.Builder var9 = var7.hide(var8);
      AddressField var10 = AddressField.ORGANIZATION;
      FormOptions.Builder var11 = var9.hide(var10);
      AddressField var12 = AddressField.DEPENDENT_LOCALITY;
      FormOptions.Builder var13 = var11.hide(var12);
      AddressField var14 = AddressField.SORTING_CODE;
      FormOptions var15 = var13.hide(var14).build();
      FragmentActivity var16 = this.getActivity();
      Cache var17 = FinskyApp.get().getCache();
      AddressMetadataCacheManager var18 = new AddressMetadataCacheManager(var17);
      AddressWidget var19 = new AddressWidget(var16, var4, var15, var18, var3);
      this.mAddressWidget = var19;
   }

   private void setupView(View var1, SubscriberInfo var2) {
      TextView var3 = this.mNameView;
      String var4 = var2.getName();
      var3.setText(var4);
      String var5 = var2.getIdentifier();
      if(BillingUtils.isEmptyOrSpaces(var5)) {
         var5 = PhoneNumberUtils.formatNumber(BillingLocator.getLine1NumberFromTelephony());
      }

      this.showPhoneNumberView(var5);
      AddressData var6 = PhoneCarrierBillingUtils.subscriberInfoToAddressData(var2);
      ViewGroup var7 = (ViewGroup)var1.findViewById(2131755048);
      FormOptions.Builder var8 = new FormOptions.Builder();
      AddressField var9 = AddressField.COUNTRY;
      FormOptions.Builder var10 = var8.hide(var9);
      AddressField var11 = AddressField.RECIPIENT;
      FormOptions.Builder var12 = var10.hide(var11);
      AddressField var13 = AddressField.ORGANIZATION;
      FormOptions.Builder var14 = var12.hide(var13);
      AddressField var15 = AddressField.DEPENDENT_LOCALITY;
      FormOptions.Builder var16 = var14.hide(var15);
      AddressField var17 = AddressField.SORTING_CODE;
      FormOptions var18 = var16.hide(var17).build();
      FragmentActivity var19 = this.getActivity();
      Cache var20 = FinskyApp.get().getCache();
      AddressMetadataCacheManager var21 = new AddressMetadataCacheManager(var20);
      AddressWidget var22 = new AddressWidget(var19, var7, var18, var21, var6);
      this.mAddressWidget = var22;
   }

   private void showPhoneNumberView(String var1) {
      if(BillingUtils.isEmptyOrSpaces(var1)) {
         this.mPhoneNumberView.setVisibility(8);
         this.mPhoneNumberEditView.setVisibility(0);
      } else {
         this.mPhoneNumberEditView.setVisibility(8);
         this.mPhoneNumberView.setVisibility(0);
         this.mPhoneNumberView.setText(var1);
      }
   }

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      if(var1 != null) {
         this.mAddressWidget.restoreInstanceState(var1);
      }
   }

   public void onClick(View var1) {
      if(var1.getId() == 2131755053) {
         String var2 = this.mNameView.getText().toString();
         String var3 = this.getPhoneNumber();
         AddressProblems var4 = this.mAddressWidget.getAddressProblems();
         Collection var5 = PhoneCarrierBillingUtils.getErrors(var2, var3, var4);
         if(var5.isEmpty()) {
            EditCarrierBillingFragment.EditCarrierBillingResultListener var6 = this.mListener;
            EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var7 = EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult.SUCCESS;
            SubscriberInfo var8 = this.getReturnAddress();
            var6.onEditCarrierBillingResult(var7, var8);
         } else {
            this.displayErrors(var5);
         }
      } else if(var1.getId() == 2131755054) {
         EditCarrierBillingFragment.EditCarrierBillingResultListener var9 = this.mListener;
         EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var10 = EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult.CANCELED;
         var9.onEditCarrierBillingResult(var10, (SubscriberInfo)null);
      }
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var4 = var1.inflate(2130968589, var2, (boolean)0);
      ViewGroup var5 = (ViewGroup)var4.findViewById(2131755057);
      var5.setFocusableInTouchMode((boolean)1);
      boolean var6 = var5.requestFocus();
      this.initViews(var5);
      ((Button)var4.findViewById(2131755053)).setOnClickListener(this);
      ((Button)var4.findViewById(2131755054)).setOnClickListener(this);
      SubscriberInfo var7 = (SubscriberInfo)this.getArguments().getParcelable("prefill_address");
      if(var7 != null) {
         this.setupView(var5, var7);
      } else {
         this.setupView(var5);
      }

      ArrayList var8 = this.getArguments().getIntegerArrayList("highlight_address");
      if(var8 != null) {
         Collection var9 = this.getFormErrors(var8);
         this.displayErrors(var9);
      }

      return var4;
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      this.mAddressWidget.saveInstanceState(var1);
   }

   public void setOnResultListener(EditCarrierBillingFragment.EditCarrierBillingResultListener var1) {
      this.mListener = var1;
   }

   public interface EditCarrierBillingResultListener {

      void onEditCarrierBillingResult(EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var1, SubscriberInfo var2);

      public static enum EditResult {

         // $FF: synthetic field
         private static final EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult[] $VALUES;
         CANCELED("CANCELED", 1),
         SUCCESS("SUCCESS", 0);


         static {
            EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult[] var0 = new EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult[2];
            EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var1 = SUCCESS;
            var0[0] = var1;
            EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var2 = CANCELED;
            var0[1] = var2;
            $VALUES = var0;
         }

         private EditResult(String var1, int var2) {}
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField = new int[PhoneCarrierBillingUtils.AddressInputField.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField;
            int var1 = PhoneCarrierBillingUtils.AddressInputField.ADDR_ADDRESS1.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var31) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField;
            int var3 = PhoneCarrierBillingUtils.AddressInputField.ADDR_COUNTRY_CODE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var30) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField;
            int var5 = PhoneCarrierBillingUtils.AddressInputField.ADDR_ADDRESS2.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var29) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField;
            int var7 = PhoneCarrierBillingUtils.AddressInputField.ADDR_CITY.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var28) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField;
            int var9 = PhoneCarrierBillingUtils.AddressInputField.PERSON_NAME.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var27) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField;
            int var11 = PhoneCarrierBillingUtils.AddressInputField.ADDR_POSTAL_CODE.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var26) {
            ;
         }

         try {
            int[] var12 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField;
            int var13 = PhoneCarrierBillingUtils.AddressInputField.ADDR_STATE.ordinal();
            var12[var13] = 7;
         } catch (NoSuchFieldError var25) {
            ;
         }

         try {
            int[] var14 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$PhoneCarrierBillingUtils$AddressInputField;
            int var15 = PhoneCarrierBillingUtils.AddressInputField.ADDR_PHONE.ordinal();
            var14[var15] = 8;
         } catch (NoSuchFieldError var24) {
            ;
         }
      }
   }
}
