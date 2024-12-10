package com.vanhuuhien99.school_device_management.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class AppHelper {

    /**
     * Tạo PageRequest cho controller
     * @param page Số trang hiện tại
     * @param size Số bản ghi giới hạn cho mỗi trang
     * @param sort Một mảng chứa thông tin sắp xếp theo định dạng [cột,chiều sắp xếp]
     * @return PageRequest
     */
    public static PageRequest createPageRequest(int page, int size, String[] sort) {
        if(sort == null || sort.length > 2) {
            return PageRequest.of(page - 1, size);
        }
        Sort sorting = Sort.by(sort[0]).ascending();
        if(sort[1].equalsIgnoreCase("desc")) {
            sorting = sorting.descending();
        }
        return PageRequest.of(page - 1, size, sorting);
    }
}
