App.controller('vaccinationController', function ($scope, $http, $location, $timeout, PropagationService) {
    console.log('💉 Contrôleur vaccinationController initialisé');

    // Fonction locale pour afficher les notifications (fallback si display() n'est pas disponible)
    var showNotification = function(type, title, message) {
        if (typeof display === 'function') {
            display(type, title, message);
        } else if (typeof $.Notification !== 'undefined' && $.Notification.notify) {
            $.Notification.notify(type, 'top right', title, message);
        } else {
            // Fallback sur alert si rien d'autre n'est disponible
            alert(title + ': ' + message);
        }
    };

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

    // Liste des vaccins disponibles
    $scope.listeVaccins = [
        'BCG (Tuberculose)',
        'DTP (Diphtérie, Tétanos, Poliomyélite)',
        'Hépatite B',
        'Hépatite A',
        'Haemophilus influenzae type b (Hib)',
        'Pneumocoque',
        'Rotavirus',
        'Rougeole',
        'Rubéole',
        'Oreillons',
        'ROR (Rougeole, Oreillons, Rubéole)',
        'Varicelle',
        'Méningocoque',
        'Papillomavirus (HPV)',
        'Grippe',
        'COVID-19',
        'Fièvre jaune',
        'Typhoïde',
        'Rage',
        'Choléra'
    ];

    // Récupérer l'ID du patient depuis l'URL
    var urlParams = new URLSearchParams(window.location.search);
    var patientId = urlParams.get('idPatient');

    console.log('📋 ID Patient depuis URL:', patientId);

    // Essayer d'abord de récupérer le patient complet depuis PropagationService
    var patientFromService = PropagationService.getPatientSender();
    console.log('🔍 Patient depuis PropagationService:', patientFromService);

    if (patientFromService && patientFromService.id) {
        $scope.patient = patientFromService;
        patientId = patientFromService.id;
        console.log('✅ Patient récupéré depuis PropagationService:', $scope.patient);
        console.log('   - ID:', $scope.patient.id);
        console.log('   - Nom:', $scope.patient.firstName, $scope.patient.lastName);
    } else if (patientId) {
        // Si on a seulement l'ID dans l'URL, charger le patient depuis l'API
        console.log('📥 Chargement du patient depuis l\'API...');
        $http.get(appUrl + 'api/patient/getPatient?id=' + patientId)
            .then(function(response) {
                console.log('📦 Réponse API patient:', response.data);
                if (response.data && response.data.patient) {
                    $scope.patient = response.data.patient;
                    console.log('✅ Patient chargé depuis API:', $scope.patient);
                    // Charger les vaccinations après avoir chargé le patient
                    $scope.chargerVaccinations();
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur chargement patient:', error);
            });
    } else {
        // Aucun patient trouvé
        console.error('❌ Aucun patient sélectionné');
        alert('Aucun patient sélectionné. Redirection vers la liste des patients.');
        window.location.href = appUrl + 'gestion/patient';
        return;
    }

    // Charger les vaccinations du patient
    $scope.chargerVaccinations = function() {
        if (!patientId) {
            console.warn('⚠️ Impossible de charger les vaccinations: patientId non défini');
            return;
        }

        console.log('📥 Chargement des vaccinations pour le patient:', patientId);
        console.log('📍 URL complète:', listeVaccinationsURL + '?idPatient=' + patientId);

        $http.get(listeVaccinationsURL + '?idPatient=' + patientId)
            .then(function(response) {
                console.log('📦 Réponse API vaccinations:', response.data);
                if (response.data && response.data.listVaccinations) {
                    $scope.vaccinations = response.data.listVaccinations;
                    console.log('✅ Vaccinations chargées:', $scope.vaccinations.length);
                } else {
                    $scope.vaccinations = [];
                    console.log('ℹ️ Aucune vaccination trouvée');
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

    // Fonction pour initialiser Select2 sur le champ vaccin
    function initializeSelect2Vaccin() {
        setTimeout(function() {
            // Détruire l'instance existante si elle existe
            if ($('#selectVaccin').data('select2')) {
                $('#selectVaccin').select2('destroy');
            }

            // Initialiser Select2
            $('#selectVaccin').select2({
                placeholder: "-- Sélectionner un vaccin --",
                allowClear: true,
                language: "fr",
                dropdownParent: $('#detail-vaccination')
            }).on('change', function() {
                $scope.$apply(function() {
                    $scope.objetVaccination.vaccin = $('#selectVaccin').val();
                });
            });

            // Définir la valeur si elle existe
            if ($scope.objetVaccination && $scope.objetVaccination.vaccin) {
                $('#selectVaccin').val($scope.objetVaccination.vaccin).trigger('change');
            }
        }, 200);
    }

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
            lieuVaccination: '',
            observations: ''
        };

        $('#detail-vaccination').modal('show');

        // Initialiser Select2 après l'ouverture du modal
        initializeSelect2Vaccin();
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

        // Initialiser Select2 après l'ouverture du modal
        initializeSelect2Vaccin();
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
                console.log('✅ Vaccination enregistrée:', response.data);

                if (response.data.success) {
                    // Utiliser la fonction de notification
                    showNotification('success', 'Succès', response.data.message || 'Vaccination enregistrée avec succès');

                    $('#detail-vaccination').modal('hide');

                    // Recharger la liste
                    $scope.chargerVaccinations();

                    $scope.modeEdition = 0;
                } else {
                    showNotification('error', 'Erreur', response.data.message || 'Erreur inconnue');
                }

                $scope.isSaving = false;
            })
            .catch(function(error) {
                console.error('❌ Erreur sauvegarde:', error);

                if (error.data && error.data.message) {
                    showNotification('error', 'Erreur', error.data.message);
                } else {
                    showNotification('error', 'Erreur', 'Erreur lors de l\'enregistrement de la vaccination. Veuillez réessayer.');
                }

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
                console.log('✅ Vaccination supprimée:', response.data);

                if (response.data.success) {
                    // Utiliser la fonction de notification
                    showNotification('success', 'Succès', response.data.message || 'Vaccination supprimée avec succès');
                } else {
                    showNotification('error', 'Erreur', response.data.message || 'Erreur lors de la suppression');
                }

                // Recharger la liste
                $scope.chargerVaccinations();
            })
            .catch(function(error) {
                console.error('❌ Erreur suppression:', error);

                if (error.data && error.data.message) {
                    showNotification('error', 'Erreur', error.data.message);
                } else {
                    showNotification('error', 'Erreur', 'Erreur lors de la suppression de la vaccination');
                }
            });
    };

    // Initialiser au chargement - charger les vaccinations si le patient est déjà disponible
    if ($scope.patient && $scope.patient.id) {
        console.log('🔄 Chargement initial des vaccinations...');
        $scope.chargerVaccinations();
    }
});


