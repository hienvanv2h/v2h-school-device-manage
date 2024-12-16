@param com.vanhuuhien99.school_device_management.viewmodel.GlobalTemplateState globalState

<header class="bg-white shadow fixed top-0 w-[calc(100%-16rem)] z-10">
    <div class="flex flex-col md:flex-row justify-end items-center gap-4 p-8">
        <div class="flex items-center gap-4">
            @if(globalState != null && globalState.isAuthenticated())
                <!-- Hiển thị thông tin profile nếu người dùng đã đăng nhập -->
                <div class="flex items-center gap-4">
                    <span class="text-sm font-medium text-gray-700">Xin chào, <b>${globalState.getUsername()}</b></span>
                    <form action="/logout" method="post">
                        ${globalState.getCsrfHiddenInput()}
                        <button type="submit" class="text-sm px-4 py-2 rounded-md bg-blue-500 text-white hover:bg-blue-600 focus:ring-2 focus:ring-blue-300">
                            Đăng xuất
                        </button>
                    </form>
                </div>
            @else
                <!-- Hiển thị nút đăng nhập nếu chưa đăng nhập -->
                <div class="hidden md:block">
                    <a href="/login" class="text-sm px-4 py-2 rounded-md bg-gray-800 text-white hover:bg-gray-700">
                        Đăng nhập
                    </a>
                </div>
            @endif
        </div>
    </div>
</header>

<%--<header class="bg-white shadow fixed top-0 w-[calc(100%-16rem)] z-5">--%>
<%--    <div class="flex flex-col md:flex-row justify-end items-center gap-4 p-8">--%>
<%--        <div>--%>
<%--            <button class="text-sm px-4 py-2 rounded-md bg-gray-800 text-white hover:bg-gray-700">--%>
<%--                Đăng nhập--%>
<%--            </button>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</header>--%>