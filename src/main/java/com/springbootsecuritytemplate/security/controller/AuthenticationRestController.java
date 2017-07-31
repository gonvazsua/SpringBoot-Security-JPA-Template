package com.springbootsecuritytemplate.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springbootsecuritytemplate.dao.UserDao;
import com.springbootsecuritytemplate.models.User;
import com.springbootsecuritytemplate.security.jwt.JwtAuthenticationRequest;
import com.springbootsecuritytemplate.security.jwt.JwtAuthenticationResponse;
import com.springbootsecuritytemplate.security.jwt.JwtTokenUtil;
import com.springbootsecuritytemplate.security.jwt.JwtUser;

/**
 * @author gonzalo
 *
 */
@RestController
public class AuthenticationRestController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserDao userDao;

	/**
	 * @param authenticationRequest
	 * @return
	 * @throws AuthenticationException
	 */
	@RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws AuthenticationException {

		// Perform the security
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload password post-security so we can generate token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		// Load User for getting userId and save it into the token
		final User user = userDao.findByUsername(userDetails.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails, user.getId());

		// Return the token
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

		if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * @param user
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody User user, HttpServletResponse response) {

		JwtAuthenticationRequest authenticationRequest;

		try {

			if (user != null) {

				userDao.save(user);

				authenticationRequest = new JwtAuthenticationRequest(user.getUsername(), user.getPassword());

				return this.createAuthenticationToken(authenticationRequest);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				user = null;
			}

		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		return null;
	}

}
