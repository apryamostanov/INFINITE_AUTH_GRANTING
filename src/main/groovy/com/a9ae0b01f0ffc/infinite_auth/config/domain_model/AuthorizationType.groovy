package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_overridable_by_accessor
import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_NULL_OBJ_REF

/**
 * Authorization Type is the highest entity in the Authorization Configuration hierarchy.<p>
 * It identifies which Identity Types are valid for which Scope Types and what should be the parameters of resulting Authorization <p>
 * (e.g. its duration, usage count, etc)<p>
 * Like all Authorization Configurations, Authorization Type is supporting Accessor overriding (using Authorization Name and Accessor Role "Authorization control")
 *
 * */
@CompileStatic
@Entity
@Table(name = "TokenTemplates")
class AuthorizationType implements I_overridable_by_accessor {

    /**
     *  Authorization name.<p>
     *  Naming convention: <b>Simple short noun or verb describing overall access privileges associated with this Authorization Type</b><p>
     *  Non-unique field - there can be more than 1 Authorization Type with the same name, but with different Accessor Type (overriding by Accessor Type)<p><p>
     *
     * Example: "Read"
     *
     * */
    String authorizationName

    /**
     *  Accessor Type associated with this Authorizatino Type.<p>
     *  Authorization Types sharing same Authorization Name can override each other within a list of Accessor Types
     *  (with Accessor Role "Authorization control") applicable to specific accessor.<p><p>
     *
     * */
    @ManyToOne(fetch = FetchType.EAGER)
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

    /**
     *  List of Identity Types allowed to be granted this Authorization Type.<p>
     *  Note: ANY ONE of the Identity Types is allowed.<p>
     *  This setting is overrideable by Accessor Type (within same Authorization Names)<p><p>
     *
     * */
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @JoinTable(name = "Identity2authorization")
    Set<IdentityType> identitySet = new HashSet<IdentityType>()

    /**
     *  List of Scope Types accessible using this Authorization Type.<p>
     *  Note: ALL of the Scope Types are allowed.<p>
     *  This setting is overrideable by Accessor Type (within same Authorization Names)<p><p>
     *
     * */
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @JoinTable(name = "Authorization2scope")
    Set<ScopeType> scopeSet = new HashSet<ScopeType>()

    /**
     *  Defines for how many seconds the successful Authorization is valid<p>
     *  This setting is overrideable by Accessor Type (within same Authorization Names)<p><p>
     *
     * Example: "1800"
     *
     * */
    Integer durationSeconds

    /**
     *  Defines for how validations the successful Authorization is valid<p>
     *  Null means no limitation<p>
     *  This setting is overrideable by Accessor Type (within same Authorization Names)<p><p>
     *
     * Example: "1"
     *
     * */
    Integer maxUsageCount

    /**
     *  Defines for how many seconds the Refresh Authorization (accompanying a successful Authorization) is valid<p>
     *  Used by "Refresh_data" built-in Authentication Module.<p>
     *  Applicable only when Refresh Allowed is "true".<p>
     *  This setting is overrideable by Accessor Type (within same Authorization Names)<p><p>
     *
     * Example: "2592000"
     *
     * */
    Integer refreshDurationSeconds

    /**
     *  Defines for how many validations the Refresh Authorization (accompanying a successful Authorization) is valid<p>
     *  Used by "Refresh_data" built-in Authentication Module.<p>
     *  Applicable only when Refresh Allowed is "true".<p>
     *  Null means no limitation<p>
     *  This setting is overrideable by Accessor Type (within same Authorization Names)<p><p>
     *
     * Example: "null"
     *
     * */
    Integer refreshMaxUsageCount

    /**
     *  Refresh Authorization Name<p>
     *  If Authorization is successful and Authorization Type has Refressh Allowed = True - an accompanying Refresh Authorization
     *  will be generated with this name.<p>
     *  This setting is overrideable by Accessor Type (within same Authorization Names)<p><p>
     *
     * Example: "Refresh"
     *
     * */
    String refreshAuthorizationName

    /**
     *  Identifies whether or not a Refresh Authorization should be generated along with Successful Authorization.<p>
     *  Refresh Authorization is a long-lived Authorization used only and only within Authorization Granting Server (and NEVER within
     *  Authorization Validation Server/Resource Server) - due to its sensitivity which requires not to store it in any logs.<p>
     *  Refresh Authorization JWT is using a different Keystore, accessible only by Authorization Granting Server<p>
     *  Refresh Authorization is validated by special predefined Authentication Module "Refresh_data".<p>
     *  This setting is overrideable by Accessor Type (within same Authorization Names)<p><p>
     *
     * Example: "true"
     *
     * */
    Boolean isRefreshAllowed

    /**
     *  List of Authorization Types - Authorization of which must be supplied as a prerequisite for granting of this Authorization Type.<p>
     *  Note: ANY ONE of the Authorization Type Prerequisites is sufficient.<p>
     *  This setting is overrideable by Accessor Type (within same Authorization Names)<p><p>
     *
     * */
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @JoinTable(name = "Authorizations2prerequisites")
    Set<AuthorizationType> prerequisiteAuthorizationSet

    /**
     *  Authorization Type id, unique generated identity field.
     *
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    /**
     *  Creates an instance of specific concrete Authorization based on this Authorization Type.
     *
     * */
    Set<Authorization> to_user_authorizations(String i_scope_name) {
        Set<Authorization> l_user_authorizations = new HashSet<Authorization>()
        ScopeType l_scope_type = this.scopeSet.first()
        if (l_scope_type.scopeName == i_scope_name) {
            for (IdentityType l_identity_type in this.identitySet) {
                    Authorization l_user_authorization = new Authorization()
                    l_user_authorization.authorizationName = this.authorizationName
                    l_user_authorization.authorizationType = "Access"
                    l_user_authorization.identity = l_identity_type.to_user_identity()
                    l_user_authorization.scope = l_scope_type.to_user_scope()
                    l_user_authorization.durationSeconds = this.durationSeconds
                    l_user_authorization.maxUsageCount = this.maxUsageCount
                    l_user_authorizations.add(l_user_authorization)
            }
        }
        return l_user_authorizations
    }

    @Override
    AccessorType get_accessor_type() {
        return accessor
    }

    @Override
    String get_name() {
        return authorizationName
    }

}
