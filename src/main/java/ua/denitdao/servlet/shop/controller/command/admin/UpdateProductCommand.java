package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.controller.command.RequestMapper;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.exception.ValidationException;
import ua.denitdao.servlet.shop.model.service.ProductService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.ExceptionMessages;
import ua.denitdao.servlet.shop.util.Paths;
import ua.denitdao.servlet.shop.util.Validator;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class UpdateProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateProductCommand.class);
    private final ProductService productService;

    public UpdateProductCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        productService = serviceFactory.getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ValidationException {
        Validator.validateNonEmptyRequest(req);
        long id = Long.parseLong(req.getParameter("id"));
        String fileName = req.getParameter("image_url");

        fileName = saveFile(req, fileName);

        Map<String, Product> localizedProduct = RequestMapper.buildLocalizedProduct(req);
        String finalFileName = fileName;
        localizedProduct.values()
                .forEach(p -> {
                    p.setId(id);
                    p.setImageUrl(finalFileName);
                    Validator.validateProduct(p);
                });
        if (!productService.update(localizedProduct))
            throw new ValidationException("Failed to edit product", ExceptionMessages.FAIL_UPDATE_PRODUCT);

        return "redirect:" + Paths.VIEW_PRODUCT + "?id=" + id;
    }

    private String saveFile(HttpServletRequest req, String fileName) {
        try {
            Part imageFile = req.getPart("image");

            if (imageFile.getSize() > 0) {
                String uploadPath = req.getServletContext().getRealPath(Paths.IMAGES);
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                fileName = (fileName == null) ? UUID.randomUUID().toString() : fileName;
                imageFile.write(uploadPath + File.separator + fileName);
            }
        } catch (Exception e) {
            logger.warn(e);
            throw new ValidationException("Failed to save image", ExceptionMessages.FAIL_UPDATE_PRODUCT);
        }
        return fileName;
    }
}
