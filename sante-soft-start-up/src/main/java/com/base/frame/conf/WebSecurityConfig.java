package com.base.frame.conf;

import com.base.frame.socle.core.utils.ImmutableList;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private ProfilRepository profilRepository;
//    @Autowired
//    private HabilitationRepository habilitationRepository;

    @PostConstruct
    public void init() {

        try {
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
                .headers().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/javax/**", "/js/**", "/plugins/**", "/css/**", "/notifications/**").permitAll()
//                .antMatchers("/**", "/index").permitAll()
                .antMatchers("/javax/**", "/js/**", "/plugins/**", "/css/**", "/css/own/**").permitAll()
                .antMatchers("/dist/**", "/home", "/img/**", "**/js/**", "/js/vendor/**", "/plugins/**", "/src/**", "/assets/**").permitAll()
                .antMatchers("/password/**").permitAll()
                .antMatchers("/api/gestion/utilisateur/reinitPassword/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .permitAll();

    }

    /**
     * @Bean @Override public UserDetailsService userDetailsService() {
     * UserDetails user = User.withDefaultPasswordEncoder() .username("admin")
     * .password("admin") .roles("USER") .build();
     * <p>
     * return new InMemoryUserDetailsManager(user); }
     *
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/error",
                "/**/javax/**", "/**/js/**", "/**/plugins/**", "/**/css/**", "/**/assets/**", "/**/dist/**", "/**/src/**",
                 "/**/scss/**", "/**/scss/navs/**", "/**/scss/utilities/**", "/**/vendor/**", "/**/img/**", "**/vendor/chart/**", "**/js/demo/**"
        )
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html,css,scss}")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
