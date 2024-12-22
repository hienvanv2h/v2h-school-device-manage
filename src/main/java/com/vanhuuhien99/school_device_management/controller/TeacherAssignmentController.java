package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.formmodel.TeacherAssignmentForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.projection.*;
import com.vanhuuhien99.school_device_management.service.*;
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
@RequestMapping("/dashboard/teacher-assignments")
@RequiredArgsConstructor
public class TeacherAssignmentController {

    private static final Logger log = LoggerFactory.getLogger(TeacherAssignmentController.class);

    private static final String TEACHER_ASSIGNMENT_TABLE_TEMPLATE = "dashboard/table/teacher-assignment-table";
    private static final String TEACHER_ASSIGNMENT_FORM_TEMPLATE = "dashboard/form/teacher-assignment-form";

    private final TeacherAssignmentService teacherAssignmentService;
    private final SubjectService subjectService;
    private final SchoolClassService schoolClassService;
    private final TeacherService teacherService;
    private final SemesterService semesterService;

    @GetMapping
    public String getTeacherAssignments(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt,desc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<TeacherAssignmentDTO> teacherAssignmentPage = teacherAssignmentService.searchByCriteria(keyword, filter, pageRequest);

        populateTableModelAttributes(model, teacherAssignmentPage, page, sort[0], sort[1]);
        return TEACHER_ASSIGNMENT_TABLE_TEMPLATE;
    }

    // Endpoint trả về dữ liệu trang
    @GetMapping("/api/data")
    @ResponseBody
    public ResponseEntity<Page<TeacherAssignmentDTO>> getTeacherAssignmentTableData(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt,desc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<TeacherAssignmentDTO> teacherAssignmentList = teacherAssignmentService.searchByCriteria(keyword, filter, pageRequest);
        return ResponseEntity.ok(teacherAssignmentList);
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("type", "create");
        populateFormModelAttributes(model);
        return TEACHER_ASSIGNMENT_FORM_TEMPLATE;
    }

    @PostMapping("/save")
    public String createNewTeacherAssignment(
            @ModelAttribute("teacherAssignmentForm") @Valid TeacherAssignmentForm teacherAssignmentForm,
            BindingResult result,
            Model model
    ) {
        log.info("Create form data for TeacherAssignment: {}", teacherAssignmentForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            log.info("Validation errors in create teacher assignment form");
            return TEACHER_ASSIGNMENT_FORM_TEMPLATE;
        }
        teacherAssignmentService.createNewTeacherAssignment(teacherAssignmentForm);
        return "redirect:/dashboard/teacher-assignments";
    }

    @GetMapping("/update/{assignmentId}")
    public String updateForm(@PathVariable("assignmentId") Long assignmentId, Model model) {
        var teacherAssignmentProjection = teacherAssignmentService.getTeacherAssignmentById(assignmentId);
        // Fill data to form
        var teacherAssignmentForm = TeacherAssignmentForm.builder()
                .teacherId(teacherAssignmentProjection.getTeacherId())
                .classId(teacherAssignmentProjection.getClassId())
                .subjectId(teacherAssignmentProjection.getSubjectId())
                .semester(teacherAssignmentProjection.getSemester())
                .description(teacherAssignmentProjection.getDescription())
                .build();

        model.addAttribute("type", "update");
        model.addAttribute("id",assignmentId);
        model.addAttribute("teacherAssignmentForm", teacherAssignmentForm);
        populateFormModelAttributes(model);
        return TEACHER_ASSIGNMENT_FORM_TEMPLATE;
    }

    @PutMapping("/save/{assignmentId}")
    public String updateTeacherAssignment(
            @PathVariable("assignmentId") Long assignmentId,
            @ModelAttribute("teacherAssignmentForm") @Valid TeacherAssignmentForm teacherAssignmentForm,
            BindingResult result,
            Model model
    ) {
        log.info("Update form data for TeacherAssignment: {}", teacherAssignmentForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            log.info("Validation errors in update teacher assignment form");
            return TEACHER_ASSIGNMENT_FORM_TEMPLATE;
        }
        teacherAssignmentService.updateTeacherAssignment(teacherAssignmentForm, assignmentId);
        return "redirect:/dashboard/teacher-assignments";
    }

    @DeleteMapping("/delete/{assignmentId}")
    @ResponseBody
    public ResponseEntity<String> deleteTeacherAssignment(@PathVariable("assignmentId") Long assignmentId) {
        log.info("Delete Teacher assignment with id: {}", assignmentId);
        teacherAssignmentService.deleteTeacherAssignment(assignmentId);
        return ResponseEntity.ok("Xóa thành công!");
    }

    private void populateTableModelAttributes(Model model, Page<?> pageData, int currentPage, String sortField, String sortDirection) {
        model.addAttribute("teacherAssignmentPage", pageData);
        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(TeacherAssignmentDTO.class));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
    }

    private void populateFormModelAttributes(Model model) {
        var teacherList = teacherService.getAll(TeacherIdAndFullNameProjection.class);
        var schoolClassList = schoolClassService.getAll(SchoolClassIdAndNameProjection.class);
        var subjectList = subjectService.getAll(SubjectIdAndNameProjection.class);
        var semesterList = semesterService.getAllSemesters();

        model.addAttribute("teacherList", teacherList);
        model.addAttribute("schoolClassList", schoolClassList);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("semesterList", semesterList);
    }
}
