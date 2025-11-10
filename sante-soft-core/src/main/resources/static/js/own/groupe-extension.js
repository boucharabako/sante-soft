/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $('.edit-groupe').click(function () {
        $('*').removeAttr('required');
    });
    loadStatus();
    loadEntite();

    $(".valid-satus").click(function (event) {
        goToNextStep(event);
    });

    $(".abs-reinit-mail").click(function (event) {

        if ($('.email').val() === '') {
            $.Notification.notify('error', 'top right', 'Email requis', '');
        } else {
//        alert($('.email').val());
            doGet('api/utilisateurs/sendReinitMail/mail/' + $('.email').val(), null, null);
        }
    });



});



function goToNextStep(event) {
    let selector = $(event.target);

    if (!confirmed) {
        event.preventDefault();
        showConfirmation(('vouloir ' + ($(selector).html()).toLowerCase()) + ' ce groupe', function () {
            confirmed = true;
            $(event.target).click();
        });
    } else {
        confirmed = false;

        var idObject = $(event.target).attr('id');

        doGet(("api/groupe_extension/goToNextStep/" + $('#' + idObject).attr('value')), function (data) {
            if (data.content) {
                var labelId = '#statusLabel' + selector.attr('data');
                $(labelId).html(data.content);
            }
            var idNextAction = "#nextAction" + selector.attr('data');

            if (data.nextLevelText && data.nextLevelText !== '') {
                $(idNextAction).html(data.nextLevelText);
                $(idNextAction).css('background-color', data.nextLevelBackColor);
                $(idNextAction).css('color', data.nextLevelColor);
            } else {
                $(idNextAction).remove();
            }
            if (data.removethings && data.removethings === true) {
                $('.removable' + selector.attr('data')).remove();
            }
        }, null);
    }

}

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
                $('.removable' + $(value).attr('data')).remove();
            }

        }, null);
    });
}

function loadEntite() {
    $(".print-entite").each(function (index, value) {
        doGet(("api/parametre/refstransverses/entiteorganisationnelle/findEntite/" + $(value).attr('data-id')), function (data) {
            $(value).html(data.content);
        }, null);
    });
}

