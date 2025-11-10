/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
var App;
var appUrl;
App.controller('usersLogController', ['$scope', 'GenericService', 'StatutService', function ($scope, GenericService, StatutService) {
        var UrlBase = appUrl + "parametre/log";
        var allUsersUrl = UrlBase + "/listeUsers";


        var firstPage = 1;
//        var initialNumerOfElements = 5;
        $scope.initialNumerOfElements = 5;
        $scope.pageSizeSelect;
        $scope.memoryPage = firstPage;
        $scope.pageSize = [5, 10, 20];
        $scope.sequence = [];
        $scope.selectedPageSize = null;
        $scope.paginationObject = {selectedPageSize: ""};
        $scope.pager = {startPage: "", endPage: "", totalPages: "", currentPage: "", buttonsToShow: ""};
        $scope.listPage = {number: ""};

        $scope.userSimpleObject = {identifiant: null, date: null, type: null, nom: null, localisation: null, prenoms: null, createdBy: null};
        $scope.userSimpleObjectMaster = {identifiant: null, date: null, type: null, nom: null, localisation: null, prenoms: null, createdBy: null};

        $scope.listeUserObject = [];
        //$scope.motCle = null;

        //$scope.dateSelected = null;
        $scope.searchMotCleUser = null;
        $scope.searchMotCleUserDate = null;

        $scope.searchObject = {mc: '', date: ''};
        $scope.searchObjectMaster = {mc: '', date: ''};

        $scope.paginate = function (pageSizeSelect, listPage) {
            $scope.memoryPage = listPage;
            var url = allUsersUrl + "/" + pageSizeSelect + "/" + $scope.memoryPage;
//            $scope.searchObject = angular.copy($scope.searchObjectMaster);
            if ($scope.searchMotCleUser && $scope.searchMotCleUser != null && $scope.searchMotCleUser !== 'undefined' && $scope.searchMotCleUser !== '') {
                $scope.searchObject.mc = $scope.searchMotCleUser;
                url = url + ((url.includes("?")) ? "&" : "?") + 'mc=' + $scope.searchObject.mc;
            }
            if ($scope.searchMotCleUserDate && $scope.searchMotCleUserDate != null && $scope.searchMotCleUserDate !== 'undefined' && $scope.searchMotCleUserDate !== '') {
                //alert('DATE     ' + $scope.searchMotCleUserDate);
                $scope.searchObject.date = $scope.searchMotCleUserDate;
                url = url + ((url.includes("?")) ? "&" : "?") + 'date=' + (($scope.searchObject.date && $scope.searchObject.date != null && $scope.searchObject.date != '') ? new Date($scope.searchObject.date).toISOString() : null);
            }
//            var finalURL = allUsersUrl + "/" + selectedPage + "/" + page + "?mc=" + $scope.motCle + "&date=" + $scope.dateSelected;
            jslog("finalURL " + url)
            GenericService.get(url)
                    .then(
                            function (data) {
                                //alert("BVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
                                $scope.listeUserObject = data.listeUsers.content;
                                //console.log("USERSLISTE  *************" + angular.toJson($scope.listeUserObject));
                                jslog("URL USERSLISTE " + angular.toJson(url));
                                $scope.listPage = data.listeUsers;
                                $scope.pageSize = data.pageSize;
                                $scope.sequence = data.sequence;
                                //$scope.pageSizeSelect = $scope.pageSize[0];
                                jslog("$scope.pageSize " + angular.toJson($scope.pageSize));
                                jslog("$scope.sequence " + angular.toJson($scope.sequence));
                                jslog("$scope.pageSizeSelect " + angular.toJson($scope.pageSizeSelect));
                            },
                            function () {}
                    );

        };
//        $scope.paginate(initialNumerOfElements, firstPage);



        $scope.afficherListe = function () {
            $scope.searchObject = angular.copy($scope.searchObjectMaster);
            var url = allUsersUrl + "/" + $scope.initialNumerOfElements + "/" + firstPage;
            if ($scope.searchMotCleUser && $scope.searchMotCleUser != null && $scope.searchMotCleUser !== 'undefined' && $scope.searchMotCleUser !== '') {
                $scope.searchObject.mc = $scope.searchMotCleUser;
                url = url + ((url.includes("?")) ? "&" : "?") + 'mc=' + $scope.searchObject.mc;
            }
            if ($scope.searchMotCleUserDate && $scope.searchMotCleUserDate != null && $scope.searchMotCleUserDate !== 'undefined' && $scope.searchMotCleUserDate !== '') {
                //alert('DATE     ' + $scope.searchMotCleUserDate);
                $scope.searchObject.date = $scope.searchMotCleUserDate;
                url = url + ((url.includes("?")) ? "&" : "?") + 'date=' + (($scope.searchObject.date && $scope.searchObject.date != null && $scope.searchObject.date != '') ? new Date($scope.searchObject.date).toISOString() : null);
            }
            GenericService.get(url)
                    .then(
                            function (data) {
                                //alert("WWWWWWWWWWWWWWWWWWWWWW");
                                $scope.listeUserObject = data.listeUsers.content;
                                $scope.listPage = data.listeUsers;
                                $scope.pageSize = data.pageSize;
                                $scope.sequence = data.sequence;
                                $scope.pageSizeSelect = $scope.pageSize[0];
                                jslog("listeObject" + angular.toJson($scope.listeUserObject));
                            },
                            function () {
                            }
                    );
        };
        $scope.afficherListe();



        $scope.searchUsers = function () {
            $scope.paginate($scope.pageSizeSelect, firstPage);
        };

//        $scope.changePageSize = function (pageSizeSelect) {
        $scope.changePageSize = function () {
//            $scope.searchObject = angular.copy($scope.searchObjectMaster);
            var url = allUsersUrl + "/" + $scope.pageSizeSelect + "/" + firstPage;

            if ($scope.searchMotCleUser && $scope.searchMotCleUser != null && $scope.searchMotCleUser !== 'undefined' && $scope.searchMotCleUser !== '') {
                $scope.searchObject.mc = $scope.searchMotCleUser;
                url = url + ((url.includes("?")) ? "&" : "?") + 'mc=' + $scope.searchObject.mc;
            }
            if ($scope.searchMotCleUserDate && $scope.searchMotCleUserDate != null && $scope.searchMotCleUserDate !== 'undefined' && $scope.searchMotCleUserDate !== '') {
                //alert('DATE     ' + $scope.searchMotCleUserDate);
                $scope.searchObject.date = $scope.searchMotCleUserDate;
                url = url + ((url.includes("?")) ? "&" : "?") + 'date=' + (($scope.searchObject.date && $scope.searchObject.date != null && $scope.searchObject.date != '') ? new Date($scope.searchObject.date).toISOString() : null);
            }
            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeUserObject = data.listeUsers.content;
                                $scope.listPage = data.listeUsers;
                                $scope.pageSize = data.pageSize;
                                $scope.sequence = data.sequence;

                                jslog("URLPAGESIZE= " + angular.toJson(url));
                            },
                            function () {
                            }
                    );
        };

        $('#idautocomplete').keyup(function () {
            if (this.value == '') {
                $scope.listeUserObject = [];
            }
        });

        if (!$scope.$$phase) {
            $scope.$apply();
        }
    }]);