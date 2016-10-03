angular.module('control-acceso', [ 'ngRoute', 'aplicaciones' ])

/**
 * configuración del módulo
 */
.config(function($routeProvider) {

    $routeProvider.when('/aplicaciones', {
        templateUrl : 'html/aplicaciones.html'
    });
    $routeProvider.when('/login', {
        templateUrl : 'html/login.html',
        controller : 'loginController',
        controllerAs : "controller"
    });
    $routeProvider.when('/registro', {
        templateUrl : 'html/registro.html',
        controller : 'new-usuario',
        controllerAs : 'controller'
    });
    $routeProvider.when('/personal', {
        templateUrl : 'html/personal.html',
        controller : 'personal',
        controllerAs : 'controller'
    });
    $routeProvider.otherwise("/");
})

/**
 * configuración de navegación para marcar la opción activa del menú
 */
.controller('navigation', function($scope, $location, AuthService) {
    $scope.isActive = function(viewLocation) {
        return viewLocation === $location.path();
    };
})

/**
 * servicio de autenticación
 */
.factory('AuthService', function($http, $rootScope) {
    var processAuthResponse = function(value) {
        if (value.data) {
            // hay información del usuario
            var auth = value.data;
            $rootScope.authentication = {
                authenticated : auth.authenticated,
                authorities : auth.authorities,
                name : auth.name
            };
        } else {
            // no hay usuario logado
            $rootScope.authentication = null;
        }
    };

    var service = {
        getMe : function() {
            $http.get('usuario/me/auth').then(processAuthResponse);
        },

        login : function(credentials) {
            $http.post('login', $.param(credentials), {
                headers : {
                    'Content-Type' : 'application/x-www-form-urlencoded'
                }
            }).then(processAuthResponse);
        }
    };

    // inicializamos
    service.getMe();

    return service;
})

/**
 * El controlador de login actúa sobre $rootScope para que el usuario logado sea
 * accesible a todo el mundo
 */
.controller('loginController',
        function($rootScope, $scope, $http, AuthService) {
            $scope.login = {
                username : null,
                password : null,
                'remember-me' : false
            };

            this.login = function() {
                AuthService.login($scope.login);
            };

        });