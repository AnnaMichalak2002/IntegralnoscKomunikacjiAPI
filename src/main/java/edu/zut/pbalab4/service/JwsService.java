package edu.zut.pbalab4.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACVerifier;
import edu.zut.pbalab4.model.UpdateRequest;
import edu.zut.pbalab4.model.User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Service
public class JwsService {

    private static final String SECRET = "12345612345612345612345612345612";

    public boolean isValidJwsForUpdateRequest(UpdateRequest body, String jwsToken) {
        try {
            String expectedPayload = buildCanonicalJson(body);

            JWSObject jwsObject = JWSObject.parse(jwsToken);

            boolean verified = jwsObject.verify(new MACVerifier(SECRET.getBytes(StandardCharsets.UTF_8)));
            if (!verified) {
                return false;
            }

            String payload = jwsObject.getPayload().toString();
            return expectedPayload.equals(payload);
        } catch (Exception e) {
            return false;
        }
    }

    private String buildCanonicalJson(UpdateRequest body) {
        String requestId = body.getRequestHeader().getRequestId().toString();
        String sendDate = body.getRequestHeader().getSendDate()
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        User user = body.getUser();

        String citizenship = user.getCitizenship() != null
                ? user.getCitizenship().toString()
                : null;

        return "{"
                + "\"requestHeader\":{"
                + "\"requestId\":\"" + escape(requestId) + "\","
                + "\"sendDate\":\"" + escape(sendDate) + "\""
                + "},"
                + "\"user\":{"
                + "\"name\":\"" + escape(user.getName()) + "\","
                + "\"surname\":\"" + escape(user.getSurname()) + "\","
                + "\"age\":" + user.getAge() + ","
                + "\"personalId\":\"" + escape(user.getPersonalId()) + "\","
                + "\"citizenship\":\"" + escape(citizenship) + "\","
                + "\"email\":\"" + escape(user.getEmail()) + "\""
                + "}"
                + "}";
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}