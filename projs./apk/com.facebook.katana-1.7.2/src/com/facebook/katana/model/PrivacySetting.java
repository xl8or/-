package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class PrivacySetting extends JMCachingDictDestination {

   public static final String ALL_FRIENDS = "ALL_FRIENDS";
   public static final String CUSTOM = "CUSTOM";
   public static final String DEFAULT = "DEFAULT";
   public static final String EVERYONE = "EVERYONE";
   public static final String FRIENDS_OF_FRIENDS = "FRIENDS_OF_FRIENDS";
   public static final String NETWORKS_FRIENDS = "NETWORKS_FRIENDS";
   public static final String ONLY_ME = "SELF";
   public static final String SOME_FRIENDS = "SOME_FRIENDS";
   @JMAutogen.InferredType(
      jsonFieldName = "allow"
   )
   public final String allow;
   @JMAutogen.InferredType(
      jsonFieldName = "deny"
   )
   public final String deny;
   @JMAutogen.InferredType(
      jsonFieldName = "description"
   )
   public final String description;
   @JMAutogen.InferredType(
      jsonFieldName = "friends"
   )
   public final String friends;
   @JMAutogen.InferredType(
      jsonFieldName = "name"
   )
   public final String name;
   @JMAutogen.InferredType(
      jsonFieldName = "networks"
   )
   public final String networks;
   @JMAutogen.InferredType(
      jsonFieldName = "value"
   )
   public final String value;


   private PrivacySetting() {
      this.name = null;
      this.value = null;
      this.description = null;
      this.allow = null;
      this.deny = null;
      this.networks = null;
      this.friends = null;
   }

   public PrivacySetting(String var1) {
      this.name = null;
      this.value = var1;
      this.description = null;
      this.allow = null;
      this.deny = null;
      this.networks = null;
      this.friends = null;
   }

   PrivacySetting(String var1, String var2, String var3, String var4, String var5, String var6, String var7) {
      this.name = var1;
      this.value = var2;
      this.description = var3;
      this.allow = var4;
      this.deny = var5;
      this.networks = var6;
      this.friends = var7;
   }

   public static enum Category {

      // $FF: synthetic field
      private static final PrivacySetting.Category[] $VALUES;
      composer_sticky("composer_sticky", 2),
      places("places", 1),
      publisher("publisher", 0);


      static {
         PrivacySetting.Category[] var0 = new PrivacySetting.Category[3];
         PrivacySetting.Category var1 = publisher;
         var0[0] = var1;
         PrivacySetting.Category var2 = places;
         var0[1] = var2;
         PrivacySetting.Category var3 = composer_sticky;
         var0[2] = var3;
         $VALUES = var0;
      }

      private Category(String var1, int var2) {}
   }
}
