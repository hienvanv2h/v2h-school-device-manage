const dropdowns = document.querySelectorAll(".dropdown");

dropdowns.forEach((dropdown) => {
  const isMultiselect = dropdown.getAttribute("data-multiselect") === "true";
  const delimiter = dropdown.getAttribute("data-delimiter") || ",";

  const button = dropdown.querySelector(".dropdown-button");
  const menu = dropdown.querySelector(".dropdown-menu");
  const selected = dropdown.querySelector(".dropdown-selected");
  const input = dropdown.querySelector(".dropdown-input");
  const icon = dropdown.querySelector(".dropdown-icon");

  if (button == null || menu == null) return;

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
      const text = item.textContent.trim();

      // Update selected text
      const currentContent = selected.textContent.trim();
      let selectedArray = isMultiselect && currentContent !== "" 
        ? currentContent.split(delimiter).map(item => item.trim())
        : [];

      if (selectedArray.includes(value)) {
        selectedArray = selectedArray.filter(item => item !== value);
      } else {
        selectedArray.push(value);
      }

      // Sắp xếp lại thứ tự trước khi hiển thị
      selectedArray.sort((a, b) => a.localeCompare(b, undefined, { numeric: true }));
      selected.textContent = selectedArray.join(delimiter);

      // Update hidden input if it exists
      if (input == null) return;
      const currentInputValue = input.value.trim();
      let inputArray = isMultiselect && currentInputValue !== "" 
        ? currentInputValue.split(delimiter).map(item => item.trim())
        : [];

      if (inputArray.includes(value)) {
        inputArray = inputArray.filter(item => item !== value);
      } else {
        inputArray.push(value);
      }

      // Sắp xếp lại thứ tự trước khi gán input value
      inputArray.sort((a, b) => a.localeCompare(b, undefined, { numeric: true }));
      input.value = inputArray.join(delimiter);

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
