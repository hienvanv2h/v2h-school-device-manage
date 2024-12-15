package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.Teacher;
import com.vanhuuhien99.school_device_management.formmodel.TeacherForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.service.TeacherService;
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
@RequestMapping("/dashboard/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private static final Logger log = LoggerFactory.getLogger(TeacherController.class);

    private static final String TEACHER_TABLE_TEMPLATE = "dashboard/table/teacher-table";
    private static final String TEACHER_FORM_TEMPLATE = "dashboard/form/teacher-form";

    private final TeacherService teacherService;

    @GetMapping
    public String getAllTeachers(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "teacherId,asc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<Teacher> teacherPage = teacherService.getFilteredTeachers(keyword, filter, pageRequest);

        model.addAttribute("teacherPage", teacherPage);
        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(Teacher.class));
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return TEACHER_TABLE_TEMPLATE;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("type", "create");
        return TEACHER_FORM_TEMPLATE;
    }

    @PostMapping("/save")
    public String createNewTeacher(
            @ModelAttribute("teacherForm") @Valid TeacherForm teacherForm,
            BindingResult result,
            Model model
    ) {
        log.info("Create form data for Teacher: {}", teacherForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            log.info("Validation errors in create teacher form");
            return TEACHER_FORM_TEMPLATE;
        }
        teacherService.createNewTeacher(teacherForm);
        return "redirect:/dashboard/teachers";
    }

    @GetMapping("/update/{teacherId}")
    public String updateForm(@PathVariable("teacherId") Long teacherId, Model model) {
        var teacher = teacherService.getTeacherById(teacherId);
        // Fill data to form
        var teacherForm = TeacherForm.builder()
                        .fullName(teacher.getFullName())
                        .email(teacher.getEmail())
                        .phoneNumber(teacher.getPhoneNumber())
                        .dateOfBirth(teacher.getDateOfBirth())
                        .gender(teacher.getGender().getValue())
                        .build();

        model.addAttribute("teacherForm", teacherForm);
        model.addAttribute("type", "update");
        model.addAttribute("id", teacherId);
        return TEACHER_FORM_TEMPLATE;
    }

    @PutMapping("/save/{teacherId}")
    public String updateTeacher(
            @PathVariable("teacherId") Long teacherId,
            @ModelAttribute("teacherForm") @Valid TeacherForm teacherForm,
            BindingResult result,
            Model model
    ) {
        log.info("Update form data for Teacher: {}", teacherForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            log.info("Validation errors in update teacher form");
            return TEACHER_FORM_TEMPLATE;
        }
        teacherService.updateTeacher(teacherForm, teacherId);
        return "redirect:/dashboard/teachers";
    }

    @DeleteMapping("/delete/{teacherId}")
    @ResponseBody
    public ResponseEntity<String> deleteTeacher(@PathVariable("teacherId") Long teacherId) {
        log.info("Delete Teacher with id: {}", teacherId);
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.ok("Xóa thành công!");
    }
}
