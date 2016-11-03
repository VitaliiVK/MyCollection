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

@Configuration //конфигурация для работы Spring security
@EnableWebSecurity // включить Spring security - фреймворк для работы с авторизацией
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired//автоинициализация
    private UserDetailsService userDetailsService; //адапетр между нашей учетной записью и spring учетной записью

    @Autowired//автоинициализация
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService) //указываем класс адапетр между нашей учетной записью и spring учетной записью
                .passwordEncoder(getShaPasswordEncoder()); //и бин который будет хешировать пароли для хранения в базе
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //правила работы Spring security
        http
                .csrf().disable() //отключаем какуюто хрень
                .authorizeRequests() //все запросы к приложению должны быть авторизированы
                .antMatchers("/","/public/**").permitAll() //корневая страница доступна всем пользователям
                .antMatchers("/add_collection").hasAnyRole("USER", "ADMIN")
                .antMatchers("/add_instance").hasAnyRole("USER", "ADMIN")
                .antMatchers("/update_remove_instance").hasAnyRole("USER", "ADMIN")
                .antMatchers("/my_collections").hasAnyRole("USER", "ADMIN")
                .antMatchers("/my_profile").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin").hasRole("ADMIN") //доступ на секретную страничку админа только дял админа
                .and() //
        .exceptionHandling().accessDeniedPage("/unauthorized") // страничка для неавторизированного запроса
                .and() //
        .formLogin() //сравила логина
                .loginPage("/login") //указываем страничку логина
                .loginProcessingUrl("/j_spring_security_check") //ссылка куда форма должна передать логин и пароль
                .failureUrl("/login?error") //страничка если неправильный логин или пароль (таже страница + параметр error)
                .usernameParameter("j_login") // имена полей в форме
                .passwordParameter("j_password") // имена полей в форме
                .permitAll() // форма логина доступна для всех
                .and()
        .logout()
                .permitAll() // логаут доступен для всех для всех
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout") //сюда перейдем после выхода (таже страница + параметр logout)
                .invalidateHttpSession(true); //прибить сессию после логаута

    }

    @Bean
    public ShaPasswordEncoder getShaPasswordEncoder(){
        return new ShaPasswordEncoder();
    }
}
