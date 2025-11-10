/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


'use strict';
var App;
App.controller("userController", ['$scope', 'GenericService', function ($scope, GenericService) {
        var urlDeBase = appUrl + "fonctionnelle/tableauDeBord";
        var getTableauDeBordURL = urlDeBase + "/getTableauDeBord";
        $scope.choix = 1;
        $scope.annee = 2023;
        var firstPage = 1;
        var initialNumerOfElements = 5;
        $scope.pageSizeSelect;
        $scope.pageSizes = [5, 10, 20];
        $scope.sequence = [];
        $scope.selectedPageSize = null;
        $scope.paginationObject = {selectedPageSize: ""};
        $scope.pager = {startPage: "", endPage: "", totalPages: "", currentPage: "", buttonsToShow: ""};
        $scope.listePage = {number: ""};
        $scope.username = null;
        $scope.user = null;
//       $scope.utilisateur = {
//            id:null,
//            username:null,
//            email:null,
//            name:null,
//            isConnect: false 
//           
//        };

        $scope.disable = true;

        $scope.totalDeclaration = 0;
        $scope.totalRequisition = 0;
        $scope.totalDeclarationAvecSuite = 0;
        $scope.totalRequisitionAvecSuite = 0;
        $scope.totalSMS = 0;
        $scope.totalSMSLivre = 0;
        $scope.totalSMSLivrePourcentage = 50;
        $scope.totalSMSNonLivre = 0;
        $scope.totalSMSNonLivrePourcentage = 30;
        $scope.totalSMSEnAttente = 0;
        $scope.totalSMSEnAttentePourcentage = 10;
        $scope.totalSMSExpire = 0;
        $scope.totalSMSExpirePourcentage = 10;
        $scope.donneDeclaration = [];
        $scope.donneRequisition = [];
        $scope.donneRequisitionDeclaration = [];
        
        $scope.loadDiagramme = function (dec, req, decreq){
            var options = {
            chart: {
                height: 280,
                type: 'pie',
                dropShadow: {
                    enabled: true,
                    top: 10,
                    left: 0,
                    bottom: 0,
                    right: 0,
                    blur: 2,
                    color: '#45404e',
                    opacity: 0.15
                },
            },
            plotOptions: {
                pie: {
                    donut: {
                        size: '85%'
                    }
                }
            },
            dataLabels: {
                enabled: true,
            },
            stroke: {
                show: true,
                width: 2,
                colors: ['transparent']
            },
            series: [$scope.totalSMSLivrePourcentage, $scope.totalSMSEnAttentePourcentage, $scope.totalSMSExpirePourcentage, $scope.totalSMSNonLivrePourcentage],
            legend: {
                show: true,
                position: 'bottom',
                horizontalAlign: 'center',
                verticalAlign: 'middle',
                floating: false,
                fontSize: '14px',
                offsetX: 0,
                offsetY: -13
            },
            labels: ["SMS Livré", "SMS En attente", "SMS Expiré", "Non livré"],
            colors: ["#34bfa3", "#5d78ff", "#ff9f43", "#fd3c97"],

            responsive: [{
                    breakpoint: 600,
                    options: {
                        plotOptions: {
                            donut: {
                                customScale: 0.2
                            }
                        },
                        chart: {
                            height: 240
                        },
                        legend: {
                            show: false
                        },
                    }
                }],

            tooltip: {
                y: {
                    formatter: function (val) {
                        return   val + " %"
                    }
                }
            }

        };

        var chart = new ApexCharts(
                document.querySelector("#digramme-sms"),
                options
                );

        chart.render();

        var options1 = {
            chart: {
                height: 300,
                type: 'bar',
                toolbar: {
                    show: false
                },
                dropShadow: {
                    enabled: true,
                    top: 0,
                    left: 5,
                    bottom: 5,
                    right: 0,
                    blur: 5,
                    color: '#45404a2e',
                    opacity: 0.35
                },
            },
            plotOptions: {
                bar: {
                    horizontal: false,
                    endingShape: 'rounded',
                    columnWidth: '25%'
                }
            },
            dataLabels: {
                enabled: false
            },
            stroke: {
                show: true,
                width: 3,
                colors: ['transparent']
            },
            colors: ["#2c77f4", "#1ecab8", "#fd3c97"],
            series: [{
                    name: 'Réquisitions',
                    data: req
                }, {
                    name: 'Déclarations',
                    data: dec
                }, {
                    name: 'Déclarations Trouvés',
                    data: decreq
                }],
            xaxis: {
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                axisBorder: {
                    show: true
                },
                axisTicks: {
                    show: true
                }
            },
            legend: {
                offsetY: -10
            },
            yaxis: {
                title: {
                    text: 'Nombres'
                }
            },
            fill: {
                opacity: 1,
            },
            // legend: {
            //     floating: true
            // },
            grid: {
                row: {
                    colors: ['transparent', 'transparent'], // takes an array which will be repeated on columns
                    opacity: 0.2
                },
                borderColor: '#f1f3fa'
            },
            tooltip: {
                y: {
                    formatter: function (val) {
                        return "" + val + ""
                    }
                }
            }
        };

        var chart1 = new ApexCharts(
                document.querySelector("#diagramme-annuel"),
                options1
                );

        chart1.render();
        };

         $scope.tableauDeBord = function () {
            
            GenericService.get(getTableauDeBordURL + "/" + $scope.choix + "/" + $scope.annee)
                    .then(
                            function (data) {
                                $scope.totalDeclaration = data.totalDeclaration;
                                $scope.totalRequisition =  data.totalRequisition;
                                $scope.totalDeclarationAvecSuite =  data.totalDeclarationAvecSuite;
                                $scope.totalRequisitionAvecSuite =  data.totalRequisitionAvecSuite;
                                $scope.totalSMS =  data.totalSMS;
                                $scope.totalSMSLivre =  data.totalSMSLivre;
                                //$scope.totalSMSLivrePourcentage =  data.totalSMSLivrePourcentage;
                                $scope.totalSMSNonLivre =  data.totalSMSNonLivre;
                                //$scope.totalSMSNonLivrePourcentage =  data.totalSMSNonLivrePourcentage;
                                $scope.totalSMSEnAttente =  data.totalSMSEnAttente;
                                //$scope.totalSMSEnAttentePourcentage =  data.totalSMSEnAttentePourcentage;
                                $scope.totalSMSExpire =  data.totalSMSExpire;
                                //$scope.totalSMSExpirePourcentage =  data.totalSMSExpirePourcentage;
                                $scope.donneDeclaration =  data.donneDeclaration;
                                $scope.donneRequisition =  data.donneRequisition;
                                $scope.donneRequisitionDeclaration =  data.donneRequisitionDeclaration;
                                $scope.loadDiagramme($scope.donneDeclaration, $scope.donneRequisition,$scope.donneRequisitionDeclaration);
                                jslog("$scope.totalSMSLivrePourcentage"+$scope.totalSMSLivrePourcentage);
                                jslog("$scope.totalSMSNonLivrePourcentage"+$scope.totalSMSNonLivrePourcentage);
                                jslog("$scope.totalSMSEnAttentePourcentage"+$scope.totalSMSEnAttentePourcentage);
                                jslog("$scope.totalSMSExpirePourcentage"+$scope.totalSMSExpirePourcentage);
                               // $scope.loadDiagramme();
//                              $scope.pageSizeSelect = $scope.pageSizes[0];
                            },
                            function () {
                            }
                    );
        };

       $scope.tableauDeBordTout = function () {
            $scope.choix=1;
            $scope.tableauDeBord();
        };
        $scope.tableauDeBordMoi = function () {
            $scope.choix=2;
            $scope.tableauDeBord();
        };
        $scope.tableauDeBordAutre = function () {
            $scope.choix=3;
            $scope.tableauDeBord();
        };
       
         $scope.tableauDeBord();
    }]);
