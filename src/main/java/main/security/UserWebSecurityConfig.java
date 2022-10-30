package main.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@Component
public class UserWebSecurityConfig extends WebSecurityConfigurerAdapter {

    UserDetailsService appDetailsService;
    PasswordEncoder passwordEncoder;

    public UserWebSecurityConfig(UserDetailsService appDetailsService, PasswordEncoder passwordEncoder) {
        this.appDetailsService = appDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // so we can make POST request from front-end
                .csrf().disable()
                // what request will be accessible
                .authorizeRequests()
                // Request from the paths below have the permission to be accessed without the need of login from user
                .antMatchers("/admin","/general","/walkers", "/users", "/dogs**","/orders**").permitAll()
                // any other user need to be authenticated with authentication and httpBasic
                .anyRequest().authenticated().and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(appDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        auth.authenticationProvider(provider);
    }
}
