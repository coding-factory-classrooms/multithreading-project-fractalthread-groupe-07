function getJulia() {
    return fetch("/getImage?direction=none&type=julia")
    .then(response => response.blob())
    .then(data => document.getElementById("FractalImage").src=URL.createObjectURL(data));
}
function getImage(direction) {
    return fetch('/getImage'+'?direction='+direction+"&type=mandel")
    .then(response => response.blob())
    .then(data => document.getElementById("FractalImage").src=URL.createObjectURL(data));
}