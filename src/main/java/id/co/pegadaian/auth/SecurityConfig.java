package id.co.pegadaian.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SQL_LOGIN = "select username, password, true as enabled "
            + "from s_user where username= ? ";
    private static final String SQL_PERMISSION = "select u.username, p.name "
            + "from s_user u "
            + "inner join s_user_role ur on u.id = ur.id_user "
            + "inner join s_role r on r.id = ur.id_role "
            + "inner join s_role_permission rp on rp.id_role = r.id "
            + "inner join s_permission p on p.id = rp.id_permission "
            + "where u.username = ?";

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(SQL_LOGIN)
                .authoritiesByUsernameQuery(SQL_PERMISSION);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    	.authorizeRequests()
    	.antMatchers("/css/**").permitAll()
    	.antMatchers("/public.html").permitAll()
        .anyRequest().authenticated()
        .and().formLogin().permitAll()
        .and().logout().permitAll();
//    	
//        http.antMatchers("/css/**").permitAll()
//                .formLogin().permitAll()
//                .and().logout()
//                .and().authorizeRequests().antMatchers("/public.html").permitAll()
//                .and().authorizeRequests().anyRequest().authenticated();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception{
        return super.userDetailsServiceBean();
    }
}
