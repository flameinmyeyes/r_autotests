package ru.exportcenter.test.vet;

import framework.Ways;

import java.io.File;
import java.util.Arrays;

public class FileFunc {

    /**
     * Удаление всех файлов, подпапок и саму папку в указанной директории
     * @param file - удаляемый файл
     *  пример recursiveDelete(new File("C:\share\files_for_tests\downloads"));
     */
    public static void recursiveDelete(File file){
        // до конца рекурсивного цикла
        if (!file.exists()) {
            return;
        }

        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                System.out.println("listFiles: " + Arrays.toString(file.listFiles()));
                // рекурсивный вызов
                recursiveDelete(f);
            }
        }

        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
        System.out.println("Удаленный файл или папка: " + file.getAbsolutePath());
    }
    public static void createFolder(String path) {
        //создаем папку downloads
        File dir = new File(path);
        dir.mkdir();
    }

    public static void recursiveDeleteWithoutMainDir(File file){
        recursiveDelete(file);
        createFolder(String.valueOf(file));

    }

}
