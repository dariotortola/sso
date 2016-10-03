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
            }, function(apps) {
                $scope.aplicacion.list = apps;
                $scope.aplicacion.selected = null;
            });
        }

    });

    // alias
    var controller = this;
    this.create = function(callback) {
        appService.save($scope.aplicacion.nueva, function() {
            callback.apply(controller);
        });
    };

    // carga inicial
    this.refresh();
})

/**
 * Edición de aplicaciones
 */
.controller('edicion-aplicacion', function($scope, $routeParams, $http) {
    $http.get('aplicacion/' + $routeParams.appId).then(function(value) {
        if (value.data) {
            $scope.aplicacion = value.data;
        }
    });

    this.save = function() {
        $http.put('aplicacion/' + $scope.aplicacion.id, $scope.aplicacion);
    }
});
;