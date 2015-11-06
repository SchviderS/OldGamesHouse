

angular.module('ecommerceoldgameshouse').controller('EditProdutoController', function($scope, $routeParams, $location, ProdutoResource , FabricanteResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.produto = new ProdutoResource(self.original);
            FabricanteResource.queryAll(function(items) {
                $scope.fabricanteSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.produto.fabricante && item.id == $scope.produto.fabricante.id) {
                        $scope.fabricanteSelection = labelObject;
                        $scope.produto.fabricante = wrappedObject;
                        self.original.fabricante = $scope.produto.fabricante;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Produtos");
        };
        ProdutoResource.get({ProdutoId:$routeParams.ProdutoId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.produto);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.produto.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Produtos");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Produtos");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.produto.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("fabricanteSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.produto.fabricante = {};
            $scope.produto.fabricante.id = selection.value;
        }
    });
    
    $scope.get();
});