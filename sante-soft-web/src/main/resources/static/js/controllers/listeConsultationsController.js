App.controller('listeConsultationsController', function ($scope, $http, GenericService) {
    console.log('📋 Contrôleur listeConsultationsController initialisé');

    // URLs de l'API
    var appUrl = window.location.origin + '/sante-start-up/';
    var listeConsultationsURL = appUrl + 'api/consultation/paginateConsultationsByProfessionnel';
    var listTypesConsultationURL = appUrl + 'api/consultation/listTypesConsultation';

    // Initialisation
    $scope.consultations = [];
    $scope.consultationsFiltrees = [];
    $scope.listeTypesConsultation = [];
    $scope.loading = true;

    // Pagination
    $scope.totalElements = 0;
    $scope.totalPages = 0;
    $scope.pageSizes = [5, 10, 15, 20, 25];
    $scope.pageSizeSelect = 10;
    $scope.memoryPage = 1;
    var firstPage = 1;

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

    // Charger les statistiques
    $scope.chargerStatistiques = function() {
        var url = appUrl + 'api/consultation/statistiques';

        GenericService.get(url)
            .then(function(data) {
                if (data) {
                    $scope.stats.aujourdhui = data.aujourdhui || 0;
                    $scope.stats.semaine = data.semaine || 0;
                    $scope.stats.mois = data.mois || 0;
                    console.log('✅ Statistiques chargées:', $scope.stats);
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur chargement statistiques:', error);
            });
    };


    // Charger les consultations du professionnel avec pagination
    $scope.chargerConsultations = function(page) {
        if (!page) page = firstPage;
        $scope.memoryPage = page;

        console.log('📥 Chargement des consultations - Page:', page, 'Size:', $scope.pageSizeSelect);

        var url = listeConsultationsURL + '?page=' + (page - 1) + '&size=' + $scope.pageSizeSelect;

        // Ajouter les filtres à l'URL
        if ($scope.filtres.recherche) {
            url += '&mc=' + encodeURIComponent($scope.filtres.recherche);
        }
        if ($scope.filtres.typeConsultation) {
            url += '&typeConsultation=' + encodeURIComponent($scope.filtres.typeConsultation);
        }
        if ($scope.filtres.dateDebut) {
            var dateDebut = new Date($scope.filtres.dateDebut);
            url += '&dateDebut=' + dateDebut.getTime();
        }
        if ($scope.filtres.dateFin) {
            var dateFin = new Date($scope.filtres.dateFin);
            dateFin.setHours(23, 59, 59, 999);
            url += '&dateFin=' + dateFin.getTime();
        }

        GenericService.get(url)
            .then(function(data) {
                console.log('📦 Réponse reçue:', data);

                if (data && data.listConsultations) {
                    // Convertir les dates
                    $scope.consultationsFiltrees = data.listConsultations.content.map(function(consult) {
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

                    $scope.listPage = data.listConsultations;
                    $scope.totalElements = data.listConsultations.totalElements;
                    $scope.totalPages = data.listConsultations.totalPages;

                    console.log('✅ Consultations chargées:', $scope.consultationsFiltrees.length);
                    console.log('📊 Total:', $scope.totalElements, 'Pages:', $scope.totalPages);

                    // Calculer les statistiques (basées sur le total)
                    $scope.stats.total = $scope.totalElements;
                } else {
                    console.warn('⚠️ Aucune consultation trouvée');
                    $scope.consultationsFiltrees = [];
                    $scope.totalElements = 0;
                    $scope.totalPages = 0;
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur lors du chargement des consultations:', error);
                $scope.consultationsFiltrees = [];
                $scope.totalElements = 0;
                $scope.totalPages = 0;
            })
            .finally(function() {
                $scope.loading = false;
            });
    };

    // Pagination - Changer de page
    $scope.paginate = function(pageSizeSelect, listePage) {
        $scope.memoryPage = listePage;
        $scope.chargerConsultations(listePage);
    };

    // Pagination - Changer la taille de page
    $scope.changePageSize = function() {
        $scope.chargerConsultations(firstPage);
    };

    // Appliquer les filtres
    $scope.appliquerFiltres = function() {
        console.log('🔍 Application des filtres:', $scope.filtres);
        $scope.chargerConsultations(firstPage);
    };

    // Réinitialiser les filtres
    $scope.reinitialiserFiltres = function() {
        $scope.filtres = {
            recherche: '',
            dateDebut: null,
            dateFin: null,
            typeConsultation: ''
        };
        $scope.chargerConsultations(firstPage);
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
    $scope.chargerStatistiques();
    $scope.chargerConsultations();
});

