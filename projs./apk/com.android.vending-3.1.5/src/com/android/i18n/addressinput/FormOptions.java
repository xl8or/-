package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.CacheData;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class FormOptions {

   private final String mBaseId;
   private final EnumMap<AddressField, String> mCustomLabels;
   private final EnumSet<AddressField> mHiddenFields;
   private final EnumMap<AddressField, Integer> mMaxLengths;
   private final Map<String, AddressField[]> mOverrideFieldOrder;
   private final EnumSet<AddressField> mReadonlyFields;
   private final EnumSet<AddressField> mRequiredFields;
   private final String mServerUrl;


   private FormOptions(FormOptions.Builder var1) {
      EnumMap var2 = new EnumMap(AddressField.class);
      this.mCustomLabels = var2;
      HashMap var3 = new HashMap();
      this.mOverrideFieldOrder = var3;
      EnumMap var4 = new EnumMap(AddressField.class);
      this.mMaxLengths = var4;
      String var5 = var1.mBaseId;
      this.mBaseId = var5;
      EnumSet var6 = EnumSet.copyOf(var1.mHiddenFields);
      this.mHiddenFields = var6;
      EnumSet var7 = EnumSet.copyOf(var1.mReadonlyFields);
      this.mReadonlyFields = var7;
      EnumSet var8 = EnumSet.copyOf(var1.mRequiredFields);
      this.mRequiredFields = var8;
      EnumMap var9 = this.mCustomLabels;
      EnumMap var10 = var1.mCustomLabels;
      var9.putAll(var10);
      Map var11 = this.mOverrideFieldOrder;
      Map var12 = var1.mOverrideFieldOrder;
      var11.putAll(var12);
      EnumMap var13 = this.mMaxLengths;
      EnumMap var14 = var1.mMaxLengths;
      var13.putAll(var14);
      String var15 = var1.mServerUrl;
      this.mServerUrl = var15;
   }

   // $FF: synthetic method
   FormOptions(FormOptions.Builder var1, FormOptions.1 var2) {
      this(var1);
   }

   String getBaseId() {
      return this.mBaseId;
   }

   AddressField[] getCustomFieldOrder(String var1) {
      if(var1 == null) {
         throw new RuntimeException("regionCode cannot be null.");
      } else {
         return (AddressField[])this.mOverrideFieldOrder.get(var1);
      }
   }

   String getCustomLabel(AddressField var1) {
      return (String)this.mCustomLabels.get(var1);
   }

   Integer getCustomMaxLength(AddressField var1) {
      return (Integer)this.mMaxLengths.get(var1);
   }

   EnumSet<AddressField> getRequiredFields() {
      return this.mRequiredFields;
   }

   String getUrl() {
      return this.mServerUrl;
   }

   boolean isHidden(AddressField var1) {
      return this.mHiddenFields.contains(var1);
   }

   boolean isReadonly(AddressField var1) {
      return this.mReadonlyFields.contains(var1);
   }

   boolean isRequired(AddressField var1) {
      return this.mRequiredFields.contains(var1);
   }

   public static class Builder {

      private String mBaseId = "addressform";
      private final EnumMap<AddressField, String> mCustomLabels;
      private final EnumSet<AddressField> mHiddenFields;
      private final EnumMap<AddressField, Integer> mMaxLengths;
      private final Map<String, AddressField[]> mOverrideFieldOrder;
      private final EnumSet<AddressField> mReadonlyFields;
      private final EnumSet<AddressField> mRequiredFields;
      private String mServerUrl;


      public Builder() {
         EnumSet var1 = EnumSet.noneOf(AddressField.class);
         this.mRequiredFields = var1;
         EnumSet var2 = EnumSet.noneOf(AddressField.class);
         this.mHiddenFields = var2;
         EnumSet var3 = EnumSet.noneOf(AddressField.class);
         this.mReadonlyFields = var3;
         EnumMap var4 = new EnumMap(AddressField.class);
         this.mCustomLabels = var4;
         HashMap var5 = new HashMap();
         this.mOverrideFieldOrder = var5;
         EnumMap var6 = new EnumMap(AddressField.class);
         this.mMaxLengths = var6;
         String var7 = (new CacheData()).getUrl();
         this.mServerUrl = var7;
      }

      public FormOptions.Builder baseId(String var1) {
         if(var1 == null) {
            throw new RuntimeException("baseId cannot be null.");
         } else {
            this.mBaseId = var1;
            return this;
         }
      }

      public FormOptions build() {
         return new FormOptions(this, (FormOptions.1)null);
      }

      public FormOptions.Builder customizeFieldOrder(String var1, AddressField ... var2) {
         if(var1 == null) {
            throw new RuntimeException("regionCode cannot be null.");
         } else if(var2 == null) {
            throw new RuntimeException("Fields cannot be null.");
         } else if(var2.length <= 1) {
            throw new RuntimeException("There must be more than one field.");
         } else {
            HashSet var3 = new HashSet();
            AddressField[] var4 = new AddressField[var2.length];
            int var5 = 0;
            AddressField[] var6 = var2;
            int var7 = var2.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               AddressField var9 = var6[var8];
               if(var3.contains(var9)) {
                  throw new RuntimeException("Address fields cannot be repeated.");
               }

               var3.add(var9);
               var4[var5] = var9;
               ++var5;
            }

            this.mOverrideFieldOrder.put(var1, var4);
            return this;
         }
      }

      public FormOptions.Builder customizeLabel(AddressField var1, String var2) {
         if(var1 == null) {
            throw new RuntimeException("AddressField field cannot be null.");
         } else if(var2 == null) {
            throw new RuntimeException("Label cannot be null.");
         } else {
            this.mCustomLabels.put(var1, var2);
            return this;
         }
      }

      public FormOptions.Builder customizeMaxLength(AddressField var1, int var2) {
         if(var1 == null) {
            throw new RuntimeException("AddressField field cannot be null.");
         } else {
            EnumMap var3 = this.mMaxLengths;
            Integer var4 = Integer.valueOf(var2);
            var3.put(var1, var4);
            return this;
         }
      }

      public FormOptions.Builder hide(AddressField var1) {
         if(var1 == null) {
            throw new RuntimeException("AddressField field cannot be null.");
         } else {
            this.mHiddenFields.add(var1);
            return this;
         }
      }

      public FormOptions.Builder readonly(AddressField var1) {
         if(var1 == null) {
            throw new RuntimeException("AddressField field cannot be null.");
         } else {
            this.mReadonlyFields.add(var1);
            return this;
         }
      }

      public FormOptions.Builder required(AddressField var1) {
         if(var1 == null) {
            throw new RuntimeException("AddressField field cannot be null.");
         } else {
            this.mRequiredFields.add(var1);
            return this;
         }
      }

      public FormOptions.Builder setUrl(String var1) {
         if(var1 == null) {
            throw new RuntimeException("Can\'t set address server URL to null.");
         } else {
            this.mServerUrl = var1;
            return this;
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
