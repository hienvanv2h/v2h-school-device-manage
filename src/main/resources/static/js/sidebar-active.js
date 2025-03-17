document.addEventListener("DOMContentLoaded", () => {
    const navLinks = document.querySelectorAll(".sidebar a");
    const currentPath = window.location.pathname;

    navLinks.forEach(link => {
        const listItem = link.closest("li");
        // So sánh đường dẫn tuyệt đối hoặc kiểm tra chứa chuỗi
        if (link.pathname === currentPath) {
            listItem.classList.add("active-link"); // Thêm class active
        } else {
            listItem.classList.remove("active-link");
        }
    });
});