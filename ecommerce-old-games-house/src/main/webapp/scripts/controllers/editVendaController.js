

angular.module('ecommerceoldgameshouse').controller('EditVendaController', function($scope, $routeParams, $location, VendaResource , ProdutoResource, ClienteResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.venda = new VendaResource(self.original);
            ProdutoResource.queryAll(function(items) {
                $scope.produtosSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.venda.produtos){
                        $.each($scope.venda.produtos, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.produtosSelection.push(labelObject);
                                $scope.venda.produtos.push(wrappedObject);
                            }
                        });
                        self.original.produtos = $scope.venda.produtos;
                    }
                    return labelObject;
                });
            });
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
    
    $scope.produtosSelection = $scope.produtosSelection || [];
    $scope.$watch("produtosSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.venda) {
            $scope.venda.produtos = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.venda.produtos.push(collectionItem);
            });
        }
    });
    $scope.$watch("clienteSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.venda.cliente = {};
            $scope.venda.cliente.id = selection.value;
        }
    });
    
    $scope.get();
});