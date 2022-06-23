let orderRows = document.getElementsByClassName("orderRow");
let newEmailRadio = document.getElementById("newEmailRadio");
let newEmailInput = document.getElementById("newEmailInput");

function setCounterListeners() {
    for (let i = 0; i < orderRows.length; i++) {
        let counter = orderRows[i].querySelector('.orderCount');
        let rowSum = orderRows[i].querySelector('.orderSum');
        let rowPrice = orderRows[i].querySelector('.orderPrice');
        counter.onchange = function() {
            rowSum.value = counter.value * rowPrice;
        }
    }
}

window.onload = function () {
    setCounterListeners();
}

newEmailRadio.onclick = function () {
    newEmailRadio.value=newEmailInput.textContent;
}

