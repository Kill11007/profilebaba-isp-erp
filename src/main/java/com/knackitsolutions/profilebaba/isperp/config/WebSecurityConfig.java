package com.knackitsolutions.profilebaba.isperp.config;

import com.knackitsolutions.profilebaba.isperp.filter.JwtRequestFilter;
import com.knackitsolutions.profilebaba.isperp.service.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableGlobalAuthentication
public class WebSecurityConfig{

  @Autowired
  private UserDetailsService jwtUserDetailsService;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /*@Bean("authenticationManager")
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }*/


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors().and().authorizeHttpRequests(
            authorize -> authorize.antMatchers("/authenticate", "/**/signup", "/**/validate-otp")
                .permitAll().anyRequest().authenticated()).csrf().disable().exceptionHandling(
            exceptions -> exceptions.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
/*
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    // We don't need CSRF for this example
    httpSecurity.csrf().disable()
        // dont authenticate this particular request
        .authorizeRequests().antMatchers("/authenticate/**").permitAll().
        // all other requests need to be authenticated
            anyRequest().authenticated().and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).
        // make sure we use stateless session; session won't be used to
        // store user's state.
            exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Add a filter to validate the tokens with every request
//    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }*/

  /*@Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }*/

  @Bean
  public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
    return authentication -> {
      UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(
          (String) authentication.getPrincipal());
      if (!userDetails.isEnabled()){
        throw new DisabledException("Phone number is not verified");
      }
      boolean matches = passwordEncoder.matches((String) authentication.getCredentials(),
          userDetails.getPassword());
      if (!matches) {
        throw new BadCredentialsException("Username/Password is not correct");
      }
      return new UsernamePasswordAuthenticationToken(userDetails, null);
    };
  }
}
