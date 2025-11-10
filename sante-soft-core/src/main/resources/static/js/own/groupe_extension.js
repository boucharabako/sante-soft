/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function saveOne(el) {
    var numero = $(el).attr("data-numero");
    var id = $("#id_" + numero).val();
    var identifiant = $(el).attr('data-id');
    var titre = $("#titre_" + numero).val();
    var description = $("#description_" + numero).val();
    var conceptMetier = $("#conceptMetier" + numero).val();
    var liste = $("#select_extension").val() + "";
    console.log("here is your liste " + liste);
    if (liste === "undefined" || liste === "null") {
        $("#sai_extensions").addClass("has-error");
        $.Notification.notify('error', 'top right', "Erreur, champ obligatoire", "Choix d'au moins une extension obligatoire");
    } else {
        var groupeExtention = {
            id: id === undefined ? id : identifiant,
            titre: titre,
            description: description,
            conceptMetier:conceptMetier,
            listeExtensions: liste
        };
        console.log("here is your object " + JSON.stringify(groupeExtention));
        doPost("api/groupe_extension/save", groupeExtention, function (data) {

        }, function () {

        });
    }

}

function detail(el) {
    var id = $(el).attr("data-id");
    console.log("id==" + id);
    if (id === undefined) {

    } else {
        window.location = appUrl + "parametre/conf/extension/detail/" + id + "#groupeExtensionTab";
    }
}


function addGrpeRow(el) {
    var first_el = $('#tbl_tbody tr td')[2];
    var first_val = $(first_el).children('input').val();
    var second_el = $('#tbl_tbody tr td')[3];
    var second_val = $(second_el).children('input').val();
    if (first_val === "" || second_val === "") {
        if (first_val === "") {
            $(first_el).attr('style', 'border:red solid 1px');
        }
        if (second_val === "") {
            $(second_el).attr('style', 'border:red solid 1px');
        }
    } else {
        window.location = appUrl + "parametre/conf/extension/add/new#groupeExtensionTab";
    }
    //if(first_el.val())
    //window.location = appUrl + "parametre/conf/extension/add/new#groupeExtensionTab";
//    doGlobalGet(appUrl + "parametre/conf/extension/add/new", "tbl_tbody");

}

