/// <reference path="../refs.ts" />

'use strict';

module auction.service {

    import m = auction.model;

    export interface IProductService {
        getFeaturedProducts: () => ng.IPromise<m.Product[]>;
        getSearchProducts:(params: string[]) => ng.IPromise<m.Product[]>;
        getProduct: (id: number) => ng.IPromise<m.Product>;

        getProductBids: (productId: number) => ng.IPromise<m.Bid[]>;
        placeBid: (productId: number, bid: m.Bid) => ng.IPromise<m.Bid>;
    }

    export interface ICategoriesService {
        getCategories:() => ng.IPromise<string[]>;
    }

    export class ProductService implements IProductService, ICategoriesService {
        public static $inject = ['Restangular', '$location', '$log', '$q'];

        private restangularResource;

        constructor(private restangular: restangular.IService,
                    private $location:   ng.ILocationService,
                    private $log:        ng.ILogService,
                    private $q:          ng.IQService)
        {
            this.restangularResource = this.restangular.all("product");
        }

        public getFeaturedProducts(): ng.IPromise<m.Product[]> {
            return this.restangularResource.one('featured').get();
        }

        public getSearchProducts(params: string[]): ng.IPromise<m.Product[]> {
            return this.restangularResource.one('search').get(params);
        }

        public getProduct(id: number): ng.IPromise<m.Product> {
            return this.restangularResource.one(id).get();
        }

        public placeBid(productId: number, bid: m.Bid): ng.IPromise<any> {
            return this.restangularResource.one(productId.toString()).all('bid').post(bid);
        }

        public getProductBids(productId: number): ng.IPromise<m.Bid[]> {
            return this.restangularResource.one(productId).one("bid").get();
        }

        //invoked by SearchFormController to populate categories select(comboBox)
        public getCategories(): ng.IPromise<string[]> {
            var res = this.$q.defer();
            res.resolve(['Category 1', 'Category 2', 'Category 3', 'Category 4']);
            return res.promise;
        }
    }

    angular.module('auction').service('ProductService', ProductService);
}