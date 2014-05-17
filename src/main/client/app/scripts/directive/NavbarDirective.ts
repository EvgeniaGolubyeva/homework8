/// <reference path="../refs.ts" />

'use strict';

class NavbarController {
    public static $inject = ['$rootScope', '$location'];

    //how to reset this title when user goes to home page?
    //in real application would navbar search be a separate directive?
    //may be the same as SearchFormDirective? introduce mode?
    private productTitle: string;

    constructor(private $rootScope,
                private $location)
    {}

    search = () => {
        //may be extract constants in app.js, duplicated in SearchFormDirective
        this.$location.path("/search");

        if (this.productTitle) this.$location.search({title : this.productTitle});
    }

    //may be the name of the page to constants as well, another way to figure out is search form is visible ???
    isSearchVisible = () => this.$rootScope.title == 'Home';
}


function navbarDirective($rootScope): ng.IDirective {
    return {
        scope: false,
        restrict: 'E',
        templateUrl: 'views/partial/navbar.html',
        controller: NavbarController,
        controllerAs: 'ctrl'
    }
}

angular.module('auction').directive('auctionNavbar', navbarDirective);