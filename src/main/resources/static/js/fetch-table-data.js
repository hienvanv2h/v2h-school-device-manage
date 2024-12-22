document.addEventListener("DOMContentLoaded", function () {
  const tableSelector = "#teacherAssignmentTable";
  const paginationSelector = "#teacherAssignmentPagination";
  const formInputSelector = "#teacherAssignmentId";

  const tableBody = document.querySelector(`${tableSelector} tbody`);
  const paginationContainer = document.querySelector(
    `${paginationSelector} ul`
  );

  if (!tableBody || !paginationContainer) return;

  const apiPath = "/dashboard/teacher-assignments/api/data";
  const defaultPage =
    new URLSearchParams(window.location.search).get("page") || 1;
  const defaultSize =
    new URLSearchParams(window.location.search).get("size") || 10;
  const defaultSort =
    new URLSearchParams(window.location.search).get("sort") || "updatedAt,desc";

  // Gọi API lấy dữ liệu
  async function fetchTeacherAssignments({
    page = defaultPage,
    size = defaultSize,
    sort = defaultSort,
  }) {
    try {
      const response = await fetch(
        `${apiPath}?page=${page}&size=${size}&sort=${sort}`
      );
      if (!response.ok) {
        throw new Error("Failed to fetch data");
      }

      const data = await response.json();
      renderTableRows(data.content);
      renderPagination(data.page.totalPages, data.page.number);
    } catch (error) {
      console.error("Error when fetching teacher assignments:", error);
      tableBody.innerHTML = `
            <tr>
                <td colspan="100%" class="text-start text-red-500 py-4">
                    Không thể tải dữ liệu, vui lòng thử lại sau.
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
          ${row.assignmentId}
        </td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${row.teacherName}
        </td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${row.className}
        </td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${row.subjectName}
        </td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${row.semester}</td>
        <td class="px-6 py-4 text-sm text-gray-700 border-b">
          ${row.description}</td>
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
      li.addEventListener("click", () => {
        fetchTeacherAssignments(i, size, sort);
      });
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
  fetchTeacherAssignments({});
});
