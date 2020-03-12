package src.main.java.controller;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class FileInfo {
    public enum FileType {
        FILE("F"), DIRECTORY("D");

        private String name;

        public String getName() {
            return name;
        }

        FileType(String name) {
            this.name = name;
        }
    }

    private String fullfilename;
    private String filename;
    private String extension;
    private FileType type;
    private long size;
    private LocalDateTime lastModified;

    public String getFullFilename() { return fullfilename; }

    public void setFullFilename(String fullfilename) {
        this.fullfilename = fullfilename;
    }

    public String getFilename() { return filename; }
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() { return extension; }
    public void setExtension() { this.extension = extension;  }

    public FileType getType() {
        return type;
    }
    public void setType(FileType type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public FileInfo(Path path) {
        try {
            this.fullfilename = getFileNameFull(path);
            this.filename = getFileNameRemoveExtension(path);
            this.extension = getExtensionNotFileName(path);
            this.size = Files.size(path);
            this.type = Files.isDirectory(path) ? FileType.DIRECTORY : FileType.FILE;
            if (this.type == FileType.DIRECTORY) {
                this.size = -1L;
            }
            this.lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path).toInstant(), ZoneOffset.ofHours(3));
        } catch (IOException e) {
            throw new RuntimeException("Нет данных о файле");
        }
    }

    private String getFileNameFull(Path path) {
        String fullFileName = path.getFileName().toString();
        if (FilenameUtils.getName(fullFileName) == null){
            return null;
        }
        return FilenameUtils.getName(fullFileName);

    }

    public String getFileNameRemoveExtension(Path path) {
        String fullFileName = path.getFileName().toString();
        if (FilenameUtils.removeExtension(fullFileName) == null){
            return null;
        }
        if(Files.isDirectory(path)){
            return fullFileName;
        }
        return FilenameUtils.removeExtension(fullFileName);
    }

    public String getExtensionNotFileName(Path path) {
        String fullFileName = path.getFileName().toString();
        if(Files.isDirectory(path)){
            return "[ DIR ]";
        }
        return FilenameUtils.getExtension(fullFileName);
    }
}