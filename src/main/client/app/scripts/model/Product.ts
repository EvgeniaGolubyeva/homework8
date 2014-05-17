/// <reference path="../refs.ts" />

'use strict';

module auction.model {
    export class Product {
        public id: number;
        public title: string;
        public thumb: string;
        public description: string;
        public watchers: number;
        public minimalPrice: number;
    }
}
