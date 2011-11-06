// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPhonebookContactUser.java

package com.facebook.katana.model;


// Referenced classes of package com.facebook.katana.model:
//            FacebookPhonebookContact

public class FacebookPhonebookContactUser extends FacebookPhonebookContact
{

    public FacebookPhonebookContactUser(String s, String s1, String s2, long l, String s3)
    {
        super(s, -1L, false, l, s2, s1);
        imageUrl = s3;
    }

    public final String imageUrl;
}
