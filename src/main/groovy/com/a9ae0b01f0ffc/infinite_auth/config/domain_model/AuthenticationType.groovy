package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_overridable_by_accessor
import com.a9ae0b01f0ffc.infinite_auth.granting.Authentication
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const.*

/**@ApiModel(description = "Defines a set of input (public, private) and output (keys, functional) data as well as the Granting Server -> AuthenticationType Provider name")*/
@CompileStatic
@Entity
@Table(name="AuthenticationTypes")
class AuthenticationType implements I_overridable_by_accessor{

    /**@ApiModelProperty(example = "User_data", value = "Defines the Granting Server -> AuthenticationType Provider name")*/
    @Column(unique = false)
    String authenticationName = GC_EMPTY_STRING

    @ManyToOne(fetch = FetchType.EAGER)
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

    @Column(nullable = false)
    Integer lockoutMaxAttemptCount = GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER

    @Column(nullable = false)
    Integer lockoutDurationSeconds = GC_AUTHENTICATION_LOCKOUT_DURATION_ZERO

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PublicFields", joinColumns = @JoinColumn(name = "authentication_id"))
    Set<String> mandatoryPublicFieldNames

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PrivateFields", joinColumns = @JoinColumn(name = "authentication_id"))
    Set<String> mandatoryPrivateFieldNames

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

    @Override
    AccessorType get_accessor_type() {
        return accessor
    }

    @Override
    String get_name() {
        return authenticationName
    }
}
