package lucrare.dizertatie.centruautorizare.cont;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lucrare.dizertatie.centruautorizare.dto.ContResponse;
import lucrare.dizertatie.centruautorizare.exception.AppException;
import lucrare.dizertatie.centruautorizare.util.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class ContService {

    private final ContRepository contRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public ContResponse signIn(AuthenticationRequest credentialsDto) {
        Cont cont = contRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        if (cont.getPassword().equals(credentialsDto.getPassword()))
        {
            ContResponse contResponse = new ContResponse();
            contResponse.setId(cont.getId());
            contResponse.setLogin(cont.getUsername());
            contResponse.setToken(createToken(cont));
            return contResponse;
        }

//        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), cont.getPassword())) {
//            return contMapper.toUserDto(cont, createToken(cont));
//        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public ContResponse validateToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        Cont contOptional = contRepository.findByUsername(username).orElse(null);

        if (contOptional == null) {
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }
        ContResponse contResponse = new ContResponse();
        contResponse.setId(contOptional.getId());
        contResponse.setLogin(contOptional.getUsername());
        contResponse.setToken(createToken(contOptional));

        return contResponse;
    }

    private String createToken(Cont cont) {
        Claims claims = Jwts.claims().setSubject(cont.getUsername());

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000*12);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
