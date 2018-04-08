package sample.Service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileServiceImpl implements FileService {

    public String getParent(File file) {
        if(file.getParent() == null)
            return file.toString();
        if (file.getParent().charAt(file.getParent().length() - 1) == '\\')
            return file.getParent();
        else
            return file.getParent() + "\\";
    }

    public void deleteFile(String path) {
        File source = new File(path);

        if(source.toPath().isAbsolute()) {
            try {
                if(source.exists()) {
                    if (source.isFile())
                        FileUtils.forceDelete(source);
                    if (source.isDirectory())
                        FileUtils.deleteDirectory(source);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyFile(String path, String dest){
        File source = new File(path);
        File target = new File(dest);

        if(target.toPath().isAbsolute() && source.toPath().isAbsolute()) {
            try {
                if(source.exists() && target.getParentFile().exists()) {
                    if (source.isFile())
                        FileUtils.copyFile(source, target);
                    if (source.isDirectory())
                        FileUtils.copyDirectory(source, target);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveFile(String path, String dest) {
        File source = new File(path);
        File target = new File(dest);

        if(target.toPath().isAbsolute() && source.toPath().isAbsolute()) {
            try {
                if(source.exists() && target.getParentFile().exists()) {
                    if (source.isFile())
                        FileUtils.moveFile(source, target);
                    if (source.isDirectory())
                        FileUtils.moveDirectory(source, target);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
