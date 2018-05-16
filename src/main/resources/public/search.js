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
    };

    this.searchOffersWithOffset = function searchOffersWithOffset(query, offset) {
        return $http({
            method : 'GET',
            url : 'search?q=' + query + '&o=' + offset
        });
    };

    this.sendProductPreferences = function sendProductPreferences(preferences) {
        return $http({
            method : 'POST',
            url : 'preferences',
            data : preferences
        })
    };

} ]);


app.controller('OfferCRUDCtrl', ['$scope','OfferCRUDService',
    function ($scope, OfferCRUDService) {
        $scope.productToAssess = null;
        $scope.searchedProductOffset = 0;
        $scope.numberOfAssessedOffers = 0;
        $scope.NUMBER_OF_OFFERS_TO_ASSESS = 10;

        $scope.MODE_SEARCHING_OFFERS = 'mode_searching_offers';
        $scope.MODE_GETTING_EMAIL = 'mode_getting_email';
        $scope.MODE_LEARNING = 'mode_learning';
        $scope.currentMode = $scope.MODE_LEARNING;

        $scope.productPreferences = {
            user: null,
            offers: []
        };

        $scope.submitQuery = function () {
            switch ($scope.currentMode) {
                case $scope.MODE_LEARNING:
                    $scope.searchSingleOffer();
                    return;
                case $scope.MODE_SEARCHING_OFFERS:
                    $scope.searchOffers();
                    return;
                default:
                    return;
            }
        };

        $scope.searchOffers = function () {
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
            $scope.searchedProductNum = 0;
            $scope.searchedProductOffset = 0;
            $scope.searchedProducts = null;
            $scope.productToAssess = null;

            $scope.loadNextProductToAssess();
        };

        $scope.assessOfferGood = function () {
            $scope.rating = 1;
            $scope.firstTimeAssessingStuff();
        };

        $scope.assessOfferBad = function () {
            $scope.rating = 0;
            $scope.firstTimeAssessingStuff();
        };

        $scope.firstTimeAssessingStuff = function () {
            $scope.numberOfAssessedOffers++;
            $scope.productPreferences.offers.push({
                title: $scope.productToAssess.title,
                imgUrl: $scope.productToAssess.imgUrl,
                url: $scope.productToAssess.url,
                price: $scope.productToAssess.price,
                feedback: $scope.rating
            });


            if ($scope.numberOfAssessedOffers >= $scope.NUMBER_OF_OFFERS_TO_ASSESS) {
                $scope.currentMode = $scope.MODE_GETTING_EMAIL;
            } else {
                $scope.loadNextProductToAssess();
            }
        };

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
        };

        $scope.submitProductPreferences = function () {
            $scope.productPreferences.user = {
                email: $scope.emailAddress
            };

            OfferCRUDService.sendProductPreferences($scope.productPreferences).then (function success(response){
                    $scope.message = 'Product preferences sent!';
                    $scope.errorMessage = '';
                    console.log('Product preferences sent!');
                },
                function error(response){
                    $scope.errorMessage = 'Error sending product preferences!';
                    $scope.message = '';
                    console.log('Error sending product preferences!');
                });
        };


    }]);