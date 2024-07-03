package com.baggujo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()throws Exception {
        //이미지,JS파일 리소스 사용가능하게함
        return (web) -> web.ignoring().requestMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //csrf 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                //특정 페이지에 대해 접근 제한 해제
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/**", "/register").permitAll())

                //폼 로그인 url 설정
                .formLogin(formLogin ->
                        formLogin
                            .loginPage("/login")
                            .permitAll())

                //로그아웃 시 세션 파기 설정
                .logout(logout ->
                        logout
                            .logoutSuccessUrl("/")
                                .invalidateHttpSession(true));

        return http.build();
    }

}
