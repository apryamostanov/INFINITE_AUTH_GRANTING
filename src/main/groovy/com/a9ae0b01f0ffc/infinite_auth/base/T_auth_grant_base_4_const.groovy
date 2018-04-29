package com.a9ae0b01f0ffc.infinite_auth.base

import base.T_common_base_3_utils

class T_auth_grant_base_4_const extends T_common_base_3_utils {

    static final String GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL = "Authorization control"
    static final String GC_ACCESSOR_TYPE_SCOPE_CONTROL = "Scope control"
    static final String GC_ACCESSOR_TYPE_GRANT_CONTROL = "Grant control"
    static final String GC_ACCESSOR_TYPE_IDENTITY_CONTROL = "Identity control"
    static final String GC_ACCESSOR_TYPE_AUTHENTICATION_CONTROL = "Authentication control"
    static final String GC_ACCESSOR_TYPE_ACCESS_CONTROL = "Access control"
    static final String GC_ACCESSOR_TYPE_ROUTING_CONTROL = "Routing control"
    static final String GC_STATUS_NEW = "New"
    static final String GC_STATUS_FAILED = "Failed"
    static final String GC_STATUS_SUCCESSFUL = "Successful"
    static final String GC_AUTHORIZATION_ERROR_CODE_01_INVALID_JWT = "1"
    static final String GC_AUTHORIZATION_ERROR_CODE_02_EMPTY_EXPIRY = "2"
    static final String GC_AUTHORIZATION_ERROR_CODE_03_EXPIRED = "3"
    static final String GC_AUTHORIZATION_ERROR_CODE_04_WRONG_NAME = "4"
    static final String GC_AUTHORIZATION_ERROR_CODE_04A_WRONG_SCOPE_NAME = "4A"
    static final String GC_AUTHORIZATION_ERROR_CODE_05_NO_PREREQUISITES = "5"
    static final String GC_AUTHORIZATION_ERROR_CODE_MDWL9403_FAILED_PREREQUISITE = "mdwl9403"
    static final String GC_AUTHORIZATION_ERROR_CODE_MDWL9401_EXPIRED_PREREQUISITE = "mdwl9401"
    static final String GC_AUTHORIZATION_ERROR_CODE_MDWL9403A_REVOKED_PREREQUISITE = "mdwl9403A"
    static final String GC_AUTHORIZATION_ERROR_CODE_MDWL9403B_EXCEEDED_PREREQUISITE = "mdwl9403B"
    static final String GC_AUTHORIZATION_ERROR_CODE_11_EMPTY_AUTHENTICATIONS = "11"
    static final String GC_AUTHORIZATION_ERROR_CODE_12_NO_IDENTITY = "12"
    static final String GC_AUTHORIZATION_ERROR_CODE_13_NO_AUTHENTICATIONS = "13"
    static final String GC_AUTHORIZATION_ERROR_CODE_14_EMPTY_AUTHENTICATIONS = "14"
    static final String GC_AUTHORIZATION_ERROR_CODE_15_WRONG_AUTHENTICATIONS_NUMBER = "15"
    static final String GC_AUTHORIZATION_ERROR_CODE_MDWL8001_FAILED_AUTHENTICATION = "mdwl8001"
    static final String GC_AUTHORIZATION_ERROR_CODE_17 = "17"
    static final String GC_AUTHORIZATION_ERROR_CODE_17A = "17A"
    static final String GC_AUTHORIZATION_ERROR_CODE_18_DATA_CONSISTENCY = "18"
    static final String GC_AUTHORIZATION_ERROR_CODE_18A_AUTHENTICATION_PREVALIDATION = "18A"
    static final String GC_AUTHORIZATION_ERROR_CODE_19_USER_SUPPLIED_JWT = "19"
    static final String GC_AUTHORIZATION_ERROR_CODE_20_MISSING_ACCESSOR_DATA = "20"
    static final Integer GC_JWT_VALIDITY_OK = 1
    static final Integer GC_JWT_VALIDITY_EXPIRED = 2
    static final Integer GC_JWT_VALIDITY_INVALID = 3
    static final String GC_DATA_TYPE_STRING = "AN"
    static final String GC_DATA_TYPE_NUMBER = "N"
    static final String GC_DATA_TYPE_BOOLEAN = "B"
    static final String GC_DATA_TYPE_DATETIME = "DT"
    static final String GC_JSON_NULL = "null"
    static final String GC_MODE_CONFIGURATION = "CONFIGURATION"
    static final String GC_MODE_GRANTING = "GRANTING"
    static final String GC_MODE_VALIDATION = "VALIDATION"
    static final Integer GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER = 0
    static final Integer GC_AUTHENTICATION_LOCKOUT_DURATION_ZERO = 0
    static final Boolean GC_PRIVATE_DATA_VALIDITY_UNKNOWN = GC_NULL_OBJ_REF
    static final Boolean GC_PRIVATE_DATA_INVALID = false
    static final Boolean GC_PRIVATE_DATA_VALID = true

}
