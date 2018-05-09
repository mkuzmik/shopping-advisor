var app = angular.module('searchApp', []);

app.config(function($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist([
        'self',
        'http://allegro.pl/**'
    ]);
});

app.service('OfferCRUDService', [ '$http', function($http) {

    this.searchOffers = function searchOffers(query) {
        return $http({
            method : 'GET',
            url : 'search?q=' + query
        });
    }

    this.searchOffersWithOffset = function searchOffersWithOffset(query, offset) {
        return $http({
            method : 'GET',
            url : 'search?q=' + query + '&o=' + offset
        });
    }

    this.assessOffer = function assessOffer(url, rating) {
        return $http({
            method : 'POST',
            url : 'offers',
            data : {
                url : url,
                rating: rating
            }
        });
    }

} ]);


app.controller('OfferCRUDCtrl', ['$scope','OfferCRUDService',
    function ($scope, OfferCRUDService) {
        $scope.learning = false;
        $scope.isLearningOn = false;
        $scope.productToAssess = null;
        $scope.searchedProductOffset = 0;

        $scope.searchOffers = function () {
            $scope.isLearningOn = $scope.learning;

            OfferCRUDService.searchOffers($scope.query).then(
                function success(response) {
                    $scope.products = response.data;
                    $scope.message='';
                    $scope.errorMessage = '';
                },
                function error(response) {
                    $scope.message = '';
                    if (response.status === 404) {
                        $scope.errorMessage = 'Could not find offers!';
                    }
                    else {
                        $scope.errorMessage = "Error finding offers!";
                    }
                });
        };

        $scope.searchOffersWithOffset = function () {
            $scope.isLearningOn = $scope.learning;

            OfferCRUDService.searchOffersWithOffset($scope.query, $scope.searchedProductOffset).then(
                function success(response) {
                    $scope.products = response.data;
                    $scope.message='';
                    $scope.errorMessage = '';
                },
                function error(response) {
                    $scope.message = '';
                    if (response.status === 404) {
                        $scope.errorMessage = 'Could not find offers!';
                    }
                    else {
                        $scope.errorMessage = "Error finding offers!";
                    }
                });
        };

        $scope.searchSingleOffer = function () {
            $scope.isLearningOn = $scope.learning;
            $scope.searchedProductNum = 0;
            $scope.searchedProductOffset = 0;
            $scope.searchedProducts = null;
            $scope.productToAssess = null;

            $scope.loadNextProductToAssess();
        };

        $scope.assessOffer = function () {
            if ($scope.productToAssess != null && $scope.rating != null) {
                OfferCRUDService.assessOffer($scope.productToAssess.url, $scope.rating)
                    .then (function success(response){
                            $scope.message = 'Offer assessed!';
                            $scope.errorMessage = '';
                        },
                        function error(response){
                            $scope.errorMessage = 'Error assessing offer!';
                            $scope.message = '';
                        });
            }
            else {
                $scope.errorMessage = 'Please assess an offer!';
                $scope.message = '';
            }
        }

        $scope.assessOfferGood = function () {
            $scope.rating = 5;
            $scope.assessOffer();
            $scope.loadNextProductToAssess();
        }

        $scope.assessOfferBad = function () {
            $scope.rating = 0;
            $scope.assessOffer();
            $scope.loadNextProductToAssess();
        }

        $scope.loadNextProductToAssess = function () {
            if ($scope.searchedProducts === null || $scope.searchedProducts.length <= $scope.searchedProductNum) {
                $scope.searchedProductNum = 0;

                OfferCRUDService.searchOffersWithOffset($scope.query, $scope.searchedProductOffset).then(
                    function success(response) {
                        $scope.searchedProducts = response.data;

                        if (response.data === null) {
                            $scope.productToAssess = {};
                        } else {
                            $scope.productToAssess = $scope.searchedProducts[$scope.searchedProductNum];
                            $scope.searchedProductNum++;
                        }
                        $scope.message='';
                        $scope.errorMessage = '';
                    },
                    function error(response) {
                        $scope.message = '';
                        if (response.status === 404) {
                            $scope.errorMessage = 'Could not find offers!';
                        }
                        else {
                            $scope.errorMessage = "Error finding offers!";
                        }
                    });

                $scope.searchedProductOffset++;
            } else {
                $scope.productToAssess = $scope.searchedProducts[$scope.searchedProductNum];
                $scope.searchedProductNum++;
            }
        }
    }]);