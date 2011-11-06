// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfigurationInspector.java

package org.acra;

import android.content.res.Configuration;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

// Referenced classes of package org.acra:
//            ACRA

public class ConfigurationInspector
{

    public ConfigurationInspector()
    {
    }

    private static String activeFlags(SparseArray sparsearray, int i)
    {
        StringBuilder stringbuilder = new StringBuilder();
        for(int j = 0; j < sparsearray.size(); j++)
        {
            int k = sparsearray.keyAt(j);
            if(!((String)sparsearray.get(k)).endsWith("_MASK"))
                continue;
            int l = i & k;
            if(l <= 0)
                continue;
            if(stringbuilder.length() > 0)
                stringbuilder.append('+');
            stringbuilder.append((String)sparsearray.get(l));
        }

        return stringbuilder.toString();
    }

    private static String getFieldValueName(Configuration configuration, Field field)
        throws IllegalArgumentException, IllegalAccessException
    {
        String s = field.getName();
        String s1;
        if(s.equals("mcc") || s.equals("mnc"))
            s1 = Integer.toString(field.getInt(configuration));
        else
        if(s.equals("uiMode"))
            s1 = activeFlags((SparseArray)mValueArrays.get("UI_MODE_"), field.getInt(configuration));
        else
        if(s.equals("screenLayout"))
        {
            s1 = activeFlags((SparseArray)mValueArrays.get("SCREENLAYOUT_"), field.getInt(configuration));
        } else
        {
            SparseArray sparsearray = (SparseArray)mValueArrays.get((new StringBuilder()).append(s.toUpperCase()).append('_').toString());
            if(sparsearray == null)
            {
                s1 = Integer.toString(field.getInt(configuration));
            } else
            {
                String s2 = (String)sparsearray.get(field.getInt(configuration));
                if(s2 == null)
                    s1 = Integer.toString(field.getInt(configuration));
                else
                    s1 = s2;
            }
        }
        return s1;
    }

    public static String toString(Configuration configuration)
    {
        StringBuilder stringbuilder;
        Field afield[];
        int i;
        int j;
        stringbuilder = new StringBuilder();
        afield = configuration.getClass().getFields();
        i = afield.length;
        j = 0;
_L3:
        Field field;
        if(j >= i)
            break MISSING_BLOCK_LABEL_163;
        field = afield[j];
label0:
        {
            if(Modifier.isStatic(field.getModifiers()))
                break MISSING_BLOCK_LABEL_168;
            stringbuilder.append(field.getName()).append('=');
            if(field.getType().equals(Integer.TYPE))
            {
                stringbuilder.append(getFieldValueName(configuration, field));
            } else
            {
                if(field.get(configuration) != null)
                    break label0;
                stringbuilder.append("null");
            }
        }
_L1:
        IllegalArgumentException illegalargumentexception;
        stringbuilder.append('\n');
        break MISSING_BLOCK_LABEL_168;
        try
        {
            stringbuilder.append(field.get(configuration).toString());
        }
        // Misplaced declaration of an exception variable
        catch(IllegalArgumentException illegalargumentexception)
        {
            Log.e(ACRA.LOG_TAG, "Error while inspecting device configuration: ", illegalargumentexception);
            break MISSING_BLOCK_LABEL_168;
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            Log.e(ACRA.LOG_TAG, "Error while inspecting device configuration: ", illegalaccessexception);
            break MISSING_BLOCK_LABEL_168;
        }
          goto _L1
        return stringbuilder.toString();
        j++;
        if(true) goto _L3; else goto _L2
_L2:
    }

    private static final String FIELD_MCC = "mcc";
    private static final String FIELD_MNC = "mnc";
    private static final String FIELD_SCREENLAYOUT = "screenLayout";
    private static final String FIELD_UIMODE = "uiMode";
    private static final String PREFIX_HARDKEYBOARDHIDDEN = "HARDKEYBOARDHIDDEN_";
    private static final String PREFIX_KEYBOARD = "KEYBOARD_";
    private static final String PREFIX_KEYBOARDHIDDEN = "KEYBOARDHIDDEN_";
    private static final String PREFIX_NAVIGATION = "NAVIGATION_";
    private static final String PREFIX_NAVIGATIONHIDDEN = "NAVIGATIONHIDDEN_";
    private static final String PREFIX_ORIENTATION = "ORIENTATION_";
    private static final String PREFIX_SCREENLAYOUT = "SCREENLAYOUT_";
    private static final String PREFIX_TOUCHSCREEN = "TOUCHSCREEN_";
    private static final String PREFIX_UI_MODE = "UI_MODE_";
    private static final String SUFFIX_MASK = "_MASK";
    private static SparseArray mHardKeyboardHiddenValues;
    private static SparseArray mKeyboardHiddenValues;
    private static SparseArray mKeyboardValues;
    private static SparseArray mNavigationHiddenValues;
    private static SparseArray mNavigationValues;
    private static SparseArray mOrientationValues;
    private static SparseArray mScreenLayoutValues;
    private static SparseArray mTouchScreenValues;
    private static SparseArray mUiModeValues;
    private static final HashMap mValueArrays;

    static 
    {
        Field afield[];
        int i;
        int j;
        mHardKeyboardHiddenValues = new SparseArray();
        mKeyboardValues = new SparseArray();
        mKeyboardHiddenValues = new SparseArray();
        mNavigationValues = new SparseArray();
        mNavigationHiddenValues = new SparseArray();
        mOrientationValues = new SparseArray();
        mScreenLayoutValues = new SparseArray();
        mTouchScreenValues = new SparseArray();
        mUiModeValues = new SparseArray();
        mValueArrays = new HashMap();
        mValueArrays.put("HARDKEYBOARDHIDDEN_", mHardKeyboardHiddenValues);
        mValueArrays.put("KEYBOARD_", mKeyboardValues);
        mValueArrays.put("KEYBOARDHIDDEN_", mKeyboardHiddenValues);
        mValueArrays.put("NAVIGATION_", mNavigationValues);
        mValueArrays.put("NAVIGATIONHIDDEN_", mNavigationHiddenValues);
        mValueArrays.put("ORIENTATION_", mOrientationValues);
        mValueArrays.put("SCREENLAYOUT_", mScreenLayoutValues);
        mValueArrays.put("TOUCHSCREEN_", mTouchScreenValues);
        mValueArrays.put("UI_MODE_", mUiModeValues);
        afield = android/content/res/Configuration.getFields();
        i = afield.length;
        j = 0;
_L2:
        Field field;
        String s;
        if(j >= i)
            break MISSING_BLOCK_LABEL_541;
        field = afield[j];
        if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()))
            break MISSING_BLOCK_LABEL_542;
        s = field.getName();
        IllegalArgumentException illegalargumentexception;
        if(s.startsWith("HARDKEYBOARDHIDDEN_"))
        {
            mHardKeyboardHiddenValues.put(field.getInt(null), s);
            break MISSING_BLOCK_LABEL_542;
        }
        if(s.startsWith("KEYBOARD_"))
        {
            mKeyboardValues.put(field.getInt(null), s);
            break MISSING_BLOCK_LABEL_542;
        }
        IllegalAccessException illegalaccessexception;
        if(s.startsWith("KEYBOARDHIDDEN_"))
        {
            mKeyboardHiddenValues.put(field.getInt(null), s);
            break MISSING_BLOCK_LABEL_542;
        }
        try
        {
            if(s.startsWith("NAVIGATION_"))
                mNavigationValues.put(field.getInt(null), s);
            else
            if(s.startsWith("NAVIGATIONHIDDEN_"))
                mNavigationHiddenValues.put(field.getInt(null), s);
            else
            if(s.startsWith("ORIENTATION_"))
                mOrientationValues.put(field.getInt(null), s);
            else
            if(s.startsWith("SCREENLAYOUT_"))
                mScreenLayoutValues.put(field.getInt(null), s);
            else
            if(s.startsWith("TOUCHSCREEN_"))
                mTouchScreenValues.put(field.getInt(null), s);
            else
            if(s.startsWith("UI_MODE_"))
                mUiModeValues.put(field.getInt(null), s);
        }
        // Misplaced declaration of an exception variable
        catch(IllegalArgumentException illegalargumentexception)
        {
            Log.w(ACRA.LOG_TAG, "Error while inspecting device configuration: ", illegalargumentexception);
        }
        // Misplaced declaration of an exception variable
        catch(IllegalAccessException illegalaccessexception)
        {
            Log.w(ACRA.LOG_TAG, "Error while inspecting device configuration: ", illegalaccessexception);
        }
        break MISSING_BLOCK_LABEL_542;
        return;
        j++;
        if(true) goto _L2; else goto _L1
_L1:
    }
}
