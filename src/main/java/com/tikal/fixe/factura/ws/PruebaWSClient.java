package com.tikal.fixe.factura.ws;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import com.tikal.cacao.factura.ws.WSClient;

//import localhost.AsignaTimbresEmisor;
//import localhost.AsignaTimbresEmisorResponse;
import localhost.CancelaCFDI;
import localhost.CancelaCFDIAck;
import localhost.CancelaCFDIAckResponse;
import localhost.CancelaCFDIResponse;
import localhost.EncodeBase64;
import localhost.ObjectFactory;
import localhost.ObtieneCFDI;
import localhost.ObtieneCFDIResponse;
import localhost.ObtieneTimbresDisponibles;
import localhost.ObtieneTimbresDisponiblesResponse;
import localhost.RegistraEmisor;
import localhost.RegistraEmisorResponse;
import localhost.TimbraCFDI;
import localhost.TimbraCFDIResponse;

@Component
public class PruebaWSClient extends WSClient {

	private ObjectFactory of = new ObjectFactory();
	private EncodeBase64 base64 = new EncodeBase64();
	private final String uri = "https://www.timbracfdipruebas.mx/serviciointegracionpruebas/Timbrado.asmx";

	@Override
	public RegistraEmisorResponse getRegistraEmisorResponse(String rfcEmisor, String pass, ByteArrayInputStream cer,
			InputStream key) {
		RegistraEmisor request = of.createRegistraEmisor();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setBase64Cer(getByteArrayBase64(cer));
		request.setBase64Key(getByteArrayBase64(key));
		request.setContrasena(pass);
		
		RegistraEmisorResponse response = (RegistraEmisorResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri,
						request,
						new SoapActionCallback("http://localhost/RegistraEmisor"));
		return response;
	}

	@Override
	public TimbraCFDIResponse getTimbraCFDIResponse(String xmlComprobante) {
		TimbraCFDI request = of.createTimbraCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setXmlComprobanteBase64(getBase64CFDI(xmlComprobante));
		request.setIdComprobante(getidComprobante());
		
		TimbraCFDIResponse response = (TimbraCFDIResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri,
						request,
						new SoapActionCallback("http://localhost/TimbraCFDI"));
		return response;
	}

	@Override
	public CancelaCFDIResponse getCancelaCFDIResponse(String uuid, String rfcEmisor) {
		CancelaCFDI request = of.createCancelaCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setFolioUUID(uuid); //getFolioUUID()
		
		CancelaCFDIResponse response = (CancelaCFDIResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://localhost/CancelaCFDI"));
		return response;
	}

	@Override
	public CancelaCFDIAckResponse getCancelaCFDIAckResponse(String uuid, String rfcEmisor) {
		this.getWebServiceTemplate().getMessageSenders()[0] = this.crearMessageSender();
		CancelaCFDIAck request = of.createCancelaCFDIAck();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setFolioUUID(uuid); //getFolioUUID()
		
		CancelaCFDIAckResponse response =  (CancelaCFDIAckResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://localhost/CancelaCFDIAck"));
		return response;
	}

	@Override
	public ObtieneCFDIResponse getObtieneCFDIResponse(String uuid, String rfcEmisor) {
		ObtieneCFDI request = of.createObtieneCFDI();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		request.setRfcEmisor(rfcEmisor);
		request.setFolioUUID(uuid); //getFolioUUID()
		
		ObtieneCFDIResponse response = (ObtieneCFDIResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://localhost/ObtieneCFDI"));
		return response;
	}

	@Override
	public ObtieneTimbresDisponiblesResponse getObtieneTimbresDisponiblesResponse() {
		ObtieneTimbresDisponibles request = new ObtieneTimbresDisponibles();
		request.setUsuarioIntegrador(getUsuarioIntegrador());
		
		ObtieneTimbresDisponiblesResponse response = (ObtieneTimbresDisponiblesResponse) getWebServiceTemplate()
				.marshalSendAndReceive(uri, request,
						new SoapActionCallback("http://localhost/ObtieneTimbresDisponibles"));
		return response;
	}

//	@Override
//	public ObtieneTimbresDisponiblesResponse getObtieneTimbresDisponiblesResponsePorEmisor(String rfcEmisor) {
//		this.getWebServiceTemplate().getMessageSenders()[0] = this.crearMessageSender();
//		ObtieneTimbresDisponibles request = new ObtieneTimbresDisponibles();
//		request.setUsuarioIntegrador(getUsuarioIntegrador());
//		request.setRfcEmisor(rfcEmisor);
//		
//		ObtieneTimbresDisponiblesResponse response = (ObtieneTimbresDisponiblesResponse) getWebServiceTemplate()
//				.marshalSendAndReceive(uri, request,
//						new SoapActionCallback("http://localhost/ObtieneTimbresDisponibles"));
//		return response;
//	}

//	@Override
//	public AsignaTimbresEmisorResponse getAsignaTimbresEmisorResponse(String rfcEmisor, int numTimbres) {
//		AsignaTimbresEmisor request = new AsignaTimbresEmisor();
//		request.setRfcEmisor(rfcEmisor);
//		request.setNoTimbres(numTimbres);
//		
//		AsignaTimbresEmisorResponse response = (AsignaTimbresEmisorResponse) getWebServiceTemplate()
//				.marshalSendAndReceive(uri, request,
//						new SoapActionCallback("http://localhost/AsignaTimbresEmisor"));
//		return response;
//	}

	
	private String getUsuarioIntegrador(){
		//Esto es Fijo para el ambito de pruebas "mvpNUXmQfK8="
		
		//return "SSaC3HanfgtTGP+gChvWNg==";
		return "mvpNUXmQfK8=";
	}
	
	private String getByteArrayBase64(InputStream inputStream) {
		return base64.encodeByteArrayIS(inputStream);
	}
	
	private String getBase64CFDI(String xmlCFDI) {
		return base64.encodeString(xmlCFDI);
	}
	
	private String getidComprobante() {
		
		Integer i; 
		i=877;
		//i=(int) Math.floor(Math.random()*1000+1);		
		return i.toString();
	}
	
	private HttpComponentsMessageSender crearMessageSender() {
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		messageSender.setConnectionTimeout(120000);
		messageSender.setReadTimeout(120000);
		return messageSender;
	}
}
