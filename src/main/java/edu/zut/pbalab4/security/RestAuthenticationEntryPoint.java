package edu.zut.pbalab4.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.zut.pbalab4.model.Error;
import edu.zut.pbalab4.model.ResponseHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setRequestId(UUID.randomUUID());
        responseHeader.setSendDate(OffsetDateTime.now());

        Error error = new Error();
        error.setResponseHeader(responseHeader);
        error.setCode("UNAUTHORIZED");
        error.setMessage("Unauthorized or invalid token");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        objectMapper.writeValue(response.getOutputStream(), error);
    }
}