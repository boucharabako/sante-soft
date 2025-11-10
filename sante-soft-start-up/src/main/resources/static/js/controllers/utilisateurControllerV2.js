/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global angular, Json, logger, firstPage, listeUserConnectedURL, firstpage */
'use strict';
var App;
var appUrl;
//App = angular.module('app',['multiselectcomp']);
App.controller("utilisateurControllerV2", ['$scope', 'GenericService', function ($scope, GenericService) {

        const urlBase1 = appUrl + "api/utilisateursV2";
        const supprimerUserURL = urlBase1 + "/deleteUtilisateur";
        const listProfilsURL = urlBase1 + "/listeProfil";
        const listProfilsURLModifier = urlBase1 + "/listeProfilModifier";
        const urlListe = appUrl + "parametre/utilisateurnew";
//        const extensionNumberArbreURL = urlBase1 + "/extensionNumberArbre";
//        const extensionsUserURL = urlBase1 + "/extensionUtilisateur";
        const listGroupesURL = urlBase1 + "/listeGroupe";
        const listGroupesListeURL = urlBase1 + "/listeToutGroupe";
        const listGroupesURLModifier = urlBase1 + "/listeGroupeModifier";
        const saveOrUpdateUtilisateurURL = urlBase1 + "/saveOrUpdateUtilisateur";
        const detailUtilisateurUrl = urlBase1 + "/detailUtilisateurListe";
        
        const deleteUtilisateurUrl = urlBase1 + "/deleteUtilisateur";
        const urlBase = appUrl + "parametre/utilisateur";
        const listeUtilisateurURL = urlBase1 + "/listeUtilisateur";
        const changerEtatURL = urlBase1 + "/changerEtat";
        //const detailUtilisateurURL = urlBase1 + "/detailUtilisateur";
        const generateSecretCodeURL = urlBase1 + "/generateSecretCode";
        const sendReinitializeMailURL = urlBase1 + "/sendReinitMailV2";
        const forcedPwdURL = urlBase1 + "/forcedPwd";
        const listeFonctionnaliteURL = urlBase1 + "/listeFonctionnalite";
        const listeProfilSansAdminURL = urlBase1 +"/listeProfilSansAdmin";
        const changePasswordURL = urlBase1+"/changePassword";
        const listeProfilsURL = urlBase1 + "/listeProfil";
        $scope.objetUtilisateur = {id: null, titre: null, username: null, firstName: null, lastName: null,
            email: null, profils: [], groupes: [], password: null,
            confirmPassword: "", statut: null, isEnSaisie: true, isActif: false, isObsolete: false, groupeUser: null,
            disabledEdit: false, photo: null, statutDt: "En saisie", extensions: [], creerUser: false, detail: false, modifierUser: false, changerMDP: false, modifierMDP: false, genererCS: false, suprimer: false, reinitialiserMDP: false, sameGpe: false, button: true};
        $scope.selectedUser = null;
        $scope.searchMotCle = null;
        $scope.searchMotCleStatut = null;
        $scope.listeObjetUsers = [];
        //$scope.searchObject = {mc: ''};//, profil: ''};
        $scope.searchObjectMaster = {mc: ''};
        $scope.displayWkf = false;
        $scope.listeObjetUtilisateur = [];
        $scope.listeDesProfils = [];
        $scope.profils = [];
        $scope.listeDesGroupes = [];
        $scope.listeDesExtensions = [];
        $scope.selectedUtilisateur = null;
        $scope.listeFonctionnalite = [];
        $scope.searchMotCle = null;
        $scope.searchObject = {profil: '', groupe: '', username: '', firstName: '', fonction: '', statut: ''};
        $scope.searchObjectMaster = {mc: ''};
        var firstPage = 1;
        $scope.initialNumerOfElements = 10;
        $scope.pageSizeSelect = 10;
        $scope.memoryPage = firstPage;
        $scope.pageSize = [];
        $scope.sequence = [];
        $scope.pageSizes = [10, 20, 50, 100];
        $scope.selectedPageSize = null;
        $scope.paginationObject = {selectedPageSize: ""};
        $scope.pager = {startPage: "", endPage: "", totalPages: "", currentPage: "", buttonsToShow: ""};
        $scope.listPage = {number: ""};
        $scope.password1 = null;
        $scope.password2 = null;

        $scope.displaySaveButton = true;
        $scope.displayCancelButton = true;
        $scope.displayExitButton = false;
        $scope.displayWkf = false;
        $scope.displayPassword = true;
        $scope.displayUsername = true;
        $scope.displayGroupeProfil = true;

        $scope.listeDesUtilisateurs = function () {
           $scope.paginate($scope.initialNumerOfElements, firstPage);
            $scope.changesPageSizes();
        };

        /*$scope.listeDesUtilisateurs = function () {
         $scope.searchObject = angular.copy($scope.searchObjectMaster);
         $scope.searchObject = angular.copy($scope.searchObjectMaster);
         if ($scope.searchMotCle !== null) {
         $scope.searchObject.mc = $scope.searchMotCle;
         
         }
         if ($scope.searchMotCleStatut !== null) {
         $scope.searchObject.mc = $scope.searchMotCleStatut;
         
         }
         var url = listeUtilisateurURL + "/" + $scope.initialNumerOfElements + "/" + firstPage;
         GenericService.get(url + "?mc=" + $scope.searchObject.mc)
         .then(
         function (data) {
         
         $scope.listeObjetUtilisateur = data.listUser.content;//listeUser
         $scope.listPage = data.listUser;
         $scope.pageSize = data.pageSize;
         $scope.pageSizeSelect = $scope.pageSize[0];
         $scope.sequence = data.sequence;
         
         jslog("LISTE EMPLACEMENT = " + angular.toJson($scope.searchObject.mc));
         },
         function () {}
         );
         };
         $scope.listeDesUtilisateurs();*/

//        $scope.changesPageSizes = function () {
//            var url = listeUtilisateurURL + "/" + $scope.pageSizeSelect + "/" + firstPage;
//
//            GenericService.get(url)
//                    .then(
//                            function (data) {
//                                $scope.listeObjetUtilisateur = data.listUser.content;
//                                $scope.listPage = data.listUser;
//                                $scope.pageSize = data.pageSize;
//                                //  $scope.pageSizeSelect = $scope.pageSize[0];
//                                $scope.sequence = data.sequence;
//                            },
//                            function () {
//                            }
//                    );
//        };

        $scope.changesPageSizes = function () {
            // var url = listeUtilisateurURL + "/" + $scope.pageSizeSelect + "/" + firstPage;
            // $scope.memoryPage = listePage;
            var url = listeUtilisateurURL + "/" + $scope.pageSizeSelect + "/" + firstPage;
            
            //            var url2 = listeUserConnectedURL + "/" + $scope.pageSizeSelect + "/" + firstPage;
            if ($scope.searchObject.username && $scope.searchObject.username !== null && $scope.searchObject.username !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'username=' + $scope.searchObject.username;
            }
            if ($scope.searchObject.profil && $scope.searchObject.profil !== null && $scope.searchObject.profil !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'profil=' + $scope.searchObject.profil;
            }
            if ($scope.searchObject.fonction && $scope.searchObject.fonction !== null && $scope.searchObject.fonction !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'fonction=' + $scope.searchObject.fonction;
            }
            if ($scope.searchObject.groupe && $scope.searchObject.groupe !== null && $scope.searchObject.groupe !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'groupe=' + $scope.searchObject.groupe;
            }
            
            if ($scope.searchObject.firstName && $scope.searchObject.firstName !== null && $scope.searchObject.firstName !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'firstName=' + $scope.searchObject.firstName;
            }
            if ($scope.searchObject.statut && $scope.searchObject.statut !== null && $scope.searchObject.statut !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'statut=' + $scope.searchObject.statut;
            }
            jslog(url);
            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeObjetUtilisateur = data.listUser.content;
                                $scope.listPage = data.listUser;
                                $scope.pageSize = data.pageSize;
                                //  $scope.pageSizeSelect = $scope.pageSize[0];
                                $scope.sequence = data.sequence;
                              //  jslog("La liste des utilisateurs :"+angular.toJson(data.listUser.content));
                               // jslog("utilisateur :"+angular.toJson(data.listUser));
                            },
                            function () {
                            }
                    );
        };
//        $scope.paginate = function (pageSizeSelect, listePage) {
//            $scope.memoryPage = listePage;
//            var url = listeUtilisateurURL + "/" + pageSizeSelect + "/" + $scope.memoryPage;
//            GenericService.get(url)
//                    .then(
//                            function (data) {
//                                $scope.listeObjetUtilisateur = data.listUser.content;
//                                $scope.listPage = data.listUser;
//                                $scope.pageSize = data.pageSize;
//                                $scope.sequence = data.sequence;
//                            },
//                            function () {
//
//                            }
//                    );
//        };

        $scope.paginate = function (pageSizeSelect, listePage) {
            $scope.memoryPage = listePage;
            var url = listeUtilisateurURL + "/" + pageSizeSelect + "/" + $scope.memoryPage;
            // var url = listeUtilisateurURL + "/" + $scope.pageSizeSelect + "/" + firstPage;
//            var url2 = listeUserConnectedURL + "/" + $scope.pageSizeSelect + "/" + firstPage;
            if ($scope.searchObject.username && $scope.searchObject.username !== null && $scope.searchObject.username !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'username=' + $scope.searchObject.username;
            }
            if ($scope.searchObject.profil && $scope.searchObject.profil !== null && $scope.searchObject.profil !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'profil=' + $scope.searchObject.profil;
            }
            if ($scope.searchObject.fonction && $scope.searchObject.fonction !== null && $scope.searchObject.fonction !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'fonction=' + $scope.searchObject.fonction;
            }
            if ($scope.searchObject.groupe && $scope.searchObject.groupe !== null && $scope.searchObject.groupe !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'groupe=' + $scope.searchObject.groupe;
            }
            
            if ($scope.searchObject.firstName && $scope.searchObject.firstName !== null && $scope.searchObject.firstName !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'firstName=' + $scope.searchObject.firstName;
            }
            if ($scope.searchObject.statut && $scope.searchObject.statut !== null && $scope.searchObject.statut !== 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'statut=' + $scope.searchObject.statut;
            }
            jslog("mot cle = " + angular.toJson(url));
            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeObjetUtilisateur = data.listUser.content;
                                $scope.listPage = data.listUser;
                                $scope.pageSize = data.pageSize;
                                $scope.sequence = data.sequence;
                            },
                            function () {
                            }
                    );
        };
        $scope.changerStatut = function (id, index) {
            showConfirmation("changer l'état ", function () {
                GenericService.post(changerEtatURL + "/" + id)
                        .then(
                                function (data) {
                                    //  $scope.detailTypeArticle(id);
                                    $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
                                },
                                function (errResponse) {

                                }
                        );
            });

        };

        $scope.listeDesUtilisateurs();
//        $scope.detailUtilisateur = function (id) {
//            if (id !== null && id !== undefined) {
//                GenericService.get(detailUtilisateurURL + "/" + id)
//                        .then(
//                                function (data) {
//                                    if (data) {
//                                        // $scope.titleModale = "Détail d'un type d'article";
//                                        $scope.objetUtilisateur = data.detailUtilisateur;
//                                        $scope.displayPassword = false;
//                                        $scope.displayUsername = false;
//                                        $scope.displayGroupeProfil = false;
//                                        /* $scope.objetTypeArticle.disabledEdit = true;
//                                         $scope.displaySaveButton = false;
//                                         $scope.displayCancelButton = false;
//                                         $scope.displayExitButton = true;
//                                         $scope.displayWkf = true;*/
////                                      $scope.getClassification();
//                                    }
//                                },
//                                function () {
//                                }
//                        );
//            }
//        };

        $scope.generateSecretCode = function (id) {
            GenericService.get(generateSecretCodeURL + "/" + id)
                    .then(
                            function (data) {

                            },
                            function (errResponse) {

                            }
                    );


        };

        $scope.sendReinitializeMail = function (id) {
            //  showConfirmation("supprimer", function () {
            if (id !== null && id !== undefined) {
                GenericService.get(sendReinitializeMailURL + "/" + id)
                        .then(
                                function (data) {
                                    if (data) {
                                    }
                                },
                                function () {
                                }
                        );
            }
            // });
        };

       /* $scope.sendReinitializeMail = function (id) {
            //  showConfirmation("supprimer", function () {
            if (id !== null && id !== undefined) {
                GenericService.post(forcedPwdURL + "/" + id)
                        .then(
                                function (data) {
                                    if (data) {
                                    }
                                },
                                function () {
                                }
                        );
            }
            // });
        };*/
        $scope.loadProfils = function () {
            GenericService.get(listeProfilSansAdminURL)
                    .then(
                            function (data) {
                                $scope.profils = data.listeProfilSansAdmin;
                                jslog("Liste profil"+angular.toJson($scope.profils));
                            },
                            function () {
                            }
                    );
        };
        $scope.loadProfilsModifier = function (id) {
            GenericService.get(listProfilsURLModifier + "/" + id)
                    .then(
                            function (data) {
                                $scope.listeDesProfils = data.listeProfils;
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
        
        $scope.loadGroupesListe = function () {
            GenericService.get(listGroupesListeURL)
                    .then(
                            function (data) {
                                $scope.listeDesGroupes = data.listeGroupes;
                                jslog("groupe" + angular.toJson(data.listeGroupes));

                            },
                            function () {
                            }
                    );
        };

        $scope.loadGroupesModifier = function (id) {
            GenericService.get(listGroupesURLModifier + "/" + id)
                    .then(
                            function (data) {

                                $scope.listeDesGroupes = data.listeGroupes;


                            },
                            function () {
                            }
                    );
        };
        $scope.saveUtilisateur = function (objetUtilisateur) {

            objetUtilisateur.extensions = $scope.extensionsInterfacesList;
            GenericService.post(saveOrUpdateUtilisateurURL, angular.toJson(objetUtilisateur))
                    .then(
                            function (data) {
                                //$scope.detailUtilisateur(data.utilisateur.id);
                                $scope.objetUtilisateur = {id: null, titre: null, username: null, firstName: null, lastName: null,
                                    email: null, profils: [], groupes: [], password: null,
                                    confirmPassword: "", statut: null, isEnSaisie: true, isActif: false, isObsolete: false, groupeUser: null,
                                    disabledEdit: false, photo: null, extensions: []};
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
                                $scope.getExtensionsInterface();
                                $scope.closeModalAddUser();
                            },
                            function () {}
                    );
        };

        $scope.saveUtilisateurEtContinuer = function (objetUtilisateur) {

            objetUtilisateur.extensions = $scope.extensionsInterfacesList;
            GenericService.post(saveOrUpdateUtilisateurURL, angular.toJson(objetUtilisateur))
                    .then(
                            function (data) {
                                //$scope.detailUtilisateur(data.utilisateur.id);
                                $scope.objetUtilisateur = {id: null, titre: null, username: null, firstName: null, lastName: null,
                                    email: null, profils: [], groupes: [], password: null,
                                    confirmPassword: "", statut: null, isEnSaisie: true, isActif: false, isObsolete: false, groupeUser: null,
                                    disabledEdit: false, photo: null, statutDt: "En saisie", extensions: []};
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
                                $scope.getExtensionsInterface();

                            },
                            function () {}
                    );
        };


        $scope.modeDetailV = false;
        $scope.modeDetail = function () {

            return $scope.modeDetailV;
        }
        $scope.editU = function (id) {
            if (id !== null && id !== undefined) {
                GenericService.get(detailUtilisateurUrl + "/" + id)
                        .then(
                                function (data) {
                                    if (data) {
                                        $scope.objetUtilisateur = data.detailUtilisateur;
                                        $scope.displaySaveButton = true;
                                        $scope.displayCancelButton = true;
                                        $scope.displayExitButton = false;
                                        $scope.displayWkf = false;
                                        $scope.loadProfils();
                                        $scope.loadGroupes();
//                                         $scope.getClassification();
                                    }
                                },
                                function () {
                                }
                        );
            }
        };
        $scope.editUtilisateur = function (id) {
            if (id !== null && id !== undefined) {
                $scope.op = "1";
                GenericService.get(detailUtilisateurUrl + "/" + id + "/" + $scope.op)
                        .then(
                                function (data) {
                                    if (data) {

                                        $scope.objetUtilisateur = data.detailUtilisateur;
                                        $scope.extensionsInterfacesList = data.extensions;
                                       // jslog("vvvvvvvvvvvvvvvvvvvv"+angular.toJson($scope.extensionsInterfacesList));
                                        for (var i = 0; i < $scope.numberArbre; i++) {
                                            $("#entiteorgid-" + i).jstree("destroy").empty();
                                        }
                                        if ($scope.objetUtilisateur.isActif === true)
                                            $scope.objetUtilisateur.statutDt = "Actif";
                                        if ($scope.objetUtilisateur.isEnSaisie === true)
                                            $scope.objetUtilisateur.statutDt = "En saisie";
                                        if ($scope.objetUtilisateur.isObsolete === true)
                                            $scope.objetUtilisateur.statutDt = "En Obselete";
                                        $scope.tree();
//                                        $scope.getExtensionsInterface();
                                       
                                        //$scope.objetUtilisateur.extensions = data.extensions;
                                        $scope.displayCancelButton = true;
                                        $scope.displayExitButton = false;
                                        $scope.displayWkf = false;
                                        $scope.displayPassword = false;
                                        $scope.displayUsername = false;
                                        $scope.displayGroupeProfil = true;
                                        $scope.displayUpdateButton = true;
                                        $scope.displaySaveButton = false;
                                        $scope.modeDetailV = false;
                                        $scope.loadProfils();
                                        $scope.loadGroupes();

                                        window.location = appUrl + "parametre/utilisateur/edit_user_new_1_1";
//                                         $scope.getClassification();
                                    }
                                },
                                function () {
                                }
                        );
            }
        };
        $scope.item = null;
        $scope.beforeSelectItem = function (item) {
            // perform operation on this item before selecting it.
            $scope.item = item;
            jslog("beforeSelectItem: " + angular.toJson(item));
        };

        $scope.afterSelectItem = function (item) {
            // perform operation on this item after selecting it.
            $scope.item = item;
            jslog("afterSelectItem" + angular.toJson(item));
        };

        $scope.beforeRemoveItem = function (item) {
            // perform operation on this item before removing it.
            $scope.item = item;
            jslog("beforeRemoveItem" + angular.toJson(item));
        };

        $scope.afterRemoveItem = function (item) {
            // perform operation on this item after removing it.
            $scope.item = item;
            jslog("afterRemoveItem" + angular.toJson(item));
        };
        $scope.canNotSwich = function (listeEspDoc) {
            $scope.canBeSwitch = false;
            if (listeEspDoc.length > 1) {
                $scope.canBeSwitch = true;
                //$scope.espaceDefautObject = false;
            }
            return  $scope.canBeSwitch;
        };

        $scope.detailUtilisateur = function (id) {
            if (id !== null && id !== undefined) {
                $scope.op = "2";
                GenericService.get(detailUtilisateurUrl + "/" + id + "/" + $scope.op)
                        .then(
                                function (data) {
                                    if (data) {
                                        $scope.objetUtilisateur = data.detailUtilisateur;
                                        $scope.objetUtilisateur.extensions = data.extensions;
                                        
                                        if ($scope.objetUtilisateur.isActif === true)
                                            $scope.objetUtilisateur.statutDt = "Actif";
                                        if ($scope.objetUtilisateur.isEnSaisie === true)
                                            $scope.objetUtilisateur.statutDt = "En saisie";
                                        if ($scope.objetUtilisateur.isObsolete === true)
                                            $scope.objetUtilisateur.statutDt = "En Obselete";
                                        $scope.extensionsInterfacesList = data.extensions;
                                        for (var i = 0; i < $scope.numberArbre; i++) {
                                            $("#entiteorgid-" + i).jstree("destroy").empty();
                                        }
                                        $scope.tree();
                                        $scope.displaySaveButton = false;
                                        $scope.displayCancelButton = false;
                                        $scope.displayExitButton = true;
                                        $scope.displayWkf = true;
                                        $scope.displayPassword = false;
                                        $scope.displayUsername = false;
                                        $scope.displayGroupeProfil = false;
                                        $scope.loadProfils();
                                        $scope.loadGroupes();
                                        $scope.modeDetailV = true;
                                        $scope.displayUpdateButton = false;
                                        window.location.href = appUrl+"parametre/utilisateur/edit_user_new_1_1";
//                                         $scope.getClassification();
                                    }
                                },
                                function () {
                                }
                        );
            }
        };
        $scope.changerMotDePasse = function (id) {
            if (id !== null && id !== undefined) {
                $scope.op = "4";
                GenericService.get(detailUtilisateurUrl + "/" + id + "/" + $scope.op)
                        .then(
                                function (data) {
                                    if (data) {
                                        
                                        //window.location.href = appUrl+"parametre/utilisateur/reset_password_new";
                                        window.location = appUrl+"parametre/utilisateur/reset_password_new";
//                                         $scope.getClassification();
                                    }
                                },
                                function () {
                                }
                        );
            }
        };
        $scope.senderValeur = function(){
            
        GenericService.get(urlBase1 + "/detailUtilisateur")
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetUtilisateur = data.detailUtilisateur;
                                   
                                }
                            },
                            function () {
                            }
                    );
        
        };
        $scope.senderValeur();
        $scope.quitterChangePassword = function(){
            window.location = appUrl + "parametre/utilisateur";
        }
        $scope.changePassword = function(){
            
        GenericService.get(urlBase1 + "/detailUtilisateur")
                    .then(
                            function (data) {
                                    //$scope.objetUtilisateur = data.detailUtilisateur;
                                    $scope.executer();
                                    
                                
                            },
                            function () {
                            }
                    );
        
        };
        $scope.executer = function () {
            GenericService.post(changePasswordURL, angular.toJson($scope.objetUtilisateur))
                                        .then(
                                                function (data) {
                                                  window.location = appUrl + "parametre/utilisateur";
                                                },
                                                function () {
                                                }
                                        );
        }
        $scope.deleteUtilisateurFunction = function (id) {
            showConfirmation("supprimer", function () {
                if (id != null && id != undefined) {
                    GenericService.delete(deleteUtilisateurUrl + "/" + id)
                            .then(
                                    function (data) {
                                        if (data) {
                                            $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
                                        }
                                    },
                                    function () {
                                    }
                            );
                }
            });
        };
        $scope.displayGroupeProfils = function () {
            return $scope.displayGroupeProfil;
        }
        $scope.displayWkfU = function (user) {
            return (user.id !== null) && (user.isObsolete === false) && ($scope.displayWkf === true);
        };
        $scope.displayWkfTaList = function (user) {
            return (user.id !== null) && (user.isObsolete === false);
        };
        
        $scope.displayCreerU = function (){
            
        }

        $scope.displayDeleteU = function (user) {
         //   jslog("user delete = " + angular.toJson(user));
            return (user != null && user != "undefined" && user.isEnSaisie != "undefined" && user.statutDto.libelle === "En saisie") && (user.suprimer);
        };
        
        $scope.displayUpdateU = function (user){
         //   jslog("user update = " + angular.toJson(user));
            return ((user !== null && user !== "undefined" && user.statutDto.libelle !== "Obsolete") && (user.modifierUser));
        };
        $scope.displayDetailU = function (user){
        //    jslog("user detail = " + angular.toJson(user));
            return user !== null && user !== "undefined" && user.statutDto.libelle !== "Obsolete" && user.detail;
        };
        
        $scope.displaySaveU = function () {
            return $scope.displaySaveButton;
        };
        $scope.displayUpdateButton = false;
        $scope.displayUpdateUs = function () {
            return $scope.displayUpdateButton;
        };


        $scope.displayCancelU = function () {
            return $scope.displayCancelButton;
        };
        $scope.displayUsernames = function () {
            return $scope.displayUsername;
        };
        $scope.displayPasswords = function () {
            return $scope.displayPassword;
        };
        $scope.displayExitU = function () {
            return $scope.displayExitButton;
        };

        $scope.conceptMetier = $('#conceptMetier').val();
        $scope.extensionsInterfacesList = [];
        var urlDeBaseUtils = appUrl + "api/utilisateursV2";
        var getExtensionsInterface = urlDeBaseUtils + "/extension-interface-arbre";
        $scope.getExtensionsInterface = function () {
            jslog("+++++++++++++++++++$scope.conceptMetier: " + angular.toJson($scope.conceptMetier));
            GenericService.get(getExtensionsInterface + "/" + $scope.conceptMetier)
                    .then(
                            function (data) {
                                $scope.extensionsInterfacesList = data.extensionInterfaces;
                                $scope.numberArbre = data.numberArbre;
                                jslog("la liste des extensions Interfaces: " + angular.toJson($scope.extensionsInterfacesList));
                                jslog("la liste des extensions Interfaces 2: " + angular.toJson($scope.numberArbre));
                                for (var i = 0; i < $scope.numberArbre; i++) {
                                    $("#entiteorgid-" + i).jstree("destroy").empty();
                                }
                                $scope.tree();
                            },
                            function (errResponse) {
                                jslog("Erreur : ");
                            }
                    );
        };

        $scope.tree = function () {

            for (var i = 0; i < $scope.numberArbre; i++) {

                var imple = $('#entiteorgid-impl-' + i).val();

                var old = $('#entiteorgid-input-' + i).val();


                jslog("ENTREZ DANS LA BOUCLE1 ==================================================" + angular.toJson($('#entiteorgid-impl-' + i).val()));
                var labelId = "#entiteorgid-label-" + i;
                var inputId = "#entiteorgid-input-" + i;
                var index = "#entiteorgid-input-" + i;
                var idendifiant = "#entiteorgid-" + i;
                $(idendifiant).on(" deselect_node.jstree ", function (evt, data) {
//                            jslog("DESELECT ==============================");
                    $scope.extensionsInterfacesList[$(index).attr('index-attr')].valeur = null;
                    $(inputId).val("");
                    $(labelId).text("");
                    console.info(inputId);
                    console.info(labelId);

                });
                $(idendifiant).on("select_node.jstree ",
                        function (evt, data) {
                            jslog("SELECT ==============================" + angular.toJson(data.node.id));
                            $(inputId).val(data.node.id);
                            $(labelId).text(data.node.text);

                            $scope.extensionsInterfacesList[$(index).attr('index-attr')].valeur = data.node.id;
                            console.info("#entiteorgid-label-" + i);
                            console.info(inputId);
                            console.info("#entiteorgid-impl-" + i);
                            jslog("ENTREZ DANS LA BOUCLE IMPLE ==================================================" + angular.toJson($(inputId).val()));
                            jslog("SELECT IMPLE ==============================" + angular.toJson($scope.extensionsInterfacesList));
                        }
                );
                $(idendifiant).jstree({
                    checkbox: {
                        three_state: false
                    },
                    "core": {
                        "multiple": false,
                        "animation": 0,
                        "check_callback": true,
                        "themes": {"stripes": true},
                        'data': {
                            'dataType': "json",
                            'url': function (node) {

                                return  urlDeBaseUtils + '/findExt';
                            },
                            'data': function (node) {
                                return {'id': node.id, 'imple': imple, 'old': $scope.extensionsInterfacesList[$(index).attr('index-attr')].valeur ? $scope.extensionsInterfacesList[$(index).attr('index-attr')].valeur : ''};
                            }
                        }
                    },
                    "types": {
                        "#": {
                            "max_children": 3,
                            "max_depth": 4,
                            "valid_children": ["default"],
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
                    }, "html_titles": true, "load_open": true,
                    "plugins": [
                        "search", "types", "checkbox"
                    ]
                });
            }

        };

        $scope.displayListExtension = false;
        $scope.getType = function (typeDonnee) {
            if (typeDonnee == undefined || typeDonnee === 'X') {
                return "text";
            }
            if (typeDonnee === 'N') {
                return "number";
            }
            if (typeDonnee === 'D') {
                return "date";
            }
            if (typeDonnee === 'A') {
                return "text";
            }
        };
        $scope.displayInput = function (ext) {
            var value = false;
            if (ext.extensionUtilisateur.implentation == null || ext.extensionUtilisateur.implementation == "") {
                value = true;
            }
            jslog("input : " + angular.toJson(value));
            return value;
        };

        $scope.displayListControle = function (ext) {
            var value = false;
//                     jslog("ENTREEEEE 1 " + angular.toJson(ext.extension.implentation));
            if (ext.extensionUtilisateur.implentation != null && ext.extensionUtilisateur.composant == "LIST") {
                value = true;
//                        jslog("ENTREEEEE 2");
            }
//                    jslog("la liste: " + angular.toJson(ext.extension.composant));
            jslog("la liste: " + angular.toJson(value));
            return value;
        };
        $scope.displayArbre = function (ext) {
            var value = false;
            if (ext.extensionUtilisateur.implentation != null && ext.extensionUtilisateur.composant == "ARBRE") {
                value = true;
            }
            jslog("l'ARBRE: " + angular.toJson(value));
            return value;
        };


//=============================== METHODE DE SUPPRESSION BY AMINA

        $scope.supprimerUtilisateur = function (id) {
            showConfirmation("Supprimer", function () {
                GenericService.delete(supprimerUserURL + "/" + id)
                        .then(
                                function (data) {
                                    if (data) {
                                        $scope.listeDesUtilisateurs();
                                    }
                                },
                                function () {
                                }
                        );
            });
        };
        //====================JEREMIE===============
       /* $scope.changesPageSizes = function () {
            var url = listeUtilisateurURL + "/" + $scope.pageSizeSelect + "/" + firstPage;
            //  var url2 = listeUserConnectedURL + "/" + $scope.pageSizeSelect + "/" + firstPage;
            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeObjetUtilisateur = data.listUser.content;
                                $scope.listPage = data.listUser;
                                $scope.pageSize = data.pageSize;
                                //  $scope.pageSizeSelect = $scope.pageSize[0];
                                $scope.sequence = data.sequence;
                            },
                            function () {
                            }
                    );
        };*/


        $scope.changerStatut = function (id, index) {
            showConfirmation("changer l'état ", function () {
                GenericService.post(changerEtatURL + "/" + id)
                        .then(
                                function (data) {
                                    //  $scope.detailTypeArticle(id);
                                    $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
                                },
                                function (errResponse) {

                                }
                        );
            });

        };
//        $scope.detailUser = function (id) {
//            if (id !== null && id !== undefined) {
//                GenericService.get(detailUtilisateurURL + "/" + id)
//                        .then(
//                                function (data) {
//                                    if (data) {
//                                        // $scope.titleModale = "Détail d'un type d'article";
//                                        $scope.objetUtilisateur = data.detailUtilisateur;
//                                        /* $scope.objetTypeArticle.disabledEdit = true;
//                                         $scope.displaySaveButton = false;
//                                         $scope.displayCancelButton = false;
//                                         $scope.displayExitButton = true;
//                                         $scope.displayWkf = true;*/
////                                      $scope.getClassification();
//                                    }
//                                },
//                                function () {
//                                }
//                        );
//            }
//        };

       /* $scope.generateSecretCode = function (id) {
            GenericService.get(generateSecretCodeURL + "/" + id)
                    .then(
                            function (data) {

                            },
                            function (errResponse) {

                            }
                    );


        };*/

       /* $scope.sendReinitializeMail = function (id) {
            //  showConfirmation("supprimer", function () {
            if (id !== null && id !== undefined) {
                GenericService.get(sendReinitializeMailURL + "/" + id)
                        .then(
                                function (data) {

                                },
                                function () {
                                }
                        );
            }
            // });
        };*/

        $scope.forcedPwd = function (id) {
            //  showConfirmation("supprimer", function () {
            if (id !== null && id !== undefined) {
                GenericService.get(forcedPwdURL + "/" + id)
                        .then(
                                function (data) {

                                },
                                function () {
                                }
                        );
            }
            // });
        };

        //====================
        $scope.addNew = function () {

//            $scope.displaySaveButton = true;
//            $scope.displayCancelButton = true;
//            $scope.displayExitButton = false;
//            $scope.displayWkf = false;
//
//            $scope.objetUtilisateur = {id: null, titre: null, username: null, firstName: null, lastName: null,
//                email: null, profils: [], groupes: [], password: null,
//                confirmPassword: "", statut: null, isEnSaisie: true, isActif: false, isObsolete: false, groupeUser: null,
//                disabledEdit: false, photo: null, statutDt: "En saisie", extensions: []};
//
//
////            $scope.loadProfils();
////            $scope.loadGroupes();
//            $scope.displayPassword = true;
//            $scope.displayUsername = true;
//            $scope.displayGroupeProfil = true;
//            $scope.getExtensionsInterface();
//            $scope.modeDetailV = false;
//            $scope.displayUpdateButton = true;

            $scope.op = "3";
            GenericService.get(detailUtilisateurUrl + "/" + $scope.op)
                    .then(
                            function (data) {
                                if (data) {
                                   window.location = appUrl+"parametre/utilisateur/edit_user_new_1_1";
                                   
                                }
                            },
                            function () {
                            }
                    );
        };
        $scope.loadFonction = function () {
            GenericService.get(listeFonctionnaliteURL)
                    .then(
                            function (data) {
                                $scope.listeFonctionnalite = data.fonctions;
                                jslog("LISTE des Fonctions = " + angular.toJson(data.fonctions));
                            },
                            function () {
                            }
                    );
        };
        
        
        $scope.getExtensionsInterface();
        //$scope.addNew();
        $scope.loadProfils();
        $scope.loadGroupesListe();
        $scope.loadFonction();
        

    }]);    