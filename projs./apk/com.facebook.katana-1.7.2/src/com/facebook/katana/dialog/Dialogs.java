// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Dialogs.java

package com.facebook.katana.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.*;
import android.widget.TextView;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.service.method.FriendsAddFriend;

public class Dialogs
{
    public static interface AddFriendListener
    {

        public abstract void onAddFriendStart(String s);
    }


    public Dialogs()
    {
    }

    public static Dialog addFriend(Context context, long l, AddFriendListener addfriendlistener)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(0x7f030046, null);
        builder.setView(view);
        builder.setTitle(0x7f0a0146);
        final TextView textInput = (TextView)view.findViewById(0x7f0e00d1);
        final Runnable friendRequestSender = new Runnable() {

            public void run()
            {
                String s = textInput.getText().toString().trim();
                if(s.length() == 0)
                    s = null;
                String s1 = FriendsAddFriend.friendsAddFriend(appSession, context, Long.valueOf(userId), s);
                addFriendListener.onAddFriendStart(s1);
            }

            final AddFriendListener val$addFriendListener;
            final AppSession val$appSession;
            final Context val$context;
            final TextView val$textInput;
            final long val$userId;

            
            {
                textInput = textview;
                appSession = appsession;
                context = context1;
                userId = l;
                addFriendListener = addfriendlistener;
                super();
            }
        }
;
        textInput.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent)
            {
                if(i == 102)
                    friendRequestSender.run();
                return false;
            }

            final Runnable val$friendRequestSender;

            
            {
                friendRequestSender = runnable;
                super();
            }
        }
);
        builder.setPositiveButton(context.getString(0x7f0a0174), new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                friendRequestSender.run();
            }

            final Runnable val$friendRequestSender;

            
            {
                friendRequestSender = runnable;
                super();
            }
        }
);
        return builder.create();
    }
}
