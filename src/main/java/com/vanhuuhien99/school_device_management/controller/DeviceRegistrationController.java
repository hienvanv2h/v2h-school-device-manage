package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.dto.Result;
import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import com.vanhuuhien99.school_device_management.entity.User;
import com.vanhuuhien99.school_device_management.formmodel.DeviceRegistrationForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationDTO;
import com.vanhuuhien99.school_device_management.projection.TeacherAssignmentDTO;
import com.vanhuuhien99.school_device_management.service.DeviceRegistrationService;
import com.vanhuuhien99.school_device_management.utils.AppHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/device-registrations")
@RequiredArgsConstructor
public class DeviceRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(DeviceRegistrationController.class);

    private static final String DEVICE_REGISTRATION_TABLE_TEMPLATE = "dashboard/table/device-registration-table";
    private static final String DEVICE_REGISTRATION_FORM_TEMPLATE = "dashboard/form/device-registration-form";

    private final DeviceRegistrationService deviceRegistrationService;

    @GetMapping
    public String getDeviceRegistrations(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "registrationId,asc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String approvalStatus,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<DeviceRegistration> deviceRegistrationPage = deviceRegistrationService.searchByCriteria(keyword, filter, approvalStatus, pageRequest);
        // Ánh xạ sang DTO class
        Page<DeviceRegistrationDTO> deviceRegistrationDTOPage = deviceRegistrationPage
                .map(DeviceRegistrationDTO::fromDeviceRegistration);

        populateTableModelAttributes(model, deviceRegistrationDTOPage, page, sort[0], sort[1]);
        return DEVICE_REGISTRATION_TABLE_TEMPLATE;
    }

    // Danh sách các đơn đăng ký của người dùng hiện tại
    @GetMapping("/list")
    public String getDeviceRegistrationListBelongToCurrentUser(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "registrationId,asc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String approvalStatus,
            Model model,
            Authentication authentication
    ) {
        if(authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        var principal = authentication.getPrincipal();
        if(principal instanceof User user) {
            String userPhoneNumber = user.getPhoneNumber();

            PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
            Page<DeviceRegistration> deviceRegistrationPage = deviceRegistrationService
                    .searchByCriteria(keyword, filter, approvalStatus, userPhoneNumber, pageRequest);
            // Ánh xạ sang DTO class
            Page<DeviceRegistrationDTO> deviceRegistrationDTOPage = deviceRegistrationPage
                    .map(DeviceRegistrationDTO::fromDeviceRegistration);

            populateTableModelAttributes(model, deviceRegistrationDTOPage, page, sort[0], sort[1]);
            return DEVICE_REGISTRATION_TABLE_TEMPLATE;
        }

        // Nếu không hợp lệ redirect về trang đăng nhập
        return "redirect:/login";
    }

    // Create registration form
    @GetMapping("/register")
    public String deviceRegisterForm(Model model) {
        model.addAttribute("type", "create");
        populateFormModelAttributes(model);
        return DEVICE_REGISTRATION_FORM_TEMPLATE;
    }

    @PostMapping("/save")
    public String createNewDeviceRegistration(
            @ModelAttribute("deviceRegistrationForm") @Valid DeviceRegistrationForm deviceRegistrationForm,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        log.info("Create form data for DeviceRegistration: {}", deviceRegistrationForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            populateFormModelAttributes(model);
            log.info("Validation errors in create device registration form");
            return DEVICE_REGISTRATION_FORM_TEMPLATE;
        }
        Result<Long> createResult = deviceRegistrationService.createNewDeviceRegistration(deviceRegistrationForm);
        if(createResult.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thuê thiết bị thành công!");
            return "redirect:/dashboard";
        } else {
            model.addAttribute("errors", List.of(createResult.getErrorMessage()));
            populateFormModelAttributes(model);
            return  DEVICE_REGISTRATION_FORM_TEMPLATE;
        }
    }

    @GetMapping("/update/{registrationId}")
    public String updateDeviceRegistrationForm(@PathVariable("registrationId") Long registrationId, Model model) {
        var deviceRegistration = deviceRegistrationService.getDeviceRegistrationById(registrationId);
        var deviceRegistrationDTO = DeviceRegistrationDTO.fromDeviceRegistration(deviceRegistration);
        // Fill data to form
        var deviceRegistrationForm = DeviceRegistrationForm.builder()
                .teacherAssignmentId(deviceRegistrationDTO.getTeacherAssignmentId())
                .deviceId(deviceRegistrationDTO.getDeviceId())
                .registrationStatus(deviceRegistrationDTO.getRegistrationStatus())
                .approvalStatus(deviceRegistrationDTO.getApprovalStatus())
                .scheduleDate(deviceRegistrationDTO.getScheduleDate())
                .returnDate(deviceRegistrationDTO.getReturnDate())
                .description(deviceRegistrationDTO.getDescription())
                .build();

        model.addAttribute("type", "update");
        model.addAttribute("id",registrationId);
        model.addAttribute("deviceRegistrationForm", deviceRegistrationForm);
        populateFormModelAttributes(model);

        return DEVICE_REGISTRATION_FORM_TEMPLATE;
    }

    @PutMapping("/save/{registrationId}")
    public String updateDeviceRegistration(
            @PathVariable("registrationId") Long registrationId,
            @ModelAttribute("deviceRegistrationForm") @Valid DeviceRegistrationForm deviceRegistrationForm,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        log.info("Update form data for DeviceRegistration: {}", deviceRegistrationForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            populateFormModelAttributes(model);
            log.info("Validation errors in update device registration form");
            return DEVICE_REGISTRATION_FORM_TEMPLATE;
        }
        Result<Void> updateResult = deviceRegistrationService.updateDeviceRegistration(deviceRegistrationForm, registrationId);
        if(updateResult.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
            return "redirect:/dashboard";
        } else {
            model.addAttribute("errors", List.of(updateResult.getErrorMessage()));
            populateFormModelAttributes(model);
            return  DEVICE_REGISTRATION_FORM_TEMPLATE;
        }
    }

    @DeleteMapping("/delete/{registrationId}")
    @ResponseBody
    public ResponseEntity<String> deleteDeviceRegistration(@PathVariable Long registrationId) {
        log.info("Delete DeviceRegistration with id: {}", registrationId);
        Result<Void> deleteResult = deviceRegistrationService.deleteDeviceRegistration(registrationId);
        if(deleteResult.isSuccess()) {
            return ResponseEntity.ok("Xóa thành công!");
        } else {
            return ResponseEntity.badRequest().body("Lỗi khi xóa:" + deleteResult.getErrorMessage());
        }
    }

    private void populateTableModelAttributes(Model model, Page<DeviceRegistrationDTO> deviceRegistrationPage, int currentPage, String sortField, String sortDirection) {
        var approvalStatusList = deviceRegistrationService.getAllApprovalStatus();
        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(DeviceRegistrationDTO.class));
        model.addAttribute("deviceRegistrationPage", deviceRegistrationPage);
        model.addAttribute("approvalStatusList", approvalStatusList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
    }

    private void populateFormModelAttributes(Model model) {
        // Column mapping for TeacherAssignment & Device table
        model.addAttribute("TA_COLUMN_MAPPING", ColumnMapping.getColumnTranslationMapping(TeacherAssignmentDTO.class));
        model.addAttribute("DEVICE_COLUMN_MAPPING", ColumnMapping.getColumnTranslationMapping(Device.class));
        var approvalStatusList = deviceRegistrationService.getAllApprovalStatus();
        model.addAttribute("approvalStatusList", approvalStatusList);
    }

}
