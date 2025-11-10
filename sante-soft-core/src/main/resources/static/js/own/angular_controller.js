/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
var App;
App.controller('AppController', ['$scope', 'GenericService', function ($scope, GenericService) {
        $scope.countries = [];
        $scope.utilisateurs = [];
        var baseUrl = appUrl + 'angularjscontroller/';
        var listUrl = baseUrl+'list';
        var createUrl = baseUrl+'save';
        var deleteUrl = baseUrl+'delete';
        var editUrl = baseUrl+'edit';

        $scope.master = {firstName: "", lastName: ""};
        $scope.reset = function () {
            $scope.user = angular.copy($scope.master);
        };
        $scope.reset();

        $scope.fetchAllUsers = function () {
            GenericService.get(listUrl)
                    .then(
                            function (d) {
                                $scope.utilisateurs = d;
                            },
                            function (errResponse) {
                            }
                    );
        };

        $scope.createUser = function () {
            GenericService.post(createUrl, JSON.stringify($scope.user))
                    .then(
                            function (d) {
                                console.log(d);
                                 $scope.fetchAllUsers();
                                 $scope.user = angular.copy($scope.master);
                            },
                            function (errResponse) {
                            }
                    );
        };
        

        $scope.editUser = function (id) {
            for (var i = 0; i < $scope.utilisateurs.length; i++) {
                if ($scope.utilisateurs[i].id === id) {
                    $scope.user = angular.copy($scope.utilisateurs[i]);
                    break;
                }
            }
            
//            GenericService.put(editUrl + "/" + id)
//                    .then(
//                            function (d) {
//                                $scope.utilisateurs = d;
//                            },
//                            function (errResponse) {
//                            }
//                    );
        };

        $scope.deleteUser = function (id) {
            GenericService.delete(deleteUrl + "/" + id)
                    .then(
                            function (d) {
                                $scope.utilisateurs = d;
                                $scope.fetchAllUsers();
                            },
                            function (errResponse) {
                            }
                    );
        };

        $scope.fetchAllUsers();
    }]);
