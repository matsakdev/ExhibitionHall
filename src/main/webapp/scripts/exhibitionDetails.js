// let imageHolder = document.getElementById("imageHolder");
// let image = document.getElementById("uploadImageInput");
//
// function readURL(input) {
//     console.log(input);
//     if (input.files && input.files[0]) {
//         let reader = new FileReader();
//         console.log(reader);
//         reader.onload = function (e) {
//             imageHolder.style.backgroundImage = 'url(' + (e.target.result) + ")";
//             console.log(imageHolder.style.backgroundImage);
//         }
//         reader.readAsDataURL(input.files[0]);
//     }
// }
//
// image.onchange = function() {
//     console.log(image);
//     readURL(this);
// }
//import tab from "../bootstrap/js/src/tab";

//import button from "../bootstrap/js/src/button";

let body = document.getElementById("body");
let buyButton = document.getElementById("buyButton");
let ticketsCount = document.getElementById("ticketsCount");
let exhibitionId = document.getElementById("buyButton").value;
let exhibitionTitle = document.getElementById("expositionName").textContent;
let exhibitionPrice = document.getElementById("priceInput").textContent;
let halfVisible = document.getElementById("halfVisible");
let overlay = document.getElementById("overlay");
let closeModalButton = document.getElementById("closeModalButton");
let tableBody = document.getElementById("tableBody");
let deleteItemsButtons;
let clearBasketMessage = document.getElementById("clearBasketMessage");
let modalTable = document.getElementById("modalTable");

function updateTable() {
    let basketItems = JSON.parse(localStorage.getItem("basketItems"));
    if (basketItems != null && basketItems.length !== 0) {
        clearBasketMessage.style.display = "none";
        modalTable.style.display = "block";
        let i = 0;
        for (i; i < basketItems.length; i++) {
    let row = document.createElement('tr');
    row.setAttribute("classname", "table-row");

    row.innerHTML =
        "                        <td>\n" +
        "                            <div className='titleField'>\n" +
        "                               <a href='" + basketItems[i].ex_id+"'>" +
                                    basketItems[i].title +
                                        "</a>" +
        "                            </div>\n" +
        "                        </td>\n" +
        "                        <td>\n" +
        "<input class=\"form-control ticketsCount\" id=\"modalCounter\" type=\"number\" min=\"1\" step=\"1\" value='" + basketItems[i].count +"'>" +
        "                        </td>\n" +
        "                        <td>\n" +
        "                            <div className=\"position-relative\">\n" +
                                        basketItems[i].count * basketItems[i].price +
        "                                <div className=\"basket__delete-icon\">\n" +
        "                                    <button name='deleteItemButton' value='" + basketItems[i].ex_id + "' type=\"button\" className='deleteItemButton btn btn-outline-dark'>\n" +
        "                                    <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\"\n" +
        "                                         fill=\"currentColor\" className=\"bi bi-trash3-fill\" viewBox=\"0 0 16 16\">\n" +
        "                                        <path\n" +
        "                                                d=\"M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5Zm-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5ZM4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06Zm6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528ZM8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5Z\"/>\n" +
        "                                    </svg>\n" +
        "                                    </button>\n" +
        "                                </div>\n" +
        "                            </div>\n" +
        "                        </td>\n";
    tableBody.append(row);

    console.log(row);
    console.log(deleteItemsButtons = document.getElementsByClassName("deleteItemButton"));
        }
    }
    else {
        clearBasketMessage.style.display = "block";
        modalTable.style.display = "none";
    }
    deleteItemsButtons = document.getElementsByName("deleteItemButton");
    console.log(deleteItemsButtons);
}

function clearTable() {
    while (tableBody.firstChild) {
        tableBody.removeChild(tableBody.firstChild);
    }
}

function loadBasket() {
    clearTable();
    updateTable();
    setDeleteListeners();
}
function closeBasket() {
    // body.classList.remove("noscroll");
    // overlay.classList.add("overlay-hide");
}

function setDeleteListeners() {
    console.log("------------------");
    console.log(deleteItemsButtons);
    for (let i = 0; i < deleteItemsButtons.length; i++) {
        deleteItemsButtons[i].onclick = function() {
            let basketItems = JSON.parse(localStorage.getItem("basketItems"));
            for (let j = 0; j < basketItems.length; j++) {
                console.log("i: " + i, " j: " + j, " basketItems[j]: " + basketItems[j].ex_id + " deleteItemsButtons[i]: " + deleteItemsButtons[i].value);
                if (basketItems[j].ex_id === deleteItemsButtons[i].value) {
                    console.log("id == >" + basketItems[j].ex_id);
                    console.log("before deleting: " + basketItems);
                    basketItems.splice(j, 1);
                    console.log("after deleting: " + basketItems);
                    localStorage.setItem("basketItems", JSON.stringify(basketItems));
                    loadBasket();
                }
            }
        }
    }
}

buyButton.onclick = function () {
    let item = {
        ex_id: exhibitionId,
        title: exhibitionTitle,
        price: +exhibitionPrice.trim(),
        count: ticketsCount.value
    }
    let basketItems = JSON.parse(localStorage.getItem("basketItems"));
    if (basketItems === null) {
        basketItems = [];
        basketItems.push(item);
        localStorage.setItem("basketItems", JSON.stringify(basketItems));
    }
    else {
        let i = 0;
        for (i; i < basketItems.length; i++) {
            if (basketItems[i].ex_id === item.ex_id) {
                basketItems[i].count = +basketItems[i].count + +item.count;
                break;
            }
        }
        if (i >= basketItems.length) {
            basketItems.push(item);
        }
        localStorage.setItem("basketItems", JSON.stringify(basketItems));
    }
    loadBasket();
}

function deleteItem () {
    alert(deleteItemButton.value);
    alert("Hello");
}
