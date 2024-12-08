<header class="bg-white shadow fixed top-0 w-[calc(100%-16rem)] z-5">
    <div class="flex flex-col md:flex-row justify-end items-center gap-4 p-8">
<%--        <form name="searchForm" action="${requestPath != null ? requestPath : "#"}" class="relative flex gap-2 items-center sm:w-1/2">--%>
<%--            <div class="relative w-full">--%>
<%--                <input--%>
<%--                        type="text"--%>
<%--                        name="keyword"--%>
<%--                        value="${keyword}"--%>
<%--                        placeholder="Tìm kiếm..."--%>
<%--                        class="border rounded-md px-4 py-2 text-sm w-full min-w-[150px]" />--%>
<%--                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"--%>
<%--                     fill="currentColor" aria-hidden="true" data-slot="icon"--%>
<%--                     class="w-6 h-6 absolute right-4 top-[50%] translate-y-[-50%]">--%>
<%--                    <path fill-rule="evenodd"--%>
<%--                          d="M9 3.5a5.5 5.5 0 1 0 0 11 5.5 5.5 0 0 0 0-11ZM2 9a7 7 0 1 1 12.452 4.391l3.328 3.329a.75.75 0 1 1-1.06 1.06l-3.329-3.328A7 7 0 0 1 2 9Z"--%>
<%--                          clip-rule="evenodd"></path>--%>
<%--                </svg>--%>
<%--            </div>--%>

<%--            <!-- Dropdown chọn bộ lọc -->--%>
<%--            <div class="relative min-w-[150px]">--%>
<%--                <select name="filter"--%>
<%--                        class="border rounded-md px-4 py-2 text-sm w-full bg-white"--%>
<%--                >--%>
<%--                    @if(filters == null || filters.isEmpty())--%>
<%--                        <option value=""></option>--%>
<%--                    @else--%>
<%--                        @for(Map.Entry<String, String> entry : filters.entrySet())--%>
<%--                            @if(!entry.getKey().equals("createdAt") && !entry.getKey().equals("updatedAt")--%>
<%--                                && !entry.getKey().toLowerCase().contains("id"))--%>
<%--                                <option value="${entry.getKey()}">--%>
<%--                                    ${entry.getValue()}--%>
<%--                                </option>--%>
<%--                            @endif--%>
<%--                        @endfor--%>
<%--                    @endif--%>
<%--                </select>--%>
<%--            </div>--%>
<%--        </form>--%>
        <div>
            <button class="text-sm px-4 py-2 rounded-md bg-gray-800 text-white hover:bg-gray-700">
                Đăng nhập
            </button>
        </div>
    </div>
</header>