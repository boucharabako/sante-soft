/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global angular, Json, logger, firstPage, listeUserConnectedURL, firstpage */
'use strict';
var App;
var appUrl;
App.controller("userConnectedController", ['$scope', 'GenericService', function ($scope, GenericService) {
        const urlBase = appUrl + "api/connect/user";
       // var urlBase = "api/connect/user";
       
        $scope.selectedProfil = null;
        $scope.searchMotCle = null;
        $scope.searchObject = {mc: ''};//, profil: ''};
        $scope.searchObjectMaster = {mc: ''};
        var listeUserConnectedURL = urlBase + "/getlisteConnectUser";
        $scope.listeObjetUser = [];
        var firstPage = 1;
        $scope.initialNumerOfElements = 5;
        $scope.pageSizeSelect;
        $scope.memoryPage = firstPage;
        $scope.pageSize = [];
        $scope.sequence = [];
        $scope.pageSizes = [5, 10, 20];
        $scope.selectedPageSize = null;
        $scope.paginationObject = {selectedPageSize: ""};
        $scope.pager = {startPage: "", endPage: "", totalPages: "", currentPage: "", buttonsToShow: ""};
        $scope.listPage = {number: ""};
        
        $scope.listeUserConnect = function () {
        $scope.searchObject = angular.copy($scope.searchObjectMaster);
        $scope.searchObject = angular.copy($scope.searchObjectMaster);
        if ($scope.searchMotCle !== null) {
                 $scope.searchObject.mc = $scope.searchMotCle;
              }
        var url =  listeUserConnectedURL + "/" + $scope.initialNumerOfElements + "/" + firstPage;
            GenericService.get(url + "?mc=" + $scope.searchObject.mc)
                    .then(
                            function (data) {
                                
                                $scope.listeObjetUser = data.listeUser.content;
                            //    alert(angular.toJson($scope.listeObjetUser));
                                $scope.listPage = data.listeUser;
                                $scope.pageSize = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSize[0];
                                $scope.sequence = data.sequence;
                             //   jslog("LISTE EMPLACEMENT = " + angular.toJson($scope.listeUserConnect()));
                            },
                            function () {}
                    );
        };
        $scope.listeUserConnect();
        
            $scope.changePageSize = function () {
            var url = listeUserConnectedURL + "/" + $scope.pageSizeSelect + "/" + firstPage;

            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeObjetUser = data.listeUser.content;
                                $scope.listPage = data.listeUser;
                                $scope.pageSize = data.pageSize;
                              //  $scope.pageSizeSelect = $scope.pageSize[0];
                                $scope.sequence = data.sequence;
                            },
                            function () {
                            }
                    );
        };
        
            $scope.paginate = function (pageSizeSelect, listePage){
            $scope.memoryPage = listePage;
            var url = listeUserConnectedURL + "/" + pageSizeSelect + "/" + $scope.memoryPage;
            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeObjetUser = data.listeUser.content;
                                $scope.listPage = data.listeUser;
                                $scope.pageSize = data.pageSize;
                                $scope.sequence = data.sequence;
                            },
                            function () {

                            }
                    );
        };
}]);
