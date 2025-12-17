'use strict';
var App;

App.controller('consultationController', ['$scope', '$http', '$location', '$rootScope', 'PropagationService', 'GenericService', function ($scope, $http, $location, $rootScope, PropagationService, GenericService) {

        // URLs de l'API
        const urlBase = appUrl + "api/consultation";
        const listTypesObservationURL = urlBase + "/listTypesObservation";
        const listCategoriesAntecedentURL = urlBase + "/listCategoriesAntecedent";
        const listTypesAntecedentByCategorieURL = urlBase + "/listTypesAntecedentByCategorie";
        const listCategoriesConsultationURL = urlBase + "/listCategoriesConsultation";
        const listTypesConsultationByCategorieURL = urlBase + "/listTypesConsultationByCategorie";
        const listTypesExamenByTypeConsultationURL = urlBase + "/listTypesExamenByTypeConsultation";
        const enregistrerConsultationURL = urlBase + "/enregistrerConsultation";

        // Initialisation des données
        $scope.consultation = {
            id: null,
            dateConsultation: new Date(),
            categorieConsultation: '', // ID de la catégorie (string)
            typeConsultation: '', // ID du type (string)
            motif: '',
            diagnostic: '',
            traitement: '',
            patientId: null
        };



        // Patient sélectionné (à charger depuis l'API ou la session)
        $scope.patient = {
            id: null,
            firstName: "",
            lastName: "",
            username: "",
            sexe: "",
            sexeLibelle: "",
            groupeSanguin: "",
            groupeSanguinLibelle: "",
            numeroCarnet: "",
            dateNaissance: null,
            email: "",
            tel: ""
        };

        // Récupérer le patient depuis le service de propagation
        var patientFromService = PropagationService.getPatientSender();
        if (patientFromService && patientFromService.id) {
            $scope.patient = patientFromService;
            console.log("✅ Patient récupéré depuis PropagationService:", $scope.patient);
            console.log("   - Nom complet: " + $scope.patient.firstName + " " + $scope.patient.lastName);
            console.log("   - N° Carnet: " + $scope.patient.numeroCarnet);
            console.log("   - Groupe sanguin: " + $scope.patient.groupeSanguinLibelle);

            // Définir l'ID du patient pour la consultation
            $scope.consultation.patientId = $scope.patient.id;
        } else {
            console.warn("⚠️ Aucun patient trouvé dans PropagationService");
            console.log("Données reçues:", patientFromService);
        }

        // Écouter les changements de patient
        $rootScope.$on("patientSender", function () {
            var updatedPatient = PropagationService.getPatientSender();
            if (updatedPatient && updatedPatient.id) {
                $scope.patient = updatedPatient;
                $scope.consultation.patientId = updatedPatient.id;
                console.log("✅ Patient mis à jour:", $scope.patient);
            }
        });

        // Listes pour les éléments multiples
        $scope.observations = [];
        $scope.antecedents = [];
        $scope.prescriptions = [];
        $scope.examens = [];
        $scope.vaccinations = [];

        // Formulaires temporaires pour ajout
        $scope.nouvelleObservation = {
            typeObservation: null, // Objet TypeObservation complet
            valeur: '',
            unite: '',
            valeurMin: '',
            valeurMax: '',
            date: new Date()
        };

        $scope.nouvelAntecedent = {
            categorieAntecedent: null, // Objet CategorieAntecedent complet
            typeAntecedent: null, // Objet TypeAntecedent complet
            description: '',
            dateDebut: null
        };

        $scope.nouvellePrescription = {
            medicament: '',
            posologie: '',
            duree: ''
        };

        $scope.nouvelExamen = {
            typeExamen: '',
            resultat: '',
            fichierJoint: ''
        };

        // Types d'observations (chargés depuis la base de données)
        $scope.listeTypesObservation = [];

        // Catégories et types d'antécédents (chargés depuis la base de données)
        $scope.listeCategoriesAntecedent = [];
        $scope.listeTypesAntecedent = [];

        // Catégories et types de consultation (chargés depuis la base de données)
        $scope.listeCategoriesConsultation = [];
        $scope.listeTypesConsultation = [];

        // Types d'examen autorisés selon le type de consultation
        $scope.listeTypesExamen = [];

        // Types d'examens prédéfinis
//    $scope.typesExamen = [
//      'Analyse de sang',
//      'Radiographie',
//      'Échographie',
//      'Scanner',
//      'IRM',
//      'ECG',
//      'EEG',
//      'Endoscopie',
//      'Biopsie'
//    ];

        // ==================== INITIALISATION ====================

        // Fonction d'initialisation au chargement de la page
        $scope.init = function () {
            // Charger les types d'observations
            $scope.chargerTypesObservation();

            // Charger les catégories d'antécédents
            $scope.chargerCategoriesAntecedent();

            // Charger les catégories de consultation
            $scope.chargerCategoriesConsultation();

            // Récupérer l'ID du patient depuis l'URL ou la session
            var patientId = getParameterByName('patientId');

            if (patientId) {
                $scope.consultation.patientId = patientId;
                $scope.chargerPatient(patientId);
            }

            // Charger les antécédents existants du patient
            if ($scope.patient.id) {
                $scope.chargerAntecedentsPatient($scope.patient.id);
            }

            // Initialiser Select2 après un court délai
            setTimeout(function () {
                initializeSelect2Consultation();
            }, 500);
        };

        // Fonction pour initialiser Select2 sur les selects de consultation
        function initializeSelect2Consultation() {
            // Détruire les instances existantes si elles existent
            try {
                if ($('#selectCategorieConsultation').hasClass('select2-hidden-accessible')) {
                    $('#selectCategorieConsultation').select2('destroy');
                }
                if ($('#selectTypeConsultation').hasClass('select2-hidden-accessible')) {
                    $('#selectTypeConsultation').select2('destroy');
                }
            } catch (e) {
                console.log("Pas d'instance Select2 à détruire");
            }

            // Initialiser Select2 pour la catégorie
            $('#selectCategorieConsultation').select2({
                placeholder: "-- Sélectionner une catégorie --",
                allowClear: true,
                language: "fr"
            }).on('change', function () {
                $scope.$apply(function () {
                    $scope.consultation.categorieConsultation = $('#selectCategorieConsultation').val();
                    console.log('✅ Catégorie sélectionnée via Select2:', $scope.consultation.categorieConsultation);
                    $scope.onCategorieConsultationChange();
                });
            });

            // Initialiser Select2 pour le type
            $('#selectTypeConsultation').select2({
                placeholder: "-- Sélectionner un type --",
                allowClear: true,
                language: "fr"
            }).on('change', function () {
                $scope.$apply(function () {
                    $scope.consultation.typeConsultation = $('#selectTypeConsultation').val();
                    console.log('✅ Type consultation sélectionné via Select2:', $scope.consultation.typeConsultation);
                    // Charger les types d'examen autorisés
                    $scope.onTypeConsultationChange();
                });
            });

            console.log('✅ Select2 initialisé pour les consultations');
        }

        // Fonction pour initialiser Select2 sur le select de type d'examen
        function initializeSelect2TypeExamen() {
            try {
                if ($('#selectTypeExamen').hasClass('select2-hidden-accessible')) {
                    $('#selectTypeExamen').select2('destroy');
                }
            } catch (e) {
                console.log("Pas d'instance Select2 à détruire pour type examen");
            }

            $('#selectTypeExamen').select2({
                placeholder: "-- Sélectionner un type d'examen --",
                allowClear: true,
                language: "fr"
            }).on('change', function () {
                $scope.$apply(function () {
                    $scope.nouvelExamen.typeExamen = $('#selectTypeExamen').val();
                    console.log('✅ Type examen sélectionné via Select2:', $scope.nouvelExamen.typeExamen);
                });
            });

            console.log('✅ Select2 initialisé pour les types d\'examen');
        }

        // Fonction utilitaire pour récupérer les paramètres URL
        function getParameterByName(name) {
            var url = window.location.href;
            name = name.replace(/[\[\]]/g, '\\$&');
            var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
            var results = regex.exec(url);
            if (!results)
                return null;
            if (!results[2])
                return '';
            return decodeURIComponent(results[2].replace(/\+/g, ' '));
        }

        // ==================== CHARGEMENT DES DONNÉES ====================

        // Charger les types d'observations depuis la base de données
        $scope.chargerTypesObservation = function () {
            GenericService.get(listTypesObservationURL)
                    .then(function (data) {
                        if (data && data.listTypesObservation) {
                            $scope.listeTypesObservation = data.listTypesObservation;
                            console.log(' Types d\'observations chargés:', $scope.listeTypesObservation);
                            console.log('   Nombre de types:', $scope.listeTypesObservation.length);
                        } else {
                            console.warn('Aucun type d\'observation trouvé dans la réponse');
                        }
                    })
                    .catch(function (error) {
                        console.error('Erreur lors du chargement des types d\'observations:', error);
                    });
        };

        // Charger les catégories d'antécédents depuis la base de données
        $scope.chargerCategoriesAntecedent = function () {
            GenericService.get(listCategoriesAntecedentURL)
                    .then(function (data) {
                        if (data && data.listCategoriesAntecedent) {
                            $scope.listeCategoriesAntecedent = data.listCategoriesAntecedent;
                            console.log(' Catégories d\'antécédents chargées:', $scope.listeCategoriesAntecedent);
                            console.log('   Nombre de catégories:', $scope.listeCategoriesAntecedent.length);
                        } else {
                            console.warn('Aucune catégorie d\'antécédent trouvée dans la réponse');
                        }
                    })
                    .catch(function (error) {
                        console.error('Erreur lors du chargement des catégories d\'antécédents:', error);
                    });
        };

        // Charger les types d'antécédents par catégorie
        $scope.chargerTypesAntecedentByCategorie = function (idCategorie) {
            var url = listTypesAntecedentByCategorieURL + '?idCategorie=' + idCategorie;

            GenericService.get(url)
                    .then(function (data) {
                        if (data && data.listTypesAntecedent) {
                            $scope.listeTypesAntecedent = data.listTypesAntecedent;
                            console.log(' Types d\'antécédents chargés pour la catégorie:', $scope.listeTypesAntecedent);
                            console.log(' Nombre de types:', $scope.listeTypesAntecedent.length);
                        } else {
                            console.warn(' Aucun type d\'antécédent trouvé dans la réponse');
                            $scope.listeTypesAntecedent = [];
                        }
                    })
                    .catch(function (error) {
                        console.error(' Erreur lors du chargement des types d\'antécédents:', error);
                        $scope.listeTypesAntecedent = [];
                    });
        };

        // Charger les catégories de consultation depuis la base de données
        $scope.chargerCategoriesConsultation = function () {
            GenericService.get(listCategoriesConsultationURL)
                    .then(function (data) {
                        if (data && data.listCategoriesConsultation) {
                            $scope.listeCategoriesConsultation = data.listCategoriesConsultation;
                            console.log('Catégories de consultation chargées:', $scope.listeCategoriesConsultation);
                            console.log('   Nombre de catégories:', $scope.listeCategoriesConsultation.length);
                        } else {
                            console.warn('Aucune catégorie de consultation trouvée dans la réponse');
                        }
                    })
                    .catch(function (error) {
                        console.error(' Erreur lors du chargement des catégories de consultation:', error);
                    });
        };

        // Charger les types de consultation par catégorie
        $scope.chargerTypesConsultationByCategorie = function (idCategorie) {
            var url = listTypesConsultationByCategorieURL + '?idCategorie=' + idCategorie;

            GenericService.get(url)
                    .then(function (data) {
                        if (data && data.listTypesConsultation) {
                            $scope.listeTypesConsultation = data.listTypesConsultation;
                            console.log('✅ Types de consultation chargés pour la catégorie:', $scope.listeTypesConsultation);
                            console.log('   Nombre de types:', $scope.listeTypesConsultation.length);
                        } else {
                            console.warn('⚠️ Aucun type de consultation trouvé dans la réponse');
                            $scope.listeTypesConsultation = [];
                        }
                    })
                    .catch(function (error) {
                        console.error('❌ Erreur lors du chargement des types de consultation:', error);
                        $scope.listeTypesConsultation = [];
                    });
        };

        // Charger les types d'examen autorisés pour un type de consultation
        $scope.chargerTypesExamenByTypeConsultation = function (idTypeConsultation) {
            var url = listTypesExamenByTypeConsultationURL + '?idTypeConsultation=' + idTypeConsultation;

            GenericService.get(url)
                    .then(function (data) {
                        if (data && data.listTypesExamen) {
                            $scope.listeTypesExamen = data.listTypesExamen;
                            console.log('✅ Types d\'examen chargés pour le type de consultation:', $scope.listeTypesExamen);
                            console.log('   Nombre de types d\'examen:', $scope.listeTypesExamen.length);

                            // Réinitialiser Select2 pour le type d'examen
                            setTimeout(function () {
                                initializeSelect2TypeExamen();
                            }, 100);
                        } else {
                            console.warn('⚠️ Aucun type d\'examen trouvé dans la réponse');
                            $scope.listeTypesExamen = [];
                        }
                    })
                    .catch(function (error) {
                        console.error('❌ Erreur lors du chargement des types d\'examen:', error);
                        $scope.listeTypesExamen = [];
                    });
        };

        // Charger les informations du patient
        $scope.chargerPatient = function (patientId) {
            $http.get('/api/patients/' + patientId)
                    .then(function (response) {
                        $scope.patient = response.data;
                        $scope.patient.dateNaissance = new Date($scope.patient.dateNaissance);
                    })
                    .catch(function (error) {
                        console.error('Erreur lors du chargement du patient:', error);
                        alert('Erreur lors du chargement des informations du patient');
                    });
        };

        // Charger les antécédents existants du patient
        $scope.chargerAntecedentsPatient = function (patientId) {
            var url = appUrl + 'api/antecedent/listAllAntecedentsPatient?idPatient=' + patientId;

            $http.get(url)
                    .then(function (response) {
                        if (response && response.data && response.data.listAntecedents) {
                            $scope.antecedentsPatient = response.data.listAntecedents;
                            console.log(' Antécédents du patient chargés:', $scope.antecedentsPatient.length);
                        } else {
                            $scope.antecedentsPatient = [];
                        }
                    })
                    .catch(function (error) {
                        console.error(' Erreur lors du chargement des antécédents:', error);
                        $scope.antecedentsPatient = [];
                    });
        };

        // ==================== OBSERVATIONS ====================

        // Fonction appelée quand on change le type d'observation
        $scope.onTypeObservationChange = function () {
            if ($scope.nouvelleObservation.typeObservation) {
                var typeObs = $scope.nouvelleObservation.typeObservation;

                // Auto-remplir l'unité
                $scope.nouvelleObservation.unite = typeObs.unite || '';

                // Stocker les valeurs min et max pour la validation
                $scope.nouvelleObservation.valeurMin = typeObs.valeurMin || '';
                $scope.nouvelleObservation.valeurMax = typeObs.valeurMax || '';

                console.log('Type d\'observation sélectionné:', typeObs.libelle);
                console.log('   - Unité:', $scope.nouvelleObservation.unite);
                console.log('   - Min:', $scope.nouvelleObservation.valeurMin);
                console.log('   - Max:', $scope.nouvelleObservation.valeurMax);
            } else {
                // Réinitialiser si aucun type sélectionné
                $scope.nouvelleObservation.unite = '';
                $scope.nouvelleObservation.valeurMin = '';
                $scope.nouvelleObservation.valeurMax = '';
            }
        };

        // Ajouter une observation
        $scope.ajouterObservation = function () {
            // Validation des champs obligatoires
            if (!$scope.nouvelleObservation.typeObservation || !$scope.nouvelleObservation.valeur) {
                alert('Veuillez remplir le type et la valeur de l\'observation');
                return;
            }

            // Validation de la valeur (doit être entre min et max)
            var valeur = parseFloat($scope.nouvelleObservation.valeur);
            var valeurMin = parseFloat($scope.nouvelleObservation.valeurMin);
            var valeurMax = parseFloat($scope.nouvelleObservation.valeurMax);

            if (isNaN(valeur)) {
                alert('La valeur doit être un nombre valide');
                return;
            }

            if (!isNaN(valeurMin) && valeur < valeurMin) {
                alert('La valeur doit être supérieure ou égale à ' + valeurMin + ' ' + $scope.nouvelleObservation.unite);
                return;
            }

            if (!isNaN(valeurMax) && valeur > valeurMax) {
                alert('La valeur doit être inférieure ou égale à ' + valeurMax + ' ' + $scope.nouvelleObservation.unite);
                return;
            }

            // Ajouter l'observation à la liste
            $scope.observations.push({
                id: Date.now(),
                idTypeObservation: $scope.nouvelleObservation.typeObservation.id,
                typeObservation: $scope.nouvelleObservation.typeObservation,
                type: $scope.nouvelleObservation.typeObservation.libelle,
                valeur: $scope.nouvelleObservation.valeur,
                unite: $scope.nouvelleObservation.unite,
                commentaire: '', // Peut être ajouté plus tard si nécessaire
                date: new Date()
            });

            console.log('✅ Observation ajoutée:', $scope.observations[$scope.observations.length - 1]);

            // Réinitialiser le formulaire
            $scope.nouvelleObservation = {
                typeObservation: null,
                valeur: '',
                unite: '',
                valeurMin: '',
                valeurMax: '',
                date: new Date()
            };
        };

        // Supprimer une observation
        $scope.supprimerObservation = function (index) {
            if (confirm('Voulez-vous vraiment supprimer cette observation ?')) {
                $scope.observations.splice(index, 1);
            }
        };

        // Modifier une observation
        $scope.modifierObservation = function (index) {
            var obs = $scope.observations[index];
            $scope.nouvelleObservation = angular.copy(obs);
            $scope.observations.splice(index, 1);
        };

        // ==================== ANTÉCÉDENTS ====================

        // Fonction appelée quand on change la catégorie d'antécédent
        $scope.onCategorieAntecedentChange = function () {
            if ($scope.nouvelAntecedent.categorieAntecedent) {
                var categorie = $scope.nouvelAntecedent.categorieAntecedent;

                console.log('📋 Catégorie d\'antécédent sélectionnée:', categorie.libelle);

                // Charger les types d'antécédents pour cette catégorie
                $scope.chargerTypesAntecedentByCategorie(categorie.id);

                // Réinitialiser le type sélectionné
                $scope.nouvelAntecedent.typeAntecedent = null;
            } else {
                // Réinitialiser si aucune catégorie sélectionnée
                $scope.listeTypesAntecedent = [];
                $scope.nouvelAntecedent.typeAntecedent = null;
            }
        };

        // ==================== CONSULTATION ====================

        // Fonction appelée quand on change la catégorie de consultation
        $scope.onCategorieConsultationChange = function () {
            console.log('🔔 onCategorieConsultationChange appelée !');
            console.log('   Catégorie actuelle:', $scope.consultation.categorieConsultation);

            if ($scope.consultation.categorieConsultation && $scope.consultation.categorieConsultation !== '') {
                var idCategorie = $scope.consultation.categorieConsultation;

                console.log('📋 Chargement des types pour la catégorie:', idCategorie);

                // Charger les types de consultation pour cette catégorie
                $scope.chargerTypesConsultationByCategorie(idCategorie);

                // Réinitialiser le type sélectionné
                $scope.consultation.typeConsultation = '';

                // Réinitialiser Select2 pour le type
                setTimeout(function () {
                    $('#selectTypeConsultation').val('').trigger('change.select2');
                }, 100);
            } else {
                console.log('⚠️ Aucune catégorie sélectionnée');
                // Réinitialiser si aucune catégorie sélectionnée
                $scope.listeTypesConsultation = [];
                $scope.consultation.typeConsultation = '';

                // Réinitialiser Select2 pour le type
                setTimeout(function () {
                    $('#selectTypeConsultation').val('').trigger('change.select2');
                }, 100);
            }
        };

        // Fonction appelée quand on change le type de consultation
        $scope.onTypeConsultationChange = function () {
            console.log('🔔 onTypeConsultationChange appelée !');
            console.log('   Type consultation actuel:', $scope.consultation.typeConsultation);

            if ($scope.consultation.typeConsultation && $scope.consultation.typeConsultation !== '') {
                var idTypeConsultation = $scope.consultation.typeConsultation;

                console.log('📋 Chargement des types d\'examen pour le type de consultation:', idTypeConsultation);

                // Charger les types d'examen autorisés pour ce type de consultation
                $scope.chargerTypesExamenByTypeConsultation(idTypeConsultation);

                // Réinitialiser le type d'examen sélectionné
                $scope.nouvelExamen.typeExamen = '';
            } else {
                console.log('⚠️ Aucun type de consultation sélectionné');
                // Réinitialiser si aucun type de consultation sélectionné
                $scope.listeTypesExamen = [];
                $scope.nouvelExamen.typeExamen = '';
            }
        };

        // Ajouter un antécédent
        $scope.ajouterAntecedent = function () {
            // Validation des champs obligatoires
            if (!$scope.nouvelAntecedent.categorieAntecedent ||
                    !$scope.nouvelAntecedent.typeAntecedent ||
                    !$scope.nouvelAntecedent.description) {
                alert('Veuillez remplir la catégorie, le type et la description de l\'antécédent');
                return;
            }

            // Ajouter l'antécédent à la liste
            $scope.antecedents.push({
                id: Date.now(),
                categorieAntecedent: $scope.nouvelAntecedent.categorieAntecedent,
                typeAntecedent: $scope.nouvelAntecedent.typeAntecedent,
                categorie: $scope.nouvelAntecedent.categorieAntecedent.libelle,
                type: $scope.nouvelAntecedent.typeAntecedent.libelle,
                description: $scope.nouvelAntecedent.description,
                dateDebut: $scope.nouvelAntecedent.dateDebut || new Date(),
                statut: 'Actif'
            });

            console.log('Antécédent ajouté:', $scope.antecedents[$scope.antecedents.length - 1]);

            // Réinitialiser le formulaire
            $scope.nouvelAntecedent = {
                categorieAntecedent: null,
                typeAntecedent: null,
                description: '',
                dateDebut: null
            };

            // Réinitialiser la liste des types
            $scope.listeTypesAntecedent = [];
        };

        // Supprimer un antécédent
        $scope.supprimerAntecedent = function (index) {
            if (confirm('Voulez-vous vraiment supprimer cet antécédent ?')) {
                $scope.antecedents.splice(index, 1);
            }
        };

        // ==================== PRESCRIPTIONS ====================

        // Ajouter une prescription
        $scope.ajouterPrescription = function () {
            if (!$scope.nouvellePrescription.medicament || !$scope.nouvellePrescription.posologie) {
                alert('Veuillez remplir le médicament et la posologie');
                return;
            }

            $scope.prescriptions.push({
                id: Date.now(),
                medicament: $scope.nouvellePrescription.medicament,
                posologie: $scope.nouvellePrescription.posologie,
                duree: $scope.nouvellePrescription.duree
            });

            // Réinitialiser le formulaire
            $scope.nouvellePrescription = {
                medicament: '',
                posologie: '',
                duree: ''
            };
        };

        // Supprimer une prescription
        $scope.supprimerPrescription = function (index) {
            if (confirm('Voulez-vous vraiment supprimer cette prescription ?')) {
                $scope.prescriptions.splice(index, 1);
            }
        };

        // ==================== EXAMENS ====================

        // Ajouter un examen
        // Gérer la sélection de fichier pour l'examen
        $scope.handleFileSelect = function (files) {
            if (files && files.length > 0) {
                var file = files[0];

                // Vérifier la taille (max 5MB)
                if (file.size > 5 * 1024 * 1024) {
                    alert('Le fichier est trop volumineux. Taille maximale: 5MB');
                    document.getElementById('fichierExamen').value = '';
                    return;
                }

                // Convertir en Base64
                var reader = new FileReader();
                reader.onload = function (e) {
                    $scope.$apply(function () {
                        $scope.nouvelExamen.fichierJoint = e.target.result;
                        $scope.nouvelExamen.nomFichier = file.name;
                        console.log('✅ Fichier chargé:', file.name, '(' + (file.size / 1024).toFixed(2) + ' KB)');
                    });
                };
                reader.readAsDataURL(file);
            }
        };

        $scope.ajouterExamen = function () {
            console.log('🔍 Tentative d\'ajout d\'examen...');
            console.log('   Type examen sélectionné:', $scope.nouvelExamen.typeExamen);
            console.log('   Liste types examen disponibles:', $scope.listeTypesExamen);

            if (!$scope.nouvelExamen.typeExamen) {
                alert('Veuillez sélectionner le type d\'examen');
                return;
            }

            // Trouver le libellé du type d'examen sélectionné
            var typeExamenObj = $scope.listeTypesExamen.find(function (type) {
                return type.id === $scope.nouvelExamen.typeExamen;
            });

            var libelleTypeExamen = typeExamenObj ? typeExamenObj.libelle : $scope.nouvelExamen.typeExamen;

            console.log('   Type examen trouvé:', typeExamenObj);
            console.log('   Libellé:', libelleTypeExamen);

            $scope.examens.push({
                id: Date.now(),
                idTypeExamen: $scope.nouvelExamen.typeExamen,
                typeExamen: libelleTypeExamen,
                resultat: $scope.nouvelExamen.resultat,
                fichierJoint: $scope.nouvelExamen.fichierJoint,
                nomFichier: $scope.nouvelExamen.nomFichier
            });

            console.log('✅ Examen ajouté:', $scope.examens[$scope.examens.length - 1]);

            // Réinitialiser le formulaire
            $scope.nouvelExamen = {
                typeExamen: '',
                resultat: '',
                fichierJoint: '',
                nomFichier: ''
            };

            // Réinitialiser le champ fichier
            document.getElementById('fichierExamen').value = '';

            // Réinitialiser Select2
            setTimeout(function () {
                $('#selectTypeExamen').val('').trigger('change.select2');
            }, 100);
        };

        // Supprimer un examen
        $scope.supprimerExamen = function (index) {
            if (confirm('Voulez-vous vraiment supprimer cet examen ?')) {
                $scope.examens.splice(index, 1);
            }
        };

        // ==================== ENREGISTREMENT ====================

        // Enregistrer la consultation
        $scope.enregistrerConsultation = function () {
            // Validation
            if (!$scope.consultation.patientId) {
                alert('Aucun patient sélectionné');
                return;
            }

            if (!$scope.consultation.categorieConsultation) {
                alert('Veuillez sélectionner une catégorie de consultation');
                return;
            }

            if (!$scope.consultation.typeConsultation) {
                alert('Veuillez sélectionner un type de consultation');
                return;
            }

            if (!$scope.consultation.motif || !$scope.consultation.motif.trim()) {
                alert('Veuillez remplir le motif de la consultation');
                return;
            }

            if (!$scope.consultation.diagnostic || !$scope.consultation.diagnostic.trim()) {
                alert('Veuillez remplir le diagnostic');
                return;
            }

            // Préparer les données à envoyer
            var consultationDTO = {
                idPatient: $scope.consultation.patientId,
                categorieConsultation: $scope.consultation.categorieConsultation,
                typeConsultation: $scope.consultation.typeConsultation,
                dateConsultation: $scope.consultation.dateConsultation.toISOString(), // Convertir en ISO String
                motif: $scope.consultation.motif,
                diagnostic: $scope.consultation.diagnostic,
                traitement: $scope.consultation.traitement,
                prescriptions: ($scope.prescriptions && $scope.prescriptions.length > 0) ? $scope.prescriptions.map(function (p) {
                    return {
                        medicament: p.medicament,
                        posologie: p.posologie,
                        duree: p.duree
                    };
                }) : [],
                examens: ($scope.examens && $scope.examens.length > 0) ? $scope.examens.map(function (e) {
                    return {
                        typeExamen: e.idTypeExamen,
                        resultat: e.resultat,
                        fichierJoint: e.fichierJoint
                    };
                }) : [],
                observations: ($scope.observations && $scope.observations.length > 0) ? $scope.observations.map(function (o) {
                    return {
                        typeObservation: o.idTypeObservation,
                        valeur: o.valeur,
                        commentaire: o.commentaire
                    };
                }) : []
            };
            console.log('📤 Données à enregistrer:', consultationDTO);
            console.log('   Nombre de prescriptions:', consultationDTO.prescriptions.length);
            console.log('   Nombre d\'examens:', consultationDTO.examens.length);
            console.log('   Nombre d\'observations:', consultationDTO.observations.length);
            console.log('   Liste examens brute ($scope.examens):', $scope.examens);
            console.log('   Liste examens mappée (consultationDTO.examens):', consultationDTO.examens);

            // Appel API pour enregistrer
            $http.post(enregistrerConsultationURL, consultationDTO)
                    .then(function (response) {
                        console.log('✅ Réponse du serveur:', response.data);

                        if (response.data.success) {
                            alert('✅ Consultation enregistrée avec succès!\n\nID: ' + response.data.consultationId);

                            // Réinitialiser le formulaire
                            $scope.reinitialiser();

                            // Redirection vers la page de détail du patient
                            window.location.href = appUrl + 'gestion/patient/detail?id=' + $scope.consultation.patientId;
                        } else {
                            alert('❌ Erreur: ' + response.data.message);
                        }
                    })
                    .catch(function (error) {
                        console.error('❌ Erreur lors de l\'enregistrement:', error);

                        if (error.data && error.data.message) {
                            alert('❌ Erreur lors de l\'enregistrement: ' + error.data.message);
                        } else {
                            alert('❌ Erreur lors de l\'enregistrement de la consultation. Veuillez réessayer.');
                        }
                    });
        };

        // ==================== ANNULATION ====================

        // Annuler et réinitialiser
        $scope.annuler = function () {
            if (confirm('Voulez-vous vraiment annuler? Toutes les données non sauvegardées seront perdues.')) {
                $scope.reinitialiser();
                // Redirection
                window.history.back();
                // Ou: $location.path('/consultations/liste');
            }
        };

        // Réinitialiser le formulaire
        $scope.reinitialiser = function () {
            $scope.consultation = {
                id: null,
                dateConsultation: new Date(),
                categorieConsultation: '',
                typeConsultation: '',
                motif: '',
                diagnostic: '',
                traitement: '',
                patientId: $scope.consultation.patientId // Garder le patient
            };
            $scope.observations = [];
            $scope.antecedents = [];
            $scope.prescriptions = [];
            $scope.examens = [];

            // Réinitialiser les formulaires temporaires
            $scope.nouvelleObservation = {type: '', valeur: '', unite: '', date: new Date()};
            $scope.nouvelAntecedent = {type: '', description: '', dateDebut: null};
            $scope.nouvellePrescription = {medicament: '', posologie: '', duree: ''};
            $scope.nouvelExamen = {typeExamen: '', resultat: '', fichierJoint: ''};
        };

        // ==================== FONCTIONS UTILITAIRES ====================

        // Calculer l'IMC
        $scope.calculerIMC = function () {
            var poids = null;
            var taille = null;

            // Chercher le poids et la taille dans les observations
            $scope.observations.forEach(function (obs) {
                if (obs.type === 'Poids' && obs.unite === 'kg') {
                    poids = parseFloat(obs.valeur);
                }
                if (obs.type === 'Taille' && (obs.unite === 'm' || obs.unite === 'cm')) {
                    taille = parseFloat(obs.valeur);
                    if (obs.unite === 'cm') {
                        taille = taille / 100; // Convertir en mètres
                    }
                }
            });

            if (poids && taille) {
                var imc = (poids / (taille * taille)).toFixed(2);

                // Ajouter l'IMC aux observations
                $scope.observations.push({
                    id: Date.now(),
                    type: 'IMC',
                    valeur: imc,
                    unite: 'kg/m²',
                    date: new Date()
                });

                alert('IMC calculé: ' + imc + ' kg/m²');
            } else {
                alert('Veuillez d\'abord ajouter le poids (en kg) et la taille (en m ou cm)');
            }
        };

        // Imprimer la consultation
        $scope.imprimerConsultation = function () {
            window.print();
        };

        // ==================== INITIALISATION AU CHARGEMENT ====================
        $scope.init();

    }]);