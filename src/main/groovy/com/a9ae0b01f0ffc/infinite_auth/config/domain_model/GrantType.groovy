package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_overridable_by_accessor
import com.a9ae0b01f0ffc.infinite_auth.granting.Grant
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF

/**
 * Grant Type is the lowest entity in the Authorization Configuration hierarchy.<p>
 * It identifies which specific REST Resource can be accessed - and using which HTTP method (POST, GET, etc) and how much times within a single Scope (Authorization) it is allowed to be accessed<p>
 * Like all Authorization Configurations, Grant Type is supporting Accessor overriding (using Grant Name and Accessor Role "Grant control")
 *
 * */
@CompileStatic
@Entity
@Table(name="ServiceTypes")
class GrantType implements I_overridable_by_accessor {

    /**
     *  Grant name.<p>
     *  Naming convention: <b>Method + REST Resource Name</b><p>
     *  This is primary configuration used in Authorization Validation Workflow. <p>
     *  Non-unique field - there can be more than 1 Grant Type with the same name, but with different Accessor Type (overriding by Accessor Type)<p><p>
     *
     * Example: "GET SecretKey" <p><p>
     *
     * */
    String grantName = GC_EMPTY_STRING

    /**
     *  Name of the REST resource being allowed.<p><p>
     *  This is typically a noun.<p>
     *  Used in Common Authentication Validation Workflow.<p>
     *  Should be exact match with URI Path. <p>
     *  Only checked when urlMask is not defined (see below). <p>
     *
     *  This setting is overrideable by Accessor Type (within same Grant Type Names)<p><p>
     *
     * Example: "W2WTransferInitiateEnhanced" <p><p>
     *
     * */
    String restResourceName = GC_EMPTY_STRING

    /**
     *  HTTP Method of the REST resource being allowed.<p>
     *  Used in Common Authentication Validation Workflow.
     *  One Grant Type allows strictly one Method - to make it possible to restrict different Methods for same REST Resource<p><p>
     *
     *  This setting is overrideable by Accessor Type (within same Grant Type Names)<p><p>
     *
     * Example: "GET" <p><p>
     *
     * */
    String method = GC_EMPTY_STRING

    /**
     *  Regular expression matching the URI path.<p>
     *  Optional parameter; if defined it is used instead of "restResourceName" ("restResourceName" is ignored).<p>
     *  Used in Common Authentication Validation Workflow.<p>
     *  Supports Data Scope (keyFieldMap) substitutions - any map keys surrounded by "%" in "urlMask" are replaced with their values from Data Scope (if present)<p>
     *  E.g.: ".*?\/%accessor_id%\/.*?\/.*" will be replaced with ".*?\/Access: Any accessor\/.*?\/.*" before matching with URI Path<p><p>
     *
     *  This setting is overrideable by Accessor Type (within same Grant Type Names)<p><p>
     *
     * Example: ".*?\/%accessor_id%\/.*?\/.*" <p><p>
     *
     * */
    String urlMask = GC_EMPTY_STRING

    /**
     *  Used in Common Authentication Validation Workflow.<p>
     *  Identifies name of the Validation Module - script (Rule) validating request payload (POST) or query parameters (GET) against Data Scope of the Authorization.<p><p>
     *  I.e. used to check that the functional request (e.g. GetCardDetails) is done against same Proxy Number OR Card Number
     *  - as it was authorized (and as defined in Authorization Data Scope - during Authorization Granting process).
     *
     *  This setting is overrideable by Accessor Type (within same Grant Type Names)<p><p>
     *
     * Example: "[CardNumber, ProxyNumber, PhoneNumber]" <p><p>
     *
     * */
    String validationModuleName = GC_EMPTY_STRING

    /**
     *  Defines how many functional requests Authorization Validations are allowed of this specific Grant Type within a single Authorization<p>
     *  Used in Common Authentication Validation Workflow.
     *  Null means no limitation<p>
     *  This setting is overrideable by Accessor Type (within same Grant Type Names)<p><p>
     *
     * Example: "1"
     *
     * */
    Integer maxUsageCountWithinAuthorization


    /**
     *  Accessor Type associated with this Grant Type.<p>
     *  Grant Types sharing same Grant Type Name can override each other within a list of Accessor Types
     *  (with Accessor Role "Grant control") applicable to specific accessor.<p><p>
     *
     * */
    @ManyToOne(fetch = FetchType.EAGER)
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

    /**
     *  Grant Type id, unique generated identity field.
     *
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    Grant to_user_grant() {
        Grant l_user_grant = new Grant()
        l_user_grant.restResourceName = this.restResourceName
        l_user_grant.method = this.method
        l_user_grant.urlMask = this.urlMask
        l_user_grant.validationModuleName = new HashSet<String>()
        l_user_grant.validationModuleName = this.validationModuleName
        l_user_grant.maxUsageCountWithinScope = this.maxUsageCountWithinAuthorization
        l_user_grant.grantTypeId = this.id
        return l_user_grant
    }

    @Override
    AccessorType get_accessor_type() {
        return accessor
    }

    @Override
    String get_name() {
        return grantName
    }

}
