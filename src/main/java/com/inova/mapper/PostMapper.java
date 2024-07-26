package com.inova.mapper;

import com.inova.dto.PostDTO;
import com.inova.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "user.id" , target = "userId")
    PostDTO toDto(Post post);

    @Mapping(target = "user.id" , source = "userId")
    Post toEntity(PostDTO postDTO);

    default Page<PostDTO> toResponsePageable(Page<Post> postPage) {
        return postPage.map(this::toDto);

    }
}