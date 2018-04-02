var app = angular.module('searchApp', []);
app.controller('searchController', function($scope, $http) {
    $scope.sendQuery = function() {
        $http.get("search?q=" + $scope.query)
            .then(function(response) {
                $scope.products = response.data;
            });
    };
});