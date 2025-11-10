/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*Les variables globales
utilisées et accessibles depuis toutes pages*/
/**
 *  Nom d'utilisateur
 * @type String
 */
var _username = "jojo";
/**
 *  Adresse Ip de base de l'application
 * @type String
 */
var _baseIp = "localhost:8084/abs-start-up/";
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

