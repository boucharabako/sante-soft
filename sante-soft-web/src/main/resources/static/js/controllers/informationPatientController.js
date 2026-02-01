App.controller('informationPatientController', function ($scope, $http, GenericService, PropagationService) {
    console.log(' Contrôleur informationPatientController initialisé');

    // URLs de l'API
    const appUrl = window.location.origin + '/sante-start-up/';
    const currentPatientURL = appUrl + 'api/patient/getCurrentPatient';
    const listeConsultationsURL = appUrl + 'api/consultation/listConsultationsByPatient';
    const listeAntecedentsURL = appUrl + 'api/antecedent/listAllAntecedentsPatient';
    const listeVaccinationsURL = appUrl + 'api/vaccination/listVaccinationsByPatient';

    console.log(' URLs configurées:');
    console.log('   appUrl:', appUrl);
    console.log('   currentPatientURL:', currentPatientURL);

    // Initialisation des données
    $scope.patient = {};
    $scope.consultations = [];
    $scope.antecedents = [];
    $scope.vaccinations = [];
    $scope.consultationDetail = {};
    
    // Variable pour stocker l'ID du patient connecté
    let patientId = null;

    // Charger les détails du patient connecté
    $scope.chargerPatient = function() {
        console.log(' Chargement des détails du patient connecté...');

        $http.get(currentPatientURL)
            .then(function(response) {
                console.log(' Réponse reçue:', response);

                if (response.data && response.data.success && response.data.patient) {
                    $scope.patient = response.data.patient;
                    patientId = $scope.patient.id;
                    console.log(' Patient connecté chargé:', $scope.patient);

                    // Charger les consultations, antécédents et vaccinations
                    $scope.chargerConsultations();
                    $scope.chargerAntecedents();
                    $scope.chargerVaccinations();
                } else {
                    console.error(' Patient non trouvé:', response.data.message);
                    alert(response.data.message || 'Vous n\'êtes pas enregistré comme patient');
                    window.location.href = appUrl;
                }
            })
            .catch(function(error) {
                console.error(' Erreur lors du chargement du patient:', error);
                alert('Erreur lors du chargement de vos informations');
                window.location.href = appUrl;
            });
    };

    // Charger les consultations
    $scope.chargerConsultations = function() {
        console.log(' Chargement des consultations...');

        $http.get(listeConsultationsURL + '?idPatient=' + patientId)
            .then(function(response) {
                if (response.data && response.data.listConsultations) {
                    $scope.consultations = response.data.listConsultations;
                    console.log(' Consultations chargées:', $scope.consultations.length);
                } else {
                    $scope.consultations = [];
                }
            })
            .catch(function(error) {
                console.error(' Erreur lors du chargement des consultations:', error);
                $scope.consultations = [];
            });
    };

    // Charger les antécédents
    $scope.chargerAntecedents = function() {
        console.log(' Chargement des antécédents...');

        $http.get(listeAntecedentsURL + '?idPatient=' + patientId)
            .then(function(response) {
                if (response.data && response.data.listAntecedents) {
                    $scope.antecedents = response.data.listAntecedents;
                    console.log(' Antécédents chargés:', $scope.antecedents.length);
                } else {
                    $scope.antecedents = [];
                }
            })
            .catch(function(error) {
                console.error(' Erreur lors du chargement des antécédents:', error);
                $scope.antecedents = [];
            });
    };

    // Charger les vaccinations
    $scope.chargerVaccinations = function() {
        console.log(' Chargement des vaccinations...');

        $http.get(listeVaccinationsURL + '?idPatient=' + patientId)
            .then(function(response) {
                if (response.data && response.data.listVaccinations) {
                    $scope.vaccinations = response.data.listVaccinations;
                    console.log(' Vaccinations chargées:', $scope.vaccinations.length);
                } else {
                    $scope.vaccinations = [];
                }
            })
            .catch(function(error) {
                console.error(' Erreur lors du chargement des vaccinations:', error);
                $scope.vaccinations = [];
            });
    };

    // Voir le détail d'une consultation
    $scope.voirDetailConsultation = function(consultationId) {
        console.log(' Voir détail consultation:', consultationId);

        $http.get(appUrl + 'api/consultation/getConsultation?id=' + consultationId)
            .then(function(response) {
                if (response.data && response.data.consultation) {
                    $scope.consultationDetail = response.data.consultation;
                    $('#modalDetailConsultation').modal('show');
                } else {
                    alert('Consultation non trouvée');
                }
            })
            .catch(function(error) {
                console.error(' Erreur:', error);
                alert('Erreur lors du chargement des détails de la consultation');
            });
    };

    /**
     * Exporter l'ordonnance en PDF
     */
    $scope.exporterOrdonnance = function(consultationId) {
        console.log('📄 Export ordonnance pour consultation:', consultationId);

        $http.get(appUrl + 'api/consultation/exportPrescription?id=' + consultationId, {
            responseType: 'arraybuffer'
        })
        .then(function(response) {
            console.log('✅ Ordonnance reçue');

            // Créer un blob à partir des données PDF
            var blob = new Blob([response.data], { type: 'application/pdf' });

            // Extraire le nom du fichier depuis l'en-tête Content-Disposition
            var contentDisposition = response.headers('Content-Disposition');
            var filename = 'ordonnance.pdf';
            if (contentDisposition) {
                var matches = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/.exec(contentDisposition);
                if (matches != null && matches[1]) {
                    filename = matches[1].replace(/['"]/g, '');
                }
            }

            // Créer un lien temporaire pour télécharger le fichier
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = filename;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);

            // Libérer la mémoire
            window.URL.revokeObjectURL(link.href);
        })
        .catch(function(error) {
            console.error('❌ Erreur export ordonnance:', error);
            alert('Erreur lors de l\'export de l\'ordonnance');
        });
    };

    // Initialiser au chargement
    $scope.chargerPatient();
});

