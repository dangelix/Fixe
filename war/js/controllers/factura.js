app.service("comprobanteService", ['$http','$q',function($http,$q) {
	this.nuevoConcepto=function(){
		return {
			noIdentificacion : null,
			cantidad : null,
			unidad : null,
			descripcion : null,
			valorUnitario : null,
			importe : null,
			informacionAduanera: null,
			cuentaPredial : null,
			complementoConcepto : null,
			parte : null // usar init y get

		}
	}
	 
	this.initInformacionAduanera = function() {
		return [];
	}
	
	this.getInformacionAduanera = function() {
		return {
			numero: null,
			fecha: null,
			aduana: null
		};
	}
	
	this.getCuentaPredial = function() {
		return {
			numero : null
		};
	}
	
	this.getComplementoConcepto = function() {
		return {};
	}
	
	this.initParte = function() {
		return [];
	}
	
	this.getParte = function() {
		return {
			noIdentificacion : null,
			cantidad : null,
			unidad : null,
			descripcion : null,
			valorUnitario : null,
			importe : null,
			informacionAduanera : null //usar init y get
		}
	}
	
	// usar esta función para crear los objetos domicilioFiscal y expedidoEn del Emisor
	// y también para crear el objeto domicilio del Receptor
	this.getDomicilio = function() {
		return {
			calle:"Calle",
			noExterior:null,
			noInterior:null,
			colonia:null,
			localidad:null,
			referencia:null,
			municipio:"Municipio",
			estado:"Estado",
			pais:"País",
			codigoPostal:"50000"
		}
	}
	
	this.getImpuestos = function() {
		
	}
	
	this.initRetenciones = function() {
		return {
			retencion: null
		}
	}
	
	this.initTraslados = function() {
		return {
			traslado: null
		}
	}
	
	this.initComplementoOAddenda = function() {
		return {
			any: []
		};
	}
	
	this.getNuevo = function() {
		return {
			version : "3.2",//3.3
			serie : null,
			folio : null,
			fecha : new Date(),
			sello : null,
			formaPago : null,
			noCertificado : null,
			certificado : null,
			condicionesDePago : null,
			subTotal : null,
			descuento : null,
			moneda : "MXN",
			tipoCambio : null,
			total : null,
			tipoDeComprobante : null,
			metodoPago : null,
			lugarExpedicion : null,
			confirmacion : null,

			emisor : {
				rfc : null,
				nombre : null,
				regimenFiscal : null,
			},
			receptor : {
				rfc : null,
				nombre : null,
				residenciaFiscal : null,
				numRegIdTrib : null,
				usoCFDI : null,
			},
			conceptos : {
				concepto: [ {
					claveProdServ : null,
					noIdentificacion : null,
					cantidad : null,
					claveUnidad : null,
					unidad : null,
					descripcion : null,
					valorUnitario : null,
					importe : null,
					descuento : null,
					impuestos : {
						traslados : {
							traslado : [ {
								base : null,
								impuesto : null,
								tipoFactor : null,
								tasaOCuota : null,
								importe : null,
							} ] 
						},

						retenciones : {
							retencion : [ {
								base : null,
								impuesto : null,
								tipoFactor : null,
								tasaOCuota : null,
								importe : null,
							} ] 
						} 

					},
					informacionAduanera : [{
						numeroPedimento : null,
					}] ,
					cuentaPredial : {
						numero : null,
					},
					complementoConcepto : {},
					parte : [{
						claveProdServ : null,
						noIdentificacion : null,
						cantidad : null,
						unidad : null,
						descripcion : null,
						valorUnitario : null,
						importe : null,
						informacionAduanera : [{
							numeroPedimento : null,
						}]
					}]

				}]
			} ,
			impuestos : {
				totalImpuestosRetenidos : null,
				totalImpuestosTraslados : null,
				retenciones : {
					retencion:[ {
						impuesto : null,
						importe : null
					} ]
				},
				traslados : {
					traslado:[ {
						impuesto : null,
						tipoFactor : null,
						tasaOCuota : null,
						importe : null
					} ]
				}
			},
			complemento :[ {
				any: []
			}],
			addenda : {
				any: []
			}

		}
	}
	
	this.getCFDI32 = function() {
		return {
			version : "3.2",//3.3
			serie : null,
			folio : null,
			fecha : new Date(),
			sello : null,
			formaDePago : "Pago en una sola exhibición",
			noCertificado : null,
			certificado : null,
			condicionesDePago : null,
			subTotal : null,
			descuento : null,
			motivoDescuento : null,
			moneda : "MXN",
			tipoCambio : null,
			total : null,
			tipoDeComprobante : "ingreso",
			metodoDePago : "03-Transferencia electrónica de fondos",
			lugarExpedicion : null,
			numCtaPago : null,
			folioFiscalOrig : null,
			serieFolioFiscalOrig : null,
			fechaFolioFiscalOrig : null,
			montoFolioFiscalOrig : null,

			emisor : {
				rfc : null, //hardcode para Web Service de pruebas
				nombre : null,
				regimenFiscal : [ {
				    regimen: "PERSONAS MORALES"
				} ],
				domicilioFiscal: null, // usar getDomicilio
				expedidoEn: null // usar getDomicilio
				
			},
			receptor : {
				rfc : null,
				nombre : null,
			    domicilio: null // usar getDomicilio
			},
			conceptos : {
				concepto: [ 
//					{
//					noIdentificacion : null,
//					cantidad : null,
//					unidad : null,
//					descripcion : null,
//					valorUnitario : null,
//					importe : null,
//					informacionAduanera: [{
//						numero: null,
//						fecha: null,
//						aduana: null
//					}],
//					cuentaPredial : {
//						numero : null
//					},
//					complementoConcepto : {},
//					parte : [{
//						noIdentificacion : null,
//						cantidad : null,
//						unidad : null,
//						descripcion : null,
//						valorUnitario : null,
//						importe : null,
//						informacionAduanera : [{
//							numero: null,
//							fecha: null,
//							aduana: null
//						}]
//					}]
//
//				}
					]
			} ,
			impuestos : {
				totalImpuestosRetenidos : null,
				totalImpuestosTrasladados : null,
				retenciones : null,
				traslados : null
			},
			complemento : null,
			addenda : null

		}
	}
	
	this.actualizarPrefactura=function(comprobante,uuid){
		var d = $q.defer();
		$http.post("/facturacion/actualizar/"+uuid,comprobante).then(
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
	
	this.guardarPrefactura=function(comprobante){
		var d = $q.defer();
		$http.post("/facturacion/generar/",comprobante).then(
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
	
	this.enviarComprobante=function(send){
		var d = $q.defer();
		$http.post("/facturacion/timbrar/",send).then(
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
	
	this.enviarCorreo=function(send){
		var d = $q.defer();
		$http.post("/facturacion/emailTo/",send).then(
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
	
	this.timbrarGuardado=function(send,uuid){
		var d = $q.defer();
		$http.post("/facturacion/timbrar/"+uuid,send).then(
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
	
	this.timbrar = function(uuid,email) {
		var d = $q.defer();
		$http.post("/facturacion/timbrarGenerado/",""+uuid+","+email).then(
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
	
	this.getComprobantes=function(url,page){
		var d = $q.defer();
		$http.get(url+"/"+page).then(
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
	
	this.getDummyReceptores = function() {
		return [
			  {
				  rfc : "ABC010101AB0",
				  nombre : "Empresa Prueba 1 SA de CV"
			  },
			  {
				  rfc : "DEF010101CD1",
				  nombre : "Consultoría Prueba 2 SA de CV"
			  },
			  {
				  rfc : "GHI010101EF2",
				  nombre : "Empresa Prueba 3 "
			  }
		]
	}

	this.getReceptores=function(rfc){
		var d = $q.defer();
		$http.get("/emisor/consultar/"+rfc).then(
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
	
	this.getComprobanteGuardado=function(uuid){
		var d = $q.defer();
		$http.get("/facturacion/editar/"+uuid).then(
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
	
	this.buscar=function(fi,ff,rfc){
		var d = $q.defer();
		send={
				params:{
					fi:fi,
					ff,ff
				}
		}
		$http.get("/facturacion/buscar/"+rfc,send).then(
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
	this.cancelarFactura=function(uuid,rfc){
		var d = $q.defer();
		$http.post("/facturacion/cancelarAck/",""+uuid+","+rfc).then(
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
	
	//numPaginas
	this.numPaginasSerie=function(rfc,serie){
		var d = $q.defer();
		$http.get("/facturacion/numPaginasSerie/"+rfc+"/"+serie).then(
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
	this.numPaginasRfc=function(rfc,receptor){
		var d = $q.defer();
		$http.get("/facturacion/numPaginasRec/"+rfc+"/"+receptor).then(
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
	
	this.getPaginas=function(rfc){
		var d = $q.defer();
		$http.get("/facturacion/numPaginas/"+rfc).then(
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
	
}]);

app.controller("comprobante", [ '$scope', '$location', 'comprobanteService','$routeParams','conceptosService','serialService','$window',
		function($scope, $location, comprobanteService,$routeParams,conceptosService,serialService,$window) {
	$scope.comprobante=comprobanteService.getCFDI32();
	
}]);