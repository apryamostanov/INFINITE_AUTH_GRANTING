package com.a9ae0b01f0ffc.infinite_auth.granting

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Identity is a group of Authentications<p>
 * Request and Response object.<p>
 * Contains user-provided Authentication data (objects) as well as results of their validation (in case of Response)<p>
 *
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
class Identity {

    /**
     *  Identity name.<p>
     *
     * Example: "Owner of User data and OTP data" <p><p>
     *
     * */
    String identityName

    /**
     *  List of Authentications required for this Identity.<p>
     *  Note: ALL of the needed Authentications must be present.<p>
     *
     * */
    @JsonProperty("authentications")
    Set<Authentication> authenticationSet

}