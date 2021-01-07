function getImage(direction) {
    fetch('/getImage '+'?direction='+direction)
    .then(response => response.blob())
    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));

//    fetch('/getImage', {
//      method: 'POST',
//      headers: { "Content-Type" : "text/plain" },
//      body: JSON.stringify(direction) //zoom, dezoom, haut, bas, droite, gauche...
//    });
}

getImage('up');