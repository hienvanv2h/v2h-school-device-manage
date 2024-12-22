package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.formmodel.ScheduleForm;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.projection.ScheduleProjection;
import com.vanhuuhien99.school_device_management.projection.TeacherAssignmentDTO;
import com.vanhuuhien99.school_device_management.service.ScheduleService;
import com.vanhuuhien99.school_device_management.service.TeacherAssignmentService;
import com.vanhuuhien99.school_device_management.utils.AppHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);

    private static final String SCHEDULE_TABLE_TEMPLATE = "dashboard/table/schedule-table";
    private static final String SCHEDULE_FORM_TEMPLATE = "dashboard/form/schedule-form";

    private final ScheduleService scheduleService;

    private final TeacherAssignmentService teacherAssignmentService;

    @GetMapping
    public String getAllSchedules(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt,desc") String[] sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String filter,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        Page<ScheduleProjection> schedulePage = scheduleService.getFilteredSchedules(keyword, filter, pageRequest);

        model.addAttribute("schedulePage", schedulePage);
        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(ScheduleProjection.class));
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);
        log.info("Loaded schedule table (page: {}, size: {}, sort: {}, keyword: {}, filter: {})", page, size, sort, keyword, filter);

        return SCHEDULE_TABLE_TEMPLATE;
    }

    @GetMapping("/api/data")
    public ResponseEntity<List<ScheduleProjection>> getAllSchedules(@RequestParam(required = false) Long assignmentId) {
        if(assignmentId != null) {
            return ResponseEntity.ok(scheduleService.getScheduleByTeacherAssignmentId(assignmentId));
        }
        Page<ScheduleProjection> scheduleProjectionPage = scheduleService.getAllSchedules(Pageable.unpaged());
        return ResponseEntity.ok(scheduleProjectionPage.getContent());
    }

    @GetMapping("/create")
    public String createScheduleForm(Model model) {
        model.addAttribute("type", "create");
        // Column mapping for TeacherAssignment table
        model.addAttribute("TA_COLUMN_MAPPING", ColumnMapping.getColumnTranslationMapping(TeacherAssignmentDTO.class));
        return SCHEDULE_FORM_TEMPLATE;
    }

    @PostMapping("/save")
    public String createNewSchedule(
            @ModelAttribute("scheduleForm") @Valid ScheduleForm scheduleForm,
            BindingResult result,
            Model model
    ) {
        log.info("Create form data for Schedule: {}", scheduleForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            // Column mapping for TeacherAssignment table
            model.addAttribute("TA_COLUMN_MAPPING", ColumnMapping.getColumnTranslationMapping(TeacherAssignmentDTO.class));
            log.info("Validation errors in create schedule form");
            return SCHEDULE_FORM_TEMPLATE;
        }
        scheduleService.createNewSchedule(scheduleForm);
        return "redirect:/dashboard/schedules";
    }

    @GetMapping("/update/{scheduleId}")
    public String updateScheduleForm(@PathVariable("scheduleId") Long scheduleId, Model model) {
        var scheduleProjection = scheduleService.getScheduleById(scheduleId);
        // Fill data to form
        var scheduleForm = ScheduleForm.builder()
                .teacherAssignmentId(scheduleProjection.getAssignmentId())
                .dayOfWeek(scheduleProjection.getDayOfWeek())
                .scheduleDate(scheduleProjection.getScheduleDate())
                .startTime(scheduleProjection.getStartTime())
                .endTime(scheduleProjection.getEndTime())
                .location(scheduleProjection.getLocation())
                .build();

        model.addAttribute("type", "update");
        model.addAttribute("id",scheduleId);
        model.addAttribute("scheduleForm", scheduleForm);
        // Column mapping for TeacherAssignment table
        model.addAttribute("TA_COLUMN_MAPPING", ColumnMapping.getColumnTranslationMapping(TeacherAssignmentDTO.class));

        return SCHEDULE_FORM_TEMPLATE;
    }

    @PutMapping("/save/{scheduleId}")
    public String updateSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @ModelAttribute("scheduleForm") @Valid ScheduleForm scheduleForm,
            BindingResult result,
            Model model
    ) {
        log.info("Update form data for Schedule: {}", scheduleForm);
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            // Column mapping for TeacherAssignment table
            model.addAttribute("TA_COLUMN_MAPPING", ColumnMapping.getColumnTranslationMapping(TeacherAssignmentDTO.class));
            log.info("Validation errors in update schedule form");
            return SCHEDULE_FORM_TEMPLATE;
        }
        scheduleService.updateSchedule(scheduleForm, scheduleId);
        return "redirect:/dashboard/schedules";
    }

    @DeleteMapping("/delete/{scheduleId}")
    @ResponseBody
    public ResponseEntity<String> deleteSchedule(@PathVariable("scheduleId") Long scheduleId) {
        log.info("Delete Schedule with id: {}", scheduleId);
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok("Xóa thành công!");
    }
}
