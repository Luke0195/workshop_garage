package br.com.lucas.santos.workshop.infrastructure.adapters.cryphtography;

import br.com.lucas.santos.workshop.business.contractors.externalibs.cryptography.TokenGenerator;
import br.com.lucas.santos.workshop.domain.dto.response.AuthenticationResponseDto;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtAdapter implements TokenGenerator {

    private final JwtEncoder jwtEncoder;
    public JwtAdapter(JwtEncoder jwtEncoder){
        this.jwtEncoder = jwtEncoder;
    }
    @Override
    public AuthenticationResponseDto generateToken(String userId) {
        Instant now = Instant.now();
        int expiredIn = 60 * 60;
        JwtClaimsSet claimsSet = JwtClaimsSet.builder().issuer("workshop").subject(userId)
                .expiresAt(now.plus(expiredIn, ChronoUnit.HOURS))
                .issuedAt(now)
                .build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
        return new AuthenticationResponseDto(token, Long.parseLong(String.format("%d", expiredIn)));
    }
}
