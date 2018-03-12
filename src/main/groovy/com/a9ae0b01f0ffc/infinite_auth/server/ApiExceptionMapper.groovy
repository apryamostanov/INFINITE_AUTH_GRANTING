package com.a9ae0b01f0ffc.infinite_auth.server;

import com.a9ae0b01f0ffc.infinite_auth.server.ApiException;
import org.glassfish.jersey.internal.Errors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    public Response toResponse(ApiException ex) {
        return Response.status(ex.getStatus())
                .entity(new ApiErrorMessage(ex))
                .type(MediaType.APPLICATION_JSON).
                        build();
    }

}