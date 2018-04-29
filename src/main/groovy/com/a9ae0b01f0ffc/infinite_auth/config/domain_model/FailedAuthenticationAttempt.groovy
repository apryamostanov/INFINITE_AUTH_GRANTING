package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import groovy.transform.CompileStatic

import javax.persistence.*

/**
 * Log of <b>Failed</b> Authentication Attempts.<p>
 * Used in Common Authentication Workflow to:<p>
 * - Find out if Authentication Public Data record is locked out (i.e. too many wrong password login attempts for specific username)<p>
 * - To log Failed Authentication Attempt in case Authentication Public Data record is not yet locked out and the Authentication
 * Validation Module returns failure result of Authentication.<p><p>
 * Note: it is recommended to store this entity on a separate specially designed high-volume storage.<p>
 * The data can grow very large over the time (billions of records), thus appropriate partitioning of table is required, as well as
 * periodical archiving and cleanup (currently out of scope of the solution).<p>
 * Special attention has to be done to attack scenarios with overflooding of authentication data. Such scenarios
 * are subject of additional design and development within the solution.<p>
 * Specifically it is suggested that Authentication Modules should support Authentication indicator "isPublicDataValid" showing whether the Public Data
 * has been recognized by the System of Record (Authentication Provider) - therefore allowing to bypass logging deliberately wrong Public Data -
 * thus only logging attempts with wrong Private Data.<p>
 * (it will be a tradeoff between storage utilization for wrong Public Data - and additional load on Authentication Providers).<p><p>
 * Main recommendation is to completely delegate the lockout functionality to Authentication providers and use it on Authorization Server only
 * for those Authentication Modules, which do not support this on the side of System of Record. (e.g. DOB_data).<p>
 * Additional recommendation is to only have it enabled for Authentications used in L3 authentication only - i.e. Step Up Authorizations (updates, transfers, etc).
 *
 * */
@CompileStatic
@Entity
@Table(name = "FailedAuthenticationAttempts")
class FailedAuthenticationAttempt {

    /**
     *  Authentication Attempt id, unique generated identity field.
     *
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    /**
     *  Authentication Validation Module name - for which the authentication has failed.<p>
     *  Authentication locking happens on Authentication Name level - ignoring any Accessor overrides (i.e. not on
     *  Authentication ID level).
     *
     * */
    String authenticationName

    /**
     *  A uniformed unique identifier of Authentication Public Data - using configurable hashing algorithm (e.g. SHA256): <p>
     *  1 - Authentication Public Data Map is sorted by Keys (case sensitive)<p>
     *  2 - Values are getting delimiter escaped (e.g. if hash delimiter is "|" before hashing this symbols is replaced by replacement character)<p>
     *  3 - Same applies to "equal" sign ("=")<p>
     *  4 - Key value pairs get concatenated using delimiter and equal sign<p>
     *  5 - Resulting string is hashed using defined hashing algorithm (e.g. SHA256)<p><p>
     *
     *  Example:<p>
     *  - Authentication Public Data: <p>
     *  ["proxy_number": "39485734",<p>
     *  "otp_id": "23804=87",<p>
     *  "phone_number": "9715544|332211"]<p><p>
     *
     * - String before hashing:<p>
     * "|otp_id=23804{equals}87|phone_number=9715544{delimiter}332211|proxy_number=39485734|"
     * */
    String authenticationHash

    /**
     *  Server Time Zone attempt date (milliseconds precision)
     *
     * */
    Date attemptDate

    /**
     *  Internal transient field.
     *
     * */
    @Transient
    Integer previousAttemptCount

    /**
     *  Count of total invalid attempts including this one.<p>
     *  Once Lockout Duration Period expires, counting starts from Zero again.
     *
     * */
    Integer currentAttemptCount

    /**
     *  Internal transient field. Possible values: New, Failed
     *
     * */
    @Transient
    String status

}
