package com.openvehicletracking.restapi.config;

import com.openvehicletracking.restapi.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Http401AuthenticationEntryPoint http401AuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter authenticationFilter;

    @Autowired
    public RestApiSecurityConfig(Http401AuthenticationEntryPoint http401AuthenticationEntryPoint, UserDetailsService userDetailsService, JwtAuthenticationFilter authenticationFilter) {
        this.http401AuthenticationEntryPoint = http401AuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SHA1PasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling()
            .authenticationEntryPoint(http401AuthenticationEntryPoint)
        .and()
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/user/access-token").permitAll()
            .antMatchers("/test-resource/**").permitAll()
            .antMatchers("/**")
            .authenticated()
        .and()
            .apply(securityConfigurer());
    }

    private JwtConfigurer securityConfigurer() {
        return new JwtConfigurer(authenticationFilter);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public DeviceAuthorityChecker deviceAuthorityChecker() {
        return new DeviceAuthorityCheckerImpl();
    }
}
