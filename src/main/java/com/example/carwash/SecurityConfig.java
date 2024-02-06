package com.example.carwash;


import com.example.carwash.filter.JwtAuthenticationFilter;
import com.example.carwash.filter.JwtExceptionFilter;
import com.example.carwash.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/car/**").permitAll()
                .requestMatchers("/members/login").permitAll()
                .requestMatchers("/members/join").permitAll()
                .requestMatchers("/members/snslogin").permitAll()
                .requestMatchers("/members/refresh").permitAll()
                .requestMatchers("/members/me").permitAll()
                .requestMatchers("/members/duplicate").permitAll()
                .requestMatchers("/members/**").permitAll()
                .requestMatchers("/members/withDrawal").permitAll()
                .requestMatchers("/community/**").permitAll()
                .requestMatchers("/comment/**").permitAll()
                .requestMatchers("/favorite/**").permitAll()
                .requestMatchers("/s3/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter,JwtAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
