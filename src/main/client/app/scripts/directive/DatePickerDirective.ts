/// <reference path="../refs.ts" />

'use strict';

class DatePickerController {
    private isPopupOpened: boolean;

    constructor() {
        this.isPopupOpened = false;
    }

    private openPopup ($event: ng.IAngularEvent): void {
        $event.preventDefault();
        $event.stopPropagation();

        this.isPopupOpened = true;
    }
}

//angular-ui bootstrap date picker component (input + button) wrapper
function datePickerDirective(): ng.IDirective {
    return {
        restrict: 'E',
        templateUrl: 'views/partial/datePicker.html',
        scope: {
            minDate   : '=min',
            ngModel   : '='
        },
        controller: DatePickerController,
        controllerAs: 'ctrl',
        require: 'ngModel',
        link: function(scope: any, elem, attrs) {
            //By default datePicker validates it's popup ONLY.
            // http://angular-ui.github.io/bootstrap/
            //If invalid date is chosen than only popup is considered to be invalid,
            //not the input.

            //1. may be validation can be checked by inner-inner ul (popup)?...
            //2. ngModel contains invalid value...
            var ctrl = elem.controller('ngModel');

            ctrl.$formatters.push(function(value) {
                var isValid = value >= scope.minDate;
                ctrl.$setValidity('date', isValid);
                return isValid ? value : scope.minDate;
            });
        }
    }
}

angular.module('auction').directive('auctionDatePicker', datePickerDirective);