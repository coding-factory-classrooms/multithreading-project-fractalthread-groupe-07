function getImage() {
//    fetch('https://api.mocki.io/v1/b13e0144')
//    .then(data => console.log(data));

    fetch('/getImage')
    document.getElementById("mandelbrotImage").src=data;
    .then(response => response.blob())
    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));
//    document.getElementById("mandelbrotImage").src=data;
}

getImage();