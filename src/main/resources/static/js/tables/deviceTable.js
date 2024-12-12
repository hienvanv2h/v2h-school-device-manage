document.addEventListener("DOMContentLoaded", () => {
  const tableSelector = "#deviceTable";
  const paginationSelector = "#devicePagination";
  const formInputSelector = "#deviceId";

  createTableManager({
    tableSelector: tableSelector,
    paginationSelector: paginationSelector,
    apiPath: "/dashboard/devices/api/data",
    columns: [
      "deviceId",
      "deviceName",
      ["deviceCategory", "categoryName"],
      "description",
      "status",
      "createdAt",
      "updatedAt",
    ],
  });

  bindTableDataToFormOnSelect(tableSelector, formInputSelector);
});
