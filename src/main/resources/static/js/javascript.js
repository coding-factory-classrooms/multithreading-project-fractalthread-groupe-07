function getImage(direction) {
    return fetch('/getImage'+'?direction='+direction)
    .then(response => response.blob())
    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));
}

function setSides() {
    verticalSide = document.getElementById('vertical').value;
    horizontalSide = document.getElementById('horizontal').value;
    return fetch('/setSides'+'?verticalSide='+verticalSide+'&horizontalSide='+horizontalSide)
    .then(response => response.blob())
    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));
}