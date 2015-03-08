/*jslint browser: true*/
/*global angular,console,Money*/
angular.module("RestService", []).factory("$restService", function ($http) {
    'use strict';
    
    var URL_GET_ACCOUNTS = "JsonGetAccounts",
        URL_GET_NOTES = "JsonGetNotes",
        URL_GET_LABELS = "JsonGetLabels",
        URL_GET_TRANSACTIONS = "JsonGetTransactions",
        URL_GET_TRANSACTIONS_TO_IMPORT = "JsonGetTransactionsToImport",
        URL_ADD_TX = "AddTransaction",
        URL_ADD_NOTE = "AddNote",
        URL_DELETE_TX = "DeleteTransaction",
        URL_DELETE_NOTE = "DeleteNote",
        jsonGet = function (url, params, callbackFn) {
            var checkStatus = function (data) {
                if (data.status === "OK") {
                    callbackFn.call(this, data.output);
                }
            };
            Money.ajax.get($http, url, params, checkStatus);
        },
        jsonPost = function (url, body, callbackFn) {
            var checkStatus = function (data) {
                if (data.status === "OK") {
                    callbackFn.call(this, data.output);
                }
            };
            Money.ajax.post($http, url, body, checkStatus);
        };

    return {
        getAccounts: function (params, callbackFn) {
            jsonGet(URL_GET_ACCOUNTS, params, callbackFn);
        },
        getNotes: function (params, callbackFn) {
            jsonGet(URL_GET_NOTES, params, callbackFn);
        },
        getLabels: function (params, callbackFn) {
            jsonGet(URL_GET_LABELS, params, callbackFn);
        },
        getTransactions: function (params, callbackFn) {
            jsonGet(URL_GET_TRANSACTIONS, params, callbackFn);
        },
        getTransactionsToImport: function (params, callbackFn) {
            jsonGet(URL_GET_TRANSACTIONS_TO_IMPORT, params, callbackFn);
        },
        addTransaction: function (body, callbackFn) {
            jsonPost(URL_ADD_TX, body, callbackFn);
        },
        addNote: function (body, callbackFn) {
            jsonPost(URL_ADD_NOTE, body, callbackFn);
        },
        deleteTransaction: function (body, callbackFn) {
            jsonPost(URL_DELETE_TX, body, callbackFn);
        },
        deleteNote: function (body, callbackFn) {
            jsonPost(URL_DELETE_NOTE, body, callbackFn);
        }
    };
});