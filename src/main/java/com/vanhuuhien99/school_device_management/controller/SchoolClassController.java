package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import com.vanhuuhien99.school_device_management.mapping.ColumnMapping;
import com.vanhuuhien99.school_device_management.service.SchoolClassService;
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
@RequestMapping("/dashboard/classes")
@RequiredArgsConstructor
public class SchoolClassController {

    private static final Logger log = LoggerFactory.getLogger(SchoolClassController.class);

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
        Sort sorting = Sort.by(sort[0]).ascending();
        if(sort[1].equalsIgnoreCase("desc")) {
            sorting = sorting.descending();
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size, sorting);

        Page<SchoolClass> schoolClassPage = null;
        if (keyword != null && !keyword.isEmpty() || filter != null && !filter.isEmpty()) {
            // Tìm kiếm khi có keyword hoặc filter
            if(filter.equalsIgnoreCase("className")) {
                schoolClassPage = schoolClassService.searchByClassName(keyword, pageRequest);
            } else {
                schoolClassPage = schoolClassService.getAllSchoolClasses(pageRequest);
            }
        } else {
            // Hiển thị danh sách đầy đủ
            schoolClassPage = schoolClassService.getAllSchoolClasses(pageRequest);
        }

        model.addAttribute("schoolClassPage", schoolClassPage);
        model.addAttribute("columnMapping", ColumnMapping.getColumnTranslationMapping(SchoolClass.class));
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return "dashboard/table/school-class-table";
    }
}
