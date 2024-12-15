package com.vanhuuhien99.school_device_management.advice;

import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex, Model model, HttpServletRequest request) {
        // Lấy tên template được truyền từ Controller (nếu có)
        String templateName = (String) request.getAttribute("templateName");
        if(templateName != null) {
            List<String> errors = List.of(ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
            model.addAttribute("errors", errors);
            return templateName;
        }
        // Nếu không có templateName thì trả về trang lỗi mặc định
        model.addAttribute("errorMessage", "Lỗi ràng buộc dữ liệu xảy ra. Vui lòng kiểm tra lại thông tin.");
        model.addAttribute("errorDetails", ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
        model.addAttribute("errorStatus", HttpStatus.BAD_REQUEST.value());
        return "error-data-integrity";
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "404";
    }
}
