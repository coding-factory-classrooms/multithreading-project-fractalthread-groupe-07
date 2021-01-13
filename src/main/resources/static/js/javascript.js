var isJulia = false;
function getJulia() {
    isJulia = true;
    return fetch("/getImage?direction=none&type=julia")
    .then(response => response.blob())
    .then(data => document.getElementById("FractalImage").src=URL.createObjectURL(data));
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