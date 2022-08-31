package com.blogapplication.blogappapis.config;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blogapplication.blogappapis.security.CustomUserDetailService;
import com.blogapplication.blogappapis.security.JwtAuthenticationEntryPoint;
import com.blogapplication.blogappapis.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_URLS = { 
        "/api/v1/auth/**", 
        "/v2/api-docs", 
        "/v3/api-docs", 
        "/swagger-resources/**", 
        "/swagger-ui/**", 
        "/webjars/**", 
    };

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeHttpRequests()
        .antMatchers(PUBLIC_URLS).permitAll()
        .antMatchers(HttpMethod.GET).permitAll()
        .anyRequest()
        .authenticated()
        .and().exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // passwordEncoder Bean is defined at the starting of this class.
        auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
