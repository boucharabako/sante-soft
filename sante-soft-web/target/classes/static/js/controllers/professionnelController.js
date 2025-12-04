/* 
 * Controller pour la gestion des professionnels de santé
 */

'use strict';
var App;

App.controller("professionnelController", ['$scope', 'GenericService', function ($scope, GenericService) {

        const urlBase = appUrl + "api/professionnel";
        const listeProfessionnelsURL = urlBase + "/listAllProfessionnel";
        const saveProfessionnelURL = urlBase + "/saveOrUpdateProfessionnel";
        const detailProfessionnelURL = urlBase + "/getProfessionnel";
        const deleteProfessionnelURL = urlBase + "/deleteProfessionnel";
        const listSexeURL = urlBase + "/listSexe";
        const listSpecialiteURL = urlBase + "/listSpecialite";

        // URLs pour les affectations
        const affectationBaseURL = appUrl + "fonctionnelle/affectation";
        const saveAffectationURL = affectationBaseURL + "/saveOrUpdateAffectation";
        const deleteAffectationURL = affectationBaseURL + "/deleteAffectation";
        const listAffectationsByProfessionnelURL = affectationBaseURL + "/listByProfessionnel";
        const listeEtablissementsURL = appUrl + "fonctionnelle/etablissement/listAllEtablissement";

        var listProfessionnels = [];
        $scope.listeSexes = [];
        $scope.listeSpecialites = [];
        $scope.listeEtablissements = [];
        $scope.listeAffectations = [];
        $scope.affectationsTemporaires = []; // Tableau pour stocker les affectations avant l'enregistrement
        $scope.nouvelleAffectation = {
            idProfessionnel: null,
            idEtablissement: null,
            idSpecialite: null,
            dateDebut: null,
            dateFin: null
        };
        $scope.objetProfessionnel = {
            id: null, username: null, firstName: null, lastName: null, sexe: null,
            dateNaissance: null, email: null, tel: null, titre: null, numeroOrdre: null,
            specialite: null, etablissement: null, dateEnregistrement: null,
            password: null, confirmPassword: null
        };
        $scope.objetProfessionnelMaster = {
            id: null, username: null, firstName: null, lastName: null, sexe: null,
            dateNaissance: null, email: null, tel: null, titre: null, numeroOrdre: null,
            specialite: null, etablissement: null, dateEnregistrement: null,
            password: null, confirmPassword: null
        };

        // Date maximale pour la date de naissance (aujourd'hui)
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0');
        var yyyy = today.getFullYear();
        $scope.maxDate = yyyy + '-' + mm + '-' + dd;
        
        $scope.searchObject = {mc: '', nom: '', prenom: '', specialite: ''};
        $scope.totalElements = 0;
        $scope.searchObjectMaster = {mc: ''};
        var firstPage = 1;
        $scope.initialNumerOfElements = 5;
        $scope.pageSizeSelect = 5;
        $scope.memoryPage = firstPage;

        // Charger la liste des professionnels avec filtres
        $scope.listeProfessionnels = function () {
            $scope.totalElements = 0;

            // Construire l'URL avec tous les paramètres de recherche
            var params = [];
            if ($scope.searchObject.mc) params.push("mc=" + encodeURIComponent($scope.searchObject.mc));
            if ($scope.searchObject.nom) params.push("nom=" + encodeURIComponent($scope.searchObject.nom));
            if ($scope.searchObject.prenom) params.push("prenom=" + encodeURIComponent($scope.searchObject.prenom));
            if ($scope.searchObject.specialite) params.push("specialite=" + encodeURIComponent($scope.searchObject.specialite));

            var url = listeProfessionnelsURL + (params.length > 0 ? "?" + params.join("&") : "?mc=");

            console.log("Chargement des professionnels avec URL:", url);

            GenericService.get(url)
                    .then(
                            function (data) {
                                $scope.listProfessionnels = data.listProfessionnelSante || [];
                                $scope.listPage = {number: 0, totalElements: $scope.listProfessionnels.length};
                                $scope.pageSizes = [5, 10, 15, 20, 25];
                                $scope.pageSizeSelect = $scope.pageSizes[0];
                                $scope.totalElements = $scope.listProfessionnels.length;
                                $scope.totalPages = Math.ceil($scope.listProfessionnels.length / $scope.pageSizeSelect);
                                console.log("Professionnels chargés:", $scope.listProfessionnels.length);
                            },
                            function () {
                                console.error("Erreur lors du chargement de la liste des professionnels");
                                $scope.listProfessionnels = [];
                            }
                    );
        };
        
        $scope.listeProfessionnels();

        // Pagination
        $scope.paginate = function (pageSizeSelect, listePage) {
            $scope.memoryPage = listePage;
            $scope.pageSizeSelect = pageSizeSelect;
            $scope.listeProfessionnels();
        };

        $scope.changePageSize = function () {
            $scope.listeProfessionnels();
        };

        // Charger les listes de paramètres
        $scope.getListeSexes = function () {
            GenericService.get(listSexeURL)
                    .then(
                            function (data) {
                                $scope.listeSexes = data.listSexe;
                                console.log("Liste des sexes chargée:", $scope.listeSexes);
                            },
                            function () {
                                console.error("Erreur lors du chargement de la liste des sexes");
                            }
                    );
        };

        $scope.getListeSpecialites = function () {
            GenericService.get(listSpecialiteURL)
                    .then(
                            function (data) {
                                $scope.listeSpecialites = data.listSpecialite;
                                console.log("Liste des spécialités chargée:", $scope.listeSpecialites);

                                // Initialiser Select2 après le chargement des données
                                setTimeout(function() {
                                    initializeSelect2();
                                }, 100);
                            },
                            function () {
                                console.error("Erreur lors du chargement de la liste des spécialités");
                            }
                    );
        };

        // Fonction pour initialiser Select2 sur les selects
        function initializeSelect2() {
            // Détruire les instances existantes avant de réinitialiser
            $('#selectEtablissementAffectation').select2('destroy');
            $('#selectSpecialiteAffectation').select2('destroy');

            // Initialiser Select2 pour l'établissement de l'affectation
            $('#selectEtablissementAffectation').select2({
                placeholder: "-- Sélectionner un établissement --",
                allowClear: true,
                language: "fr",
                dropdownParent: $('#detail-professionnel')
            }).on('change', function() {
                $scope.$apply(function() {
                    $scope.nouvelleAffectation.idEtablissement = $('#selectEtablissementAffectation').val();
                });
            });

            // Initialiser Select2 pour la spécialité de l'affectation
            $('#selectSpecialiteAffectation').select2({
                placeholder: "-- Sélectionner une spécialité --",
                allowClear: true,
                language: "fr",
                dropdownParent: $('#detail-professionnel')
            }).on('change', function() {
                $scope.$apply(function() {
                    $scope.nouvelleAffectation.idSpecialite = $('#selectSpecialiteAffectation').val();
                });
            });
        }

        // Charger les listes au démarrage
        $scope.getListeSexes();
        $scope.getListeSpecialites();

        // Ajouter un nouveau professionnel
        $scope.addProfessionnel = function () {
            $scope.modeEdition = 0;
            $scope.titleModale = "Ajouter un Professionnel de Santé";
            $scope.objetProfessionnel = angular.copy($scope.objetProfessionnelMaster);
            $scope.affectationsTemporaires = []; // Réinitialiser les affectations temporaires
            $scope.nouvelleAffectation = {
                idProfessionnel: null,
                idEtablissement: null,
                idSpecialite: null,
                dateDebut: null,
                dateFin: null
            };

            // Générer automatiquement le numéro d'ordre et la date d'enregistrement
            var currentYear = new Date().getFullYear();
            $scope.objetProfessionnel.numeroOrdre = currentYear + "-XXX"; // Sera généré par le backend
            $scope.objetProfessionnel.dateEnregistrement = new Date();

            $('#detail-professionnel').modal('show');

            // Réinitialiser Select2 après l'ouverture du modal
            setTimeout(function() {
                initializeSelect2();
            }, 300);
        };

        // Modifier un professionnel existant
        $scope.editProfessionnel = function (id) {
            $scope.modeEdition = 3;
            $scope.titleModale = "Modifier un Professionnel de Santé";
            $scope.affectationsTemporaires = []; // Réinitialiser les affectations temporaires

            GenericService.get(detailProfessionnelURL + "?id=" + id)
                    .then(
                            function (data) {
                                $scope.objetProfessionnel = data.professionnelSante;

                                // Convertir les dates en objets Date pour AngularJS
                                if ($scope.objetProfessionnel.dateEnregistrement) {
                                    $scope.objetProfessionnel.dateEnregistrement = new Date($scope.objetProfessionnel.dateEnregistrement);
                                }
                                if ($scope.objetProfessionnel.dateNaissance) {
                                    $scope.objetProfessionnel.dateNaissance = new Date($scope.objetProfessionnel.dateNaissance);
                                }

                                console.log("Professionnel chargé pour modification:", $scope.objetProfessionnel);

                                // Charger les affectations existantes pour ce professionnel
                                $scope.chargerAffectations(id);

                                $('#detail-professionnel').modal('show');

                                // Réinitialiser Select2 après l'ouverture du modal
                                setTimeout(function() {
                                    initializeSelect2();
                                }, 300);
                            },
                            function () {
                                console.error("Erreur lors du chargement du professionnel");
                            }
                    );
        };

        // Afficher le détail d'un professionnel (lecture seule)
        $scope.detailProfessionnel = function (id) {
            console.log("Affichage du détail du professionnel:", id);

            GenericService.get(detailProfessionnelURL + "?id=" + id)
                    .then(
                            function (data) {
                                $scope.objetProfessionnelDetail = data.professionnelSante;

                                // Convertir les dates pour l'affichage
                                if ($scope.objetProfessionnelDetail.dateEnregistrement) {
                                    $scope.objetProfessionnelDetail.dateEnregistrement = new Date($scope.objetProfessionnelDetail.dateEnregistrement);
                                }
                                if ($scope.objetProfessionnelDetail.dateNaissance) {
                                    $scope.objetProfessionnelDetail.dateNaissance = new Date($scope.objetProfessionnelDetail.dateNaissance);
                                }

                                console.log("Professionnel chargé pour détail:", $scope.objetProfessionnelDetail);

                                // Charger les affectations pour l'affichage
                                GenericService.get(listAffectationsByProfessionnelURL + "/" + id)
                                    .then(
                                        function (data) {
                                            var affectations = Array.isArray(data) ? data : [];

                                            // Transformer les affectations pour l'affichage
                                            $scope.affectationsDetail = affectations.map(function(aff) {
                                                return {
                                                    etablissementLibelle: aff.etablissementLibelle,
                                                    specialiteLibelle: aff.specialiteLibelle,
                                                    dateDebut: aff.dateDebut ? new Date(aff.dateDebut) : null,
                                                    dateFin: aff.dateFin ? new Date(aff.dateFin) : null
                                                };
                                            });

                                            console.log("Affectations chargées pour détail:", $scope.affectationsDetail);

                                            // Afficher la modal
                                            $('#detail-professionnel-readonly').modal('show');
                                        },
                                        function (error) {
                                            console.error("Erreur lors du chargement des affectations:", error);
                                            $scope.affectationsDetail = [];
                                            $('#detail-professionnel-readonly').modal('show');
                                        }
                                    );
                            },
                            function () {
                                console.error("Erreur lors du chargement du professionnel");
                                alert("Erreur lors du chargement des détails du professionnel");
                            }
                    );
        };

        // Ajouter une affectation temporaire au tableau
        $scope.ajouterAffectationTemporaire = function () {
            console.log("=== Tentative d'ajout d'affectation ===");
            console.log("nouvelleAffectation:", $scope.nouvelleAffectation);
            console.log("idEtablissement:", $scope.nouvelleAffectation.idEtablissement);
            console.log("idSpecialite:", $scope.nouvelleAffectation.idSpecialite);
            console.log("dateDebut:", $scope.nouvelleAffectation.dateDebut);

            // Récupérer les valeurs directement depuis les selects si elles ne sont pas dans le modèle
            if (!$scope.nouvelleAffectation.idEtablissement) {
                $scope.nouvelleAffectation.idEtablissement = $('#selectEtablissementAffectation').val();
            }
            if (!$scope.nouvelleAffectation.idSpecialite) {
                $scope.nouvelleAffectation.idSpecialite = $('#selectSpecialiteAffectation').val();
            }

            console.log("Après récupération depuis les selects:");
            console.log("idEtablissement:", $scope.nouvelleAffectation.idEtablissement);
            console.log("idSpecialite:", $scope.nouvelleAffectation.idSpecialite);

            // Validation des champs obligatoires
            if (!$scope.nouvelleAffectation.idEtablissement || !$scope.nouvelleAffectation.idSpecialite) {
                alert("Veuillez sélectionner un établissement et une spécialité");
                return;
            }
            if (!$scope.nouvelleAffectation.dateDebut) {
                alert("Veuillez saisir une date de début");
                return;
            }

            // Validation des dates : date de fin doit être supérieure à date de début
            if ($scope.nouvelleAffectation.dateFin) {
                var dateDebut = new Date($scope.nouvelleAffectation.dateDebut);
                var dateFin = new Date($scope.nouvelleAffectation.dateFin);

                if (dateFin <= dateDebut) {
                    alert("La date de fin doit être supérieure à la date de début");
                    return;
                }
            }

            // Validation des doublons : vérifier qu'il n'existe pas déjà une affectation avec le même établissement et la même spécialité
            var doublonExiste = $scope.affectationsTemporaires.some(function(aff) {
                return aff.idEtablissement === $scope.nouvelleAffectation.idEtablissement &&
                       aff.idSpecialite === $scope.nouvelleAffectation.idSpecialite;
            });

            if (doublonExiste) {
                alert("Une affectation avec cet établissement et cette spécialité existe déjà pour ce professionnel");
                return;
            }

            // Récupérer les libellés pour l'affichage
            var etablissement = $scope.listeEtablissements.find(e => e.id == $scope.nouvelleAffectation.idEtablissement);
            var specialite = $scope.listeSpecialites.find(s => s.id == $scope.nouvelleAffectation.idSpecialite);

            // Créer l'objet affectation temporaire
            var affectationTemp = {
                idEtablissement: $scope.nouvelleAffectation.idEtablissement,
                idSpecialite: $scope.nouvelleAffectation.idSpecialite,
                dateDebut: $scope.nouvelleAffectation.dateDebut,
                dateFin: $scope.nouvelleAffectation.dateFin,
                etablissementLibelle: etablissement ? etablissement.libelleEtablissement + ' (' + etablissement.codeEtablissement + ')' : '',
                specialiteLibelle: specialite ? specialite.libelle : ''
            };

            // Ajouter au tableau
            $scope.affectationsTemporaires.push(affectationTemp);

            // Réinitialiser le formulaire
            $scope.nouvelleAffectation = {
                idProfessionnel: null,
                idEtablissement: null,
                idSpecialite: null,
                dateDebut: null,
                dateFin: null
            };

            // Réinitialiser les selects
            $('#selectEtablissementAffectation').val('').trigger('change');
            $('#selectSpecialiteAffectation').val('').trigger('change');

            console.log("Affectation temporaire ajoutée:", affectationTemp);
            console.log("Liste des affectations temporaires:", $scope.affectationsTemporaires);
        };

        // Retirer une affectation temporaire du tableau
        $scope.retirerAffectationTemporaire = function (index) {
            var affectation = $scope.affectationsTemporaires[index];

            // Si l'affectation a un ID, c'est qu'elle existe en base de données, il faut la supprimer
            if (affectation.id) {
                if (confirm("Voulez-vous vraiment supprimer cette affectation ?")) {
                    GenericService.delete(deleteAffectationURL + "/" + affectation.id)
                        .then(
                            function () {
                                $scope.affectationsTemporaires.splice(index, 1);
                                console.log("Affectation supprimée de la base de données");
                                alert("Affectation supprimée avec succès !");
                            },
                            function (error) {
                                console.error("Erreur lors de la suppression de l'affectation:", error);
                                alert("Erreur lors de la suppression de l'affectation");
                            }
                        );
                }
            } else {
                // Sinon, c'est une affectation temporaire, on la retire simplement du tableau
                $scope.affectationsTemporaires.splice(index, 1);
                console.log("Affectation temporaire retirée. Liste restante:", $scope.affectationsTemporaires);
            }
        };

        // Charger les affectations existantes d'un professionnel
        $scope.chargerAffectations = function (idProfessionnel) {
            console.log("Chargement des affectations pour le professionnel:", idProfessionnel);

            GenericService.get(listAffectationsByProfessionnelURL + "/" + idProfessionnel)
                .then(
                    function (data) {
                        console.log("Affectations chargées (data brute):", data);

                        // Le backend retourne directement un tableau, pas un objet avec listAffectation
                        var affectations = Array.isArray(data) ? data : [];

                        if (affectations.length > 0) {
                            // Transformer les affectations en format temporaire pour l'affichage
                            $scope.affectationsTemporaires = affectations.map(function(aff) {
                                // Récupérer les libellés
                                var etablissement = $scope.listeEtablissements.find(e => e.id == aff.idEtablissement);
                                var specialite = $scope.listeSpecialites.find(s => s.id == aff.idSpecialite);

                                return {
                                    id: aff.id, // Garder l'ID pour pouvoir supprimer
                                    idEtablissement: aff.idEtablissement,
                                    idSpecialite: aff.idSpecialite,
                                    dateDebut: aff.dateDebut,
                                    dateFin: aff.dateFin,
                                    etablissementLibelle: etablissement ? etablissement.libelleEtablissement + ' (' + etablissement.codeEtablissement + ')' : '',
                                    specialiteLibelle: specialite ? specialite.libelle : ''
                                };
                            });

                            console.log("Affectations temporaires chargées:", $scope.affectationsTemporaires);
                        } else {
                            $scope.affectationsTemporaires = [];
                            console.log("Aucune affectation trouvée pour ce professionnel");
                        }
                    },
                    function (error) {
                        console.error("Erreur lors du chargement des affectations:", error);
                        $scope.affectationsTemporaires = [];
                    }
                );
        };

        // Enregistrer un professionnel
        $scope.saveProfessionnel = function (professionnel) {
            console.log("Enregistrement du professionnel:", professionnel);

            // Validation de l'âge minimum (18 ans)
            if (professionnel.dateNaissance) {
                var dateNaissance = new Date(professionnel.dateNaissance);
                var today = new Date();
                var age = today.getFullYear() - dateNaissance.getFullYear();
                var monthDiff = today.getMonth() - dateNaissance.getMonth();

                // Ajuster l'âge si l'anniversaire n'est pas encore passé cette année
                if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < dateNaissance.getDate())) {
                    age--;
                }

                if (age < 18) {
                    alert("L'âge du professionnel doit être supérieur ou égal à 18 ans. Âge actuel: " + age + " ans");
                    return;
                }
            }

            GenericService.post(saveProfessionnelURL, professionnel)
                    .then(
                            function (data) {
                                console.log("Professionnel enregistré avec succès:", data);

                                // Si c'est un ajout (mode 0), enregistrer toutes les affectations temporaires
                                if ($scope.modeEdition === 0 && data.professionnelSanteDTO && data.professionnelSanteDTO.id) {
                                    var professionnelId = data.professionnelSanteDTO.id;
                                    var affectationsPromises = [];

                                    // Créer une promesse pour chaque affectation
                                    $scope.affectationsTemporaires.forEach(function(affTemp) {
                                        var affectation = {
                                            idProfessionnel: professionnelId,
                                            idSpecialite: affTemp.idSpecialite,
                                            idEtablissement: affTemp.idEtablissement,
                                            dateDebut: affTemp.dateDebut,
                                            dateFin: affTemp.dateFin
                                        };

                                        console.log("Enregistrement de l'affectation:", affectation);
                                        affectationsPromises.push(GenericService.post(saveAffectationURL, affectation));
                                    });

                                    // Attendre que toutes les affectations soient enregistrées
                                    Promise.all(affectationsPromises)
                                            .then(
                                                    function () {
                                                        console.log("Toutes les affectations ont été créées avec succès");
                                                        $('#detail-professionnel').modal('hide');
                                                        $scope.listeProfessionnels();
                                                        $scope.affectationsTemporaires = []; // Réinitialiser
                                                        alert("Professionnel et " + affectationsPromises.length + " affectation(s) ajoutés avec succès !");
                                                    },
                                                    function (error) {
                                                        console.error("Erreur lors de la création des affectations:", error);
                                                        $('#detail-professionnel').modal('hide');
                                                        $scope.listeProfessionnels();
                                                        $scope.affectationsTemporaires = []; // Réinitialiser
                                                        alert("Professionnel ajouté mais erreur lors de la création de certaines affectations");
                                                    }
                                            );
                                } else if ($scope.modeEdition === 3 && $scope.affectationsTemporaires.length > 0) {
                                    // Mode modification avec nouvelles affectations
                                    var professionnelId = professionnel.id;
                                    var affectationsPromises = [];

                                    // Créer une promesse pour chaque affectation
                                    $scope.affectationsTemporaires.forEach(function(affTemp) {
                                        var affectation = {
                                            idProfessionnel: professionnelId,
                                            idSpecialite: affTemp.idSpecialite,
                                            idEtablissement: affTemp.idEtablissement,
                                            dateDebut: affTemp.dateDebut,
                                            dateFin: affTemp.dateFin
                                        };

                                        console.log("Enregistrement de l'affectation:", affectation);
                                        affectationsPromises.push(GenericService.post(saveAffectationURL, affectation));
                                    });

                                    // Attendre que toutes les affectations soient enregistrées
                                    Promise.all(affectationsPromises)
                                            .then(
                                                    function () {
                                                        console.log("Toutes les affectations ont été créées avec succès");
                                                        $('#detail-professionnel').modal('hide');
                                                        $scope.listeProfessionnels();
                                                        $scope.affectationsTemporaires = []; // Réinitialiser
                                                        alert("Professionnel modifié et " + affectationsPromises.length + " affectation(s) ajoutées avec succès !");
                                                    },
                                                    function (error) {
                                                        console.error("Erreur lors de la création des affectations:", error);
                                                        $('#detail-professionnel').modal('hide');
                                                        $scope.listeProfessionnels();
                                                        $scope.affectationsTemporaires = []; // Réinitialiser
                                                        alert("Professionnel modifié mais erreur lors de la création de certaines affectations");
                                                    }
                                            );
                                } else {
                                    // Pas d'affectations à enregistrer
                                    $('#detail-professionnel').modal('hide');
                                    $scope.listeProfessionnels();
                                    if ($scope.modeEdition === 0) {
                                        alert("Professionnel ajouté avec succès !");
                                    } else {
                                        alert("Professionnel modifié avec succès !");
                                    }
                                }
                            },
                            function (error) {
                                console.error("Erreur lors de l'enregistrement du professionnel:", error);
                                if (error.data && error.data.message) {
                                    alert("Erreur: " + error.data.message);
                                } else {
                                    alert("Erreur lors de l'enregistrement du professionnel");
                                }
                            }
                    );
        };

        // Enregistrer et continuer
        $scope.saveProfessionnelEtContinuer = function (professionnel) {
            console.log("Enregistrement du professionnel et continuer:", professionnel);

            GenericService.post(saveProfessionnelURL, professionnel)
                    .then(
                            function (data) {
                                console.log("Professionnel enregistré avec succès:", data);

                                // Enregistrer toutes les affectations temporaires
                                if (data.professionnelSanteDTO && data.professionnelSanteDTO.id) {
                                    var professionnelId = data.professionnelSanteDTO.id;
                                    var affectationsPromises = [];

                                    // Créer une promesse pour chaque affectation
                                    $scope.affectationsTemporaires.forEach(function(affTemp) {
                                        var affectation = {
                                            idProfessionnel: professionnelId,
                                            idSpecialite: affTemp.idSpecialite,
                                            idEtablissement: affTemp.idEtablissement,
                                            dateDebut: affTemp.dateDebut,
                                            dateFin: affTemp.dateFin
                                        };

                                        console.log("Enregistrement de l'affectation:", affectation);
                                        affectationsPromises.push(GenericService.post(saveAffectationURL, affectation));
                                    });

                                    // Attendre que toutes les affectations soient enregistrées
                                    Promise.all(affectationsPromises)
                                            .then(
                                                    function () {
                                                        console.log("Toutes les affectations ont été créées avec succès");
                                                        $scope.listeProfessionnels();

                                                        // Réinitialiser le formulaire pour un nouveau professionnel
                                                        $scope.objetProfessionnel = angular.copy($scope.objetProfessionnelMaster);
                                                        $scope.affectationsTemporaires = [];
                                                        var currentYear = new Date().getFullYear();
                                                        $scope.objetProfessionnel.numeroOrdre = currentYear + "-XXX";
                                                        $scope.objetProfessionnel.dateEnregistrement = new Date();

                                                        var nbAffectations = affectationsPromises.length;
                                                        alert("Professionnel et " + nbAffectations + " affectation(s) ajoutés avec succès ! Vous pouvez en ajouter un autre.");
                                                    },
                                                    function (error) {
                                                        console.error("Erreur lors de la création des affectations:", error);
                                                        $scope.listeProfessionnels();

                                                        // Réinitialiser le formulaire malgré l'erreur
                                                        $scope.objetProfessionnel = angular.copy($scope.objetProfessionnelMaster);
                                                        $scope.affectationsTemporaires = [];
                                                        var currentYear = new Date().getFullYear();
                                                        $scope.objetProfessionnel.numeroOrdre = currentYear + "-XXX";
                                                        $scope.objetProfessionnel.dateEnregistrement = new Date();

                                                        alert("Professionnel ajouté mais erreur lors de la création de certaines affectations. Vous pouvez en ajouter un autre.");
                                                    }
                                            );
                                } else {
                                    // Pas d'affectations à enregistrer
                                    $scope.listeProfessionnels();

                                    // Réinitialiser le formulaire pour un nouveau professionnel
                                    $scope.objetProfessionnel = angular.copy($scope.objetProfessionnelMaster);
                                    $scope.affectationsTemporaires = [];
                                    var currentYear = new Date().getFullYear();
                                    $scope.objetProfessionnel.numeroOrdre = currentYear + "-XXX";
                                    $scope.objetProfessionnel.dateEnregistrement = new Date();

                                    alert("Professionnel ajouté avec succès ! Vous pouvez en ajouter un autre.");
                                }
                            },
                            function (error) {
                                console.error("Erreur lors de l'enregistrement du professionnel:", error);
                                if (error.data && error.data.message) {
                                    alert("Erreur: " + error.data.message);
                                } else {
                                    alert("Erreur lors de l'enregistrement du professionnel");
                                }
                            }
                    );
        };

        // Supprimer un professionnel
        $scope.deleteProfessionnel = function (id) {
            if (confirm("Êtes-vous sûr de vouloir supprimer ce professionnel de santé ?")) {
                GenericService.post(deleteProfessionnelURL + "?id=" + id, {})
                        .then(
                                function (data) {
                                    console.log("Professionnel supprimé avec succès:", data);
                                    $scope.listeProfessionnels();
                                    alert("Professionnel supprimé avec succès !");
                                },
                                function (error) {
                                    console.error("Erreur lors de la suppression du professionnel:", error);
                                    if (error.data && error.data.message) {
                                        alert("Erreur: " + error.data.message);
                                    } else {
                                        alert("Erreur lors de la suppression du professionnel");
                                    }
                                }
                        );
            }
        };

        // Détail d'un professionnel (lecture seule)
        $scope.detailProfessionnelV = function (id) {
            $scope.modeEdition = 1;
            $scope.titleModale = "Détail du Professionnel de Santé";
            
            GenericService.get(detailProfessionnelURL + "?id=" + id)
                    .then(
                            function (data) {
                                $scope.objetProfessionnel = data.professionnelSante;
                                
                                // Convertir les dates en objets Date pour AngularJS
                                if ($scope.objetProfessionnel.dateEnregistrement) {
                                    $scope.objetProfessionnel.dateEnregistrement = new Date($scope.objetProfessionnel.dateEnregistrement);
                                }
                                if ($scope.objetProfessionnel.dateNaissance) {
                                    $scope.objetProfessionnel.dateNaissance = new Date($scope.objetProfessionnel.dateNaissance);
                                }
                                
                                console.log("Professionnel chargé pour affichage:", $scope.objetProfessionnel);

                                // Charger les affectations du professionnel
                                $scope.chargerAffectations($scope.objetProfessionnel.id);

                                $('#detail-professionnel').modal('show');
                            },
                            function () {
                                console.error("Erreur lors du chargement du professionnel");
                            }
                    );
        };

        // ========== GESTION DES AFFECTATIONS ==========

        /**
         * Charger la liste des établissements
         */
        $scope.getListeEtablissements = function () {
            GenericService.get(listeEtablissementsURL + "?mc=")
                    .then(
                            function (data) {
                                $scope.listeEtablissements = data.listEtablissement;
                                console.log("Liste des établissements chargée:", $scope.listeEtablissements);

                                // Réinitialiser Select2 après le chargement des établissements
                                setTimeout(function() {
                                    initializeSelect2();
                                }, 100);
                            },
                            function () {
                                console.error("Erreur lors du chargement des établissements");
                            }
                    );
        };

        /**
         * Charger les affectations d'un professionnel
         */
        $scope.chargerAffectations = function (idProfessionnel) {
            if (!idProfessionnel) {
                $scope.listeAffectations = [];
                return;
            }

            GenericService.get(listAffectationsByProfessionnelURL + "/" + idProfessionnel)
                    .then(
                            function (data) {
                                $scope.listeAffectations = data;
                                console.log("Affectations chargées:", $scope.listeAffectations);
                            },
                            function () {
                                console.error("Erreur lors du chargement des affectations");
                                $scope.listeAffectations = [];
                            }
                    );
        };

        /**
         * Initialiser une nouvelle affectation
         */
        $scope.initialiserNouvelleAffectation = function () {
            var today = new Date();
            var dateStr = today.toISOString().split('T')[0];

            $scope.nouvelleAffectation = {
                idProfessionnel: $scope.objetProfessionnel.id,
                idEtablissement: null,
                idSpecialite: null,
                dateDebut: dateStr,
                dateFin: null
            };
        };

        /**
         * Ajouter une affectation
         */
        $scope.ajouterAffectation = function () {
            if (!$scope.nouvelleAffectation.idEtablissement || !$scope.nouvelleAffectation.idSpecialite) {
                alert("Veuillez remplir tous les champs obligatoires (Établissement et Spécialité)");
                return;
            }

            $scope.nouvelleAffectation.idProfessionnel = $scope.objetProfessionnel.id;

            // Convertir les dates en format ISO
            if ($scope.nouvelleAffectation.dateDebut) {
                $scope.nouvelleAffectation.dateDebut = new Date($scope.nouvelleAffectation.dateDebut).toISOString();
            }
            if ($scope.nouvelleAffectation.dateFin) {
                $scope.nouvelleAffectation.dateFin = new Date($scope.nouvelleAffectation.dateFin).toISOString();
            }

            GenericService.post(saveAffectationURL, $scope.nouvelleAffectation)
                    .then(
                            function (data) {
                                console.log("Affectation ajoutée avec succès:", data);
                                alert("Affectation ajoutée avec succès");

                                // Recharger les affectations
                                $scope.chargerAffectations($scope.objetProfessionnel.id);

                                // Réinitialiser le formulaire
                                $scope.initialiserNouvelleAffectation();
                            },
                            function (error) {
                                console.error("Erreur lors de l'ajout de l'affectation:", error);
                                alert("Erreur lors de l'ajout de l'affectation");
                            }
                    );
        };

        /**
         * Supprimer une affectation
         */
        $scope.supprimerAffectation = function (idAffectation) {
            if (!confirm("Êtes-vous sûr de vouloir supprimer cette affectation ?")) {
                return;
            }

            GenericService.delete(deleteAffectationURL + "/" + idAffectation)
                    .then(
                            function (data) {
                                console.log("Affectation supprimée avec succès");
                                alert("Affectation supprimée avec succès");

                                // Recharger les affectations
                                $scope.chargerAffectations($scope.objetProfessionnel.id);
                            },
                            function (error) {
                                console.error("Erreur lors de la suppression de l'affectation:", error);
                                alert("Erreur lors de la suppression de l'affectation");
                            }
                    );
        };

        // Charger les données au démarrage
        $scope.getListeEtablissements();
        $scope.initialiserNouvelleAffectation();

    }]);

