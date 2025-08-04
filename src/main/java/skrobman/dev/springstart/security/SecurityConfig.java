package skrobman.dev.springstart.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import skrobman.dev.springstart.service.Oauth2RegistrationService;
import org.springframework.security.web.SecurityFilterChain;
import skrobman.dev.springstart.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    private final Oauth2RegistrationService oAuth2UserService;

//    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/register", "/user/verify", "/user/register/resend-token").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .oauth2Login(form -> form
                        .loginPage("/login")
                        .userInfoEndpoint(endPoint -> endPoint.userService(oAuth2UserService))
                        // TODO: change defaultSuccessUrl bcs root is used for debugging!!!!!!!!!!!!!!!!!!!!!!!!!!
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                )
                //.csrf(csrf -> csrf.disable())
                .userDetailsService(customUserDetailsService);
        return http.build();
    }
}
