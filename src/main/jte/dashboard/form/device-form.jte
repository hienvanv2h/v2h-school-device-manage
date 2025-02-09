@import com.vanhuuhien99.school_device_management.projection.DeviceCategoryDTO

@param java.util.List<String> errors
@param String type
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.DeviceForm deviceForm
@param java.util.List<DeviceCategoryDTO> deviceCategoryDropdown
@param String[] statusList = new String[]{"Còn nguyên", "Hỏng", "Mất"}

@param com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState globalState

@param String apiPath = "/dashboard/devices"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="max-w-2xl mx-auto mt-8">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Thêm thiết bị</h2>
        <form name="deviceForm"
              action="${apiPath}/save${type == null || type.equals("create") ? "" : "/" + id}"
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
                <%--    Hidden input for PUT method    --%>
                @if(type != null && type.equals("update"))
                    <input type="hidden" name="_method" value="PUT">
                @endif

                <%--    CSRF    --%>
                ${globalState.getCsrfHiddenInput()}

                <div>
                    <label for="deviceName" class="block text-gray-700 font-medium mb-2">
                        Tên thiết bị <span class="text-red-500">*</span>
                    </label>
                    <input type="text" id="deviceName" name="deviceName"
                           value="${deviceForm != null ? deviceForm.getDeviceName() : ""}"
                           placeholder="Nhập tên thiết bị"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                           required>
                </div>

                <%--    Device Category    --%>
                <div class="dropdown relative">
                    <label class="block text-gray-700 font-medium mb-2">
                        Loại thiết bị <span class="text-red-500">*</span>
                    </label>
                    <input type="hidden" name="deviceCategoryId"
                           value="${type != null && type.equals("update") && deviceForm != null && deviceForm.getDeviceCategoryId() != null
                                    ? deviceForm.getDeviceCategoryId().toString() : ""}"
                           class="dropdown-input" />
                    <button type="button" class="dropdown-button py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                        <span class="dropdown-selected">
                            @if(type != null && type.equals("update")
                                    && deviceCategoryDropdown != null && !deviceCategoryDropdown.isEmpty() && deviceForm != null)
                                ${deviceCategoryDropdown.stream()
                                        .filter(dc -> dc.getCategoryId().equals(deviceForm.getDeviceCategoryId()))
                                        .map(DeviceCategoryDTO::getCategoryName)
                                        .findFirst()
                                        .orElse("")}
                            @else
                                ${"Chọn loại thiết bị"}
                            @endif
                        </span>
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                        </svg>
                    </button>
                    <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                        @if(deviceCategoryDropdown != null && !deviceCategoryDropdown.isEmpty())
                            @for(var category: deviceCategoryDropdown)
                                <li data-id="${category.getCategoryId()}" class="dropdown-item px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                    ${category.getCategoryName()}
                                </li>
                            @endfor
                        @else
                            <li class="dropdown-item px-4 py-2 text-gray-500">Không có loại thiết bị</li>
                        @endif
                    </ul>
                </div>

                <div>
                    <label for="description" class="block text-gray-700 font-medium mb-2">
                        Mô tả
                    </label>
                    <textarea id="description" name="description" rows="3" cols="80"
                              placeholder="Nhập mô tả vào đây"
                              class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                              style="white-space: pre-wrap; overflow-wrap: break-word;"
                    >${deviceForm != null && deviceForm.getDescription() != null ? deviceForm.getDescription().trim() : ""}</textarea>
                </div>

                <div>
                    <label for="status" class="block text-gray-700 font-medium mb-2">
                        Tình trạng thiết bị
                    </label>
                    <select id="status" name="status" class="py-2 px-4 border border-gray-300 rounded-md">
                        @for(String status: statusList)
                            <option value="${status}"
                                    selected="${deviceForm != null && deviceForm.getStatus().equals(status)}">
                                ${status}
                            </option>
                        @endfor
                    </select>
                </div>
            </div>
            <div class="flex justify-end mt-4">
                <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">
                    ${type == null || type.equals("create") ? "Thêm" : "Cập nhật"}
                </button>
            </div>
        </form>
    </div>
`,
customScripts = @`
    <%--    Script xử lý dropdown    --%>
    <script src="/js/dropdown.js"></script>
`)
