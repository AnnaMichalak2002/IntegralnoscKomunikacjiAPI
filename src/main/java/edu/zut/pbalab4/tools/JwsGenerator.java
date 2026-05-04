package edu.zut.pbalab4.tools;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;

import java.nio.charset.StandardCharsets;

public class JwsGenerator {

    private static final String SECRET = "12345612345612345612345612345612";

    public static void main(String[] args) throws Exception {
        String payload = "{\"requestHeader\":{\"requestId\":\"bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb\",\"sendDate\":\"2026-04-23T10:00:00Z\"},\"user\":{\"name\":\"Anna\",\"surname\":\"Michalak\",\"age\":25,\"personalId\":\"92011165987\",\"citizenship\":\"PL\",\"email\":\"a_mich@gmail.com\"}}";

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        Payload jwsPayload = new Payload(payload);

        JWSObject jwsObject = new JWSObject(header, jwsPayload);
        jwsObject.sign(new MACSigner(SECRET.getBytes(StandardCharsets.UTF_8)));

        System.out.println("JWS:");
        System.out.println(jwsObject.serialize());
    }
}