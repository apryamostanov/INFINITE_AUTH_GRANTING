package com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Identity
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING

/**@ApiModel(description = "Defines a set of input (public, private) and output (keys, functional) data as well as the Granting Server -> AuthenticationType Provider name")*/
@CompileStatic
@Entity
@Table(name="AuthenticationTypes")
class AuthenticationType {

    /**@ApiModelProperty(example = "User_data", value = "Defines the Granting Server -> AuthenticationType Provider name")*/
    @Column(unique = true)
    String authenticationName = GC_EMPTY_STRING

    /**@ApiModelProperty(example = "1", value = "AuthenticationType id, generated field")*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    Authentication to_user_authentication() {
        Authentication l_user_authentication = new Authentication()
        l_user_authentication.authenticationName = this.authenticationName
        return l_user_authentication
    }

}
