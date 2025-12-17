'use strict';
var App;

App.controller('antecedentController', ['$scope', '$http', '$location', '$rootScope', 'PropagationService', 'GenericService', function($scope, $http, $location, $rootScope, PropagationService, GenericService) {

    // URLs de l'API
    const urlBase = appUrl + "api/antecedent";
    const listCategoriesAntecedentURL = urlBase + "/listCategoriesAntecedent";
    const listTypesAntecedentByCategorieURL = urlBase + "/listTypesAntecedentByCategorie";
    const listAntecedentsByTypeURL = urlBase + "/listAntecedentsByType";
    const listAntecedentsPatientURL = urlBase + "/listAntecedentsPatient";
    const saveAntecedentURL = urlBase + "/saveAntecedent";
    const deleteAntecedentURL = urlBase + "/deleteAntecedent";

    // Initialisation des données
    $scope.patient = {};
    $scope.antecedents = [];
    $scope.listeCategoriesAntecedent = [];
    $scope.listeTypesAntecedent = [];
    $scope.listeTypesAntecedentModal = [];
    $scope.listeAntecedentsModal = [];
    $scope.historique = [];

    // Mode édition
    $scope.modeEdition = 0; // 0 = ajout, 3 = modification
    $scope.titleModale = "Ajouter un antécédent médical";
    
    // Onglets
    $scope.activeTab = 'antecedents';
    
    // Filtres
    $scope.selectedCategory = 'all';
    $scope.searchTerm = '';
    
    // Nouvel antécédent
    $scope.nouvelAntecedent = {
        categorieAntecedent: null,
        typeAntecedent: null,
        antecedent: null,
        description: '',
        dateDebut: new Date(),
        dateFin: null,
        statut: 'Actif',
        traitementSuivi: ''
    };

    // Objet antécédent pour le modal
    $scope.objetAntecedent = {
        categorieAntecedent: '',
        typeAntecedent: '',
        antecedent: '',
        description: '',
        dateDebut: '',
        dateFin: '',
        statut: 'Actif',
        traitementSuivi: ''
    };

    // ==================== INITIALISATION ====================

    $scope.init = function() {
        console.log("Initialisation du controleur antecedentController");

        // Initialiser les erreurs de formulaire
        $scope.formErrors = {};

        // Initialiser l'onglet actif
        $scope.activeTab = 'antecedents';

        // Récupérer le patient depuis le service de propagation
        var patientFromService = PropagationService.getPatientSender();
        if (patientFromService && patientFromService.id) {
            $scope.patient = patientFromService;
            console.log("Patient recupere depuis PropagationService:", $scope.patient);
            console.log("   - Nom complet: " + $scope.patient.firstName + " " + $scope.patient.lastName);
            console.log("   - N° Carnet: " + $scope.patient.numeroCarnet);
            console.log("   - Groupe sanguin: " + $scope.patient.groupeSanguinLibelle);

            // Charger les antécédents du patient
            $scope.chargerAntecedentsPatient($scope.patient.id);
            $scope.chargerHistorique($scope.patient.id);
        } else {
            console.warn("Aucun patient trouve dans PropagationService");
            console.log("Donnees recues:", patientFromService);
        }

        // Écouter les changements de patient
        $rootScope.$on("patientSender", function () {
            var updatedPatient = PropagationService.getPatientSender();
            if (updatedPatient && updatedPatient.id) {
                $scope.patient = updatedPatient;
                console.log("Patient mis a jour:", $scope.patient);
                $scope.chargerAntecedentsPatient($scope.patient.id);
                $scope.chargerHistorique($scope.patient.id);
            }
        });

        // Charger les catégories d'antécédents
        $scope.chargerCategoriesAntecedent();
    };

    // ==================== CHARGEMENT DES DONNÉES ====================

    // Fonction pour initialiser Select2 sur les selects d'antécédent
    function initializeSelect2Antecedent() {
        // Détruire les instances existantes si elles existent
        try {
            if ($('#selectCategorieAntecedent').hasClass('select2-hidden-accessible')) {
                $('#selectCategorieAntecedent').select2('destroy');
            }
            if ($('#selectTypeAntecedent').hasClass('select2-hidden-accessible')) {
                $('#selectTypeAntecedent').select2('destroy');
            }
            if ($('#selectAntecedent').hasClass('select2-hidden-accessible')) {
                $('#selectAntecedent').select2('destroy');
            }
        } catch(e) {
            console.log("Pas d'instance Select2 a detruire");
        }

        // Initialiser Select2 pour la catégorie
        $('#selectCategorieAntecedent').select2({
            placeholder: "-- Sélectionner une catégorie --",
            allowClear: true,
            language: "fr",
            dropdownParent: $('#detail-antecedent')
        }).on('change', function() {
            $scope.$apply(function() {
                $scope.objetAntecedent.categorieAntecedent = $('#selectCategorieAntecedent').val();
                $scope.onCategorieAntecedentChangeModal();
            });
        });

        // Initialiser Select2 pour le type
        $('#selectTypeAntecedent').select2({
            placeholder: "-- Sélectionner un type --",
            allowClear: true,
            language: "fr",
            dropdownParent: $('#detail-antecedent')
        }).on('change', function() {
            $scope.$apply(function() {
                $scope.objetAntecedent.typeAntecedent = $('#selectTypeAntecedent').val();
                $scope.onTypeAntecedentChangeModal();
            });
        });

        // Initialiser Select2 pour l'antécédent
        $('#selectAntecedent').select2({
            placeholder: "-- Sélectionner un antécédent --",
            allowClear: true,
            language: "fr",
            dropdownParent: $('#detail-antecedent')
        }).on('change', function() {
            $scope.$apply(function() {
                $scope.objetAntecedent.antecedent = $('#selectAntecedent').val();
            });
        });

        console.log("Select2 initialises pour les antecedents");
    }

    // Charger les catégories d'antécédents
    $scope.chargerCategoriesAntecedent = function() {
        console.log("Chargement des categories depuis:", listCategoriesAntecedentURL);
        GenericService.get(listCategoriesAntecedentURL).then(function(response) {
            console.log("Reponse recue:", response);
            if (response && response.listCategoriesAntecedent) {
                $scope.listeCategoriesAntecedent = response.listCategoriesAntecedent;
                console.log("Categories d'antecedents chargees:", $scope.listeCategoriesAntecedent.length);
                console.log("Categories:", $scope.listeCategoriesAntecedent);
            } else {
                console.error("Format de reponse inattendu pour les categories");
                console.error("Reponse complete:", JSON.stringify(response));
            }
        }, function(error) {
            console.error("Erreur lors du chargement des categories d'antecedents:", error);
        });
    };

    // Charger les types d'antécédents par catégorie
    $scope.onCategorieAntecedentChange = function() {
        if ($scope.nouvelAntecedent.categorieAntecedent && $scope.nouvelAntecedent.categorieAntecedent.id) {
            var url = listTypesAntecedentByCategorieURL + "?idCategorie=" + $scope.nouvelAntecedent.categorieAntecedent.id;

            GenericService.get(url).then(function(response) {
                if (response && response.listCategoriesAntecedent) {
                    $scope.listeTypesAntecedent = response.listCategoriesAntecedent;
                    console.log("Types d'antecedents charges:", $scope.listeTypesAntecedent.length);
                } else {
                    console.error("Format de reponse inattendu pour les types");
                }
            }, function(error) {
                console.error("Erreur lors du chargement des types d'antecedents:", error);
            });
        } else {
            $scope.listeTypesAntecedent = [];
            $scope.nouvelAntecedent.typeAntecedent = null;
        }
    };

    // Charger les antécédents du patient
    $scope.chargerAntecedentsPatient = function(patientId) {
        console.log("Chargement des antecedents pour le patient ID:", patientId);
        var url = listAntecedentsPatientURL + "?idPatient=" + patientId;
        console.log("URL:", url);

        $http.get(url).then(function(response) {
            console.log("Reponse antecedents patient:", response);
            if (response && response.data) {
                $scope.antecedents = response.data;
                console.log("Antecedents du patient charges:", $scope.antecedents.length);
                console.log("Liste des antecedents:", $scope.antecedents);
            }
        }, function(error) {
            console.error("Erreur lors du chargement des antecedents du patient:", error);
            $scope.antecedents = [];
        });
    };

    // Charger l'historique
    $scope.chargerHistorique = function(patientId) {
        var url = urlBase + "/historique?idPatient=" + patientId;

        $http.get(url).then(function(response) {
            if (response && response.data) {
                $scope.historique = response.data;
                console.log("Historique charge:", $scope.historique.length);
            }
        }, function(error) {
            console.error("Erreur lors du chargement de l'historique:", error);
            $scope.historique = [];
        });
    };

    // ==================== GESTION DES ONGLETS ====================
    
    $scope.setActiveTab = function(tab) {
        $scope.activeTab = tab;
    };

    // ==================== FILTRAGE ====================
    
    $scope.setCategory = function(categoryId) {
        $scope.selectedCategory = categoryId;
    };

    $scope.getFilteredAntecedents = function() {
        var filtered = $scope.antecedents;

        // Filtrer par catégorie
        if ($scope.selectedCategory !== 'all') {
            filtered = filtered.filter(function(ant) {
                return ant.categorieAntecedent && ant.categorieAntecedent.id == $scope.selectedCategory;
            });
        }

        // Filtrer par recherche
        if ($scope.searchTerm && $scope.searchTerm.trim() !== '') {
            var searchLower = $scope.searchTerm.toLowerCase();
            filtered = filtered.filter(function(ant) {
                return (ant.typeAntecedent && ant.typeAntecedent.libelle && ant.typeAntecedent.libelle.toLowerCase().indexOf(searchLower) !== -1) ||
                       (ant.description && ant.description.toLowerCase().indexOf(searchLower) !== -1) ||
                       (ant.categorieAntecedent && ant.categorieAntecedent.libelle && ant.categorieAntecedent.libelle.toLowerCase().indexOf(searchLower) !== -1);
            });
        }

        return filtered;
    };

    $scope.getCategoryCount = function(categoryId) {
        if (categoryId === 'all') {
            return $scope.antecedents.length;
        }

        return $scope.antecedents.filter(function(ant) {
            return ant.categorieAntecedent && ant.categorieAntecedent.id == categoryId;
        }).length;
    };

    // ==================== GESTION DES ANTÉCÉDENTS ====================

    // Ouvrir le modal d'ajout
    $scope.openAddModal = function() {
        console.log("openAddModal appelee");
        console.log("jQuery disponible?", typeof $ !== 'undefined');
        console.log("Modal existe?", $('#detail-antecedent').length);

        $scope.modeEdition = 0;
        $scope.titleModale = "Ajouter un antécédent médical";

        // Réinitialiser l'objet antécédent
        $scope.objetAntecedent = {
            categorieAntecedent: '',
            typeAntecedent: '',
            antecedent: '',
            description: '',
            dateDebut: '',
            dateFin: '',
            statut: 'Actif',
            traitementSuivi: ''
        };

        // Réinitialiser les listes
        $scope.listeTypesAntecedentModal = [];
        $scope.listeAntecedentsModal = [];

        // Ouvrir le modal
        try {
            $('#detail-antecedent').modal('show');
            console.log("Modal d'ajout ouverte");

            // Initialiser Select2 après l'ouverture du modal
            setTimeout(function() {
                initializeSelect2Antecedent();
            }, 300);
        } catch(e) {
            console.error("Erreur lors de l'ouverture du modal:", e);
        }
    };

    // Fermer le modal
    $scope.closeModalAntecedent = function() {
        $('#detail-antecedent').modal('hide');
        console.log("Modal fermee");
    };

    // Charger les types d'antécédents par catégorie (pour le modal)
    $scope.onCategorieAntecedentChangeModal = function() {
        console.log("Categorie changee:", $scope.objetAntecedent.categorieAntecedent);

        if ($scope.objetAntecedent.categorieAntecedent) {
            var url = listTypesAntecedentByCategorieURL + "?idCategorie=" + $scope.objetAntecedent.categorieAntecedent;

            GenericService.get(url).then(function(response) {
                if (response && response.listTypesAntecedent) {
                    $scope.listeTypesAntecedentModal = response.listTypesAntecedent;
                    console.log("Types d'antecedents charges pour le modal:", $scope.listeTypesAntecedentModal.length);

                    // Réinitialiser Select2 pour le select Type après chargement des données
                    $scope.$applyAsync(function() {
                        setTimeout(function() {
                            $('#selectTypeAntecedent').select2('destroy');
                            $('#selectTypeAntecedent').select2({
                                placeholder: "-- Sélectionner un type --",
                                allowClear: true,
                                language: "fr",
                                dropdownParent: $('#detail-antecedent')
                            }).on('change', function() {
                                $scope.$apply(function() {
                                    $scope.objetAntecedent.typeAntecedent = $('#selectTypeAntecedent').val();
                                    $scope.onTypeAntecedentChangeModal();
                                });
                            });
                            console.log("Select2 reinitialise pour les types");
                        }, 100);
                    });
                } else {
                    console.error("Format de reponse inattendu pour les types");
                }
            }, function(error) {
                console.error("Erreur lors du chargement des types d'antecedents:", error);
            });
        } else {
            $scope.listeTypesAntecedentModal = [];
            $scope.objetAntecedent.typeAntecedent = '';
        }

        // Réinitialiser les antécédents
        $scope.listeAntecedentsModal = [];
        $scope.objetAntecedent.antecedent = '';
    };

    // Charger les antécédents par type (pour le modal)
    $scope.onTypeAntecedentChangeModal = function() {
        console.log("Type change:", $scope.objetAntecedent.typeAntecedent);

        if ($scope.objetAntecedent.typeAntecedent) {
            var url = listAntecedentsByTypeURL + "?idTypeAntecedent=" + $scope.objetAntecedent.typeAntecedent;

            GenericService.get(url).then(function(response) {
                if (response && response.listAntecedents) {
                    $scope.listeAntecedentsModal = response.listAntecedents;
                    console.log("Antecedents charges pour le modal:", $scope.listeAntecedentsModal.length);

                    // Réinitialiser Select2 pour le select Antécédent après chargement des données
                    $scope.$applyAsync(function() {
                        setTimeout(function() {
                            $('#selectAntecedent').select2('destroy');
                            $('#selectAntecedent').select2({
                                placeholder: "-- Sélectionner un antécédent --",
                                allowClear: true,
                                language: "fr",
                                dropdownParent: $('#detail-antecedent')
                            }).on('change', function() {
                                $scope.$apply(function() {
                                    $scope.objetAntecedent.antecedent = $('#selectAntecedent').val();
                                });
                            });
                            console.log("Select2 reinitialise pour les antecedents");
                        }, 100);
                    });
                } else {
                    console.error("Format de reponse inattendu pour les antecedents");
                }
            }, function(error) {
                console.error("Erreur lors du chargement des antecedents:", error);
            });
        } else {
            $scope.listeAntecedentsModal = [];
            $scope.objetAntecedent.antecedent = '';
        }
    };

    // Sauvegarder un antécédent depuis le modal
    $scope.saveAntecedent = function(objetAntecedent) {
        // Protection contre les doubles clics
        if ($scope.isSaving) {
            console.log("Sauvegarde deja en cours, requete ignoree");
            return;
        }

        // Réinitialiser les erreurs
        $scope.formErrors = {};

        // Validation des champs obligatoires
        if (!objetAntecedent.categorieAntecedent || !objetAntecedent.typeAntecedent || !objetAntecedent.antecedent) {
            if (!objetAntecedent.categorieAntecedent) {
                $scope.formErrors.categorieAntecedent = "Veuillez selectionner une categorie";
            }
            if (!objetAntecedent.typeAntecedent) {
                $scope.formErrors.typeAntecedent = "Veuillez selectionner un type";
            }
            if (!objetAntecedent.antecedent) {
                $scope.formErrors.antecedent = "Veuillez selectionner un antecedent";
            }
            console.error("Validation echouee: Veuillez remplir tous les champs obligatoires");
            return;
        }

        if (!objetAntecedent.description || objetAntecedent.description.trim() === '') {
            $scope.formErrors.description = "Veuillez saisir une description";
            console.error("Validation echouee: Veuillez saisir une description");
            return;
        }

        if (!objetAntecedent.dateDebut) {
            $scope.formErrors.dateDebut = "Veuillez saisir une date de debut";
            console.error("Validation echouee: Veuillez saisir une date de debut");
            return;
        }

        // Validation des dates
        var today = new Date();
        today.setHours(0, 0, 0, 0);

        if (objetAntecedent.dateDebut) {
            var dateDebut = new Date(objetAntecedent.dateDebut);
            dateDebut.setHours(0, 0, 0, 0);

            if (dateDebut > today) {
                $scope.formErrors.dateDebut = "La date de debut ne peut pas etre superieure a aujourd'hui";
                console.error("Validation echouee: La date de debut ne peut pas etre superieure a la date d'aujourd'hui");
                return;
            }
        }

        if (objetAntecedent.dateFin) {
            var dateFin = new Date(objetAntecedent.dateFin);
            dateFin.setHours(0, 0, 0, 0);

            if (dateFin > today) {
                $scope.formErrors.dateFin = "La date de fin ne peut pas etre superieure a aujourd'hui";
                console.error("Validation echouee: La date de fin ne peut pas etre superieure a la date d'aujourd'hui");
                return;
            }
        }

        if (objetAntecedent.dateDebut && objetAntecedent.dateFin) {
            var dateDebut = new Date(objetAntecedent.dateDebut);
            var dateFin = new Date(objetAntecedent.dateFin);
            dateDebut.setHours(0, 0, 0, 0);
            dateFin.setHours(0, 0, 0, 0);

            if (dateDebut.getTime() === dateFin.getTime()) {
                $scope.formErrors.dateFin = "Les dates de debut et de fin ne peuvent pas etre identiques";
                console.error("Validation echouee: La date de debut et la date de fin ne peuvent pas etre identiques");
                return;
            }

            if (dateFin < dateDebut) {
                $scope.formErrors.dateFin = "La date de fin ne peut pas etre anterieure a la date de debut";
                console.error("Validation echouee: La date de fin ne peut pas etre anterieure a la date de debut");
                return;
            }
        }

        // Préparer les données
        var antecedent = {
            idPatient: $scope.patient.id,
            categorieAntecedent: objetAntecedent.categorieAntecedent,
            typeAntecedent: objetAntecedent.typeAntecedent,
            antecedent: objetAntecedent.antecedent,
            description: objetAntecedent.description,
            dateDebut: new Date(objetAntecedent.dateDebut).toISOString(),
            dateFin: objetAntecedent.dateFin ? new Date(objetAntecedent.dateFin).toISOString() : null,
            statut: objetAntecedent.statut || 'Actif',
            traitementSuivi: objetAntecedent.traitementSuivi || ''
        };

        // Si mode édition, ajouter l'ID
        if ($scope.modeEdition === 3 && objetAntecedent.id) {
            antecedent.id = objetAntecedent.id;
        }

        var actionMessage = $scope.modeEdition === 3 ? "modification" : "ajout";
        console.log("Envoi de l'antecedent (" + actionMessage + "):", antecedent);

        // Marquer comme en cours de sauvegarde
        $scope.isSaving = true;

        $http.post(saveAntecedentURL, antecedent).then(function(response) {
            console.log("Antecedent " + ($scope.modeEdition === 3 ? "modifie" : "ajoute") + " avec succes");
            $scope.chargerAntecedentsPatient($scope.patient.id);
            $scope.closeModalAntecedent();
        }, function(error) {
            console.error("Erreur lors de " + actionMessage + " de l'antecedent:", error);
        }).finally(function() {
            // Réinitialiser le flag de sauvegarde
            $scope.isSaving = false;
        });
    };

    // Sauvegarder et continuer
    $scope.saveAntecedentEtContinuer = function(objetAntecedent) {
        // Validation
        if (!objetAntecedent.categorieAntecedent || !objetAntecedent.typeAntecedent || !objetAntecedent.antecedent) {
            console.error("Validation echouee: Veuillez selectionner une categorie, un type et un antecedent");
            return;
        }

        if (!objetAntecedent.description || objetAntecedent.description.trim() === '') {
            console.error("Validation echouee: Veuillez saisir une description");
            return;
        }

        if (!objetAntecedent.dateDebut) {
            console.error("Validation echouee: Veuillez saisir une date de debut");
            return;
        }

        // Préparer les données
        var antecedent = {
            idPatient: $scope.patient.id,
            categorieAntecedent: objetAntecedent.categorieAntecedent,
            typeAntecedent: objetAntecedent.typeAntecedent,
            antecedent: objetAntecedent.antecedent,
            description: objetAntecedent.description,
            dateDebut: new Date(objetAntecedent.dateDebut).toISOString(),
            dateFin: objetAntecedent.dateFin ? new Date(objetAntecedent.dateFin).toISOString() : null,
            statut: objetAntecedent.statut || 'Actif',
            traitementSuivi: objetAntecedent.traitementSuivi || ''
        };

        console.log("Envoi de l'antecedent:", antecedent);

        $http.post(saveAntecedentURL, antecedent).then(function(response) {
            console.log("Antecedent ajoute avec succes");
            $scope.chargerAntecedentsPatient($scope.patient.id);

            // Réinitialiser le formulaire mais garder le modal ouvert
            $scope.objetAntecedent = {
                categorieAntecedent: '',
                typeAntecedent: '',
                antecedent: '',
                description: '',
                dateDebut: '',
                dateFin: '',
                statut: 'Actif',
                traitementSuivi: ''
            };
            $scope.listeTypesAntecedentModal = [];
            $scope.listeAntecedentsModal = [];
        }, function(error) {
            console.error("Erreur lors de l'ajout de l'antecedent:", error);
        });
    };

    $scope.addAntecedent = function() {
        if (!$scope.nouvelAntecedent.categorieAntecedent || !$scope.nouvelAntecedent.typeAntecedent) {
            console.error("Validation echouee: Veuillez selectionner une categorie et un type d'antecedent");
            return;
        }

        if (!$scope.nouvelAntecedent.description || $scope.nouvelAntecedent.description.trim() === '') {
            console.error("Validation echouee: Veuillez saisir une description");
            return;
        }

        var antecedent = {
            idPatient: $scope.patient.id,
            idCategorieAntecedent: $scope.nouvelAntecedent.categorieAntecedent.id,
            idTypeAntecedent: $scope.nouvelAntecedent.typeAntecedent.id,
            description: $scope.nouvelAntecedent.description,
            dateDebut: $scope.nouvelAntecedent.dateDebut,
            dateFin: $scope.nouvelAntecedent.dateFin,
            statut: $scope.nouvelAntecedent.statut,
            traitementSuivi: $scope.nouvelAntecedent.traitementSuivi
        };

        $http.post(saveAntecedentURL, antecedent).then(function(response) {
            console.log("Antecedent ajoute avec succes");
            $scope.chargerAntecedentsPatient($scope.patient.id);
            $scope.chargerHistorique($scope.patient.id);
            $scope.resetNouvelAntecedent();
            // TODO: Fermer la modal
        }, function(error) {
            console.error("Erreur lors de l'ajout de l'antecedent:", error);
        });
    };

    $scope.editAntecedent = function(antecedent) {
        console.log("=== DEBUT EDITION ANTECEDENT ===");
        console.log("Antecedent recu:", antecedent);
        console.log("Categorie:", antecedent.categorieAntecedent);
        console.log("Type:", antecedent.typeAntecedent);
        console.log("Antecedent detail:", antecedent.antecedent);

        $scope.modeEdition = 3;
        $scope.titleModale = "Modifier un antécédent médical";

        // Remplir l'objet avec les données de l'antécédent à modifier
        $scope.objetAntecedent = {
            id: antecedent.id,
            categorieAntecedent: (antecedent.categorieAntecedent && antecedent.categorieAntecedent.id) ? antecedent.categorieAntecedent.id.toString() : '',
            typeAntecedent: (antecedent.typeAntecedent && antecedent.typeAntecedent.id) ? antecedent.typeAntecedent.id.toString() : '',
            antecedent: (antecedent.antecedent && antecedent.antecedent.id) ? antecedent.antecedent.id.toString() : '',
            description: antecedent.description || '',
            dateDebut: antecedent.dateDebut ? new Date(antecedent.dateDebut) : null,
            dateFin: antecedent.dateFin ? new Date(antecedent.dateFin) : null,
            statut: antecedent.statut || 'Actif',
            traitementSuivi: antecedent.traitementSuivi || ''
        };

        console.log("Objet antecedent prepare:", $scope.objetAntecedent);

        // Charger les types pour la catégorie sélectionnée
        if ($scope.objetAntecedent.categorieAntecedent) {
            var urlTypes = listTypesAntecedentByCategorieURL + "?idCategorie=" + $scope.objetAntecedent.categorieAntecedent;
            console.log("Chargement des types depuis:", urlTypes);
            GenericService.get(urlTypes).then(function(response) {
                console.log("Reponse types:", response);
                if (response && response.listTypesAntecedent) {
                    $scope.listeTypesAntecedentModal = response.listTypesAntecedent;
                    console.log("Types charges pour edition:", $scope.listeTypesAntecedentModal.length);

                    // Réinitialiser Select2 pour les types
                    $scope.$applyAsync(function() {
                        setTimeout(function() {
                            $('#selectTypeAntecedent').select2('destroy');
                            $('#selectTypeAntecedent').select2({
                                placeholder: "-- Sélectionner un type --",
                                allowClear: true,
                                language: "fr",
                                dropdownParent: $('#detail-antecedent')
                            }).val($scope.objetAntecedent.typeAntecedent).trigger('change.select2');
                        }, 100);
                    });
                }
            });
        }

        // Charger les antécédents pour le type sélectionné
        if ($scope.objetAntecedent.typeAntecedent) {
            var urlAntecedents = listAntecedentsByTypeURL + "?idTypeAntecedent=" + $scope.objetAntecedent.typeAntecedent;
            console.log("Chargement des antecedents depuis:", urlAntecedents);
            GenericService.get(urlAntecedents).then(function(response) {
                console.log("Reponse antecedents:", response);
                if (response && response.listAntecedents) {
                    $scope.listeAntecedentsModal = response.listAntecedents;
                    console.log("Antecedents charges pour edition:", $scope.listeAntecedentsModal.length);

                    // Réinitialiser Select2 pour les antécédents
                    $scope.$applyAsync(function() {
                        setTimeout(function() {
                            $('#selectAntecedent').select2('destroy');
                            $('#selectAntecedent').select2({
                                placeholder: "-- Sélectionner un antécédent --",
                                allowClear: true,
                                language: "fr",
                                dropdownParent: $('#detail-antecedent')
                            }).val($scope.objetAntecedent.antecedent).trigger('change.select2');
                        }, 100);
                    });
                }
            });
        }

        // Ouvrir le modal
        try {
            $('#detail-antecedent').modal('show');
            console.log("Modal d'edition ouverte");

            // Initialiser Select2 après l'ouverture du modal
            setTimeout(function() {
                initializeSelect2Antecedent();

                // Pré-sélectionner les valeurs dans Select2
                $('#selectCategorieAntecedent').val($scope.objetAntecedent.categorieAntecedent).trigger('change.select2');
            }, 300);
        } catch(e) {
            console.error("Erreur lors de l'ouverture du modal d'edition:", e);
        }

        console.log("=== FIN EDITION ANTECEDENT ===");
    };

    $scope.deleteAntecedent = function(antecedentId) {
        if (!confirm("Etes-vous sur de vouloir supprimer cet antecedent ?")) {
            return;
        }

        var url = deleteAntecedentURL + "?id=" + antecedentId;

        $http.delete(url).then(function(response) {
            console.log("Antecedent supprime avec succes");
            $scope.chargerAntecedentsPatient($scope.patient.id);
            $scope.chargerHistorique($scope.patient.id);
        }, function(error) {
            console.error("Erreur lors de la suppression de l'antecedent:", error);
        });
    };

    $scope.resetNouvelAntecedent = function() {
        $scope.nouvelAntecedent = {
            categorieAntecedent: null,
            typeAntecedent: null,
            description: '',
            dateDebut: new Date(),
            dateFin: null,
            statut: 'Actif',
            traitementSuivi: ''
        };
        $scope.listeTypesAntecedent = [];
    };

    // ==================== FONCTIONS UTILITAIRES ====================
    
    $scope.getCategoryClass = function(categoryCode) {
        return 'badge-' + categoryCode;
    };

    $scope.getStatusClass = function(statut) {
        return 'status-' + statut;
    };

    $scope.getActionClass = function(actionType) {
        return 'action-' + actionType;
    };

    $scope.getActionIcon = function(actionType) {
        var icons = {
            'add': 'fa-plus',
            'edit': 'fa-edit',
            'delete': 'fa-trash'
        };
        return icons[actionType] || 'fa-info';
    };

}]);

