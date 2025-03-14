document.addEventListener("DOMContentLoaded", function () {
    const tableSelector = "#scheduleTable";
    const paginationSelector = "#schedulePagination";
    const formInputSelector = "#scheduleId";
  
    const tableBody = document.querySelector(`${tableSelector} tbody`);
    const paginationContainer = document.querySelector(
      `${paginationSelector} ul`
    );
  
    if (!tableBody || !paginationContainer) return;
  
    const apiPath = "/dashboard/schedules/api/data";
    const defaultPage =
      new URLSearchParams(window.location.search).get("page") || 1;
    const defaultSize =
      new URLSearchParams(window.location.search).get("size") || 10;
    const defaultSort =
      new URLSearchParams(window.location.search).get("sort") || "updatedAt,desc";
  
    // Gọi API lấy dữ liệu
    async function fetchSchedules({
      page = defaultPage,
      size = defaultSize,
      sort = defaultSort,
      assignmentId = "",
    }) {
      if(!assignmentId) return;
      const fetchScheduleUrl = `${apiPath}?page=${page}&size=${size}&sort=${sort}&assignmentId=${assignmentId}`;
      try {
        const response = await fetch(fetchScheduleUrl);
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
  
        const data = await response.json();
        console.log(data);
        renderTableRows(data.content);
        renderPagination(data.page.totalPages, data.page.number);
      } catch (error) {
        console.error("Error when fetching schedules:", error);
        tableBody.innerHTML = `
              <tr>
                  <td colspan="100%">
                    <div class="text-start text-red-500 py-4">
                      Không thể tải dữ liệu.
                    </div>
                  </td>
              </tr>`;
      }
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
              <div>
                  ${row.scheduleId}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div class="w-[200px]">
                  ${row.teacherName}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div>
                  ${row.className}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div>
                  ${row.subjectName}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div>
                  ${row.dayOfWeek}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div>
                  ${formatDateTime(row.scheduleDate)}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div>
                  ${formatDateTime(row.startTime)}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div>
                  ${formatDateTime(row.endTime)}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div>
                  ${row.location}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div>
                  ${formatDateTime(row.createdAt)}
              </div>
          </td>
          <td class="px-6 py-4 text-sm text-gray-700 border-b">
              <div>
                  ${formatDateTime(row.updatedAt)}
              </div>
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
  
    // Ánh xạ giá trị chọn dòng của bảng cho form input (hàm khai báo trong file table-form-binding.js)
    bindTableDataToFormOnSelect(tableSelector, formInputSelector);
  
    // Gắn sự kiện cho paginationContainer (Event delegation)
    paginationContainer.addEventListener("click", (event) => {
      const target = event.target;
      if (target.tagName === "LI") {
        const page = Number(target.dataset.page);
        fetchSchedules({
          page: page,
          size: defaultSize,
          sort: defaultSort,
        });
      }
    });

    // Xử lý load dữ liệu schedule khi chọn dòng trong bảng assignment
    const assignmentTableBody = document.querySelector("#teacherAssignmentTable tbody");
    assignmentTableBody.addEventListener("click", (event) => {
      const selectedRow = event.target.closest("tr");
      if (!selectedRow) return;

      assignmentId = selectedRow.dataset.id;
      fetchSchedules({ assignmentId: assignmentId });
    })
  });
  