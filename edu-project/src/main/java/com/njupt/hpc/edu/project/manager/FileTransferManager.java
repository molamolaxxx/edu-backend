package com.njupt.hpc.edu.project.manager;

import com.njupt.hpc.edu.common.exception.EduProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author : molamola
 * @Project: edu
 * @Description:
 * @date : 2020-11-12 16:35
 **/
@Component
@Slf4j
public class FileTransferManager {

    /**
     * 将本地文件传输到http输出流
     * @param path
     * @param response
     */
    public boolean transfer(String path, HttpServletResponse response) {
        File file = new File(path);
        if (!file.exists()) {
            throw new EduProjectException("文件不存在");
        }

        // 获得文件输入流
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(file.getName().getBytes("utf-8"), "utf-8"));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            fileInputStream = new FileInputStream(file);
            // 装饰成bufferedStream
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[1024];
            int read = 0;
            while (-1 != (read = bufferedInputStream.read(buffer, 0, buffer.length))){
                bufferedOutputStream.write(buffer, 0, read);
            }

        } catch (FileNotFoundException e) {
            log.error("文件未找到,"+ e.getMessage(), e);
            return false;
        } catch (IOException e) {
            log.error("出现io错误,"+ e.getMessage(), e);
            return false;
        } finally {
            try {
                if (null != bufferedInputStream) {
                    bufferedInputStream.close();
                }
                if (null != bufferedOutputStream) {
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                log.error("关闭文件出错,"+ e.getMessage(), e);
                return false;
            }
        }
        return true;
    }
}
