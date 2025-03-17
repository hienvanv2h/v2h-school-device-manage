@param java.util.List<String> errors
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.UserFormUpdate form
@param java.util.List<com.vanhuuhien99.school_device_management.entity.Role> roles

@param com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState globalState

@param String apiPath = "/manage/users"

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <div class="max-w-2xl mx-auto mt-8">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Cập nhật tài khoản</h2>
        <form name="userForm"
              action="${apiPath}/save/${id}"
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
                <input type="hidden" name="_method" value="PUT">

                <%--    CSRF    --%>
                ${globalState.getCsrfHiddenInput()}

                <div>
                    <label for="fullName" class="block text-gray-700 font-medium mb-2">
                        Họ và tên <span class="text-red-500">*</span>
                    </label>
                    <input type="text" id="fullName" name="fullName"
                           value="${form != null && form.getFullName() != null ? form.getFullName() : ""}"
                           placeholder="Nhập họ và tên"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                           required>
                </div>

                <div>
                    <label for="phoneNumber" class="block text-gray-700 font-medium mb-2">
                        Số điện thoại <span class="text-red-500">*</span>
                    </label>
                    <input type="text" id="phoneNumber" name="phoneNumber"
                           value="${form != null && form.getPhoneNumber() != null ? form.getPhoneNumber() : ""}"
                           placeholder="Nhập số điện thoại"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                           required>
                </div>

<%--                <div>--%>
<%--                    <label for="password" class="block text-gray-700 font-medium mb-2">--%>
<%--                        Mật khẩu <span class="text-red-500">*</span>--%>
<%--                    </label>--%>
<%--                    <input type="password" id="password" name="password"--%>
<%--                           value="${form != null && form.getPassword() != null ? form.getPassword() : ""}"--%>
<%--                           placeholder="Nhập mật khẩu"--%>
<%--                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2">--%>
<%--                </div>--%>

<%--                <div>--%>
<%--                    <label for="confirmPassword" class="block text-gray-700 font-medium mb-2">--%>
<%--                        Xác nhận mật khẩu <span class="text-red-500">*</span>--%>
<%--                    </label>--%>
<%--                    <input type="password" id="confirmPassword" name="confirmPassword"--%>
<%--                           value="${form != null && form.getConfirmPassword() != null ? form.getConfirmPassword() : ""}"--%>
<%--                           placeholder="Nhập mật khẩu"--%>
<%--                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2">--%>
<%--                </div>--%>

                <div>
                    <label for="roleId" class="block text-gray-700 font-medium mb-2">
                        Phân quyền
                    </label>
                    <select id="roleId" name="roleId" class="py-2 px-4 border border-gray-300 rounded-md">
                        @for(var role: roles)
                            <option value="${role.getRoleId()}"
                                selected="${form != null && form.getRoleId() != null && form.getRoleId().equals(role.getRoleId())}">
                                ${role.getRoleName()}
                            </option>
                        @endfor
                    </select>
                </div>

                <div>
                    <label for="isActive" class="block text-gray-700 font-medium mb-2">
                        Trạng thái
                    </label>
                    <select id="isActive" name="isActive" class="py-2 px-4 border border-gray-300 rounded-md">
                            <option value="true"
                                    selected="${form != null && form.getIsActive() != null && form.getIsActive()}">
                                Đang hoạt động
                            </option>
                            <option value="false"
                                    selected="${form == null || form.getIsActive() == null || !form.getIsActive()}">
                                Dừng hoạt động
                            </option>
                    </select>
                </div>
            </div>
            <div class="flex justify-end mt-4">
                <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Cập nhật</button>
            </div>
        </form>
    </div>
`)
