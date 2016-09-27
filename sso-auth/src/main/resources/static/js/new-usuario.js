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
 * validación: comprobaciones de unicidad para inputs. Dado un input con
 * name="NAME", y ensure-unique="URL", se hace una consulta a URL/NAME. Si esa
 * consulta tiene éxito y devuelve un número menor de 1, se asume ensureUnique
 * correcto. En otro caso, se asume que hay ya un elemento como el que nos
 * interesa y se pone unique false
 */
.directive(
        'ensureUnique',
        function($http, $parse) {
            return {
                require : 'ngModel',
                link : function(scope, element, atribs, controller) {
                    scope.$watch(atribs.ngModel, function() {
                        var value = $parse(atribs.ngModel)(scope);
                        if (value) {
                            // preparamos los datos para la request
                            var config = {
                                params : {}
                            };
                            config.params[atribs.name] = value;

                            var url = atribs.ensureUnique;
                            
                            $http.get(url + atribs.name, config)
                                    .then(
                                            function(value) {
                                                controller.$setValidity(
                                                        'unique',
                                                        value.data < 1);
                                            },
                                            function(reason) {
                                                controller.$setValidity(
                                                        'unique', false);
                                            });
                        }

                    });
                }
            };
        });
