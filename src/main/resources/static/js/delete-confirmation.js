let deleteUrl = "";
let redirectUrl = "";

console.log("Loaded JS");
function confirmDelete(id, apiPath) {
  deleteUrl = `${apiPath}/delete/${id}`;
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
      const response = await fetch(deleteUrl, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
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
