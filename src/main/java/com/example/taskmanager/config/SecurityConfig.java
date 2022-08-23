package com.example.taskmanager.config;

import com.example.taskmanager.model.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = Role.RoleName.ADMIN.name();
    private static final String USER = Role.RoleName.USER.name();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers("/register", "/login").permitAll()
//                .antMatchers(HttpMethod.GET,
//                        "/converter").hasAnyAuthority(ADMIN, USER)
//                .antMatchers(HttpMethod.GET,
//                        "/h2-console").hasAuthority(ADMIN)
//                .antMatchers(HttpMethod.POST,
//                        "/converter").hasAnyAuthority(ADMIN, USER)
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .and()
                .csrf().disable();
    }
}
