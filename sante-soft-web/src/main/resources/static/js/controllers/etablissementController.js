/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


'use strict';
var App;

App.controller("etablissementController", ['$scope', 'GenericService', function ($scope, GenericService) {

        const urlBase = appUrl + "fonctionnelle/etablissement";
        const listeEtablissements = urlBase + "/list";
        const listeEtablissementsURL = urlBase + "/listAllEtablissement";
        const saveOrUpdateEtablissementURL = urlBase + "/saveOrUpdateEtablissement";
        const detailEtablissementURL = urlBase + "/getEtablissement";
        const deleteEtablissementURL = urlBase + "/deleteEtablissement";
        const listTypeEtablissementURL = urlBase + "/listTypeEtablissement";
        const listRegionURL = urlBase + "/listRegion";

        var listEtablissements = [];
        $scope.listeTypeEtablissements = [];
        $scope.listeRegions = [];
        $scope.objetEtablissement = {id: null, codeEtablissement: null, libelleEtablissement: null, regionEtablissement: null, typeEtablissement: null, adresse: null};
        $scope.objetEtablissementMaster = {id: null, codeEtablissement: null, libelleEtablissement: null, regionEtablissement: null, typeEtablissement: null, adresse: null};
        $scope.searchObject = {mc: '', code: '', libelle: '', region: '', typeEtablissement: ''};

        $scope.totalElements = 0;
        $scope.searchObjectMaster = {mc: ''};
        var firstPage = 1;
        $scope.initialNumerOfElements = 5;
        $scope.pageSizeSelect = 5;
        $scope.memoryPage = firstPage;

        $scope.listeEtablissements = function () {
            $scope.totalElements = 0;
            var url = listeEtablissements + "/" + $scope.initialNumerOfElements + "/" + firstPage;
            GenericService.get(url + "/?mc=" + $scope.searchObject.mc + "&code=" + $scope.searchObject.code
                    + "&libelle=" + $scope.searchObject.libelle + "&region=" + $scope.searchObject.region + "&typeEtablissement=" + $scope.searchObject.typeEtablissement)
                    .then(
                            function (data) {
                                $scope.listEtablissements = data.listEtablissement.content;
                                $scope.listPage = data.listEtablissement;
                                $scope.pageSizes = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listEtablissement.totalElements;
                            },
                            function () {}
                    );
        };
        $scope.listeEtablissements();
        $scope.totalElements = 0;

        $scope.paginate = function (pageSizeSelect, listePage) {
            $scope.memoryPage = listePage;
            var url = listeEtablissements + "/" + pageSizeSelect + "/" + $scope.memoryPage;
            var url2 = url + "/?mc=" + $scope.searchObject.mc + "&code=" + $scope.searchObject.code
                    + "&libelle=" + $scope.searchObject.libelle + "&region=" + $scope.searchObject.region + "&typeEtablissement=" + $scope.searchObject.typeEtablissement;

            GenericService.get(url2)
                    .then(
                            function (data) {
                                $scope.listEtablissements = data.listEtablissement.content;
                                $scope.listPage = data.listEtablissement;
                                $scope.pageSizes = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listEtablissement.totalElements;
                            },
                            function () {}
                    );
        };

        $scope.changePageSize = function () {
            var url = listeEtablissements + "/" + $scope.pageSizeSelect + "/" + firstPage;
            GenericService.get(url + "/?mc=" + $scope.searchObject.mc + "&code=" + $scope.searchObject.code
                    + "&libelle=" + $scope.searchObject.libelle + "&region=" + $scope.searchObject.region + "&typeEtablissement=" + $scope.searchObject.typeEtablissement)
                    .then(
                            function (data) {
                                $scope.listEtablissements = data.listEtablissement.content;
                                $scope.listPage = data.listEtablissement;
                                $scope.pageSizes = data.pageSize;
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listEtablissement.totalElements;
                            },
                            function () {}
                    );
        };

        $scope.modeEdition = 0;

        $scope.addEtablissement = function () {
            $scope.disableCode = false;
            $scope.disable = false;
            $scope.modeEdition = 0;
            $scope.titleModale = "Ajout d'un nouvel établissement";
            $scope.objetEtablissement = angular.copy($scope.objetEtablissementMaster);
            $('#detail-etablissement').modal('show');

            // Réinitialiser Select2 après l'ouverture du modal
            setTimeout(function() {
                initializeSelect2Etablissement();
            }, 300);
        };

        $scope.saveEtablissement = function (objetEtablissement) {
            GenericService.post(saveOrUpdateEtablissementURL, angular.toJson(objetEtablissement))
                    .then(
                            function (data) {
                                $scope.objetEtablissement = {id: null, codeEtablissement: null, libelleEtablissement: null, regionEtablissement: null, typeEtablissement: null, adresse: null};
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
                                $scope.closeModalEtablissement();
                                $scope.modeEdition = 0;
                            },
                            function () {}
                    );
        };

        $scope.saveEtablissementEtContinuer = function (objetEtablissement) {
            GenericService.post(saveOrUpdateEtablissementURL, angular.toJson(objetEtablissement))
                    .then(
                            function (data) {
                                $scope.objetEtablissement = angular.copy($scope.objetEtablissementMaster);
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
                                $scope.modeEdition = 0;
                            },
                            function () {}
                    );
        };

        $scope.closeModalEtablissement = function () {
            $('#detail-etablissement').modal('hide');
        };

        $scope.editEtablissement = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un établissement";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailEtablissementURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetEtablissement = data.etablissementDTO;
                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    $scope.displayWkf = true;
                                    $('#detail-etablissement').modal('show');

                                    // Réinitialiser Select2 après l'ouverture du modal
                                    setTimeout(function() {
                                        initializeSelect2Etablissement();
                                    }, 300);
                                }
                            },
                            function () {}
                    );
        };

        $scope.deleteEtablissement = function (id) {
            if (confirm("Êtes-vous sûr de vouloir supprimer cet établissement ?")) {
                GenericService.delete(deleteEtablissementURL + "/" + id)
                        .then(
                                function (data) {
                                    $scope.listeEtablissements();
                                },
                                function () {}
                        );
            }
        };

        $scope.getListeTypeEtablissements = function () {
            GenericService.get(listTypeEtablissementURL)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.listeTypeEtablissements = data.listTypeEtablissement;
                                }
                                // Initialiser Select2 après le chargement
                                setTimeout(function() {
                                    initializeSelect2Etablissement();
                                }, 100);
                            },
                            function () {}
                    );
        };

        $scope.getListeRegions = function () {
            GenericService.get(listRegionURL)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.listeRegions = data.listRegion;
                                }
                                // Initialiser Select2 après le chargement
                                setTimeout(function() {
                                    initializeSelect2Etablissement();
                                }, 100);
                            },
                            function () {}
                    );
        };

        // Fonction pour initialiser Select2 sur les selects d'établissement
        function initializeSelect2Etablissement() {
            // Détruire les instances existantes
            $('#selectRegionEtablissement').select2('destroy');
            $('#selectTypeEtablissement').select2('destroy');

            // Initialiser Select2 pour la région
            $('#selectRegionEtablissement').select2({
                placeholder: "-- Sélectionner une région --",
                allowClear: true,
                language: "fr",
                dropdownParent: $('#detail-etablissement')
            }).on('change', function() {
                $scope.$apply(function() {
                    $scope.objetEtablissement.regionEtablissement = $('#selectRegionEtablissement').val();
                });
            });

            // Initialiser Select2 pour le type d'établissement
            $('#selectTypeEtablissement').select2({
                placeholder: "-- Sélectionner un type --",
                allowClear: true,
                language: "fr",
                dropdownParent: $('#detail-etablissement')
            }).on('change', function() {
                $scope.$apply(function() {
                    $scope.objetEtablissement.typeEtablissement = $('#selectTypeEtablissement').val();
                });
            });
        }

        $scope.getListeTypeEtablissements();
        $scope.getListeRegions();

    }]);

