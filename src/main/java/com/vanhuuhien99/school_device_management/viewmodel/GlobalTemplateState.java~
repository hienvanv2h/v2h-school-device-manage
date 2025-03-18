package com.vanhuuhien99.school_device_management.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;

@Getter
@AllArgsConstructor
public class GlobalTemplateState {

    // Thẻ input ẩn chứ CSRF token
    private CsrfHiddenInput csrfHiddenInput;

    // Kiểm tra quyền admin
    private boolean isAdmin;

    // Kiểm tra trạng thái xác thực (true nếu đã đăng nhập)
    private boolean isAuthenticated;

    // Username người dùng
    private String username;

    public GlobalTemplateState(Authentication authentication, CsrfToken csrfToken) {
        this.csrfHiddenInput = new CsrfHiddenInput(csrfToken);
        this.isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        this.isAuthenticated = authentication != null && authentication.isAuthenticated();
        this.username = authentication != null ? authentication.getName() : null;
    }
}
