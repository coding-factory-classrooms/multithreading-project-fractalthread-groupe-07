function getImage(direction) {
    return fetch('/getImage'+'?direction='+direction)
    .then(response => response.blob())
    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));
}