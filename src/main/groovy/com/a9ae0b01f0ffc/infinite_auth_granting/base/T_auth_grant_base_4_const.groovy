package com.a9ae0b01f0ffc.infinite_auth_granting.base

import base.T_common_base_3_utils

class T_auth_grant_base_4_const extends T_common_base_3_utils {

    static final String GC_BEAN_NAME_CONF = "T_auth_grant_conf"
    static final String GC_SEARCH_URL_LITERAL = "/search/"
    static final String GC_DOMAIN_MODEL_CLASS_PREFIX = "com.a9ae0b01f0ffc.infinite_auth_granting.domain_model."
    static final String GC_RESOURCE_ABSOLUTE_URL = "selfUrl"
    static final String GC_RESOURCE_RELATIVE_URL = "resourceAccessUrl"
    static final String GC_RESOURCE_CACHE_REFERENCE_URL = "resourceCacheReferenceUrl"
    static final String GC_LINK_NAME_SELF = "self"
    static final Boolean GC_TRAVERSE_NO = false
    static final Boolean GC_TRAVERSE_YES = true
    static final String GC_RESOURCE_ISCACHED_PROPERTY_NAME = "isReferencing"
    static final Boolean GC_IS_REFERENCE_YES = true
    static final Boolean GC_IS_REFERENCE_NO = false
    static final String GC_NEST_MODE_VALUE = "value"
    static final String GC_NEST_MODE_REFERENCE = "reference"
    static final String GC_ANY = "%"
    static final String GC_STATUS_NEW = "new"
    static final String GC_STATUS_FAILED = "failed"
    static final String GC_STATUS_SUCCESSFUL = "successful"
    static final String GC_STATUS_ERROR = "error"
    static final Integer GC_AUTHORIZATION_ERROR_CODE_01_INVALID_JWT = 1
    static final Integer GC_AUTHORIZATION_ERROR_CODE_02_EMPTY_EXPIRY = 2
    static final Integer GC_AUTHORIZATION_ERROR_CODE_03_EXPIRED = 3
    static final Integer GC_AUTHORIZATION_ERROR_CODE_04_WRONG_NAME = 4
    static final Integer GC_AUTHORIZATION_ERROR_CODE_05_NO_PREREQUISITES = 5
    static final Integer GC_AUTHORIZATION_ERROR_CODE_06_EMPTY_PREREQUISITES = 6
    static final Integer GC_AUTHORIZATION_ERROR_CODE_07_MORE_THEN_ONE_PREREQUISITE = 7
    static final Integer GC_AUTHORIZATION_ERROR_CODE_08_FAILED_PREREQUISITE = 8
    static final Integer GC_AUTHORIZATION_ERROR_CODE_09_UNEXPECTED_REFRESH = 9
    static final Integer GC_AUTHORIZATION_ERROR_CODE_10_FAILED_REFRESH = 10
    static final Integer GC_AUTHORIZATION_ERROR_CODE_11_EMPTY_AUTHENTICATIONS = 11
    static final Integer GC_AUTHORIZATION_ERROR_CODE_12_NO_IDENTITY = 12
    static final Integer GC_AUTHORIZATION_ERROR_CODE_13_NO_AUTHENTICATIONS = 13
    static final Integer GC_AUTHORIZATION_ERROR_CODE_14_EMPTY_AUTHENTICATIONS = 14
    static final Integer GC_AUTHORIZATION_ERROR_CODE_15_WRONG_AUTHENTICATIONS_NUMBER = 15
    static final Integer GC_AUTHORIZATION_ERROR_CODE_16_FAILED_AUTHENTICATION = 16
    static final Integer GC_AUTHORIZATION_ERROR_CODE_17 = 17

    static final Integer GC_AUTHENTICATION_ERROR_CODE_04_WRONG_NAME = 4
    static final Integer GC_AUTHENTICATION_ERROR_CODE_16_FAILED_AUTHENTICATION = 16

}
