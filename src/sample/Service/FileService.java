package sample.Service;

import java.io.File;

public interface FileService {
    String getParent(File file);
    void deleteFile(String path);

    void copyFile(String path, String dest);

    void moveFile(String path, String dest);
}
