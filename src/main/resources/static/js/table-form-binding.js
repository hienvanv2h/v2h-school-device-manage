const tableBody = document.querySelector("#teacherAssignmentTable tbody");
const assignmentInput = document.querySelector("#teacherAssignmentId");

tableBody.addEventListener("click", (event) => {
  const row = event.target.closest("tr");
  if (row) {
    // Xóa lớp được chọn từ tất cả các dòng
    const rows = tableBody.querySelectorAll("tr");
    rows.forEach((r) => r.classList.remove("bg-blue-100"));
    // Thêm lớp cho dòng hiện tại
    row.classList.add("bg-blue-100");

    const assignmentId = row.cells[0].textContent.trim();
    assignmentInput.value = assignmentId;
  }
});
