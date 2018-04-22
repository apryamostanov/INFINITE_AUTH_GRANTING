package com.a9ae0b01f0ffc.infinite_auth.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AccessorType

interface I_overridable_by_accessor {

    AccessorType get_accessor_type()

    String get_name()

}