function bindTableDataToFormOnSelect(tableSelector, formInputSelector) {
  const tableBody = document.querySelector(`${tableSelector} tbody`);
  const formInput = document.querySelector(`${formInputSelector}`);

  tableBody.addEventListener("click", (event) => {
    const row = event.target.closest("tr");
    if (row) {
      // Xóa lớp được chọn từ tất cả các dòng
      const rows = tableBody.querySelectorAll("tr");
      rows.forEach((r) => r.classList.remove("bg-blue-100"));
      // Thêm lớp cho dòng hiện tại
      row.classList.add("bg-blue-100");

      const assignmentId = row.cells[0].textContent.trim();
      formInput.value = assignmentId;
    }
  });
}
