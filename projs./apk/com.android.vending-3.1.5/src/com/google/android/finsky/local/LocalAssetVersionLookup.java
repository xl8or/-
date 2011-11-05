package com.google.android.finsky.local;

import com.google.android.finsky.local.StoredLocalAssetVersion;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class LocalAssetVersionLookup {

   private final HashMap<String, StoredLocalAssetVersion> mIdLookup;
   private final HashMap<String, HashMap<String, StoredLocalAssetVersion>> mPackageLookup;


   public LocalAssetVersionLookup() {
      HashMap var1 = new HashMap();
      this.mIdLookup = var1;
      HashMap var2 = new HashMap();
      this.mPackageLookup = var2;
   }

   public void clear() {
      this.mIdLookup.clear();
      this.mPackageLookup.clear();
   }

   public Collection<StoredLocalAssetVersion> getAll() {
      return Collections.unmodifiableCollection(this.mIdLookup.values());
   }

   public Collection<StoredLocalAssetVersion> getByPackageName(String var1) {
      HashMap var2 = (HashMap)this.mPackageLookup.get(var1);
      Object var3;
      if(var2 == null) {
         var3 = new LinkedList();
      } else {
         var3 = var2.values();
      }

      return (Collection)var3;
   }

   public StoredLocalAssetVersion getByServerId(String var1) {
      return (StoredLocalAssetVersion)this.mIdLookup.get(var1);
   }

   public void put(StoredLocalAssetVersion var1) {
      String var2 = var1.getAssetId();
      this.mIdLookup.put(var2, var1);
      String var4 = var1.getPackageName();
      HashMap var5 = (HashMap)this.mPackageLookup.get(var4);
      if(var5 == null) {
         var5 = new HashMap();
      }

      var5.put(var2, var1);
      this.mPackageLookup.put(var4, var5);
   }

   public StoredLocalAssetVersion remove(String var1) {
      StoredLocalAssetVersion var2 = (StoredLocalAssetVersion)this.mIdLookup.remove(var1);
      if(var2 != null) {
         HashMap var3 = this.mPackageLookup;
         String var4 = var2.getPackageName();
         HashMap var5 = (HashMap)var3.get(var4);
         if(var5 != null) {
            var5.remove(var1);
         }

         if(var5.isEmpty()) {
            HashMap var7 = this.mPackageLookup;
            String var8 = var2.getPackageName();
            var7.remove(var8);
         }
      }

      return var2;
   }
}
