package com.baggujo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()throws Exception {
        //이미지,JS파일 리소스 사용가능하게함
        return (web) -> web.ignoring().requestMatchers("/favicon.ico", "resources/**", "/error");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //csrf 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                //특정 페이지에 대해 접근 제한 해제
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                //.requestMatchers("/member/login", "/register", "/fragment/**").permitAll())
                                //.requestMatchers("/").authenticated())
                                .requestMatchers("/**").permitAll())
                //폼 로그인 url 설정
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/member/login")
                                .loginProcessingUrl("/loginProcess")
                                .permitAll())

                //로그아웃 시 세션 파기 설정
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true));

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setUseReferer(true); // 리퍼러 사용 설정
        return handler;
    }
}
