/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


'use strict';
var App;

App.controller("patientController", ['$scope', 'GenericService','PropagationService','$location','$rootScope', function ($scope, GenericService,PropagationService,$location,$rootScope) {

        const urlBase = appUrl + "api/patient";
        const listePatients = urlBase + "/paginatePatient";
        const listePatientsURL = urlBase + "/listAllPatient";
        const saveOrUpdatePatientURL = urlBase + "/saveOrUpdatePatient";
        const detailPatientURL = urlBase + "/getPatient";
        const deletePatientURL = urlBase + "/deletePatient";
        const listSexeURL = urlBase + "/listSexe";
        const listGroupeSanguinURL = urlBase + "/listGroupeSanguin";
        const getNextNumeroCarnetURL = urlBase + "/getNextNumeroCarnet";

        var listPatients = [];
        $scope.listeSexes = [];
        $scope.listeGroupesSanguins = [];
        $scope.objetPatient = {id: null, username: null, firstName: null, lastName: null, sexe: null, dateNaissance: null, email: null, tel: null, titre: null, numeroCarnet: null, groupeSanguin: null, dateEnregistrement: null, password: null, confirmPassword: null};
        $scope.objetPatientMaster = {id: null, username: null, firstName: null, lastName: null, sexe: null, dateNaissance: null, email: null, tel: null, titre: null, numeroCarnet: null, groupeSanguin: null, dateEnregistrement: null, password: null, confirmPassword: null};

        // Date maximale pour la date de naissance (aujourd'hui)
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0');
        var yyyy = today.getFullYear();
        $scope.maxDate = yyyy + '-' + mm + '-' + dd;
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

            // Charger le prochain numéro de carnet
            $scope.loadNextNumeroCarnet();

            // Initialiser la date d'enregistrement avec la date/heure actuelle
            $scope.objetPatient.dateEnregistrement = new Date();

            $('#detail-patient').modal('show');
        };

        $scope.savePatient = function (objetPatient) {
            // Convertir les dates si nécessaire
            if (objetPatient.dateEnregistrement) {
                objetPatient.dateEnregistrement = new Date(objetPatient.dateEnregistrement).toISOString();
            }
            if (objetPatient.dateNaissance) {
                objetPatient.dateNaissance = new Date(objetPatient.dateNaissance).toISOString();
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
            // Convertir les dates si nécessaire
            if (objetPatient.dateEnregistrement) {
                objetPatient.dateEnregistrement = new Date(objetPatient.dateEnregistrement).toISOString();
            }
            if (objetPatient.dateNaissance) {
                objetPatient.dateNaissance = new Date(objetPatient.dateNaissance).toISOString();
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

                                    // Convertir les dates en objets Date pour AngularJS
                                    if ($scope.objetPatient.dateEnregistrement) {
                                        $scope.objetPatient.dateEnregistrement = new Date($scope.objetPatient.dateEnregistrement);
                                    }
                                    if ($scope.objetPatient.dateNaissance) {
                                        $scope.objetPatient.dateNaissance = new Date($scope.objetPatient.dateNaissance);
                                    }

                                    $scope.getListeGroupesSanguins();
                                    $scope.getListeSexes();
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

                                    // Convertir les dates en objets Date pour AngularJS
                                    if ($scope.objetPatient.dateEnregistrement) {
                                        $scope.objetPatient.dateEnregistrement = new Date($scope.objetPatient.dateEnregistrement);
                                    }
                                    if ($scope.objetPatient.dateNaissance) {
                                        $scope.objetPatient.dateNaissance = new Date($scope.objetPatient.dateNaissance);
                                    }

                                    $scope.getListeGroupesSanguins();
                                    $scope.getListeSexes();

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
        
        $scope.consulterPatient = function (patient) {
            console.log('🏥 Ouverture de la consultation pour le patient:', patient);

            // Charger les détails complets du patient avant de naviguer
            GenericService.get(detailPatientURL + "?id=" + patient.id)
                .then(
                    function (data) {
                        if (data && data.patient) {
                            var patientComplet = data.patient;

                            // Convertir les dates en objets Date pour l'affichage
                            if (patientComplet.dateEnregistrement) {
                                patientComplet.dateEnregistrement = new Date(patientComplet.dateEnregistrement);
                            }
                            if (patientComplet.dateNaissance) {
                                patientComplet.dateNaissance = new Date(patientComplet.dateNaissance);
                            }

                            console.log('✅ Détails complets du patient chargés:', patientComplet);
                            console.log('   - ID:', patientComplet.id);
                            console.log('   - Nom:', patientComplet.firstName, patientComplet.lastName);
                            console.log('   - N° Carnet:', patientComplet.numeroCarnet);
                            console.log('   - Groupe sanguin:', patientComplet.groupeSanguinLibelle);

                            // Enregistrer le patient dans le service de propagation
                            PropagationService.setPatientSender(patientComplet);

                            // Naviguer vers la page de consultation
                            window.location.href = 'consultation';
                        } else {
                            console.error('❌ Erreur: Données patient non trouvées');
                            alert('Erreur lors du chargement des informations du patient');
                        }
                    },
                    function (error) {
                        console.error('❌ Erreur lors du chargement du patient:', error);
                        alert('Erreur lors du chargement des informations du patient');
                    }
                );
        };

        $scope.voirAntecedents = function (patient) {
            console.log('📋 Ouverture des antécédents pour le patient:', patient);

            // Charger les détails complets du patient avant de naviguer
            GenericService.get(detailPatientURL + "?id=" + patient.id)
                .then(
                    function (data) {
                        if (data && data.patient) {
                            var patientComplet = data.patient;

                            // Convertir les dates en objets Date pour l'affichage
                            if (patientComplet.dateEnregistrement) {
                                patientComplet.dateEnregistrement = new Date(patientComplet.dateEnregistrement);
                            }
                            if (patientComplet.dateNaissance) {
                                patientComplet.dateNaissance = new Date(patientComplet.dateNaissance);
                            }

                            console.log('✅ Détails complets du patient chargés:', patientComplet);
                            console.log('   - ID:', patientComplet.id);
                            console.log('   - Nom:', patientComplet.firstName, patientComplet.lastName);
                            console.log('   - N° Carnet:', patientComplet.numeroCarnet);

                            // Enregistrer le patient dans le service de propagation
                            PropagationService.setPatientSender(patientComplet);

                            // Naviguer vers la page de gestion des antécédents
                            window.location.href = 'antecedent';
                        } else {
                            console.error('❌ Erreur: Données patient non trouvées');
                            alert('Erreur lors du chargement des informations du patient');
                        }
                    },
                    function (error) {
                        console.error('❌ Erreur lors du chargement du patient:', error);
                        alert('Erreur lors du chargement des informations du patient');
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

        // Charger la liste des sexes depuis le backend
        $scope.getListeSexes = function () {
            GenericService.get(listSexeURL)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.listeSexes = data.listSexe;
                                    jslog("listeSexes:" + $scope.listeSexes);

                                }
                            },
                            function () {
                            }
                    );
        };
        $scope.getListeSexes();

        // Charger la liste des groupes sanguins depuis le backend
        $scope.getListeGroupesSanguins = function () {
            GenericService.get(listGroupeSanguinURL)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.listeGroupesSanguins = data.listGroupeSanguin;
                                    jslog("listeGroupesSanguins:" + $scope.listeGroupesSanguins);
                                }
                            },
                            function () {
                            }
                    );
        };
        $scope.getListeGroupesSanguins();

        // Charger le prochain numéro de carnet disponible
        $scope.loadNextNumeroCarnet = function () {
            GenericService.get(getNextNumeroCarnetURL)
                    .then(
                            function (data) {
                                if (data && data.nextNumeroCarnet) {
                                    $scope.objetPatient.numeroCarnet = data.nextNumeroCarnet;
                                }
                            },
                            function () {
                            }
                    );
        };

    }]);

