package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.Semester;
import com.vanhuuhien99.school_device_management.entity.Subject;
import com.vanhuuhien99.school_device_management.formmodel.SemesterForm;
import com.vanhuuhien99.school_device_management.formmodel.SubjectForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.service.SemesterService;
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
@RequestMapping("/dashboard/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private static final Logger log = LoggerFactory.getLogger(SemesterController.class);

    private static final String SEMESTER_TABLE_TEMPLATE = "dashboard/table/semester-table";
    private static final String SEMESTER_FORM_TEMPLATE = "dashboard/form/semester-form";

    private final SemesterService semesterService;

    @GetMapping
    public String getAllSemesters(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "semesterId,desc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<Semester> semesterPage = semesterService.getFilteredSemesters(keyword, filter, pageRequest);

        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(Semester.class));
        model.addAttribute("semesterPage", semesterPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return SEMESTER_TABLE_TEMPLATE;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("type", "create");
        return SEMESTER_FORM_TEMPLATE;
    }

    @PostMapping("/save")
    public String createNewSemester(
            @ModelAttribute("semesterForm") @Valid SemesterForm semesterForm,
            BindingResult result,
            Model model
    ) {
        log.info("Create form data for Semester: {}", semesterForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            return SEMESTER_FORM_TEMPLATE;
        }
        semesterService.createNewSemester(semesterForm);
        return "redirect:/dashboard/semesters";
    }

    @GetMapping("/update/{semesterId}")
    public String updateForm(@PathVariable("semesterId") Long semesterId, Model model) {
        var semester = semesterService.getSemesterById(semesterId);
        var semesterForm = new SemesterForm();
        semesterForm.setSemesterName(semester.getSemesterName());
        semesterForm.setStartDate(semester.getStartDate());
        semesterForm.setEndDate(semester.getEndDate());

        model.addAttribute("semesterForm", semesterForm);
        model.addAttribute("type", "update");
        model.addAttribute("id", semesterId);
        return SEMESTER_FORM_TEMPLATE;
    }

    @PutMapping("/save/{semesterId}")
    public String updateSemester(
            @PathVariable("semesterId") Long semesterId,
            @ModelAttribute("semesterForm") @Valid SemesterForm semesterForm,
            BindingResult result,
            Model model
    ) {
        log.info("Update form data for Semester: {}", semesterForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            return SEMESTER_FORM_TEMPLATE;
        }
        semesterService.updateSemester(semesterForm, semesterId);
        return "redirect:/dashboard/semesters";
    }

    @DeleteMapping("/delete/{semesterId}")
    @ResponseBody
    public ResponseEntity<String> deleteSemester(@PathVariable("semesterId") Long semesterId) {
        log.info("Delete Semester with id: {}", semesterId);
        semesterService.deleteSemester(semesterId);
        return ResponseEntity.ok("Xóa thành công!");
    }
}
