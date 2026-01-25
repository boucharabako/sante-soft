App.controller('tableauBordController', function ($scope, $http) {
    console.log('📊 Contrôleur tableauBordController initialisé');

    // URLs de l'API
    const appUrl = window.location.origin + '/sante-start-up/';
    const statistiquesURL = appUrl + 'api/statistiques/tableau-bord';
    const listeProfessionnelsURL = appUrl + 'api/professionnel/listAllProfessionnelSante';
    const anneesURL = appUrl + 'api/statistiques/annees';
    const specialitesURL = appUrl + 'api/statistiques/specialites';

    // Initialisation
    $scope.loading = true;
    $scope.stats = {};
    $scope.listeProfessionnels = [];
    $scope.listeAnnees = [];
    $scope.listeSpecialites = [];

    // Configuration Selectize pour le filtre des professionnels
    $scope.selectizeConfigProfessionnel = {
        valueField: 'id',
        labelField: 'fullName',
        searchField: ['firstName', 'lastName', 'fullName'],
        placeholder: 'Tous les professionnels',
        create: false,
        maxItems: 1,
        render: {
            option: function(item, escape) {
                return '<div>Dr. ' + escape(item.firstName) + ' ' + escape(item.lastName) + '</div>';
            },
            item: function(item, escape) {
                return '<div>Dr. ' + escape(item.firstName) + ' ' + escape(item.lastName) + '</div>';
            }
        },
        onChange: function(value) {
            $scope.$apply(function() {
                $scope.filtres.idProfessionnel = value || '';
                $scope.chargerStatistiques();
            });
        }
    };

    // Filtres
    $scope.filtres = {
        annee: '',
        idProfessionnel: '',
        specialite: ''
    };

    // Charts
    let charts = {};

    /**
     * Charger la liste des professionnels pour le filtre
     */
    $scope.chargerProfessionnels = function() {
        $http.get(listeProfessionnelsURL)
            .then(function(response) {
                if (response.data && response.data.listProfessionnelSante) {
                    $scope.listeProfessionnels = response.data.listProfessionnelSante;
                    console.log('✅ Professionnels chargés:', $scope.listeProfessionnels.length);
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur chargement professionnels:', error);
            });
    };

    /**
     * Charger la liste des années avec consultations
     */
    $scope.chargerAnnees = function() {
        $http.get(anneesURL)
            .then(function(response) {
                if (response.data && response.data.listeAnnees) {
                    $scope.listeAnnees = response.data.listeAnnees;
                    console.log('✅ Années chargées:', $scope.listeAnnees);
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur chargement années:', error);
            });
    };

    /**
     * Charger la liste des spécialités
     */
    $scope.chargerSpecialites = function() {
        $http.get(specialitesURL)
            .then(function(response) {
                if (response.data && response.data.listeSpecialites) {
                    $scope.listeSpecialites = response.data.listeSpecialites;
                    console.log('✅ Spécialités chargées:', $scope.listeSpecialites.length);
                }
            })
            .catch(function(error) {
                console.error('❌ Erreur chargement spécialités:', error);
            });
    };

    /**
     * Charger les statistiques
     */
    $scope.chargerStatistiques = function() {
        $scope.loading = true;
        
        // Construire l'URL avec les filtres
        let url = statistiquesURL + '?';
        if ($scope.filtres.annee) url += 'annee=' + $scope.filtres.annee + '&';
        if ($scope.filtres.idProfessionnel) url += 'idProfessionnel=' + $scope.filtres.idProfessionnel + '&';
        if ($scope.filtres.specialite) url += 'specialite=' + $scope.filtres.specialite + '&';

        console.log('📥 Chargement des statistiques...', url);

        $http.get(url)
            .then(function(response) {
                console.log('📦 Réponse reçue:', response.data);

                if (response.data && response.data.success && response.data.statistiques) {
                    $scope.stats = response.data.statistiques;
                    console.log('✅ Statistiques chargées:', $scope.stats);

                    // Créer les graphiques
                    $scope.creerGraphiques();
                } else {
                    console.error('❌ Erreur dans les données:', response.data);
                }

                $scope.loading = false;
            })
            .catch(function(error) {
                console.error('❌ Erreur lors du chargement des statistiques:', error);
                $scope.loading = false;
            });
    };

    /**
     * Rafraîchir les données
     */
    $scope.rafraichir = function() {
        $scope.chargerStatistiques();
    };

    /**
     * Créer tous les graphiques
     */
    $scope.creerGraphiques = function() {
        // Détruire les anciens graphiques
        Object.values(charts).forEach(chart => {
            if (chart) chart.destroy();
        });
        charts = {};

        // Créer les nouveaux graphiques
        $scope.creerGraphiqueEvolutionConsultations();
        $scope.creerGraphiqueSexe();
        $scope.creerGraphiqueGroupeSanguin();
        $scope.creerGraphiqueTypeConsultation();
        $scope.creerGraphiqueAntecedents();
        $scope.creerGraphiqueTopVaccins();
        $scope.creerGraphiqueProfessionnels();
    };

    /**
     * Graphique: Évolution des consultations par mois
     */
    $scope.creerGraphiqueEvolutionConsultations = function() {
        const ctx = document.getElementById('chartEvolutionConsultations');
        if (!ctx) return;

        const data = $scope.stats.evolutionConsultationsMois || {};
        const labels = Object.keys(data);
        const values = Object.values(data);

        charts.evolution = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Consultations',
                    data: values,
                    borderColor: 'rgb(102, 126, 234)',
                    backgroundColor: 'rgba(102, 126, 234, 0.1)',
                    tension: 0.4,
                    fill: true,
                    borderWidth: 3
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            precision: 0
                        }
                    }
                }
            }
        });
    };

    /**
     * Graphique: Répartition par sexe (Camembert)
     */
    $scope.creerGraphiqueSexe = function() {
        const ctx = document.getElementById('chartSexe');
        if (!ctx) return;

        charts.sexe = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: ['Hommes', 'Femmes'],
                datasets: [{
                    data: [$scope.stats.patientsHommes || 0, $scope.stats.patientsFemmes || 0],
                    backgroundColor: ['#667eea', '#e91e63'],
                    borderWidth: 2,
                    borderColor: '#fff'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom',
                        labels: {
                            padding: 15,
                            font: {
                                size: 13,
                                weight: '600'
                            }
                        }
                    }
                }
            }
        });
    };

    /**
     * Graphique: Répartition par groupe sanguin
     */
    $scope.creerGraphiqueGroupeSanguin = function() {
        const ctx = document.getElementById('chartGroupeSanguin');
        if (!ctx) return;

        const data = $scope.stats.repartitionGroupeSanguin || {};
        const labels = Object.keys(data);
        const values = Object.values(data);

        charts.groupeSanguin = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Patients',
                    data: values,
                    backgroundColor: [
                        '#f44336', '#e91e63', '#9c27b0', '#673ab7',
                        '#3f51b5', '#2196f3', '#03a9f4', '#00bcd4'
                    ],
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            precision: 0
                        }
                    }
                }
            }
        });
    };

    /**
     * Graphique: Types de consultations
     */
    $scope.creerGraphiqueTypeConsultation = function() {
        const ctx = document.getElementById('chartTypeConsultation');
        if (!ctx) return;

        const data = $scope.stats.repartitionTypeConsultation || {};
        const labels = Object.keys(data);
        const values = Object.values(data);

        charts.typeConsultation = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                    data: values,
                    backgroundColor: [
                        '#667eea', '#764ba2', '#f093fb', '#4facfe',
                        '#00f2fe', '#43e97b', '#fa709a', '#fee140'
                    ],
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true
            }
        });
    };

    /**
     * Graphique: Antécédents médicaux
     */
    $scope.creerGraphiqueAntecedents = function() {
        const ctx = document.getElementById('chartAntecedents');
        if (!ctx) return;

        const data = $scope.stats.repartitionAntecedents || {};
        const labels = Object.keys(data);
        const values = Object.values(data);

        charts.antecedents = new Chart(ctx, {
            type: 'polarArea',
            data: {
                labels: labels,
                datasets: [{
                    data: values,
                    backgroundColor: [
                        'rgba(102, 126, 234, 0.7)',
                        'rgba(118, 75, 162, 0.7)',
                        'rgba(233, 30, 99, 0.7)',
                        'rgba(0, 184, 148, 0.7)',
                        'rgba(253, 203, 110, 0.7)',
                        'rgba(214, 48, 49, 0.7)'
                    ],
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true
            }
        });
    };

    /**
     * Graphique: Top 5 vaccins
     */
    $scope.creerGraphiqueTopVaccins = function() {
        const ctx = document.getElementById('chartTopVaccins');
        if (!ctx) return;

        const data = $scope.stats.topVaccins || {};
        const labels = Object.keys(data);
        const values = Object.values(data);

        charts.topVaccins = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Nombre de vaccinations',
                    data: values,
                    backgroundColor: 'rgba(0, 184, 148, 0.8)',
                    borderColor: 'rgb(0, 184, 148)',
                    borderWidth: 2
                }]
            },
            options: {
                indexAxis: 'y',
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    x: {
                        beginAtZero: true,
                        ticks: {
                            precision: 0
                        }
                    }
                }
            }
        });
    };

    /**
     * Graphique: Top 10 professionnels
     */
    $scope.creerGraphiqueProfessionnels = function() {
        const ctx = document.getElementById('chartProfessionnels');
        if (!ctx) return;

        const data = $scope.stats.consultationsParProfessionnel || {};
        const labels = Object.keys(data);
        const values = Object.values(data);

        charts.professionnels = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Consultations',
                    data: values,
                    backgroundColor: 'rgba(102, 126, 234, 0.8)',
                    borderColor: 'rgb(102, 126, 234)',
                    borderWidth: 2
                }]
            },
            options: {
                indexAxis: 'y',
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    x: {
                        beginAtZero: true,
                        ticks: {
                            precision: 0
                        }
                    }
                }
            }
        });
    };

    // Initialisation au chargement
    $scope.chargerProfessionnels();
    $scope.chargerAnnees();
    $scope.chargerSpecialites();
    $scope.chargerStatistiques();
});


