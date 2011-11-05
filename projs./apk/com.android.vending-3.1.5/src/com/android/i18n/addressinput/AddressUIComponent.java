package com.android.i18n.addressinput;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.RegionData;
import java.util.ArrayList;
import java.util.List;

class AddressUIComponent {

   private List<RegionData> mCandidatesList;
   private String mFieldName;
   private AddressField mId;
   private AddressField mParentId;
   private AddressUIComponent.UIComponent mUiType;
   private View mView;


   AddressUIComponent(AddressField var1) {
      ArrayList var2 = new ArrayList();
      this.mCandidatesList = var2;
      this.mId = var1;
      this.mParentId = null;
      AddressUIComponent.UIComponent var3 = AddressUIComponent.UIComponent.EDIT;
      this.mUiType = var3;
   }

   List<RegionData> getCandidatesList() {
      return this.mCandidatesList;
   }

   String getFieldName() {
      return this.mFieldName;
   }

   AddressField getId() {
      return this.mId;
   }

   AddressField getParentId() {
      return this.mParentId;
   }

   AddressUIComponent.UIComponent getUIType() {
      return this.mUiType;
   }

   String getValue() {
      String var1;
      if(this.mView == null) {
         if(this.mCandidatesList.size() == 0) {
            var1 = "";
         } else {
            var1 = ((RegionData)this.mCandidatesList.get(0)).getDisplayName();
         }
      } else {
         int[] var2 = AddressUIComponent.1.$SwitchMap$com$android$i18n$addressinput$AddressUIComponent$UIComponent;
         int var3 = this.mUiType.ordinal();
         switch(var2[var3]) {
         case 1:
            Object var4 = ((Spinner)this.mView).getSelectedItem();
            if(var4 == null) {
               var1 = "";
            } else {
               var1 = var4.toString();
            }
            break;
         case 2:
            var1 = ((EditText)this.mView).getText().toString();
            break;
         default:
            var1 = "";
         }
      }

      return var1;
   }

   View getView() {
      return this.mView;
   }

   void initializeCandidatesList(List<RegionData> var1) {
      this.mCandidatesList = var1;
      if(var1.size() > 1) {
         AddressUIComponent.UIComponent var2 = AddressUIComponent.UIComponent.SPINNER;
         this.mUiType = var2;
         int[] var3 = AddressUIComponent.1.$SwitchMap$com$android$i18n$addressinput$AddressField;
         int var4 = this.mId.ordinal();
         switch(var3[var4]) {
         case 1:
            AddressField var5 = AddressField.LOCALITY;
            this.mParentId = var5;
            return;
         case 2:
            AddressField var6 = AddressField.ADMIN_AREA;
            this.mParentId = var6;
            return;
         case 3:
            AddressField var7 = AddressField.COUNTRY;
            this.mParentId = var7;
            return;
         default:
         }
      }
   }

   void setCandidatesList(List<RegionData> var1) {
      this.mCandidatesList = var1;
   }

   void setFieldName(String var1) {
      this.mFieldName = var1;
   }

   void setId(AddressField var1) {
      this.mId = var1;
   }

   void setParentId(AddressField var1) {
      this.mParentId = var1;
   }

   void setUIType(AddressUIComponent.UIComponent var1) {
      this.mUiType = var1;
   }

   void setView(View var1) {
      this.mView = var1;
   }

   static enum UIComponent {

      // $FF: synthetic field
      private static final AddressUIComponent.UIComponent[] $VALUES;
      EDIT("EDIT", 0),
      SPINNER("SPINNER", 1);


      static {
         AddressUIComponent.UIComponent[] var0 = new AddressUIComponent.UIComponent[2];
         AddressUIComponent.UIComponent var1 = EDIT;
         var0[0] = var1;
         AddressUIComponent.UIComponent var2 = SPINNER;
         var0[1] = var2;
         $VALUES = var0;
      }

      private UIComponent(String var1, int var2) {}
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$i18n$addressinput$AddressField;
      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$i18n$addressinput$AddressUIComponent$UIComponent = new int[AddressUIComponent.UIComponent.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$android$i18n$addressinput$AddressUIComponent$UIComponent;
            int var1 = AddressUIComponent.UIComponent.SPINNER.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var19) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$i18n$addressinput$AddressUIComponent$UIComponent;
            int var3 = AddressUIComponent.UIComponent.EDIT.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var18) {
            ;
         }

         $SwitchMap$com$android$i18n$addressinput$AddressField = new int[AddressField.values().length];

         try {
            int[] var4 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var5 = AddressField.DEPENDENT_LOCALITY.ordinal();
            var4[var5] = 1;
         } catch (NoSuchFieldError var17) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var7 = AddressField.LOCALITY.ordinal();
            var6[var7] = 2;
         } catch (NoSuchFieldError var16) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$android$i18n$addressinput$AddressField;
            int var9 = AddressField.ADMIN_AREA.ordinal();
            var8[var9] = 3;
         } catch (NoSuchFieldError var15) {
            ;
         }
      }
   }
}
