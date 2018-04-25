package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_overridable_by_accessor
import com.a9ae0b01f0ffc.infinite_auth.granting.Authentication
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const.*

/**
 * Authentication configuration for the following purposes:
 * 1 - Defines Authentication Module Name (Authentication Name)
 * 2 - Defines a set of input (public, private) data for Authentication Module Name<p>
 * 3 - Defines Authentication Lockout settings <p>
 * Items 2 and 3 are supporting Accessor overriding (using item 1 - Authentication Name and Accessor Role "Authentication control")
 *
 * */
@CompileStatic
@Entity
@Table(name="AuthenticationTypes")
class AuthenticationType implements I_overridable_by_accessor{

    /**
     *  Identifies Authentication Module Name to be used for validating Public and Private Data.<p>
     *  Authentication Module is a custom script in certain location, containing business logic of validating Private and Public data.<p>
     *  Naming convention: <b>{name under which Public Data Fields can be grouped}_data</b><p>
     *  Non-unique field - there can be more than 1 Authentication Type with the same name, but with different Accessor Type (overriding by Accessor Type)<p><p>
     *
     * Example: "User_data"
     *
     * */
    @Column(unique = false)
    String authenticationName = GC_EMPTY_STRING

    /**
     *  Accessor Type associated with this Authentication Type.<p>
     *  Authentication Types sharing same Authentication Name can override each other within a list of Accessor Types
     *  (with Accessor Role "Authentication control") applicable to specific accessor.<p><p>
     *
     * */
    @ManyToOne(fetch = FetchType.EAGER)
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

    /**
     *  Defines after how many failed attempts (failure returned by Authentication Module) - Public Data Record (hash)
     *  gets locked out.<p>
     *  This setting is overrideable by Accessor Type (within same Authentication Names)<p><p>
     *
     * Example: "3"
     *
     * */
    @Column(nullable = false)
    Integer lockoutMaxAttemptCount = GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER

    /**
     *  Defines for how many seconds Public Data Record (hash) gets locked out - once the Lockout Maximum Attempt Count is exceeded<p>
     *  This setting is overrideable by Accessor Type (within same Authentication Names)<p><p>
     *
     * Example: "60"
     *
     * */
    @Column(nullable = false)
    Integer lockoutDurationSeconds = GC_AUTHENTICATION_LOCKOUT_DURATION_ZERO

    /**
     *  Used in Common Authentication Workflow.<p>
     *  Specifies Mandatory Public Fields that should be supplied within the Authentication JSON object by the Client<p>
     *  Any field missing in Client request results in immediate Failure scenario of Common Authentication Workflow.<p><p>
     *
     * Example: ["proxy_number", "phone_number", "otp_id"]
     *
     * */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PublicFields", joinColumns = @JoinColumn(name = "authentication_id"))
    Set<String> mandatoryPublicFieldNames

    /**
     *  Used in Common Authentication Workflow.<p>
     *  Specifies Mandatory Private Fields that should be supplied within the Authentication JSON object by the Client<p>
     *  Any field missing in Client request results in immediate Failure scenario of Common Authentication Workflow.<p><p>
     *
     * Example: ["otp"]
     *
     * */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PrivateFields", joinColumns = @JoinColumn(name = "authentication_id"))
    Set<String> mandatoryPrivateFieldNames

    /**
     *  Authentication Type id, unique generated identity field.
     *
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    Authentication to_user_authentication() {
        Authentication l_user_authentication = new Authentication()
        l_user_authentication.authenticationName = this.authenticationName
        return l_user_authentication
    }

    @Override
    AccessorType get_accessor_type() {
        return accessor
    }

    @Override
    String get_name() {
        return authenticationName
    }
}
