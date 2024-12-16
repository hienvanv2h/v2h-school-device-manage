package com.vanhuuhien99.school_device_management.advice;

import com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalTemplateStateAdvice {

    @ModelAttribute("globalState")
    public GlobalTemplateState globalState(Authentication authentication, HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return new GlobalTemplateState(authentication, csrfToken);
    }
}
