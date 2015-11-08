

angular.module('ecommerceoldgameshouse').controller('EditUsuarioController', function($scope, $routeParams, $location, UsuarioResource , ClienteResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.usuario = new UsuarioResource(self.original);
            ClienteResource.queryAll(function(items) {
                $scope.clienteSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.usuario.cliente && item.id == $scope.usuario.cliente.id) {
                        $scope.clienteSelection = labelObject;
                        $scope.usuario.cliente = wrappedObject;
                        self.original.cliente = $scope.usuario.cliente;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Usuarios");
        };
        UsuarioResource.get({UsuarioId:$routeParams.UsuarioId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.usuario);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.usuario.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Usuarios");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Usuarios");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.usuario.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("clienteSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.usuario.cliente = {};
            $scope.usuario.cliente.id = selection.value;
        }
    });
    
    $scope.get();
});