App.controller('listeConsultationsController', function ($scope, $http, GenericService) {
    console.log('📋 Contrôleur listeConsultationsController initialisé');

    // URLs de l'API
    var appUrl = window.location.origin + '/sante-start-up/';
    var listeConsultationsURL = appUrl + 'api/consultation/listConsultationsByProfessionnel';
    var listTypesConsultationURL = appUrl + 'api/consultation/listTypesConsultation';

    // Initialisation
    $scope.consultations = [];
    $scope.consultationsFiltrees = [];
    $scope.listeTypesConsultation = [];
    $scope.loading = true;
    
    // Statistiques
    $scope.stats = {
        total: 0,
        aujourdhui: 0,
        semaine: 0,
        mois: 0
    };
    
    // Filtres
    $scope.filtres = {
        recherche: '',
        dateDebut: null,
        dateFin: null,
        typeConsultation: ''
    };

    // Charger les types de consultation pour le filtre
    $scope.chargerTypesConsultation = function() {
        $http.get(listTypesConsultationURL)
            .then(function(response) {
                if (response.data && response.data.listTypesConsultation) {
                    $scope.listeTypesConsultation = response.data.listTypesConsultation;
                    console.log('✅ Types de consultation chargés:', $scope.listeTypesConsultation.length);
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur chargement types consultation:', error);
            });
    };

    // Charger les consultations du professionnel
    $scope.chargerConsultations = function() {
        console.log('📥 Chargement des consultations...');
        
        $http.get(listeConsultationsURL)
            .then(function(response) {
                console.log('📦 Réponse reçue:', response);
                
                if (response.data && response.data.listConsultations) {
                    // Convertir les dates
                    $scope.consultations = response.data.listConsultations.map(function(consult) {
                        if (consult.dateConsultation) {
                            if (typeof consult.dateConsultation === 'number') {
                                consult.dateConsultation = new Date(consult.dateConsultation);
                            } else if (consult.dateConsultation.epochSecond) {
                                consult.dateConsultation = new Date(consult.dateConsultation.epochSecond * 1000);
                            } else if (typeof consult.dateConsultation === 'string') {
                                consult.dateConsultation = new Date(consult.dateConsultation);
                            }
                        }
                        return consult;
                    });
                    
                    console.log('✅ Consultations chargées:', $scope.consultations.length);
                    
                    // Calculer les statistiques
                    $scope.calculerStatistiques();
                    
                    // Appliquer les filtres
                    $scope.appliquerFiltres();
                } else {
                    console.warn('⚠️ Aucune consultation trouvée');
                    $scope.consultations = [];
                    $scope.consultationsFiltrees = [];
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur lors du chargement des consultations:', error);
                $scope.consultations = [];
                $scope.consultationsFiltrees = [];
            })
            .finally(function() {
                $scope.loading = false;
            });
    };

    // Calculer les statistiques
    $scope.calculerStatistiques = function() {
        var aujourdhui = new Date();
        aujourdhui.setHours(0, 0, 0, 0);

        var debutSemaine = new Date(aujourdhui);
        debutSemaine.setDate(aujourdhui.getDate() - aujourdhui.getDay());

        var debutMois = new Date(aujourdhui.getFullYear(), aujourdhui.getMonth(), 1);

        $scope.stats.total = $scope.consultations.length;
        $scope.stats.aujourdhui = $scope.consultations.filter(function(c) {
            var dateConsult = new Date(c.dateConsultation);
            dateConsult.setHours(0, 0, 0, 0);
            return dateConsult.getTime() === aujourdhui.getTime();
        }).length;

        $scope.stats.semaine = $scope.consultations.filter(function(c) {
            return new Date(c.dateConsultation) >= debutSemaine;
        }).length;

        $scope.stats.mois = $scope.consultations.filter(function(c) {
            return new Date(c.dateConsultation) >= debutMois;
        }).length;

        console.log(' Statistiques calculées:', $scope.stats);
    };

    // Appliquer les filtres
    $scope.appliquerFiltres = function() {
        console.log('🔍 Application des filtres:', $scope.filtres);

        $scope.consultationsFiltrees = $scope.consultations.filter(function(consult) {
            // Filtre recherche
            if ($scope.filtres.recherche) {
                var recherche = $scope.filtres.recherche.toLowerCase();
                var match = (consult.patientNom && consult.patientNom.toLowerCase().indexOf(recherche) !== -1) ||
                             (consult.motif && consult.motif.toLowerCase().indexOf(recherche) !== -1) ||
                             (consult.diagnostic && consult.diagnostic.toLowerCase().indexOf(recherche) !== -1);
                if (!match) return false;
            }

            // Filtre date début
            if ($scope.filtres.dateDebut) {
                var dateDebut = new Date($scope.filtres.dateDebut);
                if (new Date(consult.dateConsultation) < dateDebut) return false;
            }

            // Filtre date fin
            if ($scope.filtres.dateFin) {
                var dateFin = new Date($scope.filtres.dateFin);
                dateFin.setHours(23, 59, 59, 999);
                if (new Date(consult.dateConsultation) > dateFin) return false;
            }

            // Filtre type consultation
            if ($scope.filtres.typeConsultation) {
                if (consult.typeConsultation !== $scope.filtres.typeConsultation) return false;
            }

            return true;
        });

        console.log('✅ Consultations filtrées:', $scope.consultationsFiltrees.length);
    };

    // Réinitialiser les filtres
    $scope.reinitialiserFiltres = function() {
        $scope.filtres = {
            recherche: '',
            dateDebut: null,
            dateFin: null,
            typeConsultation: ''
        };
        $scope.appliquerFiltres();
    };

    // Éditer une consultation
    $scope.editerConsultation = function(consultationId) {
        console.log('✏️ Éditer consultation:', consultationId);
        // Rediriger vers la page de consultation avec l'ID en paramètre
        window.location.href = appUrl + 'gestion/consultation?id=' + consultationId;
    };

    // Voir le détail d'une consultation
    $scope.voirDetail = function(consultationId) {
        console.log('👁️ Voir détail consultation:', consultationId);
        // TODO: Ouvrir modal ou rediriger vers page détail
        alert('Fonctionnalité en cours de développement');
    };

    // Voir le patient
    $scope.voirPatient = function(patientId) {
        console.log('👤 Voir patient:', patientId);
        window.location.href = appUrl + 'gestion/patient/detail?id=' + patientId;
    };

    // Initialiser au chargement
    $scope.chargerTypesConsultation();
    $scope.chargerConsultations();
});

