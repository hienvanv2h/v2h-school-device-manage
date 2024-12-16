@param java.util.List<String> errors
@param String type
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.TeacherForm teacherForm
@param String[] genders = new String[]{"Nam", "Nữ"}

@param com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState globalState

@param String apiPath = "/dashboard/teachers"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="max-w-2xl mx-auto mt-8">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Thêm giáo viên</h2>
        <form name="teacherForm"
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
                    <label for="fullName" class="block text-gray-700 font-medium mb-2">
                        Họ và tên <span class="text-red-500">*</span>
                    </label>
                    <input type="text" id="fullName" name="fullName"
                           value="${teacherForm != null ? teacherForm.getFullName() : ""}"
                           placeholder="Nhập họ và tên"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                           required>
                </div>

                <div>
                    <label for="email" class="block text-gray-700 font-medium mb-2">
                        Email
                    </label>
                    <input type="email" id="email" name="email"
                           value="${teacherForm != null ? teacherForm.getEmail() : ""}"
                           placeholder="Email"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2">
                </div>

                <div>
                    <label for="phoneNumber" class="block text-gray-700 font-medium mb-2">
                        Số điện thoại <span class="text-red-500">*</span>
                    </label>
                    <input type="text" id="phoneNumber" name="phoneNumber"
                           value="${teacherForm != null ? teacherForm.getPhoneNumber() : ""}"
                           placeholder="Nhập số điện thoại"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                           required>
                </div>

                <div>
                    <label for="dateOfBirth" class="block text-gray-700 font-medium mb-2">
                        Ngày sinh <span class="text-red-500">*</span>
                    </label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth"
                           value="${teacherForm != null && teacherForm.getDateOfBirth() != null ? teacherForm.getDateOfBirth().toString() : ""}"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                           required>
                </div>

                <div>
                    <label for="gender" class="block text-gray-700 font-medium mb-2">
                        Giới tính
                    </label>
                    <select id="gender" name="gender" class="py-2 px-4 border border-gray-300 rounded-md">
                        @for(String gender: genders)
                            <option value="${gender}"
                                    selected="${teacherForm != null && teacherForm.getGender() != null && teacherForm.getGender().equals(gender)}">
                                ${gender}
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
    <script>
        const today = new Date().toISOString().split('T')[0]; // Lấy ngày hôm nay ở định dạng yyyy-MM-dd
        document.getElementById('dateOfBirth').setAttribute('max', today);  // Thiet lập giới hạn cho input ngày sinh (max = ngày hôm nay)
    </script>
`)
