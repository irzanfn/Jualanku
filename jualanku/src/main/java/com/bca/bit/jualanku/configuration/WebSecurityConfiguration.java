package com.bca.bit.jualanku.configuration;

import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.security.MyUserDetailsService;
import com.bca.bit.jualanku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/forgotpassword").permitAll()
                .antMatchers("/resetpassword/**").permitAll()
                .antMatchers("/otp").permitAll()
                .antMatchers("/buyer/**").hasAuthority("BUYER")
                .antMatchers("/seller/**").hasAuthority("SELLER").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login")
                .failureHandler(new SimpleUrlAuthenticationFailureHandler() {

                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                        AuthenticationException exception) throws IOException, ServletException {
                        String email = request.getParameter("email");
                        Optional<User> optionalUser = userService.findUserByEmail(email);
                        if(optionalUser.isPresent()){
                            User user = optionalUser.get();
                            user.setAttempt(user.getAttempt() + 1);
                            if(user.getAttempt() >= 5){
                                user.setAccountNonBlocked("false");
                            }
                            userService.updateUser(user);
                        }
                        String error = exception.getMessage();
                        System.out.println("A failed login attempt with email: "
                                + email + ". Reason: " + error);
                        String message = null;
                        if(error.equals("UserDetailsService returned null, which is an interface contract violation") || error.equals("Bad credentials")){
                            message = "Email or Password invalid";
                        } else if (error.equals("User account is locked")) {
                            message = "Account is locked";
                        }
                        request.getSession().setAttribute("exception_message", message);
                        super.setDefaultFailureUrl("/login?error=true");
                        super.onAuthenticationFailure(request, response, exception);
                    }
                })
                .defaultSuccessUrl("/connector", true)
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/fonts/**", "/vendor/**",
                        "/images/**", "/upload/**", "/img/upload/**", "/static/img/upload/**", "/webjars/**");
    }

}
