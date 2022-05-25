package lucrare.dizertatie.centruautorizare.cont;

import lucrare.dizertatie.centruautorizare.cont.detalii.UserDetailsServiceImpl;
import lucrare.dizertatie.centruautorizare.jwt.JwtTokenProvider;
import lucrare.dizertatie.centruautorizare.util.AuthenticationRequest;
import lucrare.dizertatie.centruautorizare.util.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/cont")
public class ContController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ContRepository contRepository;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtTokenProvider.generateToken(userDetails));

        return ResponseEntity.ok(authenticationResponse);
    }

    @GetMapping(value = "/merge", produces = "application/json")
    public ResponseEntity<String> oareMerge()
    {
        return ResponseEntity.ok("merge frate jneboon?");
    }

}
