/// <reference path="../refs.ts" />

'use strict';

function footerDirective(): ng.IDirective {
    return {
        scope: false,
        restrict: 'E',
        templateUrl: 'views/partial/footer.html'
    }
}

angular.module('auction').directive('auctionFooter', footerDirective);