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
})

/**
 * validación: el contenido del input anotado tiene que ser igual a un elemento
 * del scope
 */
.directive(
        'copyOf',
        function($parse) {
            return {
                // atributo
                restrict : 'A',
                // requiere ng-model para el $watch
                require : 'ngModel',
                link : function(scope, element, attributes, controller) {

                    /*
                     * vigila ng-model
                     */
                    scope.$watch(attributes.ngModel, function(newValue,
                            oldValue, scope) {
                        var referencia = $parse(attributes.copyOf)(scope);
                        var iguales = newValue === referencia;
                        controller.$setValidity('copyOf', iguales);
                    });

                    /*
                     * cuando cambia el valor de referencia, lo comparamos con
                     * ng-model
                     */
                    scope.$watch(attributes.copyOf, function(newValue,
                            oldValue, scope) {
                        var referencia = $parse(attributes.ngModel)(scope);
                        var iguales = newValue === referencia;
                        controller.$setValidity('copyOf', iguales);
                    });

                }
            };
        })

/**
 * comprobaciones de unicidad
 */
.directive('ensureUnique', function($http, $parse) {
    return {
        require : 'ngModel',
        link : function(scope, element, atribs, controller) {
            scope.$watch(atribs.ngModel, function() {
                var data = {};
                data[atribs.name] = $parse(atribs.ngModel)(scope);

                $http.get('usuario/count/' + atribs.name, data, {
                    params : data
                }).then(function(value) {
                    controller.$setValidity('unique', value.data < 1);
                }, function(reason) {
                    controller.$setValidity('unique', false);
                })

            });
        }
    };
});
