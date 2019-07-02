app.service('receptorService', [
	'$http',
	'$q',
	'$location',
	'$rootScope',
	'$window',
	function($http, $q, $location,$rootScope, $window) {
		
		this.receptorNuevo = function(send) {
			var d = $q.defer();
			$http.post("/servicio/facturar",send).then(function(response) {
				console.log(response);
				d.resolve(response.data);
			}, function(response) {
				d.reject(response);
			});
			return d.promise;
		};
}]);
app.controller("receptorController",[
	'$scope',
	'receptorService',
	'$routeParams',
	'$location',
	'$window',
	'$cookieStore',
	function($scope, receptorService, $routeParams,$location,$window, $cookieStore){
		
		$scope.enviar = function(newReceptor) {
//			console.log(newReceptor);
			waitingDialog.show('Facturando Servicio', {dialogSize: 'sm', progressType: 'warning'});
			var send={
					receptor: newReceptor,
					idServicio: $cookieStore.get('servicio'),
					email: newReceptor.email
			}
			console.log(send);
			receptorService.receptorNuevo(send).then(function(data) {
				waitingDialog.hide();
				alert(data);				
				$window.location.reload();
				$location.path("/inicio");
			})
		}
		
		$scope.newReceptor={
				domicilio:{
					pais:"MX"
				}
		}
		
		$scope.pais = function(newReceptor){
			if(newReceptor.domicilio.pais!="MX"){
				$scope.Estado=true;
			}else{
				$scope.Estado=false;
			}
		}
	
}]);