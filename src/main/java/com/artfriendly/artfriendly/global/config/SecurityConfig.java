package com.artfriendly.artfriendly.global.config;

import com.artfriendly.artfriendly.domain.auth.filter.JwtVerificationFilter;
import com.artfriendly.artfriendly.domain.auth.handler.MemberAccessDeniedHandler;
import com.artfriendly.artfriendly.domain.auth.handler.MemberAuthenticationEntryPoint;
import com.artfriendly.artfriendly.domain.auth.handler.OAuth2LoginFailureHandler;
import com.artfriendly.artfriendly.domain.auth.handler.OAuth2LoginSuccessHandler;
import com.artfriendly.artfriendly.domain.auth.jwt.JwtTokenizer;
import com.artfriendly.artfriendly.global.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    @Bean
    @Deprecated(forRemoval=true) // 향후 사라질 메서드들을 포함하고 있다.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource()) // cors 설정
                .and()
                .csrf().disable() // Csrf 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 이용하지 않는다(각 요청마다 사용자를 새롭게 인증한다)
                .and()
                .formLogin().disable()   // Form login 비활성화
                .httpBasic().disable()   // Header에 로그인 정보를 담는 방식 비활성화
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())  // AuthenticationException이 발생할 때 실행되는 핸들러
                .accessDeniedHandler(new MemberAccessDeniedHandler()) // 인증은 됐지만 권한이 없을 때 실행되는 핸들러
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/oauth/**").permitAll()
                        .requestMatchers("exhibitions/lists/popular/clear").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("apis/**").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().hasAnyAuthority("ROLE_USER")
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true); // 인증 정보 포함 가능
        configuration.setAllowedOriginPatterns(List.of("*")); // 이렇게 설정 시 모든 경로 CORS 허용 가능, 정규식 표현 가능. 좀 더 유연하게 도메인을 설정할 수 있다.
        configuration.setAllowedMethods(List.of("GET","POST", "PATCH", "DELETE")); // 허용된 HTTP 메서드
        configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
        configuration.setExposedHeaders(List.of("*")); // 모든 응답 헤더 표시

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {  // 커스텀 필터
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils); // JWT 검증 필터 선언

            builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class); // 필터에 JWT 검증 필터를 추가한다(JWT 인증 필터 다음에 실행된다)
        }
    }
}
