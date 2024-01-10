package chathealth.chathealth.config;

import chathealth.chathealth.service.AuthService;
import chathealth.chathealth.service.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2DetailsService oAuth2DetailsService;
    private final AuthService authService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((auth)->auth
                        .requestMatchers("/","/auth/join","/auth/userjoin","/auth/entjoin","/auth/login","/auth/confirmEmail",
                                         "/board","/board/{id}",
                                         "/post","/api/post", "/api/post/best",
                                         "/error"
                                         "/css/**","/js/**","/img/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // admin role 가지고 있는 사람만 허용
                        .anyRequest()
                        .authenticated())
          
                .formLogin((form)->form  //login settings
                        .loginPage("/auth/login")   // get
                        .usernameParameter("email")
                        .passwordParameter("pw")
                        .loginProcessingUrl("/auth/login")  //post
                        .defaultSuccessUrl("/",true)
                        .permitAll()
                )
                .logout(logout-> //logout settings
                            logout .deleteCookies("JSESSIONID")
                                   .logoutUrl("/auth/logout")
                                    .logoutSuccessHandler(
                                            (((request, response, authentication) -> {
                                                String redirectUrl = request.getHeader("Referer");
                                                response.sendRedirect(redirectUrl ==null? "/" : redirectUrl);
                                            }))
                                    ))

                .sessionManagement((auth)->auth
                        .maximumSessions(1)  //한 아이디로 중복 로그인 방지
                        .maxSessionsPreventsLogin(true)) //다중 로그인 허용치 초과시 새 로그인 차단. false는 기존 세션 삭제

                .oauth2Login((oauth2Login) -> oauth2Login //소셜 로그인 허용
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/",true)
                        .userInfoEndpoint((userInfo) -> userInfo
                                .userService(oAuth2DetailsService)
                        )
                )
                .csrf((csrf)->  csrf.disable());
        return http.build();
    }
}