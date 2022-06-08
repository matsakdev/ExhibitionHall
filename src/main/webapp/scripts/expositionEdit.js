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