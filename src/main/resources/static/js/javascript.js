function getImage(direction) {

//    fetch('/getImage')
//    document.getElementById("mandelbrotImage").src=data;
//    .then(response => response.blob())
//    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));
//    document.getElementById("mandelbrotImage").src=data;

    fetch('/getImage', {
      method: 'POST',
      headers: { "Content-Type" : "text/plain" },
      body: JSON.stringify(direction) //zoom, dezoom, haut, bas, droite, gauche...
    });
}

getImage('up');