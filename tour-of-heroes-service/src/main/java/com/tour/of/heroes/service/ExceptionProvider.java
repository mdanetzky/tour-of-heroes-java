package com.tour.of.heroes.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by matthias on 02/03/2017.
 */
@Provider
public class ExceptionProvider implements ExceptionMapper<IllegalStateException> {

    @Override
    public Response toResponse(final IllegalStateException exception) {
        if ("NOT_FOUND".equals(exception.getMessage())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return null;
    }
}
