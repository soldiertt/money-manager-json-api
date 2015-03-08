/*jslint browser: true*/
/*global angular,confirm,console,Money,alert*/

var moneyApp = angular.module("money", [
    'ngRoute',
    'moneyAppCtrl',
    'RestService',
    'ui.bootstrap',
    'nsPopover'
]);

var moneyAppCtrl = angular.module('moneyAppCtrl', []);

moneyApp.config(['$routeProvider',
    function ($routeProvider) {
        'use strict';
        // Système de routage
        $routeProvider
            .when('/import', {
                templateUrl: 'parts/import.html',
                controller: 'importCtrl'
            })
            .when('/main/:viewtype?', {
                templateUrl: 'parts/main.html',
                controller: 'moneyCtrl'
            })
            .when('/notes', {
                templateUrl: 'parts/notes.html',
                controller: 'notesCtrl'
            })
            .otherwise({
                redirectTo: '/main/fixed'
            });
    }
    ]);

/******************************************************************************************************
***************************** INDEX ****************************************************************
*******************************************************************************************************/
moneyAppCtrl.controller("indexCtrl", function ($scope, $location) {
    'use strict';
    var isCurrentLocation = function (path) {
        return path === $location.path();
    };
    
    $scope.year = "2015";
    $scope.init = function () {
        $scope.$broadcast('reInit');
    };
    $scope.navButtonClass = function (path) {
        var classes = {};
        classes['btn-grey'] = isCurrentLocation(path);
        classes['btn-blue'] = !isCurrentLocation(path);
        return classes;
    };
});

/******************************************************************************************************
***************************** MAIN ****************************************************************
*******************************************************************************************************/
moneyAppCtrl.controller("moneyCtrl", function ($scope, $http, $restService, $routeParams) {
    'use strict';
    var getLabelsCallback = function (data) {
            $scope.labels = data;
            // FILL TABLE, FOR EACH LABEL, GET TX
            $scope.labels.forEach(function (label, i) {
                $restService.getTransactions({labelId: label.id}, function (data) { label.occurences = data; });
            });
        },
        deleteTxCallback = function () {
            // AFTER MANUAL TX WAS DELETE, UPDATE THE CELL TOTAL VALUE
            var getData = {labelId: $scope.currentLabel.id, index: $scope.currentIndex},
                getOccurenceCallback = function (data) {
                    $scope.currentLabel.occurences[getData.index - 1] = data;
                };
            $restService.getTransactions(getData, getOccurenceCallback);
        },
        init = function () {
            $scope.labels = [];
            if ($routeParams.viewtype !== "fixed" && $routeParams.viewtype !== "other" && $routeParams.viewtype !== "income") {
                $routeParams.viewtype = "fixed";
            }
            if ($routeParams.viewtype === "income") {
                $restService.getLabels({year: $scope.year, income: true}, getLabelsCallback);
            } else {
                $restService.getLabels({year: $scope.year, fixed: $routeParams.viewtype === "fixed", income: false}, getLabelsCallback);
            }
        };

    
    $scope.$on('reInit', function (e) {
        init();
    });
    
    $scope.months = [{month: "Janvier"}, {month: "Février"}, {month: "Mars"}, {month: "Avril"}, {month: "Mai"}, {month: "Juin"},
                     {month: "Juillet"}, {month: "Août"}, {month: "Septembre"}, {month: "Octobre"}, {month: "Novembre"}, {month: "Décembre"}];
    
    $scope.currentOccurence = function (label, index) {
        $scope.currentLabel = label;
        $scope.currentIndex = index + 1;
    };
    
    $scope.addTx = function (label) {
        // DISPLAY THE POPUP TO ADD A MANUAL TX
        var recipientId = label.defaultRecipient ? label.defaultRecipient.id : null;
        $scope.currentLabel = label;
        $scope.dialog = {name: "addtx",
                       transaction: {labelId: label.id, date: new Date(), amount: label.defaultAmount, fromAccountId: 1,
                       recipientId: recipientId, communication: null, dateCompta: null}};
        $scope.$broadcast('openModalAddTx');
    };
    
    $scope.deleteTx = function (txId) {
        if (confirm("Are you sure to delete this transaction ?")) {
            $restService.deleteTransaction({id: txId}, deleteTxCallback);
        }
    };


    $scope.cellClasses = function (occurence, type, index) {
        // DETERMINE IF CELL NEED TO BE RED BACKGROUNDED
        var classes = {},
            dateNow = new Date(),
            quarterNow = Math.floor((dateNow.getMonth() + 3) / 3);
        
        if (occurence.total === 0 && $routeParams.viewtype === "fixed" && dateNow.getFullYear() === parseInt($scope.year, 10)) {
            if (type === "MONTHLY") {
                if (dateNow.getMonth() >= index) { classes.danger = true; }
            } else if (type === "QUARTER") {
                if (quarterNow >= index + 1) { classes.danger = true; }
            } else if (type === "YEARLY") {
                classes.danger = true;
            }
        } else if ($routeParams.viewtype === "other" || $routeParams.viewtype === "income") {
            if (type === "MONTHLY") {
                if (dateNow.getMonth() === index) { classes.info = true; }
            } else if (type === "QUARTER") {
                if (quarterNow === index + 1) { classes.info = true; }
            } else if (type === "YEARLY") {
                classes.info = true;
            }
        }
        return classes;
    };

    init();

});



/******************************************************************************************************
***************************** IMPORTER ****************************************************************
*******************************************************************************************************/
moneyAppCtrl.controller("importCtrl", function ($scope, $http, $restService) {
    'use strict';

    var nbTxSaved = 0,
        saveTxCallback = function (data) {
            // AFTER A SINGLE TX WAS SAVED
            nbTxSaved = nbTxSaved + 1;
            if (nbTxSaved === $scope.txToSaveCount) {
                $scope.reload();
            }
        };


    $scope.saveAll = function () {
        var txToSave = $scope.txToImport.filter(function (elem) {
            return elem.labelId;
        });
        $scope.txToSaveCount = txToSave.length;
        $scope.txToImport.forEach(function (elem, i) {
            if (elem.labelId) {
                if (elem.dateCompta) {
                    if (elem.dateCompta.getFullYear() !== parseInt($scope.year, 10)) {
                        alert("DateCompta for recipient '" + elem.recipient.name + " is invalid !");
                        $scope.txToSaveCount = $scope.txToSaveCount - 1;
                        return;
                    }
                } else if (new Date(elem.date).getFullYear() !== parseInt($scope.year, 10)) {
                    alert("Date for recipient '" + elem.recipient.name + " is invalid !");
                    $scope.txToSaveCount = $scope.txToSaveCount - 1;
                    return;
                }

                elem.fromAccountId = elem.fromAccount.id;
                delete elem.fromAccount;
                elem.recipientId = elem.recipient.id;
                delete elem.recipient;
                $restService.addTransaction(elem, saveTxCallback);

            }
        });

    };

    $scope.reload = function () {
        nbTxSaved = 0;
        $scope.labels = [];
        $restService.getLabels({year: $scope.year, fixed: true, income: false}, function (data) { $scope.labels = $scope.labels.concat(data); });
        $restService.getLabels({year: $scope.year, fixed: false, income: false}, function (data) { $scope.labels = $scope.labels.concat(data); });
        $restService.getLabels({year: $scope.year, fixed: true, income: true}, function (data) { $scope.labels = $scope.labels.concat(data); });
        $restService.getLabels({year: $scope.year, fixed: false, income: true}, function (data) { $scope.labels = $scope.labels.concat(data); });

        $restService.getTransactionsToImport({}, function (data) { $scope.txToImport = data; });
    };

    $scope.reload();

});

/******************************************************************************************************
***************************** NOTES ****************************************************************
*******************************************************************************************************/
moneyAppCtrl.controller("notesCtrl", function ($scope, $http, $restService) {
    'use strict';
    var init = function () {
            $restService.getNotes({}, function (data) { $scope.notes = data; });
            if ($scope.newNote) {
                $scope.newNote.content = '';
            }
        };
    
    $scope.saveNote = function () {
        $restService.addNote($scope.newNote, init);
    };
    
    $scope.deleteNote = function (noteId) {
        if (confirm("Are you sure to delete this note ?")) {
            $restService.deleteNote({id: noteId}, init);
        }
    };
    
    init();
});

/******************************************************************************************************
***************************** MODAL - ADD TX***********************************************************
*******************************************************************************************************/
moneyAppCtrl.controller('ModalAddTxCtrl', function ($scope, $modal, $restService) {
    'use strict';
    
    var saveTxCallback = function (data) {
            // AFTER MANUAL TX WAS SAVED, UPDATE THE CELL TOTAL VALUE
            var getData = {labelId: $scope.currentLabel.id, index: data},
                getOccurenceCallback = function (data) {
                    $scope.currentLabel.occurences[getData.index - 1] = data;
                };
            delete $scope.dialog;
            $restService.getTransactions(getData, getOccurenceCallback);
        },
        open = function () {
            var modalInstance = $modal.open({
                templateUrl: 'parts/modal-addtx.html',
                controller: 'ModalAddTxInstanceCtrl',
                resolve: {
                    dialog: function () {
                        return $scope.dialog;
                    }
                }
            });

            modalInstance.result.then(function (dialog) {
                $restService.addTransaction(dialog.transaction, saveTxCallback);
            }, function () {
                //Cancel
            });
        };

    $scope.$on('openModalAddTx', function (e) {
        open();
    });
    
    
});

/******************************************************************************************************
***************************** MODAL - ADD TX - INSTANCE ***********************************************
*******************************************************************************************************/
moneyAppCtrl.controller('ModalAddTxInstanceCtrl', function ($scope, $modalInstance, $restService, dialog) {
    'use strict';

    $restService.getAccounts({own: "true"}, function (data) { $scope.accounts = data; });
    $restService.getAccounts({own: "false"}, function (data) { $scope.recipients = data; });
    
    $scope.dialog = dialog;

    $scope.saveTx = function () {
        $modalInstance.close($scope.dialog);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});
