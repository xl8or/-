package com.htc.android.mail.pim;

import android.content.ContentValues;
import java.util.ArrayList;
import java.util.Collection;

public class PropertyNode {

   public ContentValues paraMap;
   public ArrayList<String> paraMap_TYPE;
   public String propName;
   public String propValue = "";
   public byte[] propValue_byts;
   public Collection<String> propValue_vector;


   public PropertyNode() {
      ContentValues var1 = new ContentValues();
      this.paraMap = var1;
      ArrayList var2 = new ArrayList();
      this.paraMap_TYPE = var2;
   }
}
