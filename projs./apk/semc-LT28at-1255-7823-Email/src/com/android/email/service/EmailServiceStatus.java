package com.android.email.service;


public interface EmailServiceStatus {

   int ACCOUNT_UNINITIALIZED = 24;
   int ATTACHMENT_NOT_FOUND = 17;
   int CONNECTION_ERROR = 32;
   int FOLDER_NOT_CREATED = 20;
   int FOLDER_NOT_DELETED = 18;
   int FOLDER_NOT_RENAMED = 19;
   int IN_PROGRESS = 1;
   int LOGIN_FAILED = 22;
   int MESSAGE_NOT_FOUND = 16;
   int REMOTE_EXCEPTION = 21;
   int SECURITY_FAILURE = 23;
   int SUCCESS;

}
