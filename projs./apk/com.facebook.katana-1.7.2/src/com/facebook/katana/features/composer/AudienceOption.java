// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AudienceOption.java

package com.facebook.katana.features.composer;


public interface AudienceOption
{
    public static final class Type extends Enum
    {

        public static Type valueOf(String s)
        {
            return (Type)Enum.valueOf(com/facebook/katana/features/composer/AudienceOption$Type, s);
        }

        public static Type[] values()
        {
            return (Type[])$VALUES.clone();
        }

        private static final Type $VALUES[];
        public static final Type FRIEND_LIST;
        public static final Type GROUP;
        public static final Type PRIVACY_SETTING;

        static 
        {
            PRIVACY_SETTING = new Type("PRIVACY_SETTING", 0);
            GROUP = new Type("GROUP", 1);
            FRIEND_LIST = new Type("FRIEND_LIST", 2);
            Type atype[] = new Type[3];
            atype[0] = PRIVACY_SETTING;
            atype[1] = GROUP;
            atype[2] = FRIEND_LIST;
            $VALUES = atype;
        }

        private Type(String s, int i)
        {
            super(s, i);
        }
    }


    public abstract int getIcon();

    public abstract String getLabel();

    public abstract Type getType();
}
