@param java.util.List<String> errors
@param String type
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.SchoolClassForm schoolClassForm

@param com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState globalState

@param String apiPath = "/dashboard/classes"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="max-w-2xl mx-auto mt-8">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Thêm lớp học mới</h2>
        <form name="schoolClassForm"
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
            <div>
                <%--    Hidden input for PUT method    --%>
                @if(type != null && type.equals("update"))
                    <input type="hidden" name="_method" value="PUT">
                @endif
                <%--    CSRF    --%>
                ${globalState.getCsrfHiddenInput()}

                <label for="className" class="block text-gray-700 font-medium mb-2">
                    Tên lớp <span class="text-red-500">*</span>
                </label>
                <input type="text" id="className" name="className"
                       value="${schoolClassForm != null ? schoolClassForm.getClassName() : ""}"
                       placeholder="Nhập tên lớp học"
                       class="px-4 py-2 w-full border border-gray-300 rounded-md">
            </div>
            <div class="flex justify-end mt-4">
                <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">
                    ${type == null || type.equals("create") ? "Thêm" : "Cập nhật"}
                </button>
            </div>
        </form>
    </div>
`)
