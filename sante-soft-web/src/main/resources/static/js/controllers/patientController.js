/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


'use strict';
var App;

App.controller("patientController", ['$scope', 'GenericService', function ($scope, GenericService) {

        const urlBase = appUrl + "api/patient";
        const listePatients = urlBase + "/paginatePatient";
        const listePatientsURL = urlBase + "/listAllPatient";
        const saveOrUpdatePatientURL = urlBase + "/saveOrUpdatePatient";
        const detailPatientURL = urlBase + "/getPatient";
        const deletePatientURL = urlBase + "/deletePatient";

        var listPatients = [];
        $scope.objetPatient = {id: null, username: null, firstName: null, lastName: null, email: null, tel: null, titre: null, numeroCarnet: null, groupeSanguin: null, dateEnregistrement: null, password: null, confirmPassword: null};
        $scope.objetPatientMaster = {id: null, username: null, firstName: null, lastName: null, email: null, tel: null, titre: null, numeroCarnet: null, groupeSanguin: null, dateEnregistrement: null, password: null, confirmPassword: null};
        $scope.searchObject = {mc: '', numeroCarnet: '', groupeSanguin: ''};

        $scope.totalElements = 0;
        $scope.searchObjectMaster = {mc: ''};
        var firstPage = 1;
        $scope.initialNumerOfElements = 5;
        $scope.pageSizeSelect = 5;
        $scope.memoryPage = firstPage;

        $scope.listePatients = function () {
            $scope.totalElements = 0;
            var url = listePatients + "?page=" + (firstPage - 1) + "&size=" + $scope.initialNumerOfElements;
            GenericService.get(url + "&mc=" + $scope.searchObject.mc + "&numeroCarnet=" + $scope.searchObject.numeroCarnet
                    + "&groupeSanguin=" + $scope.searchObject.groupeSanguin)
                    .then(
                            function (data) {
                                $scope.listPatients = data.listPatient.content;
                                $scope.listPage = data.listPatient;
                                $scope.pageSizes = [5, 10, 15, 20, 25];
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.totalElements = data.listPatient.totalElements;
                                $scope.totalPages = data.listPatient.totalPages;
                            },
                            function () {}
                    );
        };
        $scope.listePatients();
        $scope.totalElements = 0;

        $scope.paginate = function (pageSizeSelect, listePage) {
            $scope.memoryPage = listePage;

            var url = listePatients + "?page=" + (listePage - 1) + "&size=" + pageSizeSelect;

            var url2 = url + "&mc=" + $scope.searchObject.mc + "&numeroCarnet=" + $scope.searchObject.numeroCarnet
                    + "&groupeSanguin=" + $scope.searchObject.groupeSanguin;

            GenericService.get(url2)
                    .then(
                            function (data) {
                                $scope.listPatients = data.listPatient.content;
                                $scope.listPage = data.listPatient;
                                $scope.pageSizes = [5, 10, 15, 20, 25];
                                $scope.pageSizeSelect = pageSizeSelect;
                                $scope.totalElements = data.listPatient.totalElements;
                                $scope.totalPages = data.listPatient.totalPages;
                            },
                            function () {

                            }
                    );
        };

        $scope.changePageSize = function () {
            var url = listePatients + "?page=0&size=" + $scope.pageSizeSelect;

            GenericService.get(url + "&mc=" + $scope.searchObject.mc + "&numeroCarnet=" + $scope.searchObject.numeroCarnet
                    + "&groupeSanguin=" + $scope.searchObject.groupeSanguin)
                    .then(
                            function (data) {
                                $scope.listPatients = data.listPatient.content;
                                $scope.listPage = data.listPatient;
                                $scope.totalElements = data.listPatient.totalElements;
                                $scope.totalPages = data.listPatient.totalPages;
                            },
                            function () {

                            }
                    );
        };

        $scope.modeEdition = 0;

        $scope.addPatient = function () {
            $scope.disableCode = false;
            $scope.disable = false;
            $scope.modeEdition = 0;
            $scope.titleModale = "Ajout d'un nouveau patient ";
            $scope.objetPatient = angular.copy($scope.objetPatientMaster);
            $('#detail-patient').modal('show');
        };

        $scope.savePatient = function (objetPatient) {
            // Convertir la date si nécessaire
            if (objetPatient.dateEnregistrement) {
                objetPatient.dateEnregistrement = new Date(objetPatient.dateEnregistrement).toISOString();
            }
            
            GenericService.post(saveOrUpdatePatientURL, angular.toJson(objetPatient))
                    .then(
                            function (data) {
                                $scope.objetPatient = angular.copy($scope.objetPatientMaster);
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);

                                $scope.closeModalAddUser();
                                $scope.modeEdition = 0;
                            },
                            function () {}
                    );
        };

        $scope.savePatientEtContinuer = function (objetPatient) {
            // Convertir la date si nécessaire
            if (objetPatient.dateEnregistrement) {
                objetPatient.dateEnregistrement = new Date(objetPatient.dateEnregistrement).toISOString();
            }

            GenericService.post(saveOrUpdatePatientURL, angular.toJson(objetPatient))
                    .then(
                            function (data) {
                                $scope.objetPatient = angular.copy($scope.objetPatientMaster);
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);

                                $scope.modeEdition = 0;

                            },
                            function () {}
                    );
        };
        
        $scope.closeModalAddUser = function () {
            $('#detail-patient').modal('hide');
        };

        $scope.closeModalPatient = function () {
            $('#detail-patient').modal('hide');
        };

        $scope.detailPatientV = function (id) {
            $scope.modeEdition = 2;
            $scope.titleModale = "Détail d'un patient";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailPatientURL + "?id=" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetPatient = data.patient;
                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    $scope.displayWkf = true;
                                    $('#detail-patient').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        
        $scope.editPatient = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un patient";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailPatientURL + "?id=" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetPatient = data.patient;
                                    
                                    // Convertir la date pour l'affichage dans le champ datetime-local
                                    if ($scope.objetPatient.dateEnregistrement) {
                                        var date = new Date($scope.objetPatient.dateEnregistrement);
                                        $scope.objetPatient.dateEnregistrement = date.toISOString().slice(0, 16);
                                    }

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    $scope.displayWkf = true;
                                    $('#detail-patient').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };

        $scope.deletePatient = function (id) {
            if (confirm("Êtes-vous sûr de vouloir supprimer ce patient ?")) {
                GenericService.post(deletePatientURL + "?id=" + id)
                        .then(
                                function (data) {
                                    $scope.listePatients();
                                },
                                function () {
                                }
                        );
            }
        };

        $scope.listeDesPatients = [];
        $scope.searchObjectPatient = null;
        $scope.loadPatients = function () {
            var url = listePatientsURL;
            if ($scope.searchObjectPatient && $scope.searchObjectPatient != null && $scope.searchObjectPatient != '' && $scope.searchObjectPatient != 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'mc=' + $scope.searchObjectPatient;
            }

            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeDesPatients = data.listPatient;
                                jslog("Liste Patients:" + angular.toJson($scope.listeDesPatients));
                            },
                            function () {
                            }
                    );
        };

        $scope.loadPatients();

    }]);

