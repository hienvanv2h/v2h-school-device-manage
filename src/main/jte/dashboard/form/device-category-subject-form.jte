@import com.vanhuuhien99.school_device_management.projection.DeviceCategoryDto
@import com.vanhuuhien99.school_device_management.projection.SubjectIdAndNameProjection

@param java.util.List<String> errors
@param java.util.List<DeviceCategoryDto> DEVICE_CATEGORY_DROPDOWN
@param java.util.List<SubjectIdAndNameProjection> SUBJECT_LIST

@param String createUrl = "/dashboard/device-category-subject/save"

@template.layout.dashboard(content = @`
    <div class="max-w-2xl mx-auto mt-8">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Thêm thiết bị</h2>
        <form name="deviceForm"
              action="${createUrl}"
              method="POST"
              class="bg-white p-6 rounded-lg shadow-md">
            @if(errors != null)
                <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                    <ul>
                        @for(var error: errors)
                            <li>${error}</li>
                        @endfor
                    </ul>
                </div>
            @endif
            <div class="flex flex-col gap-4">
                <%--    Device Category    --%>
                <div class="dropdown relative">
                    <label class="block text-gray-700 font-medium mb-2">
                        Loại thiết bị <span class="text-red-500">*</span>
                    </label>
                    <input type="hidden" name="categoryId"
                           value=""
                           class="dropdown-input" />
                    <button type="button" class="dropdown-button py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                        <span class="dropdown-selected">Chọn loại thiết bị</span>
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                        </svg>
                    </button>
                    <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                        @if(DEVICE_CATEGORY_DROPDOWN != null && !DEVICE_CATEGORY_DROPDOWN.isEmpty())
                            @for(var category: DEVICE_CATEGORY_DROPDOWN)
                                <li data-id="${category.getCategoryId()}" class="dropdown-item px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                    ${category.getCategoryName()}
                                </li>
                            @endfor
                        @else
                            <li class="dropdown-item px-4 py-2 text-gray-500">Không có loại thiết bị</li>
                        @endif
                    </ul>
                </div>

                <%--    Subject    --%>
                <div class="dropdown relative">
                    <label class="block text-gray-700 font-medium mb-2">
                        Môn học <span class="text-red-500">*</span>
                    </label>
                    <input type="hidden" name="subjectId"
                           value=""
                           class="dropdown-input" />
                    <button type="button" class="dropdown-button py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                        <span class="dropdown-selected">Chọn môn học</span>
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                        </svg>
                    </button>
                    <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                        @if(SUBJECT_LIST != null && !SUBJECT_LIST.isEmpty())
                            @for(var subject: SUBJECT_LIST)
                                <li data-id="${subject.getSubjectId()}" class="dropdown-item px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                    ${subject.getSubjectName()}
                                </li>
                            @endfor
                        @else
                            <li class="dropdown-item px-4 py-2 text-gray-500">Không có môn học</li>
                        @endif
                    </ul>
                </div>
            </div>
            <div class="flex justify-end mt-4">
                <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">
                    Thêm
                </button>
            </div>
        </form>
    </div>
`,
customScripts = @`
<%--    Script xử lý dropdown    --%>
    <script src="/js/dropdown.js"></script>
`)
