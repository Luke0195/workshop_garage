package br.com.lucas.santos.workshop.utils;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class RequestUtil {

    private RequestUtil() {}

    public static RequestBuilder request(HttpMethod method, String urlTemplate, String token, String body, Object... uriVars) {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
            .request(method, urlTemplate, uriVars)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON);

        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }

        if (body != null && !body.isBlank()) {
            builder.content(body);
        }

        return builder;
    }

    public static RequestBuilder request(HttpMethod method, String urlTemplate, String token, Object... uriVars) {
        return request(method, urlTemplate, token, null, uriVars);
    }

    public static RequestBuilder request(HttpMethod method, String urlTemplate, Object... uriVars) {
        return request(method, urlTemplate, null, null, uriVars);
    }
}
