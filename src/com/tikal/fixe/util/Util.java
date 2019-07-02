package com.tikal.kiosko.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;

import com.tikal.cacao.sat.cfd.Comprobante;
import com.tikal.cacao.sat.timbrefiscaldigital.TimbreFiscalDigital;

import mx.gob.sat.cancelacfd.Acuse;

public abstract class Util {

	public static String regresaTextoOCadenaVacia(String texto) {
		if (texto == null) {
			texto = "";
		}	
		return texto;
	}
	
	public static Comprobante unmarshallXML(String cadenaXML) {
		try {
			//TODO en el caso de nomina agregar al método newInstance la clase NominaElement.class
			JAXBContext jaxbContext = JAXBContext.newInstance(Comprobante.class, TimbreFiscalDigital.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringBuffer xmlStr = new StringBuffer(cadenaXML);
			Comprobante comprobante = (Comprobante) unmarshaller.unmarshal(new StreamSource( new StringReader(xmlStr.toString() ) ) );
			return comprobante;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Acuse unmarshallAcuseXML(String acuseXML) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Acuse.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringBuffer xmlStr = new StringBuffer(acuseXML);
			Acuse acuse = (Acuse) unmarshaller.unmarshal(new StreamSource( new StringReader( xmlStr.toString() ) ) );
			return acuse;
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String marshallComprobante(Comprobante c) {
		try {
			JAXBContext jaxbContext;
			Marshaller jaxbMarshaller;
			
			jaxbContext = JAXBContext.newInstance(Comprobante.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,	
					"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd ");
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(c, sw);
			return sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static XMLGregorianCalendar getXMLDate(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		XMLGregorianCalendar date2 = null;
		try {
		
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(
					c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH),
					c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND),
					DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
		
		} catch (DatatypeConfigurationException exception) {
			return date2;
		}
		 
		return date2;
	}
}
