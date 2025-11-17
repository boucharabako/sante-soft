'use strict';

/* globals MediaRecorder */

// Spec is at http://dvcs.w3.org/hg/dap/raw-file/tip/media-stream-capture/RecordingProposal.html

navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;


if (getBrowser() === "Chrome") {
    var constraints = {"audio": true, "video": {"mandatory": {"minWidth": 640, "maxWidth": 640, "minHeight": 480, "maxHeight": 480}, "optional": []}};//Chrome did not support the new constraints spec until 59 for video and 60 for audio
} else if (getBrowser() === "Firefox") {
    var constraints = {audio: true, video: {width: {min: 640, ideal: 640, max: 640}, height: {min: 480, ideal: 480, max: 480}}}; //Firefox
}

var recBtn = document.querySelector('button#rec');
var pauseResBtn = document.querySelector('button#pauseRes');
var stopBtn = document.querySelector('button#stop');
var uploadVideoButton = document.getElementById("btnSubmitVideo");

//var videoElement = document.querySelector('video');
var videoElement = document.getElementById("videoarea");
var dataElement = document.querySelector('#data');
var downloadLink = document.querySelector('a#downloadLink');
var videoBlob = null;
videoElement.controls = false;
var link = document.getElementById("downloadLink");
link.hidden = true;
var localStream = null;




function errorCallback(error) {
    var videoDiv = document.getElementById("videoDiv");
    videoDiv.hidden = true;

    var warningTextTitleVideo = document.getElementById('warning-text-title-video');
    warningTextTitleVideo.innerHTML = '';
    var warningTextVideo = document.getElementById('warning-text-video');
    warningTextVideo.innerHTML = '';

    pauseResBtn.disabled = true;
    stopBtn.disabled = true;

    var infoButtonVideo = document.getElementById('info-button-video');
    //var videoArea = document.getElementById('videoarea');
    //videoArea.style.display = "none";
    infoButtonVideo.style.display = "block";

    var warningTitleVideo = 'Veuillez autoriser l\'accès à votre micro et à votre caméra';
    // Chrome Setting
    var videoChrome_warningText_step_1 = ' 1- Aller dans la barre d\'adresse dans le navigateur Chrome ';
    var videoChrome_warningText_step_2 = ' 2- Cliquer sur l\'icône de la caméra barrée ';
    var videoChrome_warningText_step_3 = ' 3- Cliquer sur Autorisez toujours ce site à accéder à votre caméra et à votre microphone. ';
    var videoChrome_warningText_step_4 = ' 4- Cliquer sur Terminé. ';
    var videoChrome_warningText_step_5 = ' 5- Recharger la page. ';

    //Firefox Setting
    var videoFirefox_warningText_step_1 = ' 1- Aller à gauche dans la barre d\'adresse de Firefox ';
    var videoFirefox_warningText_step_2 = ' 2- Cliquer sur le micro barré ';
    var videoFirefox_warningText_step_3 = ' 3- Dans la partie permissions, supprimer les lignes \n\
    <strong>Utiliser le microphone bloqué temporairement</strong> et <strong>Utiliser la caméra bloqué temporairement</strong> avec l\'icone <strong>X</strong>';
    var videoFirefox_warningText_step_4 = ' 4- Recharger la page. ';

    // Other Browser
    var videoOther_step_1 = ' 1- Aller dans les paramètres de votre navigateur et autoriser le microphone ';


    warningTextTitleVideo.innerHTML = warningTitleVideo + "<br>";
    warningTextTitleVideo.style.textAlign = 'left';

    if (navigator.userAgent.indexOf('Chrome') > -1) {
        warningTextVideo.innerHTML = "<br>" + videoChrome_warningText_step_1 + "<br>" + videoChrome_warningText_step_2 + "<img src=\"" + appUrl + "img/settinggoogle.png\" width=\"20px\" height=\"20px\">" +
                "<br>" + videoChrome_warningText_step_3 + "<br>" + videoChrome_warningText_step_4 + "<br>" + videoChrome_warningText_step_5;
        warningTextVideo.style.textAlign = 'left';
    } else
    if (navigator.userAgent.indexOf('Firefox') > -1) {
        warningTextVideo.innerHTML = "<br>" + videoFirefox_warningText_step_1 + "<br>" + videoFirefox_warningText_step_2 + "<img src=\"" + appUrl + "img/firefoxavsetting.png\" width=\"45px\" height=\"20px\">" + "<br>" + videoFirefox_warningText_step_3 + "<br>" + videoFirefox_warningText_step_4;
        warningTextVideo.style.textAlign = 'left';
    } else {
        warningTextVideo.innerHTML = "<br>" + videoOther_step_1 + "<br>";
        warningTextVideo.style.textAlign = 'left';
    }

    console.log('navigator.getUserMedia error: ', error);
}

/*
 var mediaSource = new MediaSource();
 mediaSource.addEventListener('sourceopen', handleSourceOpen, false);
 var sourceBuffer;
 */

var mediaRecorder;
var chunks = [];
var count = 0;

function startRecording(stream) {
    //log('Start recording...');
    // log('Enregistrement en cours...');
    if (typeof MediaRecorder.isTypeSupported === 'function') {
        /*
         MediaRecorder.isTypeSupported is a function announced in https://developers.google.com/web/updates/2016/01/mediarecorder and later introduced in the MediaRecorder API spec http://www.w3.org/TR/mediastream-recording/
         */
        if (MediaRecorder.isTypeSupported('video/webm;codecs=vp9')) {
            var options = {mimeType: 'video/webm;codecs=vp9'};
        } else if (MediaRecorder.isTypeSupported('video/webm;codecs=h264')) {
            var options = {mimeType: 'video/webm;codecs=h264'};
        } else if (MediaRecorder.isTypeSupported('video/webm;codecs=vp8')) {
            var options = {mimeType: 'video/webm;codecs=vp8'};
        }
        //log('Using '+options.mimeType);
        mediaRecorder = new MediaRecorder(stream, options);
    } else {
        log('isTypeSupported is not supported, using default codecs for browser');
        mediaRecorder = new MediaRecorder(stream);
    }

    pauseResBtn.textContent = "Pause";

    mediaRecorder.start(10);

    //var url = window.URL || window.webkitURL;
    // videoElement.src = url ? url.createObjectURL(stream) : stream;
    videoElement.srcObject = stream;
    localStream = stream;
    //videoElement.srcObject = url ? stream : stream;
    videoElement.play();

    mediaRecorder.ondataavailable = function (e) {
        //log('Data available...');
        //console.log(e.data);
        //console.log(e.data.type);
        //console.log(e);
        chunks.push(e.data);
    };

    mediaRecorder.onerror = function (e) {
        log('Error: ' + e);
        console.log('Error: ', e);
    };


    mediaRecorder.onstart = function () {
        //log('Started & state = ' + mediaRecorder.state);
        // log('Enregistrement terminé...');
    };

    mediaRecorder.onstop = function () {
        videoElement.srcObject = null;
        //log('Stopped  & state = ' + mediaRecorder.state);
        //log('Enregistrement terminé');

        var blob = new Blob(chunks, {type: "video/webm"});
        videoBlob = blob;
        chunks = [];

        var videoURL = window.URL.createObjectURL(blob);

        downloadLink.href = videoURL;
        videoElement.src = videoURL;
        //downloadLink.innerHTML = 'Download video file';

        var rand = Math.floor((Math.random() * 10000000));
        var name = "video_" + rand + ".webm";

        downloadLink.setAttribute("download", name);
        downloadLink.setAttribute("name", name);
    };

    mediaRecorder.onpause = function () {
        log('Paused & state = ' + mediaRecorder.state);
    };

    mediaRecorder.onresume = function () {
        log('Resumed  & state = ' + mediaRecorder.state);
    };

    mediaRecorder.onwarning = function (e) {
        log('Warning: ' + e);
    };
}

function onBtnRecordClicked() {
    $("#rec");
    if (typeof MediaRecorder === 'undefined' || !navigator.getUserMedia) {
        alert('MediaRecorder not supported on your browser, use Firefox 30 or Chrome 49 instead.');
    } else {
        navigator.getUserMedia(constraints, startRecording, errorCallback);
        recBtn.disabled = true;
        pauseResBtn.disabled = false;
        stopBtn.disabled = false;
    }
}

function turnOffTheCamera() {
    //mediaRecorder.stop();
    videoElement.controls = false;
    recBtn.disabled = false;
    pauseResBtn.disabled = true;
    stopBtn.disabled = true;
    uploadVideoButton.disabled = true;
    videoElement.pause();
    videoElement.src = "";
    if (localStream !== null) {
        localStream.getTracks().forEach(function (track) {
            track.stop();
        });
    }
}

function resetButton() {
    videoElement.controls = false;
    recBtn.disabled = false;
    pauseResBtn.disabled = true;
    stopBtn.disabled = true;
    uploadVideoButton.disabled = true;
    videoElement.pause();
    videoElement.src = "";
    if (localStream !== null) {
        localStream.getTracks().forEach(function (track) {
            track.stop();
        });
    }
}

function onBtnStopClicked() {
    mediaRecorder.stop();
    videoElement.controls = true;

    recBtn.disabled = false;
    pauseResBtn.disabled = true;
    stopBtn.disabled = true;
    uploadVideoButton.disabled = false;
}

function onPauseResumeClicked() {
    if (pauseResBtn.textContent === "Pause") {
        console.log("pause");
        pauseResBtn.textContent = "Resume";
        mediaRecorder.pause();
        stopBtn.disabled = true;
    } else {
        console.log("resume");
        pauseResBtn.textContent = "Pause";
        mediaRecorder.resume();
        stopBtn.disabled = false;
    }
    recBtn.disabled = true;
    pauseResBtn.disabled = false;
    //uploadVideoButton.disabled = false;
    uploadVideoButton.disabled = true;
}


function log(message) {
    //dataElement.innerHTML = dataElement.innerHTML + '<br>' + message;
}



//browser ID
function getBrowser() {
    var nVer = navigator.appVersion;
    var nAgt = navigator.userAgent;
    var browserName = navigator.appName;
    var fullVersion = '' + parseFloat(navigator.appVersion);
    var majorVersion = parseInt(navigator.appVersion, 10);
    var nameOffset, verOffset, ix;

    // In Opera, the true version is after "Opera" or after "Version"
    if ((verOffset = nAgt.indexOf("Opera")) !== -1) {
        browserName = "Opera";
        fullVersion = nAgt.substring(verOffset + 6);
        if ((verOffset = nAgt.indexOf("Version")) !== -1)
            fullVersion = nAgt.substring(verOffset + 8);
    }
    // In MSIE, the true version is after "MSIE" in userAgent
    else if ((verOffset = nAgt.indexOf("MSIE")) !== -1) {
        browserName = "Microsoft Internet Explorer";
        fullVersion = nAgt.substring(verOffset + 5);
    }
    // In Chrome, the true version is after "Chrome"
    else if ((verOffset = nAgt.indexOf("Chrome")) !== -1) {
        browserName = "Chrome";
        fullVersion = nAgt.substring(verOffset + 7);
    }
    // In Safari, the true version is after "Safari" or after "Version"
    else if ((verOffset = nAgt.indexOf("Safari")) !== -1) {
        browserName = "Safari";
        fullVersion = nAgt.substring(verOffset + 7);
        if ((verOffset = nAgt.indexOf("Version")) !== -1)
            fullVersion = nAgt.substring(verOffset + 8);
    }
    // In Firefox, the true version is after "Firefox"
    else if ((verOffset = nAgt.indexOf("Firefox")) !== -1) {
        browserName = "Firefox";
        fullVersion = nAgt.substring(verOffset + 8);
    }
    // In most other browsers, "name/version" is at the end of userAgent
    else if ((nameOffset = nAgt.lastIndexOf(' ') + 1) <
            (verOffset = nAgt.lastIndexOf('/')))
    {
        browserName = nAgt.substring(nameOffset, verOffset);
        fullVersion = nAgt.substring(verOffset + 1);
        if (browserName.toLowerCase() === browserName.toUpperCase()) {
            browserName = navigator.appName;
        }
    }
    // trim the fullVersion string at semicolon/space if present
    if ((ix = fullVersion.indexOf(";")) !== -1)
        fullVersion = fullVersion.substring(0, ix);
    if ((ix = fullVersion.indexOf(" ")) !== -1)
        fullVersion = fullVersion.substring(0, ix);

    majorVersion = parseInt('' + fullVersion, 10);
    if (isNaN(majorVersion)) {
        fullVersion = '' + parseFloat(navigator.appVersion);
        majorVersion = parseInt(navigator.appVersion, 10);
    }


    return browserName;
}


$(document).ready(function () {
    $("#btnSubmitVideo").click(function (event) {
//        showSpinner();
        $('#btnSubmitVideo').attr('disabled', true);
        event.preventDefault();
        //showSpinner();
        doAjaxUploadVideo();
        resetButton();
//        hideSpinner();
        $('#file_input').attr('disabled', true);

    });
});

function doAjaxUploadVideo() {
     showSpinner();
    var fileType = 'video';
    var formDataVideo = new FormData();
    formDataVideo.append(fileType + '-filename', "filename");
    formDataVideo.append(fileType + '-blob', videoBlob);
    formDataVideo.append('uploadfile', videoBlob);
    var location = $("#fm_send").attr("data-action");
    $.ajax({
        type: "POST",
        async: true,
        enctype: 'multipart/form-data',
        url: location,
        data: formDataVideo,
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
            //console.log(data);
            $("#zone_raf").html(data);
            $("#camera").modal('hide');
             hideSpinner();
        },
        error: function (e) {
            l.ladda('stop');
            $("#videoresult").text(e.responseText);
             hideSpinner();
        }
    });
}

function ReDoAjaxUploadVideo() {
    var fileType = 'video';
    var formDataVideo = new FormData();
    formDataVideo.append(fileType + '-filename', "filename");
    formDataVideo.append(fileType + '-blob', videoBlob);
    formDataVideo.append('uploadfile', videoBlob);

    var url = (window.URL || window.webkitURL).createObjectURL(videoBlob);
    var link = document.getElementById("downloadLink");
    alert("upload");
//    link.href = url;
//    link.click();
//    $.ajax({
//        type: "POST",
//        async: true,
//        enctype: 'multipart/form-data',
//        url: "/api/uploadfile",
//        data: formDataVideo,
//        xhrFields: {
//            onprogress: function (e) {
//                if (e.lengthComputable) {
//                    $("#videoresult").text("Loaded " + Number((e.loaded / e.total * 100)) + "%");
//                    console.log("Loaded " + Number((e.loaded / e.total * 100)) + "%");
//
//                } else {
//                    console.log("Length not computable.");
//                }
//            }
//        },
//        processData: false, //prevent jQuery from automatically transforming the data into a query string
//        contentType: false,
//        cache: false,
//        success: function (data) {
//            $("#videoresult").text(data);
//            var videoSubmitButton = document.getElementById("btnSubmitVideo");
//            videoSubmitButton.disabled = true;
//        },
//        error: function (e) {
//            $("#videoresult").text(e.responseText);
//        }
//    });




}
