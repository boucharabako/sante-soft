///* 
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///* global angular, Json, logger, firstPage, listProfilURL, firstpage */
//
//'use strict';
//var App;
//var appUrl;
//App.controller("profilController", ['$scope', 'GenericService', function ($scope, GenericService) {
//        const urlBase = appUrl + "api/profil";
//        const getValeurIdProfilURL = urlBase +"/valeurIdProfil";
//        const getIdprofilsURL = urlBase +"/idProfils";
//        const getIdProfilURL = urlBase + "/idProfil";
//        const saveOrUpdateProfilURL = urlBase + "/saveOrUpdateProfil";
//        const detailProfilURL = urlBase + "/detailProfil";
//        const deleteProfilURL = urlBase + "/deleteProfil";
//        const editProfilURL = urlBase + "/editProfil";
//        const editHabilitationFonct = urlBase + "/upDateHabilitationFonc";
//        const changerEtatURL = urlBase + "/changerEtat";
//        const listeFonctionnaliteURL = urlBase + "/listeFonctionnalite";
//        const listeHabilitationURL = appUrl + "api/sec/parametre/fonction";
//        const saveHabiliFoncURL = urlBase + "/saveHabilitationFonc";
//        const listeProfilURL = urlBase + "/listeProfil";
//        const listeProfilURLs = urlBase + "/listeProfils";
//        const listeHabiliFonct = urlBase + "/listeHabiliteFonctionnelle";
//        const deleteHabiliFonctURL = urlBase + "/deleteHabilitationFonc";
//        const detailHabiliFonctURL = urlBase + "/detailHabiliFonct";
//        const listeHabiliFonctionnelleURL = urlBase + "/listeHabiliFonctionnelle";
//
//        /*$scope.list = true;*/
//        $scope.pagi =true;
//        $scope.disab = false;
//        $scope.searchObject = {profil: ''};
//        $scope.objetFonctionnalite = {id: null, code: null};
//        $scope.objetProfils= {id: null, code: null};
//        $scope.objetHabilitation = {id: null, code: null};
//        $scope.objetHabilitationFonctionnelle = {id: null, idProfil: null, idFonction: null, libelleFonction: null, habilitation: null, libelleHabilitation: null, etat: false};
//         $scope.idProfil ={id:null};
//        $scope.objetHabiFoncMaster = {id: null, idProfil: null, libelleFonction: null, idFonction: null, habilitation: null, libelleHabilitation: null};
//        $scope.objetProfil = {id: null, code: null, intitule: null, statut: null, disabledEdit: false, flgProfil : "5",
//            isActif: false, isEnSaisie: true, isObsolete: false, concepteMetier: null, classification: []};
//        $scope.objetMaster = {id: null, code: null, intitule: null, statut: null,
//            isActif: false, isObsolete: false, isEnSaisie: true, concepteMetier: null, classification: []};
//        $scope.titleModale;
//        // variable de la pagination
//        $scope.listeObjetProfil = [];
//        $scope.listeObjetFonctionnalite = [];
//        $scope.listeObjetProfils = [];
//        $scope.listeObjetHabilitations = [];
//        $scope.listeObjetHabilitationFonc = [];
//        $scope.listeObjetMaster = [];
//        $scope.listeVide = [];
//        $scope.listeObjetHabilitationFonctionnelle = [];
//        // pagination profil
//        var firstPage = 1;
//        $scope.initialNumerOfElements = 10;
//        $scope.pageSizeSelect = 10;
//        $scope.memoryPage = firstPage;
//        $scope.pageSize = [];
//        $scope.sequence = [];
//        $scope.selectedPageSize = null;
//        $scope.paginationObject = {selectedPageSize: ""};
//        $scope.pager = {startPage: "", endPage: "", totalPages: "", currentPage: "", buttonsToShow: ""};
//        $scope.listPage = {number: ""};
//        // pagination habilitation fonctionnelle
//        var firstPages = 1;
//        $scope.initialNumerOfElementss = 10;
//        $scope.pageSizeSelects = 10;
//        $scope.memoryPages = firstPages;
//        $scope.pageSizes = [];
//        $scope.sequences = [];
//        $scope.selectedPageSizes = null;
//        $scope.paginationObjects = {selectedPageSizes: ""};
//        $scope.listePages = {number: ""};
//        //
//        $scope.disabDelete = false;
//        $scope.disabledCode = false;
//        $scope.disabledLibelle = false;
//        $scope.dsblBtn = false;
//        $scope.displaySaveButton = true;
//        $scope.displayCancelButton = true;
//        $scope.displayExitButton = false;
//        $scope.displayWkf = false;
//        $scope.saveShow = true;
//        $scope.closeShow = true;
//        $scope.modalClose = true;
//        $scope.ligne = false;
//        $scope.val = false;
//        //
//        $scope.deleteShow = true;
//        $scope.editShow = true;
//        $scope.detailShow = true;
//        $scope.showActive = true;
//        $scope.etatCombo = true;
//        $scope.tableau = true ;
//        $scope.tableHabilitation = true;
//        $scope.sta = true;
//        $scope.titre = true;
//        
//        $scope.selectedProfil = null;
//        $scope.searchMotCle = null;
//       // $scope.searchObject = {mc: '', profil: ''};
//      //  $scope.searchObjectMaster = {mc: '', profil: ''};
//        $scope.searchObject = {mc: ''};//, profil: ''};
//        $scope.searchObjectMaster = {mc: ''};//, profil: ''};
//
//        $scope.addNew = function () {
//            $scope.titleModale = "Ajout d'un profil";
//          //  $scope.pagi = false;
//            $scope.disabledCode = false;
//            $scope.disabledLibelle = false;
//            $scope.saveShow = true;
//            $scope.closeShow = true;
//            $scope.modalClose = false;
//            $scope.listeObjetProfil = [];
//            $scope.objetMaster.concepteMetier = "En Saisie";
//            $scope.memoryPage = firstPage;
//            $scope.objetProfil.id = null;
//            $scope.objetProfil.code = null;
//            $scope.objetProfil.intitule = null;
//            $scope.displaySaveButton = true;
//            $scope.displayCancelButton = true;
//            $scope.displayExitButton = false;
//            $scope.displayWkf = false;
//            $scope.objetProfil.isActif = false;
//            $scope.listeDesProfil();
//            $scope.objetHabilitationFonctionnelle.idFonction = null;
//            $scope.objetHabilitationFonctionnelle.habilitation = null;
//            $scope.listeHabilitationFonctionnelles(null);
//            $scope.tableau = false;
//            $scope.tableHabilitation = false;
//            $scope.sta =false;
//            $scope.titre = false;
//            $scope.statutDto = true;
//            $scope.objetProfil.statutDto.libelle ="En Saisie";
//            $scope.pagination="Ajout d'un profil";
//            $scope.listeObjetHabilitationFonctionnelle = [];
//           // $scope.list = false;
//           $scope.ligne = false;
//         
//        };
//        $scope.afficherActiveButton = function () {
//            if ($scope.objetProfil.statutDto.libelle === "Obsolete") {
//                $scope.showActive = false;
//            }
//        };
//            $scope.saveProfil = function (objetProfil) {
//           // alert(objetProfil);
//            $scope.modDetail = false;
//            $scope.test = false;
//            $scope.statutDto = true;
//            $scope.objetMaster.code = $scope.objetProfil.code;
//            $scope.objetMaster.intitule = $scope.objetProfil.intitule;
//            $scope.objetMaster.statut = $scope.objetProfil.statut;
//            if($scope.titleModale !=='Edition d\'un profil'){
//                GenericService.post(saveOrUpdateProfilURL, angular.toJson($scope.objetProfil))
//                    .then(
//                            function (data) {
//                              if (data.dto) {
//                                $scope.objetProfil = angular.copy(data.dto);
//                                $scope.listeDesProfil();
//                                $scope.tableau = true;
//                                $scope.tableHabilitation = true;
//                                $scope.objetProfil.id = $scope.getIdProfil($scope.objetProfil.code);
//                              //  $scope.detailProfil($scope.objetProfil.id);
//                                $scope.editerProfil($scope.objetProfil.id);
//                                $scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
//                                $scope.displayWkf = true;
//                                $scope.vide();
//                                $scope.listeDesFonctionnalite();
//                                $scope.videHabilitation();
//                                 if ($scope.objetProfil.statutDto.libelle==="Obsolete"){
//								$scope.sta = false;      
//								$scope.titre = false;
//                                  }
//                                  }
//                               $scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
//                                
//                            },
//                            function () {
//                            }
//                    );
//            } else {
//                GenericService.post(saveOrUpdateProfilURL, angular.toJson($scope.objetProfil))
//                    .then(
//                            function () {
//                             /* if (data.dto) {
//                                $scope.objetProfil = angular.copy(data.dto);
//                                $scope.listeDesProfil();
//                                $scope.tableau = true;
//                                $scope.tableHabilitation = true;
//                                $scope.objetProfil.id = $scope.getIdProfil($scope.objetProfil.code);
//                                $scope.detailProfil($scope.objetProfil.id);
//                                $scope.displayWkf = true;
//                                $scope.vide();
//                                $scope.listeDesFonctionnalite();
//                                $scope.videHabilitation();
//                                 if ($scope.objetProfil.statutDto.libelle==="Obsolete"){
//								$scope.sta = false;      
//								$scope.titre = false;
//                              }
//                                  }*/
//                               $scope.listeDesProfil();
//                            },
//                            function () {
//                            }
//                    );
//                
//            }
//            
//            objetProfil.code = null;
//            objetProfil.intitule = null;
//            if ($scope.test === false) {
//                objetProfil.code = $scope.objetMaster.code;
//                objetProfil.intitule = $scope.objetMaster.intitule;
//            }
//         
//            // $scope.vide();
//              if ($scope.objetProfil.statutDto.libelle==="En saisie"){
//                         $scope.sta = true;      
//                         $scope.titre = true;
//                }
//            $scope.listeHabilitationFonctionnelles($scope.objetProfil.id);    
//            $scope.listeDesFonctionnalite();
//            $scope.videHabilitation();
//             $scope.tableau = true;
//              $scope.tableHabilitation = true;
//            
//            };
//      
//        $scope.listeDesProfil = function () {
//          
//             $scope.paginate($scope.initialNumerOfElements, firstPage);
//             $scope.changePageSize();
//             $scope.searchObject = angular.copy($scope.searchObjectMaster);
//            $scope.searchObject = angular.copy($scope.searchObjectMaster);
//                if ($scope.searchMotCle !== null) {
//                 $scope.searchObject.mc = $scope.searchMotCle;
//              }
//             
//            $scope.profil;
//            var url = listeProfilURLs + "/" + $scope.initialNumerOfElements + "/" + firstPage;
//              GenericService.get(url + "/?mc=" + $scope.searchObject.mc)
//                    .then(
//                            function (data) {                                              
//                                $scope.listeObjetProfil = data.listeProfils.content;
//                                $scope.listPage = data.listeProfils;
//                                $scope.pageSize = data.pageSize;
//                                $scope.pageSizeSelect = $scope.pageSize[0];
//                                $scope.sequence = data.sequence;
//                                jslog("LISTE EMPLACEMENT = " + angular.toJson($scope.listeEmplacements));
//                            },
//                            function () {}
//                
//                );
//    
//    };
//        
//        //$scope.listeDesProfil();
//
//        $scope.listeHabilitationFonctionnelles = function (idProfil) {
//           GenericService.get(listeHabiliFonctionnelleURL+"/"+idProfil+"/"+$scope.initialNumerOfElementss + "/" + firstPages)    
//                    .then(
//                            function (data) {
//                                $scope.listeObjetHabilitationFonctionnelle = data.listeHabilitationFonctionnelles.content;                                                                                              
//                                $scope.listPages = data.listeHabilitationFonctionnelles;
//                                $scope.pageSizes = data.listeHabilitationFonctionnelles;
//                                $scope.pageSizeSelects = $scope.pageSize[0];
//                                $scope.sequences = data.sequences;
//                                $scope.changePageHabiliSize();
//                            },
//                            function () {}
//                    );
//        };
//
//
//        $scope.listeDesFonctionnalite = function () {
//            var url = listeFonctionnaliteURL;
//            GenericService.get(url)
//                    .then(
//                            function (data) {
//                                $scope.listeObjetFonctionnalite = data.fonctions;
//                               // alert(angular.toJson($scope.listeObjetFonctionnalite));
//                               // jslog(angular.toJson($scope.listeObjetFonctionnalite));
//                            },
//                            function () {}
//                    );
//
//        };
//        $scope.listeDesFonctionnalite();
//
//        $scope.listeDesHabilations = function (id) {
//            var url = listeHabilitationURL;
//            GenericService.get(url + "/" + id)
//                    .then(
//                            function (data) {
//                                $scope.listeObjetHabilitations = data;
//                    
//                            }
//                    );
//        };
//        $scope.getIdProfil = function(code){
//            var url = getIdProfilURL;
//            GenericService.get(url+"/"+code)
//                    .then(
//                        function(data){
//                        $scope.idProfil = data;
//                        $scope.objetProfil.id = $scope.idProfil.id.toString();
//                        }
//                    );
//        };
//        $scope.getValeurIdProfil = function(code){
//            var url = getValeurIdProfilURL;
//            GenericService.get(url+"/"+code)
//                    .then(
//                            function(data){
//                            $scope.valeurProfil = data;
//                            alert(angular.toJson($scope.valeurProfil));  
//                            
//                            });
//        };
//        
//        $scope.getIdprofils = function(code){
//            var url = getIdprofilsURL;
//            GenericService.get(url+"/"+code)
//                    .then(
//                        function(data){
//                            $scope.listeObjetProfils = data.idProfils;
//                            alert(angular.toJson($scope.listeObjetProfils));
//                         //   jslog("Liste des id :" + angular.toJson(data.idProfils));
//                            
//              }
//                    );
//        };
//        
//        
//        $scope.afficheButtonSave = false;
//        $scope.listeHabiByFonction = function (idFonction, index) {
//
//            $scope.listeObjetHabilitationFonctionnelle[index].edit = '1';
//            $scope.liste = [];
//
//            var url = listeHabilitationURL;
//            GenericService.get(appUrl + "api/sec/parametre/fonction/" + idFonction)
//                    .then(
//                            function (data) {
//                                if (data) {
//
//                                    $scope.listeObjetHabilitationFonc = data;
//                                    $scope.listeObjetMaster[index] = data;
//                                    $scope.objetHabiFoncMaster = data;
//                                    $scope.listeObjetHabilitationFonctionnelle[index].listeHabilitation = data;
//                                    $scope.listeObjetHabilitationFonctionnelle[index].etat = true;
//
//                                 //   jslog("Liste  :" + angular.toJson(data.habilitations));
//                                }
//                            },
//                            function () {}
//                    );
//
//        };
//        
//           $scope.check = function(flg) {
//               if(flg === "5" || flg === null || flg === undefined ){
//                  document.getElementById("myCheck").checked = false; 
//               }else{
//                   document.getElementById("myCheck").checked = true;
//               }
//               
//           };
//        
//         
//
//            $scope.detailProfil = function (id){
//            $scope.titleModale = "Détail d'un profil";
//            $('#idProfile').val($scope.objetProfil.id);
//            
//          //  $scope.pagi =true;
//            $scope.saveShow = false;
//            $scope.closeShow = false;
//            $scope.modalClose = true;
//            $scope.disabledCode = true;
//            $scope.disabledLibelle = true;
//            $scope.displaySaveButton = false;
//            $scope.displayCancelButton = false;
//            $scope.displayExitButton = true;
//            $scope.vide();
//            $scope.disab = true;
//            $scope.disabDelete = true;
//            $scope.listeDesFonctionnalite();
//            $scope.listeDesHabilations(null);
//            if (id !== null && id !== undefined) {
//                GenericService.get(detailProfilURL + "/" + id)
//
//                        .then(
//                                function (data){
//                                    if (data){   
//                                        $scope.objetProfil = data.detailProfil;                                        
//                                        $scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;                                   
//                                        $('#idProfile').val($scope.objetProfil.id);
//                                        $scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
//                                        $scope.listeDesFonctionnalite();
//                                        $scope.listeDesHabilations(null);
//                                        $scope.displayWkf = true;
//                                        $scope.ligne = false;
//                                       }
//                                  if($scope.objetProfil.statutDto.libelle==="Obsolete"){
//                                        $scope.objetHabilitationFonctionnelle.idProfil = null;
//                                        $scope.sta = false;
//                                        $scope.titre = true;
//                                         $scope.tableau =false;
//                                         $scope.tableHabilitation =false;
//                                         $scope.ligne = false;
//                                         $scope.list = false;
//                                           } else if($scope.objetProfil.statutDto.libelle==="Actif"){
//                                               $scope.sta = true;      
//                                                $scope.titre = true;
//                                               $scope.tableau = true;
//                                               $scope.tableHabilitation = true;
//                                               $scope.ligne = true;
//                                                $scope.list = true;
//                                           } if ($scope.objetProfil.statutDto.libelle==="En saisie"){
//                                               $scope.sta = true;      
//                                               $scope.titre = true;
//                                               $scope.tableau = true;
//                                               $scope.tableHabilitation = true;
//                                               $scope.ligne = true;
//                                                $scope.list = true;
//                                           }else{
//                                            
//                                           } 
//                                           if($scope.objetProfil.statutDto.libelle===''){
//                                               $scope.sta = true;      
//                                               $scope.titre = true;
//                                               $scope.tableau = true;
//                                               $scope.tableHabilitation = true;
//                                           }
//                                               
//                                     if($scope.objetProfil.statutDto.libelle==="Actif"){
//                                         $scope.sta = true;      
//                                         $scope.titre = true;
//                                     }
//                                      if($scope.objetProfil.statutDto.libelle==="En saisie"){
//                                         $scope.sta = true;      
//                                         $scope.titre = true;
//                                     }
//                                     
//                                    /* if($scope.objetProfil.flgProfil === "4"){
//                                         $scope.check();
//                                     }
//                                     if($scope.objetProfil.flgProfil ==="5" || $scope.objetProfil.flgProfil === null || $scope.objetProfil.flgProfil == undefined){
//                                         
//                                     }*/
//                                    $scope.check($scope.objetProfil.flgProfil);
//                                     jslog("liste des profils : "+angular.toJson($scope.objetProfil));
//                                },
//                                function () {
//                                }
//                                        
//                        );
//                        $scope.listeHabilitationFonctionnelles($scope.objetHabilitationFonctionnelle.idProfil);
//                        $scope.listeDesFonctionnalite();
//                        $scope.listeDesHabilations(null);
//            }
//            
//
//        };
//
//     
//        $scope.deleteProfil = function (id) {
//            showConfirmation("Supprimer", function () {
//                GenericService.delete(deleteProfilURL + "/" + id)
//                        .then(
//                                function (data) {
//                                    if (data) {
//                                        $scope.listeDesProfil();
//                                    }
//                                },
//                                function () {
//                                }
//                        );
//            });
//        };
//
//
//        $scope.disabledTaLibFieldFunction = function (p) {
//            if ($scope.titleModale === "Edition d'un Profil") {
//                $scope.objetProfil.disabledEdit = true;
//            }
//        };
//
//        $scope.disabledTaFieldFunctionForCode = function (p) {
//
//        };
//
//        $scope.changePageSize = function () {
//            var url = listeProfilURLs + "/" + $scope.pageSizeSelect + "/" + firstPage;
//           //  var url = listeProfilURLs + "/" + $scope.initialNumerOfElements + "/" + firstPage;
//
//           // var url = listeProfilURLs + "/" + pageSizeSelect + "/" + $scope.memoryPage;
//           $scope.searchObject = angular.copy($scope.searchObjectMaster);
//            $scope.searchObject = angular.copy($scope.searchObjectMaster);
//            //   if ($scope.searchMotCle && $scope.searchMotCle != null) {
//                if ($scope.searchMotCle !== null) {
//                 $scope.searchObject.mc = $scope.searchMotCle;
//              }
//            var url = listeProfilURLs + "/" + $scope.pageSizeSelect + "/" + firstPage;
//            GenericService.get(url  + "/?mc=" + $scope.searchObject.mc)
//                    .then(
//                            function (data) {
//                                $scope.listeObjetProfil = data.listeProfils.content;
//                                $scope.listPage = data.listeProfils;
//                                $scope.pageSize = data.pageSize;
//                                $scope.sequence = data.sequence;
//
//
//                            },
//                            function () {
//                            }
//                    );
//        };
//
//
//        $scope.paginate = function (pageSizeSelect, listePage) {
//            $scope.memoryPage = listePage;
//          //  var url = listeProfilURLs + "/" + $scope.initialNumerOfElements + "/" + firstPage;
//
//            var url = listeProfilURLs + "/" + pageSizeSelect + "/" + $scope.memoryPage;
//           $scope.searchObject = angular.copy($scope.searchObjectMaster);
//            $scope.searchObject = angular.copy($scope.searchObjectMaster);
//            //   if ($scope.searchMotCle && $scope.searchMotCle != null) {
//                if ($scope.searchMotCle !== null) {
//                 $scope.searchObject.mc = $scope.searchMotCle;
//              }
//              var url2 = url + "/?mc=" + $scope.searchObject.mc;
// //     var url = listeProfilURLs + "/" + $scope.initialNumerOfElements + "/" + firstPage;
//         //   "/?mc=" + $scope.searchObject.mc
//          // jslog("url : "+url2);
//            GenericService.get(url2)
//                    .then(
//                            function (data) {
//                                $scope.listeObjetProfil = data.listeProfils.content;
//                                $scope.listPage = data.listeProfils;
//                                $scope.pageSize = data.pageSize;
//                                $scope.sequence = data.sequence;
//                            },
//                            function () {
//
//                            }
//                    );
//        };
//
//        $scope.changerEtats = function (idObjet, idAction) {
//            showConfirmation("changer l'état ", function () {
//                GenericService.post(changerEtatURL + "/" + idObjet+"/"+idAction)
//                        .then(
//                                function (data) {
//                                    if (data.msg.statut !== '400') {
//                                    $scope.detailProfil(id);
//                                     $scope.objetProfil= angular.copy(data.dto);
//                                    $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
//                                    }
//                                },
//                                function (errResponse) {
//
//                                }
//                        );
//            });
//
//        };
//
//        $scope.displaySaveTa = function () {
//            return $scope.displaySaveButton;
//        };
//        $scope.displayCancelTa = function () {
//            return $scope.displayCancelButton;
//        };
//        $scope.displayExitTa = function () {
//            return $scope.displayExitButton;
//        };
//
//        //===========================================
//        $scope.displayWkfTa = function (profil) {
//
//            return (profil.id !== null) && (profil.isObsolete === false) && ($scope.displayWkf === true);
//        };
//        $scope.displayWkfTaList = function (profil) {
//            return (profil.id !== null) && (profil.isObsolete === false);
//        };
////=====================================
//
//
//        $scope.disabledTaFieldFunction = function (pr) {
//            if (pr.isActif) {
//                return true;
//            } else {
//                return pr.disabledEdit;
//            }
//        };
//        $scope.disabledTaFieldFunctionForCode = function (pr) {
//            if (pr.isActif) {
//                return true;
//            }
//            return pr.disabledEdit;
//        };
//
//      
//
//        $scope.getCodeByHabilitationFoncton = function (habi) {
//
//            alert(JSON.stringify(habi));
//
//        };
//        $scope.videHabilitation = function(){
//            $scope.listeObjetHabilitationFonctionnelle = [];
//            objetHabilitationFonctionnelle = null;
//        };
//        
//        
//        $scope.vide = function (){            
//            $scope.objetHabilitationFonctionnelle.idFonction = null;
//            $scope.objetHabilitationFonctionnelle.habilitation = null;                      
//        };
//
//        $scope.saveHabilitationFonctionnelle = function (objetHabilitationFonctionnelle) {
//            $scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;            
//            $scope.objetHabiFoncMaster.idProfil = $scope.objetHabilitationFonctionnelle.idProfil;
//            $scope.objetHabiFoncMaster.idFonction = $scope.objetHabilitationFonctionnelle.idFonction;
//            $scope.objetHabiFoncMaster.habilitation = $scope.objetHabilitationFonctionnelle.habilitation;
//
//            GenericService.post(saveHabiliFoncURL, angular.toJson(objetHabilitationFonctionnelle))
//                    .then(
//                            function () {
//                                $scope.listeHabilitationFonctionnelles($scope.objetHabilitationFonctionnelle.idProfil);
//                                //$scope.videHabilitation(objetHabilitationFonctionnelle);
//                                $scope.listeDesFonctionnalite();
//                                $scope.listeDesHabilations();
//                                $scope.vide();
//                            },
//                            function () {
//                                        }     
//                                  
//                    );
//                    
//                   
//        };
// 
//        $scope.deleteHabilitationFonctionnelle = function (idHabiliFonc) {
//            showConfirmation("Supprimer", function () {
//                GenericService.delete(deleteHabiliFonctURL + "/" + idHabiliFonc)
//                        .then(
//                                function (data) {
//                                    if (data) {
//                                        $scope.listeHabilitationFonctionnelles($scope.objetHabilitationFonctionnelle.idProfil);
//                                        $scope.listeDesFonctionnalite();
//                                        $scope.listeDesHabilations(null);
//                                        $scope.vide();
//                                    }
//                                },
//                                function () {
//                                }
//                        );
//                   $scope.vide();
//                   $scope.listeDesFonctionnalite();
//                   $scope.listeDesHabilations(null);
//            });
//        };
//
//
//
//        $scope.upDateHabilitationFonctionnelle = function (objet) {
//
//            if (objet !== null && objet !== undefined) {
//                GenericService.post(editHabilitationFonct, angular.toJson(objet))
//                        .then(
//                                function (data) {
//                                    if (data) {
//                                        $scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
//                                        $scope.listeDesFonctionnalite();
//                                         $scope.listeDesHabilations(null);
//                                    }
//                                },
//                                function () {
//                                }
//                        );
//                   $scope.vide();
//                   $scope.listeDesFonctionnalite();
//                   $scope.listeDesHabilations(null);
//            }
//
//        };
//          /*  $scope.paginate = function (pageSizeSelect, listePage) {
//            $scope.memoryPage = listePage;
//            var url = listeProfilURLs + "/" + pageSizeSelect + "/" + $scope.memoryPage;
//            GenericService.get(url)
//                    .then(
//                            function (data) {
//                                $scope.listeObjetProfil = data.listeProfils.content;
//                                $scope.listPage = data.listeProfils;
//                                $scope.pageSize = data.pageSize;
//                                $scope.sequence = data.sequence;
//                            },
//                            function () {
//
//                            }
//                    );
//        };*/
//        
//        $scope.listeDesProfil();
//
//        $scope.paginerHabi = function (idProfil,pageSizeSelects, listePages) {
//      //   $scope.paginerHabi = function (pageSizeSelects, listePages) {
//            $scope.memoryPages = listePages;
//
//            var url = listeHabiliFonctionnelleURL +"/"+ idProfil+"/" + pageSizeSelects + "/" + $scope.memoryPages;
//            GenericService.get(url)
//           // GenericService.get()
//                    .then(
//                            function (data) {
//                                $scope.listeObjetHabilitationFonctionnelle = data.listeHabilitationFonctionnelles.content;
//                                $scope.listePages = data.listeHabilitationFonctionnelles;
//                                $scope.pageSizes = data.pageSizes;
//                                $scope.sequences = data.sequences;
//                            },
//                            function () {
//
//                            }
//                    );
//        };
//
//        $scope.changePageHabiliSize = function () {
//            var url = listeHabiliFonctionnelleURL +"/"+$scope.objetHabilitationFonctionnelle.idProfil+"/"+ $scope.pageSizeSelects + "/" + firstPages;
//            GenericService.get(url)
//                    .then(
//                            function (data) {
//                                $scope.listeObjetHabilitationFonctionnelle = data.listeHabilitationFonctionnelles.content;
//                                $scope.listePages = data.listeHabilitationFonctionnelles;
//                                $scope.pageSizes = data.pageSizes;
//                               // $scope.pageSizeSelects = $scope.pageSizes[0];
//                                $scope.sequences = data.sequences;
//                            },
//                            function () {
//                            }
//                    );
//        };
//
//
//        $scope.showButtonSave = function (h) {
//            return h.etat === true;
//        };
//
//        $scope.showButtonEdit = function (h) {
//            return h.etat === false;
//        };
//        
//        $scope.showButtonDelete = function(h){
//            return h.etat === false;
//            
//        };
//        $scope.showModif = function(){
//            
//            
//        };
//        
//        $scope.viderEdit = function(){
//            $scope.objetProfil.code = null;
//            $scope.objetProfil.intitule = null;
//            
//        };
//        
//      $scope.resetComboListe = function(objetProfil){
//           $scope.vide();           
//           $scope.listeDesFonctionnalite();
//           $scope.listeDesHabilations(null);
//           return objetProfil;
//      };
//        
//        
//        //================================================
//        $scope.displayEditTa = function (profil) {
//            return (profil.isEnSaisie == true || profil.isActif == true);
//        };
//        $scope.displayDeleteTa = function (profil) {
//            return profil.isEnSaisie == true;
//        };
// 
//        
//        $scope.displaySaveTa = function () {
//            return $scope.displaySaveButton;
//        };
//        $scope.displayCancelTa = function () {
//            return $scope.displayCancelButton;
//        };
//        $scope.displayExitTa = function () {
//            return $scope.displayExitButton;
//        };
//
////===========================================
//        $scope.displayWkfP = function (profil) {
//
//            return (profil.id != null) && (profil.isObsolete == false) && ($scope.displayWkf == true);
//        };
//        $scope.displayWkfTaList = function (profil) {
//            return (profil.id !== null) && (profil.isObsolete === false);
//        };
////=====================================
//
//     $scope.getStatut = function(){
//        // $scope.objetProfil.statutDto.libelle = "here";
//         
//         if($scope.objetProfil.statutDto.libelle !==null){
//             alert(JSON.stringify($scope.objetProfil.statutDto.libelle));
//         }
//         
//     };
//    $scope.getHabilitation = function(habi){
//        
//      alert(angular.toJson(habi));
//      
//    };
//    
//            $scope.editerProfils = function (id) {
//            //$scope.list = true;
//            $scope.titleModale = "Edition d'un profil";
//            $('#idProfile').val($scope.objetProfil.id);
//         //   $scope.pagi = true;
//            $scope.disabledCode = true;
//            $scope.disabledLibelle = false;
//            $scope.saveShow = true;
//            $scope.closeShow = true;
//            $scope.modalClose = false;
//            $scope.tableau = true;
//            $scope.tableHabilitation = true;
//            $scope.disab = false;
//            disabDelete = false;
//            $scope.ligne = true;
//            $scope.listeHabilitationFonctionnelles($scope.objetHabilitationFonctionnelle.idProfil);
//            $scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id; 
//            $('#idProfile').val($scope.objetHabilitationFonctionnelle.idProfil);
//          //  $scope.vide();
//            if (id !== null && id !== undefined) {
//                GenericService.get(detailProfilURL + "/" + id)
//                        .then(
//                                function (data) {
//                                    if (data) {
//                                        $scope.objetProfil = data.detailProfil;
//                                        $scope.objetProfil.disabledEdit = false;
//                                        $scope.displaySaveButton = true;
//                                        $scope.displayCancelButton = true;
//                                        $scope.displayExitButton = false;
//                                        $scope.displayWkf = false;
//                                        $scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
//                                        $scope.objetProfil = data.detailProfil;                                        
//                                        $('#idProfile').val($scope.objetProfil.id);
//                                        $scope.displayWkf = true;
//                                        $scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
//                                    }
//                                   /* if($scope.objetProfil.flgProfil === "4"){
//                                         $scope.check();
//                                     }
//                                     if($scope.objetProfil.flgProfil ==="5" || $scope.objetProfil.flgProfil === null || $scope.objetProfil.flgProfil == undefined){
//                                         
//                                     }*/
//                                    $scope.check($scope.objetProfil.flgProfil);
//                                },
//                                function () {
//                                }
//                                        
//                            );
//                              
//                              if($scope.titleModale === "Edition d'un Profil"){
//                              }
//                             if($scope.objetProfil.statutDto.libelle==="Obsolete"){
//                                        $scope.objetHabilitationFonctionnelle.idProfil = null;
//                                        $scope.sta = false;
//                                        $scope.titre = true;
//                                         $scope.tableau = false;
//                                         $scope.tableHabilitation = false;
//                                         
//                                           } else if($scope.objetProfil.statutDto.libelle==="Actif"){
//                                               $scope.sta = true;      
//                                                $scope.titre = true;
//                                               $scope.tableau = true;
//                                               $scope.tableHabilitation = true;
//                                           } else if ($scope.objetProfil.statutDto.libelle==="En saisie"){
//                                               $scope.sta = true;      
//                                               $scope.titre = true;
//                                               $scope.tableau = true;
//                                               $scope.tableHabilitation = true;
//                                           } 
//                                            if ($scope.objetProfil.statutDto.libelle===null){  
//                                               $scope.sta = true;      
//                                               $scope.titre = true;
//                                               $scope.tableau = false;
//                                               $scope.tableHabilitation = false;
//                                           }
//                                           $scope.objetHabilitationFonctionnelle = $scope.objetProfil;
//                        
//                                           if($scope.objetProfil.flgProfil === "4"){
//                                         $scope.check();
//                                         }
//                }
//        };
//      
//        
//        $scope.editerProfil = function (id) {
//            //$scope.list = true;
//            $scope.titleModale = "Edition d'un profil";
//            $('#idProfile').val($scope.objetProfil.id);
//         //   $scope.pagi = true;
//            $scope.disabledCode = true;
//            $scope.disabledLibelle = false;
//            $scope.saveShow = true;
//            $scope.closeShow = true;
//            $scope.modalClose = false;
//            $scope.tableau = true;
//            $scope.tableHabilitation = true;
//            $scope.disab = false;
//            $scope.ligne = true;
//            $scope.listeHabilitationFonctionnelles($scope.objetHabilitationFonctionnelle.idProfil);
//            $scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id; 
//            $('#idProfile').val($scope.objetHabilitationFonctionnelle.idProfil);
//          //  $scope.vide();
//            if (id !== null && id !== undefined) {
//                GenericService.get(detailProfilURL + "/" + id)
//                        .then(
//                                function (data) {
//                                    if (data) {
//                                        $scope.objetProfil = data.detailProfil;
//                                        $scope.objetProfil.disabledEdit = false;
//                                        $scope.displaySaveButton = true;
//                                        $scope.displayCancelButton = true;
//                                        $scope.displayExitButton = false;
//                                        $scope.displayWkf = false;
//                                        $scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
//                                        $scope.objetProfil = data.detailProfil;                                        
//                                        $('#idProfile').val($scope.objetProfil.id);
//                                        $scope.displayWkf = true;
//                                        $scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
//                                    }
//                                   /* if($scope.objetProfil.flgProfil === "4"){
//                                         $scope.check();
//                                     }
//                                     if($scope.objetProfil.flgProfil ==="5" || $scope.objetProfil.flgProfil === null || $scope.objetProfil.flgProfil == undefined){
//                                         
//                                     }*/
//                                    $scope.check($scope.objetProfil.flgProfil);
//                                },
//                                function () {
//                                }
//                                        
//                            );
//                              
//                              if($scope.titleModale === "Edition d'un Profil"){
//                              }
//                             if($scope.objetProfil.statutDto.libelle==="Obsolete"){
//                                        $scope.objetHabilitationFonctionnelle.idProfil = null;
//                                        $scope.sta = false;
//                                        $scope.titre = true;
//                                         $scope.tableau = false;
//                                         $scope.tableHabilitation = false;
//                                         
//                                           } else if($scope.objetProfil.statutDto.libelle==="Actif"){
//                                               $scope.sta = true;      
//                                                $scope.titre = true;
//                                               $scope.tableau = true;
//                                               $scope.tableHabilitation = true;
//                                           } else if ($scope.objetProfil.statutDto.libelle==="En saisie"){
//                                               $scope.sta = true;      
//                                               $scope.titre = true;
//                                               $scope.tableau = true;
//                                               $scope.tableHabilitation = true;
//                                           } 
//                                            if ($scope.objetProfil.statutDto.libelle===null){  
//                                               $scope.sta = true;      
//                                               $scope.titre = true;
//                                               $scope.tableau = false;
//                                               $scope.tableHabilitation = false;
//                                           }
//                                           $scope.objetHabilitationFonctionnelle = $scope.objetProfil;
//                        
//                                           if($scope.objetProfil.flgProfil === "4"){
//                                         $scope.check();
//                                         }
//                }
//        };
//        $scope.showPanelProfil = function () {
//            return $scope.objetProfil !== null && $scope.objetProfil.id !== null;
//        };
//        
//        
//    }]);
//
