package com.example.taskmanager.config;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.security.jwt.JwtConfigurer;
import com.example.taskmanager.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = Role.RoleName.ADMIN.name();
    private static final String USER = Role.RoleName.USER.name();

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/register", "/login").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/tasklists/**").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.PUT,
                        "/tasklists/**").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.POST,
                        "/tasklists/**").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.GET,
                        "/tasks/**").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.PUT,
                        "/tasks/**").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.POST,
                        "/tasks/**").hasAnyAuthority(ADMIN, USER)
                .antMatchers(HttpMethod.GET,
                        "/users/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT,
                        "/users/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.POST,
                        "/users/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.GET,
                        "/statuses/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT,
                        "/statuses/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.POST,
                        "/statuses/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.GET,
                        "/priorities/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT,
                        "/priorities/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.POST,
                        "/priorities/**").hasAuthority(ADMIN)
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .headers().frameOptions().disable();
    }
}
