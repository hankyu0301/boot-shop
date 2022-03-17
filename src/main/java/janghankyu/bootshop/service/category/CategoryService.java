package janghankyu.bootshop.service.category;

import janghankyu.bootshop.dto.category.CategoryDto;
import janghankyu.bootshop.entity.category.Category;
import janghankyu.bootshop.entity.category.CategoryCreateRequest;
import janghankyu.bootshop.exception.CategoryNotFoundException;
import janghankyu.bootshop.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> readAll() {
        List<Category> categories = categoryRepository.findAllOrderByParentIdAscNullsFirstCategoryIdAsc();
        return CategoryDto.toDtoList(categories);
    }

    public void create(CategoryCreateRequest req) {
        Category parent = Optional.ofNullable(req.getParentId())
                .map(id -> categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new))
                .orElse(null);
        categoryRepository.save(new Category(req.getName(), parent));
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
