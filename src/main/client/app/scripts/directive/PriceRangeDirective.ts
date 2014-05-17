/// <reference path="../refs.ts" />

'use strict';

//no validation done (>0, number and min > max)
function priceRangeDirective(): ng.IDirective {
    return {
        restrict: 'E',
        templateUrl: 'views/partial/priceRange.html',
        scope: {
            //if one way binding is not set (min-price or max-price attribute is not used) then undefined value
            //overrides default values every time.
            //The idea is to separate this 2 ways - attrMinPrice for attr
            //and actual minPrice (default or setted) is stored in minPrice
            attrMinPrice  : '@minPrice',
            attrMaxPrice  : '@maxPrice',

            lowPrice  : '=',
            highPrice : '='
        },

        link: (scope: any, element) => {
            scope.minPrice = parseInt(scope.attrMinPrice) || 0;
            scope.maxPrice = parseInt(scope.attrMaxPrice) || 500;

            //slider
            var priceSlider : any = angular.element(element).find('div[class="priceRangeSlider"]');
            priceSlider.slider({
                min: scope.minPrice,
                max: scope.maxPrice,
                values: [scope.lowPrice  || scope.minPrice, scope.highPrice || scope.maxPrice],
                range: true,
                slide: (e, ui) => scope.$apply(function () {
                    scope.lowPrice  = ui.values[0];
                    scope.highPrice = ui.values[1];
                })
            });

            scope.$watch('lowPrice' , (newValue) => {
                var oldValues = priceSlider.slider('values');
                priceSlider.slider('values', [newValue || scope.minPrice, oldValues[1]]);
            });
            scope.$watch('highPrice', (newValue) => {
                var oldValues = priceSlider.slider('values');
                priceSlider.slider('values', [oldValues[0], newValue || scope.maxPrice]);
            });
        }
    }
}

angular.module('auction').directive('auctionPriceRange', priceRangeDirective);