let startDateCheckbox = document.getElementById("startDateCheckbox");
let endDateCheckbox = document.getElementById("endDateCheckbox");
let themesCheckbox = document.getElementById("themesCheckbox");

let startDate = document.getElementById("startDate");
let endDate = document.getElementById("endDate");
let themesList = document.getElementById("themesList");

startDateCheckbox.onclick = function () {
    startDate.disabled = !startDate.disabled;
    if (startDate.disabled) startDate.value = "";
}
endDateCheckbox.onclick = function () {
    endDate.disabled = !endDate.disabled;
    if (endDate.disabled) endDate.value = "";
}
themesCheckbox.onclick = function () {
    themesList.disabled = !themesList.disabled;
    console.log(themesList.options);
    if (themesList.disabled) {
        themesList.selectedIndex = -1;
    }
}