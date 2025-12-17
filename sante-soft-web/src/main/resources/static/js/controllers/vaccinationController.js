App.controller('vaccinationController', function ($scope, $http, $location, PropagationService) {
    console.log('💉 Contrôleur vaccinationController initialisé');

    // URLs de l'API
    var appUrl = window.location.origin + '/sante-start-up/';
    var urlBase = appUrl + 'api/vaccination';
    var saveVaccinationURL = urlBase + '/saveOrUpdateVaccination';
    var deleteVaccinationURL = urlBase + '/deleteVaccination';
    var listeVaccinationsURL = urlBase + '/listVaccinationsByPatient';

    // Initialisation
    $scope.patient = {};
    $scope.vaccinations = [];
    $scope.searchTerm = '';
    $scope.modeEdition = 0; // 0 = ajout, 3 = modification
    $scope.titleModale = "Ajouter une vaccination";
    $scope.isSaving = false;
    $scope.formErrors = {};

    // Récupérer l'ID du patient depuis l'URL
    var urlParams = new URLSearchParams(window.location.search);
    var patientId = urlParams.get('idPatient');

    console.log('📋 ID Patient:', patientId);

    // Si pas d'ID patient, essayer de récupérer depuis PropagationService
    if (!patientId) {
        var patientFromService = PropagationService.getPatientSender();
        if (patientFromService && patientFromService.id) {
            patientId = patientFromService.id;
            $scope.patient = patientFromService;
            console.log('✅ Patient récupéré depuis PropagationService:', $scope.patient);
        } else {
            console.error('❌ Aucun patient sélectionné');
            alert('Aucun patient sélectionné. Redirection vers la liste des patients.');
            window.location.href = appUrl + 'gestion/patient';
            return;
        }
    }

    // Charger les informations du patient si on a seulement l'ID
    if (patientId && !$scope.patient.id) {
        $http.get(appUrl + 'api/patient/getPatient?id=' + patientId)
            .then(function(response) {
                if (response.data && response.data.patient) {
                    $scope.patient = response.data.patient;
                    console.log('✅ Patient chargé:', $scope.patient);
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur chargement patient:', error);
            });
    }

    // Charger les vaccinations du patient
    $scope.chargerVaccinations = function() {
        if (!patientId) return;

        console.log('📥 Chargement des vaccinations...');
        
        $http.get(listeVaccinationsURL + '?idPatient=' + patientId)
            .then(function(response) {
                if (response.data && response.data.listVaccinations) {
                    $scope.vaccinations = response.data.listVaccinations;
                    console.log('✅ Vaccinations chargées:', $scope.vaccinations.length);
                } else {
                    $scope.vaccinations = [];
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur chargement vaccinations:', error);
                $scope.vaccinations = [];
            });
    };

    // Filtrer les vaccinations
    $scope.getFilteredVaccinations = function() {
        if (!$scope.searchTerm) {
            return $scope.vaccinations;
        }

        var searchLower = $scope.searchTerm.toLowerCase();
        return $scope.vaccinations.filter(function(v) {
            return (v.vaccin && v.vaccin.toLowerCase().indexOf(searchLower) !== -1) ||
                   (v.observations && v.observations.toLowerCase().indexOf(searchLower) !== -1) ||
                   (v.lot && v.lot.toLowerCase().indexOf(searchLower) !== -1);
        });
    };

    // Ouvrir le modal d'ajout
    $scope.openAddModal = function() {
        console.log('➕ Ouverture modal ajout');
        
        $scope.modeEdition = 0;
        $scope.titleModale = "Ajouter une vaccination";
        $scope.formErrors = {};

        // Réinitialiser l'objet vaccination
        $scope.objetVaccination = {
            idPatient: patientId,
            vaccin: '',
            dateVaccination: '',
            lot: '',
            prochainRappel: '',
            statut: 'À-jour',
            professionnelSante: '',
            observations: ''
        };

        $('#detail-vaccination').modal('show');
    };

    // Éditer une vaccination
    $scope.editVaccination = function(vaccination) {
        console.log('✏️ Édition vaccination:', vaccination);
        
        $scope.modeEdition = 3;
        $scope.titleModale = "Modifier une vaccination";
        $scope.formErrors = {};

        // Copier l'objet vaccination
        $scope.objetVaccination = angular.copy(vaccination);

        // Convertir les dates pour l'input date
        if ($scope.objetVaccination.dateVaccination) {
            var date = new Date($scope.objetVaccination.dateVaccination);
            $scope.objetVaccination.dateVaccination = date.toISOString().split('T')[0];
        }
        if ($scope.objetVaccination.prochainRappel) {
            var rappel = new Date($scope.objetVaccination.prochainRappel);
            $scope.objetVaccination.prochainRappel = rappel.toISOString().split('T')[0];
        }

        $('#detail-vaccination').modal('show');
    };

    // Valider le formulaire
    $scope.validateForm = function(vaccination) {
        $scope.formErrors = {};
        var isValid = true;

        if (!vaccination.vaccin || vaccination.vaccin.trim() === '') {
            $scope.formErrors.vaccin = 'Le nom du vaccin est obligatoire';
            isValid = false;
        }

        if (!vaccination.dateVaccination) {
            $scope.formErrors.dateVaccination = 'La date de vaccination est obligatoire';
            isValid = false;
        }

        if (!vaccination.statut) {
            $scope.formErrors.statut = 'Le statut est obligatoire';
            isValid = false;
        }

        return isValid;
    };

    // Sauvegarder une vaccination
    $scope.saveVaccination = function(vaccination) {
        console.log('💾 Sauvegarde vaccination:', vaccination);

        // Valider le formulaire
        if (!$scope.validateForm(vaccination)) {
            console.warn('⚠️ Formulaire invalide');
            return;
        }

        $scope.isSaving = true;

        $http.post(saveVaccinationURL, vaccination)
            .then(function(response) {
                console.log('✅ Réponse serveur:', response);

                if (response.data && response.data.success) {
                    $('#detail-vaccination').modal('hide');

                    // Recharger la liste
                    $scope.chargerVaccinations();

                    // Message de succès
                    var message = $scope.modeEdition === 0 ? 'Vaccination ajoutée avec succès' : 'Vaccination modifiée avec succès';
                    alert(message);
                } else {
                    alert('Erreur lors de l\'enregistrement: ' + (response.data.message || 'Erreur inconnue'));
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur sauvegarde:', error);
                alert('Erreur lors de l\'enregistrement de la vaccination');
            })
            .finally(function() {
                $scope.isSaving = false;
            });
    };

    // Supprimer une vaccination
    $scope.deleteVaccination = function(vaccinationId) {
        if (!confirm('Êtes-vous sûr de vouloir supprimer cette vaccination ?')) {
            return;
        }

        console.log('🗑️ Suppression vaccination:', vaccinationId);

        $http.delete(deleteVaccinationURL + '?id=' + vaccinationId)
            .then(function(response) {
                if (response.data && response.data.success) {
                    // Recharger la liste
                    $scope.chargerVaccinations();
                    alert('Vaccination supprimée avec succès');
                } else {
                    alert('Erreur lors de la suppression');
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur suppression:', error);
                alert('Erreur lors de la suppression de la vaccination');
            });
    };

    // Initialiser au chargement
    $scope.chargerVaccinations();
});


