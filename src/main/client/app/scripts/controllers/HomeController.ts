/// <reference path="../refs.ts" />

'use strict';

class HomeController {
    public static $inject = ['featuredProducts'];

    constructor (private featuredProducts: auction.model.Product[])
    {}

    public static resolve = {
        featuredProducts: ['ProductService', (productService: auction.service.IProductService) => {
            return productService.getFeaturedProducts();
        }]
    }
}

angular.module('auction').controller('HomeController', HomeController);
