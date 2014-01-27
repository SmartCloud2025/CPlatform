package cn.tisson.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Author Jasic
 * Date 13-12-16.
 * 文件处理工具类
 */
public class FileUtil {


    /**
     * 解压缩
     *
     * @param srcZipFile
     * @param destFile
     * @return
     */
    public static boolean unzip(File srcZipFile, File destFile) {
        boolean isSuccessful = true;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcZipFile));

            ZipInputStream zis = new ZipInputStream(bis);

            BufferedOutputStream bos;
            ZipEntry entry;
            int len;
            byte[] temp = new byte[1024];


            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                File unzipDst = new File(destFile + "/" + entryName);

                if (entry.isDirectory()) {
                    unzipDst.mkdir();
                    continue;
                }
                unzipDst.getParentFile().mkdirs();

                bos = new BufferedOutputStream(new FileOutputStream(unzipDst));
                while ((len = zis.read(temp, 0, temp.length)) != -1) {
                    bos.write(temp, 0, len);

                }
                bos.flush();
                bos.close();

            }
            zis.close();
        } catch (IOException e) {
            isSuccessful = false;
        }
        return isSuccessful;
    }

}
