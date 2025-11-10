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
App.controller("userController", ['$scope', 'GenericService', function ($scope, GenericService) {
        var urlDeBase = appUrl + "api/connect/user";
        var listeUserURL = urlDeBase + "/getlisteConnectUser";


        $scope.listeUtilisateur = [];
        var firstPage = 1;
        var initialNumerOfElements = 5;
        $scope.pageSizeSelect;
        $scope.pageSizes = [5, 10, 20];
        $scope.sequence = [];
        $scope.selectedPageSize = null;

        $scope.paginationObject = {selectedPageSize: ""};
        $scope.pager = {startPage: "", endPage: "", totalPages: "", currentPage: "", buttonsToShow: ""};
        $scope.listePage = {number: ""};
        $scope.username = null;
        $scope.user = null;

//       $scope.utilisateur = {
//            id:null,
//            username:null,
//            email:null,
//            name:null,
//            isConnect: false 
//           
//        };

        $scope.disable = true;

        $scope.paginate = function (selectedPage, page) {

            GenericService.get(listeUserURL + "/" + selectedPage + "/" + page)
                    .then(
                            function (data) {
                                $scope.listeUtilisateur = data.listeUser.content;
                                $scope.listePage = data.listeUser;
                                $scope.pageSizes = data.pageSizes;
                                $scope.sequence = data.sequence;
//                              $scope.pageSizeSelect = $scope.pageSizes[0];
                            },
                            function () {
                            }
                    );
        };

        $scope.changePageSize = function () {
            GenericService.get(listeUserURL + "/" + $scope.pageSizeSelect + "/" + firstPage)
                    .then(
                            function (data) {
                                $scope.listeUtilisateur = data.listeUser.content;
                                $scope.listePage = data.listeUser;
                                $scope.sequence = data.sequence;
                            },
                            function () {
                            }
                    );
        };

        $scope.afficherListeUser = function () {

            GenericService.get(listeUserURL + "/" + initialNumerOfElements + "/" + firstPage)
                    .then(
                            function (data) {
                                $scope.listeUtilisateur = data.listeUser.content;
                                $scope.listePage = data.listeUser;
                                $scope.sequence = data.sequence;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                jslog(" Liste des utilisateurs " + angular.toJson($scope.listeUtilisateur));

                            },
                            function () {
                            }
                    );
        };


//       
//       $scope.getEtat = function (user) {
//           
//           $scope.etat1= " En ligne";
//           $scope.etat2= "Hors ligne";
//          if (user.isConnect)
//          {
//              return $scope.etat1;
//          } 
//           else 
//            {
//               return $scope.etat2;
//           }
//      };

        $scope.getEtat = function (user) {
            var etat = false;
            if (user.isConnect) {
                etat = true;
            }
            return etat;
        };


        $scope.afficherListeUser();

    }]);
