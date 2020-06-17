////package com.example.javabaisc.bio;
////
////
//////
//////import aboo.bean.FileInfo;
//////
//////import aboo.service.FileService;
//////
//////import io.swagger.annotations.Api;
//////
//////import io.swagger.annotations.ApiOperation;
////
////import org.slf4j.Logger;
////
////import org.slf4j.LoggerFactory;
////
////import org.springframework.beans.factory.annotation.Autowired;
////
////import org.springframework.stereotype.Controller;
////
////import org.springframework.util.FileCopyUtils;
////
////import org.springframework.web.bind.annotation.*;
////
////import org.springframework.web.multipart.commons.CommonsMultipartFile;
////
////
////
////import javax.servlet.http.*;
////
////import java.io.*;
////
////import java.nio.*;
////
////import java.nio.channels.FileChannel;
////
////import java.util.Collection;
////import java.util.List;
////
////
////
////
////
/////**
////
//// * 文件的Controller
////
//// * Created by admin on 2017/5/16.
////
//// *
////
//// * @author Aboo
////
//// *
////
//// */
////
//////@Api(value = "文件的controller")
////
////@Controller
////
////@RequestMapping("/file")
////
////public class FileController {
////
////    private Logger log = LoggerFactory.getLogger(FileController.class);
////
////
////
////    @Autowired
////
////    private FileService fs;
////
////
////
////
////
////
////
////    /**
////
////     * 根据文件id下载文件  (IO)
////
////     * @param id  文件id，从url中取得
////
////     * @param request
////
////     * @param response
////
////     * @throws IOException  下载文件过程中，IO操作出现异常时，抛出异常
////
////     */
////
//////    @ApiOperation(value = "下载文件",notes = "根据url里文件id来下载文件")
////
////    @RequestMapping(value="/download/{id}", method = RequestMethod.GET)
////
////    public void download(@PathVariable("id") Long id,HttpServletRequest request,HttpServletResponse response) throws IOException {
////
////
////
////        if (id != null && fs.exists(id)) {
////
////            FileInfo fileInfo = fs.findById(id);
////
////            String filename = fileInfo.getFile_name();
////
////
////
////            String realPath = request.getServletContext().getRealPath("WEB-INF/Files/");
////
////            File file = new File(realPath, filename);
////
////
////
////            if (file.exists()) {
////
////                response.setContentType(fileInfo.getMime_type());// 设置Content-Type为文件的MimeType
////
////                response.addHeader("Content-Disposition", "attachment;filename=" + filename);// 设置文件名
////
////                response.setContentLength((int) fileInfo.getLength());
////
////
////
////                //JAVA IO
////
////                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
////
////                //Copy bytes from source to destination(outputstream in this example), closes both streams.
////
////                FileCopyUtils.copy(inputStream, response.getOutputStream());
////
////
////
////                log.debug("download succeed! ---"+filename);
////
////            }
////
////        }else{
////
////            throw new Error("file who's id="+id+" is not exist!");
////
////        }
////
////    }
////
////
////
////    /**
////
////     * 根据文件id下载文件 (NIO)
////
////     * @param id  文件id，从url中取得
////
////     * @param request
////
////     * @param response
////
////     * @throws IOException  下载文件过程中，IO操作出现异常时，抛出异常
////
////     */
////
////    @ApiOperation(value = "下载文件",notes = "根据url里文件id来下载文件")
////
////    @RequestMapping(value="/nioDownload/{id}", method = RequestMethod.GET)
////
////    public void nioDownload(@PathVariable("id") Long id,HttpServletRequest request,HttpServletResponse response) throws IOException {
////
////
////
////        if (id != null && fs.exists(id)) {
////
////            FileInfo fileInfo = fs.findById(id);
////
////            String filename = fileInfo.getFile_name();
////
////
////
////            String realPath = request.getServletContext().getRealPath("WEB-INF/Files/");
////
////            File file = new File(realPath, filename);
////
////
////
////            if (file.exists()) {
////
////                response.setContentType(fileInfo.getMime_type());// 设置Content-Type为文件的MimeType
////
////                response.addHeader("Content-Disposition", "attachment;filename=" + filename);// 设置文件名
////
////                response.setContentLength((int) fileInfo.getLength());
////
////
////
////                //NIO 实现
////                //
////                int bufferSize = 131072;
////
////                FileInputStream fileInputStream = new FileInputStream(file);
////
////                FileChannel fileChannel = fileInputStream.getChannel();
////
////                // 6x128 KB = 768KB byte buffer
////
////                ByteBuffer buff = ByteBuffer.allocateDirect(786432);
////
////                byte[] byteArr = new byte[bufferSize];
////
////                int nRead, nGet;
////
////
////
////                try {
////
////                    while ((nRead = fileChannel.read(buff)) != -1) {
////
////                        if (nRead == 0) {
////
////                            continue;
////
////                        }
////
////                        buff.position(0);
////
////                        buff.limit(nRead);
////
////                        while (buff.hasRemaining()) {
////
////                            nGet = Math.min(buff.remaining(), bufferSize);
////
////                            // read bytes from disk
////
////                            buff.get(byteArr, 0, nGet);
////
////                            // write bytes to output
////
////                            response.getOutputStream().write(byteArr);
////
////                        }
////
////                        buff.clear();
////
////
////
////                        log.debug("download succeed! ---"+filename);
////
////
////
////                    }
////
////                } catch (IOException e) {
////
////                    e.printStackTrace();
////
////                } finally {
////
////                    buff.clear();
////
////                    fileChannel.close();
////
////                    fileInputStream.close();
////
////                }
////
////
////
////            }
////
////        }else{
////
////            throw new Error("file who's id="+id+" is not exist!");
////
////        }
////
////    }
////
////
////
////
////
////}
////
////public void c(Collection<? extends User> u){
////
////}
//
//
//
//
//
//
//
//
//
//
//
//
///**
// * 读取大文件的数据
// */
//
//import org.junit.jupiter.api.Test;
//
//@Test
//public void readBigData() throws Exception {
//        //==============================================
//
//        //字节输入流
//        //意思是文件内容向JVM输入
//        FileInputStream fis = new FileInputStream(file);
//        //开启通道 ，做读操作
//        FileChannel fileCannel = fis.getChannel();
//        //设置缓冲区
//        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
//        int m ;
//        while ((m =fileCannel.read(byteBuffer)) != -1) {
//        System.out.println("==================================================");
//
//        //移位操作，缺少会会重复读取
//        byteBuffer.flip();
//
//        byte[] byteArr = new byte[byteBuffer.limit()];
////            //获取前m个字节
//        byteBuffer.get(byteArr);
//        System.out.println(new String(byteArr));
//          System.out.println("==================================================");
//        }
//
//        byteBuffer.clear();
//        fileCannel.close();
//        fis.close();
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
