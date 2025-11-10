/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

'use strict';
var App;
var appUrl;
App.controller('groupeUtilisateurController', ['$scope', 'GenericService', 'StatutService', function ($scope, GenericService, StatutService) {

        var urlDeBase = appUrl + "api/utilisateur/groupeUtilisateur";
        var listeGroupeUtilisateurURL = urlDeBase + "/listeGroupeUtilisateur";
        var saveOrUpdateGroupeUtilisateurURL = urlDeBase + "/saveOrUpdateGroupeUtilisateur";
        var saveUserGroupeURL = urlDeBase + "/saveUserGroupe";
        var deleteUtilisateurGroupeURL = urlDeBase + "/deleteUtilisateurGroupe";
        var loadUserGroupeURL = urlDeBase + "/loadUtilisateurGroupe";
        var supprimerGroupeUtilisateurURL = urlDeBase + "/deleteGroupeUtilisateur";
        var detailGroupeUtilisateurURL = urlDeBase + "/detailGroupeUtilisateur";
        var changeStatutUrl = urlDeBase + "/changeStatut";
        var chargerUtilisateurURL = urlDeBase + "/chargerUtilisateur";

        var firstPage = 1;
        var initialNumerOfElements = 5;
        $scope.pageSizeSelect;
        $scope.pageSizes = [5, 10, 20];
        $scope.sequence = [];
        $scope.selectedPageSize = null;
        $scope.paginationObject = {selectedPageSize: ""};
        $scope.pager = {startPage: "", endPage: "", totalPages: "", currentPage: "", buttonsToShow: ""};
        $scope.listePage = {number: ""};
        $scope.statut = {id: null, libelle: null, ideCodeListe: null, action: null, color: null};
//        $scope.buttonEnregistrer = null;
//        $scope.buttonAnnuler = null;
//        $scope.buttonFermer = null;
        $scope.buttonEnregistrer = false;
        $scope.buttonAnnuler = false;
        $scope.buttonFermer = false;
        $scope.hideAddButton = true;
        $scope.modeConsultation = false;
        $scope.selectedGroupeUtilisateur = null;

        $scope.groupeUtilisateur = {id: null, code: null, libelle: null, description: null, isEnSaisie: null, isActif: null, isObsolete: null, statut: null, utilisateur: [], disable: false, parent: null, communityManager: false};
        $scope.groupeUtilisateurMaster = {id: null, code: null, libelle: null, description: null, isEnSaisie: null, isActif: null, isObsolete: null, statut: null, utilisateur: [], disable: false, parent: null, communityManager: false};

//        $scope.utilisateurGroupe = {id: null, idGroupe: null, idUtilisateur: null};
//        $scope.utilisateurGroupeMaster = {id: null, idGroupe: null, idUtilisateur: null};

        $scope.groupeUtilisateurListe = [];
        $scope.utilisateurSelected = null;
        $scope.entityManagerSelected = false;
//        $scope.groupeUtilisateur.parent = $scope.groupeUtilisateur.id;
        $scope.addGroupeUtilisateur = function () {
            $scope.titre = " Ajout d'un groupe d'utilisateurs ";
            if ($scope.selectedGroupeUtilisateur != null) {
                $scope.groupeUtilisateur.parent = null;
                $scope.buttonEnregistrer = true;
                $scope.buttonAnnuler = true;
                $scope.buttonFermer = false;
                $scope.buttonSupprimer = false;
                $scope.utilisateur = [];
                $scope.displayWkf = false;
                $scope.listUtilisateurGroupe = [];
                $scope.modeConsultation = false;
                $scope.buttonModifier = false;
                $scope.hideAddButton = false;
//            $scope.groupeUtilisateur.parent = $scope.groupeUtilisateur.id;
//             $scope.groupeUtilisateur.id = $scope.selectedGroupeUtilisateur;
                $scope.groupeUtilisateur = angular.copy($scope.groupeUtilisateurMaster);
                if (!$scope.$$phase) {
                    $scope.$apply();
                }
            } else {
                $scope.hideAddButton = false;
                $scope.buttonEnregistrer = true;
                $scope.buttonAnnuler = true;
                $scope.groupeUtilisateur = angular.copy($scope.groupeUtilisateurMaster);
                if (!$scope.$$phase) {
                    $scope.$apply();
                }
            }
        };

        $scope.hideAddButtonFunction = function (lb) {
            return $scope.hideAddButton;
        };

        $scope.utilisateurGroupe = {id: null, idGroupeUtilisateur: null, idUtilisateur: null, username: null, communityManager: false};
        $scope.utilisateurGroupeMaster = {id: null, idGroupeUtilisateur: null, idUtilisateur: null, username: null, communityManager: false};

        $scope.communityManagerSelected = null;

        $scope.comMan = function () {
            $scope.listUtilisateurGroupe.forEach(function (value) {
                if (value.id != null) {
                    $scope.communityManagerSelected = value.communityManager;
                }
                jslog('DERRRRRRRR' + angular.toJson($scope.listUtilisateurGroupe));
                return $scope.communityManagerSelected;
            });
        };


        $scope.addInTabListeUtilisateur = function () {
//            $scope.utilisateurGroupe = angular.copy($scope.utilisateurGroupeMaster);
            $scope.utilisateurGroupe.idUtilisateur = $scope.utilisateurSelected;
            $scope.utilisateurGroupe.idGroupeUtilisateur = $scope.selectedGroupeUtilisateur;
//            $scope.comMan();
            $scope.utilisateurGroupe.communityManager = $scope.userIsCommunityManager;
//            $scope.utilisateurGroupe.idGroupeUtilisateur = $scope.groupeUtilisateur.id;
//            $scope.utilisateurGroupe.parent = $scope.groupeUtilisateur.id;
            jslog("USERG GROUPE " + angular.toJson($scope.utilisateurGroupe));
//            return;
            GenericService.post(saveUserGroupeURL, angular.toJson($scope.utilisateurGroupe))
                    .then(
                            function (data) {
                                $scope.groupeUtilisateur.utilisateur = [];
                                $scope.utilisateurSelected = null;
                                $scope.listUtilisateurGroupe = [];
//                                $scope.entityManagerSelected = false;
                                $scope.loadUserByFunction($scope.selectedGroupeUtilisateur);
//                                $scope.loadUserByFunction($scope.groupeUtilisateur.id);
                                $scope.utilisateurGroupe = angular.copy($scope.utilisateurGroupeMaster);
                            },
                            function (errResponse) {
                            }
                    );
        };



        $scope.changerEtat = function (idGu, index) {
            showConfirmation("changer l'état ", function () {
//                GenericService.post(changeStatutUrl + "/" + idGu)
                GenericService.post(changeStatutUrl + "/" + idGu)
                        .then(
                                function (data) {
//                                    if (data.msg.statut !== '400') {
//                                        $('#addGuser').modal('hide');
//                                    }
//                                    $scope.afficherGroupeUtilisateur();
                                    $('#groupeUser').jstree("refresh");
                                },
                                function (errResponse) {
                                }
                        );
            });
        };

        $scope.paginate = function (selectedPage, page) {
            GenericService.get(listeGroupeUtilisateurURL + "/" + selectedPage + "/" + page)
                    .then(
                            function (data) {
                                $scope.groupeUtilisateurListe = data.listeGroupeUtilisateur.content;
                                $scope.listePage = data.listeGroupeUtilisateur;
                                $scope.pageSizes = data.pageSizes;
                                $scope.sequence = data.sequence;
                            },
                            function () {
                            }
                    );
        };
        $scope.changePageSize = function () {
            GenericService.get(listeGroupeUtilisateurURL + "/" + $scope.pageSizeSelect + "/" + firstPage)
                    .then(
                            function (data) {
                                $scope.groupeUtilisateurListe = data.listeGroupeUtilisateur.content;
                                $scope.listePage = data.listeGroupeUtilisateur;
                                $scope.sequence = data.sequence;
                            },
                            function () {
                            }
                    );
        };
        $scope.afficherGroupeUtilisateur = function () {
            GenericService.get(listeGroupeUtilisateurURL + "/" + initialNumerOfElements + "/" + firstPage)
                    .then(
                            function (data) {
                                $scope.groupeUtilisateurListe = data.listeGroupeUtilisateur.content;
                                $scope.listePage = data.listeGroupeUtilisateur;
                                $scope.sequence = data.sequence;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
//                                jslog(" Liste des groupes d'utilisateurs " + angular.toJson($scope.groupeUtilisateurListe));
                            },
                            function () {
                            }
                    );
        };
        $scope.afficherGroupeUtilisateur();


        $scope.saveOrUpdateGroupeUtilisateur = function (groupeUtilisateur) {
            jslog("groupeUtilisateur " + angular.toJson(groupeUtilisateur));
            if ($scope.selectedGroupeUtilisateur != null) {
                groupeUtilisateur.parent = $scope.selectedGroupeUtilisateur;
            }
            ;
//            return ;
            GenericService.post(saveOrUpdateGroupeUtilisateurURL, angular.toJson(groupeUtilisateur))
                    .then(
                            function (data) {
                                if (data.msg.statut != '400') {
                                    $('#addGuser').modal('hide');
                                }
                                $scope.afficherGroupeUtilisateur();
                                $scope.groupeUtilisateur = angular.copy($scope.groupeUtilisateurMaster);
                                $scope.buttonEnregistrer = false;
                                $scope.buttonAnnuler = false;
                                $scope.buttonFermer = false;
                                $scope.buttonSupprimer = false;
                                $scope.buttonModifier = false;
                                $scope.hideAddButton = true;
                                $('#groupeUser').jstree("refresh");
                            },
                            function () {}
                    );
        };

        $scope.annulerGroupeUtilisateur = function () {
            if ($scope.selectedGroupeUtilisateur != null) {
                $scope.groupeUtilisateur = angular.copy($scope.groupeUtilisateurMaster);
                $scope.buttonEnregistrer = false;
                $scope.buttonAnnuler = false;
                $scope.buttonFermer = false;
                $scope.buttonModifier = true;
                $scope.hideAddButton = true;
                $scope.buttonSupprimer = true;
            } else {
                $scope.hideAddButton = true;
                $scope.buttonEnregistrer = false;
                $scope.buttonAnnuler = false;
            }
        };

//        $scope.deleteGroupeUtilisateur = function (groupeUtilisateur) {
//            if ($scope.selectedGroupeUtilisateur && $scope.selectedGroupeUtilisateur != null) {
//                showConfirmation("Supprimer", function () {
//                    GenericService.delete(supprimerGroupeUtilisateurURL + "/" + $scope.selectedGroupeUtilisateur)
//                            .then(
//                                    function (data) {
//                                        $scope.afficherGroupeUtilisateur();
//                                        $('#groupeUser').jstree("refresh");
//                                    },
//                                    function (errResponse) {
//                                    }
//                            );
//                });
//            }
//
//        };

        $scope.deleteGroupeUtilisateur = function (groupeUtilisateur) {
            if ($scope.selectedGroupeUtilisateur && $scope.selectedGroupeUtilisateur != null) {

                showConfirmation("supprimer", function () {
                    GenericService.get(supprimerGroupeUtilisateurURL + "/" + $scope.selectedGroupeUtilisateur)
                            .then(
                                    function (data) {
                                        $scope.selectedGroupeUtilisateur = null;
                                        $scope.afficherGroupeUtilisateur();
                                        $scope.groupeUtilisateur = angular.copy($scope.groupeUtilisateurMaster);
                                        $scope.buttonAnnuler = false;
                                        $scope.buttonFermer = false;
                                        $scope.buttonModifier = false;
                                        $scope.buttonSupprimer = false;
                                        $('#groupeUser').jstree("refresh");
                                    },
                                    function () {}
                            );
                }, "( Cette opération impactera les entités dérivées )");
            }
        };


        $scope.detailsGroupeUtilisateur = function () {
//            alert("detailsGroupeUtilisateur");
            $scope.modeConsultation = true;
            if ($scope.selectedGroupeUtilisateur && $scope.selectedGroupeUtilisateur != null) {
                GenericService.get(detailGroupeUtilisateurURL + "/" + $scope.selectedGroupeUtilisateur)
                        .then(
                                function (data) {
                                    $scope.titre = " Détail d'un groupe d'utilisateurs ";
                                    $scope.groupeUtilisateur = angular.copy(data.detailGroupeUtilisateur);
                                    $scope.groupeUtilisateur.disable = true;
                                    $scope.listUtilisateurGroupe = data.utilisateur;
                                    $scope.groupeUtilisateur.utilisateur = [];
                                    $scope.buttonEnregistrer = false;
                                    $scope.buttonAnnuler = false;
                                    $scope.buttonFermer = true;
                                    $scope.modeEcriture = true;
                                },
                                function (errReponse) {
                                }
                        );

            }
            ;
        };

        $scope.modeEcriture = false;

        $scope.editGroupeUtilisateur = function (groupeUtilisateur)
        {
//                    alert("editGroupeUtilisateur");
            if ($scope.selectedGroupeUtilisateur && $scope.selectedGroupeUtilisateur != null) {

                GenericService.get(detailGroupeUtilisateurURL + "/" + $scope.selectedGroupeUtilisateur)
                        .then(
                                function (data) {
                                    $scope.titre = " Edition d'un groupe d'utilisateurs";
                                    if ($scope.selectedGroupeUtilisateur != null) {
                                        $scope.groupeUtilisateur = angular.copy(data.detailGroupeUtilisateur);
                                        $scope.groupeUtilisateur = data.detailGroupeUtilisateur;
                                        $scope.listUtilisateurGroupe = data.utilisateur;
                                        $scope.groupeUtilisateur.id = $scope.selectedGroupeUtilisateur;
                                        $scope.groupeUtilisateur.disable = false;
                                        $scope.buttonAnnuler = true;
                                        $scope.buttonEnregistrer = true;
                                        $scope.buttonFermer = false;
                                        $scope.buttonModifier = false;
                                        $scope.buttonSupprimer = false;
                                        $scope.hideAddButton = false;
                                    }
                                    if ($scope.selectedGroupeUtilisateur == null) {
                                        $scope.hideAddButton = false;
                                        $scope.buttonEnregistrer = false;
                                        $scope.buttonAnnuler = true;
                                    }
                                },
                                function (errResponse) {
                                    console.error('Error while fetching Currencies' + errResponse);
                                }
                        );
            }
            ;
        };

        $scope.activerEnregistrer = function () {
            return $scope.buttonEnregistrer;
        };

        $scope.activerAnnuler = function () {
            return $scope.buttonAnnuler;
        };

        $scope.buttonSupprimer = false;

        $scope.activerButtonSupprimer = function () {
            return $scope.buttonSupprimer;
        };

        $scope.activerButtonFermer = function () {
            return $scope.buttonFermer;
        };

        $scope.buttonModifier = false;

        $scope.activerModifierButton = function () {
            return $scope.buttonModifier;
        };

        $scope.canEdit = function (gu) {
            return (gu.isEnSaisie || gu.isActif) && $scope.hideAddButton;
        };

//                $scope.displayEditButtonFunction = function (lb) {
//            return $scope.displayEditButton && lb != null && lb.isEnsaisie;
//        };

        $scope.canDelete = function (gu) {
            return gu.isEnSaisie && $scope.hideAddButton;
        };

        //************************************************************************
        $scope.displayWkf = false;

//        $scope.displayWkfGu = function (gu) {
//            return (gu.id != null) && (gu.isObsolete == false) && ($scope.displayWkf == true);
//        };
        $scope.displayWkfGu = function (gu) {
            return (gu.id != null) && (gu.isObsolete == false) && ($scope.displayWkf == true);
        };

        $scope.displayWkfGuList = function (gu) {
            return (gu.id != null) && (gu.isObsolete == false);
        };

        $scope.listeUtilisateur = [];
        $scope.loadListeUtilisateur = function () {
            GenericService.get(chargerUtilisateurURL)
                    .then(
                            function (data) {
                                $scope.listeUtilisateur = data.listeUser;
//                                jslog("LISTE DES UTILISATEURS" + angular.toJson($scope.listeUtilisateur));
                            }
                    );

        };
        $scope.loadListeUtilisateur();
        $scope.userConfig = {
            plugins: ['remove_button'],
            maxItems: 1,
            onChange: function (value, text) {
                $scope.utilisateurSelected = value;
                jslog("value /////////////" + angular.toJson(value));
            }
        };

        $scope.canEditCode = function (groupeUtilisateur) {
            return  !groupeUtilisateur.disable && groupeUtilisateur.isEnSaisie;
        };

        $scope.disableFunctionForCode = function (gu) {
            if (gu.disable) {
                return true;
            }
            if (gu.isEnSaisie) {
                return true;
            }
        };

        $scope.updateLine = function (user) {
//            user.idUtilisateur = $scope.groupeUtilisateur.utilisateur;
            user.idGroupeUtilisateur = $scope.selectedGroupeUtilisateur;
//            user.idGroupeUtilisateur = $scope.groupeUtilisateur.id;
            jslog("USER " + angular.toJson(user));
//            return ;
            GenericService.post(saveUserGroupeURL, angular.toJson(user))
                    .then(
                            function (data) {

                                $scope.loadUserByFunction($scope.selectedGroupeUtilisateur);
//                                $scope.loadUserByFunction($scope.groupeUtilisateur.id);
                                $scope.groupeUtilisateur.utilisateur = [];
                                $scope.utilisateurSelected = null;
                            },
                            function (errResponse) {
                                $scope.loadUserByFunction($scope.selectedGroupeUtilisateur);
//                                $scope.loadUserByFunction($scope.groupeUtilisateur.id);
                            }
                    );
//            $scope.listeUtilisateur.forEach(function (value) {
//                if (value.id = user.id) {
//                    $scope.groupeUtilisateur.utilisateur.splice(0, 0, angular.copy(value));
//                }
//            });
        };

        $scope.deleteUtilisateurGroupe = function (user) {
            showConfirmation("supprimer", function () {
                if (user && user.id != null) {
                    GenericService.delete(deleteUtilisateurGroupeURL + "/" + user.id)
                            .then(
                                    function (data) {
                                        $scope.loadUserByFunction($scope.selectedGroupeUtilisateur);
                                        $scope.loadUserByFunction($scope.utilisateurSelected);
                                        $scope.utilisateurGroupe = angular.copy($scope.utilisateurGroupeMaster)
                                        $scope.listUtilisateurGroupe.splice(user, 1);
//                                        $scope.loadUserByFunction($scope.groupeUtilisateur.id);
                                    },
                                    function (errResponse) {
                                    }
                            );
                }
            });
        };

        $scope.loadUserByFunction = function (id) {
            if (id && id != null) {
                GenericService.get(loadUserGroupeURL + "/" + id)
                        .then(
                                function (data) {
                                    $scope.listUtilisateurGroupe = data.utilisateur;
                                },
                                function (errResponse) {
                                }
                        );
            }
        };

        $scope.modeConsultationFunction = function () {
            return $scope.modeConsultation == true;
        };

        $('#groupeUser').jstree({
            checkbox: {
                three_state: false
            },
            'plugins': ['search', 'types', 'checkbox', 'adv_search'],
            "core": {
                "multiple": false,
                "animation": 0,
                "check_callback": true,
                "themes": {"stripes": true},
                'data': {
                    'dataType': "json",
                    'url': function (node) {
                        var url = $('#marbre_noeud').attr('href');
                        url = url + "api/utilisateur/groupeUtilisateur/loadGroupeUtilisateurs";
                        jslog("AFFICHAFE URL" + url);
                        return url;

                    },
                    'data': function (node) {
                        return {'id': node.id};
                    }
                }
            },
            "search": {
                "case_sensitive": false,
                "show_only_matches": true,
                "show_only_matches_children": true
            },
            "types": {
                "#": {
                    "max_children": 3,
                    "max_depth": 4,
                    "valid_children": ["default"]
                },
                "root": {
                    'icon': 'fa fa-folder',
                    "valid_children": ["default"]
                },
                'default': {
                    'icon': 'fa fa-folder'
                },
                'file': {
                    'icon': 'fa fa-file'
                }
            }, "html_titles": true, "load_open": true
        });
        $('#groupeUser').jstree("refresh");
        $("#groupeUser").on("select_node.jstree", function (evt, data) {
            if (data.node.id != null) {
                $scope.selectedGroupeUtilisateur = data.node.id;
                $scope.afficherGroupeUtilisateur();
                $scope.detailsGroupeUtilisateur();
                $scope.buttonModifier = true;
                $scope.buttonSupprimer = true;
                $scope.buttonEnregistrer = false;
                $scope.hideAddButton = true;
                jslog("GROUPE SELECTIONNE 01    " + $scope.selectedGroupeUtilisateur);
            }
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        });
        $("#groupeUser").on("deselect_node.jstree", function (evt, data) {
            jslog("GROUPE SELECTIONNE 02    " + $scope.selectedGroupeUtilisateur);
            $scope.selectedGroupeUtilisateur = null;
            $scope.afficherGroupeUtilisateur();
            $scope.modeConsultation = false;
            $scope.buttonModifier = false;
            $scope.buttonAnnuler = false;
            $scope.buttonSupprimer = false;
            $scope.buttonEnregistrer = false;
            $scope.hideAddButton = true;
            $scope.groupeUtilisateur = angular.copy($scope.groupeUtilisateurMaster);
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        });

        $(document).ready(function () {
            $(".search-input").keyup(function () {
                var searchString = $(this).val();
                $('#groupeUser').jstree('search', searchString);
            });
        });

        $scope.openAllNode = function () {
            $('#search-input').val("");
        };

        $("#groupeUser").bind("hover_node.jstree", function (e, data)
        {
            var element = document.getElementById(data.node.id);
            element.setAttribute('title', data.node.text);
            $scope.$apply();
        });

        $("#marbre_noeud").mouseleave(function () {
            $scope.title = "";
            $scope.$apply();
        });
    }]);
