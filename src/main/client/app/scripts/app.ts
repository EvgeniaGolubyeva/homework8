/// <reference path="refs.ts" />

'use strict';

class PageTitles {
    public static HOME    = "Home";
    public static SEARCH  = "Search";
    public static PRODUCT = "Product";
}

//create a module for an application
var auctionApplication = angular.module('auction', ['ngRoute', 'ui.bootstrap', 'restangular']);

//config routerProvider
auctionApplication.config(['$routeProvider', ($routeProvider: ng.route.IRouteProvider) => {
    $routeProvider
        .when ('/', {
            templateUrl: 'views/home.html',
            controller: 'HomeController',
            controllerAs: 'ctrl',
            title: PageTitles.HOME,
            resolve: HomeController.resolve
        })
        .when('/search', {
            templateUrl: 'views/search.html',
            controller: 'SearchController',
            controllerAs: 'ctrl',
            title: PageTitles.SEARCH,
            resolve: SearchController.resolve
        })
        .when('/product/:id', {
            templateUrl: 'views/product.html',
            controller: 'ProductController',
            controllerAs: 'ctrl',
            title: PageTitles.PRODUCT,
            resolve: ProductController.resolve
        })
        .otherwise({
            redirectTo: '/'
        });
}]);

auctionApplication.config(['RestangularProvider', (RestangularProvider) => {
    RestangularProvider.setBaseUrl("http://localhost:8080/auction/server");
}]);

auctionApplication.run(["$rootScope", ($rootScope: any) => {
        $rootScope.$on("$routeChangeStart", (event:ng.IAngularEvent, next:any):any => {
            //assign title
            $rootScope.title = next.title;
        });
    }
]);