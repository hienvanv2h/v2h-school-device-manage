let deleteUrl = "";
let redirectUrl = "";

console.log("Loaded JS");
function confirmDelete(id, apiPath, options = null) {
  if (options && options.compositeKey && options.idNames) {
    // Nếu có lựa chọn là hợp khóa bởi 2 khóa chính từ 2 bảng khác
    // id và idNames là một mảng 2 phần tử (idNames chứa tên khóa, id chứa giá trị của khóa)
    const { idNames } = options;
    deleteUrl = `${apiPath}/delete?${idNames[0]}=${id[0]}&${idNames[1]}=${id[1]}`;
  } else {
    // Chỉ có khóa chính bình thường
    deleteUrl = `${apiPath}/delete/${id}`;
  }
  redirectUrl = apiPath;
  document.getElementById("confirmModal").classList.remove("hidden");
}

document.getElementById("cancelBtn").addEventListener("click", function () {
  document.getElementById("confirmModal").classList.add("hidden");
});

document
  .getElementById("confirmBtn")
  .addEventListener("click", async function () {
    try {
      // Lấy CSRF token từ input hidden
      const csrfToken = document.getElementById("_csrf").value;

      const response = await fetch(deleteUrl, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          "X-CSRF-TOKEN": csrfToken,
        },
      });

      if (response.ok) {
        alert("Xóa thành công!");
        // Chuyển hướng
        window.location.href = redirectUrl;
      } else {
        alert("Xóa thất bại!");
      }
    } catch (error) {
      console.error("Lỗi khi xóa:", error);
      alert("Đã xảy ra lỗi, vui lòng thử lại.");
    }
    document.getElementById("confirmModal").classList.add("hidden");
  });
