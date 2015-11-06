
angular.module('ecommerceoldgameshouse').controller('NewClienteController', function ($scope, $location, locationParser, ClienteResource , UsuarioResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.cliente = $scope.cliente || {};
    
    $scope.UsuarioList = UsuarioResource.queryAll(function(items){
        $scope.UsuarioSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("UsuarioSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.cliente.Usuario = {};
            $scope.cliente.Usuario.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Clientes/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ClienteResource.save($scope.cliente, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Clientes");
    };
});