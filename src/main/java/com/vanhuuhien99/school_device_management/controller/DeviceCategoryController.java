package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.formmodel.DeviceCategoryForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.service.DeviceCategoryService;
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
@RequestMapping("/dashboard/device-categories")
@RequiredArgsConstructor
public class DeviceCategoryController {

    private static final Logger log = LoggerFactory.getLogger(DeviceCategoryController.class);

    private static final String DEVICE_CATEGORY_TABLE_TEMPLATE = "dashboard/table/device-category-table";
    private static final String DEVICE_CATEGORY_FORM_TEMPLATE = "dashboard/form/device-category-form";

    private final DeviceCategoryService deviceCategoryService;

    @GetMapping
    public String getDeviceCategories(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "categoryId,asc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<DeviceCategory> deviceCategoryPage = deviceCategoryService.getFilteredDeviceCategories(keyword, filter, pageRequest);

        model.addAttribute("deviceCategoryPage", deviceCategoryPage);
        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(DeviceCategory.class));
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return DEVICE_CATEGORY_TABLE_TEMPLATE;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("type", "create");
        return DEVICE_CATEGORY_FORM_TEMPLATE;
    }

    @PostMapping("/save")
    public String createNewDeviceCategory(
            @ModelAttribute("deviceCategoryForm") @Valid DeviceCategoryForm deviceCategoryForm,
            BindingResult result,
            Model model
    ) {
        log.info("Create form data for DeviceCategory: {}", deviceCategoryForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            log.info("Validation errors in create device category form");
            return DEVICE_CATEGORY_FORM_TEMPLATE;
        }
        deviceCategoryService.createNewDeviceCategory(deviceCategoryForm);
        return "redirect:/dashboard/device-categories";
    }

    @GetMapping("/update/{categoryId}")
    public String updateForm(@PathVariable("categoryId") Long categoryId, Model model) {
        var deviceCategory = deviceCategoryService.getDeviceCategoryById(categoryId);
        // Fill data to form
        var deviceCategoryForm = DeviceCategoryForm.builder()
                .categoryName(deviceCategory.getCategoryName())
                .description(deviceCategory.getDescription())
                .unit(deviceCategory.getUnit())
                .unitPrice(deviceCategory.getUnitPrice())
                .build();

        model.addAttribute("deviceCategoryForm", deviceCategoryForm);
        model.addAttribute("type", "update");
        model.addAttribute("id", categoryId);
        return DEVICE_CATEGORY_FORM_TEMPLATE;
    }

    @PutMapping("/save/{categoryId}")
    public String updateDeviceCategory(
            @PathVariable("categoryId") Long categoryId,
            @ModelAttribute("deviceCategoryForm") @Valid DeviceCategoryForm deviceCategoryForm,
            BindingResult result,
            Model model
    ) {
        log.info("Update form data for DeviceCategory: {}", deviceCategoryForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            log.info("Validation errors in update device category form");
            return DEVICE_CATEGORY_FORM_TEMPLATE;
        }
        deviceCategoryService.updateDeviceCategory(deviceCategoryForm, categoryId);
        return "redirect:/dashboard/device-categories";
    }

    @DeleteMapping("/delete/{categoryId}")
    @ResponseBody
    public ResponseEntity<String> deleteDeviceCategory(@PathVariable("categoryId") Long categoryId) {
        log.info("Delete DeviceCategory with id: {}", categoryId);
        deviceCategoryService.deleteDeviceCategory(categoryId);
        return ResponseEntity.ok("Xóa thành công!");
    }
}
