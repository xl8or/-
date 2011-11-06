// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Header.java

package org.apache.http.entity.mime;

import java.util.*;

// Referenced classes of package org.apache.http.entity.mime:
//            MinimalField

public class Header
    implements Iterable
{

    public Header()
    {
    }

    public void addField(MinimalField minimalfield)
    {
        if(minimalfield != null)
        {
            String s = minimalfield.getName().toLowerCase(Locale.US);
            Object obj = (List)fieldMap.get(s);
            if(obj == null)
            {
                obj = new LinkedList();
                fieldMap.put(s, obj);
            }
            ((List) (obj)).add(minimalfield);
            fields.add(minimalfield);
        }
    }

    public MinimalField getField(String s)
    {
        MinimalField minimalfield;
        if(s == null)
        {
            minimalfield = null;
        } else
        {
            String s1 = s.toLowerCase(Locale.US);
            List list = (List)fieldMap.get(s1);
            if(list != null && !list.isEmpty())
                minimalfield = (MinimalField)list.get(0);
            else
                minimalfield = null;
        }
        return minimalfield;
    }

    public List getFields()
    {
        return new ArrayList(fields);
    }

    public List getFields(String s)
    {
        Object obj;
        if(s == null)
        {
            obj = null;
        } else
        {
            String s1 = s.toLowerCase(Locale.US);
            List list = (List)fieldMap.get(s1);
            if(list == null || list.isEmpty())
                obj = Collections.emptyList();
            else
                obj = new ArrayList(list);
        }
        return ((List) (obj));
    }

    public Iterator iterator()
    {
        return Collections.unmodifiableList(fields).iterator();
    }

    public int removeFields(String s)
    {
        int i;
        if(s == null)
        {
            i = 0;
        } else
        {
            String s1 = s.toLowerCase(Locale.US);
            List list = (List)fieldMap.remove(s1);
            if(list == null || list.isEmpty())
            {
                i = 0;
            } else
            {
                fields.removeAll(list);
                i = list.size();
            }
        }
        return i;
    }

    public void setField(MinimalField minimalfield)
    {
        if(minimalfield != null)
        {
            String s = minimalfield.getName().toLowerCase(Locale.US);
            List list = (List)fieldMap.get(s);
            if(list == null || list.isEmpty())
            {
                addField(minimalfield);
            } else
            {
                list.clear();
                list.add(minimalfield);
                int i = -1;
                int j = 0;
                for(Iterator iterator1 = fields.iterator(); iterator1.hasNext(); j++)
                {
                    if(!((MinimalField)iterator1.next()).getName().equalsIgnoreCase(minimalfield.getName()))
                        continue;
                    iterator1.remove();
                    if(i == -1)
                        i = j;
                }

                fields.add(i, minimalfield);
            }
        }
    }

    public String toString()
    {
        return fields.toString();
    }

    private final Map fieldMap = new HashMap();
    private final List fields = new LinkedList();
}
