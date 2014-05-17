/// <reference path="../refs.ts" />

'use strict';

class SearchController {
    public static $inject = ['searchProducts'];

    constructor(private searchProducts:auction.model.Product[]) {
    }

    public static resolve = {
        searchProducts: ['ProductService', '$location',
            (productService:auction.service.IProductService, $location:ng.ILocationService) => {
                return productService.getSearchProducts($location.search());
            }]
    }
}

angular.module('auction').controller('SearchController', SearchController);
