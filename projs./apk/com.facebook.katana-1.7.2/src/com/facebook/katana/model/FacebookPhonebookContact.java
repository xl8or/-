// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookPhonebookContact.java

package com.facebook.katana.model;

import com.facebook.katana.util.Log;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.*;
import org.json.*;

public class FacebookPhonebookContact extends JMCachingDictDestination
{

    private FacebookPhonebookContact()
    {
        name = null;
        recordId = -1L;
        isFriend = false;
        userId = -1L;
        phone = null;
        phones = null;
        email = null;
        emails = null;
    }

    public FacebookPhonebookContact(String s, long l, List list, List list1)
    {
        name = s;
        recordId = l;
        emails = list;
        if(emails != null && emails.size() > 0)
            email = (String)emails.get(0);
        else
            email = null;
        phones = list1;
        if(phones != null && phones.size() > 0)
            phone = (String)phones.get(0);
        else
            phone = null;
        userId = -1L;
        isFriend = false;
    }

    protected FacebookPhonebookContact(String s, long l, boolean flag, long l1, String s1, 
            String s2)
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add(s2);
        ArrayList arraylist1 = new ArrayList();
        arraylist1.add(s1);
        name = s;
        recordId = l;
        isFriend = flag;
        userId = l1;
        phone = s1;
        phones = arraylist1;
        email = s2;
        emails = arraylist;
    }

    public static String jsonEncode(List list)
    {
        JSONArray jsonarray;
        Iterator iterator;
        jsonarray = new JSONArray();
        iterator = list.iterator();
_L6:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        FacebookPhonebookContact facebookphonebookcontact;
        JSONObject jsonobject;
        List list1;
        facebookphonebookcontact = (FacebookPhonebookContact)iterator.next();
        jsonobject = new JSONObject();
        if(facebookphonebookcontact.name != null)
            jsonobject.put("name", facebookphonebookcontact.name);
        list1 = facebookphonebookcontact.emails;
        if(list1 == null || list1.size() <= 0) goto _L4; else goto _L3
_L3:
        JSONArray jsonarray2;
        jsonarray2 = new JSONArray();
        for(Iterator iterator2 = list1.iterator(); iterator2.hasNext(); jsonarray2.put((String)iterator2.next()));
          goto _L5
        JSONException jsonexception;
        jsonexception;
        String s;
        Object aobj[] = new Object[1];
        aobj[0] = jsonexception.getMessage();
        Log.e("JMCachingDictDestination", String.format("How do we get a JSONException when *encoding* data? %s", aobj));
        s = "";
_L7:
        return s;
_L5:
        jsonobject.put("emails", jsonarray2);
_L4:
        List list2 = facebookphonebookcontact.phones;
        if(list2 != null && list2.size() > 0)
        {
            JSONArray jsonarray1 = new JSONArray();
            for(Iterator iterator1 = list2.iterator(); iterator1.hasNext(); jsonarray1.put((String)iterator1.next()));
            jsonobject.put("phones", jsonarray1);
        }
        if(facebookphonebookcontact.recordId != -1L)
            jsonobject.put("record_id", String.valueOf(facebookphonebookcontact.recordId));
        jsonarray.put(jsonobject);
          goto _L6
_L2:
        String s1 = jsonarray.toString();
        s = s1;
          goto _L7
    }

    public String getContactAddress()
    {
        String s = "";
        if(email == null) goto _L2; else goto _L1
_L1:
        s = email;
_L4:
        return s;
_L2:
        if(phone != null)
            s = phone;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public final String email;
    public List emails;
    public final boolean isFriend;
    public String name;
    public final String phone;
    public List phones;
    public final long recordId;
    public final long userId;
}
