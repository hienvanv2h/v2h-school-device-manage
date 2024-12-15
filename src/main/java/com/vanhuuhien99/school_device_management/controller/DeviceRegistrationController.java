package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.formmodel.DeviceRegistrationForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationProjection;
import com.vanhuuhien99.school_device_management.projection.TeacherAssignmentProjection;
import com.vanhuuhien99.school_device_management.service.DeviceRegistrationService;
import com.vanhuuhien99.school_device_management.utils.AppHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

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
    public String getDeviceRegistrationList(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "registrationId,asc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String approvalStatus,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<DeviceRegistrationProjection> deviceRegistrationPage = deviceRegistrationService.getFilteredDeviceRegistrations(keyword, filter, approvalStatus, pageRequest);
        var approvalStatusList = deviceRegistrationService.getAllApprovalStatus();

        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(DeviceRegistrationProjection.class));
        model.addAttribute("deviceRegistrationPage", deviceRegistrationPage);
        model.addAttribute("approvalStatusList", approvalStatusList);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return DEVICE_REGISTRATION_TABLE_TEMPLATE;
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
            Model model
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
        deviceRegistrationService.createNewDeviceRegistration(deviceRegistrationForm);
        return "redirect:/dashboard";
    }

    @GetMapping("/update/{registrationId}")
    public String updateDeviceRegistrationForm(@PathVariable("registrationId") Long registrationId, Model model) {
        var deviceRegistrationProjection = deviceRegistrationService.getDeviceRegistrationById(registrationId);
        // Fill data to form
        var deviceRegistrationForm = DeviceRegistrationForm.builder()
                .teacherAssignmentId(deviceRegistrationProjection.getTeacherAssignmentId())
                .deviceId(deviceRegistrationProjection.getDeviceId())
                .registrationStatus(deviceRegistrationProjection.getRegistrationStatus())
                .approvalStatus(deviceRegistrationProjection.getApprovalStatus())
                .description(deviceRegistrationProjection.getDescription())
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
            Model model
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
        deviceRegistrationService.updateDeviceRegistration(deviceRegistrationForm, registrationId);
        return "redirect:/dashboard/device-registrations";
    }

    @DeleteMapping("/delete/{registrationId}")
    @ResponseBody
    public ResponseEntity<String> deleteDeviceRegistration(@PathVariable Long registrationId) {
        log.info("Delete DeviceRegistration with id: {}", registrationId);
        deviceRegistrationService.deleteDeviceRegistration(registrationId);
        return ResponseEntity.ok("Xóa thành công!");
    }

    private void populateFormModelAttributes(Model model) {
        // Column mapping for TeacherAssignment & Device table
        model.addAttribute("TA_COLUMN_MAPPING", ColumnMapping.getColumnTranslationMapping(TeacherAssignmentProjection.class));
        model.addAttribute("DEVICE_COLUMN_MAPPING", ColumnMapping.getColumnTranslationMapping(Device.class));
        var approvalStatusList = deviceRegistrationService.getAllApprovalStatus();
        model.addAttribute("approvalStatusList", approvalStatusList);
    }

}
