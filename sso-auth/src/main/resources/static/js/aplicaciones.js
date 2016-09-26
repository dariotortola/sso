angular.module('aplicaciones', [ 'ngResource' ])

/**
 * gestión de aplicaciones
 */
.controller('gestion-aplicaciones', function($scope, $resource) {

    var appService = $resource('aplicacion/:appId', {
        appId : '@id'
    });

    angular.extend($scope, {
        aplicacion : {
            list : [],
            selected : null,
            query : null
        }
    });

    // métodos del controlador
    angular.extend(this, {
        /**
         * búsqueda de aplicaciones
         */
        refresh : function() {
            appService.query({
                query : $scope.query
            },function(apps) {
                $scope.aplicacion.list = apps;
                $scope.aplicacion.selected = null;
            });
        }
    });

    // carga inicial
    this.refresh();
});