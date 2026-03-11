package com.example.authapp.exception;

import com.example.authapp.dto.ApiErrorResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.List;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof BusinessException businessException) {
            return buildResponse(
                    businessException.getStatusCode(),
                    "Business Error",
                    businessException.getMessage(),
                    null
            );
        }

        if (exception instanceof ConstraintViolationException violationException) {
            List<String> details = violationException.getConstraintViolations()
                    .stream()
                    .map(this::formatViolation)
                    .toList();

            return buildResponse(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "Validation Error",
                    "Erro de validação nos dados enviados",
                    details
            );
        }

        if (exception instanceof NotFoundException) {
            return buildResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "Not Found",
                    "Rota não encontrada",
                    null
            );
        }

        if (exception instanceof WebApplicationException webApplicationException) {
            int status = webApplicationException.getResponse().getStatus();

            return buildResponse(
                    status,
                    Response.Status.fromStatusCode(status) != null
                            ? Response.Status.fromStatusCode(status).getReasonPhrase()
                            : "HTTP Error",
                    webApplicationException.getMessage() != null && !webApplicationException.getMessage().isBlank()
                            ? webApplicationException.getMessage()
                            : "Erro na requisição",
                    null
            );
        }

        return buildResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Internal Server Error",
                "Ocorreu um erro inesperado no servidor",
                null
        );
    }

    private Response buildResponse(int status, String error, String message, List<String> details) {
        ApiErrorResponseDTO response = new ApiErrorResponseDTO(
                LocalDateTime.now(),
                status,
                error,
                message,
                uriInfo != null ? uriInfo.getPath() : "",
                details
        );

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(response)
                .build();
    }

    private String formatViolation(ConstraintViolation<?> violation) {
        return violation.getPropertyPath() + ": " + violation.getMessage();
    }
}