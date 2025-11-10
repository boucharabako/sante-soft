/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*Les variables globales
 utilisées et accessibles depuis toutes pages*/
/**
 * 
 * @type String Nom d'utilisateur
 */
var _username = "jojo";
/**
 * 
 * @type String Adresse Ip de base de l'application
 */
var _baseIp = "http://localhost:8085/api/";
var _baseUrl = "";
var _displayName;
var _token;


function display(type, title, msg) {
    $.Notification.notify(type, 'top right', title, msg);
}

function success() {
    $.Notification.notify('custom', 'top right', 'Succes', 'Operation éffectué');
}

function error() {
    $.Notification.notify('error', 'top right', 'Echec', 'Opération non éffectué');
}

function openDetailWithDomain(event, nomClass) {
    var selector = "." + sessionStorage.getItem("nomClass");
    $(selector).addClass("active");
    sessionStorage.setItem("nomClass", nomClass);
    console.log(nomClass);
    var val = $(event).attr('data-id');
    window.location.href = "/abs-start-up/parametre/conf/parameter?type=d&code=" + val;
}


function openDetailWithPorte(event, nomClass) {
    var selector = "." + sessionStorage.getItem("nomClass");
    $(selector).addClass("active");
    sessionStorage.setItem("nomClass", nomClass);
    console.log(nomClass);
//    var elem = document.getElementById(id);
//    $(elem).addClass("active");
    var val = $(event).attr('href');
    window.location = val;
}

function openEtiquetteDetailWithDomain(event, nomClass) {
    var selector = "." + sessionStorage.getItem("nomClass");
    $(selector).addClass("active");
    sessionStorage.setItem("nomClass", nomClass);
    console.log(nomClass);
    var val = $(event).attr('href');
    window.location = val;
}

function openEtiquetteDetailWithPorte(event, nomClass) {
    var selector = "." + sessionStorage.getItem("nomClass");
    $(selector).addClass("active");
    sessionStorage.setItem("nomClass", nomClass);

    console.log(nomClass);
    var val = $(event).attr('href');
    window.location = val;
}

function checkSpcialChar(event) {
    if (!((event.keyCode >= 65) && (event.keyCode <= 90) || (event.keyCode >= 97) && (event.keyCode <= 122) || (event.keyCode >= 48) && (event.keyCode <= 57))) {
        event.returnValue = false;
        return;
    }
    event.returnValue = true;
}

function changeColorPortee(newcolor) {
    var elem = document.getElementById('portee');
    console.log(elem);
    elem.style.background = newcolor;
    // $(elem).addClass('tabColor #ebeff2') ;
}

function changeColorDomaine(newcolor) {
    var elem = document.getElementById('domaine');
    console.log(elem);
    elem.style.background = newcolor;
    // $(elem).addClass('tabColor #ebeff2') ;
}


function initer() {
    var url = $('.noeud').attr('href');
    var id = $('.noeud').attr('data-id');
    console.log(url);
    $.ajax({
        url: url + 'api/ged/directory-node/content?id=' + id,
        type: 'GET',
        success: function (data, textStatus, jqXHR) {
            for (var i = 0; i < data.directories.length; i++) {
                console.log(data.directories[i].name);
                console.log("<li data-jstree='" + "{'opened':false}'" + " class='jstree-open'>" + data.directories[i].name + "</li>");
                $('#ul_node_under_directory').html("<li data-jstree='" + "{'opened':false}'" + " class='jstree-open'" + "onclick='selectDossier($(this))'>" + data.directories[i].name + "</li>");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {

        }

    });
}

function showConfirmation(complement, succes_callBack) {
    alert("Sure");
    swal({
        title: 'Êtes-vous sûr ' + complement + ' ?',
        text: '',
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui',
        cancelButtonText: 'Non',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger m-l-10',
        buttonsStyling: false
    }).then(function () {
        succes_callBack();
    }, function (dismiss) {
        if (dismiss === 'cancel') {
            swal(
                    'Annulé',
                    "L'opération a été annulée",
                    'error'
                    )
        }
    });
}

function showConfirmationNew(complement, succes_callBack) {
    Swal.fire({
                title: '<strong>HTML <u>example</u></strong>',
                type: 'info',
                html:
                  'You can use <b>bold text</b>, ' +
                  '<a href="//github.com">links</a> ' +
                  'and other HTML tags',
                showCloseButton: true,
                showCancelButton: true,
                focusConfirm: false,
                confirmButtonText:
                  '<i class="fa fa-thumbs-up"></i> Great!',
                confirmButtonAriaLabel: 'Thumbs up, great!',
                cancelButtonText:
                  '<i class="fa fa-thumbs-down"></i>',
                cancelButtonAriaLabel: 'Thumbs down',
              }).then(function () {
        succes_callBack();
    }, function (dismiss) {
        
    });
}


function getBaseUrl(){
    var ro = $("#main-content-page").attr("data-main-href");
    return ro;
}


