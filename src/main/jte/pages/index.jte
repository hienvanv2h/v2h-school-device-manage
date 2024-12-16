@param com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState globalState

@template.layout.dashboard(
header = @`
    @template.dashboard.header(globalState = globalState)
`,
content = @`
    <h1 class="text-3xl font-bold mb-6">Trang chủ</h1>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div class="bg-gray-100 p-6 rounded-lg shadow-md">
            <h2 class="text-xl font-semibold mb-4">Phân công</h2>
            <p class="text-gray-600">Phân công chuyên môn</p>
            <p class="text-gray-600">Tạo thời khóa biểu</p>
        </div>
        <div class="bg-gray-100 p-6 rounded-lg shadow-md">
            <h2 class="text-xl font-semibold mb-4">Đăng ký mượn thiết bị </h2>
            <p class="text-gray-600">Đăng ký mượn thiết bị dạy học</p>
        </div>
        <div class="bg-gray-100 p-6 rounded-lg shadow-md">
            <h2 class="text-xl font-semibold mb-4">Duyệt đơn đăng ký</h2>
            <p class="text-gray-600">Quyết định phê duyệt/từ chối đơn đăng ký mượn thiết bị dạy học </p>
        </div>
    </div>
`)