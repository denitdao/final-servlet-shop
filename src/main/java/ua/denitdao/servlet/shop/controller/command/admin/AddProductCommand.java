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

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class AddProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddProductCommand.class);
    private final ProductService productService;

    public AddProductCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        productService = serviceFactory.getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ValidationException {
        Validator.validateNonEmptyRequest(req);
        long categoryId = Long.parseLong(req.getParameter("category_id"));
        String fileName = saveFile(req);

        Map<String, Product> localizedProduct = RequestMapper.buildLocalizedProduct(req);
        localizedProduct.values()
                .forEach(p -> {
                    p.setImageUrl(fileName);
                    Validator.validateProduct(p);
                });

        if (!productService.create(categoryId, localizedProduct))
            throw new ValidationException("Failed to create product", ExceptionMessages.FAIL_CREATE_PRODUCT);

        return "redirect:" + Paths.VIEW_CATEGORY + "?id=" + categoryId;
    }

    private String saveFile(HttpServletRequest req) {
        try {
            Part imageFile = req.getPart("image");
            String fileName = null;
            if (imageFile.getSize() > 0) {
                String uploadPath = req.getServletContext().getRealPath(Paths.IMAGES);
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                fileName = UUID.randomUUID().toString();
                imageFile.write(uploadPath + File.separator + fileName);
            }
            return fileName;
        } catch (Exception e) {
            logger.warn(e);
            throw new ValidationException("Failed to save image", ExceptionMessages.FAIL_CREATE_PRODUCT);
        }
    }
}