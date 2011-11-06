// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlatformStorage.java

package com.facebook.katana.platform;

import android.content.*;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import com.facebook.katana.model.*;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.PlatformUtils;
import java.util.*;

public final class PlatformStorage
{
    private static interface ProfileQuery
    {

        public static final int COLUMN_ID;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[1];
            as[0] = "_id";
        }
    }

    private static interface UserIdQuery
    {

        public static final int COLUMN_ID = 0;
        public static final String PROJECTION[] = as;
        public static final String SELECTION = "account_type='com.facebook.auth.login' AND sourceid=?";

        
        {
            String as[] = new String[1];
            as[0] = "_id";
        }
    }

    public static interface DataQuery
    {

        public static final int COLUMN_DATA1 = 2;
        public static final int COLUMN_DATA2 = 3;
        public static final int COLUMN_DATA3 = 4;
        public static final int COLUMN_EMAIL_ADDRESS = 2;
        public static final int COLUMN_EMAIL_TYPE = 3;
        public static final int COLUMN_FAMILY_NAME = 4;
        public static final int COLUMN_GIVEN_NAME = 3;
        public static final int COLUMN_ID = 0;
        public static final int COLUMN_MIMETYPE = 1;
        public static final int COLUMN_PHONE_NUMBER = 2;
        public static final int COLUMN_PHONE_TYPE = 3;
        public static final String PROJECTION[] = as;
        public static final String SELECTION = "raw_contact_id=?";

        
        {
            String as[] = new String[5];
            as[0] = "_id";
            as[1] = "mimetype";
            as[2] = "data1";
            as[3] = "data2";
            as[4] = "data3";
        }
    }

    private static interface SyncHashQuery
    {

        public static final int COLUMN_ID = 0;
        public static final int COLUMN_SOURCE_ID = 1;
        public static final int COLUMN_SYNC1 = 2;
        public static final String PROJECTION[] = as;
        public static final String SELECTION = "account_type='com.facebook.auth.login' AND account_name=?";

        
        {
            String as[] = new String[3];
            as[0] = "_id";
            as[1] = "sourceid";
            as[2] = "sync1";
        }
    }

    private static final class ExistingContact
    {

        public long localId;
        public long syncHash;

        public ExistingContact(long l, long l1)
        {
            localId = l;
            syncHash = l1;
        }
    }


    public PlatformStorage()
    {
    }

    static Uri addCallerIsSyncAdapterParameter(Uri uri)
    {
        return uri.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").build();
    }

    private static void addInsertContactOperations(Context context, ArrayList arraylist, ContentValues contentvalues, String s, String s1, String s2, String s3, String s4, 
            String s5, long l, long l1)
    {
        int i = arraylist.size();
        contentvalues.clear();
        contentvalues.put("is_restricted", Integer.valueOf(1));
        contentvalues.put("sourceid", Long.valueOf(l));
        contentvalues.put("sync1", Long.valueOf(l1));
        contentvalues.put("account_type", "com.facebook.auth.login");
        contentvalues.put("account_name", s);
        arraylist.add(newInsertCpo(android.provider.ContactsContract.RawContacts.CONTENT_URI, true).withValues(contentvalues).build());
        contentvalues.clear();
        contentvalues.put("data2", s1);
        contentvalues.put("data3", s2);
        contentvalues.put("mimetype", "vnd.android.cursor.item/name");
        android.content.ContentProviderOperation.Builder builder = newInsertCpo(android.provider.ContactsContract.Data.CONTENT_URI, false).withValues(contentvalues);
        builder.withValueBackReference("raw_contact_id", i);
        arraylist.add(builder.build());
        if(!TextUtils.isEmpty(s3))
        {
            contentvalues.clear();
            contentvalues.put("data1", s3);
            contentvalues.put("data2", Integer.valueOf(2));
            contentvalues.put("mimetype", "vnd.android.cursor.item/phone_v2");
            android.content.ContentProviderOperation.Builder builder4 = newInsertCpo(android.provider.ContactsContract.Data.CONTENT_URI, false).withValues(contentvalues);
            builder4.withValueBackReference("raw_contact_id", i);
            arraylist.add(builder4.build());
        }
        if(!TextUtils.isEmpty(s4))
        {
            contentvalues.clear();
            contentvalues.put("data1", s4);
            contentvalues.put("data2", Integer.valueOf(7));
            contentvalues.put("mimetype", "vnd.android.cursor.item/phone_v2");
            android.content.ContentProviderOperation.Builder builder3 = newInsertCpo(android.provider.ContactsContract.Data.CONTENT_URI, false).withValues(contentvalues);
            builder3.withValueBackReference("raw_contact_id", i);
            arraylist.add(builder3.build());
        }
        if(!TextUtils.isEmpty(s5))
        {
            contentvalues.clear();
            contentvalues.put("data1", s5);
            contentvalues.put("data2", Integer.valueOf(3));
            contentvalues.put("mimetype", "vnd.android.cursor.item/email_v2");
            android.content.ContentProviderOperation.Builder builder2 = newInsertCpo(android.provider.ContactsContract.Data.CONTENT_URI, false).withValues(contentvalues);
            builder2.withValueBackReference("raw_contact_id", i);
            arraylist.add(builder2.build());
        }
        contentvalues.clear();
        contentvalues.put("data1", Long.valueOf(l));
        contentvalues.put("data2", context.getString(0x7f0a0024));
        contentvalues.put("data3", context.getString(0x7f0a0177));
        contentvalues.put("mimetype", "vnd.android.cursor.item/vnd.facebook.profile");
        android.content.ContentProviderOperation.Builder builder1 = ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI).withValues(contentvalues);
        builder1.withValueBackReference("raw_contact_id", i);
        arraylist.add(builder1.build());
    }

    private static void addUpdateContactOperations(Context context, ContentResolver contentresolver, ArrayList arraylist, ContentValues contentvalues, String s, String s1, String s2, String s3, 
            String s4, String s5, Long long1, Long long2, long l)
    {
        Cursor cursor;
        boolean flag;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        Uri uri2;
        android.content.ContentProviderOperation.Builder builder1;
        boolean flag4;
        Uri uri = ContentUris.withAppendedId(android.provider.ContactsContract.RawContacts.CONTENT_URI, long2.longValue());
        contentvalues.clear();
        contentvalues.put("sync1", Long.valueOf(l));
        android.content.ContentProviderOperation.Builder builder = newUpdateCpo(uri, true).withValues(contentvalues);
        arraylist.add(builder.build());
        Uri uri1 = android.provider.ContactsContract.Data.CONTENT_URI;
        String as[] = DataQuery.PROJECTION;
        String as1[] = new String[1];
        as1[0] = String.valueOf(long2);
        cursor = contentresolver.query(uri1, as, "raw_contact_id=?", as1, null);
        flag = true;
        flag1 = false;
        flag2 = false;
        flag3 = false;
        uri2 = uri;
        builder1 = builder;
        flag4 = false;
_L9:
        if(!cursor.moveToNext()) goto _L2; else goto _L1
_L1:
        String s6;
        Uri uri3;
        long l1 = cursor.getLong(0);
        s6 = cursor.getString(1);
        uri3 = ContentUris.withAppendedId(android.provider.ContactsContract.Data.CONTENT_URI, l1);
        if(!"vnd.android.cursor.item/name".equals(s6)) goto _L4; else goto _L3
_L3:
        String s8;
        String s9;
        s8 = cursor.getString(3);
        s9 = cursor.getString(4);
        if(s8 != null || s1 == null) goto _L6; else goto _L5
_L5:
        boolean flag12 = true;
_L10:
        if(!flag12) goto _L8; else goto _L7
_L7:
        contentvalues.clear();
        contentvalues.put("data2", s1);
        contentvalues.put("data3", s2);
        android.content.ContentProviderOperation.Builder builder3 = newUpdateCpo(uri3, flag).withValues(contentvalues);
        boolean flag6;
        builder1 = builder3;
        flag6 = false;
        arraylist.add(builder1.build());
_L36:
        boolean flag7;
        boolean flag8;
        boolean flag9;
        boolean flag10;
        flag7 = flag4;
        flag10 = flag1;
        flag8 = flag2;
        flag9 = flag3;
_L30:
        flag4 = flag7;
        flag1 = flag10;
        flag2 = flag8;
        flag3 = flag9;
        flag = flag6;
        uri2 = uri3;
          goto _L9
_L6:
label0:
        {
            if(s9 != null || s2 == null)
                break label0;
            flag12 = true;
        }
          goto _L10
label1:
        {
            if(s8.equals(s1))
                break label1;
            flag12 = true;
        }
          goto _L10
        if(s9.equals(s2)) goto _L12; else goto _L11
_L11:
        flag12 = true;
          goto _L10
_L4:
        if(!"vnd.android.cursor.item/phone_v2".equals(s6)) goto _L14; else goto _L13
_L13:
        int i;
        String s7;
        i = cursor.getInt(3);
        s7 = cursor.getString(2);
        if(i != 2) goto _L16; else goto _L15
_L15:
        ContentProviderOperation contentprovideroperation1 = getPhoneOperation(contentvalues, s7, s3, uri3, flag);
        if(contentprovideroperation1 == null) goto _L18; else goto _L17
_L17:
        arraylist.add(contentprovideroperation1);
        flag6 = false;
          goto _L19
_L16:
        if(i != 7) goto _L21; else goto _L20
_L20:
        ContentProviderOperation contentprovideroperation = getPhoneOperation(contentvalues, s7, s4, uri3, flag);
        if(contentprovideroperation == null) goto _L23; else goto _L22
_L22:
        arraylist.add(contentprovideroperation);
        flag6 = false;
_L35:
        boolean flag11;
        flag11 = true;
        flag9 = flag3;
        break MISSING_BLOCK_LABEL_1174;
_L21:
        Log.e("ContactsStorage", (new StringBuilder()).append("Ignoring unkown row type: ").append(i).toString());
        flag6 = flag;
        flag9 = flag3;
        flag11 = flag2;
        break MISSING_BLOCK_LABEL_1174;
_L14:
        if(!"vnd.android.cursor.item/email_v2".equals(s6)) goto _L25; else goto _L24
_L24:
        if(TextUtils.isEmpty(s5)) goto _L27; else goto _L26
_L26:
        if(s5.equals(cursor.getString(2))) goto _L29; else goto _L28
_L28:
        contentvalues.clear();
        contentvalues.put("data1", s5);
        android.content.ContentProviderOperation.Builder builder2 = newUpdateCpo(uri3, flag).withValues(contentvalues);
        builder1 = builder2;
        flag6 = false;
        arraylist.add(builder1.build());
_L31:
        flag7 = flag4;
        flag10 = true;
        flag8 = flag2;
        flag9 = flag3;
          goto _L30
_L27:
        arraylist.add(newDeleteCpo(uri3, flag).build());
        flag6 = false;
          goto _L31
_L25:
        if(!"vnd.android.cursor.item/vnd.facebook.profile".equals(s6)) goto _L33; else goto _L32
_L32:
        Long long3 = Long.valueOf(cursor.getLong(2));
        if(long3 != null && long3.equals(long1))
        {
            contentvalues.clear();
            contentvalues.put("data1", long1);
            builder1 = ContentProviderOperation.newUpdate(uri3).withValues(contentvalues);
            arraylist.add(builder1.build());
        }
        flag7 = true;
        flag8 = flag2;
        flag9 = flag3;
        flag6 = flag;
        flag10 = flag1;
          goto _L30
_L2:
        cursor.close();
        Exception exception;
        Exception exception1;
        boolean flag5;
        Exception exception2;
        Exception exception3;
        Exception exception4;
        if(!flag3 && !TextUtils.isEmpty(s3))
        {
            contentvalues.clear();
            contentvalues.put("data1", s3);
            contentvalues.put("data2", Integer.valueOf(2));
            contentvalues.put("mimetype", "vnd.android.cursor.item/phone_v2");
            contentvalues.put("raw_contact_id", long2);
            builder1 = newInsertCpo(android.provider.ContactsContract.Data.CONTENT_URI, flag).withValues(contentvalues);
            flag5 = false;
            arraylist.add(builder1.build());
        } else
        {
            flag5 = flag;
        }
        if(!flag2 && !TextUtils.isEmpty(s4))
        {
            contentvalues.clear();
            contentvalues.put("data1", s4);
            contentvalues.put("data2", Integer.valueOf(7));
            contentvalues.put("mimetype", "vnd.android.cursor.item/phone_v2");
            contentvalues.put("raw_contact_id", long2);
            builder1 = newInsertCpo(android.provider.ContactsContract.Data.CONTENT_URI, flag5).withValues(contentvalues);
            flag5 = false;
            arraylist.add(builder1.build());
        }
        if(!flag1 && !TextUtils.isEmpty(s5))
        {
            contentvalues.clear();
            contentvalues.put("data1", s5);
            contentvalues.put("data2", Integer.valueOf(3));
            contentvalues.put("mimetype", "vnd.android.cursor.item/email_v2");
            contentvalues.put("raw_contact_id", long2);
            builder1 = newInsertCpo(android.provider.ContactsContract.Data.CONTENT_URI, flag5).withValues(contentvalues);
            flag5 = false;
            arraylist.add(builder1.build());
        }
        if(!flag4)
        {
            contentvalues.clear();
            contentvalues.put("data1", long1);
            contentvalues.put("data2", context.getString(0x7f0a0024));
            contentvalues.put("data3", context.getString(0x7f0a0177));
            contentvalues.put("mimetype", "vnd.android.cursor.item/vnd.facebook.profile");
            contentvalues.put("raw_contact_id", long2);
            arraylist.add(newInsertCpo(android.provider.ContactsContract.Data.CONTENT_URI, flag5).withValues(contentvalues).build());
        } else
        {
            builder1;
            flag5;
        }
        return;
        exception;
        exception1 = exception;
        uri2;
        builder1;
        flag;
_L34:
        cursor.close();
        throw exception1;
        exception2;
        exception1 = exception2;
        builder1;
        flag;
        continue; /* Loop/switch isn't completed */
        exception3;
        exception1 = exception3;
        builder1;
        flag;
        continue; /* Loop/switch isn't completed */
        exception4;
        exception1 = exception4;
        builder1;
        flag6;
        if(true) goto _L34; else goto _L33
_L33:
        flag6 = flag;
        flag7 = flag4;
        flag8 = flag2;
        flag9 = flag3;
        flag10 = flag1;
          goto _L30
_L29:
        flag6 = flag;
          goto _L31
_L23:
        flag6 = flag;
          goto _L35
_L18:
        flag6 = flag;
          goto _L19
_L8:
        flag6 = flag;
          goto _L36
_L12:
        flag12 = false;
          goto _L10
_L19:
        flag9 = true;
        flag11 = flag2;
        flag7 = flag4;
        flag8 = flag11;
        flag10 = flag1;
          goto _L30
    }

    private static void applyOperationsToContacts(ContentResolver contentresolver, ArrayList arraylist)
    {
        if(arraylist.size() != 0)
            try
            {
                contentresolver.applyBatch("com.android.contacts", arraylist);
            }
            catch(OperationApplicationException operationapplicationexception)
            {
                Log.e("ContactsStorage", "storing contact data failed", operationapplicationexception);
            }
            catch(RemoteException remoteexception)
            {
                Log.e("ContactsStorage", "storing contact data failed", remoteexception);
            }
    }

    public static boolean checkUnrestrictedPackages(Context context)
        throws android.content.pm.PackageManager.NameNotFoundException
    {
        ProviderInfo providerinfo = context.getPackageManager().resolveContentProvider("com.android.contacts", 0);
        if(providerinfo != null) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        Resources resources = context.createPackageContext(providerinfo.packageName, 0).getResources();
        if(resources == null)
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        int i = resources.getIdentifier("unrestricted_packages", "array", providerinfo.packageName);
        if(i == 0)
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        String as[] = resources.getStringArray(i);
        if(as == null)
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        String s = context.getPackageName();
        int j = 0;
        do
        {
            if(j >= as.length)
                break;
            if(as[j].equals(s))
            {
                flag = true;
                continue; /* Loop/switch isn't completed */
            }
            j++;
        } while(true);
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static void fixContactsHelper(Context context)
    {
        if(!$assertionsDisabled && (!PlatformUtils.isEclairOrLater() || PlatformUtils.platformStorageSupported(context)))
        {
            throw new AssertionError();
        } else
        {
            Uri uri = android.provider.ContactsContract.RawContacts.CONTENT_URI.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").build();
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("account_type");
            stringbuilder.append("='");
            stringbuilder.append("com.facebook.auth.login");
            stringbuilder.append("'");
            context.getContentResolver().delete(uri, stringbuilder.toString(), null);
            return;
        }
    }

    private static ContentProviderOperation getPhoneOperation(ContentValues contentvalues, String s, String s1, Uri uri, boolean flag)
    {
        if(TextUtils.isEmpty(s1)) goto _L2; else goto _L1
_L1:
        if(!s1.equals(s)) goto _L4; else goto _L3
_L3:
        ContentProviderOperation contentprovideroperation = null;
_L5:
        return contentprovideroperation;
_L4:
        android.content.ContentProviderOperation.Builder builder;
        contentvalues.clear();
        contentvalues.put("data1", s1);
        builder = newUpdateCpo(uri, flag).withValues(contentvalues);
_L6:
        contentprovideroperation = builder.build();
        if(true) goto _L5; else goto _L2
_L2:
        builder = newDeleteCpo(uri, flag);
          goto _L6
    }

    public static void insertStatuses(Context context, String s, List list)
    {
        ContentResolver contentresolver = context.getContentResolver();
        ContentValues contentvalues = new ContentValues();
        ArrayList arraylist = new ArrayList();
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookStatus facebookstatus = (FacebookStatus)iterator.next();
            long l = lookupProfile(contentresolver, facebookstatus.getUser().mUserId);
            contentvalues.clear();
            if(l > 0L)
            {
                contentvalues.put("presence_data_id", Long.valueOf(l));
                contentvalues.put("status", facebookstatus.getMessage());
                contentvalues.put("status_ts", Long.valueOf(1000L * facebookstatus.getTime()));
                contentvalues.put("protocol", Integer.valueOf(-1));
                contentvalues.put("custom_protocol", "Facebook");
                contentvalues.put("im_account", s);
                contentvalues.put("im_handle", Long.valueOf(facebookstatus.getUser().mUserId));
                contentvalues.put("status_res_package", context.getPackageName());
                contentvalues.put("status_icon", Integer.valueOf(0x7f02009e));
                contentvalues.put("status_label", Integer.valueOf(0x7f0a001e));
                arraylist.add(newInsertCpo(android.provider.ContactsContract.StatusUpdates.CONTENT_URI, true).withValues(contentvalues).build());
                if(arraylist.size() >= 50)
                {
                    applyOperationsToContacts(contentresolver, arraylist);
                    arraylist.clear();
                }
            }
        } while(true);
        applyOperationsToContacts(contentresolver, arraylist);
    }

    private static long lookupProfile(ContentResolver contentresolver, long l)
    {
        long l1;
        Cursor cursor;
        l1 = 0L;
        Uri uri = android.provider.ContactsContract.Data.CONTENT_URI;
        String as[] = ProfileQuery.PROJECTION;
        String as1[] = new String[1];
        as1[0] = String.valueOf(l);
        cursor = contentresolver.query(uri, as, "mimetype='vnd.android.cursor.item/vnd.facebook.profile' AND data1=?", as1, null);
        if(cursor == null)
            break MISSING_BLOCK_LABEL_72;
        long l2;
        if(!cursor.moveToFirst())
            break MISSING_BLOCK_LABEL_72;
        l2 = cursor.getLong(0);
        l1 = l2;
        if(cursor != null)
            cursor.close();
        return l1;
        Exception exception;
        exception;
        if(cursor != null)
            cursor.close();
        throw exception;
    }

    private static long lookupRawContact(ContentResolver contentresolver, long l)
    {
        long l1;
        Cursor cursor;
        l1 = 0L;
        Uri uri = android.provider.ContactsContract.RawContacts.CONTENT_URI;
        String as[] = UserIdQuery.PROJECTION;
        String as1[] = new String[1];
        as1[0] = String.valueOf(l);
        cursor = contentresolver.query(uri, as, "account_type='com.facebook.auth.login' AND sourceid=?", as1, null);
        long l2;
        if(!cursor.moveToFirst())
            break MISSING_BLOCK_LABEL_67;
        l2 = cursor.getLong(0);
        l1 = l2;
        if(cursor != null)
            cursor.close();
        return l1;
        Exception exception;
        exception;
        if(cursor != null)
            cursor.close();
        throw exception;
    }

    static android.content.ContentProviderOperation.Builder newDeleteCpo(Uri uri, boolean flag)
    {
        return ContentProviderOperation.newDelete(addCallerIsSyncAdapterParameter(uri)).withYieldAllowed(flag);
    }

    static android.content.ContentProviderOperation.Builder newInsertCpo(Uri uri, boolean flag)
    {
        return ContentProviderOperation.newInsert(addCallerIsSyncAdapterParameter(uri)).withYieldAllowed(flag);
    }

    static android.content.ContentProviderOperation.Builder newUpdateCpo(Uri uri, boolean flag)
    {
        return ContentProviderOperation.newUpdate(addCallerIsSyncAdapterParameter(uri)).withYieldAllowed(flag);
    }

    /**
     * @deprecated Method syncContacts is deprecated
     */

    public static void syncContacts(Context context, String s, List list, Map map)
    {
        com/facebook/katana/platform/PlatformStorage;
        JVM INSTR monitorenter ;
        ContentResolver contentresolver;
        HashMap hashmap;
        Cursor cursor;
        contentresolver = context.getContentResolver();
        hashmap = new HashMap();
        Uri uri = android.provider.ContactsContract.RawContacts.CONTENT_URI;
        String as[] = SyncHashQuery.PROJECTION;
        String as1[] = new String[1];
        as1[0] = s;
        cursor = contentresolver.query(uri, as, "account_type='com.facebook.auth.login' AND account_name=?", as1, null);
        if(cursor != null) goto _L2; else goto _L1
_L1:
        com/facebook/katana/platform/PlatformStorage;
        JVM INSTR monitorexit ;
        return;
_L2:
        for(; cursor.moveToNext(); hashmap.put(Long.valueOf(cursor.getLong(1)), new ExistingContact(cursor.getLong(0), cursor.getLong(2))));
        break MISSING_BLOCK_LABEL_138;
        Exception exception1;
        exception1;
        cursor.close();
        throw exception1;
        Exception exception;
        exception;
        com/facebook/katana/platform/PlatformStorage;
        JVM INSTR monitorexit ;
        throw exception;
        cursor.close();
        ArrayList arraylist = new ArrayList();
        HashMap hashmap1 = new HashMap();
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookFriendInfo facebookfriendinfo2 = (FacebookFriendInfo)iterator.next();
            long l = facebookfriendinfo2.mUserId;
            ExistingContact existingcontact1 = (ExistingContact)hashmap.get(Long.valueOf(l));
            if(existingcontact1 != null)
            {
                long l1 = facebookfriendinfo2.computeHash();
                if(Long.valueOf(existingcontact1.syncHash).longValue() != l1)
                {
                    hashmap1.put(Long.valueOf(existingcontact1.localId), facebookfriendinfo2);
                    if(facebookfriendinfo2.mImageUrl != null)
                        map.put(Long.valueOf(l), facebookfriendinfo2.mImageUrl);
                }
                hashmap.remove(Long.valueOf(l));
            } else
            {
                arraylist.add(facebookfriendinfo2);
                if(facebookfriendinfo2.mImageUrl != null)
                    map.put(Long.valueOf(l), facebookfriendinfo2.mImageUrl);
            }
        } while(true);
        if(hashmap.size() > 0)
        {
            ArrayList arraylist1 = new ArrayList();
            Iterator iterator1 = hashmap.values().iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                ExistingContact existingcontact = (ExistingContact)iterator1.next();
                arraylist1.add(newDeleteCpo(ContentUris.withAppendedId(android.provider.ContactsContract.RawContacts.CONTENT_URI, existingcontact.localId), true).build());
                if(arraylist1.size() >= 50)
                {
                    applyOperationsToContacts(context.getContentResolver(), arraylist1);
                    arraylist1.clear();
                }
            } while(true);
            applyOperationsToContacts(context.getContentResolver(), arraylist1);
        }
        if(arraylist.size() > 0)
        {
            ArrayList arraylist2 = new ArrayList();
            ContentValues contentvalues = new ContentValues();
            Iterator iterator2 = arraylist.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                FacebookFriendInfo facebookfriendinfo1 = (FacebookFriendInfo)iterator2.next();
                addInsertContactOperations(context, arraylist2, contentvalues, s, facebookfriendinfo1.mFirstName, facebookfriendinfo1.mLastName, facebookfriendinfo1.mCellPhone, facebookfriendinfo1.mOtherPhone, facebookfriendinfo1.mContactEmail, facebookfriendinfo1.mUserId, facebookfriendinfo1.computeHash());
                if(arraylist2.size() >= 50)
                {
                    applyOperationsToContacts(context.getContentResolver(), arraylist2);
                    arraylist2.clear();
                }
            } while(true);
            applyOperationsToContacts(context.getContentResolver(), arraylist2);
        }
        if(hashmap1.size() > 0)
        {
            ArrayList arraylist3 = new ArrayList();
            ContentValues contentvalues1 = new ContentValues();
            Iterator iterator3 = hashmap1.keySet().iterator();
            do
            {
                if(!iterator3.hasNext())
                    break;
                Long long1 = (Long)iterator3.next();
                FacebookFriendInfo facebookfriendinfo = (FacebookFriendInfo)hashmap1.get(long1);
                addUpdateContactOperations(context, contentresolver, arraylist3, contentvalues1, s, facebookfriendinfo.mFirstName, facebookfriendinfo.mLastName, facebookfriendinfo.mCellPhone, facebookfriendinfo.mOtherPhone, facebookfriendinfo.mContactEmail, Long.valueOf(facebookfriendinfo.mUserId), long1, facebookfriendinfo.computeHash());
                if(arraylist3.size() >= 50)
                {
                    applyOperationsToContacts(context.getContentResolver(), arraylist3);
                    arraylist3.clear();
                }
            } while(true);
            applyOperationsToContacts(contentresolver, arraylist3);
        }
          goto _L1
    }

    public static void updateContactPhoto(Context context, long l, byte abyte0[])
    {
        ContentResolver contentresolver = context.getContentResolver();
        long l1 = lookupRawContact(contentresolver, l);
        if(l1 > 0L)
        {
            ContentValues contentvalues = new ContentValues();
            contentvalues.put("mimetype", "vnd.android.cursor.item/photo");
            contentvalues.put("raw_contact_id", Long.valueOf(l1));
            contentvalues.put("data15", abyte0);
            Uri uri = addCallerIsSyncAdapterParameter(android.provider.ContactsContract.Data.CONTENT_URI);
            if(contentresolver.update(uri, contentvalues, (new StringBuilder()).append("raw_contact_id=").append(l1).append(" AND ").append("mimetype").append("='").append("vnd.android.cursor.item/photo").append("'").toString(), null) == 0)
                contentresolver.insert(uri, contentvalues);
        }
    }

    static final boolean $assertionsDisabled = false;
    public static final String CUSTOM_IM_PROTOCOL = "Facebook";
    public static final String DATA_DETAIL = "data3";
    public static final String DATA_PID = "data1";
    public static final String DATA_SUMMARY = "data2";
    public static final String MIME_PROFILE = "vnd.android.cursor.item/vnd.facebook.profile";
    private static final String TAG = "ContactsStorage";

    static 
    {
        boolean flag;
        if(!com/facebook/katana/platform/PlatformStorage.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
