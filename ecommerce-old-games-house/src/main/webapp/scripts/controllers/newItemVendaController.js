
angular.module('ecommerceoldgameshouse').controller('NewItemVendaController', function ($scope, $location, locationParser, ItemVendaResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.itemVenda = $scope.itemVenda || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/ItemVendas/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ItemVendaResource.save($scope.itemVenda, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/ItemVendas");
    };
});