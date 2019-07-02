app.service('inicioService', [
	'$http',
	'$q',
	'$location',
	'$rootScope',
	'$window',
	function($http, $q, $location,$rootScope, $window) {
	
		this.consultarServicio=function(id){
			var d = $q.defer();
			$http.post("/servicio/buscar",id).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
				});
			return d.promise;
		}
		
		this.validarCaptcha=function(captchaResponse){
			var d = $q.defer();
			$http.post("/servicio/validarCaptcha",captchaResponse).then(
				function(response) {
					console.log(response);
					d.resolve(response.data);
				}, function(response) {
					if(response.status==403){
						alert("No está autorizado para realizar esta acción");
						$location.path("/");
					}
				});
			return d.promise;
		}
		
}])
app.controller("inicioController",[
	'$scope',
	'$http',
	'inicioService',
	'$routeParams',
	'$location',
	'$window','$cookieStore', 'vcRecaptchaService', 
	function($scope,$http, inicioService, $routeParams,$location,$window,$cookieStore, vcRecaptchaService){
		$scope.Facturar=false;
		$scope.Documento=true;
		$scope.buscar= function(){
			
			var validacion=vcRecaptchaService.getResponse();
			//checar el captcha
			if (validacion!="") {
				waitingDialog.show('Consultando Servicios', {dialogSize: 'sm', progressType: 'warning'});
				inicioService.validarCaptcha(validacion).then(function(data){
					$http.get('https://facturacion.tikal.mx/kioskoinfo.php',{params:{id:$scope.id}}).then(function(response){
						if(response.data.status!='1'){
							alert("El servicio se encuentra con pago pendiente");
							$window.location.reload();
						}else{
							var compra={
									id:$scope.id,
									detalle:response.data.detalle,
									precio:response.data.precio,
							
							}
							inicioService.consultarServicio(compra).then(function(data){
								$scope.servicio=data;
								if($scope.servicio.uuid){
									$scope.Facturar=true;
									$scope.Documento=false;
								}
								$scope.listo=true;
								$cookieStore.put('servicio',$scope.id);
								waitingDialog.hide();
							})
						}
					},function(response){
						if(response.status==404){
							alert('No se encontró ningun servicio con el número de referencia ingresado');
							$window.location.reload();
						}else{
							console.log(response);
						}
					});
				},
				function(response) {
					if (response.error == 400) {
						alert("Por favor resuelva el captcha e intente de nuevo");
					}
				});
			} //fin if 
			
			else {
				alert("Por favor resuelva el captcha e intente de nuevo");
			}
			
		}
	
		$scope.facturar=function(){
			$location.path("/receptor");
		}
	
}]);