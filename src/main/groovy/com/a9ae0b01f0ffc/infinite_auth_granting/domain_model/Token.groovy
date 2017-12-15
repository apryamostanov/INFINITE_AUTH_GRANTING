package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.springframework.hateoas.ResourceSupport

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.MediaType

@CompileStatic
@Path("/tokens")
class Token extends ResourceSupport {

    String authorizationName
    String scopeName
    String identityName
    List<Authentication> authenticationList
    Map<String, List<String>> dataFieldListMap
    String prerequisiteToken
    Date creationDate
    Date expiryDate
    Integer usageLimit
    List<Grant> resourceGrantList
    String authorizationStatus
    String errorCode
    Token refreshToken
    String jwt


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void post_list(String i_json_string) {
        Object l_parsed_json_array = new JsonSlurper().parseText(i_json_string)
        l_parsed_json_array.each { l_json_array_element ->
            ObjectMapper l_object_mapper = new ObjectMapper()
            Token l_token = (Token) l_object_mapper.readValue(new JsonBuilder(l_json_array_element).toPrettyString(), this.class)
            System.out.println("Received authorization " + l_token.getAuthorizationName())
        }
    }

}