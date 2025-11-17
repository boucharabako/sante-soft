/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

'use strict';
var App;
var appUrl;
App.controller('AccesTerminauxMobileController', ['$scope', 'GenericService', function ($scope, GenericService) {

        var listmdacUrl = appUrl + "api/parametre/mdac/find-all";
        var executerActionUrl = appUrl + "api/parametre/mdac/executer-action";
        //variables
        $scope.listMdac = [];
        $scope.motcle = "";
        $scope.size = 10;
        $scope.sizes = [10, 50, 100, 300, 500, 1000, 2000, 5000, 10000, 100000];

        $scope.page = 0;
        $scope.pages = [];



        /**
         * 
         * Recupere la liste des compte
         */
        $scope.findAll = function () {
            console.log("$scope.page " + $scope.page);
            console.log("$scope.page " + $scope.size);
            GenericService.get(listmdacUrl + "?mc=" + $scope.motcle + "&size=" + $scope.size + "&page=" + $scope.page)
                    .then(
                            function (data) {
                                console.log("RECHERCHE OK" + $scope.size)
                                $scope.listMdac = data.content;
                                $scope.pages = [];
                                for (var i = 0; i < data.totalPages; i++) {
                                    $scope.pages.push(i);
                                }

                            },
                            function (errResponse) {
                                console.error('Error while fetching list compte' + JSON.stringify(errResponse));
                            }
                    );
        };
        $scope.findAll();
        $scope.rechercher = function (page) {
            $scope.page = page;
            $scope.findAll();
        };
        /**
         * 
         * @param {type} index index de l'objet à modifier
         * @param {type} action identifiant de l'action à executer
         * @returns {undefined}
         */
        $scope.executeraction = function (index, action) {

            var param = {idObjet: $scope.listMdac[index].id, idAction: action, concepMetier: $scope.listMdac[index].conceptMetier};

            console.log(param);
            showConfirmation($scope.listMdac[index].actionObject.libelleAction, function () {
                GenericService.post(executerActionUrl, param)
                        .then(
                                function (data) {
                                    console.log("****");
                                    console.log(data.etatCourant)
                                    $scope.listMdac[index].etatObject = data.etatCourant;
                                    if (data.actions.length === 1) {
                                        $scope.listMdac[index].etatHasOnlyOneAction = true;

                                        $scope.listMdac[index].actionObject = data.actions[0];
                                    }
                                    if (data.actions.length > 1) {
                                        $scope.listMdac[index].etatHasOnlyOneAction = false;
                                        $scope.listMdac[index].listActions = data.actions;
                                    }
                                    if (data.actions.length === 0) {
                                        $scope.listMdac[index].etatHasOnlyOneAction = false;
                                        $scope.listMdac[index].listActions = [];
                                        $scope.listMdac[index].actionObject = {};
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching list compte' + JSON.stringify(errResponse));
                                }
                        );
            }, '', function () {
                $scope.listMdac[index].actionObject = {};
                var t=[];
                angular.forEach($scope.listMdac[index].listActions,function(val){
                    t.push(val);
                });
                $scope.listMdac[index].listActions=t;
console.log("******************************************************");

            });

        };



    }]);
