// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhonebookUtils.java

package com.facebook.katana.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import com.facebook.katana.model.FacebookPhonebookContact;
import java.util.*;

public class PhonebookUtils
{

    public PhonebookUtils()
    {
    }

    public static List extractAddressBook(Context context)
    {
        HashMap hashmap = new HashMap();
        HashSet hashset = new HashSet();
        String as[] = new String[2];
        as[0] = "_id";
        as[1] = "display_name";
        String as1[] = new String[2];
        as1[0] = "contact_id";
        as1[1] = "data1";
        String as2[] = new String[2];
        as2[0] = "contact_id";
        as2[1] = "data1";
        ContentResolver contentresolver = context.getContentResolver();
        Cursor cursor = contentresolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI, as, null, null, null);
        Cursor cursor1 = contentresolver.query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, as1, null, null, null);
        Cursor cursor2 = contentresolver.query(android.provider.ContactsContract.CommonDataKinds.Email.CONTENT_URI, as2, null, null, null);
        Long long3;
        for(; cursor.moveToNext(); hashmap.put(long3, new FacebookPhonebookContact(cursor.getString(cursor.getColumnIndex("display_name")), long3.longValue(), new ArrayList(), new ArrayList())))
            long3 = Long.valueOf(cursor.getLong(cursor.getColumnIndex("_id")));

        cursor.close();
        do
        {
            if(!cursor2.moveToNext())
                break;
            Long long2 = Long.valueOf(cursor2.getLong(cursor2.getColumnIndex("contact_id")));
            if(hashmap.get(long2) != null)
            {
                String s1 = cursor2.getString(cursor2.getColumnIndex("data1"));
                if(!hashset.contains(s1))
                {
                    hashset.add(s1);
                    ((FacebookPhonebookContact)hashmap.get(long2)).emails.add(s1);
                }
            }
        } while(true);
        cursor2.close();
        do
        {
            if(!cursor1.moveToNext())
                break;
            Long long1 = Long.valueOf(cursor1.getLong(cursor1.getColumnIndex("contact_id")));
            if(hashmap.get(long1) != null)
            {
                String s = cursor1.getString(cursor1.getColumnIndex("data1"));
                if(!hashset.contains(s))
                {
                    hashset.add(s);
                    ((FacebookPhonebookContact)hashmap.get(long1)).phones.add(s);
                }
            }
        } while(true);
        cursor1.close();
        return new ArrayList(hashmap.values());
    }
}
