package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.viewmodel.CsrfHiddenInput;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "pages/index";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            Model model,
            HttpServletRequest request
    ) {
        if(error != null)  {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu.");
        }
        // Thêm CSRF token cho model
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        CsrfHiddenInput csrfHiddenInput = new CsrfHiddenInput(csrfToken);
        model.addAttribute("csrfHiddenInput", csrfHiddenInput);
        return "pages/login";
    }
}
