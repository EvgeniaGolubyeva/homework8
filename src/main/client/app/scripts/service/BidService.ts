/// <reference path="../refs.ts" />

'use strict';

module auction.service {

    import m = auction.model;

    export interface IBidService {
        placeBid: (bid: m.Bid) => void;
        watchBids: (productId, onChangeCallback) => void;
        unwatchBids: () => void;
    }

    export class BidService implements IBidService {
        private webSocket: WebSocket;

        public watchBids(productId, onChangeCallback) {
            //since restful service to place a bid uses URI ... /product/id/bid
            //I've used the same pattern for web sockets ... /product/id/bidws
            //no special reason, just to uniform 2 approaches

            this.webSocket = new WebSocket('ws://localhost:8080/auction/server/product/' + productId + '/bidws');
            this.webSocket.onmessage = (e) => onChangeCallback(JSON.parse(e.data));
        }

        public placeBid(bid: m.Bid) {
            this.webSocket.send(JSON.stringify(bid));
        }

        public unwatchBids() {
            this.webSocket.close();
        }
    }

    angular.module('auction').service('BidService', BidService);
}