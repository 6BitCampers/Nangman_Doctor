package com._6bitcampers.nangman_doctor.config;

import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService.customOAuth2UserService;
import com._6bitcampers.nangman_doctor.servingPackage.jangwoo.login.loginService.userLoginAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class securityConfig{

    private final AuthenticationFailureHandler customFailureHandler;
    private final customOAuth2UserService customOAuth2UserService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerBuilder authenticationManagerBuilder, userLoginAuthenticationProvider userLoginAuthenticationProvider) throws Exception {

        //csrf 설정 해제(POST 전송 시 CSRF 토큰값을 요구하지만 해당 설정으로 잠깐 꺼둘수 있음)
        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((login) -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureHandler(customFailureHandler)
                        .permitAll());

        http
                .oauth2Login((oauth) -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint((userInfoEndpointConfig)-> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));

        http
                .httpBasic((basic) -> basic.disable());

        //경로에 대한 권한
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/my/**").authenticated()
                        .requestMatchers("/addinfo","addinfo/").hasRole("LOGINONLY")
                        .requestMatchers("pwreset/","/pwreset/").hasRole("RESET")
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc","/loginError").permitAll()
                        .anyRequest().permitAll());

        //세션 통제를 통한 다중 로그인 허가
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) //다중 로그인 허가 갯수
                        .maxSessionsPreventsLogin(false)); //다중 로그인 개수를 초과하였을 경우 처리 방법 true : 초과시 새로운 로그인 차단 false : 초과시 기존 세션 하나 삭제

        //세션 고정 보호
//        - sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
//        - sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
//        - sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());

        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}
