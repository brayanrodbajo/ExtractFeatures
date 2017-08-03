import jAudioFeatureExtractor.ACE.XMLParsers.XMLDocumentParser;
import jAudioFeatureExtractor.AudioFeatures.FeatureExtractor;
import jAudioFeatureExtractor.DataTypes.RecordingInfo;

import jAudioFeatureExtractor.DataModel;
import jAudioFeatureExtractor.ModelListener;

import jAudioFeatureExtractor.jAudioTools.AudioMethods;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Runtime;

import org.omg.CORBA.portable.OutputStream;

import processing.core.PApplet;

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
		dm = new DataModel ("features.xml", null, "10sec_estimulo_c_fk_muestra.arff", "10sec_estimulo_c_fv_pruebas_muestra.arff");
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
	@SuppressWarnings("static-access")
	public void listFilesForFolder(final File folder) throws Exception {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				try {
					String path =fileEntry.getAbsolutePath();
					if (path.contains(".mp3")) {
						final int numberOfSeconds =  10;
						String wav_path = path.substring(0, path.length()-4)+"Large.wav";
						String wav_path_short = path.substring(0, path.length()-4)+".wav";
						AudioFileProcessor.mp3towav(path, wav_path);
						double seconds = AudioFileProcessor.secondsOf(wav_path);
						int startSecond = ThreadLocalRandom.current().nextInt(0, (int)(seconds) - numberOfSeconds);
						AudioFileProcessor.copyAudio(wav_path, wav_path_short, startSecond, numberOfSeconds);
						
						ri.add(new RecordingInfo(wav_path_short));
						System.out.println(wav_path);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
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
