package com.android.i18n.addressinput;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;
import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressDataKey;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressProblems;
import com.android.i18n.addressinput.AddressUIComponent;
import com.android.i18n.addressinput.AddressVerificationNodeData;
import com.android.i18n.addressinput.CacheData;
import com.android.i18n.addressinput.ClientCacheManager;
import com.android.i18n.addressinput.ClientData;
import com.android.i18n.addressinput.DataLoadListener;
import com.android.i18n.addressinput.FieldVerifier;
import com.android.i18n.addressinput.FormController;
import com.android.i18n.addressinput.FormOptions;
import com.android.i18n.addressinput.FormatInterpreter;
import com.android.i18n.addressinput.LookupKey;
import com.android.i18n.addressinput.RegionData;
import com.android.i18n.addressinput.RegionDataConstants;
import com.android.i18n.addressinput.StandardAddressVerifier;
import com.android.i18n.addressinput.Util;
import com.google.android.finsky.utils.FinskyLog;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddressWidget implements OnItemSelectedListener {

   private static final String ADDRESS_DATA_KEY = "address_data";
   private static final String ADDRESS_ERROR_FIELDS_KEY = "address_error_fields";
   private static final String ADDRESS_ERROR_VALUES_KEY = "address_error_values";
   private static final Map<String, Integer> ADMIN_ERROR_MESSAGES;
   private static final Map<String, Integer> ADMIN_LABELS;
   private static final FormOptions SHOW_ALL_FIELDS = (new FormOptions.Builder()).build();
   private String mAdminLabel;
   private CacheData mCacheData;
   private Context mContext;
   private String mCurrentRegion;
   private FormController mFormController;
   private FormOptions mFormOptions;
   private FormatInterpreter mFormatInterpreter;
   final Handler mHandler;
   private LayoutInflater mInflater;
   private final EnumMap<AddressField, AddressUIComponent> mInputWidgets;
   private ProgressDialog mProgressDialog;
   private ViewGroup mRootView;
   private boolean mSaveInstanceStateCalled;
   private AddressData mSavedAddress;
   private Map<AddressField, String> mSavedErrors;
   private LookupKey.ScriptType mScript;
   private ArrayList<AddressWidget.AddressSpinnerInfo> mSpinners;
   final Runnable mUpdateMultipleFields;
   private StandardAddressVerifier mVerifier;
   private String mWidgetLocale;
   private AddressWidget.ZipLabel mZipLabel;


   static {
      HashMap var0 = new HashMap(15);
      Integer var1 = Integer.valueOf(2131230729);
      var0.put("area", var1);
      Integer var3 = Integer.valueOf(2131230730);
      var0.put("county", var3);
      Integer var5 = Integer.valueOf(2131230731);
      var0.put("department", var5);
      Integer var7 = Integer.valueOf(2131230723);
      var0.put("district", var7);
      Integer var9 = Integer.valueOf(2131230732);
      var0.put("do_si", var9);
      Integer var11 = Integer.valueOf(2131230733);
      var0.put("emirate", var11);
      Integer var13 = Integer.valueOf(2131230734);
      var0.put("island", var13);
      Integer var15 = Integer.valueOf(2131230735);
      var0.put("parish", var15);
      Integer var17 = Integer.valueOf(2131230736);
      var0.put("prefecture", var17);
      Integer var19 = Integer.valueOf(2131230737);
      var0.put("province", var19);
      Integer var21 = Integer.valueOf(2131230738);
      var0.put("state", var21);
      ADMIN_LABELS = Collections.unmodifiableMap(var0);
      HashMap var23 = new HashMap(15);
      Integer var24 = Integer.valueOf(2131230745);
      var23.put("area", var24);
      Integer var26 = Integer.valueOf(2131230746);
      var23.put("county", var26);
      Integer var28 = Integer.valueOf(2131230747);
      var23.put("department", var28);
      Integer var30 = Integer.valueOf(2131230742);
      var23.put("district", var30);
      Integer var32 = Integer.valueOf(2131230748);
      var23.put("do_si", var32);
      Integer var34 = Integer.valueOf(2131230749);
      var23.put("emirate", var34);
      Integer var36 = Integer.valueOf(2131230750);
      var23.put("island", var36);
      Integer var38 = Integer.valueOf(2131230751);
      var23.put("parish", var38);
      Integer var40 = Integer.valueOf(2131230752);
      var23.put("prefecture", var40);
      Integer var42 = Integer.valueOf(2131230753);
      var23.put("province", var42);
      Integer var44 = Integer.valueOf(2131230754);
      var23.put("state", var44);
      ADMIN_ERROR_MESSAGES = Collections.unmodifiableMap(var23);
   }

   public AddressWidget(Context var1, ViewGroup var2, FormOptions var3, ClientCacheManager var4) {
      EnumMap var5 = new EnumMap(AddressField.class);
      this.mInputWidgets = var5;
      Handler var6 = new Handler();
      this.mHandler = var6;
      AddressWidget.1 var7 = new AddressWidget.1();
      this.mUpdateMultipleFields = var7;
      ArrayList var8 = new ArrayList();
      this.mSpinners = var8;
      String var9 = getDefaultRegionCode(var1);
      this.mCurrentRegion = var9;
      this.init(var1, var2, var3, var4);
      this.renderForm();
   }

   public AddressWidget(Context var1, ViewGroup var2, FormOptions var3, ClientCacheManager var4, AddressData var5) {
      EnumMap var6 = new EnumMap(AddressField.class);
      this.mInputWidgets = var6;
      Handler var7 = new Handler();
      this.mHandler = var7;
      AddressWidget.1 var8 = new AddressWidget.1();
      this.mUpdateMultipleFields = var8;
      ArrayList var9 = new ArrayList();
      this.mSpinners = var9;
      String var10 = var5.getPostalCountry();
      this.mCurrentRegion = var10;
      if(!isValidRegionCode(this.mCurrentRegion)) {
         String var11 = getDefaultRegionCode(var1);
         this.mCurrentRegion = var11;
      }

      this.init(var1, var2, var3, var4);
      this.renderFormWithSavedAddress(var5);
   }

   private void buildCountryListBox() {
      AddressField var1 = AddressField.COUNTRY;
      AddressUIComponent var2 = new AddressUIComponent(var1);
      String var3 = this.mContext.getString(2131230721);
      var2.setFieldName(var3);
      ArrayList var4 = new ArrayList();
      FormController var5 = this.mFormController;
      LookupKey.KeyType var6 = LookupKey.KeyType.DATA;
      LookupKey var7 = (new LookupKey.Builder(var6)).build();
      Iterator var8 = var5.getRegionData(var7).iterator();

      while(var8.hasNext()) {
         String var9 = ((RegionData)var8.next()).getKey();
         if(!var9.equals("ZZ")) {
            String var10 = this.getLocalCountryName(var9);
            RegionData var11 = (new RegionData.Builder()).setKey(var9).setName(var10).build();
            var4.add(var11);
         }
      }

      var2.initializeCandidatesList(var4);
      EnumMap var13 = this.mInputWidgets;
      AddressField var14 = AddressField.COUNTRY;
      var13.put(var14, var2);
   }

   private void buildFieldWidgets() {
      AddressData.Builder var1 = new AddressData.Builder();
      String var2 = this.mCurrentRegion;
      AddressData var3 = var1.setCountry(var2).build();
      LookupKey.KeyType var4 = LookupKey.KeyType.DATA;
      LookupKey var5 = (new LookupKey.Builder(var4)).setAddressData(var3).build();
      CacheData var6 = this.mCacheData;
      ClientData var7 = new ClientData(var6);
      String var8 = var5.toString();
      AddressVerificationNodeData var9 = var7.getDefaultData(var8);
      AddressField var10 = AddressField.ADMIN_AREA;
      AddressUIComponent var11 = new AddressUIComponent(var10);
      String var12 = this.getAdminAreaFieldName(var9);
      var11.setFieldName(var12);
      EnumMap var13 = this.mInputWidgets;
      AddressField var14 = AddressField.ADMIN_AREA;
      var13.put(var14, var11);
      AddressField var16 = AddressField.LOCALITY;
      AddressUIComponent var17 = new AddressUIComponent(var16);
      String var18 = this.mContext.getString(2131230722);
      var17.setFieldName(var18);
      EnumMap var19 = this.mInputWidgets;
      AddressField var20 = AddressField.LOCALITY;
      var19.put(var20, var17);
      AddressField var22 = AddressField.DEPENDENT_LOCALITY;
      AddressUIComponent var23 = new AddressUIComponent(var22);
      String var24 = this.mContext.getString(2131230723);
      var23.setFieldName(var24);
      EnumMap var25 = this.mInputWidgets;
      AddressField var26 = AddressField.DEPENDENT_LOCALITY;
      var25.put(var26, var23);
      AddressField var28 = AddressField.ADDRESS_LINE_1;
      AddressUIComponent var29 = new AddressUIComponent(var28);
      String var30 = this.mContext.getString(2131230726);
      var29.setFieldName(var30);
      EnumMap var31 = this.mInputWidgets;
      AddressField var32 = AddressField.ADDRESS_LINE_1;
      var31.put(var32, var29);
      EnumMap var34 = this.mInputWidgets;
      AddressField var35 = AddressField.STREET_ADDRESS;
      var34.put(var35, var29);
      AddressField var37 = AddressField.ADDRESS_LINE_2;
      AddressUIComponent var38 = new AddressUIComponent(var37);
      var38.setFieldName("");
      EnumMap var39 = this.mInputWidgets;
      AddressField var40 = AddressField.ADDRESS_LINE_2;
      var39.put(var40, var38);
      AddressField var42 = AddressField.ORGANIZATION;
      AddressUIComponent var43 = new AddressUIComponent(var42);
      String var44 = this.mContext.getString(2131230724);
      var43.setFieldName(var44);
      EnumMap var45 = this.mInputWidgets;
      AddressField var46 = AddressField.ORGANIZATION;
      var45.put(var46, var43);
      AddressField var48 = AddressField.RECIPIENT;
      AddressUIComponent var49 = new AddressUIComponent(var48);
      String var50 = this.mContext.getString(2131230725);
      var49.setFieldName(var50);
      EnumMap var51 = this.mInputWidgets;
      AddressField var52 = AddressField.RECIPIENT;
      var51.put(var52, var49);
      AddressField var54 = AddressField.POSTAL_CODE;
      AddressUIComponent var55 = new AddressUIComponent(var54);
      String var56 = this.getZipFieldName(var9);
      var55.setFieldName(var56);
      EnumMap var57 = this.mInputWidgets;
      AddressField var58 = AddressField.POSTAL_CODE;
      var57.put(var58, var55);
      AddressField var60 = AddressField.SORTING_CODE;
      AddressUIComponent var61 = new AddressUIComponent(var60);
      var61.setFieldName("CEDEX");
      EnumMap var62 = this.mInputWidgets;
      AddressField var63 = AddressField.SORTING_CODE;
      var62.put(var63, var61);
   }

   private void createView(ViewGroup var1, AddressUIComponent var2, String var3, boolean var4) {
      byte var5 = 0;
      LayoutParams var6 = new LayoutParams(-1, -1);
      String var7 = var2.getFieldName();
      if(var7.length() > 0) {
         TextView var8 = (TextView)this.mInflater.inflate(2130968580, var1, (boolean)0);
         var1.addView(var8, var6);
         var8.setText(var7);
      }

      AddressUIComponent.UIComponent var9 = var2.getUIType();
      AddressUIComponent.UIComponent var10 = AddressUIComponent.UIComponent.EDIT;
      if(var9.equals(var10)) {
         View var11 = this.mInflater.inflate(2130968577, var1, (boolean)0);
         var2.setView(var11);
         EditText var12 = (EditText)var11;
         if(!var4) {
            var5 = 1;
         }

         var12.setEnabled((boolean)var5);
         var1.addView(var12, var6);
      } else {
         AddressUIComponent.UIComponent var13 = var2.getUIType();
         AddressUIComponent.UIComponent var14 = AddressUIComponent.UIComponent.SPINNER;
         if(var13.equals(var14)) {
            View var15 = this.mInflater.inflate(2130968579, var1, (boolean)0);
            var2.setView(var15);
            Spinner var16 = (Spinner)var15;
            var1.addView(var16, var6);
            AddressField var17 = var2.getId();
            AddressField var18 = var2.getParentId();
            AddressWidget.AddressSpinnerInfo var19 = new AddressWidget.AddressSpinnerInfo(var16, var17, var18);
            Context var20 = this.mContext;
            var19.initAdapter(var20, 17367048, 17367049);
            ArrayAdapter var21 = var19.mAdapter;
            var16.setAdapter(var21);
            List var22 = var2.getCandidatesList();
            var19.setSpinnerList(var22, var3);
            if(var7.length() > 0) {
               var16.setPrompt(var7);
            }

            var16.setOnItemSelectedListener(this);
            this.mSpinners.add(var19);
         }
      }
   }

   private AddressWidget.AddressSpinnerInfo findSpinnerByView(View var1) {
      Iterator var2 = this.mSpinners.iterator();

      AddressWidget.AddressSpinnerInfo var3;
      do {
         if(!var2.hasNext()) {
            var3 = null;
            break;
         }

         var3 = (AddressWidget.AddressSpinnerInfo)var2.next();
      } while(var3.mView != var1);

      return var3;
   }

   public static AddressProblems getAddressProblems(AddressData var0, ClientCacheManager var1) {
      AddressProblems var2 = new AddressProblems();
      CacheData var3 = new CacheData(var1);
      ClientData var4 = new ClientData(var3);
      FieldVerifier var5 = new FieldVerifier(var4);
      (new StandardAddressVerifier(var5)).verify(var0, var2);
      return var2;
   }

   private String getAdminAreaFieldName(AddressVerificationNodeData var1) {
      AddressDataKey var2 = AddressDataKey.STATE_NAME_TYPE;
      String var3 = var1.get(var2);
      this.mAdminLabel = var3;
      Integer var4 = (Integer)ADMIN_LABELS.get(var3);
      if(var4 == null) {
         var4 = Integer.valueOf(2131230737);
      }

      Context var5 = this.mContext;
      int var6 = var4.intValue();
      return var5.getString(var6);
   }

   private static String getDefaultRegionCode(Context var0) {
      String var1 = "US";
      if(var0 != null) {
         String var2 = ((TelephonyManager)var0.getSystemService("phone")).getSimCountryIso().toUpperCase();
         if(var2 != null && var2.length() == 2) {
            var1 = var2;
         }
      }

      return var1;
   }

   private int getErrorMessageIdForInvalidEntryIn(AddressField var1) {
      int[] var2 = AddressWidget.4.$SwitchMap$com$android$i18n$addressinput$AddressField;
      int var3 = var1.ordinal();
      int var4;
      switch(var2[var3]) {
      case 1:
         Map var5 = ADMIN_ERROR_MESSAGES;
         String var6 = this.mAdminLabel;
         var4 = ((Integer)var5.get(var6)).intValue();
         break;
      case 2:
         var4 = 2131230741;
         break;
      case 3:
         var4 = 2131230742;
         break;
      case 4:
         AddressWidget.ZipLabel var7 = this.mZipLabel;
         AddressWidget.ZipLabel var8 = AddressWidget.ZipLabel.POSTAL;
         if(var7 == var8) {
            var4 = 2131230743;
         } else {
            var4 = 2131230744;
         }
         break;
      default:
         var4 = 2131230740;
      }

      return var4;
   }

   public static List<String> getFullEnvelopeAddress(AddressData var0, Context var1) {
      FormOptions var2 = SHOW_ALL_FIELDS;
      FormatInterpreter var3 = new FormatInterpreter(var2);
      String var4 = getDefaultRegionCode(var1);
      return var3.getEnvelopeAddress(var0, var4);
   }

   private String getLocalCountryName(String var1) {
      Locale var2 = new Locale("", var1);
      Locale var3 = Locale.getDefault();
      return var2.getDisplayCountry(var3);
   }

   private List<RegionData> getRegionData(AddressField var1) {
      AddressData var2 = this.getAddressData();
      FormController var3 = this.mFormController;
      String var4 = var2.getLanguageCode();
      if(var3.isDefaultLanguage(var4)) {
         var2 = (new AddressData.Builder(var2)).setLanguageCode((String)null).build();
      }

      LookupKey var5 = this.mFormController.getDataKeyFor(var2).getKeyForUpperLevelField(var1);
      Object var9;
      if(var5 == null) {
         String var6 = this.toString();
         String var7 = "Can\'t build key with parent field " + var1 + ". One of" + " the ancestor fields might be empty";
         Log.w(var6, var7);
         var9 = new ArrayList(1);
      } else {
         var9 = this.mFormController.getRegionData(var5);
      }

      return (List)var9;
   }

   private Map<AddressField, String> getViewErrors() {
      HashMap var1 = new HashMap();
      FormatInterpreter var2 = this.mFormatInterpreter;
      LookupKey.ScriptType var3 = this.mScript;
      String var4 = this.mCurrentRegion;
      Iterator var5 = var2.getAddressFieldOrder(var3, var4).iterator();

      while(var5.hasNext()) {
         AddressField var6 = (AddressField)var5.next();
         AddressUIComponent var7 = (AddressUIComponent)this.mInputWidgets.get(var6);
         if(var7 != null) {
            View var8 = var7.getView();
            if(var8 != null) {
               AddressUIComponent.UIComponent var9 = var7.getUIType();
               AddressUIComponent.UIComponent var10 = AddressUIComponent.UIComponent.EDIT;
               if(var9 == var10) {
                  CharSequence var11 = ((EditText)var8).getError();
                  if(var11 != null) {
                     String var12 = var11.toString();
                     var1.put(var6, var12);
                  }
               }
            }
         }
      }

      return var1;
   }

   private String getZipFieldName(AddressVerificationNodeData var1) {
      AddressDataKey var2 = AddressDataKey.ZIP_NAME_TYPE;
      String var4;
      if(var1.get(var2) == null) {
         AddressWidget.ZipLabel var3 = AddressWidget.ZipLabel.POSTAL;
         this.mZipLabel = var3;
         var4 = this.mContext.getString(2131230727);
      } else {
         AddressWidget.ZipLabel var5 = AddressWidget.ZipLabel.ZIP;
         this.mZipLabel = var5;
         var4 = this.mContext.getString(2131230728);
      }

      return var4;
   }

   private void init(Context var1, ViewGroup var2, FormOptions var3, ClientCacheManager var4) {
      this.mContext = var1;
      this.mRootView = var2;
      this.mFormOptions = var3;
      CacheData var5 = new CacheData(var4);
      this.mCacheData = var5;
      LayoutInflater var6 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.mInflater = var6;
      CacheData var7 = this.mCacheData;
      ClientData var8 = new ClientData(var7);
      String var9 = this.mWidgetLocale;
      String var10 = this.mCurrentRegion;
      FormController var11 = new FormController(var8, var9, var10);
      this.mFormController = var11;
      FormOptions var12 = this.mFormOptions;
      FormatInterpreter var13 = new FormatInterpreter(var12);
      this.mFormatInterpreter = var13;
      CacheData var14 = this.mCacheData;
      ClientData var15 = new ClientData(var14);
      FieldVerifier var16 = new FieldVerifier(var15);
      StandardAddressVerifier var17 = new StandardAddressVerifier(var16);
      this.mVerifier = var17;
      AddressField var18 = AddressField.COUNTRY;
      if(!var3.isHidden(var18)) {
         this.buildCountryListBox();
         ViewGroup var19 = this.mRootView;
         EnumMap var20 = this.mInputWidgets;
         AddressField var21 = AddressField.COUNTRY;
         AddressUIComponent var22 = (AddressUIComponent)var20.get(var21);
         String var23 = this.mCurrentRegion;
         String var24 = this.getLocalCountryName(var23);
         AddressField var25 = AddressField.COUNTRY;
         boolean var26 = var3.isReadonly(var25);
         this.createView(var19, var22, var24, var26);
      }
   }

   private void initializeDropDowns() {
      EnumMap var1 = this.mInputWidgets;
      AddressField var2 = AddressField.ADMIN_AREA;
      AddressUIComponent var3 = (AddressUIComponent)var1.get(var2);
      AddressField var4 = AddressField.COUNTRY;
      List var5 = this.getRegionData(var4);
      var3.initializeCandidatesList(var5);
      EnumMap var6 = this.mInputWidgets;
      AddressField var7 = AddressField.LOCALITY;
      AddressUIComponent var8 = (AddressUIComponent)var6.get(var7);
      AddressField var9 = AddressField.ADMIN_AREA;
      List var10 = this.getRegionData(var9);
      var8.initializeCandidatesList(var10);
   }

   private void initializeFieldsWithAddress(AddressData var1) {
      FormatInterpreter var2 = this.mFormatInterpreter;
      LookupKey.ScriptType var3 = this.mScript;
      String var4 = this.mCurrentRegion;
      Iterator var5 = var2.getAddressFieldOrder(var3, var4).iterator();

      while(var5.hasNext()) {
         AddressField var6 = (AddressField)var5.next();
         AddressUIComponent var7 = (AddressUIComponent)this.mInputWidgets.get(var6);
         if(var7 != null) {
            String var8 = var1.getFieldValue(var6);
            if(var8 == null) {
               var8 = "";
            }

            View var9 = var7.getView();
            if(var9 != null) {
               AddressUIComponent.UIComponent var10 = var7.getUIType();
               AddressUIComponent.UIComponent var11 = AddressUIComponent.UIComponent.SPINNER;
               if(var10 == var11) {
                  AddressWidget.AddressSpinnerInfo var12 = this.findSpinnerByView(var9);
                  if(var12 != null) {
                     var12.setSelection(var8);
                  }
               } else {
                  ((EditText)var9).setText(var8);
               }
            }
         }
      }

   }

   public static boolean isValidRegionCode(String var0) {
      return RegionDataConstants.getCountryFormatMap().containsKey(var0);
   }

   private void layoutAddressFields() {
      FormatInterpreter var1 = this.mFormatInterpreter;
      LookupKey.ScriptType var2 = this.mScript;
      String var3 = this.mCurrentRegion;
      Iterator var4 = var1.getAddressFieldOrder(var2, var3).iterator();

      while(var4.hasNext()) {
         AddressField var5 = (AddressField)var4.next();
         if(!this.mFormOptions.isHidden(var5)) {
            ViewGroup var6 = this.mRootView;
            AddressUIComponent var7 = (AddressUIComponent)this.mInputWidgets.get(var5);
            boolean var8 = this.mFormOptions.isReadonly(var5);
            this.createView(var6, var7, "", var8);
         }
      }

   }

   private void removePreviousViews() {
      if(this.mRootView != null) {
         int var1 = this.mRootView.getChildCount();
         FormOptions var2 = this.mFormOptions;
         AddressField var3 = AddressField.COUNTRY;
         if(var2.isHidden(var3)) {
            if(var1 > 0) {
               this.mRootView.removeAllViews();
            }
         } else if(var1 > 2) {
            ViewGroup var4 = this.mRootView;
            int var5 = this.mRootView.getChildCount() + -2;
            var4.removeViews(2, var5);
         }
      }
   }

   private void setViewErrors(Map<AddressField, String> var1) {
      Iterator var2 = var1.keySet().iterator();

      while(var2.hasNext()) {
         AddressField var3 = (AddressField)var2.next();
         AddressUIComponent var4 = (AddressUIComponent)this.mInputWidgets.get(var3);
         if(var4 != null) {
            View var5 = var4.getView();
            if(var5 != null) {
               AddressUIComponent.UIComponent var6 = var4.getUIType();
               AddressUIComponent.UIComponent var7 = AddressUIComponent.UIComponent.EDIT;
               if(var6 == var7) {
                  String var8 = (String)var1.get(var3);
                  if(var8 != null) {
                     ((EditText)var5).setError(var8);
                  }
               }
            }
         }
      }

   }

   private void setWidgetLocaleAndScript() {
      Locale var1 = Locale.getDefault();
      String var2 = this.mCurrentRegion;
      String var3 = Util.getWidgetCompatibleLanguageCode(var1, var2);
      this.mWidgetLocale = var3;
      FormController var4 = this.mFormController;
      String var5 = this.mWidgetLocale;
      var4.setLanguageCode(var5);
      LookupKey.ScriptType var6;
      if(Util.isExplicitLatinScript(this.mWidgetLocale)) {
         var6 = LookupKey.ScriptType.LATIN;
      } else {
         var6 = LookupKey.ScriptType.LOCAL;
      }

      this.mScript = var6;
   }

   private void updateChildNodes(AdapterView<?> var1, int var2) {
      AddressWidget.AddressSpinnerInfo var3 = this.findSpinnerByView(var1);
      if(var3 != null) {
         AddressField var4 = var3.mId;
         AddressField var5 = AddressField.COUNTRY;
         if(var4 != var5) {
            AddressField var6 = AddressField.ADMIN_AREA;
            if(var4 != var6) {
               AddressField var7 = AddressField.LOCALITY;
               if(var4 != var7) {
                  return;
               }
            }
         }

         String var8 = var3.getRegionCode(var2);
         AddressField var9 = AddressField.COUNTRY;
         if(var4 == var9) {
            this.updateWidgetOnCountryChange(var8);
         } else {
            FormController var10 = this.mFormController;
            AddressData var11 = this.getAddressData();
            AddressWidget.2 var12 = new AddressWidget.2(var4);
            var10.requestDataForAddress(var11, var12);
         }
      }
   }

   private void updateFields() {
      this.removePreviousViews();
      this.buildFieldWidgets();
      this.initializeDropDowns();
      this.layoutAddressFields();
      if(this.mSavedAddress != null) {
         AddressData var1 = this.mSavedAddress;
         this.initializeFieldsWithAddress(var1);
      }

      if(this.mSavedErrors != null) {
         Map var2 = this.mSavedErrors;
         this.setViewErrors(var2);
      }
   }

   private void updateInputWidget(AddressField var1) {
      Iterator var2 = this.mSpinners.iterator();

      while(var2.hasNext()) {
         AddressWidget.AddressSpinnerInfo var3 = (AddressWidget.AddressSpinnerInfo)var2.next();
         if(var3.mParentId == var1) {
            AddressField var4 = var3.mParentId;
            List var5 = this.getRegionData(var4);
            var3.setSpinnerList(var5, "");
         }
      }

   }

   public void clearErrorMessage() {
      FormatInterpreter var1 = this.mFormatInterpreter;
      LookupKey.ScriptType var2 = this.mScript;
      String var3 = this.mCurrentRegion;
      Iterator var4 = var1.getAddressFieldOrder(var2, var3).iterator();

      while(var4.hasNext()) {
         AddressField var5 = (AddressField)var4.next();
         AddressUIComponent var6 = (AddressUIComponent)this.mInputWidgets.get(var5);
         if(var6 != null) {
            AddressUIComponent.UIComponent var7 = var6.getUIType();
            AddressUIComponent.UIComponent var8 = AddressUIComponent.UIComponent.EDIT;
            if(var7 == var8) {
               EditText var9 = (EditText)var6.getView();
               if(var9 != null) {
                  var9.setError((CharSequence)null);
               }
            }
         }
      }

   }

   public View displayErrorMessageForInvalidEntryIn(AddressField var1) {
      AddressUIComponent var2 = (AddressUIComponent)this.mInputWidgets.get(var1);
      EditText var6;
      if(var2 != null) {
         AddressUIComponent.UIComponent var3 = var2.getUIType();
         AddressUIComponent.UIComponent var4 = AddressUIComponent.UIComponent.EDIT;
         if(var3 == var4) {
            int var5 = this.getErrorMessageIdForInvalidEntryIn(var1);
            var6 = (EditText)var2.getView();
            String var7 = this.mContext.getString(var5);
            var6.setError(var7);
            return var6;
         }
      }

      var6 = null;
      return var6;
   }

   public AddressData getAddressData() {
      AddressData.Builder var1 = new AddressData.Builder();
      String var2 = this.mCurrentRegion;
      var1.setCountry(var2);
      FormatInterpreter var4 = this.mFormatInterpreter;
      LookupKey.ScriptType var5 = this.mScript;
      String var6 = this.mCurrentRegion;
      Iterator var7 = var4.getAddressFieldOrder(var5, var6).iterator();

      while(var7.hasNext()) {
         AddressField var8 = (AddressField)var7.next();
         AddressUIComponent var9 = (AddressUIComponent)this.mInputWidgets.get(var8);
         if(var9 != null) {
            String var10 = var9.getValue();
            AddressUIComponent.UIComponent var11 = var9.getUIType();
            AddressUIComponent.UIComponent var12 = AddressUIComponent.UIComponent.SPINNER;
            if(var11 == var12) {
               View var13 = this.getViewForField(var8);
               AddressWidget.AddressSpinnerInfo var14 = this.findSpinnerByView(var13);
               if(var14 != null) {
                  var10 = var14.getRegionDataKeyForValue(var10);
               }
            }

            var1.set(var8, var10);
         }
      }

      String var16 = this.mWidgetLocale;
      var1.setLanguageCode(var16);
      return var1.build();
   }

   public AddressProblems getAddressProblems() {
      AddressProblems var1 = new AddressProblems();
      AddressData var2 = this.getAddressData();
      this.mVerifier.verify(var2, var1);
      return var1;
   }

   public List<String> getEnvelopeAddress() {
      FormatInterpreter var1 = this.mFormatInterpreter;
      AddressData var2 = this.getAddressData();
      String var3 = this.mCurrentRegion;
      return var1.getEnvelopeAddress(var2, var3);
   }

   public List<String> getEnvelopeAddress(AddressData var1) {
      FormatInterpreter var2 = this.mFormatInterpreter;
      String var3 = this.mCurrentRegion;
      return var2.getEnvelopeAddress(var1, var3);
   }

   public View getViewForField(AddressField var1) {
      AddressUIComponent var2 = (AddressUIComponent)this.mInputWidgets.get(var1);
      View var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = var2.getView();
      }

      return var3;
   }

   public void onItemSelected(AdapterView<?> var1, View var2, int var3, long var4) {
      this.updateChildNodes(var1, var3);
   }

   public void onNothingSelected(AdapterView<?> var1) {}

   public void renderForm() {
      this.setWidgetLocaleAndScript();
      AddressData.Builder var1 = new AddressData.Builder();
      String var2 = this.mCurrentRegion;
      AddressData.Builder var3 = var1.setCountry(var2);
      String var4 = this.mWidgetLocale;
      AddressData var5 = var3.setLanguageCode(var4).build();
      FormController var6 = this.mFormController;
      AddressWidget.3 var7 = new AddressWidget.3();
      var6.requestDataForAddress(var5, var7);
   }

   public void renderFormWithSavedAddress(AddressData var1) {
      this.setWidgetLocaleAndScript();
      this.removePreviousViews();
      this.buildFieldWidgets();
      this.layoutAddressFields();
      this.initializeFieldsWithAddress(var1);
   }

   public void restoreInstanceState(Bundle var1) {
      AddressData var2 = (AddressData)var1.getSerializable("address_data");
      this.mSavedAddress = var2;
      AddressData var3 = this.mSavedAddress;
      this.initializeFieldsWithAddress(var3);
      ArrayList var4 = var1.getIntegerArrayList("address_error_fields");
      ArrayList var5 = var1.getStringArrayList("address_error_values");
      if(var4 != null) {
         if(var5 != null) {
            HashMap var6 = new HashMap();
            int var7 = 0;

            while(true) {
               int var8 = var4.size();
               if(var7 >= var8) {
                  this.mSavedErrors = var6;
                  this.setViewErrors(var6);
                  return;
               }

               int var9 = ((Integer)var4.get(var7)).intValue();
               String var10 = (String)var5.get(var7);
               AddressField var11 = AddressField.values()[var9];
               var6.put(var11, var10);
               ++var7;
            }
         }
      }
   }

   public void saveInstanceState(Bundle var1) {
      this.mSaveInstanceStateCalled = (boolean)1;
      AddressData var2 = this.getAddressData();
      var1.putSerializable("address_data", var2);
      Map var3 = this.getViewErrors();
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      Iterator var6 = var3.keySet().iterator();

      while(var6.hasNext()) {
         AddressField var7 = (AddressField)var6.next();
         Integer var8 = Integer.valueOf(var7.ordinal());
         var4.add(var8);
         Object var10 = var3.get(var7);
         var5.add(var10);
      }

      var1.putIntegerArrayList("address_error_fields", var4);
      var1.putStringArrayList("address_error_values", var5);
   }

   public void setUrl(String var1) {
      this.mCacheData.setUrl(var1);
   }

   public void updateWidgetOnCountryChange(String var1) {
      if(!this.mCurrentRegion.equalsIgnoreCase(var1)) {
         this.mCurrentRegion = var1;
         FormController var2 = this.mFormController;
         String var3 = this.mCurrentRegion;
         var2.setCurrentCountry(var3);
         this.renderForm();
      }
   }

   // $FF: synthetic class
   static class 4 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$i18n$addressinput$AddressField = new int[AddressField.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var1 = AddressField.ADMIN_AREA.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var3 = AddressField.LOCALITY.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var5 = AddressField.DEPENDENT_LOCALITY.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var7 = AddressField.POSTAL_CODE.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var12) {
            ;
         }
      }
   }

   class 2 implements DataLoadListener {

      // $FF: synthetic field
      final AddressField val$myId;


      2(AddressField var2) {
         this.val$myId = var2;
      }

      public void dataLoadingBegin() {}

      public void dataLoadingEnd() {
         AddressWidget var1 = AddressWidget.this;
         AddressField var2 = this.val$myId;
         AddressWidget.UpdateRunnable var3 = var1.new UpdateRunnable(var2);
         AddressWidget.this.mHandler.post(var3);
      }
   }

   class 3 implements DataLoadListener {

      3() {}

      public void dataLoadingBegin() {
         AddressWidget var1 = AddressWidget.this;
         Context var2 = AddressWidget.this.mContext;
         String var3 = AddressWidget.this.mContext.getString(2131230720);
         ProgressDialog var4 = ProgressDialog.show(var2, "", var3);
         var1.mProgressDialog = var4;
      }

      public void dataLoadingEnd() {
         int var1 = Log.d(this.toString(), "Data loading completed.");
         if(!AddressWidget.this.mSaveInstanceStateCalled) {
            AddressWidget.this.mProgressDialog.dismiss();
            Handler var2 = AddressWidget.this.mHandler;
            Runnable var3 = AddressWidget.this.mUpdateMultipleFields;
            var2.post(var3);
         } else {
            Object[] var5 = new Object[0];
            FinskyLog.d("Views already disposed. We just leaked a dialog.", var5);
         }
      }
   }

   private class UpdateRunnable implements Runnable {

      private AddressField myId;


      public UpdateRunnable(AddressField var2) {
         this.myId = var2;
      }

      public void run() {
         AddressWidget var1 = AddressWidget.this;
         AddressField var2 = this.myId;
         var1.updateInputWidget(var2);
      }
   }

   private static enum ZipLabel {

      // $FF: synthetic field
      private static final AddressWidget.ZipLabel[] $VALUES;
      POSTAL("POSTAL", 1),
      ZIP("ZIP", 0);


      static {
         AddressWidget.ZipLabel[] var0 = new AddressWidget.ZipLabel[2];
         AddressWidget.ZipLabel var1 = ZIP;
         var0[0] = var1;
         AddressWidget.ZipLabel var2 = POSTAL;
         var0[1] = var2;
         $VALUES = var0;
      }

      private ZipLabel(String var1, int var2) {}
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         AddressWidget.this.updateFields();
      }
   }

   private static class AddressSpinnerInfo {

      private ArrayAdapter<String> mAdapter;
      private List<RegionData> mCurrentRegions;
      private AddressField mId;
      private AddressField mParentId;
      private Spinner mView;


      public AddressSpinnerInfo(Spinner var1, AddressField var2, AddressField var3) {
         this.mView = var1;
         this.mId = var2;
         this.mParentId = var3;
      }

      private RegionData findRegionByKey(String var1) {
         Iterator var2 = this.mCurrentRegions.iterator();

         RegionData var3;
         do {
            if(!var2.hasNext()) {
               var3 = null;
               break;
            }

            var3 = (RegionData)var2.next();
         } while(!var3.getKey().equals(var1));

         return var3;
      }

      public String getRegionCode(int var1) {
         String var2;
         if(this.mAdapter.getCount() <= var1) {
            var2 = "";
         } else {
            String var3 = (String)this.mAdapter.getItem(var1);
            var2 = this.getRegionDataKeyForValue(var3);
         }

         return var2;
      }

      public String getRegionDataKeyForValue(String var1) {
         Iterator var2 = this.mCurrentRegions.iterator();

         String var4;
         while(true) {
            if(var2.hasNext()) {
               RegionData var3 = (RegionData)var2.next();
               if(!var3.getDisplayName().endsWith(var1)) {
                  continue;
               }

               var4 = var3.getKey();
               break;
            }

            var4 = "";
            break;
         }

         return var4;
      }

      public void initAdapter(Context var1, int var2, int var3) {
         ArrayAdapter var4 = new ArrayAdapter(var1, var2);
         this.mAdapter = var4;
         this.mAdapter.setDropDownViewResource(var3);
      }

      public void setSelection(String var1) {
         RegionData var2 = this.findRegionByKey(var1);
         if(var2 != null) {
            ArrayAdapter var3 = this.mAdapter;
            String var4 = var2.getDisplayName();
            int var5 = var3.getPosition(var4);
            if(var5 >= 0) {
               this.mView.setSelection(var5);
            }
         }
      }

      public void setSpinnerList(List<RegionData> var1, String var2) {
         this.mCurrentRegions = var1;
         this.mAdapter.clear();
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            RegionData var4 = (RegionData)var3.next();
            ArrayAdapter var5 = this.mAdapter;
            String var6 = var4.getDisplayName();
            var5.add(var6);
         }

         ArrayAdapter var7 = this.mAdapter;
         Collator var8 = Collator.getInstance(Locale.getDefault());
         var7.sort(var8);
         if(var2.length() == 0) {
            this.mView.setSelection(0);
         } else {
            int var9 = this.mAdapter.getPosition(var2);
            this.mView.setSelection(var9);
         }
      }
   }
}
