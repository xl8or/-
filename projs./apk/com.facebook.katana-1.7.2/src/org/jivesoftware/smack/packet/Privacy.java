// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Privacy.java

package org.jivesoftware.smack.packet;

import java.util.*;

// Referenced classes of package org.jivesoftware.smack.packet:
//            IQ, PrivacyItem

public class Privacy extends IQ
{

    public Privacy()
    {
        declineActiveList = false;
        declineDefaultList = false;
        itemLists = new HashMap();
    }

    public boolean changeDefaultList(String s)
    {
        boolean flag;
        if(getItemLists().containsKey(s))
        {
            setDefaultName(s);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    public void deleteList(String s)
    {
        getItemLists().remove(s);
    }

    public void deletePrivacyList(String s)
    {
        getItemLists().remove(s);
        if(getDefaultName() != null && s.equals(getDefaultName()))
            setDefaultName(null);
    }

    public String getActiveName()
    {
        return activeName;
    }

    public List getActivePrivacyList()
    {
        List list;
        if(getActiveName() == null)
            list = null;
        else
            list = (List)getItemLists().get(getActiveName());
        return list;
    }

    public String getChildElementXML()
    {
        StringBuilder stringbuilder;
        stringbuilder = new StringBuilder();
        stringbuilder.append("<query xmlns=\"jabber:iq:privacy\">");
        Iterator iterator;
        java.util.Map.Entry entry;
        Iterator iterator1;
        if(isDeclineActiveList())
        {
            stringbuilder.append("<active/>");
            break MISSING_BLOCK_LABEL_29;
        } else
        {
            if(getActiveName() != null)
                stringbuilder.append("<active name=\"").append(getActiveName()).append("\"/>");
            continue;
        }
        do
        {
            if(isDeclineDefaultList())
                stringbuilder.append("<default/>");
            else
            if(getDefaultName() != null)
                stringbuilder.append("<default name=\"").append(getDefaultName()).append("\"/>");
            iterator = getItemLists().entrySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                entry = (java.util.Map.Entry)iterator.next();
                String s = (String)entry.getKey();
                List list = (List)entry.getValue();
                if(list.isEmpty())
                    stringbuilder.append("<list name=\"").append(s).append("\"/>");
                else
                    stringbuilder.append("<list name=\"").append(s).append("\">");
                for(iterator1 = list.iterator(); iterator1.hasNext(); stringbuilder.append(((PrivacyItem)iterator1.next()).toXML()));
                if(!list.isEmpty())
                    stringbuilder.append("</list>");
            } while(true);
            stringbuilder.append(getExtensionsXML());
            stringbuilder.append("</query>");
            return stringbuilder.toString();
        } while(true);
    }

    public String getDefaultName()
    {
        return defaultName;
    }

    public List getDefaultPrivacyList()
    {
        List list;
        if(getDefaultName() == null)
            list = null;
        else
            list = (List)getItemLists().get(getDefaultName());
        return list;
    }

    public PrivacyItem getItem(String s, int i)
    {
        Iterator iterator = getPrivacyList(s).iterator();
        PrivacyItem privacyitem = null;
        do
        {
            if(privacyitem != null || !iterator.hasNext())
                break;
            PrivacyItem privacyitem1 = (PrivacyItem)iterator.next();
            if(privacyitem1.getOrder() == i)
                privacyitem = privacyitem1;
        } while(true);
        return privacyitem;
    }

    public Map getItemLists()
    {
        return itemLists;
    }

    public List getPrivacyList(String s)
    {
        return (List)getItemLists().get(s);
    }

    public Set getPrivacyListNames()
    {
        return itemLists.keySet();
    }

    public boolean isDeclineActiveList()
    {
        return declineActiveList;
    }

    public boolean isDeclineDefaultList()
    {
        return declineDefaultList;
    }

    public void setActiveName(String s)
    {
        activeName = s;
    }

    public List setActivePrivacyList()
    {
        setActiveName(getDefaultName());
        return (List)getItemLists().get(getActiveName());
    }

    public void setDeclineActiveList(boolean flag)
    {
        declineActiveList = flag;
    }

    public void setDeclineDefaultList(boolean flag)
    {
        declineDefaultList = flag;
    }

    public void setDefaultName(String s)
    {
        defaultName = s;
    }

    public List setPrivacyList(String s, List list)
    {
        getItemLists().put(s, list);
        return list;
    }

    private String activeName;
    private boolean declineActiveList;
    private boolean declineDefaultList;
    private String defaultName;
    private Map itemLists;
}
