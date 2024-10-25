package hyundai.blog.image_upload.dto;

public record ImageUploadResponse(
        String uploadedUrl
) {

    public static ImageUploadResponse of(String uploadedUrl) {
        return new ImageUploadResponse(uploadedUrl);
    }

}
