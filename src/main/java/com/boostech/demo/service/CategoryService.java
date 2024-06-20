package com.boostech.demo.service;

import com.boostech.demo.dto.CategoryDTO;
import com.boostech.demo.entity.Attribute;
import com.boostech.demo.entity.Category;
import com.boostech.demo.repository.AttributeRepository;
import com.boostech.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    /**
     * Get all categories
     * @return
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Get category by id
     * @param id
     * @return
     */
    public Optional<Category> getCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    /**
     * Create a new category
     * @param categoryDTO
     * @return
     */
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        return categoryRepository.save(category);
    }

    /**
     * Update a category
     * @param id
     * @param categoryDTO
     * @return
     */
    public Category updateCategory(UUID id, CategoryDTO categoryDTO) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setName(categoryDTO.getName());
            return categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category not found");
        }
    }


    /**
     * Delete a category
     * @param id UUID
     */
    public void deleteCategory(UUID id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            if (category.getDeletedAt() != null) {
                throw new RuntimeException("Category already deleted");
            }
            category.setDeletedAt(LocalDateTime.now());
            categoryRepository.save(category);
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    /**
     * Add attributes to a category
     * @param categoryId UUID
     * @param attributeIds List of UUID
     * @return
     */
    @Transactional
    public Category addAttributesToCategory(UUID categoryId, List<UUID> attributeIds) {
        return modifyAttributesInCategory(categoryId, attributeIds, true);
    }

    /**
     * Remove attributes from a category
     * @param categoryId UUID
     * @param attributeIds List of UUID
     * @return
     */
    @Transactional
    public Category removeAttributesFromCategory(UUID categoryId, List<UUID> attributeIds) {
        return modifyAttributesInCategory(categoryId, attributeIds, false);
    }

    /**
     * Modify attributes in category
     * @param categoryId
     * @param attributeIds
     * @param isAdding
     * @return
     */
    private Category modifyAttributesInCategory(UUID categoryId, List<UUID> attributeIds, boolean isAdding) {
        Category category = getCategoryOrThrow(categoryId);
        attributeIds.forEach(attributeId -> modifyAttributeInCategory(category, attributeId, isAdding));
        return categoryRepository.save(category);
    }

    /**
     * Modify attribute in category
     * @param category
     * @param attributeId
     * @param isAdding
     */
    private void modifyAttributeInCategory(Category category, UUID attributeId, boolean isAdding) {
        Attribute attribute = getAttributeOrThrow(attributeId);
        if (isAdding) {
            if (category.getAttributes().contains(attribute)) {
                throw new RuntimeException("Attribute already added to the category");
            }
            category.addAttribute(attribute);
        } else {
            if (!category.getAttributes().contains(attribute)) {
                throw new RuntimeException("Attribute not found in the category");
            }
            category.removeAttribute(attribute);
        }
    }

    /**
     * Get category by id or throw exception
     * @param categoryId UUID
     * @return
     */
    private Category getCategoryOrThrow(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    /**
     * Get attribute by id or throw exception
     * @param attributeId
     * @return
     */
    private Attribute getAttributeOrThrow(UUID attributeId) {
        return attributeRepository.findById(attributeId)
                .orElseThrow(() -> new RuntimeException("Attribute not found"));
    }
}