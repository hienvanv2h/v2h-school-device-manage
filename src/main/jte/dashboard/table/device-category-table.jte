@import java.time.format.DateTimeFormatter
@import java.util.Map
@import com.vanhuuhien99.school_device_management.entity.DeviceCategory
@import com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState
@import org.springframework.data.domain.Page

@param Map<String, String> columnMapping
@param Page<DeviceCategory> deviceCategoryPage

@param Integer currentPage
@param String sortField
@param String sortDirection

@param GlobalTemplateState globalState

@param String apiPath = "/dashboard/device-categories"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="relative">
        <div class="sticky top-0 left-0 right-0 mb-4 bg-gray-800 rounded-lg p-8">
            <div class="flex justify-between items-center gap-2">
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
                                        @if(entry.getKey().equalsIgnoreCase("categoryName"))
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

                @if(globalState.isAdmin())
                    <a href="${apiPath}/create" class="px-4 py-2 bg-red-500 text-white rounded-md hover:bg-red-600 h-10">Thêm mới</a>
                @endif
            </div>
        </div>

        <div class="mt-[60px] overflow-x-auto">
            @if(deviceCategoryPage == null || deviceCategoryPage.isEmpty())
                <p class="text-gray-600">Không tìm thấy loại thiết bị</p>
            @else
                <%--    Table    --%>
                <table class="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                    <thead>
                    <tr>
                        @for(Map.Entry<String, String> entry: columnMapping.entrySet())
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[160px]">
                                <a href="${apiPath}?page=${currentPage}&size=${deviceCategoryPage.getSize()}&sort=${entry.getKey()},${sortField.equals(entry.getKey()) && sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc"}"
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
                    @for(var deviceCategory: deviceCategoryPage.getContent())
                        <%--    Lưu ý: Thứ tự cột cần khớp tương ứng với LinkedHashMap trong ColumnMapping    --%>
                        <tr class="hover:bg-gray-50">
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceCategory.getCategoryId()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceCategory.getCategoryName()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceCategory.getDescription()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceCategory.getUnit()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${deviceCategory.getUnitPrice()}</td>
                            <td class="px-6 py-4 text-sm text-gray-500 border-b">
                                ${DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy").format(deviceCategory.getCreatedAt())}
                            </td>
                            <td class="px-6 py-4 text-sm text-gray-500 border-b">
                                ${DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy").format(deviceCategory.getUpdatedAt())}
                            </td>

                            @if(globalState.isAdmin())
                                <td class="flex flex-wrap justify-start items-center gap-2 px-6 py-4 text-center border-b">
                                    <a href="${apiPath}/update/${deviceCategory.getCategoryId()}" class="w-24 px-4 py-2 text-sm text-white bg-blue-500 rounded-md hover:bg-blue-600 focus:ring-2 focus:ring-blue-300">
                                        Cập nhật
                                    </a>
                                    <button type="button" class="w-24 px-4 py-2 text-sm text-white bg-red-500 rounded-md hover:bg-red-600 focus:ring-2 focus:ring-red-300"
                                            onclick="confirmDelete(${deviceCategory.getCategoryId()}, '${apiPath}')">
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
            page = deviceCategoryPage,
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
`)