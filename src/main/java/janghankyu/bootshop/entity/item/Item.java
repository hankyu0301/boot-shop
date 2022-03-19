package janghankyu.bootshop.entity.item;

import janghankyu.bootshop.entity.BaseEntity;
import janghankyu.bootshop.entity.category.Category;
import janghankyu.bootshop.entity.member.Member;
import janghankyu.bootshop.exception.OutOfStockException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private int price;

    private int stockNumber;

    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ItemImage> images;

    public Item(String itemName, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus, Category category, Member member, List<ItemImage> images) {
        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.category = category;
        this.member = member;
        addImages(images);
    }

    private void addImages(List<ItemImage> added) {
        added.stream().forEach(i -> {
            images.add(i);
            i.initPost(this);
        });
    }

    private ImageUpdatedResult findImageUpdatedResult(List<MultipartFile> addedImageFiles, List<Long> deletedImageIds) {
        List<ItemImage> addedImages = convertImageFilesToImages(addedImageFiles);
        List<ItemImage> deletedImages = convertImageIdsToImages(deletedImageIds);
        return new ImageUpdatedResult(addedImageFiles, addedImages, deletedImages);
    }

    //삭제되어야 하는 이미지는 이미지의 id를 받아서 this.images와 대조함
    //List<Long> imageIds -> convertImageIdToImage -> List<ItemImage>
    private List<ItemImage> convertImageIdsToImages(List<Long> imageIds) {
        return imageIds.stream()
                //stream().filter(image->image.getId().eqauls(id))
                .map(id -> convertImageIdToImage(id))
                .filter(i -> i.isPresent())
                .map(i -> i.get())
                .collect(toList());
    }

    private Optional<ItemImage> convertImageIdToImage(Long id) {
        return this.images.stream().filter(i -> i.getId().equals(id)).findAny();
    }

    //List<MultipartFile> imageFiles -> new ItemImage() -> collect toList
    private List<ItemImage> convertImageFilesToImages(List<MultipartFile> imageFiles) {
        return imageFiles.stream().map(imageFile -> new ItemImage(imageFile.getOriginalFilename())).collect(toList());
    }

    @Getter
    @AllArgsConstructor
    public static class ImageUpdatedResult {
        private List<MultipartFile> addedImageFiles;
        private List<ItemImage> addedImages;
        private List<ItemImage> deletedImages;
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}
