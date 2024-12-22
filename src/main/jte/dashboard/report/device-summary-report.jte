@import java.time.LocalDate
@import java.time.format.DateTimeFormatter
@import com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState

@param LocalDate startDate
@param LocalDate endDate

@param GlobalTemplateState globalState

@param String currentPath = "/reports/device-summary-report"
@param String exportExcelPath = "/reports/device-summary-report/export"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="relative">
        <div class="sticky top-0 left-0 right-0 mb-4 bg-gray-800 rounded-lg p-8">
            <div class="flex flex-wrap justify-between items-start gap-2">
                <form id="filterForm" action="${exportExcelPath}" method="GET">
                    <div class="flex flex-wrap gap-2 items-center h-full">
                        <input type="date" name="startDate"
                               value="${startDate != null ? startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : ""}"
                               class="p-2 border border-gray-300 rounded-md h-10">
                        <input type="date" name="endDate"
                               value="${endDate != null ? endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : ""}"
                               class="p-2 border border-gray-300 rounded-md h-10">
                        <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 h-10">Xuất Excel</button>
                    </div>
                </form>
            </div>
        </div>

    </div>
`)