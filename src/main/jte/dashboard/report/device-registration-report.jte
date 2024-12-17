@import java.time.format.DateTimeFormatter
@import java.util.Map
@import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationReport
@import com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState
@import org.springframework.data.domain.Page

@param Page<DeviceRegistrationReport> deviceRegistrationReportPage
@param Map<String, String> columnMapping

@param Integer currentPage
@param String sortField
@param String sortDirection

@param GlobalTemplateState globalState

@param String currentPath = "/reports//device-registration-report"
@param String exportExcelPath = "/reports/device-registration-report/export"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="relative">
        <div class="sticky top-0 left-0 right-0 mb-4 bg-gray-800 rounded-lg p-8">
            <div class="flex flex-wrap justify-between items-start gap-2">
                <a href="${exportExcelPath}" target="_blank" class="ml-2 px-4 py-2 bg-red-500 text-white rounded-md hover:bg-red-600 h-10">Xuất Excel</a>
            </div>
        </div>

        <div class="mt-[60px] overflow-x-auto">
            @if(deviceRegistrationReportPage == null || deviceRegistrationReportPage.isEmpty())
                <p class="text-gray-600">Không có thông tin báo cáo đơn đăng ký</p>
            @else
            <%--    Table    --%>
                <table class="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                    <thead>
                    <tr>
                        @for(Map.Entry<String, String> entry: columnMapping.entrySet())
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[180px]">
                                <a href="${currentPath}?page=${currentPage}&size=${deviceRegistrationReportPage.getSize()}&sort=${entry.getKey()},${sortField.equals(entry.getKey()) && sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc"}"
                                   class="hover:underline">
                                    <%--    Tên cột hiển thị    --%>
                                    ${entry.getValue()}
                                    @if(sortField.equals(entry.getKey()))
                                        ${sortDirection.equalsIgnoreCase("asc") ? "▲" : "▼"}
                                    @endif
                                </a>
                            </th>
                        @endfor
                    </tr>
                    </thead>
                    <tbody>
                    @for(var reportItem: deviceRegistrationReportPage.getContent())
                    <%--    Lưu ý: Thứ tự cột cần khớp tương ứng với LinkedHashMap trong ReportColumnMapping    --%>
                        <tr class="hover:bg-gray-50">
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">
                                ${reportItem.getRegistrationDate() != null
                                    ? DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy").format(reportItem.getRegistrationDate()) : ""}
                            </td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${reportItem.getDeviceName()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${reportItem.getTeacherName()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${reportItem.getClassName()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${reportItem.getScheduleTime()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">
                                ${reportItem.getReturnDate() != null
                                    ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(reportItem.getReturnDate()) : ""}
                            </td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${reportItem.getDescription()}</td>
                        </tr>
                    @endfor
                    </tbody>
                </table>
            @endif
        </div>

        <%--    Pagination    --%>
        @template.component.pagination(
        page = deviceRegistrationReportPage,
        currentPage = currentPage,
        sortField = sortField,
        sortDirection = sortDirection,
        apiPath = currentPath,
        hasSort = sortField != null && !sortField.isEmpty() && sortDirection != null && !sortDirection.isEmpty()
        )

        <%--    CSRF    --%>
        ${globalState.getCsrfHiddenInput()}
    </div>
`)