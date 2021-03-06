package com.example.homework66.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.formLogin().loginPage("/login");
        http.formLogin().failureUrl("/login?error=true");

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .invalidateHttpSession(true);

        http.authorizeRequests()
                .antMatchers("/profile")
                .authenticated();

        http.authorizeRequests()
                .anyRequest()
                .permitAll();
    }

    // запрос для выборки пользователя
//    String fetchUsersQuery = "select email, password, enabled"
//            + "from customers"
//            + "where email=?";

    // запрос для ролей пользователя
//    String fetchRolesQuery = "select email, role"
//            + "from customers"
//            + "where email=?";


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception{

        String fetchUsersQuery = "select email, password, enabled"
                + " from customers"
                + " where email = ?";

        String fetchRolesQuery = "select email, role"
                + " from customers"
                + " where email = ?";

        auth.jdbcAuthentication()
                .usersByUsernameQuery(fetchUsersQuery)
                .authoritiesByUsernameQuery(fetchRolesQuery)
                .dataSource(dataSource);
    }
}
