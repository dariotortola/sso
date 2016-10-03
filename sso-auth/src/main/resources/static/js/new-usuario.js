angular.module('control-acceso')

/**
 * controlador para registro de nuevos usuarios
 */
.controller('new-usuario', function($scope, $http) {

    $scope.usuario = {
        email : null,
        meta4 : null,
        nombre : null,
        password : null,
        password2 : null,
        username : null
    };

    this.submit = function() {
        $http.post('usuario', $scope.usuario).then(function(value) {
            $scope.creado = true;
            $scope.error = null;
            $scope.usuario = {
                email : null,
                meta4 : null,
                nombre : null,
                password : null,
                password2 : null,
                username : null
            };
        }, function(reason) {
            $scope.creado = false;
            $scope.error = reason.data.error || 'Algo ha salido mal';
        })
    }
});
