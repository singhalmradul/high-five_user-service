package io.github.singhalmradul.userservice.utilities;

import static com.cloudinary.utils.ObjectUtils.asMap;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;

import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor_ = @Autowired)
@Component
public class CloudinaryUtilities {

    private final Cloudinary cloudinary;

    public String uploadWithId(Part part, String publicId) {

        var parameters = asMap(
            "folder", "high-five/users",
            "unique_filename", false,
            "overwrite", true,
            "resource_type", "auto",
            "public_id", publicId
        );

        try {

            return cloudinary
                .uploader()
                .upload(part.getInputStream().readAllBytes(), parameters)
                .get("url")
                .toString();

        } catch (IOException e) {

            throw new RuntimeException(e.getMessage());
        }
    }
}
