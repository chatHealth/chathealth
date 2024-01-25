package chathealth.chathealth.config;

import chathealth.chathealth.handler.CustomAuthenticationEntryPoint;
import chathealth.chathealth.handler.CustomDeniedHandler;
import chathealth.chathealth.handler.UserLoginFailHandler;
import chathealth.chathealth.service.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2DetailsService oAuth2DetailsService;
    private final UserLoginFailHandler userLoginFailHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomDeniedHandler customDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/auth/selection", "/auth/userjoin", "/auth/entjoin", "/auth/login", "/auth/confirmEmail",
                                "/board", "/board/{id}", "/board/api", "/board/api/recent",
                                "/post", "/api/post", "/api/post/best", "/api/post/best-week", "/api/post/recent","/post/write",
                                "/board-comment/{id}",
                                "/error",
                                "/css/**", "/js/**", "/img/**",
                                 "/board-image/print","/post-img/**","/profile/**"
                                    ,"/view/**", "/review/**",
                                "/auth/login-check", "/auth/is-user", "/auth/is-ent",
                                "/noty/subscribe")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // admin role 가지고 있는 사람만 허용
                        .anyRequest()
                        .authenticated())

                .formLogin((form) -> form  //login settings
                        .loginPage("/auth/login")   // get
                        .usernameParameter("email")
                        .passwordParameter("pw")
                        .loginProcessingUrl("/auth/login")  //post
                        .defaultSuccessUrl("/",true)
                        .failureHandler(userLoginFailHandler)
                        .permitAll()
                )
                .logout(logout-> //logout settings
                            logout .deleteCookies("JSESSIONID")
                                   .logoutUrl("/auth/logout")
                                    .logoutSuccessHandler(
                                            (((request, response, authentication) -> {
                                                response.sendRedirect("/" );
                                            }))
                                    )
                                    .invalidateHttpSession(true)
                )

                .sessionManagement((auth) -> auth
                        .maximumSessions(1)  //한 아이디로 중복 로그인 방지
                        .maxSessionsPreventsLogin(false)) //다중 로그인 허용치 초과시 새 로그인 차단. false는 기존 세션 삭제

                .oauth2Login((oauth2Login) -> oauth2Login //소셜 로그인 허용
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint((userInfo) -> userInfo
                                .userService(oAuth2DetailsService)
                        )
                )

                //예외 처리
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customDeniedHandler)
                )

                .csrf((csrf)->  csrf.disable());
        return httpSecurity.build();
    }
}