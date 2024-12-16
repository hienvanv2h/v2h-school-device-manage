package com.vanhuuhien99.school_device_management.viewmodel;

import gg.jte.Content;
import gg.jte.TemplateOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;

@RequiredArgsConstructor
public class CsrfHiddenInput implements Content {

    private final CsrfToken csrfToken;
    @Override
    public void writeTo(TemplateOutput templateOutput) {
        if(csrfToken != null) {
            templateOutput.writeContent("<input id=\"_csrf\" type=\"hidden\" name=\"%s\" value=\"%s\">"
                            .formatted(csrfToken.getParameterName(), csrfToken.getToken()));
        }
    }
}
