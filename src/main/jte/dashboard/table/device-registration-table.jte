@import java.net.URLEncoder
@import java.nio.charset.StandardCharsets
@import java.time.format.DateTimeFormatter
@import java.util.List
@import java.util.Map
@import com.vanhuuhien99.school_device_management.entity.ApprovalStatusDefinition
@import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationDTO
@import com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState
@import org.springframework.data.domain.Page

@param Page<DeviceRegistrationDTO> deviceRegistrationPage
@param Map<String, String> columnMapping
@param List<ApprovalStatusDefinition> approvalStatusList

@param Integer currentPage
@param String sortField
@param String sortDirection

@param GlobalTemplateState globalState

@param String apiPath = "/dashboard/device-registrations"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="relative">
        <div class="sticky top-0 left-0 right-0 mb-4 bg-gray-800 rounded-lg p-8">
            <div class="flex flex-wrap justify-between items-start gap-2">
                <form id="filterForm" action="${apiPath}" method="GET">
                    <div class="flex flex-wrap gap-2 items-center h-full">
                        <input type="text" name="keyword" placeholder="Nhập từ khóa" class="p-2 border border-gray-300 rounded-md h-10">
                        <!-- Dropdown chọn bộ lọc -->
                        <div class="relative min-w-[150px]">
                            <select name="filter"
                                    class="border rounded-md px-4 py-2 text-sm w-full bg-white h-10"
                            >
                                @if(columnMapping == null || columnMapping.isEmpty())
                                    <option value=""></option>
                                @else
                                    @for(Map.Entry<String, String> entry : columnMapping.entrySet())
                                    <%--    Refer to ColumnMapping class    --%>
                                        @if(entry.getKey().contains("teacher.fullName")
                                        || entry.getKey().contains("device.deviceName"))
                                            <option value="${entry.getKey()}">
                                                ${entry.getValue()}
                                            </option>
                                        @endif
                                    @endfor
                                @endif
                            </select>
                        </div>
                        <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 h-10">Tìm kiếm</button>
                    </div>
                </form>

                <%--    Approval Status Filter    --%>
                <div class="dropdown relative">
                    <button type="button" class="dropdown-button bg-white py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                        <span class="dropdown-selected">Chọn trạng thái phê duyệt</span>
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                        </svg>
                    </button>
                    <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                        @if(approvalStatusList != null && !approvalStatusList.isEmpty())
                            @for(var status: approvalStatusList)
                                <li data-id="${status.getStatusId()}" class="dropdown-item hover:bg-gray-100 cursor-pointer">
                                    <a href="${apiPath}?approvalStatus=${URLEncoder.encode(status.getApprovalStatus(), StandardCharsets.UTF_8)}"
                                        class="inline-block px-4 py-2">
                                        ${status.getApprovalStatus()}
                                    </a>
                                </li>
                            @endfor
                        @else
                            <li class="dropdown-item px-4 py-2 text-gray-500">N/A</li>
                        @endif
                    </ul>
                </div>
            </div>
        </div>

        <div class="mt-[60px] overflow-x-auto">
            @if(deviceRegistrationPage == null || deviceRegistrationPage.isEmpty())
                <p class="text-gray-600">Không tìm thấy thông tin đơn đăng ký</p>
            @else
            <%--    Table    --%>
                <table class="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                    <thead>
                    <tr>
                        @for(Map.Entry<String, String> entry: columnMapping.entrySet())
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[180px]">
                                <a href="${apiPath}?page=${currentPage}&size=${deviceRegistrationPage.getSize()}&sort=${entry.getKey()},${sortField.equals(entry.getKey()) && sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc"}"
                                   class="hover:underline">
                                    <%--    Tên cột hiển thị    --%>
                                    ${entry.getValue()}
                                    @if(sortField.equals(entry.getKey()))
                                        ${sortDirection.equalsIgnoreCase("asc") ? "▲" : "▼"}
                                    @endif
                                </a>
                            </th>
                        @endfor
                        <th class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[160px]">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    @for(var deviceRegistration: deviceRegistrationPage.getContent())
                        <%--    Lưu ý: Thứ tự cột cần khớp tương ứng với LinkedHashMap trong ColumnMapping    --%>
                        <tr class="hover:bg-gray-50">
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceRegistration.getRegistrationId()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceRegistration.getDeviceName()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceRegistration.getTeacherName()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceRegistration.getDescription()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceRegistration.getRegistrationStatus()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceRegistration.getApprovalStatus()}</td>
                            <td class="px-6 py-4 text-sm text-gray-500 border-b">
                                ${DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy").format(deviceRegistration.getCreatedAt())}
                            </td>
                            <td class="px-6 py-4 text-sm text-gray-500 border-b">
                                ${DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy").format(deviceRegistration.getUpdatedAt())}
                            </td>

                            @if(globalState.isAdmin())
                                <td class="flex flex-wrap justify-start items-center gap-2 px-6 py-4 text-center border-b">
                                    <a href="${apiPath}/update/${deviceRegistration.getRegistrationId()}" class="w-24 px-4 py-2 text-sm text-white bg-blue-500 rounded-md hover:bg-blue-600 focus:ring-2 focus:ring-blue-300">
                                        Cập nhật
                                    </a>
                                    <button type="button" class="w-24 px-4 py-2 text-sm text-white bg-red-500 rounded-md hover:bg-red-600 focus:ring-2 focus:ring-red-300"
                                            onclick="confirmDelete(${deviceRegistration.getRegistrationId()}, '${apiPath}')">
                                        Xóa
                                    </button>
                                </td>
                            @else
                                <td class="px-6 py-4 text-sm text-gray-500 border-b"></td>
                            @endif
                        </tr>
                    @endfor
                    </tbody>
                </table>
            @endif
        </div>

        <%--    Pagination    --%>
        @template.component.pagination(
        page = deviceRegistrationPage,
        currentPage = currentPage,
        sortField = sortField,
        sortDirection = sortDirection,
        apiPath = apiPath,
        hasSort = sortField != null && !sortField.isEmpty() && sortDirection != null && !sortDirection.isEmpty()
        )

        <%--    CSRF    --%>
        ${globalState.getCsrfHiddenInput()}

        <%-- Modal Dialog --%>
        @template.component.delete-confirm-modal()
    </div>
`,
customScripts = @`
    <%--    Liên kết đến script mở modal xác nhận xóa bản ghi    --%>
    <script src="/js/delete-confirmation.js"></script>
    <%--    Script xử lý dropdown    --%>
    <script src="/js/dropdown.js"></script>
`)