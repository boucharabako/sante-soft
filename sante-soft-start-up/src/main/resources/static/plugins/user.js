/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    passWordLentgth = 7;

    $(".ajouter").click(function () {
        value = $("#pwd1").val();
        if (value.length < passWordLentgth) {
            $.Notification.notify('error', 'top right', 'Le mot de passe doit avoir au moins ' + passWordLentgth + ' caractères!', '');
        } else {
            if ($("#pwd1").val() !== $("#pwd2").val() || $("#pwd1").val() === '') {
                $("#pwd1").val("");
                $("#pwd2").val("");
                $(".pwd").addClass("has-error");
                $.Notification.notify('error', 'top right', 'Les mots de passe ne correspondent pas !', '');
            } else {
                $(".pwd").removeClass("has-error");
                $("#ajouter").click();
            }
        }
    });

    $(".ajouter1").click(function () {
        value = $("#pwd1").val();
        if (value.length < passWordLentgth) {
            $.Notification.notify('error', 'top right', 'Le mot de passe doit avoir au moins ' + passWordLentgth + ' caractères!', '');
            $("#pwd1").val("");
            $("#pwd2").val("");
            $(".pwd").addClass("has-error");
        } else {
            if ($("#pwd1").val() !== $("#pwd2").val() || $("#pwd1").val() === '') {
                $("#pwd1").val("");
                $("#pwd2").val("");
                $(".pwd").addClass("has-error");
                $.Notification.notify('error', 'top right', 'Les mots de passe ne correspondent pas !', '');
            } else {
                $(".pwd").removeClass("has-error");
                $("#ajouter1").click();
            }
        }
    });

    $(".modifier").click(function () {
        if ($("#pwd1").val() !== $("#pwd2").val()) {
            $("#pwd1").val("");
            $("#pwd2").val("");
            $(".pwd").addClass("has-error");
        } else {
            $(".pwd").removeClass("has-error");
            $("#pwd1").addClass("");
        }
    });


    $(".modifier-other").click(function () {
        value = $("#pwd1").val();
        if (value.length < passWordLentgth) {
            $.Notification.notify('error', 'top right', 'Le mot de passe doit avoir au moins ' + passWordLentgth + ' caractères!', '');
            $(".pwd").addClass("has-error");
            $("#pwd").val("");
            $("#pwd1").val("");
        } else {
            if ($("#pwd1").val() !== $("#pwd").val() || $("#pwd").val() === '') {
                $("#pwd").val("");
                $("#pwd1").val("");
                $(".pwd").addClass("has-error");
                $.Notification.notify('error', 'top right', 'Les mots de passe ne correspondent pas !', '');
            } else {
                $(".pwd").removeClass("has-error");
                $("#modifier").click();
            }
        }
    });

    $(".modifier-self").click(function () {
        value = $("#pwd1").val();
        if (value.length < passWordLentgth) {
            $.Notification.notify('error', 'top right', 'Le mot de passe doit avoir au moins ' + passWordLentgth + ' caractères!', '');
            $(".pwd").addClass("has-error");
            $("#pwd2").val("");
            $("#pwd1").val("");
        } else {
            if ($("#pwd1").val() !== $("#pwd2").val() || $("#pwd1").val() === '') {
                $("#pwd").val("");
                $("#pwd1").val("");
                $("#pwd2").val("");
                $(".pwd").addClass("has-error");
                $.Notification.notify('error', 'top right', 'Les mots de passe ne correspondent pas !', '');
            } else {
                $(".pwd").removeClass("has-error");
                $("#modifierSelf").click();
            }
        }
    });

    function uploadFile() {
        $.ajax({
            url: "http://localhost:8084/abs-start-up/api/picture/uploadFile",
            type: "POST",
            data: new FormData($("#upload-file-form")[0]),
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            success: function (data) {
//                            $("#result").text(data);
//                            console.log("SUCCESS : ", JSON.stringify(data));
                console.log("SUCCESS !");
                var binary = '';
                var bytes = new Uint8Array(data);
                var len = bytes.byteLength;
                for (var i = 0; i < len; i++) {
                    binary += String.fromCharCode(bytes[i]);
                }

                console.log(JSON.stringify(binary));
                $('#myImage').attr('src', ('data:image/png;base64,' + data));
//                            $('#myImage1').attr('src', ('data:image/png;base64,' + 'iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=='));
//                            $("#btnSubmit").prop("disabled", false);
                // Handle upload success
                // ...
            },
            error: function () {
                // Handle upload error
                // ...
            }
        });
    }

    $(".reset-mail").click(function () {

        $.ajax({
            url: $(this).attr("value"),
            type: "GET",
            processData: false,
            contentType: false,
            cache: false,
            success: function (data) {
                if (data.error === false) {
                    $.Notification.notify('success', 'top right', data.msg, '');
                } else {
                    $.Notification.notify('error', 'top right', data.msg, '');
                }
            },
            error: function () {
                $.Notification.notify('error', 'top right', 'Erreur lors de opération', '');
                // Handle upload error
                // ...
            }
        });

    });

});

