function getImage() {
    fetch('/getImage')
 //   .then(response => response.json())
    .then(data => console.log(data));
    document.getElementById("mandelbrotImage").src=data;
}
getImage();