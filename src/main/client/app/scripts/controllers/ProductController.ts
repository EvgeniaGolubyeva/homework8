/// <reference path="../refs.ts" />

'use strict';

class ProductController {
    public static $inject = ['$scope', 'product', 'bids', 'ProductService', 'BidService'];

    private bidAmount: number;

    private topPrice: number;
    private bidsCount: number;

    private placeBidResponseMessage: string;
    private alertClass: string;

    constructor (private $scope: any,
                 private product: auction.model.Product,
                 private bids: auction.model.Bid[],
                 private productService: auction.service.IProductService,
                 private bidService: auction.service.IBidService)
    {
        this.bidsCount = this.bids.length;
        this.topPrice = (this.bidsCount > 0) ? this.bids[0].amount : this.product.minimalPrice;

        //for websockets
        bidService.watchBids(product.id, (response) => {$scope.$apply(() => this.processServerResponse(response))});
        $scope.$on('$destroy', () => bidService.unwatchBids());
    }

    //place bid through restful service
    placeBid() {
        var topBid = this.productService.placeBid(this.product.id, this.createBid()).then(
            (response: any) => {
                this.processServerResponse(response);
            }
        );
    }

    //place bid through web sockets
    placeBidWS() {
        this.bidService.placeBid(this.createBid());
    }

    private createBid(): auction.model.Bid {
        var bid = new auction.model.Bid();
        bid.amount = this.bidAmount;
        bid.user = {id: 1, name: "user1", email: "user1@mail.com", getOverbidNotifications: true};

        return bid;
    }

    private processServerResponse(response: any) {
        if (response.status == "rejected") {
            this.alertClass = "alert-warning";
            this.placeBidResponseMessage = response.reason;
        }
        else if (response.status == "win") {
            this.alertClass = "alert-success";
            this.placeBidResponseMessage = response.reason;
        }
        else {
            this.placeBidResponseMessage = 'Bid was succesfully placed';
            this.alertClass = "alert-info";

            this.topPrice = response.topBidPrice;
            this.bidsCount = response.totalCountOfBids;
        }
    }

    public static resolve = {
        product: ['ProductService', '$route',
            (productService: auction.service.IProductService, $route: any) => {
                return productService.getProduct($route.current.params.id);
            }
        ],
        bids: ['ProductService', '$route',
            (productService: auction.service.IProductService, $route: any) => {
                return productService.getProductBids($route.current.params.id);
            }
        ]
    }
}

angular.module('auction').controller('ProductController', ProductController);
