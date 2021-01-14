var isJulia = false;
function getFractal() {
    if (document.getElementById('chkSwitch').checked)
    {
        isJulia = false;
        document.getElementById('nameFractalTxt').textContent = "Mandelbrot";
        return fetch("/getImage?direction=none&type=mandel")
            .then(response => response.blob())
            .then(data => document.getElementById("FractalImage").src=URL.createObjectURL(data));
    } else {
        isJulia = true;
        document.getElementById('nameFractalTxt').textContent = "Julia";
        return fetch("/getImage?direction=none&type=julia")
            .then(response => response.blob())
            .then(data => document.getElementById("FractalImage").src=URL.createObjectURL(data));
    }

}
function getImage(direction) {
    if(isJulia) {
        return fetch('/getImage'+'?direction='+direction+"&type=julia")
        .then(response => response.blob())
            .then(data => document.getElementById("FractalImage").src=URL.createObjectURL(data));
    }
    else {
        return fetch('/getImage'+'?direction='+direction+"&type=mandel")
        .then(response => response.blob())
            .then(data => document.getElementById("FractalImage").src=URL.createObjectURL(data));
    }

}

function setSides() {
    verticalSide = document.getElementById('vertical').value;
    horizontalSide = document.getElementById('horizontal').value;
    if(isJulia) {
     return fetch('/setSides'+'?verticalSide='+verticalSide+'&horizontalSide='+horizontalSide+'&type=julia')
        .then(response => response.blob())
        .then(data => document.getElementById("FractalImage").src=URL.createObjectURL(data));
    }
    else {
    return fetch('/setSides'+'?verticalSide='+verticalSide+'&horizontalSide='+horizontalSide+'&type=mandel')
        .then(response => response.blob())
        .then(data => document.getElementById("FractalImage").src=URL.createObjectURL(data));
        }
}