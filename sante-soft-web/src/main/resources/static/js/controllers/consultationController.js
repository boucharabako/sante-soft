'use strict';
var App;

App.controller('consultationController', ['$scope', '$http', '$location','$rootScope','PropagationService', function($scope, $http, $location,$rootScope,PropagationService) {
    
    // Initialisation des données
    $scope.consultation = {
      id: null,
      dateConsultation: new Date(),
      motif: '',
      diagnostic: '',
      traitement: '',
      patientId: null
    };
    
    

    // Patient sélectionné (à charger depuis l'API ou la session)
    $scope.patient = {
      id: null,
      nom: "",
      prenom: "",
      groupeSanguin: "",
      numeroCamet: "",
      dateNaissance: null
    };
    
    $scope.patient = PropagationService.getPatientSender();
        jslog("------------+++++++++++++++++++++++-------COMPTE OBJ " + angular.toJson(PropagationService.getPatientSender()));
    
    $rootScope.$on("patientSender", function () {
        $scope.patient = PropagationService.getPatientSender();
        jslog("-------------------COMPTE OBJ " + angular.toJson($scope.patient));
    });

    // Listes pour les éléments multiples
    $scope.observations = [];
    $scope.antecedents = [];
    $scope.prescriptions = [];
    $scope.examens = [];
    $scope.vaccinations = [];

    // Formulaires temporaires pour ajout
    $scope.nouvelleObservation = {
      type: '',
      valeur: '',
      unite: '',
      date: new Date()
    };

    $scope.nouvelAntecedent = {
      type: '',
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

    // Types d'observations prédéfinis
    $scope.typesObservation = [
      'Tension artérielle',
      'Température',
      'Poids',
      'Taille',
      'Fréquence cardiaque',
      'Saturation O2',
      'Glycémie',
      'IMC'
    ];

    // Types d'examens prédéfinis
    $scope.typesExamen = [
      'Analyse de sang',
      'Radiographie',
      'Échographie',
      'Scanner',
      'IRM',
      'ECG',
      'EEG',
      'Endoscopie',
      'Biopsie'
    ];

    // ==================== INITIALISATION ====================
    
    // Fonction d'initialisation au chargement de la page
    $scope.init = function() {
      // Récupérer l'ID du patient depuis l'URL ou la session
      var patientId = getParameterByName('patientId');
      
      if (patientId) {
        $scope.consultation.patientId = patientId;
        $scope.chargerPatient(patientId);
      }
      
      // Charger les antécédents existants du patient
      if (patientId) {
        $scope.chargerAntecedentsPatient(patientId);
      }
    };

    // Fonction utilitaire pour récupérer les paramètres URL
    function getParameterByName(name) {
      var url = window.location.href;
      name = name.replace(/[\[\]]/g, '\\$&');
      var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
      var results = regex.exec(url);
      if (!results) return null;
      if (!results[2]) return '';
      return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }

    // ==================== CHARGEMENT DES DONNÉES ====================

    // Charger les informations du patient
    $scope.chargerPatient = function(patientId) {
      $http.get('/api/patients/' + patientId)
        .then(function(response) {
          $scope.patient = response.data;
          $scope.patient.dateNaissance = new Date($scope.patient.dateNaissance);
        })
        .catch(function(error) {
          console.error('Erreur lors du chargement du patient:', error);
          alert('Erreur lors du chargement des informations du patient');
        });
    };

    // Charger les antécédents existants du patient
    $scope.chargerAntecedentsPatient = function(patientId) {
      $http.get('/api/antecedents/patient/' + patientId)
        .then(function(response) {
          $scope.antecedents = response.data;
        })
        .catch(function(error) {
          console.error('Erreur lors du chargement des antécédents:', error);
        });
    };

    // ==================== OBSERVATIONS ====================

    // Ajouter une observation
    $scope.ajouterObservation = function() {
      if (!$scope.nouvelleObservation.type || !$scope.nouvelleObservation.valeur) {
        alert('Veuillez remplir le type et la valeur de l\'observation');
        return;
      }

      $scope.observations.push({
        id: Date.now(),
        type: $scope.nouvelleObservation.type,
        valeur: $scope.nouvelleObservation.valeur,
        unite: $scope.nouvelleObservation.unite,
        date: new Date()
      });
      
      // Réinitialiser le formulaire
      $scope.nouvelleObservation = {
        type: '',
        valeur: '',
        unite: '',
        date: new Date()
      };
    };

    // Supprimer une observation
    $scope.supprimerObservation = function(index) {
      if (confirm('Voulez-vous vraiment supprimer cette observation ?')) {
        $scope.observations.splice(index, 1);
      }
    };

    // Modifier une observation
    $scope.modifierObservation = function(index) {
      var obs = $scope.observations[index];
      $scope.nouvelleObservation = angular.copy(obs);
      $scope.observations.splice(index, 1);
    };

    // ==================== ANTÉCÉDENTS ====================

    // Ajouter un antécédent
    $scope.ajouterAntecedent = function() {
      if (!$scope.nouvelAntecedent.type || !$scope.nouvelAntecedent.description) {
        alert('Veuillez remplir le type et la description de l\'antécédent');
        return;
      }

      $scope.antecedents.push({
        id: Date.now(),
        type: $scope.nouvelAntecedent.type,
        description: $scope.nouvelAntecedent.description,
        dateDebut: $scope.nouvelAntecedent.dateDebut || new Date(),
        statut: 'string'
      });
      
      // Réinitialiser le formulaire
      $scope.nouvelAntecedent = {
        type: '',
        description: '',
        dateDebut: null
      };
    };

    // Supprimer un antécédent
    $scope.supprimerAntecedent = function(index) {
      if (confirm('Voulez-vous vraiment supprimer cet antécédent ?')) {
        $scope.antecedents.splice(index, 1);
      }
    };

    // ==================== PRESCRIPTIONS ====================

    // Ajouter une prescription
    $scope.ajouterPrescription = function() {
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
    $scope.supprimerPrescription = function(index) {
      if (confirm('Voulez-vous vraiment supprimer cette prescription ?')) {
        $scope.prescriptions.splice(index, 1);
      }
    };

    // ==================== EXAMENS ====================

    // Ajouter un examen
    $scope.ajouterExamen = function() {
      if (!$scope.nouvelExamen.typeExamen) {
        alert('Veuillez sélectionner le type d\'examen');
        return;
      }

      $scope.examens.push({
        id: Date.now(),
        typeExamen: $scope.nouvelExamen.typeExamen,
        resultat: $scope.nouvelExamen.resultat,
        fichierJoint: $scope.nouvelExamen.fichierJoint
      });
      
      // Réinitialiser le formulaire
      $scope.nouvelExamen = {
        typeExamen: '',
        resultat: '',
        fichierJoint: ''
      };
    };

    // Supprimer un examen
    $scope.supprimerExamen = function(index) {
      if (confirm('Voulez-vous vraiment supprimer cet examen ?')) {
        $scope.examens.splice(index, 1);
      }
    };

    // ==================== ENREGISTREMENT ====================

    // Enregistrer la consultation
    $scope.enregistrerConsultation = function() {
      // Validation
      if (!$scope.consultation.motif || !$scope.consultation.motif.trim()) {
        alert('Veuillez remplir le motif de la consultation');
        return;
      }

      if (!$scope.consultation.diagnostic || !$scope.consultation.diagnostic.trim()) {
        alert('Veuillez remplir le diagnostic');
        return;
      }

      if (!$scope.consultation.patientId) {
        alert('Aucun patient sélectionné');
        return;
      }

      // Préparer les données à envoyer
      var donnees = {
        consultation: {
          dateConsultation: $scope.consultation.dateConsultation,
          motif: $scope.consultation.motif,
          diagnostic: $scope.consultation.diagnostic,
          traitement: $scope.consultation.traitement,
          patientId: $scope.consultation.patientId
        },
        observations: $scope.observations,
        antecedents: $scope.antecedents,
        prescriptions: $scope.prescriptions,
        examens: $scope.examens
      };

      console.log('Données à enregistrer:', donnees);

      // Appel API pour enregistrer
      $http.post('/api/consultations', donnees)
        .then(function(response) {
          alert('Consultation enregistrée avec succès!');
          console.log('Réponse du serveur:', response.data);
          
          // Redirection vers la liste des consultations ou le détail
          $location.path('/consultations/liste');
          // Ou: window.location.href = '/consultations/liste';
        })
        .catch(function(error) {
          console.error('Erreur lors de l\'enregistrement:', error);
          
          if (error.data && error.data.message) {
            alert('Erreur lors de l\'enregistrement: ' + error.data.message);
          } else {
            alert('Erreur lors de l\'enregistrement de la consultation. Veuillez réessayer.');
          }
        });
    };

    // ==================== ANNULATION ====================

    // Annuler et réinitialiser
    $scope.annuler = function() {
      if (confirm('Voulez-vous vraiment annuler? Toutes les données non sauvegardées seront perdues.')) {
        $scope.reinitialiser();
        // Redirection
        window.history.back();
        // Ou: $location.path('/consultations/liste');
      }
    };

    // Réinitialiser le formulaire
    $scope.reinitialiser = function() {
      $scope.consultation = {
        id: null,
        dateConsultation: new Date(),
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
      $scope.nouvelleObservation = { type: '', valeur: '', unite: '', date: new Date() };
      $scope.nouvelAntecedent = { type: '', description: '', dateDebut: null };
      $scope.nouvellePrescription = { medicament: '', posologie: '', duree: '' };
      $scope.nouvelExamen = { typeExamen: '', resultat: '', fichierJoint: '' };
    };

    // ==================== FONCTIONS UTILITAIRES ====================

    // Calculer l'IMC
    $scope.calculerIMC = function() {
      var poids = null;
      var taille = null;
      
      // Chercher le poids et la taille dans les observations
      $scope.observations.forEach(function(obs) {
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
    $scope.imprimerConsultation = function() {
      window.print();
    };

    // ==================== INITIALISATION AU CHARGEMENT ====================
    $scope.init();

  }]);