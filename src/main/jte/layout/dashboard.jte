@import gg.jte.Content
@import com.vanhuuhien99.school_device_management.viewmodel.Page
@import java.util.Map

@param Page page = null
@param Content content = null
@param Content footer = null
@param Content customStyles = null
@param Content customScripts = null
<%-- CsrfToken --%>

<%--    Searching    --%>
@param String keyword = ""
@param Map<String, String> filters = new java.util.HashMap<String, String>()
@param String requestPath = null

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    @if(page != null && !page.description().isEmpty())
        <meta name="description" content="${page.description()}">
    @endif
    <title>${(page != null && !page.title().isEmpty()) ? page.title() : "Quản lý thiết bị học tập"}</title>

    <%--  Tailwind CSS  --%>
    <script src="https://cdn.tailwindcss.com"></script>
    <%--  Custom styles  --%>
    @if(customStyles != null)
        ${customStyles}
    @endif
</head>
<body class="bg-gray-100 h-screen">
    <div class="flex h-full">
        <!-- Sidebar -->
        <aside
                class="bg-gray-800 text-white w-64 flex flex-col justify-between p-2 h-screen">
            <div>
                <!-- Sidebar Top -->
                <div class="p-2 flex items-center mb-8">
                    <svg class="w-8 h-8 mr-3" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd"
                              d="M12.316 3.051a1 1 0 01.633 1.265l-4 12a1 1 0 01-1.265.633L5.432 15.5l-2.539.639a1 1 0 01-1.265-.633l-1-3a1 1 0 01.633-1.265L4 9.5l2.961-8.822a1 1 0 011.265-.633l3 1z"
                              clip-rule="evenodd" />
                    </svg>
                    <h2 class="text-xl font-bold">Dashboard</h2>
                </div>
                <div class="h-px border border-gray-700 my-2"></div>

                <!-- Sidebar Menu -->
                @template.dashboard.sidebar-menu()
            </div>

            <!-- Sidebar Bottom -->
            <div class="p-4 border-t border-gray-700">
                <a href="#"
                   class="flex items-center p-2 rounded-md hover:bg-gray-700">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none"
                         viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
                         aria-hidden="true" data-slot="icon" class="w-6 h-6 mr-3"><path
                                stroke-linecap="round" stroke-linejoin="round"
                                d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.325.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 0 1 1.37.49l1.296 2.247a1.125 1.125 0 0 1-.26 1.431l-1.003.827c-.293.241-.438.613-.43.992a7.723 7.723 0 0 1 0 .255c-.008.378.137.75.43.991l1.004.827c.424.35.534.955.26 1.43l-1.298 2.247a1.125 1.125 0 0 1-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.47 6.47 0 0 1-.22.128c-.331.183-.581.495-.644.869l-.213 1.281c-.09.543-.56.94-1.11.94h-2.594c-.55 0-1.019-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 0 1-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 0 1-1.369-.49l-1.297-2.247a1.125 1.125 0 0 1 .26-1.431l1.004-.827c.292-.24.437-.613.43-.991a6.932 6.932 0 0 1 0-.255c.007-.38-.138-.751-.43-.992l-1.004-.827a1.125 1.125 0 0 1-.26-1.43l1.297-2.247a1.125 1.125 0 0 1 1.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.086.22-.128.332-.183.582-.495.644-.869l.214-1.28Z"></path><path
                                stroke-linecap="round" stroke-linejoin="round"
                                d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"></path></svg>
                    Settings
                </a>
            </div>
        </aside>

        <div class="flex-1 flex flex-col">
            <%--    Header    --%>
            @template.dashboard.header(keyword = keyword, filters = filters, requestPath = requestPath)

            <!-- Main Content -->
            <main class="flex-1 bg-white p-8 overflow-y-auto">
                ${content}
            </main>
        </div>
    </div>
</body>
</html>