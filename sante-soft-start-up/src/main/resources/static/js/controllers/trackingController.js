/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


'use strict';
var App;
App.controller("trackingCtrl", ['$scope', 'GenericService', 'UploadeService', function ($scope, GenericService, UploadeService) {
        var urlDeBase = appUrl + "api/tracking/user";
        var ListeTrackingURL = urlDeBase + "/listeTraking";
        var userListeURL = urlDeBase + "/chargerUtilisateur";
//        var getTitreUrl = urlDeBase + "/getTitre";

        $scope.listeTrack = [];
        var firstPage = 1;
        var initialNumerOfElements = 5;
        $scope.pageSizeSelect;
        $scope.pageSizes = [5, 10, 20];
        $scope.sequence = [];
        $scope.selectedPageSize = null;

        $scope.paginationObject = {selectedPageSize: ""};
        $scope.pager = {startPage: "", endPage: "", totalPages: "", currentPage: "", buttonsToShow: ""};
        $scope.listPage = {number: ""};
        $scope.username = null;

        $scope.disable = true;
        $scope.trackListe = [];
        $scope.listeUser = [];
        $scope.searchObject = {date: null, user: ''};
        $scope.searchObjectMaster = {date: null, user: ''};

        $scope.dateTrack = null;
        $scope.dateT = Date.now();

        $scope.userTrack = {
            id: null,
            username: null,
            inventionName: null
        };



//        $scope.getTitre = function (titre) {
//            
//            GenericService.get(getTitreUrl + "/" + titre.id)
//                    .then(
//                            function (data) {
//                                $scope.titreFonction = data.titre;
//                                console.log ("+++++++++++++++++++"+$scope.titreFonction.titre);
//                            },
//                            function () {
//                                
//                            }
//                    );
//        };

        $scope.userListe = function () {
            GenericService.get(userListeURL).then(
                    function (data) {
                        $scope.listeUser = data.listeUser;
                        console.log('liste des utilisateurs ' + angular.toJson($scope.listeUser));
                    }, null
                    );
        };

        $scope.userListe();

        $scope.setValue = function (valeur) {
            jslog("USER OBJECT " + angular.toJson(valeur));
        };

        $scope.paginate = function (selectedPage, page) {
            $scope.searchObject = angular.copy($scope.searchObjectMaster);
            var url = ListeTrackingURL + "/" + selectedPage + "/" + page;
            var url2 = ListeTrackingURL + "/" + selectedPage + "/" + page;

            if ($scope.dateTrack != null && $scope.dateTrack != '' && typeof ($scope.dateTrack) != 'Undefined') {
                $scope.searchObject.date = $scope.dateTrack.toISOString();
                url = url + '?date=' + $scope.searchObject.date;
            }

            if ($scope.username != null && $scope.username != '' && typeof ($scope.username) != 'Undefined') {
                $scope.searchObject.user = $scope.username;
                url = url + '&username=' + $scope.searchObject.user;
            }
            if ($scope.dateTrack != null && $scope.dateTrack != '' && $scope.dateTrack != 'Undefined' && $scope.username != null && $scope.username != '' && $scope.username != 'Undefined') {
                $scope.searchObject.date = $scope.dateTrack.toISOString();
                $scope.searchObject.user = $scope.username;
                url = url2 + '?date=' + $scope.searchObject.date + '&username=' + $scope.searchObject.user;
            }
            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeTrack = data.listeTrakingUser.content;
                                $scope.listPage = data.listeTrakingUser;
                                $scope.pageSizes = data.pageSizes;
                                $scope.sequence = data.sequence;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                            },
                            function () {
                            }
                    );
        };

        $scope.changePageSize = function (pageSizeSelect) {
            $scope.searchObject = angular.copy($scope.searchObjectMaster);
            var url = ListeTrackingURL + "/" + pageSizeSelect + "/" + firstPage;
            var url2 = ListeTrackingURL + "/" + pageSizeSelect + "/" + firstPage;
            if ($scope.dateTrack != null && $scope.dateTrack != '' && typeof ($scope.dateTrack) != 'Undefined') {
                $scope.searchObject.date = $scope.dateTrack.toISOString();
                url = url + '?date=' + $scope.searchObject.date;
            }

            if ($scope.username != null && $scope.username != '' && typeof ($scope.username) != 'Undefined') {
                $scope.searchObject.user = $scope.username;
                url = url + '&username=' + $scope.searchObject.user;
            }
            if ($scope.dateTrack != null && $scope.dateTrack != '' && $scope.dateTrack != 'Undefined' && $scope.username != null && $scope.username != '' && $scope.username != 'Undefined') {
                $scope.searchObject.date = $scope.dateTrack.toISOString();
                $scope.searchObject.user = $scope.username;
                url = url2 + '?date=' + $scope.searchObject.date + '&username=' + $scope.searchObject.user;
            }
            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeTrack = data.listeTrakingUser.content;
                                $scope.listPage = data.listeTrakingUser;
                                $scope.sequence = data.sequence;
                            },
                            function () {
                            }
                    );
        };

        // Tracking
        $scope.afficherListeTracking = function () {
            $scope.searchObject = angular.copy($scope.searchObjectMaster);
            var url = ListeTrackingURL + "/" + initialNumerOfElements + "/" + firstPage;
            var url2 = ListeTrackingURL + "/" + initialNumerOfElements + "/" + firstPage;
            if ($scope.dateTrack != null && $scope.dateTrack != '' && $scope.dateTrack != 'Undefined') {
                $scope.searchObject.date = $scope.dateTrack.toISOString();
                url = url + '?date=' + $scope.searchObject.date;
            }

            if ($scope.username != null && $scope.username != '' && $scope.username != 'Undefined') {
                $scope.searchObject.user = $scope.username;
                url = url + '?username=' + $scope.searchObject.user;
            }

            if ($scope.dateTrack != null && $scope.dateTrack != '' && $scope.dateTrack != 'Undefined' && $scope.username != null && $scope.username != '' && $scope.username != 'Undefined') {
                $scope.searchObject.date = $scope.dateTrack.toISOString();
                $scope.searchObject.user = $scope.username;
                url = url2 + '?date=' + $scope.searchObject.date + '&username=' + $scope.searchObject.user;
            }

            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeTrack = data.listeTrakingUser.content;
                                $scope.listPage = data.listeTrakingUser;
                                $scope.sequence = data.sequence;
                                $scope.pageSize = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                jslog(" List track " + angular.toJson($scope.listeTrack));
                                jslog(" url " + angular.toJson(url));

                            },
                            function () {

                            }
                    );
        };
        $scope.afficherListeTracking();

    }]);