let imageHolder = document.getElementById("imageHolder");
let image = document.getElementById("uploadImageInput");

window.onload = function() {
     if (!document.getElementById("startDate").readOnly) {
         setMinDate("startDate");
     }
     if (!document.getElementById("endDate").readOnly) setMinDate("endDate");
}

function zero_first_format(value)
{
    if (value < 10)
    {
        value='0'+value;
    }
    return value;
}

function setMinDate(field){
    let currentDate = new Date();
    let year = currentDate.getFullYear();
    let month = zero_first_format(currentDate.getMonth() + 1);
    let day = zero_first_format(currentDate.getDate());
    //yyyy-MM-dd
    document.getElementById(field).min = year + "-" + month + "-" + day;
}

function readURL(input) {
    console.log(input);
    if (input.files && input.files[0]) {
        let reader = new FileReader();
        console.log(reader);
        reader.onload = function (e) {
            imageHolder.style.backgroundImage = 'url(' + (e.target.result) + ")";
            console.log(imageHolder.style.backgroundImage);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

image.onchange = function() {
    console.log(image);
    readURL(this);
}
