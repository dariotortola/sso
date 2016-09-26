angular.module('control-acceso', [ 'ngRoute', 'aplicaciones' ])

/**
 * configuración del módulo
 */
.config(function($routeProvider) {

    $routeProvider.when('/aplicaciones', {
        templateUrl : 'html/aplicaciones.html'
    });
    $routeProvider.otherwise("/");
})

/**
 * configuración de navegación para marcar la opción activa del menú
 */
.controller('navigation', function($scope, $location) {
    $scope.isActive = function(viewLocation) {
        return viewLocation === $location.path();
    };
});