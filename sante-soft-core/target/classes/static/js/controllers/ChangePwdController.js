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
App.controller('ChangePwdController', ['$scope', 'GenericService', function ($scope, GenericService) {

        var changepwdUrl = appUrl + "api/password-reset";
        //variables
        $scope.dto = {pwd:'',pwdc:''};
       



        /**
         * 
         * Recupere la liste des compte
         */
        $scope.save = function () {
            GenericService.post(changepwdUrl , $scope.dto)
                    .then(
                            function (data) {
                                window.location=appUrl;

                            },
                            function (errResponse) {
                                console.error('Error while fetching list compte' + JSON.stringify(errResponse));
                            }
                    );
        };
   



    }]);
