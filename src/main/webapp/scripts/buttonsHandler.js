let profile = document.getElementById("profile");
let path = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'))
let host = window.location.host;
let protocol = window.location.protocol;

profile.onclick = function () {
    console.log(localStorage.getItem("basketItems"));
    let response = fetch(
        protocol + "//" + host + path + "/order", {
            method: 'POST',
            redirect: 'follow',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Producer' : 'JSONPusher'
            },
            body: localStorage.getItem("basketItems")
        }
    );
    if (response != null) {
        console.log("hello");
        response.then(response => {
            if (response.redirected) {
                console.log(response.url)
                document.location.href = response.url;
            }
            else {
                console.log("GO REDIRECT");
                document.location.href = host + path + "/profile";
            }
            console.log(response);
            console.log(response.url);
            console.log(response.headers);
            console.log(response.body);
        })

    };
}

let GETRequestSetItems = function() {
    profile.click();
}