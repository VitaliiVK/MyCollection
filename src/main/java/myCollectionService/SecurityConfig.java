package myCollectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration //Spring security Configuration
@EnableWebSecurity // Enable Spring security - framework for authorization
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired//auto initialization
    private UserDetailsService userDetailsService; //адапетр между нашей учетной записью и spring учетной записью

    @Autowired//auto initialization
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService) // We specify the class adapetr between our Account and spring Account
            .passwordEncoder(getShaPasswordEncoder()); //Bin to encode password before writing to the database
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //Spring security configure
        http
                .csrf().disable() //disable csrf
                .authorizeRequests()
                .antMatchers("/").permitAll() //root page / and /public is available to all users
                .antMatchers("/add_collection").hasAnyRole("USER", "ADMIN") //page available for authorize user only
                .antMatchers("/add_instance").hasAnyRole("USER", "ADMIN") //page available for authorize user only
                .antMatchers("/update_remove_instance").hasAnyRole("USER", "ADMIN") //page available for authorize user only
                .antMatchers("/my_collections").hasAnyRole("USER", "ADMIN") //page available for authorize user only
                .antMatchers("/my_profile").hasAnyRole("USER", "ADMIN") //page available for authorize user only
                .antMatchers("/admin").hasRole("ADMIN") // page available to ADMIN only
                .and() //
        .exceptionHandling().accessDeniedPage("/unauthorized") // unauthorized request page
                .and() //
        .formLogin() //login
                .loginPage("/login") // login page
                .loginProcessingUrl("/j_spring_security_check") // URL for rom on login page
                .failureUrl("/login?error") // page for wrong login or password (login page + "error" param)
                .usernameParameter("j_login") // field name in form on login page
                .passwordParameter("j_password") // field name in form on login page
                .permitAll() // login page is available to all users
                .and()
        .logout() //logout
                .permitAll() // logout page is available to all users
                .logoutUrl("/logout") // URL for rom on logout page
                .logoutSuccessUrl("/?logout") // after logout page (home page + "logout" param)
                .invalidateHttpSession(true); //destroy session after logout
    }

    @Bean //Bin to encode password before writing to the database
    public ShaPasswordEncoder getShaPasswordEncoder(){
        return new ShaPasswordEncoder();
    }
}
