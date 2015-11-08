

angular.module('ecommerceoldgameshouse').controller('EditVendaController', function($scope, $routeParams, $location, VendaResource , ClienteResource, ItemVendaResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.venda = new VendaResource(self.original);
            ClienteResource.queryAll(function(items) {
                $scope.clienteSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.venda.cliente && item.id == $scope.venda.cliente.id) {
                        $scope.clienteSelection = labelObject;
                        $scope.venda.cliente = wrappedObject;
                        self.original.cliente = $scope.venda.cliente;
                    }
                    return labelObject;
                });
            });
            ItemVendaResource.queryAll(function(items) {
                $scope.itensSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.venda.itens){
                        $.each($scope.venda.itens, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.itensSelection.push(labelObject);
                                $scope.venda.itens.push(wrappedObject);
                            }
                        });
                        self.original.itens = $scope.venda.itens;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Vendas");
        };
        VendaResource.get({VendaId:$routeParams.VendaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.venda);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.venda.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Vendas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Vendas");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.venda.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("clienteSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.venda.cliente = {};
            $scope.venda.cliente.id = selection.value;
        }
    });
    $scope.itensSelection = $scope.itensSelection || [];
    $scope.$watch("itensSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.venda) {
            $scope.venda.itens = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.venda.itens.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});