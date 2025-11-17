/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

'use strict';
var App;
var appUrl;
App.controller('UserDetailController', ['$scope', 'GenericService', function ($scope, GenericService) {
        const urlDeBase = appUrl + "api/utilisateurs";
        const checkSecretCodeUrl = urlDeBase + "/checkPassword";
        $scope.requestObject = {password: null, secretCode: null, disabledField: false};
        $scope.requestObjectMaster = {password: null, secretCode: null, disabledField: false};

        $scope.showSecretCode = function () {
            $("#detailsecretcode").modal('show');
            $scope.requestObject = angular.copy($scope.requestObjectMaster);
        };

        $scope.exitModal = function () {
            $("#detailsecretcode").modal('hide');
        };

        $scope.afficher = function () {
            console.log("requestObject " + angular.toJson($scope.requestObject));
            if ($scope.requestObject != null &&
                    $scope.requestObject != undefined &&
                    $scope.requestObject.password != null &&
                    $scope.requestObject.password != undefined)
                GenericService.post(checkSecretCodeUrl, angular.toJson($scope.requestObject))
                        .then(
                                function (data) {
                                    $scope.requestObject = angular.copy(data.requestObject);
                                    console.log("$scope.requestObject "+$scope.requestObject);
                                },
                                function () {

                                }
                        );
        };
       
     $scope.show = function(){
         
       alert(" hi !!!");  
     };

    }]);
