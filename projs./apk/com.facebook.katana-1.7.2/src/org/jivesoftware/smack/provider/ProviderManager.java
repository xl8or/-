package org.jivesoftware.smack.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smack.provider.PacketExtensionProvider;

public class ProviderManager {

   private static ProviderManager instance;
   private Map<String, Object> extensionProviders;
   private Map<String, Object> iqProviders;


   private ProviderManager() {
      ConcurrentHashMap var1 = new ConcurrentHashMap();
      this.extensionProviders = var1;
      ConcurrentHashMap var2 = new ConcurrentHashMap();
      this.iqProviders = var2;
      this.initialize();
   }

   private ClassLoader[] getClassLoaders() {
      int var1 = 0;
      ClassLoader[] var2 = new ClassLoader[2];
      ClassLoader var3 = ProviderManager.class.getClassLoader();
      var2[var1] = var3;
      ClassLoader var4 = Thread.currentThread().getContextClassLoader();
      var2[1] = var4;
      ArrayList var5 = new ArrayList();

      for(int var6 = var2.length; var1 < var6; ++var1) {
         ClassLoader var7 = var2[var1];
         if(var7 != null) {
            var5.add(var7);
         }
      }

      ClassLoader[] var9 = new ClassLoader[var5.size()];
      return (ClassLoader[])var5.toArray(var9);
   }

   public static ProviderManager getInstance() {
      synchronized(ProviderManager.class){}

      ProviderManager var0;
      try {
         if(instance == null) {
            instance = new ProviderManager();
         }

         var0 = instance;
      } finally {
         ;
      }

      return var0;
   }

   private String getProviderKey(String var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      StringBuilder var4 = var3.append("<").append(var1).append("/><").append(var2).append("/>");
      return var3.toString();
   }

   public static void setInstance(ProviderManager var0) {
      synchronized(ProviderManager.class){}

      try {
         if(instance != null) {
            throw new IllegalStateException("ProviderManager singleton already set");
         }

         instance = var0;
      } finally {
         ;
      }

   }

   public void addExtensionProvider(String var1, String var2, Object var3) {
      if(!(var3 instanceof PacketExtensionProvider) && !(var3 instanceof Class)) {
         throw new IllegalArgumentException("Provider must be a PacketExtensionProvider or a Class instance.");
      } else {
         String var4 = this.getProviderKey(var1, var2);
         this.extensionProviders.put(var4, var3);
      }
   }

   public void addIQProvider(String var1, String var2, Object var3) {
      if(!(var3 instanceof IQProvider)) {
         label12: {
            if(var3 instanceof Class) {
               Class var4 = (Class)var3;
               if(IQ.class.isAssignableFrom(var4)) {
                  break label12;
               }
            }

            throw new IllegalArgumentException("Provider must be an IQProvider or a Class instance.");
         }
      }

      String var5 = this.getProviderKey(var1, var2);
      this.iqProviders.put(var5, var3);
   }

   public Object getExtensionProvider(String var1, String var2) {
      String var3 = this.getProviderKey(var1, var2);
      return this.extensionProviders.get(var3);
   }

   public Collection<Object> getExtensionProviders() {
      return Collections.unmodifiableCollection(this.extensionProviders.values());
   }

   public Object getIQProvider(String var1, String var2) {
      String var3 = this.getProviderKey(var1, var2);
      return this.iqProviders.get(var3);
   }

   public Collection<Object> getIQProviders() {
      return Collections.unmodifiableCollection(this.iqProviders.values());
   }

   protected void initialize() {
      // $FF: Couldn't be decompiled
   }

   public void removeExtensionProvider(String var1, String var2) {
      String var3 = this.getProviderKey(var1, var2);
      this.extensionProviders.remove(var3);
   }

   public void removeIQProvider(String var1, String var2) {
      String var3 = this.getProviderKey(var1, var2);
      this.iqProviders.remove(var3);
   }
}
