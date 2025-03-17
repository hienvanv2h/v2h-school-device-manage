@param java.util.List<String> errors
@param String type
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.DeviceCategoryForm deviceCategoryForm

@param com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState globalState

@param String apiPath = "/dashboard/device-categories"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="max-w-2xl mx-auto mt-8">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Thêm loại thiết bị mới</h2>
        <form name="deviceCategoryForm"
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
                    <label for="categoryName" class="block text-gray-700 font-medium mb-2">
                        Tên loại thiết bị <span class="text-red-500">*</span>
                    </label>
                    <input type="text" id="categoryName" name="categoryName"
                           value="${deviceCategoryForm != null ? deviceCategoryForm.getCategoryName() : ""}"
                           placeholder="Nhập tên loại thiết bị"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                           required>
                </div>

                <div>
                    <label for="description" class="block text-gray-700 font-medium mb-2">
                        Mô tả
                    </label>
                    <textarea id="description" name="description" rows="3" cols="80"
                              placeholder="Nhập mô tả vào đây"
                              class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                              style="white-space: pre-wrap; overflow-wrap: break-word;"
                    >${deviceCategoryForm != null && deviceCategoryForm.getDescription() != null ? deviceCategoryForm.getDescription().trim() : ""}</textarea>
                </div>

                <div>
                    <label for="unit" class="block text-gray-700 font-medium mb-2">
                        Đơn vị tính <span class="text-red-500">*</span>
                    </label>
                    <input type="text" id="unit" name="unit"
                           value="${deviceCategoryForm != null ? deviceCategoryForm.getUnit() : ""}"
                           placeholder="Nhập đơn vị tính"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                           required>
                </div>

                <div>
                    <label for="unitPrice" class="block text-gray-700 font-medium mb-2">
                        Đơn giá nhập (VND) <span class="text-red-500">*</span>
                    </label>
                    <input type="number" id="unitPrice" name="unitPrice"
                           value="${deviceCategoryForm != null ? deviceCategoryForm.getUnitPrice().toString() : ""}"
                           placeholder="Nhập số tiền"
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
`)
