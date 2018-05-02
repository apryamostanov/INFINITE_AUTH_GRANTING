package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF

/**
 * Contains Accessor Type rules used for the following purposes:
 * 1 - Modifies (overrides) Authorization Configuration as per accessor parameters (FIID, platform,  app name, etc)<p>
 * Settings overriding happens within settings sharing the same name - e.g. Identity Name, etc - by Accessor Type.<p>
 * 2 - Restricts access of specific accessors (using built-in Accessor_data authentication module)<p>
 * 3 - Specifies Authorization Validation Endpoint Group Name - JWTs will not work on endpoints which are
 * not enabled within this group<p>
 * 4 - Specifies Resource Endpoint Name - for the Authorization Validation Endpoint to route the API request to
 * appropriate Resource Endpoint
 *
 * */
@CompileStatic
@Entity
@Table(name = "AccessorTypes")
class AccessorType {

    /**
     * Defines the purpose of Accessor Type rule.<p>
     * Possible values:<p>
     * - Authorization control<p>
     * - Scope control<p>
     * - Grant control<p>
     * - Identity control<p>
     * - Authentication control<p>
     * - Access control<p>
     * - Routing control<p><p>
     *
     * Example: "Access control"<p>
     *
     * */
    String accessorRole = GC_EMPTY_STRING

    /**
     * Identifies the order of precedence for finding Authorization Configuration settings overridden with specific
     * Accessor Type rules.<p>
     * Settings overriding happens within settings sharing the same name - e.g. Identity Name, etc - by Accessor Type.<p>
     * The matching happens "from specific to general".<p><p>
     *
     * Example: "2"<p>
     *
     * */
    Integer lookupPriority = GC_NULL_OBJ_REF as Integer

    /**
     * Rule Matching field.<p>
     * Client software name, as hardcoded on the client side.<p>
     * E.g. name of the app.<p>
     * Supported SQL "like" syntax - e.g. "%" wildcard.<p><p>
     *
     * Example: "%Bank ABCD%QA%Single Currency%"<p>
     *
     * */
    String appName = GC_EMPTY_STRING

    /**
     * Unique Accessor Type Rule name.<p><p>
     *
     * Example: "Authorization: Any White Labeled Multi Currency"<p>
     *
     * */
    @Column(unique = true)
    String accessorName = GC_EMPTY_STRING

    /**
     * Rule Matching field.<p>
     * Client software platform name (e.g. iOS, Android, React), hardcoded on the client side<p>
     * Supported SQL "like" syntax - e.g. "%" wildcard.<p><p>
     *
     * Example: "React"<p>
     *
     * */
    String platform = GC_EMPTY_STRING

    /**
     * Rule Matching field.<p>
     * Client software operating system name (e.g. iOS 10, Android KitCat, Windows 10), hardcoded on the client side<p>
     * Supported SQL "like" syntax - e.g. "%" wildcard.<p><p>
     *
     * Example: "iOS 10"<p>
     *
     * */
    String osName = GC_EMPTY_STRING

    /**
     * Rule Matching field.<p>
     * Client software application version name, hardcoded on the client side<p>
     * Supported SQL "like" syntax - e.g. "%" wildcard.<p><p>
     *
     * Example: "1.6.%"<p>
     *
     * */
    String appVersion = GC_EMPTY_STRING

    /**
     * Rule Matching field.<p>
     * Program manager/business associate ID (in case there are multiple app names for same fiid), hardcoded on the client side<p>
     * Supported SQL "like" syntax - e.g. "%" wildcard.<p><p>
     *
     * Example: "1150"<p>
     *
     * */
    String fiid = GC_EMPTY_STRING

    /**
     * Rule Matching field.<p>
     * Card Product, hardcoded on the client side.<p>
     * Supported SQL "like" syntax - e.g. "%" wildcard.<p><p>
     *
     * Example: "2209"<p>
     *
     * */
    String product = GC_EMPTY_STRING

    /**
     * Rule Matching field.<p>
     * Card Product Group.<p>
     * Supported SQL "like" syntax - e.g. "%" wildcard, hardcoded on the client side.<p><p>
     *
     * Example: "Multicurrency"<p>
     *
     * */
    String productGroup = GC_EMPTY_STRING

    /**
     * Indicates that this specific accessor is banned - used in predefined Accessor_data authentication module.<p>
     * Note: only applicable to Accessor Types with role "Access control".<p><p>
     *
     * Example: "false"<p>
     *
     * */
    Boolean isForbidden

    /**
     * Rule Matching field.<p>
     * Endpoint ID / object as configured on Authorization Granting Server).<p>
     * Used when more then one Authorization Granting Server instance is sharing same Authorization Granting Configuration Database.
     * Supported SQL "like" syntax - e.g. "%" wildcard.<p><p>
     *
     * Example: "Authorization Granting QA"<p>
     *
     * */
    String grantingEndpointName = GC_EMPTY_STRING

    /**
     * Rule Matching field.<p>
     * Client software application version name, hardcoded on the client side<p>
     * API version supported (used) by the Client Software<p>
     * Supported SQL "like" syntax - e.g. "%" wildcard.<p><p>
     *
     * Example: "2.%.%"<p>
     *
     * */
    String apiVersionName = GC_EMPTY_STRING

    /**
     * Specifies Authorization Validation Endpoint Name only for which the Authorizations produced using this Accessor Type are valid<p>
     * Applicable only for Accessor Role "Routing control"<p>
     * Supported Java regexp syntax - e.g. ".*" wildcard.<p><p>
     *
     * Example: "Authorization Validation EMEA QA"<p>
     *
     * */
    String validationEndpointGroupName = GC_EMPTY_STRING

    /**
     * Specifies a direction to Authorization Validation Endpoint Name on where to route the API request (if JWT is successfully validated)<p>
     * Applicable only for Accessor Role "Routing control"<p><p>
     *
     * Example: "EMEA QA"<p>
     *
     * */
    String resourceEndpointName = GC_EMPTY_STRING

    /**
     *  Specifies which products are allowed for this accessor.<p>
     *  Prevents any step-up Authorizations with Authentications (e.g. User_data) with resulting Product ID not within this list. <p>
     *  Null means no restriction.<p>
     *  Comma-separated string list; when at least 1 product is set - list should start and end with comma<p><p>
     *
     *  Examples:<p>
     *      -",2230,2241,2242,"<p>
     *      -",2230,"<p>
     *      -"" (empty string) or null - not restriction<p>
     *      "," - wrong usage<p>
     *      ",," - wrong usage<p>
     *
     * */
    String allowedProducts = GC_EMPTY_STRING

    /**
     *  AccessorType id, generated field
     *
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

}
