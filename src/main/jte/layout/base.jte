@import gg.jte.Content
@import com.vanhuuhien99.school_device_management.viewmodel.Page

@param Page page = null
@param Content content = null
@param Content footer = null
@param Content customStyles = null
@param Content customScripts = null
<%-- CsrfToken --%>

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
<body>

    <!-- Main content -->
    <main>
        <div class="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
            ${content}
        </div>
    </main>

    @if (footer != null)
        <div class="footer">
            ${footer}
        </div>
    @endif

    <%-- Custom scripts --%>
    @if (customScripts != null)
        ${customScripts}
    @endif
</body>
</html>