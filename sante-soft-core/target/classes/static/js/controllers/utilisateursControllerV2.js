/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
var App;
var appUrl;
App.controller("utilisateursControllerV2", ['$scope', 'GenericService', function ($scope, GenericService) {

        const urlBase = appUrl+"gestion/utilisateur";
        const listeUtilisateurURL = urlBase + "/listAllUtilisateur";
        const listeGroupeURL = urlBase + "/listeGroupe";
        const listeProfilURL = urlBase + "/listeProfil";
        const saveOrUpdateUtilisateurURL = urlBase + "/saveOrUpdateUser";
        $scope.objetUtilisateur = {id: null, titre: null, username: null, firstName: null, lastName: null, dateNaissance: new Date(), tel: null, estAgent: true,
            email: null, profils: [], groupes: [], password: null,
            confirmPassword: null, statut: null, isEnSaisie: true, isActif: false, isObsolete: false, 
            disabledEdit: false, photo: null};

//, statutDt: "En saisie",  button: true, etat: true


        $scope.listeObjetUtilisateur = [];
        $scope.listeDesProfils = [];
        $scope.listeDesGroupes = [];

        $scope.listeAllUtilisateurs = function () {
            alert("123:"+appUrl);
            var url = listeUtilisateurURL;
            GenericService.get(url)
                    .then(
                            function (data) {

                                $scope.listeObjetUtilisateur = data.listUtilisateur;//listeUser
                                alert(angular.toJson(data.listUtilisateur));
                                console.log(angular.toJson(data.listUtilisateur));


                            },
                            function () {}
                    );
        };
$scope.listeAllUtilisateurs();
        $scope.listeAllGroupes = function () {

            var url = listeGroupeURL;
            GenericService.get(url)
                    .then(
                            function (data) {

                                $scope.listeDesGroupes = data.listeGroupes;//listeUser
                                alert(angular.toJson(data.listeGroupes));
                                console.log(angular.toJson(data.listeGroupes));
                            },
                            function () {}
                    );
        };
        $scope.listeAllGroupes();
        $scope.listeAllProfils = function () {

            var url = listeProfilURL;
            GenericService.get(url)
                    .then(
                            function (data) {

                                $scope.listeDesProfils = data.listeProfils;//listeUser
                                alert(angular.toJson(data.listeProfils));
                                console.log(angular.toJson(data.listeProfils));
                            },
                            function () {}
                    );
        };
        $scope.listeAllProfils();

        //const tr = 
        $scope.listeAllUtilisateurs();

        $scope.saveUtilisateur = function (objetUtilisateur) {
            jslog("++++++++++++objetUtilisateur++++++++++" + angular.toJson(objetUtilisateur));
            if (objetUtilisateur.password === "") {
                objetUtilisateur.password = null;
            }
            jslog("++++++++++++objetUtilisateur++++++++++" + angular.toJson(objetUtilisateur));
            
            GenericService.post(saveOrUpdateUtilisateurURL, angular.toJson(objetUtilisateur))
                    .then(
                            function (data) {
                                alert("1");
                                //$scope.detailUtilisateur(data.utilisateur.id);
                                $scope.objetUtilisateur = {id: null, titre: null, username: null, firstName: null, lastName: null,
                                    email: null, profils: [], groupes: [], password: null,
                                    confirmPassword: "", statut: null, isEnSaisie: true, isActif: false, isObsolete: false, groupeUser: null,
                                    disabledEdit: false, photo: null, extensions: [], button: true};
//                                $scope.paginate($scope.pageSizeSelect, $scope.memoryPage);
//                                $scope.getExtensionsInterface();
//                                $scope.closeModalAddUser();
                            },
                            function () {}
                    );
        };

    }]);