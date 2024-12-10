@import java.util.Map

@param java.util.List<String> errors
@param String type
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.ScheduleForm scheduleForm

@param Map<String, String> columnMapping
@param Integer currentPage = 1
@param Integer size = 10
@param String sortField = "updatedAt"
@param String sortDirection = "desc"
@param String apiPath = "/dashboard/teacher-assignments"

@param String[] weekDays = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}

@template.layout.dashboard(content = @`
    <div class="flex flex-col xl:flex-row justify-between items-start gap-2 max-w-7xl mx-auto h-screen mt-8">
        <div class="flex flex-col justify-between max-w-3xl mx-auto max-h-[80%] h-full">
            <div class="overflow-auto p-2 flex-1 custom-scrollbar">
                <%--    Teacher Assignment list    --%>
                <table id="teacherAssignmentTable" class="bg-white border border-gray-200 rounded-lg shadow-md h-full">
                    <thead>
                        <tr>
                            @for(Map.Entry<String, String> entry: columnMapping.entrySet())
                                <th id="${entry.getKey() + "Header"}" class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[160px]">
                                    ${entry.getValue()}
                                </th>
                            @endfor
                        </tr>
                    </thead>
                    <tbody class="align-top">
                        <%--    Dữ liệu bảng được rendered bởi JavaScript    --%>
                        <%--    Lưu ý: Thứ tự cột cần khớp tương ứng với LinkedHashMap trong ColumnMapping    --%>
                    </tbody>
                </table>

            </div>
            <nav id="pagination" class="mt-8 py-2 flex justify-center">
                <ul class="inline-flex rounded-md">
                    <%--    Phân trang được rendered bởi JavaScript    --%>
                </ul>
            </nav>
        </div>

        <div class="max-w-2xl mx-auto h-full">
            <form name="scheduleForm"
                  action="/dashboard/schedules/save${type == null || type.equals("create") ? "" : "/" + id}"
                  method="POST"
                  class="bg-white p-6 rounded-lg shadow-md">
                <h2 class="text-2xl font-semibold text-gray-700 mb-4">Tạo thời khóa biểu</h2>
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
                    <%--    Hidden input for PUT method    --%>
                    @if(type != null && type.equals("update"))
                        <input type="hidden" name="_method" value="PUT">
                    @endif

                    <div>
                        <label for="teacherAssignmentId" class="block text-gray-700 font-medium mb-2">
                            Assignment ID <span class="text-red-500">*</span>
                        </label>
                        <input type="text" id="teacherAssignmentId" name="teacherAssignmentId"
                               value="${scheduleForm != null && scheduleForm.getTeacherAssignmentId() != null ? scheduleForm.getTeacherAssignmentId().toString() : ""}"
                               class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                               required readonly>
                    </div>
                    <div>
                        <label for="scheduleDate" class="block text-gray-700 font-medium mb-2">
                            Ngày học <span class="text-red-500">*</span>
                        </label>
                        <input type="date" id="scheduleDate" name="scheduleDate"
                               value="${scheduleForm != null && scheduleForm.getScheduleDate() != null ? scheduleForm.getScheduleDate().toString() : ""}"
                               class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                               required>
                    </div>

                    <div class="dropdown relative">
                        <label class="block text-gray-700 font-medium mb-2">
                            Ngày trong tuần <span class="text-red-500">*</span>
                        </label>
                        <input type="hidden" name="dayOfWeek"
                               value=""
                               class="dropdown-input" />
                        <button type="button" class="dropdown-button py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                            <span class="dropdown-selected">Chọn ngày trong tuần</span>
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                                <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                            </svg>
                        </button>
                        <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                            @for(String weekDay : weekDays)
                                <li data-id="${weekDay}" class="dropdown-item px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                    ${weekDay}
                                </li>
                            @endfor
                        </ul>
                    </div>

                    <div>
                        <label for="startTime" class="inline-block text-gray-700 font-medium mb-2">
                            Bắt đầu từ <span class="text-red-500">*</span>
                        </label>
                        <input type="time" id="startTime" name="startTime"
                               value="${scheduleForm != null && scheduleForm.getStartTime() != null ? scheduleForm.getStartTime().toString() : ""}"
                               class="px-4 py-2 w-28 border border-gray-300 rounded-md mt-2"
                               min="00:00" max="23:59"
                               required>
                        <label for="endTime" class="inline-block text-gray-700 font-medium mb-2">
                            Đến <span class="text-red-500">*</span>
                        </label>
                        <input type="time" id="endTime" name="endTime"
                               value="${scheduleForm != null && scheduleForm.getEndTime() != null ? scheduleForm.getEndTime().toString() : ""}"
                               class="px-4 py-2 w-28 border border-gray-300 rounded-md mt-2"
                               min="00:00" max="23:59"
                               required>
                    </div>
                    <div>
                        <label for="location" class="block text-gray-700 font-medium mb-2">
                            Địa điểm học
                        </label>
                        <input type="text" id="location" name="location"
                               value="${scheduleForm != null && scheduleForm.getLocation() != null ? scheduleForm.getLocation() : ""}"
                               class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                               required>
                    </div>
                </div>
                <div class="flex justify-end mt-4">
                    <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">
                        ${type == null || type.equals("create") ? "Thêm" : "Cập nhật"}
                    </button>
                </div>
            </form>
        </div>
    </div>
`,
customStyles = @`
    <link rel="stylesheet" type="text/css" href="/css/custom-scrollbar.css">
`,
customScripts = @`
    <%--    Script lấy dữ liệu cho bảng    --%>
    <script src="/js/fetch-table-data.js"></script>
    <%--    Script gắn sự kiện chọn dòng từ bảng và gán giá trị cho input    --%>
    <script src="/js/table-form-binding.js"></script>
    <%--    Script xử lý dropdown    --%>
    <script src="/js/dropdown.js"></script>
`)
