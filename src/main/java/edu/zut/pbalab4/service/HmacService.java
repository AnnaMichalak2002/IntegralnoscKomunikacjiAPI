package edu.zut.pbalab4.service;

import edu.zut.pbalab4.model.CreateRequest;
import edu.zut.pbalab4.model.User;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.format.DateTimeFormatter;

@Service
public class HmacService {

    private static final String SECRET = "123456";
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    public String calculateHmacForCreateRequest(CreateRequest body) {
        try {
            String json = buildCanonicalJson(body);

            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    SECRET.getBytes(StandardCharsets.UTF_8),
                    HMAC_ALGORITHM
            );
            mac.init(secretKeySpec);

            byte[] hmacBytes = mac.doFinal(json.getBytes(StandardCharsets.UTF_8));
            return toHex(hmacBytes);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to calculate HMAC signature", e);
        }
    }

    public boolean isValidCreateRequestSignature(CreateRequest body, String providedSignature) {
        String calculatedSignature = calculateHmacForCreateRequest(body);

        return MessageDigest.isEqual(
                calculatedSignature.getBytes(StandardCharsets.UTF_8),
                providedSignature.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String buildCanonicalJson(CreateRequest body) {
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
                + "\"id\":\"" + escape(user.getId().toString()) + "\","
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

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}