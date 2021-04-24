package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Category;
import kr.co.fastcampus.eatgo.domain.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CategoryServiceTests {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(categoryRepository);

        mockCategoryRepository();
    }

    private void mockCategoryRepository() {
        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder().name("korea food").build());
        given(categoryRepository.findAll()).willReturn(categories);
    }

    @Test
    public void getCategory() {
        List<Category> categories = categoryService.getCategory();
        Category category = categories.get(0);
        assertThat(category.getName(),is("korea food"));
    }

    @Test
    public void addCategory() {
        Category category = categoryService.addCategory("korea food");
        assertThat(category.getName(),is("korea food"));
        verify(categoryRepository).save(any());
    }

}