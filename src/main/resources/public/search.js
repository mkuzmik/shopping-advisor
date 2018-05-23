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
        $scope.numberOfGoodOffers = 0;
        $scope.numberOfBadOffers = 0;
        $scope.NUMBER_OF_OFFERS_TO_ASSESS = 6;

        $scope.MODE_SEARCHING_OFFERS = 'mode_searching_offers';
        $scope.MODE_GETTING_EMAIL = 'mode_getting_email';
        $scope.MODE_LEARNING = 'mode_learning';
        $scope.currentMode = $scope.MODE_LEARNING;

        $scope.assessedOffers = {
            good: [],
            bad: [],
            ignored: []
        };

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

        $scope.assessOfferIgnore = function () {
            $scope.rating = -1;
            $scope.firstTimeAssessingStuff();
        };

        $scope.firstTimeAssessingStuff = function () {
            var assessedOffer = {
                title: $scope.productToAssess.title,
                imgUrl: $scope.productToAssess.imgUrl,
                url: $scope.productToAssess.url,
                price: $scope.productToAssess.price,
                feedback: $scope.rating
            };

            if (assessedOffer.feedback === 1) {
                $scope.numberOfGoodOffers++;
                $scope.assessedOffers.good.push(assessedOffer);
            } else if (assessedOffer.feedback === 0) {
                $scope.numberOfBadOffers++;
                $scope.assessedOffers.bad.push(assessedOffer);
            } else
                $scope.assessedOffers.ignored.push(assessedOffer);


            if ($scope.numberOfGoodOffers >= $scope.NUMBER_OF_OFFERS_TO_ASSESS &&
                $scope.numberOfBadOffers >= $scope.NUMBER_OF_OFFERS_TO_ASSESS
            ) {
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

            var goodLen = $scope.assessedOffers.good.length;
            var badLen = $scope.assessedOffers.bad.length;
            var min = goodLen < badLen ? goodLen : badLen;

            $scope.productPreferences.offers = $scope.assessedOffers.good.slice(0, min).concat(
                    $scope.assessedOffers.bad.slice(0, min));

            $scope.assessedOffers.good = $scope.assessedOffers.good.slice(min);
            $scope.assessedOffers.bad = $scope.assessedOffers.bad.slice(min);

            OfferCRUDService.sendProductPreferences($scope.productPreferences).then(function success(response){
                    $scope.message = 'Product preferences sent!';
                    $scope.errorMessage = '';
                    $scope.sentSuccessfully = true;
                    console.log('Product preferences sent!');
                },
                function error(response){
                    $scope.errorMessage = 'Error sending product preferences!';
                    $scope.message = '';
                    console.log('Error sending product preferences!');
                });

            $scope.currentMode = $scope.MODE_SEARCHING_OFFERS;
            $scope.query = '';
        };


    }]);