var app = angular.module('app', ["xeditable"]);
app.run(function (editableOptions) {
    editableOptions.theme = 'bs3';
});
app.controller('controller', function ($scope, $window, $filter, $http) {

    $scope.utilisateur = {
        name: 'awesome user'
    };

    $scope.users = [
        {id: 1, name: 'awesome user1', status: 2, group: 4, groupName: 'admin'},
        {id: 2, name: 'awesome user2', status: undefined, group: 3, groupName: 'vip'},
        {id: 3, name: 'awesome user3', status: 2, group: null}
    ];

    $scope.statuses = [
        {value: 1, text: 'status1'},
        {value: 2, text: 'status2'},
        {value: 3, text: 'status3'},
        {value: 4, text: 'status4'}
    ];

    $scope.groups = [
        {value: 1, text: 'groupe1'},
        {value: 2, text: 'groupe2'},
        {value: 3, text: 'groupe3'},
        {value: 4, text: 'groupe4'}];

    $scope.loadGroups = function () {
        return $scope.groups;
    };

    $scope.showGroup = function (user) {
        if (user.group && $scope.groups.length) {
            var selected = $filter('filter')($scope.groups, {id: user.group});
            return selected.length ? selected[0].text : 'Not set';
        } else {
            return user.groupName || 'Not set';
        }
    };

    $scope.showStatus = function (user) {
        var selected = [];
        if (user.status) {
            selected = $filter('filter')($scope.statuses, {value: user.status});
        }
        return selected.length ? selected[0].text : 'Not set';
    };

    $scope.checkName = function (data, id) {
        if (id === 2 && data !== 'awesome') {
            return "Username 2 should be `awesome`";
        }
    };

    $scope.saveUser = function (data, id) {
        console.log("DATA "+JSON.stringify(data));
        console.log("ID "+id);
        angular.extend(data, {id: id});
        return $scope.users.push(data);
    };

    // remove user
    $scope.removeUser = function (index) {
        $scope.users.splice(index, 1);
    };

    // add user
    $scope.addUser = function () {
        $scope.inserted = {
            id: $scope.users.length + 1,
            name: '',
            status: null,
            group: null
        };
        $scope.users.push($scope.inserted);
    };
});



