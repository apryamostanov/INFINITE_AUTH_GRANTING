package com.a9ae0b01f0ffc.infinite_auth.granting

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * Contains Public and Private data.<p>
 * Note: at no point of time Private Data should be sent back to the Client or saved at the server side.<p>
 * It must be truncated from the response Authorization object as well as from the JWT.<p>
 * It is strictly a single one-directional use - from Client to Server during Authorization Granting.
 *
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication_data {

    /**
     * Request and Response field.<p><p>
     * Public data field map<p>
     * Note: this field is used as an identifier to lock Authentication after consecutive failed attempts.<p>
     * For more details see Common Authentication Workflow description<p><p>
     *
     *     Example: ["proxy_number": "123423154", "phone_number": "+9715544332211", "otp_id": 234534]
     *
     * */
    @JsonProperty("public_data")
    HashMap<String, Object> publicDataFieldMap

    /**
     * Request only field.<p><p>
     * Private data field map<p>
     * At no point of time Private Data should be sent back to the Client or saved at the server side.<p>
     * It must be truncated from the response Authorization object as well as from the JWT.<p>
     * It is strictly a single one-directional use - from Client to Server during Authorization Granting.<p><p>
     *
     *     Example: ["otp": 4334521]
     *
     * */
    @JsonProperty("private_data")
    HashMap<String, Object> privateDataFieldMap

}
