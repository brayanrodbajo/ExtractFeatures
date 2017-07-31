import jAudioFeatureExtractor.ACE.XMLParsers.XMLDocumentParser;
import jAudioFeatureExtractor.AudioFeatures.FeatureExtractor;
import jAudioFeatureExtractor.DataTypes.RecordingInfo;

import jAudioFeatureExtractor.DataModel;
import jAudioFeatureExtractor.ModelListener;

import jAudioFeatureExtractor.jAudioTools.AudioMethods;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.Vector;

import org.omg.CORBA.portable.OutputStream;

import processing.core.PApplet;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

@SuppressWarnings("serial")
public class reconocedor  {
	//TODO Reemplazar la referencia a la ruta donde se encuentran los archivos
	
	
	Vector <RecordingInfo> ri =new Vector <RecordingInfo>();  
	
	String csv_path;
	
	String metaFinal;
	AudioMethods metodos;
	ModelListener ml;  
	DataModel dm;
	
	//MFCC m	fccObj;

	public void setup(String path) {
		csv_path = "/home/uv/Dropbox/Programming/ComputerMusic/Musicar/Listado de Musica.csv"; //path of features
		dm = new DataModel ("features.xml", null, "estimulo_c_fk_muestra.arff", "estimulo_c_fv_pruebas_muestra.arff");
		metaFinal = "";
		
		final File folder = new File(path);
		try {
			listFilesForFolder(folder);
			extract();
		} catch (Exception e) {
			System.out.println("excepcion ");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void mp3towav(String sourceFile, String destinationFile) {

		Converter myConverter = new Converter();
        try {
			myConverter.convert(sourceFile, destinationFile);
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@SuppressWarnings("static-access")
	public void listFilesForFolder(final File folder) throws Exception {
		int i=0;
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				try {
					String path =fileEntry.getAbsolutePath();
					if (path.contains(".mp3")) {
						//metodos.getAudioFileFormatData(fileEntry);
						String wav_path = path.substring(0,path.length()-4)+".wav";
						mp3towav(path, wav_path);
						
						ri.add(new RecordingInfo(wav_path));
						System.out.println(wav_path);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}
		}
		
		
	}
	
	public void extract() throws Exception{
		dm.extract(512, 0.0, 22.05, false, true, false, ri, 1, csv_path);
	}
	
	public static void main(String args[]){
		String path= "/home/uv/Dropbox/Programming/ComputerMusic/Musicar/ANALISIS MUSICA ICESI/BALADAS INSTRUMENTALES AC MODERNO - ESTIMULO C - ROMANTICO - AMOROSO - NOSTALGICO/";
	
		System.out.println("Ejecucion Iniciadaaaaaa");
		reconocedor recon = new reconocedor();
		recon.setup(path);
		System.out.println("Ejecucion Finalizada");
	}
	

}
