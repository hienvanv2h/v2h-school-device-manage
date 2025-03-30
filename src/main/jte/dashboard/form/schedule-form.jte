@import com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState
@import java.time.LocalDate
@import java.util.List
@import java.util.Map

@param java.util.List<String> errors
@param String type
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.ScheduleForm scheduleForm

@param Map<String, String> TA_COLUMN_MAPPING
@param List<String> periodList
@param String delimiter = ","
@param String[] weekDays = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}

@param GlobalTemplateState globalState

@param String apiPath = "/dashboard/schedules"

@template.layout.dashboard(
globalState = globalState,
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="flex flex-col xl:flex-row justify-between items-start gap-2 max-w-7xl mx-auto h-screen mt-8">
        <div class="flex flex-col justify-between xl:max-w-3xl max-w-[-webkit-fill-available] max-h-[80%] h-full">
            <input id="teacherAssignmentSearch" type="text" placeholder="Nhập tên giáo viên"
                   class="px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 mb-2">
            <div class="relative flex-1 overflow-auto custom-scrollbar max-h-[80vh]">
                <%--    Teacher Assignment list    --%>
                <table id="teacherAssignmentTable" class="bg-white border border-gray-200 rounded-lg shadow-md">
                    <thead class="sticky top-0 left-0 right-0 z-10 bg-gray-100">
                        <tr>
                            @for(Map.Entry<String, String> entry: TA_COLUMN_MAPPING.entrySet())
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
            <nav id="teacherAssignmentPagination" class="mt-8 py-2 flex justify-center">
                <ul class="inline-flex rounded-md">
                    <%--    Phân trang được rendered bởi JavaScript    --%>
                </ul>
            </nav>
        </div>

        <div class="xl:max-w-2xl mx-auto h-full max-h-[80vh] xl:w-[50vw] w-full">
            <form name="scheduleForm"
                  action="${apiPath}/save${type == null || type.equals("create") ? "" : "/" + id}"
                  method="POST"
                  class="bg-slate-100 p-6 rounded-lg shadow-md w-full">
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

                    <%--    CSRF    --%>
                    ${globalState.getCsrfHiddenInput()}

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
                               value="${scheduleForm != null && scheduleForm.getScheduleDate() != null ? scheduleForm.getScheduleDate().toString() : LocalDate.now().toString()}"
                               class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                               required>
                    </div>

                    <div>
                        <label for="dayOfWeek" class="block text-gray-700 font-medium mb-2">
                            Ngày trong tuần
                        </label>
                        <input type="text" id="dayOfWeek" name="dayOfWeek"
                               class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                               readonly required>
                    </div>

                    <%--    Dropdown chọn tiết học    --%>
                    <div data-multiselect="true" data-delimiter="${delimiter}" class="dropdown relative">
                        <label class="block text-gray-700 font-medium mb-2">
                            Tiết học <span class="text-red-500">*</span>
                        </label>
                        <input type="hidden" name="periods"
                               value="${type != null && type.equals("update")
                                    && scheduleForm != null && scheduleForm.getPeriods() != null && !scheduleForm.getPeriods().isEmpty()
                                    ? String.join(delimiter, scheduleForm.getPeriods()) : ""}"
                               class="dropdown-input" />
                        <button type="button" class="dropdown-button py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                        <span class="dropdown-selected w-[80%] truncate">
                            @if(type != null && type.equals("update")
                                    && scheduleForm != null && scheduleForm.getPeriods() != null && !scheduleForm.getPeriods().isEmpty())
                                ${String.join(delimiter, scheduleForm.getPeriods())}
                            @endif
                        </span>
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                                <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                            </svg>
                        </button>
                        <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                            @if(periodList != null && !periodList.isEmpty())
                                @for(var period: periodList)
                                    <li data-id="${period}" class="dropdown-item px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                        ${period}
                                    </li>
                                @endfor
                            @else
                                <li class="dropdown-item px-4 py-2 text-gray-500">N/A</li>
                            @endif
                        </ul>
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
customScripts = @`
    <%--    Script gắn sự kiện chọn dòng từ bảng và ánh xạ giá trị cho input    --%>
    <script src="/js/form/table-form-binding.js"></script>
    <%--    Script xử lý dropdown    --%>
    <script src="/js/dropdown.js"></script>
    <%--    Script tạo tablemananager và ánh xạ giá trị table-form (TeacherAssignment)   --%>
    <script src="/js/tables/teacherAssignmentTable.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const scheduleDateInput = document.getElementById("scheduleDate");
            const dayOfWeekInput = document.querySelector('input[name="dayOfWeek"]');

            setWeekDay(new Date(scheduleDateInput.value));

            scheduleDateInput.addEventListener("change", function () {
                const selectedDate = new Date(scheduleDateInput.value);
                setWeekDay(selectedDate);
            });

            function setWeekDay(date) {
                if (!isNaN(date)) {
                    const weekdays = ["Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"];
                    const dayOfWeek = weekdays[date.getDay()];
                    dayOfWeekInput.value = dayOfWeek;
                } else {
                    dayOfWeekInput.value = "";
                }
            }
        });
    </script>
`)
