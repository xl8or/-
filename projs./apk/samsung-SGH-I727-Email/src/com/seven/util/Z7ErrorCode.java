package com.seven.util;

import java.io.Serializable;

public class Z7ErrorCode implements Serializable {

   public static final Z7ErrorCode Z7_ERROR_PING_ALREADY_SERVICE_REGISTERED_USER = new Z7ErrorCode(7005);
   public static final Z7ErrorCode Z7_ERROR_PING_FAIL_TO_CREATE_ACCOUNT = new Z7ErrorCode(7001);
   public static final Z7ErrorCode Z7_ERROR_PING_INVALID_CUSTOM_DATA = new Z7ErrorCode(7004);
   public static final Z7ErrorCode Z7_ERROR_PING_INVALID_SERVICE_DESCRIPTOR = new Z7ErrorCode(7003);
   public static final Z7ErrorCode Z7_ERROR_PING_INVALID_SERVICE_KEY = new Z7ErrorCode(7002);
   public static final Z7ErrorCode Z7_ERR_ACCOUNT_ERROR = new Z7ErrorCode(400);
   public static final Z7ErrorCode Z7_ERR_ACCOUNT_LOCKED = new Z7ErrorCode(306);
   public static final Z7ErrorCode Z7_ERR_ACTIVATE_MAIL_FAILED = new Z7ErrorCode(309);
   public static final Z7ErrorCode Z7_ERR_ADMIN_DUP_ERROR = new Z7ErrorCode(503);
   public static final Z7ErrorCode Z7_ERR_AMBIGUOUS_ACCOUNT = new Z7ErrorCode(303);
   public static final Z7ErrorCode Z7_ERR_API_ACCESS_DENIED = new Z7ErrorCode(1015);
   public static final Z7ErrorCode Z7_ERR_API_ACCESS_NOT_ENABLED = new Z7ErrorCode(1016);
   public static final Z7ErrorCode Z7_ERR_API_BRAND_NOT_FOUND = new Z7ErrorCode(1011);
   public static final Z7ErrorCode Z7_ERR_API_DUPLCATE_SUBSCRIPTION = new Z7ErrorCode(1023);
   public static final Z7ErrorCode Z7_ERR_API_DUPLICATE_DEVICE = new Z7ErrorCode(1002);
   public static final Z7ErrorCode Z7_ERR_API_DUPLICATE_USER = new Z7ErrorCode(1001);
   public static final Z7ErrorCode Z7_ERR_API_INCONSISTENT_PARAMETERS = new Z7ErrorCode(1037);
   public static final Z7ErrorCode Z7_ERR_API_INTERNAL_ERROR = new Z7ErrorCode(1020);
   public static final Z7ErrorCode Z7_ERR_API_INVALID_DATE_FORMAT = new Z7ErrorCode(1004);
   public static final Z7ErrorCode Z7_ERR_API_INVALID_DAY_FORMAT = new Z7ErrorCode(1005);
   public static final Z7ErrorCode Z7_ERR_API_INVALID_MSISDN_FORMAT = new Z7ErrorCode(1038);
   public static final Z7ErrorCode Z7_ERR_API_INVALID_PRODUCT = new Z7ErrorCode(1039);
   public static final Z7ErrorCode Z7_ERR_API_MISSING_REQ_PARAMETER = new Z7ErrorCode(1008);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_ALREADY_EXISTS = new Z7ErrorCode(2016);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_AUTHENTICATION_FAILED = new Z7ErrorCode(2000);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_CHARGING_HEADER_VALIDATION_FAILED = new Z7ErrorCode(2015);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_INVALID_CLIENT_ID_FORMAT = new Z7ErrorCode(2014);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_INVALID_MSISDN_FORMAT = new Z7ErrorCode(2013);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_MSISDN_INFORMATION_INVALID = new Z7ErrorCode(2012);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_NOT_EXISTS = new Z7ErrorCode(2017);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_NO_CLIENT_FOUND = new Z7ErrorCode(2011);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_NO_CLIENT_ID_FOUND = new Z7ErrorCode(2010);
   public static final Z7ErrorCode Z7_ERR_API_MSISDN_TRANSACTION_FAILED = new Z7ErrorCode(2099);
   public static final Z7ErrorCode Z7_ERR_API_MULTIPLE_MATCHING_SUBSCRIPTIONS = new Z7ErrorCode(1024);
   public static final Z7ErrorCode Z7_ERR_API_NO_EXPIRATION_DATE = new Z7ErrorCode(1014);
   public static final Z7ErrorCode Z7_ERR_API_QUERY_NOT_FOUND = new Z7ErrorCode(1036);
   public static final Z7ErrorCode Z7_ERR_API_REPLICATION_CALL_FAILED = new Z7ErrorCode(1050);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_ACCESS_DENIED = new Z7ErrorCode(2101);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_ADDRESS_BLOCKED = new Z7ErrorCode(2114);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_API_NOT_ENABLED = new Z7ErrorCode(2115);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_AUTHENTICATION_BLOCKED = new Z7ErrorCode(2102);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_AUTHENTICATION_FAILED = new Z7ErrorCode(2100);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_BATCH_PARTIALLY_FAILED = new Z7ErrorCode(2103);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_BATCH_SIZE_OVER_LIMIT = new Z7ErrorCode(2106);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_CHANGE_ACCOUNT_PASSWORD_ERROR = new Z7ErrorCode(2433);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_CHANGE_ACCOUNT_PASSWORD_MULTIPLE_MATCHES = new Z7ErrorCode(2431);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_CHANGE_ACCOUNT_PASSWORD_NO_MATCHES = new Z7ErrorCode(2432);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_CREATE_ACCOUNT_SUB_MISSING_PARAMETER = new Z7ErrorCode(2200);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_CREATE_PREPROV_ACCOUNT_SUB_COMPANY_NOT_FOUND = new Z7ErrorCode(2362);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_CREATE_PREPROV_ACCOUNT_SUB_INVALID_PARAMETERS = new Z7ErrorCode(2361);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_CREATE_PREPROV_ACCOUNT_SUB_ISP_SERVER_NOT_FOUND = new Z7ErrorCode(2363);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_CREATE_PREPROV_ACCOUNT_SUB_ISP_WRONG_SCOPE = new Z7ErrorCode(2364);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_DATABASE_ERROR = new Z7ErrorCode(2109);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_DELETE_ACCOUNT_SUB_PRODUCT_WITH_ID = new Z7ErrorCode(2261);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_ILLEGAL_PARAMETER = new Z7ErrorCode(2108);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_ILLEGAL_WILDCARD_USE = new Z7ErrorCode(2110);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_INVALID_WILDCARD = new Z7ErrorCode(2111);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_IS_CONNECTOR_ID_VALID_NO = new Z7ErrorCode(2601);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_LIST_ACCOUNT_SUB_PRODUCT_WITH_ID = new Z7ErrorCode(2221);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_MISSING_HEADER = new Z7ErrorCode(2112);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_MISSING_HEADER_PARAMETER = new Z7ErrorCode(2113);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_MISSING_PARAMETER = new Z7ErrorCode(2107);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_RETURN_LIST_OVER_LIMIT = new Z7ErrorCode(2105);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_RUN_SCRIPT_INVALID_PARAMETERS = new Z7ErrorCode(2781);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_RUN_SCRIPT_NOT_FOUND = new Z7ErrorCode(2782);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_SEND_DOWNLOAD_LINK_CLIENT_NOT_FOUND = new Z7ErrorCode(2803);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_SEND_DOWNLOAD_LINK_FAILED = new Z7ErrorCode(2802);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_SEND_DOWNLOAD_LINK_NOT_ENABLED = new Z7ErrorCode(2801);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_UNSPECIFIED_ERROR = new Z7ErrorCode(2199);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_UPDATE_ACCOUNT_SUB_INCORRECT_ID_PARAMETERS = new Z7ErrorCode(2241);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_UPDATE_ACCOUNT_SUB_MULTIPLE_MATCHES = new Z7ErrorCode(2240);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_UPDATE_ACCOUNT_SUB_NO_MATCHES = new Z7ErrorCode(2243);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_UPDATE_ACCOUNT_SUB_NO_UPDATE = new Z7ErrorCode(2242);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_UPDATE_CONNECTOR_SUB_INCORRECT_ID_PARAMETERS = new Z7ErrorCode(2321);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_UPDATE_CONNECTOR_SUB_MULTIPLE_MATCHES = new Z7ErrorCode(2320);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_UPDATE_CONNECTOR_SUB_NO_MATCHES = new Z7ErrorCode(2323);
   public static final Z7ErrorCode Z7_ERR_API_SOAP_UPDATE_CONNECTOR_SUB_NO_UPDATE = new Z7ErrorCode(2322);
   public static final Z7ErrorCode Z7_ERR_API_SUBSCRIPTION_NOT_FOUND = new Z7ErrorCode(1040);
   public static final Z7ErrorCode Z7_ERR_API_USER_LIMIT_EXCEEDED = new Z7ErrorCode(1000);
   public static final Z7ErrorCode Z7_ERR_API_USER_NOT_FOUND = new Z7ErrorCode(1003);
   public static final Z7ErrorCode Z7_ERR_BAD_RELAY_NEGOTIATION = new Z7ErrorCode(102);
   public static final Z7ErrorCode Z7_ERR_BAD_REQUEST = new Z7ErrorCode(101);
   public static final Z7ErrorCode Z7_ERR_BAD_XML_RPC_PARAMETERS = new Z7ErrorCode(5003);
   public static final Z7ErrorCode Z7_ERR_CALL_ID_INVALID = new Z7ErrorCode(6013);
   public static final Z7ErrorCode Z7_ERR_CLIENT_NOT_CERTIFIED = new Z7ErrorCode(312);
   public static final Z7ErrorCode Z7_ERR_CONFIGURATION_NOT_FOUND = new Z7ErrorCode(6016);
   public static final Z7ErrorCode Z7_ERR_CONNECTOR_DISABLED = new Z7ErrorCode(204);
   public static final Z7ErrorCode Z7_ERR_CONNECT_FAILED = new Z7ErrorCode(107);
   public static final Z7ErrorCode Z7_ERR_CONTACT_BLOCKED = new Z7ErrorCode(6002);
   public static final Z7ErrorCode Z7_ERR_CONTACT_OFFLINE = new Z7ErrorCode(6001);
   public static final Z7ErrorCode Z7_ERR_ENDPOINT_DOWN = new Z7ErrorCode(106);
   public static final Z7ErrorCode Z7_ERR_ENDPOINT_NOT_FOUND = new Z7ErrorCode(103);
   public static final Z7ErrorCode Z7_ERR_ENTERPRISE_EDITION_MISMATCH = new Z7ErrorCode(206);
   public static final Z7ErrorCode Z7_ERR_ENTERPRISE_NOT_UNIQUE = new Z7ErrorCode(202);
   public static final Z7ErrorCode Z7_ERR_ENT_DUP_ERROR = new Z7ErrorCode(502);
   public static final Z7ErrorCode Z7_ERR_FILE_SIZE_MISMATCH = new Z7ErrorCode(6003);
   public static final Z7ErrorCode Z7_ERR_IM_TOKEN_AUTH_FAILED = new Z7ErrorCode(6017);
   public static final Z7ErrorCode Z7_ERR_INSTANCE_REDIRECT = new Z7ErrorCode(308);
   public static final Z7ErrorCode Z7_ERR_INTERNAL_ERROR = new Z7ErrorCode(100);
   public static final Z7ErrorCode Z7_ERR_INTERNAL_ERROR_IN_XML_RPC = new Z7ErrorCode(5002);
   public static final Z7ErrorCode Z7_ERR_INVALID_LICENSEKEY = new Z7ErrorCode(200);
   public static final Z7ErrorCode Z7_ERR_INVALID_REGISTRATION = new Z7ErrorCode(203);
   public static final Z7ErrorCode Z7_ERR_ISP_CONNECTION_FAILED = new Z7ErrorCode(4002);
   public static final Z7ErrorCode Z7_ERR_LOGINS_DISABLED = new Z7ErrorCode(310);
   public static final Z7ErrorCode Z7_ERR_LOGIN_FAILED = new Z7ErrorCode(300);
   public static final Z7ErrorCode Z7_ERR_LOGIN_FAILED_CE = new Z7ErrorCode(301);
   public static final Z7ErrorCode Z7_ERR_LOGIN_FAILED_RSA = new Z7ErrorCode(311);
   public static final Z7ErrorCode Z7_ERR_LOGIN_FAILED_USERNAME_REQUIRED = new Z7ErrorCode(305);
   public static final Z7ErrorCode Z7_ERR_MAIL_ATTACHMENT_TOO_BIG = new Z7ErrorCode(3005);
   public static final Z7ErrorCode Z7_ERR_MAIL_BODY_TOO_BIG = new Z7ErrorCode(3006);
   public static final Z7ErrorCode Z7_ERR_MAIL_CONNECTION_FAILED = new Z7ErrorCode(3001);
   public static final Z7ErrorCode Z7_ERR_MAIL_INBOX_NOT_FOUND = new Z7ErrorCode(3002);
   public static final Z7ErrorCode Z7_ERR_MAIL_INVALID_EMAIL_ADDRESS = new Z7ErrorCode(3004);
   public static final Z7ErrorCode Z7_ERR_MAIL_INVALID_PROVIDER = new Z7ErrorCode(3000);
   public static final Z7ErrorCode Z7_ERR_MAIL_OTHER_ERROR = new Z7ErrorCode(3100);
   public static final Z7ErrorCode Z7_ERR_MAIL_SEARCH_FAILED = new Z7ErrorCode(3003);
   public static final Z7ErrorCode Z7_ERR_MALFORMED_XML_RPC = new Z7ErrorCode(5001);
   public static final Z7ErrorCode Z7_ERR_MAX_P2P_SESSION_EXCEEDED = new Z7ErrorCode(6004);
   public static final Z7ErrorCode Z7_ERR_MISSING_BIRTHDATE = new Z7ErrorCode(6005);
   public static final Z7ErrorCode Z7_ERR_NOERROR = new Z7ErrorCode(0);
   public static final Z7ErrorCode Z7_ERR_NOT_PRIVILEGED_ERROR = new Z7ErrorCode(506);
   public static final Z7ErrorCode Z7_ERR_NO_AVAILABLE_CONNECTORS = new Z7ErrorCode(6019);
   public static final Z7ErrorCode Z7_ERR_NO_ID_FILE = new Z7ErrorCode(321);
   public static final Z7ErrorCode Z7_ERR_NO_ISP_MATCH = new Z7ErrorCode(304);
   public static final Z7ErrorCode Z7_ERR_NO_PARTICIPANTS_PRESENT = new Z7ErrorCode(6011);
   public static final Z7ErrorCode Z7_ERR_NO_SUCH_ACCOUNT = new Z7ErrorCode(302);
   public static final Z7ErrorCode Z7_ERR_ORG_DUP_ERROR = new Z7ErrorCode(501);
   public static final Z7ErrorCode Z7_ERR_OWA_CONNECTION_FAILED = new Z7ErrorCode(4001);
   public static final Z7ErrorCode Z7_ERR_P2P_SESSION_NOT_SUPPORTED = new Z7ErrorCode(6006);
   public static final Z7ErrorCode Z7_ERR_PARENT_PERMISSION_REQUIRED = new Z7ErrorCode(6012);
   public static final Z7ErrorCode Z7_ERR_PASSWORD_EXPIRED = new Z7ErrorCode(319);
   public static final Z7ErrorCode Z7_ERR_PERSISTENCE_ERROR = new Z7ErrorCode(500);
   public static final Z7ErrorCode Z7_ERR_PROVIDE_TEMP_PASSWORD = new Z7ErrorCode(201);
   public static final Z7ErrorCode Z7_ERR_PUBLIC_KEY_MISMATCH = new Z7ErrorCode(315);
   public static final Z7ErrorCode Z7_ERR_REGISTRATION_EXPIRED = new Z7ErrorCode(6007);
   public static final Z7ErrorCode Z7_ERR_RELOGIN_NOT_POSSIBLE = new Z7ErrorCode(316);
   public static final Z7ErrorCode Z7_ERR_SEND_FAILED = new Z7ErrorCode(104);
   public static final Z7ErrorCode Z7_ERR_SEND_TIMEDOUT = new Z7ErrorCode(105);
   public static final Z7ErrorCode Z7_ERR_SERVER_BUSY = new Z7ErrorCode(108);
   public static final Z7ErrorCode Z7_ERR_SERVER_TOO_BUSY = new Z7ErrorCode(6008);
   public static final Z7ErrorCode Z7_ERR_SERVICE_NOT_ENABLED = new Z7ErrorCode(205);
   public static final Z7ErrorCode Z7_ERR_SERVICE_SUBSCRIPTION_REQUIRED = new Z7ErrorCode(6018);
   public static final Z7ErrorCode Z7_ERR_SSL_PEER_UNVERIFIED = new Z7ErrorCode(510);
   public static final Z7ErrorCode Z7_ERR_STAGED_ACCOUNT_EXISTS = new Z7ErrorCode(307);
   public static final Z7ErrorCode Z7_ERR_STAGED_DUP_ERROR = new Z7ErrorCode(505);
   public static final Z7ErrorCode Z7_ERR_SUBSCRIPTION_EXPIRED = new Z7ErrorCode(507);
   public static final Z7ErrorCode Z7_ERR_SUBSCRIPTION_LIMIT_EXCEEDED = new Z7ErrorCode(508);
   public static final Z7ErrorCode Z7_ERR_SUBSCRIPTION_TRIALS_NOT_ACCEPTED = new Z7ErrorCode(509);
   public static final Z7ErrorCode Z7_ERR_TOO_MANY_PARTICIPANTS = new Z7ErrorCode(6010);
   public static final Z7ErrorCode Z7_ERR_TOO_MANY_PHONES = new Z7ErrorCode(320);
   public static final Z7ErrorCode Z7_ERR_UNKNOWN_USER = new Z7ErrorCode(6015);
   public static final Z7ErrorCode Z7_ERR_URL_DISALLOWED = new Z7ErrorCode(314);
   public static final Z7ErrorCode Z7_ERR_URL_INVALID = new Z7ErrorCode(313);
   public static final Z7ErrorCode Z7_ERR_URL_NOANSWER = new Z7ErrorCode(317);
   public static final Z7ErrorCode Z7_ERR_URL_NOTOWA = new Z7ErrorCode(318);
   public static final Z7ErrorCode Z7_ERR_USER_DUP_ERROR = new Z7ErrorCode(504);
   public static final Z7ErrorCode Z7_ERR_USER_OFFLINE = new Z7ErrorCode(6014);
   public static final Z7ErrorCode Z7_ERR_VOICE_CLIP_NOT_SUPPORTED = new Z7ErrorCode(6009);
   public static final Z7ErrorCode Z7_OWA_ACCESS_DENIED = new Z7ErrorCode(512);
   public static final Z7ErrorCode Z7_OWA_INVALID_SERVICE_ADDRESS = new Z7ErrorCode(511);
   int m_value;


   Z7ErrorCode(int var1) {
      this.m_value = var1;
   }

   public int compareTo(Object var1) {
      int var2 = this.m_value;
      int var3 = ((Z7ErrorCode)var1).m_value;
      return var2 - var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof Z7ErrorCode)) {
         var2 = false;
      } else {
         int var3 = ((Z7ErrorCode)var1).m_value;
         int var4 = this.m_value;
         if(var3 == var4) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   public int hashCode() {
      return this.m_value;
   }

   public String toString() {
      return Integer.toString(this.m_value);
   }
}
