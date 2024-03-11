package org.androsovich.applications.feign.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.ContainsPattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class DadataMocks {
    public static void setupMockDadataResponse(WireMockServer mockService) throws IOException {
        StringValuePattern requestBodyPattern = new ContainsPattern("[\"+7 911 243-45-76\"]");
        mockService.stubFor(
                WireMock.post(WireMock.urlEqualTo("https://cleaner.dadata.ru/api/v1/clean/phone"))
                        .withRequestBody(matching("^\\[\\\"[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*\\\"]$"))
                        .withHeader("X-Secret", equalTo("2813e363144a825e98f90afb52f0404d3aa94a43"))
                        .withHeader("Authorization", equalTo("Token " + "f50daf4ce27a8db156f66514b2d76b3126082188"))
                        .withHeader("Content-Type", equalTo("application/json"))
                        .withHeader("charset", equalTo("utf-8"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(
                                        copyToString(
                                                DadataMocks.class.getClassLoader().getResourceAsStream("payload/phone-response.json"),
                                                defaultCharset()))));
    }
}


