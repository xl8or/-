package org.apache.http.entity.mime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.http.entity.mime.MinimalField;

public class Header implements Iterable<MinimalField> {

   private final Map<String, List<MinimalField>> fieldMap;
   private final List<MinimalField> fields;


   public Header() {
      LinkedList var1 = new LinkedList();
      this.fields = var1;
      HashMap var2 = new HashMap();
      this.fieldMap = var2;
   }

   public void addField(MinimalField var1) {
      if(var1 != null) {
         String var2 = var1.getName();
         Locale var3 = Locale.US;
         String var4 = var2.toLowerCase(var3);
         Object var5 = (List)this.fieldMap.get(var4);
         if(var5 == null) {
            var5 = new LinkedList();
            this.fieldMap.put(var4, var5);
         }

         ((List)var5).add(var1);
         this.fields.add(var1);
      }
   }

   public MinimalField getField(String var1) {
      MinimalField var2;
      if(var1 == null) {
         var2 = null;
      } else {
         Locale var3 = Locale.US;
         String var4 = var1.toLowerCase(var3);
         List var5 = (List)this.fieldMap.get(var4);
         if(var5 != null && !var5.isEmpty()) {
            var2 = (MinimalField)var5.get(0);
         } else {
            var2 = null;
         }
      }

      return var2;
   }

   public List<MinimalField> getFields() {
      List var1 = this.fields;
      return new ArrayList(var1);
   }

   public List<MinimalField> getFields(String var1) {
      Object var2;
      if(var1 == null) {
         var2 = null;
      } else {
         Locale var3 = Locale.US;
         String var4 = var1.toLowerCase(var3);
         List var5 = (List)this.fieldMap.get(var4);
         if(var5 != null && !var5.isEmpty()) {
            var2 = new ArrayList(var5);
         } else {
            var2 = Collections.emptyList();
         }
      }

      return (List)var2;
   }

   public Iterator<MinimalField> iterator() {
      return Collections.unmodifiableList(this.fields).iterator();
   }

   public int removeFields(String var1) {
      int var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         Locale var3 = Locale.US;
         String var4 = var1.toLowerCase(var3);
         List var5 = (List)this.fieldMap.remove(var4);
         if(var5 != null && !var5.isEmpty()) {
            this.fields.removeAll(var5);
            var2 = var5.size();
         } else {
            var2 = 0;
         }
      }

      return var2;
   }

   public void setField(MinimalField var1) {
      if(var1 != null) {
         String var2 = var1.getName();
         Locale var3 = Locale.US;
         String var4 = var2.toLowerCase(var3);
         List var5 = (List)this.fieldMap.get(var4);
         if(var5 != null && !var5.isEmpty()) {
            var5.clear();
            var5.add(var1);
            int var7 = -1;
            int var8 = 0;

            for(Iterator var9 = this.fields.iterator(); var9.hasNext(); ++var8) {
               String var10 = ((MinimalField)var9.next()).getName();
               String var11 = var1.getName();
               if(var10.equalsIgnoreCase(var11)) {
                  var9.remove();
                  if(var7 == -1) {
                     var7 = var8;
                  }
               }
            }

            this.fields.add(var7, var1);
         } else {
            this.addField(var1);
         }
      }
   }

   public String toString() {
      return this.fields.toString();
   }
}
