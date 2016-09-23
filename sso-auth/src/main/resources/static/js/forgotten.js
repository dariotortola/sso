angular.module("forgotPass", []).controller(
        "forgotten",
        function($http, $scope) {
            $scope.username = '';

            this.submit = function() {
                $http.post('user/forgot', $scope.username).then(
                        function(value) {
                            // TODO
                            console.log(value);
                        }, function(reason) {
                            // TODO
                            // se pueden abrir modals desde javascript con
                            // $.modal(id)
                            console.log(reason);
                        });
            };
        });