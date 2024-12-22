document.addEventListener("DOMContentLoaded", function () {
  const tableSelector = "#deviceTable";
  const paginationSelector = "#devicePagination";
  const formInputSelector = "#deviceId";
  const searchInputSelector = "#deviceSearch";

  const tableBody = document.querySelector(`${tableSelector} tbody`);
  const paginationContainer = document.querySelector(
    `${paginationSelector} ul`
  );
  const searchInput = document.querySelector(searchInputSelector);

  if (!tableBody || !paginationContainer) return;

  const apiPath = "/dashboard/devices/api/data";
  const defaultPage =
    new URLSearchParams(window.location.search).get("page") || 1;
  const defaultSize =
    new URLSearchParams(window.location.search).get("size") || 10;
  const defaultSort =
    new URLSearchParams(window.location.search).get("sort") || "deviceName,asc";

  // Thiết lập giá trị debounce nhằm giảm tải fetch dữ liệu liên tục khi input thay đổi
  let debounceTimeout;

  // Gọi API lấy dữ liệu
  async function fetchDevices({
    page = defaultPage,
    size = defaultSize,
    sort = defaultSort,
    searchText = "",
  }) {
    const fetchDevicesUrl = `${apiPath}?page=${page}&size=${size}&sort=${sort}${
      searchText ? `&deviceName=${searchText}` : ""
    }`;
    try {
      const response = await fetch(fetchDevicesUrl);
      if (!response.ok) {
        throw new Error("Failed to fetch data");
      }

      const data = await response.json();
      renderTableRows(data.content);
      renderPagination(data.page.totalPages, data.page.number);
    } catch (error) {
      console.error("Error when fetching devices:", error);
      tableBody.innerHTML = `
            <tr>
                <td colspan="100%" class="text-start text-red-500 py-4">
                    Không thể tải dữ liệu, vui lòng thử lại sau.
                </td>
            </tr>`;
    }
  }

  function debounceFetchDevices(params) {
    clearTimeout(debounceTimeout);
    debounceTimeout = setTimeout(() => fetchDevices(params), 1000); // 1 giây
  }

  // Định dạng lại thời gian hiển thị
  function formatDateTime(dateTime) {
    const date = new Date(dateTime);
    const hours = date.getHours().toString().padStart(2, "0");
    const minutes = date.getMinutes().toString().padStart(2, "0");
    const day = date.getDate().toString().padStart(2, "0");
    const month = (date.getMonth() + 1).toString().padStart(2, "0");
    const year = date.getFullYear();
    return `${hours}:${minutes} ${day}/${month}/${year}`;
  }

  // Tạo và hiển thị các dòng bản ghi trong bảng
  function renderTableRows(rows) {
    tableBody.innerHTML = ""; // Clear dữ liệu trước đó
    if (rows.length === 0) {
      tableBody.innerHTML = `
          <tr>
              <td colspan="100%" class="text-center text-gray-500 py-4">
                  Không có dữ liệu.
              </td>
          </tr>`;
      return;
    }

    rows.forEach((row) => {
      const tr = document.createElement("tr");
      tr.classList.add("border-b", "hover:bg-gray-50");
      tr.innerHTML = `
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${row.deviceId}
        </td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${row.deviceName}
        </td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${row.categoryName}
        </td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">${
          row.description
        }</td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${row.status}
          </td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${formatDateTime(row.createdAt)}
        </td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${formatDateTime(row.updatedAt)}
        </td>
      `;

      tableBody.appendChild(tr);
    });
  }

  // Tạo phân trang
  function renderPagination(totalPages, currentPage) {
    paginationContainer.innerHTML = "";
    for (let i = 1; i <= totalPages; i++) {
      const li = document.createElement("li");
      li.textContent = i;
      li.dataset.page = i; // Thêm thông tin số trang vào thuộc tính data
      li.classList.add(
        "px-4",
        "py-2",
        "cursor-pointer",
        "border",
        "border-gray-300",
        "rounded-md",
        "text-sm"
      );

      if (i === currentPage + 1) {
        li.classList.add("bg-blue-500", "text-white");
      } else {
        li.classList.add("bg-white", "text-gray-700", "hover:bg-gray-50");
      }
      paginationContainer.appendChild(li);
    }
  }

  // Gọi lần đầu khi tải trang
  fetchDevices({});

  // Ánh xạ giá trị chọn dòng của bảng cho form input (hàm khai báo trong file table-form-binding.js)
  bindTableDataToFormOnSelect(tableSelector, formInputSelector);

  // Gắn sự kiện cho paginationContainer (Event delegation)
  paginationContainer.addEventListener("click", (event) => {
    const target = event.target;
    if (target.tagName === "LI") {
      const page = Number(target.dataset.page);
      fetchDevices({
        page: page,
        size: defaultSize,
        sort: defaultSort,
        searchText: searchInput.value.trim(),
      });
    }
  });

  // Gắn sự kiện cho search input
  searchInput.addEventListener("input", () => {
    debounceFetchDevices({ searchText: searchInput.value.trim() });
  });
});
