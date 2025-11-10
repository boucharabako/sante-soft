/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


'use strict';
var App;

//App.controller("produitController", ['$scope', 'GenericService', 'StripeService', function ($scope, GenericService, StripeService) {
App.controller("produitController", ['$scope', 'GenericService', function ($scope, GenericService) {

        const urlBase = appUrl + "fonctionnelle/produit";
        const listeProduits = urlBase + "/list";
        const changerEtatURL = urlBase + "/changerEtat";
        const listeProduitsURL = urlBase + "/listAllProduit";
        const listeServicesURL = urlBase + "/listeService";
        const saveOrUpdateProduitURL = urlBase + "/saveOrUpdateProduit";
        const detailProduitURL = urlBase + "/getProduit";
        const deleteProduitURL = urlBase + "/deleteProduit";
        const listeTypeProduitsURL = urlBase + "/listTypeProduits";
        const listeEtatsURL = urlBase + "/listeEtat";
        const listTypeProduitURL = urlBase + "/listTypeProduit";
//        const saveCommandePannierURL = urlBase + "/saveCommandePannier";
        const saveCommandePannierURL = appUrl + "fonctionnelle/commande/saveCommandePannier";

        var listProduits = [];
        $scope.listeTypeProduits = [];
        $scope.objetProduit = {id: null, code: null, libelle: null, description: null, quantite: 0, prix: 0, typeProduit: null, libelleTypeProduit: null, image: null};
        $scope.objetProduitMaster = {id: null, code: null, libelle: null, description: null, quantite: 0, prix: 0, typeProduit: null, libelleTypeProduit: null, image: null};
        $scope.objetCommande = {produits: [], ordonnance: null, numeroTelephone: null, nomClient: null, prenomsClient: null, emailClient: null, total:null};
        $scope.searchObject = {mc: '', code: '', libelle: '', description: '', typeProduit: ''};

        $scope.totalElements = 0;
        $scope.searchObjectMaster = {mc: ''};
        var firstPage = 1;
        $scope.initialNumerOfElements = 5;
        $scope.pageSizeSelect = 5;
        $scope.memoryPage = firstPage;

        $scope.afficheJointureOrdonnance = false;
        $scope.listeProduits = function () {
//            alert("aaaaaaaaaaaaaaaaa");

            $scope.totalElements = 0;
            var url = listeProduits + "/" + $scope.initialNumerOfElements + "/" + firstPage;
            GenericService.get(url + "/?mc=" + $scope.searchObject.mc + "&code=" + $scope.searchObject.code
                    + "&libelle=" + $scope.searchObject.libelle + "&description=" + $scope.searchObject.description + "&typeProduit=" + $scope.searchObject.typeProduit)
                    .then(
                            function (data) {

                                $scope.listProduits = data.listProduit.content;
//                                alert("AA:" + angular.toJson($scope.listProduits));
                                $scope.listPage = data.listProduit;
                                $scope.pageSizes = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listProduit.totalElements;

                            },
                            function () {}

                    );
        };
        $scope.listeProduits();
        $scope.totalElements = 0;

        $scope.paginate = function (pageSizeSelect, listePage) {
            $scope.memoryPage = listePage;

            var url = listeProduits + "/" + pageSizeSelect + "/" + $scope.memoryPage;


            var url2 = url + "/?mc=" + $scope.searchObject.mc + "&code=" + $scope.searchObject.code
                    + "&libelle=" + $scope.searchObject.libelle + "&description=" + $scope.searchObject.description + "&typeProduit=" + $scope.searchObject.typeProduit;

            GenericService.get(url2)
                    .then(
                            function (data) {
                                $scope.listProduits = data.listProduit.content;
                                $scope.listPage = data.listProduit;
                                $scope.pageSizes = data.pageSize;
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listProduit.totalElements;
                            },
                            function () {

                            }
                    );
        };
        $scope.log1 = function (u) {
            console.log("abbbb" + angular.toJson(u));
        };
        $scope.changePageSize = function () {

            var url = listeProduits + "/" + $scope.pageSizeSelect + "/" + firstPage;

            GenericService.get(url + "/?mc=" + $scope.searchObject.mc
                    + "&idTypeProduit=" + $scope.searchObject.idTypeProduit + "&plaque=" + $scope.searchObject.plaque
                    + "&chassis=" + url + "/?mc=" + $scope.searchObject.mc + "&code=" + $scope.searchObject.code
                    + "&libelle=" + $scope.searchObject.libelle + "&description=" + $scope.searchObject.description + "&typeProduit=" + $scope.searchObject.typeProduit)
                    .then(
                            function (data) {
                                $scope.listProduits = data.listProduit.content;
                                $scope.listPage = data.listProduit;
                                $scope.pageSizes = data.pageSize;
                                $scope.sequence = data.sequence;
                                $scope.totalElements = data.listProduit.totalElements;


                            },
                            function () {
                            }
                    );
        };


        $scope.modeEdition = 0;

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

        $scope.saveProduit = function (objetProduit) {
//            var idTypeProduit = document.querySelector('#idTypeProduit');
//            objetProduit.idTypeProduit = idTypeProduit.options[idTypeProduit.selectedIndex].value;
            objetProduit.image = $scope.objetProduit.image;
            //jslog("Produit:"+angular.toJson(produitSuivante));
//            alert(angular.toJson(objetProduit));
            GenericService.post(saveOrUpdateProduitURL, angular.toJson(objetProduit))
                    .then(
                            function (data) {
                                //$scope.detailProduit(data.produit.id);
                                $scope.objetProduit = {id: null, code: null, libelle: null, description: null, quantite: 0, prix: 0, typeProduit: null, image: null};
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);

                                $scope.closeModalAddUser();
                                $scope.modeEdition = 0;
                            },
                            function () {}
                    );
        };

        $scope.saveProduitEtContinuer = function (objetProduit) {
//            var idTypeProduit = document.querySelector('#idTypeProduit');
//            objetProduit.idTypeProduit = grade.options[idTypeProduit.selectedIndex].value;

            GenericService.post(saveOrUpdateProduitURL, angular.toJson(objetProduit))
                    .then(
                            function (data) {
                                //$scope.detailProduit(data.produit.id);
//                                $scope.objetProduit = {id: null, code: null, idTypeProduit: null, etat: null};
                                $scope.objetProduit = angular.copy(objetProduitMaster);
                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);

                                $scope.modeEdition = 0;

                            },
                            function () {}
                    );
        };
        $scope.closeModalAddUser = function () {
            $('#detail-produit').modal('hide');
        };

        $scope.detailProduitV = function (id) {
            $scope.modeEdition = 2;
            $scope.titleModale = "Détail d'un produit perdu";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailProduitURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetProduit = data.produitDTO;
//                                    $('#idTypeProduit').val(data.produitDTO.idTypeProduit);
                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsProduit(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-produit').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };
        $scope.editProduit = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modification d'un produit";
            $scope.disable = true;
            $scope.disableCode = true;
            GenericService.get(detailProduitURL + "/" + id)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.objetProduit = data.produitDTO;

                                    $scope.displaySaveButton = true;
                                    $scope.displayCancelButton = true;
                                    $scope.displayExitButton = false;
                                    $scope.displayWkf = false;
                                    //$scope.objetHabilitationFonctionnelle.idProfil = $scope.objetProfil.id;
                                    $scope.displayWkf = true;
                                    //$scope.getProfilsProduit(id);
                                    //$scope.listeHabilitationFonctionnelles($scope.objetProfil.id);
                                    $('#detail-produit').modal('show');
                                }

                            },
                            function () {
                            }

                    );
        };



        $scope.deleteProduit = function (id) {
//            alert(id)
//            var dialog = $('#myModal').modal('show');
//            $('#btnYes').click(function () {
            GenericService.delete(deleteProduitURL + "/" + id)
                    .then(
                            function (data) {
                                $('#myModal').modal('hide');
                                $scope.listeProduits();

                            },
                            function () {
                            }
                    );
//            });
//            $('#btnNo').click(function () {
//                //dialog.dialog('close');
//            });
        };
        $scope.listeDesProduits = [];
        $scope.searchObjectProduit = null;
        $scope.loadProduits = function () {
            var url = listeProduitsURL;
            if ($scope.searchObjectProduit && $scope.searchObjectProduit != null && $scope.searchObjectProduit != '' && $scope.searchObjectProduit != 'undefined') {
                url = url + ((url.includes("?")) ? "&" : "?") + 'mc=' + $scope.searchObjectProduit;
            }

            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listeDesProduits = data.listProduit;
                                jslog("Liste Service:" + angular.toJson($scope.listeDesProduits));
                            },
                            function () {
                            }
                    );
        };

        $scope.loadProduits();





        $scope.cart = [];

//    // Fonction pour ajouter un produit au panier
//     $scope.addToCart = function (product) {
//            let found = $scope.cart.find(item => item.libelle === product.libelle);
//            if (found) {
//                found.quantity++;
//            } else {
//                $scope.cart.push({
//                    libelle: product.libelle,
//                    prix: product.prix,
//                    quantity: 1
//                });
//            }
//        };
//
//        // Supprimer un produit du panier
//        $scope.removeFromCart = function (product) {
//            let index = $scope.cart.findIndex(item => item.libelle === product.libelle);
//            if (index !== -1) {
//                if ($scope.cart[index].quantity > 1) {
//                    $scope.cart[index].quantity--;
//                } else {
//                    $scope.cart.splice(index, 1);
//                }
//            }
//        };
//
//        // Calculer le prix total du panier
//        $scope.getTotalPrice = function () {
//            return $scope.cart.reduce(function (total, item) {
//                return total + (item.prix * item.quantity);
//            }, 0);
//        };


        $scope.listOrdo = [];
        $scope.ordo = {
            code: null,
            file: null
        };
        $scope.objet = {
            code: null,
            file: null
        };
        $scope.handleFileUpload = function (event, item) {
            var file = event.target.files[0];
            if (file) {

                item.prescriptionFile = file; // Associer le fichier au produit
                $scope.ordo.code = item.code;
                $scope.ordo.file = file;
                $scope.listOrdo.push($scope.ordo);
            }
        };

        $scope.handleFileChange = function (input) {
            jslog("item:" + angular.toJson(input));
            var files = input.files;
            if (files.length > 0) {
                var file = files[0]; // Prendre le premier fichier (si plusieurs sont sélectionnés)
                jslog("file:" + file);
                jslog("item:" + angular.toJson(input));
                // Appel de plusieurs méthodes
//            $scope.uploadOrdonnance(file, item);
//            $scope.someOtherMethod(file, item); // Remplacez par d'autres méthodes si nécessaire
            }
        };
        // Fonction pour ajouter un produit au panier
        $scope.addToCart = function (product) {
            let found = $scope.cart.find(item => item.libelle === product.libelle);
            if (found) {
                found.quantity++;
            } else {
                $scope.cart.push({
                    code: product.code,
                    libelle: product.libelle,
                    prix: product.prix,
                    quantity: 1,
                    typeProduit: product.typeProduit,
                    prescriptionFile: null // Initialiser sans fichier
                });
                if (product.typeProduit === '1') {
                    $scope.afficheJointureOrdonnance = true;
                }
            }
        };

        // Fonction pour retirer un produit du panier
        $scope.removeFromCart = function (item) {
            let index = $scope.cart.indexOf(item);
            if (index !== -1) {
                if ($scope.cart[index].quantity > 1) {
                    $scope.cart[index].quantity--;
                } else {
                    if (item.typeProduit === '1') {
                        $scope.afficheJointureOrdonnance = false;
                    } else {
                        //$scope.afficheJointureOrdonnance = true;
                    }
                    $scope.cart.splice(index, 1);

                }
            }
        };

        $scope.openCheckoutForm = function () {
            $('#checkoutModal').modal('show');
        };
        // Fonction pour passer la commande
        $scope.submitOrder = function () {
            var formData = new FormData();

            // Ajouter les détails de chaque produit avec le fichier d'ordonnance
            $scope.cart.forEach(item => {
                jslog("item:" + angular.toJson(item));

                jslog("item:" + angular.toJson(item));
                $scope.objetCommande.produits.push({

                    code: item.code,
                    libelle: item.libelle,
                    prix: item.prix,
                    quantite: item.quantity
                });
                
                $scope.objetCommande.total = $scope.getTotalPrice();
//                formData.append('items[]', JSON.stringify({
//
//                    code: item.code,
//                    libelle: item.libelle,
//                    prix: item.prix,
//                    quantity: item.quantity
//                }));
//                if (item.prescriptionFile) {
//                    formData.append('prescriptions[]', item.prescriptionFile, item.prescriptionFile.name);
//                }
            });
            jslog("formData:" + angular.toJson($scope.objetCommande));
            // Envoyer la commande avec les fichiers d'ordonnance
            GenericService.post(saveCommandePannierURL, angular.toJson($scope.objetCommande))
                    .then(
                            function (response) {
//                                alert('Commande passée avec succès !');
                                // Réinitialiser le panier
                                $scope.cart = [];
                                $('#checkoutModal').modal('hide');
                            },
                            function (error) {
//                                alert('Erreur lors de la passation de la commande.');
                            }
                    );
        };

        // Calculer le prix total du panier
        $scope.getTotalPrice = function () {
            return $scope.cart.reduce(function (total, item) {
                return total + (item.prix * item.quantity);
            }, 0);
        };
// Fonction pour gérer le téléchargement de l'image
        $scope.uploadImage = function (element) {
//            alert("aaa");
            $scope.$apply(function () {
                var file = element.files[0];
                var reader = new FileReader();
                reader.onloadend = function () {

                    $scope.objetProduit.image = reader.result;
                    jslog("$scope.produit.image:" + $scope.objetProduit.image);
                };
                reader.readAsDataURL(file);
            });
        };
        //$scope.objet = ;
        $scope.affecterIteme = function (item) {
//            alert(1);
            $scope.item = item;
        };
        $scope.uploadOrdonnance = function (fichier) {
//            alert("aaa" + $scope.objet);
            $scope.$apply(function () {
                var file = fichier.files[0];
                var reader = new FileReader();
                reader.onloadend = function () {

                    //item.prescriptionFile = reader.result;
                    //objet.prescriptionFile = file; // Associer le fichier au produit
                    $scope.ordo.code = $scope.objet.code;
                    $scope.objetCommande.ordonnance = reader.result;

                    jslog("prescriptionFile:" + $scope.objetCommande.ordonnance);
                };
                reader.readAsDataURL(file);
            });
        };
        // Initial call to fetch products

        $scope.getListeProduits = function () {
            GenericService.get(listTypeProduitURL)
                    .then(
                            function (data) {
                                if (data) {
                                    $scope.listeTypeProduits = data.listTypeProduit;
                                }
                            },
                            function () {
                            }

                    );
        };
        $scope.getListeProduits();

//        const stripe = Stripe('your-publishable-key-here'); // Remplacez par votre clé publique Stripe
//        const elements = stripe.elements();
//
//        const style = {
//            base: {
//                color: '#32325d',
//                fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
//                fontSmoothing: 'antialiased',
//                fontSize: '16px',
//                '::placeholder': {
//                    color: '#aab7c4'
//                }
//            },
//            invalid: {
//                color: '#fa755a',
//                iconColor: '#fa755a'
//            }
//        };
//
//        const card = elements.create('card', {style: style});
//        card.mount('#card-element');
//
//        card.on('change', ({error}) => {
//            const displayError = document.getElementById('card-errors');
//            if (error) {
//                displayError.textContent = error.message;
//            } else {
//                displayError.textContent = '';
//        }
//        });
//
//        $scope.handlePayment = function (event) {
//            event.preventDefault();
//
//            stripe.createToken(card).then((result) => {
//                if (result.error) {
//                    const errorElement = document.getElementById('card-errors');
//                    errorElement.textContent = result.error.message;
//                } else {
//                    $scope.processPayment(result.token);
//                }
//            });
//        };
//
//        $scope.processPayment = function (token) {
//            const paymentData = {
//                token: token.id,
//                amount: $scope.getTotalPrice() * 100, // Montant en centimes
//                description: 'Achat sur notre site'
//            };
//
//            GenericService.post('/stripe/charge', paymentData)
//                    .then((response) => {
//                        alert('Paiement réussi !');
//                        $scope.cart = [];
//                    })
//                    .catch((error) => {
//                        alert('Erreur lors du paiement.');
//                    });
//        };


        }]);