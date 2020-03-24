package com.njupt.hpc.edu.config;

import com.njupt.hpc.edu.common.components.RestAuthenticationEntryPoint;
import com.njupt.hpc.edu.user.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : molamola
 * @Project: edu
 * @Description: spring-security配置
 * @date : 2019-11-27 15:17
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/","/*.html","favicon.icon","/**/*.html","/**/*.css","/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/iconHeader/**",  //头像可访问
                        "/pmsTest/**", // 测试接口
                        "/webjars/springfox-swagger-ui/**")
                .permitAll()
                .antMatchers("/umsUser/login","/umsUser/refreshToken") //允许登录与刷新token
                .permitAll()
                .antMatchers("/show/**")   // 展示界面可访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
//                .antMatchers("/**")
//                .permitAll()
                .anyRequest()
                .authenticated();

        // 缓存禁用
        http.headers().cacheControl();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                // 未登录或token失效返回
                .authenticationEntryPoint(authenticationEntryPoint);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
