/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
var App;

App.controller("utilisateursController", ['$scope', 'GenericService', function ($scope, GenericService) {

        $scope.statutUser;
        $scope.hideArbre = function () {
//            alert($scope.statutUser);
            if ($scope.statutUser != null && $scope.statutUser == '2') {
                return true;
            }
            return false;
        };

        $scope.disableDiv = function (ideCode) {
//            jslog("TTTTTTT " + ideCode)
            if (ideCode != null && ideCode == '2') {
                $scope.disablePointerOnDetailReservationPanel();
            }
            $scope.enablePointerOnDetailReservationPanel();
        };

        $scope.disablePointerOnDetailReservationPanel = function () {
            var element = document.getElementById("marbre_noeud");
//            var element = document.getElementById("eoDiv");
           
            element.classList.add("disabledDiv");
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        };
        $scope.enablePointerOnDetailReservationPanel = function () {
            var element = document.getElementById("marbre_noeud");
            element.classList.remove("disabledDiv");
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        };
// $scope.disablePointerOnDetailReservationPanel();
    }]);