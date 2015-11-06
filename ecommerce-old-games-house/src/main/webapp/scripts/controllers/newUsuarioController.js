
angular.module('ecommerceoldgameshouse').controller('NewUsuarioController', function ($scope, $location, locationParser, UsuarioResource , ClienteResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.usuario = $scope.usuario || {};
    
    $scope.adminList = [
        "true",
        " false"
    ];
    
    $scope.ClienteList = ClienteResource.queryAll(function(items){
        $scope.ClienteSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("ClienteSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.usuario.Cliente = {};
            $scope.usuario.Cliente.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Usuarios/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        UsuarioResource.save($scope.usuario, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Usuarios");
    };
});