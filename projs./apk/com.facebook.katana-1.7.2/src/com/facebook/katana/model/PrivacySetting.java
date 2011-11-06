// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrivacySetting.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class PrivacySetting extends JMCachingDictDestination
{
    public static final class Category extends Enum
    {

        public static Category valueOf(String s)
        {
            return (Category)Enum.valueOf(com/facebook/katana/model/PrivacySetting$Category, s);
        }

        public static Category[] values()
        {
            return (Category[])$VALUES.clone();
        }

        private static final Category $VALUES[];
        public static final Category composer_sticky;
        public static final Category places;
        public static final Category publisher;

        static 
        {
            publisher = new Category("publisher", 0);
            places = new Category("places", 1);
            composer_sticky = new Category("composer_sticky", 2);
            Category acategory[] = new Category[3];
            acategory[0] = publisher;
            acategory[1] = places;
            acategory[2] = composer_sticky;
            $VALUES = acategory;
        }

        private Category(String s, int i)
        {
            super(s, i);
        }
    }


    private PrivacySetting()
    {
        name = null;
        value = null;
        description = null;
        allow = null;
        deny = null;
        networks = null;
        friends = null;
    }

    public PrivacySetting(String s)
    {
        name = null;
        value = s;
        description = null;
        allow = null;
        deny = null;
        networks = null;
        friends = null;
    }

    PrivacySetting(String s, String s1, String s2, String s3, String s4, String s5, String s6)
    {
        name = s;
        value = s1;
        description = s2;
        allow = s3;
        deny = s4;
        networks = s5;
        friends = s6;
    }

    public static final String ALL_FRIENDS = "ALL_FRIENDS";
    public static final String CUSTOM = "CUSTOM";
    public static final String DEFAULT = "DEFAULT";
    public static final String EVERYONE = "EVERYONE";
    public static final String FRIENDS_OF_FRIENDS = "FRIENDS_OF_FRIENDS";
    public static final String NETWORKS_FRIENDS = "NETWORKS_FRIENDS";
    public static final String ONLY_ME = "SELF";
    public static final String SOME_FRIENDS = "SOME_FRIENDS";
    public final String allow;
    public final String deny;
    public final String description;
    public final String friends;
    public final String name;
    public final String networks;
    public final String value;
}
