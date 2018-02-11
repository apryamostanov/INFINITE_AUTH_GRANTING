package com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Accessor
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authorization
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF

/** @ApiModel ( d e s c r i p t i o n = "Consumed by AccessorType AuthenticationType Provider (e.g. Accessor_data module). Groups authorization rules (AuthorizationType, IdentityType, ScopeType definitions). Defines Set of client software allowed (or restricted) for accessing the API - and their corresponding identification mappings.") */
@CompileStatic
@Entity
class AccessorType {

    /** */
    String resourceName = this.getClass().getSimpleName()

    /** @ApiModelProperty ( e x a m p l e = "0", value = "Internal field. Priority for AccessorType identity provider matching. All accessor definitions are sorted with this field - and those with higher priority - matched first within AccessorType authentication provider") */
    Integer lookupPriority = GC_NULL_OBJ_REF as Integer

    /** @ApiModelProperty ( e x a m p l e = "Whitelabel-S.x", value = "Matching field. Client software name, hardcoded on the client side. E.g. name of the app. Supported wildcard value \"Any\".") */
    String appName = GC_EMPTY_STRING

    /** @ApiModelProperty ( e x a m p l e = "Any accessor Multi Currency 1.0.x", value = "Output field. Internal name of the accessor record.") */
    @Column(unique = true)
    String accessorName = GC_EMPTY_STRING

    /** @ApiModelProperty ( e x a m p l e = "React", value = "Matching field. Client software platform name (iOS, Android, React), hardcoded on the client side. E.g. name of the app. Supported wildcard value \"Any\".") */
    String platform = GC_EMPTY_STRING

    /** @ApiModelProperty ( e x a m p l e = "0.6.1", value = "Matching field. Client software platform name (iOS, Android, React), hardcoded on the client side. E.g. name of the app. Supported wildcard value \"Any\".") */
    String appVersion = GC_EMPTY_STRING

    /** @ApiModelProperty ( e x a m p l e = "1101", value = "Matching field. Program manager/business associate ID (in case there are multiple app names for same fiid) - hardcoded at app. Allows overriding authorization rules level of specific fiid. Supported wildcard value \"Any\".") */
    String fiid = GC_EMPTY_STRING

    /** @ApiModelProperty ( e x a m p l e = "2209", value = "Matching field. Application product. Allows overriding authorization rules level of specific product. Supported wildcard value \"Any\".") */
    String product = GC_EMPTY_STRING

    /** @ApiModelProperty ( e x a m p l e = "Multicurrency", value = "Matching field. Application product group. Allows overriding authorization rules level of specific product group. Supported wildcard value \"Any\".") */
    String productGroup = GC_EMPTY_STRING

    /** @ApiModelProperty ( e x a m p l e = "0", value = "Optional output field. Indicates that this specific accessor is banned - for AccessorType authentication provider. E.g. to restrict clients with outdated unsupported API versions") */
    Integer isForbidden = GC_NULL_OBJ_REF as Integer

    /** @ApiModelProperty ( v a l u e = "Matching field. Endpoint ID/object as configured on web app instance (granting server). Allows overriding authorization rules level of specific endpointName. Supported wildcard value \"null\". Same value should be in AuthorizationType Validation web app endpointName configuration.") */
    String endpointName = GC_EMPTY_STRING

    /** @ApiModelProperty ( v a l u e = "Matching field. API version (combination of minor and major versions). Allows overriding authorization rules level of specific API version. Supported wildcard value \"null\". Same value should be in AuthorizationType Validation web app endpointName configuration.") */
    String apiVersionName = GC_EMPTY_STRING

    /** @ApiModelProperty ( e x a m p l e = "1", value = "AccessorType id, generated field") */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    Accessor to_user_accessor() {
        Accessor l_user_accessor = new Accessor()
        l_user_accessor.appName = this.appName
        l_user_accessor.accessorName = this.accessorName
        l_user_accessor.platform = this.platform
        l_user_accessor.appVersion = this.appVersion
        l_user_accessor.fiid = this.fiid
        l_user_accessor.product = this.product
        l_user_accessor.productGroup = this.productGroup
        l_user_accessor.endpointName = this.endpointName
        l_user_accessor.apiVersionName = this.apiVersionName
        return l_user_accessor
    }

}
