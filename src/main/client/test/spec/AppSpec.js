'use strict';

describe('Testing routing', function () {

    beforeEach(module('auction'));

    describe('Home route', function () {
        it('should use home.html view ', inject(function ($route) {
            expect($route.routes['/'].templateUrl).toEqual('views/home.html');
        }));
        it('should be handled by HomeController', inject(function ($route) {
            expect($route.routes['/'].controller).toEqual('HomeController');
        }));
    });

    describe('Search route', function () {
        it('should use search.html view', inject(function ($route) {
            expect($route.routes['/search'].templateUrl).toEqual('views/search.html');
        }));
        it('should be handled by SearchController', inject(function ($route) {
            expect($route.routes['/search'].controller).toEqual('SearchController');
        }));
    });

    describe('Product route', function () {
        it('should use product.html view', inject(function ($route) {
            expect($route.routes['/product/:id'].templateUrl).toEqual('views/product.html');
        }));
        it('should be handled by ProductController', inject(function ($route) {
            expect($route.routes['/product/:id'].controller).toEqual('ProductController');
        }));
    });
});
