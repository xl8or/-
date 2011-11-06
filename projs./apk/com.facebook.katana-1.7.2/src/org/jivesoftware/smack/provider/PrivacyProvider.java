// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrivacyProvider.java

package org.jivesoftware.smack.provider;

import java.util.ArrayList;
import org.jivesoftware.smack.packet.*;
import org.xmlpull.v1.XmlPullParser;

// Referenced classes of package org.jivesoftware.smack.provider:
//            IQProvider

public class PrivacyProvider
    implements IQProvider
{

    public PrivacyProvider()
    {
    }

    public IQ parseIQ(XmlPullParser xmlpullparser)
        throws Exception
    {
        Privacy privacy = new Privacy();
        privacy.addExtension(new DefaultPacketExtension(xmlpullparser.getName(), xmlpullparser.getNamespace()));
        boolean flag = false;
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("active"))
                {
                    String s1 = xmlpullparser.getAttributeValue("", "name");
                    if(s1 == null)
                        privacy.setDeclineActiveList(true);
                    else
                        privacy.setActiveName(s1);
                } else
                if(xmlpullparser.getName().equals("default"))
                {
                    String s = xmlpullparser.getAttributeValue("", "name");
                    if(s == null)
                        privacy.setDeclineDefaultList(true);
                    else
                        privacy.setDefaultName(s);
                } else
                if(xmlpullparser.getName().equals("list"))
                    parseList(xmlpullparser, privacy);
            } else
            if(i == 3 && xmlpullparser.getName().equals("query"))
                flag = true;
        } while(true);
        return privacy;
    }

    public PrivacyItem parseItem(XmlPullParser xmlpullparser)
        throws Exception
    {
        String s = xmlpullparser.getAttributeValue("", "action");
        String s1 = xmlpullparser.getAttributeValue("", "order");
        String s2 = xmlpullparser.getAttributeValue("", "type");
        boolean flag;
        PrivacyItem privacyitem;
        boolean flag1;
        if("allow".equalsIgnoreCase(s))
            flag = true;
        else
        if("deny".equalsIgnoreCase(s))
            flag = false;
        else
            flag = true;
        privacyitem = new PrivacyItem(s2, flag, Integer.parseInt(s1));
        privacyitem.setValue(xmlpullparser.getAttributeValue("", "value"));
        flag1 = false;
        do
        {
            if(flag1)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("iq"))
                    privacyitem.setFilterIQ(true);
                if(xmlpullparser.getName().equals("message"))
                    privacyitem.setFilterMessage(true);
                if(xmlpullparser.getName().equals("presence-in"))
                    privacyitem.setFilterPresence_in(true);
                if(xmlpullparser.getName().equals("presence-out"))
                    privacyitem.setFilterPresence_out(true);
            } else
            if(i == 3 && xmlpullparser.getName().equals("item"))
                flag1 = true;
        } while(true);
        return privacyitem;
    }

    public void parseList(XmlPullParser xmlpullparser, Privacy privacy)
        throws Exception
    {
        boolean flag = false;
        String s = xmlpullparser.getAttributeValue("", "name");
        ArrayList arraylist = new ArrayList();
        do
        {
            if(flag)
                break;
            int i = xmlpullparser.next();
            if(i == 2)
            {
                if(xmlpullparser.getName().equals("item"))
                    arraylist.add(parseItem(xmlpullparser));
            } else
            if(i == 3 && xmlpullparser.getName().equals("list"))
                flag = true;
        } while(true);
        privacy.setPrivacyList(s, arraylist);
    }
}
