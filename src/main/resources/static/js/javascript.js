function getImage(direction) {
    return fetch('/getImage'+'?direction='+direction)
    .then(response => response.blob())
    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));
}

function setVerticalSide() {
    verticalSide = document.getElementById('vertical').value;
    return fetch('/setVerticalSide'+'?verticalSide='+verticalSide)
    .then(response => response.blob())
    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));
}

function setHorizontalSide() {
    horizontalSide = document.getElementById('horizontal').value;
    return fetch('/setHorizontalSide'+'?horizontalSide='+horizontalSide)
    .then(response => response.blob())
    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));
}