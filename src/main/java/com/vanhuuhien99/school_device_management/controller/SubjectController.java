package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.Subject;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private static final Logger log = LoggerFactory.getLogger(SubjectController.class);

    private final SubjectService subjectService;

    @GetMapping
    public String getAllSubjects(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "subjectId,asc") String[] sort,
            Model model
    ) {
        Sort sorting = Sort.by(sort[0]).ascending();
        if(sort[1].equalsIgnoreCase("desc")) {
            sorting = sorting.descending();
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size, sorting);
        Page<Subject> subjectPage = subjectService.getAllSubjects(pageRequest);
        var columnMapping = ColumnMapping.getColumnTranslationMapping(Subject.class);

        model.addAttribute("columnMapping", columnMapping);
        model.addAttribute("subjectPage", subjectPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        log.info("Column Names: {}", columnMapping);
        log.info("Subject Page: {}", subjectPage.getContent());
        return "dashboard/table/subject-table";
    }
}
