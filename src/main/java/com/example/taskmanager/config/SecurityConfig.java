package com.example.taskmanager.config;

import com.example.taskmanager.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = Role.RoleName.ADMIN.name();
    private static final String USER = Role.RoleName.USER.name();

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
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
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
