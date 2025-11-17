/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    console.log(" ready " + $("#ideCode").val());
   var ide = $("#ideCode").val();
     if(ide === ""){
        ide = "1";
   }
    doGet(("api/codifList/findNextStep/" +  ide), function (data) {
        if ($("#ideCode").val() !== '1') {
            $('#modif').remove();
            $('#addDetails').remove();
            $('.fa-remove').remove();
            $('.remove').remove();
            disableSelects();
            readOnlyInputs();

        } else {
            if ($('#codeCodification').attr('readOnly') !== 'readonly') {
                $('#nextStatut').remove();
            }
        }
        if (data.backColor) {
            $('#nextStatut').css('background-color', data.backColor);
            $('#nextStatut').css('color', data.fontColor);
        }
        if (data.content) {
            $('#nextStatut').html(data.content);
            $('#nextStatut').attr('message', ('vouloir ' + data.content).toLowerCase());
        } else {
            $('#nextStatut').remove();
        }
    }, null);

    $('#nextStatut').click(function (event) {
        if (!confirmed) {
            event.preventDefault();
            showConfirmation($(this).attr('message'), function () {
                confirmed = true;
                $(event.target).click();
            });
        } else {
            confirmed = false;
            doPost(("api/codifList/moveToNextStep/" + $("#codeCodification").val()), null, function (data) {
                if (data.error === false) {
                    $('#statut').html(data.currentStep);
                    $('#ideCode').val(data.ideCode);
                    if ($("#ideCode").val() !== '1') {
                        $('#modif').remove();
                        $('#addDetails').remove();
                        $('.fa-remove').remove();
                        $('.remove').remove();
                    }
                    if (data.nextStep) {
                        $('#nextStatut').html(data.nextStep);
                        $('#nextStatut').attr('message', ' de vouloir ' + (data.nextStep).toLowerCase());
                    } else {
                        $('#nextStatut').remove();
                    }

                    if (data.backColor) {
                        $('#nextStatut').css('background-color', data.backColor);
                        $('#nextStatut').css('color', data.fontColor);
                    }

                    disableSelects();
                    readOnlyInputs();
                }
            }, null);
        }
    });

    $(".abs-valid-satus").click(function (event) {
        alert('entrée !');
        goToNextStep(event);
    });
});


function loadStatus() {
    $(".print-status").each(function (index, value) {
        doGet(("api/codifList/findStep/" + $(value).attr('statut')), function (data) {
            if (data.content) {
                $(value).html(data.content);
            }
            var idNextAction = "#nextAction" + $(value).attr('data');
            if (data.nextLevelText && data.nextLevelText !== '') {
                $(idNextAction).html(data.nextLevelText);
                $(idNextAction).css('background-color', data.nextLevelBackColor);
                $(idNextAction).css('color', data.nextLevelColor);
            } else {
                $(idNextAction).remove();
            }
            if (data.removethings && data.removethings === true) {
//                $('.removable' + $(value).attr('data')).remove();
            }

        }, null);
    });
}


function readOnlyInputs() {
    var line = $('.abs-disabled-for-status');
    $(line).find('input').attr('readOnly', 'readOnly');
}

function disableSelects() {
    var line = $('.abs-disabled-for-status');
    $(line).find('select').attr('disabled', 'disabled');
}

function removeButton() {
    var line = $('.abs-disabled-for-status');
    $(line).find('.btn').remove();
}



function goToNextStep1(event) {
//    event.p
//    var selector = $('#' + $(event).attr('id'));
//    selector.on('click', (function (even) {
    goToNextStep(jQuery.Event(event));
//    }));
}
function goToNextStep(event) {
    var selector = $('#' + $(event).attr('id'));
    console.log('id = ' + selector.attr('value'));
    var value = selector.attr('value');
    if (!confirmed) {
        showConfirmation(('vouloir ' + ($(selector).html()).toLowerCase()), function () {
            confirmed = true;
            goToNextStep(event);
        });
    } else {
        confirmed = false;

        if (typeof value !== "undefined") {
            doGet(("api/codifList/goToNextStep/" + value), function (data) {
                if (data.removethings && data.removethings === true) {
                    $('.removable' + selector.attr('data')).remove();
                }
            }, null);
        }
    }

}
