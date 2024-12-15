package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.Subject;
import com.vanhuuhien99.school_device_management.formmodel.SubjectForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.service.SubjectService;
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
@RequestMapping("/dashboard/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private static final Logger log = LoggerFactory.getLogger(SubjectController.class);

    private static final String SUBJECT_TABLE_TEMPLATE = "dashboard/table/subject-table";
    private static final String SUBJECT_FORM_TEMPLATE = "dashboard/form/subject-form";

    private final SubjectService subjectService;

    @GetMapping
    public String getAllSubjects(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "subjectId,asc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<Subject> subjectPage = subjectService.getFilteredSubjects(keyword, filter, pageRequest);

        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(Subject.class));
        model.addAttribute("subjectPage", subjectPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return SUBJECT_TABLE_TEMPLATE;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("type", "create");
        return SUBJECT_FORM_TEMPLATE;
    }

    @PostMapping("/save")
    public String createNewSubject(
            @ModelAttribute("subjectForm") @Valid SubjectForm subjectForm,
            BindingResult result,
            Model model
    ) {
        log.info("Create form data for Subject: {}", subjectForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            return SUBJECT_FORM_TEMPLATE;
        }
        subjectService.createNewSubject(subjectForm);
        return "redirect:/dashboard/subjects";
    }

    @GetMapping("/update/{subjectId}")
    public String updateForm(@PathVariable("subjectId") Long subjectId, Model model) {
        var subject = subjectService.getSubjectById(subjectId);
        var subjectForm = new SubjectForm();
        subjectForm.setSubjectName(subject.getSubjectName());

        model.addAttribute("subjectForm", subjectForm);
        model.addAttribute("type", "update");
        model.addAttribute("id", subjectId);
        return SUBJECT_FORM_TEMPLATE;
    }

    @PutMapping("/save/{subjectId}")
    public String updateSubject(
            @PathVariable("subjectId") Long subjectId,
            @ModelAttribute("subjectForm") @Valid SubjectForm subjectForm,
            BindingResult result,
            Model model
    ) {
        log.info("Update form data for Subject: {}", subjectForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            return SUBJECT_FORM_TEMPLATE;
        }
        subjectService.updateSubject(subjectForm, subjectId);
        return "redirect:/dashboard/subjects";
    }

    @DeleteMapping("/delete/{subjectId}")
    @ResponseBody
    public ResponseEntity<String> deleteSubject(@PathVariable("subjectId") Long subjectId) {
        log.info("Delete Subject with id: {}", subjectId);
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.ok("Xóa thành công!");
    }
}
