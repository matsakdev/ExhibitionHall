package com.matsak.exhibitionhall.commands;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;

@MultipartConfig
@WebServlet
public class UpdateExpositionCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {

        Part filePart = request.getPart("file");
        String expName = request.getParameter("expTitle");
        String expAuthor = request.getParameter("expAuthor");
        System.out.println("filePart ==> " + filePart);
        System.out.println("title ==> " + expName);
        System.out.println("author ==> " + expAuthor);

        String fileName =
                Paths.get(filePart.getSubmittedFileName())
                        .getFileName()
                        .toString();

        System.out.println("fileName ==> " + fileName);

        String outputFile = request.getServletContext()
                .getRealPath("images/")
                .concat(fileName);
        System.out.println("address ==> " + outputFile);


        InputStream fileContent = filePart.getInputStream();
        Files.copy(fileContent,
                Paths.get(outputFile),
                StandardCopyOption.REPLACE_EXISTING);

        request.getSession().setAttribute("uploadedFile", fileName);
        response.sendRedirect(request.getContextPath() + "/admin");
    }
}
