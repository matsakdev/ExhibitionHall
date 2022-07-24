package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.ExpositionDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@MultipartConfig
@WebServlet
public class UpdateExpositionCommand extends FrontCommand {
    Logger logger = LogManager.getLogger(UpdateExpositionCommand.class);
    static Map<String, String> validationFailures = new HashMap<>();
    static {
        validationFailures.put("fileMessage", "File was not loaded properly");
        validationFailures.put("expTitleMessage", "Title was not entered correctly.");
        validationFailures.put("expAuthorMessage", "Author was not entered correctly.");
        validationFailures.put("startDateMessage", "Set the correct date of beginning");
        validationFailures.put("startTimeMessage", "Set the correct time of beginning");
        validationFailures.put("endDateMessage", "Set the correct date of the ending");
        validationFailures.put("endTimeMessage", "Set the correct time of the ending");
        validationFailures.put("descriptionMessage", "Please, type the description. It must be less than 500 symbols.");
        validationFailures.put("showroomMessage", "Please, select available showrooms");
        validationFailures.put("priceMessage", "Please, set the price of the ticket");
        validationFailures.put("fileWarning", "Please, reload the image");
    }
    //list for not validated fields
    List<String> failedValidation = new ArrayList<>();
    private Timestamp correctedStartDate;
    private Timestamp correctedEndDate;
    private Set<Integer> selectedShowroomsId = new HashSet<>();

    Part filePart;
    String expName;
    String expAuthor;
    String expStartDate;
    String expStartTime;
    String expEndDate;
    String expEndTime;
    String description;
    String[] showrooms;
    String priceString;
    double price;
    long expositionId;
    @Override
    public void process() throws ServletException, IOException {
        boolean isCreating = false;
        try {
            if (request.getSession().getAttribute("currentExpositionId") == null) isCreating = true;
            else expositionId = (long) request.getSession().getAttribute("currentExpositionId");
        }catch (ClassCastException e) {
            logger.error("Exposition ID was not setted correctly");
        }
        filePart = request.getPart("file");
        if (filePart == null && request.getSession().getAttribute("fileInput") != null) {
            filePart = (Part)request.getSession().getAttribute("fileInput");
        }
        expName = request.getParameter("expTitle");
        expAuthor = request.getParameter("expAuthor");
        expStartDate = request.getParameter("startDate");
        expStartTime = request.getParameter("startTime");
        expEndDate = request.getParameter("endDate");
        expEndTime = request.getParameter("endTime");
        description = request.getParameter("description");
        showrooms = request.getParameterValues("showroom");
        priceString = request.getParameter("priceInput");
        if (logger.isDebugEnabled()) {
            logger.debug("Title ==> " + expName + " Author ==> " + expAuthor + " startDate ==> "
                    + expStartDate + "(time: " + expStartTime + ") endDate ==> " + expEndDate + "(time: " + expEndTime + ") "
            + "showrooms ==> " + Arrays.toString(showrooms) + " description ==> " + description);
        }

        boolean dateCorrectness = true;
        correctedStartDate = dateCorrection(expStartDate, expStartTime);
        correctedEndDate = dateCorrection(expEndDate, expEndTime);
        if (correctedStartDate.compareTo(correctedEndDate) > 0) dateCorrectness = false;
            if (!validation() || !dateCorrectness) {
            insertFieldsInformation();
            setErrorMessages();
            response.sendRedirect(request.getContextPath() + "/admin/expositions/" + expositionId);
            return;
        }


            checkShowroomsAvailability();

        String fileName =
                Paths.get(filePart.getSubmittedFileName())
                        .getFileName()
                        .toString();
        //file
        try {
            String filePath = request.getServletContext()
                    .getRealPath("images/");
            File file = new File(filePath.concat(fileName));

            ExpositionDAO expositionDAO = DAOFactory.getInstance().getExpositionDAO();
            if (!expositionDAO.isImagePathAvailable(fileName) || file.exists()) {
                String extension = fileName.substring(fileName.length() - 4);
                fileName = fileName.substring(0, fileName.length() - 4);
                int suffix = 1;
                fileName = fileName.replaceAll("(\\([0-9]*\\))", "");
                String newFileName;
                do {
                    newFileName = fileName + "(" + suffix + ")";
                    file = new File(filePath + newFileName + extension);
                    suffix++;
                } while (!expositionDAO.isImagePathAvailable(newFileName + extension) || file.exists());
                fileName = newFileName + extension;
            }
            logger.debug("fileName ==> " + fileName + " title ==> " + expName + " author ==> " + expAuthor);

//            Exposition exposition = (Exposition) request.getSession().getAttribute("currentExposition");

            InputStream fileContent = filePart.getInputStream();
            Files.copy(fileContent,
                    Paths.get(file.getAbsolutePath()),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println(request.getServletContext().getContextPath());

        } catch (Exception e) {
            logger.info("Problem with checking the fileName in DB or getting/casting Exposition object from sessionScope" + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/expositions/" + expositionId);
        }

        if (isCreating) {
            try {
                Exposition newExposition = new Exposition();
                newExposition.setExpName(expName);
                newExposition.setAuthor(expAuthor);
                newExposition.setDescription(description);
                newExposition.setExpStartDate(correctedStartDate);
                newExposition.setExpFinalDate(correctedEndDate);
                newExposition.setImage(fileName);
                newExposition.setPrice(price);
                boolean isCreated = DAOFactory.getInstance().getExpositionDAO().createExposition(newExposition, selectedShowroomsId);
                if (isCreated) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }
                else {
                    logger.error("Exposition cannot be created");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        //updating DB
        else {
            try {
                Exposition updatedExposition = new Exposition();
                updatedExposition.setId(expositionId);
                updatedExposition.setExpName(expName);
                updatedExposition.setAuthor(expAuthor);
                updatedExposition.setDescription(description);
                updatedExposition.setExpStartDate(correctedStartDate);
                updatedExposition.setExpFinalDate(correctedEndDate);
                updatedExposition.setImage(fileName);
                updatedExposition.setPrice(price);
                boolean isUpdated = DAOFactory.getInstance().getExpositionDAO().updateExposition(updatedExposition, selectedShowroomsId);
                if (isUpdated) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                    return;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }


        request.getSession().setAttribute("uploadedFile", fileName);
        response.sendRedirect(request.getContextPath() + "/admin");
    }

    private boolean checkShowroomsAvailability() {
        boolean isAvailable = true;
        try {
            for (String showroom : showrooms) {
                try {
                    int showroomId = Integer.parseInt(showroom);
                    if (!DAOFactory.getInstance().getShowroomDAO()
                            .isAvailable(showroomId, correctedStartDate, correctedEndDate)) isAvailable = false;
                    selectedShowroomsId.add(showroomId);
                } catch (NumberFormatException e) {
                    logger.warn("Showroom ID not parsed as Integer " + e.getMessage());
                }
            }
            if (!isAvailable) {
                failedValidation.add("showroomMessage");
                request.getSession().setAttribute("showroomsIsFree", false);
                insertFieldsInformation();
                setErrorMessages();
                response.sendRedirect(request.getContextPath() + "/admin/expositions/" + expositionId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private void insertFieldsInformation() {
        HttpSession session = request.getSession();
        session.setAttribute("titleInput", expName);
        session.setAttribute("authorInput", expAuthor);
        session.setAttribute("descriptionInput", description);
        StringBuilder sb = new StringBuilder();
        if (showrooms != null)
            for (String showroom : showrooms) {
                sb.append(showroom + ";");
            }
        session.setAttribute("showroomInput", sb.toString());
    }

    private void setErrorMessages() {
        HttpSession session = request.getSession();
        if (!failedValidation.contains("fileMessage")) {
            failedValidation.add("fileWarning");
        }
        session.setAttribute("fileInput", filePart);
        session.setAttribute("nonValidatedItems", failedValidation);
        session.setAttribute("validationFailures", validationFailures);
    }

    private void correctFormats() {
        expName = expName.trim();
        expName = expName.trim();
        expAuthor = expAuthor.trim();
        expAuthor = expAuthor.trim();
        description = description.trim();
        if (priceString != null)
            try {
                price = Double.parseDouble(priceString);
            } catch (NumberFormatException e) {
                failedValidation.add("priceMessage");
                logger.warn(e.getMessage());
            }
    }

    private Timestamp dateCorrection(String date, String time) {
        Date formattedDate = Date.valueOf(date);
        Time formattedTime = Time.valueOf(time + ":00");
        //format of DB: 1920-01-30 12:30
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dt = LocalDateTime.parse(date + " " + time, dtf);
        return Timestamp.valueOf(dt);
    }

    private boolean validation() {
        correctFormats();
        boolean isFormValidated = true;
        //file
        if (filePart.getSubmittedFileName() == null || filePart.getSubmittedFileName().trim().equals("")) {
                failedValidation.add("fileMessage");
        }
        //title
        if (expName == null || expName.length() > 60 || expName.length() == 0) {
            failedValidation.add("expTitleMessage");
        }
        //author
        if (expAuthor == null || expAuthor.length() > 200 || expAuthor.length() == 0) {
            failedValidation.add("expAuthorMessage");
        }
        //price
        if (priceString == null || priceString.length() == 0 || price <= 0) {
            failedValidation.add("priceMessage");
        }
        //dates
        try {
            DateTimeFormatter dateInputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (expStartDate == null /*|| LocalDate.parse(expStartDate, dateInputFormat).compareTo(LocalDate.now()) < 0 todo validation*/) {
                failedValidation.add("expStartDateMessage");
            }
            if (expStartTime == null) {
                failedValidation.add("expStartTimeMessage");
            }
            if (expEndDate == null /*|| LocalDate.parse(expEndDate, dateInputFormat).compareTo(LocalDate.now()) < 0*/) {
                failedValidation.add("expEndDateMessage");
            }
            if (expEndTime == null) {
                failedValidation.add("expEndTimeMessage");
            }
        } catch (DateTimeParseException e) {
            failedValidation.add(validationFailures.get("expStartDateMessage"));
            failedValidation.add(validationFailures.get("expStartTimeMessage"));
            failedValidation.add(validationFailures.get("expEndDateMessage"));
            failedValidation.add(validationFailures.get("expEndTimeMessage"));
            logger.warn("DateTimeParseException failed. Illegal data from client.");
        }
        //description
        if (description == null || description.length() > 500 || description.length() == 0) {
            failedValidation.add("descriptionMessage");
        }
        //showrooms
        if (showrooms == null || showrooms.length == 0) {
            failedValidation.add("showroomMessage");
        }
        if (failedValidation.size() != 0) isFormValidated = false;
        return isFormValidated;
    }
}
