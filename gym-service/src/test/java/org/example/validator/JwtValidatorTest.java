package org.example.validator;

import org.assertj.core.api.Assertions;
import org.example.exception.JwtException;
import org.example.service.core.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtValidatorTest {

    private JwtValidator testSubject;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    public void init() {
        testSubject = new JwtValidator(jwtService);
    }

    @Test
    public void testValidateBearerTokenWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateBearerToken(null))
            .isExactlyInstanceOf(JwtException.class);
        Mockito.verifyNoInteractions(jwtService);
    }

    @Test
    public void testValidateBearerTokenWhenDoesNotStartWithBearer() {
        Assertions.assertThatThrownBy(() -> testSubject.validateBearerToken("hjkasdhfa"))
            .isExactlyInstanceOf(JwtException.class);
        Mockito.verifyNoMoreInteractions(jwtService);
    }

    @Test
    public void testValidateBearerTokenWhenIsExpired() {

        // when
        Mockito.when(jwtService.isExpired("token")).thenReturn(true);

        // then
        Assertions.assertThatThrownBy(() -> testSubject.validateBearerToken("Bearer token"))
            .isExactlyInstanceOf(JwtException.class);

        Mockito.verifyNoMoreInteractions(jwtService);
    }

    @Test
    public void testValidateBearerTokenWhenValid() {
        // when
        Mockito.when(jwtService.isExpired("token")).thenReturn(false);
        Assertions.assertThatNoException().isThrownBy(() -> testSubject.validateBearerToken("Bearer token"));
        Mockito.verifyNoMoreInteractions(jwtService);
    }

    @Test
    public void testValidateJwtTokenWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateJwt(null))
            .isExactlyInstanceOf(JwtException.class);
    }

    @Test
    public void testValidateJwtTokenWhenExpired() {
        // when
        Mockito.when(jwtService.isExpired("token")).thenReturn(true);

        // then
        Assertions.assertThatThrownBy(() -> testSubject.validateJwt("token"))
            .isExactlyInstanceOf(JwtException.class);

        Mockito.verifyNoMoreInteractions(jwtService);
    }

    @Test
    public void testValidateJwtWhenValid() {
        // when
        Mockito.when(jwtService.isExpired("token")).thenReturn(false);
        Assertions.assertThatNoException().isThrownBy(() -> testSubject.validateJwt("token"));
        Mockito.verifyNoMoreInteractions(jwtService);
    }
}