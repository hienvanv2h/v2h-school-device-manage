@import java.time.format.DateTimeFormatter
@import java.util.Map
@import com.vanhuuhien99.school_device_management.entity.SchoolClass
@import org.springframework.data.domain.Page

@param Map<String, String> columnMapping
@param Page<SchoolClass> schoolClassPage

@param Integer currentPage
@param String sortField
@param String sortDirection
@param String apiPath = "/dashboard/classes"

@template.layout.dashboard(content = @`
    <div class="overflow-x-auto">
        <div class="sticky top-0 left-0 right-0 mb-4 h-[50px]">
            <div class="flex justify-between items-center gap-2">
                <form id="filterForm" action="${apiPath}" method="GET">
                    <div class="flex items-center">
                        <input type="text" name="keyword" placeholder="Nhập tên lớp học" class="p-2 border border-gray-300 rounded-md">
                        <input type="hidden" name="filter" value="className">
                        <button type="submit" class="ml-2 px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Tìm kiếm</button>
                    </div>
                </form>

                <a href="${apiPath}/create" class="ml-2 px-4 py-2 bg-red-500 text-white rounded-md hover:bg-red-600">Thêm mới</a>
            </div>
        </div>

        <div class="mt-[60px]">
            @if(schoolClassPage == null || schoolClassPage.isEmpty())
                <p class="text-gray-600">Không tìm thấy thông tin lớp học</p>
            @else
                <%--    Table    --%>
                <table class="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                    <thead>
                    <tr>
                        @for(Map.Entry<String, String> entry: columnMapping.entrySet())
                            <th class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[160px]">
                                <a href="${apiPath}?page=${currentPage}&size=${schoolClassPage.getSize()}&sort=${entry.getKey()},${sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc"}"
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
                    @for(var schoolClass: schoolClassPage.getContent())
                        <tr class="hover:bg-gray-50">
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${schoolClass.getClassId()}</td>
                            <td class="px-6 py-4 text-sm text-gray-700 border-b">${schoolClass.getClassName()}</td>
                            <td class="px-6 py-4 text-sm text-gray-500 border-b">
                                ${DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy").format(schoolClass.getCreatedAt())}
                            </td>
                            <td class="px-6 py-4 text-sm text-gray-500 border-b">
                                ${DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy").format(schoolClass.getUpdatedAt())}
                            </td>
                            <td class="flex flex-wrap justify-start items-center gap-2 px-6 py-4 text-center border-b">
                                <a href="${apiPath}/update/${schoolClass.getClassId()}" class="w-24 px-4 py-2 text-sm text-white bg-blue-500 rounded-md hover:bg-blue-600 focus:ring-2 focus:ring-blue-300">
                                    Cập nhật
                                </a>
                                <button type="button" class="w-24 px-4 py-2 text-sm text-white bg-red-500 rounded-md hover:bg-red-600 focus:ring-2 focus:ring-red-300"
                                    onclick="confirmDelete(${schoolClass.getClassId()}, '${apiPath}')">
                                    Xóa
                                </button>
                            </td>
                        </tr>
                    @endfor
                    </tbody>
                </table>
            @endif
        </div>

        <%--    Pagination    --%>
        @template.component.pagination(
            page = schoolClassPage,
            currentPage = currentPage,
            sortField = sortField,
            sortDirection = sortDirection,
            apiPath = apiPath,
            hasSort = sortField != null && !sortField.isEmpty() && sortDirection != null && !sortDirection.isEmpty()
        )

        <%-- Modal Dialog --%>
        @template.component.delete-confirm-modal()
    </div>
`,
customScripts = @`
    <%--    Liên kết đến script mở modal xác nhận xóa bản ghi    --%>
    <script src="/js/delete-confirmation.js"></script>
`)