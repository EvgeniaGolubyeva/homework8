/// <reference path="../refs.ts" />

'use strict';

class SearchCriteria {
    public title: string;
    public category: string;
    public lowPrice: number;
    public highPrice: number;
    public date: Date;
    public numberOfBids;

    static fromStringObject(source): SearchCriteria {
        return angular.extend(new SearchCriteria(), source, {
            lowPrice: source.lowPrice ? parseInt(source.lowPrice) : 0,
            highPrice: source.highPrice ? parseInt(source.highPrice) : 500,
            numberOfBids: source.numberOfBids ? parseInt(source.numberOfBids) : 0,
            date: source.date ? new Date(source.date) : new Date()
        });
    }

    static toStringObject(sc: SearchCriteria): any {
        return angular.extend({}, sc, {date: sc.date.toISOString()});
    }
}

class SearchFormController {
    public static $inject = ['ProductService', '$location'];

    private searchCriteria: SearchCriteria;
    private categories: string[];
    private minDate: Date =  new Date();

    constructor(categoriesService: auction.service.ICategoriesService,
                private $location: ng.ILocationService) {

        this.searchCriteria = SearchCriteria.fromStringObject($location.search());

        this.minDate.setHours(0,0,0,0);

        categoriesService.getCategories().then((data) => this.categories = data);
    }

    search() {
        this.$location.path('/search');
        this.$location.search(SearchCriteria.toStringObject(this.searchCriteria));
    }
}

function searchFormDirective(): ng.IDirective {
    return {
        restrict: 'E',
        templateUrl: 'views/partial/searchForm.html',
        scope: {},
        controller: SearchFormController,
        controllerAs: 'ctrl'
    }
}

angular.module('auction').directive('auctionSearchForm', searchFormDirective);