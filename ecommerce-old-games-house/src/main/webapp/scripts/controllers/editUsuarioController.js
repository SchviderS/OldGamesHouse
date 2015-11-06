

angular.module('ecommerceoldgameshouse').controller('EditUsuarioController', function($scope, $routeParams, $location, UsuarioResource , ClienteResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.usuario = new UsuarioResource(self.original);
            ClienteResource.queryAll(function(items) {
                $scope.ClienteSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.usuario.Cliente && item.id == $scope.usuario.Cliente.id) {
                        $scope.ClienteSelection = labelObject;
                        $scope.usuario.Cliente = wrappedObject;
                        self.original.Cliente = $scope.usuario.Cliente;
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
    
    $scope.adminList = [
        "true",  
        " false"  
    ];
    $scope.$watch("ClienteSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.usuario.Cliente = {};
            $scope.usuario.Cliente.id = selection.value;
        }
    });
    
    $scope.get();
});