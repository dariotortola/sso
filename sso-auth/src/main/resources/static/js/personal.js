angular.module('control-acceso')

/**
 * controlador de los datos propios
 */
.controller(
        'personal',
        function($scope, $http) {
            $scope.password = {
                current : null,
                nueva : null,
                nueva2 : null
            };

            $http.get('usuario/me').then(function(value) {
                $scope.usuario = value.data;
            });

            this.submit = function() {
                $http.post('usuario/me', $scope.usuario);
            };

            this.changePassword = function() {
                $http.post('usuario/me/password', $scope.password).then(
                        function(value) {
                            if (value.data.success) {
                                // se ha hecho bien, reiniciamos el cambio
                                $scope.password = {
                                    current : null,
                                    nueva : null,
                                    nueva2 : null
                                };
                            } else {
                                // error
                                $scope.password.error = value.data.error;
                            }
                        });
            };
        });
