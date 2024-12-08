package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.formmodel.DeviceForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.service.DeviceCategoryService;
import com.vanhuuhien99.school_device_management.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/devices")
@RequiredArgsConstructor
public class DeviceController {

    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceService deviceService;

    private final DeviceCategoryService deviceCategoryService;

    @GetMapping
    public String getDevices(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deviceId,asc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        Sort sorting = Sort.by(sort[0]).ascending();
        if(sort[1].equalsIgnoreCase("desc")) {
            sorting = sorting.descending();
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size, sorting);

        Page<Device> devicePage;
        if(keyword == null || keyword.isEmpty() || filter == null || filter.isEmpty()) {
            devicePage = deviceService.getAllDevices(pageRequest);
        } else {
            if(filter.equalsIgnoreCase("deviceName")) {
                devicePage = deviceService.searchByDeviceNameContaining(keyword, pageRequest);
            } else {
                devicePage = deviceService.getAllDevices(pageRequest);
            }
        }

        model.addAttribute("devicePage", devicePage);
        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(Device.class));
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return "dashboard/table/device-table";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("deviceCategoryDropdown", deviceCategoryService.getAllDeviceCategoriesForDropdown());
        model.addAttribute("type", "create");
        return "dashboard/form/device-form";
    }

    @PostMapping("/save")
    public String createNewDevice(
            @ModelAttribute("deviceForm") @Valid DeviceForm deviceForm,
            BindingResult result,
            Model model
    ) {
        log.info("Create form data for Device: {}", deviceForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            log.info("Validation errors in create device form");
            return "dashboard/form/device-form";
        }
        deviceService.createNewDevice(deviceForm);
        return "redirect:/dashboard/devices";
    }

    @GetMapping("/update/{deviceId}")
    public String updateForm(@PathVariable("deviceId") Long deviceId, Model model) {
        var device = deviceService.getDeviceById(deviceId);
        // Fill data to form
        var deviceForm = DeviceForm.builder()
                .deviceName(device.getDeviceName())
                .deviceCategoryId(device.getDeviceCategory().getCategoryId())
                .description(device.getDescription())
                .status(device.getStatus())
                .build();

        model.addAttribute("deviceForm", deviceForm);
        model.addAttribute("deviceCategoryDropdown", deviceCategoryService.getAllDeviceCategoriesForDropdown());
        model.addAttribute("type", "update");
        model.addAttribute("id", deviceId);
        return "dashboard/form/device-form";
    }

    @PutMapping("/save/{deviceId}")
    public String updateDevice(
            @PathVariable("deviceId") Long deviceId,
            @ModelAttribute("deviceForm") @Valid DeviceForm deviceForm,
            BindingResult result,
            Model model
    ) {
        log.info("Update form data for Device: {}", deviceForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            log.info("Validation errors in update device form");
            return "dashboard/form/device-form";
        }
        deviceService.updateDevice(deviceForm, deviceId);
        return "redirect:/dashboard/devices";
    }

    @DeleteMapping("/delete/{deviceId}")
    @ResponseBody
    public ResponseEntity<String> deleteDevice(@PathVariable("deviceId") Long deviceId) {
        log.info("Delete Device with id: {}", deviceId);
        deviceService.deleteDevice(deviceId);
        return ResponseEntity.ok("Xóa thành công!");
    }
}
