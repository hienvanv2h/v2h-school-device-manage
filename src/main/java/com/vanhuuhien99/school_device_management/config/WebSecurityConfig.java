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
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                authorize ->
                        authorize
                                .requestMatchers("/", "/dashboard", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                                // USERS
                                .requestMatchers("/manage/users", "/manage/users/**").hasRole(Role.ADMIN)
                                // SUBJECT
                                .requestMatchers(HttpMethod.GET, "/dashboard/subjects").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/subjects/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/subjects/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/subjects/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/subjects/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                // SCHOOL CLASS
                                .requestMatchers(HttpMethod.GET, "/dashboard/classes").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/classes/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/classes/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/classes/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/classes/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                // TEACHER
                                .requestMatchers(HttpMethod.GET, "/dashboard/teachers").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/teachers/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/teachers/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/teachers/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/teachers/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                // DEVICE CATEGORY
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-categories").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-categories/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/device-categories/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/device-categories/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/device-categories/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                // DEVICE CATEGORY SUBJECT
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-category-subject").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-category-subject/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/device-category-subject/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/device-category-subject/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/device-category-subject/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                // DEVICE
                                .requestMatchers(HttpMethod.GET, "/dashboard/devices", "/dashboard/devices/api/**").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/devices/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/devices/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/devices/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/devices/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                // TEACHER ASSIGNMENT
                                .requestMatchers(HttpMethod.GET, "/dashboard/teacher-assignments/**").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/teacher-assignments/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/teacher-assignments/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/teacher-assignments/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                // SCHEDULE
                                .requestMatchers(HttpMethod.GET, "/dashboard/schedules/**").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/schedules/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/schedules/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/schedules/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                // DEVICE REGISTRATION
                                .requestMatchers(HttpMethod.GET,
                                        "/dashboard/device-registrations/register/**",
                                        "/dashboard/device-registrations/list/**").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.GET, "/dashboard/device-registrations/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.POST, "/dashboard/device-registrations/save/**").hasAnyRole(Role.ADMIN, Role.MANAGER, Role.USER)
                                .requestMatchers(HttpMethod.PUT, "/dashboard/device-registrations/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .requestMatchers(HttpMethod.DELETE, "/dashboard/device-registrations/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                // REPORTS
                                .requestMatchers("/reports/**").hasAnyRole(Role.ADMIN, Role.MANAGER)
                                .anyRequest().authenticated()
        )
        .formLogin(form -> form
                .loginPage("/login")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .failureUrl("/login?error=true")
                .permitAll()
        )
        .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
        )
        .exceptionHandling(exc -> exc
                .accessDeniedHandler(new CustomAccessDeniedHandler())   // 403
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
