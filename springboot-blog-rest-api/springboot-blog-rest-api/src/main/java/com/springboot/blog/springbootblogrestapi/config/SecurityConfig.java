package com.springboot.blog.springbootblogrestapi.config;

import com.springboot.blog.springbootblogrestapi.security.JwtAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import  org.springframework.security.core.userdetails.UserDetailsService;
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private  UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    public  SecurityConfig(UserDetailsService userDetailsService,
                           JwtAuthenticationEntryPoint authenticationEntryPoint){
        this.userDetailsService =userDetailsService;
        this.authenticationEntryPoint=authenticationEntryPoint;
    }
   @Bean
    public static PasswordEncoder passwordEncoder(){

        return  new BCryptPasswordEncoder();
    }

    @Bean
 public  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

       return configuration.getAuthenticationManager();
 }


 @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                   // authorize.anyRequest().authenticated();
                    authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());

        return http.build();
    }
/*
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails bora = User.builder()
                .username("bora")
                .password(passwordEncoder().encode("bora"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(bora,admin);



    }
    */

}

