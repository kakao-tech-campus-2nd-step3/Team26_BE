package org.ktc2.cokaen.wouldyouin._common.config;

import lombok.AllArgsConstructor;
import org.ktc2.cokaen.wouldyouin.auth.application.CustomAccessDeniedHandler;
import org.ktc2.cokaen.wouldyouin.auth.application.CustomAuthenticationEntryPoint;
import org.ktc2.cokaen.wouldyouin.auth.application.CustomUserDetailsService;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtAuthFilter;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    private static final String[] AUTH_WHITELIST = {
        "/**", //임시 설정, 나중에 제거필요
        "/h2-console/**",
        "/swagger-ui/**",
        "/swagger-ui-custom.html",
        "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {

        // CSRF, CORS 설정
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        // H2 접속 위한 설정
        http.headers(headers -> headers.addHeaderWriter(
            new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));

        // 세션 비활성화
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS));

        // Form 로그인, BasicHttp 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(new JwtAuthFilter(customUserDetailsService, jwtService), UsernamePasswordAuthenticationFilter.class);

        // 예외 핸들링 추가
        http.exceptionHandling((exceptionHandling) -> exceptionHandling
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler));

        // 권한 규칙 작성
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(AUTH_WHITELIST).permitAll());

        return http.build();
    }

    //패스워드 인코더
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // 정적 리소스를 spring security 대상에서 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
            .ignoring()
            .requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
            );
    }
}