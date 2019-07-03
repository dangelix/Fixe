package com.tikal.kiosko.springController;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;
import com.tikal.cacao.dao.FacturaDAO;
import com.tikal.cacao.factura.Estatus;
import com.tikal.cacao.factura.ws.WSClient;
import com.tikal.cacao.model.Factura;
import com.tikal.cacao.model.Imagen;
import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.cacao.sat.cfd.Comprobante.Conceptos.Concepto;
import com.tikal.cacao.sat.cfd.Comprobante.Receptor;
//import com.tikal.kiosko.modelo.Response;
//import com.tikal.kiosko.springController.vo.CompraConCaptchaVO;
import com.tikal.cacao.sat.cfd.ObjectFactoryComprobante;
import com.tikal.cacao.sat.timbrefiscaldigital.TimbreFiscalDigital;
import com.tikal.cacao.util.AsignadorDeCharset;
import com.tikal.cacao.util.Util;
import com.tikal.kiosko.dao.BitacoraDAO;
import com.tikal.kiosko.dao.CompraDAO;
import com.tikal.kiosko.dao.FolioDAO;
import com.tikal.kiosko.modelo.Compra;
import com.tikal.kiosko.modelo.RegistroBitacora;
import com.tikal.kiosko.modelo.Response;
import com.tikal.kiosko.util.EmailSender;
import com.tikal.kiosko.util.JsonConvertidor;
import com.tikal.kiosko.util.PDFFactura;

import localhost.TimbraCFDIResponse;

@Controller
@RequestMapping(value={"/servicio"})
public class ServicioController {
	
	@Autowired
	CompraDAO compradao;
	
	@Autowired
	@Qualifier("prodClient")
	WSClient client;
	
	@Autowired
	FacturaDAO facturaDAO;
	
	@Autowired
	FolioDAO foliodao;
	
	@Autowired
	BitacoraDAO bitacora;
	
	private static final String urlVerifyCaptcha = "https://www.google.com/recaptcha/api/siteverify";
	
	private static final String serverK = "6Ld4bikUAAAAAGtL91J0j65RuFxDWO-xKx06lPoy";
	
	@PostConstruct
	public void init() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package with the WSDL java classes
		marshaller.setContextPath("localhost");

		client.setDefaultUri("http://www.timbracfdipruebas.mx/serviciointegracionpruebas/Timbrado.asmx");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);

	}
	
	@RequestMapping(value="/validarCaptcha", method= RequestMethod.POST, produces="application/json")
	public void valCaptcha(HttpServletRequest req, HttpServletResponse res,@RequestBody String captchaResponse) throws IOException{
		System.out.println("Validando chapta....");
		if (verificarCaptcha(captchaResponse)) {
			System.out.println("----");
			res.getWriter().print("Validaci�n correcta");
		} else {
			System.out.println("------------k");
			res.sendError(400);
		}
		
		
	}
	
	@RequestMapping(value="/buscar/{id}", method= RequestMethod.GET, produces="application/json")
	public void buscar(HttpServletRequest req, HttpServletResponse res,@PathVariable String id) throws IOException{
		
		res.getWriter().print(JsonConvertidor.toJson(compradao.get(id)));
	}
	
	@RequestMapping(value="/buscar", method= RequestMethod.POST, produces="application/json")
	public void buscar2(HttpServletRequest req, HttpServletResponse res,@RequestBody String json) throws IOException{
		
		//CompraConCaptchaVO cVO = (CompraConCaptchaVO) JsonConvertidor.fromJson(json, CompraConCaptchaVO.class);
		

			Compra c= (Compra) JsonConvertidor.fromJson(json, Compra.class);
			//Compra c = cVO.getCompra();
			Compra c2=compradao.get(c.getId().toString());
			if(c2!= null){
				res.getWriter().print(JsonConvertidor.toJson(c2));
			}else{
				compradao.save(c);
				res.getWriter().print(JsonConvertidor.toJson(c));
			}
		
		
	}
	
	@RequestMapping(value="/facturar", method= RequestMethod.POST, produces="application/json", consumes="application/json")
	public void facturar(HttpServletRequest req, HttpServletResponse res, @RequestBody String json) throws IOException{
		
		ServicioVO servicioVO = (ServicioVO) JsonConvertidor.fromJson(json, ServicioVO.class);
		Compra compra = compradao.get(servicioVO.getIdServicio());
		Comprobante comprobante = getComprobante(servicioVO.getReceptor(), compra);
		comprobante.setFolio(foliodao.getFolio()+"");
		comprobante.setSerie("FK");
		String cadenaComprobante = com.tikal.kiosko.util.Util.marshallComprobante(comprobante);
		TimbraCFDIResponse timbraResponse = client.getTimbraCFDIResponse(cadenaComprobante);
		List<Object> respuesta = timbraResponse.getTimbraCFDIResult().getAnyType();
		int codigoError = (int) respuesta.get(1);
		
		PrintWriter writer = res.getWriter();
		if (codigoError == 0) {
			String cfdiXML = (String) respuesta.get(3);
			Comprobante cfdi = Util.unmarshallXML(cfdiXML);
			String selloDigital = (String) respuesta.get(5);
			byte[] bytesQRCode = (byte[]) respuesta.get(4);
			TimbreFiscalDigital timbreFD = (TimbreFiscalDigital) cfdi.getComplemento().getAny().get(0);
			Date fechaCertificacion = timbreFD.getFechaTimbrado().toGregorianCalendar().getTime();
			Factura factura = new Factura(timbreFD.getUUID(), cfdiXML, cfdi.getEmisor().getRfc(),
					cfdi.getReceptor().getNombre(), fechaCertificacion, selloDigital, bytesQRCode);
			facturaDAO.guardar(factura);

			//crearReporteRenglon(factura);
			Imagen imagen = new Imagen();
			imagen.setImage("images/FIdit.png");
			EmailSender mailero = new EmailSender();
			//Imagen imagen = imagenDAO.get(cfdi.getEmisor().getRfc());
			writer.println("�La factura se timbr� con �xito!");
			mailero.enviaFactura(servicioVO.getEmail(), factura, "", cfdi.getComplemento().getAny().get(0).toString(),
					imagen);
			
			compra.setUuid(timbreFD.getUUID());
			compradao.save(compra);
			foliodao.incrementa();
			String mensaje = (String) respuesta.get(2);
			writer.print(mensaje);
			RegistroBitacora bita= new RegistroBitacora();
			bita.setEvento("Se factur� el folio "+compra.getId());
			bitacora.addReg(bita);
		} else {
			
			String mensajeError = (String) respuesta.get(2);
			System.out.println(mensajeError);
			writer.print(mensajeError);
			RegistroBitacora bita= new RegistroBitacora();
			bita.setEvento("error al facturar el folio "+compra.getId()+" "+ mensajeError);
			bitacora.addReg(bita);
		}
		
	}
	
	
	
	@RequestMapping(value = "/obtenerXML/{uuid}", method = RequestMethod.GET, produces = "text/xml")
	public void obtenerXML(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		try {
			AsignadorDeCharset.asignar(req, res);
			Factura factura = facturaDAO.consultar(uuid);
			PrintWriter writer = res.getWriter();
			if (factura != null) {
				res.setContentType("text/xml");
				writer.println(factura.getCfdiXML());
			} else {
				writer.println("La factuca con el folio fiscal (uuid) ".concat(uuid).concat(" no existe"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/creaFolio", method = RequestMethod.GET)
	public void crearFolio(HttpServletRequest req, HttpServletResponse res) {
		foliodao.crear();
	}
	
	@RequestMapping(value = "/obtenerPDF/{uuid}", method = RequestMethod.GET, produces = "application/pdf")
	public void consultar(HttpServletRequest req, HttpServletResponse res, @PathVariable String uuid) {
		Factura factura = facturaDAO.consultar(uuid);
		if (factura != null) {
			try {
				res.setContentType("Application/Pdf");
				Comprobante cfdi = Util.unmarshallXML(factura.getCfdiXML());
				Imagen imagen = new Imagen();
				imagen.setImage("images/FIdit.png");

				PDFFactura pdfFactura = new PDFFactura();
				PdfWriter writer = PdfWriter.getInstance(pdfFactura.getDocument(), res.getOutputStream());
				pdfFactura.getPieDePagina().setUuid(uuid);
				if (factura.getEstatus().equals(Estatus.CANCELADO)) {
					pdfFactura.getPieDePagina().setFechaCancel(factura.getFechaCancelacion());
					pdfFactura.getPieDePagina().setSelloCancel(factura.getSelloCancelacion());
					;
				}
				writer.setPageEvent(pdfFactura.getPieDePagina());

				pdfFactura.getDocument().open();
				if (factura.getEstatus().equals(Estatus.TIMBRADO))
					pdfFactura.construirPdf(cfdi, factura.getSelloDigital(), factura.getCodigoQR(), imagen,
							factura.getEstatus());
				else if (factura.getEstatus().equals(Estatus.GENERADO)) {
					pdfFactura.construirPdf(cfdi, imagen, factura.getEstatus());

					PdfContentByte fondo = writer.getDirectContent();
					Font fuente = new Font(FontFamily.HELVETICA, 45);
					Phrase frase = new Phrase("Pre-factura", fuente);
					fondo.saveState();
					PdfGState gs1 = new PdfGState();
					gs1.setFillOpacity(0.5f);
					fondo.setGState(gs1);
					ColumnText.showTextAligned(fondo, Element.ALIGN_CENTER, frase, 297, 650, 45);
					fondo.restoreState();
				}

				else if (factura.getEstatus().equals(Estatus.CANCELADO)) {
					pdfFactura.construirPdfCancelado(cfdi, factura.getSelloDigital(), factura.getCodigoQR(), imagen,
							factura.getEstatus(), factura.getSelloCancelacion(), factura.getFechaCancelacion());

					pdfFactura.crearMarcaDeAgua("CANCELADO", writer);
				}

				pdfFactura.getDocument().close();
				res.getOutputStream().flush();
				res.getOutputStream().close();
				

			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else {
			try {
				AsignadorDeCharset.asignar(req, res);
				PrintWriter writer = res.getWriter();
				writer.println(
						"El N�mero de Folio Fiscal (UUID): ".concat(uuid).concat(" no pertenece a ninguna factura"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private Comprobante getComprobante(Receptor receptor, Compra compra) {
		ObjectFactoryComprobante ofComprobante = new ObjectFactoryComprobante();
		Comprobante comprobante = ofComprobante.createComprobante();
		
		comprobante.setFecha(com.tikal.kiosko.util.Util.getXMLDate( new Date() ) );
		comprobante.setLugarExpedicion("M�XICO");
		comprobante.setMoneda("MXN");
		comprobante.setTipoDeComprobante("ingreso");
		comprobante.setFormaDePago("Pago en una sola exhibici�n");
		comprobante.setMetodoDePago("Efectivo");
		//comprobante.setSubTotal(new BigDecimal(200));
		//comprobante.setTotal(new BigDecimal(200.0));
		comprobante.setVersion("3.2");

		Comprobante.Emisor emisor = ofComprobante.createComprobanteEmisor();
		//emisor.setRfc("AAA010101AAA");
		emisor.setNombre("Find it Corporation M�xico S.A. de C.V.");
		emisor.setRfc("FIC1704078IA");// para prueba con ValidarCfd32Client
										// CARC7302234T5
		//emisor.setNombre("CARLOS CASTILLO ROJAS");

//		TUbicacionFiscal ubicacionFiscalEmisor = ofComprobante.createTUbicacionFiscal();
//		ubicacionFiscalEmisor.setCalle("MARTIN CARRERA");
//		ubicacionFiscalEmisor.setNoExterior("94");
//		ubicacionFiscalEmisor.setColonia("MARTIN CARRERA");
//		ubicacionFiscalEmisor.setLocalidad("MEXICO, D.F.");
//		ubicacionFiscalEmisor.setMunicipio("GUSTAVO A. MADERO");
//		ubicacionFiscalEmisor.setEstado("Ciudad de M�xico");
//		ubicacionFiscalEmisor.setPais("MEXICO");
//		ubicacionFiscalEmisor.setCodigoPostal("07070");
//		emisor.setDomicilioFiscal(ubicacionFiscalEmisor);

		Comprobante.Emisor.RegimenFiscal regimenFiscalEmisor = ofComprobante.createComprobanteEmisorRegimenFiscal();
		regimenFiscalEmisor.setRegimen("General de Ley Personas Morales");
		emisor.getRegimenFiscal().add(regimenFiscalEmisor);
		comprobante.setEmisor(emisor);

		comprobante.setReceptor(receptor);

//		TUbicacion ubicacionReceptor = ofComprobante.createTUbicacion();
//		ubicacionReceptor.setCalle("FRANCISCO MORENO LOCAL A");
//		ubicacionReceptor.setColonia("LA VILLA DE GUADALUPE");
//		ubicacionReceptor.setMunicipio("GUSTAVO A. MADERO");
//		ubicacionReceptor.setEstado("Ciudad de M�xico");
//		ubicacionReceptor.setPais("M�XICO");
//		ubicacionReceptor.setCodigoPostal("CP. 07050");
//		receptor.setDomicilio(ubicacionReceptor);
//		comprobante.setReceptor(receptor);

		Comprobante.Conceptos conceptos = ofComprobante.createComprobanteConceptos();
		Comprobante.Conceptos.Concepto concepto = ofComprobante.createComprobanteConceptosConcepto();
		concepto.setCantidad(new BigDecimal(1));
		concepto.setUnidad("Servicio");
		concepto.setDescripcion(compra.getDetalle());
		concepto.setValorUnitario( Util.redondearBigD(compra.getPrecio() / 1.16f) );
		concepto.setImporte( concepto.getCantidad().multiply( concepto.getValorUnitario() ) );
		conceptos.getConcepto().add(concepto);
		comprobante.setConceptos(conceptos);
		
		List<Concepto> conceptosList = comprobante.getConceptos().getConcepto();
		BigDecimal suma = new BigDecimal(0);
		for (Concepto concept: conceptosList) {
			suma = suma.add(concept.getImporte()); 
		}
		comprobante.setSubTotal(suma);

		Comprobante.Impuestos impuestos = ofComprobante.createComprobanteImpuestos();
		
		Comprobante.Impuestos.Traslados traslados = ofComprobante.createComprobanteImpuestosTraslados();
		Comprobante.Impuestos.Traslados.Traslado traslado = ofComprobante.createComprobanteImpuestosTrasladosTraslado();
		traslado.setImpuesto("IVA");
		traslado.setTasa(Util.redondearBigD(0.16));
		traslado.setImporte( Util.redondearBigD( comprobante.getSubTotal().multiply( traslado.getTasa() ).doubleValue() ) );
		traslados.getTraslado().add(traslado);
		impuestos.setTraslados(traslados);
		impuestos.setTotalImpuestosTrasladados(traslado.getImporte());
		comprobante.setImpuestos(impuestos);
		
		comprobante.setTotal( comprobante.getSubTotal().add( comprobante.getImpuestos().getTotalImpuestosTrasladados() ) );

		return comprobante;
	}

	
	private boolean verificarCaptcha(String reCaptchaResponse) {
		System.out.println("ReCaptchaResponse: " + reCaptchaResponse);
		if (reCaptchaResponse == null || "".equals(reCaptchaResponse)) {
			return false;
		}
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(urlVerifyCaptcha).queryParam("secret", serverK)
					.queryParam("response", reCaptchaResponse);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			ResponseEntity<Response> response = restTemplate.exchange(builder
					.build().encode().toUri(), HttpMethod.GET, entity,
					Response.class);
			
			Response rs = response.getBody();
			System.out.println(rs);
			System.out.println(rs.isSuccess());
			
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return rs.isSuccess();
			}
			
			return false;
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
			return false;
		} catch (RestClientException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
