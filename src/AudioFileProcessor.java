import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat.Encoding;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class AudioFileProcessor {
	
	public static double secondsOf(String pathFile) {
		File file = new File(pathFile);
		AudioInputStream audioInputStream;
		double durationInSeconds = 0;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(file);
			AudioFormat format = audioInputStream.getFormat();
			long frames = audioInputStream.getFrameLength();
			durationInSeconds = (frames+0.0) / format.getFrameRate();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return durationInSeconds;
	}
	
	public static void mp3towav(String sourceFile, String destinationFile) {

		Converter myConverter = new Converter();
        try {
			myConverter.convert(sourceFile, destinationFile);
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
	
	
	public static void copyAudio(String sourceFileName, String destinationFileName, int startSecond, int secondsToCopy) {
		AudioInputStream inputStream = null;
		AudioInputStream shortenedStream = null;
		try {
			File file = new File(sourceFileName);
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
			AudioFormat format = fileFormat.getFormat();
			inputStream = AudioSystem.getAudioInputStream(file);
			int bytesPerSecond = format.getFrameSize() * (int)format.getFrameRate();
			inputStream.skip(startSecond * bytesPerSecond);
			long framesOfAudioToCopy = secondsToCopy * (int)format.getFrameRate();
			shortenedStream = new AudioInputStream(inputStream, format, framesOfAudioToCopy);
			File destinationFile = new File(destinationFileName);
			AudioSystem.write(shortenedStream, fileFormat.getType(), destinationFile);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (Exception e) { System.out.println(e); }
			if (shortenedStream != null) try { shortenedStream.close(); } catch (Exception e) { System.out.println(e); }
		}
	}
}