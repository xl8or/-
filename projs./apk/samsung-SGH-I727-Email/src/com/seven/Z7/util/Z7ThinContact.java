package com.seven.Z7.util;

import android.text.TextUtils;
import android.util.Log;
import com.seven.util.IntArrayMap;
import java.util.ArrayList;
import java.util.List;

public class Z7ThinContact extends IntArrayMap {

   public static final String TAG = "Z7Contact";
   public static final int VCARD_VALUE_ADDRESS = 8;
   public static final String VCARD_VALUE_ADDRESS_PARAM_TYPE_HOME = "HOME";
   public static final String VCARD_VALUE_ADDRESS_PARAM_TYPE_POSTAL = "POSTAL";
   public static final String VCARD_VALUE_ADDRESS_PARAM_TYPE_WORK = "WORK";
   public static final int VCARD_VALUE_AGENT = 10;
   public static final int VCARD_VALUE_ANNIVERSARY = 15;
   public static final int VCARD_VALUE_BIRTHDAY = 14;
   public static final int VCARD_VALUE_CATEGORIES = 17;
   public static final int VCARD_VALUE_CHILDREN = 12;
   public static final int VCARD_VALUE_CLASS = 18;
   public static final String VCARD_VALUE_CLASS_VALUE_CONFIDENTIAL = "CONFIDENTIAL";
   public static final String VCARD_VALUE_CLASS_VALUE_PERSONAL = "X-PERSONAL";
   public static final String VCARD_VALUE_CLASS_VALUE_PRIVATE = "PRIVATE";
   public static final String VCARD_VALUE_CLASS_VALUE_PUBLIC = "PUBLIC";
   public static final int VCARD_VALUE_COMPANY_PHONE = 21;
   public static final int VCARD_VALUE_CUSTOM_PRESERVE = 19;
   public static final int VCARD_VALUE_EMAIL = 5;
   public static final int VCARD_VALUE_FORMATTED_NAME = 0;
   public static final int VCARD_VALUE_IM = 22;
   public static final int VCARD_VALUE_MANAGER = 20;
   public static final int VCARD_VALUE_NAME = 1;
   public static final int VCARD_VALUE_NICKNAME = 3;
   public static final int VCARD_VALUE_NOTE = 13;
   public static final int VCARD_VALUE_ORGANIZATION = 7;
   public static final String VCARD_VALUE_PARAM_TYPE_ANY = "ANY";
   public static final int VCARD_VALUE_PHONE = 4;
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_CAR = "CAR";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_CELLULAR = "CELL";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_FAX = "FAX";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_HOME = "HOME";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_ISDN = "ISDN";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_MSG = "MSG";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_PAGER = "PAGER";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_RADIO = "RADIO";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_VIDEO = "VIDEO";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_VOICE = "VOICE";
   public static final String VCARD_VALUE_PHONE_PARAM_TYPE_WORK = "WORK";
   public static final int VCARD_VALUE_PHOTO = 23;
   public static final int VCARD_VALUE_SPOUSE = 11;
   public static final int VCARD_VALUE_TITLE = 6;
   public static final int VCARD_VALUE_URL = 9;
   public static final int Z7CPK_ENCODING = 1;
   public static final int Z7CPK_TYPE = 0;
   public static final int Z7CPPK_ADDRESS_CITY = 3;
   public static final int Z7CPPK_ADDRESS_COUNTRY = 6;
   public static final int Z7CPPK_ADDRESS_EXTENDED = 1;
   public static final int Z7CPPK_ADDRESS_POSTAL_CODE = 5;
   public static final int Z7CPPK_ADDRESS_POST_OFFICE_BOX = 0;
   public static final int Z7CPPK_ADDRESS_STATE_OR_PROVINCE = 4;
   public static final int Z7CPPK_ADDRESS_STREET = 2;
   public static final int Z7CPPK_COMPANY_NAME = 0;
   public static final int Z7CPPK_DEPARTMENT_NAME = 1;
   public static final int Z7CPPK_DISPLAY_NAME_PREFIX = 3;
   public static final int Z7CPPK_GENERATION = 4;
   public static final int Z7CPPK_GIVEN_NAME = 1;
   public static final int Z7CPPK_MIDDLE_NAME = 2;
   public static final int Z7CPPK_PHOTO_DATA = 1;
   public static final int Z7CPPK_PHOTO_TYPE = 0;
   public static final int Z7CPPK_SURNAME = 0;
   public static final int Z7_CONTACTS_LOCAL_CONTACT_ID = 4098;
   public static final int Z7_CONTACTS_NATIVE_ID = 4100;
   public static final int Z7_CONTACTS_SEARCH_CONTACT_ID = 4096;
   public static final int Z7_CONTACT_SETTING_CLIENT_SUPPORTED_PHOTO_TYPES = 6;
   public static final int Z7_CONTACT_SETTING_CONNECTOR_SUPPORTED_PHOTO_TYPES = 7;
   public static final int Z7_CONTACT_SETTING_CONTEXT = 0;
   public static final int Z7_CONTACT_SETTING_FIELD_CAPABILITIES = 3;
   public static final int Z7_CONTACT_SETTING_HOLD_SERVER_UPDATES = 4;
   public static final int Z7_CONTACT_SETTING_SYNCHRONIZE_DIST_LISTS = 2;
   public static final int Z7_CONTACT_SETTING_SYNCHRONIZE_PHOTOS = 8;
   public static final int Z7_CONTACT_SETTING_TRUNCATION_LIMIT = 1;


   public Z7ThinContact() {}

   public Z7ThinContact(int var1) {
      super(var1);
   }

   public Z7ThinContact(IntArrayMap var1) {
      super(var1);
   }

   public static String getContactName(IntArrayMap var0) {
      return getContactName(var0, (boolean)0);
   }

   public static String getContactName(IntArrayMap var0, boolean var1) {
      List var2 = var0.getList(1);
      IntArrayMap var3 = null;
      if(var2 != null && var2.size() > 1) {
         var3 = (IntArrayMap)var2.get(1);
      }

      String var8;
      if(var1 && var3 != null) {
         StringBuilder var4 = new StringBuilder();
         String var5 = var3.getString(0, "");
         StringBuilder var6 = var4.append(var5);
         String var7 = var3.getString(1, "").trim();
         var8 = var6.append(var7).toString();
      } else {
         List var9 = var0.getList(0);
         if(var9 != null && var9.size() > 1) {
            String var10 = (String)var9.get(1);
            if(!TextUtils.isEmpty(var10)) {
               var8 = var10;
               return var8;
            }
         }

         if(var3 != null) {
            String var11 = var3.getString(1, "").trim();
            if(var11.length() != 0) {
               var11 = var11 + " ";
            }

            StringBuilder var12 = (new StringBuilder()).append(var11);
            String var13 = var3.getString(0, "").trim();
            String var14 = var12.append(var13).toString();
            if(var14.length() != 0) {
               var8 = var14;
               return var8;
            }
         }

         List var15 = var0.getList(3);
         if(var15 != null && var15.size() > 1 && ((String)var15.get(1)).trim().length() > 0) {
            var8 = ((String)var15.get(1)).trim();
         } else {
            var8 = "";
         }
      }

      return var8;
   }

   public static String trimIfNeeded(String var0) {
      String var2;
      if(var0 != null) {
         String var1 = var0.trim();
         if(var1.length() > 0) {
            var2 = var1;
         } else {
            var2 = null;
         }
      } else {
         var2 = var0;
      }

      return var2;
   }

   public Object accessDefaultValue(int var1, Object var2) {
      return this.accessValueList(var1).accessDefaultValue(var2);
   }

   public Object accessValueAt(int var1, int var2, Object var3) {
      return this.accessValueList(var1).accessValueAt(var2, var3);
   }

   public Z7ThinContact.Z7ContactValueList accessValueList(int var1) {
      if(!this.hasValueList(var1)) {
         ArrayList var2 = new ArrayList();
         this.put(var1, var2);
      }

      return this.getValueList(var1);
   }

   public void forceDefaultValue(int var1, Object var2) {
      this.accessValueList(var1).forceDefaultValue(var2);
   }

   public void forceValueAt(int var1, int var2, Object var3) {
      this.accessValueList(var1).forceValueAt(var2, var3);
   }

   public String getContactName() {
      return getContactName(this);
   }

   public Object getDefaultValue(int var1) {
      return this.getValueList(var1).getDefaultValue();
   }

   public int getNumValues(int var1) {
      return this.getValueList(var1).getSize();
   }

   public Object getValueAt(int var1, int var2) {
      return this.getValueList(var1).getValueAt(var2);
   }

   public Z7ThinContact.Z7ContactValueList getValueList(int var1) {
      List var2 = (List)this.get(var1);
      return new Z7ThinContact.Z7ContactValueList(var2);
   }

   public boolean hasDefaultValue(int var1) {
      boolean var2;
      if(this.hasValueList(var1) && this.getValueList(var1).hasDefault()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean hasValueAt(int var1, int var2) {
      boolean var3;
      if(this.hasValueList(var1) && this.getValueList(var1).hasAt(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean hasValueList(int var1) {
      return this.containsKey(var1);
   }

   public int nameHash() {
      StringBuffer var1 = new StringBuffer();
      IntArrayMap var2 = (IntArrayMap)this.getDefaultValue(1);
      if(var2 != null) {
         if(var2.containsKey(0)) {
            String var3 = trimIfNeeded(var2.getString(0));
            if(var3 != null) {
               String var4 = var3.toLowerCase();
               var1.append(var4);
            } else {
               StringBuffer var9 = var1.append("");
            }
         } else {
            StringBuffer var10 = var1.append("");
         }

         if(var2.containsKey(1)) {
            String var6 = trimIfNeeded(var2.getString(1));
            if(var6 != null) {
               String var7 = var6.toLowerCase();
               var1.append(var7);
            } else {
               StringBuffer var11 = var1.append("");
            }
         } else {
            StringBuffer var12 = var1.append("");
         }
      } else {
         StringBuffer var13 = var1.append("");
         StringBuffer var14 = var1.append("");
      }

      return var1.toString().hashCode();
   }

   public void trimFields() {
      String var1 = "trimFields, [initial] this: " + this;
      int var2 = Log.v("Z7Contact", var1);
      int var3 = 0;

      while(true) {
         int var4 = this.size();
         if(var3 >= var4) {
            String var24 = "trimFields, [final] this: " + this;
            int var25 = Log.v("Z7Contact", var24);
            return;
         }

         Object var5 = this.getAt(var3);
         if(var5 != null && var5 instanceof List) {
            List var8 = (List)this.getAt(var3);
            Z7ThinContact.Z7ContactValueList var9 = new Z7ThinContact.Z7ContactValueList(var8);
            int var10 = this.getKeyAt(var3);
            int var11 = 0;

            while(true) {
               int var12 = var9.getSize();
               if(var11 >= var12) {
                  if(var9.getSize() == 0) {
                     this.removeAt(var3);
                     var3 += -1;
                  }
                  break;
               }

               Object var13 = var9.getValueAt(var11);
               if(var13 != null && (!(var13 instanceof String) || ((String)var13).length() != 0) && (!(var13 instanceof List) || ((List)var13).size() != 0)) {
                  if(var13 instanceof IntArrayMap) {
                     IntArrayMap var15 = (IntArrayMap)var13;
                     if(var10 != 1 && var10 != 7 && var10 != 8) {
                        if(var10 != 10 && var10 != 11 && var10 != 20) {
                           if(var15.size() == 0) {
                              var9.removeAt(var11);
                              var11 += -1;
                           }
                        } else {
                           Z7ThinContact var22 = new Z7ThinContact(var15);
                           var22.trimFields();
                           if(var22.size() == 0) {
                              var9.removeAt(var11);
                              var11 += -1;
                           }
                        }
                     } else {
                        boolean var16 = false;
                        int var17 = 0;

                        while(true) {
                           int var18 = var15.size();
                           if(var17 >= var18) {
                              if(!var16) {
                                 var9.removeAt(var11);
                                 var11 += -1;
                              }
                              break;
                           }

                           Object var19 = var15.getAt(var17);
                           if(var19 != null) {
                              if(var19 instanceof String && ((String)var19).length() == 0) {
                                 int var20 = var15.getKeyAt(var17);
                                 var15.put(var20, (Object)null);
                              } else {
                                 var16 = true;
                              }
                           }

                           ++var17;
                        }
                     }
                  }
               } else {
                  var9.removeAt(var11);
                  var11 += -1;
               }

               int var14 = var11 + 1;
            }
         } else {
            this.removeAt(var3);
            var3 += -1;
         }

         int var7 = var3 + 1;
      }
   }

   public class Z7ContactValueList {

      private List m_list = null;


      public Z7ContactValueList() {
         ArrayList var2 = new ArrayList();
         this.m_list = var2;
      }

      public Z7ContactValueList(List var2) {
         this.m_list = var2;
      }

      public Z7ThinContact.Z7ContactParameterMap accessDefaultParameterMap() {
         return this.accessParameterMapAt(0);
      }

      public Object accessDefaultValue(Object var1) {
         return this.accessValueAt(0, var1);
      }

      public Z7ThinContact.Z7ContactParameterMap accessParameterMap(int var1, String var2) {
         int var3 = this.indexOf(var1, var2);
         Z7ThinContact.Z7ContactParameterMap var4;
         if(var3 != -1) {
            var4 = this.getParameterMapAt(var3);
         } else {
            this.add(var1, var2, (Object)null);
            int var5 = this.getSize() - 1;
            var4 = this.getParameterMapAt(var5);
         }

         return var4;
      }

      public Z7ThinContact.Z7ContactParameterMap accessParameterMap(Z7ThinContact.Z7ContactParameterMap var1) {
         int var2 = this.indexOf(var1);
         Z7ThinContact.Z7ContactParameterMap var3;
         if(var2 != -1) {
            var3 = this.getParameterMapAt(var2);
         } else {
            this.add(var1, (Object)null);
            int var4 = this.getSize() - 1;
            var3 = this.getParameterMapAt(var4);
         }

         return var3;
      }

      public Z7ThinContact.Z7ContactParameterMap accessParameterMapAt(int var1) {
         while(!this.hasAt(var1)) {
            Z7ThinContact var2 = Z7ThinContact.this;
            Z7ThinContact.Z7ContactParameterMap var3 = var2.new Z7ContactParameterMap();
            this.add(var3, (Object)null);
         }

         return this.getParameterMapAt(var1);
      }

      public Object accessValue(int var1, String var2, Object var3) {
         int var4 = this.indexOf(var1, var2);
         Object var5;
         if(var4 != -1) {
            var5 = this.getValueAt(var4);
         } else {
            this.add(var1, var2, var3);
            int var6 = this.getSize() - 1;
            var5 = this.getValueAt(var6);
         }

         return var5;
      }

      public Object accessValue(Z7ThinContact.Z7ContactParameterMap var1, Object var2) {
         int var3 = this.indexOf(var1);
         Object var4;
         if(var3 != -1) {
            var4 = this.getValueAt(var3);
         } else {
            this.add(var1, var2);
            int var5 = this.getSize() - 1;
            var4 = this.getValueAt(var5);
         }

         return var4;
      }

      public Object accessValueAt(int var1, Object var2) {
         while(!this.hasAt(var1)) {
            Z7ThinContact var3 = Z7ThinContact.this;
            Z7ThinContact.Z7ContactParameterMap var4 = var3.new Z7ContactParameterMap();
            this.add(var4, var2);
         }

         return this.getValueAt(var1);
      }

      public void add(int var1, String var2, Object var3) {
         Z7ThinContact var4 = Z7ThinContact.this;
         Z7ThinContact.Z7ContactParameterMap var5 = var4.new Z7ContactParameterMap();
         Z7ThinContact var6 = Z7ThinContact.this;
         Z7ThinContact.Z7ContactParameter var7 = var6.new Z7ContactParameter(var2);
         var5.setParameter(var1, var7);
         this.add(var5, var3);
      }

      public void add(Z7ThinContact.Z7ContactParameterMap var1, Object var2) {
         this.m_list.add(var1);
         this.m_list.add(var2);
      }

      public void forceDefaultValue(Object var1) {
         this.forceValueAt(0, var1);
      }

      public void forceValueAt(int var1, Object var2) {
         int var3 = this.getSize();
         if(var1 < var3) {
            List var4 = this.m_list;
            int var5 = (var1 << 1) + 1;
            var4.set(var5, var2);
         } else {
            this.accessValueAt(var1, var2);
         }
      }

      public Z7ThinContact.Z7ContactParameterMap getDefaultParameterMap() {
         return this.getParameterMapAt(0);
      }

      public Object getDefaultValue() {
         return this.getValueAt(0);
      }

      public Z7ThinContact.Z7ContactParameterMap getParameterMap(int var1, String var2) {
         int var3 = this.indexOf(var1, var2);
         return this.getParameterMapAt(var3);
      }

      public Z7ThinContact.Z7ContactParameterMap getParameterMapAt(int var1) {
         Z7ThinContact var2 = Z7ThinContact.this;
         List var3 = this.m_list;
         int var4 = var1 << 1;
         IntArrayMap var5 = (IntArrayMap)var3.get(var4);
         return var2.new Z7ContactParameterMap(var5);
      }

      public int getSize() {
         return this.m_list.size() >> 1;
      }

      public Object getValue(int var1, String var2) {
         int var3 = this.indexOf(var1, var2);
         return this.getValueAt(var3);
      }

      public Object getValueAt(int var1) {
         Object var2;
         if(this.m_list == null) {
            var2 = null;
         } else {
            List var3 = this.m_list;
            int var4 = (var1 << 1) + 1;
            var2 = var3.get(var4);
         }

         return var2;
      }

      public boolean has(int var1, String var2) {
         boolean var3;
         if(this.indexOf(var1, var2) != -1) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public boolean has(Z7ThinContact.Z7ContactParameterMap var1) {
         boolean var2;
         if(this.indexOf(var1) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean hasAt(int var1) {
         int var2 = this.getSize();
         boolean var3;
         if(var1 < var2) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public boolean hasDefault() {
         return this.hasAt(0);
      }

      public int indexOf(int var1, String var2) {
         return this.indexOf(var1, var2, 0);
      }

      public int indexOf(int var1, String var2, int var3) {
         int var4 = this.getSize();
         int var5 = var3;

         int var7;
         while(true) {
            if(var5 >= var4) {
               var7 = -1;
               break;
            }

            Z7ThinContact.Z7ContactParameterMap var6 = this.getParameterMapAt(var5);
            if(var6.hasParameter(var1) && var6.getParameter(var1).hasValue(var2)) {
               var7 = var5;
               break;
            }

            ++var5;
         }

         return var7;
      }

      public int indexOf(Z7ThinContact.Z7ContactParameterMap var1) {
         return this.indexOf(var1, 0);
      }

      public int indexOf(Z7ThinContact.Z7ContactParameterMap var1, int var2) {
         int var3 = var1.size();
         int var4 = this.getSize();
         int var5 = var2;

         int var13;
         while(true) {
            if(var5 >= var4) {
               var13 = -1;
               break;
            }

            Z7ThinContact.Z7ContactParameterMap var6 = this.getParameterMapAt(var5);
            if(var6.size() == var3) {
               int var7;
               for(var7 = 0; var7 < var3; ++var7) {
                  int var8 = var1.getKeyAt(var7);
                  if(!var6.hasParameter(var8)) {
                     break;
                  }

                  Z7ThinContact.Z7ContactParameter var9 = var6.getParameter(var8);
                  Z7ThinContact var10 = Z7ThinContact.this;
                  List var11 = (List)var1.getAt(var7);
                  Z7ThinContact.Z7ContactParameter var12 = var10.new Z7ContactParameter(var11);
                  if(!var9.equivalent(var12)) {
                     break;
                  }
               }

               if(var7 == var3) {
                  var13 = var5;
                  break;
               }
            }

            ++var5;
         }

         return var13;
      }

      public void remove(int var1, String var2) {
         int var3 = this.indexOf(var1, var2);
         this.removeAt(var3);
      }

      public void removeAt(int var1) {
         int var2 = this.getSize();
         if(var1 < var2) {
            List var3 = this.m_list;
            int var4 = var1 << 1;
            var3.remove(var4);
            List var6 = this.m_list;
            int var7 = var1 << 1;
            var6.remove(var7);
         }
      }

      public void setParameterMapAt(int var1, Z7ThinContact.Z7ContactParameterMap var2) {
         if(var1 == -1) {
            this.add(var2, (Object)null);
         } else {
            int var3 = this.getSize();
            if(var1 < var3) {
               List var4 = this.m_list;
               int var5 = var1 << 1;
               var4.set(var5, var2);
            }
         }
      }

      public void setValueAt(int var1, Object var2) {
         int var3 = this.getSize();
         if(var1 < var3) {
            List var4 = this.m_list;
            int var5 = (var1 << 1) + 1;
            var4.set(var5, var2);
         }
      }

      public List toList() {
         return this.m_list;
      }
   }

   public class Z7ContactParameter {

      private List m_list;


      public Z7ContactParameter() {
         this.m_list = null;
         ArrayList var2 = new ArrayList();
         this.m_list = var2;
      }

      public Z7ContactParameter(Z7ThinContact.Z7ContactParameter var2) {
         List var3 = var2.m_list;
         this(var3);
      }

      public Z7ContactParameter(String var2) {
         this();
         this.add(var2);
      }

      public Z7ContactParameter(List var2) {
         this.m_list = null;
         this.m_list = var2;
      }

      public void add(String var1) {
         this.m_list.add(var1);
      }

      public void ensureValueExists(String var1) {
         if(!this.hasValue(var1)) {
            this.m_list.add(var1);
         }
      }

      public boolean equivalent(Z7ThinContact.Z7ContactParameter var1) {
         int var2 = 0;

         while(true) {
            int var3 = this.m_list.size();
            boolean var5;
            if(var2 >= var3) {
               var2 = 0;

               while(true) {
                  int var6 = var1.m_list.size();
                  if(var2 >= var6) {
                     var5 = true;
                     return var5;
                  }

                  String var7 = (String)var1.m_list.get(var2);
                  if(!this.hasValue(var7)) {
                     var5 = false;
                     return var5;
                  }

                  ++var2;
               }
            }

            String var4 = (String)this.m_list.get(var2);
            if(!var1.hasValue(var4)) {
               var5 = false;
               return var5;
            }

            ++var2;
         }
      }

      public int getSize() {
         return this.m_list.size();
      }

      public boolean hasValue(String var1) {
         return this.m_list.contains(var1);
      }

      public void removeValue(String var1) {
         int var2 = this.m_list.indexOf(var1);
         if(var2 != -1) {
            this.m_list.remove(var2);
         }
      }

      public List toList() {
         return this.m_list;
      }
   }

   public class Z7ContactParameterMap extends IntArrayMap {

      public Z7ContactParameterMap() {}

      public Z7ContactParameterMap(IntArrayMap var2) {
         super(var2);
      }

      public Z7ThinContact.Z7ContactParameter accessParameter(int var1) {
         if(!this.hasParameter(var1)) {
            ArrayList var2 = new ArrayList();
            this.put(var1, var2);
         }

         return this.getParameter(var1);
      }

      public Z7ThinContact.Z7ContactParameter getParameter(int var1) {
         Z7ThinContact var2 = Z7ThinContact.this;
         List var3 = (List)this.get(var1);
         return var2.new Z7ContactParameter(var3);
      }

      public boolean hasParameter(int var1) {
         return this.containsKey(var1);
      }

      public boolean hasParameterWithValue(int var1, String var2) {
         boolean var3;
         if(this.hasParameter(var1) && this.getParameter(var1).hasValue(var2)) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public void setParameter(int var1, Z7ThinContact.Z7ContactParameter var2) {
         List var3 = var2.toList();
         this.put(var1, var3);
      }
   }
}
