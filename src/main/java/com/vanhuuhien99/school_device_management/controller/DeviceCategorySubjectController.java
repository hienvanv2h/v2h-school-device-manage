package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.DeviceCategorySubject;
import com.vanhuuhien99.school_device_management.entity.DeviceCategorySubjectId;
import com.vanhuuhien99.school_device_management.formmodel.DeviceCategorySubjectForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.projection.SubjectIdAndNameProjection;
import com.vanhuuhien99.school_device_management.service.DeviceCategoryService;
import com.vanhuuhien99.school_device_management.service.DeviceCategorySubjectService;
import com.vanhuuhien99.school_device_management.service.SubjectService;
import com.vanhuuhien99.school_device_management.utils.AppHelper;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/dashboard/device-category-subject")
@RequiredArgsConstructor
public class DeviceCategorySubjectController {

    private static final Logger log = LoggerFactory.getLogger(DeviceCategorySubjectController.class);

    private static final String DEVICE_CATEGORY_SUBJECT_TABLE_TEMPLATE = "dashboard/table/device-category-subject-table";
    private static final String DEVICE_CATEGORY_SUBJECT_FORM_TEMPLATE = "dashboard/form/device-category-subject-form";

    private final DeviceCategorySubjectService deviceCategorySubjectService;
    private final DeviceCategoryService deviceCategoryService;
    private final SubjectService subjectService;

    @GetMapping
    public String getDeviceCategorySubjectMappings(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deviceCategory.categoryId,asc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<DeviceCategorySubject> deviceCategorySubjectPage = deviceCategorySubjectService.getFilteredDeviceCategorySubjects(keyword, filter, pageRequest);

        model.addAttribute("deviceCategorySubjectPage", deviceCategorySubjectPage);
        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(DeviceCategorySubject.class));
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return DEVICE_CATEGORY_SUBJECT_TABLE_TEMPLATE;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("type", "create");
        populateFormModelAttributes(model);
        return DEVICE_CATEGORY_SUBJECT_FORM_TEMPLATE;
    }

    @PostMapping("/save")
    public String createNewDeviceCategorySubject(
            @ModelAttribute("deviceCategorySubjectForm") @Valid DeviceCategorySubjectForm deviceCategorySubjectForm,
            BindingResult result,
            Model model,
            HttpServletRequest request
    ) {
        // Đặt templateName vào request attribute để ExceptionHandler có thể lấy được
        request.setAttribute("templateName", DEVICE_CATEGORY_SUBJECT_FORM_TEMPLATE);
        log.info("Create request for DeviceCategorySubject: {}", deviceCategorySubjectForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            populateFormModelAttributes(model);

            log.info("Validation errors when creating DeviceCategorySubject");
            return DEVICE_CATEGORY_SUBJECT_FORM_TEMPLATE;
        }
        deviceCategorySubjectService.createNewDeviceCategorySubject(deviceCategorySubjectForm);
        return "redirect:/dashboard/device-category-subject";
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deleteDeviceCategorySubject(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("subjectId") Long subjectId
    ) {
        if(categoryId == null || subjectId == null) return ResponseEntity.badRequest().body("categoryId and subjectId is required");
        log.info("Delete DeviceCategorySubject with id: categoryId: {}, subjectId: {}", categoryId, subjectId);
        deviceCategorySubjectService.deleteDeviceCategorySubject(new DeviceCategorySubjectId(categoryId, subjectId));
        return ResponseEntity.ok("Xóa thành công!");
    }

    private void populateFormModelAttributes(Model model) {
        model.addAttribute("DEVICE_CATEGORY_DROPDOWN", deviceCategoryService.getAllDeviceCategoriesForDropdown());
        model.addAttribute("SUBJECT_LIST", subjectService.getAll(SubjectIdAndNameProjection.class));
    }
}
