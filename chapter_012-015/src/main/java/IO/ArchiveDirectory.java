package IO;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 4. Архивировать проект.  [#861]
 */
public class ArchiveDirectory {
    /**
     * Archive folder
     * @param ext extentions of required files.
     * @param source source path.
     * @param dest destination archive.
     */
    public void archive(List<String> ext, String source, String dest) {
        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(dest))) {
            List<File> toZip = this.getFilesFromDirectory(new File(source), ext);
            for (File file : toZip) {
                zip.putNextEntry(new ZipEntry(file.getAbsolutePath().substring(source.length())));
                Files.copy(file.toPath(), zip);
                zip.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get all files of required extention from directory and all subdir.
     * @param dir directory.
     * @param ext extentions of required files.
     * @return List with all required files.
     */
    private List<File> getFilesFromDirectory(File dir, List<String> ext) {
        List<File> result = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                result.addAll(this.getFilesFromDirectory(file, ext));
            } else if (ext.contains(FilenameUtils.getExtension(file.getName()))){
                result.add(file);
            }
        }
        return result;
    }
}
