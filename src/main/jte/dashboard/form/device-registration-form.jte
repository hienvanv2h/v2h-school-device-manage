@import java.util.Map
@import java.util.List
@import com.vanhuuhien99.school_device_management.entity.ApprovalStatusDefinition

@param java.util.List<String> errors
@param String type
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.DeviceRegistrationForm deviceRegistrationForm

@param Map<String, String> TA_COLUMN_MAPPING
@param Map<String, String> DEVICE_COLUMN_MAPPING
@param List<ApprovalStatusDefinition> approvalStatusList

@param com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState globalState

@param String apiPath = "/dashboard/device-registrations"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="flex flex-col xl:flex-row justify-between items-center gap-2 max-w-7xl mx-auto h-screen mt-8">
        <div class="flex flex-col gap-4 h-[100vh]">
            <div class="flex flex-col justify-between flex-1 max-w-3xl mx-auto max-h-[50%] h-full">
                <div class="overflow-auto p-2 flex-1 custom-scrollbar">
                    <%--    Teacher Assignment list    --%>
                    <table id="teacherAssignmentTable" class="bg-white border border-gray-200 rounded-lg shadow-md h-full">
                        <thead>
                        <tr>
                            @for(Map.Entry<String, String> entry: TA_COLUMN_MAPPING.entrySet())
                                <th id="${"tAssignment" + entry.getKey()}" class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[160px]">
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

            <div class="flex flex-col justify-between flex-1 max-w-3xl mx-auto max-h-[50%] h-full">
                <div class="overflow-auto p-2 flex-1 custom-scrollbar">
                    <%--    Device list    --%>
                    <table id="deviceTable" class="bg-white border border-gray-200 rounded-lg shadow-md h-full">
                        <thead>
                        <tr>
                            @for(Map.Entry<String, String> entry: DEVICE_COLUMN_MAPPING.entrySet())
                                <th id="${"device" + entry.getKey()}" class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[160px]">
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
                <nav id="devicePagination" class="mt-8 py-2 flex justify-center">
                    <ul class="inline-flex rounded-md">
                        <%--    Phân trang được rendered bởi JavaScript    --%>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="min-w-[30%] max-w-4xl mx-auto h-full">
            <form name="deviceRegistrationForm"
                  action="${apiPath}/save${type == null || type.equals("create") ? "" : "/" + id}"
                  method="POST"
                  class="bg-slate-100 p-6 rounded-lg shadow-md">
                <h2 class="text-2xl font-semibold text-gray-700 mb-4">Đăng ký mượn thiết bị</h2>
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
                               value="${deviceRegistrationForm != null && deviceRegistrationForm.getTeacherAssignmentId() != null
                                        ? deviceRegistrationForm.getTeacherAssignmentId().toString() : ""}"
                               class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                               required readonly>
                    </div>

                    <div>
                        <label for="deviceId" class="block text-gray-700 font-medium mb-2">
                            Device ID <span class="text-red-500">*</span>
                        </label>
                        <input type="text" id="deviceId" name="deviceId"
                               value="${deviceRegistrationForm != null && deviceRegistrationForm.getDeviceId() != null
                                        ? deviceRegistrationForm.getDeviceId().toString() : ""}"
                               class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                               required readonly>
                    </div>

                    <div>
                        <label for="description" class="block text-gray-700 font-medium mb-2">
                            Ghi chú
                        </label>
                        <input type="text" id="description" name="description"
                               value="${deviceRegistrationForm != null && deviceRegistrationForm.getDescription() != null
                                        ? deviceRegistrationForm.getDescription() : ""}"
                               class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                               required>
                    </div>

                    <div>
                        <label for="registrationStatus" class="block text-gray-700 font-medium mb-2">
                            Trạng thái đơn đăng ký <span class="text-red-500">*</span>
                        </label>
                        <input type="text" id="registrationStatus" name="registrationStatus"
                               value="${deviceRegistrationForm != null && deviceRegistrationForm.getRegistrationStatus() != null
                                        ? deviceRegistrationForm.getRegistrationStatus() : "Tạo mới"}"
                               class="px-4 py-2 w-full bg-yellow-300 border border-gray-300 rounded-md mt-2"
                               required readonly>
                    </div>

                    @if(type != null && type.equals("update"))
                        <%--Chỉ hiện thị Approval Status dropdown với form cập nhật--%>
                        <div class="dropdown relative">
                            <label class="block text-gray-700 font-medium mb-2">
                                Trạng thái phê duyệt <span class="text-red-500">*</span>
                            </label>
                            <input type="hidden" name="approvalStatus"
                                   value="${deviceRegistrationForm != null && deviceRegistrationForm.getApprovalStatus() != null
                                            ? deviceRegistrationForm.getApprovalStatus() : ""}"
                                   class="dropdown-input" />
                            <button type="button" class="dropdown-button py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                                <span class="dropdown-selected">
                                    @if(approvalStatusList != null && !approvalStatusList.isEmpty() && deviceRegistrationForm != null)
                                        ${approvalStatusList.stream()
                                                .map(ApprovalStatusDefinition::getApprovalStatus)
                                                .filter(as -> as.equalsIgnoreCase(deviceRegistrationForm.getApprovalStatus()))
                                                .findFirst()
                                                .orElse("")}
                                    @else
                                        ${"Chọn trạng thái phê duyệt"}
                                    @endif
                                </span>
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                                    <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                                </svg>
                            </button>
                            <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                                @if(approvalStatusList != null && !approvalStatusList.isEmpty())
                                    @for(var status: approvalStatusList)
                                        <li data-id="${status.getApprovalStatus()}" class="dropdown-item px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                            ${status.getApprovalStatus()}
                                        </li>
                                    @endfor
                                @else
                                    <li class="dropdown-item px-4 py-2 text-gray-500">N/A</li>
                                @endif
                            </ul>
                        </div>
                    @endif
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
    <%--    Script định nghĩa hàm lấy dữ liệu bảng (dùng chung)    --%>
    <script src="/js/table-manager-declaration.js"></script>
    <%--    Script gắn sự kiện chọn dòng từ bảng và ánh xạ giá trị cho input    --%>
    <script src="/js/table-form-binding.js"></script>
    <%--    Script xử lý dropdown    --%>
    <script src="/js/dropdown.js"></script>
    <%--    Script tạo tablemananager và ánh xạ giá trị table-form (TeacherAssignment)   --%>
    <script src="/js/tables/teacherAssignmentTable.js"></script>
    <%--    Script tạo tablemananager và ánh xạ giá trị table-form (Device)   --%>
    <script src="/js/tables/deviceTable.js"></script>
`)
