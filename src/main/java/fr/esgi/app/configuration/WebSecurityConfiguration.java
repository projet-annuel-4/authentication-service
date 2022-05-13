package fr.esgi.app.configuration;

import fr.esgi.app.security.oauth2.CustomOAuth2UserService;
import fr.esgi.app.security.JwtConfigurer;
import fr.esgi.app.security.oauth2.OAuth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;
    private final OAuth2SuccessHandler oauthSuccessHandler;
    private final CustomOAuth2UserService oAuth2UserService;

    public WebSecurityConfiguration(JwtConfigurer jwtConfigurer, @Lazy OAuth2SuccessHandler oauthSuccessHandler,@Lazy CustomOAuth2UserService oAuth2UserService) {
        this.jwtConfigurer = jwtConfigurer;
        this.oauthSuccessHandler = oauthSuccessHandler;
        this.oAuth2UserService = oAuth2UserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable().authorizeRequests()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**/api/v1/auth/**",
                        "/**/api/v1/auth/login",
                        "/**/api/v1/registration",
                        "/websocket", "/websocket/**",
                        "/img/**",
                        "/static/**").permitAll()
                .antMatchers("/auth/**", "/oauth2/**", "/**/*swagger*/**", "/v2/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorize")
                .and()
                .userInfoEndpoint().userService(oAuth2UserService)
                .and()
                .successHandler(oauthSuccessHandler)
                .and()
                .apply(jwtConfigurer);
    }
    /**
     * Configuration principale du coeur d'application
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        //ici on autorise ici toute les origines par defaut. celle-ci seront filtrées ensuite via le allowedOriginFilter ce qui permet d'ajotuer des allowedOrigin en runtime
        corsConfiguration.setAllowedOrigins(Collections.unmodifiableList(Collections.singletonList("*")));
        corsConfiguration.setAllowedMethods(
                Collections.unmodifiableList(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"))
        );
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        corsConfiguration.setAllowCredentials(false);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        corsConfiguration.setAllowedHeaders(Collections.unmodifiableList(Collections.singletonList("*")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        source.registerCorsConfiguration("/auth/api/**", corsConfiguration);
        source.registerCorsConfiguration("/chat/api/**", corsConfiguration);
        return source;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
