const dropdowns = document.querySelectorAll(".dropdown");

dropdowns.forEach((dropdown) => {
  const button = dropdown.querySelector(".dropdown-button");
  const menu = dropdown.querySelector(".dropdown-menu");
  const selected = dropdown.querySelector(".dropdown-selected");
  const input = dropdown.querySelector(".dropdown-input");
  const icon = dropdown.querySelector(".dropdown-icon");

  // Toggle dropdown visibility
  button.addEventListener("click", () => {
    menu.classList.toggle("hidden");
    icon.classList.toggle("rotate-180");
  });

  // Handle item select - use Event Delegation
  menu.addEventListener("click", (event) => {
    const item = event.target.closest(".dropdown-item");
    if (item) {
      const value = item.getAttribute("data-id");
      const text = item.textContent;

      // Update selected text and hidden input
      selected.textContent = text;
      input.value = value;

      // Close dropdown
      menu.classList.add("hidden");
      icon.classList.remove("rotate-180");
    }
  });

  // Handle outside click
  document.addEventListener("click", (event) => {
    if (!dropdown.contains(event.target)) {
      menu.classList.add("hidden");
      icon.classList.remove("rotate-180");
    }
  });
});
