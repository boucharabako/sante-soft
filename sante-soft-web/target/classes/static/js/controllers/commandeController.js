/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


'use strict';
var App;

App.controller("commandeController", ['$scope', 'GenericService', function ($scope, GenericService) {

        const urlBase = appUrl + "fonctionnelle/commande";
        const listeCommandes = urlBase + "/list";
        const changerEtatURL = urlBase + "/changerEtat";
        const listeCommandesURL = urlBase + "/listAllCommande";
        const listeServicesURL = urlBase + "/listeService";
        const saveOrUpdateCommandeURL = urlBase + "/saveOrUpdateCommande";
        const detailCommandeURL = urlBase + "/getCommande";
        const deleteCommandeURL = urlBase + "/deleteCommande";
        const listeTypeCommandesURL = urlBase + "/listTypeCommandes";
        const listeEtatsURL = urlBase + "/listeEtat";
        const listTypeCommandeURL = urlBase + "/listTypeCommande";

        var listCommandes = [];
        $scope.listCommandes = [];

        $scope.listeTypeCommandes = [];

        $scope.objetCommande = {id: null, numeroCommande: null, dateCommande: null, prixTotalCommande: null,
            etatCommande: null, utilisateur: null, numeroTelClient: null, nomClient: null, prenomClient: null, emailClient: null, listDetailCommande: []};
        $scope.objetCommandeMaster = {id: null, numeroCommande: null, dateCommande: null, prixTotalCommande: null,
            etatCommande: null, utilisateur: null, numeroTelClient: null, nomClient: null, prenomClient: null, emailClient: null, listDetailCommande: []};

        $scope.searchObject = {mc: '', date: new Date()};

        $scope.totalElements = 0;
        $scope.searchObjectMaster = {mc: ''};
        var firstPage = 1;
        $scope.initialNumerOfElements = 5;
        $scope.pageSizeSelect = 5;
        $scope.memoryPage = firstPage;
        $scope.listeCommandes = function () {
            
//            alert("aaaaaaaaaaaaaaaaa");

            $scope.totalElements = 0;
            var url = listeCommandes + "/" + $scope.initialNumerOfElements + "/" + firstPage;
            if ($scope.searchObject.mc && $scope.searchObject.mc != null && $scope.searchObject.mc != '' && $scope.searchObject.mc != 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'mc=' + $scope.searchObject.mc;
            }
            if ($scope.searchObject.date && $scope.searchObject.date != null && $scope.searchObject.date != '' && $scope.searchObject.date != 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'date=' + $scope.searchObject.date.toISOString();
            }

            GenericService.get(url)
                    .then(
                            function (data) {

                                $scope.listCommandes = data.listCommande.content;
//                                alert("AA:"+angular.toJson($scope.listCommandes));
                                $scope.listPage = data.listCommande;
                                $scope.pageSizes = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listCommande.totalElements;

                            },
                            function () {}

                    );
        };
        $scope.listeCommandes();
        $scope.totalElements = 0;

        $scope.paginate = function (pageSizeSelect, listePage) {
            $scope.memoryPage = listePage;

            var url = listeCommandes + "/" + pageSizeSelect + "/" + $scope.memoryPage;
            if ($scope.searchObject.mc && $scope.searchObject.mc != null && $scope.searchObject.mc != '' && $scope.searchObject.mc != 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'mc=' + $scope.searchObject.mc;
            }
            if ($scope.searchObject.date && $scope.searchObject.date != null && $scope.searchObject.date != '' && $scope.searchObject.date != 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'date=' + $scope.searchObject.date.toISOString();
            }

//            var url2 = url + "?mc=" + $scope.searchObject.mc + "&code=" + $scope.searchObject.code + "&date=" + $scope.searchObject.date.toISOString()
            var url2 = url;
            GenericService.get(url2)
                    .then(
                            function (data) {
                                $scope.listCommandes = data.listCommande.content;
                                $scope.listPage = data.listCommande;
                                $scope.pageSizes = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listCommande.totalElements;
                            },
                            function () {

                            }
                    );
        };
        $scope.log1 = function (u) {
            console.log("abbbb" + angular.toJson(u));
        };
        $scope.changePageSize = function () {
            var url = listeCommandes + "/" + $scope.pageSizeSelect + "/" + firstPage;
            if ($scope.searchObject.mc && $scope.searchObject.mc != null && $scope.searchObject.mc != '' && $scope.searchObject.mc != 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'mc=' + $scope.searchObject.mc;
            }
            if ($scope.searchObject.date && $scope.searchObject.date != null && $scope.searchObject.date != '' && $scope.searchObject.date != 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'date=' + $scope.searchObject.date.toISOString();
            }

//            GenericService.get(url + "?mc=" + $scope.searchObject.mc + "&date=" + $scope.searchObject.date.toISOString())
            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listCommandes = data.listCommande.content;
                                $scope.listPage = data.listCommande;
                                $scope.pageSizes = data.pageSize;
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listCommande.totalElements;


                            },
                            function () {
                            }
                    );
        };


        $scope.modeEdition = 0;

        $scope.addCommande = function () {
//            alert("alllaaa");
//             document.querySelector('#un').class = "disabled";
//             document.querySelector('#un').class = "disabled";
            $scope.boutonSelect1 = "first current";
            //
            // 

            $scope.disableCode = false;
            $scope.disable = false;
            $scope.modeEdition = 0;
            $scope.titleModale = "Détail d'une Commande";

            $('#detail-Commande').modal('show');
        };

        $scope.saveCommande = function (objetCommande) {
//            var idTypeCommande = document.querySelector('#idTypeCommande');
//            objetCommande.idTypeCommande = idTypeCommande.options[idTypeCommande.selectedIndex].value;
            objetCommande.image = $scope.objetCommande.image;
            //jslog("Commande:"+angular.toJson(Commandesuivante));
//            alert(angular.toJson(objetCommande));
            GenericService.post(saveOrUpdateCommandeURL, angular.toJson(objetCommande))
                    .then(
                            function (data) {
                                //$scope.detailCommande(data.Commande.id);
                                $scope.objetCommande = {id: null, code: null, libelle: null, description: null, quantite: 0, prix: 0, typeCommande: null, image: null};
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);

                                $scope.closeModalAddUser();
                                $scope.modeEdition = 0;
                            },
                            function () {}
                    );
        };

        $scope.saveCommandeEtContinuer = function (objetCommande) {
            var idTypeCommande = document.querySelector('#idTypeCommande');
            objetCommande.idTypeCommande = grade.options[idTypeCommande.selectedIndex].value;

            GenericService.post(saveOrUpdateCommandeURL, angular.toJson(objetCommande))
                    .then(
                            function (data) {
                                //$scope.detailCommande(data.Commande.id);
                                $scope.objetCommande = {id: null, code: null, idTypeCommande: null, etat: null};
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);

                                $scope.modeEdition = 0;

                            },
                            function () {}
                    );
        };
        $scope.closeModalAddUser = function () {
            $('#detail-Commande').modal('hide');
        };

        $scope.detailCommande = function (id) {
            $scope.modeEdition = 2;
            $scope.titleModale = "Détail d'une Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.commandeDTO;
//                                    $('#idTypeCommande').val(data.CommandeDTO.idTypeCommande);
                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editCommandeV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.CommandeDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-Commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };



        $scope.editCommandeV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.CommandeDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-Commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editCommandeV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.CommandeDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-Commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editCommandeV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.CommandeDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-Commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editCommandeV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.CommandeDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-Commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editCommandeV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.CommandeDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-Commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editCommandeV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.CommandeDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-Commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editCommandeV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.CommandeDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-Commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editCommandeV = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un Commande";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailCommandeURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetCommande = data.CommandeDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsCommande(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-Commande').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };

        $scope.deleteCommande = function (id) {

//            var dialog = $('#myModal').modal('show');
//            $('#btnYes').click(function () {
                GenericService.delete(deleteCommandeURL + "/" + id)
                        .then(
                                function (data) {
                                    $('#myModal').modal('hide');
                                    $scope.loadCommandes();

                                },
                                function () {
                                }
                        );
//            });
//            $('#btnNo').click(function () {
//                //dialog.dialog('close');
//            });
        };
        $scope.listeDesCommandes = [];
        $scope.loadCommandes = function () {
            GenericService.get(listeCommandesURL)
                    .then(
                            function (data) {
                                $scope.listeDesCommandes = data.listCommande;
                                jslog("Liste Service:" + angular.toJson($scope.listeDesCommandes));
                            },
                            function () {
                            }
                    );
        };

        //$scope.loadCommandes();





        $scope.cart = [];



        $scope.addToCart = function (product) {
            $scope.cart.push(product);
        };

        $scope.getTotalPrice = function () {
            return $scope.cart.reduce(function (total, product) {
                return total + product.price;
            }, 0);
        };

// Fonction pour gérer le téléchargement de l'image
        $scope.uploadImage = function (element) {
//            alert("aaa");
            $scope.$apply(function () {
                var file = element.files[0];
                var reader = new FileReader();
                reader.onloadend = function () {

                    $scope.objetCommande.image = reader.result;
                    jslog("$scope.Commande.image:" + $scope.objetCommande.image);
                };
                reader.readAsDataURL(file);
            });
        };



        $scope.getListeCommandes = function () {
            GenericService.get(listTypeCommandeURL)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.listeTypeCommandes = data.listTypeCommande;
                                }
                            },
                            function () {
                            }

                    );
        };
        // $scope.getListeCommandes();

        $scope.addProduit = function () {
//            alert("alllaaa");
//             document.querySelector('#un').class = "disabled";
//             document.querySelector('#un').class = "disabled";
            $scope.boutonSelect1 = "first current";
            //
            // 

            $scope.disableCode = false;
            $scope.disable = false;
            $scope.modeEdition = 0;
            $scope.titleModale = "Ajout d'un nouveau produit ";

            $('#detail-produit').modal('show');
        };
        }]);
