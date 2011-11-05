package com.google.android.finsky.utils.persistence;

import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.persistence.KeyValueStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FileBasedKeyValueStore implements KeyValueStore {

   private final String mDataStoreId;
   private final File mRootDirectory;


   public FileBasedKeyValueStore(File var1, String var2) {
      this.mRootDirectory = var1;
      this.mDataStoreId = var2;
   }

   private Map<String, String> parseMapFromJson(JSONObject var1) throws JSONException {
      HashMap var2 = Maps.newHashMap();
      Iterator var3 = var1.keys();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         if(!var1.isNull(var4)) {
            String var5 = var1.getString(var4);
            var2.put(var4, var5);
         } else {
            var2.put(var4, (Object)null);
         }
      }

      return var2;
   }

   public void delete(String var1) {
      File var2 = this.mRootDirectory;
      StringBuilder var3 = new StringBuilder();
      String var4 = this.mDataStoreId;
      String var5 = var3.append(var4).append(var1).toString();
      if(!(new File(var2, var5)).delete()) {
         Object[] var6 = new Object[]{var1};
         FinskyLog.e("Attempt to delete \'%s\' failed!", var6);
      }
   }

   public Map<String, Map<String, String>> fetchAll() {
      // $FF: Couldn't be decompiled
   }

   public Map<String, String> get(String var1) {
      File var2 = this.mRootDirectory;
      StringBuilder var3 = new StringBuilder();
      String var4 = this.mDataStoreId;
      String var5 = var3.append(var4).append(var1).toString();
      File var6 = new File(var2, var5);

      Map var10;
      Map var11;
      label34: {
         String var13;
         try {
            FileInputStream var7 = new FileInputStream(var6);
            String var8 = (new ObjectInputStream(var7)).readUTF();
            JSONObject var9 = new JSONObject(var8);
            var7.close();
            var10 = this.parseMapFromJson(var9);
            break label34;
         } catch (IOException var20) {
            var13 = var6.getName();
            Object[] var14 = new Object[]{var13};
            FinskyLog.d("IOException when reading file \'%s\'. Deleting.", var14);
            if(!var6.delete()) {
               Object[] var15 = new Object[]{var13};
               FinskyLog.e("Attempt to delete \'%s\' failed!", var15);
            }
         } catch (JSONException var21) {
            var13 = var6.getName();
            Object[] var17 = new Object[]{var13, null};
            String var18 = var21.toString();
            var17[1] = var18;
            FinskyLog.e("JSONException when reading file \'%s\'. Deleting. error=[%s]", var17);
            if(!var6.delete()) {
               Object[] var19 = new Object[]{var13};
               FinskyLog.e("Attempt to delete \'%s\' failed!", var19);
            }
         }

         var11 = null;
         return var11;
      }

      var11 = var10;
      return var11;
   }

   public void put(String param1, Map<String, String> param2) {
      // $FF: Couldn't be decompiled
   }
}
