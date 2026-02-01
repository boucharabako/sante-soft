App.controller('detailConsultationController', function ($scope, $http) {
    console.log(' Contrôleur detailConsultationController initialisé');

    // URLs de l'API
    const appUrl = window.location.origin + '/sante-start-up/';
    const consultationURL = appUrl + 'api/consultation/getConsultation';
    const patientURL = appUrl + 'api/patient/getPatient';
    const antecedentsURL = appUrl + 'api/antecedent/listAllAntecedentsPatient';

    console.log('🔗 URLs configurées:');
    console.log('   appUrl:', appUrl);
    console.log('   consultationURL:', consultationURL);
    console.log('   patientURL:', patientURL);

    // Initialisation
    $scope.consultation = {};
    $scope.patient = {};
    $scope.antecedents = [];
    $scope.loading = true;

    // Récupérer l'ID de la consultation depuis l'URL
    function getParameterByName(name) {
        const url = window.location.href;
        name = name.replace(/[\[\]]/g, '\\$&');
        const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
        const results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }

    const consultationId = getParameterByName('id');
    console.log(' ID Consultation depuis URL:', consultationId);

    if (!consultationId) {
        alert(' Aucune consultation spécifiée');
        window.location.href = appUrl + 'gestion/consultation/liste';
        return;
    }

    // Charger les détails de la consultation
    $scope.chargerConsultation = function() {
        console.log('📥 Chargement de la consultation...');
        console.log('   URL:', consultationURL + '?id=' + consultationId);

        $http.get(consultationURL + '?id=' + consultationId)
            .then(function(response) {
                console.log('📦 Réponse consultation:', response);

                if (response.data && response.data.consultation) {
                    $scope.consultation = response.data.consultation;
                    
                    // Convertir la date
                    if ($scope.consultation.dateConsultation) {
                        if (typeof $scope.consultation.dateConsultation === 'number') {
                            $scope.consultation.dateConsultation = new Date($scope.consultation.dateConsultation);
                        } else if ($scope.consultation.dateConsultation.epochSecond) {
                            $scope.consultation.dateConsultation = new Date($scope.consultation.dateConsultation.epochSecond * 1000);
                        } else if (typeof $scope.consultation.dateConsultation === 'string') {
                            $scope.consultation.dateConsultation = new Date($scope.consultation.dateConsultation);
                        }
                    }

                    console.log(' Consultation chargée:', $scope.consultation);

                    // Charger les informations du patient
                    $scope.chargerPatient($scope.consultation.idPatient);
                } else {
                    console.error(' Consultation non trouvée');
                    alert('Consultation non trouvée');
                    window.location.href = appUrl + 'gestion/consultation/liste';
                }
            })
            .catch(function(error) {
                console.error(' Erreur lors du chargement de la consultation:', error);
                alert('Erreur lors du chargement de la consultation');
                window.location.href = appUrl + 'gestion/consultation/liste';
            })
            .finally(function() {
                $scope.loading = false;
            });
    };

    // Charger les informations du patient
    $scope.chargerPatient = function(patientId) {
        console.log(' Chargement du patient...');
        console.log('   URL:', patientURL + '?id=' + patientId);

        $http.get(patientURL + '?id=' + patientId)
            .then(function(response) {
                console.log(' Réponse patient:', response);

                if (response.data && response.data.patient) {
                    $scope.patient = response.data.patient;
                    console.log(' Patient chargé:', $scope.patient);

                    // Charger les antécédents
                    $scope.chargerAntecedents(patientId);
                } else {
                    console.error(' Patient non trouvé');
                }
            })
            .catch(function(error) {
                console.error(' Erreur lors du chargement du patient:', error);
            });
    };

    // Charger les antécédents du patient
    $scope.chargerAntecedents = function(patientId) {
        console.log(' Chargement des antécédents...');
        console.log('   URL:', antecedentsURL + '?idPatient=' + patientId);

        $http.get(antecedentsURL + '?idPatient=' + patientId)
            .then(function(response) {
                console.log(' Réponse antécédents:', response);

                if (response.data && response.data.listAntecedents) {
                    $scope.antecedents = response.data.listAntecedents;
                    console.log(' Antécédents chargés:', $scope.antecedents.length);
                } else {
                    console.warn(' Aucun antécédent trouvé');
                    $scope.antecedents = [];
                }
            })
            .catch(function(error) {
                console.error(' Erreur lors du chargement des antécédents:', error);
                $scope.antecedents = [];
            });
    };

    // Retour à la liste des consultations
    $scope.retourListe = function() {
        console.log(' Retour à la liste des consultations');
        window.location.href = appUrl + 'gestion/consultation/liste';
    };

    // Initialiser au chargement
    $scope.chargerConsultation();
});

