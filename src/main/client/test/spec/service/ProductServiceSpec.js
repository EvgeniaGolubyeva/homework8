'use strict';

describe('ProductService behaviour', function () {
    beforeEach(module('auction'));

    var productService, $httpBackend

    beforeEach(inject(function($injector) {
        $httpBackend = $injector.get('$httpBackend');
        productService = $injector.get('ProductService');
    }));

    afterEach(function() {
        $httpBackend.flush();
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('should get featured products', function () {
        productService.getFeaturedProducts();
        $httpBackend.expect('GET', 'http://webauctionv1.apiary-mock.com/product/featured').respond({});
    });

    it('should get search products', function () {
        productService.getSearchProducts();
        $httpBackend.expectGET('http://webauctionv1.apiary-mock.com/product/search').respond({});
    });

    it('should get product by id', function () {
        productService.getProduct('1');
        $httpBackend.expectGET('http://webauctionv1.apiary-mock.com/product/1').respond({});
    });
});
