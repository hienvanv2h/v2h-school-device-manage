document.addEventListener("DOMContentLoaded", () => {
  const tableSelector = "#teacherAssignmentTable";
  const paginationSelector = "#teacherAssignmentPagination";
  const formInputSelector = "#teacherAssignmentId";

  createTableManager({
    tableSelector: tableSelector,
    paginationSelector: paginationSelector,
    apiPath: "/dashboard/teacher-assignments/api/data",
    columns: [
      "assignmentId",
      "teacherName",
      "className",
      "subjectName",
      "semester",
      "description",
      "createdAt",
      "updatedAt",
    ],
  });

  bindTableDataToFormOnSelect(tableSelector, formInputSelector);
});
