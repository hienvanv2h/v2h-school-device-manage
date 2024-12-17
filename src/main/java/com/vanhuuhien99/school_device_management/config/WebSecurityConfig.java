package com.vanhuuhien99.school_device_management.config;

import com.vanhuuhien99.school_device_management.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize ->
                        authorize
                                .requestMatchers("/", "/dashboard", "/login", "/register").permitAll()
                                // SUBJECT
                                .requestMatchers(HttpMethod.GET, "/dashboard/subjects").hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/subjects/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.POST, "/dashboard/subjects/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/subjects/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/subjects/**").hasRole(Role.ADMIN)
                                // SCHOOL CLASS
                                .requestMatchers(HttpMethod.GET, "/dashboard/classes").hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/classes/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.POST, "/dashboard/classes/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/classes/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/classes/**").hasRole(Role.ADMIN)
                                // TEACHER
                                .requestMatchers(HttpMethod.GET, "/dashboard/teachers").hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/teachers/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.POST, "/dashboard/teachers/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/teachers/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/teachers/**").hasRole(Role.ADMIN)
                                // DEVICE CATEGORY
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-categories").hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-categories/**").hasRole(Role.ADMIN)
//                                .requestMatchers(HttpMethod.GET, "/dashboard/device-categories/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/dashboard/device-categories/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/device-categories/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/device-categories/**").hasRole(Role.ADMIN)
                                // DEVICE CATEGORY SUBJECT
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-category-subject").hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-category-subject/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.POST, "/dashboard/device-category-subject/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/device-category-subject/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/device-category-subject/**").hasRole(Role.ADMIN)
                                // DEVICE
                                .requestMatchers(HttpMethod.GET, "/dashboard/devices", "/dashboard/devices/api/**").hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/devices/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.POST, "/dashboard/devices/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/devices/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/devices/**").hasRole(Role.ADMIN)
                                // TEACHER ASSIGNMENT
                                .requestMatchers(HttpMethod.GET, "/dashboard/teacher-assignments/**").hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/teacher-assignments/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/teacher-assignments/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/teacher-assignments/**").hasRole(Role.ADMIN)
                                // SCHEDULE
                                .requestMatchers(HttpMethod.GET, "/dashboard/schedules/**").hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/schedules/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/schedules/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/schedules/**").hasRole(Role.ADMIN)
                                // DEVICE REGISTRATION
                                .requestMatchers(HttpMethod.GET,
                                        "/dashboard/device-registrations/register/**",
                                        "/dashboard/device-registrations/list/**").hasAnyRole(Role.ADMIN,Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-registrations/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.POST, "/dashboard/device-registrations/save/**").hasAnyRole(Role.ADMIN, Role.USER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/device-registrations/**").hasRole(Role.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/device-registrations/**").hasRole(Role.ADMIN)
                                // REPORTS
                                .requestMatchers("/reports/**").hasRole(Role.ADMIN)
                                .anyRequest().authenticated()
        )
        .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
        )
        .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
