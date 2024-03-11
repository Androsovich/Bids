package org.androsovich.applications.feign.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.androsovich.applications.entities.embeddeds.Phone;
import org.androsovich.applications.exceptions.BadFeignRequestException;
import org.androsovich.applications.exceptions.PhoneException;
import org.androsovich.applications.feign.config.PhoneConfig;
import org.androsovich.applications.utils.PhoneUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.androsovich.applications.feign.clients.DadataMocks.setupMockDadataResponse;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PhoneConfig.class, WireMockConfig.class})
public class PhoneClientIntegrationTest {
    @Autowired
    private WireMockServer mockDadataService;

    @Autowired
    private PhoneClient phoneClient;

    @BeforeEach
    void setUp() throws IOException {
        setupMockDadataResponse(mockDadataService);
    }

    @Test
    void testValidRequestPhoneNumber() {
        Phone[] phones = phoneClient.checkPhone("[\"+7 911 243-45-76\"]");
        assertEquals(1, phones.length);
    }

    @Test
    void testNotValidRequestPhoneNumberExpectedException() {
        assertThrows(BadFeignRequestException.class, () -> phoneClient.checkPhone("eqwqqw"));
    }

    @Test
    void testValidRequestPhoneNotMobileExpectedException() {
        assertThrows(PhoneException.class, () ->
                PhoneUtils.processingResponse(phoneClient.checkPhone("[\"+7 495 456-55-77\"]")));
    }

    @Test
    void checkFieldsPhoneAfterDecodeJsonResponse() {
        Phone phone = PhoneUtils.processingResponse(phoneClient.checkPhone("[\"+7 911 243-45-68\"]"));
        assertEquals("+7 911 243-45-68", phone.getPhone());
        assertEquals("7", phone.getCountryCode());
        assertEquals("911", phone.getCityCode());
    }
}
