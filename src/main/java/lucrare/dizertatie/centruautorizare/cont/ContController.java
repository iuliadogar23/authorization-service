package lucrare.dizertatie.centruautorizare.cont;

import lombok.extern.slf4j.Slf4j;
import lucrare.dizertatie.centruautorizare.dto.ContResponse;
import lucrare.dizertatie.centruautorizare.util.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/cont")
@Slf4j
public class ContController {

    @Autowired
    private ContService contService;


    @PostMapping("/login")
    public ResponseEntity<ContResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        log.info("Trying to login {}", authenticationRequest.getUsername());
        return ResponseEntity.ok(contService.signIn(authenticationRequest));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<ContResponse> validateToken(@RequestParam String token) {
        log.info("Trying to validate token {}", token);
        return ResponseEntity.ok(contService.validateToken(token));
    }

    @GetMapping(value = "/merge", produces = "application/json")
    public ResponseEntity<String> oareMerge()
    {
        return ResponseEntity.ok("merge frate jneboon?");
    }

}
