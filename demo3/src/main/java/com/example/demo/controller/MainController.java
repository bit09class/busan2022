package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.AuthRequestDto;
import com.example.demo.auth.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MainController {    
//	private final JwtTokenProvider jwtUtils;
//	private final AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/")
	public String index() {
		log.info("hello!");
		return "hello";
	}
	
	@PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequestDto authRequest) throws Exception {
        try {
           // 1. 인증 정보 가져온다.
           final Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

   			log.info(authentication.toString());
           // 2. 인증정보로 JWT 토큰 발행
           final String token = jwtUtils.generateToken(authentication);

           // 3. SecurityContext에 인증정보 Set
            SecurityContextHolder.getContext().setAuthentication(authentication);

           Map<String,Object> map = new HashMap<>();
           map.put("name", authentication.getName());
           map.put("authentication", authentication.getAuthorities()); // 권한목록
           map.put("token", token);
           log.info(map);
           return ResponseEntity.ok(map);
        } catch (DisabledException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch(BadCredentialsException ex){
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }catch (Exception ex) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }
}
