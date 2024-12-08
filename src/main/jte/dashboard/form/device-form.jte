@import com.vanhuuhien99.school_device_management.dto.DeviceCategoryDropdownDto

@param java.util.List<String> errors
@param String type
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.DeviceForm deviceForm
@param java.util.List<DeviceCategoryDropdownDto> deviceCategoryDropdown
@param String[] statusList = new String[]{"Còn nguyên", "Hỏng", "Mất"}

@template.layout.dashboard(content = @`
    <div class="max-w-2xl mx-auto mt-8">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Thêm thiết bị</h2>
        <form name="deviceForm"
              action="/dashboard/devices/save${type == null || type.equals("create") ? "" : "/" + id}"
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
                <div>
                    <label for="deviceCategoryId" class="block text-gray-700 font-medium mb-2">
                        Loại thiết bị
                    </label>
                    <select id="deviceCategoryId" name="deviceCategoryId" class="py-2 px-4 border border-gray-300 rounded-md"
                            onmousedown="if(this.options.length>5){this.size=5;}"  onchange='this.size=0;' onblur="this.size=0;">
                        @for(int i = 0; i < deviceCategoryDropdown.size() * 5; i++)

                            @for(var deviceCategory: deviceCategoryDropdown)
                                <option value="${deviceCategory.getCategoryId()}"
                                        selected="${deviceForm != null && deviceForm.getDeviceCategoryId().equals(deviceCategory.getCategoryId())}">
                                    ${deviceCategory.getCategoryName()}
                                </option>
                            @endfor
                        @endfor
                    </select>
                </div>

                <div>
                    <label for="description" class="block text-gray-700 font-medium mb-2">
                        Mô tả
                    </label>
                    <input type="text" id="description" name="description"
                           value="${deviceForm != null ? deviceForm.getDescription() : ""}"
                           placeholder="Nhập mô tả cho thiết bị"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2">
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
`)
