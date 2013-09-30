package archiver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import junrar.Archive;
import junrar.exception.RarException;
import junrar.rarfile.FileHeader;

public class Rearchiver {
	
	public static void main(String args[]){
		for (String arg : args){
			try{
				parseFile(arg);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private static boolean isValid(final File file) {
	    ZipFile zipfile = null;
	    try {
	        zipfile = new ZipFile(file);
	        return true;
	    } catch (ZipException e) {
	        return false;
	    } catch (IOException e) {
	        return false;
	    } finally {
	        try {
	            if (zipfile != null) {
	                zipfile.close();
	                zipfile = null;
	            }
	        } catch (IOException e) {
	        }
	    }
	}
	
	private static void parseFile(String arg) throws RarException, IOException{
		File input = new File(arg);
		if ((input).isFile()){
			String extension;
			String lowerArg = arg.toLowerCase();
			if (lowerArg.endsWith(".cbr")){
				extension = "cbz";
			}else if(lowerArg.endsWith(".rar")){
				extension = "zip";
			}else{
				return;
			}
			if (isValid(input)){
				int i = arg.lastIndexOf('.');
				String newName = ((i > 0) ? arg.substring(0, i+1) : arg) + extension;
				input.renameTo(new File(newName));
			}else{
				createZip(arg, extension);
			}
		}else{
			parseDir(arg);
		}
	}
	
	private static void parseDir(String arg) throws RarException, IOException{
		File dir = new File(arg);
		for(String file : dir.list()){
			parseFile(arg + File.separatorChar + file);
		}
	}
	
	private static void createZip(String path, String extension) throws RarException, IOException{
		
		File f = new File(path);
		int i = path.lastIndexOf('.');
		String newName = ((i > 0) ? path.substring(0, i+1) : path) + extension;
		
		Archive archive = new Archive(f);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(newName));
        
		for (FileHeader hd : archive.getFileHeaders()){

			InputStream in = archive.getInputStream(hd);
	        out.putNextEntry(new ZipEntry(hd.getFileNameString())); 

	        byte[] b = new byte[4096];
	        int count;

	        while ((count = in.read(b)) > 0) {
	            out.write(b, 0, count);
	        }
	        in.close();
		}
        out.close();
        //zip.close();
        archive.close();
        f.delete();
	}
	
}
