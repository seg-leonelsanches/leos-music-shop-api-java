package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.components.JwtTokenUtil;
import br.com.segment.leosmusicstoreapi.dtos.CustomerPostDto;
import br.com.segment.leosmusicstoreapi.dtos.outputs.UserOutput;
import br.com.segment.leosmusicstoreapi.models.auth.JwtRequest;
import br.com.segment.leosmusicstoreapi.models.auth.JwtResponse;
import br.com.segment.leosmusicstoreapi.services.JwtUserDetailsService;
import com.segment.analytics.Analytics;
import com.segment.analytics.messages.IdentifyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private Analytics analytics;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserOutput userDetails = (UserOutput) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        Map<String, String> map = new HashMap<>();
        map.put("firstName", userDetails.getFirstName());
        map.put("lastName", userDetails.getLastName());
        map.put("email", userDetails.getUsername());

        analytics.enqueue(IdentifyMessage.builder()
                .userId(userDetails.getId().toString())
                .traits(map));

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody CustomerPostDto user) {
        return ResponseEntity.ok(userDetailsService.createNewCustomer(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
