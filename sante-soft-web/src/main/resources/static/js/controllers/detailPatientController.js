App.controller('detailPatientController', function ($scope, $http, GenericService, PropagationService) {
    console.log('🏥 Contrôleur detailPatientController initialisé');

    // URLs de l'API
    const appUrl = window.location.origin + '/sante-start-up/';
    const detailPatientURL = appUrl + 'api/patient/getPatient';
    const listeConsultationsURL = appUrl + 'api/consultation/listConsultationsByPatient';
    const listeAntecedentsURL = appUrl + 'api/antecedent/listAllAntecedentsPatient';

    console.log('🔗 URLs configurées:');
    console.log('   appUrl:', appUrl);
    console.log('   detailPatientURL:', detailPatientURL);
    console.log('   listeConsultationsURL:', listeConsultationsURL);

    // Initialisation
    $scope.patient = {};
    $scope.consultations = [];
    $scope.antecedents = [];
    $scope.loading = true;

    // Récupérer l'ID du patient depuis l'URL
    function getParameterByName(name) {
        const url = window.location.href;
        name = name.replace(/[\[\]]/g, '\\$&');
        const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
        const results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }

    const patientId = getParameterByName('id');
    console.log('📋 ID Patient depuis URL:', patientId);

    if (!patientId) {
        alert('❌ Aucun patient spécifié');
        window.location.href = appUrl + 'patient';
        return;
    }

    // Charger les détails du patient
    $scope.chargerPatient = function() {
        console.log('📥 Chargement des détails du patient...');
        console.log('   URL:', detailPatientURL + '?id=' + patientId);

        $http.get(detailPatientURL + '?id=' + patientId)
            .then(function(response) {
                console.log('📦 Réponse reçue:', response);

                if (response.data && response.data.patient) {
                    $scope.patient = response.data.patient;
                    console.log('✅ Patient chargé:', $scope.patient);

                    // Charger les consultations et antécédents
                    $scope.chargerConsultations();
                    $scope.chargerAntecedents();
                } else {
                    console.error('❌ Patient non trouvé dans la réponse');
                    alert('Patient non trouvé');
                    window.location.href = appUrl + 'gestion/patient';
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur lors du chargement du patient:', error);
                console.error('   Status:', error.status);
                console.error('   Message:', error.statusText);
                console.error('   Data:', error.data);
                alert('Erreur lors du chargement du patient: ' + (error.statusText || 'Erreur inconnue'));
            })
            .finally(function() {
                $scope.loading = false;
            });
    };

    // Charger les consultations du patient
    $scope.chargerConsultations = function() {
        console.log('📥 Chargement des consultations...');
        console.log('   URL:', listeConsultationsURL + '?idPatient=' + patientId);

        $http.get(listeConsultationsURL + '?idPatient=' + patientId)
            .then(function(response) {
                console.log('📦 Réponse consultations:', response);

                if (response.data && response.data.listConsultations) {
                    // Convertir les dates Instant en objets Date JavaScript
                    $scope.consultations = response.data.listConsultations.map(function(consult) {
                        if (consult.dateConsultation) {
                            // Si c'est un timestamp numérique (millisecondes depuis epoch)
                            if (typeof consult.dateConsultation === 'number') {
                                consult.dateConsultation = new Date(consult.dateConsultation);
                            }
                            // Si c'est un objet avec epochSecond et nano
                            else if (consult.dateConsultation.epochSecond) {
                                consult.dateConsultation = new Date(consult.dateConsultation.epochSecond * 1000);
                            }
                            // Si c'est une chaîne ISO
                            else if (typeof consult.dateConsultation === 'string') {
                                consult.dateConsultation = new Date(consult.dateConsultation);
                            }
                        }
                        return consult;
                    });

                    console.log('✅ Consultations chargées:', $scope.consultations.length);

                    // Log pour déboguer les dates
                    if ($scope.consultations.length > 0) {
                        console.log('🕐 Première consultation - dateConsultation:', $scope.consultations[0].dateConsultation);
                        console.log('   Type:', typeof $scope.consultations[0].dateConsultation);
                    }
                } else {
                    console.warn('⚠️ Aucune consultation trouvée');
                    $scope.consultations = [];
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur lors du chargement des consultations:', error);
                console.error('   Status:', error.status);
                console.error('   Data:', error.data);
                $scope.consultations = [];
            });
    };

    // Charger les antécédents du patient
    $scope.chargerAntecedents = function() {
        console.log('📥 Chargement des antécédents...');
        console.log('   URL:', listeAntecedentsURL + '?idPatient=' + patientId);

        $http.get(listeAntecedentsURL + '?idPatient=' + patientId)
            .then(function(response) {
                console.log('📦 Réponse antécédents:', response);

                if (response.data && response.data.listAntecedents) {
                    $scope.antecedents = response.data.listAntecedents;
                    console.log('✅ Antécédents chargés:', $scope.antecedents.length);
                } else {
                    console.warn('⚠️ Aucun antécédent trouvé');
                    $scope.antecedents = [];
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur lors du chargement des antécédents:', error);
                console.error('   Status:', error.status);
                console.error('   Data:', error.data);
                $scope.antecedents = [];
            });
    };

    // Nouvelle consultation
    $scope.nouvelleConsultation = function() {
        console.log('🏥 Redirection vers nouvelle consultation');

        // Enregistrer le patient dans le service de propagation
        PropagationService.setPatientSender($scope.patient);

        // Rediriger vers la page de consultation
        window.location.href = appUrl + 'gestion/consultation';
    };

    // Gérer les antécédents
    $scope.gererAntecedents = function() {
        console.log('📋 Redirection vers gestion des antécédents');

        // Enregistrer le patient dans le service de propagation
        PropagationService.setPatientSender($scope.patient);

        // Rediriger vers la page des antécédents
        window.location.href = appUrl + 'gestion/antecedent';
    };

    // Gérer les vaccinations
    $scope.gererVaccinations = function() {
        console.log('💉 Redirection vers gestion des vaccinations');

        // Enregistrer le patient dans le service de propagation
        PropagationService.setPatientSender($scope.patient);

        // Rediriger vers la page des vaccinations
        window.location.href = appUrl + 'gestion/vaccination?idPatient=' + patientId;
    };

    // Voir le détail d'une consultation
    $scope.voirDetailConsultation = function(consultationId) {
        console.log('👁️ Voir détail consultation:', consultationId);

        // Charger les détails de la consultation
        $http.get(appUrl + 'api/consultation/getConsultation?id=' + consultationId)
            .then(function(response) {
                console.log('📦 Détail consultation reçu:', response);

                if (response.data && response.data.consultation) {
                    $scope.consultationDetail = response.data.consultation;

                    // Convertir la date
                    if ($scope.consultationDetail.dateConsultation) {
                        if (typeof $scope.consultationDetail.dateConsultation === 'number') {
                            $scope.consultationDetail.dateConsultation = new Date($scope.consultationDetail.dateConsultation);
                        } else if ($scope.consultationDetail.dateConsultation.epochSecond) {
                            $scope.consultationDetail.dateConsultation = new Date($scope.consultationDetail.dateConsultation.epochSecond * 1000);
                        } else if (typeof $scope.consultationDetail.dateConsultation === 'string') {
                            $scope.consultationDetail.dateConsultation = new Date($scope.consultationDetail.dateConsultation);
                        }
                    }

                    console.log('✅ Consultation détaillée:', $scope.consultationDetail);
                    console.log('🕐 Date convertie:', $scope.consultationDetail.dateConsultation);

                    // Ouvrir la modale
                    $('#modalDetailConsultation').modal('show');
                } else {
                    console.error('❌ Consultation non trouvée');
                    alert('Consultation non trouvée');
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur lors du chargement de la consultation:', error);
                alert('Erreur lors du chargement de la consultation');
            });
    };

    // Initialiser au chargement
    $scope.chargerPatient();
});

