/* Copyright 2013 Chris Wilson
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

window.AudioContext = window.AudioContext || window.webkitAudioContext;

var audioContext = new AudioContext();
var audioInput = null,
        realAudioInput = null,
        inputPoint = null,
        audioRecorder = null;
var rafID = null;
var analyserContext = null;
var canvasWidth, canvasHeight;
var recIndex = 0;
var recordingblob = null;
var filen = null;
var audioStream = null;

var warningTextTitle = document.getElementById('warning-text-title');
warningTextTitle.innerHTML = '';
var warningText = document.getElementById('warning-text');
warningText.innerHTML = '';


/* TODO:
 
 - offer mono option
 - "Monitor input" switch
 */

function saveAudio() {

    audioRecorder.exportWAV(doneEncoding);
    // could get mono instead by saying
    // audioRecorder.exportMonoWAV( doneEncoding );
}

function gotBuffers(buffers) {
    var canvas = document.getElementById("wavedisplay");

    drawBuffer(canvas.width, canvas.height, canvas.getContext('2d'), buffers[0]);

    // the ONLY time gotBuffers is called is right after a new recording is completed -
    // so here's where we should set up the download.
    audioRecorder.exportWAV(doneEncoding);
}

function doneEncoding(blob) {
    recordingblob = blob;
    d = new Date();
    Recorder.setupDownload(blob, "audio_" + d.toUTCString() + ".wav");
    filen = "audio_" + d.toUTCString() + ".wav";
    recIndex++;
}

function toggleRecording(e) {

    if (e.classList.contains("recording")) {
        // stop recording
        audioRecorder.stop();
        e.classList.remove("recording");
        audioRecorder.getBuffers(gotBuffers);
    } else {

        // start recording
        if (!audioRecorder)
            return;
        e.classList.add("recording");
        audioRecorder.clear();
        audioRecorder.record();
    }
}
let counterTime = 0;
let demarage = 0;
function startRecordingAudio() {
    var startButton = document.getElementById("startaudio");
    var stopButton = document.getElementById("stopaudio");
    var submitButton = document.getElementById("btnSubmit");
    submitButton.disabled = true;
    counterTime = 0;
    demarage = setInterval(function () {
        counter(counterTime);
    }, 1000);

    audioRecorder.clear();
    audioRecorder.record();
    startButton.disabled = true;
    stopButton.disabled = false;

}

function counter(temps) {

    temps = parseInt(counterTime) + 1;
    let h = parseInt(temps / 3600);
    let m = parseInt((temps % 3600) / 60);
    let s = parseInt((temps % 3600) % 60);
    $("#clock-icone").prop("hidden", false);
    $("#counter").html(h + " : " + m + " : " + s);
    counterTime = counterTime + 1;

}
function stopRecordingAudio() {
    var startButton = document.getElementById("startaudio");
    var stopButton = document.getElementById("stopaudio");
    var submitButton = document.getElementById("btnSubmit");
    submitButton.disabled = false;
    clearInterval(demarage);
    audioRecorder.stop();
    audioRecorder.getBuffers(gotBuffers);
    startButton.disabled = false;
    stopButton.disabled = true;
    
  // turnOffMicroStream();

}

function convertToMono(input) {
    var splitter = audioContext.createChannelSplitter(2);
    var merger = audioContext.createChannelMerger(2);

    input.connect(splitter);
    splitter.connect(merger, 0, 0);
    splitter.connect(merger, 0, 1);
    return merger;
}

function cancelAnalyserUpdates() {
    window.cancelAnimationFrame(rafID);
    rafID = null;
}

function updateAnalysers(time) {
    if (!analyserContext) {
        var canvas = document.getElementById("analyser");
        canvasWidth = canvas.width;
        canvasHeight = canvas.height;
        analyserContext = canvas.getContext('2d');
    }

    // analyzer draw code here
    {
        var SPACING = 3;
        var BAR_WIDTH = 1;
        var numBars = Math.round(canvasWidth / SPACING);
        var freqByteData = new Uint8Array(analyserNode.frequencyBinCount);

        analyserNode.getByteFrequencyData(freqByteData);

        analyserContext.clearRect(0, 0, canvasWidth, canvasHeight);
        analyserContext.fillStyle = '#F6D565';
        analyserContext.lineCap = 'round';
        var multiplier = analyserNode.frequencyBinCount / numBars;

        // Draw rectangle for each frequency bin.
        for (var i = 0; i < numBars; ++i) {
            var magnitude = 0;
            var offset = Math.floor(i * multiplier);
            // gotta sum/average the block, or we miss narrow-bandwidth spikes
            for (var j = 0; j < multiplier; j++)
                magnitude += freqByteData[offset + j];
            magnitude = magnitude / multiplier;
            var magnitude2 = freqByteData[i * multiplier];
            analyserContext.fillStyle = "hsl( " + Math.round((i * 360) / numBars) + ", 100%, 50%)";
            analyserContext.fillRect(i * SPACING, canvasHeight, BAR_WIDTH, -magnitude);
        }
    }

    rafID = window.requestAnimationFrame(updateAnalysers);
}

function toggleMono() {
    if (audioInput !== realAudioInput) {
        audioInput.disconnect();
        realAudioInput.disconnect();
        audioInput = realAudioInput;
    } else {
        realAudioInput.disconnect();
        audioInput = convertToMono(realAudioInput);
    }

    audioInput.connect(inputPoint);
}

function turnOffMicroStream() {
    //mediaRecorder.stop();
   
    if (audioStream !== null) {
        audioStream.getTracks().forEach(function (track) {
            track.stop();
        });
    }
}

function gotStream(stream) {
    inputPoint = audioContext.createGain();

    // Create an AudioNode from the stream.
    realAudioInput = audioContext.createMediaStreamSource(stream);
    audioInput = realAudioInput;
    audioInput.connect(inputPoint);
    audioStream = stream;

//    audioInput = convertToMono( input );

    analyserNode = audioContext.createAnalyser();
    analyserNode.fftSize = 2048;
    inputPoint.connect(analyserNode);

    audioRecorder = new Recorder(inputPoint);

    zeroGain = audioContext.createGain();
    zeroGain.gain.value = 0.0;
    inputPoint.connect(zeroGain);
    zeroGain.connect(audioContext.destination);
    updateAnalysers();
}

function initAudio() {
    var stopButton = document.getElementById("stopaudio");
    stopButton.disabled = true;
    if (!navigator.getUserMedia)
        navigator.getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
    if (!navigator.cancelAnimationFrame)
        navigator.cancelAnimationFrame = navigator.webkitCancelAnimationFrame || navigator.mozCancelAnimationFrame;
    if (!navigator.requestAnimationFrame)
        navigator.requestAnimationFrame = navigator.webkitRequestAnimationFrame || navigator.mozRequestAnimationFrame;

    navigator.getUserMedia(
            {
                "audio": {
                    "mandatory": {
                        "googEchoCancellation": "false",
                        "googAutoGainControl": "false",
                        "googNoiseSuppression": "false",
                        "googHighpassFilter": "false"
                    },
                    "optional": []
                }
            }, gotStream, function (e) {

        var infoButton = document.getElementById('info-button');
        infoButton.style.display = "block";

        var startButton = document.getElementById("startaudio");
        startButton.disabled = true;

        var submitButton = document.getElementById("btnSubmit");
        submitButton.disabled = true;

        var audioDiv = document.getElementById("audioDiv");

        audioDiv.hidden = true;

        var warningTitle = 'Veuillez autoriser l\'accès à votre micro';
        // Chrome Setting
        var chrome_warningText_step_1 = ' 1- Aller dans la barre d\'adresse dans le navigateur Chrome ';
        var chrome_warningText_step_2 = ' 2- Cliquer sur l\'icône de la caméra barrée ';
        var chrome_warningText_step_3 = ' 3- Cliquer sur Autorisez toujours ce site à accéder à votre caméra et à votre microphone. ';
        var chrome_warningText_step_4 = ' 4- Cliquer sur Terminé. ';
        var chrome_warningText_step_5 = ' 5- Recharger la page. ';

        //Firefox Setting
        var firefox_warningText_step_1 = ' 1- Aller à gauche dans la barre d\'adresse de Firefox ';
        var firefox_warningText_step_2 = ' 2- Cliquer sur le micro barré ';
        var firefox_warningText_step_3 = ' 3- Dans la partie permissions, supprimer la ligne <strong>Utiliser le microphone bloqué temporairement avec l\'icone X</strong> ';
        var firefox_warningText_step_4 = ' 4- Recharger la page. ';

        // Other Browser
        var other_step_1 = ' 1- Aller dans les paramètres de votre navigateur et autoriser le microphone ';


        warningTextTitle.innerHTML = warningTitle + "<br>";
        warningTextTitle.style.textAlign = 'left';

        if (navigator.userAgent.indexOf('Chrome') > -1) {
            warningText.innerHTML = "<br>" + chrome_warningText_step_1 + "<br>" + chrome_warningText_step_2 + "<img src=\"" + appUrl + "img/settinggoogle.png\" width=\"20px\" height=\"20px\">" +
                    "<br>" + chrome_warningText_step_3 + "<br>" + chrome_warningText_step_4 + "<br>" + chrome_warningText_step_5;
            warningText.style.textAlign = 'left';
        } else
        if (navigator.userAgent.indexOf('Firefox') > -1) {
            warningText.innerHTML = "<br>" + firefox_warningText_step_1 + "<br>" + firefox_warningText_step_2 + "<img src=\"" + appUrl + "img/firefoxaudiosetting.png\" width=\"45px\" height=\"20px\">" + "<br>" + firefox_warningText_step_3 + "<br>" + firefox_warningText_step_4;
            warningText.style.textAlign = 'left';
        } else {
            warningText.innerHTML = "<br>" + other_step_1 + "<br>";
            warningText.style.textAlign = 'left';
        }
        // console.log(e);
    });
}

//if (navigator.userAgent.indexOf("Firefox") === -1) {
//    window.addEventListener('load', initAudio);
//}

function askPermissionForAudioRecording() {
    initAudio();
}

//window.addEventListener('load', initAudio);

$(document).ready(function () {
    $("#btnSubmit").click(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
//        showSpinner();
        doAjax();
//        hideSpinner();
        $('#file_input').attr('disabled', true);
    });

});

function doAjax() {
    showSpinner();
    var fileType = 'audio';
    var formData = new FormData();
    formData.append(fileType + '-filename', "filename");
    formData.append(fileType + '-blob', filen);
    formData.append('uploadfile', recordingblob);
    var location = $("#row_send").attr("data-action");
    $.ajax({
        type: "POST",
        async: true,
        enctype: 'multipart/form-data',
        url: location,
        data: formData,
        xhrFields: {
            onprogress: function (e) {
                if (e.lengthComputable) {
                    // console.log("Loaded " + Number((e.loaded / e.total * 100)) + "%");
                } else {
                    console.log("Length not computable.");
                }
            }
        },
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        success: function (data) {
            console.log(data);
            $("#zone_raf").html(data);
            $("#audio").modal('hide');
            turnOffMicroStream();
             hideSpinner();
        },
        error: function (e) {
            $("#videoresult").text(e.responseText);
             hideSpinner();
        }
    });
}

function ReDoAjax() {
    showSpinner();
    var fileType = 'audio';
    // var form = $('#fileUploadForm')[0];

    var formData = new FormData();

    formData.append(fileType + '-filename', "filename");
    formData.append(fileType + '-blob', filen);
    formData.append('uploadfile', recordingblob);


    var url = (window.URL || window.webkitURL).createObjectURL(recordingblob);
    var link = document.getElementById("save");
    link.href = url;
    link.click();

    $.ajax({
        type: "POST",
        async: true,
        enctype: 'multipart/form-data',
        url: "/api/uploadfile",
        data: formData,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        success: function (data) {
            $("#result").text(data);
            var submitButton = document.getElementById("btnSubmit");
            submitButton.disabled = true;
             hideSpinner();
        },
        error: function (e) {
            $("#result").text(e.responseText);
             hideSpinner();
        }
    });
}







