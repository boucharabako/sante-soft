/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

'use strict';
/*
 * Generic basic services
 */
var App;
var appUrl;
App.factory('StatutService', ['$http', '$q', function ($http, $q) {
        var statutSaisieUrl = appUrl + "api/codifList/statutSaisieProvider";
        var statutActifUrl = appUrl + "api/codifList/statutActifProvider";
        var statutObsoleteUrl = appUrl + "api/codifList/statutObsoleteProvider";
        var etatEnSaisie = {id: null, libelle: null, action: null, color: null};
        var etatActif = {id: null, libelle: null, action: null, color: null};
        var etatObselete = {id: null, libelle: null};
        return {
            getStatutSaisie: function () {
                return $http.get(statutSaisieUrl)
                        .then(
                                function (response) {
                                    etatEnSaisie = response.data.statutSaisie;
                                    return etatEnSaisie;
                                },
                                function (errResponse) {
                                    $.Notification.notify('error', 'top right', 'Error lors de l\'envoie de requête ! ', '');
                                    return $q.reject(errResponse);
                                }
                        );
            },

            getStatutActif: function () {
                return $http.get(statutActifUrl)
                        .then(
                                function (response) {
                                    etatActif = response.data.statutActif;
                                    return etatActif;
                                },
                                function (errResponse) {
                                    $.Notification.notify('error', 'top right', 'Error lors de l\'envoie de requête ! ', '');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getStatutObsolete: function () {
                return $http.get(statutObsoleteUrl)
                        .then(
                                function (response) {
                                    etatObselete = response.data.statutObsolete;
                                    return etatObselete;
                                },
                                function (errResponse) {
                                    $.Notification.notify('error', 'top right', 'Error lors de l\'envoie de requête ! ', '');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);

