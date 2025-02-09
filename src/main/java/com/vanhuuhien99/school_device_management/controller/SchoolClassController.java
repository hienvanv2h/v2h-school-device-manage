package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import com.vanhuuhien99.school_device_management.formmodel.SchoolClassForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.service.SchoolClassService;
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
@RequestMapping("/dashboard/classes")
@RequiredArgsConstructor
public class SchoolClassController {

    private static final Logger log = LoggerFactory.getLogger(SchoolClassController.class);

    private static final String SCHOOL_CLASS_TABLE_TEMPLATE = "dashboard/table/school-class-table";
    private static final String SCHOOL_CLASS_FORM_TEMPLATE = "dashboard/form/school-class-form";

    private final SchoolClassService schoolClassService;

    @GetMapping
    public String getAllSchoolClasses(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "classId,asc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<SchoolClass> schoolClassPage = schoolClassService.getFilteredSchoolClasses(keyword, filter, pageRequest);

        model.addAttribute("schoolClassPage", schoolClassPage);
        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(SchoolClass.class));
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return SCHOOL_CLASS_TABLE_TEMPLATE;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("type", "create");
        return SCHOOL_CLASS_FORM_TEMPLATE;
    }

    @PostMapping("/save")
    public String createNewSchoolClass(
            @ModelAttribute("schoolClassForm") @Valid SchoolClassForm schoolClassForm,
            BindingResult result,
            Model model
    ) {
        log.info("Create form data for SchoolClass: {}", schoolClassForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            return SCHOOL_CLASS_FORM_TEMPLATE;
        }
        schoolClassService.createNewSchoolClass(schoolClassForm);
        return "redirect:/dashboard/classes";
    }

    @GetMapping("/update/{classId}")
    public String updateForm(@PathVariable("classId") Long classId, Model model) {
        var schoolClass = schoolClassService.getSchoolClassById(classId);
        var schoolClassForm = new SchoolClassForm();
        schoolClassForm.setClassName(schoolClass.getClassName());

        model.addAttribute("schoolClassForm", schoolClassForm);
        model.addAttribute("type", "update");
        model.addAttribute("id", classId);
        return SCHOOL_CLASS_FORM_TEMPLATE;
    }

    @PutMapping("/save/{classId}")
    public String updateSchoolClass(
            @PathVariable("classId") Long classId,
            @ModelAttribute("schoolClassForm") @Valid SchoolClassForm schoolClassForm,
            BindingResult result,
            Model model
    ) {
        log.info("Update form data for SchoolClass: {}", schoolClassForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            return SCHOOL_CLASS_FORM_TEMPLATE;
        }
        schoolClassService.updateSchoolClass(schoolClassForm, classId);
        return "redirect:/dashboard/classes";
    }

    @DeleteMapping("/delete/{classId}")
    @ResponseBody
    public ResponseEntity<String> deleteSchoolClass(@PathVariable("classId") Long classId) {
        log.info("Delete SchoolClass with id: {}", classId);
        schoolClassService.deleteSchoolClass(classId);
        return ResponseEntity.ok("Xóa thành công!");
    }
}
