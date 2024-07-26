package com.inova.mapper;

import com.inova.dto.ReviewDTO;
import com.inova.model.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDTO toDto(Review review);
    Review toEntity(ReviewDTO reviewDTO);
}