/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
var App;

App.controller("utilisateurController", ['$scope', 'GenericService', function ($scope, GenericService) {

        const urlBase = appUrl + "gestion/utilisateur";
        const listeUtilisateurs = urlBase + "/list";
        const changerEtatURL = urlBase + "/changerEtat";
        const listeProfilsURL = urlBase + "/listeProfil";
        const listGroupesURL = urlBase + "/listeGroupe";
        const saveOrUpdateUtilisateurURL = urlBase + "/saveOrUpdateUser";
        const detailUtilisateurURL = urlBase + "/getUtilisateur";
        const getUserProfil = urlBase + "/getUserProfil";
        const deleteUtilisateurURL = urlBase + "/deleteUtilisateur";

        var listUtilisateurs = [];
    
        $scope.objetUtilisateur = {id: null, titre: null, username: null, firstName: null, lastName: null,
            email: null, profils: [], groupes: [], password: null,
            confirmPassword: "", statut: null, isEnSaisie: true, isActif: false, isObsolete: false, statutDto: null,
            disabledEdit: false, picture: null, creerUser: false, detail: false, modifierUser: false, changerMDP: false, modifierMDP: false, genererCS: false, suprimer: false, reinitialiserMDP: false, sameGpe: false, button: true};
        
        
                $scope.searchObject = {mc: '', username: '', firstName: '', lastName: '', profil: '', groupe: '', etat: ''};
        $scope.totalElements = 0;
        $scope.searchObjectMaster = {mc: ''};
        var firstPage = 1;
        $scope.initialNumerOfElements = 5;
        $scope.pageSizeSelect = 5;
        $scope.memoryPage = firstPage;
        $scope.listeDesUtilisateurs = function () {
            var idProfil = document.querySelector('#idProfil');
            var idGroupe = document.querySelector('#idGroupe');
            $scope.searchObject.profil = idProfil.options[idProfil.selectedIndex].value;
            $scope.searchObject.groupe = idGroupe.options[idGroupe.selectedIndex].value;
            jslog("Le search:"+angular.toJson($scope.searchObject));
            $scope.totalElements = 0;
            var url = listeUtilisateurs + "/" + $scope.initialNumerOfElements + "/" + firstPage;
            GenericService.get(url + "/?mc=" + $scope.searchObject.mc + "&username=" + $scope.searchObject.username
                    + "&firstName=" + $scope.searchObject.firstName + "&lastName=" + $scope.searchObject.lastName + "&profil=" + $scope.searchObject.profil + "&groupe=" + $scope.searchObject.groupe + "&etat=" + $scope.searchObject.etat)
                    .then(
                            function (data) {

                                $scope.listUtilisateurs = data.listUtilisateur.content;
                                $scope.listPage = data.listUtilisateur;
                                $scope.pageSizes = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listUtilisateur.totalElements;
                            },
                            function () {}

                    );
        };
        $scope.listeDesUtilisateurs();
        $scope.totalElements = 0;

        $scope.paginate = function (pageSizeSelect, listePage) {
            $scope.memoryPage = listePage;
            //  var url = listeProfilURLs + "/" + $scope.initialNumerOfElements + "/" + firstPage;

            var url = listeUtilisateurs + "/" + pageSizeSelect + "/" + $scope.memoryPage;

            //   if ($scope.searchMotCle && $scope.searchMotCle != null) {

            var url2 = url + "/?mc=" + $scope.searchObject.mc + "&username=" + $scope.searchObject.username
                    + "&firstName=" + $scope.searchObject.firstName + "&lastName=" + $scope.searchObject.lastName + "&profil=" + $scope.searchObject.profil + "&groupe=" + $scope.searchObject.groupe + "&etat=" + $scope.searchObject.etat;
            //     var url = listeProfilURLs + "/" + $scope.initialNumerOfElements + "/" + firstPage;
            //   "/?mc=" + $scope.searchObject.mc
            // jslog("url : "+url2);
            GenericService.get(url2)
                    .then(
                            function (data) {
                                $scope.listUtilisateurs = data.listUtilisateur.content;
                                $scope.listPage = data.listUtilisateur;
                                $scope.pageSizes = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listUtilisateur.totalElements;
                            },
                            function () {

                            }
                    );
        };

        $scope.changePageSize = function () {
            var url = listeUtilisateurs + "/" + $scope.pageSizeSelect + "/" + firstPage;


            var url = listeUtilisateurs + "/" + $scope.pageSizeSelect + "/" + firstPage;

            GenericService.get(url + "/?mc=" + $scope.searchObject.mc + "&username=" + $scope.searchObject.username
                    + "&firstName=" + $scope.searchObject.firstName + "&lastName=" + $scope.searchObject.lastName + "&profil=" + $scope.searchObject.profil + "&groupe=" + $scope.searchObject.groupe + "&etat=" + $scope.searchObject.etat)
                    .then(
                            function (data) {
                                $scope.listUtilisateurs = data.listUtilisateur.content;
                                $scope.listPage = data.listUtilisateur;
                                $scope.pageSizes = data.pageSize;
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listUtilisateur.totalElements;


                            },
                            function () {
                            }
                    );
        };

        $scope.changerEtats = function (idAction, u) {
            console.log(angular.toJson(u));
            var workflowObjectOpp = {
                idObjet: u.id,
                idAction: idAction

            };
            console.log(angular.toJson(workflowObjectOpp));
            //showConfirmation("changer l'état ", function () {
            GenericService.post(changerEtatURL, angular.toJson(workflowObjectOpp))
                    .then(
                            function (data) {
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
                                if ($scope.modeEdition === 2) {
                                    $scope.detailUtilisateurV(u.id);
                                } else if ($scope.modeEdition === 3) {
                                    $scope.editUtilisateurV(u.id);
                                }
                                console.log("aaaaaaaaaaa" + $scope.modeEdition + "--" + angular.toJson(data));

//                                if (data.msg.statut !== '400') {
//                                    $scope.detailProfil(pr.id);
//                                    $scope.objetProfil = angular.copy(data.dto);
//                                    $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
//                                }
                            },
                            function (errResponse) {

                                console.info(errResponse);


                                alert("alert");

                                //notify();
                                //$.frontValidationMessages


                            }
                    );
            //});

        };
       
        $scope.addProfil = function () {

//             document.querySelector('#un').class = "disabled";
//             document.querySelector('#un').class = "disabled";
            $scope.boutonSelect1 = "first current";
            //
            // 
            //alert("111");
            $scope.disableCode = false;
            $scope.disable = false;
            $scope.modeEdition = 0;
            $scope.titleModale = "Ajout d'un utilisateur";
            $scope.boutonSelect1 = "first current";
            $scope.boutonSelect2 = "done";
            $scope.boutonSelect3 = "done";
            $scope.boutonSelect4 = "last done";
            $('#detail-utilisateur').modal('show');
        };
        $scope.listeDesProfils = [];
        $scope.listeDesGroupes = [];
        $scope.loadProfils = function () {
            GenericService.get(listeProfilsURL)
                    .then(
                            function (data) {
                                $scope.listeDesProfils = data.listeProfils;
                                jslog("Liste profil1" + angular.toJson($scope.listeDesProfils));
                            },
                            function () {
                            }
                    );
        };
        $scope.loadGroupes = function () {
            GenericService.get(listGroupesURL)
                    .then(
                            function (data) {
                                $scope.listeDesGroupes = data.listeGroupes;
                                jslog("groupe" + angular.toJson(data.listeGroupes));

                            },
                            function () {
                            }
                    );
        };
        $scope.loadProfils();
        $scope.loadGroupes();

        $scope.saveUtilisateur = function (objetUtilisateur) {

            GenericService.post(saveOrUpdateUtilisateurURL, angular.toJson(objetUtilisateur))
                    .then(
                            function (data) {
                                //$scope.detailUtilisateur(data.utilisateur.id);
                                $scope.objetUtilisateur = {id: null, titre: null, username: null, firstName: null, lastName: null,
                                    email: null, profils: [], groupes: [], password: null,
                                    confirmPassword: "", statut: null, isEnSaisie: true, isActif: false, isObsolete: false, groupeUser: null,
                                    disabledEdit: false, photo: null, extensions: []};
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);

                                $scope.closeModalAddUser();
                                $scope.modeEdition = 0;
                            },
                            function () {}
                    );
        };

        $scope.saveUtilisateurEtContinuer = function (objetUtilisateur) {


            GenericService.post(saveOrUpdateUtilisateurURL, angular.toJson(objetUtilisateur))
                    .then(
                            function (data) {
                                //$scope.detailUtilisateur(data.utilisateur.id);
                                $scope.objetUtilisateur = {id: null, titre: null, username: null, firstName: null, lastName: null,
                                    email: null, profils: [], groupes: [], password: null,
                                    confirmPassword: "", statut: null, isEnSaisie: true, isActif: false, isObsolete: false, groupeUser: null,
                                    disabledEdit: false, photo: null, statutDt: "En saisie", extensions: []};
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);

                                $scope.modeEdition = 0;

                            },
                            function () {}
                    );
        };
        $scope.closeModalAddUser = function () {
            $('#detail-utilisateur').modal('hide');
        };

        $scope.detailUtilisateurV = function (id) {
            $scope.modeEdition = 2;
            $scope.titleModale = "Détail d'un utilisateur";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailUtilisateurURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetUtilisateur = data.utilisateurDTO;
                                    // $scope.objetProfil.disabledEdit = false;
                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    $scope.getProfilsUtilisateur(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-utilisateur').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editUtilisateurV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un utilisateur";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailUtilisateurURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetUtilisateur = data.utilisateurDTO;
                                    // $scope.objetProfil.disabledEdit = false;
                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    $scope.getProfilsUtilisateur(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-utilisateur').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };

        $scope.getProfilsUtilisateur = function (id) {
            GenericService.get(getUserProfil + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetUtilisateur.profils = data.utilisateurProfilDto.listProfil;
                                    jslog("data.utilisateurProfilDto:" + angular.toJson(data.utilisateurProfilDto.listProfil))
                                    // $scope.objetProfil.disabledEdit = false;

                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);

                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.setProfil = function (){
            alert(angular.toJson($scope.searchObject));
        }
        $scope.deleteUtilisateur = function (id) {

            var dialog = $('#myModal').modal('show');
            $('#btnYes').click(function () {
                GenericService.delete(deleteUtilisateurURL + "/" + id)
                        .then(
                                function (data) {
                                    $('#myModal').modal('hide');
                                    $scope.listeDesUtilisateurs();

                                },
                                function () {
                                }
                        );
            });
            $('#btnNo').click(function () {
                //dialog.dialog('close');
            });
        };
    }]);