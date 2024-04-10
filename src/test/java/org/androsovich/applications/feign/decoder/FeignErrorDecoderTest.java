package org.androsovich.applications.feign.decoder;

import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import feign.Response;
import org.mockito.MockitoSession;

import static org.androsovich.applications.constants.Constants.GENERIC_ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FeignErrorDecoderTest {

    @Mock
    Response response;
    private MockitoSession session;

    @BeforeEach
    void setUp() {
        session = Mockito.mockitoSession().initMocks(this).startMocking();
    }

    @AfterEach
    public void afterTest() {
        session.finishMocking();
    }

    @Test
    void decodeExpectedException() {
        ErrorDecoder decoder = new FeignErrorDecoder();
        Mockito.when(response.status()).thenReturn(500);
        Mockito.when(response.reason()).thenReturn("");
        Exception exception = decoder.decode("", response);

        assertThat(exception.getMessage(), is(GENERIC_ERROR));
    }
}