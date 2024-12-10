@param org.springframework.data.domain.Page<?> page
@param Integer currentPage
@param String sortField
@param String sortDirection
@param String apiPath
@param boolean hasSort = false

@if(page != null && currentPage != null && page.getTotalPages() > 1 && currentPage > 0 && apiPath != null && !apiPath.isEmpty())
    <div class="mt-8 py-2 flex justify-center">
        <nav class="inline-flex rounded-md shadow-xl">
            @if(page.hasPrevious())
                <a href="${apiPath}?page=${currentPage - 1}&sort=${hasSort ? sortField + "," + sortDirection : ""}"
                   class="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50">
                    &lt;
                </a>
            @endif

            <%--       Show 5 pages include current page         --%>
            @for(int i = Math.max(1, currentPage - 2); i <= Math.min(page.getTotalPages(), currentPage + 2); i++)
                <a href="${apiPath}?page=${i}&size=${page.getSize()}&sort=${hasSort ? sortField + "," + sortDirection : ""}"
                   class="relative inline-flex items-center px-4 py-2 border border-gray-300 ${i == currentPage ? "bg-blue-500 text-white" : "bg-white text-gray-700 hover:bg-gray-50"} text-sm font-medium">
                    ${i}
                </a>
            @endfor

            @if(page.hasNext())
                <a href="${apiPath}?page=${currentPage + 1}&sort=${hasSort ? sortField + "," + sortDirection : ""}"
                   class="relative inline-flex items-center px-4 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50">
                    &gt;
                </a>
            @endif
        </nav>
    </div>
@endif
