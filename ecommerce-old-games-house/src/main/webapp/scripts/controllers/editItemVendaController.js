

angular.module('ecommerceoldgameshouse').controller('EditItemVendaController', function($scope, $routeParams, $location, ItemVendaResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.itemVenda = new ItemVendaResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/ItemVendas");
        };
        ItemVendaResource.get({ItemVendaId:$routeParams.ItemVendaId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.itemVenda);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.itemVenda.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/ItemVendas");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/ItemVendas");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.itemVenda.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});