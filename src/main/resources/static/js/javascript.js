function getImage(direction) {

    fetch('/getImage', {
      method: 'POST',
      headers: { "Content-Type" : "text/plain" },
      body: JSON.stringify(direction) //zoom, dezoom, haut, bas, droite, gauche...
    })
    .then(response => response.blob())
    .then(data => document.getElementById("mandelbrotImage").src=URL.createObjectURL(data));
}

getImage("up");
getImage("up");
getImage("up");
getImage("up");
getImage("up");