package janghankyu.bootshop.entity.item;

import janghankyu.bootshop.exception.UnsupportedImageFormatException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long id;

    private String uniqueName;

    private String originName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item item;

    private final static String supportedExtension [] = {"jpg", "jpeg", "gif", "bmp", "png"};

    public ItemImage(String originName) {
        this.uniqueName = generateUniqueName(extractExtension(originName));
        this.originName = originName;
    }

    public void initPost(Item item) {
        if(this.item == null) {
            this.item = item;
        }
    }

    private String generateUniqueName(String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }

    private String extractExtension(String originName) {
        try {
            String ext = originName.substring(originName.lastIndexOf(".") + 1);
            if (isSupportedFormat(ext)) return ext;
        } catch (StringIndexOutOfBoundsException e) {
        }
        throw new UnsupportedImageFormatException();
    }

    private boolean isSupportedFormat(String ext) {
        return Arrays.stream(supportedExtension).anyMatch(e -> e.equalsIgnoreCase(ext));
    }
}
