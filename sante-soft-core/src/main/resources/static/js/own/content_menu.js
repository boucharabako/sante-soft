var App;
var appUrl;
App.controller('contentMenuController', ['$scope', 'PropagationService', 'GenericService', function ($scope) {

        menuInContent = function (menuHeader) {
            $scope.listMenuPanneau = [];
            var m = "." + menuHeader;
            $(".imageCLI").css("display", "none");
            $(".contentSection").css("display", "none");

            $(m).find('ul.list-unstyled').addClass('ulSelected');

            $(".ulSelected").find('a').addClass('liSelected');

            console.log('liste des menus ' + JSON.stringify($scope.listMenuPanneau));

            var listA = document.querySelectorAll('.liSelected');
            $(m).find('ul.list-unstyled').removeClass('ulSelected');


            listA.forEach(function (a) {
                $scope.menuPanneau = {
                    lien: null,
                    text: null,
                    icon: null,
                    selectLeft: null
                };
                $scope.menuPanneau.lien = a.href;
                $scope.menuPanneau.text = a.textContent;
                $scope.menuPanneau.icon = a.dataset.ic;
                $scope.menuPanneau.selectLeft = a.classList[0];
                $scope.listMenuPanneau.push($scope.menuPanneau);
                $("ul.list-unstyled").find('a').removeClass('liSelected');
            });


            if (!$scope.$$phase) {
                $scope.$apply();
            }
            console.log('liste des menus ' + JSON.stringify($scope.listMenuPanneau));
        };

        $scope.selectLeftMenuClassSession = function (className) {
            sessionStorage.setItem("clickMenuClass", className);
        };

    }]);
