package com.reviewms.mapper;

import com.reviewms.bean.Review;
import com.reviewms.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {
    Review toBean(ReviewEntity reviewEntity);
    ReviewEntity toEntity(Review review);
    List<Review> toBeanList(List<ReviewEntity> reviewEntities);
    @Mapping(target = "id",ignore = true)
    void updateEntityFromBean(Review review, @MappingTarget ReviewEntity reviewEntity);
}

