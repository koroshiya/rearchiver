package archiver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
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
	
	private static void parseFile(String arg) throws RarException, IOException{
		if ((new File(arg)).isFile()){
			if (arg.endsWith(".cbr")){
				createZip(arg, "cbz");
			}else if(arg.endsWith(".rar")){
				createZip(arg, "zip");
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
		String newName = (i > 0) ? path.substring(0, i+1) + extension : path;
		
		Archive archive = new Archive(f);
		//ZipFile zip = new ZipFile(newName);
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
