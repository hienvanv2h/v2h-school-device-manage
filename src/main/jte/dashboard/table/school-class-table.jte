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
        <table class="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
            <thead>
            <tr>
                @for(Map.Entry<String, String> entry: columnMapping.entrySet())
                    <th class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[120px]">
                        <a href="${apiPath}?page=${currentPage}&size=${schoolClassPage.getSize()}&sort=${entry.getKey()},${sortField.equals(entry.getKey()) && sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc"}"
                           class="hover:underline">
                            <%--    Tên cột hiển thị    --%>
                            ${entry.getValue()}
                            @if(sortField.equals(entry.getKey()))
                                ${sortDirection.equalsIgnoreCase("asc") ? "▲" : "▼"}
                            @endif
                        </a>
                    </th>
                @endfor
                <th class="px-6 py-3 text-left text-sm font-medium text-gray-700 bg-gray-100 border-b min-w-[120px]">Actions</th>
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
                    <td class="flex gap-2 px-6 py-4 text-center border-b">
                        <button class="px-4 py-2 text-sm text-white bg-blue-500 rounded-md hover:bg-blue-600 focus:ring-2 focus:ring-blue-300">
                            Edit
                        </button>
                        <button class="px-4 py-2 text-sm text-white bg-red-500 rounded-md hover:bg-red-600 focus:ring-2 focus:ring-red-300">
                            Delete
                        </button>
                    </td>
                </tr>
            @endfor
            </tbody>
        </table>

        <%--    Pagination    --%>
        <div class="mt-8 flex justify-center">
            <nav class="inline-flex rounded-md shadow-xl">
                @if(schoolClassPage.hasPrevious())
                    <a href="${apiPath}?page=${currentPage - 1}&sort=${sortField},${sortDirection}"
                       class="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50">
                        &lt;
                    </a>
                @endif

                <%--       Show 5 pages include current page         --%>
                @for(int i = Math.max(1, currentPage - 2); i <= Math.min(schoolClassPage.getTotalPages(), currentPage + 2); i++)
                    <a href="${apiPath}?page=${i}&size=${schoolClassPage.getSize()}&sort=${sortField},${sortDirection}"
                       class="relative inline-flex items-center px-4 py-2 border border-gray-300 ${i == currentPage ? "bg-blue-500 text-white" : "bg-white text-gray-700 hover:bg-gray-50"} text-sm font-medium">
                        ${i}
                    </a>
                @endfor

                @if(schoolClassPage.hasNext())
                    <a href="${apiPath}?page=${currentPage + 1}&sort=${sortField},${sortDirection}"
                       class="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50">
                        &gt;
                    </a>
                @endif
            </nav>
        </div>
    </div>
`,
requestPath = apiPath,
filters = columnMapping
)