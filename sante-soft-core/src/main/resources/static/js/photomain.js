/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
// <![CDATA[ 

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mediaDevices.getUserMedia || navigator.msGetUserMedia;

if (getBrowser() === "Chrome") {
    var photoconstraints = {"video": {"mandatory": {"minWidth": 640, "maxWidth": 640, "minHeight": 480, "maxHeight": 480}, "optional": []}};//Chrome did not support the new constraints spec until 59 for video and 60 for audio
} else if (getBrowser() === "Firefox") {
    var photoconstraints = {video: {width: {min: 640, ideal: 640, max: 640}, height: {min: 480, ideal: 480, max: 480}}}; //Firefox
}

var displayWidth = 257;
var displayHeight = 257;

var btnTakePicture = document.getElementById('takePic');
var btnSubmitPhoto = document.getElementById('btnSubmitPhoto');
var photoarea = document.getElementById('photoarea');
var canvas = document.getElementById('photocanvas');
canvas.hidden = true;
document.getElementById('photocanvas').setAttribute("width", displayWidth);
document.getElementById('photocanvas').setAttribute("height", displayHeight);
var context = canvas.getContext('2d');

var localStream = null;



btnSubmitPhoto.disabled = true;

function askPermissionForPicture() {
    if (typeof MediaRecorder === 'undefined' || !navigator.getUserMedia) {
        alert('MediaRecorder not supported on your browser, use the latest Firefox or Chrome instead.');
    } else {
        navigator.getUserMedia(photoconstraints, startStream, photoErrorCallback);
    }
}

// ]]>

function startStream(stream) {
    photoarea.srcObject = stream;
    localStream = stream;
    photoarea.play();
}
;

function createVideoCanvas() {
    canvas.hidden = false;
    context.drawImage(photoarea, 0, 0, displayWidth, displayHeight);
    $('#btnSubmitPhoto').prop("disabled", "false");
    btnSubmitPhoto.disabled = false;
}

function photoErrorCallback(error) {
    var photoDiv = document.getElementById("photoDiv");
    photoDiv.hidden = true;

    var warningTextTitleVideo = document.getElementById('warning-text-title-photo');
    warningTextTitleVideo.innerHTML = '';
    var warningTextVideo = document.getElementById('warning-text-photo');
    warningTextVideo.innerHTML = '';

    btnTakePicture.disabled = true;
    btnSubmitPhoto.disabled = true;

    var infoButtonVideo = document.getElementById('info-button-photo');
    //var photoArea = document.getElementById('photoarea');
    //photoArea.style.display = "none";
    infoButtonVideo.style.display = "block";

    var warningTitleVideo = 'Veuillez autoriser l\'accès à votre caméra';
    // Chrome Setting
    var photoChrome_warningText_step_1 = ' 1- Aller dans la barre d\'adresse dans le navigateur Chrome ';
    var photoChrome_warningText_step_2 = ' 2- Cliquer sur l\'icône de la caméra barrée ';
    var photoChrome_warningText_step_3 = ' 3- Cliquer sur Autorisez toujours ce site à accéder à votre caméra. ';
    var photoChrome_warningText_step_4 = ' 4- Cliquer sur Terminé. ';
    var photoChrome_warningText_step_5 = ' 5- Recharger la page. ';

    //Firefox Setting
    var photoFirefox_warningText_step_1 = ' 1- Aller à gauche dans la barre d\'adresse de Firefox ';
    var photoFirefox_warningText_step_2 = ' 2- Cliquer sur la camera barré ';
    var photoFirefox_warningText_step_3 = ' 3- Dans la partie permissions, supprimer la ligne \n\
    <strong>Utiliser la caméra bloqué temporairement</strong> avec l\'icone <strong>X</strong>';
    var photoFirefox_warningText_step_4 = ' 4- Recharger la page. ';

    // Other Browser
    var photoOther_step_1 = ' 1- Aller dans les paramètres de votre navigateur et autoriser votre caméra ';


    warningTextTitleVideo.innerHTML = warningTitleVideo + "<br>";
    warningTextTitleVideo.style.textAlign = 'left';

    if (navigator.userAgent.indexOf('Chrome') > -1) {
        warningTextVideo.innerHTML = "<br>" + photoChrome_warningText_step_1 + "<br>" + photoChrome_warningText_step_2 + "<img src=\"" + appUrl + "img/settinggoogle.png\" width=\"20px\" height=\"20px\">" +
                "<br>" + photoChrome_warningText_step_3 + "<br>" + photoChrome_warningText_step_4 + "<br>" + photoChrome_warningText_step_5;
        warningTextVideo.style.textAlign = 'left';
    } else
    if (navigator.userAgent.indexOf('Firefox') > -1) {
        warningTextVideo.innerHTML = "<br>" + photoFirefox_warningText_step_1 + "<br>" + photoFirefox_warningText_step_2 + "<img src=\"" + appUrl + "img/firefoxcamsetting.png\" width=\"45px\" height=\"20px\">" + "<br>" + photoFirefox_warningText_step_3 + "<br>" + photoFirefox_warningText_step_4;
        warningTextVideo.style.textAlign = 'left';
    } else {
        warningTextVideo.innerHTML = "<br>" + photoOther_step_1 + "<br>";
        warningTextVideo.style.textAlign = 'left';
    }

    console.log('navigator.getUserMedia error: ', error);
}

$(document).ready(function ()
{
    $('#btnSubmitPhoto').click(function (event) {
//        showSpinner();

        event.preventDefault();

        doAjaxUploadPhoto();

//        hideSpinner();
        turnOffTheCam();

    });
});


function turnOffTheCam() {

    btnTakePicture.disabled = false;
    btnSubmitPhoto.disabled = true;
    canvas.hidden = true;

    photoarea.pause();
    photoarea.src = "";
    if (localStream !== null) {
        localStream.getTracks().forEach(function (track) {
            track.stop();
        });
    }
}

function doAjaxUploadPhoto() {
    showSpinner();
    var fileType = 'image';
    var formDataPhoto = new FormData();
    formDataPhoto.append(fileType + '-filename', "filename");
    canvas.toBlob(function (blob) {
        formDataPhoto.append(fileType + '-blob', blob);
        formDataPhoto.append('uploadfile', blob);
        var location = $("#fm_send").attr("data-upload-photo");
        $.ajax({
            type: "POST",
            async: true,
            enctype: 'multipart/form-data',
            url: location,
            data: formDataPhoto,
            xhrFields: {
                onprogress: function (e) {
                    if (e.lengthComputable) {
                        console.log("Loaded " + Number((e.loaded / e.total * 100)) + "%");
                    } else {
                        console.log("Length not computable.");
                    }
                }
            },
            processData: false, //prevent jQuery from automatically transforming the data into a query string
            contentType: false,
            cache: false,
            success: function (data) {
                $("#zone_raf").html(data);
                $("#photo").modal('hide');
                canvas.hidden = true;
                 hideSpinner();
            },
            error: function (e) {
                $("#photoresult").text(e.responseText);
                 hideSpinner();
            }
        });
    });
}