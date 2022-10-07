package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.components.JwtTokenUtil;
import br.com.segment.leosmusicstoreapi.components.SegmentHelper;
import br.com.segment.leosmusicstoreapi.dtos.CustomerPostDto;
import br.com.segment.leosmusicstoreapi.dtos.outputs.UserOutput;
import br.com.segment.leosmusicstoreapi.helpers.MapsHelper;
import br.com.segment.leosmusicstoreapi.models.Customer;
import br.com.segment.leosmusicstoreapi.models.auth.JwtRequest;
import br.com.segment.leosmusicstoreapi.models.auth.JwtResponse;
import br.com.segment.leosmusicstoreapi.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

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
    private SegmentHelper segmentHelper;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserOutput userDetails = (UserOutput) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        segmentHelper.identifyEvent(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody CustomerPostDto user)
            throws InvocationTargetException, IllegalAccessException {
        Customer newCustomer = userDetailsService.createNewCustomer(user);

        segmentHelper.trackEvent("New Customer Registered",
                newCustomer.getId().toString(),
                MapsHelper.objectToMap(newCustomer, 0));

        return ResponseEntity.ok(newCustomer);
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
